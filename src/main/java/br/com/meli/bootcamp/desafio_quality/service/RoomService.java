package br.com.meli.bootcamp.desafio_quality.service;

import br.com.meli.bootcamp.desafio_quality.entities.RoomEntity;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    public Double getArea(RoomEntity roomEntity){
        return roomEntity.getLength() * roomEntity.getWidth();
    }
}
