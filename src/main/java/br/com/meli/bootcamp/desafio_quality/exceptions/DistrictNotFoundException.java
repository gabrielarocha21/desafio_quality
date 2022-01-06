package br.com.meli.bootcamp.desafio_quality.exceptions;

import java.util.function.Supplier;

public class DistrictNotFoundException extends RuntimeException {

    public DistrictNotFoundException(String message){
        super(message);
    }
    public DistrictNotFoundException(){

    }
}
