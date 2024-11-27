package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableReprocessFormatsMap implements android.hardware.camera2.marshal.MarshalQueryable<android.hardware.camera2.params.ReprocessFormatsMap> {

    private class MarshalerReprocessFormatsMap extends android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.ReprocessFormatsMap> {
        protected MarshalerReprocessFormatsMap(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.ReprocessFormatsMap> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableReprocessFormatsMap.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(android.hardware.camera2.params.ReprocessFormatsMap reprocessFormatsMap, java.nio.ByteBuffer byteBuffer) {
            for (int i : android.hardware.camera2.params.StreamConfigurationMap.imageFormatToInternal(reprocessFormatsMap.getInputs())) {
                byteBuffer.putInt(i);
                int[] imageFormatToInternal = android.hardware.camera2.params.StreamConfigurationMap.imageFormatToInternal(reprocessFormatsMap.getOutputs(i));
                byteBuffer.putInt(imageFormatToInternal.length);
                for (int i2 : imageFormatToInternal) {
                    byteBuffer.putInt(i2);
                }
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public android.hardware.camera2.params.ReprocessFormatsMap unmarshal(java.nio.ByteBuffer byteBuffer) {
            int remaining = byteBuffer.remaining() / 4;
            if (byteBuffer.remaining() % 4 != 0) {
                throw new java.lang.AssertionError("ReprocessFormatsMap was not TYPE_INT32");
            }
            int[] iArr = new int[remaining];
            byteBuffer.asIntBuffer().get(iArr);
            return new android.hardware.camera2.params.ReprocessFormatsMap(iArr);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return NATIVE_SIZE_DYNAMIC;
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int calculateMarshalSize(android.hardware.camera2.params.ReprocessFormatsMap reprocessFormatsMap) {
            int i = 0;
            for (int i2 : reprocessFormatsMap.getInputs()) {
                i = i + 1 + 1 + reprocessFormatsMap.getOutputs(i2).length;
            }
            return i * 4;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.ReprocessFormatsMap> createMarshaler(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.ReprocessFormatsMap> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableReprocessFormatsMap.MarshalerReprocessFormatsMap(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.ReprocessFormatsMap> typeReference, int i) {
        return i == 1 && typeReference.getType().equals(android.hardware.camera2.params.ReprocessFormatsMap.class);
    }
}
