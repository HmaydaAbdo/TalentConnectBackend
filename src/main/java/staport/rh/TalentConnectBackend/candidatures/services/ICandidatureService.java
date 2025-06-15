// staport.rh.TalentConnectBackend.candidatures.services.ICandidatureService

package staport.rh.TalentConnectBackend.candidatures.services;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import staport.rh.TalentConnectBackend.candidatures.dtos.CandidatureCriteria;
import staport.rh.TalentConnectBackend.candidatures.dtos.CandidatureRequest;
import staport.rh.TalentConnectBackend.candidatures.dtos.CandidatureResponse;
import staport.rh.TalentConnectBackend.candidatures.dtos.FirstPhysicalInterviewRequest;
import staport.rh.TalentConnectBackend.candidatures.dtos.PhoneEvaluationRequest;
import staport.rh.TalentConnectBackend.candidatures.dtos.SecondPhysicalInterviewRequest;


public interface ICandidatureService {
    CandidatureResponse createCandidature(CandidatureRequest request, MultipartFile cvFile);
    CandidatureResponse updateCandidature(Long id, CandidatureRequest request, MultipartFile cvFile);
    void deleteCandidature(Long id);
    CandidatureResponse getCandidatureById(Long id);
    Page<CandidatureResponse> searchCandidatures(CandidatureCriteria criteria);

    byte[] downloadCv(Long id);
    String getCvFilename(Long id);
    String getCvContentType(Long id);
    void deleteCv(Long id);

    void passerEvaluationTelephonique(Long id, PhoneEvaluationRequest request, MultipartFile reportFile);
    void validerPremierEntretienPhysique(Long id, FirstPhysicalInterviewRequest request, MultipartFile reportFile);
    void validerDeuxiemeEntretienPhysique(Long id, SecondPhysicalInterviewRequest request, MultipartFile reportFile);

    byte[] downloadRapportEvaluationTelephonique(Long id);
    // Add these new methods for report metadata
    String getRapportEvaluationTelephoniqueFilename(Long id);
    String getRapportEvaluationTelephoniqueContentType(Long id);

    byte[] downloadRapportPremiereEvaluationPhysique(Long id);
    // Add these new methods for report metadata
    String getRapportPremiereEvaluationPhysiqueFilename(Long id);
    String getRapportPremiereEvaluationPhysiqueContentType(Long id);

    byte[] downloadRapportDeuxiemeEvaluationPhysique(Long id);
    // Add these new methods for report metadata
    String getRapportDeuxiemeEvaluationPhysiqueFilename(Long id);
    String getRapportDeuxiemeEvaluationPhysiqueContentType(Long id);

    void deleteRapportEvaluationTelephonique(Long id);
    void deleteRapportPremiereEvaluationPhysique(Long id);
    void deleteRapportDeuxiemeEvaluationPhysique(Long id);
}