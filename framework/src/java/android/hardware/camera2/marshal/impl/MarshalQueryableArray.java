package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableArray<T> implements android.hardware.camera2.marshal.MarshalQueryable<T> {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = android.hardware.camera2.marshal.impl.MarshalQueryableArray.class.getSimpleName();

    private interface PrimitiveArrayFiller {
        void fillArray(java.lang.Object obj, int i, java.nio.ByteBuffer byteBuffer);

        static android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller getPrimitiveArrayFiller(java.lang.Class<?> cls) {
            if (cls == java.lang.Integer.TYPE) {
                return new android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller() { // from class: android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller.1
                    @Override // android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller
                    public void fillArray(java.lang.Object obj, int i, java.nio.ByteBuffer byteBuffer) {
                        byteBuffer.position(byteBuffer.position() + (byteBuffer.asIntBuffer().get((int[]) int[].class.cast(obj), 0, i).position() * 4));
                    }
                };
            }
            if (cls == java.lang.Float.TYPE) {
                return new android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller() { // from class: android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller.2
                    @Override // android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller
                    public void fillArray(java.lang.Object obj, int i, java.nio.ByteBuffer byteBuffer) {
                        byteBuffer.position(byteBuffer.position() + (byteBuffer.asFloatBuffer().get((float[]) float[].class.cast(obj), 0, i).position() * 4));
                    }
                };
            }
            if (cls == java.lang.Long.TYPE) {
                return new android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller() { // from class: android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller.3
                    @Override // android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller
                    public void fillArray(java.lang.Object obj, int i, java.nio.ByteBuffer byteBuffer) {
                        byteBuffer.position(byteBuffer.position() + (byteBuffer.asLongBuffer().get((long[]) long[].class.cast(obj), 0, i).position() * 8));
                    }
                };
            }
            if (cls == java.lang.Double.TYPE) {
                return new android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller() { // from class: android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller.4
                    @Override // android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller
                    public void fillArray(java.lang.Object obj, int i, java.nio.ByteBuffer byteBuffer) {
                        byteBuffer.position(byteBuffer.position() + (byteBuffer.asDoubleBuffer().get((double[]) double[].class.cast(obj), 0, i).position() * 8));
                    }
                };
            }
            if (cls == java.lang.Byte.TYPE) {
                return new android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller() { // from class: android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller.5
                    @Override // android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller
                    public void fillArray(java.lang.Object obj, int i, java.nio.ByteBuffer byteBuffer) {
                        byteBuffer.get((byte[]) byte[].class.cast(obj), 0, i);
                    }
                };
            }
            throw new java.lang.UnsupportedOperationException("PrimitiveArrayFiller of type " + cls.getName() + " not supported");
        }
    }

    private class MarshalerArray extends android.hardware.camera2.marshal.Marshaler<T> {
        private final java.lang.Class<T> mClass;
        private final java.lang.Class<?> mComponentClass;
        private final android.hardware.camera2.marshal.Marshaler<?> mComponentMarshaler;

        protected MarshalerArray(android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableArray.this, typeReference, i);
            this.mClass = typeReference.getRawType();
            android.hardware.camera2.utils.TypeReference<?> componentType = typeReference.getComponentType();
            this.mComponentMarshaler = android.hardware.camera2.marshal.MarshalRegistry.getMarshaler(componentType, this.mNativeType);
            this.mComponentClass = componentType.getRawType();
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(T t, java.nio.ByteBuffer byteBuffer) {
            int length = java.lang.reflect.Array.getLength(t);
            for (int i = 0; i < length; i++) {
                marshalArrayElement(this.mComponentMarshaler, byteBuffer, t, i);
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public T unmarshal(java.nio.ByteBuffer byteBuffer) {
            java.lang.Object copyListToArray;
            int nativeSize = this.mComponentMarshaler.getNativeSize();
            if (nativeSize != android.hardware.camera2.marshal.Marshaler.NATIVE_SIZE_DYNAMIC) {
                int remaining = byteBuffer.remaining();
                int i = remaining / nativeSize;
                int i2 = remaining % nativeSize;
                if (i2 != 0) {
                    throw new java.lang.UnsupportedOperationException("Arrays for " + this.mTypeReference + " must be packed tighly into a multiple of " + nativeSize + "; but there are " + i2 + " left over bytes");
                }
                copyListToArray = java.lang.reflect.Array.newInstance(this.mComponentClass, i);
                if (android.hardware.camera2.marshal.MarshalHelpers.isUnwrappedPrimitiveClass(this.mComponentClass) && this.mComponentClass == android.hardware.camera2.marshal.MarshalHelpers.getPrimitiveTypeClass(this.mNativeType)) {
                    android.hardware.camera2.marshal.impl.MarshalQueryableArray.PrimitiveArrayFiller.getPrimitiveArrayFiller(this.mComponentClass).fillArray(copyListToArray, i, byteBuffer);
                } else {
                    for (int i3 = 0; i3 < i; i3++) {
                        java.lang.reflect.Array.set(copyListToArray, i3, this.mComponentMarshaler.unmarshal(byteBuffer));
                    }
                }
            } else {
                java.util.ArrayList<?> arrayList = new java.util.ArrayList<>();
                while (byteBuffer.hasRemaining()) {
                    arrayList.add(this.mComponentMarshaler.unmarshal(byteBuffer));
                }
                copyListToArray = copyListToArray(arrayList, java.lang.reflect.Array.newInstance(this.mComponentClass, arrayList.size()));
            }
            if (byteBuffer.remaining() != 0) {
                android.util.Log.e(android.hardware.camera2.marshal.impl.MarshalQueryableArray.TAG, "Trailing bytes (" + byteBuffer.remaining() + ") left over after unpacking " + this.mClass);
            }
            return this.mClass.cast(copyListToArray);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return NATIVE_SIZE_DYNAMIC;
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int calculateMarshalSize(T t) {
            int nativeSize = this.mComponentMarshaler.getNativeSize();
            int length = java.lang.reflect.Array.getLength(t);
            if (nativeSize != android.hardware.camera2.marshal.Marshaler.NATIVE_SIZE_DYNAMIC) {
                return nativeSize * length;
            }
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += calculateElementMarshalSize(this.mComponentMarshaler, t, i2);
            }
            return i;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private <TElem> void marshalArrayElement(android.hardware.camera2.marshal.Marshaler<TElem> marshaler, java.nio.ByteBuffer byteBuffer, java.lang.Object obj, int i) {
            marshaler.marshal(java.lang.reflect.Array.get(obj, i), byteBuffer);
        }

        private java.lang.Object copyListToArray(java.util.ArrayList<?> arrayList, java.lang.Object obj) {
            return arrayList.toArray((java.lang.Object[]) obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private <TElem> int calculateElementMarshalSize(android.hardware.camera2.marshal.Marshaler<TElem> marshaler, java.lang.Object obj, int i) {
            return marshaler.calculateMarshalSize(java.lang.reflect.Array.get(obj, i));
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<T> createMarshaler(android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableArray.MarshalerArray(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
        return typeReference.getRawType().isArray();
    }
}
