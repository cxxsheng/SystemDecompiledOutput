package android.media.midi;

/* loaded from: classes2.dex */
public final class MidiDeviceServer implements java.io.Closeable {
    private static final java.lang.String TAG = "MidiDeviceServer";
    private static final int UNUSED_UID = -1;
    private final android.media.midi.MidiDeviceServer.Callback mCallback;
    private android.media.midi.MidiDeviceInfo mDeviceInfo;
    private final dalvik.system.CloseGuard mGuard;
    private final java.util.HashMap<android.media.midi.MidiInputPort, android.media.midi.MidiDeviceServer.PortClient> mInputPortClients;
    private final int mInputPortCount;
    private final com.android.internal.midi.MidiDispatcher.MidiReceiverFailureHandler mInputPortFailureHandler;
    private final boolean[] mInputPortOpen;
    private final android.media.midi.MidiOutputPort[] mInputPortOutputPorts;
    private final android.media.midi.MidiReceiver[] mInputPortReceivers;
    private final java.util.concurrent.CopyOnWriteArrayList<android.media.midi.MidiInputPort> mInputPorts;
    private boolean mIsClosed;
    private final android.media.midi.IMidiManager mMidiManager;
    private final int mOutputPortCount;
    private com.android.internal.midi.MidiDispatcher[] mOutputPortDispatchers;
    private final int[] mOutputPortOpenCount;
    private final java.util.HashMap<android.os.IBinder, android.media.midi.MidiDeviceServer.PortClient> mPortClients;
    private final android.media.midi.IMidiDeviceServer mServer;
    private java.util.concurrent.atomic.AtomicInteger mTotalInputBytes;
    private java.util.concurrent.atomic.AtomicInteger mTotalOutputBytes;
    private final int[] mUmpInputPortUids;
    private final int[] mUmpOutputPortUids;
    private final java.lang.Object mUmpUidLock;

    public interface Callback {
        void onClose();

        void onDeviceStatusChanged(android.media.midi.MidiDeviceServer midiDeviceServer, android.media.midi.MidiDeviceStatus midiDeviceStatus);
    }

    private abstract class PortClient implements android.os.IBinder.DeathRecipient {
        final android.os.IBinder mToken;

        abstract void close();

        PortClient(android.os.IBinder iBinder) {
            this.mToken = iBinder;
            try {
                iBinder.linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                close();
            }
        }

        android.media.midi.MidiInputPort getInputPort() {
            return null;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            close();
        }
    }

    private class InputPortClient extends android.media.midi.MidiDeviceServer.PortClient {
        private final android.media.midi.MidiOutputPort mOutputPort;

        InputPortClient(android.os.IBinder iBinder, android.media.midi.MidiOutputPort midiOutputPort) {
            super(iBinder);
            this.mOutputPort = midiOutputPort;
        }

        @Override // android.media.midi.MidiDeviceServer.PortClient
        void close() {
            this.mToken.unlinkToDeath(this, 0);
            synchronized (android.media.midi.MidiDeviceServer.this.mInputPortOutputPorts) {
                int portNumber = this.mOutputPort.getPortNumber();
                android.media.midi.MidiDeviceServer.this.mInputPortOutputPorts[portNumber] = null;
                android.media.midi.MidiDeviceServer.this.mInputPortOpen[portNumber] = false;
                synchronized (android.media.midi.MidiDeviceServer.this.mUmpUidLock) {
                    android.media.midi.MidiDeviceServer.this.mUmpInputPortUids[portNumber] = -1;
                }
                android.media.midi.MidiDeviceServer.this.mTotalOutputBytes.addAndGet(this.mOutputPort.pullTotalBytesCount());
                android.media.midi.MidiDeviceServer.this.updateTotalBytes();
                android.media.midi.MidiDeviceServer.this.updateDeviceStatus();
            }
            libcore.io.IoUtils.closeQuietly(this.mOutputPort);
        }
    }

    private class OutputPortClient extends android.media.midi.MidiDeviceServer.PortClient {
        private final android.media.midi.MidiInputPort mInputPort;

        OutputPortClient(android.os.IBinder iBinder, android.media.midi.MidiInputPort midiInputPort) {
            super(iBinder);
            this.mInputPort = midiInputPort;
        }

        @Override // android.media.midi.MidiDeviceServer.PortClient
        void close() {
            this.mToken.unlinkToDeath(this, 0);
            int portNumber = this.mInputPort.getPortNumber();
            com.android.internal.midi.MidiDispatcher midiDispatcher = android.media.midi.MidiDeviceServer.this.mOutputPortDispatchers[portNumber];
            synchronized (midiDispatcher) {
                midiDispatcher.getSender().disconnect(this.mInputPort);
                android.media.midi.MidiDeviceServer.this.mOutputPortOpenCount[portNumber] = midiDispatcher.getReceiverCount();
                synchronized (android.media.midi.MidiDeviceServer.this.mUmpUidLock) {
                    android.media.midi.MidiDeviceServer.this.mUmpOutputPortUids[portNumber] = -1;
                }
                android.media.midi.MidiDeviceServer.this.mTotalInputBytes.addAndGet(this.mInputPort.pullTotalBytesCount());
                android.media.midi.MidiDeviceServer.this.updateTotalBytes();
                android.media.midi.MidiDeviceServer.this.updateDeviceStatus();
            }
            android.media.midi.MidiDeviceServer.this.mInputPorts.remove(this.mInputPort);
            libcore.io.IoUtils.closeQuietly(this.mInputPort);
        }

        @Override // android.media.midi.MidiDeviceServer.PortClient
        android.media.midi.MidiInputPort getInputPort() {
            return this.mInputPort;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.io.FileDescriptor[] createSeqPacketSocketPair() throws java.io.IOException {
        try {
            java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
            java.io.FileDescriptor fileDescriptor2 = new java.io.FileDescriptor();
            android.system.Os.socketpair(android.system.OsConstants.AF_UNIX, android.system.OsConstants.SOCK_SEQPACKET, 0, fileDescriptor, fileDescriptor2);
            return new java.io.FileDescriptor[]{fileDescriptor, fileDescriptor2};
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    MidiDeviceServer(android.media.midi.IMidiManager iMidiManager, android.media.midi.MidiReceiver[] midiReceiverArr, int i, android.media.midi.MidiDeviceServer.Callback callback) {
        this.mInputPorts = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mGuard = dalvik.system.CloseGuard.get();
        this.mPortClients = new java.util.HashMap<>();
        this.mInputPortClients = new java.util.HashMap<>();
        this.mTotalInputBytes = new java.util.concurrent.atomic.AtomicInteger();
        this.mTotalOutputBytes = new java.util.concurrent.atomic.AtomicInteger();
        this.mUmpUidLock = new java.lang.Object();
        this.mServer = new android.media.midi.IMidiDeviceServer.Stub() { // from class: android.media.midi.MidiDeviceServer.1
            @Override // android.media.midi.IMidiDeviceServer
            public java.io.FileDescriptor openInputPort(android.os.IBinder iBinder, int i2) {
                if (android.media.midi.MidiDeviceServer.this.mDeviceInfo.isPrivate() && android.os.Binder.getCallingUid() != android.os.Process.myUid()) {
                    throw new java.lang.SecurityException("Can't access private device from different UID");
                }
                if (i2 < 0 || i2 >= android.media.midi.MidiDeviceServer.this.mInputPortCount) {
                    android.util.Log.e(android.media.midi.MidiDeviceServer.TAG, "portNumber out of range in openInputPort: " + i2);
                    return null;
                }
                synchronized (android.media.midi.MidiDeviceServer.this.mInputPortOutputPorts) {
                    if (android.media.midi.MidiDeviceServer.this.mInputPortOutputPorts[i2] != null) {
                        android.util.Log.d(android.media.midi.MidiDeviceServer.TAG, "port " + i2 + " already open");
                        return null;
                    }
                    if (android.media.midi.MidiDeviceServer.this.isUmpDevice()) {
                        if (i2 >= android.media.midi.MidiDeviceServer.this.mOutputPortCount) {
                            android.util.Log.e(android.media.midi.MidiDeviceServer.TAG, "out portNumber out of range in openInputPort: " + i2);
                            return null;
                        }
                        synchronized (android.media.midi.MidiDeviceServer.this.mUmpUidLock) {
                            if (android.media.midi.MidiDeviceServer.this.mUmpInputPortUids[i2] != -1) {
                                android.util.Log.e(android.media.midi.MidiDeviceServer.TAG, "input port already open in openInputPort: " + i2);
                                return null;
                            }
                            if (android.media.midi.MidiDeviceServer.this.mUmpOutputPortUids[i2] != -1 && android.os.Binder.getCallingUid() != android.media.midi.MidiDeviceServer.this.mUmpOutputPortUids[i2]) {
                                android.util.Log.e(android.media.midi.MidiDeviceServer.TAG, "different uid for output in openInputPort: " + i2);
                                return null;
                            }
                            android.media.midi.MidiDeviceServer.this.mUmpInputPortUids[i2] = android.os.Binder.getCallingUid();
                        }
                    }
                    try {
                        java.io.FileDescriptor[] createSeqPacketSocketPair = android.media.midi.MidiDeviceServer.createSeqPacketSocketPair();
                        android.media.midi.MidiOutputPort midiOutputPort = new android.media.midi.MidiOutputPort(createSeqPacketSocketPair[0], i2);
                        android.media.midi.MidiDeviceServer.this.mInputPortOutputPorts[i2] = midiOutputPort;
                        midiOutputPort.connect(android.media.midi.MidiDeviceServer.this.mInputPortReceivers[i2]);
                        android.media.midi.MidiDeviceServer.InputPortClient inputPortClient = android.media.midi.MidiDeviceServer.this.new InputPortClient(iBinder, midiOutputPort);
                        synchronized (android.media.midi.MidiDeviceServer.this.mPortClients) {
                            android.media.midi.MidiDeviceServer.this.mPortClients.put(iBinder, inputPortClient);
                        }
                        android.media.midi.MidiDeviceServer.this.mInputPortOpen[i2] = true;
                        android.media.midi.MidiDeviceServer.this.updateDeviceStatus();
                        return createSeqPacketSocketPair[1];
                    } catch (java.io.IOException e) {
                        android.util.Log.e(android.media.midi.MidiDeviceServer.TAG, "unable to create FileDescriptors in openInputPort");
                        return null;
                    }
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public java.io.FileDescriptor openOutputPort(android.os.IBinder iBinder, int i2) {
                if (android.media.midi.MidiDeviceServer.this.mDeviceInfo.isPrivate() && android.os.Binder.getCallingUid() != android.os.Process.myUid()) {
                    throw new java.lang.SecurityException("Can't access private device from different UID");
                }
                if (i2 < 0 || i2 >= android.media.midi.MidiDeviceServer.this.mOutputPortCount) {
                    android.util.Log.e(android.media.midi.MidiDeviceServer.TAG, "portNumber out of range in openOutputPort: " + i2);
                    return null;
                }
                if (android.media.midi.MidiDeviceServer.this.isUmpDevice()) {
                    if (i2 >= android.media.midi.MidiDeviceServer.this.mInputPortCount) {
                        android.util.Log.e(android.media.midi.MidiDeviceServer.TAG, "in portNumber out of range in openOutputPort: " + i2);
                        return null;
                    }
                    synchronized (android.media.midi.MidiDeviceServer.this.mUmpUidLock) {
                        if (android.media.midi.MidiDeviceServer.this.mUmpOutputPortUids[i2] != -1) {
                            android.util.Log.e(android.media.midi.MidiDeviceServer.TAG, "output port already open in openOutputPort: " + i2);
                            return null;
                        }
                        if (android.media.midi.MidiDeviceServer.this.mUmpInputPortUids[i2] != -1 && android.os.Binder.getCallingUid() != android.media.midi.MidiDeviceServer.this.mUmpInputPortUids[i2]) {
                            android.util.Log.e(android.media.midi.MidiDeviceServer.TAG, "different uid for input in openOutputPort: " + i2);
                            return null;
                        }
                        android.media.midi.MidiDeviceServer.this.mUmpOutputPortUids[i2] = android.os.Binder.getCallingUid();
                    }
                }
                try {
                    java.io.FileDescriptor[] createSeqPacketSocketPair = android.media.midi.MidiDeviceServer.createSeqPacketSocketPair();
                    android.media.midi.MidiInputPort midiInputPort = new android.media.midi.MidiInputPort(createSeqPacketSocketPair[0], i2);
                    if (android.media.midi.MidiDeviceServer.this.mDeviceInfo.getType() != 2) {
                        libcore.io.IoUtils.setBlocking(createSeqPacketSocketPair[0], false);
                    }
                    com.android.internal.midi.MidiDispatcher midiDispatcher = android.media.midi.MidiDeviceServer.this.mOutputPortDispatchers[i2];
                    synchronized (midiDispatcher) {
                        midiDispatcher.getSender().connect(midiInputPort);
                        android.media.midi.MidiDeviceServer.this.mOutputPortOpenCount[i2] = midiDispatcher.getReceiverCount();
                        android.media.midi.MidiDeviceServer.this.updateDeviceStatus();
                    }
                    android.media.midi.MidiDeviceServer.this.mInputPorts.add(midiInputPort);
                    android.media.midi.MidiDeviceServer.OutputPortClient outputPortClient = android.media.midi.MidiDeviceServer.this.new OutputPortClient(iBinder, midiInputPort);
                    synchronized (android.media.midi.MidiDeviceServer.this.mPortClients) {
                        android.media.midi.MidiDeviceServer.this.mPortClients.put(iBinder, outputPortClient);
                    }
                    synchronized (android.media.midi.MidiDeviceServer.this.mInputPortClients) {
                        android.media.midi.MidiDeviceServer.this.mInputPortClients.put(midiInputPort, outputPortClient);
                    }
                    return createSeqPacketSocketPair[1];
                } catch (java.io.IOException e) {
                    android.util.Log.e(android.media.midi.MidiDeviceServer.TAG, "unable to create FileDescriptors in openOutputPort");
                    return null;
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public void closePort(android.os.IBinder iBinder) {
                android.media.midi.MidiInputPort midiInputPort;
                synchronized (android.media.midi.MidiDeviceServer.this.mPortClients) {
                    android.media.midi.MidiDeviceServer.PortClient portClient = (android.media.midi.MidiDeviceServer.PortClient) android.media.midi.MidiDeviceServer.this.mPortClients.remove(iBinder);
                    if (portClient == null) {
                        midiInputPort = null;
                    } else {
                        midiInputPort = portClient.getInputPort();
                        portClient.close();
                    }
                }
                if (midiInputPort != null) {
                    synchronized (android.media.midi.MidiDeviceServer.this.mInputPortClients) {
                        android.media.midi.MidiDeviceServer.this.mInputPortClients.remove(midiInputPort);
                    }
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public void closeDevice() {
                if (android.media.midi.MidiDeviceServer.this.mCallback != null) {
                    android.media.midi.MidiDeviceServer.this.mCallback.onClose();
                }
                libcore.io.IoUtils.closeQuietly(android.media.midi.MidiDeviceServer.this);
            }

            @Override // android.media.midi.IMidiDeviceServer
            public int connectPorts(android.os.IBinder iBinder, java.io.FileDescriptor fileDescriptor, int i2) {
                android.media.midi.MidiInputPort midiInputPort = new android.media.midi.MidiInputPort(fileDescriptor, i2);
                com.android.internal.midi.MidiDispatcher midiDispatcher = android.media.midi.MidiDeviceServer.this.mOutputPortDispatchers[i2];
                synchronized (midiDispatcher) {
                    midiDispatcher.getSender().connect(midiInputPort);
                    android.media.midi.MidiDeviceServer.this.mOutputPortOpenCount[i2] = midiDispatcher.getReceiverCount();
                    android.media.midi.MidiDeviceServer.this.updateDeviceStatus();
                }
                android.media.midi.MidiDeviceServer.this.mInputPorts.add(midiInputPort);
                android.media.midi.MidiDeviceServer.OutputPortClient outputPortClient = android.media.midi.MidiDeviceServer.this.new OutputPortClient(iBinder, midiInputPort);
                synchronized (android.media.midi.MidiDeviceServer.this.mPortClients) {
                    android.media.midi.MidiDeviceServer.this.mPortClients.put(iBinder, outputPortClient);
                }
                synchronized (android.media.midi.MidiDeviceServer.this.mInputPortClients) {
                    android.media.midi.MidiDeviceServer.this.mInputPortClients.put(midiInputPort, outputPortClient);
                }
                return android.os.Process.myPid();
            }

            @Override // android.media.midi.IMidiDeviceServer
            public android.media.midi.MidiDeviceInfo getDeviceInfo() {
                return android.media.midi.MidiDeviceServer.this.mDeviceInfo;
            }

            @Override // android.media.midi.IMidiDeviceServer
            public void setDeviceInfo(android.media.midi.MidiDeviceInfo midiDeviceInfo) {
                if (android.os.Binder.getCallingUid() != 1000) {
                    throw new java.lang.SecurityException("setDeviceInfo should only be called by MidiService");
                }
                if (android.media.midi.MidiDeviceServer.this.mDeviceInfo != null) {
                    throw new java.lang.IllegalStateException("setDeviceInfo should only be called once");
                }
                android.media.midi.MidiDeviceServer.this.mDeviceInfo = midiDeviceInfo;
            }
        };
        this.mInputPortFailureHandler = new com.android.internal.midi.MidiDispatcher.MidiReceiverFailureHandler() { // from class: android.media.midi.MidiDeviceServer.2
            @Override // com.android.internal.midi.MidiDispatcher.MidiReceiverFailureHandler
            public void onReceiverFailure(android.media.midi.MidiReceiver midiReceiver, java.io.IOException iOException) {
                android.media.midi.MidiDeviceServer.PortClient portClient;
                android.util.Log.e(android.media.midi.MidiDeviceServer.TAG, "MidiInputPort failed to send data", iOException);
                synchronized (android.media.midi.MidiDeviceServer.this.mInputPortClients) {
                    portClient = (android.media.midi.MidiDeviceServer.PortClient) android.media.midi.MidiDeviceServer.this.mInputPortClients.remove(midiReceiver);
                }
                if (portClient != null) {
                    portClient.close();
                }
            }
        };
        this.mMidiManager = iMidiManager;
        this.mInputPortReceivers = midiReceiverArr;
        this.mInputPortCount = midiReceiverArr.length;
        this.mOutputPortCount = i;
        this.mCallback = callback;
        this.mInputPortOutputPorts = new android.media.midi.MidiOutputPort[this.mInputPortCount];
        this.mOutputPortDispatchers = new com.android.internal.midi.MidiDispatcher[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mOutputPortDispatchers[i2] = new com.android.internal.midi.MidiDispatcher(this.mInputPortFailureHandler);
        }
        this.mInputPortOpen = new boolean[this.mInputPortCount];
        this.mOutputPortOpenCount = new int[i];
        synchronized (this.mUmpUidLock) {
            this.mUmpInputPortUids = new int[this.mInputPortCount];
            this.mUmpOutputPortUids = new int[this.mOutputPortCount];
            java.util.Arrays.fill(this.mUmpInputPortUids, -1);
            java.util.Arrays.fill(this.mUmpOutputPortUids, -1);
        }
        this.mGuard.open("close");
    }

    MidiDeviceServer(android.media.midi.IMidiManager iMidiManager, android.media.midi.MidiReceiver[] midiReceiverArr, android.media.midi.MidiDeviceInfo midiDeviceInfo, android.media.midi.MidiDeviceServer.Callback callback) {
        this(iMidiManager, midiReceiverArr, midiDeviceInfo.getOutputPortCount(), callback);
        this.mDeviceInfo = midiDeviceInfo;
    }

    android.media.midi.IMidiDeviceServer getBinderInterface() {
        return this.mServer;
    }

    public android.os.IBinder asBinder() {
        return this.mServer.asBinder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceStatus() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                android.media.midi.MidiDeviceStatus midiDeviceStatus = new android.media.midi.MidiDeviceStatus(this.mDeviceInfo, this.mInputPortOpen, this.mOutputPortOpenCount);
                if (this.mCallback != null) {
                    this.mCallback.onDeviceStatusChanged(this, midiDeviceStatus);
                }
                this.mMidiManager.setDeviceStatus(this.mServer, midiDeviceStatus);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException in updateDeviceStatus");
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        synchronized (this.mGuard) {
            if (this.mIsClosed) {
                return;
            }
            this.mGuard.close();
            for (int i = 0; i < this.mInputPortCount; i++) {
                android.media.midi.MidiOutputPort midiOutputPort = this.mInputPortOutputPorts[i];
                if (midiOutputPort != null) {
                    this.mTotalOutputBytes.addAndGet(midiOutputPort.pullTotalBytesCount());
                    libcore.io.IoUtils.closeQuietly(midiOutputPort);
                    this.mInputPortOutputPorts[i] = null;
                }
            }
            java.util.Iterator<android.media.midi.MidiInputPort> it = this.mInputPorts.iterator();
            while (it.hasNext()) {
                android.media.midi.MidiInputPort next = it.next();
                this.mTotalInputBytes.addAndGet(next.pullTotalBytesCount());
                libcore.io.IoUtils.closeQuietly(next);
            }
            this.mInputPorts.clear();
            updateTotalBytes();
            try {
                this.mMidiManager.unregisterDeviceServer(this.mServer);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException in unregisterDeviceServer");
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

    public android.media.midi.MidiReceiver[] getOutputPortReceivers() {
        android.media.midi.MidiReceiver[] midiReceiverArr = new android.media.midi.MidiReceiver[this.mOutputPortCount];
        java.lang.System.arraycopy(this.mOutputPortDispatchers, 0, midiReceiverArr, 0, this.mOutputPortCount);
        return midiReceiverArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTotalBytes() {
        try {
            this.mMidiManager.updateTotalBytes(this.mServer, this.mTotalInputBytes.get(), this.mTotalOutputBytes.get());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "RemoteException in updateTotalBytes");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUmpDevice() {
        return this.mDeviceInfo.getDefaultProtocol() != -1;
    }
}
