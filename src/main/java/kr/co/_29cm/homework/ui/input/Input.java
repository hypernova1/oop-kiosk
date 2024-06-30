package kr.co._29cm.homework.ui.input;

import kr.co._29cm.homework.ui.input.command.Command;

public interface Input {

    Command inputProductNoOrIsCompleteOrder();
    Command inputQuantity();
    Command inputProgramTerminatedOrOrderContinue();
}
