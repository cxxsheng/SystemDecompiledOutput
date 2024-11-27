package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableRange<T extends java.lang.Comparable<? super T>> implements android.hardware.camera2.marshal.MarshalQueryable<android.util.Range<T>> {
    private static final int RANGE_COUNT = 2;

    private class MarshalerRange extends android.hardware.camera2.marshal.Marshaler<android.util.Range<T>> {
        private final java.lang.Class<? super android.util.Range<T>> mClass;
        private final java.lang.reflect.Constructor<android.util.Range<T>> mConstructor;
        private final android.hardware.camera2.marshal.Marshaler<T> mNestedTypeMarshaler;

        protected MarshalerRange(android.hardware.camera2.utils.TypeReference<android.util.Range<T>> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableRange.this, typeReference, i);
            this.mClass = typeReference.getRawType();
            try {
                this.mNestedTypeMarshaler = android.hardware.camera2.marshal.MarshalRegistry.getMarshaler(android.hardware.camera2.utils.TypeReference.createSpecializedTypeReference(((java.lang.reflect.ParameterizedType) typeReference.getType()).getActualTypeArguments()[0]), this.mNativeType);
                try {
                    this.mConstructor = this.mClass.getConstructor(java.lang.Comparable.class, java.lang.Comparable.class);
                } catch (java.lang.NoSuchMethodException e) {
                    throw new java.lang.AssertionError(e);
                }
            } catch (java.lang.ClassCastException e2) {
                throw new java.lang.AssertionError("Raw use of Range is not supported", e2);
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(android.util.Range<T> range, java.nio.ByteBuffer byteBuffer) {
            this.mNestedTypeMarshaler.marshal(range.getLower(), byteBuffer);
            this.mNestedTypeMarshaler.marshal(range.getUpper(), byteBuffer);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public android.util.Range<T> unmarshal(java.nio.ByteBuffer byteBuffer) {
            try {
                return this.mConstructor.newInstance(this.mNestedTypeMarshaler.unmarshal(byteBuffer), this.mNestedTypeMarshaler.unmarshal(byteBuffer));
            } catch (java.lang.IllegalAccessException e) {
                throw new java.lang.AssertionError(e);
            } catch (java.lang.IllegalArgumentException e2) {
                throw new java.lang.AssertionError(e2);
            } catch (java.lang.InstantiationException e3) {
                throw new java.lang.AssertionError(e3);
            } catch (java.lang.reflect.InvocationTargetException e4) {
                throw new java.lang.AssertionError(e4);
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            int nativeSize = this.mNestedTypeMarshaler.getNativeSize();
            if (nativeSize != NATIVE_SIZE_DYNAMIC) {
                return nativeSize * 2;
            }
            return NATIVE_SIZE_DYNAMIC;
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int calculateMarshalSize(android.util.Range<T> range) {
            int nativeSize = getNativeSize();
            if (nativeSize != NATIVE_SIZE_DYNAMIC) {
                return nativeSize;
            }
            return this.mNestedTypeMarshaler.calculateMarshalSize(range.getLower()) + this.mNestedTypeMarshaler.calculateMarshalSize(range.getUpper());
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<android.util.Range<T>> createMarshaler(android.hardware.camera2.utils.TypeReference<android.util.Range<T>> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableRange.MarshalerRange(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<android.util.Range<T>> typeReference, int i) {
        return android.util.Range.class.equals(typeReference.getRawType());
    }
}
