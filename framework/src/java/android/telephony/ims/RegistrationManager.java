package android.telephony.ims;

/* loaded from: classes3.dex */
public interface RegistrationManager {
    public static final java.util.Map<java.lang.Integer, java.lang.Integer> IMS_REG_TO_ACCESS_TYPE_MAP = java.util.Map.of(-1, -1, 0, 1, 3, 1, 1, 2, 2, 2);
    public static final int REGISTRATION_STATE_NOT_REGISTERED = 0;
    public static final int REGISTRATION_STATE_REGISTERED = 2;
    public static final int REGISTRATION_STATE_REGISTERING = 1;

    @android.annotation.SystemApi
    public static final int SUGGESTED_ACTION_NONE = 0;

    @android.annotation.SystemApi
    public static final int SUGGESTED_ACTION_TRIGGER_CLEAR_RAT_BLOCKS = 4;

    @android.annotation.SystemApi
    public static final int SUGGESTED_ACTION_TRIGGER_PLMN_BLOCK = 1;

    @android.annotation.SystemApi
    public static final int SUGGESTED_ACTION_TRIGGER_PLMN_BLOCK_WITH_TIMEOUT = 2;

    @android.annotation.SystemApi
    public static final int SUGGESTED_ACTION_TRIGGER_RAT_BLOCK = 3;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImsRegistrationState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SuggestedAction {
    }

    void getRegistrationState(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer);

    void getRegistrationTransportType(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer);

    void registerImsRegistrationCallback(java.util.concurrent.Executor executor, android.telephony.ims.RegistrationManager.RegistrationCallback registrationCallback) throws android.telephony.ims.ImsException;

    void unregisterImsRegistrationCallback(android.telephony.ims.RegistrationManager.RegistrationCallback registrationCallback);

    static java.lang.String registrationStateToString(int i) {
        switch (i) {
            case 0:
                return "REGISTRATION_STATE_NOT_REGISTERED";
            case 1:
                return "REGISTRATION_STATE_REGISTERING";
            case 2:
                return "REGISTRATION_STATE_REGISTERED";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    static int getAccessType(int i) {
        if (!IMS_REG_TO_ACCESS_TYPE_MAP.containsKey(java.lang.Integer.valueOf(i))) {
            android.util.Log.w("RegistrationManager", "getAccessType - invalid regType returned: " + i);
            return -1;
        }
        return IMS_REG_TO_ACCESS_TYPE_MAP.get(java.lang.Integer.valueOf(i)).intValue();
    }

    public static class RegistrationCallback {
        private final android.telephony.ims.RegistrationManager.RegistrationCallback.RegistrationBinder mBinder = new android.telephony.ims.RegistrationManager.RegistrationCallback.RegistrationBinder(this);

        /* JADX INFO: Access modifiers changed from: private */
        static class RegistrationBinder extends android.telephony.ims.aidl.IImsRegistrationCallback.Stub {
            private android.os.Bundle mBundle = new android.os.Bundle();
            private java.util.concurrent.Executor mExecutor;
            private final android.telephony.ims.RegistrationManager.RegistrationCallback mLocalCallback;

            RegistrationBinder(android.telephony.ims.RegistrationManager.RegistrationCallback registrationCallback) {
                this.mLocalCallback = registrationCallback;
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onRegistered(final android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) {
                if (this.mLocalCallback == null) {
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.RegistrationManager$RegistrationCallback$RegistrationBinder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.RegistrationManager.RegistrationCallback.RegistrationBinder.this.lambda$onRegistered$0(imsRegistrationAttributes);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onRegistered$0(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) {
                this.mLocalCallback.onRegistered(imsRegistrationAttributes);
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onRegistering(final android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) {
                if (this.mLocalCallback == null) {
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.RegistrationManager$RegistrationCallback$RegistrationBinder$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.RegistrationManager.RegistrationCallback.RegistrationBinder.this.lambda$onRegistering$1(imsRegistrationAttributes);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onRegistering$1(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) {
                this.mLocalCallback.onRegistering(imsRegistrationAttributes);
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onDeregistered(final android.telephony.ims.ImsReasonInfo imsReasonInfo, final int i, final int i2) {
                if (this.mLocalCallback == null) {
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.RegistrationManager$RegistrationCallback$RegistrationBinder$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.RegistrationManager.RegistrationCallback.RegistrationBinder.this.lambda$onDeregistered$2(imsReasonInfo, i, i2);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDeregistered$2(android.telephony.ims.ImsReasonInfo imsReasonInfo, int i, int i2) {
                this.mLocalCallback.onUnregistered(imsReasonInfo, i, i2);
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onDeregisteredWithDetails(final android.telephony.ims.ImsReasonInfo imsReasonInfo, final int i, final int i2, final android.telephony.ims.SipDetails sipDetails) {
                if (this.mLocalCallback == null) {
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.RegistrationManager$RegistrationCallback$RegistrationBinder$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.RegistrationManager.RegistrationCallback.RegistrationBinder.this.lambda$onDeregisteredWithDetails$3(imsReasonInfo, i, i2);
                        }
                    });
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.RegistrationManager$RegistrationCallback$RegistrationBinder$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.RegistrationManager.RegistrationCallback.RegistrationBinder.this.lambda$onDeregisteredWithDetails$4(imsReasonInfo, sipDetails);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDeregisteredWithDetails$3(android.telephony.ims.ImsReasonInfo imsReasonInfo, int i, int i2) {
                this.mLocalCallback.onUnregistered(imsReasonInfo, i, i2);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDeregisteredWithDetails$4(android.telephony.ims.ImsReasonInfo imsReasonInfo, android.telephony.ims.SipDetails sipDetails) {
                this.mLocalCallback.onUnregistered(imsReasonInfo, sipDetails);
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onTechnologyChangeFailed(final int i, final android.telephony.ims.ImsReasonInfo imsReasonInfo) {
                if (this.mLocalCallback == null) {
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.RegistrationManager$RegistrationCallback$RegistrationBinder$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.RegistrationManager.RegistrationCallback.RegistrationBinder.this.lambda$onTechnologyChangeFailed$5(i, imsReasonInfo);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onTechnologyChangeFailed$5(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
                this.mLocalCallback.onTechnologyChangeFailed(android.telephony.ims.RegistrationManager.getAccessType(i), imsReasonInfo);
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onSubscriberAssociatedUriChanged(final android.net.Uri[] uriArr) {
                if (this.mLocalCallback == null) {
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.RegistrationManager$RegistrationCallback$RegistrationBinder$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.RegistrationManager.RegistrationCallback.RegistrationBinder.this.lambda$onSubscriberAssociatedUriChanged$6(uriArr);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onSubscriberAssociatedUriChanged$6(android.net.Uri[] uriArr) {
                this.mLocalCallback.onSubscriberAssociatedUriChanged(uriArr);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void setExecutor(java.util.concurrent.Executor executor) {
                this.mExecutor = executor;
            }
        }

        @java.lang.Deprecated
        public void onRegistered(int i) {
        }

        public void onRegistered(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) {
            onRegistered(imsRegistrationAttributes.getTransportType());
        }

        public void onRegistering(int i) {
        }

        public void onRegistering(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) {
            onRegistering(imsRegistrationAttributes.getTransportType());
        }

        public void onUnregistered(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void onUnregistered(android.telephony.ims.ImsReasonInfo imsReasonInfo, int i, int i2) {
            onUnregistered(imsReasonInfo);
        }

        @android.annotation.SystemApi
        public void onUnregistered(android.telephony.ims.ImsReasonInfo imsReasonInfo, android.telephony.ims.SipDetails sipDetails) {
        }

        public void onTechnologyChangeFailed(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        public void onSubscriberAssociatedUriChanged(android.net.Uri[] uriArr) {
        }

        public final android.telephony.ims.aidl.IImsRegistrationCallback getBinder() {
            return this.mBinder;
        }

        public void setExecutor(java.util.concurrent.Executor executor) {
            this.mBinder.setExecutor(executor);
        }
    }
}
