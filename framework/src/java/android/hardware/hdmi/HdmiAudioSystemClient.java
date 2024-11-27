package android.hardware.hdmi;

/* loaded from: classes2.dex */
public final class HdmiAudioSystemClient extends android.hardware.hdmi.HdmiClient {
    private static final int REPORT_AUDIO_STATUS_INTERVAL_MS = 500;
    private static final java.lang.String TAG = "HdmiAudioSystemClient";
    private boolean mCanSendAudioStatus;
    private final android.os.Handler mHandler;
    private boolean mLastIsMute;
    private int mLastMaxVolume;
    private int mLastVolume;
    private boolean mPendingReportAudioStatus;

    public interface SetSystemAudioModeCallback {
        void onComplete(int i);
    }

    public HdmiAudioSystemClient(android.hardware.hdmi.IHdmiControlService iHdmiControlService) {
        this(iHdmiControlService, null);
    }

    public HdmiAudioSystemClient(android.hardware.hdmi.IHdmiControlService iHdmiControlService, android.os.Handler handler) {
        super(iHdmiControlService);
        this.mCanSendAudioStatus = true;
        this.mHandler = handler == null ? new android.os.Handler(android.os.Looper.getMainLooper()) : handler;
    }

    @Override // android.hardware.hdmi.HdmiClient
    public int getDeviceType() {
        return 5;
    }

    public void sendReportAudioStatusCecCommand(boolean z, int i, int i2, boolean z2) {
        if (z) {
            try {
                this.mService.reportAudioStatus(getDeviceType(), i, i2, z2);
                return;
            } catch (android.os.RemoteException e) {
                return;
            }
        }
        this.mLastVolume = i;
        this.mLastMaxVolume = i2;
        this.mLastIsMute = z2;
        if (this.mCanSendAudioStatus) {
            try {
                this.mService.reportAudioStatus(getDeviceType(), i, i2, z2);
                this.mCanSendAudioStatus = false;
                this.mHandler.postDelayed(new java.lang.Runnable() { // from class: android.hardware.hdmi.HdmiAudioSystemClient.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (android.hardware.hdmi.HdmiAudioSystemClient.this.mPendingReportAudioStatus) {
                            try {
                                try {
                                    android.hardware.hdmi.HdmiAudioSystemClient.this.mService.reportAudioStatus(android.hardware.hdmi.HdmiAudioSystemClient.this.getDeviceType(), android.hardware.hdmi.HdmiAudioSystemClient.this.mLastVolume, android.hardware.hdmi.HdmiAudioSystemClient.this.mLastMaxVolume, android.hardware.hdmi.HdmiAudioSystemClient.this.mLastIsMute);
                                    android.hardware.hdmi.HdmiAudioSystemClient.this.mHandler.postDelayed(this, 500L);
                                } catch (android.os.RemoteException e2) {
                                    android.hardware.hdmi.HdmiAudioSystemClient.this.mCanSendAudioStatus = true;
                                }
                                return;
                            } finally {
                                android.hardware.hdmi.HdmiAudioSystemClient.this.mPendingReportAudioStatus = false;
                            }
                        }
                        android.hardware.hdmi.HdmiAudioSystemClient.this.mCanSendAudioStatus = true;
                    }
                }, 500L);
                return;
            } catch (android.os.RemoteException e2) {
                return;
            }
        }
        this.mPendingReportAudioStatus = true;
    }

    public void setSystemAudioMode(boolean z, android.hardware.hdmi.HdmiAudioSystemClient.SetSystemAudioModeCallback setSystemAudioModeCallback) {
    }

    public void setSystemAudioModeOnForAudioOnlySource() {
        try {
            this.mService.setSystemAudioModeOnForAudioOnlySource();
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "Failed to set System Audio Mode on for Audio Only source");
        }
    }
}
