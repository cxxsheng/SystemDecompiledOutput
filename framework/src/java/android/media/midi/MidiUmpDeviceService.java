package android.media.midi;

/* loaded from: classes2.dex */
public abstract class MidiUmpDeviceService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.media.midi.MidiUmpDeviceService";
    private static final java.lang.String TAG = "MidiUmpDeviceService";
    private final android.media.midi.MidiDeviceServer.Callback mCallback = new android.media.midi.MidiDeviceServer.Callback() { // from class: android.media.midi.MidiUmpDeviceService.1
        @Override // android.media.midi.MidiDeviceServer.Callback
        public void onDeviceStatusChanged(android.media.midi.MidiDeviceServer midiDeviceServer, android.media.midi.MidiDeviceStatus midiDeviceStatus) {
            android.media.midi.MidiUmpDeviceService.this.onDeviceStatusChanged(midiDeviceStatus);
        }

        @Override // android.media.midi.MidiDeviceServer.Callback
        public void onClose() {
            android.media.midi.MidiUmpDeviceService.this.onClose();
        }
    };
    private android.media.midi.MidiDeviceInfo mDeviceInfo;
    private android.media.midi.IMidiManager mMidiManager;
    private android.media.midi.MidiDeviceServer mServer;

    public abstract java.util.List<android.media.midi.MidiReceiver> onGetInputPortReceivers();

    @Override // android.app.Service
    public void onCreate() {
        android.media.midi.MidiDeviceServer midiDeviceServer;
        android.media.midi.MidiDeviceInfo serviceDeviceInfo;
        this.mMidiManager = android.media.midi.IMidiManager.Stub.asInterface(android.os.ServiceManager.getService("midi"));
        try {
            serviceDeviceInfo = this.mMidiManager.getServiceDeviceInfo(getPackageName(), getClass().getName());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "RemoteException in IMidiManager.getServiceDeviceInfo");
            midiDeviceServer = null;
        }
        if (serviceDeviceInfo == null) {
            android.util.Log.e(TAG, "Could not find MidiDeviceInfo for MidiUmpDeviceService " + this);
            return;
        }
        this.mDeviceInfo = serviceDeviceInfo;
        java.util.List<android.media.midi.MidiReceiver> onGetInputPortReceivers = onGetInputPortReceivers();
        if (onGetInputPortReceivers == null) {
            android.util.Log.e(TAG, "Could not get input port receivers for MidiUmpDeviceService " + this);
            return;
        }
        android.media.midi.MidiReceiver[] midiReceiverArr = new android.media.midi.MidiReceiver[onGetInputPortReceivers.size()];
        onGetInputPortReceivers.toArray(midiReceiverArr);
        midiDeviceServer = new android.media.midi.MidiDeviceServer(this.mMidiManager, midiReceiverArr, serviceDeviceInfo, this.mCallback);
        this.mServer = midiDeviceServer;
    }

    public final java.util.List<android.media.midi.MidiReceiver> getOutputPortReceivers() {
        if (this.mServer == null) {
            return new java.util.ArrayList();
        }
        return java.util.Arrays.asList(this.mServer.getOutputPortReceivers());
    }

    public final android.media.midi.MidiDeviceInfo getDeviceInfo() {
        return this.mDeviceInfo;
    }

    public void onDeviceStatusChanged(android.media.midi.MidiDeviceStatus midiDeviceStatus) {
    }

    public void onClose() {
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction()) && this.mServer != null) {
            return this.mServer.getBinderInterface().asBinder();
        }
        return null;
    }
}
