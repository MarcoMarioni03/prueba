package com.marco.marioni.listeners;

import com.marco.marioni.services.ShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageListener {
    private final ShipService shipService;

    @KafkaListener(topics = "${app.kafka.topics.topic}")
    @SendTo("${app.kafka.topics.defaultRepl}")
    @Transactional
    public Integer deleteEntityById(Integer id) {
        shipService.deleteById(id);
        return id;
    }

}
