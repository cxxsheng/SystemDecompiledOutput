package android.net;

/* loaded from: classes2.dex */
class LocalSocketImpl {
    private java.io.FileDescriptor fd;
    private android.net.LocalSocketImpl.SocketInputStream fis;
    private android.net.LocalSocketImpl.SocketOutputStream fos;
    java.io.FileDescriptor[] inboundFileDescriptors;
    private boolean mFdCreatedInternally;
    java.io.FileDescriptor[] outboundFileDescriptors;
    private java.lang.Object readMonitor = new java.lang.Object();
    private java.lang.Object writeMonitor = new java.lang.Object();

    private native void bindLocal(java.io.FileDescriptor fileDescriptor, java.lang.String str, int i) throws java.io.IOException;

    private native void connectLocal(java.io.FileDescriptor fileDescriptor, java.lang.String str, int i) throws java.io.IOException;

    private native android.net.Credentials getPeerCredentials_native(java.io.FileDescriptor fileDescriptor) throws java.io.IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public native int read_native(java.io.FileDescriptor fileDescriptor) throws java.io.IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public native int readba_native(byte[] bArr, int i, int i2, java.io.FileDescriptor fileDescriptor) throws java.io.IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public native void write_native(int i, java.io.FileDescriptor fileDescriptor) throws java.io.IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public native void writeba_native(byte[] bArr, int i, int i2, java.io.FileDescriptor fileDescriptor) throws java.io.IOException;

    class SocketInputStream extends java.io.InputStream {
        SocketInputStream() {
        }

        @Override // java.io.InputStream
        public int available() throws java.io.IOException {
            java.io.FileDescriptor fileDescriptor = android.net.LocalSocketImpl.this.fd;
            if (fileDescriptor == null) {
                throw new java.io.IOException("socket closed");
            }
            try {
                return android.system.Os.ioctlInt(fileDescriptor, android.system.OsConstants.FIONREAD);
            } catch (android.system.ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            android.net.LocalSocketImpl.this.close();
        }

        @Override // java.io.InputStream
        public int read() throws java.io.IOException {
            int read_native;
            synchronized (android.net.LocalSocketImpl.this.readMonitor) {
                java.io.FileDescriptor fileDescriptor = android.net.LocalSocketImpl.this.fd;
                if (fileDescriptor == null) {
                    throw new java.io.IOException("socket closed");
                }
                read_native = android.net.LocalSocketImpl.this.read_native(fileDescriptor);
            }
            return read_native;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr) throws java.io.IOException {
            return read(bArr, 0, bArr.length);
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
            int readba_native;
            synchronized (android.net.LocalSocketImpl.this.readMonitor) {
                java.io.FileDescriptor fileDescriptor = android.net.LocalSocketImpl.this.fd;
                if (fileDescriptor == null) {
                    throw new java.io.IOException("socket closed");
                }
                if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
                    throw new java.lang.ArrayIndexOutOfBoundsException();
                }
                readba_native = android.net.LocalSocketImpl.this.readba_native(bArr, i, i2, fileDescriptor);
            }
            return readba_native;
        }
    }

    class SocketOutputStream extends java.io.OutputStream {
        SocketOutputStream() {
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            android.net.LocalSocketImpl.this.close();
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws java.io.IOException {
            write(bArr, 0, bArr.length);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
            synchronized (android.net.LocalSocketImpl.this.writeMonitor) {
                java.io.FileDescriptor fileDescriptor = android.net.LocalSocketImpl.this.fd;
                if (fileDescriptor == null) {
                    throw new java.io.IOException("socket closed");
                }
                if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
                    throw new java.lang.ArrayIndexOutOfBoundsException();
                }
                android.net.LocalSocketImpl.this.writeba_native(bArr, i, i2, fileDescriptor);
            }
        }

        @Override // java.io.OutputStream
        public void write(int i) throws java.io.IOException {
            synchronized (android.net.LocalSocketImpl.this.writeMonitor) {
                java.io.FileDescriptor fileDescriptor = android.net.LocalSocketImpl.this.fd;
                if (fileDescriptor == null) {
                    throw new java.io.IOException("socket closed");
                }
                android.net.LocalSocketImpl.this.write_native(i, fileDescriptor);
            }
        }
    }

    LocalSocketImpl() {
    }

    LocalSocketImpl(java.io.FileDescriptor fileDescriptor) {
        this.fd = fileDescriptor;
    }

    public java.lang.String toString() {
        return super.toString() + " fd:" + this.fd;
    }

    public void create(int i) throws java.io.IOException {
        int i2;
        if (this.fd != null) {
            throw new java.io.IOException("LocalSocketImpl already has an fd");
        }
        switch (i) {
            case 1:
                i2 = android.system.OsConstants.SOCK_DGRAM;
                break;
            case 2:
                i2 = android.system.OsConstants.SOCK_STREAM;
                break;
            case 3:
                i2 = android.system.OsConstants.SOCK_SEQPACKET;
                break;
            default:
                throw new java.lang.IllegalStateException("unknown sockType");
        }
        try {
            this.fd = android.system.Os.socket(android.system.OsConstants.AF_UNIX, i2, 0);
            this.mFdCreatedInternally = true;
        } catch (android.system.ErrnoException e) {
            e.rethrowAsIOException();
        }
    }

    public void close() throws java.io.IOException {
        synchronized (this) {
            if (this.fd == null || !this.mFdCreatedInternally) {
                this.fd = null;
                return;
            }
            try {
                android.system.Os.close(this.fd);
            } catch (android.system.ErrnoException e) {
                e.rethrowAsIOException();
            }
            this.fd = null;
        }
    }

    protected void connect(android.net.LocalSocketAddress localSocketAddress, int i) throws java.io.IOException {
        if (this.fd == null) {
            throw new java.io.IOException("socket not created");
        }
        connectLocal(this.fd, localSocketAddress.getName(), localSocketAddress.getNamespace().getId());
    }

    public void bind(android.net.LocalSocketAddress localSocketAddress) throws java.io.IOException {
        if (this.fd == null) {
            throw new java.io.IOException("socket not created");
        }
        bindLocal(this.fd, localSocketAddress.getName(), localSocketAddress.getNamespace().getId());
    }

    protected void listen(int i) throws java.io.IOException {
        if (this.fd == null) {
            throw new java.io.IOException("socket not created");
        }
        try {
            android.system.Os.listen(this.fd, i);
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    protected void accept(android.net.LocalSocketImpl localSocketImpl) throws java.io.IOException {
        if (this.fd == null) {
            throw new java.io.IOException("socket not created");
        }
        try {
            localSocketImpl.fd = android.system.Os.accept(this.fd, null);
            localSocketImpl.mFdCreatedInternally = true;
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    protected java.io.InputStream getInputStream() throws java.io.IOException {
        android.net.LocalSocketImpl.SocketInputStream socketInputStream;
        if (this.fd == null) {
            throw new java.io.IOException("socket not created");
        }
        synchronized (this) {
            if (this.fis == null) {
                this.fis = new android.net.LocalSocketImpl.SocketInputStream();
            }
            socketInputStream = this.fis;
        }
        return socketInputStream;
    }

    protected java.io.OutputStream getOutputStream() throws java.io.IOException {
        android.net.LocalSocketImpl.SocketOutputStream socketOutputStream;
        if (this.fd == null) {
            throw new java.io.IOException("socket not created");
        }
        synchronized (this) {
            if (this.fos == null) {
                this.fos = new android.net.LocalSocketImpl.SocketOutputStream();
            }
            socketOutputStream = this.fos;
        }
        return socketOutputStream;
    }

    protected int available() throws java.io.IOException {
        return getInputStream().available();
    }

    protected void shutdownInput() throws java.io.IOException {
        if (this.fd == null) {
            throw new java.io.IOException("socket not created");
        }
        try {
            android.system.Os.shutdown(this.fd, android.system.OsConstants.SHUT_RD);
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    protected void shutdownOutput() throws java.io.IOException {
        if (this.fd == null) {
            throw new java.io.IOException("socket not created");
        }
        try {
            android.system.Os.shutdown(this.fd, android.system.OsConstants.SHUT_WR);
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    protected java.io.FileDescriptor getFileDescriptor() {
        return this.fd;
    }

    protected boolean supportsUrgentData() {
        return false;
    }

    protected void sendUrgentData(int i) throws java.io.IOException {
        throw new java.lang.RuntimeException("not impled");
    }

    public java.lang.Object getOption(int i) throws java.io.IOException {
        if (this.fd == null) {
            throw new java.io.IOException("socket not created");
        }
        try {
            switch (i) {
                case 1:
                    return java.lang.Integer.valueOf(android.system.Os.getsockoptInt(this.fd, android.system.OsConstants.IPPROTO_TCP, android.system.OsConstants.TCP_NODELAY));
                case 4:
                case 4097:
                case 4098:
                    return java.lang.Integer.valueOf(android.system.Os.getsockoptInt(this.fd, android.system.OsConstants.SOL_SOCKET, javaSoToOsOpt(i)));
                case 128:
                    android.system.StructLinger structLinger = android.system.Os.getsockoptLinger(this.fd, android.system.OsConstants.SOL_SOCKET, android.system.OsConstants.SO_LINGER);
                    if (!structLinger.isOn()) {
                        return -1;
                    }
                    return java.lang.Integer.valueOf(structLinger.l_linger);
                case 4102:
                    return java.lang.Integer.valueOf((int) android.system.Os.getsockoptTimeval(this.fd, android.system.OsConstants.SOL_SOCKET, android.system.OsConstants.SO_SNDTIMEO).toMillis());
                default:
                    throw new java.io.IOException("Unknown option: " + i);
            }
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v6, types: [int] */
    public void setOption(int i, java.lang.Object obj) throws java.io.IOException {
        int i2;
        ?? r0;
        if (this.fd == null) {
            throw new java.io.IOException("socket not created");
        }
        if (obj instanceof java.lang.Integer) {
            i2 = ((java.lang.Integer) obj).intValue();
            r0 = -1;
        } else if (obj instanceof java.lang.Boolean) {
            boolean booleanValue = ((java.lang.Boolean) obj).booleanValue();
            i2 = 0;
            r0 = booleanValue;
        } else {
            throw new java.io.IOException("bad value: " + obj);
        }
        try {
            switch (i) {
                case 1:
                    android.system.Os.setsockoptInt(this.fd, android.system.OsConstants.IPPROTO_TCP, android.system.OsConstants.TCP_NODELAY, i2);
                    return;
                case 4:
                case 4097:
                case 4098:
                    android.system.Os.setsockoptInt(this.fd, android.system.OsConstants.SOL_SOCKET, javaSoToOsOpt(i), i2);
                    return;
                case 128:
                    android.system.Os.setsockoptLinger(this.fd, android.system.OsConstants.SOL_SOCKET, android.system.OsConstants.SO_LINGER, new android.system.StructLinger((int) r0, i2));
                    return;
                case 4102:
                    android.system.StructTimeval fromMillis = android.system.StructTimeval.fromMillis(i2);
                    android.system.Os.setsockoptTimeval(this.fd, android.system.OsConstants.SOL_SOCKET, android.system.OsConstants.SO_RCVTIMEO, fromMillis);
                    android.system.Os.setsockoptTimeval(this.fd, android.system.OsConstants.SOL_SOCKET, android.system.OsConstants.SO_SNDTIMEO, fromMillis);
                    return;
                default:
                    throw new java.io.IOException("Unknown option: " + i);
            }
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public void setFileDescriptorsForSend(java.io.FileDescriptor[] fileDescriptorArr) {
        synchronized (this.writeMonitor) {
            this.outboundFileDescriptors = fileDescriptorArr;
        }
    }

    public java.io.FileDescriptor[] getAncillaryFileDescriptors() throws java.io.IOException {
        java.io.FileDescriptor[] fileDescriptorArr;
        synchronized (this.readMonitor) {
            fileDescriptorArr = this.inboundFileDescriptors;
            this.inboundFileDescriptors = null;
        }
        return fileDescriptorArr;
    }

    public android.net.Credentials getPeerCredentials() throws java.io.IOException {
        return getPeerCredentials_native(this.fd);
    }

    public android.net.LocalSocketAddress getSockAddress() throws java.io.IOException {
        return null;
    }

    protected void finalize() throws java.io.IOException {
        close();
    }

    private static int javaSoToOsOpt(int i) {
        switch (i) {
            case 4:
                return android.system.OsConstants.SO_REUSEADDR;
            case 4097:
                return android.system.OsConstants.SO_SNDBUF;
            case 4098:
                return android.system.OsConstants.SO_RCVBUF;
            default:
                throw new java.lang.UnsupportedOperationException("Unknown option: " + i);
        }
    }
}
