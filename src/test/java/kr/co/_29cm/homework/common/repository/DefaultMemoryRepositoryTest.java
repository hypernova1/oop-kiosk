package kr.co._29cm.homework.common.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class DefaultMemoryRepositoryTest {
    private final DefaultMemoryRepository<TestEntity, String> defaultMemoryRepository = new DefaultMemoryRepository<>() {};

    @AfterEach
    void clearItems() {
        this.defaultMemoryRepository.items.clear();
    }

    @Test
    void not_exists_id() {
        TestEntity item = new TestEntity();

        assertThatExceptionOfType(PrimaryKeyNotFoundException.class)
                .isThrownBy(() -> defaultMemoryRepository.save(item));
    }

    @Test
    void save() {
        TestEntity item = new TestEntity();
        item.setId(UUID.randomUUID().toString());
        defaultMemoryRepository.save(item);
        assertThat(defaultMemoryRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void save_all() {
        int size = 10;
        List<TestEntity> dummyItems = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TestEntity item = new TestEntity();
            item.setId(UUID.randomUUID().toString());
            dummyItems.add(item);
        }

        defaultMemoryRepository.save(dummyItems);
        assertThat(defaultMemoryRepository.findAll().size()).isEqualTo(size);
    }

    @Test
    void find_by_id() {
        TestEntity item = new TestEntity();
        String id = UUID.randomUUID().toString();
        item.setId(id);
        defaultMemoryRepository.save(item);

        assertThat(defaultMemoryRepository.findByPrimaryKey(id).isPresent()).isTrue();
        assertThat(defaultMemoryRepository.findByPrimaryKey(id).get()).isEqualTo(item);
    }

}