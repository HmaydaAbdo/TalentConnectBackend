package staport.rh.TalentConnectBackend.candidatures.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public class FirstPhysicalInterviewRequest {
    private String premiereEvaluationPhysique;
    @NotNull(message = "Date of second physical interview is required")
    private OffsetDateTime dateDeuxiemeEntretienPhysique;

    // Constructors
    public FirstPhysicalInterviewRequest() {}

    public FirstPhysicalInterviewRequest(String premiereEvaluationPhysique, OffsetDateTime dateDeuxiemeEntretienPhysique) {
        this.premiereEvaluationPhysique = premiereEvaluationPhysique;
        this.dateDeuxiemeEntretienPhysique = dateDeuxiemeEntretienPhysique;
    }

    // Getters and Setters
    public String getPremiereEvaluationPhysique() {
        return premiereEvaluationPhysique;
    }

    public void setPremiereEvaluationPhysique(String premiereEvaluationPhysique) {
        this.premiereEvaluationPhysique = premiereEvaluationPhysique;
    }

    public OffsetDateTime getDateDeuxiemeEntretienPhysique() {
        return dateDeuxiemeEntretienPhysique;
    }

    public void setDateDeuxiemeEntretienPhysique(OffsetDateTime dateDeuxiemeEntretienPhysique) {
        this.dateDeuxiemeEntretienPhysique = dateDeuxiemeEntretienPhysique;
    }
}
