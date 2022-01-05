package br.com.meli.bootcamp.desafio_quality.entities;

import br.com.meli.bootcamp.desafio_quality.DTO.HouseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HouseEntity {

    private String name;
    private List<RoomEntity> roomsList;
    private DistrictEntity district;


    public static HouseEntity convertToEntity(HouseDTO houseDTO) {
        return HouseEntity.builder()
                .name(houseDTO.getName())
                .roomsList(houseDTO.getRoomsList())
                .build();
    }

}
