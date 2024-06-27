package kr.co._29cm.homework.util.csv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CsvDataReaderTest {

    @Test
    @DisplayName("csv 파일 읽기")
    void read_data() {
        CsvData csvData = new CsvDataReader("employee.csv").readCsv();
        assertThat(csvData).isNotNull();
        assertThat(csvData.getFields()).hasSize(3);
        assertThat(csvData.getValues()).hasSize(3);
        assertThat(csvData.getFields().get(0)).isEqualTo("이름");
        assertThat(csvData.getFields().get(1)).isEqualTo("나이");
        assertThat(csvData.getFields().get(2)).isEqualTo("직업");
    }

    @Test
    @DisplayName("경로에 파일이 없는 경우")
    void read_data_no_path() {
        assertThatThrownBy(() -> new CsvDataReader("product2.csv").readCsv())
                .isInstanceOf(RuntimeException.class);
    }

}