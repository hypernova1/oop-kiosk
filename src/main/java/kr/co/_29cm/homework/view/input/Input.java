package kr.co._29cm.homework.view.input;

import kr.co._29cm.homework.view.input.command.Command;

public interface Input {

    Command inputProductNoOrIsCompleteOrder();
    Command inputQuantity();
    Command inputProgramTerminatedOrOrderContinue();
}
