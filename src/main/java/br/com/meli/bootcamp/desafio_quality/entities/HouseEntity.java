package br.com.meli.bootcamp.desafio_quality.entities;

import br.com.meli.bootcamp.desafio_quality.DTO.HouseDTO;
import br.com.meli.bootcamp.desafio_quality.DTO.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HouseEntity {

    private String name;
    private List<RoomEntity> roomsList;
    private DistrictEntity district;


    public static HouseEntity convertToEntity(HouseDTO houseDTO) {
        List<RoomEntity> roomEntityList = houseDTO.getRoomsList().stream().map(RoomEntity::convertToEntity).collect(Collectors.toList());

        return HouseEntity.builder()
                .name(houseDTO.getName())
                .roomsList(roomEntityList)
                .build();
    }

}
