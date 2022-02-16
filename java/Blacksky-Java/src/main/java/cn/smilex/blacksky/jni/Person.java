package cn.smilex.blacksky.jni;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author smilex
 */
@Data
@AllArgsConstructor
public class Person {
    private String name;
    private Integer age;
    private List<String> loves;
    private Set<String> looks;

    public Person() {
    }
}
