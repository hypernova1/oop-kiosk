package kr.co._29cm.homework.util.csv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CsvDataReaderTest {

    @Test
    @DisplayName("csv 파일 읽기")
    void read_data() {
        InputStream inputStreamFromPath = CsvDataReader.getInputStreamFromPath("product.csv");
        assertThat(inputStreamFromPath).isNotNull();
    }

    @Test
    @DisplayName("경로에 파일이 없는 경우")
    void read_data_no_path() {
        assertThatThrownBy(() -> CsvDataReader.getInputStreamFromPath("product2.csv"))
                .isInstanceOf(RuntimeException.class);
    }

}