package br.com.meli.bootcamp.desafio_quality.DTO;

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
    @Size(max = 30, message = "O comprimento do comodo nao pode exceder 30 caracteres")
    @Pattern(regexp = "([A-Z]+)", message = "O nome do comodo deve começar com letra maiuscula")
    private String name;

    @DecimalMax(value = "33", message = "O comprimento maximo permitido por comodo e de 33 metros")
    @NotEmpty(message = "O comprimento do comodo nao pode estar vazio")
    private Double length;

    @DecimalMax(value = "25", message = "A largura maxima permitda e de por comodo e de 25 metros")
    @NotEmpty(message = "A largura do comodo nao pode estar vazia")
    private Double width;
}
