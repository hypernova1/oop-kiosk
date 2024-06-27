package kr.co._29cm.homework.common.repository;

import org.junit.jupiter.api.Test;

public class TestEntity {
    @PrimaryKey
    private String id;

    private String value;

    protected TestEntity() {}

    public String getId() {
        return this.id;
    }

    public String getValue() {
        return this.value;
    }

    public void setId(String id) {
        this.id = id;
    }

}
