package cn.smilex.blacksky.jni.json;

import cn.smilex.blacksky.jni.Info;

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
     * @return 可变JSON对象
     */
    public JsonMutObject createJsonMutObject(String name) {
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
     * @return 可变JSON数组
     */
    public JsonMutArr createJsonMutArr(String name) {
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
     * 用于数组绑定数据
     * @param obj 数据
     * @return this
     */
    public JsonMut bind(JsonMut obj) {
        synchronized (JsonMut.class) {
            long objAddress = JsonMut.getObjAddress(obj);
            _bind(address, objAddress, null);
            return this;
        }
    }

    /**
     * 用于对象绑定属性
     * @param obj 数据
     * @param name 名称
     * @return this
     */
    public JsonMut bind(JsonMut obj, String name) {
        synchronized (JsonMut.class) {
            long objAddress = JsonMut.getObjAddress(obj);
            _bind(address, objAddress, name);
            return this;
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
    protected native void _objAdd(int type, long address, String name, Object data);
    protected native void _arrAdd(int type, long address, Object data);
    protected native void _bind(long rootAddress, long address, String name);
    protected native String _writeString(long address);
}
