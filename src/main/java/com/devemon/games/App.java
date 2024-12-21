package com.devemon.games;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;


@SpringBootApplication
@EnableSpringConfigured
public class App {
    public static void main(String[] args) {
        var application = SpringApplication.run(App.class, args);
        var game = application.getBean(Game.class);
        game.run();
    }

}
