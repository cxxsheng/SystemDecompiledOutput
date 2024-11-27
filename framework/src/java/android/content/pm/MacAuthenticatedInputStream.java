package android.content.pm;

/* loaded from: classes.dex */
public class MacAuthenticatedInputStream extends java.io.FilterInputStream {
    private final javax.crypto.Mac mMac;

    public MacAuthenticatedInputStream(java.io.InputStream inputStream, javax.crypto.Mac mac) {
        super(inputStream);
        this.mMac = mac;
    }

    public boolean isTagEqual(byte[] bArr) {
        byte[] doFinal = this.mMac.doFinal();
        if (bArr == null || doFinal == null || bArr.length != doFinal.length) {
            return false;
        }
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            i |= bArr[i2] ^ doFinal[i2];
        }
        return i == 0;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws java.io.IOException {
        int read = super.read();
        if (read >= 0) {
            this.mMac.update((byte) read);
        }
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
        int read = super.read(bArr, i, i2);
        if (read > 0) {
            this.mMac.update(bArr, i, read);
        }
        return read;
    }
}
