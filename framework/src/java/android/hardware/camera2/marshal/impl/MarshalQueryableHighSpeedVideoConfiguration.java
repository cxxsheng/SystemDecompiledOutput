package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableHighSpeedVideoConfiguration implements android.hardware.camera2.marshal.MarshalQueryable<android.hardware.camera2.params.HighSpeedVideoConfiguration> {
    private static final int SIZE = 20;

    private class MarshalerHighSpeedVideoConfiguration extends android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.HighSpeedVideoConfiguration> {
        protected MarshalerHighSpeedVideoConfiguration(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.HighSpeedVideoConfiguration> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableHighSpeedVideoConfiguration.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(android.hardware.camera2.params.HighSpeedVideoConfiguration highSpeedVideoConfiguration, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.putInt(highSpeedVideoConfiguration.getWidth());
            byteBuffer.putInt(highSpeedVideoConfiguration.getHeight());
            byteBuffer.putInt(highSpeedVideoConfiguration.getFpsMin());
            byteBuffer.putInt(highSpeedVideoConfiguration.getFpsMax());
            byteBuffer.putInt(highSpeedVideoConfiguration.getBatchSizeMax());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public android.hardware.camera2.params.HighSpeedVideoConfiguration unmarshal(java.nio.ByteBuffer byteBuffer) {
            return new android.hardware.camera2.params.HighSpeedVideoConfiguration(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt());
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 20;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.HighSpeedVideoConfiguration> createMarshaler(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.HighSpeedVideoConfiguration> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableHighSpeedVideoConfiguration.MarshalerHighSpeedVideoConfiguration(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.HighSpeedVideoConfiguration> typeReference, int i) {
        return i == 1 && typeReference.getType().equals(android.hardware.camera2.params.HighSpeedVideoConfiguration.class);
    }
}
