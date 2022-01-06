package br.com.meli.bootcamp.desafio_quality.unit.service;

import br.com.meli.bootcamp.desafio_quality.entities.DistrictEntity;
import br.com.meli.bootcamp.desafio_quality.repositories.DistrictRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class DistrictServiceTeste {


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



}
