package android.hardware.camera2.marshal.impl;

/* loaded from: classes.dex */
public class MarshalQueryableString implements android.hardware.camera2.marshal.MarshalQueryable<java.lang.String> {
    private static final boolean DEBUG = false;
    private static final byte NUL = 0;
    private static final java.lang.String TAG = android.hardware.camera2.marshal.impl.MarshalQueryableString.class.getSimpleName();

    private static class PreloadHolder {
        public static final java.nio.charset.Charset UTF8_CHARSET = java.nio.charset.Charset.forName(android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);

        private PreloadHolder() {
        }
    }

    private class MarshalerString extends android.hardware.camera2.marshal.Marshaler<java.lang.String> {
        protected MarshalerString(android.hardware.camera2.utils.TypeReference<java.lang.String> typeReference, int i) {
            super(android.hardware.camera2.marshal.impl.MarshalQueryableString.this, typeReference, i);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public void marshal(java.lang.String str, java.nio.ByteBuffer byteBuffer) {
            byteBuffer.put(str.getBytes(android.hardware.camera2.marshal.impl.MarshalQueryableString.PreloadHolder.UTF8_CHARSET));
            byteBuffer.put((byte) 0);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int calculateMarshalSize(java.lang.String str) {
            return str.getBytes(android.hardware.camera2.marshal.impl.MarshalQueryableString.PreloadHolder.UTF8_CHARSET).length + 1;
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public java.lang.String unmarshal(java.nio.ByteBuffer byteBuffer) {
            boolean z;
            byteBuffer.mark();
            int i = 0;
            while (true) {
                if (!byteBuffer.hasRemaining()) {
                    z = false;
                    break;
                }
                if (byteBuffer.get() == 0) {
                    z = true;
                    break;
                }
                i++;
            }
            if (!z) {
                throw new java.lang.UnsupportedOperationException("Strings must be null-terminated");
            }
            byteBuffer.reset();
            int i2 = i + 1;
            byte[] bArr = new byte[i2];
            byteBuffer.get(bArr, 0, i2);
            return new java.lang.String(bArr, 0, i, android.hardware.camera2.marshal.impl.MarshalQueryableString.PreloadHolder.UTF8_CHARSET);
        }

        @Override // android.hardware.camera2.marshal.Marshaler
        public int getNativeSize() {
            return NATIVE_SIZE_DYNAMIC;
        }
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public android.hardware.camera2.marshal.Marshaler<java.lang.String> createMarshaler(android.hardware.camera2.utils.TypeReference<java.lang.String> typeReference, int i) {
        return new android.hardware.camera2.marshal.impl.MarshalQueryableString.MarshalerString(typeReference, i);
    }

    @Override // android.hardware.camera2.marshal.MarshalQueryable
    public boolean isTypeMappingSupported(android.hardware.camera2.utils.TypeReference<java.lang.String> typeReference, int i) {
        return i == 0 && java.lang.String.class.equals(typeReference.getType());
    }
}
