package staport.rh.TalentConnectBackend.candidatures.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import staport.rh.TalentConnectBackend.candidatures.enums.CandidatureStatus;

import java.time.OffsetDateTime;

public class CandidatureRequest {

    @NotBlank(message = "Le nom complet est requis")
    @NotNull(message = "Le nom complet est requis")
    private String fullName;

    @Size(max = 20, message = "Le numéro de téléphone ne peut pas dépasser 20 caractères")
    private String phoneNumber;

    @NotNull(message = "L'ID métier est requis")
    private Long metierId;

    // Added the new field for phone interview date
    private OffsetDateTime dateEntretienTelephonique;

    // These fields typically won't be present when creating a new candidature
    // They are usually updated in subsequent steps (e.g., via PhoneEvaluationRequest)
    private String evaluationTelephonique;
    private String premiereEvaluationPhysique;
    private String deuxiemeEvaluationPhysique;

    private OffsetDateTime datePremierEntretienPhysique;
    private OffsetDateTime dateDeuxiemeEntretienPhysique;

    // Initial status can be handled by the backend default or provided, but often defaults to NEW_APPLICATION
    private CandidatureStatus status;

    public CandidatureRequest() {
    }

    public CandidatureRequest(String fullName , String phoneNumber, Long metierId,
                              OffsetDateTime dateEntretienTelephonique, // Added to constructor
                              String evaluationTelephonique, String premiereEvaluationPhysique,
                              String deuxiemeEvaluationPhysique, OffsetDateTime datePremierEntretienPhysique,
                              OffsetDateTime dateDeuxiemeEntretienPhysique, CandidatureStatus status) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.metierId = metierId;
        this.dateEntretienTelephonique = dateEntretienTelephonique; // Initialized in constructor
        this.evaluationTelephonique = evaluationTelephonique;
        this.premiereEvaluationPhysique = premiereEvaluationPhysique;
        this.deuxiemeEvaluationPhysique = deuxiemeEvaluationPhysique;
        this.datePremierEntretienPhysique = datePremierEntretienPhysique;
        this.dateDeuxiemeEntretienPhysique = dateDeuxiemeEntretienPhysique;
        this.status = status;
    }

    // Getters & Setters
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getMetierId() {
        return metierId;
    }
    public void setMetierId(Long metierId) {
        this.metierId = metierId;
    }

    // New getter and setter for dateEntretienTelephonique
    public OffsetDateTime getDateEntretienTelephonique() {
        return dateEntretienTelephonique;
    }
    public void setDateEntretienTelephonique(OffsetDateTime dateEntretienTelephonique) {
        this.dateEntretienTelephonique = dateEntretienTelephonique;
    }

    public String getEvaluationTelephonique() {
        return evaluationTelephonique;
    }
    public void setEvaluationTelephonique(String evaluationTelephonique) {
        this.evaluationTelephonique = evaluationTelephonique;
    }

    public String getPremiereEvaluationPhysique() {
        return premiereEvaluationPhysique;
    }
    public void setPremiereEvaluationPhysique(String premiereEvaluationPhysique) {
        this.premiereEvaluationPhysique = premiereEvaluationPhysique;
    }

    public String getDeuxiemeEvaluationPhysique() {
        return deuxiemeEvaluationPhysique;
    }
    public void setDeuxiemeEvaluationPhysique(String deuxiemeEvaluationPhysique) {
        this.deuxiemeEvaluationPhysique = deuxiemeEvaluationPhysique;
    }

    public OffsetDateTime getDatePremierEntretienPhysique() {
        return datePremierEntretienPhysique;
    }
    public void setDatePremierEntretienPhysique(OffsetDateTime datePremierEntretienPhysique) {
        this.datePremierEntretienPhysique = datePremierEntretienPhysique;
    }

    public OffsetDateTime getDateDeuxiemeEntretienPhysique() {
        return dateDeuxiemeEntretienPhysique;
    }
    public void setDateDeuxiemeEntretienPhysique(OffsetDateTime dateDeuxiemeEntretienPhysique) {
        this.dateDeuxiemeEntretienPhysique = dateDeuxiemeEntretienPhysique;
    }

    public CandidatureStatus getStatus() {
        return status;
    }
    public void setStatus(CandidatureStatus status) {
        this.status = status;
    }
}