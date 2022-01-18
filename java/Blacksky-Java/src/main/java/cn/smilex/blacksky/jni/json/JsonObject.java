package cn.smilex.blacksky.jni.json;

import java.util.List;

/**
 * @author smilex
 */
public class JsonObject extends Json {

    private long address;
    private long pointerAddress;

    public JsonObject(long address) {
        synchronized (JsonObject.class) {
            this.address = address;
        }
    }

    @Override
    public JsonObject getPoint(String point) {
        synchronized (JsonObject.class) {
            pointerAddress = super._getPoint(address, point);
            return this;
        }
    }

    public JsonObject getPointerObject(String name) {
        synchronized (JsonObject.class) {
            String _address = (String) _get(Json_Type.OBJECT.id, name, JSON_GET_METHOD.NAME_ADDRESS.id, address);
            pointerAddress = Long.parseLong(_address);
            return this;
        }
    }

    public String asPointerString() {
        return super.asString(pointerAddress);
    }

    public int asPointerInt() {
        return super.asInt(pointerAddress);
    }

    public double asPointerDouble() {
        return super.asDouble(pointerAddress);
    }

    public long asPointerLong() {
        return super.asLong(pointerAddress);
    }

    @Override
    public boolean asPointerBoolean() {
        return super.asBoolean(pointerAddress);
    }

    public String getPointString(String name) {
        return super.getString(name, pointerAddress);
    }

    @Override
    public String getString(String name) {
        return super.getString(name, address);
    }

    public String asString() {
        return super.asString(address);
    }

    @Override
    public int getInt(String name) {
        return super.getInt(name, address);
    }

    public int asInt() {
        return super.asInt(address);
    }

    @Override
    public double getDouble(String name) {
        return super.getDouble(name, address);
    }

    public double asDouble() {
        return super.asDouble(address);
    }

    @Override
    public long getLong(String name) {
        return super.getLong(name, address);
    }

    public long asLong() {
        return super.asLong(address);
    }

    @Override
    public boolean getBoolean(String name) {
        return super.getBoolean(name, address);
    }

    public boolean asBoolean() {
        return super.asBoolean(address);
    }

    @Override
    public JsonObject getObject(String name) {
        return super.getObject(name, address);
    }

    @Override
    public List<JsonObject> getArrJsonObject(String name) {
        return super.getArrJsonObject(name, address);
    }
}
