package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryablePair<T1, T2> implements android.hardware.camera2.marshal.MarshalQueryable<android.util.Pair<T1, T2>> {

    private class MarshalerPair extends android.hardware.camera2.marshal.Marshaler<android.util.Pair<T1, T2>> {
        private final java.lang.Class<? super android.util.Pair<T1, T2>> mClass;
        private final java.lang.reflect.Constructor<android.util.Pair<T1, T2>> mConstructor;
        private final android.hardware.camera2.marshal.Marshaler<T1> mNestedTypeMarshalerFirst;
        private final android.hardware.camera2.marshal.Marshaler<T2> mNestedTypeMarshalerSecond;

        protected MarshalerPair(android.hardware.camera2.utils.TypeReference<android.util.Pair<T1, T2>> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryablePair.this, typeReference, i);
            this.mClass = typeReference.getRawType();
            try {
                java.lang.reflect.ParameterizedType parameterizedType = (java.lang.reflect.ParameterizedType) typeReference.getType();
                this.mNestedTypeMarshalerFirst = android.hardware.camera2.marshal.MarshalRegistry.getMarshaler(android.hardware.camera2.utils.TypeReference.createSpecializedTypeReference(parameterizedType.getActualTypeArguments()[0]), this.mNativeType);
                this.mNestedTypeMarshalerSecond = android.hardware.camera2.marshal.MarshalRegistry.getMarshaler(android.hardware.camera2.utils.TypeReference.createSpecializedTypeReference(parameterizedType.getActualTypeArguments()[1]), this.mNativeType);
                try {
                    this.mConstructor = this.mClass.getConstructor(java.lang.Object.class, java.lang.Object.class);
                } catch (java.lang.NoSuchMethodException e) {
                    throw new java.lang.AssertionError(e);
                }
            } catch (java.lang.ClassCastException e2) {
                throw new java.lang.AssertionError("Raw use of Pair is not supported", e2);
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(android.util.Pair<T1, T2> pair, java.nio.ByteBuffer byteBuffer) {
            if (pair.first == null) {
                throw new java.lang.UnsupportedOperationException("Pair#first must not be null");
            }
            if (pair.second == null) {
                throw new java.lang.UnsupportedOperationException("Pair#second must not be null");
            }
            this.mNestedTypeMarshalerFirst.marshal(pair.first, byteBuffer);
            this.mNestedTypeMarshalerSecond.marshal(pair.second, byteBuffer);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public android.util.Pair<T1, T2> unmarshal(java.nio.ByteBuffer byteBuffer) {
            try {
                return this.mConstructor.newInstance(this.mNestedTypeMarshalerFirst.unmarshal(byteBuffer), this.mNestedTypeMarshalerSecond.unmarshal(byteBuffer));
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
            int nativeSize = this.mNestedTypeMarshalerFirst.getNativeSize();
            int nativeSize2 = this.mNestedTypeMarshalerSecond.getNativeSize();
            if (nativeSize != NATIVE_SIZE_DYNAMIC && nativeSize2 != NATIVE_SIZE_DYNAMIC) {
                return nativeSize + nativeSize2;
            }
            return NATIVE_SIZE_DYNAMIC;
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int calculateMarshalSize(android.util.Pair<T1, T2> pair) {
            int nativeSize = getNativeSize();
            if (nativeSize != NATIVE_SIZE_DYNAMIC) {
                return nativeSize;
            }
            return this.mNestedTypeMarshalerFirst.calculateMarshalSize(pair.first) + this.mNestedTypeMarshalerSecond.calculateMarshalSize(pair.second);
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<android.util.Pair<T1, T2>> createMarshaler(android.hardware.camera2.utils.TypeReference<android.util.Pair<T1, T2>> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryablePair.MarshalerPair(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<android.util.Pair<T1, T2>> typeReference, int i) {
        return android.util.Pair.class.equals(typeReference.getRawType());
    }
}
