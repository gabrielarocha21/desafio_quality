package br.com.meli.bootcamp.desafio_quality.service;

import br.com.meli.bootcamp.desafio_quality.entities.HouseEntity;
import br.com.meli.bootcamp.desafio_quality.entities.RoomEntity;
import br.com.meli.bootcamp.desafio_quality.repositories.HouseRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class HouseService {
    private HouseRepository houseRepository;


    @Autowired
    RoomService roomService;

    public HouseService() {
        this.houseRepository = new HouseRepository();
    }

    public HouseEntity getHouse(String propName) {

        return this.houseRepository.findByName(propName);
    }

    public Double getArea(String propName) {

        HouseEntity houseEntity = this.houseRepository.findByName(propName);
        Double areaTotal = 0.0;
        for (RoomEntity r : houseEntity.getRoomsList()) {
            areaTotal += this.roomService.getArea(r);
        }
        return areaTotal;
    }

    public RoomEntity getBiggestRoom(String propName) {
        Map<RoomEntity, Double> map = this.getAreaPerRoom(propName);

        Double biggestRoomTotal= null;
        RoomEntity room = null;
        for (Map.Entry<RoomEntity, Double> m : map.entrySet()){
            if(biggestRoomTotal == null){
                biggestRoomTotal = m.getValue();
                room = m.getKey();
            } else if(m.getValue() > biggestRoomTotal){
                biggestRoomTotal = m.getValue();
                room = m.getKey();
            }
        }
        return room;
    }

    public BigDecimal getValue(String propName) {
        HouseEntity houseEntity = this.houseRepository.findByName(propName);
        Double areaTotal = this.getArea(propName);
        BigDecimal valueM2 = houseEntity.getDistrict().getValueDistrictM2();
        BigDecimal valorTotal = new BigDecimal(String.valueOf(valueM2.multiply(new BigDecimal(areaTotal))));

        return valorTotal;
    }

    public Map<RoomEntity, Double> getAreaPerRoom(String propName) {
        HouseEntity houseEntity = this.houseRepository.findByName(propName);
        Map<RoomEntity, Double> map = new HashMap<RoomEntity, Double>();
        for (RoomEntity r : houseEntity.getRoomsList()) {
            map.put(r, this.roomService.getArea(r));
        }

        return map;
    }
}
