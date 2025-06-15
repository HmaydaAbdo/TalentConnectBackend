package staport.rh.TalentConnectBackend.metiers.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import staport.rh.TalentConnectBackend.metiers.dtos.MetierRequest;
import staport.rh.TalentConnectBackend.metiers.dtos.MetierResponse;
import staport.rh.TalentConnectBackend.metiers.entities.Metier;

import java.util.List;

@Mapper(componentModel = "spring") // componentModel = "spring" makes it a Spring bean
public interface MetierMapper {

    MetierMapper INSTANCE = Mappers.getMapper(MetierMapper.class); // For non-Spring context if needed

    Metier toEntity(MetierRequest request);

    MetierResponse toDto(Metier metier);

    @Mapping(target = "id", ignore = true) // Ignore ID when updating
    void updateEntityFromRequest(MetierRequest request, @MappingTarget Metier metier);

    // Optional: For mapping a list of entities to a list of DTOs
    List<MetierResponse> toDtoList(List<Metier> metiers);
}