package org.sam.kiosk.ui.input.command;

public interface Command {

    boolean isCompleteOrder();
    boolean isProgramTerminated();
    int toInt();

}
