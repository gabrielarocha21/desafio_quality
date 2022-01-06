package br.com.meli.bootcamp.desafio_quality.DTO;

import br.com.meli.bootcamp.desafio_quality.entities.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RoomDTO {

    @NotEmpty(message = "O campo nao pode estar vazio")
    @NotNull(message = "O campo nao pode estar null")
    @Size(max = 30, message = "O comprimento do comodo nao pode exceder 30 caracteres")
    @Pattern(regexp = "^[A-Z].*$", message = "O nome do comodo deve começar com letra maiuscula")
    private String name;

    @NotNull(message = "O comprimento do comodo nao pode estar vazio")
    @DecimalMax(value = "33", message = "O comprimento maximo permitido por comodo e de 33 metros")
    private Double length;

    @NotNull(message = "A largura do comodo nao pode estar vazia")
    @DecimalMax(value = "25", message = "A largura maxima permitda e de por comodo e de 25 metros")
    private Double width;

    public static RoomDTO convertToDTO(RoomEntity roomEntity){
        return RoomDTO.builder()
                .name(roomEntity.getName())
                .length(roomEntity.getLength())
                .width(roomEntity.getWidth())
                .build();
    }
}
