package br.com.meli.bootcamp.desafio_quality.controller;

import br.com.meli.bootcamp.desafio_quality.DTO.HouseDTO;
import br.com.meli.bootcamp.desafio_quality.entities.HouseEntity;
import br.com.meli.bootcamp.desafio_quality.service.DistrictService;
import br.com.meli.bootcamp.desafio_quality.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/")
public class HouseController {

    @Autowired
    HouseService houseService;

    @Autowired
    DistrictService districtService;

    @GetMapping("/area/{prop_name}")
    public ResponseEntity<?> getArea(@PathVariable String prop_name) {
        return ResponseEntity.ok(houseService.getArea(prop_name));

    }

    @GetMapping("/biggest_room/{prop_name}")
    public ResponseEntity<?> getBiggestRoom(@PathVariable String prop_name){
        return ResponseEntity.ok(houseService.getBiggestRoom(prop_name));
    }

    @GetMapping("/value/{prop_name}")
    public ResponseEntity<?> getValue( @PathVariable String prop_name){
        return ResponseEntity.ok(houseService.getValue(prop_name));
    }

    @GetMapping("/area_per_room/{prop_name}")
    public ResponseEntity<?> getAreaPerRoom(@PathVariable String prop_name){
        return ResponseEntity.ok(houseService.getAreaPerRoom(prop_name));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerHouse(@Valid @RequestBody HouseDTO houseDto, UriComponentsBuilder uriBuilder) {
        HouseEntity houseEntity = HouseEntity.convertToEntity(houseDto);
        houseEntity.setDistrict(this.districtService.convertToEntity(houseDto.getDistrict()));

            houseService.save(houseEntity);
            return ResponseEntity.created(
                    uriBuilder
                            .path("/register")
                            .buildAndExpand("register")
                            .toUri()
            ).body(HouseDTO.convertToDTO(houseEntity));
    }
}
