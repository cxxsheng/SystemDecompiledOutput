package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableNativeByteToInteger implements android.hardware.camera2.marshal.MarshalQueryable<java.lang.Integer> {
    private static final int UINT8_MASK = 255;

    private class MarshalerNativeByteToInteger extends android.hardware.camera2.marshal.Marshaler<java.lang.Integer> {
        protected MarshalerNativeByteToInteger(android.hardware.camera2.utils.TypeReference<java.lang.Integer> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableNativeByteToInteger.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(java.lang.Integer num, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.put((byte) num.intValue());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public java.lang.Integer unmarshal(java.nio.ByteBuffer byteBuffer) {
            return java.lang.Integer.valueOf(byteBuffer.get() & 255);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 1;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<java.lang.Integer> createMarshaler(android.hardware.camera2.utils.TypeReference<java.lang.Integer> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableNativeByteToInteger.MarshalerNativeByteToInteger(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<java.lang.Integer> typeReference, int i) {
        return (java.lang.Integer.class.equals(typeReference.getType()) || java.lang.Integer.TYPE.equals(typeReference.getType())) && i == 0;
    }
}
