package kr.co._29cm.homework.ui.input.command;

public class CommandNotFoundException extends RuntimeException {
    public CommandNotFoundException() {
        super("커맨드를 입력해주세요.");
    }
}
