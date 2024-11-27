package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableSizeF implements android.hardware.camera2.marshal.MarshalQueryable<android.util.SizeF> {
    private static final int SIZE = 8;

    private class MarshalerSizeF extends android.hardware.camera2.marshal.Marshaler<android.util.SizeF> {
        protected MarshalerSizeF(android.hardware.camera2.utils.TypeReference<android.util.SizeF> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableSizeF.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(android.util.SizeF sizeF, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.putFloat(sizeF.getWidth());
            byteBuffer.putFloat(sizeF.getHeight());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public android.util.SizeF unmarshal(java.nio.ByteBuffer byteBuffer) {
            return new android.util.SizeF(byteBuffer.getFloat(), byteBuffer.getFloat());
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 8;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<android.util.SizeF> createMarshaler(android.hardware.camera2.utils.TypeReference<android.util.SizeF> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableSizeF.MarshalerSizeF(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<android.util.SizeF> typeReference, int i) {
        return i == 2 && android.util.SizeF.class.equals(typeReference.getType());
    }
}
