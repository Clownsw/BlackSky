package cn.smilex.blacksky.jni.json;

import cn.smilex.blacksky.jni.Info;
import cn.smilex.blacksky.jni.json.type.*;
import cn.smilex.blacksky.jni.util.BlackSkyUtil;

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

    private JsonMut(String str) {
        if (str == null || str.isBlank()) {
            throw new RuntimeException("");
        }

        createByJsonStr(str);

        String type = getType(address);
        if (type.equals("object")) {
            root = new JsonMutObject(address);
        } else if (type.equals("array")) {
            root = new JsonMutArr(address);
        }
    }

    private JsonMut(int type) {
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

    public static JsonMut buildByJsonStr(String str) {
        return new JsonMut(str);
    }

    private enum JSON_MUT_TYPE {
        OBJ(0),
        ARR(1);

        int type;
        JSON_MUT_TYPE(int type) {
            this.type = type;
        }
    }

    private enum JSON_MUT_ARR_ACTION {
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

    private enum JSON_MUT_OBJ_ACTION {
        JSON_MUT_OBJ_ACTION_REMOVE(0),
        JSON_MUT_OBJ_ACTION_CLEAN(1),
        JSON_MUT_OBJ_ACTION_REPLACE(2);

        int id;

        JSON_MUT_OBJ_ACTION(int id) {
            this.id = id;
        }
    }

    private void _parse(String s) {
        String a = s.substring(0, s.indexOf("+"));
        _address = Long.parseLong(a);
        String b = s.substring(a.length() + 1);
        address = Long.parseLong(b);
    }

    /**
     * ????????????????????????JSON
     * @param type JSON_MUT_TYPE
     */
    private void create(int type) {
        synchronized (JsonMut.class) {
            if (address > 0) {
                close();
            }
            _parse(_createMut(type, null));
        }
    }

    /**
     * ??????JSON?????????????????????JSON
     * @param str JSON?????????
     */
    private void createByJsonStr(String str) {
        synchronized (JsonMut.class) {
            if (address > 0) {
                close();
            }
            _parse(_createMut(-1, str));
        }
    }

    /**
     * ????????????JsonMutObject
     * @return JsonMutObject
     */
    private JsonMutObject buildJsonMutObject() {
        synchronized (JsonMut.class) {
            create(JSON_MUT_TYPE.OBJ.type);
            return new JsonMutObject(address);
        }
    }

    /**
     * ????????????JsonMutArr
     * @return JsonMutArr
     */
    private JsonMutArr buildJsonMutArr() {
        synchronized (JsonMut.class) {
            create(JSON_MUT_TYPE.ARR.type);
            return new JsonMutArr(address);
        }
    }

    /**
     * ??????JSON?????????
     * @return JSON?????????
     */
    public final String getJsonStr() {
        if (_address == 0) {
            return null;
        }
        return _writeString(_address);
    }

    public static String getType(JsonMutInter obj) {
        synchronized (JsonMut.class) {
            long x = getObjAddress(obj);
            return getType(x);
        }
    }

    private static String getType(long x) {
        return BlackSkyUtil.getType(_getType(x));
    }

    /**
     * ?????????????????????????????????JSON??????
     * @param name name
     * @param address address
     * @return ??????JSON??????
     */
    private JsonMutObject _createJsonMutObject(String name, long address) {
        synchronized (JsonMut.class) {
            return new JsonMutObject(_add(_address, address, JSON_MUT_TYPE.OBJ.type, name, true));
        }
    }

    /**
     * ??????????????????????????????JSON??????
     * @param name name
     * @return ??????JSON??????
     */
    public JsonMutObject createFreeJsonMutObject(String name) {
        synchronized (JsonMut.class) {
            return new JsonMutObject(_add(_address, address, JSON_MUT_TYPE.OBJ.type, name, false));
        }
    }

    /**
     * ?????????????????????????????????JSON??????
     * @param name name
     * @param address address
     * @return ??????JSON??????
     */
    private JsonMutArr _createJsonMutArr(String name, long address) {
        synchronized (JsonMut.class) {
            return new JsonMutArr(_add(_address, address, JSON_MUT_TYPE.ARR.type, name, true));
        }
    }

    /**
     * ??????????????????????????????JSON??????
     * @return ??????JSON??????
     */
    public JsonMutArr createFreeJsonMutArr(String name) {
        synchronized (JsonMut.class) {
            return new JsonMutArr(_add(_address, address, JSON_MUT_TYPE.ARR.type, name, false));
        }
    }

    /**
     * ????????????jsonTypeStr
     * @param value value
     * @return jsonTypeStr
     */
    public JsonTypeStr createJsonTypeStr(String value) {
        return (JsonTypeStr) createType(Json.Json_Type.STRING, value);
    }

    /**
     * ????????????jsonTypeInt
     * @param value value
     * @return jsonTypeInt
     */
    public JsonTypeInt createJsonTypeInt(int value) {
        return (JsonTypeInt) createType(Json.Json_Type.INTEGER, value);
    }

    /**
     * ????????????jsonTypeLong
     * @param value value
     * @return jsonTypeLong
     */
    public JsonTypeLong createJsonTypeLong(long value) {
        return (JsonTypeLong) createType(Json.Json_Type.LONG, value);
    }

    /**
     * ????????????jsonTypeDouble
     * @param value value
     * @return jsonTypeDouble
     */
    public JsonTypeDouble createJsonTypeDouble(double value) {
        return (JsonTypeDouble) createType(Json.Json_Type.DOUBLE, value);
    }

    /**
     * ????????????jsonTypeBoolean
     * @param value value
     * @return jsonTypeBoolean
     */
    public JsonTypeBoolean createJsonTypeBoolean(boolean value) {
        return (JsonTypeBoolean) createType(Json.Json_Type.BOOLEAN, value);
    }

    /**
     * ??????JSONType
     * @param type ??????
     * @param value ???
     * @return JSONType
     */
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

    public JsonMutObject jsonToMutJson(long addr) {
        synchronized (JsonMutObject.class) {
            return new JsonMutObject(_finalToMut(_address, addr));
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
     * ????????????
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
         * ????????????????????????????????????
         * @param name key
         * @param data value
         * @return this
         */
        public JsonMutObject addStr(String name, String data) {
            synchronized (JsonMutObject.class) {
                _objAdd(Json.Json_Type.STRING.id, _address, address, name, data);
                return this;
            }
        }

        /**
         * ?????????????????????????????????
         * @param name key
         * @param data value
         * @return this
         */
        public JsonMutObject addInt(String name, int data) {
            synchronized (JsonMutObject.class) {
                _objAdd(Json.Json_Type.INTEGER.id, _address, address, name, data);
                return this;
            }
        }

        /**
         * ?????????????????????????????????
         * @param name key
         * @param data value
         * @return this
         */
        public JsonMutObject addDouble(String name, double data) {
            synchronized (JsonMutObject.class) {
                _objAdd(Json.Json_Type.DOUBLE.id, _address, address, name, data);
                return this;
            }
        }

        /**
         * ????????????????????????????????????
         * @param name key
         * @param data value
         * @return this
         */
        public JsonMutObject addLong(String name, long data) {
            synchronized (JsonMutObject.class) {
                _objAdd(Json.Json_Type.LONG.id, _address, address, name, data);
                return this;
            }
        }

        /**
         * ?????????????????????????????????
         * @param name key
         * @param data value
         * @return this
         */
        public JsonMutObject addBoolean(String name, boolean data) {
            synchronized (JsonMutObject.class) {
                _objAdd(Json.Json_Type.BOOLEAN.id, _address, address, name, data);
                return this;
            }
        }

        /**
         * ?????????????????????????????????JSON??????
         * @param name name
         * @return ??????JSON??????
         */
        public JsonMutObject createJsonMutObject(String name) {
            return _createJsonMutObject(name, address);
        }

        /**
         * ?????????????????????????????????JSON??????
         * @param name name
         * @return ??????JSON??????
         */
        public JsonMutArr createJsonMutArr(String name) {
            return _createJsonMutArr(name, address);
        }

        /**
         * ???????????????????????????
         * @param name ??????
         * @param obj ???????????????
         * @return this
         */
        public JsonMutObject bind(String name, JsonMutInter obj) {
            synchronized (JsonMutObject.class) {
                long objAddress = JsonMut.getObjAddress(obj);
                _bind(_address, address, objAddress, name);
                return this;
            }
        }

        /**
         * ????????????JSONType
         * @param name ??????
         * @param obj JSONType
         * @return this
         */
        public JsonMutObject bind(String name, JsonType obj) {
            synchronized (JsonMutObject.class) {
                long objAddress = JsonMut.getObjAddress(obj);
                _bind(_address, address, objAddress, name);
                return this;
            }
        }

        /**
         * ???????????????????????????
         * @param key key
         * @return ????????????
         */
        public boolean removeObj(String key) {
            synchronized (JsonMutObject.class) {
                return _objAction(JSON_MUT_OBJ_ACTION.JSON_MUT_OBJ_ACTION_REMOVE.id, 0, address, 0, key);
            }
        }

        /**
         * ???????????????????????????
         * @return ????????????
         */
        public boolean cleanObj() {
            synchronized (JsonMutObject.class) {
                return _objAction(JSON_MUT_OBJ_ACTION.JSON_MUT_OBJ_ACTION_CLEAN.id,0, address, 0,null);
            }
        }

        /**
         * ???????????????????????????
         * @param key key
         * @param data ??????
         * @return this
         */
        private boolean _replaceObj(String key, long data) {
            synchronized (JsonMutObject.class) {
                if (data == 0 || key.isBlank()) {
                    return false;
                }
                return _objAction(JSON_MUT_OBJ_ACTION.JSON_MUT_OBJ_ACTION_REPLACE.id, _address, address, data, key);
            }
        }

        /**
         * ???????????????????????????
         * @param key key
         * @param data ??????
         * @return this
         */
        public boolean replaceObj(String key, JsonType data) {
            return _replaceObj(key, getObjAddress(data));
        }

        /**
         * ???????????????????????????
         * @param key key
         * @param data ??????
         * @return this
         */
        public boolean replaceObj(String key, JsonMutInter data) {
            return _replaceObj(key, getObjAddress(data));
        }

        public JsonMutObject getPointerJsonMutObject(String pointer) {
            synchronized (JsonMutObject.class) {
                long ret = _getPointer(address, pointer);
                if (ret == 0 || !getType(ret).equals("object")) {
                    return null;
                }
                return new JsonMutObject(ret);
            }
        }

        public JsonMutArr getPointerJsonMutArr(String pointer) {
            synchronized (JsonMutArr.class) {
                long ret = _getPointer(address, pointer);
                if (ret == 0 || !getType(ret).equals("array")) {
                    return null;
                }
                return new JsonMutArr(ret);
            }
        }

        public boolean isNone() {
            return getType(address).equals("none");
        }

        public boolean isNull() {
            return getType(address).equals("null");
        }

        public boolean isBool() {
            return getType(address).equals("boolean");
        }

        public boolean isNumber() {
            return getType(address).equals("number");
        }

        public boolean isString() {
            return getType(address).equals("string");
        }

        public boolean isArray() {
            return getType(address).equals("array");
        }

        public boolean isObject() {
            return getType(address).equals("object");
        }

    }

    public class JsonMutArr implements JsonMutInter {
        private final long address;

        public JsonMutArr(long address) {
            this.address = address;
        }

        /**
         * ????????????????????????????????????
         * @param data ??????
         * @return this
         */
        public JsonMutArr addArrStr(String data) {
            synchronized (JsonMutArr.class) {
                _arrAdd(Json.Json_Type.STRING.id, _address, address, data);
                return this;
            }
        }

        /**
         * ?????????????????????????????????
         * @param data ??????
         * @return this
         */
        public JsonMutArr addArrInt(int data) {
            synchronized (JsonMutArr.class) {
                _arrAdd(Json.Json_Type.INTEGER.id, _address, address, data);
                return this;
            }
        }

        /**
         * ?????????????????????????????????
         * @param data ??????
         * @return this
         */
        public JsonMutArr addArrDouble(double data) {
            synchronized (JsonMutArr.class) {
                _arrAdd(Json.Json_Type.DOUBLE.id, _address, address, data);
                return this;
            }
        }

        /**
         * ????????????????????????????????????
         * @param data ??????
         * @return this
         */
        public JsonMutArr addArrLong(long data) {
            synchronized (JsonMutArr.class) {
                _arrAdd(Json.Json_Type.LONG.id, _address, address, data);
                return this;
            }
        }

        /**
         * ?????????????????????????????????
         * @param data ??????
         * @return this
         */
        public JsonMutArr addArrBoolean(boolean data) {
            synchronized (JsonMutArr.class) {
                _arrAdd(Json.Json_Type.BOOLEAN.id, _address, address, data);
                return this;
            }
        }

        /**
         * ?????????????????????????????????JSON??????
         * @param name name
         * @return ??????JSON??????
         */
        public JsonMutObject createJsonMutObject(String name) {
            return _createJsonMutObject(name, address);
        }

        /**
         * ?????????????????????????????????JSON??????
         * @param name name
         * @return ??????JSON??????
         */
        public JsonMutArr createJsonMutArr(String name) {
            return _createJsonMutArr(name, address);
        }

        /**
         * ???????????????????????????
         * @param obj ???????????????
         * @return this
         */
        public JsonMutArr bind(JsonMutInter obj) {
            synchronized (JsonMutArr.class) {
                long objAddress = JsonMut.getObjAddress(obj);
                _bind(_address, address, objAddress, null);
                return this;
            }
        }

        /**
         * ????????????JSONType
         * @param obj JSONType
         * @return this
         */
        public JsonMutArr bind(JsonType obj) {
            synchronized (JsonMutArr.class) {
                long objAddress = JsonMut.getObjAddress(obj);
                _bind(_address, address, objAddress, null);
                return this;
            }
        }

        /**
         * ??????????????????????????????????????????
         * @param data ??????
         * @param index ??????
         * @return ????????????
         */
        public boolean insertArr(JsonMutInter data, int index) {
            synchronized (JsonMutArr.class) {
                if (data != null) {
                    long objAddress = JsonMut.getObjAddress(data);

                    return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_INSERT.id, address, objAddress, index, 0);
                }
                return false;
            }
        }

        /**
         * ????????????????????????????????????
         * @param data ??????
         * @return ????????????
         */
        public boolean appendArr(JsonMutInter data) {
            synchronized (JsonMutArr.class) {

                if (data != null) {
                    long objAddress = JsonMut.getObjAddress(data);

                    return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_APPEND.id, address, objAddress, 0, 0);
                }
                return false;
            }
        }

        /**
         * ????????????????????????????????????
         * @param data ??????
         * @return ????????????
         */
        public boolean prependArr(JsonMutInter data) {
            synchronized (JsonMutArr.class) {

                if (data != null) {
                    long objAddress = JsonMut.getObjAddress(data);

                    return objAddress > 0
                            &&
                            _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_PREPEND.id, address, objAddress, 0, 0);
                }
                return false;
            }
        }

        /**
         * ????????????????????????????????????
         * @param data ??????
         * @param index ??????
         * @return ????????????
         */
        public boolean replaceArr(JsonType data, int index) {
            synchronized (JsonMutArr.class) {
                if (data != null) {
                    long objAddress = JsonMut.getObjAddress(data);
                    return objAddress > 0
                            &&
                            _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_REPLACE.id, address, objAddress, index, 0);
                }
                return false;
            }
        }

        /**
         * ????????????????????????????????????
         * @param data ??????
         * @param index ??????
         * @return ????????????
         */
        public boolean replaceArr(JsonMutInter data, int index) {
            synchronized (JsonMutArr.class) {
                if (data != null) {
                    long objAddress = JsonMut.getObjAddress(data);
                    return objAddress > 0
                            &&
                            _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_REPLACE.id, address, objAddress, index, 0);
                }
                return false;
            }
        }

        /**
         * ??????????????????????????????
         * @param index ??????
         * @return ????????????
         */
        public boolean removeArr(int index) {
            synchronized (JsonMutArr.class) {
                return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_REMOVE.id, address, 0, index, 0);
            }
        }

        /**
         * ??????????????????????????????
         * @return ????????????
         */
        public boolean removeArrFirst() {
            synchronized (JsonMutArr.class) {
                return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_REMOVE_FIRST.id, address, 0, 0, 0);
            }
        }

        /**
         * ?????????????????????????????????
         * @return ????????????
         */
        public boolean removeArrLast() {
            synchronized (JsonMutArr.class) {
                return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_REMOVE_LAST.id, address, 0, 0, 0);
            }
        }

        /**
         * ???????????????????????????
         * @param index ????????????
         * @param len ??????
         * @return ????????????
         */
        public boolean removeArrRange(int index, int len) {
            synchronized (JsonMutArr.class) {
                return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_REMOVE_RANGE.id, address, 0, index, len);
            }
        }

        /**
         * ???????????????????????????
         * @return ????????????
         */
        public boolean cleanArr() {
            synchronized (JsonMutArr.class) {
                return _arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_CLEAN.id, address, 0, 0, 0);
            }
        }

        public boolean isNone() {
            return getType(address).equals("none");
        }

        public boolean isNull() {
            return getType(address).equals("null");
        }

        public boolean isBool() {
            return getType(address).equals("boolean");
        }

        public boolean isNumber() {
            return getType(address).equals("number");
        }

        public boolean isString() {
            return getType(address).equals("string");
        }

        public boolean isArray() {
            return getType(address).equals("array");
        }

        public boolean isObject() {
            return getType(address).equals("object");
        }
    }

    private native String _createMut(int type, String str);
    private native void _closeMut(long address);
    private native long _add(long _address, long address, int type, String name, boolean isBind);
    private native long _createType(long address, int type, Object value);
    private native void _objAdd(int type, long _address, long address, String name, Object data);
    private native void _arrAdd(int type, long _address, long address, Object data);
    private native boolean _arrAction(int type, long arr, long data, int index, int len);
    private native boolean _objAction(int type, long _address, long obj, long obj2, String key);
    private native void _bind(long _address, long rootAddress, long address, String name);
    private native String _writeString(long address);
    private native long _getPointer(long address, String pointer);
    private native long _finalToMut(long _address, long address);
    private static native int _getType(long address);
}
