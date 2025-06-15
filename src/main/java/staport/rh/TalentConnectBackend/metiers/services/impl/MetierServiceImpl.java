package staport.rh.TalentConnectBackend.metiers.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import staport.rh.TalentConnectBackend.metiers.dtos.MetierCriteria;
import staport.rh.TalentConnectBackend.metiers.dtos.MetierRequest;
import staport.rh.TalentConnectBackend.metiers.dtos.MetierResponse;
import staport.rh.TalentConnectBackend.metiers.entities.Metier;
import staport.rh.TalentConnectBackend.metiers.mappers.MetierMapper;
import staport.rh.TalentConnectBackend.metiers.repositories.MetierRepository;
import staport.rh.TalentConnectBackend.metiers.services.MetierService;
import staport.rh.TalentConnectBackend.metiers.specification.MetierSpecification;
import staport.rh.TalentConnectBackend.shared.dtos.PageResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional // Good practice for service layer
public class MetierServiceImpl implements MetierService {

    private final MetierRepository metierRepository;
    private final MetierMapper metierMapper;

    public MetierServiceImpl(MetierRepository metierRepository, MetierMapper metierMapper) {
        this.metierRepository = metierRepository;
        this.metierMapper = metierMapper;
    }

    @Override
    public MetierResponse createMetier(MetierRequest metierRequest) {
        // Check for uniqueness before creating
        if (metierRepository.existsByMetierNameIgnoreCase(metierRequest.getMetierName())) {
            throw new IllegalArgumentException("Metier with name '" + metierRequest.getMetierName() + "' already exists.");
        }
        Metier metier = metierMapper.toEntity(metierRequest);
        Metier savedMetier = metierRepository.save(metier);
        return metierMapper.toDto(savedMetier);
    }

    @Override
    @Transactional(readOnly = true) // Read-only for read operations
    public MetierResponse getMetierById(Long id) {
        Metier metier = metierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Metier not found with id: " + id));
        return metierMapper.toDto(metier);
    }

    @Override
    public MetierResponse updateMetier(Long id, MetierRequest metierRequest) {
        Metier existingMetier = metierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Metier not found with id: " + id));

        // Check for uniqueness if name is changed (and not the same as the existing metier)
        if (!existingMetier.getMetierName().equalsIgnoreCase(metierRequest.getMetierName()) &&
                metierRepository.existsByMetierNameIgnoreCase(metierRequest.getMetierName())) {
            throw new IllegalArgumentException("Metier with name '" + metierRequest.getMetierName() + "' already exists.");
        }

        metierMapper.updateEntityFromRequest(metierRequest, existingMetier);
        Metier updatedMetier = metierRepository.save(existingMetier);
        return metierMapper.toDto(updatedMetier);
    }

    @Override
    public void deleteMetier(Long id) {
        if (!metierRepository.existsById(id)) {
            throw new EntityNotFoundException("Metier not found with id: " + id);
        }
        metierRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<MetierResponse> searchMetiers(MetierCriteria criteria) {
        // Create Sort object
        Sort sort = Sort.by(criteria.getSortDirection(), criteria.getSortBy());

        // Create Pageable object
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), sort);

        // Create Specification from criteria
        MetierSpecification spec = new MetierSpecification(criteria);

        // Execute search with pagination and sorting
        Page<Metier> metierPage = metierRepository.findAll(spec, pageable);

        // Map entities to DTOs
        List<MetierResponse> content = metierPage.getContent().stream()
                .map(metierMapper::toDto)
                .collect(Collectors.toList());

        // Build PageResponse
        return new PageResponse<>(
                content,
                metierPage.getNumber(),
                metierPage.getSize(),
                metierPage.getTotalElements(),
                metierPage.getTotalPages(),
                metierPage.isLast(),
                metierPage.isFirst()
        );
    }
}