package android.hardware.hdmi;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class HdmiTvClient extends android.hardware.hdmi.HdmiClient {
    private static final java.lang.String TAG = "HdmiTvClient";
    public static final int VENDOR_DATA_SIZE = 16;

    public interface HdmiMhlVendorCommandListener {
        void onReceived(int i, int i2, int i3, byte[] bArr);
    }

    public interface InputChangeListener {
        void onChanged(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo);
    }

    public interface SelectCallback {
        void onComplete(int i);
    }

    HdmiTvClient(android.hardware.hdmi.IHdmiControlService iHdmiControlService) {
        super(iHdmiControlService);
    }

    static android.hardware.hdmi.HdmiTvClient create(android.hardware.hdmi.IHdmiControlService iHdmiControlService) {
        return new android.hardware.hdmi.HdmiTvClient(iHdmiControlService);
    }

    @Override // android.hardware.hdmi.HdmiClient
    public int getDeviceType() {
        return 0;
    }

    @java.lang.Deprecated
    public void deviceSelect(int i, android.hardware.hdmi.HdmiTvClient.SelectCallback selectCallback) {
        if (selectCallback == null) {
            throw new java.lang.IllegalArgumentException("callback must not be null.");
        }
        try {
            this.mService.deviceSelect(i, getCallbackWrapper(selectCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to select device: ", e);
        }
    }

    private static android.hardware.hdmi.IHdmiControlCallback getCallbackWrapper(final android.hardware.hdmi.HdmiTvClient.SelectCallback selectCallback) {
        return new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: android.hardware.hdmi.HdmiTvClient.1
            @Override // android.hardware.hdmi.IHdmiControlCallback
            public void onComplete(int i) {
                android.hardware.hdmi.HdmiTvClient.SelectCallback.this.onComplete(i);
            }
        };
    }

    public void portSelect(int i, android.hardware.hdmi.HdmiTvClient.SelectCallback selectCallback) {
        if (selectCallback == null) {
            throw new java.lang.IllegalArgumentException("Callback must not be null");
        }
        try {
            this.mService.portSelect(i, getCallbackWrapper(selectCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to select port: ", e);
        }
    }

    public void setInputChangeListener(android.hardware.hdmi.HdmiTvClient.InputChangeListener inputChangeListener) {
        if (inputChangeListener == null) {
            throw new java.lang.IllegalArgumentException("listener must not be null.");
        }
        try {
            this.mService.setInputChangeListener(getListenerWrapper(inputChangeListener));
        } catch (android.os.RemoteException e) {
            android.util.Log.e("TAG", "Failed to set InputChangeListener:", e);
        }
    }

    private static android.hardware.hdmi.IHdmiInputChangeListener getListenerWrapper(final android.hardware.hdmi.HdmiTvClient.InputChangeListener inputChangeListener) {
        return new android.hardware.hdmi.IHdmiInputChangeListener.Stub() { // from class: android.hardware.hdmi.HdmiTvClient.2
            @Override // android.hardware.hdmi.IHdmiInputChangeListener
            public void onChanged(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
                android.hardware.hdmi.HdmiTvClient.InputChangeListener.this.onChanged(hdmiDeviceInfo);
            }
        };
    }

    @java.lang.Deprecated
    public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getDeviceList() {
        try {
            return this.mService.getDeviceList();
        } catch (android.os.RemoteException e) {
            android.util.Log.e("TAG", "Failed to call getDeviceList():", e);
            return java.util.Collections.emptyList();
        }
    }

    public void setSystemAudioMode(boolean z, android.hardware.hdmi.HdmiTvClient.SelectCallback selectCallback) {
        try {
            this.mService.setSystemAudioMode(z, getCallbackWrapper(selectCallback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to set system audio mode:", e);
        }
    }

    public void setSystemAudioVolume(int i, int i2, int i3) {
        try {
            this.mService.setSystemAudioVolume(i, i2, i3);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to set volume: ", e);
        }
    }

    public void setSystemAudioMute(boolean z) {
        try {
            this.mService.setSystemAudioMute(z);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to set mute: ", e);
        }
    }

    public void setRecordListener(android.hardware.hdmi.HdmiRecordListener hdmiRecordListener) {
        if (hdmiRecordListener == null) {
            throw new java.lang.IllegalArgumentException("listener must not be null.");
        }
        try {
            this.mService.setHdmiRecordListener(getListenerWrapper(hdmiRecordListener));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to set record listener.", e);
        }
    }

    public void sendStandby(int i) {
        try {
            this.mService.sendStandby(getDeviceType(), i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "sendStandby threw exception ", e);
        }
    }

    private static android.hardware.hdmi.IHdmiRecordListener getListenerWrapper(final android.hardware.hdmi.HdmiRecordListener hdmiRecordListener) {
        return new android.hardware.hdmi.IHdmiRecordListener.Stub() { // from class: android.hardware.hdmi.HdmiTvClient.3
            @Override // android.hardware.hdmi.IHdmiRecordListener
            public byte[] getOneTouchRecordSource(int i) {
                android.hardware.hdmi.HdmiRecordSources.RecordSource onOneTouchRecordSourceRequested = android.hardware.hdmi.HdmiRecordListener.this.onOneTouchRecordSourceRequested(i);
                if (onOneTouchRecordSourceRequested == null) {
                    return libcore.util.EmptyArray.BYTE;
                }
                byte[] bArr = new byte[onOneTouchRecordSourceRequested.getDataSize(true)];
                onOneTouchRecordSourceRequested.toByteArray(true, bArr, 0);
                return bArr;
            }

            @Override // android.hardware.hdmi.IHdmiRecordListener
            public void onOneTouchRecordResult(int i, int i2) {
                android.hardware.hdmi.HdmiRecordListener.this.onOneTouchRecordResult(i, i2);
            }

            @Override // android.hardware.hdmi.IHdmiRecordListener
            public void onTimerRecordingResult(int i, int i2) {
                android.hardware.hdmi.HdmiRecordListener.this.onTimerRecordingResult(i, android.hardware.hdmi.HdmiRecordListener.TimerStatusData.parseFrom(i2));
            }

            @Override // android.hardware.hdmi.IHdmiRecordListener
            public void onClearTimerRecordingResult(int i, int i2) {
                android.hardware.hdmi.HdmiRecordListener.this.onClearTimerRecordingResult(i, i2);
            }
        };
    }

    public void startOneTouchRecord(int i, android.hardware.hdmi.HdmiRecordSources.RecordSource recordSource) {
        if (recordSource == null) {
            throw new java.lang.IllegalArgumentException("source must not be null.");
        }
        try {
            byte[] bArr = new byte[recordSource.getDataSize(true)];
            recordSource.toByteArray(true, bArr, 0);
            this.mService.startOneTouchRecord(i, bArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to start record: ", e);
        }
    }

    public void stopOneTouchRecord(int i) {
        try {
            this.mService.stopOneTouchRecord(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to stop record: ", e);
        }
    }

    public void startTimerRecording(int i, int i2, android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource timerRecordSource) {
        if (timerRecordSource == null) {
            throw new java.lang.IllegalArgumentException("source must not be null.");
        }
        checkTimerRecordingSourceType(i2);
        try {
            byte[] bArr = new byte[timerRecordSource.getDataSize()];
            timerRecordSource.toByteArray(bArr, 0);
            this.mService.startTimerRecording(i, i2, bArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to start record: ", e);
        }
    }

    private void checkTimerRecordingSourceType(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
                return;
            default:
                throw new java.lang.IllegalArgumentException("Invalid source type:" + i);
        }
    }

    public void clearTimerRecording(int i, int i2, android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource timerRecordSource) {
        if (timerRecordSource == null) {
            throw new java.lang.IllegalArgumentException("source must not be null.");
        }
        checkTimerRecordingSourceType(i2);
        try {
            byte[] bArr = new byte[timerRecordSource.getDataSize()];
            timerRecordSource.toByteArray(bArr, 0);
            this.mService.clearTimerRecording(i, i2, bArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to start record: ", e);
        }
    }

    public void setHdmiMhlVendorCommandListener(android.hardware.hdmi.HdmiTvClient.HdmiMhlVendorCommandListener hdmiMhlVendorCommandListener) {
        if (hdmiMhlVendorCommandListener == null) {
            throw new java.lang.IllegalArgumentException("listener must not be null.");
        }
        try {
            this.mService.addHdmiMhlVendorCommandListener(getListenerWrapper(hdmiMhlVendorCommandListener));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to set hdmi mhl vendor command listener: ", e);
        }
    }

    private android.hardware.hdmi.IHdmiMhlVendorCommandListener getListenerWrapper(final android.hardware.hdmi.HdmiTvClient.HdmiMhlVendorCommandListener hdmiMhlVendorCommandListener) {
        return new android.hardware.hdmi.IHdmiMhlVendorCommandListener.Stub() { // from class: android.hardware.hdmi.HdmiTvClient.4
            @Override // android.hardware.hdmi.IHdmiMhlVendorCommandListener
            public void onReceived(int i, int i2, int i3, byte[] bArr) {
                hdmiMhlVendorCommandListener.onReceived(i, i2, i3, bArr);
            }
        };
    }

    public void sendMhlVendorCommand(int i, int i2, int i3, byte[] bArr) {
        if (bArr == null || bArr.length != 16) {
            throw new java.lang.IllegalArgumentException("Invalid vendor command data.");
        }
        if (i2 < 0 || i2 >= 16) {
            throw new java.lang.IllegalArgumentException("Invalid offset:" + i2);
        }
        if (i3 < 0 || i2 + i3 > 16) {
            throw new java.lang.IllegalArgumentException("Invalid length:" + i3);
        }
        try {
            this.mService.sendMhlVendorCommand(i, i2, i3, bArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to send vendor command: ", e);
        }
    }
}
