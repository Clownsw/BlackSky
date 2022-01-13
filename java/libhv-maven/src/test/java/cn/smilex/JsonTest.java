package cn.smilex;

import cn.smilex.libhv.jni.json.Json;
import org.junit.Test;

/**
 * @author smilex
 */
public class JsonTest {

    @Test
    public void createAndCloseTest() {
        String jsonStr = "{\"name\":\"Mash\",\"star\":4,\"hits\":[2,2,1,3]}";
        String jsonStr1 = "{\"name\":\"Mash1\",\"star\":4,\"hits\":[2,2,1,3]}";
        Json json = new Json(jsonStr);
        Json json1 = new Json(jsonStr1);
        json.close();
        json1.close();
    }

    @Test
    public void getStringTest() {
        String jsonStr = "{\"name\":\"Mash1\",\"star\":4,\"hits\":[2,2,1,3]}";
        Json json = new Json(jsonStr);
        String name = json.getString("name");
        System.out.println(name);

        json.close();
    }

    @Test
    public void getIntTest() {
        String jsonStr = "{\"name\":\"Mash1\",\"star\":4,\"hits\":[2,2,1,3]}";
        Json json = new Json(jsonStr);
        int star = json.getInt("star");
        System.out.println(star);

        json.close();
    }
}
