package kr.co._29cm.homework.util;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CSV 파일을 기반으로 인스턴스를 생성하는 유틸 클래스
 */
public class CsvInstanceConvertor {

    /**
     * 주어진 경로에 존재하는 CSV를 기반으로 인스턴스 목록을 생성한다.
     *
     * @param filePath CSV 파일 경로
     * @param clazz 변환할 클래스 타입
     * @return 인스턴스 목록
     * */
    public static <T> List<T> createInstances(String filePath, Class<T> clazz) {
        InputStream inputStream = getInputStreamFromPath(filePath);
        return convertToInstances(inputStream, clazz);
    }

    /**
     * InputStream의 데이터로 인스턴스 목록을 생성한다.
     *
     * @param inputStream CSV 인풋 스트림
     * @param clazz 변환할 클래스 타입
     * @return 인스턴스 목록
     * */
    protected static <T> List<T> convertToInstances(InputStream inputStream, Class<T> clazz) {
        List<T> instances = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> lines = reader.lines().toList();
            String[] properties = lines.get(0).split(",");
            for (int i = 1; i < lines.size(); i++) {
                String[] csvColumnValues = lines.get(i).split(",");
                T t = convertToInstance(csvColumnValues, Arrays.asList(properties), clazz);
                instances.add(t);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return instances;
    }

    /**
     * 인스턴스를 생성한다.
     *
     * @param csvColumnValues CSV 레코드 내의 컬럼 값 목록
     * @param csvProperties CSV 프로퍼티 목록
     * @param clazz 변환할 클래스 타입
     * @return 인스턴스
     * */
    protected static <T> T convertToInstance(String[] csvColumnValues, List<String> csvProperties, Class<T> clazz) {
        try {
            T instance = clazz.getConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                String csvValue = fineCsvValue(csvColumnValues, csvProperties, field);
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
     * @param csvProperties csv 프로퍼티 목록
     * @param field 인스턴스의 필드
     * @return CSV 값
     * */
    private static String fineCsvValue(String[] csvColumnValues, List<String> csvProperties, Field field) {
        CsvMatcher matcher = field.getDeclaredAnnotation(CsvMatcher.class);
        String fieldName = matcher != null ? matcher.value() : field.getName();
        int index = csvProperties.indexOf(fieldName);
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
    private static Object convertValue(Class<?> fieldType, String value) {
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

    /**
     * 주어진 경로에서 CSV 파일의 인풋스트림을 가져온다.
     *
     * @param path CSV 경로
     * @return CSV 인풋 스트림
     * */
    protected static InputStream getInputStreamFromPath(String path) {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(path);
        if (inputStream == null) {
            throw new RuntimeException("파일을 읽는 동안 오류가 발생했습니다.");
        }
        return inputStream;
    }

}
