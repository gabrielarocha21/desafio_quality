package br.com.meli.bootcamp.desafio_quality.unit.service;


import br.com.meli.bootcamp.desafio_quality.entities.RoomEntity;

import br.com.meli.bootcamp.desafio_quality.service.RoomService;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class RoomServiceTest {

    private RoomService roomService = new RoomService();

    @Test
    public void shouldReturnTheArea() {
        RoomEntity roomEntity = new RoomEntity("name", 3.0, 2.0);
        Double area = this.roomService.getArea(roomEntity);
        assertEquals(6.0, area);
    }
}
