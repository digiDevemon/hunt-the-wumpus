package com.devemon.games;

import org.springframework.stereotype.Component;

@Component
public class Game implements Runnable {
    @Override
    public void run() {
        System.out.println("Run");
    }
}
