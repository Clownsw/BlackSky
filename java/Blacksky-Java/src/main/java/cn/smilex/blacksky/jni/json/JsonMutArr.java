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
     * @param obj 对象或数组
     * @return this
     */
    public JsonMutArr bind(JsonMut obj) {
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
    public boolean insertArr(JsonMut data, int index) {
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
                return super._arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_INSERT.id, address, objAddress, index, 0);
            }
            return false;
        }
    }

    /**
     * 在数组的尾部插入一个内容
     * @param data 内容
     * @return 是否成功
     */
    public boolean appendArr(JsonMut data) {
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
                return super._arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_APPEND.id, address, objAddress, 0, 0);
            }
            return false;
        }
    }

    /**
     * 在数组的头部插入一个内容
     * @param data 内容
     * @return 是否成功
     */
    public boolean prependArr(JsonMut data) {
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
                return super._arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_PREPEND.id, address, objAddress, 0, 0);
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
            return super._arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_REMOVE.id, address, 0, index, 0);
        }
    }

    /**
     * 删除数组内第一个内容
     * @return 是否成功
     */
    public boolean removeArrFirst() {
        synchronized (JsonMutArr.class) {
            return super._arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_REMOVE_FIRST.id, address, 0, 0, 0);
        }
    }

    /**
     * 删除数组内最后一个内容
     * @return 是否成功
     */
    public boolean removeArrLast() {
        synchronized (JsonMutArr.class) {
            return super._arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_REMOVE_LAST.id, address, 0, 0, 0);
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
            return super._arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_REMOVE_RANGE.id, address, 0, index, len);
        }
    }

    /**
     * 清空数组内所有内容
     * @return 是否成功
     */
    public boolean cleanArr() {
        synchronized (JsonMutArr.class) {
            return super._arrAction(JSON_MUT_ARR_ACTION.JSON_MUT_ARR_ACTION_CLEAN.id, address, 0, 0, 0);
        }
    }
}
