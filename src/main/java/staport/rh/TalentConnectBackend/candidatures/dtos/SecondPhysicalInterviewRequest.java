package staport.rh.TalentConnectBackend.candidatures.dtos;

import jakarta.validation.constraints.NotNull;
import staport.rh.TalentConnectBackend.candidatures.enums.CandidatureStatus;

public class SecondPhysicalInterviewRequest {
    private String deuxiemeEvaluationPhysique;
    @NotNull(message = "Final status (HIRED or REJECTED) is required")
    private CandidatureStatus finalStatus;

    // Constructors
    public SecondPhysicalInterviewRequest() {}

    public SecondPhysicalInterviewRequest(String deuxiemeEvaluationPhysique, CandidatureStatus finalStatus) {
        this.deuxiemeEvaluationPhysique = deuxiemeEvaluationPhysique;
        this.finalStatus = finalStatus;
    }

    // Getters and Setters
    public String getDeuxiemeEvaluationPhysique() {
        return deuxiemeEvaluationPhysique;
    }

    public void setDeuxiemeEvaluationPhysique(String deuxiemeEvaluationPhysique) {
        this.deuxiemeEvaluationPhysique = deuxiemeEvaluationPhysique;
    }

    public CandidatureStatus getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(CandidatureStatus finalStatus) {
        this.finalStatus = finalStatus;
    }
}
