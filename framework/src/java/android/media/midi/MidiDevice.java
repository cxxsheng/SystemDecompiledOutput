package android.media.midi;

/* loaded from: classes2.dex */
public final class MidiDevice implements java.io.Closeable {
    private static final java.lang.String TAG = "MidiDevice";
    private final android.os.IBinder mClientToken;
    private final android.media.midi.MidiDeviceInfo mDeviceInfo;
    private final android.media.midi.IMidiDeviceServer mDeviceServer;
    private final android.os.IBinder mDeviceServerBinder;
    private final android.os.IBinder mDeviceToken;
    private final dalvik.system.CloseGuard mGuard = dalvik.system.CloseGuard.get();
    private boolean mIsDeviceClosed;
    private final android.media.midi.IMidiManager mMidiManager;
    private long mNativeHandle;

    public class MidiConnection implements java.io.Closeable {
        private final dalvik.system.CloseGuard mGuard = dalvik.system.CloseGuard.get();
        private final android.media.midi.IMidiDeviceServer mInputPortDeviceServer;
        private final android.os.IBinder mInputPortToken;
        private boolean mIsClosed;
        private final android.os.IBinder mOutputPortToken;

        MidiConnection(android.os.IBinder iBinder, android.media.midi.MidiInputPort midiInputPort) {
            this.mInputPortDeviceServer = midiInputPort.getDeviceServer();
            this.mInputPortToken = midiInputPort.getToken();
            this.mOutputPortToken = iBinder;
            this.mGuard.open("close");
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            synchronized (this.mGuard) {
                if (this.mIsClosed) {
                    return;
                }
                this.mGuard.close();
                try {
                    this.mInputPortDeviceServer.closePort(this.mInputPortToken);
                    android.media.midi.MidiDevice.this.mDeviceServer.closePort(this.mOutputPortToken);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.media.midi.MidiDevice.TAG, "RemoteException in MidiConnection.close");
                }
                this.mIsClosed = true;
            }
        }

        protected void finalize() throws java.lang.Throwable {
            try {
                if (this.mGuard != null) {
                    this.mGuard.warnIfOpen();
                }
                close();
            } finally {
                super.finalize();
            }
        }
    }

    MidiDevice(android.media.midi.MidiDeviceInfo midiDeviceInfo, android.media.midi.IMidiDeviceServer iMidiDeviceServer, android.media.midi.IMidiManager iMidiManager, android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        this.mDeviceInfo = midiDeviceInfo;
        this.mDeviceServer = iMidiDeviceServer;
        this.mDeviceServerBinder = this.mDeviceServer.asBinder();
        this.mMidiManager = iMidiManager;
        this.mClientToken = iBinder;
        this.mDeviceToken = iBinder2;
        this.mGuard.open("close");
    }

    public android.media.midi.MidiDeviceInfo getInfo() {
        return this.mDeviceInfo;
    }

    public android.media.midi.MidiInputPort openInputPort(int i) {
        if (this.mIsDeviceClosed) {
            return null;
        }
        try {
            android.os.Binder binder = new android.os.Binder();
            java.io.FileDescriptor openInputPort = this.mDeviceServer.openInputPort(binder, i);
            if (openInputPort == null) {
                return null;
            }
            return new android.media.midi.MidiInputPort(this.mDeviceServer, binder, openInputPort, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "RemoteException in openInputPort");
            return null;
        }
    }

    public android.media.midi.MidiOutputPort openOutputPort(int i) {
        if (this.mIsDeviceClosed) {
            return null;
        }
        try {
            android.os.Binder binder = new android.os.Binder();
            java.io.FileDescriptor openOutputPort = this.mDeviceServer.openOutputPort(binder, i);
            if (openOutputPort == null) {
                return null;
            }
            return new android.media.midi.MidiOutputPort(this.mDeviceServer, binder, openOutputPort, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "RemoteException in openOutputPort");
            return null;
        }
    }

    public android.media.midi.MidiDevice.MidiConnection connectPorts(android.media.midi.MidiInputPort midiInputPort, int i) {
        java.io.FileDescriptor claimFileDescriptor;
        if (i < 0 || i >= this.mDeviceInfo.getOutputPortCount()) {
            throw new java.lang.IllegalArgumentException("outputPortNumber out of range");
        }
        if (this.mIsDeviceClosed || (claimFileDescriptor = midiInputPort.claimFileDescriptor()) == null) {
            return null;
        }
        try {
            android.os.Binder binder = new android.os.Binder();
            if (this.mDeviceServer.connectPorts(binder, claimFileDescriptor, i) != android.os.Process.myPid()) {
                libcore.io.IoUtils.closeQuietly(claimFileDescriptor);
            }
            return new android.media.midi.MidiDevice.MidiConnection(binder, midiInputPort);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "RemoteException in connectPorts");
            return null;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        synchronized (this.mGuard) {
            if (this.mNativeHandle != 0) {
                android.util.Log.w(TAG, "MidiDevice#close() called while there is an outstanding native client 0x" + java.lang.Long.toHexString(this.mNativeHandle));
            }
            if (!this.mIsDeviceClosed && this.mNativeHandle == 0) {
                this.mGuard.close();
                this.mIsDeviceClosed = true;
                try {
                    this.mMidiManager.closeDevice(this.mClientToken, this.mDeviceToken);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "RemoteException in closeDevice");
                }
            }
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mGuard != null) {
                this.mGuard.warnIfOpen();
            }
            close();
        } finally {
            super.finalize();
        }
    }

    public java.lang.String toString() {
        return "MidiDevice: " + this.mDeviceInfo.toString();
    }
}
