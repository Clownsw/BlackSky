package cn.smilex;

import cn.smilex.blacksky.jni.json.*;
import org.junit.Test;

import java.util.List;

/**
 * @author smilex
 */
public class JsonTest {

    /**
     * 测试: 创建和释放
     */
    @Test
    public void createAndCloseTest() {
        String jsonStr = "{\"name\":\"Mash\",\"star\":4,\"hits\":[2,2,1,3]}";
        String jsonStr1 = "{\"name\":\"Mash1\",\"star\":4,\"hits\":[2,2,1,3]}";
        Json json = new Json(jsonStr);
        Json json1 = new Json(jsonStr1);
        json.close();
        json1.close();
    }

    /**
     * 测试: 获取字符串
     */
    @Test
    public void getStringTest() {
        String jsonStr = "{\"name\":\"Mash1\",\"star\":4,\"hits\":[2,2,1,3]}";
        Json json = new Json(jsonStr);
        String name = json.getString("name");
        System.out.println(name);

        json.close();
    }

    /**
     * 测试: 获取整数
     */
    @Test
    public void getIntTest() {
        String jsonStr = "{\"name\":\"Mash1\",\"star\":4,\"hits\":[2,2,1,3]}";
        Json json = new Json(jsonStr);
        int star = json.getInt("star");
        System.out.println(star);

        json.close();
    }

    /**
     * 测试: 获取长整数
     */
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

    /**
     * 测试: 获取一个对象
     */
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

    /**
     * 测试: 获取子节点的子节点对象
     */
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

    /**
     * 测试: 获取数组
     */
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

    /**
     * 测试: 获取布尔类型
     */
    @Test
    public void getBooleanTest() {
        String jsonStr1 = "{\n" +
                "\t\"b1\": true,\n" +
                "\t\"b2\": false\n" +
                "}";

        Json json1 = new Json(jsonStr1);
        System.out.println(json1.getBoolean("b1"));
        System.out.println(json1.getBoolean("b2"));
        json1.close();


        String jsonStr2 = "{\n" +
                "\t\"obj\": {\n" +
                "\t\t   \"b1\": true,\n" +
                "\t\t\"b2\": false\n" +
                "\t}\n" +
                "}";

        Json json2 = new Json(jsonStr2);

        System.out.println(json2.getObject("obj").getBoolean("b1"));
        System.out.println(json2.getObject("obj").getBoolean("b2"));

        System.out.println(json2.getPoint("/obj/b1").asPointerBoolean());
        System.out.println(json2.getPoint("/obj/b2").asPointerBoolean());

        System.out.println(json2.getObject("obj").getPoint("/b1").asPointerBoolean());
        System.out.println(json2.getObject("obj").getPoint("/b2").asPointerBoolean());

        json2.close();

        String jsonStr3 = "[\n" +
                "\t{\n" +
                "\t\t\"b1\": true\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"b2\": false\n" +
                "\t}\n" +
                "]";

        Json json3 = new Json(jsonStr3);

        System.out.println(json3.asRootArrJsonObject().get(0).getBoolean("b1"));
        System.out.println(json3.asRootArrJsonObject().get(1).getBoolean("b2"));

        System.out.println(json3.asRootArrJsonObject().get(0).getPoint("/b1").asPointerBoolean());
        System.out.println(json3.asRootArrJsonObject().get(1).getPoint("/b2").asPointerBoolean());

        json3.close();
    }

    /**
     * 测试: 获取一个对象数组(每个对象各有一个names数组), 获取对象内的names数组
     */
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

    /**
     * 测试: 如果根节点是数组则之间转换为数组
     */
    @Test
    public void getRootAsArr() {
        String jsonStr = "[\n" +
                "  {\n" +
                "    \"name\": \"xuda\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"xuda1\"\n" +
                "  }\n" +
                "]";

        Json json = new Json(jsonStr);

        List<JsonObject> jsonObjects = json.asRootArrJsonObject();
        System.out.println(jsonObjects.get(0).getString("name"));
        System.out.println(jsonObjects.get(1).getString("name"));

        System.out.println(jsonObjects.get(0).getPoint("/name").asPointerString());
        System.out.println(jsonObjects.get(1).getPoint("/name").asPointerString());

        json.close();
    }

    /**
     * 测试: 获取一个对象数组(每个对象各有一个names数组), 获取对象内的names数组的第n个对象内的数据
     */
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

    /**
     * 测试: json pointer
     */
    @Test
    public void jsonPointTest() {
        String jsonStr = "{\n" +
                "    \"size\" : 3,\n" +
                "    \"users\" : [\n" +
                "        {\"id\": 1, \"name\": \"Harry\"},\n" +
                "        {\"id\": 2, \"name\": \"Ron\"},\n" +
                "        {\"id\": 3, \"name\": \"Hermione\"}\n" +
                "    ]\n" +
                "}";

        Json json = new Json(jsonStr);

        System.out.println(json.getPoint("/size").asPointerInt());

        System.out.println(json.getPoint("/users/0/id").asPointerInt());
        System.out.println(json.getPoint("/users/1/id").asPointerInt());
        System.out.println(json.getPoint("/users/2/id").asPointerInt());

        System.out.println(json.getPoint("/users/0/name").asPointerString());
        System.out.println(json.getPoint("/users/1/name").asPointerString());
        System.out.println(json.getPoint("/users/2/name").asPointerString());

        System.out.println(json.getPoint("/users/0/id").asPointerLong());
        System.out.println(json.getPoint("/users/1/id").asPointerLong());
        System.out.println(json.getPoint("/users/2/id").asPointerLong());

        json.close();
    }

    /**
     * 二次测试: json pointer
     */
    @Test
    public void jsonPointTest2() {
        String jsonStr = "{\n" +
                "\t\"a\": {\n" +
                "\t\t\"b\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"name\": \"c1\",\n" +
                "\t\t\t\t\"height\": 1.77\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"name\": \"c2\",\n" +
                "\t\t\t\t\"height\": 1.88\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";

        Json json = new Json(jsonStr);

        System.out.println(json.getPoint("/a/b/0/name").asPointerString());
        System.out.println(json.getPoint("/a/b/1/name").asPointerString());

        System.out.println(json.getObject("a").getPoint("/b/0/name").asPointerString());
        System.out.println(json.getObject("a").getPoint("/b/1/name").asPointerString());

        // a -> b -> 0 -> name
        //           ^
        //           |
        System.out.println(json.getObject("a").getArrJsonObject("b").get(0).getPoint("/name").asPointerString());

        System.out.println(json.getObject("a").getPoint("/b/0/height").asPointerDouble());
        System.out.println(json.getObject("a").getPoint("/b/1/height").asPointerDouble());
        json.close();
    }

    /**
     * 测试: 根节点是一个数组获取这个数组的第0元素(对象), 并且以json pointer方式获取到对象内的对数据
     */
    @Test
    public void getPointTest02() {
        String jsonStr = "[\n" +
                "\t{\n" +
                "\t\t\"obj\": {\n" +
                "\t\t\t\"b1\": true,\n" +
                "\t\t\t\"c2\": \"test\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "]";

        Json json = new Json(jsonStr);

        System.out.println(json.asRootArrJsonObject().get(0).getPoint("/obj/b1").asPointerBoolean());
        System.out.println(json.asRootArrJsonObject().get(0).getPoint("/obj/c2").asPointerString());

        json.close();
    }

    /**
     * 测试: json pointer获取的到节点直接转换为对象
     */
    @Test
    public void getPointerAsObject() {
        String jsonStr = "{\n" +
                "\t\"obj1\": {\n" +
                "\t\t\"obj2\": {\n" +
                "\t\t\t\"name\": \"xuda\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";

        Json json = new Json(jsonStr);

        System.out.println(json.getPoint("/obj1")
                .asPointerObject()
                .getPointerObject("obj2")
                .getPointString("name")
        );

        System.out.println(json.getPoint("/obj1/obj2")
                .asPointerObject()
                .getPoint("/name")
                .asPointerString());

        json.close();
    }

    /**
     * 测试: 获取一个对象内的对象数组的第n个对象内的names数组(使用json pointer)
     */
    @Test
    public void getPointerAsArrObject() {
        String str = "{\n" +
                "\t\"obj1\": {\n" +
                "\t\t\"arr1\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"names\": [\n" +
                "\t\t\t\t\t\"a1\",\n" +
                "\t\t\t\t\t\"a2\"\n" +
                "\t\t\t\t]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"names\": [\n" +
                "\t\t\t\t\t\"b1\",\n" +
                "\t\t\t\t\t\"b2\"\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\t\t\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";

        Json json = new Json(str);

        List<JsonObject> objs1 = json.getPoint("/obj1/arr1").asPointArrJsonObject();

        for (JsonObject obj1 : objs1) {
            List<JsonObject> objs2 = obj1.getPoint("/names").asPointArrJsonObject();

            for (JsonObject obj2 : objs2) {
                System.out.println(obj2.asPointerString());
            }
        }

        json.close();
    }

    /**
     * 测试多个可变JSON释放
     * @author smilex
     */
    @Test
    public void testTwoJsonMutClose() {

        JsonMut json = JsonMut.buildObject();
        JsonMutObject root = (JsonMutObject) json.getRoot();

        JsonMut json2 = JsonMut.buildArr();
        JsonMutArr root2 = (JsonMutArr) json2.getRoot();

        root.addStr("testStr", "Hello, 你好")
                .addInt("testInt", Integer.MAX_VALUE)
                .addDouble("testDouble", Double.MAX_VALUE)
                .addLong("testLong", Long.MAX_VALUE)
                .addBoolean("testBooleanTrue", true)
                .addBoolean("testBooleanFalse", false);

        root2.addArrStr("testStr")
                .addArrInt(Integer.MAX_VALUE)
                .addArrDouble(Double.MAX_VALUE)
                .addArrLong(Long.MAX_VALUE)
                .addArrBoolean(true)
                .addArrBoolean(false);


        System.out.println(json.getJsonStr());
        System.out.println(json2.getJsonStr());

        json.close();
        json2.close();
    }

    /**
     * 测试可变JSON对象和可变JSON数组绑定
     * @author smilex
     */
    @Test
    public void testJsonMutObjectAndJsonMutArrBind() {
        JsonMut jsonMut = JsonMut.buildObject();
        JsonMutObject root = (JsonMutObject) jsonMut.getRoot();

        JsonMutObject testObj = root.createFreeJsonMutObject("testObj");
        JsonMutArr testArr = root.createFreeJsonMutArr("testArr");

        root.bind("testObj", testObj);
        root.bind("testArr", testArr);

        JsonMutObject testObj2 = testObj.createFreeJsonMutObject("testObj2");
        testObj.bind("testObj2", testObj2);

        JsonMutObject testArrTestObj1 = testArr.createFreeJsonMutObject("testArrTestObj1");
        testArr.bind(testArrTestObj1);

        System.out.println(jsonMut.getJsonStr());

        jsonMut.close();
    }

    /**
     * 测试可变对象的添加
     * @author smilex
     */
    @Test
    public void testJsonMutObjectAdd() {
        JsonMut jsonMut = JsonMut.buildObject();
        JsonMutObject root = (JsonMutObject) jsonMut.getRoot();

        JsonMutObject obj = root.createFreeJsonMutObject("obj");
        root.bind("obj", obj);

        obj.addStr("testStr", "Hello, 你好")
                .addInt("testInt", Integer.MAX_VALUE)
                .addDouble("testDouble", Double.MAX_VALUE)
                .addLong("testLong", Long.MAX_VALUE)
                .addBoolean("testBooleanTrue", true)
                .addBoolean("testBooleanFalse", false);

        System.out.println(jsonMut.getJsonStr());

        jsonMut.close();
    }

    /**
     * 测试可变数组的添加
     * @author smilex
     */
    @Test
    public void testJsonMutArrAdd() {
        JsonMut jsonMut = JsonMut.buildArr();
        JsonMutArr root = (JsonMutArr) jsonMut.getRoot();

        JsonMutArr arr = root.createFreeJsonMutArr("arr");
        root.bind(arr);

        arr.addArrStr("testStr")
                .addArrInt(Integer.MAX_VALUE)
                .addArrDouble(Double.MAX_VALUE)
                .addArrLong(Long.MAX_VALUE)
                .addArrBoolean(true)
                .addArrBoolean(false);

        System.out.println(jsonMut.getJsonStr());

        jsonMut.close();
    }

    /**
     * 测试使用可变JSON对象和可变JSON数组结合
     * @author smilex
     */
    @Test
    public void testUseJsonMutObjectAndJsonMutArr() {
        JsonMut mut = JsonMut.buildObject();
        JsonMutObject root = (JsonMutObject) mut.getRoot();

        JsonMutObject testObj1 = root.createJsonMutObject("testObj1");
        testObj1.addStr("testStr", "Hello, 你好")
                .addInt("testInt", Integer.MAX_VALUE)
                .addDouble("testDouble", Double.MAX_VALUE)
                .addLong("testLong", Long.MAX_VALUE)
                .addBoolean("testBooleanTrue", true)
                .addBoolean("testBooleanFalse", false);

        JsonMutArr testArr1 = root.createJsonMutArr("testArr1");

        JsonMutObject testArr1Obj1 = testArr1.createFreeJsonMutObject("testArr1Obj1");
        JsonMutObject testArr1Obj2 = testArr1.createFreeJsonMutObject("testArr1Obj2");
        JsonMutObject testArr1Obj3 = testArr1.createFreeJsonMutObject("testArr1Obj3");

        testArr1.bind(testArr1Obj1);
        testArr1.bind(testArr1Obj2);
        testArr1.bind(testArr1Obj3);

        JsonMutObject[] jsonMutObjects = {testArr1Obj1, testArr1Obj2, testArr1Obj3};
        for (JsonMutObject jsonMutObject : jsonMutObjects) {
            jsonMutObject.addStr("testStr", "Hello, 你好")
                    .addInt("testInt", Integer.MAX_VALUE)
                    .addDouble("testDouble", Double.MAX_VALUE)
                    .addLong("testLong", Long.MAX_VALUE)
                    .addBoolean("testBooleanTrue", true)
                    .addBoolean("testBooleanFalse", false);
        }

        JsonMutArr testArr1Obj1Arr1 = testArr1Obj1.createFreeJsonMutArr("testArr1Obj1Arr1");
        testArr1Obj1.bind("testArr1Obj1Arr1", testArr1Obj1Arr1);

        testArr1Obj1Arr1.addArrStr("testStr")
                .addArrInt(Integer.MAX_VALUE)
                .addArrDouble(Double.MAX_VALUE)
                .addArrLong(Long.MAX_VALUE)
                .addArrBoolean(true)
                .addArrBoolean(false);

        System.out.println(mut.getJsonStr());

        mut.close();
    }
}
