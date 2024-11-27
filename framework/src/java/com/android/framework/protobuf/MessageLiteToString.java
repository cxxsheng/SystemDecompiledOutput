package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class MessageLiteToString {
    private static final java.lang.String BUILDER_LIST_SUFFIX = "OrBuilderList";
    private static final java.lang.String BYTES_SUFFIX = "Bytes";
    private static final char[] INDENT_BUFFER = new char[80];
    private static final java.lang.String LIST_SUFFIX = "List";
    private static final java.lang.String MAP_SUFFIX = "Map";

    static {
        java.util.Arrays.fill(INDENT_BUFFER, ' ');
    }

    private MessageLiteToString() {
    }

    static java.lang.String toString(com.android.framework.protobuf.MessageLite messageLite, java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("# ").append(str);
        reflectivePrintWithIndent(messageLite, sb, 0);
        return sb.toString();
    }

    private static void reflectivePrintWithIndent(com.android.framework.protobuf.MessageLite messageLite, java.lang.StringBuilder sb, int i) {
        int i2;
        boolean booleanValue;
        java.lang.reflect.Method method;
        java.lang.reflect.Method method2;
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.HashMap hashMap = new java.util.HashMap();
        java.util.TreeMap treeMap = new java.util.TreeMap();
        java.lang.reflect.Method[] declaredMethods = messageLite.getClass().getDeclaredMethods();
        int length = declaredMethods.length;
        int i3 = 0;
        while (true) {
            i2 = 3;
            if (i3 >= length) {
                break;
            }
            java.lang.reflect.Method method3 = declaredMethods[i3];
            if (!java.lang.reflect.Modifier.isStatic(method3.getModifiers()) && method3.getName().length() >= 3) {
                if (method3.getName().startsWith("set")) {
                    hashSet.add(method3.getName());
                } else if (java.lang.reflect.Modifier.isPublic(method3.getModifiers()) && method3.getParameterTypes().length == 0) {
                    if (method3.getName().startsWith("has")) {
                        hashMap.put(method3.getName(), method3);
                    } else if (method3.getName().startsWith("get")) {
                        treeMap.put(method3.getName(), method3);
                    }
                }
            }
            i3++;
        }
        for (java.util.Map.Entry entry : treeMap.entrySet()) {
            java.lang.String substring = ((java.lang.String) entry.getKey()).substring(i2);
            if (substring.endsWith(LIST_SUFFIX) && !substring.endsWith(BUILDER_LIST_SUFFIX) && !substring.equals(LIST_SUFFIX) && (method2 = (java.lang.reflect.Method) entry.getValue()) != null && method2.getReturnType().equals(java.util.List.class)) {
                printField(sb, i, substring.substring(0, substring.length() - LIST_SUFFIX.length()), com.android.framework.protobuf.GeneratedMessageLite.invokeOrDie(method2, messageLite, new java.lang.Object[0]));
                i2 = 3;
            } else if (substring.endsWith(MAP_SUFFIX) && !substring.equals(MAP_SUFFIX) && (method = (java.lang.reflect.Method) entry.getValue()) != null && method.getReturnType().equals(java.util.Map.class) && !method.isAnnotationPresent(java.lang.Deprecated.class) && java.lang.reflect.Modifier.isPublic(method.getModifiers())) {
                printField(sb, i, substring.substring(0, substring.length() - MAP_SUFFIX.length()), com.android.framework.protobuf.GeneratedMessageLite.invokeOrDie(method, messageLite, new java.lang.Object[0]));
                i2 = 3;
            } else if (!hashSet.contains("set" + substring)) {
                i2 = 3;
            } else if (substring.endsWith(BYTES_SUFFIX) && treeMap.containsKey("get" + substring.substring(0, substring.length() - BYTES_SUFFIX.length()))) {
                i2 = 3;
            } else {
                java.lang.reflect.Method method4 = (java.lang.reflect.Method) entry.getValue();
                java.lang.reflect.Method method5 = (java.lang.reflect.Method) hashMap.get("has" + substring);
                if (method4 != null) {
                    java.lang.Object invokeOrDie = com.android.framework.protobuf.GeneratedMessageLite.invokeOrDie(method4, messageLite, new java.lang.Object[0]);
                    if (method5 != null) {
                        booleanValue = ((java.lang.Boolean) com.android.framework.protobuf.GeneratedMessageLite.invokeOrDie(method5, messageLite, new java.lang.Object[0])).booleanValue();
                    } else {
                        booleanValue = !isDefaultValue(invokeOrDie);
                    }
                    if (!booleanValue) {
                        i2 = 3;
                    } else {
                        printField(sb, i, substring, invokeOrDie);
                        i2 = 3;
                    }
                } else {
                    i2 = 3;
                }
            }
        }
        if (messageLite instanceof com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) {
            java.util.Iterator<java.util.Map.Entry<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor, java.lang.Object>> it = ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) messageLite).extensions.iterator();
            while (it.hasNext()) {
                java.util.Map.Entry<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor, java.lang.Object> next = it.next();
                printField(sb, i, android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + next.getKey().getNumber() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END, next.getValue());
            }
        }
        com.android.framework.protobuf.GeneratedMessageLite generatedMessageLite = (com.android.framework.protobuf.GeneratedMessageLite) messageLite;
        if (generatedMessageLite.unknownFields != null) {
            generatedMessageLite.unknownFields.printWithIndent(sb, i);
        }
    }

    private static boolean isDefaultValue(java.lang.Object obj) {
        if (obj instanceof java.lang.Boolean) {
            return !((java.lang.Boolean) obj).booleanValue();
        }
        if (obj instanceof java.lang.Integer) {
            return ((java.lang.Integer) obj).intValue() == 0;
        }
        if (obj instanceof java.lang.Float) {
            return java.lang.Float.floatToRawIntBits(((java.lang.Float) obj).floatValue()) == 0;
        }
        if (obj instanceof java.lang.Double) {
            return java.lang.Double.doubleToRawLongBits(((java.lang.Double) obj).doubleValue()) == 0;
        }
        if (obj instanceof java.lang.String) {
            return obj.equals("");
        }
        if (obj instanceof com.android.framework.protobuf.ByteString) {
            return obj.equals(com.android.framework.protobuf.ByteString.EMPTY);
        }
        return obj instanceof com.android.framework.protobuf.MessageLite ? obj == ((com.android.framework.protobuf.MessageLite) obj).getDefaultInstanceForType() : (obj instanceof java.lang.Enum) && ((java.lang.Enum) obj).ordinal() == 0;
    }

    static void printField(java.lang.StringBuilder sb, int i, java.lang.String str, java.lang.Object obj) {
        if (obj instanceof java.util.List) {
            java.util.Iterator it = ((java.util.List) obj).iterator();
            while (it.hasNext()) {
                printField(sb, i, str, it.next());
            }
            return;
        }
        if (obj instanceof java.util.Map) {
            java.util.Iterator it2 = ((java.util.Map) obj).entrySet().iterator();
            while (it2.hasNext()) {
                printField(sb, i, str, (java.util.Map.Entry) it2.next());
            }
            return;
        }
        sb.append('\n');
        indent(i, sb);
        sb.append(pascalCaseToSnakeCase(str));
        if (obj instanceof java.lang.String) {
            sb.append(": \"").append(com.android.framework.protobuf.TextFormatEscaper.escapeText((java.lang.String) obj)).append('\"');
            return;
        }
        if (obj instanceof com.android.framework.protobuf.ByteString) {
            sb.append(": \"").append(com.android.framework.protobuf.TextFormatEscaper.escapeBytes((com.android.framework.protobuf.ByteString) obj)).append('\"');
            return;
        }
        if (obj instanceof com.android.framework.protobuf.GeneratedMessageLite) {
            sb.append(" {");
            reflectivePrintWithIndent((com.android.framework.protobuf.GeneratedMessageLite) obj, sb, i + 2);
            sb.append("\n");
            indent(i, sb);
            sb.append("}");
            return;
        }
        if (obj instanceof java.util.Map.Entry) {
            sb.append(" {");
            java.util.Map.Entry entry = (java.util.Map.Entry) obj;
            int i2 = i + 2;
            printField(sb, i2, "key", entry.getKey());
            printField(sb, i2, "value", entry.getValue());
            sb.append("\n");
            indent(i, sb);
            sb.append("}");
            return;
        }
        sb.append(": ").append(obj);
    }

    private static void indent(int i, java.lang.StringBuilder sb) {
        int i2;
        while (i > 0) {
            if (i <= INDENT_BUFFER.length) {
                i2 = i;
            } else {
                i2 = INDENT_BUFFER.length;
            }
            sb.append(INDENT_BUFFER, 0, i2);
            i -= i2;
        }
    }

    private static java.lang.String pascalCaseToSnakeCase(java.lang.String str) {
        if (str.isEmpty()) {
            return str;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(java.lang.Character.toLowerCase(str.charAt(0)));
        for (int i = 1; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (java.lang.Character.isUpperCase(charAt)) {
                sb.append(android.telecom.Logging.Session.SESSION_SEPARATION_CHAR_CHILD);
            }
            sb.append(java.lang.Character.toLowerCase(charAt));
        }
        return sb.toString();
    }
}
