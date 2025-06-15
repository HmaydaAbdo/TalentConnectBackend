package staport.rh.TalentConnectBackend.metiers.dtos;

import org.springframework.data.domain.Sort;

public class MetierCriteria {
    private String metierName; // Search by name (partial match)
    private String description; // Search by description (partial match)

    // Pagination
    private int page = 0;
    private int size = 10;

    // Sorting (e.g., "metierName,asc" or "id,desc")
    private String sortBy = "metierName"; // Default sort field
    private Sort.Direction sortDirection = Sort.Direction.ASC; // Default sort direction

    // Constructors
    public MetierCriteria() {
    }

    public MetierCriteria(String metierName, String description, int page, int size, String sortBy, Sort.Direction sortDirection) {
        this.metierName = metierName;
        this.description = description;
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }
}