package com.example.kafkaspring.kafka;

import com.example.kafkaspring.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class KafkaConsumer {

    private final String REGISTRATION_TOPIC = "REGISTRATION";

    private final EmailService emailService;

    @Autowired
    public KafkaConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "email", groupId = "my-consumer")
    public void listen(String message) {
        String[] splitMessage = message.split(";");
        String topic = splitMessage[0];
        String email = splitMessage[1];
        String code = splitMessage[2];
        if (REGISTRATION_TOPIC.equals(topic)) {
            emailService.sendMessageAboutRegistration(email, code);
        } else {
            System.out.println("UNDEFINED!");
        }
    }

}