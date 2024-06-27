package kr.co._29cm.homework.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CsvInstanceConvertorTest {

    @Test
    @DisplayName("csv 파일 읽기")
    void read_data() {
        InputStream inputStreamFromPath = CsvInstanceConvertor.getInputStreamFromPath("product.csv");
        assertThat(inputStreamFromPath).isNotNull();
    }

    @Test
    @DisplayName("경로에 파일이 없는 경우")
    void read_data_no_path() {
        assertThatThrownBy(() -> CsvInstanceConvertor.getInputStreamFromPath("product2.csv"))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("인스턴스 생성 성공")
    void test_create_instance() {
        List<Employee> employees = CsvInstanceConvertor.createInstances("employee.csv", Employee.class);
        assertThat(employees.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("모든 필드가 일치하면 인스턴스 생성")
    void test_success_create_instance() {
        String[] values = new String[] {"샘찬", "35", "개발자"};
        List<String> fields = Arrays.asList("이름", "나이", "직업");
        CsvInstanceConvertor.convertToInstance(values, fields, Employee.class);
    }

    @Test
    @DisplayName("필드가 일치하지 않아 인스턴스 생성 실패")
    void test_failure_instance() {
        String[] values = new String[] {"샘찬", "35", "개발자"};
        List<String> fields = Arrays.asList("이름", "나이", "부서");
        assertThatThrownBy(() -> CsvInstanceConvertor.convertToInstance(values, fields, Employee.class))
                .isInstanceOf(RuntimeException.class);
    }

}