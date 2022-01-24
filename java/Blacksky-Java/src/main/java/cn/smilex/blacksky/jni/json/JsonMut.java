package cn.smilex.blacksky.jni.json;

/**
 * @author smilex
 */
public class JsonMut {

    private long address;

    public enum JSON_MUT_TYPE {
        OBJ(0),
        ARR(1);

        int type;
        JSON_MUT_TYPE(int type) {
            this.type = type;
        }
    }

    public JsonMut(int type) {
        synchronized (JsonMut.class) {
            create(type);
        }
    }

    public JsonMut(long address) {
        this.address = address;
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
            address = _createMut(type);
        }
    }

    /**
     * 获取JSON字符串
     * @return JSON字符串
     */
    public String getJsonStr() {
        if (address == 0) {
            return null;
        }
        return _writeString();
    }

    /**
     * 向obj添加一个String类型数据
     * @param name name
     * @param data 数据
     * @return this
     */
    public JsonMut addStr(String name, String data) {
        synchronized (JsonMut.class) {
            _objAdd(Json.Json_Type.STRING.id, address, name, data);
            return this;
        }
    }

    /**
     * 向obj添加一个int类型数据
     * @param name name
     * @param data 数据
     * @return this
     */
    public JsonMut addInt(String name, int data) {
        synchronized (JsonMut.class) {
            _objAdd(Json.Json_Type.INTEGER.id, address, name, data);
            return this;
        }
    }

    /**
     * 向obj添加一个double类型数据
     * @param name name
     * @param data 数据
     * @return this
     */
    public JsonMut addDouble(String name, double data) {
        synchronized (JsonMut.class) {
            _objAdd(Json.Json_Type.DOUBLE.id, address, name, data);
            return this;
        }
    }

    /**
     * 向obj添加一个long类型数据
     * @param name name
     * @param data 数据
     * @return this
     */
    public JsonMut addLong(String name, long data) {
        synchronized (JsonMut.class) {
            _objAdd(Json.Json_Type.LONG.id, address, name, data);
            return this;
        }
    }

    /**
     * 向obj添加一个boolean类型数据
     * @param name name
     * @param data 数据
     * @return this
     */
    public JsonMut addBoolean(String name, boolean data) {
        synchronized (JsonMut.class) {
            _objAdd(Json.Json_Type.BOOLEAN.id, address, name, data);
            return this;
        }
    }

    /**
     * 向obj创建并添加一个obj类型数据
     * @param name name
     * @return new obj
     */
    public JsonMut createObject(String name) {
        synchronized (JsonMut.class) {
            return new JsonMut(_add(address, JSON_MUT_TYPE.OBJ.type, name));
        }
    }

    /**
     * 创建并添加一个arr类型数据
     * @param name name
     * @return new obj
     */
    public JsonMut createArr(String name) {
        synchronized (JsonMut.class) {
            return new JsonMut(_add(address, JSON_MUT_TYPE.ARR.type, name));
        }
    }

    /**
     * 向arr添加一个int类型数据
     * @param data 数据
     * @return this
     */
    public JsonMut addArrStr(String data) {
        synchronized (JsonMut.class) {
            _arrAdd(Json.Json_Type.STRING.id, address, data);
            return this;
        }
    }

    /**
     * 向arr添加一个int类型数据
     * @param data 数据
     * @return this
     */
    public JsonMut addArrInt(int data) {
        synchronized (JsonMut.class) {
            _arrAdd(Json.Json_Type.INTEGER.id, address, data);
            return this;
        }
    }

    /**
     * 向arr添加一个double类型数据
     * @param data 数据
     * @return this
     */
    public JsonMut addArrDouble(double data) {
        synchronized (JsonMut.class) {
            _arrAdd(Json.Json_Type.DOUBLE.id, address, data);
            return this;
        }
    }

    /**
     * 向arr添加一个long类型数据
     * @param data 数据
     * @return this
     */
    public JsonMut addArrLong(long data) {
        synchronized (JsonMut.class) {
            _arrAdd(Json.Json_Type.LONG.id, address, data);
            return this;
        }
    }

    /**
     * 向arr添加一个boolean类型数据
     * @param data 数据
     * @return this
     */
    public JsonMut addArrBoolean(boolean data) {
        synchronized (JsonMut.class) {
            _arrAdd(Json.Json_Type.BOOLEAN.id, address, data);
            return this;
        }
    }

    /**
     * 释放内存
     */
    public void close() {
        synchronized (JsonMut.class) {
            _closeMut();
            this.address = 0;
        }
    }

    private native long _createMut(int type);
    private native void _closeMut();
    private native long _add(long address, int type, String name);
    private native void _objAdd(int type, long address, String name, Object data);
    private native void _arrAdd(int type, long address, Object data);
    private native String _writeString();
}
