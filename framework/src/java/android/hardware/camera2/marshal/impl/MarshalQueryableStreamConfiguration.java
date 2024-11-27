package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableStreamConfiguration implements android.hardware.camera2.marshal.MarshalQueryable<android.hardware.camera2.params.StreamConfiguration> {
    private static final int SIZE = 16;

    private class MarshalerStreamConfiguration extends android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.StreamConfiguration> {
        protected MarshalerStreamConfiguration(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.StreamConfiguration> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableStreamConfiguration.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(android.hardware.camera2.params.StreamConfiguration streamConfiguration, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.putInt(streamConfiguration.getFormat());
            byteBuffer.putInt(streamConfiguration.getWidth());
            byteBuffer.putInt(streamConfiguration.getHeight());
            byteBuffer.putInt(streamConfiguration.isInput() ? 1 : 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public android.hardware.camera2.params.StreamConfiguration unmarshal(java.nio.ByteBuffer byteBuffer) {
            return new android.hardware.camera2.params.StreamConfiguration(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt() != 0);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 16;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.StreamConfiguration> createMarshaler(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.StreamConfiguration> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableStreamConfiguration.MarshalerStreamConfiguration(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.StreamConfiguration> typeReference, int i) {
        return i == 1 && typeReference.getType().equals(android.hardware.camera2.params.StreamConfiguration.class);
    }
}
