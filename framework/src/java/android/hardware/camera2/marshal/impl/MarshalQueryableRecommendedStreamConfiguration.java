package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableRecommendedStreamConfiguration implements android.hardware.camera2.marshal.MarshalQueryable<android.hardware.camera2.params.RecommendedStreamConfiguration> {
    private static final int SIZE = 20;

    private class MarshalerRecommendedStreamConfiguration extends android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.RecommendedStreamConfiguration> {
        protected MarshalerRecommendedStreamConfiguration(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.RecommendedStreamConfiguration> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableRecommendedStreamConfiguration.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(android.hardware.camera2.params.RecommendedStreamConfiguration recommendedStreamConfiguration, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.putInt(recommendedStreamConfiguration.getWidth());
            byteBuffer.putInt(recommendedStreamConfiguration.getHeight());
            byteBuffer.putInt(recommendedStreamConfiguration.getFormat());
            byteBuffer.putInt(recommendedStreamConfiguration.isInput() ? 1 : 0);
            byteBuffer.putInt(recommendedStreamConfiguration.getUsecaseBitmap());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public android.hardware.camera2.params.RecommendedStreamConfiguration unmarshal(java.nio.ByteBuffer byteBuffer) {
            return new android.hardware.camera2.params.RecommendedStreamConfiguration(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt() != 0, byteBuffer.getInt());
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 20;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.RecommendedStreamConfiguration> createMarshaler(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.RecommendedStreamConfiguration> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableRecommendedStreamConfiguration.MarshalerRecommendedStreamConfiguration(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.RecommendedStreamConfiguration> typeReference, int i) {
        return i == 1 && typeReference.getType().equals(android.hardware.camera2.params.RecommendedStreamConfiguration.class);
    }
}
