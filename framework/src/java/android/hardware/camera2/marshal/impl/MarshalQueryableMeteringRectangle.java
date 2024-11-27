package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableMeteringRectangle implements android.hardware.camera2.marshal.MarshalQueryable<android.hardware.camera2.params.MeteringRectangle> {
    private static final int SIZE = 20;

    private class MarshalerMeteringRectangle extends android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.MeteringRectangle> {
        protected MarshalerMeteringRectangle(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.MeteringRectangle> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableMeteringRectangle.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(android.hardware.camera2.params.MeteringRectangle meteringRectangle, java.nio.ByteBuffer byteBuffer) {
            int x = meteringRectangle.getX();
            int y = meteringRectangle.getY();
            int width = meteringRectangle.getWidth() + x;
            int height = meteringRectangle.getHeight() + y;
            int meteringWeight = meteringRectangle.getMeteringWeight();
            byteBuffer.putInt(x);
            byteBuffer.putInt(y);
            byteBuffer.putInt(width);
            byteBuffer.putInt(height);
            byteBuffer.putInt(meteringWeight);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public android.hardware.camera2.params.MeteringRectangle unmarshal(java.nio.ByteBuffer byteBuffer) {
            int i = byteBuffer.getInt();
            int i2 = byteBuffer.getInt();
            int i3 = byteBuffer.getInt();
            int i4 = byteBuffer.getInt();
            return new android.hardware.camera2.params.MeteringRectangle(i, i2, i3 - i, i4 - i2, byteBuffer.getInt());
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 20;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.MeteringRectangle> createMarshaler(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.MeteringRectangle> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableMeteringRectangle.MarshalerMeteringRectangle(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.MeteringRectangle> typeReference, int i) {
        return i == 1 && android.hardware.camera2.params.MeteringRectangle.class.equals(typeReference.getType());
    }
}
