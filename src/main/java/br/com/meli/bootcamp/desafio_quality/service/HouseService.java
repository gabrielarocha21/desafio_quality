package br.com.meli.bootcamp.desafio_quality.service;

import br.com.meli.bootcamp.desafio_quality.entities.HouseEntity;
import br.com.meli.bootcamp.desafio_quality.entities.RoomEntity;
import br.com.meli.bootcamp.desafio_quality.repositories.HouseRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class HouseService {
    private HouseRepository houseRepository;

    public HouseService(){
        this.houseRepository = new HouseRepository();
    }

    public HouseEntity getHouse(String propName){
        return null;
    }

    public Double getArea(String propName){
        return null;
    }

    public RoomEntity getBiggestRoom(String propName){
        return null;
    }

    public BigDecimal getValue(String propName){
        return null;
    }

    public List<RoomEntity> getAreaPerRoom(String propName){
        return null;
    }
}
