package staport.rh.TalentConnectBackend.candidatures.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import staport.rh.TalentConnectBackend.candidatures.dtos.CandidatureRequest;
import staport.rh.TalentConnectBackend.candidatures.dtos.CandidatureResponse;
import staport.rh.TalentConnectBackend.candidatures.entities.Candidature;
import staport.rh.TalentConnectBackend.candidatures.enums.CandidatureStatus; // Import the updated enum
import staport.rh.TalentConnectBackend.exception.customExceptions.ResourceNotFoundException;
import staport.rh.TalentConnectBackend.metiers.entities.Metier;
import staport.rh.TalentConnectBackend.metiers.repositories.MetierRepository;

@Mapper(componentModel = "spring")
public abstract class CandidatureMapper {

    @Autowired
    protected MetierRepository metierRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "metier", source = "metierId", qualifiedByName = "mapMetierIdToMetier")
    @Mapping(target = "cvData", ignore = true)
    @Mapping(target = "rapportEvaluationTelephoniqueData", ignore = true)
    @Mapping(target = "rapportPremiereEvaluationPhysiqueData", ignore = true)
    @Mapping(target = "rapportDeuxiemeEvaluationPhysiqueData", ignore = true)
    // Set default status for new candidatures to ENTRETIEN_TELEPHONIQUE_PLANIFIE
    @Mapping(target = "status", expression = "java(request.getStatus() != null ? request.getStatus() : staport.rh.TalentConnectBackend.candidatures.enums.CandidatureStatus.ENTRETIEN_TELEPHONIQUE_PLANIFIE)")
    // New mapping for dateEntretienTelephonique
    @Mapping(target = "dateEntretienTelephonique", source = "dateEntretienTelephonique")
    public abstract Candidature toEntity(CandidatureRequest request);

    @Mapping(target = "metierId", source = "metier.id")
    @Mapping(target = "metierName", source = "metier.metierName")
    @Mapping(target = "hasCv", expression = "java(candidature.getCvData() != null && candidature.getCvData().length > 0)")
    @Mapping(target = "hasRapportEvaluationTelephonique", expression = "java(candidature.getRapportEvaluationTelephoniqueData() != null && candidature.getRapportEvaluationTelephoniqueData().length > 0)")
    @Mapping(target = "hasRapportPremiereEvaluationPhysique", expression = "java(candidature.getRapportPremiereEvaluationPhysiqueData() != null && candidature.getRapportPremiereEvaluationPhysiqueData().length > 0)")
    @Mapping(target = "hasRapportDeuxiemeEvaluationPhysique", expression = "java(candidature.getRapportDeuxiemeEvaluationPhysiqueData() != null && candidature.getRapportDeuxiemeEvaluationPhysiqueData().length > 0)")
    @Mapping(target = "datePremierEntretienPhysique", source = "datePremierEntretienPhysique")
    @Mapping(target = "dateDeuxiemeEntretienPhysique", source = "dateDeuxiemeEntretienPhysique")
    // New mapping for dateEntretienTelephonique
    @Mapping(target = "dateEntretienTelephonique", source = "dateEntretienTelephonique")
    public abstract CandidatureResponse toResponse(Candidature candidature);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "metier", source = "metierId", qualifiedByName = "mapMetierIdToMetier")
    @Mapping(target = "cvData", ignore = true)
    @Mapping(target = "rapportEvaluationTelephoniqueData", ignore = true)
    @Mapping(target = "rapportPremiereEvaluationPhysiqueData", ignore = true)
    @Mapping(target = "rapportDeuxiemeEvaluationPhysiqueData", ignore = true)
    @Mapping(target = "status", expression = "java(request.getStatus() != null ? request.getStatus() : target.getStatus())")
    // New mapping for dateEntretienTelephonique when updating
    @Mapping(target = "dateEntretienTelephonique", source = "dateEntretienTelephonique")
    public abstract void updateEntityFromRequest(CandidatureRequest request, @MappingTarget Candidature target);

    @Named("mapMetierIdToMetier")
    public Metier mapMetierIdToMetier(Long metierId) {
        if (metierId == null) {
            return null;
        }
        return metierRepository.findById(metierId)
                .orElseThrow(() -> new ResourceNotFoundException("Metier with ID " + metierId + " not found for mapping."));
    }
}