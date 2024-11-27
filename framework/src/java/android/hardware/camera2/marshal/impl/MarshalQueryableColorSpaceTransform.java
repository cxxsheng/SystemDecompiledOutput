package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableColorSpaceTransform implements android.hardware.camera2.marshal.MarshalQueryable<android.hardware.camera2.params.ColorSpaceTransform> {
    private static final int ELEMENTS_INT32 = 18;
    private static final int SIZE = 72;

    private class MarshalerColorSpaceTransform extends android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.ColorSpaceTransform> {
        protected MarshalerColorSpaceTransform(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.ColorSpaceTransform> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableColorSpaceTransform.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(android.hardware.camera2.params.ColorSpaceTransform colorSpaceTransform, java.nio.ByteBuffer byteBuffer) {
            int[] iArr = new int[18];
            colorSpaceTransform.copyElements(iArr, 0);
            for (int i = 0; i < 18; i++) {
                byteBuffer.putInt(iArr[i]);
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public android.hardware.camera2.params.ColorSpaceTransform unmarshal(java.nio.ByteBuffer byteBuffer) {
            int[] iArr = new int[18];
            for (int i = 0; i < 18; i++) {
                iArr[i] = byteBuffer.getInt();
            }
            return new android.hardware.camera2.params.ColorSpaceTransform(iArr);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 72;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.ColorSpaceTransform> createMarshaler(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.ColorSpaceTransform> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableColorSpaceTransform.MarshalerColorSpaceTransform(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.ColorSpaceTransform> typeReference, int i) {
        return i == 5 && android.hardware.camera2.params.ColorSpaceTransform.class.equals(typeReference.getType());
    }
}
