package com.reloadly.paypro.transactionservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reloadly.paypro.transactionservice.constant.EventTopicConstant;
import com.reloadly.paypro.transactionservice.exception.BadRequestException;
import com.reloadly.paypro.transactionservice.exception.NotFoundException;
import com.reloadly.paypro.transactionservice.payload.event.UserCreationEvent;
import com.reloadly.paypro.transactionservice.payload.event.UserUpdateEvent;
import com.reloadly.paypro.transactionservice.persistence.model.User;
import com.reloadly.paypro.transactionservice.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

@Slf4j
@Service
public class AccountServiceEventListener {

    @Autowired
    UserRepository userRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = EventTopicConstant.USER_CREATION)
    public void handleUserCreationEvent(String payload){
        log.info("User Creation Event: " + payload);
        UserCreationEvent event = new UserCreationEvent();
        try{
            event = objectMapper.readValue(payload, UserCreationEvent.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = new User (event.getEmail(), event.getUsername(), event.getPhoneNumber(), event.getAccountNumber(), BigDecimal.valueOf(10000));
        userRepository.save(user);
        log.info("User saved successfully");
    }

    @KafkaListener(topics = EventTopicConstant.USER_UPDATE)
    public void handleUserUpdateEvent(String payload){
        log.info("User Update Event: " + payload);
        UserUpdateEvent event = new UserUpdateEvent();
        try {
            event = objectMapper.readValue(payload, UserUpdateEvent.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        String email = event.getEmail();
        String username = event.getUsername();
        String phoneNumber = event.getPhoneNumber();
        if(ObjectUtils.isEmpty(email)) throw new BadRequestException("Email is required for user update");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Unable to fine user with email" + email));
        if(!ObjectUtils.isEmpty(username)) user.setUsername(username);
        if(!ObjectUtils.isEmpty(phoneNumber)) user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
    }
}
