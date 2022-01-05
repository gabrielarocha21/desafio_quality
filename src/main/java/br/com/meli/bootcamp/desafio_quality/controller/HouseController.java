package br.com.meli.bootcamp.desafio_quality.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HouseController {

    @GetMapping("/area/{prop_name}")
    public ResponseEntity<?> getArea(@PathVariable String prop_name) {
        return null;
    }

    @GetMapping("/biggest_room/{prop_name}")
    public ResponseEntity<?> getBiggestRoom(@PathVariable String prop_name){
        return null;
    }

    @GetMapping("/value/{prop_name}")
    public ResponseEntity<?> getValue( @PathVariable String prop_name){
        return null;
    }

    @GetMapping("/area_per_room/{prop_name}")
    public ResponseEntity<?> getAreaPerRoom(@PathVariable String prop_name){
        return null;
    }

}
