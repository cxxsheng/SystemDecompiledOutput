package android.media;

/* loaded from: classes2.dex */
public final class MediaDescrambler implements java.lang.AutoCloseable {
    public static final byte SCRAMBLE_CONTROL_EVEN_KEY = 2;
    public static final byte SCRAMBLE_CONTROL_ODD_KEY = 3;
    public static final byte SCRAMBLE_CONTROL_RESERVED = 1;
    public static final byte SCRAMBLE_CONTROL_UNSCRAMBLED = 0;
    public static final byte SCRAMBLE_FLAG_PES_HEADER = 1;
    private static final java.lang.String TAG = "MediaDescrambler";
    private android.media.MediaDescrambler.DescramblerWrapper mIDescrambler;
    private boolean mIsAidlHal;
    private long mNativeContext;

    private interface DescramblerWrapper {
        android.os.IHwBinder asBinder();

        int descramble(java.nio.ByteBuffer byteBuffer, java.nio.ByteBuffer byteBuffer2, android.media.MediaCodec.CryptoInfo cryptoInfo) throws android.os.RemoteException;

        void release() throws android.os.RemoteException;

        boolean requiresSecureDecoderComponent(java.lang.String str) throws android.os.RemoteException;

        void setMediaCasSession(byte[] bArr) throws android.os.RemoteException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final native int native_descramble(byte b, byte b2, int i, int[] iArr, int[] iArr2, java.nio.ByteBuffer byteBuffer, int i2, int i3, java.nio.ByteBuffer byteBuffer2, int i4, int i5) throws android.os.RemoteException;

    private static final native void native_init();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void native_release();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void native_setup(android.os.IHwBinder iHwBinder);

    private class AidlDescrambler implements android.media.MediaDescrambler.DescramblerWrapper {
        android.hardware.cas.IDescrambler mAidlDescrambler;

        AidlDescrambler(android.hardware.cas.IDescrambler iDescrambler) throws java.lang.Exception {
            if (iDescrambler != null) {
                this.mAidlDescrambler = iDescrambler;
                return;
            }
            throw new java.lang.Exception("Descrambler could not be created");
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public android.os.IHwBinder asBinder() {
            return null;
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public int descramble(java.nio.ByteBuffer byteBuffer, java.nio.ByteBuffer byteBuffer2, android.media.MediaCodec.CryptoInfo cryptoInfo) throws android.os.RemoteException {
            throw new android.os.RemoteException("Not supported");
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public boolean requiresSecureDecoderComponent(java.lang.String str) throws android.os.RemoteException {
            throw new android.os.RemoteException("Not supported");
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public void setMediaCasSession(byte[] bArr) throws android.os.RemoteException {
            throw new android.os.RemoteException("Not supported");
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public void release() throws android.os.RemoteException {
            this.mAidlDescrambler.release();
        }
    }

    private class HidlDescrambler implements android.media.MediaDescrambler.DescramblerWrapper {
        android.hardware.cas.V1_0.IDescramblerBase mHidlDescrambler;

        HidlDescrambler(android.hardware.cas.V1_0.IDescramblerBase iDescramblerBase) throws java.lang.Exception {
            if (iDescramblerBase != null) {
                this.mHidlDescrambler = iDescramblerBase;
                android.media.MediaDescrambler.this.native_setup(iDescramblerBase.asBinder());
                return;
            }
            throw new java.lang.Exception("Descrambler could not be created");
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public android.os.IHwBinder asBinder() {
            return this.mHidlDescrambler.asBinder();
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public int descramble(java.nio.ByteBuffer byteBuffer, java.nio.ByteBuffer byteBuffer2, android.media.MediaCodec.CryptoInfo cryptoInfo) throws android.os.RemoteException {
            try {
                return android.media.MediaDescrambler.this.native_descramble(cryptoInfo.key[0], cryptoInfo.key[1], cryptoInfo.numSubSamples, cryptoInfo.numBytesOfClearData, cryptoInfo.numBytesOfEncryptedData, byteBuffer, byteBuffer.position(), byteBuffer.limit(), byteBuffer2, byteBuffer2.position(), byteBuffer2.limit());
            } catch (android.os.RemoteException e) {
                android.media.MediaDescrambler.this.cleanupAndRethrowIllegalState();
                return -1;
            } catch (android.os.ServiceSpecificException e2) {
                android.media.MediaCasStateException.throwExceptionIfNeeded(e2.errorCode, e2.getMessage());
                return -1;
            }
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public boolean requiresSecureDecoderComponent(java.lang.String str) throws android.os.RemoteException {
            return this.mHidlDescrambler.requiresSecureDecoderComponent(str);
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public void setMediaCasSession(byte[] bArr) throws android.os.RemoteException {
            java.util.ArrayList<java.lang.Byte> arrayList = new java.util.ArrayList<>();
            if (bArr != null) {
                java.util.ArrayList<java.lang.Byte> arrayList2 = new java.util.ArrayList<>(bArr.length);
                for (byte b : bArr) {
                    arrayList2.add(java.lang.Byte.valueOf(b));
                }
                arrayList = arrayList2;
            }
            android.media.MediaCasStateException.throwExceptionIfNeeded(this.mHidlDescrambler.setMediaCasSession(arrayList));
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public void release() throws android.os.RemoteException {
            this.mHidlDescrambler.release();
            android.media.MediaDescrambler.this.native_release();
        }
    }

    private final void validateInternalStates() {
        if (this.mIDescrambler == null) {
            throw new java.lang.IllegalStateException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cleanupAndRethrowIllegalState() {
        this.mIDescrambler = null;
        throw new java.lang.IllegalStateException();
    }

    public MediaDescrambler(int i) throws android.media.MediaCasException.UnsupportedCasException {
        try {
            try {
                if (android.media.MediaCas.getService() != null) {
                    this.mIDescrambler = new android.media.MediaDescrambler.AidlDescrambler(android.media.MediaCas.getService().createDescrambler(i));
                    this.mIsAidlHal = true;
                } else {
                    if (android.media.MediaCas.getServiceHidl() == null) {
                        throw new java.lang.Exception("No CAS service found!");
                    }
                    this.mIDescrambler = new android.media.MediaDescrambler.HidlDescrambler(android.media.MediaCas.getServiceHidl().createDescrambler(i));
                    this.mIsAidlHal = false;
                }
                if (this.mIDescrambler == null) {
                    throw new android.media.MediaCasException.UnsupportedCasException("Unsupported CA_system_id " + i);
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "Failed to create descrambler: " + e);
                this.mIDescrambler = null;
                if (this.mIDescrambler == null) {
                    throw new android.media.MediaCasException.UnsupportedCasException("Unsupported CA_system_id " + i);
                }
            }
        } catch (java.lang.Throwable th) {
            if (this.mIDescrambler != null) {
                throw th;
            }
            throw new android.media.MediaCasException.UnsupportedCasException("Unsupported CA_system_id " + i);
        }
    }

    public boolean isAidlHal() {
        return this.mIsAidlHal;
    }

    android.os.IHwBinder getBinder() {
        validateInternalStates();
        return this.mIDescrambler.asBinder();
    }

    public final boolean requiresSecureDecoderComponent(java.lang.String str) {
        validateInternalStates();
        try {
            return this.mIDescrambler.requiresSecureDecoderComponent(str);
        } catch (android.os.RemoteException e) {
            cleanupAndRethrowIllegalState();
            return true;
        }
    }

    public final void setMediaCasSession(android.media.MediaCas.Session session) {
        validateInternalStates();
        try {
            this.mIDescrambler.setMediaCasSession(session.mSessionId);
        } catch (android.os.RemoteException e) {
            cleanupAndRethrowIllegalState();
        }
    }

    public final int descramble(java.nio.ByteBuffer byteBuffer, java.nio.ByteBuffer byteBuffer2, android.media.MediaCodec.CryptoInfo cryptoInfo) {
        validateInternalStates();
        if (cryptoInfo.numSubSamples <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid CryptoInfo: invalid numSubSamples=" + cryptoInfo.numSubSamples);
        }
        if (cryptoInfo.numBytesOfClearData == null && cryptoInfo.numBytesOfEncryptedData == null) {
            throw new java.lang.IllegalArgumentException("Invalid CryptoInfo: clearData and encryptedData size arrays are both null!");
        }
        if (cryptoInfo.numBytesOfClearData != null && cryptoInfo.numBytesOfClearData.length < cryptoInfo.numSubSamples) {
            throw new java.lang.IllegalArgumentException("Invalid CryptoInfo: numBytesOfClearData is too small!");
        }
        if (cryptoInfo.numBytesOfEncryptedData != null && cryptoInfo.numBytesOfEncryptedData.length < cryptoInfo.numSubSamples) {
            throw new java.lang.IllegalArgumentException("Invalid CryptoInfo: numBytesOfEncryptedData is too small!");
        }
        if (cryptoInfo.key == null || cryptoInfo.key.length != 16) {
            throw new java.lang.IllegalArgumentException("Invalid CryptoInfo: key array is invalid!");
        }
        try {
            return this.mIDescrambler.descramble(byteBuffer, byteBuffer2, cryptoInfo);
        } catch (android.os.RemoteException e) {
            cleanupAndRethrowIllegalState();
            return -1;
        } catch (android.os.ServiceSpecificException e2) {
            android.media.MediaCasStateException.throwExceptionIfNeeded(e2.errorCode, e2.getMessage());
            return -1;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.mIDescrambler != null) {
            try {
                this.mIDescrambler.release();
            } catch (android.os.RemoteException e) {
            } catch (java.lang.Throwable th) {
                this.mIDescrambler = null;
                throw th;
            }
            this.mIDescrambler = null;
        }
    }

    protected void finalize() {
        close();
    }

    static {
        java.lang.System.loadLibrary("media_jni");
        native_init();
    }
}
