package cn.smilex.libhv.jni.json;

/**
 * @author smilex
 */
public class JsonObject extends Json {

    private long address;

    public JsonObject(long address) {
        this.address = address;
    }

    @Override
    public String getString(String name) {
        return super.getString(name, address);
    }

    @Override
    public int getInt(String name) {
        return super.getInt(name, address);
    }

    @Override
    public double getDouble(String name) {
        return super.getDouble(name, address);
    }

    @Override
    public JsonObject getObject(String name) {
        return super.getObject(name, address);
    }

    public void setAddress(long address) {
        this.address = address;
    }
}
