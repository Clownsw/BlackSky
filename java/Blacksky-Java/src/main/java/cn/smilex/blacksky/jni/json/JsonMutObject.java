package cn.smilex.blacksky.jni.json;

import cn.smilex.blacksky.jni.Info;
import cn.smilex.blacksky.jni.json.type.JsonType;
import cn.smilex.blacksky.jni.json.type.JsonTypeStr;

/**
 * @author smilex
 */
public class JsonMutObject extends JsonMut {
    private final long address;

    static {
        synchronized (Json.class) {
            if (!Info.isInit) {
                Info.init();
            }
        }
    }

    public JsonMutObject(long address) {
        this.address = address;
    }

    /**
     * 向当前对象添加一个字符串
     * @param name key
     * @param data value
     * @return this
     */
    public JsonMutObject addStr(String name, String data) {
        synchronized (JsonMutObject.class) {
            _objAdd(Json.Json_Type.STRING.id, address, name, data);
            return this;
        }
    }

    /**
     * 向当前对象添加一个整数
     * @param name key
     * @param data value
     * @return this
     */
    public JsonMutObject addInt(String name, int data) {
        synchronized (JsonMutObject.class) {
            _objAdd(Json.Json_Type.INTEGER.id, address, name, data);
            return this;
        }
    }

    /**
     * 向当前对象添加一个小数
     * @param name key
     * @param data value
     * @return this
     */
    public JsonMutObject addDouble(String name, double data) {
        synchronized (JsonMutObject.class) {
            _objAdd(Json.Json_Type.DOUBLE.id, address, name, data);
            return this;
        }
    }

    /**
     * 向当前对象添加一个长整数
     * @param name key
     * @param data value
     * @return this
     */
    public JsonMutObject addLong(String name, long data) {
        synchronized (JsonMutObject.class) {
            _objAdd(Json.Json_Type.LONG.id, address, name, data);
            return this;
        }
    }

    /**
     * 向当前对象添加一个布尔
     * @param name key
     * @param data value
     * @return this
     */
    public JsonMutObject addBoolean(String name, boolean data) {
        synchronized (JsonMutObject.class) {
            _objAdd(Json.Json_Type.BOOLEAN.id, address, name, data);
            return this;
        }
    }

    /**
     * 创建一个自动绑定的可变JSON对象
     * @param name name
     * @return 可变JSON对象
     */
    public JsonMutObject createJsonMutObject(String name) {
        return super.createJsonMutObject(name, address);
    }

    /**
     * 创建一个自动绑定的可变JSON数组
     * @param name name
     * @return 可变JSON数组
     */
    public JsonMutArr createJsonMutArr(String name) {
        return super.createJsonMutArr(name, address);
    }

    /**
     * 绑定一个对象或数组
     * @param name 名称
     * @param obj 对象或数组
     * @return this
     */
    public JsonMutObject bind(String name, JsonMut obj) {
        synchronized (JsonMutObject.class) {
            long objAddress = JsonMut.getObjAddress(obj);
            _bind(address, objAddress, name);
            return this;
        }
    }

    public JsonMutObject bind(String name, JsonType obj) {
        synchronized (JsonMutObject.class) {
            long objAddress = JsonMut.getObjAddress(obj);
            _bind(address, objAddress, name);
            return this;
        }
    }
}
