package br.com.meli.bootcamp.desafio_quality.entities;

import br.com.meli.bootcamp.desafio_quality.DTO.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RoomEntity {

    private String name;
    private Double length;
    private Double width;

    public static RoomEntity convertToEntity(RoomDTO roomDTO){
        return RoomEntity.builder()
                .name(roomDTO.getName())
                .length(roomDTO.getLength())
                .width(roomDTO.getWidth())
                .build();
    }

}
