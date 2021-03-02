package com.reloadly.paypro.transactionservice.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventManagerImpl implements EventManager {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publishEvent(String topic, String payload) {
        kafkaTemplate.send(topic, payload);
        log.info("Published event to " + topic + " Payload: " + payload);
    }
}
