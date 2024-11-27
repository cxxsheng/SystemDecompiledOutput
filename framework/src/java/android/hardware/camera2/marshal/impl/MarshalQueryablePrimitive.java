package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public final class MarshalQueryablePrimitive<T> implements android.hardware.camera2.marshal.MarshalQueryable<T> {

    private class MarshalerPrimitive extends android.hardware.camera2.marshal.Marshaler<T> {
        private final java.lang.Class<T> mClass;

        protected MarshalerPrimitive(android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryablePrimitive.this, typeReference, i);
            this.mClass = android.hardware.camera2.marshal.MarshalHelpers.wrapClassIfPrimitive(typeReference.getRawType());
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public T unmarshal(java.nio.ByteBuffer byteBuffer) {
            return this.mClass.cast(unmarshalObject(byteBuffer));
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int calculateMarshalSize(T t) {
            return android.hardware.camera2.marshal.MarshalHelpers.getPrimitiveTypeSize(this.mNativeType);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(T t, java.nio.ByteBuffer byteBuffer) {
            if (t instanceof java.lang.Integer) {
                android.hardware.camera2.marshal.MarshalHelpers.checkNativeTypeEquals(1, this.mNativeType);
                marshalPrimitive(((java.lang.Integer) t).intValue(), byteBuffer);
                return;
            }
            if (t instanceof java.lang.Float) {
                android.hardware.camera2.marshal.MarshalHelpers.checkNativeTypeEquals(2, this.mNativeType);
                marshalPrimitive(((java.lang.Float) t).floatValue(), byteBuffer);
                return;
            }
            if (t instanceof java.lang.Long) {
                android.hardware.camera2.marshal.MarshalHelpers.checkNativeTypeEquals(3, this.mNativeType);
                marshalPrimitive(((java.lang.Long) t).longValue(), byteBuffer);
                return;
            }
            if (t instanceof android.util.Rational) {
                android.hardware.camera2.marshal.MarshalHelpers.checkNativeTypeEquals(5, this.mNativeType);
                marshalPrimitive((android.util.Rational) t, byteBuffer);
            } else if (t instanceof java.lang.Double) {
                android.hardware.camera2.marshal.MarshalHelpers.checkNativeTypeEquals(4, this.mNativeType);
                marshalPrimitive(((java.lang.Double) t).doubleValue(), byteBuffer);
            } else {
                if (t instanceof java.lang.Byte) {
                    android.hardware.camera2.marshal.MarshalHelpers.checkNativeTypeEquals(0, this.mNativeType);
                    marshalPrimitive(((java.lang.Byte) t).byteValue(), byteBuffer);
                    return;
                }
                throw new java.lang.UnsupportedOperationException("Can't marshal managed type " + this.mTypeReference);
            }
        }

        private void marshalPrimitive(int i, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.putInt(i);
        }

        private void marshalPrimitive(float f, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.putFloat(f);
        }

        private void marshalPrimitive(double d, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.putDouble(d);
        }

        private void marshalPrimitive(long j, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.putLong(j);
        }

        private void marshalPrimitive(android.util.Rational rational, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.putInt(rational.getNumerator());
            byteBuffer.putInt(rational.getDenominator());
        }

        private void marshalPrimitive(byte b, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.put(b);
        }

        private java.lang.Object unmarshalObject(java.nio.ByteBuffer byteBuffer) {
            switch (this.mNativeType) {
                case 0:
                    return java.lang.Byte.valueOf(byteBuffer.get());
                case 1:
                    return java.lang.Integer.valueOf(byteBuffer.getInt());
                case 2:
                    return java.lang.Float.valueOf(byteBuffer.getFloat());
                case 3:
                    return java.lang.Long.valueOf(byteBuffer.getLong());
                case 4:
                    return java.lang.Double.valueOf(byteBuffer.getDouble());
                case 5:
                    return new android.util.Rational(byteBuffer.getInt(), byteBuffer.getInt());
                default:
                    throw new java.lang.UnsupportedOperationException("Can't unmarshal native type " + this.mNativeType);
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return android.hardware.camera2.marshal.MarshalHelpers.getPrimitiveTypeSize(this.mNativeType);
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<T> createMarshaler(android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryablePrimitive.MarshalerPrimitive(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
        if (typeReference.getType() instanceof java.lang.Class) {
            java.lang.Class cls = (java.lang.Class) typeReference.getType();
            return (cls == java.lang.Byte.TYPE || cls == java.lang.Byte.class) ? i == 0 : (cls == java.lang.Integer.TYPE || cls == java.lang.Integer.class) ? i == 1 : (cls == java.lang.Float.TYPE || cls == java.lang.Float.class) ? i == 2 : (cls == java.lang.Long.TYPE || cls == java.lang.Long.class) ? i == 3 : (cls == java.lang.Double.TYPE || cls == java.lang.Double.class) ? i == 4 : cls == android.util.Rational.class && i == 5;
        }
        return false;
    }
}
