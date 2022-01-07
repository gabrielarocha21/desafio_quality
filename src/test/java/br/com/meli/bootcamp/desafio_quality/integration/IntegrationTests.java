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
import org.springframework.web.bind.MethodArgumentNotValidException;
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

        String payloadJson = "{\n" +
                "    \"name\": \"Casa 1\",\n" +
                "    \"district\": \"ipiranga\",\n" +
                "    \"roomsList\": [\n" +
                "        {\n" +
                "            \"name\": \"Sala\",\n" +
                "            \"length\": 2.0,\n" +
                "            \"width\": 2.0\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Quarto\",\n" +
                "            \"length\": 2.0,\n" +
                "            \"width\": 2.0\n" +
                "        },\n" +
                "          {\n" +
                "            \"name\": \"Cozinha\",\n" +
                "            \"length\": 2.0,\n" +
                "            \"width\": 2.0\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andExpect(status().isNotFound())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof DistrictNotFoundException))
                .andReturn();
    }

    @Test
    public  void testPostRegisterHouseShouldThrowMethodArgumentNotValidException() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"casa 1\",\n" +
                "    \"district\": \"cacupe\",\n" +
                "    \"roomsList\": [\n" +
                "        {\n" +
                "            \"name\": \"Sala\",\n" +
                "            \"length\": 2.0,\n" +
                "            \"width\": 2.0\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Quarto\",\n" +
                "            \"length\": 2.0,\n" +
                "            \"width\": 2.0\n" +
                "        },\n" +
                "          {\n" +
                "            \"name\": \"Cozinha\",\n" +
                "            \"length\": 2.0,\n" +
                "            \"width\": 2.0\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andReturn();
        System.out.println(" ============= PRINT : " + response.getResponse());
        System.out.println(" ============= PRINT : " + response.getResponse().getContentAsString());
        System.out.println(" ============= PRINT : " + MockMvcResultMatchers.jsonPath("$.message").value("Erro de validação na requisição."));

    }

    @Test
    public  void testPostRegisterHouseShouldReturnHouseDTO() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"Casa 1\",\n" +
                "    \"district\": \"cacupe\",\n" +
                "    \"roomsList\": [\n" +
                "        {\n" +
                "            \"name\": \"Sala\",\n" +
                "            \"length\": 2.0,\n" +
                "            \"width\": 2.0\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Quarto\",\n" +
                "            \"length\": 2.0,\n" +
                "            \"width\": 2.0\n" +
                "        },\n" +
                "          {\n" +
                "            \"name\": \"Cozinha\",\n" +
                "            \"length\": 2.0,\n" +
                "            \"width\": 2.0\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andExpect(status().isCreated());
    }


    @Test
    public void houseControllerGetAreaOutPut() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"Casa 1\",\n" +
                "    \"district\": \"cacupe\",\n" +
                "    \"roomsList\": [\n" +
                "        {\n" +
                "            \"name\": \"Sala\",\n" +
                "            \"length\": 2.0,\n" +
                "            \"width\": 2.0\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Quarto\",\n" +
                "            \"length\": 2.0,\n" +
                "            \"width\": 2.0\n" +
                "        },\n" +
                "          {\n" +
                "            \"name\": \"Cozinha\",\n" +
                "            \"length\": 2.0,\n" +
                "            \"width\": 2.0\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andExpect(status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/area/{prop_name}","Casa 1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> Assertions.assertEquals(12.0, Double.valueOf(result.getResponse().getContentAsString())))
            .andReturn();
    }
}
