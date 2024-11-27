package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableSize implements android.hardware.camera2.marshal.MarshalQueryable<android.util.Size> {
    private static final int SIZE = 8;

    private class MarshalerSize extends android.hardware.camera2.marshal.Marshaler<android.util.Size> {
        protected MarshalerSize(android.hardware.camera2.utils.TypeReference<android.util.Size> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableSize.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(android.util.Size size, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.putInt(size.getWidth());
            byteBuffer.putInt(size.getHeight());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public android.util.Size unmarshal(java.nio.ByteBuffer byteBuffer) {
            return new android.util.Size(byteBuffer.getInt(), byteBuffer.getInt());
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 8;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<android.util.Size> createMarshaler(android.hardware.camera2.utils.TypeReference<android.util.Size> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableSize.MarshalerSize(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<android.util.Size> typeReference, int i) {
        return i == 1 && android.util.Size.class.equals(typeReference.getType());
    }
}
