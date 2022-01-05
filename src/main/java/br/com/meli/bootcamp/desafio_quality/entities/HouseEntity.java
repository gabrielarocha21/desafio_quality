package br.com.meli.bootcamp.desafio_quality.entities;

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

}
