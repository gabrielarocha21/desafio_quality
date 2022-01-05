package br.com.meli.bootcamp.desafio_quality.entities;

import br.com.meli.bootcamp.desafio_quality.DTO.DistrictDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DistrictEntity {
    private String district;
    private BigDecimal valueDistrictM2;

    public DistrictEntity convertToEntity(DistrictDTO districtDTO) {
        return DistrictEntity.builder()
                .district(districtDTO.getDistrict())
                .build();
    }
}
