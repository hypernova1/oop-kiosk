package kr.co._29cm.homework.util.csv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CsvToInstanceConvertorTest {

    @Test
    @DisplayName("인스턴스 생성 성공")
    void test_create_instance() {
        CsvData csvData = CsvDataReader.readCsv("employee.csv");
        List<Employee> employees = CsvToInstanceConvertor.convertToInstances(csvData, Employee.class);
        assertThat(employees.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("모든 필드가 일치하면 인스턴스 생성")
    void test_success_create_instance() {
        String[] values = new String[] {"샘찬", "35", "개발자"};
        List<String> fields = Arrays.asList("이름", "나이", "직업");

        Employee employee = CsvToInstanceConvertor.convertToInstance(values, fields, Employee.class);
        assertThat(employee).isNotNull();
    }

    @Test
    @DisplayName("필드가 일치하지 않아 인스턴스 생성 실패")
    void test_failure_instance() {
        String[] values = new String[] {"샘찬", "35", "개발자"};
        List<String> fields = Arrays.asList("이름", "나이", "부서");

        assertThatThrownBy(() -> CsvToInstanceConvertor.convertToInstance(values, fields, Employee.class))
                .isInstanceOf(RuntimeException.class);
    }

}