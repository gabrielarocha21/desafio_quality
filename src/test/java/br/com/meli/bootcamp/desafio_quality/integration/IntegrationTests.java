package br.com.meli.bootcamp.desafio_quality.integration;

import br.com.meli.bootcamp.desafio_quality.DTO.HouseDTO;
import br.com.meli.bootcamp.desafio_quality.DTO.RoomDTO;
import br.com.meli.bootcamp.desafio_quality.entities.DistrictEntity;
import br.com.meli.bootcamp.desafio_quality.entities.HouseEntity;
import br.com.meli.bootcamp.desafio_quality.entities.RoomEntity;
import br.com.meli.bootcamp.desafio_quality.exceptions.DistrictNotFoundException;
import br.com.meli.bootcamp.desafio_quality.repositories.DistrictRepository;
import br.com.meli.bootcamp.desafio_quality.repositories.HouseRepository;
import br.com.meli.bootcamp.desafio_quality.service.DistrictService;
import br.com.meli.bootcamp.desafio_quality.service.HouseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public  void testPostRegisterHouseShouldThrowDistrictNotFoundException() throws Exception {
        HouseDTO payloadDTO = HouseDTO.builder()
                .name("Casa 1")
                .roomsList(
                        Arrays.asList(
                                RoomDTO.builder().name("Sala").length(2.0).width(2.0).build(),
                                RoomDTO.builder().name("Quarto").length(2.0).width(2.0).build(),
                                RoomDTO.builder().name("Cozinha").length(2.0).width(2.0).build()))
                .district("Ipiranga")
                .build();

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer();

        String payloadJson = writer.writeValueAsString(payloadDTO);

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
            .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        Assertions.assertEquals(DistrictNotFoundException.class, Objects.requireNonNull(response.getResolvedException()).getClass());
    }


    @Test
    public  void testPostRegisterHouseShouldReturnHouseDTO() throws Exception {

        HouseDTO payloadDTO = HouseDTO.builder()
                .name("Casa 1")
                .roomsList(
                        Arrays.asList(
                                RoomDTO.builder().name("Sala").length(2.0).width(2.0).build(),
                                RoomDTO.builder().name("Quarto").length(2.0).width(2.0).build(),
                                RoomDTO.builder().name("Cozinha").length(2.0).width(2.0).build()))
                .district("cacupe")
                .build();

        HouseDTO responseDTO = HouseDTO.builder()
                .name("Casa 1")
                .roomsList(
                        Arrays.asList(
                                RoomDTO.builder().name("Sala").length(2.0).width(2.0).build(),
                                RoomDTO.builder().name("Quarto").length(2.0).width(2.0).build(),
                                RoomDTO.builder().name("Cozinha").length(2.0).width(2.0).build()))
                .district("cacupe")
                .build();

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer();

        String payloadJson = writer.writeValueAsString(payloadDTO);
        String responseJson = writer.writeValueAsString(responseDTO);

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        Assertions.assertEquals(responseJson, response.getResponse().getContentAsString());
    }


    @Test
    public void houseControllerGetAreaOutPut() throws Exception {

        HouseDTO payloadDTO = HouseDTO.builder()
                .name("Casa 1")
                .roomsList(
                        Arrays.asList(
                                RoomDTO.builder().name("Sala").length(2.0).width(2.0).build(),
                                RoomDTO.builder().name("Quarto").length(2.0).width(2.0).build(),
                                RoomDTO.builder().name("Cozinha").length(2.0).width(2.0).build()))
                .district("cacupe")
                .build();

        HouseDTO responseDTO = HouseDTO.builder()
                .name("Casa 1")
                .roomsList(
                        Arrays.asList(
                                RoomDTO.builder().name("Sala").length(2.0).width(2.0).build(),
                                RoomDTO.builder().name("Quarto").length(2.0).width(2.0).build(),
                                RoomDTO.builder().name("Cozinha").length(2.0).width(2.0).build()))
                .district("cacupe")
                .build();

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer();

        String payloadJson = writer.writeValueAsString(payloadDTO);
        String responseJson = writer.writeValueAsString(responseDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/area/{prop_name}","Casa 1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
            .andReturn();

        Assertions.assertEquals(12.0, Double.valueOf(result.getResponse().getContentAsString()));
    }
}
