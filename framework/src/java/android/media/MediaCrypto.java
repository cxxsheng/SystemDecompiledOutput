package android.media;

/* loaded from: classes2.dex */
public final class MediaCrypto {
    private long mNativeContext;

    private static final native boolean isCryptoSchemeSupportedNative(byte[] bArr);

    private final native void native_finalize();

    private static final native void native_init();

    private final native void native_setup(byte[] bArr, byte[] bArr2) throws android.media.MediaCryptoException;

    public final native void release();

    public final native boolean requiresSecureDecoderComponent(java.lang.String str);

    public final native void setMediaDrmSession(byte[] bArr) throws android.media.MediaCryptoException;

    public static final boolean isCryptoSchemeSupported(java.util.UUID uuid) {
        return isCryptoSchemeSupportedNative(getByteArrayFromUUID(uuid));
    }

    private static final byte[] getByteArrayFromUUID(java.util.UUID uuid) {
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        byte[] bArr = new byte[16];
        for (int i = 0; i < 8; i++) {
            int i2 = (7 - i) * 8;
            bArr[i] = (byte) (mostSignificantBits >>> i2);
            bArr[i + 8] = (byte) (leastSignificantBits >>> i2);
        }
        return bArr;
    }

    public MediaCrypto(java.util.UUID uuid, byte[] bArr) throws android.media.MediaCryptoException {
        native_setup(getByteArrayFromUUID(uuid), bArr);
    }

    protected void finalize() {
        native_finalize();
    }

    static {
        java.lang.System.loadLibrary("media_jni");
        native_init();
    }
}
