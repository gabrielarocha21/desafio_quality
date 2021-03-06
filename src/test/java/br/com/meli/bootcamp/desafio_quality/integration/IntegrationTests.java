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

    @Test void testGetBigestRoom() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.payload))
                .andExpect(status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/biggest_room/{prop_name}", "Casa 1"))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("Quarto")))
                .andDo(print());
    }

    @Test
    public void testGetValue() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.payload))
                .andExpect(status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/value/{prop_name}", "Casa 1")
        ).andExpect(response -> Assertions.assertTrue(response.getResponse().getContentAsString().contains("171036")));
    }

    @Test
    public void testGetAreaPerRoom() throws Exception {


        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.payload))
                .andExpect(status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/area_per_room/{prop_name}","Casa 1"))
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
    public  void testPostRegisterHouseShouldThrowMethodArgumentNotValidExceptionAndMessageErrorInTheFieldNameWithoutUpperCase() throws Exception {

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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
                    Assertions.assertTrue(result.getResponse().getContentAsString().contains("O nome da propriedade deve comecar com uma letra maiuscula"));
                })
                .andReturn();
    }

    @Test
    public  void testPostRegisterHouseShouldThrowMethodArgumentNotValidExceptionAndMessageErrorInTheFieldNameEmpty() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"\",\n" +
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


        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
                    Assertions.assertTrue(result.getResponse().getContentAsString().contains("O nome da propriedade nao pode estar vazio"));
                })
                .andReturn();
    }

    @Test
    public  void testPostRegisterHouseShouldThrowMethodArgumentNotValidExceptionAndMessageErrorInTheFieldNameMaxSize() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"Mais de 30 caracteres - Mais de 30 caracteres - Mais de 30 caracteres - Mais de 30 caracteres - Mais de 30 caracteres - Mais de 30 caracteres\",\n" +
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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
                    Assertions.assertTrue(result.getResponse().getContentAsString().contains("O comprimento do nome nao pode exceder 30 caracteres"));
                })
                .andReturn();
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

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.payload))
                .andExpect(status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/area/{prop_name}","Casa 1"))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertEquals(18.0, Double.valueOf(result.getResponse().getContentAsString())))
            .andReturn();
    }



    @Test
    public  void shouldThrowMethodArgumentNotValidExceptionAndMessageErrorInTheFieldNameOfRoomListWithoutUpperCase() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"Casa 1\",\n" +
                "    \"district\": \"cacupe\",\n" +
                "    \"roomsList\": [\n" +
                "        {\n" +
                "            \"name\": \"sala\",\n" +
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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
                    Assertions.assertTrue(result.getResponse().getContentAsString().contains("O nome do comodo deve comecar com letra maiuscula"));
                })
                .andReturn();
    }

    @Test
    public  void shouldThrowMethodArgumentNotValidExceptionAndMessageErrorInTheFieldNameOfRoomListEmpty() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"Casa 1\",\n" +
                "    \"district\": \"cacupe\",\n" +
                "    \"roomsList\": [\n" +
                "        {\n" +
                "            \"name\": \"\",\n" +
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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
                    Assertions.assertTrue(result.getResponse().getContentAsString().contains("O campo nao pode estar vazio"));
                })
                .andReturn();
    }

    @Test
    public  void shouldThrowMethodArgumentNotValidExceptionAndMessageErrorInTheFieldNameOfRoomListNull() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"Casa 1\",\n" +
                "    \"district\": \"cacupe\",\n" +
                "    \"roomsList\": [\n" +
                "        {\n" +
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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
                    Assertions.assertTrue(result.getResponse().getContentAsString().contains("O campo nao pode estar null"));
                })
                .andReturn();
    }

    @Test
    public  void shouldThrowMethodArgumentNotValidExceptionAndMessageErrorInTheFieldNameOfRoomListMaxSize() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"Casa 1\",\n" +
                "    \"district\": \"cacupe\",\n" +
                "    \"roomsList\": [\n" +
                "        {\n" +
                "            \"name\": \"Mais de 30 caracteres - Mais de 30 caracteres - Mais de 30 caracteres - Mais de 30 caracteres - Mais de 30 caracteres\",\n" +
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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
                    Assertions.assertTrue(result.getResponse().getContentAsString().contains("O comprimento do nome do comodo nao pode exceder 30 caracteres"));
                })
                .andReturn();
    }

    @Test
    public  void shouldThrowMethodArgumentNotValidExceptionAndMessageErrorInTheFieldLengthOfRoomListNull() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"Casa 1\",\n" +
                "    \"district\": \"cacupe\",\n" +
                "    \"roomsList\": [\n" +
                "        {\n" +
                "            \"name\": \"Sala\",\n" +
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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
                    Assertions.assertTrue(result.getResponse().getContentAsString().contains("O comprimento do comodo nao pode estar vazio"));
                })
                .andReturn();
    }

    @Test
    public  void shouldThrowMethodArgumentNotValidExceptionAndMessageErrorInTheFieldLengthOfRoomListMaxDecimal() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"Casa 1\",\n" +
                "    \"district\": \"cacupe\",\n" +
                "    \"roomsList\": [\n" +
                "        {\n" +
                "            \"name\": \"Sala\",\n" +
                "            \"length\": 34.0,\n" +
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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
                    Assertions.assertTrue(result.getResponse().getContentAsString().contains("O comprimento maximo permitido por comodo e de 33 metros"));
                })
                .andReturn();
    }

    @Test
    public  void shouldThrowMethodArgumentNotValidExceptionAndMessageErrorInTheFieldLengthOfRoomListMinDecimal() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"Casa 1\",\n" +
                "    \"district\": \"cacupe\",\n" +
                "    \"roomsList\": [\n" +
                "        {\n" +
                "            \"name\": \"Sala\",\n" +
                "            \"length\": 0.0,\n" +
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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
                    Assertions.assertTrue(result.getResponse().getContentAsString().contains("O comprimento minimo maior que 0"));
                })
                .andReturn();
    }

    @Test
    public  void shouldThrowMethodArgumentNotValidExceptionAndMessageErrorInTheFieldWidthOfRoomListNull() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"Casa 1\",\n" +
                "    \"district\": \"cacupe\",\n" +
                "    \"roomsList\": [\n" +
                "        {\n" +
                "            \"name\": \"Sala\",\n" +
                "            \"length\": 2.0\n" +
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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
                    Assertions.assertTrue(result.getResponse().getContentAsString().contains("A largura do comodo nao pode estar vazia"));
                })
                .andReturn();
    }

    @Test
    public  void shouldThrowMethodArgumentNotValidExceptionAndMessageErrorInTheFieldWidthOfRoomListMaxDecimal() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"Casa 1\",\n" +
                "    \"district\": \"cacupe\",\n" +
                "    \"roomsList\": [\n" +
                "        {\n" +
                "            \"name\": \"Sala\",\n" +
                "            \"length\": 2.0,\n" +
                "            \"width\": 26.0\n" +
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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
                    Assertions.assertTrue(result.getResponse().getContentAsString().contains("A largura maxima permitda e de por comodo e de 25 metros"));
                })
                .andReturn();
    }

    @Test
    public  void shouldThrowMethodArgumentNotValidExceptionAndMessageErrorInTheFieldWidthOfRoomListMinDecimal() throws Exception {

        String payloadJson = "{\n" +
                "    \"name\": \"Casa 1\",\n" +
                "    \"district\": \"cacupe\",\n" +
                "    \"roomsList\": [\n" +
                "        {\n" +
                "            \"name\": \"Sala\",\n" +
                "            \"length\": 2.0,\n" +
                "            \"width\": 0.0\n" +
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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> {
                    Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
                    Assertions.assertTrue(result.getResponse().getContentAsString().contains("O largura minimo maior que 0"));
                })
                .andReturn();
    }
}
