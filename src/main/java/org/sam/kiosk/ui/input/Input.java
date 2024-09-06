package org.sam.kiosk.ui.input;

import org.sam.kiosk.ui.input.command.Command;

public interface Input {
    Command inputProductNoOrIsCompleteOrder();
    Command inputQuantity();
    Command inputProgramTerminatedOrOrderContinue();
}
