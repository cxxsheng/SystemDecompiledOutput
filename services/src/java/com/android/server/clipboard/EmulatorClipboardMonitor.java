package com.android.server.clipboard;

/* loaded from: classes.dex */
class EmulatorClipboardMonitor implements java.util.function.Consumer<android.content.ClipData> {
    private static final int HOST_PORT = 5000;
    private static final boolean LOG_CLIBOARD_ACCESS = android.os.SystemProperties.getBoolean("ro.boot.qemu.log_clipboard_access", false);
    private static final int MAX_CLIPBOARD_BYTES = 134217728;
    private static final java.lang.String PIPE_NAME = "pipe:clipboard";
    private static final java.lang.String TAG = "EmulatorClipboardMonitor";
    private final java.lang.Thread mHostMonitorThread;
    private java.io.FileDescriptor mPipe = null;

    private static byte[] createOpenHandshake() {
        byte[] copyOf = java.util.Arrays.copyOf(PIPE_NAME.getBytes(), PIPE_NAME.length() + 1);
        copyOf[PIPE_NAME.length()] = 0;
        return copyOf;
    }

    private synchronized java.io.FileDescriptor getPipeFD() {
        return this.mPipe;
    }

    private synchronized void setPipeFD(java.io.FileDescriptor fileDescriptor) {
        this.mPipe = fileDescriptor;
    }

    private static java.io.FileDescriptor openPipeImpl() {
        try {
            java.io.FileDescriptor socket = android.system.Os.socket(android.system.OsConstants.AF_VSOCK, android.system.OsConstants.SOCK_STREAM, 0);
            try {
                android.system.Os.connect(socket, new android.system.VmSocketAddress(5000, android.system.OsConstants.VMADDR_CID_HOST));
                byte[] createOpenHandshake = createOpenHandshake();
                writeFully(socket, createOpenHandshake, 0, createOpenHandshake.length);
                return socket;
            } catch (android.system.ErrnoException | java.io.InterruptedIOException | java.net.SocketException e) {
                android.system.Os.close(socket);
                return null;
            }
        } catch (android.system.ErrnoException e2) {
            return null;
        }
    }

    private static java.io.FileDescriptor openPipe() throws java.lang.InterruptedException {
        java.io.FileDescriptor openPipeImpl = openPipeImpl();
        while (openPipeImpl == null) {
            java.lang.Thread.sleep(100L);
            openPipeImpl = openPipeImpl();
        }
        return openPipeImpl;
    }

    private byte[] receiveMessage(java.io.FileDescriptor fileDescriptor) throws android.system.ErrnoException, java.io.InterruptedIOException, java.io.EOFException, java.net.ProtocolException {
        byte[] bArr = new byte[4];
        readFully(fileDescriptor, bArr, 0, 4);
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
        wrap.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        int i = wrap.getInt();
        if (i < 0 || i > 134217728) {
            throw new java.net.ProtocolException("Clipboard message length: " + i + " out of bounds.");
        }
        byte[] bArr2 = new byte[i];
        readFully(fileDescriptor, bArr2, 0, i);
        return bArr2;
    }

    private static void sendMessage(java.io.FileDescriptor fileDescriptor, byte[] bArr) throws android.system.ErrnoException, java.io.InterruptedIOException {
        byte[] bArr2 = new byte[4];
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr2);
        wrap.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        wrap.putInt(bArr.length);
        writeFully(fileDescriptor, bArr2, 0, 4);
        writeFully(fileDescriptor, bArr, 0, bArr.length);
    }

    EmulatorClipboardMonitor(final java.util.function.Consumer<android.content.ClipData> consumer) {
        this.mHostMonitorThread = new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.clipboard.EmulatorClipboardMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.clipboard.EmulatorClipboardMonitor.this.lambda$new$0(consumer);
            }
        });
        this.mHostMonitorThread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(java.util.function.Consumer consumer) {
        java.io.FileDescriptor fileDescriptor = null;
        while (!java.lang.Thread.interrupted()) {
            if (fileDescriptor == null) {
                try {
                    fileDescriptor = openPipe();
                    setPipeFD(fileDescriptor);
                } catch (android.system.ErrnoException | java.io.EOFException | java.io.InterruptedIOException | java.lang.InterruptedException | java.lang.OutOfMemoryError | java.net.ProtocolException e) {
                    android.util.Slog.w(TAG, "Failure to read from host clipboard", e);
                    setPipeFD(null);
                    try {
                        android.system.Os.close(fileDescriptor);
                    } catch (android.system.ErrnoException e2) {
                    }
                    fileDescriptor = null;
                }
            }
            java.lang.String str = new java.lang.String(receiveMessage(fileDescriptor));
            android.content.ClipData clipData = new android.content.ClipData("host clipboard", new java.lang.String[]{"text/plain"}, new android.content.ClipData.Item(str));
            android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
            persistableBundle.putBoolean("com.android.systemui.SUPPRESS_CLIPBOARD_OVERLAY", true);
            clipData.getDescription().setExtras(persistableBundle);
            if (LOG_CLIBOARD_ACCESS) {
                android.util.Slog.i(TAG, "Setting the guest clipboard to '" + str + "'");
            }
            consumer.accept(clipData);
        }
    }

    @Override // java.util.function.Consumer
    public void accept(@android.annotation.Nullable android.content.ClipData clipData) {
        java.io.FileDescriptor pipeFD = getPipeFD();
        if (pipeFD != null) {
            setHostClipboard(pipeFD, getClipString(clipData));
        }
    }

    private java.lang.String getClipString(@android.annotation.Nullable android.content.ClipData clipData) {
        java.lang.CharSequence text;
        if (clipData == null || clipData.getItemCount() == 0 || (text = clipData.getItemAt(0).getText()) == null) {
            return "";
        }
        return text.toString();
    }

    private static void setHostClipboard(final java.io.FileDescriptor fileDescriptor, final java.lang.String str) {
        new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.clipboard.EmulatorClipboardMonitor$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.clipboard.EmulatorClipboardMonitor.lambda$setHostClipboard$1(str, fileDescriptor);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setHostClipboard$1(java.lang.String str, java.io.FileDescriptor fileDescriptor) {
        if (LOG_CLIBOARD_ACCESS) {
            android.util.Slog.i(TAG, "Setting the host clipboard to '" + str + "'");
        }
        try {
            sendMessage(fileDescriptor, str.getBytes());
        } catch (android.system.ErrnoException | java.io.InterruptedIOException e) {
            android.util.Slog.e(TAG, "Failed to set host clipboard " + e.getMessage());
        } catch (java.lang.IllegalArgumentException e2) {
        }
    }

    private static void readFully(java.io.FileDescriptor fileDescriptor, byte[] bArr, int i, int i2) throws android.system.ErrnoException, java.io.InterruptedIOException, java.io.EOFException {
        while (i2 > 0) {
            int read = android.system.Os.read(fileDescriptor, bArr, i, i2);
            if (read > 0) {
                i += read;
                i2 -= read;
            } else {
                throw new java.io.EOFException();
            }
        }
    }

    private static void writeFully(java.io.FileDescriptor fileDescriptor, byte[] bArr, int i, int i2) throws android.system.ErrnoException, java.io.InterruptedIOException {
        while (i2 > 0) {
            int write = android.system.Os.write(fileDescriptor, bArr, i, i2);
            if (write > 0) {
                i += write;
                i2 -= write;
            } else {
                throw new android.system.ErrnoException("write", android.system.OsConstants.EIO);
            }
        }
    }
}
