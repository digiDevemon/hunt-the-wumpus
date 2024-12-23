package com.devemon.games.keyboard;

import com.devemon.games.domain.GameCommand;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class CommandListener {

    public GameCommand read() {
        var line = keyboardListener.nextLine();
        return commandFactory.apply(line);
    }

    public CommandListener(CommandsFactory commandFactory) {
        keyboardListener = new Scanner(System.in);
        this.commandFactory = commandFactory;
    }

    public void setScanner(Scanner scanner){
        this.keyboardListener = scanner;
    }

    private Scanner keyboardListener;
    private final CommandsFactory commandFactory;
}
