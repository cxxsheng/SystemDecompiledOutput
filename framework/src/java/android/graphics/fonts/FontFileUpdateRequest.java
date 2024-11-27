package android.graphics.fonts;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class FontFileUpdateRequest {
    private final android.os.ParcelFileDescriptor mParcelFileDescriptor;
    private final byte[] mSignature;

    public FontFileUpdateRequest(android.os.ParcelFileDescriptor parcelFileDescriptor, byte[] bArr) {
        java.util.Objects.requireNonNull(parcelFileDescriptor);
        java.util.Objects.requireNonNull(bArr);
        this.mParcelFileDescriptor = parcelFileDescriptor;
        this.mSignature = bArr;
    }

    public android.os.ParcelFileDescriptor getParcelFileDescriptor() {
        return this.mParcelFileDescriptor;
    }

    public byte[] getSignature() {
        return this.mSignature;
    }
}
