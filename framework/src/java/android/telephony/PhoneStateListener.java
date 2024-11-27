package android.telephony;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class PhoneStateListener {
    private static final boolean DBG = false;

    @java.lang.Deprecated
    public static final int LISTEN_ACTIVE_DATA_SUBSCRIPTION_ID_CHANGE = 4194304;

    @java.lang.Deprecated
    public static final int LISTEN_BARRING_INFO = Integer.MIN_VALUE;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int LISTEN_CALL_ATTRIBUTES_CHANGED = 67108864;

    @java.lang.Deprecated
    public static final int LISTEN_CALL_DISCONNECT_CAUSES = 33554432;

    @java.lang.Deprecated
    public static final int LISTEN_CALL_FORWARDING_INDICATOR = 8;

    @java.lang.Deprecated
    public static final int LISTEN_CALL_STATE = 32;

    @java.lang.Deprecated
    public static final int LISTEN_CARRIER_NETWORK_CHANGE = 65536;

    @java.lang.Deprecated
    public static final int LISTEN_CELL_INFO = 1024;

    @java.lang.Deprecated
    public static final int LISTEN_CELL_LOCATION = 16;

    @java.lang.Deprecated
    public static final int LISTEN_DATA_ACTIVATION_STATE = 262144;

    @java.lang.Deprecated
    public static final int LISTEN_DATA_ACTIVITY = 128;

    @java.lang.Deprecated
    public static final int LISTEN_DATA_CONNECTION_REAL_TIME_INFO = 8192;

    @java.lang.Deprecated
    public static final int LISTEN_DATA_CONNECTION_STATE = 64;

    @java.lang.Deprecated
    public static final int LISTEN_DISPLAY_INFO_CHANGED = 1048576;

    @java.lang.Deprecated
    public static final int LISTEN_EMERGENCY_NUMBER_LIST = 16777216;

    @java.lang.Deprecated
    public static final int LISTEN_IMS_CALL_DISCONNECT_CAUSES = 134217728;

    @java.lang.Deprecated
    public static final int LISTEN_MESSAGE_WAITING_INDICATOR = 4;
    public static final int LISTEN_NONE = 0;

    @java.lang.Deprecated
    public static final int LISTEN_OEM_HOOK_RAW_EVENT = 32768;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int LISTEN_OUTGOING_EMERGENCY_CALL = 268435456;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int LISTEN_OUTGOING_EMERGENCY_SMS = 536870912;

    @java.lang.Deprecated
    public static final int LISTEN_PHONE_CAPABILITY_CHANGE = 2097152;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int LISTEN_PRECISE_CALL_STATE = 2048;

    @java.lang.Deprecated
    public static final int LISTEN_PRECISE_DATA_CONNECTION_STATE = 4096;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int LISTEN_RADIO_POWER_STATE_CHANGED = 8388608;

    @java.lang.Deprecated
    public static final int LISTEN_REGISTRATION_FAILURE = 1073741824;

    @java.lang.Deprecated
    public static final int LISTEN_SERVICE_STATE = 1;

    @java.lang.Deprecated
    public static final int LISTEN_SIGNAL_STRENGTH = 2;

    @java.lang.Deprecated
    public static final int LISTEN_SIGNAL_STRENGTHS = 256;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int LISTEN_SRVCC_STATE_CHANGED = 16384;

    @java.lang.Deprecated
    public static final int LISTEN_USER_MOBILE_DATA_STATE = 524288;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int LISTEN_VOICE_ACTIVATION_STATE = 131072;
    private static final java.lang.String LOG_TAG = "PhoneStateListener";
    public final com.android.internal.telephony.IPhoneStateListener callback;
    protected java.lang.Integer mSubId;

    public PhoneStateListener() {
        this((java.lang.Integer) null, android.os.Looper.myLooper());
    }

    public PhoneStateListener(android.os.Looper looper) {
        this((java.lang.Integer) null, looper);
    }

    public PhoneStateListener(java.lang.Integer num) {
        this(num, android.os.Looper.myLooper());
        if (num != null && dalvik.system.VMRuntime.getRuntime().getTargetSdkVersion() >= 29) {
            throw new java.lang.IllegalArgumentException("PhoneStateListener with subId: " + num + " is not supported, use default constructor");
        }
    }

    public PhoneStateListener(java.lang.Integer num, android.os.Looper looper) {
        this(num, new android.os.HandlerExecutor(new android.os.Handler(looper)));
        if (num != null && dalvik.system.VMRuntime.getRuntime().getTargetSdkVersion() >= 29) {
            throw new java.lang.IllegalArgumentException("PhoneStateListener with subId: " + num + " is not supported, use default constructor");
        }
    }

    @java.lang.Deprecated
    public PhoneStateListener(java.util.concurrent.Executor executor) {
        this((java.lang.Integer) null, executor);
    }

    private PhoneStateListener(java.lang.Integer num, java.util.concurrent.Executor executor) {
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("PhoneStateListener Executor must be non-null");
        }
        this.mSubId = num;
        this.callback = new android.telephony.PhoneStateListener.IPhoneStateListenerStub(this, executor);
    }

    @java.lang.Deprecated
    public void onServiceStateChanged(android.telephony.ServiceState serviceState) {
    }

    @java.lang.Deprecated
    public void onSignalStrengthChanged(int i) {
    }

    @java.lang.Deprecated
    public void onMessageWaitingIndicatorChanged(boolean z) {
    }

    @java.lang.Deprecated
    public void onCallForwardingIndicatorChanged(boolean z) {
    }

    @java.lang.Deprecated
    public void onCellLocationChanged(android.telephony.CellLocation cellLocation) {
    }

    @java.lang.Deprecated
    public void onCallStateChanged(int i, java.lang.String str) {
    }

    @java.lang.Deprecated
    public void onDataConnectionStateChanged(int i) {
    }

    @java.lang.Deprecated
    public void onDataConnectionStateChanged(int i, int i2) {
    }

    @java.lang.Deprecated
    public void onDataActivity(int i) {
    }

    @java.lang.Deprecated
    public void onSignalStrengthsChanged(android.telephony.SignalStrength signalStrength) {
    }

    @java.lang.Deprecated
    public void onCellInfoChanged(java.util.List<android.telephony.CellInfo> list) {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void onPreciseCallStateChanged(android.telephony.PreciseCallState preciseCallState) {
    }

    @java.lang.Deprecated
    public void onCallDisconnectCauseChanged(int i, int i2) {
    }

    @java.lang.Deprecated
    public void onImsCallDisconnectCauseChanged(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
    }

    @java.lang.Deprecated
    public void onPreciseDataConnectionStateChanged(android.telephony.PreciseDataConnectionState preciseDataConnectionState) {
    }

    @java.lang.Deprecated
    public void onDataConnectionRealTimeInfoChanged(android.telephony.DataConnectionRealTimeInfo dataConnectionRealTimeInfo) {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void onSrvccStateChanged(int i) {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void onVoiceActivationStateChanged(int i) {
    }

    @java.lang.Deprecated
    public void onDataActivationStateChanged(int i) {
    }

    @java.lang.Deprecated
    public void onUserMobileDataStateChanged(boolean z) {
    }

    @java.lang.Deprecated
    public void onDisplayInfoChanged(android.telephony.TelephonyDisplayInfo telephonyDisplayInfo) {
    }

    @java.lang.Deprecated
    public void onEmergencyNumberListChanged(java.util.Map<java.lang.Integer, java.util.List<android.telephony.emergency.EmergencyNumber>> map) {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void onOutgoingEmergencyCall(android.telephony.emergency.EmergencyNumber emergencyNumber) {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void onOutgoingEmergencyCall(android.telephony.emergency.EmergencyNumber emergencyNumber, int i) {
        onOutgoingEmergencyCall(emergencyNumber);
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void onOutgoingEmergencySms(android.telephony.emergency.EmergencyNumber emergencyNumber) {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void onOutgoingEmergencySms(android.telephony.emergency.EmergencyNumber emergencyNumber, int i) {
        onOutgoingEmergencySms(emergencyNumber);
    }

    @java.lang.Deprecated
    public void onOemHookRawEvent(byte[] bArr) {
    }

    @java.lang.Deprecated
    public void onPhoneCapabilityChanged(android.telephony.PhoneCapability phoneCapability) {
    }

    @java.lang.Deprecated
    public void onActiveDataSubscriptionIdChanged(int i) {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void onCallAttributesChanged(android.telephony.CallAttributes callAttributes) {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void onRadioPowerStateChanged(int i) {
    }

    @java.lang.Deprecated
    public void onCarrierNetworkChange(boolean z) {
    }

    @java.lang.Deprecated
    public void onRegistrationFailed(android.telephony.CellIdentity cellIdentity, java.lang.String str, int i, int i2, int i3) {
    }

    @java.lang.Deprecated
    public void onBarringInfoChanged(android.telephony.BarringInfo barringInfo) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class IPhoneStateListenerStub extends com.android.internal.telephony.IPhoneStateListener.Stub {
        private java.util.concurrent.Executor mExecutor;
        private java.lang.ref.WeakReference<android.telephony.PhoneStateListener> mPhoneStateListenerWeakRef;

        IPhoneStateListenerStub(android.telephony.PhoneStateListener phoneStateListener, java.util.concurrent.Executor executor) {
            this.mPhoneStateListenerWeakRef = new java.lang.ref.WeakReference<>(phoneStateListener);
            this.mExecutor = executor;
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onServiceStateChanged(final android.telephony.ServiceState serviceState) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda62
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onServiceStateChanged$1(phoneStateListener, serviceState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceStateChanged$1(final android.telephony.PhoneStateListener phoneStateListener, final android.telephony.ServiceState serviceState) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onServiceStateChanged(serviceState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthChanged(final int i) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda17
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onSignalStrengthChanged$3(phoneStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSignalStrengthChanged$3(final android.telephony.PhoneStateListener phoneStateListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onSignalStrengthChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onMessageWaitingIndicatorChanged(final boolean z) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda19
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onMessageWaitingIndicatorChanged$5(phoneStateListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageWaitingIndicatorChanged$5(final android.telephony.PhoneStateListener phoneStateListener, final boolean z) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda37
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onMessageWaitingIndicatorChanged(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallForwardingIndicatorChanged(final boolean z) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda50
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onCallForwardingIndicatorChanged$7(phoneStateListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallForwardingIndicatorChanged$7(final android.telephony.PhoneStateListener phoneStateListener, final boolean z) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda41
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onCallForwardingIndicatorChanged(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellLocationChanged(android.telephony.CellIdentity cellIdentity) {
            final android.telephony.CellLocation empty = cellIdentity == null ? android.telephony.CellLocation.getEmpty() : cellIdentity.asCellLocation();
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda34
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onCellLocationChanged$9(phoneStateListener, empty);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCellLocationChanged$9(final android.telephony.PhoneStateListener phoneStateListener, final android.telephony.CellLocation cellLocation) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda53
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onCellLocationChanged(cellLocation);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onLegacyCallStateChanged(final int i, final java.lang.String str) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda21
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onLegacyCallStateChanged$11(phoneStateListener, i, str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLegacyCallStateChanged$11(final android.telephony.PhoneStateListener phoneStateListener, final int i, final java.lang.String str) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda43
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onCallStateChanged(i, str);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStateChanged(int i) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionStateChanged(final int i, final int i2) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            if (i == 4 && dalvik.system.VMRuntime.getRuntime().getTargetSdkVersion() < 30) {
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda58
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onDataConnectionStateChanged$13(phoneStateListener, i2);
                    }
                });
            } else {
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda59
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onDataConnectionStateChanged$15(phoneStateListener, i, i2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataConnectionStateChanged$13(final android.telephony.PhoneStateListener phoneStateListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.lambda$onDataConnectionStateChanged$12(android.telephony.PhoneStateListener.this, i);
                }
            });
        }

        static /* synthetic */ void lambda$onDataConnectionStateChanged$12(android.telephony.PhoneStateListener phoneStateListener, int i) {
            phoneStateListener.onDataConnectionStateChanged(2, i);
            phoneStateListener.onDataConnectionStateChanged(2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataConnectionStateChanged$15(final android.telephony.PhoneStateListener phoneStateListener, final int i, final int i2) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda61
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.lambda$onDataConnectionStateChanged$14(android.telephony.PhoneStateListener.this, i, i2);
                }
            });
        }

        static /* synthetic */ void lambda$onDataConnectionStateChanged$14(android.telephony.PhoneStateListener phoneStateListener, int i, int i2) {
            phoneStateListener.onDataConnectionStateChanged(i, i2);
            phoneStateListener.onDataConnectionStateChanged(i);
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivity(final int i) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda28
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onDataActivity$17(phoneStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataActivity$17(final android.telephony.PhoneStateListener phoneStateListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda57
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onDataActivity(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthsChanged(final android.telephony.SignalStrength signalStrength) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda31
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onSignalStrengthsChanged$19(phoneStateListener, signalStrength);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSignalStrengthsChanged$19(final android.telephony.PhoneStateListener phoneStateListener, final android.telephony.SignalStrength signalStrength) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda51
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onSignalStrengthsChanged(signalStrength);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellInfoChanged(final java.util.List<android.telephony.CellInfo> list) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda46
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onCellInfoChanged$21(phoneStateListener, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCellInfoChanged$21(final android.telephony.PhoneStateListener phoneStateListener, final java.util.List list) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda39
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onCellInfoChanged(list);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseCallStateChanged(final android.telephony.PreciseCallState preciseCallState) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda45
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onPreciseCallStateChanged$23(phoneStateListener, preciseCallState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreciseCallStateChanged$23(final android.telephony.PhoneStateListener phoneStateListener, final android.telephony.PreciseCallState preciseCallState) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onPreciseCallStateChanged(preciseCallState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallDisconnectCauseChanged(final int i, final int i2) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda26
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onCallDisconnectCauseChanged$25(phoneStateListener, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallDisconnectCauseChanged$25(final android.telephony.PhoneStateListener phoneStateListener, final int i, final int i2) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onCallDisconnectCauseChanged(i, i2);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseDataConnectionStateChanged(final android.telephony.PreciseDataConnectionState preciseDataConnectionState) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda22
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onPreciseDataConnectionStateChanged$27(phoneStateListener, preciseDataConnectionState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreciseDataConnectionStateChanged$27(final android.telephony.PhoneStateListener phoneStateListener, final android.telephony.PreciseDataConnectionState preciseDataConnectionState) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda27
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onPreciseDataConnectionStateChanged(preciseDataConnectionState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionRealTimeInfoChanged(final android.telephony.DataConnectionRealTimeInfo dataConnectionRealTimeInfo) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda7
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onDataConnectionRealTimeInfoChanged$29(phoneStateListener, dataConnectionRealTimeInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataConnectionRealTimeInfoChanged$29(final android.telephony.PhoneStateListener phoneStateListener, final android.telephony.DataConnectionRealTimeInfo dataConnectionRealTimeInfo) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onDataConnectionRealTimeInfoChanged(dataConnectionRealTimeInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSrvccStateChanged(final int i) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda35
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onSrvccStateChanged$31(phoneStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSrvccStateChanged$31(final android.telephony.PhoneStateListener phoneStateListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onSrvccStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onVoiceActivationStateChanged(final int i) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda10
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onVoiceActivationStateChanged$33(phoneStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVoiceActivationStateChanged$33(final android.telephony.PhoneStateListener phoneStateListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda56
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onVoiceActivationStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivationStateChanged(final int i) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onDataActivationStateChanged$35(phoneStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataActivationStateChanged$35(final android.telephony.PhoneStateListener phoneStateListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda47
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onDataActivationStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onUserMobileDataStateChanged(final boolean z) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda18
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onUserMobileDataStateChanged$37(phoneStateListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserMobileDataStateChanged$37(final android.telephony.PhoneStateListener phoneStateListener, final boolean z) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onUserMobileDataStateChanged(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDisplayInfoChanged(final android.telephony.TelephonyDisplayInfo telephonyDisplayInfo) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onDisplayInfoChanged$39(phoneStateListener, telephonyDisplayInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayInfoChanged$39(final android.telephony.PhoneStateListener phoneStateListener, final android.telephony.TelephonyDisplayInfo telephonyDisplayInfo) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onDisplayInfoChanged(telephonyDisplayInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOemHookRawEvent(final byte[] bArr) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda42
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onOemHookRawEvent$41(phoneStateListener, bArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOemHookRawEvent$41(final android.telephony.PhoneStateListener phoneStateListener, final byte[] bArr) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda36
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onOemHookRawEvent(bArr);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierNetworkChange(final boolean z) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda9
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onCarrierNetworkChange$43(phoneStateListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierNetworkChange$43(final android.telephony.PhoneStateListener phoneStateListener, final boolean z) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda55
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onCarrierNetworkChange(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onEmergencyNumberListChanged(final java.util.Map map) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda48
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onEmergencyNumberListChanged$45(phoneStateListener, map);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEmergencyNumberListChanged$45(final android.telephony.PhoneStateListener phoneStateListener, final java.util.Map map) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onEmergencyNumberListChanged(map);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOutgoingEmergencyCall(final android.telephony.emergency.EmergencyNumber emergencyNumber, final int i) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda54
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onOutgoingEmergencyCall$47(phoneStateListener, emergencyNumber, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOutgoingEmergencyCall$47(final android.telephony.PhoneStateListener phoneStateListener, final android.telephony.emergency.EmergencyNumber emergencyNumber, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda40
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onOutgoingEmergencyCall(emergencyNumber, i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOutgoingEmergencySms(final android.telephony.emergency.EmergencyNumber emergencyNumber, final int i) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda13
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onOutgoingEmergencySms$49(phoneStateListener, emergencyNumber, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOutgoingEmergencySms$49(final android.telephony.PhoneStateListener phoneStateListener, final android.telephony.emergency.EmergencyNumber emergencyNumber, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onOutgoingEmergencySms(emergencyNumber, i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhoneCapabilityChanged(final android.telephony.PhoneCapability phoneCapability) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda63
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onPhoneCapabilityChanged$51(phoneStateListener, phoneCapability);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPhoneCapabilityChanged$51(final android.telephony.PhoneStateListener phoneStateListener, final android.telephony.PhoneCapability phoneCapability) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda38
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onPhoneCapabilityChanged(phoneCapability);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRadioPowerStateChanged(final int i) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda52
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onRadioPowerStateChanged$53(phoneStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRadioPowerStateChanged$53(final android.telephony.PhoneStateListener phoneStateListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda49
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onRadioPowerStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStatesChanged(java.util.List<android.telephony.CallState> list) {
            final android.telephony.CallAttributes callAttributes;
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null || list == null) {
                return;
            }
            if (list.isEmpty()) {
                callAttributes = new android.telephony.CallAttributes(new android.telephony.PreciseCallState(0, 0, 0, -1, -1), 0, new android.telephony.CallQuality());
            } else {
                int i = 0;
                int i2 = 0;
                int i3 = 0;
                for (android.telephony.CallState callState : list) {
                    switch (callState.getCallClassification()) {
                        case 0:
                            i = callState.getCallState();
                            break;
                        case 1:
                            i2 = callState.getCallState();
                            break;
                        case 2:
                            i3 = callState.getCallState();
                            break;
                    }
                }
                callAttributes = new android.telephony.CallAttributes(new android.telephony.PreciseCallState(i, i2, i3, -1, -1), list.get(0).getNetworkType(), list.get(0).getCallQuality());
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda29
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onCallStatesChanged$55(phoneStateListener, callAttributes);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallStatesChanged$55(final android.telephony.PhoneStateListener phoneStateListener, final android.telephony.CallAttributes callAttributes) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onCallAttributesChanged(callAttributes);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onActiveDataSubIdChanged(final int i) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda32
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onActiveDataSubIdChanged$57(phoneStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onActiveDataSubIdChanged$57(final android.telephony.PhoneStateListener phoneStateListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onActiveDataSubscriptionIdChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onImsCallDisconnectCauseChanged(final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onImsCallDisconnectCauseChanged$59(phoneStateListener, imsReasonInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onImsCallDisconnectCauseChanged$59(final android.telephony.PhoneStateListener phoneStateListener, final android.telephony.ims.ImsReasonInfo imsReasonInfo) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda33
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onImsCallDisconnectCauseChanged(imsReasonInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRegistrationFailed(final android.telephony.CellIdentity cellIdentity, final java.lang.String str, final int i, final int i2, final int i3) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda60
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onRegistrationFailed$61(phoneStateListener, cellIdentity, str, i, i2, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRegistrationFailed$61(final android.telephony.PhoneStateListener phoneStateListener, final android.telephony.CellIdentity cellIdentity, final java.lang.String str, final int i, final int i2, final int i3) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onRegistrationFailed(cellIdentity, str, i, i2, i3);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onBarringInfoChanged(final android.telephony.BarringInfo barringInfo) {
            final android.telephony.PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.PhoneStateListener.IPhoneStateListenerStub.this.lambda$onBarringInfoChanged$63(phoneStateListener, barringInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBarringInfoChanged$63(final android.telephony.PhoneStateListener phoneStateListener, final android.telephony.BarringInfo barringInfo) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda44
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.PhoneStateListener.this.onBarringInfoChanged(barringInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhysicalChannelConfigChanged(java.util.List<android.telephony.PhysicalChannelConfig> list) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataEnabledChanged(boolean z, int i) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onAllowedNetworkTypesChanged(int i, long j) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onLinkCapacityEstimateChanged(java.util.List<android.telephony.LinkCapacityEstimate> list) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onMediaQualityStatusChanged(android.telephony.ims.MediaQualityStatus mediaQualityStatus) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onCallBackModeStarted(int i) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onCallBackModeStopped(int i, int i2) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onSimultaneousCallingStateChanged(int[] iArr) {
        }
    }

    private void log(java.lang.String str) {
        android.telephony.Rlog.d(LOG_TAG, str);
    }
}
