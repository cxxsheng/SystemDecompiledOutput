package android.drm;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class DrmOutputStream extends java.io.OutputStream {
    private static final java.lang.String TAG = "DrmOutputStream";
    private final android.drm.DrmManagerClient mClient;
    private final java.io.FileDescriptor mFd;
    private final android.os.ParcelFileDescriptor mPfd;
    private int mSessionId;

    public DrmOutputStream(android.drm.DrmManagerClient drmManagerClient, android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String str) throws java.io.IOException {
        this.mSessionId = -1;
        this.mClient = drmManagerClient;
        this.mPfd = parcelFileDescriptor;
        this.mFd = parcelFileDescriptor.getFileDescriptor();
        this.mSessionId = this.mClient.openConvertSession(str);
        if (this.mSessionId == -1) {
            throw new java.net.UnknownServiceException("Failed to open DRM session for " + str);
        }
    }

    public void finish() throws java.io.IOException {
        android.drm.DrmConvertedStatus closeConvertSession = this.mClient.closeConvertSession(this.mSessionId);
        if (closeConvertSession.statusCode == 1) {
            try {
                android.system.Os.lseek(this.mFd, closeConvertSession.offset, android.system.OsConstants.SEEK_SET);
            } catch (android.system.ErrnoException e) {
                e.rethrowAsIOException();
            }
            libcore.io.IoBridge.write(this.mFd, closeConvertSession.convertedData, 0, closeConvertSession.convertedData.length);
            this.mSessionId = -1;
            return;
        }
        throw new java.io.IOException("Unexpected DRM status: " + closeConvertSession.statusCode);
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        if (this.mSessionId == -1) {
            android.util.Log.w(TAG, "Closing stream without finishing");
        }
        this.mPfd.close();
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
        com.android.internal.util.ArrayUtils.throwsIfOutOfBounds(bArr.length, i, i2);
        if (i2 != bArr.length) {
            byte[] bArr2 = new byte[i2];
            java.lang.System.arraycopy(bArr, i, bArr2, 0, i2);
            bArr = bArr2;
        }
        android.drm.DrmConvertedStatus convertData = this.mClient.convertData(this.mSessionId, bArr);
        if (convertData.statusCode == 1) {
            libcore.io.IoBridge.write(this.mFd, convertData.convertedData, 0, convertData.convertedData.length);
            return;
        }
        throw new java.io.IOException("Unexpected DRM status: " + convertData.statusCode);
    }

    @Override // java.io.OutputStream
    public void write(int i) throws java.io.IOException {
        libcore.io.Streams.writeSingleByte(this, i);
    }
}
