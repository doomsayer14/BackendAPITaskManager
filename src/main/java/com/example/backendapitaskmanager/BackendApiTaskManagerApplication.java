package com.example.backendapitaskmanager;

import com.example.backendapitaskmanager.configuration.RabbitConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RabbitConfiguration.class)
public class BackendApiTaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApiTaskManagerApplication.class, args);
    }

}
