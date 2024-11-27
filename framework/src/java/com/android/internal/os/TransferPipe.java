package com.android.internal.os;

/* loaded from: classes4.dex */
public class TransferPipe implements java.lang.Runnable, java.io.Closeable {
    static final boolean DEBUG = false;
    static final long DEFAULT_TIMEOUT = 5000;
    static final java.lang.String TAG = "TransferPipe";
    java.lang.String mBufferPrefix;
    boolean mComplete;
    long mEndTime;
    java.lang.String mFailure;
    final android.os.ParcelFileDescriptor[] mFds;
    java.io.FileDescriptor mOutFd;
    final java.lang.Thread mThread;

    interface Caller {
        void go(android.os.IInterface iInterface, java.io.FileDescriptor fileDescriptor, java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException;
    }

    public TransferPipe() throws java.io.IOException {
        this(null);
    }

    public TransferPipe(java.lang.String str) throws java.io.IOException {
        this(str, TAG);
    }

    protected TransferPipe(java.lang.String str, java.lang.String str2) throws java.io.IOException {
        this.mThread = new java.lang.Thread(this, str2);
        this.mFds = android.os.ParcelFileDescriptor.createPipe();
        this.mBufferPrefix = str;
    }

    android.os.ParcelFileDescriptor getReadFd() {
        return this.mFds[0];
    }

    public android.os.ParcelFileDescriptor getWriteFd() {
        return this.mFds[1];
    }

    public void setBufferPrefix(java.lang.String str) {
        this.mBufferPrefix = str;
    }

    public static void dumpAsync(android.os.IBinder iBinder, java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr) throws java.io.IOException, android.os.RemoteException {
        goDump(iBinder, fileDescriptor, strArr);
    }

    public static byte[] dumpAsync(android.os.IBinder iBinder, java.lang.String... strArr) throws java.io.IOException, android.os.RemoteException {
        android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
        try {
            dumpAsync(iBinder, createPipe[1].getFileDescriptor(), strArr);
            createPipe[1].close();
            createPipe[1] = null;
            byte[] bArr = new byte[4096];
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(createPipe[0].getFileDescriptor());
                while (true) {
                    try {
                        int read = fileInputStream.read(bArr);
                        if (read != -1) {
                            byteArrayOutputStream.write(bArr, 0, read);
                        } else {
                            fileInputStream.close();
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            byteArrayOutputStream.close();
                            return byteArray;
                        }
                    } finally {
                    }
                }
            } finally {
            }
        } finally {
            createPipe[0].close();
            libcore.io.IoUtils.closeQuietly(createPipe[1]);
        }
    }

    static void go(com.android.internal.os.TransferPipe.Caller caller, android.os.IInterface iInterface, java.io.FileDescriptor fileDescriptor, java.lang.String str, java.lang.String[] strArr) throws java.io.IOException, android.os.RemoteException {
        go(caller, iInterface, fileDescriptor, str, strArr, 5000L);
    }

    static void go(com.android.internal.os.TransferPipe.Caller caller, android.os.IInterface iInterface, java.io.FileDescriptor fileDescriptor, java.lang.String str, java.lang.String[] strArr, long j) throws java.io.IOException, android.os.RemoteException {
        if (iInterface.asBinder() instanceof android.os.Binder) {
            try {
                caller.go(iInterface, fileDescriptor, str, strArr);
                return;
            } catch (android.os.RemoteException e) {
                return;
            }
        }
        com.android.internal.os.TransferPipe transferPipe = new com.android.internal.os.TransferPipe();
        try {
            caller.go(iInterface, transferPipe.getWriteFd().getFileDescriptor(), str, strArr);
            transferPipe.go(fileDescriptor, j);
            transferPipe.close();
        } catch (java.lang.Throwable th) {
            try {
                transferPipe.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    static void goDump(android.os.IBinder iBinder, java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr) throws java.io.IOException, android.os.RemoteException {
        goDump(iBinder, fileDescriptor, strArr, 5000L);
    }

    static void goDump(android.os.IBinder iBinder, java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr, long j) throws java.io.IOException, android.os.RemoteException {
        if (iBinder instanceof android.os.Binder) {
            try {
                iBinder.dump(fileDescriptor, strArr);
                return;
            } catch (android.os.RemoteException e) {
                return;
            }
        }
        com.android.internal.os.TransferPipe transferPipe = new com.android.internal.os.TransferPipe();
        try {
            iBinder.dumpAsync(transferPipe.getWriteFd().getFileDescriptor(), strArr);
            transferPipe.go(fileDescriptor, j);
            transferPipe.close();
        } catch (java.lang.Throwable th) {
            try {
                transferPipe.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void go(java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
        go(fileDescriptor, 5000L);
    }

    public void go(java.io.FileDescriptor fileDescriptor, long j) throws java.io.IOException {
        try {
            synchronized (this) {
                this.mOutFd = fileDescriptor;
                this.mEndTime = android.os.SystemClock.uptimeMillis() + j;
                closeFd(1);
                this.mThread.start();
                while (this.mFailure == null && !this.mComplete) {
                    long uptimeMillis = this.mEndTime - android.os.SystemClock.uptimeMillis();
                    if (uptimeMillis <= 0) {
                        this.mThread.interrupt();
                        throw new java.io.IOException("Timeout");
                    }
                    try {
                        wait(uptimeMillis);
                    } catch (java.lang.InterruptedException e) {
                    }
                }
                if (this.mFailure != null) {
                    throw new java.io.IOException(this.mFailure);
                }
            }
        } finally {
            kill();
        }
    }

    void closeFd(int i) {
        if (this.mFds[i] != null) {
            try {
                this.mFds[i].close();
            } catch (java.io.IOException e) {
            }
            this.mFds[i] = null;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        kill();
    }

    public void kill() {
        synchronized (this) {
            closeFd(0);
            closeFd(1);
        }
    }

    protected java.io.OutputStream getNewOutputStream() {
        return new java.io.FileOutputStream(this.mOutFd);
    }

    @Override // java.lang.Runnable
    public void run() {
        byte[] bArr;
        boolean z;
        int i;
        byte[] bArr2 = new byte[1024];
        synchronized (this) {
            android.os.ParcelFileDescriptor readFd = getReadFd();
            if (readFd == null) {
                android.util.Slog.w(TAG, "Pipe has been closed...");
                return;
            }
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(readFd.getFileDescriptor());
            java.io.OutputStream newOutputStream = getNewOutputStream();
            if (this.mBufferPrefix == null) {
                bArr = null;
                z = true;
            } else {
                bArr = this.mBufferPrefix.getBytes();
                z = true;
            }
            while (true) {
                try {
                    int read = fileInputStream.read(bArr2);
                    if (read <= 0) {
                        this.mThread.isInterrupted();
                        synchronized (this) {
                            this.mComplete = true;
                            notifyAll();
                        }
                        return;
                    }
                    if (bArr == null) {
                        newOutputStream.write(bArr2, 0, read);
                    } else {
                        int i2 = 0;
                        int i3 = 0;
                        while (i2 < read) {
                            if (bArr2[i2] != 10) {
                                if (i2 > i3) {
                                    newOutputStream.write(bArr2, i3, i2 - i3);
                                }
                                if (!z) {
                                    i = i2;
                                } else {
                                    newOutputStream.write(bArr);
                                    z = false;
                                    i = i2;
                                }
                                do {
                                    i++;
                                    if (i >= read) {
                                        break;
                                    }
                                } while (bArr2[i] != 10);
                                if (i >= read) {
                                    i3 = i2;
                                    i2 = i;
                                } else {
                                    z = true;
                                    i3 = i2;
                                    i2 = i;
                                }
                            }
                            i2++;
                        }
                        if (read > i3) {
                            newOutputStream.write(bArr2, i3, read - i3);
                        }
                    }
                } catch (java.io.IOException e) {
                    synchronized (this) {
                        this.mFailure = e.toString();
                        notifyAll();
                        return;
                    }
                }
            }
        }
    }
}
