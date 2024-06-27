package kr.co._29cm.homework.util;

public class Employee {
    @CsvMatcher("이름")
    private String name;
    @CsvMatcher("나이")
    private int age;
    @CsvMatcher("직업")
    private String job;

    @Override
    public String toString() {
        return "name: " + name + ", age: " + age + ", job: " + job;
    }
}
