package br.com.meli.bootcamp.desafio_quality.DTO;

import br.com.meli.bootcamp.desafio_quality.entities.DistrictEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDTO {

    private String district;

    public DistrictDTO convertToDTO(DistrictEntity districtEntity) {
        return DistrictDTO.builder()
                .district(districtEntity.getDistrict())
                .build();
    }
}
