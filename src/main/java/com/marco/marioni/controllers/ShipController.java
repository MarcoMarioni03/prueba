package com.marco.marioni.controllers;

import com.marco.marioni.controllers.interfaces.ShipRestApi;
import com.marco.marioni.domain.dtos.ShipDTO;
import com.marco.marioni.services.KafkaService;
import com.marco.marioni.services.ShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShipController implements ShipRestApi {
    private final ShipService shipService;
    private final KafkaService kafkaService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ShipDTO findById(Integer id) {
        return shipService.findById(id);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ShipDTO save(ShipDTO shipDTO) {
        return shipService.save(shipDTO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ShipDTO update(ShipDTO shipDTO) {
        return shipService.update(shipDTO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public Integer deleteById(Integer id) {
        return kafkaService.sendMessageWithIdToBeDeleted(id)
                .thenApply(result -> result)
                .join();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public List<ShipDTO> findAll(Integer pageNumber, Integer pageSize) {
        return shipService.findAll(null, pageNumber, pageSize);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public List<ShipDTO> findByName(String name, Integer pageNumber, Integer pageSize) {
        return shipService.findAll(name, pageNumber, pageSize);
    }

}
