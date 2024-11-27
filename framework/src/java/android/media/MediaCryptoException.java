package android.media;

/* loaded from: classes2.dex */
public final class MediaCryptoException extends java.lang.Exception implements android.media.MediaDrmThrowable {
    private final int mErrorContext;
    private final int mOemError;
    private final int mVendorError;

    public MediaCryptoException(java.lang.String str) {
        this(str, 0, 0, 0);
    }

    public MediaCryptoException(java.lang.String str, int i, int i2, int i3) {
        super(str);
        this.mVendorError = i;
        this.mOemError = i2;
        this.mErrorContext = i3;
    }

    @Override // android.media.MediaDrmThrowable
    public int getVendorError() {
        return this.mVendorError;
    }

    @Override // android.media.MediaDrmThrowable
    public int getOemError() {
        return this.mOemError;
    }

    @Override // android.media.MediaDrmThrowable
    public int getErrorContext() {
        return this.mErrorContext;
    }
}
