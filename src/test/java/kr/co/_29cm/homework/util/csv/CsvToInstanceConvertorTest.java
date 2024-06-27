package kr.co._29cm.homework.util.csv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CsvToInstanceConvertorTest {

    @Test
    @DisplayName("인스턴스 생성 성공")
    void test_create_instance() {
        CsvData csvData = new CsvDataReader("employee.csv").readCsv();
        List<Employee> employees = new CsvToInstanceConvertor<>(csvData, Employee.class).convertToInstances();
        assertThat(employees.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("모든 필드가 일치하면 인스턴스 생성")
    void test_success_create_instance() {
        List<String> fields = Arrays.asList("이름", "나이", "직업");
        String[] value = new String[] {"샘찬", "35", "개발자"};
        List<String[]> values = new ArrayList<>();
        values.add(value);
        CsvData csvData = new CsvData(fields, values);

        List<Employee> employee = new CsvToInstanceConvertor<>(csvData, Employee.class).convertToInstances();
        assertThat(employee).isNotNull();
    }

    @Test
    @DisplayName("필드가 일치하지 않아 인스턴스 생성 실패")
    void test_failure_instance() {
        List<String> fields = Arrays.asList("이름", "나이", "부서");
        String[] value = new String[] {"샘찬", "35", "개발자"};
        List<String[]> values = new ArrayList<>();
        values.add(value);
        CsvData csvData = new CsvData(fields, values);

        assertThatThrownBy(() -> new CsvToInstanceConvertor<Employee>(csvData, Employee.class).convertToInstances())
                .isInstanceOf(RuntimeException.class);
    }

}