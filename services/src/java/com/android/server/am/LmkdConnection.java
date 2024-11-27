package com.android.server.am;

/* loaded from: classes.dex */
public class LmkdConnection {
    private static final int LMKD_REPLY_MAX_SIZE = 222;
    private static final java.lang.String TAG = "ActivityManager";
    private final com.android.server.am.LmkdConnection.LmkdConnectionListener mListener;
    private final android.os.MessageQueue mMsgQueue;
    private final java.lang.Object mLmkdSocketLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLmkdSocketLock"})
    private android.net.LocalSocket mLmkdSocket = null;

    @com.android.internal.annotations.GuardedBy({"mLmkdSocketLock"})
    private java.io.OutputStream mLmkdOutputStream = null;

    @com.android.internal.annotations.GuardedBy({"mLmkdSocketLock"})
    private java.io.InputStream mLmkdInputStream = null;
    private final java.nio.ByteBuffer mInputBuf = java.nio.ByteBuffer.allocate(LMKD_REPLY_MAX_SIZE);
    private final java.io.DataInputStream mInputData = new java.io.DataInputStream(new java.io.ByteArrayInputStream(this.mInputBuf.array()));
    private final java.lang.Object mReplyBufLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mReplyBufLock"})
    private java.nio.ByteBuffer mReplyBuf = null;

    interface LmkdConnectionListener {
        boolean handleUnsolicitedMessage(java.io.DataInputStream dataInputStream, int i);

        boolean isReplyExpected(java.nio.ByteBuffer byteBuffer, java.nio.ByteBuffer byteBuffer2, int i);

        boolean onConnect(java.io.OutputStream outputStream);

        void onDisconnect();
    }

    LmkdConnection(android.os.MessageQueue messageQueue, com.android.server.am.LmkdConnection.LmkdConnectionListener lmkdConnectionListener) {
        this.mMsgQueue = messageQueue;
        this.mListener = lmkdConnectionListener;
    }

    public boolean connect() {
        synchronized (this.mLmkdSocketLock) {
            try {
                if (this.mLmkdSocket != null) {
                    return true;
                }
                android.net.LocalSocket openSocket = openSocket();
                if (openSocket == null) {
                    android.util.Slog.w(TAG, "Failed to connect to lowmemorykiller, retry later");
                    return false;
                }
                try {
                    java.io.OutputStream outputStream = openSocket.getOutputStream();
                    java.io.InputStream inputStream = openSocket.getInputStream();
                    if (this.mListener != null && !this.mListener.onConnect(outputStream)) {
                        android.util.Slog.w(TAG, "Failed to communicate with lowmemorykiller, retry later");
                        libcore.io.IoUtils.closeQuietly(openSocket);
                        return false;
                    }
                    this.mLmkdSocket = openSocket;
                    this.mLmkdOutputStream = outputStream;
                    this.mLmkdInputStream = inputStream;
                    this.mMsgQueue.addOnFileDescriptorEventListener(this.mLmkdSocket.getFileDescriptor(), 5, new android.os.MessageQueue.OnFileDescriptorEventListener() { // from class: com.android.server.am.LmkdConnection.1
                        @Override // android.os.MessageQueue.OnFileDescriptorEventListener
                        public int onFileDescriptorEvents(java.io.FileDescriptor fileDescriptor, int i) {
                            return com.android.server.am.LmkdConnection.this.fileDescriptorEventHandler(fileDescriptor, i);
                        }
                    });
                    this.mLmkdSocketLock.notifyAll();
                    return true;
                } catch (java.io.IOException e) {
                    libcore.io.IoUtils.closeQuietly(openSocket);
                    return false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int fileDescriptorEventHandler(java.io.FileDescriptor fileDescriptor, int i) {
        if (this.mListener == null) {
            return 0;
        }
        if ((i & 1) != 0) {
            processIncomingData();
        }
        if ((i & 4) != 0) {
            synchronized (this.mLmkdSocketLock) {
                this.mMsgQueue.removeOnFileDescriptorEventListener(this.mLmkdSocket.getFileDescriptor());
                libcore.io.IoUtils.closeQuietly(this.mLmkdSocket);
                this.mLmkdSocket = null;
            }
            synchronized (this.mReplyBufLock) {
                try {
                    if (this.mReplyBuf != null) {
                        this.mReplyBuf = null;
                        this.mReplyBufLock.notifyAll();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mListener.onDisconnect();
            return 0;
        }
        return 5;
    }

    private void processIncomingData() {
        int read = read(this.mInputBuf);
        if (read > 0) {
            try {
                this.mInputData.reset();
                synchronized (this.mReplyBufLock) {
                    try {
                        if (this.mReplyBuf != null) {
                            if (this.mListener.isReplyExpected(this.mReplyBuf, this.mInputBuf, read)) {
                                this.mReplyBuf.put(this.mInputBuf.array(), 0, read);
                                this.mReplyBuf.rewind();
                                this.mReplyBufLock.notifyAll();
                            } else if (!this.mListener.handleUnsolicitedMessage(this.mInputData, read)) {
                                this.mReplyBuf = null;
                                this.mReplyBufLock.notifyAll();
                                android.util.Slog.e(TAG, "Received an unexpected packet from lmkd");
                            }
                        } else if (!this.mListener.handleUnsolicitedMessage(this.mInputData, read)) {
                            android.util.Slog.w(TAG, "Received an unexpected packet from lmkd");
                        }
                    } finally {
                    }
                }
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Failed to parse lmkd data buffer. Size = " + read);
            }
        }
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.mLmkdSocketLock) {
            z = this.mLmkdSocket != null;
        }
        return z;
    }

    public boolean waitForConnection(long j) {
        synchronized (this.mLmkdSocketLock) {
            if (this.mLmkdSocket != null) {
                return true;
            }
            try {
                this.mLmkdSocketLock.wait(j);
                return this.mLmkdSocket != null;
            } catch (java.lang.InterruptedException e) {
                return false;
            }
        }
    }

    private android.net.LocalSocket openSocket() {
        try {
            android.net.LocalSocket localSocket = new android.net.LocalSocket(3);
            localSocket.connect(new android.net.LocalSocketAddress("lmkd", android.net.LocalSocketAddress.Namespace.RESERVED));
            return localSocket;
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Connection failed: " + e.toString());
            return null;
        }
    }

    private boolean write(java.nio.ByteBuffer byteBuffer) {
        synchronized (this.mLmkdSocketLock) {
            try {
                try {
                    this.mLmkdOutputStream.write(byteBuffer.array(), 0, byteBuffer.position());
                } catch (java.io.IOException e) {
                    return false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return true;
    }

    private int read(java.nio.ByteBuffer byteBuffer) {
        int read;
        synchronized (this.mLmkdSocketLock) {
            try {
                try {
                    read = this.mLmkdInputStream.read(byteBuffer.array(), 0, byteBuffer.array().length);
                } catch (java.io.IOException e) {
                    return -1;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return read;
    }

    public boolean exchange(java.nio.ByteBuffer byteBuffer, java.nio.ByteBuffer byteBuffer2) {
        boolean z;
        if (byteBuffer2 == null) {
            return write(byteBuffer);
        }
        synchronized (this.mReplyBufLock) {
            this.mReplyBuf = byteBuffer2;
            z = false;
            if (write(byteBuffer)) {
                try {
                    this.mReplyBufLock.wait();
                    if (this.mReplyBuf != null) {
                        z = true;
                    }
                } catch (java.lang.InterruptedException e) {
                }
            }
            this.mReplyBuf = null;
        }
        return z;
    }
}
