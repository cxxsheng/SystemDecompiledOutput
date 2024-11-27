package android.os;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class FileBridge extends java.lang.Thread {
    private static final int CMD_CLOSE = 3;
    private static final int CMD_FSYNC = 2;
    private static final int CMD_WRITE = 1;
    private static final int MSG_LENGTH = 8;
    private static final java.lang.String TAG = "FileBridge";
    private android.os.ParcelFileDescriptor mClient;
    private volatile boolean mClosed;
    private android.os.ParcelFileDescriptor mServer;
    private android.os.ParcelFileDescriptor mTarget;

    public FileBridge() {
        try {
            android.os.ParcelFileDescriptor[] createSocketPair = android.os.ParcelFileDescriptor.createSocketPair(android.system.OsConstants.SOCK_STREAM);
            this.mServer = createSocketPair[0];
            this.mClient = createSocketPair[1];
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Failed to create bridge");
        }
    }

    public boolean isClosed() {
        return this.mClosed;
    }

    public void forceClose() {
        libcore.io.IoUtils.closeQuietly(this.mTarget);
        libcore.io.IoUtils.closeQuietly(this.mServer);
        this.mClosed = true;
    }

    public void setTargetFile(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        this.mTarget = parcelFileDescriptor;
    }

    public android.os.ParcelFileDescriptor getClientSocket() {
        return this.mClient;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        java.nio.ByteBuffer allocateDirect = java.nio.ByteBuffer.allocateDirect(8192);
        byte[] array = allocateDirect.hasArray() ? allocateDirect.array() : new byte[8192];
        while (true) {
            try {
                try {
                    if (libcore.io.IoBridge.read(this.mServer.getFileDescriptor(), array, 0, 8) != 8) {
                        break;
                    }
                    int peekInt = libcore.io.Memory.peekInt(array, 0, java.nio.ByteOrder.BIG_ENDIAN);
                    if (peekInt == 1) {
                        int peekInt2 = libcore.io.Memory.peekInt(array, 4, java.nio.ByteOrder.BIG_ENDIAN);
                        while (peekInt2 > 0) {
                            int read = libcore.io.IoBridge.read(this.mServer.getFileDescriptor(), array, 0, java.lang.Math.min(array.length, peekInt2));
                            if (read == -1) {
                                throw new java.io.IOException("Unexpected EOF; still expected " + peekInt2 + " bytes");
                            }
                            libcore.io.IoBridge.write(this.mTarget.getFileDescriptor(), array, 0, read);
                            peekInt2 -= read;
                        }
                    } else if (peekInt == 2) {
                        android.system.Os.fsync(this.mTarget.getFileDescriptor());
                        libcore.io.IoBridge.write(this.mServer.getFileDescriptor(), array, 0, 8);
                    } else if (peekInt == 3) {
                        android.system.Os.fsync(this.mTarget.getFileDescriptor());
                        this.mTarget.close();
                        this.mClosed = true;
                        libcore.io.IoBridge.write(this.mServer.getFileDescriptor(), array, 0, 8);
                        break;
                    }
                } catch (android.system.ErrnoException | java.io.IOException e) {
                    android.util.Log.wtf(TAG, "Failed during bridge", e);
                }
            } finally {
                forceClose();
            }
        }
    }

    public static class FileBridgeOutputStream extends java.io.OutputStream {
        private final java.io.FileDescriptor mClient;
        private final android.os.ParcelFileDescriptor mClientPfd;
        private final byte[] mTemp;
        private final java.nio.ByteBuffer mTempBuffer = java.nio.ByteBuffer.allocateDirect(8);

        public FileBridgeOutputStream(android.os.ParcelFileDescriptor parcelFileDescriptor) {
            byte[] bArr;
            if (this.mTempBuffer.hasArray()) {
                bArr = this.mTempBuffer.array();
            } else {
                bArr = new byte[8];
            }
            this.mTemp = bArr;
            this.mClientPfd = parcelFileDescriptor;
            this.mClient = parcelFileDescriptor.getFileDescriptor();
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            try {
                writeCommandAndBlock(3, "close()");
            } finally {
                libcore.io.IoUtils.closeQuietly(this.mClientPfd);
            }
        }

        public void fsync() throws java.io.IOException {
            writeCommandAndBlock(2, "fsync()");
        }

        private void writeCommandAndBlock(int i, java.lang.String str) throws java.io.IOException {
            libcore.io.Memory.pokeInt(this.mTemp, 0, i, java.nio.ByteOrder.BIG_ENDIAN);
            libcore.io.IoBridge.write(this.mClient, this.mTemp, 0, 8);
            if (libcore.io.IoBridge.read(this.mClient, this.mTemp, 0, 8) == 8 && libcore.io.Memory.peekInt(this.mTemp, 0, java.nio.ByteOrder.BIG_ENDIAN) == i) {
            } else {
                throw new java.io.IOException("Failed to execute " + str + " across bridge");
            }
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
            com.android.internal.util.ArrayUtils.throwsIfOutOfBounds(bArr.length, i, i2);
            libcore.io.Memory.pokeInt(this.mTemp, 0, 1, java.nio.ByteOrder.BIG_ENDIAN);
            libcore.io.Memory.pokeInt(this.mTemp, 4, i2, java.nio.ByteOrder.BIG_ENDIAN);
            libcore.io.IoBridge.write(this.mClient, this.mTemp, 0, 8);
            libcore.io.IoBridge.write(this.mClient, bArr, i, i2);
        }

        @Override // java.io.OutputStream
        public void write(int i) throws java.io.IOException {
            libcore.io.Streams.writeSingleByte(this, i);
        }
    }
}
