package android.hardware.hdmi;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class HdmiPlaybackClient extends android.hardware.hdmi.HdmiClient {
    private static final int ADDR_TV = 0;
    private static final java.lang.String TAG = "HdmiPlaybackClient";

    public interface DisplayStatusCallback {
        void onComplete(int i);
    }

    public interface OneTouchPlayCallback {
        void onComplete(int i);
    }

    HdmiPlaybackClient(android.hardware.hdmi.IHdmiControlService iHdmiControlService) {
        super(iHdmiControlService);
    }

    public void oneTouchPlay(android.hardware.hdmi.HdmiPlaybackClient.OneTouchPlayCallback oneTouchPlayCallback) {
        try {
            this.mService.oneTouchPlay(getCallbackWrapper(oneTouchPlayCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "oneTouchPlay threw exception ", e);
        }
    }

    @Override // android.hardware.hdmi.HdmiClient
    public int getDeviceType() {
        return 4;
    }

    public void queryDisplayStatus(android.hardware.hdmi.HdmiPlaybackClient.DisplayStatusCallback displayStatusCallback) {
        try {
            this.mService.queryDisplayStatus(getCallbackWrapper(displayStatusCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "queryDisplayStatus threw exception ", e);
        }
    }

    public void sendStandby() {
        try {
            this.mService.sendStandby(getDeviceType(), android.hardware.hdmi.HdmiDeviceInfo.idForCecDevice(0));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "sendStandby threw exception ", e);
        }
    }

    private android.hardware.hdmi.IHdmiControlCallback getCallbackWrapper(final android.hardware.hdmi.HdmiPlaybackClient.OneTouchPlayCallback oneTouchPlayCallback) {
        if (oneTouchPlayCallback == null) {
            throw new java.lang.IllegalArgumentException("OneTouchPlayCallback cannot be null.");
        }
        return new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: android.hardware.hdmi.HdmiPlaybackClient.1
            @Override // android.hardware.hdmi.IHdmiControlCallback
            public void onComplete(int i) {
                oneTouchPlayCallback.onComplete(i);
            }
        };
    }

    private android.hardware.hdmi.IHdmiControlCallback getCallbackWrapper(final android.hardware.hdmi.HdmiPlaybackClient.DisplayStatusCallback displayStatusCallback) {
        if (displayStatusCallback == null) {
            throw new java.lang.IllegalArgumentException("DisplayStatusCallback cannot be null.");
        }
        return new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: android.hardware.hdmi.HdmiPlaybackClient.2
            @Override // android.hardware.hdmi.IHdmiControlCallback
            public void onComplete(int i) {
                displayStatusCallback.onComplete(i);
            }
        };
    }
}
