package kr.co._29cm.homework.common.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @DisplayName("저장시 아이디가 없을 시")
    void not_exists_id() {
        TestEntity item = new TestEntity();

        assertThatExceptionOfType(PrimaryKeyNotFoundException.class)
                .isThrownBy(() -> defaultMemoryRepository.save(item));
    }

    @Test
    @DisplayName("저장 완료")
    void save() {
        TestEntity item = new TestEntity();
        item.setId(UUID.randomUUID().toString());
        defaultMemoryRepository.save(item);
        assertThat(defaultMemoryRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("여러개 저장 완료")
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
    @DisplayName("고유키로 조회")
    void find_by_primary_key() {
        TestEntity item = new TestEntity();
        String id = UUID.randomUUID().toString();
        item.setId(id);
        defaultMemoryRepository.save(item);

        assertThat(defaultMemoryRepository.findByPrimaryKey(id).isPresent()).isTrue();
        assertThat(defaultMemoryRepository.findByPrimaryKey(id).get()).isEqualTo(item);
    }

    @Test
    @DisplayName("업데이트")
    void update() {
        TestEntity item = new TestEntity();
        String id = UUID.randomUUID().toString();
        item.setId(id);
        item.setValue("before");

        this.defaultMemoryRepository.save(item);

        item.setValue("after");
        this.defaultMemoryRepository.save(item);

        Optional<TestEntity> byPrimaryKey = this.defaultMemoryRepository.findByPrimaryKey(item.getId());

        assertThat(this.defaultMemoryRepository.items).hasSize(1);
        assertThat(byPrimaryKey.isPresent()).isTrue();
        assertThat(byPrimaryKey.get().getValue()).isEqualTo("after");
    }

}