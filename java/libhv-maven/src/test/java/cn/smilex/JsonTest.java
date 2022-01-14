package cn.smilex;

import cn.smilex.libhv.jni.json.Json;
import cn.smilex.libhv.jni.json.JsonObject;
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

    @Test
    public void getObjectTest() {
        String jsonStr = "{\n" +
                "\t\"info\": {\n" +
                "\t\t\"name\": \"xuda\",\n" +
                "\t\t\"age\": 18,\n" +
                "\t\t\"gender\": \"女\",\n" +
                "\t\t\"height\": 1.88\n" +
                "\t}\n" +
                "}";
        Json json = new Json(jsonStr);
        JsonObject info = json.getObject("info");

        String name = info.getString("name");
        int age = info.getInt("age");
        String gender = info.getString("gender");
        double height = info.getDouble("height");

        System.out.println(name);
        System.out.println(age);
        System.out.println(gender);
        System.out.println(height);

        json.close();
    }

    @Test
    public void getChildChildTest() {
        String jsonStr = "{\n" +
                "\t\"a\": {\n" +
                "\t\t\"b\": {\n" +
                "\t\t\t\"name\": \"xuda\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";

        Json json = new Json(jsonStr);

        JsonObject b = json.getObject("a").getObject("b");
        System.out.println(b);
        String name = b.getString("name");
        System.out.println(name);

        json.close();
    }
}
