package com.reloadly.paypro.transactionservice.messaging;

public interface EventManager {

    void publishEvent(String topic, String payload);

}
