package android.net;

/* loaded from: classes2.dex */
public class LocalSocket implements java.io.Closeable {
    public static final int SOCKET_DGRAM = 1;
    public static final int SOCKET_SEQPACKET = 3;
    public static final int SOCKET_STREAM = 2;
    static final int SOCKET_UNKNOWN = 0;
    private final android.net.LocalSocketImpl impl;
    private volatile boolean implCreated;
    private boolean isBound;
    private boolean isConnected;
    private android.net.LocalSocketAddress localAddress;
    private final int sockType;

    public LocalSocket() {
        this(2);
    }

    public LocalSocket(int i) {
        this(new android.net.LocalSocketImpl(), i);
    }

    private LocalSocket(android.net.LocalSocketImpl localSocketImpl, int i) {
        this.impl = localSocketImpl;
        this.sockType = i;
        this.isConnected = false;
        this.isBound = false;
    }

    private void checkConnected() {
        try {
            android.system.Os.getpeername(this.impl.getFileDescriptor());
            this.isConnected = true;
            this.isBound = true;
            this.implCreated = true;
        } catch (android.system.ErrnoException e) {
            throw new java.lang.IllegalArgumentException("Not a connected socket", e);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public LocalSocket(java.io.FileDescriptor fileDescriptor) {
        this(new android.net.LocalSocketImpl(fileDescriptor), 0);
        checkConnected();
    }

    static android.net.LocalSocket createLocalSocketForAccept(android.net.LocalSocketImpl localSocketImpl) {
        android.net.LocalSocket localSocket = new android.net.LocalSocket(localSocketImpl, 0);
        localSocket.checkConnected();
        return localSocket;
    }

    public java.lang.String toString() {
        return super.toString() + " impl:" + this.impl;
    }

    private void implCreateIfNeeded() throws java.io.IOException {
        if (!this.implCreated) {
            synchronized (this) {
                if (!this.implCreated) {
                    try {
                        this.impl.create(this.sockType);
                        this.implCreated = true;
                    } catch (java.lang.Throwable th) {
                        this.implCreated = true;
                        throw th;
                    }
                }
            }
        }
    }

    public void connect(android.net.LocalSocketAddress localSocketAddress) throws java.io.IOException {
        synchronized (this) {
            if (this.isConnected) {
                throw new java.io.IOException("already connected");
            }
            implCreateIfNeeded();
            this.impl.connect(localSocketAddress, 0);
            this.isConnected = true;
            this.isBound = true;
        }
    }

    public void bind(android.net.LocalSocketAddress localSocketAddress) throws java.io.IOException {
        implCreateIfNeeded();
        synchronized (this) {
            if (this.isBound) {
                throw new java.io.IOException("already bound");
            }
            this.localAddress = localSocketAddress;
            this.impl.bind(this.localAddress);
            this.isBound = true;
        }
    }

    public android.net.LocalSocketAddress getLocalSocketAddress() {
        return this.localAddress;
    }

    public java.io.InputStream getInputStream() throws java.io.IOException {
        implCreateIfNeeded();
        return this.impl.getInputStream();
    }

    public java.io.OutputStream getOutputStream() throws java.io.IOException {
        implCreateIfNeeded();
        return this.impl.getOutputStream();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        implCreateIfNeeded();
        this.impl.close();
    }

    public void shutdownInput() throws java.io.IOException {
        implCreateIfNeeded();
        this.impl.shutdownInput();
    }

    public void shutdownOutput() throws java.io.IOException {
        implCreateIfNeeded();
        this.impl.shutdownOutput();
    }

    public void setReceiveBufferSize(int i) throws java.io.IOException {
        this.impl.setOption(4098, java.lang.Integer.valueOf(i));
    }

    public int getReceiveBufferSize() throws java.io.IOException {
        return ((java.lang.Integer) this.impl.getOption(4098)).intValue();
    }

    public void setSoTimeout(int i) throws java.io.IOException {
        this.impl.setOption(4102, java.lang.Integer.valueOf(i));
    }

    public int getSoTimeout() throws java.io.IOException {
        return ((java.lang.Integer) this.impl.getOption(4102)).intValue();
    }

    public void setSendBufferSize(int i) throws java.io.IOException {
        this.impl.setOption(4097, java.lang.Integer.valueOf(i));
    }

    public int getSendBufferSize() throws java.io.IOException {
        return ((java.lang.Integer) this.impl.getOption(4097)).intValue();
    }

    public android.net.LocalSocketAddress getRemoteSocketAddress() {
        throw new java.lang.UnsupportedOperationException();
    }

    public synchronized boolean isConnected() {
        return this.isConnected;
    }

    public boolean isClosed() {
        throw new java.lang.UnsupportedOperationException();
    }

    public synchronized boolean isBound() {
        return this.isBound;
    }

    public boolean isOutputShutdown() {
        throw new java.lang.UnsupportedOperationException();
    }

    public boolean isInputShutdown() {
        throw new java.lang.UnsupportedOperationException();
    }

    public void connect(android.net.LocalSocketAddress localSocketAddress, int i) throws java.io.IOException {
        throw new java.lang.UnsupportedOperationException();
    }

    public void setFileDescriptorsForSend(java.io.FileDescriptor[] fileDescriptorArr) {
        this.impl.setFileDescriptorsForSend(fileDescriptorArr);
    }

    public java.io.FileDescriptor[] getAncillaryFileDescriptors() throws java.io.IOException {
        return this.impl.getAncillaryFileDescriptors();
    }

    public android.net.Credentials getPeerCredentials() throws java.io.IOException {
        return this.impl.getPeerCredentials();
    }

    public java.io.FileDescriptor getFileDescriptor() {
        return this.impl.getFileDescriptor();
    }
}
