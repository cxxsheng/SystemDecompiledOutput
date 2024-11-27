package com.android.internal.util;

/* loaded from: classes5.dex */
public class TypedProperties extends java.util.HashMap<java.lang.String, java.lang.Object> {
    static final java.lang.String NULL_STRING = new java.lang.String("<TypedProperties:NULL_STRING>");
    public static final int STRING_NOT_SET = -1;
    public static final int STRING_NULL = 0;
    public static final int STRING_SET = 1;
    public static final int STRING_TYPE_MISMATCH = -2;
    static final int TYPE_BOOLEAN = 90;
    static final int TYPE_BYTE = 329;
    static final int TYPE_DOUBLE = 2118;
    static final int TYPE_ERROR = -1;
    static final int TYPE_FLOAT = 1094;
    static final int TYPE_INT = 1097;
    static final int TYPE_LONG = 2121;
    static final int TYPE_SHORT = 585;
    static final int TYPE_STRING = 29516;
    static final int TYPE_UNSET = 120;

    static java.io.StreamTokenizer initTokenizer(java.io.Reader reader) {
        java.io.StreamTokenizer streamTokenizer = new java.io.StreamTokenizer(reader);
        streamTokenizer.resetSyntax();
        streamTokenizer.wordChars(48, 57);
        streamTokenizer.wordChars(65, 90);
        streamTokenizer.wordChars(97, 122);
        streamTokenizer.wordChars(95, 95);
        streamTokenizer.wordChars(36, 36);
        streamTokenizer.wordChars(46, 46);
        streamTokenizer.wordChars(45, 45);
        streamTokenizer.wordChars(43, 43);
        streamTokenizer.ordinaryChar(61);
        streamTokenizer.whitespaceChars(32, 32);
        streamTokenizer.whitespaceChars(9, 9);
        streamTokenizer.whitespaceChars(10, 10);
        streamTokenizer.whitespaceChars(13, 13);
        streamTokenizer.quoteChar(34);
        streamTokenizer.slashStarComments(true);
        streamTokenizer.slashSlashComments(true);
        return streamTokenizer;
    }

    public static class ParseException extends java.lang.IllegalArgumentException {
        ParseException(java.io.StreamTokenizer streamTokenizer, java.lang.String str) {
            super("expected " + str + ", saw " + streamTokenizer.toString());
        }
    }

    static int interpretType(java.lang.String str) {
        if ("unset".equals(str)) {
            return 120;
        }
        if ("boolean".equals(str)) {
            return 90;
        }
        if ("byte".equals(str)) {
            return 329;
        }
        if ("short".equals(str)) {
            return 585;
        }
        if (android.app.slice.SliceItem.FORMAT_INT.equals(str)) {
            return 1097;
        }
        if (android.app.slice.SliceItem.FORMAT_LONG.equals(str)) {
            return 2121;
        }
        if ("float".equals(str)) {
            return 1094;
        }
        if ("double".equals(str)) {
            return 2118;
        }
        if ("String".equals(str)) {
            return TYPE_STRING;
        }
        return -1;
    }

    static void parse(java.io.Reader reader, java.util.Map<java.lang.String, java.lang.Object> map) throws com.android.internal.util.TypedProperties.ParseException, java.io.IOException {
        java.io.StreamTokenizer initTokenizer = initTokenizer(reader);
        java.util.regex.Pattern compile = java.util.regex.Pattern.compile("([a-zA-Z_$][0-9a-zA-Z_$]*\\.)*[a-zA-Z_$][0-9a-zA-Z_$]*");
        do {
            int nextToken = initTokenizer.nextToken();
            if (nextToken != -1) {
                if (nextToken != -3) {
                    throw new com.android.internal.util.TypedProperties.ParseException(initTokenizer, "type name");
                }
                int interpretType = interpretType(initTokenizer.sval);
                if (interpretType == -1) {
                    throw new com.android.internal.util.TypedProperties.ParseException(initTokenizer, "valid type name");
                }
                initTokenizer.sval = null;
                if (interpretType == 120 && initTokenizer.nextToken() != 40) {
                    throw new com.android.internal.util.TypedProperties.ParseException(initTokenizer, "'('");
                }
                if (initTokenizer.nextToken() != -3) {
                    throw new com.android.internal.util.TypedProperties.ParseException(initTokenizer, "property name");
                }
                java.lang.String str = initTokenizer.sval;
                if (!compile.matcher(str).matches()) {
                    throw new com.android.internal.util.TypedProperties.ParseException(initTokenizer, "valid property name");
                }
                initTokenizer.sval = null;
                if (interpretType == 120) {
                    if (initTokenizer.nextToken() != 41) {
                        throw new com.android.internal.util.TypedProperties.ParseException(initTokenizer, "')'");
                    }
                    map.remove(str);
                } else {
                    if (initTokenizer.nextToken() != 61) {
                        throw new com.android.internal.util.TypedProperties.ParseException(initTokenizer, "'='");
                    }
                    java.lang.Object parseValue = parseValue(initTokenizer, interpretType);
                    java.lang.Object remove = map.remove(str);
                    if (remove != null && parseValue.getClass() != remove.getClass()) {
                        throw new com.android.internal.util.TypedProperties.ParseException(initTokenizer, "(property previously declared as a different type)");
                    }
                    map.put(str, parseValue);
                }
            } else {
                return;
            }
        } while (initTokenizer.nextToken() == 59);
        throw new com.android.internal.util.TypedProperties.ParseException(initTokenizer, "';'");
    }

    static java.lang.Object parseValue(java.io.StreamTokenizer streamTokenizer, int i) throws java.io.IOException {
        int nextToken = streamTokenizer.nextToken();
        if (i == 90) {
            if (nextToken != -3) {
                throw new com.android.internal.util.TypedProperties.ParseException(streamTokenizer, "boolean constant");
            }
            if ("true".equals(streamTokenizer.sval)) {
                return java.lang.Boolean.TRUE;
            }
            if ("false".equals(streamTokenizer.sval)) {
                return java.lang.Boolean.FALSE;
            }
            throw new com.android.internal.util.TypedProperties.ParseException(streamTokenizer, "boolean constant");
        }
        int i2 = i & 255;
        if (i2 == 73) {
            if (nextToken != -3) {
                throw new com.android.internal.util.TypedProperties.ParseException(streamTokenizer, "integer constant");
            }
            try {
                long longValue = java.lang.Long.decode(streamTokenizer.sval).longValue();
                int i3 = (i >> 8) & 255;
                switch (i3) {
                    case 1:
                        if (longValue < -128 || longValue > 127) {
                            throw new com.android.internal.util.TypedProperties.ParseException(streamTokenizer, "8-bit integer constant");
                        }
                        return java.lang.Byte.valueOf((byte) longValue);
                    case 2:
                        if (longValue < -32768 || longValue > 32767) {
                            throw new com.android.internal.util.TypedProperties.ParseException(streamTokenizer, "16-bit integer constant");
                        }
                        return java.lang.Short.valueOf((short) longValue);
                    case 4:
                        if (longValue < -2147483648L || longValue > 2147483647L) {
                            throw new com.android.internal.util.TypedProperties.ParseException(streamTokenizer, "32-bit integer constant");
                        }
                        return java.lang.Integer.valueOf((int) longValue);
                    case 8:
                        if (longValue < Long.MIN_VALUE || longValue > Long.MAX_VALUE) {
                            throw new com.android.internal.util.TypedProperties.ParseException(streamTokenizer, "64-bit integer constant");
                        }
                        return java.lang.Long.valueOf(longValue);
                    default:
                        throw new java.lang.IllegalStateException("Internal error; unexpected integer type width " + i3);
                }
            } catch (java.lang.NumberFormatException e) {
                throw new com.android.internal.util.TypedProperties.ParseException(streamTokenizer, "integer constant");
            }
        }
        if (i2 != 70) {
            if (i == TYPE_STRING) {
                if (nextToken == 34) {
                    return streamTokenizer.sval;
                }
                if (nextToken == -3 && "null".equals(streamTokenizer.sval)) {
                    return NULL_STRING;
                }
                throw new com.android.internal.util.TypedProperties.ParseException(streamTokenizer, "double-quoted string or 'null'");
            }
            throw new java.lang.IllegalStateException("Internal error; unknown type " + i);
        }
        if (nextToken != -3) {
            throw new com.android.internal.util.TypedProperties.ParseException(streamTokenizer, "float constant");
        }
        try {
            double parseDouble = java.lang.Double.parseDouble(streamTokenizer.sval);
            if (((i >> 8) & 255) == 4) {
                double abs = java.lang.Math.abs(parseDouble);
                if (abs != 0.0d && !java.lang.Double.isInfinite(parseDouble) && !java.lang.Double.isNaN(parseDouble) && (abs < 1.401298464324817E-45d || abs > 3.4028234663852886E38d)) {
                    throw new com.android.internal.util.TypedProperties.ParseException(streamTokenizer, "32-bit float constant");
                }
                return java.lang.Float.valueOf((float) parseDouble);
            }
            return java.lang.Double.valueOf(parseDouble);
        } catch (java.lang.NumberFormatException e2) {
            throw new com.android.internal.util.TypedProperties.ParseException(streamTokenizer, "float constant");
        }
    }

    public void load(java.io.Reader reader) throws java.io.IOException {
        parse(reader, this);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public java.lang.Object get(java.lang.Object obj) {
        java.lang.Object obj2 = super.get(obj);
        if (obj2 == NULL_STRING) {
            return null;
        }
        return obj2;
    }

    public static class TypeException extends java.lang.IllegalArgumentException {
        TypeException(java.lang.String str, java.lang.Object obj, java.lang.String str2) {
            super(str + " has type " + obj.getClass().getName() + ", not " + str2);
        }
    }

    public boolean getBoolean(java.lang.String str, boolean z) {
        java.lang.Object obj = super.get(str);
        if (obj == null) {
            return z;
        }
        if (obj instanceof java.lang.Boolean) {
            return ((java.lang.Boolean) obj).booleanValue();
        }
        throw new com.android.internal.util.TypedProperties.TypeException(str, obj, "boolean");
    }

    public byte getByte(java.lang.String str, byte b) {
        java.lang.Object obj = super.get(str);
        if (obj == null) {
            return b;
        }
        if (obj instanceof java.lang.Byte) {
            return ((java.lang.Byte) obj).byteValue();
        }
        throw new com.android.internal.util.TypedProperties.TypeException(str, obj, "byte");
    }

    public short getShort(java.lang.String str, short s) {
        java.lang.Object obj = super.get(str);
        if (obj == null) {
            return s;
        }
        if (obj instanceof java.lang.Short) {
            return ((java.lang.Short) obj).shortValue();
        }
        throw new com.android.internal.util.TypedProperties.TypeException(str, obj, "short");
    }

    public int getInt(java.lang.String str, int i) {
        java.lang.Object obj = super.get(str);
        if (obj == null) {
            return i;
        }
        if (obj instanceof java.lang.Integer) {
            return ((java.lang.Integer) obj).intValue();
        }
        throw new com.android.internal.util.TypedProperties.TypeException(str, obj, android.app.slice.SliceItem.FORMAT_INT);
    }

    public long getLong(java.lang.String str, long j) {
        java.lang.Object obj = super.get(str);
        if (obj == null) {
            return j;
        }
        if (obj instanceof java.lang.Long) {
            return ((java.lang.Long) obj).longValue();
        }
        throw new com.android.internal.util.TypedProperties.TypeException(str, obj, android.app.slice.SliceItem.FORMAT_LONG);
    }

    public float getFloat(java.lang.String str, float f) {
        java.lang.Object obj = super.get(str);
        if (obj == null) {
            return f;
        }
        if (obj instanceof java.lang.Float) {
            return ((java.lang.Float) obj).floatValue();
        }
        throw new com.android.internal.util.TypedProperties.TypeException(str, obj, "float");
    }

    public double getDouble(java.lang.String str, double d) {
        java.lang.Object obj = super.get(str);
        if (obj == null) {
            return d;
        }
        if (obj instanceof java.lang.Double) {
            return ((java.lang.Double) obj).doubleValue();
        }
        throw new com.android.internal.util.TypedProperties.TypeException(str, obj, "double");
    }

    public java.lang.String getString(java.lang.String str, java.lang.String str2) {
        java.lang.Object obj = super.get(str);
        if (obj == null) {
            return str2;
        }
        if (obj == NULL_STRING) {
            return null;
        }
        if (obj instanceof java.lang.String) {
            return (java.lang.String) obj;
        }
        throw new com.android.internal.util.TypedProperties.TypeException(str, obj, "string");
    }

    public boolean getBoolean(java.lang.String str) {
        return getBoolean(str, false);
    }

    public byte getByte(java.lang.String str) {
        return getByte(str, (byte) 0);
    }

    public short getShort(java.lang.String str) {
        return getShort(str, (short) 0);
    }

    public int getInt(java.lang.String str) {
        return getInt(str, 0);
    }

    public long getLong(java.lang.String str) {
        return getLong(str, 0L);
    }

    public float getFloat(java.lang.String str) {
        return getFloat(str, 0.0f);
    }

    public double getDouble(java.lang.String str) {
        return getDouble(str, 0.0d);
    }

    public java.lang.String getString(java.lang.String str) {
        return getString(str, "");
    }

    public int getStringInfo(java.lang.String str) {
        java.lang.Object obj = super.get(str);
        if (obj == null) {
            return -1;
        }
        if (obj == NULL_STRING) {
            return 0;
        }
        if (obj instanceof java.lang.String) {
            return 1;
        }
        return -2;
    }
}
