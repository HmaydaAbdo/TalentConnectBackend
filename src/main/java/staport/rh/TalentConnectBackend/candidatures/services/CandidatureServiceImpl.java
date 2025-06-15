package staport.rh.TalentConnectBackend.candidatures.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import staport.rh.TalentConnectBackend.candidatures.dtos.CandidatureCriteria;
import staport.rh.TalentConnectBackend.candidatures.dtos.CandidatureRequest;
import staport.rh.TalentConnectBackend.candidatures.dtos.CandidatureResponse;
import staport.rh.TalentConnectBackend.candidatures.dtos.PhoneEvaluationRequest;
import staport.rh.TalentConnectBackend.candidatures.dtos.FirstPhysicalInterviewRequest;
import staport.rh.TalentConnectBackend.candidatures.dtos.SecondPhysicalInterviewRequest;
import staport.rh.TalentConnectBackend.candidatures.entities.Candidature;
import staport.rh.TalentConnectBackend.candidatures.enums.CandidatureStatus;
import staport.rh.TalentConnectBackend.candidatures.mappers.CandidatureMapper;
import staport.rh.TalentConnectBackend.candidatures.repositories.CandidatureRepository;
import staport.rh.TalentConnectBackend.candidatures.specifications.CandidatureSpecification;
import staport.rh.TalentConnectBackend.exception.customExceptions.ResourceNotFoundException;
import staport.rh.TalentConnectBackend.metiers.repositories.MetierRepository;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
@Transactional
public class CandidatureServiceImpl implements ICandidatureService {

    private final CandidatureRepository candidatureRepository;
    private final MetierRepository metierRepository;
    private final CandidatureMapper candidatureMapper;

    public CandidatureServiceImpl(CandidatureRepository candidatureRepository, MetierRepository metierRepository, CandidatureMapper candidatureMapper) {
        this.candidatureRepository = candidatureRepository;
        this.metierRepository = metierRepository;
        this.candidatureMapper = candidatureMapper;
    }

    @Override
    public CandidatureResponse createCandidature(CandidatureRequest request, MultipartFile cvFile) {
        if (cvFile == null || cvFile.isEmpty()) {
            throw new IllegalArgumentException("Le CV est obligatoire.");
        }
        Candidature candidature = candidatureMapper.toEntity(request);
        try {
            candidature.setCvData(cvFile.getBytes());
            candidature.setCvFilename(cvFile.getOriginalFilename());
            candidature.setCvContentType(cvFile.getContentType());
        } catch (IOException e) {
            throw new RuntimeException("Échec du stockage des données du CV", e);
        }
        // Set the initial status for a new candidature
        candidature.setStatus(CandidatureStatus.ENTRETIEN_TELEPHONIQUE_PLANIFIE);

        Candidature savedCandidature = candidatureRepository.save(candidature);
        return candidatureMapper.toResponse(savedCandidature);
    }

    @Override
    public CandidatureResponse updateCandidature(Long id, CandidatureRequest request, MultipartFile cvFile) {
        Candidature existingCandidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));

        // Mapstruct will handle the update including dateEntretienTelephonique if present in the request
        candidatureMapper.updateEntityFromRequest(request, existingCandidature);

        if (cvFile != null) {
            try {
                if (cvFile.isEmpty()) {
                    existingCandidature.setCvData(null);
                    existingCandidature.setCvFilename(null);
                    existingCandidature.setCvContentType(null);
                } else {
                    existingCandidature.setCvData(cvFile.getBytes());
                    existingCandidature.setCvFilename(cvFile.getOriginalFilename());
                    existingCandidature.setCvContentType(cvFile.getContentType());
                }
            } catch (IOException e) {
                throw new RuntimeException("Échec du stockage des données du CV", e);
            }
        }
        Candidature updatedCandidature = candidatureRepository.save(existingCandidature);
        return candidatureMapper.toResponse(updatedCandidature);
    }

    @Override
    public void deleteCandidature(Long id) {
        if (!candidatureRepository.existsById(id)) {
            throw new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable.");
        }
        candidatureRepository.deleteById(id);
    }

    @Override
    public CandidatureResponse getCandidatureById(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));
        return candidatureMapper.toResponse(candidature);
    }

    @Override
    public Page<CandidatureResponse> searchCandidatures(CandidatureCriteria criteria) {
        Sort sort = Sort.by(Sort.Direction.fromString(criteria.getSortDirection()), criteria.getSortBy());
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), sort);
        Specification<Candidature> spec = CandidatureSpecification.byCriteria(criteria);
        Page<Candidature> candidaturesPage = candidatureRepository.findAll(spec, pageable);
        return candidaturesPage.map(candidatureMapper::toResponse);
    }

    @Override
    public byte[] downloadCv(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));
        if (candidature.getCvData() == null || candidature.getCvData().length == 0) {
            throw new ResourceNotFoundException("Aucun CV trouvé pour la candidature avec l'ID " + id);
        }
        return candidature.getCvData();
    }

    @Override
    public String getCvFilename(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));
        if (candidature.getCvFilename() == null || candidature.getCvFilename().isEmpty()) {
            return "cv_" + id + ".pdf"; // Default filename if not stored
        }
        return candidature.getCvFilename();
    }

    @Override
    public String getCvContentType(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));
        if (candidature.getCvContentType() == null || candidature.getCvContentType().isEmpty()) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE; // Default to generic binary stream if not stored
        }
        return candidature.getCvContentType();
    }

    @Override
    public void deleteCv(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));
        if (candidature.getCvData() == null || candidature.getCvData().length == 0) {
            throw new ResourceNotFoundException("Aucun CV trouvé à supprimer pour la candidature avec l'ID " + id);
        }
        candidature.setCvData(null);
        candidature.setCvFilename(null);
        candidature.setCvContentType(null);
        candidatureRepository.save(candidature);
    }

    // --- LIFECYCLE METHODS WITH REPORTS ---

    @Override
    public void passerEvaluationTelephonique(Long id, PhoneEvaluationRequest request, MultipartFile reportFile) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature introuvable."));

        // Enforce correct status for the transition
        if (candidature.getStatus() != CandidatureStatus.ENTRETIEN_TELEPHONIQUE_PLANIFIE) {
            throw new IllegalStateException("La candidature doit être au statut 'Entretien téléphonique planifié' pour passer l'évaluation téléphonique. Statut actuel : " + candidature.getStatus());
        }

        candidature.setEvaluationTelephonique(request.getEvaluationTelephonique());
        // Set the date of the phone interview
        candidature.setDateEntretienTelephonique(request.getDateEntretienTelephonique());
        // Set the date for the *next* step (first physical interview)
        candidature.setDatePremierEntretienPhysique(request.getDatePremierEntretienPhysique());


        if (reportFile != null) {
            try {
                if (reportFile.isEmpty()) {
                    candidature.setRapportEvaluationTelephoniqueData(null);
                    candidature.setRapportEvaluationTelephoniqueFilename(null);
                    candidature.setRapportEvaluationTelephoniqueContentType(null);
                } else {
                    candidature.setRapportEvaluationTelephoniqueData(reportFile.getBytes());
                    candidature.setRapportEvaluationTelephoniqueFilename(reportFile.getOriginalFilename());
                    candidature.setRapportEvaluationTelephoniqueContentType(reportFile.getContentType());
                }
            } catch (IOException e) {
                throw new RuntimeException("Échec du stockage des données du rapport d'évaluation téléphonique", e);
            }
        }
        // Transition to the next status
        candidature.setStatus(CandidatureStatus.ENTRETIEN_PHYSIQUE_1_PLANIFIE);
        candidatureRepository.save(candidature);
    }

    @Override
    public void validerPremierEntretienPhysique(Long id, FirstPhysicalInterviewRequest request, MultipartFile reportFile) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature introuvable."));

        // Enforce correct status for the transition
        if (candidature.getStatus() != CandidatureStatus.ENTRETIEN_PHYSIQUE_1_PLANIFIE) {
            throw new IllegalStateException("La candidature doit être au statut 'Entretien physique (1er) planifié' pour valider le premier entretien physique. Statut actuel : " + candidature.getStatus());
        }

        candidature.setPremiereEvaluationPhysique(request.getPremiereEvaluationPhysique());
        // Set the date for the *next* step (second physical interview)
        candidature.setDateDeuxiemeEntretienPhysique(request.getDateDeuxiemeEntretienPhysique());

        if (reportFile != null) {
            try {
                if (reportFile.isEmpty()) {
                    candidature.setRapportPremiereEvaluationPhysiqueData(null);
                    candidature.setRapportPremiereEvaluationPhysiqueFilename(null);
                    candidature.setRapportPremiereEvaluationPhysiqueContentType(null);
                } else {
                    candidature.setRapportPremiereEvaluationPhysiqueData(reportFile.getBytes());
                    candidature.setRapportPremiereEvaluationPhysiqueFilename(reportFile.getOriginalFilename());
                    candidature.setRapportPremiereEvaluationPhysiqueContentType(reportFile.getContentType());
                }
            } catch (IOException e) {
                throw new RuntimeException("Échec du stockage des données du rapport du premier entretien physique", e);
            }
        }
        // Transition to the next status
        candidature.setStatus(CandidatureStatus.ENTRETIEN_PHYSIQUE_2_PLANIFIE);
        candidatureRepository.save(candidature);
    }

    @Override
    public void validerDeuxiemeEntretienPhysique(Long id, SecondPhysicalInterviewRequest request, MultipartFile reportFile) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature introuvable."));

        // Enforce correct status for the transition
        if (candidature.getStatus() != CandidatureStatus.ENTRETIEN_PHYSIQUE_2_PLANIFIE) {
            throw new IllegalStateException("La candidature doit être au statut 'Entretien physique (2ème) planifié' pour valider le deuxième entretien physique. Statut actuel : " + candidature.getStatus());
        }

        if (request.getFinalStatus() != CandidatureStatus.EMBAUCHE && request.getFinalStatus() != CandidatureStatus.REJETE) {
            throw new IllegalArgumentException("Le statut final après le deuxième entretien doit être 'EMBAUCHE' ou 'REJETE'.");
        }

        candidature.setDeuxiemeEvaluationPhysique(request.getDeuxiemeEvaluationPhysique());

        if (reportFile != null) {
            try {
                if (reportFile.isEmpty()) {
                    candidature.setRapportDeuxiemeEvaluationPhysiqueData(null);
                    candidature.setRapportDeuxiemeEvaluationPhysiqueFilename(null);
                    candidature.setRapportDeuxiemeEvaluationPhysiqueContentType(null);
                } else {
                    candidature.setRapportDeuxiemeEvaluationPhysiqueData(reportFile.getBytes());
                    candidature.setRapportDeuxiemeEvaluationPhysiqueFilename(reportFile.getOriginalFilename());
                    candidature.setRapportDeuxiemeEvaluationPhysiqueContentType(reportFile.getContentType());
                }
            } catch (IOException e) {
                throw new RuntimeException("Échec du stockage des données du rapport du deuxième entretien physique", e);
            }
        }
        // Transition to the final status (HIRED or REJECTED)
        candidature.setStatus(request.getFinalStatus());
        candidatureRepository.save(candidature);
    }

    // --- REPORT DOWNLOAD METHODS

    @Override
    public byte[] downloadRapportEvaluationTelephonique(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));
        if (candidature.getRapportEvaluationTelephoniqueData() == null || candidature.getRapportEvaluationTelephoniqueData().length == 0) {
            throw new ResourceNotFoundException("Aucun rapport d'évaluation téléphonique trouvé pour la candidature avec l'ID " + id);
        }
        return candidature.getRapportEvaluationTelephoniqueData();
    }

    @Override
    public String getRapportEvaluationTelephoniqueFilename(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));
        if (candidature.getRapportEvaluationTelephoniqueFilename() == null || candidature.getRapportEvaluationTelephoniqueFilename().isEmpty()) {
            return "rapport_evaluation_telephonique_" + id + ".pdf";
        }
        return candidature.getRapportEvaluationTelephoniqueFilename();
    }

    @Override
    public String getRapportEvaluationTelephoniqueContentType(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));
        if (candidature.getRapportEvaluationTelephoniqueContentType() == null || candidature.getRapportEvaluationTelephoniqueContentType().isEmpty()) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return candidature.getRapportEvaluationTelephoniqueContentType();
    }

    @Override
    public byte[] downloadRapportPremiereEvaluationPhysique(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));
        if (candidature.getRapportPremiereEvaluationPhysiqueData() == null || candidature.getRapportPremiereEvaluationPhysiqueData().length == 0) {
            throw new ResourceNotFoundException("Aucun rapport du premier entretien physique trouvé pour la candidature avec l'ID " + id);
        }
        return candidature.getRapportPremiereEvaluationPhysiqueData();
    }

    @Override
    public String getRapportPremiereEvaluationPhysiqueFilename(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));
        if (candidature.getRapportPremiereEvaluationPhysiqueFilename() == null || candidature.getRapportPremiereEvaluationPhysiqueFilename().isEmpty()) {
            return "rapport_premiere_evaluation_physique_" + id + ".pdf";
        }
        return candidature.getRapportPremiereEvaluationPhysiqueFilename();
    }

    @Override
    public String getRapportPremiereEvaluationPhysiqueContentType(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));
        if (candidature.getRapportPremiereEvaluationPhysiqueContentType() == null || candidature.getRapportPremiereEvaluationPhysiqueContentType().isEmpty()) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return candidature.getRapportPremiereEvaluationPhysiqueContentType();
    }

    @Override
    public byte[] downloadRapportDeuxiemeEvaluationPhysique(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));
        if (candidature.getRapportDeuxiemeEvaluationPhysiqueData() == null || candidature.getRapportDeuxiemeEvaluationPhysiqueData().length == 0) {
            throw new ResourceNotFoundException("Aucun rapport du deuxième entretien physique trouvé pour la candidature avec l'ID " + id);
        }
        return candidature.getRapportDeuxiemeEvaluationPhysiqueData();
    }

    @Override
    public String getRapportDeuxiemeEvaluationPhysiqueFilename(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));
        if (candidature.getRapportDeuxiemeEvaluationPhysiqueFilename() == null || candidature.getRapportDeuxiemeEvaluationPhysiqueFilename().isEmpty()) {
            return "rapport_deuxieme_evaluation_physique_" + id + ".pdf";
        }
        return candidature.getRapportDeuxiemeEvaluationPhysiqueFilename();
    }

    @Override
    public String getRapportDeuxiemeEvaluationPhysiqueContentType(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));
        if (candidature.getRapportDeuxiemeEvaluationPhysiqueContentType() == null || candidature.getRapportDeuxiemeEvaluationPhysiqueContentType().isEmpty()) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return candidature.getRapportDeuxiemeEvaluationPhysiqueContentType();
    }


    // --- REPORT DELETE METHODS ---

    private void deleteReportData(Long id, Function<Candidature, byte[]> dataExtractor, BiConsumer<Candidature, byte[]> dataSetter,
                                  BiConsumer<Candidature, String> filenameSetter, BiConsumer<Candidature, String> contentTypeSetter,
                                  String reportName) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature avec l'ID " + id + " introuvable."));

        if (dataExtractor.apply(candidature) == null || dataExtractor.apply(candidature).length == 0) {
            throw new ResourceNotFoundException("Aucun " + reportName + " trouvé à supprimer pour la candidature avec l'ID " + id);
        }

        dataSetter.accept(candidature, null);
        filenameSetter.accept(candidature, null);
        contentTypeSetter.accept(candidature, null);
        candidatureRepository.save(candidature);
    }

    @Override
    public void deleteRapportEvaluationTelephonique(Long id) {
        deleteReportData(id, Candidature::getRapportEvaluationTelephoniqueData, Candidature::setRapportEvaluationTelephoniqueData,
                Candidature::setRapportEvaluationTelephoniqueFilename, Candidature::setRapportEvaluationTelephoniqueContentType,
                "rapport d'évaluation téléphonique");
    }

    @Override
    public void deleteRapportPremiereEvaluationPhysique(Long id) {
        deleteReportData(id, Candidature::getRapportPremiereEvaluationPhysiqueData, Candidature::setRapportPremiereEvaluationPhysiqueData,
                Candidature::setRapportPremiereEvaluationPhysiqueFilename, Candidature::setRapportPremiereEvaluationPhysiqueContentType,
                "rapport du premier entretien physique");
    }

    @Override
    public void deleteRapportDeuxiemeEvaluationPhysique(Long id) {
        deleteReportData(id, Candidature::getRapportDeuxiemeEvaluationPhysiqueData, Candidature::setRapportDeuxiemeEvaluationPhysiqueData,
                Candidature::setRapportDeuxiemeEvaluationPhysiqueFilename, Candidature::setRapportDeuxiemeEvaluationPhysiqueContentType,
                "rapport du deuxième entretien physique");
    }
}