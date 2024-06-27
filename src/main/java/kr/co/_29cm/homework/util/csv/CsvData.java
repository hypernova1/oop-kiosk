package kr.co._29cm.homework.util.csv;

import java.util.List;

/**
 * CsvDataReader에서 생성된 CSV 데이터
 *
 * @see kr.co._29cm.homework.util.csv.CsvDataReader
 * */
public class CsvData {

    private final List<String> fields;
    private final List<String[]> values;

    protected CsvData(List<String> fields, List<String[]> values) {
        this.fields = fields;
        this.values = values;
    }

    /**
     * CSV 필드 이름 목록을 가져온다.
     * */
    public List<String> getFieldNames() {
        return this.fields;
    }

    /**
     * 각 레코드 데이터 목록을 가져온다.
     *
     * @return CSV 레코드 데이터 목록
     * */
    public List<String[]> getRecords() {
        return this.values;
    }

}
