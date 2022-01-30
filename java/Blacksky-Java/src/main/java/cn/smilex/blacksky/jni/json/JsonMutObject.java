package cn.smilex.blacksky.jni.json;

import cn.smilex.blacksky.jni.Info;

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
     * 绑定一个对象或数组
     * @param obj 对象或数组
     * @param name 名称
     * @return this
     */
    @Override
    public JsonMutObject bind(JsonMut obj, String name) {
        synchronized (JsonMutObject.class) {
            long objAddress = JsonMut.getObjAddress(obj);
            _bind(address, objAddress, name);
            return this;
        }
    }
}
