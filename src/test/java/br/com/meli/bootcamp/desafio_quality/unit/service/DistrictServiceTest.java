package br.com.meli.bootcamp.desafio_quality.unit.service;

import br.com.meli.bootcamp.desafio_quality.entities.DistrictEntity;
import br.com.meli.bootcamp.desafio_quality.exceptions.DistrictNotFoundException;
import br.com.meli.bootcamp.desafio_quality.repositories.DistrictRepository;
import br.com.meli.bootcamp.desafio_quality.service.DistrictService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class DistrictServiceTest {


    @Mock
    private DistrictRepository districtRepository;

    public DistrictEntity districtEntity;



    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        this.districtEntity = new DistrictEntity("Ipiranga", new BigDecimal(10000));
    }

    @Test
    public void findDistrictByName(){

        when(districtRepository.findByName(any())).thenReturn(districtEntity);
        assertNotNull(this.districtEntity.getDistrict());

    }

    @Test
    void shouldSearchDistrictByNameAndReturnADistrictNotFoundException(){
        String district = "ipiranga";
        DistrictRepository mock = Mockito.mock(DistrictRepository.class);

        DistrictService districtService = new DistrictService(mock);
        when(mock.findByName(any())).thenThrow(DistrictNotFoundException.class);
        DistrictNotFoundException exceptionExpected = Assertions.assertThrows(
                DistrictNotFoundException.class,
                ()->{ districtService.findDistrict(district); }
        );
        Assertions.assertEquals(DistrictNotFoundException.class, exceptionExpected.getClass());
    }

    @Test
    public void shouldCreateANewStanceOfDistrictService() {
        DistrictService districtService = new DistrictService();
        assertNotNull(districtService);
    }

    @Test
    public void shouldReturnADistrictEntity() {
        DistrictEntity districtEntity = new DistrictEntity("Jacana", new BigDecimal(3000));

        DistrictRepository mock = Mockito.mock(DistrictRepository.class);

        DistrictService districtService = new DistrictService(mock);

        when(districtService.findDistrict(any())).thenReturn(districtEntity);

        DistrictEntity districtEntity1 = districtService.convertToEntity(" ");

        assertEquals(districtEntity.getDistrict(), districtEntity1.getDistrict());
    }



}
