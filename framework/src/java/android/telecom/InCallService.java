package android.telecom;

/* loaded from: classes3.dex */
public abstract class InCallService extends android.app.Service {
    private static final int MSG_ADD_CALL = 2;
    private static final int MSG_BRING_TO_FOREGROUND = 6;
    private static final int MSG_ON_AVAILABLE_CALL_ENDPOINTS_CHANGED = 15;
    private static final int MSG_ON_CALL_AUDIO_STATE_CHANGED = 5;
    private static final int MSG_ON_CALL_ENDPOINT_CHANGED = 14;
    private static final int MSG_ON_CAN_ADD_CALL_CHANGED = 7;
    private static final int MSG_ON_CONNECTION_EVENT = 9;
    private static final int MSG_ON_HANDOVER_COMPLETE = 13;
    private static final int MSG_ON_HANDOVER_FAILED = 12;
    private static final int MSG_ON_MUTE_STATE_CHANGED = 16;
    private static final int MSG_ON_RTT_INITIATION_FAILURE = 11;
    private static final int MSG_ON_RTT_UPGRADE_REQUEST = 10;
    private static final int MSG_SET_IN_CALL_ADAPTER = 1;
    private static final int MSG_SET_POST_DIAL_WAIT = 4;
    private static final int MSG_SILENCE_RINGER = 8;
    private static final int MSG_UPDATE_CALL = 3;
    public static final java.lang.String SERVICE_INTERFACE = "android.telecom.InCallService";
    private android.telecom.CallEndpoint mCallEndpoint;
    private android.telecom.Phone mPhone;
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: android.telecom.InCallService.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            com.android.internal.os.SomeArgs someArgs;
            if (android.telecom.InCallService.this.mPhone == null && message.what != 1) {
                return;
            }
            switch (message.what) {
                case 1:
                    if (android.telecom.InCallService.this.mPhone != null) {
                        android.telecom.Log.i(this, "mPhone is already instantiated, ignoring request to reset adapter.", new java.lang.Object[0]);
                        return;
                    }
                    android.telecom.InCallService.this.mPhone = new android.telecom.Phone(new android.telecom.InCallAdapter((com.android.internal.telecom.IInCallAdapter) message.obj), android.telecom.InCallService.this.getApplicationContext().getOpPackageName(), android.telecom.InCallService.this.getApplicationContext().getApplicationInfo().targetSdkVersion);
                    android.telecom.InCallService.this.mPhone.addListener(android.telecom.InCallService.this.mPhoneListener);
                    android.telecom.InCallService.this.onPhoneCreated(android.telecom.InCallService.this.mPhone);
                    return;
                case 2:
                    android.telecom.InCallService.this.mPhone.internalAddCall((android.telecom.ParcelableCall) message.obj);
                    return;
                case 3:
                    android.telecom.InCallService.this.mPhone.internalUpdateCall((android.telecom.ParcelableCall) message.obj);
                    return;
                case 4:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.InCallService.this.mPhone.internalSetPostDialWait((java.lang.String) someArgs.arg1, (java.lang.String) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 5:
                    android.telecom.InCallService.this.mPhone.internalCallAudioStateChanged((android.telecom.CallAudioState) message.obj);
                    return;
                case 6:
                    android.telecom.InCallService.this.mPhone.internalBringToForeground(message.arg1 == 1);
                    return;
                case 7:
                    android.telecom.InCallService.this.mPhone.internalSetCanAddCall(message.arg1 == 1);
                    return;
                case 8:
                    android.telecom.InCallService.this.mPhone.internalSilenceRinger();
                    return;
                case 9:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.InCallService.this.mPhone.internalOnConnectionEvent((java.lang.String) someArgs.arg1, (java.lang.String) someArgs.arg2, (android.os.Bundle) someArgs.arg3);
                        return;
                    } finally {
                    }
                case 10:
                    android.telecom.InCallService.this.mPhone.internalOnRttUpgradeRequest((java.lang.String) message.obj, message.arg1);
                    return;
                case 11:
                    android.telecom.InCallService.this.mPhone.internalOnRttInitiationFailure((java.lang.String) message.obj, message.arg1);
                    return;
                case 12:
                    android.telecom.InCallService.this.mPhone.internalOnHandoverFailed((java.lang.String) message.obj, message.arg1);
                    return;
                case 13:
                    android.telecom.InCallService.this.mPhone.internalOnHandoverComplete((java.lang.String) message.obj);
                    return;
                case 14:
                    android.telecom.CallEndpoint callEndpoint = (android.telecom.CallEndpoint) message.obj;
                    if (!java.util.Objects.equals(android.telecom.InCallService.this.mCallEndpoint, callEndpoint)) {
                        android.telecom.InCallService.this.mCallEndpoint = callEndpoint;
                        android.telecom.InCallService.this.onCallEndpointChanged(android.telecom.InCallService.this.mCallEndpoint);
                        return;
                    }
                    return;
                case 15:
                    android.telecom.InCallService.this.onAvailableCallEndpointsChanged((java.util.List) message.obj);
                    return;
                case 16:
                    android.telecom.InCallService.this.onMuteStateChanged(((java.lang.Boolean) message.obj).booleanValue());
                    return;
                default:
                    return;
            }
        }
    };
    private android.telecom.Phone.Listener mPhoneListener = new android.telecom.Phone.Listener() { // from class: android.telecom.InCallService.2
        @Override // android.telecom.Phone.Listener
        public void onAudioStateChanged(android.telecom.Phone phone, android.telecom.AudioState audioState) {
            android.telecom.InCallService.this.onAudioStateChanged(audioState);
        }

        @Override // android.telecom.Phone.Listener
        public void onCallAudioStateChanged(android.telecom.Phone phone, android.telecom.CallAudioState callAudioState) {
            android.telecom.InCallService.this.onCallAudioStateChanged(callAudioState);
        }

        @Override // android.telecom.Phone.Listener
        public void onBringToForeground(android.telecom.Phone phone, boolean z) {
            android.telecom.InCallService.this.onBringToForeground(z);
        }

        @Override // android.telecom.Phone.Listener
        public void onCallAdded(android.telecom.Phone phone, android.telecom.Call call) {
            android.telecom.InCallService.this.onCallAdded(call);
        }

        @Override // android.telecom.Phone.Listener
        public void onCallRemoved(android.telecom.Phone phone, android.telecom.Call call) {
            android.telecom.InCallService.this.onCallRemoved(call);
        }

        @Override // android.telecom.Phone.Listener
        public void onCanAddCallChanged(android.telecom.Phone phone, boolean z) {
            android.telecom.InCallService.this.onCanAddCallChanged(z);
        }

        @Override // android.telecom.Phone.Listener
        public void onSilenceRinger(android.telecom.Phone phone) {
            android.telecom.InCallService.this.onSilenceRinger();
        }
    };

    public static abstract class VideoCall {

        public static abstract class Callback {
            public abstract void onCallDataUsageChanged(long j);

            public abstract void onCallSessionEvent(int i);

            public abstract void onCameraCapabilitiesChanged(android.telecom.VideoProfile.CameraCapabilities cameraCapabilities);

            public abstract void onPeerDimensionsChanged(int i, int i2);

            public abstract void onSessionModifyRequestReceived(android.telecom.VideoProfile videoProfile);

            public abstract void onSessionModifyResponseReceived(int i, android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2);

            public abstract void onVideoQualityChanged(int i);
        }

        public abstract void destroy();

        public abstract void registerCallback(android.telecom.InCallService.VideoCall.Callback callback);

        public abstract void registerCallback(android.telecom.InCallService.VideoCall.Callback callback, android.os.Handler handler);

        public abstract void requestCallDataUsage();

        public abstract void requestCameraCapabilities();

        public abstract void sendSessionModifyRequest(android.telecom.VideoProfile videoProfile);

        public abstract void sendSessionModifyResponse(android.telecom.VideoProfile videoProfile);

        public abstract void setCamera(java.lang.String str);

        public abstract void setDeviceOrientation(int i);

        public abstract void setDisplaySurface(android.view.Surface surface);

        public abstract void setPauseImage(android.net.Uri uri);

        public abstract void setPreviewSurface(android.view.Surface surface);

        public abstract void setZoom(float f);

        public abstract void unregisterCallback(android.telecom.InCallService.VideoCall.Callback callback);
    }

    private final class InCallServiceBinder extends com.android.internal.telecom.IInCallService.Stub {
        private InCallServiceBinder() {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void setInCallAdapter(com.android.internal.telecom.IInCallAdapter iInCallAdapter) {
            android.telecom.InCallService.this.mHandler.obtainMessage(1, iInCallAdapter).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void addCall(android.telecom.ParcelableCall parcelableCall) {
            android.telecom.InCallService.this.mHandler.obtainMessage(2, parcelableCall).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void updateCall(android.telecom.ParcelableCall parcelableCall) {
            android.telecom.InCallService.this.mHandler.obtainMessage(3, parcelableCall).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void setPostDial(java.lang.String str, java.lang.String str2) {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void setPostDialWait(java.lang.String str, java.lang.String str2) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = str2;
            android.telecom.InCallService.this.mHandler.obtainMessage(4, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onCallAudioStateChanged(android.telecom.CallAudioState callAudioState) {
            android.telecom.InCallService.this.mHandler.obtainMessage(5, callAudioState).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onCallEndpointChanged(android.telecom.CallEndpoint callEndpoint) {
            android.telecom.InCallService.this.mHandler.obtainMessage(14, callEndpoint).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onAvailableCallEndpointsChanged(java.util.List<android.telecom.CallEndpoint> list) {
            android.telecom.InCallService.this.mHandler.obtainMessage(15, list).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onMuteStateChanged(boolean z) {
            android.telecom.InCallService.this.mHandler.obtainMessage(16, java.lang.Boolean.valueOf(z)).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void bringToForeground(boolean z) {
            android.telecom.InCallService.this.mHandler.obtainMessage(6, z ? 1 : 0, 0).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onCanAddCallChanged(boolean z) {
            android.telecom.InCallService.this.mHandler.obtainMessage(7, z ? 1 : 0, 0).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void silenceRinger() {
            android.telecom.InCallService.this.mHandler.obtainMessage(8).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onConnectionEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = str2;
            obtain.arg3 = bundle;
            android.telecom.InCallService.this.mHandler.obtainMessage(9, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onRttUpgradeRequest(java.lang.String str, int i) {
            android.telecom.InCallService.this.mHandler.obtainMessage(10, i, 0, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onRttInitiationFailure(java.lang.String str, int i) {
            android.telecom.InCallService.this.mHandler.obtainMessage(11, i, 0, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onHandoverFailed(java.lang.String str, int i) {
            android.telecom.InCallService.this.mHandler.obtainMessage(12, i, 0, str).sendToTarget();
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onHandoverComplete(java.lang.String str) {
            android.telecom.InCallService.this.mHandler.obtainMessage(13, str).sendToTarget();
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return new android.telecom.InCallService.InCallServiceBinder();
    }

    @Override // android.app.Service
    public boolean onUnbind(android.content.Intent intent) {
        if (this.mPhone != null) {
            android.telecom.Phone phone = this.mPhone;
            this.mPhone = null;
            phone.destroy();
            phone.removeListener(this.mPhoneListener);
            onPhoneDestroyed(phone);
            return false;
        }
        return false;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public android.telecom.Phone getPhone() {
        return this.mPhone;
    }

    public final java.util.List<android.telecom.Call> getCalls() {
        return this.mPhone == null ? java.util.Collections.emptyList() : this.mPhone.getCalls();
    }

    public final boolean canAddCall() {
        if (this.mPhone == null) {
            return false;
        }
        return this.mPhone.canAddCall();
    }

    @java.lang.Deprecated
    public final android.telecom.AudioState getAudioState() {
        if (this.mPhone == null) {
            return null;
        }
        return this.mPhone.getAudioState();
    }

    @java.lang.Deprecated
    public final android.telecom.CallAudioState getCallAudioState() {
        if (this.mPhone == null) {
            return null;
        }
        return this.mPhone.getCallAudioState();
    }

    public final void setMuted(boolean z) {
        if (this.mPhone != null) {
            this.mPhone.setMuted(z);
        }
    }

    @java.lang.Deprecated
    public final void setAudioRoute(int i) {
        if (this.mPhone != null) {
            this.mPhone.setAudioRoute(i);
        }
    }

    @java.lang.Deprecated
    public final void requestBluetoothAudio(android.bluetooth.BluetoothDevice bluetoothDevice) {
        if (this.mPhone != null) {
            this.mPhone.requestBluetoothAudio(bluetoothDevice.getAddress());
        }
    }

    public final void requestCallEndpointChange(android.telecom.CallEndpoint callEndpoint, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallEndpointException> outcomeReceiver) {
        if (this.mPhone != null) {
            this.mPhone.requestCallEndpointChange(callEndpoint, executor, outcomeReceiver);
        }
    }

    public final android.telecom.CallEndpoint getCurrentCallEndpoint() {
        return this.mCallEndpoint;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void onPhoneCreated(android.telecom.Phone phone) {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void onPhoneDestroyed(android.telecom.Phone phone) {
    }

    @java.lang.Deprecated
    public void onAudioStateChanged(android.telecom.AudioState audioState) {
    }

    @java.lang.Deprecated
    public void onCallAudioStateChanged(android.telecom.CallAudioState callAudioState) {
    }

    public void onCallEndpointChanged(android.telecom.CallEndpoint callEndpoint) {
    }

    public void onAvailableCallEndpointsChanged(java.util.List<android.telecom.CallEndpoint> list) {
    }

    public void onMuteStateChanged(boolean z) {
    }

    public void onBringToForeground(boolean z) {
    }

    public void onCallAdded(android.telecom.Call call) {
    }

    public void onCallRemoved(android.telecom.Call call) {
    }

    public void onCanAddCallChanged(boolean z) {
    }

    public void onSilenceRinger() {
    }

    public void onConnectionEvent(android.telecom.Call call, java.lang.String str, android.os.Bundle bundle) {
    }
}
