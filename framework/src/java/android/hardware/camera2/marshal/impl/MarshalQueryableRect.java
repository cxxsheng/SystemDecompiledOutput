package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableRect implements android.hardware.camera2.marshal.MarshalQueryable<android.graphics.Rect> {
    private static final int SIZE = 16;

    private class MarshalerRect extends android.hardware.camera2.marshal.Marshaler<android.graphics.Rect> {
        protected MarshalerRect(android.hardware.camera2.utils.TypeReference<android.graphics.Rect> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableRect.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(android.graphics.Rect rect, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.putInt(rect.left);
            byteBuffer.putInt(rect.top);
            byteBuffer.putInt(rect.width());
            byteBuffer.putInt(rect.height());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public android.graphics.Rect unmarshal(java.nio.ByteBuffer byteBuffer) {
            int i = byteBuffer.getInt();
            int i2 = byteBuffer.getInt();
            return new android.graphics.Rect(i, i2, byteBuffer.getInt() + i, byteBuffer.getInt() + i2);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 16;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<android.graphics.Rect> createMarshaler(android.hardware.camera2.utils.TypeReference<android.graphics.Rect> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableRect.MarshalerRect(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<android.graphics.Rect> typeReference, int i) {
        return i == 1 && android.graphics.Rect.class.equals(typeReference.getType());
    }
}
