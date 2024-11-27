package android.content.res;

/* loaded from: classes.dex */
public class FontScaleConverterFactory {
    private static final float SCALE_KEY_MULTIPLIER = 100.0f;
    private static float sMinScaleBeforeCurvesApplied;
    public static volatile android.util.SparseArray<android.content.res.FontScaleConverter> sLookupTables = new android.util.SparseArray<>();
    private static final java.lang.Object LOOKUP_TABLES_WRITE_LOCK = new java.lang.Object();

    static {
        sMinScaleBeforeCurvesApplied = 1.05f;
        synchronized (LOOKUP_TABLES_WRITE_LOCK) {
            putInto(sLookupTables, 1.1f, new android.content.res.FontScaleConverterImpl(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{8.8f, 11.0f, 13.2f, 15.6f, 19.2f, 21.2f, 24.8f, 30.0f, 100.0f}));
            putInto(sLookupTables, 1.15f, new android.content.res.FontScaleConverterImpl(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{9.2f, 11.5f, 13.8f, 16.4f, 19.8f, 21.8f, 25.2f, 30.0f, 100.0f}));
            putInto(sLookupTables, 1.3f, new android.content.res.FontScaleConverterImpl(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{10.4f, 13.0f, 15.6f, 18.8f, 21.6f, 23.6f, 26.4f, 30.0f, 100.0f}));
            putInto(sLookupTables, 1.5f, new android.content.res.FontScaleConverterImpl(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{12.0f, 15.0f, 18.0f, 22.0f, 24.0f, 26.0f, 28.0f, 30.0f, 100.0f}));
            putInto(sLookupTables, 1.8f, new android.content.res.FontScaleConverterImpl(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{14.4f, 18.0f, 21.6f, 24.4f, 27.6f, 30.8f, 32.8f, 34.8f, 100.0f}));
            putInto(sLookupTables, 2.0f, new android.content.res.FontScaleConverterImpl(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{16.0f, 20.0f, 24.0f, 26.0f, 30.0f, 34.0f, 36.0f, 38.0f, 100.0f}));
        }
        sMinScaleBeforeCurvesApplied = getScaleFromKey(sLookupTables.keyAt(0)) - 0.02f;
        if (sMinScaleBeforeCurvesApplied <= 1.0f) {
            throw new java.lang.IllegalStateException("You should only apply non-linear scaling to font scales > 1");
        }
    }

    private FontScaleConverterFactory() {
    }

    public static boolean isNonLinearFontScalingActive(float f) {
        return f >= sMinScaleBeforeCurvesApplied;
    }

    public static android.content.res.FontScaleConverter forScale(float f) {
        if (!isNonLinearFontScalingActive(f)) {
            return null;
        }
        android.content.res.FontScaleConverter fontScaleConverter = get(f);
        if (fontScaleConverter != null) {
            return fontScaleConverter;
        }
        int indexOfKey = sLookupTables.indexOfKey(getKey(f));
        if (indexOfKey >= 0) {
            return sLookupTables.valueAt(indexOfKey);
        }
        int i = (-(indexOfKey + 1)) - 1;
        int i2 = i + 1;
        if (i < 0 || i2 >= sLookupTables.size()) {
            android.content.res.FontScaleConverterImpl fontScaleConverterImpl = new android.content.res.FontScaleConverterImpl(new float[]{1.0f}, new float[]{f});
            if (android.content.res.Flags.fontScaleConverterPublic()) {
                put(f, fontScaleConverterImpl);
            }
            return fontScaleConverterImpl;
        }
        android.content.res.FontScaleConverter createInterpolatedTableBetween = createInterpolatedTableBetween(sLookupTables.valueAt(i), sLookupTables.valueAt(i2), android.util.MathUtils.constrainedMap(0.0f, 1.0f, getScaleFromKey(sLookupTables.keyAt(i)), getScaleFromKey(sLookupTables.keyAt(i2)), f));
        if (android.content.res.Flags.fontScaleConverterPublic()) {
            put(f, createInterpolatedTableBetween);
        }
        return createInterpolatedTableBetween;
    }

    private static android.content.res.FontScaleConverter createInterpolatedTableBetween(android.content.res.FontScaleConverter fontScaleConverter, android.content.res.FontScaleConverter fontScaleConverter2, float f) {
        float[] fArr = {8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f};
        float[] fArr2 = new float[9];
        for (int i = 0; i < 9; i++) {
            float f2 = fArr[i];
            fArr2[i] = android.util.MathUtils.lerp(fontScaleConverter.convertSpToDp(f2), fontScaleConverter2.convertSpToDp(f2), f);
        }
        return new android.content.res.FontScaleConverterImpl(fArr, fArr2);
    }

    private static int getKey(float f) {
        return (int) (f * 100.0f);
    }

    private static float getScaleFromKey(int i) {
        return i / 100.0f;
    }

    private static void put(float f, android.content.res.FontScaleConverter fontScaleConverter) {
        synchronized (LOOKUP_TABLES_WRITE_LOCK) {
            android.util.SparseArray<android.content.res.FontScaleConverter> m4835clone = sLookupTables.m4835clone();
            putInto(m4835clone, f, fontScaleConverter);
            sLookupTables = m4835clone;
        }
    }

    private static void putInto(android.util.SparseArray<android.content.res.FontScaleConverter> sparseArray, float f, android.content.res.FontScaleConverter fontScaleConverter) {
        sparseArray.put(getKey(f), fontScaleConverter);
    }

    private static android.content.res.FontScaleConverter get(float f) {
        return sLookupTables.get(getKey(f));
    }
}
