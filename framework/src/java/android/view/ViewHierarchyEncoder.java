package android.view;

/* loaded from: classes4.dex */
public class ViewHierarchyEncoder {
    private static final byte SIG_BOOLEAN = 90;
    private static final byte SIG_BYTE = 66;
    private static final byte SIG_DOUBLE = 68;
    private static final short SIG_END_MAP = 0;
    private static final byte SIG_FLOAT = 70;
    private static final byte SIG_INT = 73;
    private static final byte SIG_LONG = 74;
    private static final byte SIG_MAP = 77;
    private static final byte SIG_SHORT = 83;
    private static final byte SIG_STRING = 82;
    private final java.io.DataOutputStream mStream;
    private final java.util.Map<java.lang.String, java.lang.Short> mPropertyNames = new java.util.HashMap(200);
    private short mPropertyId = 1;
    private java.nio.charset.Charset mCharset = java.nio.charset.Charset.forName("utf-8");
    private boolean mUserPropertiesEnabled = true;

    public ViewHierarchyEncoder(java.io.ByteArrayOutputStream byteArrayOutputStream) {
        this.mStream = new java.io.DataOutputStream(byteArrayOutputStream);
    }

    public void setUserPropertiesEnabled(boolean z) {
        this.mUserPropertiesEnabled = z;
    }

    public void beginObject(java.lang.Object obj) {
        startPropertyMap();
        addProperty("meta:__name__", obj.getClass().getName());
        addProperty("meta:__hash__", obj.hashCode());
    }

    public void endObject() {
        endPropertyMap();
    }

    public void endStream() {
        startPropertyMap();
        addProperty("__name__", "propertyIndex");
        for (java.util.Map.Entry<java.lang.String, java.lang.Short> entry : this.mPropertyNames.entrySet()) {
            writeShort(entry.getValue().shortValue());
            writeString(entry.getKey());
        }
        endPropertyMap();
    }

    public void addProperty(java.lang.String str, boolean z) {
        writeShort(createPropertyIndex(str));
        writeBoolean(z);
    }

    public void addProperty(java.lang.String str, short s) {
        writeShort(createPropertyIndex(str));
        writeShort(s);
    }

    public void addProperty(java.lang.String str, int i) {
        writeShort(createPropertyIndex(str));
        writeInt(i);
    }

    public void addProperty(java.lang.String str, float f) {
        writeShort(createPropertyIndex(str));
        writeFloat(f);
    }

    public void addProperty(java.lang.String str, java.lang.String str2) {
        writeShort(createPropertyIndex(str));
        writeString(str2);
    }

    public void addUserProperty(java.lang.String str, java.lang.String str2) {
        if (this.mUserPropertiesEnabled) {
            addProperty(str, str2);
        }
    }

    public void addPropertyKey(java.lang.String str) {
        writeShort(createPropertyIndex(str));
    }

    private short createPropertyIndex(java.lang.String str) {
        java.lang.Short sh = this.mPropertyNames.get(str);
        if (sh == null) {
            short s = this.mPropertyId;
            this.mPropertyId = (short) (s + 1);
            sh = java.lang.Short.valueOf(s);
            this.mPropertyNames.put(str, sh);
        }
        return sh.shortValue();
    }

    private void startPropertyMap() {
        try {
            this.mStream.write(77);
        } catch (java.io.IOException e) {
        }
    }

    private void endPropertyMap() {
        writeShort((short) 0);
    }

    private void writeBoolean(boolean z) {
        try {
            this.mStream.write(90);
            this.mStream.write(z ? 1 : 0);
        } catch (java.io.IOException e) {
        }
    }

    private void writeShort(short s) {
        try {
            this.mStream.write(83);
            this.mStream.writeShort(s);
        } catch (java.io.IOException e) {
        }
    }

    private void writeInt(int i) {
        try {
            this.mStream.write(73);
            this.mStream.writeInt(i);
        } catch (java.io.IOException e) {
        }
    }

    private void writeFloat(float f) {
        try {
            this.mStream.write(70);
            this.mStream.writeFloat(f);
        } catch (java.io.IOException e) {
        }
    }

    private void writeString(java.lang.String str) {
        if (str == null) {
            str = "";
        }
        try {
            this.mStream.write(82);
            byte[] bytes = str.getBytes(this.mCharset);
            short min = (short) java.lang.Math.min(bytes.length, 32767);
            this.mStream.writeShort(min);
            this.mStream.write(bytes, 0, min);
        } catch (java.io.IOException e) {
        }
    }
}
