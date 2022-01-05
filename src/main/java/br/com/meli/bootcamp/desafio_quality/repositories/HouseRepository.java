package br.com.meli.bootcamp.desafio_quality.repositories;

import br.com.meli.bootcamp.desafio_quality.entities.DistrictEntity;
import br.com.meli.bootcamp.desafio_quality.entities.HouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HouseRepository {

    @Autowired
    DistrictRepository districtRepository;

    private List<HouseEntity> houseEntityList = new ArrayList<>();

    public HouseEntity findByName(String name){
        return houseEntityList.stream()
                .filter(h -> h.getName().equals(name)).findFirst().orElseThrow(RuntimeException::new);

    }

    public HouseEntity save(HouseEntity houseEntity) {
        this.houseEntityList.add(houseEntity);
        return houseEntity;
    }



}
