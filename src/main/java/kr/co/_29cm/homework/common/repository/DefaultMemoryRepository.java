package kr.co._29cm.homework.common.repository;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefaultMemoryRepository<T, P> implements Repository<T, P> {

    protected List<T> items = new ArrayList<>();

    @Override
    public void save(T t) {
        P primaryKey = this.getPrimaryKey(t);
        if (primaryKey == null) {
            throw new PrimaryKeyNotFoundException();
        }
        int index = this.items.indexOf(t);
        if (index != -1) {
            this.items.set(index, t);
            return;
        }

        this.items.add(t);
    }

    @Override
    public void save(List<T> items) {
        items.forEach(this::save);
    }

    @Override
    public Optional<T> findByPrimaryKey(P primaryKey) {
        for (T item : this.items) {
            P itemPrimaryKey = getPrimaryKey(item);
            if (itemPrimaryKey.equals(primaryKey)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<T> findAll() {
        return items;
    }

    @Override
    public void delete(T t) {
        this.items.remove(t);
    }

    @Override
    public void delete(List<T> list) {
        this.items.removeAll(list);
    }

    @Override
    public void deleteByPrimaryKey(P primaryKey) {
        T item = this.findByPrimaryKey(primaryKey).orElse(null);
        if (item == null) {
            return;
        }
        this.items.remove(item);
    }

    @SuppressWarnings("unchecked")
    protected P getPrimaryKey(T t) {
        try {
            return (P) getFieldValue(t);
        } catch (ClassCastException e) {
            throw new PrimaryKeyTypeNotMatchException();
        }
    }

    private static <T> Object getFieldValue(T t) {
        Field field = getPropertyField(t);
        if (field == null) {
            throw new EntityPropertyNotFoundException();
        }

        field.setAccessible(true);

        try {
            return field.get(t);
        } catch (NullPointerException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> Field getPropertyField(T t) {
        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            try {
                Annotation annotation = field.getAnnotation((Class<? extends Annotation>) PrimaryKey.class);
                if (annotation == null) {
                    continue;
                }

                field.setAccessible(true);
                return field;
            } catch (NullPointerException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


}
