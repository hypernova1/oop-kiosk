package org.sam.kiosk.common.lock;

import lombok.Getter;

@Getter
public enum LockType {
    PRODUCT("PRODUCT");

    private final String name;

    LockType(String name) {
        this.name = name;
    }

}
