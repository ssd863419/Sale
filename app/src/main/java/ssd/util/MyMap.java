package ssd.util;

import java.util.HashMap;

/**
 * Created by 0_o on 2014/11/29.
 */
public class MyMap extends HashMap {


    // 字串
    public String getString(String key) {
        return (String) this.get(key);
    }

    // 整數 short 2 / int 4 / long 8
    public short getShort(String key) {
        return (Short) this.get(key);
    }

    public int getInt(String key) {
        return (Integer) this.get(key);
    }

    public long getLong(String key) {
        return (Long) this.get(key);
    }

    // 浮點數 float 4 / double 8
    public float getFloat(String key) {
        return (Float) this.get(key);
    }

    public double getDouble(String key) {
        return (Double) this.get(key);
    }

    // 其他
    public byte[] getBlob(String key) {
        return (byte[]) this.get(key);
    }


}
