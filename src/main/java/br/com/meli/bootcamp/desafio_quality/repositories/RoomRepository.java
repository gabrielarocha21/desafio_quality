package br.com.meli.bootcamp.desafio_quality.repositories;

import br.com.meli.bootcamp.desafio_quality.entities.RoomEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomRepository {
    private List<RoomEntity> roomEntityList = new ArrayList<>();
}
