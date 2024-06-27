package kr.co._29cm.homework.util.csv;

import java.util.List;

/**
 * 경로를 통해 CSV를 읽어 클래스 인스턴스로 변환하는 클래스
 * */
public class CsvProcessor {

    public static <T> List<T> convertToInstanceFromPath(String filePath, Class<T> clazz) {
        CsvData csvData = CsvDataReader.readCsv(filePath);
        return CsvToInstanceConvertor.convertToInstances(csvData, clazz);
    }

}
