package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableStreamConfigurationDuration implements android.hardware.camera2.marshal.MarshalQueryable<android.hardware.camera2.params.StreamConfigurationDuration> {
    private static final long MASK_UNSIGNED_INT = 4294967295L;
    private static final int SIZE = 32;

    private class MarshalerStreamConfigurationDuration extends android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.StreamConfigurationDuration> {
        protected MarshalerStreamConfigurationDuration(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.StreamConfigurationDuration> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableStreamConfigurationDuration.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(android.hardware.camera2.params.StreamConfigurationDuration streamConfigurationDuration, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.putLong(streamConfigurationDuration.getFormat() & 4294967295L);
            byteBuffer.putLong(streamConfigurationDuration.getWidth());
            byteBuffer.putLong(streamConfigurationDuration.getHeight());
            byteBuffer.putLong(streamConfigurationDuration.getDuration());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public android.hardware.camera2.params.StreamConfigurationDuration unmarshal(java.nio.ByteBuffer byteBuffer) {
            return new android.hardware.camera2.params.StreamConfigurationDuration((int) byteBuffer.getLong(), (int) byteBuffer.getLong(), (int) byteBuffer.getLong(), byteBuffer.getLong());
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 32;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.StreamConfigurationDuration> createMarshaler(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.StreamConfigurationDuration> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableStreamConfigurationDuration.MarshalerStreamConfigurationDuration(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.StreamConfigurationDuration> typeReference, int i) {
        return i == 3 && android.hardware.camera2.params.StreamConfigurationDuration.class.equals(typeReference.getType());
    }
}
