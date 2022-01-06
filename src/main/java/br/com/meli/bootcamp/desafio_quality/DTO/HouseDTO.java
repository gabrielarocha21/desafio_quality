package br.com.meli.bootcamp.desafio_quality.DTO;

import br.com.meli.bootcamp.desafio_quality.entities.HouseEntity;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseDTO {

    @NotEmpty(message = "O nome da propriedade nao pode estar vazio")
    @Size(max = 30, message = "O comprimento do nome nao pode exceder 30 caracteres")
    @Pattern(regexp = "^[A-Z].*$", message = "O nome da propriedade deve começar com uma letra maiuscula")
    private String name;

    @NotEmpty(message = "O bairro nao pode estar vazio")
    @Size(max = 30, message = "O comprimento do bairro nao pode exceder 45 caracteres")
    private String district;

    @Valid
    private List<RoomDTO> roomsList = new ArrayList<>();

    public static HouseDTO convertToDTO(HouseEntity houseEntity) {
        List<RoomDTO> roomDTOList = houseEntity.getRoomsList().stream().map(RoomDTO::convertToDTO).collect(Collectors.toList());

        return HouseDTO.builder()
                .name(houseEntity.getName())
                .district(houseEntity.getDistrict().getDistrict())
                .roomsList(roomDTOList)
                .build();
    }
}
