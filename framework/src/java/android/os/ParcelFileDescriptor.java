package android.os;

/* loaded from: classes3.dex */
public class ParcelFileDescriptor implements android.os.Parcelable, java.io.Closeable {
    public static final android.os.Parcelable.Creator<android.os.ParcelFileDescriptor> CREATOR = new android.os.Parcelable.Creator<android.os.ParcelFileDescriptor>() { // from class: android.os.ParcelFileDescriptor.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ParcelFileDescriptor createFromParcel(android.os.Parcel parcel) {
            java.io.FileDescriptor fileDescriptor;
            int readInt = parcel.readInt();
            java.io.FileDescriptor readRawFileDescriptor = parcel.readRawFileDescriptor();
            if (readInt == 0) {
                fileDescriptor = null;
            } else {
                fileDescriptor = parcel.readRawFileDescriptor();
            }
            return new android.os.ParcelFileDescriptor(readRawFileDescriptor, fileDescriptor);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ParcelFileDescriptor[] newArray(int i) {
            return new android.os.ParcelFileDescriptor[i];
        }
    };
    private static final int MAX_STATUS = 1024;
    public static final int MODE_APPEND = 33554432;
    public static final int MODE_CREATE = 134217728;
    public static final int MODE_READ_ONLY = 268435456;
    public static final int MODE_READ_WRITE = 805306368;
    public static final int MODE_TRUNCATE = 67108864;

    @java.lang.Deprecated
    public static final int MODE_WORLD_READABLE = 1;

    @java.lang.Deprecated
    public static final int MODE_WORLD_WRITEABLE = 2;
    public static final int MODE_WRITE_ONLY = 536870912;
    private static final java.lang.String TAG = "ParcelFileDescriptor";
    private volatile boolean mClosed;
    private java.io.FileDescriptor mCommFd;
    private final java.io.FileDescriptor mFd;
    private final android.util.CloseGuard mGuard;
    private android.os.ParcelFileDescriptor.Status mStatus;
    private byte[] mStatusBuf;
    private final android.os.ParcelFileDescriptor mWrapped;

    public interface OnCloseListener {
        void onClose(java.io.IOException iOException);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_close$ravenwood(java.io.FileDescriptor fileDescriptor);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int native_getFdInt$ravenwood(java.io.FileDescriptor fileDescriptor);

    private static native java.io.FileDescriptor native_open$ravenwood(java.io.File file, int i) throws java.io.IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_setFdInt$ravenwood(java.io.FileDescriptor fileDescriptor, int i);

    public ParcelFileDescriptor(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        this.mGuard = android.util.CloseGuard.get();
        this.mWrapped = parcelFileDescriptor;
        this.mFd = null;
        this.mCommFd = null;
        this.mClosed = true;
    }

    public ParcelFileDescriptor(java.io.FileDescriptor fileDescriptor) {
        this(fileDescriptor, null);
    }

    public ParcelFileDescriptor(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2) {
        this.mGuard = android.util.CloseGuard.get();
        if (fileDescriptor == null) {
            throw new java.lang.NullPointerException("FileDescriptor must not be null");
        }
        this.mWrapped = null;
        this.mFd = fileDescriptor;
        setFdOwner(this.mFd);
        this.mCommFd = fileDescriptor2;
        if (this.mCommFd != null) {
            setFdOwner(this.mCommFd);
        }
        this.mGuard.open("close");
    }

    public static android.os.ParcelFileDescriptor open(java.io.File file, int i) throws java.io.FileNotFoundException {
        java.io.FileDescriptor openInternal = openInternal(file, i);
        if (openInternal == null) {
            return null;
        }
        return new android.os.ParcelFileDescriptor(openInternal);
    }

    public static android.os.ParcelFileDescriptor open(java.io.File file, int i, android.os.Handler handler, android.os.ParcelFileDescriptor.OnCloseListener onCloseListener) throws java.io.IOException {
        if (handler == null) {
            throw new java.lang.IllegalArgumentException("Handler must not be null");
        }
        if (onCloseListener == null) {
            throw new java.lang.IllegalArgumentException("Listener must not be null");
        }
        java.io.FileDescriptor openInternal = openInternal(file, i);
        if (openInternal == null) {
            return null;
        }
        return fromFd(openInternal, handler, onCloseListener);
    }

    public static android.os.ParcelFileDescriptor wrap(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.Handler handler, android.os.ParcelFileDescriptor.OnCloseListener onCloseListener) throws java.io.IOException {
        java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
        setFdInt(fileDescriptor, parcelFileDescriptor.detachFd());
        return fromFd(fileDescriptor, handler, onCloseListener);
    }

    public static android.os.ParcelFileDescriptor fromFd(java.io.FileDescriptor fileDescriptor, android.os.Handler handler, final android.os.ParcelFileDescriptor.OnCloseListener onCloseListener) throws java.io.IOException {
        if (handler == null) {
            throw new java.lang.IllegalArgumentException("Handler must not be null");
        }
        if (onCloseListener == null) {
            throw new java.lang.IllegalArgumentException("Listener must not be null");
        }
        java.io.FileDescriptor[] createCommSocketPair = createCommSocketPair();
        android.os.ParcelFileDescriptor parcelFileDescriptor = new android.os.ParcelFileDescriptor(fileDescriptor, createCommSocketPair[0]);
        final android.os.MessageQueue queue = handler.getLooper().getQueue();
        queue.addOnFileDescriptorEventListener(createCommSocketPair[1], 1, new android.os.MessageQueue.OnFileDescriptorEventListener() { // from class: android.os.ParcelFileDescriptor.1
            @Override // android.os.MessageQueue.OnFileDescriptorEventListener
            public int onFileDescriptorEvents(java.io.FileDescriptor fileDescriptor2, int i) {
                android.os.ParcelFileDescriptor.Status status;
                if ((i & 1) != 0) {
                    status = android.os.ParcelFileDescriptor.readCommStatus(fileDescriptor2, new byte[1024]);
                } else if ((i & 4) == 0) {
                    status = null;
                } else {
                    status = new android.os.ParcelFileDescriptor.Status(-2);
                }
                if (status != null) {
                    android.os.MessageQueue.this.removeOnFileDescriptorEventListener(fileDescriptor2);
                    android.os.ParcelFileDescriptor.closeInternal(fileDescriptor2);
                    onCloseListener.onClose(status.asIOException());
                    return 0;
                }
                return 1;
            }
        });
        return parcelFileDescriptor;
    }

    private static java.io.FileDescriptor openInternal(java.io.File file, int i) throws java.io.FileNotFoundException {
        if ((536870912 & i) != 0 && (33554432 & i) == 0 && (67108864 & i) == 0 && (268435456 & i) == 0 && file != null && file.exists()) {
            android.util.Slog.wtfQuiet(TAG, "ParcelFileDescriptor.open is called with w without t or a or r, which will have a different behavior beginning in Android Q.\nMode: " + i + "\nFilename: " + file.getPath());
        }
        int translateModePfdToPosix = android.os.FileUtils.translateModePfdToPosix(i) | ifAtLeastQ(android.system.OsConstants.O_CLOEXEC);
        int i2 = android.system.OsConstants.S_IRWXU | android.system.OsConstants.S_IRWXG;
        if ((i & 1) != 0) {
            i2 |= android.system.OsConstants.S_IROTH;
        }
        if ((i & 2) != 0) {
            i2 |= android.system.OsConstants.S_IWOTH;
        }
        try {
            return android.system.Os.open(file.getPath(), translateModePfdToPosix, i2);
        } catch (android.system.ErrnoException e) {
            throw new java.io.FileNotFoundException(e.getMessage());
        }
    }

    private static java.io.FileDescriptor openInternal$ravenwood(java.io.File file, int i) throws java.io.FileNotFoundException {
        try {
            return native_open$ravenwood(file, i);
        } catch (java.io.FileNotFoundException e) {
            throw e;
        } catch (java.io.IOException e2) {
            throw new java.io.FileNotFoundException(e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void closeInternal(java.io.FileDescriptor fileDescriptor) {
        libcore.io.IoUtils.closeQuietly(fileDescriptor);
    }

    public static android.os.ParcelFileDescriptor dup(java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
        try {
            java.io.FileDescriptor fileDescriptor2 = new java.io.FileDescriptor();
            setFdInt(fileDescriptor2, android.system.Os.fcntlInt(fileDescriptor, isAtLeastQ() ? android.system.OsConstants.F_DUPFD_CLOEXEC : android.system.OsConstants.F_DUPFD, 0));
            return new android.os.ParcelFileDescriptor(fileDescriptor2);
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public android.os.ParcelFileDescriptor dup() throws java.io.IOException {
        if (this.mWrapped != null) {
            return this.mWrapped.dup();
        }
        return dup(getFileDescriptor());
    }

    public static android.os.ParcelFileDescriptor fromFd(int i) throws java.io.IOException {
        java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
        setFdInt(fileDescriptor, i);
        try {
            java.io.FileDescriptor fileDescriptor2 = new java.io.FileDescriptor();
            setFdInt(fileDescriptor2, android.system.Os.fcntlInt(fileDescriptor, isAtLeastQ() ? android.system.OsConstants.F_DUPFD_CLOEXEC : android.system.OsConstants.F_DUPFD, 0));
            return new android.os.ParcelFileDescriptor(fileDescriptor2);
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public static android.os.ParcelFileDescriptor adoptFd(int i) {
        java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
        setFdInt(fileDescriptor, i);
        return new android.os.ParcelFileDescriptor(fileDescriptor);
    }

    public static android.os.ParcelFileDescriptor fromSocket(java.net.Socket socket) {
        java.io.FileDescriptor fileDescriptor$ = socket.getFileDescriptor$();
        if (fileDescriptor$ == null) {
            return null;
        }
        try {
            return dup(fileDescriptor$);
        } catch (java.io.IOException e) {
            throw new java.io.UncheckedIOException(e);
        }
    }

    public static android.os.ParcelFileDescriptor fromDatagramSocket(java.net.DatagramSocket datagramSocket) {
        java.io.FileDescriptor fileDescriptor$ = datagramSocket.getFileDescriptor$();
        if (fileDescriptor$ == null) {
            return null;
        }
        try {
            return dup(fileDescriptor$);
        } catch (java.io.IOException e) {
            throw new java.io.UncheckedIOException(e);
        }
    }

    public static android.os.ParcelFileDescriptor[] createPipe() throws java.io.IOException {
        try {
            java.io.FileDescriptor[] pipe2 = android.system.Os.pipe2(ifAtLeastQ(android.system.OsConstants.O_CLOEXEC));
            return new android.os.ParcelFileDescriptor[]{new android.os.ParcelFileDescriptor(pipe2[0]), new android.os.ParcelFileDescriptor(pipe2[1])};
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public static android.os.ParcelFileDescriptor[] createReliablePipe() throws java.io.IOException {
        try {
            java.io.FileDescriptor[] createCommSocketPair = createCommSocketPair();
            java.io.FileDescriptor[] pipe2 = android.system.Os.pipe2(ifAtLeastQ(android.system.OsConstants.O_CLOEXEC));
            return new android.os.ParcelFileDescriptor[]{new android.os.ParcelFileDescriptor(pipe2[0], createCommSocketPair[0]), new android.os.ParcelFileDescriptor(pipe2[1], createCommSocketPair[1])};
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public static android.os.ParcelFileDescriptor[] createSocketPair() throws java.io.IOException {
        return createSocketPair(android.system.OsConstants.SOCK_STREAM);
    }

    public static android.os.ParcelFileDescriptor[] createSocketPair(int i) throws java.io.IOException {
        try {
            java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
            java.io.FileDescriptor fileDescriptor2 = new java.io.FileDescriptor();
            android.system.Os.socketpair(android.system.OsConstants.AF_UNIX, i | ifAtLeastQ(android.system.OsConstants.SOCK_CLOEXEC), 0, fileDescriptor, fileDescriptor2);
            return new android.os.ParcelFileDescriptor[]{new android.os.ParcelFileDescriptor(fileDescriptor), new android.os.ParcelFileDescriptor(fileDescriptor2)};
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public static android.os.ParcelFileDescriptor[] createReliableSocketPair() throws java.io.IOException {
        return createReliableSocketPair(android.system.OsConstants.SOCK_STREAM);
    }

    public static android.os.ParcelFileDescriptor[] createReliableSocketPair(int i) throws java.io.IOException {
        try {
            java.io.FileDescriptor[] createCommSocketPair = createCommSocketPair();
            java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
            java.io.FileDescriptor fileDescriptor2 = new java.io.FileDescriptor();
            android.system.Os.socketpair(android.system.OsConstants.AF_UNIX, i | ifAtLeastQ(android.system.OsConstants.SOCK_CLOEXEC), 0, fileDescriptor, fileDescriptor2);
            return new android.os.ParcelFileDescriptor[]{new android.os.ParcelFileDescriptor(fileDescriptor, createCommSocketPair[0]), new android.os.ParcelFileDescriptor(fileDescriptor2, createCommSocketPair[1])};
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    private static java.io.FileDescriptor[] createCommSocketPair() throws java.io.IOException {
        try {
            java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
            java.io.FileDescriptor fileDescriptor2 = new java.io.FileDescriptor();
            android.system.Os.socketpair(android.system.OsConstants.AF_UNIX, android.system.OsConstants.SOCK_SEQPACKET | ifAtLeastQ(android.system.OsConstants.SOCK_CLOEXEC), 0, fileDescriptor, fileDescriptor2);
            libcore.io.IoUtils.setBlocking(fileDescriptor, false);
            libcore.io.IoUtils.setBlocking(fileDescriptor2, false);
            return new java.io.FileDescriptor[]{fileDescriptor, fileDescriptor2};
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    @java.lang.Deprecated
    public static android.os.ParcelFileDescriptor fromData(byte[] bArr, java.lang.String str) throws java.io.IOException {
        if (bArr == null) {
            return null;
        }
        android.os.MemoryFile memoryFile = new android.os.MemoryFile(str, bArr.length);
        try {
            if (bArr.length > 0) {
                memoryFile.writeBytes(bArr, 0, 0, bArr.length);
            }
            memoryFile.deactivate();
            java.io.FileDescriptor fileDescriptor = memoryFile.getFileDescriptor();
            return fileDescriptor != null ? dup(fileDescriptor) : null;
        } finally {
            memoryFile.close();
        }
    }

    public static int parseMode(java.lang.String str) {
        return android.os.FileUtils.translateModePosixToPfd(android.os.FileUtils.translateModeStringToPosix(str));
    }

    public static java.io.File getFile(java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
        try {
            java.lang.String readlink = android.system.Os.readlink("/proc/self/fd/" + getFdInt(fileDescriptor));
            if (!android.system.OsConstants.S_ISREG(android.system.Os.stat(readlink).st_mode) && !android.system.OsConstants.S_ISCHR(android.system.Os.stat(readlink).st_mode)) {
                throw new java.io.IOException("Not a regular file or character device: " + readlink);
            }
            return new java.io.File(readlink);
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public java.io.FileDescriptor getFileDescriptor() {
        if (this.mWrapped != null) {
            return this.mWrapped.getFileDescriptor();
        }
        return this.mFd;
    }

    public long getStatSize() {
        if (this.mWrapped != null) {
            return this.mWrapped.getStatSize();
        }
        try {
            android.system.StructStat fstat = android.system.Os.fstat(this.mFd);
            if (!android.system.OsConstants.S_ISREG(fstat.st_mode) && !android.system.OsConstants.S_ISLNK(fstat.st_mode)) {
                return -1L;
            }
            return fstat.st_size;
        } catch (android.system.ErrnoException e) {
            android.util.Log.w(TAG, "fstat() failed: " + e);
            return -1L;
        }
    }

    public long seekTo(long j) throws java.io.IOException {
        if (this.mWrapped != null) {
            return this.mWrapped.seekTo(j);
        }
        try {
            return android.system.Os.lseek(this.mFd, j, android.system.OsConstants.SEEK_SET);
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public int getFd() {
        if (this.mWrapped != null) {
            return this.mWrapped.getFd();
        }
        if (this.mClosed) {
            throw new java.lang.IllegalStateException("Already closed");
        }
        return getFdInt(this.mFd);
    }

    public int detachFd() {
        if (this.mWrapped != null) {
            return this.mWrapped.detachFd();
        }
        if (this.mClosed) {
            throw new java.lang.IllegalStateException("Already closed");
        }
        int acquireRawFd = acquireRawFd(this.mFd);
        writeCommStatusAndClose(2, null);
        this.mClosed = true;
        this.mGuard.close();
        releaseResources();
        return acquireRawFd;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        if (this.mWrapped != null) {
            try {
                this.mWrapped.close();
                return;
            } finally {
                releaseResources();
            }
        }
        closeWithStatus(0, null);
    }

    public void closeWithError(java.lang.String str) throws java.io.IOException {
        if (this.mWrapped != null) {
            try {
                this.mWrapped.closeWithError(str);
            } finally {
                releaseResources();
            }
        } else {
            if (str == null) {
                throw new java.lang.IllegalArgumentException("Message must not be null");
            }
            closeWithStatus(1, str);
        }
    }

    private void closeWithStatus(int i, java.lang.String str) {
        if (this.mClosed) {
            return;
        }
        this.mClosed = true;
        if (this.mGuard != null) {
            this.mGuard.close();
        }
        writeCommStatusAndClose(i, str);
        closeInternal(this.mFd);
        releaseResources();
    }

    public void releaseResources() {
    }

    private byte[] getOrCreateStatusBuffer() {
        if (this.mStatusBuf == null) {
            this.mStatusBuf = new byte[1024];
        }
        return this.mStatusBuf;
    }

    private void writeCommStatusAndClose(int i, java.lang.String str) {
        if (this.mCommFd == null) {
            if (str != null) {
                android.util.Log.w(TAG, "Unable to inform peer: " + str);
                return;
            }
            return;
        }
        if (i == 2) {
            android.util.Log.w(TAG, "Peer expected signal when closed; unable to deliver after detach");
        }
        if (i == -1) {
            return;
        }
        try {
            this.mStatus = readCommStatus(this.mCommFd, getOrCreateStatusBuffer());
            if (this.mStatus != null) {
                return;
            }
            try {
                byte[] orCreateStatusBuffer = getOrCreateStatusBuffer();
                libcore.io.Memory.pokeInt(orCreateStatusBuffer, 0, i, java.nio.ByteOrder.BIG_ENDIAN);
                int i2 = 4;
                if (str != null) {
                    byte[] bytes = str.getBytes();
                    int min = java.lang.Math.min(bytes.length, orCreateStatusBuffer.length - 4);
                    java.lang.System.arraycopy(bytes, 0, orCreateStatusBuffer, 4, min);
                    i2 = 4 + min;
                }
                android.system.Os.write(this.mCommFd, orCreateStatusBuffer, 0, i2);
            } catch (android.system.ErrnoException e) {
                android.util.Log.w(TAG, "Failed to report status: " + e);
            } catch (java.io.InterruptedIOException e2) {
                android.util.Log.w(TAG, "Failed to report status: " + e2);
            }
        } finally {
            closeInternal(this.mCommFd);
            this.mCommFd = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.ParcelFileDescriptor.Status readCommStatus(java.io.FileDescriptor fileDescriptor, byte[] bArr) {
        try {
            int read = android.system.Os.read(fileDescriptor, bArr, 0, bArr.length);
            if (read == 0) {
                return new android.os.ParcelFileDescriptor.Status(-2);
            }
            int peekInt = libcore.io.Memory.peekInt(bArr, 0, java.nio.ByteOrder.BIG_ENDIAN);
            if (peekInt == 1) {
                return new android.os.ParcelFileDescriptor.Status(peekInt, new java.lang.String(bArr, 4, read - 4));
            }
            return new android.os.ParcelFileDescriptor.Status(peekInt);
        } catch (android.system.ErrnoException e) {
            if (e.errno != android.system.OsConstants.EAGAIN) {
                android.util.Log.d(TAG, "Failed to read status; assuming dead: " + e);
                return new android.os.ParcelFileDescriptor.Status(-2);
            }
            return null;
        } catch (java.io.InterruptedIOException e2) {
            android.util.Log.d(TAG, "Failed to read status; assuming dead: " + e2);
            return new android.os.ParcelFileDescriptor.Status(-2);
        }
    }

    public boolean canDetectErrors() {
        if (this.mWrapped != null) {
            return this.mWrapped.canDetectErrors();
        }
        return this.mCommFd != null;
    }

    public void checkError() throws java.io.IOException {
        if (this.mWrapped != null) {
            this.mWrapped.checkError();
            return;
        }
        if (this.mStatus == null) {
            if (this.mCommFd == null) {
                android.util.Log.w(TAG, "Peer didn't provide a comm channel; unable to check for errors");
                return;
            }
            this.mStatus = readCommStatus(this.mCommFd, getOrCreateStatusBuffer());
        }
        if (this.mStatus == null || this.mStatus.status == 0) {
        } else {
            throw this.mStatus.asIOException();
        }
    }

    public static class AutoCloseInputStream extends java.io.FileInputStream {
        private final android.os.ParcelFileDescriptor mPfd;

        public AutoCloseInputStream(android.os.ParcelFileDescriptor parcelFileDescriptor) {
            super(parcelFileDescriptor.getFileDescriptor());
            this.mPfd = parcelFileDescriptor;
        }

        @Override // java.io.FileInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            try {
                super.close();
            } finally {
                this.mPfd.close();
            }
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int read() throws java.io.IOException {
            int read = super.read();
            if (read == -1 && this.mPfd.canDetectErrors()) {
                this.mPfd.checkError();
            }
            return read;
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr) throws java.io.IOException {
            int read = super.read(bArr);
            if (read == -1 && this.mPfd.canDetectErrors()) {
                this.mPfd.checkError();
            }
            return read;
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
            int read = super.read(bArr, i, i2);
            if (read == -1 && this.mPfd.canDetectErrors()) {
                this.mPfd.checkError();
            }
            return read;
        }
    }

    public static class AutoCloseOutputStream extends java.io.FileOutputStream {
        private final android.os.ParcelFileDescriptor mPfd;

        public AutoCloseOutputStream(android.os.ParcelFileDescriptor parcelFileDescriptor) {
            super(parcelFileDescriptor.getFileDescriptor());
            this.mPfd = parcelFileDescriptor;
        }

        @Override // java.io.FileOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            try {
                super.close();
            } finally {
                this.mPfd.close();
            }
        }
    }

    public java.lang.String toString() {
        if (this.mWrapped != null) {
            return this.mWrapped.toString();
        }
        return "{ParcelFileDescriptor: " + this.mFd + "}";
    }

    protected void finalize() throws java.lang.Throwable {
        if (this.mWrapped != null) {
            releaseResources();
        }
        if (this.mGuard != null) {
            this.mGuard.warnIfOpen();
        }
        try {
            if (!this.mClosed) {
                closeWithStatus(3, null);
            }
        } finally {
            super.finalize();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        if (this.mWrapped != null) {
            return this.mWrapped.describeContents();
        }
        return 1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mWrapped != null) {
            try {
                this.mWrapped.writeToParcel(parcel, i);
                return;
            } finally {
                releaseResources();
            }
        }
        if (this.mCommFd != null) {
            parcel.writeInt(1);
            parcel.writeFileDescriptor(this.mFd);
            parcel.writeFileDescriptor(this.mCommFd);
        } else {
            parcel.writeInt(0);
            parcel.writeFileDescriptor(this.mFd);
        }
        if ((i & 1) != 0 && !this.mClosed) {
            closeWithStatus(-1, null);
        }
    }

    public static class FileDescriptorDetachedException extends java.io.IOException {
        private static final long serialVersionUID = 955542466045L;

        public FileDescriptorDetachedException() {
            super("Remote side is detached");
        }
    }

    private static class Status {
        public static final int DEAD = -2;
        public static final int DETACHED = 2;
        public static final int ERROR = 1;
        public static final int LEAKED = 3;
        public static final int OK = 0;
        public static final int SILENCE = -1;
        public final java.lang.String msg;
        public final int status;

        public Status(int i) {
            this(i, null);
        }

        public Status(int i, java.lang.String str) {
            this.status = i;
            this.msg = str;
        }

        public java.io.IOException asIOException() {
            switch (this.status) {
                case -2:
                    return new java.io.IOException("Remote side is dead");
                case -1:
                default:
                    return new java.io.IOException("Unknown status: " + this.status);
                case 0:
                    return null;
                case 1:
                    return new java.io.IOException("Remote error: " + this.msg);
                case 2:
                    return new android.os.ParcelFileDescriptor.FileDescriptorDetachedException();
                case 3:
                    return new java.io.IOException("Remote side was leaked");
            }
        }

        public java.lang.String toString() {
            return "{" + this.status + ": " + this.msg + "}";
        }
    }

    private static void setFdInt(java.io.FileDescriptor fileDescriptor, int i) {
        fileDescriptor.setInt$(i);
    }

    private static int getFdInt(java.io.FileDescriptor fileDescriptor) {
        return fileDescriptor.getInt$();
    }

    private void setFdOwner(java.io.FileDescriptor fileDescriptor) {
        libcore.io.IoUtils.setFdOwner(fileDescriptor, this);
    }

    private void setFdOwner$ravenwood(java.io.FileDescriptor fileDescriptor) {
    }

    private int acquireRawFd(java.io.FileDescriptor fileDescriptor) {
        return libcore.io.IoUtils.acquireRawFd(fileDescriptor);
    }

    private int acquireRawFd$ravenwood(java.io.FileDescriptor fileDescriptor) {
        return getFdInt(fileDescriptor);
    }

    private static boolean isAtLeastQ() {
        return dalvik.system.VMRuntime.getRuntime().getTargetSdkVersion() >= 29;
    }

    private static int ifAtLeastQ(int i) {
        if (isAtLeastQ()) {
            return i;
        }
        return 0;
    }
}
