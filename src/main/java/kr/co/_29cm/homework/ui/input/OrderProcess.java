package kr.co._29cm.homework.ui.input;

public enum OrderProcess {
    ADD_PRODUCT,
    COMPLETE_ORDER,
    CONTINUE_OR_QUIT,
    QUIT;

    public static OrderProcess start() {
        return ADD_PRODUCT;
    }
}
