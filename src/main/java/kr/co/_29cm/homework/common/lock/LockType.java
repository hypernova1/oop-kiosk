package kr.co._29cm.homework.common.lock;

public enum LockType {
    PRODUCT("PRODUCT");

    private final String name;

    LockType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
