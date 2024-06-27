package kr.co._29cm.homework.util.csv;

public class Employee {
    @CsvFieldMatcher("이름")
    private String name;
    @CsvFieldMatcher("나이")
    private int age;
    @CsvFieldMatcher("직업")
    private String job;

    @Override
    public String toString() {
        return "name: " + name + ", age: " + age + ", job: " + job;
    }
}
