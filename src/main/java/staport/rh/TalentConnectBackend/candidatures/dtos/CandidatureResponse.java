package staport.rh.TalentConnectBackend.candidatures.dtos;

import staport.rh.TalentConnectBackend.candidatures.enums.CandidatureStatus;

import java.time.OffsetDateTime;

public class CandidatureResponse {

    private Long id;
    private String fullName;
    private String phoneNumber;
    private Long metierId;
    private String metierName;
    private OffsetDateTime dateEntretienTelephonique; // Added field
    private String evaluationTelephonique;
    private OffsetDateTime datePremierEntretienPhysique;
    private String premiereEvaluationPhysique;
    private OffsetDateTime dateDeuxiemeEntretienPhysique;
    private String deuxiemeEvaluationPhysique;
    private CandidatureStatus status;

    // fields to indicate presence of reports and CV
    private boolean hasCv;
    private boolean hasRapportEvaluationTelephonique;
    private boolean hasRapportPremiereEvaluationPhysique;
    private boolean hasRapportDeuxiemeEvaluationPhysique;

    // Constructors
    public CandidatureResponse() {
    }

    public CandidatureResponse(Long id, String fullName, String phoneNumber, Long metierId, String metierName,
                               OffsetDateTime dateEntretienTelephonique, // Added to constructor
                               String evaluationTelephonique, OffsetDateTime datePremierEntretienPhysique,
                               String premiereEvaluationPhysique, OffsetDateTime dateDeuxiemeEntretienPhysique,
                               String deuxiemeEvaluationPhysique, CandidatureStatus status,
                               boolean hasCv, boolean hasRapportEvaluationTelephonique,
                               boolean hasRapportPremiereEvaluationPhysique, boolean hasRapportDeuxiemeEvaluationPhysique) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.metierId = metierId;
        this.metierName = metierName;
        this.dateEntretienTelephonique = dateEntretienTelephonique; // Initialized in constructor
        this.evaluationTelephonique = evaluationTelephonique;
        this.datePremierEntretienPhysique = datePremierEntretienPhysique;
        this.premiereEvaluationPhysique = premiereEvaluationPhysique;
        this.dateDeuxiemeEntretienPhysique = dateDeuxiemeEntretienPhysique;
        this.deuxiemeEvaluationPhysique = deuxiemeEvaluationPhysique;
        this.status = status;
        this.hasCv = hasCv;
        this.hasRapportEvaluationTelephonique = hasRapportEvaluationTelephonique;
        this.hasRapportPremiereEvaluationPhysique = hasRapportPremiereEvaluationPhysique;
        this.hasRapportDeuxiemeEvaluationPhysique = hasRapportDeuxiemeEvaluationPhysique;
    }

    // Getters and Setters (existing ones + new ones for hasX properties and dates)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getMetierName() {
        return metierName;
    }

    public void setMetierName(String metierName) {
        this.metierName = metierName;
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

    public boolean isHasCv() {
        return hasCv;
    }

    public void setHasCv(boolean hasCv) {
        this.hasCv = hasCv;
    }

    public boolean isHasRapportEvaluationTelephonique() {
        return hasRapportEvaluationTelephonique;
    }

    public void setHasRapportEvaluationTelephonique(boolean hasRapportEvaluationTelephonique) {
        this.hasRapportEvaluationTelephonique = hasRapportEvaluationTelephonique;
    }

    public boolean isHasRapportPremiereEvaluationPhysique() {
        return hasRapportPremiereEvaluationPhysique;
    }

    public void setHasRapportPremiereEvaluationPhysique(boolean hasRapportPremiereEvaluationPhysique) {
        this.hasRapportPremiereEvaluationPhysique = hasRapportPremiereEvaluationPhysique;
    }

    public boolean isHasRapportDeuxiemeEvaluationPhysique() {
        return hasRapportDeuxiemeEvaluationPhysique;
    }

    public void setHasRapportDeuxiemeEvaluationPhysique(boolean hasRapportDeuxiemeEvaluationPhysique) {
        this.hasRapportDeuxiemeEvaluationPhysique = hasRapportDeuxiemeEvaluationPhysique;
    }
}