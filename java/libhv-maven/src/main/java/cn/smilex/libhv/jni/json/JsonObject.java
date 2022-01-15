package cn.smilex.libhv.jni.json;

import java.util.List;

/**
 * @author smilex
 */
public class JsonObject extends Json {

    private long address;

    public long getAddress() {
        return address;
    }

    public JsonObject(long address) {
        this.address = address;
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
    public JsonObject getObject(String name) {
        return super.getObject(name, address);
    }

    @Override
    public List<JsonObject> getArrJsonObject(String name) {
        return super.getArrJsonObject(name, address);
    }

    public void setAddress(long address) {
        this.address = address;
    }
}
