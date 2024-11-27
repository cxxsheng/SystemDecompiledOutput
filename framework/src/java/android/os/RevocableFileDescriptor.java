package android.os;

/* loaded from: classes3.dex */
public class RevocableFileDescriptor {
    private static final boolean DEBUG = true;
    private static final java.lang.String TAG = "RevocableFileDescriptor";
    private final android.os.ProxyFileDescriptorCallback mCallback = new android.os.ProxyFileDescriptorCallback() { // from class: android.os.RevocableFileDescriptor.1
        private void checkRevoked() throws android.system.ErrnoException {
            if (android.os.RevocableFileDescriptor.this.mRevoked) {
                throw new android.system.ErrnoException(android.os.RevocableFileDescriptor.TAG, android.system.OsConstants.EPERM);
            }
        }

        @Override // android.os.ProxyFileDescriptorCallback
        public long onGetSize() throws android.system.ErrnoException {
            checkRevoked();
            return android.system.Os.fstat(android.os.RevocableFileDescriptor.this.mInner).st_size;
        }

        @Override // android.os.ProxyFileDescriptorCallback
        public int onRead(long j, int i, byte[] bArr) throws android.system.ErrnoException {
            checkRevoked();
            int i2 = 0;
            while (i2 < i) {
                try {
                    return i2 + android.system.Os.pread(android.os.RevocableFileDescriptor.this.mInner, bArr, i2, i - i2, j + i2);
                } catch (java.io.InterruptedIOException e) {
                    i2 += e.bytesTransferred;
                }
            }
            return i2;
        }

        @Override // android.os.ProxyFileDescriptorCallback
        public int onWrite(long j, int i, byte[] bArr) throws android.system.ErrnoException {
            checkRevoked();
            int i2 = 0;
            while (i2 < i) {
                try {
                    return i2 + android.system.Os.pwrite(android.os.RevocableFileDescriptor.this.mInner, bArr, i2, i - i2, j + i2);
                } catch (java.io.InterruptedIOException e) {
                    i2 += e.bytesTransferred;
                }
            }
            return i2;
        }

        @Override // android.os.ProxyFileDescriptorCallback
        public void onFsync() throws android.system.ErrnoException {
            android.util.Slog.v(android.os.RevocableFileDescriptor.TAG, "onFsync()");
            checkRevoked();
            android.system.Os.fsync(android.os.RevocableFileDescriptor.this.mInner);
        }

        @Override // android.os.ProxyFileDescriptorCallback
        public void onRelease() {
            android.util.Slog.v(android.os.RevocableFileDescriptor.TAG, "onRelease()");
            android.os.RevocableFileDescriptor.this.mRevoked = true;
            libcore.io.IoUtils.closeQuietly(android.os.RevocableFileDescriptor.this.mInner);
            if (android.os.RevocableFileDescriptor.this.mOnCloseListener != null) {
                android.os.RevocableFileDescriptor.this.mOnCloseListener.onClose(null);
            }
        }
    };
    private java.io.FileDescriptor mInner;
    private android.os.ParcelFileDescriptor.OnCloseListener mOnCloseListener;
    private android.os.ParcelFileDescriptor mOuter;
    private volatile boolean mRevoked;

    public RevocableFileDescriptor() {
    }

    public RevocableFileDescriptor(android.content.Context context, java.io.File file) throws java.io.IOException {
        try {
            init(context, android.system.Os.open(file.getAbsolutePath(), android.system.OsConstants.O_CREAT | android.system.OsConstants.O_RDWR, 448));
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public RevocableFileDescriptor(android.content.Context context, java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
        init(context, fileDescriptor);
    }

    public RevocableFileDescriptor(android.content.Context context, java.io.FileDescriptor fileDescriptor, android.os.Handler handler) throws java.io.IOException {
        init(context, fileDescriptor, handler);
    }

    public void init(android.content.Context context, java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
        init(context, fileDescriptor, null);
    }

    public void init(android.content.Context context, java.io.FileDescriptor fileDescriptor, android.os.Handler handler) throws java.io.IOException {
        this.mInner = fileDescriptor;
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) context.getSystemService(android.os.storage.StorageManager.class);
        if (handler != null) {
            this.mOuter = storageManager.openProxyFileDescriptor(805306368, this.mCallback, handler);
        } else {
            this.mOuter = storageManager.openProxyFileDescriptor(805306368, this.mCallback);
        }
    }

    public android.os.ParcelFileDescriptor getRevocableFileDescriptor() {
        return this.mOuter;
    }

    public void revoke() {
        this.mRevoked = true;
        libcore.io.IoUtils.closeQuietly(this.mInner);
    }

    public void addOnCloseListener(android.os.ParcelFileDescriptor.OnCloseListener onCloseListener) {
        this.mOnCloseListener = onCloseListener;
    }

    public boolean isRevoked() {
        return this.mRevoked;
    }
}
