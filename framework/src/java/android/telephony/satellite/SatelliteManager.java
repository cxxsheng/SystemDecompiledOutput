package android.telephony.satellite;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class SatelliteManager {
    public static final int DATAGRAM_TYPE_LOCATION_SHARING = 2;
    public static final int DATAGRAM_TYPE_SOS_MESSAGE = 1;
    public static final int DATAGRAM_TYPE_UNKNOWN = 0;
    public static final int DEVICE_HOLD_POSITION_LANDSCAPE_LEFT = 2;
    public static final int DEVICE_HOLD_POSITION_LANDSCAPE_RIGHT = 3;
    public static final int DEVICE_HOLD_POSITION_PORTRAIT = 1;
    public static final int DEVICE_HOLD_POSITION_UNKNOWN = 0;
    public static final int DISPLAY_MODE_CLOSED = 3;
    public static final int DISPLAY_MODE_FIXED = 1;
    public static final int DISPLAY_MODE_OPENED = 2;
    public static final int DISPLAY_MODE_UNKNOWN = 0;
    public static final int EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE_SOS = 1;
    public static final int EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE_T911 = 2;
    public static final java.lang.String KEY_DEMO_MODE_ENABLED = "demo_mode_enabled";
    public static final java.lang.String KEY_EMERGENCY_MODE_ENABLED = "emergency_mode_enabled";
    public static final java.lang.String KEY_NTN_SIGNAL_STRENGTH = "ntn_signal_strength";
    public static final java.lang.String KEY_SATELLITE_CAPABILITIES = "satellite_capabilities";
    public static final java.lang.String KEY_SATELLITE_COMMUNICATION_ALLOWED = "satellite_communication_allowed";
    public static final java.lang.String KEY_SATELLITE_ENABLED = "satellite_enabled";
    public static final java.lang.String KEY_SATELLITE_NEXT_VISIBILITY = "satellite_next_visibility";
    public static final java.lang.String KEY_SATELLITE_PROVISIONED = "satellite_provisioned";
    public static final java.lang.String KEY_SATELLITE_SUPPORTED = "satellite_supported";
    public static final int NT_RADIO_TECHNOLOGY_EMTC_NTN = 3;
    public static final int NT_RADIO_TECHNOLOGY_NB_IOT_NTN = 1;
    public static final int NT_RADIO_TECHNOLOGY_NR_NTN = 2;
    public static final int NT_RADIO_TECHNOLOGY_PROPRIETARY = 4;
    public static final int NT_RADIO_TECHNOLOGY_UNKNOWN = 0;
    public static final int SATELLITE_COMMUNICATION_RESTRICTION_REASON_ENTITLEMENT = 2;
    public static final int SATELLITE_COMMUNICATION_RESTRICTION_REASON_GEOLOCATION = 1;
    public static final int SATELLITE_COMMUNICATION_RESTRICTION_REASON_USER = 0;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_IDLE = 0;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVE_FAILED = 7;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVE_NONE = 6;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVE_SUCCESS = 5;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVING = 4;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_SENDING = 1;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_SEND_FAILED = 3;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_SEND_SUCCESS = 2;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_UNKNOWN = -1;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_WAITING_TO_CONNECT = 8;
    public static final int SATELLITE_MODEM_STATE_CONNECTED = 7;
    public static final int SATELLITE_MODEM_STATE_DATAGRAM_RETRYING = 3;
    public static final int SATELLITE_MODEM_STATE_DATAGRAM_TRANSFERRING = 2;
    public static final int SATELLITE_MODEM_STATE_IDLE = 0;
    public static final int SATELLITE_MODEM_STATE_LISTENING = 1;
    public static final int SATELLITE_MODEM_STATE_NOT_CONNECTED = 6;
    public static final int SATELLITE_MODEM_STATE_OFF = 4;
    public static final int SATELLITE_MODEM_STATE_UNAVAILABLE = 5;
    public static final int SATELLITE_MODEM_STATE_UNKNOWN = -1;
    public static final int SATELLITE_RESULT_ACCESS_BARRED = 16;
    public static final int SATELLITE_RESULT_ERROR = 1;
    public static final int SATELLITE_RESULT_ILLEGAL_STATE = 23;
    public static final int SATELLITE_RESULT_INVALID_ARGUMENTS = 8;
    public static final int SATELLITE_RESULT_INVALID_MODEM_STATE = 7;
    public static final int SATELLITE_RESULT_INVALID_TELEPHONY_STATE = 6;
    public static final int SATELLITE_RESULT_MODEM_BUSY = 22;
    public static final int SATELLITE_RESULT_MODEM_ERROR = 4;
    public static final int SATELLITE_RESULT_MODEM_TIMEOUT = 24;
    public static final int SATELLITE_RESULT_NETWORK_ERROR = 5;
    public static final int SATELLITE_RESULT_NETWORK_TIMEOUT = 17;
    public static final int SATELLITE_RESULT_NOT_AUTHORIZED = 19;
    public static final int SATELLITE_RESULT_NOT_REACHABLE = 18;
    public static final int SATELLITE_RESULT_NOT_SUPPORTED = 20;
    public static final int SATELLITE_RESULT_NO_RESOURCES = 12;
    public static final int SATELLITE_RESULT_RADIO_NOT_AVAILABLE = 10;
    public static final int SATELLITE_RESULT_REQUEST_ABORTED = 15;
    public static final int SATELLITE_RESULT_REQUEST_FAILED = 9;
    public static final int SATELLITE_RESULT_REQUEST_IN_PROGRESS = 21;
    public static final int SATELLITE_RESULT_REQUEST_NOT_SUPPORTED = 11;
    public static final int SATELLITE_RESULT_SERVER_ERROR = 2;
    public static final int SATELLITE_RESULT_SERVICE_ERROR = 3;
    public static final int SATELLITE_RESULT_SERVICE_NOT_PROVISIONED = 13;
    public static final int SATELLITE_RESULT_SERVICE_PROVISION_IN_PROGRESS = 14;
    public static final int SATELLITE_RESULT_SUCCESS = 0;
    private static final java.lang.String TAG = "SatelliteManager";
    private final android.content.Context mContext;
    private final int mSubId;
    private static final java.util.concurrent.ConcurrentHashMap<android.telephony.satellite.SatelliteDatagramCallback, android.telephony.satellite.ISatelliteDatagramCallback> sSatelliteDatagramCallbackMap = new java.util.concurrent.ConcurrentHashMap<>();
    private static final java.util.concurrent.ConcurrentHashMap<android.telephony.satellite.SatelliteProvisionStateCallback, android.telephony.satellite.ISatelliteProvisionStateCallback> sSatelliteProvisionStateCallbackMap = new java.util.concurrent.ConcurrentHashMap<>();
    private static final java.util.concurrent.ConcurrentHashMap<android.telephony.satellite.SatelliteModemStateCallback, android.telephony.satellite.ISatelliteModemStateCallback> sSatelliteModemStateCallbackMap = new java.util.concurrent.ConcurrentHashMap<>();
    private static final java.util.concurrent.ConcurrentHashMap<android.telephony.satellite.SatelliteTransmissionUpdateCallback, android.telephony.satellite.ISatelliteTransmissionUpdateCallback> sSatelliteTransmissionUpdateCallbackMap = new java.util.concurrent.ConcurrentHashMap<>();
    private static final java.util.concurrent.ConcurrentHashMap<android.telephony.satellite.NtnSignalStrengthCallback, android.telephony.satellite.INtnSignalStrengthCallback> sNtnSignalStrengthCallbackMap = new java.util.concurrent.ConcurrentHashMap<>();
    private static final java.util.concurrent.ConcurrentHashMap<android.telephony.satellite.SatelliteCapabilitiesCallback, android.telephony.satellite.ISatelliteCapabilitiesCallback> sSatelliteCapabilitiesCallbackMap = new java.util.concurrent.ConcurrentHashMap<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DatagramType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeviceHoldPosition {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DisplayMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NTRadioTechnology {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SatelliteCommunicationRestrictionReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SatelliteDatagramTransferState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SatelliteModemState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SatelliteResult {
    }

    public SatelliteManager(android.content.Context context) {
        this(context, Integer.MAX_VALUE);
    }

    private SatelliteManager(android.content.Context context, int i) {
        this.mContext = context;
        this.mSubId = i;
    }

    public static class SatelliteException extends java.lang.Exception {
        private final int mErrorCode;

        public SatelliteException(int i) {
            this.mErrorCode = i;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }
    }

    public void requestEnabled(android.telephony.satellite.EnableRequestAttributes enableRequestAttributes, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        java.util.Objects.requireNonNull(enableRequestAttributes);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "requestEnabled() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda13
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                r1.accept(23);
                            }
                        });
                    }
                });
            } else {
                iTelephony.requestSatelliteEnabled(this.mSubId, enableRequestAttributes.isEnabled(), enableRequestAttributes.isDemoMode(), enableRequestAttributes.isEmergencyMode(), new android.telephony.satellite.SatelliteManager.AnonymousClass1(executor, consumer));
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "requestEnabled() exception: ", e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda48
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(23);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$resultListener;

        AnonymousClass1(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$resultListener;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$1$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    public void requestIsEnabled(java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<java.lang.Boolean, android.telephony.satellite.SatelliteManager.SatelliteException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestIsSatelliteEnabled(this.mSubId, new android.telephony.satellite.SatelliteManager.AnonymousClass2(null, executor, outcomeReceiver));
            } else {
                loge("requestIsEnabled() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda69
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda55
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("requestIsEnabled() RemoteException: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda70
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda54
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$2, reason: invalid class name */
    class AnonymousClass2 extends android.os.ResultReceiver {
        final /* synthetic */ android.os.OutcomeReceiver val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(android.os.Handler handler, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, android.os.Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey("satellite_enabled")) {
                    final boolean z = bundle.getBoolean("satellite_enabled");
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda1
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    android.os.OutcomeReceiver.this.onResult(java.lang.Boolean.valueOf(r2));
                                }
                            });
                        }
                    });
                    return;
                }
                android.telephony.satellite.SatelliteManager.loge("KEY_SATELLITE_ENABLED does not exist.");
                java.util.concurrent.Executor executor2 = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda0
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            java.util.concurrent.Executor executor3 = this.val$executor;
            final android.os.OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda5
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(r2));
                        }
                    });
                }
            });
        }
    }

    public void requestIsDemoModeEnabled(java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<java.lang.Boolean, android.telephony.satellite.SatelliteManager.SatelliteException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestIsDemoModeEnabled(this.mSubId, new android.telephony.satellite.SatelliteManager.AnonymousClass3(null, executor, outcomeReceiver));
            } else {
                loge("requestIsDemoModeEnabled() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda29
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("requestIsDemoModeEnabled() RemoteException: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda2
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$3, reason: invalid class name */
    class AnonymousClass3 extends android.os.ResultReceiver {
        final /* synthetic */ android.os.OutcomeReceiver val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(android.os.Handler handler, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, android.os.Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(android.telephony.satellite.SatelliteManager.KEY_DEMO_MODE_ENABLED)) {
                    final boolean z = bundle.getBoolean(android.telephony.satellite.SatelliteManager.KEY_DEMO_MODE_ENABLED);
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda1
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    android.os.OutcomeReceiver.this.onResult(java.lang.Boolean.valueOf(r2));
                                }
                            });
                        }
                    });
                    return;
                }
                android.telephony.satellite.SatelliteManager.loge("KEY_DEMO_MODE_ENABLED does not exist.");
                java.util.concurrent.Executor executor2 = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda2
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            java.util.concurrent.Executor executor3 = this.val$executor;
            final android.os.OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(r2));
                        }
                    });
                }
            });
        }
    }

    public void requestIsEmergencyModeEnabled(java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<java.lang.Boolean, android.telephony.satellite.SatelliteManager.SatelliteException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestIsEmergencyModeEnabled(this.mSubId, new android.telephony.satellite.SatelliteManager.AnonymousClass4(null, executor, outcomeReceiver));
            } else {
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda20
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda72
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("requestIsEmergencyModeEnabled() RemoteException: " + e);
            e.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$4, reason: invalid class name */
    class AnonymousClass4 extends android.os.ResultReceiver {
        final /* synthetic */ android.os.OutcomeReceiver val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass4(android.os.Handler handler, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, android.os.Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(android.telephony.satellite.SatelliteManager.KEY_EMERGENCY_MODE_ENABLED)) {
                    final boolean z = bundle.getBoolean(android.telephony.satellite.SatelliteManager.KEY_EMERGENCY_MODE_ENABLED);
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda5
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    android.os.OutcomeReceiver.this.onResult(java.lang.Boolean.valueOf(r2));
                                }
                            });
                        }
                    });
                    return;
                }
                android.telephony.satellite.SatelliteManager.loge("KEY_EMERGENCY_MODE_ENABLED does not exist.");
                java.util.concurrent.Executor executor2 = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda3
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            java.util.concurrent.Executor executor3 = this.val$executor;
            final android.os.OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda4
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(r2));
                        }
                    });
                }
            });
        }
    }

    public void requestIsSupported(java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<java.lang.Boolean, android.telephony.satellite.SatelliteManager.SatelliteException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestIsSatelliteSupported(this.mSubId, new android.telephony.satellite.SatelliteManager.AnonymousClass5(null, executor, outcomeReceiver));
            } else {
                loge("requestIsSupported() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda35
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda66
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("requestIsSupported() RemoteException: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda36
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda47
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$5, reason: invalid class name */
    class AnonymousClass5 extends android.os.ResultReceiver {
        final /* synthetic */ android.os.OutcomeReceiver val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass5(android.os.Handler handler, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, android.os.Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(android.telephony.satellite.SatelliteManager.KEY_SATELLITE_SUPPORTED)) {
                    final boolean z = bundle.getBoolean(android.telephony.satellite.SatelliteManager.KEY_SATELLITE_SUPPORTED);
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda2
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    android.os.OutcomeReceiver.this.onResult(java.lang.Boolean.valueOf(r2));
                                }
                            });
                        }
                    });
                    return;
                }
                android.telephony.satellite.SatelliteManager.loge("KEY_SATELLITE_SUPPORTED does not exist.");
                java.util.concurrent.Executor executor2 = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda1
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            java.util.concurrent.Executor executor3 = this.val$executor;
            final android.os.OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(r2));
                        }
                    });
                }
            });
        }
    }

    public void requestCapabilities(java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<android.telephony.satellite.SatelliteCapabilities, android.telephony.satellite.SatelliteManager.SatelliteException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestSatelliteCapabilities(this.mSubId, new android.telephony.satellite.SatelliteManager.AnonymousClass6(null, executor, outcomeReceiver));
            } else {
                loge("requestCapabilities() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda40
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda45
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("requestCapabilities() RemoteException: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda41
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda15
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$6, reason: invalid class name */
    class AnonymousClass6 extends android.os.ResultReceiver {
        final /* synthetic */ android.os.OutcomeReceiver val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass6(android.os.Handler handler, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, android.os.Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(android.telephony.satellite.SatelliteManager.KEY_SATELLITE_CAPABILITIES)) {
                    final android.telephony.satellite.SatelliteCapabilities satelliteCapabilities = (android.telephony.satellite.SatelliteCapabilities) bundle.getParcelable(android.telephony.satellite.SatelliteManager.KEY_SATELLITE_CAPABILITIES, android.telephony.satellite.SatelliteCapabilities.class);
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda5
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    android.os.OutcomeReceiver.this.onResult(r2);
                                }
                            });
                        }
                    });
                    return;
                }
                android.telephony.satellite.SatelliteManager.loge("KEY_SATELLITE_CAPABILITIES does not exist.");
                java.util.concurrent.Executor executor2 = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda4
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            java.util.concurrent.Executor executor3 = this.val$executor;
            final android.os.OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(r2));
                        }
                    });
                }
            });
        }
    }

    public void startTransmissionUpdates(java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer, android.telephony.satellite.SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        java.util.Objects.requireNonNull(satelliteTransmissionUpdateCallback);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                android.telephony.satellite.SatelliteManager.AnonymousClass7 anonymousClass7 = new android.telephony.satellite.SatelliteManager.AnonymousClass7(executor, consumer);
                android.telephony.satellite.SatelliteManager.AnonymousClass8 anonymousClass8 = new android.telephony.satellite.SatelliteManager.AnonymousClass8(executor, satelliteTransmissionUpdateCallback);
                sSatelliteTransmissionUpdateCallbackMap.put(satelliteTransmissionUpdateCallback, anonymousClass8);
                iTelephony.startSatelliteTransmissionUpdates(this.mSubId, anonymousClass7, anonymousClass8);
            } else {
                loge("startTransmissionUpdates() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda58
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                r1.accept(23);
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("startTransmissionUpdates() RemoteException: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda64
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(23);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$7, reason: invalid class name */
    class AnonymousClass7 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$resultListener;

        AnonymousClass7(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$resultListener;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$7$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$7$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$8, reason: invalid class name */
    class AnonymousClass8 extends android.telephony.satellite.ISatelliteTransmissionUpdateCallback.Stub {
        final /* synthetic */ android.telephony.satellite.SatelliteTransmissionUpdateCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass8(java.util.concurrent.Executor executor, android.telephony.satellite.SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteTransmissionUpdateCallback;
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSatellitePositionChanged(final android.telephony.satellite.PointingInfo pointingInfo) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.telephony.satellite.SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda4
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.telephony.satellite.SatelliteTransmissionUpdateCallback.this.onSatellitePositionChanged(r2);
                        }
                    });
                }
            });
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSendDatagramStateChanged(final int i, final int i2, final int i3) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.telephony.satellite.SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda3
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.telephony.satellite.SatelliteTransmissionUpdateCallback.this.onSendDatagramStateChanged(r2, r3, r4);
                        }
                    });
                }
            });
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onReceiveDatagramStateChanged(final int i, final int i2, final int i3) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.telephony.satellite.SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.telephony.satellite.SatelliteTransmissionUpdateCallback.this.onReceiveDatagramStateChanged(r2, r3, r4);
                        }
                    });
                }
            });
        }
    }

    public void stopTransmissionUpdates(android.telephony.satellite.SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        java.util.Objects.requireNonNull(satelliteTransmissionUpdateCallback);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        android.telephony.satellite.ISatelliteTransmissionUpdateCallback remove = sSatelliteTransmissionUpdateCallbackMap.remove(satelliteTransmissionUpdateCallback);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (remove != null) {
                    iTelephony.stopSatelliteTransmissionUpdates(this.mSubId, new android.telephony.satellite.SatelliteManager.AnonymousClass9(executor, consumer), remove);
                } else {
                    loge("stopSatelliteTransmissionUpdates: No internal callback.");
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda26
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda19
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    r1.accept(8);
                                }
                            });
                        }
                    });
                }
            } else {
                loge("stopTransmissionUpdates() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda27
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda46
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                r1.accept(23);
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("stopTransmissionUpdates() RemoteException: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda16
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(23);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$9, reason: invalid class name */
    class AnonymousClass9 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$resultListener;

        AnonymousClass9(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$resultListener;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$9$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$9$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    public void provisionService(java.lang.String str, byte[] bArr, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        java.util.Objects.requireNonNull(bArr);
        android.os.ICancellationSignal iCancellationSignal = null;
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iCancellationSignal = iTelephony.provisionSatelliteService(this.mSubId, str, bArr, new android.telephony.satellite.SatelliteManager.AnonymousClass10(executor, consumer));
            } else {
                loge("provisionService() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda67
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda61
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                r1.accept(23);
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("provisionService() RemoteException=" + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda68
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda4
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(23);
                        }
                    });
                }
            });
        }
        if (cancellationSignal != null) {
            cancellationSignal.setRemote(iCancellationSignal);
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$10, reason: invalid class name */
    class AnonymousClass10 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$resultListener;

        AnonymousClass10(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$resultListener;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$10$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$10$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    public void deprovisionService(java.lang.String str, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.deprovisionSatelliteService(this.mSubId, str, new android.telephony.satellite.SatelliteManager.AnonymousClass11(executor, consumer));
            } else {
                loge("deprovisionService() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda22
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda21
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                r1.accept(23);
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("deprovisionService() RemoteException ex=" + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda32
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(23);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$11, reason: invalid class name */
    class AnonymousClass11 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$resultListener;

        AnonymousClass11(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$resultListener;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$11$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$11$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    public int registerForProvisionStateChanged(java.util.concurrent.Executor executor, android.telephony.satellite.SatelliteProvisionStateCallback satelliteProvisionStateCallback) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(satelliteProvisionStateCallback);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                android.telephony.satellite.SatelliteManager.AnonymousClass12 anonymousClass12 = new android.telephony.satellite.SatelliteManager.AnonymousClass12(executor, satelliteProvisionStateCallback);
                sSatelliteProvisionStateCallbackMap.put(satelliteProvisionStateCallback, anonymousClass12);
                return iTelephony.registerForSatelliteProvisionStateChanged(this.mSubId, anonymousClass12);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("registerForProvisionStateChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$12, reason: invalid class name */
    class AnonymousClass12 extends android.telephony.satellite.ISatelliteProvisionStateCallback.Stub {
        final /* synthetic */ android.telephony.satellite.SatelliteProvisionStateCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass12(java.util.concurrent.Executor executor, android.telephony.satellite.SatelliteProvisionStateCallback satelliteProvisionStateCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteProvisionStateCallback;
        }

        @Override // android.telephony.satellite.ISatelliteProvisionStateCallback
        public void onSatelliteProvisionStateChanged(final boolean z) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.telephony.satellite.SatelliteProvisionStateCallback satelliteProvisionStateCallback = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.telephony.satellite.SatelliteProvisionStateCallback.this.onSatelliteProvisionStateChanged(r2);
                        }
                    });
                }
            });
        }
    }

    public void unregisterForProvisionStateChanged(android.telephony.satellite.SatelliteProvisionStateCallback satelliteProvisionStateCallback) {
        java.util.Objects.requireNonNull(satelliteProvisionStateCallback);
        android.telephony.satellite.ISatelliteProvisionStateCallback remove = sSatelliteProvisionStateCallbackMap.remove(satelliteProvisionStateCallback);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (remove != null) {
                    iTelephony.unregisterForSatelliteProvisionStateChanged(this.mSubId, remove);
                } else {
                    loge("unregisterForProvisionStateChanged: No internal callback.");
                }
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("unregisterForProvisionStateChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
        }
    }

    public void requestIsProvisioned(java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<java.lang.Boolean, android.telephony.satellite.SatelliteManager.SatelliteException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestIsSatelliteProvisioned(this.mSubId, new android.telephony.satellite.SatelliteManager.AnonymousClass13(null, executor, outcomeReceiver));
            } else {
                loge("requestIsProvisioned() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda42
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda56
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("requestIsProvisioned() RemoteException: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda43
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$13, reason: invalid class name */
    class AnonymousClass13 extends android.os.ResultReceiver {
        final /* synthetic */ android.os.OutcomeReceiver val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass13(android.os.Handler handler, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, android.os.Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(android.telephony.satellite.SatelliteManager.KEY_SATELLITE_PROVISIONED)) {
                    final boolean z = bundle.getBoolean(android.telephony.satellite.SatelliteManager.KEY_SATELLITE_PROVISIONED);
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda5
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    android.os.OutcomeReceiver.this.onResult(java.lang.Boolean.valueOf(r2));
                                }
                            });
                        }
                    });
                    return;
                }
                android.telephony.satellite.SatelliteManager.loge("KEY_SATELLITE_PROVISIONED does not exist.");
                java.util.concurrent.Executor executor2 = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda4
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            java.util.concurrent.Executor executor3 = this.val$executor;
            final android.os.OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda3
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(r2));
                        }
                    });
                }
            });
        }
    }

    public int registerForModemStateChanged(java.util.concurrent.Executor executor, android.telephony.satellite.SatelliteModemStateCallback satelliteModemStateCallback) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(satelliteModemStateCallback);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                android.telephony.satellite.SatelliteManager.AnonymousClass14 anonymousClass14 = new android.telephony.satellite.SatelliteManager.AnonymousClass14(executor, satelliteModemStateCallback);
                sSatelliteModemStateCallbackMap.put(satelliteModemStateCallback, anonymousClass14);
                return iTelephony.registerForSatelliteModemStateChanged(this.mSubId, anonymousClass14);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("registerForModemStateChanged() RemoteException:" + e);
            e.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$14, reason: invalid class name */
    class AnonymousClass14 extends android.telephony.satellite.ISatelliteModemStateCallback.Stub {
        final /* synthetic */ android.telephony.satellite.SatelliteModemStateCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass14(java.util.concurrent.Executor executor, android.telephony.satellite.SatelliteModemStateCallback satelliteModemStateCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteModemStateCallback;
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onSatelliteModemStateChanged(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.telephony.satellite.SatelliteModemStateCallback satelliteModemStateCallback = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.telephony.satellite.SatelliteModemStateCallback.this.onSatelliteModemStateChanged(r2);
                        }
                    });
                }
            });
        }
    }

    public void unregisterForModemStateChanged(android.telephony.satellite.SatelliteModemStateCallback satelliteModemStateCallback) {
        java.util.Objects.requireNonNull(satelliteModemStateCallback);
        android.telephony.satellite.ISatelliteModemStateCallback remove = sSatelliteModemStateCallbackMap.remove(satelliteModemStateCallback);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (remove != null) {
                    iTelephony.unregisterForModemStateChanged(this.mSubId, remove);
                } else {
                    loge("unregisterForModemStateChanged: No internal callback.");
                }
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("unregisterForModemStateChanged() RemoteException:" + e);
            e.rethrowAsRuntimeException();
        }
    }

    public int registerForIncomingDatagram(java.util.concurrent.Executor executor, android.telephony.satellite.SatelliteDatagramCallback satelliteDatagramCallback) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(satelliteDatagramCallback);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                android.telephony.satellite.SatelliteManager.AnonymousClass15 anonymousClass15 = new android.telephony.satellite.SatelliteManager.AnonymousClass15(executor, satelliteDatagramCallback);
                sSatelliteDatagramCallbackMap.put(satelliteDatagramCallback, anonymousClass15);
                return iTelephony.registerForIncomingDatagram(this.mSubId, anonymousClass15);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("registerForIncomingDatagram() RemoteException:" + e);
            e.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$15, reason: invalid class name */
    class AnonymousClass15 extends android.telephony.satellite.ISatelliteDatagramCallback.Stub {
        final /* synthetic */ android.telephony.satellite.SatelliteDatagramCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass15(java.util.concurrent.Executor executor, android.telephony.satellite.SatelliteDatagramCallback satelliteDatagramCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteDatagramCallback;
        }

        @Override // android.telephony.satellite.ISatelliteDatagramCallback
        public void onSatelliteDatagramReceived(final long j, final android.telephony.satellite.SatelliteDatagram satelliteDatagram, final int i, final com.android.internal.telephony.IVoidConsumer iVoidConsumer) {
            final java.util.function.Consumer<java.lang.Void> consumer = new java.util.function.Consumer<java.lang.Void>() { // from class: android.telephony.satellite.SatelliteManager.15.1
                @Override // java.util.function.Consumer
                public void accept(java.lang.Void r3) {
                    try {
                        iVoidConsumer.accept();
                    } catch (android.os.RemoteException e) {
                        android.telephony.satellite.SatelliteManager.logd("onSatelliteDatagramReceived RemoteException: " + e);
                    }
                }
            };
            java.util.concurrent.Executor executor = this.val$executor;
            final android.telephony.satellite.SatelliteDatagramCallback satelliteDatagramCallback = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$15$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$15$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.telephony.satellite.SatelliteDatagramCallback.this.onSatelliteDatagramReceived(r2, r4, r5, r6);
                        }
                    });
                }
            });
        }
    }

    public void unregisterForIncomingDatagram(android.telephony.satellite.SatelliteDatagramCallback satelliteDatagramCallback) {
        java.util.Objects.requireNonNull(satelliteDatagramCallback);
        android.telephony.satellite.ISatelliteDatagramCallback remove = sSatelliteDatagramCallbackMap.remove(satelliteDatagramCallback);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (remove != null) {
                    iTelephony.unregisterForIncomingDatagram(this.mSubId, remove);
                } else {
                    loge("unregisterForIncomingDatagram: No internal callback.");
                }
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("unregisterForIncomingDatagram() RemoteException:" + e);
            e.rethrowAsRuntimeException();
        }
    }

    public void pollPendingDatagrams(java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.pollPendingDatagrams(this.mSubId, new android.telephony.satellite.SatelliteManager.AnonymousClass16(executor, consumer));
            } else {
                loge("pollPendingDatagrams() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda59
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda14
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                r1.accept(23);
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("pollPendingDatagrams() RemoteException:" + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda60
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda63
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(23);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$16, reason: invalid class name */
    class AnonymousClass16 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$resultListener;

        AnonymousClass16(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$resultListener;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$16$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$16$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    public void sendDatagram(int i, android.telephony.satellite.SatelliteDatagram satelliteDatagram, boolean z, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        java.util.Objects.requireNonNull(satelliteDatagram);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.sendDatagram(this.mSubId, i, satelliteDatagram, z, new android.telephony.satellite.SatelliteManager.AnonymousClass17(executor, consumer));
            } else {
                loge("sendDatagram() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda37
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda31
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                r1.accept(23);
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("sendDatagram() RemoteException:" + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda38
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda71
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(23);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$17, reason: invalid class name */
    class AnonymousClass17 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$resultListener;

        AnonymousClass17(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$resultListener;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$17$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$17$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    public void requestIsCommunicationAllowedForCurrentLocation(java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<java.lang.Boolean, android.telephony.satellite.SatelliteManager.SatelliteException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestIsCommunicationAllowedForCurrentLocation(this.mSubId, new android.telephony.satellite.SatelliteManager.AnonymousClass18(null, executor, outcomeReceiver));
            } else {
                loge("requestIsCommunicationAllowedForCurrentLocation() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda24
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda62
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("requestIsCommunicationAllowedForCurrentLocation() RemoteException: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda65
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$18, reason: invalid class name */
    class AnonymousClass18 extends android.os.ResultReceiver {
        final /* synthetic */ android.os.OutcomeReceiver val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass18(android.os.Handler handler, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, android.os.Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(android.telephony.satellite.SatelliteManager.KEY_SATELLITE_COMMUNICATION_ALLOWED)) {
                    final boolean z = bundle.getBoolean(android.telephony.satellite.SatelliteManager.KEY_SATELLITE_COMMUNICATION_ALLOWED);
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda4
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    android.os.OutcomeReceiver.this.onResult(java.lang.Boolean.valueOf(r2));
                                }
                            });
                        }
                    });
                    return;
                }
                android.telephony.satellite.SatelliteManager.loge("KEY_SATELLITE_COMMUNICATION_ALLOWED does not exist.");
                java.util.concurrent.Executor executor2 = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda3
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            java.util.concurrent.Executor executor3 = this.val$executor;
            final android.os.OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda5
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(r2));
                        }
                    });
                }
            });
        }
    }

    public void requestTimeForNextSatelliteVisibility(java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<java.time.Duration, android.telephony.satellite.SatelliteManager.SatelliteException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestTimeForNextSatelliteVisibility(this.mSubId, new android.telephony.satellite.SatelliteManager.AnonymousClass19(null, executor, outcomeReceiver));
            } else {
                loge("requestTimeForNextSatelliteVisibility() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda53
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("requestTimeForNextSatelliteVisibility() RemoteException: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda52
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$19, reason: invalid class name */
    class AnonymousClass19 extends android.os.ResultReceiver {
        final /* synthetic */ android.os.OutcomeReceiver val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass19(android.os.Handler handler, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, android.os.Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(android.telephony.satellite.SatelliteManager.KEY_SATELLITE_NEXT_VISIBILITY)) {
                    final int i2 = bundle.getInt(android.telephony.satellite.SatelliteManager.KEY_SATELLITE_NEXT_VISIBILITY);
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda4
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    android.os.OutcomeReceiver.this.onResult(java.time.Duration.ofSeconds(r2));
                                }
                            });
                        }
                    });
                    return;
                }
                android.telephony.satellite.SatelliteManager.loge("KEY_SATELLITE_NEXT_VISIBILITY does not exist.");
                java.util.concurrent.Executor executor2 = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda5
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            java.util.concurrent.Executor executor3 = this.val$executor;
            final android.os.OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(r2));
                        }
                    });
                }
            });
        }
    }

    public void setDeviceAlignedWithSatellite(boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setDeviceAlignedWithSatellite(this.mSubId, z);
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("setDeviceAlignedWithSatellite() RemoteException:" + e);
            e.rethrowAsRuntimeException();
        }
    }

    public void requestAttachEnabledForCarrier(int i, boolean z, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        if (z) {
            removeAttachRestrictionForCarrier(i, 0, executor, consumer);
        } else {
            addAttachRestrictionForCarrier(i, 0, executor, consumer);
        }
    }

    public void requestIsAttachEnabledForCarrier(int i, java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<java.lang.Boolean, android.telephony.satellite.SatelliteManager.SatelliteException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        final java.util.Set<java.lang.Integer> attachRestrictionReasonsForCarrier = getAttachRestrictionReasonsForCarrier(i);
        executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda49
            @Override // java.lang.Runnable
            public final void run() {
                android.os.OutcomeReceiver outcomeReceiver2 = android.os.OutcomeReceiver.this;
                java.util.Set set = attachRestrictionReasonsForCarrier;
                outcomeReceiver2.onResult(java.lang.Boolean.valueOf(!set.contains(0)));
            }
        });
    }

    public void addAttachRestrictionForCarrier(int i, int i2, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        if (!android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            throw new java.lang.IllegalArgumentException("Invalid subscription ID");
        }
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.addAttachRestrictionForCarrier(i, i2, new android.telephony.satellite.SatelliteManager.AnonymousClass20(executor, consumer));
            } else {
                loge("addAttachRestrictionForCarrier() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda33
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda39
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                r1.accept(23);
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("addAttachRestrictionForCarrier() RemoteException:" + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda34
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda44
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(23);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$20, reason: invalid class name */
    class AnonymousClass20 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$resultListener;

        AnonymousClass20(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$resultListener;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    public void removeAttachRestrictionForCarrier(int i, int i2, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        if (!android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            throw new java.lang.IllegalArgumentException("Invalid subscription ID");
        }
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.removeAttachRestrictionForCarrier(i, i2, new android.telephony.satellite.SatelliteManager.AnonymousClass21(executor, consumer));
            } else {
                loge("removeAttachRestrictionForCarrier() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda57
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                r1.accept(23);
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("removeAttachRestrictionForCarrier() RemoteException:" + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda3
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(23);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$21, reason: invalid class name */
    class AnonymousClass21 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$resultListener;

        AnonymousClass21(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$resultListener;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$21$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$21$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    public java.util.Set<java.lang.Integer> getAttachRestrictionReasonsForCarrier(int i) {
        if (!android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            throw new java.lang.IllegalArgumentException("Invalid subscription ID");
        }
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                int[] attachRestrictionReasonsForCarrier = iTelephony.getAttachRestrictionReasonsForCarrier(i);
                if (attachRestrictionReasonsForCarrier.length == 0) {
                    logd("receivedArray is empty, create empty set");
                    return new java.util.HashSet();
                }
                return (java.util.Set) java.util.Arrays.stream(attachRestrictionReasonsForCarrier).boxed().collect(java.util.stream.Collectors.toSet());
            }
            throw new java.lang.IllegalStateException("Telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("getAttachRestrictionReasonsForCarrier() RemoteException: " + e);
            e.rethrowAsRuntimeException();
            return new java.util.HashSet();
        }
    }

    public void requestNtnSignalStrength(java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<android.telephony.satellite.NtnSignalStrength, android.telephony.satellite.SatelliteManager.SatelliteException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestNtnSignalStrength(this.mSubId, new android.telephony.satellite.SatelliteManager.AnonymousClass22(null, executor, outcomeReceiver));
            } else {
                loge("requestNtnSignalStrength() invalid telephony");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda50
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda30
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (android.os.RemoteException e) {
            loge("requestNtnSignalStrength() RemoteException: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda51
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(23));
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$22, reason: invalid class name */
    class AnonymousClass22 extends android.os.ResultReceiver {
        final /* synthetic */ android.os.OutcomeReceiver val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass22(android.os.Handler handler, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, android.os.Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(android.telephony.satellite.SatelliteManager.KEY_NTN_SIGNAL_STRENGTH)) {
                    final android.telephony.satellite.NtnSignalStrength ntnSignalStrength = (android.telephony.satellite.NtnSignalStrength) bundle.getParcelable(android.telephony.satellite.SatelliteManager.KEY_NTN_SIGNAL_STRENGTH, android.telephony.satellite.NtnSignalStrength.class);
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$22$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$22$$ExternalSyntheticLambda4
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    android.os.OutcomeReceiver.this.onResult(r2);
                                }
                            });
                        }
                    });
                    return;
                }
                android.telephony.satellite.SatelliteManager.loge("KEY_NTN_SIGNAL_STRENGTH does not exist.");
                java.util.concurrent.Executor executor2 = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$22$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$22$$ExternalSyntheticLambda5
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            java.util.concurrent.Executor executor3 = this.val$executor;
            final android.os.OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$22$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$22$$ExternalSyntheticLambda3
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.os.OutcomeReceiver.this.onError(new android.telephony.satellite.SatelliteManager.SatelliteException(r2));
                        }
                    });
                }
            });
        }
    }

    public void registerForNtnSignalStrengthChanged(java.util.concurrent.Executor executor, android.telephony.satellite.NtnSignalStrengthCallback ntnSignalStrengthCallback) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(ntnSignalStrengthCallback);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                android.telephony.satellite.SatelliteManager.AnonymousClass23 anonymousClass23 = new android.telephony.satellite.SatelliteManager.AnonymousClass23(executor, ntnSignalStrengthCallback);
                iTelephony.registerForNtnSignalStrengthChanged(this.mSubId, anonymousClass23);
                sNtnSignalStrengthCallbackMap.put(ntnSignalStrengthCallback, anonymousClass23);
                return;
            }
            throw new java.lang.IllegalStateException("Telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("registerForNtnSignalStrengthChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$23, reason: invalid class name */
    class AnonymousClass23 extends android.telephony.satellite.INtnSignalStrengthCallback.Stub {
        final /* synthetic */ android.telephony.satellite.NtnSignalStrengthCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass23(java.util.concurrent.Executor executor, android.telephony.satellite.NtnSignalStrengthCallback ntnSignalStrengthCallback) {
            this.val$executor = executor;
            this.val$callback = ntnSignalStrengthCallback;
        }

        @Override // android.telephony.satellite.INtnSignalStrengthCallback
        public void onNtnSignalStrengthChanged(final android.telephony.satellite.NtnSignalStrength ntnSignalStrength) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.telephony.satellite.NtnSignalStrengthCallback ntnSignalStrengthCallback = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$23$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$23$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.telephony.satellite.NtnSignalStrengthCallback.this.onNtnSignalStrengthChanged(r2);
                        }
                    });
                }
            });
        }
    }

    public void unregisterForNtnSignalStrengthChanged(android.telephony.satellite.NtnSignalStrengthCallback ntnSignalStrengthCallback) {
        java.util.Objects.requireNonNull(ntnSignalStrengthCallback);
        android.telephony.satellite.INtnSignalStrengthCallback remove = sNtnSignalStrengthCallbackMap.remove(ntnSignalStrengthCallback);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (remove != null) {
                    iTelephony.unregisterForNtnSignalStrengthChanged(this.mSubId, remove);
                    return;
                } else {
                    loge("unregisterForNtnSignalStrengthChanged: No internal callback.");
                    throw new java.lang.IllegalArgumentException("callback is not valid");
                }
            }
            throw new java.lang.IllegalStateException("Telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("unregisterForNtnSignalStrengthChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
        }
    }

    public int registerForCapabilitiesChanged(java.util.concurrent.Executor executor, android.telephony.satellite.SatelliteCapabilitiesCallback satelliteCapabilitiesCallback) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(satelliteCapabilitiesCallback);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                android.telephony.satellite.SatelliteManager.AnonymousClass24 anonymousClass24 = new android.telephony.satellite.SatelliteManager.AnonymousClass24(executor, satelliteCapabilitiesCallback);
                sSatelliteCapabilitiesCallbackMap.put(satelliteCapabilitiesCallback, anonymousClass24);
                return iTelephony.registerForCapabilitiesChanged(this.mSubId, anonymousClass24);
            }
            throw new java.lang.IllegalStateException("Telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("registerForCapabilitiesChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$24, reason: invalid class name */
    class AnonymousClass24 extends android.telephony.satellite.ISatelliteCapabilitiesCallback.Stub {
        final /* synthetic */ android.telephony.satellite.SatelliteCapabilitiesCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass24(java.util.concurrent.Executor executor, android.telephony.satellite.SatelliteCapabilitiesCallback satelliteCapabilitiesCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteCapabilitiesCallback;
        }

        @Override // android.telephony.satellite.ISatelliteCapabilitiesCallback
        public void onSatelliteCapabilitiesChanged(final android.telephony.satellite.SatelliteCapabilities satelliteCapabilities) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.telephony.satellite.SatelliteCapabilitiesCallback satelliteCapabilitiesCallback = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.satellite.SatelliteManager$24$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$24$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.telephony.satellite.SatelliteCapabilitiesCallback.this.onSatelliteCapabilitiesChanged(r2);
                        }
                    });
                }
            });
        }
    }

    public void unregisterForCapabilitiesChanged(android.telephony.satellite.SatelliteCapabilitiesCallback satelliteCapabilitiesCallback) {
        java.util.Objects.requireNonNull(satelliteCapabilitiesCallback);
        android.telephony.satellite.ISatelliteCapabilitiesCallback remove = sSatelliteCapabilitiesCallbackMap.remove(satelliteCapabilitiesCallback);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (remove != null) {
                    iTelephony.unregisterForCapabilitiesChanged(this.mSubId, remove);
                } else {
                    loge("unregisterForCapabilitiesChanged: No internal callback.");
                }
                return;
            }
            throw new java.lang.IllegalStateException("Telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("unregisterForCapabilitiesChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
        }
    }

    public java.util.List<java.lang.String> getSatellitePlmnsForCarrier(int i) {
        if (!android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            throw new java.lang.IllegalArgumentException("Invalid subscription ID");
        }
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSatellitePlmnsForCarrier(i);
            }
            throw new java.lang.IllegalStateException("Telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("getSatellitePlmnsForCarrier() RemoteException: " + e);
            e.rethrowAsRuntimeException();
            return new java.util.ArrayList();
        }
    }

    private static com.android.internal.telephony.ITelephony getITelephony() {
        return com.android.internal.telephony.ITelephony.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logd(java.lang.String str) {
        com.android.telephony.Rlog.d(TAG, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void loge(java.lang.String str) {
        com.android.telephony.Rlog.e(TAG, str);
    }
}
