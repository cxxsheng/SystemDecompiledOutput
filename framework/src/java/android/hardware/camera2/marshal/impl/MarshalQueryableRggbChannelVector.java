package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableRggbChannelVector implements android.hardware.camera2.marshal.MarshalQueryable<android.hardware.camera2.params.RggbChannelVector> {
    private static final int SIZE = 16;

    private class MarshalerRggbChannelVector extends android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.RggbChannelVector> {
        protected MarshalerRggbChannelVector(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.RggbChannelVector> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableRggbChannelVector.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(android.hardware.camera2.params.RggbChannelVector rggbChannelVector, java.nio.ByteBuffer byteBuffer) {
            for (int i = 0; i < 4; i++) {
                byteBuffer.putFloat(rggbChannelVector.getComponent(i));
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public android.hardware.camera2.params.RggbChannelVector unmarshal(java.nio.ByteBuffer byteBuffer) {
            return new android.hardware.camera2.params.RggbChannelVector(byteBuffer.getFloat(), byteBuffer.getFloat(), byteBuffer.getFloat(), byteBuffer.getFloat());
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 16;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.RggbChannelVector> createMarshaler(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.RggbChannelVector> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableRggbChannelVector.MarshalerRggbChannelVector(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.RggbChannelVector> typeReference, int i) {
        return i == 2 && android.hardware.camera2.params.RggbChannelVector.class.equals(typeReference.getType());
    }
}
