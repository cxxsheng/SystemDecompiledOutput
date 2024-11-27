package android.os;

/* loaded from: classes3.dex */
public abstract class ProxyFileDescriptorCallback {
    public abstract void onRelease();

    public long onGetSize() throws android.system.ErrnoException {
        throw new android.system.ErrnoException("onGetSize", android.system.OsConstants.EBADF);
    }

    public int onRead(long j, int i, byte[] bArr) throws android.system.ErrnoException {
        throw new android.system.ErrnoException("onRead", android.system.OsConstants.EBADF);
    }

    public int onWrite(long j, int i, byte[] bArr) throws android.system.ErrnoException {
        throw new android.system.ErrnoException("onWrite", android.system.OsConstants.EBADF);
    }

    public void onFsync() throws android.system.ErrnoException {
        throw new android.system.ErrnoException("onFsync", android.system.OsConstants.EINVAL);
    }
}
