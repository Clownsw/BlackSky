package cn.smilex.blacksky.jni.json;

import cn.smilex.blacksky.jni.Info;

/**
 * @author smilex
 */
public class JsonMutArr extends JsonMut{
    private final long address;

    static {
        synchronized (Json.class) {
            if (!Info.isInit) {
                Info.init();
            }
        }
    }

    public JsonMutArr(long address) {
        this.address = address;
    }

    /**
     * 向当前数组添加一个字符串
     * @param data 数据
     * @return this
     */
    public JsonMutArr addArrStr(String data) {
        synchronized (JsonMutArr.class) {
            _arrAdd(Json.Json_Type.STRING.id, address, data);
            return this;
        }
    }

    /**
     * 向当前数组添加一个整数
     * @param data 数据
     * @return this
     */
    public JsonMutArr addArrInt(int data) {
        synchronized (JsonMutArr.class) {
            _arrAdd(Json.Json_Type.INTEGER.id, address, data);
            return this;
        }
    }

    /**
     * 向当前数组添加一个小数
     * @param data 数据
     * @return this
     */
    public JsonMutArr addArrDouble(double data) {
        synchronized (JsonMutArr.class) {
            _arrAdd(Json.Json_Type.DOUBLE.id, address, data);
            return this;
        }
    }

    /**
     * 向当前数组添加一个长整数
     * @param data 数据
     * @return this
     */
    public JsonMutArr addArrLong(long data) {
        synchronized (JsonMutArr.class) {
            _arrAdd(Json.Json_Type.LONG.id, address, data);
            return this;
        }
    }

    /**
     * 向当前数组添加一个布尔
     * @param data 数据
     * @return this
     */
    public JsonMutArr addArrBoolean(boolean data) {
        synchronized (JsonMutArr.class) {
            _arrAdd(Json.Json_Type.BOOLEAN.id, address, data);
            return this;
        }
    }

    /**
     * 绑定一个对象或数组
     * @param obj 对象或数组
     * @return this
     */
    @Override
    public JsonMutArr bind(JsonMut obj) {
        synchronized (JsonMutArr.class) {
            long objAddress = JsonMut.getObjAddress(obj);
            _bind(address, objAddress, null);
            return this;
        }
    }
}
