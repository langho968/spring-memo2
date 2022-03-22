package com.hanghae99.springweek01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Springweek01Application {

    public static void main(String[] args) {
        SpringApplication.run(Springweek01Application.class, args);
    }

}
