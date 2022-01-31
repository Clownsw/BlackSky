package cn.smilex.blacksky.jni.json;

import cn.smilex.blacksky.jni.Info;
import cn.smilex.blacksky.jni.json.type.*;

import java.lang.reflect.Field;

/**
 * @author smilex
 */
public class JsonMut {

    private long _address;
    private long address;
    private Object root;

    static {
        synchronized (Json.class) {
            if (!Info.isInit) {
                Info.init();
            }
        }
    }

    protected JsonMut() {
    }

    public JsonMut(int type) {
        root = type == JSON_MUT_TYPE.OBJ.type
                ? buildJsonMutObject()
                : buildJsonMutArr();
    }

    public Object getRoot() {
        return root;
    }

    public static JsonMut buildObject() {
        return new JsonMut(JSON_MUT_TYPE.OBJ.type);
    }

    public static JsonMut buildArr() {
        return new JsonMut(JSON_MUT_TYPE.ARR.type);
    }

    public enum JSON_MUT_TYPE {
        OBJ(0),
        ARR(1);

        int type;
        JSON_MUT_TYPE(int type) {
            this.type = type;
        }
    }

    /**
     * 创建一个空JSON
     * @param type JSON_MUT_TYPE
     */
    protected void create(int type) {
        synchronized (JsonMut.class) {
            if (address > 0) {
                close();
            }
            String s = _createMut(type);
            String a = s.substring(0, s.indexOf("+"));
            _address = Long.parseLong(a);
            String b = s.substring(a.length() + 1);
            address = Long.parseLong(b);
        }
    }

    private JsonMutObject buildJsonMutObject() {
        synchronized (JsonMut.class) {
            create(JSON_MUT_TYPE.OBJ.type);
            return new JsonMutObject(address);
        }
    }

    private JsonMutArr buildJsonMutArr() {
        synchronized (JsonMut.class) {
            create(JSON_MUT_TYPE.ARR.type);
            return new JsonMutArr(address);
        }
    }

    /**
     * 获取JSON字符串
     * @return JSON字符串
     */
    public final String getJsonStr() {
        if (_address == 0) {
            return null;
        }
        return _writeString(_address);
    }

    /**
     * 创建一个自动绑定的可变JSON对象
     * @param name name
     * @param address address
     * @return 可变JSON对象
     */
    protected JsonMutObject createJsonMutObject(String name, long address) {
        synchronized (JsonMut.class) {
            return new JsonMutObject(_add(address, JSON_MUT_TYPE.OBJ.type, name, true));
        }
    }

    /**
     * 创建一个未绑定的可变JSON对象
     * @param name name
     * @return 可变JSON对象
     */
    public JsonMutObject createFreeJsonMutObject(String name) {
        synchronized (JsonMut.class) {
            return new JsonMutObject(_add(address, JSON_MUT_TYPE.OBJ.type, name, false));
        }
    }

    /**
     * 创建一个自动绑定的可变JSON数组
     * @param name name
     * @param address address
     * @return 可变JSON数组
     */
    protected JsonMutArr createJsonMutArr(String name, long address) {
        synchronized (JsonMut.class) {
            return new JsonMutArr(_add(address, JSON_MUT_TYPE.ARR.type, name, true));
        }
    }

    /**
     * 创建一个未绑定的可变JSON数组
     * @return 可变JSON数组
     */
    public JsonMutArr createFreeJsonMutArr(String name) {
        synchronized (JsonMut.class) {
            return new JsonMutArr(_add(address, JSON_MUT_TYPE.ARR.type, name, false));
        }
    }

    /**
     * 创建一个jsonTypeStr
     * @param value value
     * @return jsonTypeStr
     */
    public JsonTypeStr createJsonTypeStr(String value) {
        return (JsonTypeStr) createType(Json.Json_Type.STRING, value);
    }

    public JsonTypeInt createJsonTypeInt(int value) {
        return (JsonTypeInt) createType(Json.Json_Type.INTEGER, value);
    }

    public JsonTypeLong createJsonTypeLong(long value) {
        return (JsonTypeLong) createType(Json.Json_Type.LONG, value);
    }

    public JsonTypeDouble createJsonTypeDouble(double value) {
        return (JsonTypeDouble) createType(Json.Json_Type.DOUBLE, value);
    }

    public JsonTypeBoolean createJsonTypeBoolean(boolean value) {
        return (JsonTypeBoolean) createType(Json.Json_Type.BOOLEAN, value);
    }

    private JsonType createType(Json.Json_Type type, Object value) {
        synchronized (JsonMut.class) {
            if (value == null) {
                return null;
            }

            switch (type) {
                case STRING: {
                    return new JsonTypeStr(_createType(_address, Json.Json_Type.STRING.id, value));
                }

                case INTEGER: {
                    return new JsonTypeInt(_createType(_address, Json.Json_Type.INTEGER.id, value));
                }

                case DOUBLE: {
                    return new JsonTypeDouble(_createType(_address, Json.Json_Type.DOUBLE.id, value));
                }

                case LONG: {
                    return new JsonTypeLong(_createType(_address, Json.Json_Type.LONG.id, value));
                }

                case BOOLEAN: {
                    return new JsonTypeBoolean(_createType(_address, Json.Json_Type.BOOLEAN.id, value));
                }

                default: {
                    return null;
                }
            }
        }
    }

    protected static long getObjAddress(Object obj) {
        Class<?> aClass = obj.getClass();
        try {
            Field address = aClass.getDeclaredField("address");
            address.setAccessible(true);
            return (long) address.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 释放内存
     */
    public void close() {
        synchronized (JsonMut.class) {
            _closeMut(_address);
            this._address = 0;
            this.address = 0;
        }
    }

    protected native String _createMut(int type);
    protected native void _closeMut(long address);
    protected native long _add(long address, int type, String name, boolean isBind);
    protected native long _createType(long address, int type, Object value);
    protected native void _objAdd(int type, long address, String name, Object data);
    protected native void _arrAdd(int type, long address, Object data);
    protected native boolean _arrAction(int type, long arr, long data, int index, int len);
    protected native void _bind(long rootAddress, long address, String name);
    protected native String _writeString(long address);
}
