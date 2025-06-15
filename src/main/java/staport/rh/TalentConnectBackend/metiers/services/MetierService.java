package staport.rh.TalentConnectBackend.metiers.services;


import staport.rh.TalentConnectBackend.metiers.dtos.MetierCriteria;
import staport.rh.TalentConnectBackend.metiers.dtos.MetierRequest;
import staport.rh.TalentConnectBackend.metiers.dtos.MetierResponse;
import staport.rh.TalentConnectBackend.shared.dtos.PageResponse;

public interface MetierService {
    MetierResponse createMetier(MetierRequest metierRequest);
    MetierResponse getMetierById(Long id);
    MetierResponse updateMetier(Long id, MetierRequest metierRequest);
    void deleteMetier(Long id);
    PageResponse<MetierResponse> searchMetiers(MetierCriteria criteria);
}