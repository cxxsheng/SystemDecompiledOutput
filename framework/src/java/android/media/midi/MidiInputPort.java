package android.media.midi;

/* loaded from: classes2.dex */
public final class MidiInputPort extends android.media.midi.MidiReceiver implements java.io.Closeable {
    private static final java.lang.String TAG = "MidiInputPort";
    private final byte[] mBuffer;
    private android.media.midi.IMidiDeviceServer mDeviceServer;
    private java.io.FileDescriptor mFileDescriptor;
    private final dalvik.system.CloseGuard mGuard;
    private boolean mIsClosed;
    private java.io.FileOutputStream mOutputStream;
    private final int mPortNumber;
    private final android.os.IBinder mToken;
    private java.util.concurrent.atomic.AtomicInteger mTotalBytes;

    MidiInputPort(android.media.midi.IMidiDeviceServer iMidiDeviceServer, android.os.IBinder iBinder, java.io.FileDescriptor fileDescriptor, int i) {
        super(1015);
        this.mGuard = dalvik.system.CloseGuard.get();
        this.mTotalBytes = new java.util.concurrent.atomic.AtomicInteger();
        this.mBuffer = new byte[1024];
        this.mDeviceServer = iMidiDeviceServer;
        this.mToken = iBinder;
        this.mFileDescriptor = fileDescriptor;
        this.mPortNumber = i;
        this.mOutputStream = new java.io.FileOutputStream(fileDescriptor);
        this.mGuard.open("close");
    }

    MidiInputPort(java.io.FileDescriptor fileDescriptor, int i) {
        this(null, null, fileDescriptor, i);
    }

    public final int getPortNumber() {
        return this.mPortNumber;
    }

    @Override // android.media.midi.MidiReceiver
    public void onSend(byte[] bArr, int i, int i2, long j) throws java.io.IOException {
        if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
            throw new java.lang.IllegalArgumentException("offset or count out of range");
        }
        if (i2 > 1015) {
            throw new java.lang.IllegalArgumentException("count exceeds max message size");
        }
        synchronized (this.mBuffer) {
            if (this.mOutputStream == null) {
                throw new java.io.IOException("MidiInputPort is closed");
            }
            int packData = android.media.midi.MidiPortImpl.packData(bArr, i, i2, j, this.mBuffer);
            this.mOutputStream.write(this.mBuffer, 0, packData);
            this.mTotalBytes.addAndGet(packData);
        }
    }

    @Override // android.media.midi.MidiReceiver
    public void onFlush() throws java.io.IOException {
        synchronized (this.mBuffer) {
            if (this.mOutputStream == null) {
                throw new java.io.IOException("MidiInputPort is closed");
            }
            this.mOutputStream.write(this.mBuffer, 0, android.media.midi.MidiPortImpl.packFlush(this.mBuffer));
        }
    }

    java.io.FileDescriptor claimFileDescriptor() {
        synchronized (this.mGuard) {
            synchronized (this.mBuffer) {
                java.io.FileDescriptor fileDescriptor = this.mFileDescriptor;
                if (fileDescriptor == null) {
                    return null;
                }
                libcore.io.IoUtils.closeQuietly(this.mOutputStream);
                this.mFileDescriptor = null;
                this.mOutputStream = null;
                this.mIsClosed = true;
                return fileDescriptor;
            }
        }
    }

    android.os.IBinder getToken() {
        return this.mToken;
    }

    android.media.midi.IMidiDeviceServer getDeviceServer() {
        return this.mDeviceServer;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        synchronized (this.mGuard) {
            if (this.mIsClosed) {
                return;
            }
            this.mGuard.close();
            synchronized (this.mBuffer) {
                if (this.mFileDescriptor != null) {
                    libcore.io.IoUtils.closeQuietly(this.mFileDescriptor);
                    this.mFileDescriptor = null;
                }
                if (this.mOutputStream != null) {
                    this.mOutputStream.close();
                    this.mOutputStream = null;
                }
            }
            if (this.mDeviceServer != null) {
                try {
                    this.mDeviceServer.closePort(this.mToken);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "RemoteException in MidiInputPort.close()");
                }
            }
            this.mIsClosed = true;
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mGuard != null) {
                this.mGuard.warnIfOpen();
            }
            this.mDeviceServer = null;
            close();
        } finally {
            super.finalize();
        }
    }

    public int pullTotalBytesCount() {
        return this.mTotalBytes.getAndSet(0);
    }
}
