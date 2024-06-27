package kr.co._29cm.homework.util.csv;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CSV 컬럼 이름과 엔티티의 필드명을 매핑해주는 어노테이션
 * */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvFieldMatcher {
    String value() default "";
}
