package android.util;

/* loaded from: classes3.dex */
public class TypedValue {
    public static final int COMPLEX_MANTISSA_MASK = 16777215;
    public static final int COMPLEX_MANTISSA_SHIFT = 8;
    public static final int COMPLEX_RADIX_0p23 = 3;
    public static final int COMPLEX_RADIX_16p7 = 1;
    public static final int COMPLEX_RADIX_23p0 = 0;
    public static final int COMPLEX_RADIX_8p15 = 2;
    public static final int COMPLEX_RADIX_MASK = 3;
    public static final int COMPLEX_RADIX_SHIFT = 4;
    public static final int COMPLEX_UNIT_DIP = 1;
    public static final int COMPLEX_UNIT_FRACTION = 0;
    public static final int COMPLEX_UNIT_FRACTION_PARENT = 1;
    public static final int COMPLEX_UNIT_IN = 4;
    public static final int COMPLEX_UNIT_MASK = 15;
    public static final int COMPLEX_UNIT_MM = 5;
    public static final int COMPLEX_UNIT_PT = 3;
    public static final int COMPLEX_UNIT_PX = 0;
    public static final int COMPLEX_UNIT_SHIFT = 0;
    public static final int COMPLEX_UNIT_SP = 2;
    public static final int DATA_NULL_EMPTY = 1;
    public static final int DATA_NULL_UNDEFINED = 0;
    public static final int DENSITY_DEFAULT = 0;
    public static final int DENSITY_NONE = 65535;
    private static final float INCHES_PER_MM = 0.03937008f;
    private static final float INCHES_PER_PT = 0.013888889f;
    private static final float MANTISSA_MULT = 0.00390625f;
    public static final int TYPE_ATTRIBUTE = 2;
    public static final int TYPE_DIMENSION = 5;
    public static final int TYPE_FIRST_COLOR_INT = 28;
    public static final int TYPE_FIRST_INT = 16;
    public static final int TYPE_FLOAT = 4;
    public static final int TYPE_FRACTION = 6;
    public static final int TYPE_INT_BOOLEAN = 18;
    public static final int TYPE_INT_COLOR_ARGB4 = 30;
    public static final int TYPE_INT_COLOR_ARGB8 = 28;
    public static final int TYPE_INT_COLOR_RGB4 = 31;
    public static final int TYPE_INT_COLOR_RGB8 = 29;
    public static final int TYPE_INT_DEC = 16;
    public static final int TYPE_INT_HEX = 17;
    public static final int TYPE_LAST_COLOR_INT = 31;
    public static final int TYPE_LAST_INT = 31;
    public static final int TYPE_NULL = 0;
    public static final int TYPE_REFERENCE = 1;
    public static final int TYPE_STRING = 3;
    public int assetCookie;
    public int changingConfigurations = -1;
    public int data;
    public int density;
    public int resourceId;
    public int sourceResourceId;
    public java.lang.CharSequence string;
    public int type;
    private static final float[] RADIX_MULTS = {0.00390625f, 3.0517578E-5f, 1.1920929E-7f, 4.656613E-10f};
    private static final java.lang.String[] DIMENSION_UNIT_STRS = {"px", "dip", android.app.backup.FullBackup.SHAREDPREFS_TREE_TOKEN, "pt", "in", "mm"};
    private static final java.lang.String[] FRACTION_UNIT_STRS = {"%", "%p"};

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ComplexDimensionUnit {
    }

    public final float getFloat() {
        return java.lang.Float.intBitsToFloat(this.data);
    }

    public boolean isColorType() {
        return this.type >= 28 && this.type <= 31;
    }

    public static float complexToFloat(int i) {
        return (i & (-256)) * RADIX_MULTS[(i >> 4) & 3];
    }

    public static float complexToDimension(int i, android.util.DisplayMetrics displayMetrics) {
        return applyDimension((i >> 0) & 15, complexToFloat(i), displayMetrics);
    }

    public static int complexToDimensionPixelOffset(int i, android.util.DisplayMetrics displayMetrics) {
        return (int) applyDimension((i >> 0) & 15, complexToFloat(i), displayMetrics);
    }

    public static int complexToDimensionPixelSize(int i, android.util.DisplayMetrics displayMetrics) {
        float complexToFloat = complexToFloat(i);
        float applyDimension = applyDimension((i >> 0) & 15, complexToFloat, displayMetrics);
        int i2 = (int) (applyDimension >= 0.0f ? applyDimension + 0.5f : applyDimension - 0.5f);
        if (i2 != 0) {
            return i2;
        }
        if (complexToFloat == 0.0f) {
            return 0;
        }
        return complexToFloat > 0.0f ? 1 : -1;
    }

    @java.lang.Deprecated
    public static float complexToDimensionNoisy(int i, android.util.DisplayMetrics displayMetrics) {
        return complexToDimension(i, displayMetrics);
    }

    public int getComplexUnit() {
        return getUnitFromComplexDimension(this.data);
    }

    public static int getUnitFromComplexDimension(int i) {
        return (i >> 0) & 15;
    }

    public static float applyDimension(int i, float f, android.util.DisplayMetrics displayMetrics) {
        switch (i) {
            case 0:
                return f;
            case 1:
                return f * displayMetrics.density;
            case 2:
                if (displayMetrics.fontScaleConverter != null) {
                    return applyDimension(1, displayMetrics.fontScaleConverter.convertSpToDp(f), displayMetrics);
                }
                return f * displayMetrics.scaledDensity;
            case 3:
                return f * displayMetrics.xdpi * INCHES_PER_PT;
            case 4:
                return f * displayMetrics.xdpi;
            case 5:
                return f * displayMetrics.xdpi * INCHES_PER_MM;
            default:
                return 0.0f;
        }
    }

    public static float deriveDimension(int i, float f, android.util.DisplayMetrics displayMetrics) {
        switch (i) {
            case 0:
                return f;
            case 1:
                if (displayMetrics.density == 0.0f) {
                    return 0.0f;
                }
                return f / displayMetrics.density;
            case 2:
                if (displayMetrics.fontScaleConverter != null) {
                    return displayMetrics.fontScaleConverter.convertDpToSp(deriveDimension(1, f, displayMetrics));
                }
                if (displayMetrics.scaledDensity == 0.0f) {
                    return 0.0f;
                }
                return f / displayMetrics.scaledDensity;
            case 3:
                if (displayMetrics.xdpi == 0.0f) {
                    return 0.0f;
                }
                return (f / displayMetrics.xdpi) / INCHES_PER_PT;
            case 4:
                if (displayMetrics.xdpi == 0.0f) {
                    return 0.0f;
                }
                return f / displayMetrics.xdpi;
            case 5:
                if (displayMetrics.xdpi == 0.0f) {
                    return 0.0f;
                }
                return (f / displayMetrics.xdpi) / INCHES_PER_MM;
            default:
                throw new java.lang.IllegalArgumentException("Invalid unitToConvertTo " + i);
        }
    }

    public static float convertPixelsToDimension(int i, float f, android.util.DisplayMetrics displayMetrics) {
        return deriveDimension(i, f, displayMetrics);
    }

    public static float convertDimensionToPixels(int i, float f, android.util.DisplayMetrics displayMetrics) {
        return applyDimension(i, f, displayMetrics);
    }

    public float getDimension(android.util.DisplayMetrics displayMetrics) {
        return complexToDimension(this.data, displayMetrics);
    }

    private static int createComplex(int i, int i2) {
        if (i < -8388608 || i >= 8388608) {
            throw new java.lang.IllegalArgumentException("Magnitude of mantissa is too large: " + i);
        }
        if (i2 < 0 || i2 > 3) {
            throw new java.lang.IllegalArgumentException("Invalid radix: " + i2);
        }
        return ((i & 16777215) << 8) | (i2 << 4);
    }

    public static int intToComplex(int i) {
        if (i < -8388608 || i >= 8388608) {
            throw new java.lang.IllegalArgumentException("Magnitude of the value is too large: " + i);
        }
        return createComplex(i, 0);
    }

    public static int floatToComplex(float f) {
        if (f < -8388608.0f || f >= 8388607.5f) {
            throw new java.lang.IllegalArgumentException("Magnitude of the value is too large: " + f);
        }
        int i = (int) f;
        try {
            if (f == i) {
                return createComplex(i, 0);
            }
            float abs = java.lang.Math.abs(f);
            if (abs < 1.0f) {
                return createComplex(java.lang.Math.round(8388608.0f * f), 3);
            }
            if (abs < 256.0f) {
                return createComplex(java.lang.Math.round(32768.0f * f), 2);
            }
            if (abs < 65536.0f) {
                return createComplex(java.lang.Math.round(128.0f * f), 1);
            }
            return createComplex(java.lang.Math.round(f), 0);
        } catch (java.lang.IllegalArgumentException e) {
            throw new java.lang.IllegalArgumentException("Unable to convert value to complex: " + f, e);
        }
    }

    public static int createComplexDimension(int i, int i2) {
        if (i2 < 0 || i2 > 5) {
            throw new java.lang.IllegalArgumentException("Must be a valid COMPLEX_UNIT_*: " + i2);
        }
        return intToComplex(i) | i2;
    }

    public static int createComplexDimension(float f, int i) {
        if (i < 0 || i > 5) {
            throw new java.lang.IllegalArgumentException("Must be a valid COMPLEX_UNIT_*: " + i);
        }
        return floatToComplex(f) | i;
    }

    public static float complexToFraction(int i, float f, float f2) {
        switch ((i >> 0) & 15) {
            case 0:
                return complexToFloat(i) * f;
            case 1:
                return complexToFloat(i) * f2;
            default:
                return 0.0f;
        }
    }

    public float getFraction(float f, float f2) {
        return complexToFraction(this.data, f, f2);
    }

    public final java.lang.CharSequence coerceToString() {
        int i = this.type;
        if (i == 3) {
            return this.string;
        }
        return coerceToString(i, this.data);
    }

    public static final java.lang.String coerceToString(int i, int i2) {
        switch (i) {
            case 0:
                return null;
            case 1:
                return "@" + i2;
            case 2:
                return "?" + i2;
            case 4:
                return java.lang.Float.toString(java.lang.Float.intBitsToFloat(i2));
            case 5:
                return java.lang.Float.toString(complexToFloat(i2)) + DIMENSION_UNIT_STRS[(i2 >> 0) & 15];
            case 6:
                return java.lang.Float.toString(complexToFloat(i2) * 100.0f) + FRACTION_UNIT_STRS[(i2 >> 0) & 15];
            case 17:
                return "0x" + java.lang.Integer.toHexString(i2);
            case 18:
                return i2 != 0 ? "true" : "false";
            default:
                if (i >= 28 && i <= 31) {
                    return "#" + java.lang.Integer.toHexString(i2);
                }
                if (i < 16 || i > 31) {
                    return null;
                }
                return java.lang.Integer.toString(i2);
        }
    }

    public void setTo(android.util.TypedValue typedValue) {
        this.type = typedValue.type;
        this.string = typedValue.string;
        this.data = typedValue.data;
        this.assetCookie = typedValue.assetCookie;
        this.resourceId = typedValue.resourceId;
        this.density = typedValue.density;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("TypedValue{t=0x").append(java.lang.Integer.toHexString(this.type));
        sb.append("/d=0x").append(java.lang.Integer.toHexString(this.data));
        if (this.type == 3) {
            sb.append(" \"").append(this.string != null ? this.string : "<null>").append("\"");
        }
        if (this.assetCookie != 0) {
            sb.append(" a=").append(this.assetCookie);
        }
        if (this.resourceId != 0) {
            sb.append(" r=0x").append(java.lang.Integer.toHexString(this.resourceId));
        }
        sb.append("}");
        return sb.toString();
    }
}
