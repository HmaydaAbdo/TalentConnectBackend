package staport.rh.TalentConnectBackend.metiers.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "metiers")
public class Metier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "metier_name", nullable = false, unique = true, length = 100)
    private String metierName; // e.g., "Software Development", "Human Resources", "Marketing"

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // Optional detailed description of the metier

    // Constructors
    public Metier() {
    }

    public Metier(String metierName, String description) {
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