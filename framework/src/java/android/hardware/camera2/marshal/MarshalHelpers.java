package android.hardware.camera2.marshal;

/* loaded from: classes.dex */
public final class MarshalHelpers {
    public static final int SIZEOF_BYTE = 1;
    public static final int SIZEOF_DOUBLE = 8;
    public static final int SIZEOF_FLOAT = 4;
    public static final int SIZEOF_INT32 = 4;
    public static final int SIZEOF_INT64 = 8;
    public static final int SIZEOF_RATIONAL = 8;

    public static int getPrimitiveTypeSize(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 4;
            case 2:
                return 4;
            case 3:
                return 8;
            case 4:
                return 8;
            case 5:
                return 8;
            default:
                throw new java.lang.UnsupportedOperationException("Unknown type, can't get size for " + i);
        }
    }

    public static <T> java.lang.Class<T> checkPrimitiveClass(java.lang.Class<T> cls) {
        com.android.internal.util.Preconditions.checkNotNull(cls, "klass must not be null");
        if (isPrimitiveClass(cls)) {
            return cls;
        }
        throw new java.lang.UnsupportedOperationException("Unsupported class '" + cls + "'; expected a metadata primitive class");
    }

    public static boolean isUnwrappedPrimitiveClass(java.lang.Class<?> cls) {
        if (cls == null) {
            return false;
        }
        return cls == java.lang.Byte.TYPE || cls == java.lang.Integer.TYPE || cls == java.lang.Float.TYPE || cls == java.lang.Long.TYPE || cls == java.lang.Double.TYPE;
    }

    public static <T> boolean isPrimitiveClass(java.lang.Class<T> cls) {
        if (cls == null) {
            return false;
        }
        return cls == java.lang.Byte.TYPE || cls == java.lang.Byte.class || cls == java.lang.Integer.TYPE || cls == java.lang.Integer.class || cls == java.lang.Float.TYPE || cls == java.lang.Float.class || cls == java.lang.Long.TYPE || cls == java.lang.Long.class || cls == java.lang.Double.TYPE || cls == java.lang.Double.class || cls == android.util.Rational.class;
    }

    public static <T> java.lang.Class<T> wrapClassIfPrimitive(java.lang.Class<T> cls) {
        if (cls == java.lang.Byte.TYPE) {
            return java.lang.Byte.class;
        }
        if (cls == java.lang.Integer.TYPE) {
            return java.lang.Integer.class;
        }
        if (cls == java.lang.Float.TYPE) {
            return java.lang.Float.class;
        }
        if (cls == java.lang.Long.TYPE) {
            return java.lang.Long.class;
        }
        if (cls == java.lang.Double.TYPE) {
            return java.lang.Double.class;
        }
        return cls;
    }

    public static java.lang.String toStringNativeType(int i) {
        switch (i) {
            case 0:
                return "TYPE_BYTE";
            case 1:
                return "TYPE_INT32";
            case 2:
                return "TYPE_FLOAT";
            case 3:
                return "TYPE_INT64";
            case 4:
                return "TYPE_DOUBLE";
            case 5:
                return "TYPE_RATIONAL";
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static int checkNativeType(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return i;
            default:
                throw new java.lang.UnsupportedOperationException("Unknown nativeType " + i);
        }
    }

    public static java.lang.Class<?> getPrimitiveTypeClass(int i) {
        switch (i) {
            case 0:
                return java.lang.Byte.TYPE;
            case 1:
                return java.lang.Integer.TYPE;
            case 2:
                return java.lang.Float.TYPE;
            case 3:
                return java.lang.Long.TYPE;
            case 4:
                return java.lang.Double.TYPE;
            default:
                throw new java.lang.UnsupportedOperationException("Unknown nativeType " + i);
        }
    }

    public static int checkNativeTypeEquals(int i, int i2) {
        if (i != i2) {
            throw new java.lang.UnsupportedOperationException(java.lang.String.format("Expected native type %d, but got %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
        }
        return i2;
    }

    private MarshalHelpers() {
        throw new java.lang.AssertionError();
    }
}
