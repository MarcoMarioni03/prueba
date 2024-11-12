package com.marco.marioni.services;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final ReplyingKafkaTemplate<String, Integer, Integer> replyingKafkaTemplate;

    @Value("${app.kafka.topics.topic}")
    private String topic;

    public CompletableFuture<Integer> sendMessageWithIdToBeDeleted(Integer id) {
        ProducerRecord<String, Integer> record = new ProducerRecord<>(topic, "id", id);

        RequestReplyFuture<String, Integer, Integer> replyFuture = replyingKafkaTemplate.sendAndReceive(record);

        return replyFuture.thenApply(ConsumerRecord::value);
    }

}
