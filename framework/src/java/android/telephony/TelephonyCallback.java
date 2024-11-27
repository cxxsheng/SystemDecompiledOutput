package android.telephony;

/* loaded from: classes3.dex */
public class TelephonyCallback {
    public static final int DEFAULT_PER_PID_REGISTRATION_LIMIT = 50;

    @android.annotation.SystemApi
    public static final int EVENT_ACTIVE_DATA_SUBSCRIPTION_ID_CHANGED = 23;

    @android.annotation.SystemApi
    public static final int EVENT_ALLOWED_NETWORK_TYPE_LIST_CHANGED = 35;

    @android.annotation.SystemApi
    public static final int EVENT_ALWAYS_REPORTED_SIGNAL_STRENGTH_CHANGED = 10;

    @android.annotation.SystemApi
    public static final int EVENT_BARRING_INFO_CHANGED = 32;

    @android.annotation.SystemApi
    public static final int EVENT_CALL_ATTRIBUTES_CHANGED = 27;

    @android.annotation.SystemApi
    public static final int EVENT_CALL_DISCONNECT_CAUSE_CHANGED = 26;

    @android.annotation.SystemApi
    public static final int EVENT_CALL_FORWARDING_INDICATOR_CHANGED = 4;

    @android.annotation.SystemApi
    public static final int EVENT_CALL_STATE_CHANGED = 6;

    @android.annotation.SystemApi
    public static final int EVENT_CARRIER_NETWORK_CHANGED = 17;

    @android.annotation.SystemApi
    public static final int EVENT_CELL_INFO_CHANGED = 11;

    @android.annotation.SystemApi
    public static final int EVENT_CELL_LOCATION_CHANGED = 5;

    @android.annotation.SystemApi
    public static final int EVENT_DATA_ACTIVATION_STATE_CHANGED = 19;

    @android.annotation.SystemApi
    public static final int EVENT_DATA_ACTIVITY_CHANGED = 8;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int EVENT_DATA_CONNECTION_REAL_TIME_INFO_CHANGED = 14;

    @android.annotation.SystemApi
    public static final int EVENT_DATA_CONNECTION_STATE_CHANGED = 7;

    @android.annotation.SystemApi
    public static final int EVENT_DATA_ENABLED_CHANGED = 34;

    @android.annotation.SystemApi
    public static final int EVENT_DISPLAY_INFO_CHANGED = 21;
    public static final int EVENT_EMERGENCY_CALLBACK_MODE_CHANGED = 40;

    @android.annotation.SystemApi
    public static final int EVENT_EMERGENCY_NUMBER_LIST_CHANGED = 25;

    @android.annotation.SystemApi
    public static final int EVENT_IMS_CALL_DISCONNECT_CAUSE_CHANGED = 28;

    @android.annotation.SystemApi
    public static final int EVENT_LEGACY_CALL_STATE_CHANGED = 36;

    @android.annotation.SystemApi
    public static final int EVENT_LINK_CAPACITY_ESTIMATE_CHANGED = 37;

    @android.annotation.SystemApi
    public static final int EVENT_MEDIA_QUALITY_STATUS_CHANGED = 39;

    @android.annotation.SystemApi
    public static final int EVENT_MESSAGE_WAITING_INDICATOR_CHANGED = 3;

    @android.annotation.SystemApi
    public static final int EVENT_OEM_HOOK_RAW = 15;

    @android.annotation.SystemApi
    public static final int EVENT_OUTGOING_EMERGENCY_CALL = 29;

    @android.annotation.SystemApi
    public static final int EVENT_OUTGOING_EMERGENCY_SMS = 30;

    @android.annotation.SystemApi
    public static final int EVENT_PHONE_CAPABILITY_CHANGED = 22;

    @android.annotation.SystemApi
    public static final int EVENT_PHYSICAL_CHANNEL_CONFIG_CHANGED = 33;

    @android.annotation.SystemApi
    public static final int EVENT_PRECISE_CALL_STATE_CHANGED = 12;

    @android.annotation.SystemApi
    public static final int EVENT_PRECISE_DATA_CONNECTION_STATE_CHANGED = 13;

    @android.annotation.SystemApi
    public static final int EVENT_RADIO_POWER_STATE_CHANGED = 24;

    @android.annotation.SystemApi
    public static final int EVENT_REGISTRATION_FAILURE = 31;

    @android.annotation.SystemApi
    public static final int EVENT_SERVICE_STATE_CHANGED = 1;

    @android.annotation.SystemApi
    public static final int EVENT_SIGNAL_STRENGTHS_CHANGED = 9;

    @android.annotation.SystemApi
    public static final int EVENT_SIGNAL_STRENGTH_CHANGED = 2;

    @android.annotation.SystemApi
    public static final int EVENT_SIMULTANEOUS_CELLULAR_CALLING_SUBSCRIPTIONS_CHANGED = 41;

    @android.annotation.SystemApi
    public static final int EVENT_SRVCC_STATE_CHANGED = 16;
    public static final int EVENT_TRIGGER_NOTIFY_ANBR = 38;

    @android.annotation.SystemApi
    public static final int EVENT_USER_MOBILE_DATA_STATE_CHANGED = 20;

    @android.annotation.SystemApi
    public static final int EVENT_VOICE_ACTIVATION_STATE_CHANGED = 18;
    public static final java.lang.String FLAG_PER_PID_REGISTRATION_LIMIT = "phone_state_listener_per_pid_registration_limit";
    private static final java.lang.String LOG_TAG = "TelephonyCallback";
    public static final long PHONE_STATE_LISTENER_LIMIT_CHANGE_ID = 150880553;
    public com.android.internal.telephony.IPhoneStateListener callback;

    public interface ActiveDataSubscriptionIdListener {
        void onActiveDataSubscriptionIdChanged(int i);
    }

    @android.annotation.SystemApi
    public interface AllowedNetworkTypesListener {
        void onAllowedNetworkTypesChanged(int i, long j);
    }

    public interface BarringInfoListener {
        void onBarringInfoChanged(android.telephony.BarringInfo barringInfo);
    }

    public interface CallDisconnectCauseListener {
        void onCallDisconnectCauseChanged(int i, int i2);
    }

    public interface CallForwardingIndicatorListener {
        void onCallForwardingIndicatorChanged(boolean z);
    }

    public interface CallStateListener {
        void onCallStateChanged(int i);
    }

    public interface CarrierNetworkListener {
        void onCarrierNetworkChange(boolean z);
    }

    public interface CellInfoListener {
        void onCellInfoChanged(java.util.List<android.telephony.CellInfo> list);
    }

    public interface CellLocationListener {
        void onCellLocationChanged(android.telephony.CellLocation cellLocation);
    }

    public interface DataActivationStateListener {
        void onDataActivationStateChanged(int i);
    }

    public interface DataActivityListener {
        void onDataActivity(int i);
    }

    public interface DataConnectionStateListener {
        void onDataConnectionStateChanged(int i, int i2);
    }

    @android.annotation.SystemApi
    public interface DataEnabledListener {
        void onDataEnabledChanged(boolean z, int i);
    }

    public interface DisplayInfoListener {
        void onDisplayInfoChanged(android.telephony.TelephonyDisplayInfo telephonyDisplayInfo);
    }

    public interface EmergencyCallbackModeListener {
        void onCallBackModeStarted(int i);

        void onCallBackModeStopped(int i, int i2);
    }

    public interface EmergencyNumberListListener {
        void onEmergencyNumberListChanged(java.util.Map<java.lang.Integer, java.util.List<android.telephony.emergency.EmergencyNumber>> map);
    }

    public interface ImsCallDisconnectCauseListener {
        void onImsCallDisconnectCauseChanged(android.telephony.ims.ImsReasonInfo imsReasonInfo);
    }

    @android.annotation.SystemApi
    public interface LinkCapacityEstimateChangedListener {
        void onLinkCapacityEstimateChanged(java.util.List<android.telephony.LinkCapacityEstimate> list);
    }

    @android.annotation.SystemApi
    public interface MediaQualityStatusChangedListener {
        void onMediaQualityStatusChanged(android.telephony.ims.MediaQualityStatus mediaQualityStatus);
    }

    public interface MessageWaitingIndicatorListener {
        void onMessageWaitingIndicatorChanged(boolean z);
    }

    @android.annotation.SystemApi
    public interface OutgoingEmergencyCallListener {
        void onOutgoingEmergencyCall(android.telephony.emergency.EmergencyNumber emergencyNumber, int i);
    }

    @android.annotation.SystemApi
    public interface OutgoingEmergencySmsListener {
        void onOutgoingEmergencySms(android.telephony.emergency.EmergencyNumber emergencyNumber, int i);
    }

    @android.annotation.SystemApi
    public interface PhoneCapabilityListener {
        void onPhoneCapabilityChanged(android.telephony.PhoneCapability phoneCapability);
    }

    public interface PhysicalChannelConfigListener {
        void onPhysicalChannelConfigChanged(java.util.List<android.telephony.PhysicalChannelConfig> list);
    }

    @android.annotation.SystemApi
    public interface PreciseCallStateListener {
        void onPreciseCallStateChanged(android.telephony.PreciseCallState preciseCallState);
    }

    public interface PreciseDataConnectionStateListener {
        void onPreciseDataConnectionStateChanged(android.telephony.PreciseDataConnectionState preciseDataConnectionState);
    }

    @android.annotation.SystemApi
    public interface RadioPowerStateListener {
        void onRadioPowerStateChanged(int i);
    }

    public interface RegistrationFailedListener {
        void onRegistrationFailed(android.telephony.CellIdentity cellIdentity, java.lang.String str, int i, int i2, int i3);
    }

    public interface ServiceStateListener {
        void onServiceStateChanged(android.telephony.ServiceState serviceState);
    }

    public interface SignalStrengthsListener {
        void onSignalStrengthsChanged(android.telephony.SignalStrength signalStrength);
    }

    @android.annotation.SystemApi
    public interface SimultaneousCellularCallingSupportListener {
        void onSimultaneousCellularCallingSubscriptionsChanged(java.util.Set<java.lang.Integer> set);
    }

    @android.annotation.SystemApi
    public interface SrvccStateListener {
        void onSrvccStateChanged(int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TelephonyEvent {
    }

    public interface UserMobileDataStateListener {
        void onUserMobileDataStateChanged(boolean z);
    }

    @android.annotation.SystemApi
    public interface VoiceActivationStateListener {
        void onVoiceActivationStateChanged(int i);
    }

    public void init(java.util.concurrent.Executor executor) {
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("TelephonyCallback Executor must be non-null");
        }
        this.callback = new android.telephony.TelephonyCallback.IPhoneStateListenerStub(this, executor);
    }

    @android.annotation.SystemApi
    public interface CallAttributesListener {
        @java.lang.Deprecated
        default void onCallAttributesChanged(android.telephony.CallAttributes callAttributes) {
            android.util.Log.w(android.telephony.TelephonyCallback.LOG_TAG, "onCallAttributesChanged(List<CallState>) should be overridden.");
        }

        default void onCallStatesChanged(java.util.List<android.telephony.CallState> list) {
            if (list.size() > 0) {
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
                onCallAttributesChanged(new android.telephony.CallAttributes(new android.telephony.PreciseCallState(i, i2, i3, -1, -1), list.get(0).getNetworkType(), list.get(0).getCallQuality()));
                return;
            }
            onCallAttributesChanged(new android.telephony.CallAttributes(new android.telephony.PreciseCallState(0, 0, 0, -1, -1), 0, new android.telephony.CallQuality()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class IPhoneStateListenerStub extends com.android.internal.telephony.IPhoneStateListener.Stub {
        private java.util.concurrent.Executor mExecutor;
        private java.lang.ref.WeakReference<android.telephony.TelephonyCallback> mTelephonyCallbackWeakRef;

        IPhoneStateListenerStub(android.telephony.TelephonyCallback telephonyCallback, java.util.concurrent.Executor executor) {
            this.mTelephonyCallbackWeakRef = new java.lang.ref.WeakReference<>(telephonyCallback);
            this.mExecutor = executor;
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onServiceStateChanged(final android.telephony.ServiceState serviceState) {
            final android.telephony.TelephonyCallback.ServiceStateListener serviceStateListener = (android.telephony.TelephonyCallback.ServiceStateListener) this.mTelephonyCallbackWeakRef.get();
            if (serviceStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda48
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onServiceStateChanged$1(serviceStateListener, serviceState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceStateChanged$1(final android.telephony.TelephonyCallback.ServiceStateListener serviceStateListener, final android.telephony.ServiceState serviceState) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda67
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.ServiceStateListener.this.onServiceStateChanged(serviceState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthChanged(int i) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onMessageWaitingIndicatorChanged(final boolean z) {
            final android.telephony.TelephonyCallback.MessageWaitingIndicatorListener messageWaitingIndicatorListener = (android.telephony.TelephonyCallback.MessageWaitingIndicatorListener) this.mTelephonyCallbackWeakRef.get();
            if (messageWaitingIndicatorListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda17
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onMessageWaitingIndicatorChanged$3(messageWaitingIndicatorListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageWaitingIndicatorChanged$3(final android.telephony.TelephonyCallback.MessageWaitingIndicatorListener messageWaitingIndicatorListener, final boolean z) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.MessageWaitingIndicatorListener.this.onMessageWaitingIndicatorChanged(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallForwardingIndicatorChanged(final boolean z) {
            final android.telephony.TelephonyCallback.CallForwardingIndicatorListener callForwardingIndicatorListener = (android.telephony.TelephonyCallback.CallForwardingIndicatorListener) this.mTelephonyCallbackWeakRef.get();
            if (callForwardingIndicatorListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda8
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallForwardingIndicatorChanged$5(callForwardingIndicatorListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallForwardingIndicatorChanged$5(final android.telephony.TelephonyCallback.CallForwardingIndicatorListener callForwardingIndicatorListener, final boolean z) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.CallForwardingIndicatorListener.this.onCallForwardingIndicatorChanged(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellLocationChanged(android.telephony.CellIdentity cellIdentity) {
            final android.telephony.CellLocation empty = cellIdentity == null ? android.telephony.CellLocation.getEmpty() : cellIdentity.asCellLocation();
            final android.telephony.TelephonyCallback.CellLocationListener cellLocationListener = (android.telephony.TelephonyCallback.CellLocationListener) this.mTelephonyCallbackWeakRef.get();
            if (cellLocationListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda52
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCellLocationChanged$7(cellLocationListener, empty);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCellLocationChanged$7(final android.telephony.TelephonyCallback.CellLocationListener cellLocationListener, final android.telephony.CellLocation cellLocation) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.CellLocationListener.this.onCellLocationChanged(cellLocation);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onLegacyCallStateChanged(int i, java.lang.String str) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStateChanged(final int i) {
            final android.telephony.TelephonyCallback.CallStateListener callStateListener = (android.telephony.TelephonyCallback.CallStateListener) this.mTelephonyCallbackWeakRef.get();
            if (callStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda66
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallStateChanged$9(callStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallStateChanged$9(final android.telephony.TelephonyCallback.CallStateListener callStateListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda47
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.CallStateListener.this.onCallStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionStateChanged(final int i, final int i2) {
            final android.telephony.TelephonyCallback.DataConnectionStateListener dataConnectionStateListener = (android.telephony.TelephonyCallback.DataConnectionStateListener) this.mTelephonyCallbackWeakRef.get();
            if (dataConnectionStateListener == null) {
                return;
            }
            if (i == 4 && dalvik.system.VMRuntime.getRuntime().getTargetSdkVersion() < 30) {
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda33
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataConnectionStateChanged$11(dataConnectionStateListener, i2);
                    }
                });
            } else {
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda34
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataConnectionStateChanged$13(dataConnectionStateListener, i, i2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataConnectionStateChanged$11(final android.telephony.TelephonyCallback.DataConnectionStateListener dataConnectionStateListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.DataConnectionStateListener.this.onDataConnectionStateChanged(2, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataConnectionStateChanged$13(final android.telephony.TelephonyCallback.DataConnectionStateListener dataConnectionStateListener, final int i, final int i2) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.DataConnectionStateListener.this.onDataConnectionStateChanged(i, i2);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivity(final int i) {
            final android.telephony.TelephonyCallback.DataActivityListener dataActivityListener = (android.telephony.TelephonyCallback.DataActivityListener) this.mTelephonyCallbackWeakRef.get();
            if (dataActivityListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda15
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataActivity$15(dataActivityListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataActivity$15(final android.telephony.TelephonyCallback.DataActivityListener dataActivityListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.DataActivityListener.this.onDataActivity(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthsChanged(final android.telephony.SignalStrength signalStrength) {
            final android.telephony.TelephonyCallback.SignalStrengthsListener signalStrengthsListener = (android.telephony.TelephonyCallback.SignalStrengthsListener) this.mTelephonyCallbackWeakRef.get();
            if (signalStrengthsListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda31
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onSignalStrengthsChanged$17(signalStrengthsListener, signalStrength);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSignalStrengthsChanged$17(final android.telephony.TelephonyCallback.SignalStrengthsListener signalStrengthsListener, final android.telephony.SignalStrength signalStrength) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda43
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.SignalStrengthsListener.this.onSignalStrengthsChanged(signalStrength);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellInfoChanged(final java.util.List<android.telephony.CellInfo> list) {
            final android.telephony.TelephonyCallback.CellInfoListener cellInfoListener = (android.telephony.TelephonyCallback.CellInfoListener) this.mTelephonyCallbackWeakRef.get();
            if (cellInfoListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda55
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCellInfoChanged$19(cellInfoListener, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCellInfoChanged$19(final android.telephony.TelephonyCallback.CellInfoListener cellInfoListener, final java.util.List list) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.CellInfoListener.this.onCellInfoChanged(list);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseCallStateChanged(final android.telephony.PreciseCallState preciseCallState) {
            final android.telephony.TelephonyCallback.PreciseCallStateListener preciseCallStateListener = (android.telephony.TelephonyCallback.PreciseCallStateListener) this.mTelephonyCallbackWeakRef.get();
            if (preciseCallStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda14
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onPreciseCallStateChanged$21(preciseCallStateListener, preciseCallState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreciseCallStateChanged$21(final android.telephony.TelephonyCallback.PreciseCallStateListener preciseCallStateListener, final android.telephony.PreciseCallState preciseCallState) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda54
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.PreciseCallStateListener.this.onPreciseCallStateChanged(preciseCallState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallDisconnectCauseChanged(final int i, final int i2) {
            final android.telephony.TelephonyCallback.CallDisconnectCauseListener callDisconnectCauseListener = (android.telephony.TelephonyCallback.CallDisconnectCauseListener) this.mTelephonyCallbackWeakRef.get();
            if (callDisconnectCauseListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallDisconnectCauseChanged$23(callDisconnectCauseListener, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallDisconnectCauseChanged$23(final android.telephony.TelephonyCallback.CallDisconnectCauseListener callDisconnectCauseListener, final int i, final int i2) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda35
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.CallDisconnectCauseListener.this.onCallDisconnectCauseChanged(i, i2);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseDataConnectionStateChanged(final android.telephony.PreciseDataConnectionState preciseDataConnectionState) {
            final android.telephony.TelephonyCallback.PreciseDataConnectionStateListener preciseDataConnectionStateListener = (android.telephony.TelephonyCallback.PreciseDataConnectionStateListener) this.mTelephonyCallbackWeakRef.get();
            if (preciseDataConnectionStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda51
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onPreciseDataConnectionStateChanged$25(preciseDataConnectionStateListener, preciseDataConnectionState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreciseDataConnectionStateChanged$25(final android.telephony.TelephonyCallback.PreciseDataConnectionStateListener preciseDataConnectionStateListener, final android.telephony.PreciseDataConnectionState preciseDataConnectionState) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.PreciseDataConnectionStateListener.this.onPreciseDataConnectionStateChanged(preciseDataConnectionState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionRealTimeInfoChanged(android.telephony.DataConnectionRealTimeInfo dataConnectionRealTimeInfo) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSrvccStateChanged(final int i) {
            final android.telephony.TelephonyCallback.SrvccStateListener srvccStateListener = (android.telephony.TelephonyCallback.SrvccStateListener) this.mTelephonyCallbackWeakRef.get();
            if (srvccStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda59
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onSrvccStateChanged$27(srvccStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSrvccStateChanged$27(final android.telephony.TelephonyCallback.SrvccStateListener srvccStateListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda56
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.SrvccStateListener.this.onSrvccStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onVoiceActivationStateChanged(final int i) {
            final android.telephony.TelephonyCallback.VoiceActivationStateListener voiceActivationStateListener = (android.telephony.TelephonyCallback.VoiceActivationStateListener) this.mTelephonyCallbackWeakRef.get();
            if (voiceActivationStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda11
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onVoiceActivationStateChanged$29(voiceActivationStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVoiceActivationStateChanged$29(final android.telephony.TelephonyCallback.VoiceActivationStateListener voiceActivationStateListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.VoiceActivationStateListener.this.onVoiceActivationStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivationStateChanged(final int i) {
            final android.telephony.TelephonyCallback.DataActivationStateListener dataActivationStateListener = (android.telephony.TelephonyCallback.DataActivationStateListener) this.mTelephonyCallbackWeakRef.get();
            if (dataActivationStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda46
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataActivationStateChanged$31(dataActivationStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataActivationStateChanged$31(final android.telephony.TelephonyCallback.DataActivationStateListener dataActivationStateListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda72
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.DataActivationStateListener.this.onDataActivationStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onUserMobileDataStateChanged(final boolean z) {
            final android.telephony.TelephonyCallback.UserMobileDataStateListener userMobileDataStateListener = (android.telephony.TelephonyCallback.UserMobileDataStateListener) this.mTelephonyCallbackWeakRef.get();
            if (userMobileDataStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda12
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onUserMobileDataStateChanged$33(userMobileDataStateListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserMobileDataStateChanged$33(final android.telephony.TelephonyCallback.UserMobileDataStateListener userMobileDataStateListener, final boolean z) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.UserMobileDataStateListener.this.onUserMobileDataStateChanged(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDisplayInfoChanged(final android.telephony.TelephonyDisplayInfo telephonyDisplayInfo) {
            final android.telephony.TelephonyCallback.DisplayInfoListener displayInfoListener = (android.telephony.TelephonyCallback.DisplayInfoListener) this.mTelephonyCallbackWeakRef.get();
            if (displayInfoListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda32
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDisplayInfoChanged$35(displayInfoListener, telephonyDisplayInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayInfoChanged$35(final android.telephony.TelephonyCallback.DisplayInfoListener displayInfoListener, final android.telephony.TelephonyDisplayInfo telephonyDisplayInfo) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.DisplayInfoListener.this.onDisplayInfoChanged(telephonyDisplayInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOemHookRawEvent(byte[] bArr) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierNetworkChange(final boolean z) {
            final android.telephony.TelephonyCallback.CarrierNetworkListener carrierNetworkListener = (android.telephony.TelephonyCallback.CarrierNetworkListener) this.mTelephonyCallbackWeakRef.get();
            if (carrierNetworkListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda61
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCarrierNetworkChange$37(carrierNetworkListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierNetworkChange$37(final android.telephony.TelephonyCallback.CarrierNetworkListener carrierNetworkListener, final boolean z) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.CarrierNetworkListener.this.onCarrierNetworkChange(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onEmergencyNumberListChanged(final java.util.Map map) {
            final android.telephony.TelephonyCallback.EmergencyNumberListListener emergencyNumberListListener = (android.telephony.TelephonyCallback.EmergencyNumberListListener) this.mTelephonyCallbackWeakRef.get();
            if (emergencyNumberListListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda39
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onEmergencyNumberListChanged$39(emergencyNumberListListener, map);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEmergencyNumberListChanged$39(final android.telephony.TelephonyCallback.EmergencyNumberListListener emergencyNumberListListener, final java.util.Map map) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda42
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.EmergencyNumberListListener.this.onEmergencyNumberListChanged(map);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOutgoingEmergencyCall(final android.telephony.emergency.EmergencyNumber emergencyNumber, final int i) {
            final android.telephony.TelephonyCallback.OutgoingEmergencyCallListener outgoingEmergencyCallListener = (android.telephony.TelephonyCallback.OutgoingEmergencyCallListener) this.mTelephonyCallbackWeakRef.get();
            if (outgoingEmergencyCallListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda30
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onOutgoingEmergencyCall$41(outgoingEmergencyCallListener, emergencyNumber, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOutgoingEmergencyCall$41(final android.telephony.TelephonyCallback.OutgoingEmergencyCallListener outgoingEmergencyCallListener, final android.telephony.emergency.EmergencyNumber emergencyNumber, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda57
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.OutgoingEmergencyCallListener.this.onOutgoingEmergencyCall(emergencyNumber, i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOutgoingEmergencySms(final android.telephony.emergency.EmergencyNumber emergencyNumber, final int i) {
            final android.telephony.TelephonyCallback.OutgoingEmergencySmsListener outgoingEmergencySmsListener = (android.telephony.TelephonyCallback.OutgoingEmergencySmsListener) this.mTelephonyCallbackWeakRef.get();
            if (outgoingEmergencySmsListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda49
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onOutgoingEmergencySms$43(outgoingEmergencySmsListener, emergencyNumber, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOutgoingEmergencySms$43(final android.telephony.TelephonyCallback.OutgoingEmergencySmsListener outgoingEmergencySmsListener, final android.telephony.emergency.EmergencyNumber emergencyNumber, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.OutgoingEmergencySmsListener.this.onOutgoingEmergencySms(emergencyNumber, i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhoneCapabilityChanged(final android.telephony.PhoneCapability phoneCapability) {
            final android.telephony.TelephonyCallback.PhoneCapabilityListener phoneCapabilityListener = (android.telephony.TelephonyCallback.PhoneCapabilityListener) this.mTelephonyCallbackWeakRef.get();
            if (phoneCapabilityListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda70
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onPhoneCapabilityChanged$45(phoneCapabilityListener, phoneCapability);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPhoneCapabilityChanged$45(final android.telephony.TelephonyCallback.PhoneCapabilityListener phoneCapabilityListener, final android.telephony.PhoneCapability phoneCapability) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda62
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.PhoneCapabilityListener.this.onPhoneCapabilityChanged(phoneCapability);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRadioPowerStateChanged(final int i) {
            final android.telephony.TelephonyCallback.RadioPowerStateListener radioPowerStateListener = (android.telephony.TelephonyCallback.RadioPowerStateListener) this.mTelephonyCallbackWeakRef.get();
            if (radioPowerStateListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda44
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onRadioPowerStateChanged$47(radioPowerStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRadioPowerStateChanged$47(final android.telephony.TelephonyCallback.RadioPowerStateListener radioPowerStateListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.RadioPowerStateListener.this.onRadioPowerStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStatesChanged(final java.util.List<android.telephony.CallState> list) {
            final android.telephony.TelephonyCallback.CallAttributesListener callAttributesListener = (android.telephony.TelephonyCallback.CallAttributesListener) this.mTelephonyCallbackWeakRef.get();
            if (callAttributesListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda21
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallStatesChanged$49(callAttributesListener, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallStatesChanged$49(final android.telephony.TelephonyCallback.CallAttributesListener callAttributesListener, final java.util.List list) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda65
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.CallAttributesListener.this.onCallStatesChanged(list);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onActiveDataSubIdChanged(final int i) {
            final android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener activeDataSubscriptionIdListener = (android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener) this.mTelephonyCallbackWeakRef.get();
            if (activeDataSubscriptionIdListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda22
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onActiveDataSubIdChanged$51(activeDataSubscriptionIdListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onActiveDataSubIdChanged$51(final android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener activeDataSubscriptionIdListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda60
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener.this.onActiveDataSubscriptionIdChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onImsCallDisconnectCauseChanged(final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            final android.telephony.TelephonyCallback.ImsCallDisconnectCauseListener imsCallDisconnectCauseListener = (android.telephony.TelephonyCallback.ImsCallDisconnectCauseListener) this.mTelephonyCallbackWeakRef.get();
            if (imsCallDisconnectCauseListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda40
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onImsCallDisconnectCauseChanged$53(imsCallDisconnectCauseListener, imsReasonInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onImsCallDisconnectCauseChanged$53(final android.telephony.TelephonyCallback.ImsCallDisconnectCauseListener imsCallDisconnectCauseListener, final android.telephony.ims.ImsReasonInfo imsReasonInfo) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda63
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.ImsCallDisconnectCauseListener.this.onImsCallDisconnectCauseChanged(imsReasonInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRegistrationFailed(final android.telephony.CellIdentity cellIdentity, final java.lang.String str, final int i, final int i2, final int i3) {
            final android.telephony.TelephonyCallback.RegistrationFailedListener registrationFailedListener = (android.telephony.TelephonyCallback.RegistrationFailedListener) this.mTelephonyCallbackWeakRef.get();
            if (registrationFailedListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda58
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onRegistrationFailed$55(registrationFailedListener, cellIdentity, str, i, i2, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRegistrationFailed$55(final android.telephony.TelephonyCallback.RegistrationFailedListener registrationFailedListener, final android.telephony.CellIdentity cellIdentity, final java.lang.String str, final int i, final int i2, final int i3) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.RegistrationFailedListener.this.onRegistrationFailed(cellIdentity, str, i, i2, i3);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onBarringInfoChanged(final android.telephony.BarringInfo barringInfo) {
            final android.telephony.TelephonyCallback.BarringInfoListener barringInfoListener = (android.telephony.TelephonyCallback.BarringInfoListener) this.mTelephonyCallbackWeakRef.get();
            if (barringInfoListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda71
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onBarringInfoChanged$57(barringInfoListener, barringInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBarringInfoChanged$57(final android.telephony.TelephonyCallback.BarringInfoListener barringInfoListener, final android.telephony.BarringInfo barringInfo) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.BarringInfoListener.this.onBarringInfoChanged(barringInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhysicalChannelConfigChanged(final java.util.List<android.telephony.PhysicalChannelConfig> list) {
            final android.telephony.TelephonyCallback.PhysicalChannelConfigListener physicalChannelConfigListener = (android.telephony.TelephonyCallback.PhysicalChannelConfigListener) this.mTelephonyCallbackWeakRef.get();
            if (physicalChannelConfigListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda36
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onPhysicalChannelConfigChanged$59(physicalChannelConfigListener, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPhysicalChannelConfigChanged$59(final android.telephony.TelephonyCallback.PhysicalChannelConfigListener physicalChannelConfigListener, final java.util.List list) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.PhysicalChannelConfigListener.this.onPhysicalChannelConfigChanged(list);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataEnabledChanged(final boolean z, final int i) {
            final android.telephony.TelephonyCallback.DataEnabledListener dataEnabledListener = (android.telephony.TelephonyCallback.DataEnabledListener) this.mTelephonyCallbackWeakRef.get();
            if (dataEnabledListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataEnabledChanged$61(dataEnabledListener, z, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataEnabledChanged$61(final android.telephony.TelephonyCallback.DataEnabledListener dataEnabledListener, final boolean z, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.DataEnabledListener.this.onDataEnabledChanged(z, i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onAllowedNetworkTypesChanged(final int i, final long j) {
            final android.telephony.TelephonyCallback.AllowedNetworkTypesListener allowedNetworkTypesListener = (android.telephony.TelephonyCallback.AllowedNetworkTypesListener) this.mTelephonyCallbackWeakRef.get();
            if (allowedNetworkTypesListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda68
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onAllowedNetworkTypesChanged$63(allowedNetworkTypesListener, i, j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAllowedNetworkTypesChanged$63(final android.telephony.TelephonyCallback.AllowedNetworkTypesListener allowedNetworkTypesListener, final int i, final long j) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda53
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.AllowedNetworkTypesListener.this.onAllowedNetworkTypesChanged(i, j);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSimultaneousCallingStateChanged(final int[] iArr) {
            final android.telephony.TelephonyCallback.SimultaneousCellularCallingSupportListener simultaneousCellularCallingSupportListener = (android.telephony.TelephonyCallback.SimultaneousCellularCallingSupportListener) this.mTelephonyCallbackWeakRef.get();
            if (simultaneousCellularCallingSupportListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda27
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onSimultaneousCallingStateChanged$65(simultaneousCellularCallingSupportListener, iArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSimultaneousCallingStateChanged$65(final android.telephony.TelephonyCallback.SimultaneousCellularCallingSupportListener simultaneousCellularCallingSupportListener, final int[] iArr) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.SimultaneousCellularCallingSupportListener.this.onSimultaneousCellularCallingSubscriptionsChanged((java.util.Set) java.util.Arrays.stream(iArr).boxed().collect(java.util.stream.Collectors.toSet()));
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onLinkCapacityEstimateChanged(final java.util.List<android.telephony.LinkCapacityEstimate> list) {
            final android.telephony.TelephonyCallback.LinkCapacityEstimateChangedListener linkCapacityEstimateChangedListener = (android.telephony.TelephonyCallback.LinkCapacityEstimateChangedListener) this.mTelephonyCallbackWeakRef.get();
            if (linkCapacityEstimateChangedListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda73
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onLinkCapacityEstimateChanged$67(linkCapacityEstimateChangedListener, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLinkCapacityEstimateChanged$67(final android.telephony.TelephonyCallback.LinkCapacityEstimateChangedListener linkCapacityEstimateChangedListener, final java.util.List list) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda37
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.LinkCapacityEstimateChangedListener.this.onLinkCapacityEstimateChanged(list);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onMediaQualityStatusChanged(final android.telephony.ims.MediaQualityStatus mediaQualityStatus) {
            final android.telephony.TelephonyCallback.MediaQualityStatusChangedListener mediaQualityStatusChangedListener = (android.telephony.TelephonyCallback.MediaQualityStatusChangedListener) this.mTelephonyCallbackWeakRef.get();
            if (mediaQualityStatusChangedListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda38
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onMediaQualityStatusChanged$69(mediaQualityStatusChangedListener, mediaQualityStatus);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMediaQualityStatusChanged$69(final android.telephony.TelephonyCallback.MediaQualityStatusChangedListener mediaQualityStatusChangedListener, final android.telephony.ims.MediaQualityStatus mediaQualityStatus) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda69
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.MediaQualityStatusChangedListener.this.onMediaQualityStatusChanged(mediaQualityStatus);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallBackModeStarted(final int i) {
            final android.telephony.TelephonyCallback.EmergencyCallbackModeListener emergencyCallbackModeListener = (android.telephony.TelephonyCallback.EmergencyCallbackModeListener) this.mTelephonyCallbackWeakRef.get();
            android.util.Log.d(android.telephony.TelephonyCallback.LOG_TAG, "onCallBackModeStarted:type=" + i + ", listener=" + emergencyCallbackModeListener);
            if (emergencyCallbackModeListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda45
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallBackModeStarted$71(emergencyCallbackModeListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallBackModeStarted$71(final android.telephony.TelephonyCallback.EmergencyCallbackModeListener emergencyCallbackModeListener, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda50
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.EmergencyCallbackModeListener.this.onCallBackModeStarted(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallBackModeStopped(final int i, final int i2) {
            final android.telephony.TelephonyCallback.EmergencyCallbackModeListener emergencyCallbackModeListener = (android.telephony.TelephonyCallback.EmergencyCallbackModeListener) this.mTelephonyCallbackWeakRef.get();
            android.util.Log.d(android.telephony.TelephonyCallback.LOG_TAG, "onCallBackModeStopped:type=" + i + ", reason=" + i2 + ", listener=" + emergencyCallbackModeListener);
            if (emergencyCallbackModeListener == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda64
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallBackModeStopped$73(emergencyCallbackModeListener, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallBackModeStopped$73(final android.telephony.TelephonyCallback.EmergencyCallbackModeListener emergencyCallbackModeListener, final int i, final int i2) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda41
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyCallback.EmergencyCallbackModeListener.this.onCallBackModeStopped(i, i2);
                }
            });
        }
    }
}
