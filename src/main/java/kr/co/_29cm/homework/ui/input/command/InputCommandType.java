package kr.co._29cm.homework.ui.input.command;

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

    public boolean equals(Command command) {
        if (command == null) {
            return false;
        }
        return this.shortCommand.equals(command.toString()) || this.fullCommand.equals(command.toString());
    }
}
