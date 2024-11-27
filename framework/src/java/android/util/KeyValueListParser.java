package android.util;

/* loaded from: classes3.dex */
public class KeyValueListParser {
    private final android.text.TextUtils.StringSplitter mSplitter;
    private final android.util.ArrayMap<java.lang.String, java.lang.String> mValues = new android.util.ArrayMap<>();

    public KeyValueListParser(char c) {
        this.mSplitter = new android.text.TextUtils.SimpleStringSplitter(c);
    }

    public void setString(java.lang.String str) throws java.lang.IllegalArgumentException {
        this.mValues.clear();
        if (str != null) {
            this.mSplitter.setString(str);
            for (java.lang.String str2 : this.mSplitter) {
                int indexOf = str2.indexOf(61);
                if (indexOf < 0) {
                    this.mValues.clear();
                    throw new java.lang.IllegalArgumentException("'" + str2 + "' in '" + str + "' is not a valid key-value pair");
                }
                this.mValues.put(str2.substring(0, indexOf).trim(), str2.substring(indexOf + 1).trim());
            }
        }
    }

    public int getInt(java.lang.String str, int i) {
        java.lang.String str2 = this.mValues.get(str);
        if (str2 != null) {
            try {
                return java.lang.Integer.parseInt(str2);
            } catch (java.lang.NumberFormatException e) {
            }
        }
        return i;
    }

    public long getLong(java.lang.String str, long j) {
        java.lang.String str2 = this.mValues.get(str);
        if (str2 != null) {
            try {
                return java.lang.Long.parseLong(str2);
            } catch (java.lang.NumberFormatException e) {
            }
        }
        return j;
    }

    public float getFloat(java.lang.String str, float f) {
        java.lang.String str2 = this.mValues.get(str);
        if (str2 != null) {
            try {
                return java.lang.Float.parseFloat(str2);
            } catch (java.lang.NumberFormatException e) {
            }
        }
        return f;
    }

    public java.lang.String getString(java.lang.String str, java.lang.String str2) {
        java.lang.String str3 = this.mValues.get(str);
        if (str3 != null) {
            return str3;
        }
        return str2;
    }

    public boolean getBoolean(java.lang.String str, boolean z) {
        java.lang.String str2 = this.mValues.get(str);
        if (str2 != null) {
            try {
                return java.lang.Boolean.parseBoolean(str2);
            } catch (java.lang.NumberFormatException e) {
            }
        }
        return z;
    }

    public int[] getIntArray(java.lang.String str, int[] iArr) {
        java.lang.String str2 = this.mValues.get(str);
        if (str2 != null) {
            try {
                java.lang.String[] split = str2.split(":");
                if (split.length > 0) {
                    int[] iArr2 = new int[split.length];
                    for (int i = 0; i < split.length; i++) {
                        iArr2[i] = java.lang.Integer.parseInt(split[i]);
                    }
                    return iArr2;
                }
            } catch (java.lang.NumberFormatException e) {
            }
        }
        return iArr;
    }

    public int size() {
        return this.mValues.size();
    }

    public java.lang.String keyAt(int i) {
        return this.mValues.keyAt(i);
    }

    public long getDurationMillis(java.lang.String str, long j) {
        java.lang.String str2 = this.mValues.get(str);
        if (str2 != null) {
            try {
                if (!str2.startsWith(android.hardware.gnss.GnssSignalType.CODE_TYPE_P) && !str2.startsWith("p")) {
                    return java.lang.Long.parseLong(str2);
                }
                return java.time.Duration.parse(str2).toMillis();
            } catch (java.lang.NumberFormatException | java.time.format.DateTimeParseException e) {
            }
        }
        return j;
    }

    public static class IntValue {
        private final int mDefaultValue;
        private final java.lang.String mKey;
        private int mValue;

        public IntValue(java.lang.String str, int i) {
            this.mKey = str;
            this.mDefaultValue = i;
            this.mValue = this.mDefaultValue;
        }

        public void parse(android.util.KeyValueListParser keyValueListParser) {
            this.mValue = keyValueListParser.getInt(this.mKey, this.mDefaultValue);
        }

        public java.lang.String getKey() {
            return this.mKey;
        }

        public int getDefaultValue() {
            return this.mDefaultValue;
        }

        public int getValue() {
            return this.mValue;
        }

        public void setValue(int i) {
            this.mValue = i;
        }

        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.print(this.mKey);
            printWriter.print("=");
            printWriter.print(this.mValue);
            printWriter.println();
        }

        public void dumpProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            protoOutputStream.write(j, this.mValue);
        }
    }

    public static class LongValue {
        private final long mDefaultValue;
        private final java.lang.String mKey;
        private long mValue;

        public LongValue(java.lang.String str, long j) {
            this.mKey = str;
            this.mDefaultValue = j;
            this.mValue = this.mDefaultValue;
        }

        public void parse(android.util.KeyValueListParser keyValueListParser) {
            this.mValue = keyValueListParser.getLong(this.mKey, this.mDefaultValue);
        }

        public java.lang.String getKey() {
            return this.mKey;
        }

        public long getDefaultValue() {
            return this.mDefaultValue;
        }

        public long getValue() {
            return this.mValue;
        }

        public void setValue(long j) {
            this.mValue = j;
        }

        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.print(this.mKey);
            printWriter.print("=");
            printWriter.print(this.mValue);
            printWriter.println();
        }

        public void dumpProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            protoOutputStream.write(j, this.mValue);
        }
    }

    public static class StringValue {
        private final java.lang.String mDefaultValue;
        private final java.lang.String mKey;
        private java.lang.String mValue;

        public StringValue(java.lang.String str, java.lang.String str2) {
            this.mKey = str;
            this.mDefaultValue = str2;
            this.mValue = this.mDefaultValue;
        }

        public void parse(android.util.KeyValueListParser keyValueListParser) {
            this.mValue = keyValueListParser.getString(this.mKey, this.mDefaultValue);
        }

        public java.lang.String getKey() {
            return this.mKey;
        }

        public java.lang.String getDefaultValue() {
            return this.mDefaultValue;
        }

        public java.lang.String getValue() {
            return this.mValue;
        }

        public void setValue(java.lang.String str) {
            this.mValue = str;
        }

        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.print(this.mKey);
            printWriter.print("=");
            printWriter.print(this.mValue);
            printWriter.println();
        }

        public void dumpProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            protoOutputStream.write(j, this.mValue);
        }
    }

    public static class FloatValue {
        private final float mDefaultValue;
        private final java.lang.String mKey;
        private float mValue;

        public FloatValue(java.lang.String str, float f) {
            this.mKey = str;
            this.mDefaultValue = f;
            this.mValue = this.mDefaultValue;
        }

        public void parse(android.util.KeyValueListParser keyValueListParser) {
            this.mValue = keyValueListParser.getFloat(this.mKey, this.mDefaultValue);
        }

        public java.lang.String getKey() {
            return this.mKey;
        }

        public float getDefaultValue() {
            return this.mDefaultValue;
        }

        public float getValue() {
            return this.mValue;
        }

        public void setValue(float f) {
            this.mValue = f;
        }

        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.print(this.mKey);
            printWriter.print("=");
            printWriter.print(this.mValue);
            printWriter.println();
        }

        public void dumpProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            protoOutputStream.write(j, this.mValue);
        }
    }
}
