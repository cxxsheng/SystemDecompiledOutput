package android.filterfw.core;

/* loaded from: classes.dex */
public class KeyValueMap extends java.util.HashMap<java.lang.String, java.lang.Object> {
    public void setKeyValues(java.lang.Object... objArr) {
        if (objArr.length % 2 != 0) {
            throw new java.lang.RuntimeException("Key-Value arguments passed into setKeyValues must be an alternating list of keys and values!");
        }
        for (int i = 0; i < objArr.length; i += 2) {
            if (!(objArr[i] instanceof java.lang.String)) {
                throw new java.lang.RuntimeException("Key-value argument " + i + " must be a key of type String, but found an object of type " + objArr[i].getClass() + "!");
            }
            put((java.lang.String) objArr[i], objArr[i + 1]);
        }
    }

    public static android.filterfw.core.KeyValueMap fromKeyValues(java.lang.Object... objArr) {
        android.filterfw.core.KeyValueMap keyValueMap = new android.filterfw.core.KeyValueMap();
        keyValueMap.setKeyValues(objArr);
        return keyValueMap;
    }

    public java.lang.String getString(java.lang.String str) {
        java.lang.Object obj = get(str);
        if (obj != null) {
            return (java.lang.String) obj;
        }
        return null;
    }

    public int getInt(java.lang.String str) {
        java.lang.Object obj = get(str);
        if (obj != null) {
            return ((java.lang.Integer) obj).intValue();
        }
        return 0;
    }

    public float getFloat(java.lang.String str) {
        java.lang.Object obj = get(str);
        if (obj != null) {
            return ((java.lang.Float) obj).floatValue();
        }
        return 0.0f;
    }

    @Override // java.util.AbstractMap
    public java.lang.String toString() {
        java.lang.String obj;
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        for (java.util.Map.Entry<java.lang.String, java.lang.Object> entry : entrySet()) {
            java.lang.Object value = entry.getValue();
            if (value instanceof java.lang.String) {
                obj = "\"" + value + "\"";
            } else {
                obj = value.toString();
            }
            stringWriter.write(entry.getKey() + " = " + obj + ";\n");
        }
        return stringWriter.toString();
    }
}
