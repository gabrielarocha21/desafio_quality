package br.com.meli.bootcamp.desafio_quality.repositories;

import br.com.meli.bootcamp.desafio_quality.entities.DistrictEntity;
import br.com.meli.bootcamp.desafio_quality.exceptions.DistrictNotFoundException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Repository
public class DistrictRepository {
    private List<DistrictEntity> districtEntityList;

    public DistrictRepository() {
        this.districtEntityList = Arrays.asList(
                new DistrictEntity("cacupe", new BigDecimal(9502)),
                new DistrictEntity("parque bristol", new BigDecimal(5000)),
                new DistrictEntity("nacoes", new BigDecimal(4040)),
                new DistrictEntity("alphaville", new BigDecimal(7805))
        );
    }

    public DistrictEntity findByName(String name) throws RuntimeException{
        return districtEntityList.stream()
                .filter(d -> d.getDistrict().equals(name)).findFirst().orElseThrow(DistrictNotFoundException::new);
    }

}
