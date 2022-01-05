package br.com.meli.bootcamp.desafio_quality.DTO;

import br.com.meli.bootcamp.desafio_quality.entities.DistrictEntity;
import br.com.meli.bootcamp.desafio_quality.entities.HouseEntity;
import br.com.meli.bootcamp.desafio_quality.entities.RoomEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder

public class HouseDTO {

    private String name;
    private String district;
    private List<RoomEntity> roomsList;

    public static HouseDTO convertToDTO(HouseEntity houseEntity) {
        return HouseDTO.builder()
                .name(houseEntity.getName())
                .district(houseEntity.getDistrict().getDistrict())
                .roomsList(houseEntity.getRoomsList())
                .build();
    }
}
