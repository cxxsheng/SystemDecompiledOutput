package android.util;

/* loaded from: classes3.dex */
public class DebugUtils {
    public static boolean isObjectSelected(java.lang.Object obj) {
        java.lang.reflect.Method declaredMethod;
        java.lang.String str = java.lang.System.getenv("ANDROID_OBJECT_FILTER");
        if (str == null || str.length() <= 0) {
            return false;
        }
        java.lang.String[] split = str.split("@");
        if (obj.getClass().getSimpleName().matches(split[0])) {
            boolean z = false;
            for (int i = 1; i < split.length; i++) {
                java.lang.String[] split2 = split[i].split("=");
                java.lang.Class<?> cls = obj.getClass();
                java.lang.Class<?> cls2 = cls;
                while (true) {
                    try {
                        declaredMethod = cls2.getDeclaredMethod("get" + split2[0].substring(0, 1).toUpperCase(java.util.Locale.ROOT) + split2[0].substring(1), null);
                        java.lang.Class<? super java.lang.Object> superclass = cls.getSuperclass();
                        if (superclass == null || declaredMethod != null) {
                            break;
                        }
                        cls2 = superclass;
                    } catch (java.lang.IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (java.lang.NoSuchMethodException e2) {
                        e2.printStackTrace();
                    } catch (java.lang.reflect.InvocationTargetException e3) {
                        e3.printStackTrace();
                    }
                }
                if (declaredMethod != null) {
                    java.lang.Object invoke = declaredMethod.invoke(obj, null);
                    z |= (invoke != null ? invoke.toString() : "null").matches(split2[1]);
                }
            }
            return z;
        }
        return false;
    }

    public static void buildShortClassTag(java.lang.Object obj, java.lang.StringBuilder sb) {
        int lastIndexOf;
        if (obj == null) {
            sb.append("null");
            return;
        }
        java.lang.String simpleName = obj.getClass().getSimpleName();
        if ((simpleName == null || simpleName.isEmpty()) && (lastIndexOf = (simpleName = obj.getClass().getName()).lastIndexOf(46)) > 0) {
            simpleName = simpleName.substring(lastIndexOf + 1);
        }
        sb.append(simpleName);
        sb.append('{');
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(obj)));
    }

    public static void printSizeValue(java.io.PrintWriter printWriter, long j) {
        java.lang.String str;
        java.lang.String format;
        float f = j;
        if (f <= 900.0f) {
            str = "";
        } else {
            f /= 1024.0f;
            str = "KB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "MB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "GB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "TB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "PB";
        }
        if (f < 1.0f) {
            format = java.lang.String.format("%.2f", java.lang.Float.valueOf(f));
        } else if (f < 10.0f) {
            format = java.lang.String.format("%.1f", java.lang.Float.valueOf(f));
        } else if (f < 100.0f) {
            format = java.lang.String.format("%.0f", java.lang.Float.valueOf(f));
        } else {
            format = java.lang.String.format("%.0f", java.lang.Float.valueOf(f));
        }
        printWriter.print(format);
        printWriter.print(str);
    }

    public static java.lang.String sizeValueToString(long j, java.lang.StringBuilder sb) {
        java.lang.String str;
        java.lang.String format;
        if (sb == null) {
            sb = new java.lang.StringBuilder(32);
        }
        float f = j;
        if (f <= 900.0f) {
            str = "";
        } else {
            f /= 1024.0f;
            str = "KB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "MB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "GB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "TB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "PB";
        }
        if (f < 1.0f) {
            format = java.lang.String.format("%.2f", java.lang.Float.valueOf(f));
        } else if (f < 10.0f) {
            format = java.lang.String.format("%.1f", java.lang.Float.valueOf(f));
        } else if (f < 100.0f) {
            format = java.lang.String.format("%.0f", java.lang.Float.valueOf(f));
        } else {
            format = java.lang.String.format("%.0f", java.lang.Float.valueOf(f));
        }
        sb.append(format);
        sb.append(str);
        return sb.toString();
    }

    public static java.lang.String valueToString(java.lang.Class<?> cls, java.lang.String str, int i) {
        java.lang.reflect.Field[] declaredFields = cls.getDeclaredFields();
        int length = declaredFields.length;
        for (int i2 = 0; i2 < length; i2++) {
            java.lang.reflect.Field field = declaredFields[i2];
            int modifiers = field.getModifiers();
            if (java.lang.reflect.Modifier.isStatic(modifiers) && java.lang.reflect.Modifier.isFinal(modifiers) && field.getType().equals(java.lang.Integer.TYPE) && field.getName().startsWith(str)) {
                try {
                    if (i == field.getInt(null)) {
                        return constNameWithoutPrefix(str, field);
                    }
                    continue;
                } catch (java.lang.IllegalAccessException e) {
                }
            }
        }
        return java.lang.Integer.toString(i);
    }

    public static java.lang.String flagsToString(java.lang.Class<?> cls, java.lang.String str, long j) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        boolean z = j == 0;
        for (java.lang.reflect.Field field : cls.getDeclaredFields()) {
            int modifiers = field.getModifiers();
            if (java.lang.reflect.Modifier.isStatic(modifiers) && java.lang.reflect.Modifier.isFinal(modifiers) && ((field.getType().equals(java.lang.Integer.TYPE) || field.getType().equals(java.lang.Long.TYPE)) && field.getName().startsWith(str))) {
                long fieldValue = getFieldValue(field);
                if (fieldValue == 0 && z) {
                    return constNameWithoutPrefix(str, field);
                }
                if (fieldValue != 0 && (j & fieldValue) == fieldValue) {
                    j &= ~fieldValue;
                    sb.append(constNameWithoutPrefix(str, field)).append('|');
                }
            }
        }
        if (j != 0 || sb.length() == 0) {
            sb.append(java.lang.Long.toHexString(j));
        } else {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private static long getFieldValue(java.lang.reflect.Field field) {
        long j;
        try {
            j = field.getLong(null);
        } catch (java.lang.IllegalAccessException e) {
        }
        if (j != 0) {
            return j;
        }
        int i = field.getInt(null);
        if (i != 0) {
            return i;
        }
        return 0L;
    }

    public static java.lang.String constantToString(java.lang.Class<?> cls, java.lang.String str, int i) {
        java.lang.reflect.Field[] declaredFields = cls.getDeclaredFields();
        int length = declaredFields.length;
        for (int i2 = 0; i2 < length; i2++) {
            java.lang.reflect.Field field = declaredFields[i2];
            int modifiers = field.getModifiers();
            try {
                if (java.lang.reflect.Modifier.isStatic(modifiers) && java.lang.reflect.Modifier.isFinal(modifiers) && field.getType().equals(java.lang.Integer.TYPE) && field.getName().startsWith(str) && field.getInt(null) == i) {
                    return constNameWithoutPrefix(str, field);
                }
            } catch (java.lang.IllegalAccessException e) {
            }
        }
        return str + java.lang.Integer.toString(i);
    }

    private static java.lang.String constNameWithoutPrefix(java.lang.String str, java.lang.reflect.Field field) {
        return field.getName().substring(str.length());
    }

    public static java.util.List<java.lang.String> callersWithin(final java.lang.Class<?> cls, int i) {
        java.util.List<java.lang.String> list = (java.util.List) java.util.Arrays.stream(java.lang.Thread.currentThread().getStackTrace()).skip(i + 3).filter(new java.util.function.Predicate() { // from class: android.util.DebugUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean startsWith;
                startsWith = ((java.lang.StackTraceElement) obj).getClassName().startsWith(cls.getName());
                return startsWith;
            }
        }).map(new java.util.function.Function() { // from class: android.util.DebugUtils$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((java.lang.StackTraceElement) obj).getMethodName();
            }
        }).collect(java.util.stream.Collectors.toList());
        java.util.Collections.reverse(list);
        return list;
    }
}
