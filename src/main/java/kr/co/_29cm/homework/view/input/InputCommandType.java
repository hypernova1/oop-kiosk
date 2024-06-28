package kr.co._29cm.homework.view.input;

public enum InputCommandType {
    COMPLETE_ORDER(" ", " "),
    CONTINUE_ORDER("o", "order"),
    QUIT("q", "quit");

    private final String fullCommand;
    private final String shortCommand;

    InputCommandType(String fullCommand, String shortCommand) {
        this.fullCommand = fullCommand;
        this.shortCommand = shortCommand;
    }

    public boolean equals(Command commandStr) {
        if (commandStr == null) {
            return false;
        }
        return this.fullCommand.equals(commandStr.toString()) || this.shortCommand.equals(commandStr.toString());
    }
}
