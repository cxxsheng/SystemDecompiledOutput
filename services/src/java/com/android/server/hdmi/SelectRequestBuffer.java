package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class SelectRequestBuffer {
    public static final com.android.server.hdmi.SelectRequestBuffer EMPTY_BUFFER = new com.android.server.hdmi.SelectRequestBuffer() { // from class: com.android.server.hdmi.SelectRequestBuffer.1
        @Override // com.android.server.hdmi.SelectRequestBuffer
        public void process() {
        }
    };
    private static final java.lang.String TAG = "SelectRequestBuffer";
    private com.android.server.hdmi.SelectRequestBuffer.SelectRequest mRequest;

    public static abstract class SelectRequest {
        protected final android.hardware.hdmi.IHdmiControlCallback mCallback;
        protected final int mId;
        protected final com.android.server.hdmi.HdmiControlService mService;

        public abstract void process();

        public SelectRequest(com.android.server.hdmi.HdmiControlService hdmiControlService, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
            this.mService = hdmiControlService;
            this.mId = i;
            this.mCallback = iHdmiControlCallback;
        }

        protected com.android.server.hdmi.HdmiCecLocalDeviceTv tv() {
            return this.mService.tv();
        }

        protected com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem audioSystem() {
            return this.mService.audioSystem();
        }

        protected boolean isLocalDeviceReady() {
            if (tv() == null) {
                android.util.Slog.e(com.android.server.hdmi.SelectRequestBuffer.TAG, "Local tv device not available");
                invokeCallback(2);
                return false;
            }
            return true;
        }

        private void invokeCallback(int i) {
            try {
                if (this.mCallback != null) {
                    this.mCallback.onComplete(i);
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.hdmi.SelectRequestBuffer.TAG, "Invoking callback failed:" + e);
            }
        }
    }

    public static class DeviceSelectRequest extends com.android.server.hdmi.SelectRequestBuffer.SelectRequest {
        private DeviceSelectRequest(com.android.server.hdmi.HdmiControlService hdmiControlService, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
            super(hdmiControlService, i, iHdmiControlCallback);
        }

        @Override // com.android.server.hdmi.SelectRequestBuffer.SelectRequest
        public void process() {
            if (isLocalDeviceReady()) {
                android.util.Slog.v(com.android.server.hdmi.SelectRequestBuffer.TAG, "calling delayed deviceSelect id:" + this.mId);
                tv().deviceSelect(this.mId, this.mCallback);
            }
        }
    }

    public static class PortSelectRequest extends com.android.server.hdmi.SelectRequestBuffer.SelectRequest {
        private PortSelectRequest(com.android.server.hdmi.HdmiControlService hdmiControlService, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
            super(hdmiControlService, i, iHdmiControlCallback);
        }

        @Override // com.android.server.hdmi.SelectRequestBuffer.SelectRequest
        public void process() {
            if (isLocalDeviceReady()) {
                android.util.Slog.v(com.android.server.hdmi.SelectRequestBuffer.TAG, "calling delayed portSelect id:" + this.mId);
                com.android.server.hdmi.HdmiCecLocalDeviceTv tv = tv();
                if (tv != null) {
                    tv.doManualPortSwitching(this.mId, this.mCallback);
                    return;
                }
                com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem audioSystem = audioSystem();
                if (audioSystem != null) {
                    audioSystem.doManualPortSwitching(this.mId, this.mCallback);
                }
            }
        }
    }

    public static com.android.server.hdmi.SelectRequestBuffer.DeviceSelectRequest newDeviceSelect(com.android.server.hdmi.HdmiControlService hdmiControlService, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        return new com.android.server.hdmi.SelectRequestBuffer.DeviceSelectRequest(hdmiControlService, i, iHdmiControlCallback);
    }

    public static com.android.server.hdmi.SelectRequestBuffer.PortSelectRequest newPortSelect(com.android.server.hdmi.HdmiControlService hdmiControlService, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        return new com.android.server.hdmi.SelectRequestBuffer.PortSelectRequest(hdmiControlService, i, iHdmiControlCallback);
    }

    public void set(com.android.server.hdmi.SelectRequestBuffer.SelectRequest selectRequest) {
        this.mRequest = selectRequest;
    }

    public void process() {
        if (this.mRequest != null) {
            this.mRequest.process();
            clear();
        }
    }

    public void clear() {
        this.mRequest = null;
    }
}
