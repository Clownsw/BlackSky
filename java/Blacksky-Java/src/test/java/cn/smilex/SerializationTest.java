package cn.smilex;

import cn.smilex.blacksky.jni.Person;
import cn.smilex.blacksky.jni.util.BlackSkyUtil;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * @author smilex
 */
public class SerializationTest {

    @Test
    public void serializationTest() {
        Person person = new Person("xd", 18, List.of("1", "2", "3"), Set.of("4", "5", "6"));
        String s = BlackSkyUtil.objToJsonStr(person);
        System.out.println(s);
    }

}
