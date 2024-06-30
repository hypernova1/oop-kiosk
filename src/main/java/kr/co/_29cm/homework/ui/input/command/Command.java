package kr.co._29cm.homework.ui.input.command;

public interface Command {

    boolean isCompleteOrder();
    boolean isProgramTerminated();
    int toInt();

}
