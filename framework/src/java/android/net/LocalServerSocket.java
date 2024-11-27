package android.net;

/* loaded from: classes2.dex */
public class LocalServerSocket implements java.io.Closeable {
    private static final int LISTEN_BACKLOG = 50;
    private final android.net.LocalSocketImpl impl;
    private final android.net.LocalSocketAddress localAddress;

    public LocalServerSocket(java.lang.String str) throws java.io.IOException {
        this.impl = new android.net.LocalSocketImpl();
        this.impl.create(2);
        this.localAddress = new android.net.LocalSocketAddress(str);
        this.impl.bind(this.localAddress);
        this.impl.listen(50);
    }

    public LocalServerSocket(java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
        this.impl = new android.net.LocalSocketImpl(fileDescriptor);
        this.impl.listen(50);
        this.localAddress = this.impl.getSockAddress();
    }

    public android.net.LocalSocketAddress getLocalSocketAddress() {
        return this.localAddress;
    }

    public android.net.LocalSocket accept() throws java.io.IOException {
        android.net.LocalSocketImpl localSocketImpl = new android.net.LocalSocketImpl();
        this.impl.accept(localSocketImpl);
        return android.net.LocalSocket.createLocalSocketForAccept(localSocketImpl);
    }

    public java.io.FileDescriptor getFileDescriptor() {
        return this.impl.getFileDescriptor();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        this.impl.close();
    }
}
