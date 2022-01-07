package br.com.meli.bootcamp.desafio_quality.integration;

import br.com.meli.bootcamp.desafio_quality.DTO.HouseDTO;
import br.com.meli.bootcamp.desafio_quality.DTO.RoomDTO;
import br.com.meli.bootcamp.desafio_quality.exceptions.DistrictNotFoundException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;

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
    public void testGetAreaPerRoom() throws Exception {

        Gson gson = new Gson();

        List<RoomDTO> roomDTOList = Arrays.asList(
                RoomDTO.builder().name("Sala").length(3.0).width(2.0).build(),
                RoomDTO.builder().name("Quarto").length(4.0).width(2.0).build(),
                RoomDTO.builder().name("Cozinha").length(2.0).width(2.0).build());

        HouseDTO houseDto = HouseDTO.builder()
                .name("Casa 1")
                .roomsList(roomDTOList)
                .district("cacupe")
                .build();

        String payload = gson.toJson(houseDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/area_per_room/Casa 1"))
            .andExpect(status().isOk())
            .andDo(print());
         /*   .andExpect(result -> {

                Double area;

                String response = result.getResponse().getContentAsString();

                Map<RoomEntity,Double> map =
                        gson.fromJson(response, Map.class);

                System.out.println("----------> " + map);

                area = map.entrySet().stream()
                        .filter(r -> r.getKey().getName().equals("Sala")).findFirst().get().getValue();

                assertEquals(6.0, area);

                area = map.entrySet().stream()
                        .filter(r -> r.getKey().getName().equals("Quarto")).findFirst().get().getValue();

                assertEquals(8.0, area);

                area = map.entrySet().stream()
                        .filter(r -> r.getKey().getName().equals("Cozinha")).findFirst().get().getValue();

                assertEquals(4.0, area);

            }); */
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
