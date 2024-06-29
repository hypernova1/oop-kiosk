package kr.co._29cm.homework.common;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class BaseUuidEntity {

    @Id
    protected final String id;

    protected BaseUuidEntity() {
        id = UUID.randomUUID().toString();
    }

}
