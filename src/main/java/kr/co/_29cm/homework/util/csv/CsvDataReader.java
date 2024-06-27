package kr.co._29cm.homework.util.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 파일 경로를 받아 CSV 데이터를 생성하는 클래스
 *
 * @see kr.co._29cm.homework.util.csv.CsvData
 * */
public class CsvDataReader {

    private final String filePath;

    private static final String CSV_PATTERN = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    public CsvDataReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 주어진 경로에 존재하는 CSV를 기반으로 인스턴스 목록을 생성한다.
     *
     * @return CSV 인스턴스
     * */
    public CsvData readCsv() {
        InputStream inputStream = getInputStreamFromPath();
        return readCsv(inputStream);
    }

    /**
     * CSV를 읽어 인스턴스를 반환한다.
     *
     * @param inputStream CSV 인풋 스트림
     * @return CSV 인스턴스
     * */
    private CsvData readCsv(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> lines = reader.lines().toList();
            List<String> properties = Arrays.asList(lines.get(0).split(CSV_PATTERN));
            List<String[]> values = new ArrayList<>();
            for (int i = 1; i < lines.size(); i++) {
                String[] csvColumnValues = lines.get(i).split(CSV_PATTERN);
                values.add(csvColumnValues);
            }

            return new CsvData(properties, values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 주어진 경로에서 CSV 파일의 인풋스트림을 가져온다.
     *
     * @param path CSV 경로
     * @return CSV 인풋 스트림
     * */
    private InputStream getInputStreamFromPath() {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(filePath);
        if (inputStream == null) {
            throw new RuntimeException("파일을 읽는 동안 오류가 발생했습니다.");
        }
        return inputStream;
    }

}
