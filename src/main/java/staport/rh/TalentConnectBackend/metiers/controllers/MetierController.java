package staport.rh.TalentConnectBackend.metiers.controllers;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import staport.rh.TalentConnectBackend.metiers.dtos.MetierCriteria;
import staport.rh.TalentConnectBackend.metiers.dtos.MetierRequest;
import staport.rh.TalentConnectBackend.metiers.dtos.MetierResponse;
import staport.rh.TalentConnectBackend.metiers.services.MetierService;
import staport.rh.TalentConnectBackend.shared.dtos.PageResponse;

@RestController
@RequestMapping("/metiers")
public class MetierController {

    private final MetierService metierService;

    public MetierController(MetierService metierService) {
        this.metierService = metierService;
    }


    @PostMapping
    public ResponseEntity<MetierResponse> createMetier(@Valid @RequestBody MetierRequest metierRequest) {
        try {
            MetierResponse response = metierService.createMetier(metierRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict for existing name
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetierResponse> getMetierById(@PathVariable Long id) {
        try {
            MetierResponse response = metierService.getMetierById(id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetierResponse> updateMetier(@PathVariable Long id, @Valid @RequestBody MetierRequest metierRequest) {
        try {
            MetierResponse response = metierService.updateMetier(id, metierRequest);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict for existing name
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetier(@PathVariable Long id) {
        try {
            metierService.deleteMetier(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .notFound()
                    .build(); // 404 Not Found
        }
    }



    @GetMapping
    public ResponseEntity<PageResponse<MetierResponse>> searchMetiers(
            @RequestParam(required = false) String metierName,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "metierName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {


        Sort.Direction directionEnum;
        try {
            directionEnum = Sort.Direction.fromString(sortDirection.toUpperCase()); // Convert to uppercase for parsing
        } catch (IllegalArgumentException e) {
            directionEnum = Sort.Direction.ASC;
        }

        MetierCriteria criteria = new MetierCriteria(metierName, description, page, size, sortBy, directionEnum); // Pass the enum
        PageResponse<MetierResponse> response = metierService.searchMetiers(criteria);
        return ResponseEntity.ok(response);
    }
}