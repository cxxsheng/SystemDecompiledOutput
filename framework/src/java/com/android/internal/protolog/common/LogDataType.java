package com.android.internal.protolog.common;

/* loaded from: classes5.dex */
public class LogDataType {
    public static final int BOOLEAN = 3;
    public static final int DOUBLE = 2;
    public static final int LONG = 1;
    public static final int STRING = 0;
    private static final int TYPE_MASK = 3;
    private static final int TYPE_WIDTH = 2;

    public static int logDataTypesToBitMask(java.util.List<java.lang.Integer> list) {
        if (list.size() > 16) {
            throw new com.android.internal.protolog.common.BitmaskConversionException("Too many log call parameters - max 16 parameters supported");
        }
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            i |= list.get(i2).intValue() << (i2 * 2);
        }
        return i;
    }

    public static int bitmaskToLogDataType(int i, int i2) {
        if (i2 > 16) {
            throw new com.android.internal.protolog.common.BitmaskConversionException("Max 16 parameters allowed");
        }
        return (i >> (i2 * 2)) & 3;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static java.util.List<java.lang.Integer> parseFormatString(java.lang.String str) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i = 0;
        while (i < str.length()) {
            if (str.charAt(i) == '%') {
                int i2 = i + 1;
                if (i2 >= str.length()) {
                    throw new com.android.internal.protolog.common.InvalidFormatStringException("Invalid format string in config");
                }
                switch (str.charAt(i2)) {
                    case '%':
                        i += 2;
                        break;
                    case 'b':
                        arrayList.add(3);
                        i += 2;
                        break;
                    case 'd':
                    case 'x':
                        arrayList.add(1);
                        i += 2;
                        break;
                    case 'f':
                        arrayList.add(2);
                        i += 2;
                        break;
                    case 's':
                        arrayList.add(0);
                        i += 2;
                        break;
                    default:
                        throw new com.android.internal.protolog.common.InvalidFormatStringException("Invalid format string field %${messageString[i + 1]}");
                }
            } else {
                i++;
            }
        }
        return arrayList;
    }
}
