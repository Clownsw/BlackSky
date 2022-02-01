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

    private JsonMut() {
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

    public enum JSON_MUT_ARR_ACTION {
        JSON_MUT_ARR_ACTION_INSERT(0),
        JSON_MUT_ARR_ACTION_APPEND(1),
        JSON_MUT_ARR_ACTION_PREPEND(2),
        JSON_MUT_ARR_ACTION_REMOVE(3),
        JSON_MUT_ARR_ACTION_REPLACE(4),
        JSON_MUT_ARR_ACTION_REMOVE_FIRST(5),
        JSON_MUT_ARR_ACTION_REMOVE_LAST(6),
        JSON_MUT_ARR_ACTION_REMOVE_RANGE(7),
        JSON_MUT_ARR_ACTION_CLEAN(8);

        int id;

        JSON_MUT_ARR_ACTION(int id) {
            this.id = id;
        }
    }

    /**
     * 创建一个空JSON
     * @param type JSON_MUT_TYPE
     */
    private void create(int type) {
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
    private JsonMutObject _createJsonMutObject(String name, long address) {
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
    private JsonMutArr _createJsonMutArr(String name, long address) {
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

    /**
     * 创建一个jsonTypeInt
     * @param value value
     * @return jsonTypeInt
     */
    public JsonTypeInt createJsonTypeInt(int value) {
        return (JsonTypeInt) createType(Json.Json_Type.INTEGER, value);
    }

    /**
     * 创建一个jsonTypeLong
     * @param value value
     * @return jsonTypeLong
     */
    public JsonTypeLong createJsonTypeLong(long value) {
        return (JsonTypeLong) createType(Json.Json_Type.LONG, value);
    }

    /**
     * 创建一个jsonTypeDouble
     * @param value value
     * @return jsonTypeDouble
     */
    public JsonTypeDouble createJsonTypeDouble(double value) {
        return (JsonTypeDouble) createType(Json.Json_Type.DOUBLE, value);
    }

    /**
     * 创建一个jsonTypeBoolean
     * @param value value
     * @return jsonTypeBoolean
     */
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

    private static long getObjAddress(Object obj) {
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

    public class JsonMutObject implements JsonMutInter {
        private final long address;

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
            return _createJsonMutObject(name, address);
        }

        /**
         * 创建一个自动绑定的可变JSON数组
         * @param name name
         * @return 可变JSON数组
         */
        public JsonMutArr createJsonMutArr(String name) {
            return _createJsonMutArr(name, address);
        }

        /**
         * 绑定一个对象或数组
         * @param name 名称
         * @param obj 对象或数组
         * @return this
         */
        public JsonMutObject bind(String name, JsonMutInter obj) {
            synchronized (JsonMutObject.class) {
                long objAddress = JsonMut.getObjAddress(obj);
                _bind(address, objAddress, name);
                return this;
            }
        }

        /**
         * 绑定一个JSONType
         * @param name 名称
         * @param obj JSONType
         * @return this
         */
        public JsonMutObject bind(String name, JsonType obj) {
            synchronized (JsonMutObject.class) {
                long objAddress = JsonMut.getObjAddress(obj);
                _bind(address, objAddress, name);
                return this;
            }
        }
    }

    public class JsonMutArr implements JsonMutInter {
        private final long address;

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
         * 创建一个自动绑定的可变JSON对象
         * @param name name
         * @return 可变JSON对象
         */
        public JsonMutObject createJsonMutObject(String name) {
            return _createJsonMutObject(name, address);
        }

        /**
         * 创建一个自动绑定的可变JSON数组
         * @param name name
         * @return 可变JSON数组
         */
        public JsonMutArr createJsonMutArr(String name) {
            return _createJsonMutArr(name, address);
        }

        /**
         * 绑定一个对象或数组
         * @param obj 对象或数组
         * @return this
         */
        public JsonMutArr bind(JsonMutInter obj) {
            synchronized (JsonMutArr.class) {
                long objAddress = JsonMut.getObjAddress(obj);
                _bind(address, objAddress, null);
                return this;
            }
        }

        /**
         * 绑定一个JSONType
         * @param obj JSONType
         * @return this
         */
        public JsonMutArr bind(JsonType obj) {
            synchronized (JsonMutArr.class) {
                long objAddress = JsonMut.getObjAddress(obj);
                _bind(address, objAddress, null);
                return this;
            }
        }

        /**
         * 在数组的指定位置插入一个内容
         * @param data 内容
         * @param index 位置
         * @return 是否成功
         */
        public boolean insertArr(JsonMutInter data, int index) {
            synchronized (JsonMutArr.class) {
                if (data != null) {
                    long objAddress;

                    if (data instanceof JsonMutObject) {
                        var _data = (JsonMutObject) data;
                        objAddress = JsonMut.getObjAddress(_data);
                    } else {
                        var _data = (JsonMutArr) data;
                        objAddress = JsonMut.getObjAddress(_data);
                    }
                    return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_INSERT.id, address, objAddress, index, 0);
                }
                return false;
            }
        }

        /**
         * 在数组的尾部插入一个内容
         * @param data 内容
         * @return 是否成功
         */
        public boolean appendArr(JsonMutInter data) {
            synchronized (JsonMutArr.class) {

                if (data != null) {
                    long objAddress;

                    if (data instanceof JsonMutObject) {
                        var _data = (JsonMutObject) data;
                        objAddress = JsonMut.getObjAddress(_data);
                    } else {
                        var _data = (JsonMutArr) data;
                        objAddress = JsonMut.getObjAddress(_data);
                    }
                    return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_APPEND.id, address, objAddress, 0, 0);
                }
                return false;
            }
        }

        /**
         * 在数组的头部插入一个内容
         * @param data 内容
         * @return 是否成功
         */
        public boolean prependArr(JsonMutInter data) {
            synchronized (JsonMutArr.class) {

                if (data != null) {
                    long objAddress;

                    if (data instanceof JsonMutObject) {
                        var _data = (JsonMutObject) data;
                        objAddress = JsonMut.getObjAddress(_data);
                    } else {
                        var _data = (JsonMutArr) data;
                        objAddress = JsonMut.getObjAddress(_data);
                    }
                    return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_PREPEND.id, address, objAddress, 0, 0);
                }
                return false;
            }
        }

        /**
         * 删除数组指定位置内容
         * @param index 位置
         * @return 是否成功
         */
        public boolean removeArr(int index) {
            synchronized (JsonMutArr.class) {
                return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_REMOVE.id, address, 0, index, 0);
            }
        }

        /**
         * 删除数组内第一个内容
         * @return 是否成功
         */
        public boolean removeArrFirst() {
            synchronized (JsonMutArr.class) {
                return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_REMOVE_FIRST.id, address, 0, 0, 0);
            }
        }

        /**
         * 删除数组内最后一个内容
         * @return 是否成功
         */
        public boolean removeArrLast() {
            synchronized (JsonMutArr.class) {
                return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_REMOVE_LAST.id, address, 0, 0, 0);
            }
        }

        /**
         * 删除数组内范围内容
         * @param index 起始位置
         * @param len 长度
         * @return 是否成功
         */
        public boolean removeArrRange(int index, int len) {
            synchronized (JsonMutArr.class) {
                return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_REMOVE_RANGE.id, address, 0, index, len);
            }
        }

        /**
         * 清空数组内所有内容
         * @return 是否成功
         */
        public boolean cleanArr() {
            synchronized (JsonMutArr.class) {
                return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_CLEAN.id, address, 0, 0, 0);
            }
        }
    }

    private native String _createMut(int type);
    private native void _closeMut(long address);
    private native long _add(long address, int type, String name, boolean isBind);
    private native long _createType(long address, int type, Object value);
    private native void _objAdd(int type, long address, String name, Object data);
    private native void _arrAdd(int type, long address, Object data);
    private native boolean _arrAction(int type, long arr, long data, int index, int len);
    private native void _bind(long rootAddress, long address, String name);
    private native String _writeString(long address);
}
