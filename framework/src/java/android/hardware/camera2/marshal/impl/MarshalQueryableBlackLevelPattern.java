package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableBlackLevelPattern implements android.hardware.camera2.marshal.MarshalQueryable<android.hardware.camera2.params.BlackLevelPattern> {
    private static final int SIZE = 16;

    private class MarshalerBlackLevelPattern extends android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.BlackLevelPattern> {
        protected MarshalerBlackLevelPattern(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.BlackLevelPattern> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableBlackLevelPattern.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(android.hardware.camera2.params.BlackLevelPattern blackLevelPattern, java.nio.ByteBuffer byteBuffer) {
            for (int i = 0; i < 2; i++) {
                for (int i2 = 0; i2 < 2; i2++) {
                    byteBuffer.putInt(blackLevelPattern.getOffsetForIndex(i2, i));
                }
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public android.hardware.camera2.params.BlackLevelPattern unmarshal(java.nio.ByteBuffer byteBuffer) {
            int[] iArr = new int[4];
            for (int i = 0; i < 4; i++) {
                iArr[i] = byteBuffer.getInt();
            }
            return new android.hardware.camera2.params.BlackLevelPattern(iArr);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 16;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<android.hardware.camera2.params.BlackLevelPattern> createMarshaler(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.BlackLevelPattern> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableBlackLevelPattern.MarshalerBlackLevelPattern(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<android.hardware.camera2.params.BlackLevelPattern> typeReference, int i) {
        return i == 1 && android.hardware.camera2.params.BlackLevelPattern.class.equals(typeReference.getType());
    }
}
