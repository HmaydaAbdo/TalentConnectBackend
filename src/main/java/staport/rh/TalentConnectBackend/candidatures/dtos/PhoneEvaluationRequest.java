package staport.rh.TalentConnectBackend.candidatures.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class PhoneEvaluationRequest {

    // New field to capture the date of the phone interview
    @NotNull(message = "Date de l'entretien téléphonique est requise")
    private OffsetDateTime dateEntretienTelephonique;

    private String evaluationTelephonique;

    // This now correctly reflects the transition to planning the first physical interview
    @NotNull(message = "La date du premier entretien physique est requise")
    private OffsetDateTime datePremierEntretienPhysique;

    // Constructors
    public PhoneEvaluationRequest() {
    }

    public PhoneEvaluationRequest(OffsetDateTime dateEntretienTelephonique, String evaluationTelephonique, OffsetDateTime datePremierEntretienPhysique) {
        this.dateEntretienTelephonique = dateEntretienTelephonique;
        this.evaluationTelephonique = evaluationTelephonique;
        this.datePremierEntretienPhysique = datePremierEntretienPhysique;
    }

    // Getters and Setters
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

    public OffsetDateTime getDatePremierEntretienPhysique() {
        return datePremierEntretienPhysique;
    }

    public void setDatePremierEntretienPhysique(OffsetDateTime datePremierEntretienPhysique) {
        this.datePremierEntretienPhysique = datePremierEntretienPhysique;
    }
}