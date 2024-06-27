package kr.co._29cm.homework.view;

public enum InputCommand {
    COMPLETE_ORDER(" ", " "),
    CONTINUE_ORDER("o", "order"),
    QUIT("q", "quit");

    private final String fullCommand;
    private final String shortCommand;

    InputCommand(String fullCommand, String shortCommand) {
        this.fullCommand = fullCommand;
        this.shortCommand = shortCommand;
    }

    public boolean equals(String commandStr) {
        if (commandStr == null) {
            return false;
        }
        return this.fullCommand.equals(commandStr) || this.shortCommand.equals(commandStr);
    }
}
