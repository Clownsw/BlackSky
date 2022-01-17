package cn.smilex;

import cn.smilex.blacksky.jni.json.Json;
import cn.smilex.blacksky.jni.json.JsonObject;
import org.junit.Test;

import java.util.List;

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
    public void getLongTest() {
        String jsonStr = "{\n" +
                "\t\"createTime\":1641453215534,\n" +
                "\t\"obj\": {\n" +
                "\t\t\"createTime\":1641453215534\n" +
                "\t},\n" +
                "\t\"arr\": [\n" +
                "\t\t1641453215534\n" +
                "\t]\n" +
                "}";

        Json json = new Json(jsonStr);
        System.out.println(json.getLong("createTime"));
        System.out.println(json.getObject("obj").getLong("createTime"));
        System.out.println(json.getArrJsonObject("arr").get(0).asLong());
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
        /*
            {
                "a": {
                    "name": "a-xuda",
                    "b": {
                        "name": "b-xuda",
                        "c": {
                            "name": "c-xuda"
                        }
                    }
                }
            }
        */
        String jsonStr = "{\n" +
                "\t\"a\": {\n" +
                "\t\t\"name\": \"a-xuda\",\n" +
                "\t\t\"b\": {\n" +
                "\t\t\t\"name\": \"b-xuda\",\n" +
                "\t\t\t\"c\": {\n" +
                "\t\t\t\t\"name\": \"c-xuda\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";

        Json json = new Json(jsonStr);

        JsonObject a = json.getObject("a");

        System.out.println(a.getString("name"));

        JsonObject b = json.getObject("a").getObject("b");

        System.out.println(b.getString("name"));

        JsonObject c = b.getObject("c");

        System.out.println(c.getString("name"));

        json.close();
    }

    @Test
    public void getArrTest() {
        /*
        {
            "infos": [
                {
                    "name": "xuda1"
                },
                {
                    "name": "xuda2"
                },
                {
                    "name": "xuda3"
                }
            ]
        }
        */
        String jsonStr = "{\n" +
                "\t\"infos\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"xuda1\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"xuda2\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"xuda3\"\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";

        Json json = new Json(jsonStr);

        List<JsonObject> infos = json.getArrJsonObject("infos");
        for (JsonObject info : infos) {
            String name = info.getString("name");
            System.out.println(name);
        }

        json.close();
    }

    @Test
    public void getArrChildArr() {
        /*
            {
                "infos": [
                    {
                        "names:": [
                            "a",
                            "b",
                            "c"
                        ]
                    },
                    {
                        "names": [
                            "d",
                            "e",
                            "f",
                            "g"
                        ]
                    }
                ]
            }
        */
        String jsonStr = "{\n" +
                "\t\"infos\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"names\": [\n" +
                "\t\t\t\t\"a\",\n" +
                "\t\t\t\t\"b\",\n" +
                "\t\t\t\t\"c\"\n" +
                "\t\t\t]\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"names\": [\n" +
                "\t\t\t\t\"d\",\n" +
                "\t\t\t\t\"e\",\n" +
                "\t\t\t\t\"f\",\n" +
                "\t\t\t\t\"g\"\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";

        Json json = new Json(jsonStr);

        List<JsonObject> infos = json.getArrJsonObject("infos");

        for (JsonObject info : infos) {
            List<JsonObject> names = info.getArrJsonObject("names");
            for (JsonObject name : names) {
                System.out.println(name.asString());
            }
        }

//        System.out.println(infos.get(0).getArrJsonObject("names").get(2).asString());

        json.close();
    }

    @Test
    public void getArrObjArrNthArgs() {
        String jsonStr = "{\n" +
                "\t\"infos\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"names\": [\n" +
                "\t\t\t\t\"a\",\n" +
                "\t\t\t\t\"b\",\n" +
                "\t\t\t\t\"c\",\n" +
                "\t\t\t\t1\n" +
                "\t\t\t]\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"names\": [\n" +
                "\t\t\t\t\"d\",\n" +
                "\t\t\t\t\"e\",\n" +
                "\t\t\t\t\"f\",\n" +
                "\t\t\t\t\"g\",\n" +
                "\t\t\t\t1.2\n" +
                "\t\t\t]\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"double\": 1.2\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";

        Json json = new Json(jsonStr);

        int i = json.getArrJsonObject("infos")
                .get(0)
                .getArrJsonObject("names")
                .get(3)
                .asInt();
        System.out.println(i);

        System.out.println(json.getArrJsonObject("infos")
                .get(1)
                .getArrJsonObject("names")
                .get(4)
                .asDouble());

        System.out.println(json.getArrJsonObject("infos")
                .get(2)
                .getDouble("double"));

        json.close();
    }
}
