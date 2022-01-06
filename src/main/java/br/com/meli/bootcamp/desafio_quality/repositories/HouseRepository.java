package br.com.meli.bootcamp.desafio_quality.repositories;

import br.com.meli.bootcamp.desafio_quality.entities.HouseEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.DecimalMin;
import java.util.ArrayList;
import java.util.List;
@Data
@Repository
public class HouseRepository {



    private List<HouseEntity> houseEntityList = new ArrayList<>();

    public HouseEntity findByName(String name){
        return this.houseEntityList.stream()
                .filter(h -> h.getName().equals(name)).findFirst().orElseThrow(RuntimeException::new);

    }

    public HouseEntity save(HouseEntity houseEntity) {
        this.houseEntityList.add(houseEntity);
        return houseEntity;
    }



}
