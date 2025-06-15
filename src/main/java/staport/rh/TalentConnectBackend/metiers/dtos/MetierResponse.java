package staport.rh.TalentConnectBackend.metiers.dtos;


public class MetierResponse {
    private Long id;
    private String metierName;
    private String description;

    // Constructors
    public MetierResponse() {
    }

    public MetierResponse(Long id, String metierName, String description) {
        this.id = id;
        this.metierName = metierName;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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