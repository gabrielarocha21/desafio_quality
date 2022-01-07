package br.com.meli.bootcamp.desafio_quality.unit.service;

import br.com.meli.bootcamp.desafio_quality.entities.DistrictEntity;
import br.com.meli.bootcamp.desafio_quality.entities.HouseEntity;
import br.com.meli.bootcamp.desafio_quality.entities.RoomEntity;
import br.com.meli.bootcamp.desafio_quality.exceptions.DistrictNotFoundException;
import br.com.meli.bootcamp.desafio_quality.repositories.DistrictRepository;
import br.com.meli.bootcamp.desafio_quality.repositories.HouseRepository;
import br.com.meli.bootcamp.desafio_quality.service.DistrictService;
import br.com.meli.bootcamp.desafio_quality.service.HouseService;
import br.com.meli.bootcamp.desafio_quality.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class HouseServiceTest {


    @Mock
    private HouseRepository houseRepository;

    @InjectMocks
    private HouseService houseService;

    @Mock
    private RoomService roomService;

    @Mock
    private DistrictService districtService;

    @Mock
    DistrictRepository districtRepository;



    private HouseEntity houseEntity = null;
    private List<RoomEntity> roomEntityList;

    @BeforeEach
     void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnTheArea(){

        this.roomEntityList = Arrays.asList(
                RoomEntity.builder().name("Sala").length(2.0).width(2.0).build(),
                RoomEntity.builder().name("Quarto").length(2.0).width(2.0).build(),
                RoomEntity.builder().name("Cozinha").length(2.0).width(2.0).build());

        this.houseEntity = HouseEntity.builder()
                .name("Casa 1")
                .roomsList(this.roomEntityList)
                .district(
                        DistrictEntity.builder()
                                .district("Ipiranga")
                                .valueDistrictM2(new BigDecimal(10.00))
                                .build())
                .build();

        when(houseRepository.findByName(any())).thenReturn(houseEntity);
        when(roomService.getArea(any())).thenReturn(4.0);
        Double area = this.houseService.getArea(any());
        assertEquals(12, area);
    }

    @Test
    public void shouldReturnTheLivingRoomAsTheBiggestRoom(){

        this.roomEntityList = Arrays.asList(
                RoomEntity.builder().name("Sala").length(3.0).width(2.0).build(),
                RoomEntity.builder().name("Quarto").length(2.0).width(2.0).build(),
                RoomEntity.builder().name("Cozinha").length(2.0).width(2.0).build());

        this.houseEntity = HouseEntity.builder()
                .name("Casa 1")
                .roomsList(this.roomEntityList)
                .district(
                        DistrictEntity.builder()
                                .district("Ipiranga")
                                .valueDistrictM2(new BigDecimal(10.00))
                                .build())
                .build();

        when(houseRepository.findByName(any())).thenReturn(houseEntity);
        when(roomService.getArea(any())).thenCallRealMethod();

        RoomEntity roomEntity = this.houseService.getBiggestRoom(any());
        assertEquals("Sala", roomEntity.getName());
    }

    @Test
    public void shouldReturnTheKitchenAsTheBiggestRoom(){
        this.roomEntityList = Arrays.asList(
                RoomEntity.builder().name("Sala").length(2.0).width(2.0).build(),
                RoomEntity.builder().name("Quarto").length(3.0).width(2.0).build(),
                RoomEntity.builder().name("Cozinha").length(5.0).width(2.0).build());

        this.houseEntity = HouseEntity.builder()
                .name("Casa 1")
                .roomsList(this.roomEntityList)
                .district(
                        DistrictEntity.builder()
                                .district("Ipiranga")
                                .valueDistrictM2(new BigDecimal(10.00))
                                .build())
                .build();

        when(houseRepository.findByName(any())).thenReturn(houseEntity);
        when(roomService.getArea(any())).thenCallRealMethod();

        RoomEntity roomEntity = this.houseService.getBiggestRoom(any());
        assertEquals("Cozinha", roomEntity.getName());
    }

    @Test
    public void shouldReturnTheValue(){

        this.roomEntityList = Arrays.asList(
                RoomEntity.builder().name("Sala").length(2.0).width(2.0).build(),
                RoomEntity.builder().name("Quarto").length(2.0).width(2.0).build(),
                RoomEntity.builder().name("Cozinha").length(2.0).width(2.0).build());

        this.houseEntity = HouseEntity.builder()
                .name("Casa 1")
                .roomsList(this.roomEntityList)
                .district(
                        DistrictEntity.builder()
                                .district("Ipiranga")
                                .valueDistrictM2(new BigDecimal(100.00))
                                .build())
                .build();

        when(houseRepository.findByName(any())).thenReturn(houseEntity);
        when(roomService.getArea(any())).thenCallRealMethod();

        BigDecimal total = this.houseService.getValue(any());

        assertEquals(new BigDecimal(1200),total);

    }

    @Test
    public void shouldReturnTheAreaPerRoom (){

        Double area;

        this.roomEntityList = Arrays.asList(
                RoomEntity.builder().name("Sala").length(3.0).width(2.0).build(),
                RoomEntity.builder().name("Quarto").length(4.0).width(2.0).build(),
                RoomEntity.builder().name("Cozinha").length(2.0).width(2.0).build());

        this.houseEntity = HouseEntity.builder()
                .name("Casa 1")
                .roomsList(this.roomEntityList)
                .district(
                        DistrictEntity.builder()
                                .district("Ipiranga")
                                .valueDistrictM2(new BigDecimal(100.00))
                                .build())
                .build();

        when(houseRepository.findByName(any())).thenReturn(houseEntity);
        when(roomService.getArea(any())).thenCallRealMethod();

        Map<RoomEntity,Double> map = this.houseService.getAreaPerRoom(any());

         area = map.entrySet().stream().filter(r -> r.getKey().getName().equals("Sala")).findFirst().get().getValue();

        assertEquals(6.0, area);

        area = map.entrySet().stream().filter(r -> r.getKey().getName().equals("Quarto")).findFirst().get().getValue();

        assertEquals(8.0, area);

        area = map.entrySet().stream().filter(r -> r.getKey().getName().equals("Cozinha")).findFirst().get().getValue();

        assertEquals(4.0, area);

    }

    @Test
    public void shouldReturnAHouse(){

        this.roomEntityList = Arrays.asList(
                RoomEntity.builder().name("Sala").length(3.0).width(2.0).build(),
                RoomEntity.builder().name("Quarto").length(4.0).width(2.0).build(),
                RoomEntity.builder().name("Cozinha").length(2.0).width(2.0).build());

        this.houseEntity = HouseEntity.builder()
                .name("Casa 1")
                .roomsList(this.roomEntityList)
                .district(
                        DistrictEntity.builder()
                                .district("Ipiranga")
                                .valueDistrictM2(new BigDecimal(100.00))
                                .build())
                .build();

        when(houseRepository.findByName(any())).thenReturn(houseEntity);

        HouseEntity houseEntity = this.houseService.getHouse(any());

        assertEquals("Casa 1", houseEntity.getName());
    }

    @Test
    public void shouldSaveAHouse(){

        this.roomEntityList = Arrays.asList(
                RoomEntity.builder().name("Sala").length(3.0).width(2.0).build(),
                RoomEntity.builder().name("Quarto").length(4.0).width(2.0).build(),
                RoomEntity.builder().name("Cozinha").length(2.0).width(2.0).build());

        DistrictEntity districtEntity = DistrictEntity.builder()
                .district("Ipiranga")
                .valueDistrictM2(new BigDecimal(100.00))
                .build();

        this.houseEntity = HouseEntity.builder()
                .name("Casa 1")
                .roomsList(this.roomEntityList)
                .district(districtEntity)
                .build();

        when(this.districtService.findDistrict(any())).thenReturn(districtEntity);
        when(this.houseRepository.save(any())).thenReturn(this.houseEntity);

        HouseEntity h = this.houseService.save(this.houseEntity);

        assertEquals("Casa 1", h.getName());
    }

    @Test
    public void shouldNotSaveAHouseAndReturnADistrictNotFoundException(){

        this.roomEntityList = Arrays.asList(
                RoomEntity.builder().name("Sala").length(3.0).width(2.0).build(),
                RoomEntity.builder().name("Quarto").length(4.0).width(2.0).build(),
                RoomEntity.builder().name("Cozinha").length(2.0).width(2.0).build());

        DistrictEntity districtEntity = DistrictEntity.builder()
                .district("ipi")
                .valueDistrictM2(new BigDecimal(10.00))
                .build();

        this.houseEntity = HouseEntity.builder()
                .name("Casa 1")
                .roomsList(this.roomEntityList)
                .district(districtEntity)
                .build();

        when(this.districtService.findDistrict(any())).thenThrow(DistrictNotFoundException.class);

        assertThrows(DistrictNotFoundException.class, () -> {
            this.houseService.save(houseEntity);
        });
    }

    @Test
    public void shouldCreateANewStanceOfHouseService(){
        HouseService houseService = new HouseService();
        assertNotNull(houseService);
    }

    @Test
    public void shouldCreateANewStanceOfHouseServicePassingTheRepository(){
        HouseRepository mock = Mockito.mock(HouseRepository.class);
        HouseService houseService = new HouseService(mock);
        assertNotNull(houseService);
    }

}
