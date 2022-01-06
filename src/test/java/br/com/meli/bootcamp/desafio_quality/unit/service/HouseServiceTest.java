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
    public void getArea(){


        this.roomEntityList =  Arrays.asList(
                new RoomEntity("Sala", 2.0,2.0)
                , new RoomEntity("Quarto", 2.0, 2.0)
                , new RoomEntity("Cozinha", 2.0, 2.0));

        this.houseEntity =
                new HouseEntity("Casa 1", this.roomEntityList,
                        new DistrictEntity("Ipiranga",new BigDecimal(10)));



        when(houseRepository.findByName(any())).thenReturn(houseEntity);
        when(roomService.getArea(any())).thenReturn(4.0);
        Double area = this.houseService.getArea(any());
        assertEquals(12, area);

    }

    @Test
    public void getBiggestRoom(){

        this.roomEntityList =  Arrays.asList(
                new RoomEntity("Sala", 3.0,2.0)
                , new RoomEntity("Quarto", 2.0, 2.0)
                , new RoomEntity("Cozinha", 2.0, 2.0));

        this.houseEntity =
                new HouseEntity("Casa 1",
                        this.roomEntityList,
                        new DistrictEntity("Ipiranga",new BigDecimal(10)));


        when(houseRepository.findByName(any())).thenReturn(houseEntity);
        when(roomService.getArea(any())).thenCallRealMethod();

        RoomEntity roomEntity = this.houseService.getBiggestRoom(any());
        assertEquals("Sala", roomEntity.getName());

    }

    @Test
    public void getValue(){

        this.roomEntityList =  Arrays.asList(
                new RoomEntity("Sala", 2.0,2.0)
                , new RoomEntity("Quarto", 2.0, 2.0)
                , new RoomEntity("Cozinha", 2.0, 2.0));

        this.houseEntity =
                new HouseEntity("Casa 1",
                        this.roomEntityList,
                        new DistrictEntity("Ipiranga",new BigDecimal(100)));


        when(houseRepository.findByName(any())).thenReturn(houseEntity);
        when(roomService.getArea(any())).thenCallRealMethod();

        BigDecimal total = this.houseService.getValue(any());

        assertEquals(new BigDecimal(1200),total);

    }

    @Test
    public void getAreaPerRoom (){

        Double area;

        this.roomEntityList =  Arrays.asList(
                new RoomEntity("Sala", 3.0,2.0)
                , new RoomEntity("Quarto", 4.0, 2.0)
                , new RoomEntity("Cozinha", 2.0, 2.0));

        this.houseEntity =
                new HouseEntity("Casa 1",
                        this.roomEntityList,
                        new DistrictEntity("Ipiranga",new BigDecimal(100)));


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
    public void getHouse(){

        this.roomEntityList =  Arrays.asList(
                new RoomEntity("Sala", 3.0,2.0)
                , new RoomEntity("Quarto", 4.0, 2.0)
                , new RoomEntity("Cozinha", 2.0, 2.0));

        this.houseEntity =
                new HouseEntity("Casa 1",
                        this.roomEntityList,
                        new DistrictEntity("Ipiranga",new BigDecimal(100)));


        when(houseRepository.findByName(any())).thenReturn(houseEntity);

        HouseEntity houseEntity = this.houseService.getHouse(any());

        assertEquals("Casa 1", houseEntity.getName());
    }

    @Test
    public void save(){

        this.roomEntityList =  Arrays.asList(
                new RoomEntity("Sala", 3.0,2.0)
                , new RoomEntity("Quarto", 4.0, 2.0)
                , new RoomEntity("Cozinha", 2.0, 2.0));

        DistrictEntity districtEntity = new  DistrictEntity("Ipiranga",new BigDecimal(100));

        this.houseEntity =
                new HouseEntity("Casa 1",
                        this.roomEntityList,
                        districtEntity);

        when(this.districtService.findDistrict(any())).thenReturn(districtEntity);
        when(this.houseRepository.save(any())).thenReturn(this.houseEntity);


        HouseEntity h = this.houseService.save(this.houseEntity);

        assertEquals("Casa 1", h.getName());


    }

    @Test
    public void erroSave(){

        this.roomEntityList =  Arrays.asList(
                new RoomEntity("Sala", 3.0,2.0)
                , new RoomEntity("Quarto", 4.0, 2.0)
                , new RoomEntity("Cozinha", 2.0, 2.0));

        DistrictEntity districtEntity = new  DistrictEntity("Ipi", new BigDecimal(10));

        this.houseEntity =
                new HouseEntity("Casa 1",
                        this.roomEntityList,
                        districtEntity);

        List<RoomEntity> roomEntityList1 =  Arrays.asList(
                new RoomEntity("Sala", 3.0,2.0)
                , new RoomEntity("Quarto", 4.0, 2.0)
                , new RoomEntity("Cozinha", 2.0, 2.0));

        DistrictEntity districtEntity1 = new  DistrictEntity("APA", new BigDecimal(10));

        HouseEntity houseEntity1 =
                new HouseEntity("Casa 1",
                        this.roomEntityList,
                        districtEntity1);

        this.houseRepository.setHouseEntityList(Arrays.asList(houseEntity));

        when(houseRepository.findByName(any())).thenReturn(houseEntity);
        when(this.districtService.findDistrict(any())).thenReturn( new DistrictEntity( ));


        this.houseEntity.getDistrict().setDistrict("APA");

        assertThrows(DistrictNotFoundException.class, () -> {
            this.houseService.save(houseEntity1);
        });
    }

}
