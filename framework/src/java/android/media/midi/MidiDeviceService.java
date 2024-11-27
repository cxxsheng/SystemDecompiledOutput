package android.media.midi;

/* loaded from: classes2.dex */
public abstract class MidiDeviceService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.media.midi.MidiDeviceService";
    private static final java.lang.String TAG = "MidiDeviceService";
    private final android.media.midi.MidiDeviceServer.Callback mCallback = new android.media.midi.MidiDeviceServer.Callback() { // from class: android.media.midi.MidiDeviceService.1
        @Override // android.media.midi.MidiDeviceServer.Callback
        public void onDeviceStatusChanged(android.media.midi.MidiDeviceServer midiDeviceServer, android.media.midi.MidiDeviceStatus midiDeviceStatus) {
            android.media.midi.MidiDeviceService.this.onDeviceStatusChanged(midiDeviceStatus);
        }

        @Override // android.media.midi.MidiDeviceServer.Callback
        public void onClose() {
            android.media.midi.MidiDeviceService.this.onClose();
        }
    };
    private android.media.midi.MidiDeviceInfo mDeviceInfo;
    private android.media.midi.IMidiManager mMidiManager;
    private android.media.midi.MidiDeviceServer mServer;

    public abstract android.media.midi.MidiReceiver[] onGetInputPortReceivers();

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
            android.util.Log.e(TAG, "Could not find MidiDeviceInfo for MidiDeviceService " + this);
            return;
        }
        this.mDeviceInfo = serviceDeviceInfo;
        android.media.midi.MidiReceiver[] onGetInputPortReceivers = onGetInputPortReceivers();
        if (onGetInputPortReceivers == null) {
            onGetInputPortReceivers = new android.media.midi.MidiReceiver[0];
        }
        midiDeviceServer = new android.media.midi.MidiDeviceServer(this.mMidiManager, onGetInputPortReceivers, serviceDeviceInfo, this.mCallback);
        this.mServer = midiDeviceServer;
    }

    public final android.media.midi.MidiReceiver[] getOutputPortReceivers() {
        if (this.mServer == null) {
            return null;
        }
        return this.mServer.getOutputPortReceivers();
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
