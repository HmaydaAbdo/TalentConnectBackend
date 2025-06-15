package staport.rh.TalentConnectBackend.candidatures.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import staport.rh.TalentConnectBackend.candidatures.dtos.CandidatureRequest;
import staport.rh.TalentConnectBackend.candidatures.dtos.CandidatureResponse;
import staport.rh.TalentConnectBackend.candidatures.dtos.CandidatureCriteria;
import staport.rh.TalentConnectBackend.candidatures.dtos.PhoneEvaluationRequest;
import staport.rh.TalentConnectBackend.candidatures.dtos.FirstPhysicalInterviewRequest;
import staport.rh.TalentConnectBackend.candidatures.dtos.SecondPhysicalInterviewRequest;
import staport.rh.TalentConnectBackend.candidatures.enums.CandidatureStatus;
import staport.rh.TalentConnectBackend.candidatures.services.ICandidatureService;
import staport.rh.TalentConnectBackend.shared.dtos.PageResponse;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/candidatures")
public class CandidatureController {

    private final ICandidatureService candidatureService;

    public CandidatureController(ICandidatureService candidatureService) {
        this.candidatureService = candidatureService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CandidatureResponse> createCandidature(
            @RequestPart("candidature") @Valid CandidatureRequest request,
            @RequestPart(value = "cvFile", required = false) MultipartFile cvFile) {
        CandidatureResponse response = candidatureService.createCandidature(request, cvFile);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CandidatureResponse> updateCandidature(
            @PathVariable Long id,
            @RequestPart("candidature") @Valid CandidatureRequest request,
            @RequestPart(value = "cvFile", required = false) MultipartFile cvFile) {
        CandidatureResponse response = candidatureService.updateCandidature(id, request, cvFile);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidature(@PathVariable Long id) {
        candidatureService.deleteCandidature(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidatureResponse> getCandidatureById(@PathVariable Long id) {
        CandidatureResponse response = candidatureService.getCandidatureById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CandidatureResponse>> searchCandidatures(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) Long metierId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dateEntretienTelephonique, // New parameter
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        CandidatureCriteria criteria = new CandidatureCriteria();
        criteria.setFullName(fullName);
        criteria.setPhoneNumber(phoneNumber);
        criteria.setMetierId(metierId);
        if (status != null && !status.isEmpty()) {
            try {
                criteria.setStatus(CandidatureStatus.valueOf(status.toUpperCase()));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        criteria.setDateEntretienTelephonique(dateEntretienTelephonique); // Set the new parameter

        criteria.setPage(page);
        criteria.setSize(size);
        criteria.setSortBy(sortBy);
        criteria.setSortDirection(sortDirection);

        Page<CandidatureResponse> candidaturePage = candidatureService.searchCandidatures(criteria);
        PageResponse<CandidatureResponse> pageResponse = new PageResponse<>(
                candidaturePage.getContent(),
                candidaturePage.getNumber(),
                candidaturePage.getSize(),
                candidaturePage.getTotalElements(),
                candidaturePage.getTotalPages(),
                candidaturePage.isFirst(),
                candidaturePage.isLast()
        );
        return ResponseEntity.ok(pageResponse);
    }

    @PatchMapping(value = "/{id}/phone-evaluation", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> passPhoneEvaluation(
            @PathVariable Long id,
            @RequestPart("request") @Valid PhoneEvaluationRequest request,
            @RequestPart(value = "reportFile", required = false) MultipartFile reportFile) {
        candidatureService.passerEvaluationTelephonique(id, request, reportFile);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}/first-physical-interview", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> validateFirstPhysicalInterview(
            @PathVariable Long id,
            @RequestPart("request") @Valid FirstPhysicalInterviewRequest request,
            @RequestPart(value = "reportFile", required = false) MultipartFile reportFile) {
        candidatureService.validerPremierEntretienPhysique(id, request, reportFile);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}/second-physical-interview", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> validateSecondPhysicalInterview(
            @PathVariable Long id,
            @RequestPart("request") @Valid SecondPhysicalInterviewRequest request,
            @RequestPart(value = "reportFile", required = false) MultipartFile reportFile) {
        candidatureService.validerDeuxiemeEntretienPhysique(id, request, reportFile);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/cv")
    public ResponseEntity<byte[]> downloadCv(@PathVariable Long id) {
        byte[] cvData = candidatureService.downloadCv(id);
        String filename = candidatureService.getCvFilename(id);
        String contentType = candidatureService.getCvContentType(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", filename);
        return new ResponseEntity<>(cvData, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/cv")
    public ResponseEntity<Void> deleteCv(@PathVariable Long id) {
        candidatureService.deleteCv(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/reports/phone-evaluation")
    public ResponseEntity<byte[]> downloadRapportEvaluationTelephonique(@PathVariable Long id) {
        byte[] reportData = candidatureService.downloadRapportEvaluationTelephonique(id);
        String filename = candidatureService.getRapportEvaluationTelephoniqueFilename(id);
        String contentType = candidatureService.getRapportEvaluationTelephoniqueContentType(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", filename);
        return new ResponseEntity<>(reportData, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}/reports/first-physical-interview")
    public ResponseEntity<byte[]> downloadRapportPremiereEvaluationPhysique(@PathVariable Long id) {
        byte[] reportData = candidatureService.downloadRapportPremiereEvaluationPhysique(id);
        String filename = candidatureService.getRapportPremiereEvaluationPhysiqueFilename(id);
        String contentType = candidatureService.getRapportPremiereEvaluationPhysiqueContentType(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", filename);
        return new ResponseEntity<>(reportData, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}/reports/second-physical-interview")
    public ResponseEntity<byte[]> downloadRapportDeuxiemeEvaluationPhysique(@PathVariable Long id) {
        byte[] reportData = candidatureService.downloadRapportDeuxiemeEvaluationPhysique(id);
        String filename = candidatureService.getRapportDeuxiemeEvaluationPhysiqueFilename(id);
        String contentType = candidatureService.getRapportDeuxiemeEvaluationPhysiqueContentType(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", filename);
        return new ResponseEntity<>(reportData, headers, HttpStatus.OK);
    }


    @DeleteMapping("/{id}/reports/phone-evaluation")
    public ResponseEntity<Void> deleteRapportEvaluationTelephonique(@PathVariable Long id) {
        candidatureService.deleteRapportEvaluationTelephonique(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/reports/first-physical-interview")
    public ResponseEntity<Void> deleteRapportPremiereEvaluationPhysique(@PathVariable Long id) {
        candidatureService.deleteRapportPremiereEvaluationPhysique(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/reports/second-physical-interview")
    public ResponseEntity<Void> deleteRapportDeuxiemeEvaluationPhysique(@PathVariable Long id) {
        candidatureService.deleteRapportDeuxiemeEvaluationPhysique(id);
        return ResponseEntity.noContent().build();
    }
}