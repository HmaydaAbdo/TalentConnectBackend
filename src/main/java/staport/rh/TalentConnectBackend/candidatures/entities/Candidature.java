package staport.rh.TalentConnectBackend.candidatures.entities;

import jakarta.persistence.*;
import staport.rh.TalentConnectBackend.candidatures.enums.CandidatureStatus;
import staport.rh.TalentConnectBackend.metiers.entities.Metier;

import java.time.OffsetDateTime;

@Entity
@Table(name = "candidatures")
public class Candidature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metier_id", nullable = false)
    private Metier metier;

    // --- NEW FIELD ADDED HERE ---
    @Column(name = "date_entretien_telephonique")
    private OffsetDateTime dateEntretienTelephonique;
    // ----------------------------

    @Column(name = "evaluation_telephonique", columnDefinition = "TEXT")
    private String evaluationTelephonique;

    @Column(name = "date_premier_entretien_physique")
    private OffsetDateTime datePremierEntretienPhysique;

    @Lob
    @Column(name = "rapport_evaluation_telephonique_data", columnDefinition = "LONGBLOB")
    private byte[] rapportEvaluationTelephoniqueData;
    // --- NEW FIELDS FOR PHONE EVALUATION REPORT METADATA ---
    @Column(name = "rapport_evaluation_telephonique_filename", length = 255)
    private String rapportEvaluationTelephoniqueFilename;
    @Column(name = "rapport_evaluation_telephonique_content_type", length = 100)
    private String rapportEvaluationTelephoniqueContentType;
    // --------------------------------------------------------

    @Column(name = "premiere_evaluation_physique", columnDefinition = "TEXT")
    private String premiereEvaluationPhysique;

    @Column(name = "date_deuxieme_entretien_physique")
    private OffsetDateTime dateDeuxiemeEntretienPhysique;

    @Lob
    @Column(name = "rapport_premiere_evaluation_physique_data", columnDefinition = "LONGBLOB")
    private byte[] rapportPremiereEvaluationPhysiqueData;
    // --- NEW FIELDS FOR FIRST PHYSICAL INTERVIEW REPORT METADATA ---
    @Column(name = "rapport_premiere_evaluation_physique_filename", length = 255)
    private String rapportPremiereEvaluationPhysiqueFilename;
    @Column(name = "rapport_premiere_evaluation_physique_content_type", length = 100)
    private String rapportPremiereEvaluationPhysiqueContentType;
    // -----------------------------------------------------------------

    @Column(name = "deuxieme_evaluation_physique", columnDefinition = "TEXT")
    private String deuxiemeEvaluationPhysique;

    @Lob
    @Column(name = "rapport_deuxieme_evaluation_physique_data", columnDefinition = "LONGBLOB")
    private byte[] rapportDeuxiemeEvaluationPhysiqueData;
    // --- NEW FIELDS FOR SECOND PHYSICAL INTERVIEW REPORT METADATA ---
    @Column(name = "rapport_deuxieme_evaluation_physique_filename", length = 255)
    private String rapportDeuxiemeEvaluationPhysiqueFilename;
    @Column(name = "rapport_deuxieme_evaluation_physique_content_type", length = 100)
    private String rapportDeuxiemeEvaluationPhysiqueContentType;
    // -----------------------------------------------------------------

    @Lob
    @Column(name = "cv_data", columnDefinition = "LONGBLOB")
    private byte[] cvData;

    @Column(name = "cv_filename", length = 255)
    private String cvFilename;

    @Column(name = "cv_content_type", length = 100)
    private String cvContentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CandidatureStatus status;

    // Constructors
    public Candidature() {
        this.status = CandidatureStatus.ENTRETIEN_TELEPHONIQUE_PLANIFIE;
    }

    public Candidature(String fullName, String phoneNumber, Metier metier, byte[] cvData) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.metier = metier;
        this.cvData = cvData;
        this.status = CandidatureStatus.ENTRETIEN_TELEPHONIQUE_PLANIFIE;
    }

    // Getters and Setters for existing fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public Metier getMetier() { return metier; }
    public void setMetier(Metier metier) { this.metier = metier; }
    public String getEvaluationTelephonique() { return evaluationTelephonique; }
    public void setEvaluationTelephonique(String evaluationTelephonique) { this.evaluationTelephonique = evaluationTelephonique; }
    public OffsetDateTime getDatePremierEntretienPhysique() { return datePremierEntretienPhysique; }
    public void setDatePremierEntretienPhysique(OffsetDateTime datePremierEntretienPhysique) { this.datePremierEntretienPhysique = datePremierEntretienPhysique; }
    public byte[] getRapportEvaluationTelephoniqueData() { return rapportEvaluationTelephoniqueData; }
    public void setRapportEvaluationTelephoniqueData(byte[] rapportEvaluationTelephoniqueData) { this.rapportEvaluationTelephoniqueData = rapportEvaluationTelephoniqueData; }
    public String getPremiereEvaluationPhysique() { return premiereEvaluationPhysique; }
    public void setPremiereEvaluationPhysique(String premiereEvaluationPhysique) { this.premiereEvaluationPhysique = premiereEvaluationPhysique; }
    public OffsetDateTime getDateDeuxiemeEntretienPhysique() { return dateDeuxiemeEntretienPhysique; }
    public void setDateDeuxiemeEntretienPhysique(OffsetDateTime dateDeuxiemeEntretienPhysique) { this.dateDeuxiemeEntretienPhysique = dateDeuxiemeEntretienPhysique; }
    public byte[] getRapportPremiereEvaluationPhysiqueData() { return rapportPremiereEvaluationPhysiqueData; }
    public void setRapportPremiereEvaluationPhysiqueData(byte[] rapportPremiereEvaluationPhysiqueData) { this.rapportPremiereEvaluationPhysiqueData = rapportPremiereEvaluationPhysiqueData; }
    public String getDeuxiemeEvaluationPhysique() { return deuxiemeEvaluationPhysique; }
    public void setDeuxiemeEvaluationPhysique(String deuxiemeEvaluationPhysique) { this.deuxiemeEvaluationPhysique = deuxiemeEvaluationPhysique; }
    public byte[] getRapportDeuxiemeEvaluationPhysiqueData() { return rapportDeuxiemeEvaluationPhysiqueData; }
    public void setRapportDeuxiemeEvaluationPhysiqueData(byte[] rapportDeuxiemeEvaluationPhysiqueData) { this.rapportDeuxiemeEvaluationPhysiqueData = rapportDeuxiemeEvaluationPhysiqueData; }
    public byte[] getCvData() { return cvData; }
    public void setCvData(byte[] cvData) { this.cvData = cvData; }
    public String getCvFilename() { return cvFilename; }
    public void setCvFilename(String cvFilename) { this.cvFilename = cvFilename; }
    public String getCvContentType() { return cvContentType; }
    public void setCvContentType(String cvContentType) { this.cvContentType = cvContentType; }
    public CandidatureStatus getStatus() { return status; }
    public void setStatus(CandidatureStatus status) { this.status = status; }

    // NEW Getters and Setters for Report Metadata
    public String getRapportEvaluationTelephoniqueFilename() {
        return rapportEvaluationTelephoniqueFilename;
    }

    public void setRapportEvaluationTelephoniqueFilename(String rapportEvaluationTelephoniqueFilename) {
        this.rapportEvaluationTelephoniqueFilename = rapportEvaluationTelephoniqueFilename;
    }

    public String getRapportEvaluationTelephoniqueContentType() {
        return rapportEvaluationTelephoniqueContentType;
    }

    public void setRapportEvaluationTelephoniqueContentType(String rapportEvaluationTelephoniqueContentType) {
        this.rapportEvaluationTelephoniqueContentType = rapportEvaluationTelephoniqueContentType;
    }

    public String getRapportPremiereEvaluationPhysiqueFilename() {
        return rapportPremiereEvaluationPhysiqueFilename;
    }

    public void setRapportPremiereEvaluationPhysiqueFilename(String rapportPremiereEvaluationPhysiqueFilename) {
        this.rapportPremiereEvaluationPhysiqueFilename = rapportPremiereEvaluationPhysiqueFilename;
    }

    public String getRapportPremiereEvaluationPhysiqueContentType() {
        return rapportPremiereEvaluationPhysiqueContentType;
    }

    public void setRapportPremiereEvaluationPhysiqueContentType(String rapportPremiereEvaluationPhysiqueContentType) {
        this.rapportPremiereEvaluationPhysiqueContentType = rapportPremiereEvaluationPhysiqueContentType;
    }

    public String getRapportDeuxiemeEvaluationPhysiqueFilename() {
        return rapportDeuxiemeEvaluationPhysiqueFilename;
    }

    public void setRapportDeuxiemeEvaluationPhysiqueFilename(String rapportDeuxiemeEvaluationPhysiqueFilename) {
        this.rapportDeuxiemeEvaluationPhysiqueFilename = rapportDeuxiemeEvaluationPhysiqueFilename;
    }

    public String getRapportDeuxiemeEvaluationPhysiqueContentType() {
        return rapportDeuxiemeEvaluationPhysiqueContentType;
    }

    public void setRapportDeuxiemeEvaluationPhysiqueContentType(String rapportDeuxiemeEvaluationPhysiqueContentType) {
        this.rapportDeuxiemeEvaluationPhysiqueContentType = rapportDeuxiemeEvaluationPhysiqueContentType;
    }

    // --- NEW GETTER AND SETTER FOR dateEntretienTelephonique ---
    public OffsetDateTime getDateEntretienTelephonique() {
        return dateEntretienTelephonique;
    }

    public void setDateEntretienTelephonique(OffsetDateTime dateEntretienTelephonique) {
        this.dateEntretienTelephonique = dateEntretienTelephonique;
    }
    // -----------------------------------------------------------

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = CandidatureStatus.ENTRETIEN_TELEPHONIQUE_PLANIFIE;
        }
    }
}