package kr.co._29cm.homework.common.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, P> {
    void save(T t);
    void save(List<T> list);
    List<T> findAll();
    Optional<T> findByPrimaryKey(P primaryKey);
    void delete(T t);
    void delete(List<T> list);
    void deleteByPrimaryKey(P primaryKey);
}