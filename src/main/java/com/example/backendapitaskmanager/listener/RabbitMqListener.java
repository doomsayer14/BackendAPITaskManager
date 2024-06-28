package com.example.backendapitaskmanager.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMqListener {
    @RabbitListener(queues = "tasks")
    public void receive(String message) {
        log.info(message);
    }
}
