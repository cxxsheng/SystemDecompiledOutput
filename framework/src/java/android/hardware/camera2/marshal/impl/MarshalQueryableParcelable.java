package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableParcelable<T extends android.os.Parcelable> implements android.hardware.camera2.marshal.MarshalQueryable<T> {
    private static final boolean DEBUG = false;
    private static final java.lang.String FIELD_CREATOR = "CREATOR";
    private static final java.lang.String TAG = "MarshalParcelable";

    private class MarshalerParcelable extends android.hardware.camera2.marshal.Marshaler<T> {
        private final java.lang.Class<T> mClass;
        private final android.os.Parcelable.Creator<T> mCreator;

        protected MarshalerParcelable(android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableParcelable.this, typeReference, i);
            this.mClass = typeReference.getRawType();
            try {
                try {
                    this.mCreator = (android.os.Parcelable.Creator) this.mClass.getDeclaredField(android.hardware.camera2.marshal.impl.MarshalQueryableParcelable.FIELD_CREATOR).get(null);
                } catch (java.lang.IllegalAccessException e) {
                    throw new java.lang.AssertionError(e);
                } catch (java.lang.IllegalArgumentException e2) {
                    throw new java.lang.AssertionError(e2);
                }
            } catch (java.lang.NoSuchFieldException e3) {
                throw new java.lang.AssertionError(e3);
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(T t, java.nio.ByteBuffer byteBuffer) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            try {
                t.writeToParcel(obtain, 0);
                if (obtain.hasFileDescriptors()) {
                    throw new java.lang.UnsupportedOperationException("Parcelable " + t + " must not have file descriptors");
                }
                byte[] marshall = obtain.marshall();
                obtain.recycle();
                if (marshall.length == 0) {
                    throw new java.lang.AssertionError("No data marshaled for " + t);
                }
                byteBuffer.put(marshall);
            } catch (java.lang.Throwable th) {
                obtain.recycle();
                throw th;
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public T unmarshal(java.nio.ByteBuffer byteBuffer) {
            byteBuffer.mark();
            android.os.Parcel obtain = android.os.Parcel.obtain();
            try {
                int remaining = byteBuffer.remaining();
                byte[] bArr = new byte[remaining];
                byteBuffer.get(bArr);
                obtain.unmarshall(bArr, 0, remaining);
                obtain.setDataPosition(0);
                T createFromParcel = this.mCreator.createFromParcel(obtain);
                int dataPosition = obtain.dataPosition();
                if (dataPosition == 0) {
                    throw new java.lang.AssertionError("No data marshaled for " + createFromParcel);
                }
                byteBuffer.reset();
                byteBuffer.position(byteBuffer.position() + dataPosition);
                return this.mClass.cast(createFromParcel);
            } finally {
                obtain.recycle();
            }
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return NATIVE_SIZE_DYNAMIC;
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int calculateMarshalSize(T t) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            try {
                t.writeToParcel(obtain, 0);
                return obtain.marshall().length;
            } finally {
                obtain.recycle();
            }
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<T> createMarshaler(android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableParcelable.MarshalerParcelable(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<T> typeReference, int i) {
        return android.os.Parcelable.class.isAssignableFrom(typeReference.getRawType());
    }
}
