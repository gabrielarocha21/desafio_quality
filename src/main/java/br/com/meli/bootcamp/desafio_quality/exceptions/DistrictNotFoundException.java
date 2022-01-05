package br.com.meli.bootcamp.desafio_quality.exceptions;

public class DistrictNotFoundException extends RuntimeException{

    public DistrictNotFoundException(String message){
        super(message);
    }
    public DistrictNotFoundException(){

    }
}
