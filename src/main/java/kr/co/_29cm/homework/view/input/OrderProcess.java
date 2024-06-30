package kr.co._29cm.homework.view.input;

public enum OrderProcess {
    ADD_PRODUCT,
    COMPLETE_ORDER,
    CONTINUE_OR_QUIT,
    QUIT;

    public static OrderProcess start() {
        return ADD_PRODUCT;
    }
}
