package com.android.framework.protobuf.nano;

/* loaded from: classes4.dex */
public final class MessageNanoPrinter {
    private static final java.lang.String INDENT = "  ";
    private static final int MAX_STRING_LEN = 200;

    private MessageNanoPrinter() {
    }

    public static <T extends com.android.framework.protobuf.nano.MessageNano> java.lang.String print(T t) {
        if (t == null) {
            return "";
        }
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        try {
            print(null, t, new java.lang.StringBuffer(), stringBuffer);
            return stringBuffer.toString();
        } catch (java.lang.IllegalAccessException e) {
            return "Error printing proto: " + e.getMessage();
        } catch (java.lang.reflect.InvocationTargetException e2) {
            return "Error printing proto: " + e2.getMessage();
        }
    }

    private static void print(java.lang.String str, java.lang.Object obj, java.lang.StringBuffer stringBuffer, java.lang.StringBuffer stringBuffer2) throws java.lang.IllegalAccessException, java.lang.reflect.InvocationTargetException {
        if (obj != null) {
            if (!(obj instanceof com.android.framework.protobuf.nano.MessageNano)) {
                if (obj instanceof java.util.Map) {
                    java.lang.String deCamelCaseify = deCamelCaseify(str);
                    for (java.util.Map.Entry entry : ((java.util.Map) obj).entrySet()) {
                        stringBuffer2.append(stringBuffer).append(deCamelCaseify).append(" <\n");
                        int length = stringBuffer.length();
                        stringBuffer.append(INDENT);
                        print("key", entry.getKey(), stringBuffer, stringBuffer2);
                        print("value", entry.getValue(), stringBuffer, stringBuffer2);
                        stringBuffer.setLength(length);
                        stringBuffer2.append(stringBuffer).append(">\n");
                    }
                    return;
                }
                stringBuffer2.append(stringBuffer).append(deCamelCaseify(str)).append(": ");
                if (obj instanceof java.lang.String) {
                    stringBuffer2.append("\"").append(sanitizeString((java.lang.String) obj)).append("\"");
                } else if (obj instanceof byte[]) {
                    appendQuotedBytes((byte[]) obj, stringBuffer2);
                } else {
                    stringBuffer2.append(obj);
                }
                stringBuffer2.append("\n");
                return;
            }
            int length2 = stringBuffer.length();
            if (str != null) {
                stringBuffer2.append(stringBuffer).append(deCamelCaseify(str)).append(" <\n");
                stringBuffer.append(INDENT);
            }
            java.lang.Class<?> cls = obj.getClass();
            for (java.lang.reflect.Field field : cls.getFields()) {
                int modifiers = field.getModifiers();
                java.lang.String name = field.getName();
                if (!"cachedSize".equals(name) && (modifiers & 1) == 1 && (modifiers & 8) != 8 && !name.startsWith(android.telecom.Logging.Session.SESSION_SEPARATION_CHAR_CHILD) && !name.endsWith(android.telecom.Logging.Session.SESSION_SEPARATION_CHAR_CHILD)) {
                    java.lang.Class<?> type = field.getType();
                    java.lang.Object obj2 = field.get(obj);
                    if (type.isArray()) {
                        if (type.getComponentType() == java.lang.Byte.TYPE) {
                            print(name, obj2, stringBuffer, stringBuffer2);
                        } else {
                            int length3 = obj2 == null ? 0 : java.lang.reflect.Array.getLength(obj2);
                            for (int i = 0; i < length3; i++) {
                                print(name, java.lang.reflect.Array.get(obj2, i), stringBuffer, stringBuffer2);
                            }
                        }
                    } else {
                        print(name, obj2, stringBuffer, stringBuffer2);
                    }
                }
            }
            for (java.lang.reflect.Method method : cls.getMethods()) {
                java.lang.String name2 = method.getName();
                if (name2.startsWith("set")) {
                    java.lang.String substring = name2.substring(3);
                    try {
                        if (((java.lang.Boolean) cls.getMethod("has" + substring, new java.lang.Class[0]).invoke(obj, new java.lang.Object[0])).booleanValue()) {
                            try {
                                print(substring, cls.getMethod("get" + substring, new java.lang.Class[0]).invoke(obj, new java.lang.Object[0]), stringBuffer, stringBuffer2);
                            } catch (java.lang.NoSuchMethodException e) {
                            }
                        }
                    } catch (java.lang.NoSuchMethodException e2) {
                    }
                }
            }
            if (str != null) {
                stringBuffer.setLength(length2);
                stringBuffer2.append(stringBuffer).append(">\n");
            }
        }
    }

    private static java.lang.String deCamelCaseify(java.lang.String str) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (i == 0) {
                stringBuffer.append(java.lang.Character.toLowerCase(charAt));
            } else if (java.lang.Character.isUpperCase(charAt)) {
                stringBuffer.append('_').append(java.lang.Character.toLowerCase(charAt));
            } else {
                stringBuffer.append(charAt);
            }
        }
        return stringBuffer.toString();
    }

    private static java.lang.String sanitizeString(java.lang.String str) {
        if (!str.startsWith(android.content.IntentFilter.SCHEME_HTTP) && str.length() > 200) {
            str = str.substring(0, 200) + "[...]";
        }
        return escapeString(str);
    }

    private static java.lang.String escapeString(java.lang.String str) {
        int length = str.length();
        java.lang.StringBuilder sb = new java.lang.StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt >= ' ' && charAt <= '~' && charAt != '\"' && charAt != '\'') {
                sb.append(charAt);
            } else {
                sb.append(java.lang.String.format("\\u%04x", java.lang.Integer.valueOf(charAt)));
            }
        }
        return sb.toString();
    }

    private static void appendQuotedBytes(byte[] bArr, java.lang.StringBuffer stringBuffer) {
        if (bArr == null) {
            stringBuffer.append("\"\"");
            return;
        }
        stringBuffer.append('\"');
        for (byte b : bArr) {
            int i = b & 255;
            if (i == 92 || i == 34) {
                stringBuffer.append('\\').append((char) i);
            } else if (i >= 32 && i < 127) {
                stringBuffer.append((char) i);
            } else {
                stringBuffer.append(java.lang.String.format("\\%03o", java.lang.Integer.valueOf(i)));
            }
        }
        stringBuffer.append('\"');
    }
}
