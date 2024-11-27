package android.media.midi;

/* loaded from: classes2.dex */
public final class MidiOutputPort extends android.media.midi.MidiSender implements java.io.Closeable {
    private static final java.lang.String TAG = "MidiOutputPort";
    private android.media.midi.IMidiDeviceServer mDeviceServer;
    private final com.android.internal.midi.MidiDispatcher mDispatcher;
    private final dalvik.system.CloseGuard mGuard;
    private final java.io.FileInputStream mInputStream;
    private boolean mIsClosed;
    private final int mPortNumber;
    private final java.lang.Thread mThread;
    private final android.os.IBinder mToken;
    private java.util.concurrent.atomic.AtomicInteger mTotalBytes;

    MidiOutputPort(android.media.midi.IMidiDeviceServer iMidiDeviceServer, android.os.IBinder iBinder, java.io.FileDescriptor fileDescriptor, int i) {
        this.mDispatcher = new com.android.internal.midi.MidiDispatcher();
        this.mGuard = dalvik.system.CloseGuard.get();
        this.mTotalBytes = new java.util.concurrent.atomic.AtomicInteger();
        this.mThread = new java.lang.Thread() { // from class: android.media.midi.MidiOutputPort.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                int read;
                byte[] bArr = new byte[1024];
                while (true) {
                    try {
                        read = android.media.midi.MidiOutputPort.this.mInputStream.read(bArr);
                    } catch (java.io.IOException e) {
                    } catch (java.lang.Throwable th) {
                        libcore.io.IoUtils.closeQuietly(android.media.midi.MidiOutputPort.this.mInputStream);
                        throw th;
                    }
                    if (read >= 0) {
                        int packetType = android.media.midi.MidiPortImpl.getPacketType(bArr, read);
                        switch (packetType) {
                            case 1:
                                android.media.midi.MidiOutputPort.this.mDispatcher.send(bArr, android.media.midi.MidiPortImpl.getDataOffset(bArr, read), android.media.midi.MidiPortImpl.getDataSize(bArr, read), android.media.midi.MidiPortImpl.getPacketTimestamp(bArr, read));
                                break;
                            case 2:
                                android.media.midi.MidiOutputPort.this.mDispatcher.flush();
                                break;
                            default:
                                android.util.Log.e(android.media.midi.MidiOutputPort.TAG, "Unknown packet type " + packetType);
                                break;
                        }
                        android.media.midi.MidiOutputPort.this.mTotalBytes.addAndGet(read);
                    } else {
                        libcore.io.IoUtils.closeQuietly(android.media.midi.MidiOutputPort.this.mInputStream);
                        return;
                    }
                }
            }
        };
        this.mDeviceServer = iMidiDeviceServer;
        this.mToken = iBinder;
        this.mPortNumber = i;
        this.mInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(new android.os.ParcelFileDescriptor(fileDescriptor));
        this.mThread.start();
        this.mGuard.open("close");
    }

    MidiOutputPort(java.io.FileDescriptor fileDescriptor, int i) {
        this(null, null, fileDescriptor, i);
    }

    public final int getPortNumber() {
        return this.mPortNumber;
    }

    @Override // android.media.midi.MidiSender
    public void onConnect(android.media.midi.MidiReceiver midiReceiver) {
        this.mDispatcher.getSender().connect(midiReceiver);
    }

    @Override // android.media.midi.MidiSender
    public void onDisconnect(android.media.midi.MidiReceiver midiReceiver) {
        this.mDispatcher.getSender().disconnect(midiReceiver);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        synchronized (this.mGuard) {
            if (this.mIsClosed) {
                return;
            }
            this.mGuard.close();
            this.mInputStream.close();
            if (this.mDeviceServer != null) {
                try {
                    this.mDeviceServer.closePort(this.mToken);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "RemoteException in MidiOutputPort.close()");
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
