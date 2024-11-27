package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableEnum<T extends java.lang.Enum<T>> implements android.hardware.camera2.marshal.MarshalQueryable<T> {
    private static final boolean DEBUG = false;
    private static final int UINT8_MASK = 255;
    private static final int UINT8_MAX = 255;
    private static final int UINT8_MIN = 0;
    private static final java.lang.String TAG = android.hardware.camera2.marshal.impl.MarshalQueryableEnum.class.getSimpleName();
    private static final java.util.HashMap<java.lang.Class<? extends java.lang.Enum>, int[]> sEnumValues = new java.util.HashMap<>();

    private class MarshalerEnum extends android.hardware.camera2.marshal.Marshaler<T> {
        private final java.lang.Class<T> mClass;

        protected MarshalerEnum(android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableEnum.this, typeReference, i);
            this.mClass = typeReference.getRawType();
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(T t, java.nio.ByteBuffer byteBuffer) {
            int enumValue = android.hardware.camera2.marshal.impl.MarshalQueryableEnum.getEnumValue(t);
            if (this.mNativeType == 1) {
                byteBuffer.putInt(enumValue);
            } else {
                if (this.mNativeType == 0) {
                    if (enumValue < 0 || enumValue > 255) {
                        throw new java.lang.UnsupportedOperationException(java.lang.String.format("Enum value %x too large to fit into unsigned byte", java.lang.Integer.valueOf(enumValue)));
                    }
                    byteBuffer.put((byte) enumValue);
                    return;
                }
                throw new java.lang.AssertionError();
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public T unmarshal(java.nio.ByteBuffer byteBuffer) {
            int i;
            switch (this.mNativeType) {
                case 0:
                    i = byteBuffer.get() & 255;
                    break;
                case 1:
                    i = byteBuffer.getInt();
                    break;
                default:
                    throw new java.lang.AssertionError("Unexpected native type; impossible since its not supported");
            }
            return (T) android.hardware.camera2.marshal.impl.MarshalQueryableEnum.getEnumFromValue(this.mClass, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return android.hardware.camera2.marshal.MarshalHelpers.getPrimitiveTypeSize(this.mNativeType);
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<T> createMarshaler(android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableEnum.MarshalerEnum(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
        if ((i == 1 || i == 0) && (typeReference.getType() instanceof java.lang.Class)) {
            java.lang.Class cls = (java.lang.Class) typeReference.getType();
            if (cls.isEnum()) {
                try {
                    cls.getDeclaredConstructor(java.lang.String.class, java.lang.Integer.TYPE);
                    return true;
                } catch (java.lang.NoSuchMethodException e) {
                    android.util.Log.e(TAG, "Can't marshal class " + cls + "; no default constructor");
                } catch (java.lang.SecurityException e2) {
                    android.util.Log.e(TAG, "Can't marshal class " + cls + "; not accessible");
                }
            }
        }
        return false;
    }

    public static <T extends java.lang.Enum<T>> void registerEnumValues(java.lang.Class<T> cls, int[] iArr) {
        if (cls.getEnumConstants().length != iArr.length) {
            throw new java.lang.IllegalArgumentException("Expected values array to be the same size as the enumTypes values " + iArr.length + " for type " + cls);
        }
        sEnumValues.put(cls, iArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends java.lang.Enum<T>> int getEnumValue(T t) {
        int[] iArr = sEnumValues.get(t.getClass());
        int ordinal = t.ordinal();
        if (iArr != null) {
            return iArr[ordinal];
        }
        return ordinal;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends java.lang.Enum<T>> T getEnumFromValue(java.lang.Class<T> cls, int i) {
        int i2;
        int[] iArr = sEnumValues.get(cls);
        if (iArr != null) {
            i2 = 0;
            while (true) {
                if (i2 >= iArr.length) {
                    i2 = -1;
                    break;
                }
                if (iArr[i2] == i) {
                    break;
                }
                i2++;
            }
        } else {
            i2 = i;
        }
        T[] enumConstants = cls.getEnumConstants();
        if (i2 < 0 || i2 >= enumConstants.length) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Argument 'value' (%d) was not a valid enum value for type %s (registered? %b)", java.lang.Integer.valueOf(i), cls, java.lang.Boolean.valueOf(iArr != null)));
        }
        return enumConstants[i2];
    }
}
