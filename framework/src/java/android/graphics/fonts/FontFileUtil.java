package android.graphics.fonts;

/* loaded from: classes.dex */
public class FontFileUtil {
    private static final int ANALYZE_ERROR = -1;
    private static final int FVAR_TABLE_TAG = 1719034226;
    private static final int OS2_TABLE_TAG = 1330851634;
    private static final int SFNT_VERSION_1 = 65536;
    private static final int SFNT_VERSION_OTTO = 1330926671;
    private static final int TTC_TAG = 1953784678;

    @dalvik.annotation.optimization.FastNative
    private static native java.lang.String nGetFontPostScriptName(java.nio.ByteBuffer byteBuffer, int i);

    @dalvik.annotation.optimization.FastNative
    private static native long nGetFontRevision(java.nio.ByteBuffer byteBuffer, int i);

    @dalvik.annotation.optimization.FastNative
    private static native int nIsPostScriptType1Font(java.nio.ByteBuffer byteBuffer, int i);

    private FontFileUtil() {
    }

    public static int unpackWeight(int i) {
        return i & 65535;
    }

    public static boolean unpackItalic(int i) {
        return (i & 65536) != 0;
    }

    public static boolean isSuccess(int i) {
        return i != -1;
    }

    private static int pack(int i, boolean z) {
        return i | (z ? 65536 : 0);
    }

    public static final int analyzeStyle(java.nio.ByteBuffer byteBuffer, int i, android.graphics.fonts.FontVariationAxis[] fontVariationAxisArr) {
        int i2;
        char c;
        int i3;
        int i4;
        if (fontVariationAxisArr != null) {
            i2 = -1;
            c = 65535;
            for (android.graphics.fonts.FontVariationAxis fontVariationAxis : fontVariationAxisArr) {
                if ("wght".equals(fontVariationAxis.getTag())) {
                    i2 = (int) fontVariationAxis.getStyleValue();
                } else if ("ital".equals(fontVariationAxis.getTag())) {
                    c = fontVariationAxis.getStyleValue() == 1.0f ? (char) 1 : (char) 0;
                }
            }
        } else {
            i2 = -1;
            c = 65535;
        }
        if (i2 != -1 && c != 65535) {
            return pack(i2, c == 1);
        }
        java.nio.ByteOrder order = byteBuffer.order();
        byteBuffer.order(java.nio.ByteOrder.BIG_ENDIAN);
        try {
            if (byteBuffer.getInt(0) != TTC_TAG) {
                i3 = 0;
            } else {
                if (i >= byteBuffer.getInt(8)) {
                    return -1;
                }
                i3 = byteBuffer.getInt((i * 4) + 12);
            }
            int i5 = byteBuffer.getInt(i3);
            if (i5 != 65536 && i5 != SFNT_VERSION_OTTO) {
                return -1;
            }
            short s = byteBuffer.getShort(i3 + 4);
            int i6 = 0;
            while (true) {
                if (i6 >= s) {
                    i4 = -1;
                    break;
                }
                int i7 = i3 + 12 + (i6 * 16);
                if (byteBuffer.getInt(i7) == OS2_TABLE_TAG) {
                    i4 = byteBuffer.getInt(i7 + 8);
                    break;
                }
                i6++;
            }
            if (i4 == -1) {
                return pack(400, false);
            }
            short s2 = byteBuffer.getShort(i4 + 4);
            boolean z = (byteBuffer.getShort(i4 + 62) & 1) != 0;
            if (i2 == -1) {
                i2 = s2;
            }
            if (c == 65535) {
                r0 = z;
            } else if (c != 1) {
                r0 = false;
            }
            return pack(i2, r0);
        } finally {
            byteBuffer.order(order);
        }
    }

    public static long getRevision(java.nio.ByteBuffer byteBuffer, int i) {
        return nGetFontRevision(byteBuffer, i);
    }

    public static java.lang.String getPostScriptName(java.nio.ByteBuffer byteBuffer, int i) {
        return nGetFontPostScriptName(byteBuffer, i);
    }

    public static int isPostScriptType1Font(java.nio.ByteBuffer byteBuffer, int i) {
        return nIsPostScriptType1Font(byteBuffer, i);
    }

    public static int isCollectionFont(java.nio.ByteBuffer byteBuffer) {
        java.nio.ByteBuffer slice = byteBuffer.slice();
        slice.order(java.nio.ByteOrder.BIG_ENDIAN);
        int i = slice.getInt(0);
        if (i == TTC_TAG) {
            return 1;
        }
        if (i == 65536 || i == SFNT_VERSION_OTTO) {
            return 0;
        }
        return -1;
    }

    private static int getUInt16(java.nio.ByteBuffer byteBuffer, int i) {
        return byteBuffer.getShort(i) & 65535;
    }

    public static java.util.Set<java.lang.Integer> getSupportedAxes(java.nio.ByteBuffer byteBuffer, int i) {
        int i2;
        int i3;
        java.nio.ByteOrder order = byteBuffer.order();
        byteBuffer.order(java.nio.ByteOrder.BIG_ENDIAN);
        try {
            if (byteBuffer.getInt(0) != TTC_TAG) {
                i2 = 0;
            } else {
                if (i >= byteBuffer.getInt(8)) {
                    return java.util.Collections.EMPTY_SET;
                }
                i2 = byteBuffer.getInt((i * 4) + 12);
            }
            int i4 = byteBuffer.getInt(i2);
            if (i4 != 65536 && i4 != SFNT_VERSION_OTTO) {
                return java.util.Collections.EMPTY_SET;
            }
            short s = byteBuffer.getShort(i2 + 4);
            int i5 = 0;
            while (true) {
                if (i5 >= s) {
                    i3 = -1;
                    break;
                }
                int i6 = i2 + 12 + (i5 * 16);
                if (byteBuffer.getInt(i6) == FVAR_TABLE_TAG) {
                    i3 = byteBuffer.getInt(i6 + 8);
                    break;
                }
                i5++;
            }
            if (i3 == -1) {
                return java.util.Collections.EMPTY_SET;
            }
            if (byteBuffer.getShort(i3) == 1 && byteBuffer.getShort(i3 + 2) == 0) {
                int uInt16 = getUInt16(byteBuffer, i3 + 4);
                int uInt162 = getUInt16(byteBuffer, i3 + 8);
                int uInt163 = getUInt16(byteBuffer, i3 + 10);
                android.util.ArraySet arraySet = new android.util.ArraySet();
                for (int i7 = 0; i7 < uInt162; i7++) {
                    arraySet.add(java.lang.Integer.valueOf(byteBuffer.getInt(i3 + uInt16 + (uInt163 * i7))));
                }
                return arraySet;
            }
            return java.util.Collections.EMPTY_SET;
        } finally {
            byteBuffer.order(order);
        }
    }
}
