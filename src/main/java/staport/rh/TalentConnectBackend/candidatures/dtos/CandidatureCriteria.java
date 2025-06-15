package staport.rh.TalentConnectBackend.candidatures.dtos;

import staport.rh.TalentConnectBackend.candidatures.enums.CandidatureStatus;

import java.time.OffsetDateTime; // Import for OffsetDateTime

public class CandidatureCriteria {
    private String fullName;
    private String phoneNumber;
    private Long metierId;
    private CandidatureStatus status;

    // Added for filtering by phone interview date
    private OffsetDateTime dateEntretienTelephonique;

    // Pagination and Sorting fields (already present in your MetierCriteria example)
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortDirection; // "asc" or "desc"

    // Constructors
    public CandidatureCriteria() {
        this.page = 0;
        this.size = 10;
        this.sortBy = "id"; // Default sort by ID
        this.sortDirection = "asc";
    }

    // Getters and Setters
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

    public CandidatureStatus getStatus() {
        return status;
    }

    public void setStatus(CandidatureStatus status) {
        this.status = status;
    }

    // New getters and setters for dateEntretienTelephonique
    public OffsetDateTime getDateEntretienTelephonique() {
        return dateEntretienTelephonique;
    }

    public void setDateEntretienTelephonique(OffsetDateTime dateEntretienTelephonique) {
        this.dateEntretienTelephonique = dateEntretienTelephonique;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}