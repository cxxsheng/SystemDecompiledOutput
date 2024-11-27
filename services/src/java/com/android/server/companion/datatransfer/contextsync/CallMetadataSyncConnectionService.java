package com.android.server.companion.datatransfer.contextsync;

/* loaded from: classes.dex */
public class CallMetadataSyncConnectionService extends android.telecom.ConnectionService {
    private static final java.lang.String TAG = "CallMetadataSyncConnectionService";

    @com.android.internal.annotations.VisibleForTesting
    android.media.AudioManager mAudioManager;
    private com.android.server.companion.CompanionDeviceManagerServiceInternal mCdmsi;

    @com.android.internal.annotations.VisibleForTesting
    android.telecom.TelecomManager mTelecomManager;

    @com.android.internal.annotations.VisibleForTesting
    final java.util.Map<com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionIdentifier, com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection> mActiveConnections = new java.util.HashMap();

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback mCrossDeviceSyncControllerCallback = new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.AnonymousClass1();

    @com.android.internal.annotations.VisibleForTesting
    static abstract class CallMetadataSyncConnectionCallback {
        CallMetadataSyncConnectionCallback() {
        }

        abstract void sendCallAction(int i, java.lang.String str, int i2);
    }

    /* renamed from: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback {
        AnonymousClass1() {
        }

        @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
        void processContextSyncMessage(final int i, final com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData callMetadataSyncData) {
            com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionIdentifier callMetadataSyncConnectionIdentifier;
            for (com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.Call call : callMetadataSyncData.getCalls()) {
                com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection callMetadataSyncConnection = com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.this.mActiveConnections.get(new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionIdentifier(i, call.getId()));
                if (callMetadataSyncConnection != null) {
                    callMetadataSyncConnection.update(call);
                } else {
                    java.util.Iterator<java.util.Map.Entry<com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionIdentifier, com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection>> it = com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.this.mActiveConnections.entrySet().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            callMetadataSyncConnectionIdentifier = null;
                            break;
                        }
                        java.util.Map.Entry<com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionIdentifier, com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection> next = it.next();
                        if (next.getValue().getAssociationId() == i && !next.getValue().isIdFinalized() && call.getId().endsWith(next.getValue().getCallId())) {
                            callMetadataSyncConnectionIdentifier = next.getKey();
                            break;
                        }
                    }
                    if (callMetadataSyncConnectionIdentifier != null) {
                        com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection remove = com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.this.mActiveConnections.remove(callMetadataSyncConnectionIdentifier);
                        remove.update(call);
                        com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.this.mActiveConnections.put(new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionIdentifier(i, call.getId()), remove);
                    }
                }
            }
            com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.this.mActiveConnections.values().removeIf(new java.util.function.Predicate() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$processContextSyncMessage$0;
                    lambda$processContextSyncMessage$0 = com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.AnonymousClass1.lambda$processContextSyncMessage$0(i, callMetadataSyncData, (com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection) obj);
                    return lambda$processContextSyncMessage$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$processContextSyncMessage$0(int i, com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData callMetadataSyncData, com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection callMetadataSyncConnection) {
            if (callMetadataSyncConnection.isIdFinalized() && i == callMetadataSyncConnection.getAssociationId() && !callMetadataSyncData.hasCall(callMetadataSyncConnection.getCallId())) {
                callMetadataSyncConnection.setDisconnected(new android.telecom.DisconnectCause(3));
                return true;
            }
            return false;
        }

        @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
        void cleanUpCallIds(final java.util.Set<java.lang.String> set) {
            com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.this.mActiveConnections.values().removeIf(new java.util.function.Predicate() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$cleanUpCallIds$1;
                    lambda$cleanUpCallIds$1 = com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.AnonymousClass1.lambda$cleanUpCallIds$1(set, (com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection) obj);
                    return lambda$cleanUpCallIds$1;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$cleanUpCallIds$1(java.util.Set set, com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection callMetadataSyncConnection) {
            if (set.contains(callMetadataSyncConnection.getCallId())) {
                callMetadataSyncConnection.setDisconnected(new android.telecom.DisconnectCause(3));
                return true;
            }
            return false;
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mAudioManager = (android.media.AudioManager) getSystemService(android.media.AudioManager.class);
        this.mTelecomManager = (android.telecom.TelecomManager) getSystemService(android.telecom.TelecomManager.class);
        this.mCdmsi = (com.android.server.companion.CompanionDeviceManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.companion.CompanionDeviceManagerServiceInternal.class);
        this.mCdmsi.registerCallMetadataSyncCallback(this.mCrossDeviceSyncControllerCallback, 1);
    }

    @Override // android.telecom.ConnectionService
    public android.telecom.Connection onCreateIncomingConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        int i = connectionRequest.getExtras().getInt("com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID");
        com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.Call fromBundle = com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.Call.fromBundle(connectionRequest.getExtras().getBundle("com.android.server.companion.datatransfer.contextsync.extra.CALL"));
        fromBundle.setDirection(1);
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.CALL");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.CALL_FACILITATOR_ID");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID");
        com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection callMetadataSyncConnection = new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection(this.mTelecomManager, this.mAudioManager, i, fromBundle, new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionCallback() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.2
            @Override // com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionCallback
            void sendCallAction(int i2, java.lang.String str, int i3) {
                com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.this.mCdmsi.sendCrossDeviceSyncMessage(i2, com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.createCallControlMessage(str, i3));
            }
        });
        callMetadataSyncConnection.setConnectionProperties(16);
        callMetadataSyncConnection.setInitializing();
        return callMetadataSyncConnection;
    }

    @Override // android.telecom.ConnectionService
    public void onCreateIncomingConnectionFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        android.util.Slog.e(TAG, "onCreateOutgoingConnectionFailed for: " + (phoneAccountHandle != null ? phoneAccountHandle.getId() : "unknown PhoneAccount"));
    }

    @Override // android.telecom.ConnectionService
    public android.telecom.Connection onCreateOutgoingConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        java.lang.String shortClassName;
        java.lang.String packageName;
        if (phoneAccountHandle == null) {
            phoneAccountHandle = connectionRequest.getAccountHandle();
        }
        android.telecom.PhoneAccount phoneAccount = this.mTelecomManager.getPhoneAccount(phoneAccountHandle);
        com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.Call call = new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.Call();
        call.setId(connectionRequest.getExtras().getString(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.EXTRA_CALL_ID));
        call.setStatus(0);
        if (phoneAccount != null) {
            shortClassName = phoneAccount.getLabel().toString();
        } else {
            shortClassName = phoneAccountHandle.getComponentName().getShortClassName();
        }
        if (phoneAccount != null) {
            packageName = phoneAccount.getExtras().getString("com.android.server.companion.datatransfer.contextsync.extra.CALL_FACILITATOR_ID");
        } else {
            packageName = phoneAccountHandle.getComponentName().getPackageName();
        }
        call.setFacilitator(new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.CallFacilitator(shortClassName, packageName, phoneAccountHandle.getComponentName().flattenToString()));
        call.setDirection(2);
        call.setCallerId(connectionRequest.getAddress().getSchemeSpecificPart());
        int i = phoneAccount.getExtras().getInt("com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.CALL");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.CALL_FACILITATOR_ID");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID");
        com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection callMetadataSyncConnection = new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection(this.mTelecomManager, this.mAudioManager, i, call, new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionCallback() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.3
            @Override // com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionCallback
            void sendCallAction(int i2, java.lang.String str, int i3) {
                com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.this.mCdmsi.sendCrossDeviceSyncMessage(i2, com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.createCallControlMessage(str, i3));
            }
        });
        callMetadataSyncConnection.setCallerDisplayName(call.getCallerId(), 1);
        this.mCdmsi.addSelfOwnedCallId(call.getId());
        this.mCdmsi.sendCrossDeviceSyncMessage(i, com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.createCallCreateMessage(call.getId(), connectionRequest.getAddress().toString(), call.getFacilitator().getIdentifier()));
        callMetadataSyncConnection.setInitializing();
        return callMetadataSyncConnection;
    }

    @Override // android.telecom.ConnectionService
    public void onCreateOutgoingConnectionFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        android.util.Slog.e(TAG, "onCreateOutgoingConnectionFailed for: " + (phoneAccountHandle != null ? phoneAccountHandle.getId() : "unknown PhoneAccount"));
    }

    public void onCreateConnectionComplete(android.telecom.Connection connection) {
        if (connection instanceof com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection) {
            com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection callMetadataSyncConnection = (com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnection) connection;
            callMetadataSyncConnection.initialize();
            this.mActiveConnections.put(new com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionIdentifier(callMetadataSyncConnection.getAssociationId(), callMetadataSyncConnection.getCallId()), callMetadataSyncConnection);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class CallMetadataSyncConnectionIdentifier {
        private final int mAssociationId;
        private final java.lang.String mCallId;

        CallMetadataSyncConnectionIdentifier(int i, java.lang.String str) {
            this.mAssociationId = i;
            this.mCallId = str;
        }

        public int getAssociationId() {
            return this.mAssociationId;
        }

        public java.lang.String getCallId() {
            return this.mCallId;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mAssociationId), this.mCallId);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionIdentifier)) {
                return false;
            }
            com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionIdentifier callMetadataSyncConnectionIdentifier = (com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionIdentifier) obj;
            return callMetadataSyncConnectionIdentifier.getAssociationId() == this.mAssociationId && this.mCallId != null && this.mCallId.equals(callMetadataSyncConnectionIdentifier.getCallId());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class CallMetadataSyncConnection extends android.telecom.Connection {
        private final int mAssociationId;
        private final android.media.AudioManager mAudioManager;
        private final com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.Call mCall;
        private final com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionCallback mCallback;
        private boolean mIsIdFinalized;
        private final android.telecom.TelecomManager mTelecomManager;

        CallMetadataSyncConnection(android.telecom.TelecomManager telecomManager, android.media.AudioManager audioManager, int i, com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.Call call, com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionCallback callMetadataSyncConnectionCallback) {
            this.mTelecomManager = telecomManager;
            this.mAudioManager = audioManager;
            this.mAssociationId = i;
            this.mCall = call;
            this.mCallback = callMetadataSyncConnectionCallback;
        }

        public java.lang.String getCallId() {
            return this.mCall.getId();
        }

        public int getAssociationId() {
            return this.mAssociationId;
        }

        public boolean isIdFinalized() {
            return this.mIsIdFinalized;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initialize() {
            int i;
            int i2;
            int status = this.mCall.getStatus();
            if (status == 4) {
                this.mTelecomManager.silenceRinger();
            }
            int convertStatusToState = com.android.server.companion.datatransfer.contextsync.CrossDeviceCall.convertStatusToState(status);
            if (convertStatusToState == 2) {
                setRinging();
            } else if (convertStatusToState == 4) {
                setActive();
            } else if (convertStatusToState == 3) {
                setOnHold();
            } else if (convertStatusToState == 7) {
                setDisconnected(new android.telecom.DisconnectCause(3));
            } else if (convertStatusToState == 1) {
                setDialing();
            } else {
                setInitialized();
            }
            java.lang.String callerId = this.mCall.getCallerId();
            if (callerId != null) {
                setCallerDisplayName(callerId, 1);
                setAddress(android.net.Uri.fromParts("custom", this.mCall.getCallerId(), null), 1);
            }
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.EXTRA_CALL_ID, this.mCall.getId());
            putExtras(bundle);
            int connectionCapabilities = getConnectionCapabilities();
            if (this.mCall.hasControl(7)) {
                i = connectionCapabilities | 1;
            } else {
                i = connectionCapabilities & (-2);
            }
            if (this.mCall.hasControl(4)) {
                i2 = i | 64;
            } else {
                i2 = i & (-65);
            }
            this.mAudioManager.setMicrophoneMute(this.mCall.hasControl(5));
            if (i2 != getConnectionCapabilities()) {
                setConnectionCapabilities(i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void update(com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData.Call call) {
            int i;
            int i2;
            boolean z = true;
            if (!this.mIsIdFinalized) {
                this.mCall.setId(call.getId());
                this.mIsIdFinalized = true;
            }
            int status = call.getStatus();
            if (status == 4 && this.mCall.getStatus() != 4) {
                this.mTelecomManager.silenceRinger();
            }
            this.mCall.setStatus(status);
            int convertStatusToState = com.android.server.companion.datatransfer.contextsync.CrossDeviceCall.convertStatusToState(status);
            if (convertStatusToState != getState()) {
                if (convertStatusToState == 2) {
                    setRinging();
                } else if (convertStatusToState == 4) {
                    setActive();
                } else if (convertStatusToState == 3) {
                    setOnHold();
                } else if (convertStatusToState == 7) {
                    setDisconnected(new android.telecom.DisconnectCause(3));
                } else if (convertStatusToState == 1) {
                    setDialing();
                } else {
                    android.util.Slog.e(com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.TAG, "Could not update call to unknown state");
                }
            }
            int connectionCapabilities = getConnectionCapabilities();
            this.mCall.setControls(call.getControls());
            if (this.mCall.hasControl(7) || this.mCall.hasControl(8)) {
                i = connectionCapabilities | 1;
            } else {
                i = connectionCapabilities & (-2);
            }
            if (!this.mCall.hasControl(4) && !this.mCall.hasControl(5)) {
                z = false;
            }
            if (z) {
                i2 = i | 64;
            } else {
                i2 = i & (-65);
            }
            this.mAudioManager.setMicrophoneMute(this.mCall.hasControl(5));
            if (i2 != getConnectionCapabilities()) {
                setConnectionCapabilities(i2);
            }
        }

        @Override // android.telecom.Connection
        public void onAnswer(int i) {
            sendCallAction(1);
        }

        @Override // android.telecom.Connection
        public void onReject() {
            sendCallAction(2);
        }

        @Override // android.telecom.Connection
        public void onReject(int i) {
            onReject();
        }

        @Override // android.telecom.Connection
        public void onReject(java.lang.String str) {
            onReject();
        }

        @Override // android.telecom.Connection
        public void onSilence() {
            sendCallAction(3);
        }

        @Override // android.telecom.Connection
        public void onHold() {
            sendCallAction(7);
        }

        @Override // android.telecom.Connection
        public void onUnhold() {
            sendCallAction(8);
        }

        @Override // android.telecom.Connection
        public void onMuteStateChanged(boolean z) {
            sendCallAction(z ? 4 : 5);
        }

        @Override // android.telecom.Connection
        public void onDisconnect() {
            sendCallAction(6);
        }

        private void sendCallAction(int i) {
            this.mCallback.sendCallAction(this.mAssociationId, this.mCall.getId(), i);
        }
    }
}
