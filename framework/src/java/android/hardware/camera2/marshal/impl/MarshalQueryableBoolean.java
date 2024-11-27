package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableBoolean implements android.hardware.camera2.marshal.MarshalQueryable<java.lang.Boolean> {

    private class MarshalerBoolean extends android.hardware.camera2.marshal.Marshaler<java.lang.Boolean> {
        protected MarshalerBoolean(android.hardware.camera2.utils.TypeReference<java.lang.Boolean> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableBoolean.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(java.lang.Boolean bool, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.put(bool.booleanValue() ? (byte) 1 : (byte) 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.hardware.camera2.marshal.Marshaler
        public java.lang.Boolean unmarshal(java.nio.ByteBuffer byteBuffer) {
            return java.lang.Boolean.valueOf(byteBuffer.get() != 0);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return 1;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<java.lang.Boolean> createMarshaler(android.hardware.camera2.utils.TypeReference<java.lang.Boolean> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableBoolean.MarshalerBoolean(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<java.lang.Boolean> typeReference, int i) {
        return (java.lang.Boolean.class.equals(typeReference.getType()) || java.lang.Boolean.TYPE.equals(typeReference.getType())) && i == 0;
    }
}
