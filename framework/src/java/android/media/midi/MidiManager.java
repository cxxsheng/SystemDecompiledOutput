package android.media.midi;

/* loaded from: classes2.dex */
public final class MidiManager {
    public static final java.lang.String BLUETOOTH_MIDI_SERVICE_CLASS = "com.android.bluetoothmidiservice.BluetoothMidiService";
    public static final java.lang.String BLUETOOTH_MIDI_SERVICE_INTENT = "android.media.midi.BluetoothMidiService";
    public static final java.lang.String BLUETOOTH_MIDI_SERVICE_PACKAGE = "com.android.bluetoothmidiservice";
    private static final java.lang.String TAG = "MidiManager";
    public static final int TRANSPORT_MIDI_BYTE_STREAM = 1;
    public static final int TRANSPORT_UNIVERSAL_MIDI_PACKETS = 2;
    private final android.media.midi.IMidiManager mService;
    private final android.os.IBinder mToken = new android.os.Binder();
    private java.util.concurrent.ConcurrentHashMap<android.media.midi.MidiManager.DeviceCallback, android.media.midi.MidiManager.DeviceListener> mDeviceListeners = new java.util.concurrent.ConcurrentHashMap<>();

    public interface OnDeviceOpenedListener {
        void onDeviceOpened(android.media.midi.MidiDevice midiDevice);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Transport {
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DeviceListener extends android.media.midi.IMidiDeviceListener.Stub {
        private final android.media.midi.MidiManager.DeviceCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;
        private final int mTransport;

        DeviceListener(android.media.midi.MidiManager.DeviceCallback deviceCallback, java.util.concurrent.Executor executor, int i) {
            this.mCallback = deviceCallback;
            this.mExecutor = executor;
            this.mTransport = i;
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceAdded(final android.media.midi.MidiDeviceInfo midiDeviceInfo) {
            if (shouldInvokeCallback(midiDeviceInfo)) {
                if (this.mExecutor != null) {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.midi.MidiManager$DeviceListener$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.midi.MidiManager.DeviceListener.this.lambda$onDeviceAdded$0(midiDeviceInfo);
                        }
                    });
                } else {
                    this.mCallback.onDeviceAdded(midiDeviceInfo);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDeviceAdded$0(android.media.midi.MidiDeviceInfo midiDeviceInfo) {
            this.mCallback.onDeviceAdded(midiDeviceInfo);
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceRemoved(final android.media.midi.MidiDeviceInfo midiDeviceInfo) {
            if (shouldInvokeCallback(midiDeviceInfo)) {
                if (this.mExecutor != null) {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.midi.MidiManager$DeviceListener$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.midi.MidiManager.DeviceListener.this.lambda$onDeviceRemoved$1(midiDeviceInfo);
                        }
                    });
                } else {
                    this.mCallback.onDeviceRemoved(midiDeviceInfo);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDeviceRemoved$1(android.media.midi.MidiDeviceInfo midiDeviceInfo) {
            this.mCallback.onDeviceRemoved(midiDeviceInfo);
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceStatusChanged(final android.media.midi.MidiDeviceStatus midiDeviceStatus) {
            if (this.mExecutor != null) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.midi.MidiManager$DeviceListener$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.midi.MidiManager.DeviceListener.this.lambda$onDeviceStatusChanged$2(midiDeviceStatus);
                    }
                });
            } else {
                this.mCallback.onDeviceStatusChanged(midiDeviceStatus);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDeviceStatusChanged$2(android.media.midi.MidiDeviceStatus midiDeviceStatus) {
            this.mCallback.onDeviceStatusChanged(midiDeviceStatus);
        }

        private boolean shouldInvokeCallback(android.media.midi.MidiDeviceInfo midiDeviceInfo) {
            if (this.mTransport == 2) {
                return midiDeviceInfo.getDefaultProtocol() != -1;
            }
            if (this.mTransport == 1) {
                return midiDeviceInfo.getDefaultProtocol() == -1;
            }
            android.util.Log.e(android.media.midi.MidiManager.TAG, "Invalid transport type: " + this.mTransport);
            return false;
        }
    }

    public static class DeviceCallback {
        public void onDeviceAdded(android.media.midi.MidiDeviceInfo midiDeviceInfo) {
        }

        public void onDeviceRemoved(android.media.midi.MidiDeviceInfo midiDeviceInfo) {
        }

        public void onDeviceStatusChanged(android.media.midi.MidiDeviceStatus midiDeviceStatus) {
        }
    }

    public MidiManager(android.media.midi.IMidiManager iMidiManager) {
        this.mService = iMidiManager;
    }

    @java.lang.Deprecated
    public void registerDeviceCallback(android.media.midi.MidiManager.DeviceCallback deviceCallback, android.os.Handler handler) {
        android.media.midi.MidiManager$$ExternalSyntheticLambda0 midiManager$$ExternalSyntheticLambda0;
        if (handler == null) {
            midiManager$$ExternalSyntheticLambda0 = null;
        } else {
            java.util.Objects.requireNonNull(handler);
            midiManager$$ExternalSyntheticLambda0 = new android.media.midi.MidiManager$$ExternalSyntheticLambda0(handler);
        }
        android.media.midi.MidiManager.DeviceListener deviceListener = new android.media.midi.MidiManager.DeviceListener(deviceCallback, midiManager$$ExternalSyntheticLambda0, 1);
        try {
            this.mService.registerListener(this.mToken, deviceListener);
            this.mDeviceListeners.put(deviceCallback, deviceListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerDeviceCallback(int i, java.util.concurrent.Executor executor, android.media.midi.MidiManager.DeviceCallback deviceCallback) {
        java.util.Objects.requireNonNull(executor);
        android.media.midi.MidiManager.DeviceListener deviceListener = new android.media.midi.MidiManager.DeviceListener(deviceCallback, executor, i);
        try {
            this.mService.registerListener(this.mToken, deviceListener);
            this.mDeviceListeners.put(deviceCallback, deviceListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterDeviceCallback(android.media.midi.MidiManager.DeviceCallback deviceCallback) {
        android.media.midi.MidiManager.DeviceListener remove = this.mDeviceListeners.remove(deviceCallback);
        if (remove != null) {
            try {
                this.mService.unregisterListener(this.mToken, remove);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public android.media.midi.MidiDeviceInfo[] getDevices() {
        try {
            return this.mService.getDevices();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Set<android.media.midi.MidiDeviceInfo> getDevicesForTransport(int i) {
        try {
            android.media.midi.MidiDeviceInfo[] devicesForTransport = this.mService.getDevicesForTransport(i);
            if (devicesForTransport == null) {
                return java.util.Collections.emptySet();
            }
            return new android.util.ArraySet(devicesForTransport);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendOpenDeviceResponse(final android.media.midi.MidiDevice midiDevice, final android.media.midi.MidiManager.OnDeviceOpenedListener onDeviceOpenedListener, android.os.Handler handler) {
        if (handler != null) {
            handler.post(new java.lang.Runnable() { // from class: android.media.midi.MidiManager.1
                @Override // java.lang.Runnable
                public void run() {
                    onDeviceOpenedListener.onDeviceOpened(midiDevice);
                }
            });
        } else {
            onDeviceOpenedListener.onDeviceOpened(midiDevice);
        }
    }

    public void openDevice(final android.media.midi.MidiDeviceInfo midiDeviceInfo, final android.media.midi.MidiManager.OnDeviceOpenedListener onDeviceOpenedListener, final android.os.Handler handler) {
        try {
            this.mService.openDevice(this.mToken, midiDeviceInfo, new android.media.midi.IMidiDeviceOpenCallback.Stub() { // from class: android.media.midi.MidiManager.2
                @Override // android.media.midi.IMidiDeviceOpenCallback
                public void onDeviceOpened(android.media.midi.IMidiDeviceServer iMidiDeviceServer, android.os.IBinder iBinder) {
                    android.media.midi.MidiDevice midiDevice;
                    if (iMidiDeviceServer != null) {
                        midiDevice = new android.media.midi.MidiDevice(midiDeviceInfo, iMidiDeviceServer, android.media.midi.MidiManager.this.mService, android.media.midi.MidiManager.this.mToken, iBinder);
                    } else {
                        midiDevice = null;
                    }
                    android.media.midi.MidiManager.this.sendOpenDeviceResponse(midiDevice, onDeviceOpenedListener, handler);
                }
            });
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void openBluetoothDevice(android.bluetooth.BluetoothDevice bluetoothDevice, final android.media.midi.MidiManager.OnDeviceOpenedListener onDeviceOpenedListener, final android.os.Handler handler) {
        android.util.Log.d(TAG, "openBluetoothDevice() " + bluetoothDevice);
        try {
            this.mService.openBluetoothDevice(this.mToken, bluetoothDevice, new android.media.midi.IMidiDeviceOpenCallback.Stub() { // from class: android.media.midi.MidiManager.3
                @Override // android.media.midi.IMidiDeviceOpenCallback
                public void onDeviceOpened(android.media.midi.IMidiDeviceServer iMidiDeviceServer, android.os.IBinder iBinder) {
                    android.media.midi.MidiDevice midiDevice;
                    android.util.Log.d(android.media.midi.MidiManager.TAG, "onDeviceOpened() server:" + iMidiDeviceServer);
                    if (iMidiDeviceServer != null) {
                        try {
                            midiDevice = new android.media.midi.MidiDevice(iMidiDeviceServer.getDeviceInfo(), iMidiDeviceServer, android.media.midi.MidiManager.this.mService, android.media.midi.MidiManager.this.mToken, iBinder);
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(android.media.midi.MidiManager.TAG, "remote exception in getDeviceInfo()");
                        }
                        android.media.midi.MidiManager.this.sendOpenDeviceResponse(midiDevice, onDeviceOpenedListener, handler);
                    }
                    midiDevice = null;
                    android.media.midi.MidiManager.this.sendOpenDeviceResponse(midiDevice, onDeviceOpenedListener, handler);
                }
            });
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void closeBluetoothDevice(android.media.midi.MidiDevice midiDevice) {
        try {
            midiDevice.close();
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "Exception closing BLE-MIDI device" + e);
        }
    }

    public android.media.midi.MidiDeviceServer createDeviceServer(android.media.midi.MidiReceiver[] midiReceiverArr, int i, java.lang.String[] strArr, java.lang.String[] strArr2, android.os.Bundle bundle, int i2, int i3, android.media.midi.MidiDeviceServer.Callback callback) {
        try {
            android.media.midi.MidiDeviceServer midiDeviceServer = new android.media.midi.MidiDeviceServer(this.mService, midiReceiverArr, i, callback);
            if (this.mService.registerDeviceServer(midiDeviceServer.getBinderInterface(), midiReceiverArr.length, i, strArr, strArr2, bundle, i2, i3) == null) {
                android.util.Log.e(TAG, "registerVirtualDevice failed");
                return null;
            }
            return midiDeviceServer;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
