package kr.co._29cm.homework.util.csv;

import java.util.List;

public class CsvData {

    private final List<String> fields;
    private final List<String[]> values;

    protected CsvData(List<String> fields, List<String[]> values) {
        this.fields = fields;
        this.values = values;
    }

    public List<String> getFields() {
        return this.fields;
    }

    public List<String[]> getValues() {
        return this.values;
    }

}
