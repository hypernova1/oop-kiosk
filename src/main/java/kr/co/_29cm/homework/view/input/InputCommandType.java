package kr.co._29cm.homework.view.input;

public enum InputCommandType {
    COMPLETE_ORDER(" ", " "),
    CONTINUE_ORDER("o", "order"),
    TERMINATED_PROGRAM("q", "quit");

    private final String shortCommand;
    private final String fullCommand;

    InputCommandType(String shortCommand, String fullCommand) {
        this.shortCommand = shortCommand;
        this.fullCommand = fullCommand;
    }

    public boolean equals(Command commandStr) {
        if (commandStr == null) {
            return false;
        }
        return this.shortCommand.equals(commandStr.toString()) || this.fullCommand.equals(commandStr.toString());
    }
}
