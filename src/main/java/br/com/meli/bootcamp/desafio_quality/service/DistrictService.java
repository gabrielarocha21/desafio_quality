package br.com.meli.bootcamp.desafio_quality.service;

import br.com.meli.bootcamp.desafio_quality.DTO.DistrictDTO;
import br.com.meli.bootcamp.desafio_quality.entities.DistrictEntity;
import br.com.meli.bootcamp.desafio_quality.exceptions.DistrictNotFoundException;
import br.com.meli.bootcamp.desafio_quality.repositories.DistrictRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DistrictService {

    private DistrictRepository districtRepository;

    public DistrictService(){
        this.districtRepository = new DistrictRepository();
    }

    public  DistrictEntity convertToEntity(String districtDTO) {
        return this.findDistrict(districtDTO);

    }

    public DistrictEntity findDistrict(String districtName) throws DistrictNotFoundException {
        return districtRepository.findByName(districtName);
    }

}
