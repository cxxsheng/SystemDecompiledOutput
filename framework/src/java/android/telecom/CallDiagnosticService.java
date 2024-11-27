package android.telecom;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class CallDiagnosticService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.telecom.CallDiagnosticService";
    private com.android.internal.telecom.ICallDiagnosticServiceAdapter mAdapter;
    private android.telecom.CallDiagnostics.Listener mDiagnosticCallListener = new android.telecom.CallDiagnostics.Listener() { // from class: android.telecom.CallDiagnosticService.1
        @Override // android.telecom.CallDiagnostics.Listener
        public void onSendDeviceToDeviceMessage(android.telecom.CallDiagnostics callDiagnostics, int i, int i2) {
            android.telecom.CallDiagnosticService.this.handleSendDeviceToDeviceMessage(callDiagnostics, i, i2);
        }

        @Override // android.telecom.CallDiagnostics.Listener
        public void onDisplayDiagnosticMessage(android.telecom.CallDiagnostics callDiagnostics, int i, java.lang.CharSequence charSequence) {
            android.telecom.CallDiagnosticService.this.handleDisplayDiagnosticMessage(callDiagnostics, i, charSequence);
        }

        @Override // android.telecom.CallDiagnostics.Listener
        public void onClearDiagnosticMessage(android.telecom.CallDiagnostics callDiagnostics, int i) {
            android.telecom.CallDiagnosticService.this.handleClearDiagnosticMessage(callDiagnostics, i);
        }
    };
    private final java.util.Map<java.lang.String, android.telecom.Call.Details> mCallByTelecomCallId = new android.util.ArrayMap();
    private final java.util.Map<java.lang.String, android.telecom.CallDiagnostics> mDiagnosticCallByTelecomCallId = new android.util.ArrayMap();
    private final java.lang.Object mLock = new java.lang.Object();

    /* renamed from: onBluetoothCallQualityReportReceived, reason: merged with bridge method [inline-methods] */
    public abstract void lambda$handleBluetoothCallQualityReport$4(android.telecom.BluetoothCallQualityReport bluetoothCallQualityReport);

    public abstract void onCallAudioStateChanged(android.telecom.CallAudioState callAudioState);

    public abstract android.telecom.CallDiagnostics onInitializeCallDiagnostics(android.telecom.Call.Details details);

    /* renamed from: onRemoveCallDiagnostics, reason: merged with bridge method [inline-methods] */
    public abstract void lambda$handleCallRemoved$2(android.telecom.CallDiagnostics callDiagnostics);

    /* JADX INFO: Access modifiers changed from: private */
    final class CallDiagnosticServiceBinder extends com.android.internal.telecom.ICallDiagnosticService.Stub {
        private CallDiagnosticServiceBinder() {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void setAdapter(com.android.internal.telecom.ICallDiagnosticServiceAdapter iCallDiagnosticServiceAdapter) throws android.os.RemoteException {
            android.telecom.CallDiagnosticService.this.handleSetAdapter(iCallDiagnosticServiceAdapter);
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void initializeDiagnosticCall(android.telecom.ParcelableCall parcelableCall) throws android.os.RemoteException {
            android.telecom.CallDiagnosticService.this.handleCallAdded(parcelableCall);
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void updateCall(android.telecom.ParcelableCall parcelableCall) throws android.os.RemoteException {
            android.telecom.CallDiagnosticService.this.handleCallUpdated(parcelableCall);
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void removeDiagnosticCall(java.lang.String str) throws android.os.RemoteException {
            android.telecom.CallDiagnosticService.this.handleCallRemoved(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateCallAudioState$0(android.telecom.CallAudioState callAudioState) {
            android.telecom.CallDiagnosticService.this.onCallAudioStateChanged(callAudioState);
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void updateCallAudioState(final android.telecom.CallAudioState callAudioState) throws android.os.RemoteException {
            android.telecom.CallDiagnosticService.this.getExecutor().execute(new java.lang.Runnable() { // from class: android.telecom.CallDiagnosticService$CallDiagnosticServiceBinder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telecom.CallDiagnosticService.CallDiagnosticServiceBinder.this.lambda$updateCallAudioState$0(callAudioState);
                }
            });
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void receiveDeviceToDeviceMessage(java.lang.String str, int i, int i2) {
            android.telecom.CallDiagnosticService.this.handleReceivedD2DMessage(str, i, i2);
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void receiveBluetoothCallQualityReport(android.telecom.BluetoothCallQualityReport bluetoothCallQualityReport) throws android.os.RemoteException {
            android.telecom.CallDiagnosticService.this.handleBluetoothCallQualityReport(bluetoothCallQualityReport);
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void notifyCallDisconnected(java.lang.String str, android.telecom.DisconnectCause disconnectCause) throws android.os.RemoteException {
            android.telecom.CallDiagnosticService.this.handleCallDisconnected(str, disconnectCause);
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void callQualityChanged(java.lang.String str, android.telephony.CallQuality callQuality) throws android.os.RemoteException {
            android.telecom.CallDiagnosticService.this.handleCallQualityChanged(str, callQuality);
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        android.telecom.Log.i(this, "onBind!", new java.lang.Object[0]);
        return new android.telecom.CallDiagnosticService.CallDiagnosticServiceBinder();
    }

    public java.util.concurrent.Executor getExecutor() {
        return new android.os.HandlerExecutor(android.os.Handler.createAsync(getMainLooper()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetAdapter(com.android.internal.telecom.ICallDiagnosticServiceAdapter iCallDiagnosticServiceAdapter) {
        this.mAdapter = iCallDiagnosticServiceAdapter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCallAdded(android.telecom.ParcelableCall parcelableCall) {
        final java.lang.String id = parcelableCall.getId();
        android.telecom.Log.i(this, "handleCallAdded: callId=%s - added", id);
        final android.telecom.Call.Details createFromParcelableCall = android.telecom.Call.Details.createFromParcelableCall(parcelableCall);
        synchronized (this.mLock) {
            this.mCallByTelecomCallId.put(id, createFromParcelableCall);
        }
        getExecutor().execute(new java.lang.Runnable() { // from class: android.telecom.CallDiagnosticService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                android.telecom.CallDiagnosticService.this.lambda$handleCallAdded$0(createFromParcelableCall, id);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleCallAdded$0(android.telecom.Call.Details details, java.lang.String str) {
        android.telecom.CallDiagnostics onInitializeCallDiagnostics = onInitializeCallDiagnostics(details);
        if (onInitializeCallDiagnostics == null) {
            throw new java.lang.IllegalArgumentException("A valid DiagnosticCall instance was not provided.");
        }
        synchronized (this.mLock) {
            onInitializeCallDiagnostics.setListener(this.mDiagnosticCallListener);
            onInitializeCallDiagnostics.setCallId(str);
            this.mDiagnosticCallByTelecomCallId.put(str, onInitializeCallDiagnostics);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCallUpdated(android.telecom.ParcelableCall parcelableCall) {
        java.lang.String id = parcelableCall.getId();
        android.telecom.Log.i(this, "handleCallUpdated: callId=%s - updated", id);
        final android.telecom.Call.Details createFromParcelableCall = android.telecom.Call.Details.createFromParcelableCall(parcelableCall);
        synchronized (this.mLock) {
            final android.telecom.CallDiagnostics callDiagnostics = this.mDiagnosticCallByTelecomCallId.get(id);
            if (callDiagnostics == null) {
                return;
            }
            this.mCallByTelecomCallId.put(id, createFromParcelableCall);
            getExecutor().execute(new java.lang.Runnable() { // from class: android.telecom.CallDiagnosticService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telecom.CallDiagnostics.this.handleCallUpdated(createFromParcelableCall);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCallRemoved(java.lang.String str) {
        final android.telecom.CallDiagnostics callDiagnostics;
        android.telecom.Log.i(this, "handleCallRemoved: callId=%s - removed", str);
        synchronized (this.mLock) {
            if (this.mCallByTelecomCallId.containsKey(str)) {
                this.mCallByTelecomCallId.remove(str);
            }
            if (this.mDiagnosticCallByTelecomCallId.containsKey(str)) {
                callDiagnostics = this.mDiagnosticCallByTelecomCallId.remove(str);
            } else {
                callDiagnostics = null;
            }
        }
        if (callDiagnostics != null) {
            getExecutor().execute(new java.lang.Runnable() { // from class: android.telecom.CallDiagnosticService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.telecom.CallDiagnosticService.this.lambda$handleCallRemoved$2(callDiagnostics);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReceivedD2DMessage(java.lang.String str, final int i, final int i2) {
        final android.telecom.CallDiagnostics callDiagnostics;
        android.telecom.Log.i(this, "handleReceivedD2DMessage: callId=%s, msg=%d/%d", str, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        synchronized (this.mLock) {
            callDiagnostics = this.mDiagnosticCallByTelecomCallId.get(str);
        }
        if (callDiagnostics != null) {
            getExecutor().execute(new java.lang.Runnable() { // from class: android.telecom.CallDiagnosticService$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.telecom.CallDiagnostics.this.onReceiveDeviceToDeviceMessage(i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCallDisconnected(java.lang.String str, android.telecom.DisconnectCause disconnectCause) {
        android.telecom.CallDiagnostics callDiagnostics;
        java.lang.CharSequence onCallDisconnected;
        android.telecom.Log.i(this, "handleCallDisconnected: call=%s; cause=%s", str, disconnectCause);
        synchronized (this.mLock) {
            callDiagnostics = this.mDiagnosticCallByTelecomCallId.get(str);
        }
        if (disconnectCause.getImsReasonInfo() != null) {
            onCallDisconnected = callDiagnostics.onCallDisconnected(disconnectCause.getImsReasonInfo());
        } else {
            onCallDisconnected = callDiagnostics.onCallDisconnected(disconnectCause.getTelephonyDisconnectCause(), disconnectCause.getTelephonyPreciseDisconnectCause());
        }
        try {
            this.mAdapter.overrideDisconnectMessage(str, onCallDisconnected);
        } catch (android.os.RemoteException e) {
            android.telecom.Log.w(this, "handleCallDisconnected: call=%s; cause=%s; %s", str, disconnectCause, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBluetoothCallQualityReport(final android.telecom.BluetoothCallQualityReport bluetoothCallQualityReport) {
        android.telecom.Log.i(this, "handleBluetoothCallQualityReport; report=%s", bluetoothCallQualityReport);
        getExecutor().execute(new java.lang.Runnable() { // from class: android.telecom.CallDiagnosticService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.telecom.CallDiagnosticService.this.lambda$handleBluetoothCallQualityReport$4(bluetoothCallQualityReport);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCallQualityChanged(java.lang.String str, android.telephony.CallQuality callQuality) {
        android.telecom.CallDiagnostics callDiagnostics;
        android.telecom.Log.i(this, "handleCallQualityChanged; call=%s, cq=%s", str, callQuality);
        synchronized (this.mLock) {
            callDiagnostics = this.mDiagnosticCallByTelecomCallId.get(str);
        }
        if (callDiagnostics != null) {
            callDiagnostics.onCallQualityReceived(callQuality);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSendDeviceToDeviceMessage(android.telecom.CallDiagnostics callDiagnostics, int i, int i2) {
        java.lang.String callId = callDiagnostics.getCallId();
        try {
            this.mAdapter.sendDeviceToDeviceMessage(callId, i, i2);
            android.telecom.Log.i(this, "handleSendDeviceToDeviceMessage: call=%s; msg=%d/%d", callId, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        } catch (android.os.RemoteException e) {
            android.telecom.Log.w(this, "handleSendDeviceToDeviceMessage: call=%s; msg=%d/%d failed %s", callId, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayDiagnosticMessage(android.telecom.CallDiagnostics callDiagnostics, int i, java.lang.CharSequence charSequence) {
        java.lang.String callId = callDiagnostics.getCallId();
        try {
            this.mAdapter.displayDiagnosticMessage(callId, i, charSequence);
            android.telecom.Log.i(this, "handleDisplayDiagnosticMessage: call=%s; msg=%d/%s", callId, java.lang.Integer.valueOf(i), charSequence);
        } catch (android.os.RemoteException e) {
            android.telecom.Log.w(this, "handleDisplayDiagnosticMessage: call=%s; msg=%d/%s failed %s", callId, java.lang.Integer.valueOf(i), charSequence, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleClearDiagnosticMessage(android.telecom.CallDiagnostics callDiagnostics, int i) {
        java.lang.String callId = callDiagnostics.getCallId();
        try {
            this.mAdapter.clearDiagnosticMessage(callId, i);
            android.telecom.Log.i(this, "handleClearDiagnosticMessage: call=%s; msg=%d", callId, java.lang.Integer.valueOf(i));
        } catch (android.os.RemoteException e) {
            android.telecom.Log.w(this, "handleClearDiagnosticMessage: call=%s; msg=%d failed %s", callId, java.lang.Integer.valueOf(i), e);
        }
    }
}
