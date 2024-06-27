package kr.co._29cm.homework.util.csv;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV 데이터를 인스턴스로 변환한다.
 *
 * @see kr.co._29cm.homework.util.csv.CsvData
 * */
public class CsvToInstanceConvertor<T> {

    private final List<String> fields;
    private final List<String[]> values;
    private final Class<T> clazz;

    public CsvToInstanceConvertor(CsvData csvData, Class<T> clazz) {
        this.fields = csvData.getFieldNames();
        this.values = csvData.getRecords();
        this.clazz = clazz;
    }

    /**
     * InputStream의 데이터로 인스턴스 목록을 생성한다.
     *
     * @return 인스턴스 목록
     * */
    public List<T> convertToInstances() {
        List<T> instances = new ArrayList<>();
        for (String[] value : this.values) {
            T t = convertToInstance(value);
            instances.add(t);
        }

        return instances;
    }

    /**
     * 인스턴스를 생성한다.
     *
     * @param csvColumnValues CSV 레코드 내의 컬럼 값 목록
     * @return 인스턴스
     * */
    private T convertToInstance(String[] csvColumnValues) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();
            constructor.setAccessible(false);
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                String csvValue = fineCsvValue(csvColumnValues, field);
                field.setAccessible(true);
                field.set(instance, convertValue(field.getType(), csvValue));
                field.setAccessible(false);
            }
            return instance;
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 필드와 일치하는 CSV 값을 찾는다.
     *
     * @param csvColumnValues CSV 레코드 내의 컬럼 값 목록
     * @param field 인스턴스의 필드
     * @return CSV 값
     * */
    private String fineCsvValue(String[] csvColumnValues, Field field) {
        CsvFieldMatcher matcher = field.getDeclaredAnnotation(CsvFieldMatcher.class);
        String fieldName = matcher != null ? matcher.value() : field.getName();
        int index = this.fields.indexOf(fieldName);
        if (index == -1) {
            throw new RuntimeException("필드를 찾을 수 없습니다.");
        }
        return csvColumnValues[index];
    }

    /**
     * 주어진 값 문자열을 해당 필드 타입으로 변환한다.
     *
     * @param fieldType 변환할 필드 타입
     * @param value 값 문자열
     * @return 변환된 값
     */
    private Object convertValue(Class<?> fieldType, String value) {
        if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(value);
        } else if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(value);
        } else if (fieldType == float.class || fieldType == Float.class) {
            return Float.parseFloat(value);
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            return Boolean.parseBoolean(value);
        }

        return value;
    }

}
