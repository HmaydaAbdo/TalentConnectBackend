package staport.rh.TalentConnectBackend.metiers.dtos;
import jakarta.validation.constraints.NotBlank;

public class MetierRequest {

    @NotBlank(message = "Metier name cannot be empty")
    private String metierName;

    private String description;

    // Constructors
    public MetierRequest() {
    }

    public MetierRequest(String metierName, String description) {
        this.metierName = metierName;
        this.description = description;
    }

    // Getters and Setters
    public String getMetierName() {
        return metierName;
    }

    public void setMetierName(String metierName) {
        this.metierName = metierName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}