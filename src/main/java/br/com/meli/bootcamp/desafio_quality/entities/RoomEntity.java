package br.com.meli.bootcamp.desafio_quality.entities;

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
}
