package kr.co._29cm.homework.view;

public enum InputCommand {
    EXIT(" ");

    private final String commandStr;

    InputCommand(String commandStr) {
        this.commandStr = commandStr;
    }

    public boolean equals(String commandStr) {
        return this.commandStr.equals(commandStr);
    }
}
