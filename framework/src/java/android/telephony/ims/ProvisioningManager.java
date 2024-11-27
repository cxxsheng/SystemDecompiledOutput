package android.telephony.ims;

/* loaded from: classes3.dex */
public class ProvisioningManager {

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_RCS_SINGLE_REGISTRATION_CAPABILITY_UPDATE = "android.telephony.ims.action.RCS_SINGLE_REGISTRATION_CAPABILITY_UPDATE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_STATUS = "android.telephony.ims.extra.STATUS";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_SUBSCRIPTION_ID = "android.telephony.ims.extra.SUBSCRIPTION_ID";
    public static final int KEY_1X_EPDG_TIMER_SEC = 64;
    public static final int KEY_1X_THRESHOLD = 59;
    public static final int KEY_AMR_BANDWIDTH_EFFICIENT_PAYLOAD_TYPE = 50;
    public static final int KEY_AMR_CODEC_MODE_SET_VALUES = 0;
    public static final int KEY_AMR_DEFAULT_ENCODING_MODE = 53;
    public static final int KEY_AMR_OCTET_ALIGNED_PAYLOAD_TYPE = 49;
    public static final int KEY_AMR_WB_BANDWIDTH_EFFICIENT_PAYLOAD_TYPE = 48;
    public static final int KEY_AMR_WB_CODEC_MODE_SET_VALUES = 1;
    public static final int KEY_AMR_WB_OCTET_ALIGNED_PAYLOAD_TYPE = 47;
    public static final int KEY_DTMF_NB_PAYLOAD_TYPE = 52;
    public static final int KEY_DTMF_WB_PAYLOAD_TYPE = 51;
    public static final int KEY_EAB_PROVISIONING_STATUS = 25;
    public static final int KEY_ENABLE_SILENT_REDIAL = 6;
    public static final int KEY_LOCAL_BREAKOUT_PCSCF_ADDRESS = 31;
    public static final int KEY_LTE_EPDG_TIMER_SEC = 62;
    public static final int KEY_LTE_THRESHOLD_1 = 56;
    public static final int KEY_LTE_THRESHOLD_2 = 57;
    public static final int KEY_LTE_THRESHOLD_3 = 58;
    public static final int KEY_MINIMUM_SIP_SESSION_EXPIRATION_TIMER_SEC = 3;
    public static final int KEY_MOBILE_DATA_ENABLED = 29;
    public static final int KEY_MULTIENDPOINT_ENABLED = 65;
    public static final int KEY_RCS_AVAILABILITY_CACHE_EXPIRATION_SEC = 19;
    public static final int KEY_RCS_CAPABILITIES_CACHE_EXPIRATION_SEC = 18;
    public static final int KEY_RCS_CAPABILITIES_POLL_INTERVAL_SEC = 20;
    public static final int KEY_RCS_CAPABILITY_DISCOVERY_ENABLED = 17;
    public static final int KEY_RCS_CAPABILITY_POLL_LIST_SUB_EXP_SEC = 23;
    public static final int KEY_RCS_MAX_NUM_ENTRIES_IN_RCL = 22;
    public static final int KEY_RCS_PUBLISH_OFFLINE_AVAILABILITY_TIMER_SEC = 16;
    public static final int KEY_RCS_PUBLISH_SOURCE_THROTTLE_MS = 21;
    public static final int KEY_RCS_PUBLISH_TIMER_SEC = 15;
    public static final int KEY_REGISTRATION_DOMAIN_NAME = 12;
    public static final int KEY_REGISTRATION_RETRY_BASE_TIME_SEC = 33;
    public static final int KEY_REGISTRATION_RETRY_MAX_TIME_SEC = 34;
    public static final int KEY_RTP_SPEECH_END_PORT = 36;
    public static final int KEY_RTP_SPEECH_START_PORT = 35;
    public static final int KEY_RTT_ENABLED = 66;
    public static final int KEY_SIP_ACK_RECEIPT_WAIT_TIME_MS = 43;
    public static final int KEY_SIP_ACK_RETRANSMIT_WAIT_TIME_MS = 44;
    public static final int KEY_SIP_INVITE_ACK_WAIT_TIME_MS = 38;
    public static final int KEY_SIP_INVITE_CANCELLATION_TIMER_MS = 4;
    public static final int KEY_SIP_INVITE_REQUEST_TRANSMIT_INTERVAL_MS = 37;
    public static final int KEY_SIP_INVITE_RESPONSE_RETRANSMIT_INTERVAL_MS = 42;
    public static final int KEY_SIP_INVITE_RESPONSE_RETRANSMIT_WAIT_TIME_MS = 39;
    public static final int KEY_SIP_KEEP_ALIVE_ENABLED = 32;
    public static final int KEY_SIP_NON_INVITE_REQUEST_RETRANSMISSION_WAIT_TIME_MS = 45;
    public static final int KEY_SIP_NON_INVITE_REQUEST_RETRANSMIT_INTERVAL_MS = 40;
    public static final int KEY_SIP_NON_INVITE_RESPONSE_RETRANSMISSION_WAIT_TIME_MS = 46;
    public static final int KEY_SIP_NON_INVITE_TRANSACTION_TIMEOUT_TIMER_MS = 41;
    public static final int KEY_SIP_SESSION_TIMER_SEC = 2;
    public static final int KEY_SMS_FORMAT = 13;
    public static final int KEY_SMS_OVER_IP_ENABLED = 14;
    public static final int KEY_SMS_PUBLIC_SERVICE_IDENTITY = 54;
    public static final int KEY_T1_TIMER_VALUE_MS = 7;
    public static final int KEY_T2_TIMER_VALUE_MS = 8;
    public static final int KEY_TF_TIMER_VALUE_MS = 9;
    public static final int KEY_TRANSITION_TO_LTE_DELAY_MS = 5;
    public static final int KEY_USE_GZIP_FOR_LIST_SUBSCRIPTION = 24;
    public static final int KEY_VIDEO_QUALITY = 55;
    public static final int KEY_VOICE_OVER_WIFI_ENABLED_OVERRIDE = 28;

    @android.annotation.SystemApi
    public static final int KEY_VOICE_OVER_WIFI_ENTITLEMENT_ID = 67;

    @android.annotation.SystemApi
    public static final int KEY_VOICE_OVER_WIFI_MODE_OVERRIDE = 27;

    @android.annotation.SystemApi
    public static final int KEY_VOICE_OVER_WIFI_ROAMING_ENABLED_OVERRIDE = 26;
    public static final int KEY_VOIMS_OPT_IN_STATUS = 68;
    public static final int KEY_VOLTE_PROVISIONING_STATUS = 10;
    public static final int KEY_VOLTE_USER_OPT_IN_STATUS = 30;
    public static final int KEY_VT_PROVISIONING_STATUS = 11;
    public static final int KEY_WIFI_EPDG_TIMER_SEC = 63;
    public static final int KEY_WIFI_THRESHOLD_A = 60;
    public static final int KEY_WIFI_THRESHOLD_B = 61;
    public static final int PROVISIONING_RESULT_UNKNOWN = -1;

    @android.annotation.SystemApi
    public static final int PROVISIONING_VALUE_DISABLED = 0;

    @android.annotation.SystemApi
    public static final int PROVISIONING_VALUE_ENABLED = 1;
    public static final int SMS_FORMAT_3GPP = 1;
    public static final int SMS_FORMAT_3GPP2 = 0;

    @android.annotation.SystemApi
    public static final int STATUS_CAPABLE = 0;

    @android.annotation.SystemApi
    public static final int STATUS_CARRIER_NOT_CAPABLE = 2;

    @android.annotation.SystemApi
    public static final int STATUS_DEVICE_NOT_CAPABLE = 1;

    @android.annotation.SystemApi
    public static final java.lang.String STRING_QUERY_RESULT_ERROR_GENERIC = "STRING_QUERY_RESULT_ERROR_GENERIC";

    @android.annotation.SystemApi
    public static final java.lang.String STRING_QUERY_RESULT_ERROR_NOT_READY = "STRING_QUERY_RESULT_ERROR_NOT_READY";
    private static final java.lang.String TAG = "ProvisioningManager";
    public static final int VIDEO_QUALITY_HIGH = 1;
    public static final int VIDEO_QUALITY_LOW = 0;
    private int mSubId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StringResultError {
    }

    @android.annotation.SystemApi
    public static class Callback {
        private final android.telephony.ims.ProvisioningManager.Callback.CallbackBinder mBinder = new android.telephony.ims.ProvisioningManager.Callback.CallbackBinder();

        /* JADX INFO: Access modifiers changed from: private */
        static class CallbackBinder extends android.telephony.ims.aidl.IImsConfigCallback.Stub {
            private java.util.concurrent.Executor mExecutor;
            private final android.telephony.ims.ProvisioningManager.Callback mLocalConfigurationCallback;

            private CallbackBinder(android.telephony.ims.ProvisioningManager.Callback callback) {
                this.mLocalConfigurationCallback = callback;
            }

            @Override // android.telephony.ims.aidl.IImsConfigCallback
            public final void onIntConfigChanged(final int i, final int i2) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ProvisioningManager$Callback$CallbackBinder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.ProvisioningManager.Callback.CallbackBinder.this.lambda$onIntConfigChanged$0(i, i2);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onIntConfigChanged$0(int i, int i2) {
                this.mLocalConfigurationCallback.onProvisioningIntChanged(i, i2);
            }

            @Override // android.telephony.ims.aidl.IImsConfigCallback
            public final void onStringConfigChanged(final int i, final java.lang.String str) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ProvisioningManager$Callback$CallbackBinder$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.ProvisioningManager.Callback.CallbackBinder.this.lambda$onStringConfigChanged$1(i, str);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onStringConfigChanged$1(int i, java.lang.String str) {
                this.mLocalConfigurationCallback.onProvisioningStringChanged(i, str);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void setExecutor(java.util.concurrent.Executor executor) {
                this.mExecutor = executor;
            }
        }

        public void onProvisioningIntChanged(int i, int i2) {
        }

        public void onProvisioningStringChanged(int i, java.lang.String str) {
        }

        public final android.telephony.ims.aidl.IImsConfigCallback getBinder() {
            return this.mBinder;
        }

        public void setExecutor(java.util.concurrent.Executor executor) {
            this.mBinder.setExecutor(executor);
        }
    }

    public static abstract class FeatureProvisioningCallback {
        private final android.telephony.ims.ProvisioningManager.FeatureProvisioningCallback.CallbackBinder mBinder = new android.telephony.ims.ProvisioningManager.FeatureProvisioningCallback.CallbackBinder();

        public abstract void onFeatureProvisioningChanged(int i, int i2, boolean z);

        public abstract void onRcsFeatureProvisioningChanged(int i, int i2, boolean z);

        /* JADX INFO: Access modifiers changed from: private */
        static class CallbackBinder extends android.telephony.ims.aidl.IFeatureProvisioningCallback.Stub {
            private java.util.concurrent.Executor mExecutor;
            private final android.telephony.ims.ProvisioningManager.FeatureProvisioningCallback mFeatureProvisioningCallback;

            private CallbackBinder(android.telephony.ims.ProvisioningManager.FeatureProvisioningCallback featureProvisioningCallback) {
                this.mFeatureProvisioningCallback = featureProvisioningCallback;
            }

            @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
            public final void onFeatureProvisioningChanged(final int i, final int i2, final boolean z) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ProvisioningManager$FeatureProvisioningCallback$CallbackBinder$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.ProvisioningManager.FeatureProvisioningCallback.CallbackBinder.this.lambda$onFeatureProvisioningChanged$0(i, i2, z);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onFeatureProvisioningChanged$0(int i, int i2, boolean z) {
                this.mFeatureProvisioningCallback.onFeatureProvisioningChanged(i, i2, z);
            }

            @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
            public final void onRcsFeatureProvisioningChanged(final int i, final int i2, final boolean z) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ProvisioningManager$FeatureProvisioningCallback$CallbackBinder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.ProvisioningManager.FeatureProvisioningCallback.CallbackBinder.this.lambda$onRcsFeatureProvisioningChanged$1(i, i2, z);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onRcsFeatureProvisioningChanged$1(int i, int i2, boolean z) {
                this.mFeatureProvisioningCallback.onRcsFeatureProvisioningChanged(i, i2, z);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void setExecutor(java.util.concurrent.Executor executor) {
                this.mExecutor = executor;
            }
        }

        public final android.telephony.ims.aidl.IFeatureProvisioningCallback getBinder() {
            return this.mBinder;
        }

        public void setExecutor(java.util.concurrent.Executor executor) {
            this.mBinder.setExecutor(executor);
        }
    }

    @android.annotation.SystemApi
    public static class RcsProvisioningCallback {
        private final android.telephony.ims.ProvisioningManager.RcsProvisioningCallback.CallbackBinder mBinder = new android.telephony.ims.ProvisioningManager.RcsProvisioningCallback.CallbackBinder();

        /* JADX INFO: Access modifiers changed from: private */
        static class CallbackBinder extends android.telephony.ims.aidl.IRcsConfigCallback.Stub {
            private java.util.concurrent.Executor mExecutor;
            private final android.telephony.ims.ProvisioningManager.RcsProvisioningCallback mLocalCallback;

            private CallbackBinder(android.telephony.ims.ProvisioningManager.RcsProvisioningCallback rcsProvisioningCallback) {
                this.mLocalCallback = rcsProvisioningCallback;
            }

            @Override // android.telephony.ims.aidl.IRcsConfigCallback
            public void onConfigurationChanged(final byte[] bArr) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ProvisioningManager$RcsProvisioningCallback$CallbackBinder$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.ProvisioningManager.RcsProvisioningCallback.CallbackBinder.this.lambda$onConfigurationChanged$0(bArr);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onConfigurationChanged$0(byte[] bArr) {
                this.mLocalCallback.onConfigurationChanged(bArr);
            }

            @Override // android.telephony.ims.aidl.IRcsConfigCallback
            public void onAutoConfigurationErrorReceived(final int i, final java.lang.String str) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ProvisioningManager$RcsProvisioningCallback$CallbackBinder$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.ProvisioningManager.RcsProvisioningCallback.CallbackBinder.this.lambda$onAutoConfigurationErrorReceived$1(i, str);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onAutoConfigurationErrorReceived$1(int i, java.lang.String str) {
                this.mLocalCallback.onAutoConfigurationErrorReceived(i, str);
            }

            @Override // android.telephony.ims.aidl.IRcsConfigCallback
            public void onConfigurationReset() {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ProvisioningManager$RcsProvisioningCallback$CallbackBinder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.ProvisioningManager.RcsProvisioningCallback.CallbackBinder.this.lambda$onConfigurationReset$2();
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onConfigurationReset$2() {
                this.mLocalCallback.onConfigurationReset();
            }

            @Override // android.telephony.ims.aidl.IRcsConfigCallback
            public void onRemoved() {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ProvisioningManager$RcsProvisioningCallback$CallbackBinder$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.ProvisioningManager.RcsProvisioningCallback.CallbackBinder.this.lambda$onRemoved$3();
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onRemoved$3() {
                this.mLocalCallback.onRemoved();
            }

            @Override // android.telephony.ims.aidl.IRcsConfigCallback
            public void onPreProvisioningReceived(final byte[] bArr) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ProvisioningManager$RcsProvisioningCallback$CallbackBinder$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.ProvisioningManager.RcsProvisioningCallback.CallbackBinder.this.lambda$onPreProvisioningReceived$4(bArr);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onPreProvisioningReceived$4(byte[] bArr) {
                this.mLocalCallback.onPreProvisioningReceived(bArr);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void setExecutor(java.util.concurrent.Executor executor) {
                this.mExecutor = executor;
            }
        }

        public void onConfigurationChanged(byte[] bArr) {
        }

        public void onAutoConfigurationErrorReceived(int i, java.lang.String str) {
        }

        public void onConfigurationReset() {
        }

        public void onRemoved() {
        }

        public void onPreProvisioningReceived(byte[] bArr) {
        }

        public final android.telephony.ims.aidl.IRcsConfigCallback getBinder() {
            return this.mBinder;
        }

        public void setExecutor(java.util.concurrent.Executor executor) {
            this.mBinder.setExecutor(executor);
        }
    }

    @android.annotation.SystemApi
    public static android.telephony.ims.ProvisioningManager createForSubscriptionId(int i) {
        if (!android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            throw new java.lang.IllegalArgumentException("Invalid subscription ID");
        }
        return new android.telephony.ims.ProvisioningManager(i);
    }

    public ProvisioningManager(int i) {
        this.mSubId = i;
    }

    @android.annotation.SystemApi
    public void registerProvisioningChangedCallback(java.util.concurrent.Executor executor, android.telephony.ims.ProvisioningManager.Callback callback) throws android.telephony.ims.ImsException {
        callback.setExecutor(executor);
        try {
            getITelephony().registerImsProvisioningChangedCallback(this.mSubId, callback.getBinder());
        } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @android.annotation.SystemApi
    public void unregisterProvisioningChangedCallback(android.telephony.ims.ProvisioningManager.Callback callback) {
        try {
            getITelephony().unregisterImsProvisioningChangedCallback(this.mSubId, callback.getBinder());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void registerFeatureProvisioningChangedCallback(java.util.concurrent.Executor executor, android.telephony.ims.ProvisioningManager.FeatureProvisioningCallback featureProvisioningCallback) throws android.telephony.ims.ImsException {
        featureProvisioningCallback.setExecutor(executor);
        try {
            getITelephony().registerFeatureProvisioningChangedCallback(this.mSubId, featureProvisioningCallback.getBinder());
        } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    public void unregisterFeatureProvisioningChangedCallback(android.telephony.ims.ProvisioningManager.FeatureProvisioningCallback featureProvisioningCallback) {
        try {
            getITelephony().unregisterFeatureProvisioningChangedCallback(this.mSubId, featureProvisioningCallback.getBinder());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public int getProvisioningIntValue(int i) {
        try {
            return getITelephony().getImsProvisioningInt(this.mSubId, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public java.lang.String getProvisioningStringValue(int i) {
        try {
            return getITelephony().getImsProvisioningString(this.mSubId, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public int setProvisioningIntValue(int i, int i2) {
        try {
            return getITelephony().setImsProvisioningInt(this.mSubId, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public int setProvisioningStringValue(int i, java.lang.String str) {
        try {
            return getITelephony().setImsProvisioningString(this.mSubId, i, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setProvisioningStatusForCapability(int i, int i2, boolean z) {
        try {
            getITelephony().setImsProvisioningStatusForCapability(this.mSubId, i, i2, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean getProvisioningStatusForCapability(int i, int i2) {
        try {
            return getITelephony().getImsProvisioningStatusForCapability(this.mSubId, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean getRcsProvisioningStatusForCapability(int i) {
        try {
            return getITelephony().getRcsProvisioningStatusForCapability(this.mSubId, i, 0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean getRcsProvisioningStatusForCapability(int i, int i2) {
        try {
            return getITelephony().getRcsProvisioningStatusForCapability(this.mSubId, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setRcsProvisioningStatusForCapability(int i, boolean z) {
        try {
            getITelephony().setRcsProvisioningStatusForCapability(this.mSubId, i, 0, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setRcsProvisioningStatusForCapability(int i, int i2, boolean z) {
        try {
            getITelephony().setRcsProvisioningStatusForCapability(this.mSubId, i, i2, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean isProvisioningRequiredForCapability(int i, int i2) {
        try {
            return getITelephony().isProvisioningRequiredForCapability(this.mSubId, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean isRcsProvisioningRequiredForCapability(int i, int i2) {
        try {
            return getITelephony().isRcsProvisioningRequiredForCapability(this.mSubId, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public void notifyRcsAutoConfigurationReceived(byte[] bArr, boolean z) {
        if (bArr == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null config XML file.");
        }
        try {
            getITelephony().notifyRcsAutoConfigurationReceived(this.mSubId, bArr, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public void setRcsClientConfiguration(android.telephony.ims.RcsClientConfiguration rcsClientConfiguration) throws android.telephony.ims.ImsException {
        try {
            getITelephony().setRcsClientConfiguration(this.mSubId, rcsClientConfiguration);
        } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @android.annotation.SystemApi
    public boolean isRcsVolteSingleRegistrationCapable() throws android.telephony.ims.ImsException {
        try {
            return getITelephony().isRcsVolteSingleRegistrationCapable(this.mSubId);
        } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @android.annotation.SystemApi
    public void registerRcsProvisioningCallback(java.util.concurrent.Executor executor, android.telephony.ims.ProvisioningManager.RcsProvisioningCallback rcsProvisioningCallback) throws android.telephony.ims.ImsException {
        rcsProvisioningCallback.setExecutor(executor);
        try {
            getITelephony().registerRcsProvisioningCallback(this.mSubId, rcsProvisioningCallback.getBinder());
        } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @android.annotation.SystemApi
    public void unregisterRcsProvisioningCallback(android.telephony.ims.ProvisioningManager.RcsProvisioningCallback rcsProvisioningCallback) {
        try {
            getITelephony().unregisterRcsProvisioningCallback(this.mSubId, rcsProvisioningCallback.getBinder());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public void triggerRcsReconfiguration() {
        try {
            getITelephony().triggerRcsReconfiguration(this.mSubId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    private static com.android.internal.telephony.ITelephony getITelephony() {
        com.android.internal.telephony.ITelephony asInterface = com.android.internal.telephony.ITelephony.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
        if (asInterface == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        return asInterface;
    }
}
