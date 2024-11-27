package android.service.euicc;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class EuiccService extends android.app.Service {
    public static final java.lang.String ACTION_BIND_CARRIER_PROVISIONING_SERVICE = "android.service.euicc.action.BIND_CARRIER_PROVISIONING_SERVICE";
    public static final java.lang.String ACTION_CONVERT_TO_EMBEDDED_SUBSCRIPTION = "android.service.euicc.action.CONVERT_TO_EMBEDDED_SUBSCRIPTION";
    public static final java.lang.String ACTION_DELETE_SUBSCRIPTION_PRIVILEGED = "android.service.euicc.action.DELETE_SUBSCRIPTION_PRIVILEGED";
    public static final java.lang.String ACTION_MANAGE_EMBEDDED_SUBSCRIPTIONS = "android.service.euicc.action.MANAGE_EMBEDDED_SUBSCRIPTIONS";
    public static final java.lang.String ACTION_PROVISION_EMBEDDED_SUBSCRIPTION = "android.service.euicc.action.PROVISION_EMBEDDED_SUBSCRIPTION";
    public static final java.lang.String ACTION_RENAME_SUBSCRIPTION_PRIVILEGED = "android.service.euicc.action.RENAME_SUBSCRIPTION_PRIVILEGED";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_RESOLVE_CONFIRMATION_CODE = "android.service.euicc.action.RESOLVE_CONFIRMATION_CODE";
    public static final java.lang.String ACTION_RESOLVE_DEACTIVATE_SIM = "android.service.euicc.action.RESOLVE_DEACTIVATE_SIM";
    public static final java.lang.String ACTION_RESOLVE_NO_PRIVILEGES = "android.service.euicc.action.RESOLVE_NO_PRIVILEGES";
    public static final java.lang.String ACTION_RESOLVE_RESOLVABLE_ERRORS = "android.service.euicc.action.RESOLVE_RESOLVABLE_ERRORS";
    public static final java.lang.String ACTION_START_CARRIER_ACTIVATION = "android.service.euicc.action.START_CARRIER_ACTIVATION";
    public static final java.lang.String ACTION_START_EUICC_ACTIVATION = "android.service.euicc.action.START_EUICC_ACTIVATION";
    public static final java.lang.String ACTION_TOGGLE_SUBSCRIPTION_PRIVILEGED = "android.service.euicc.action.TOGGLE_SUBSCRIPTION_PRIVILEGED";
    public static final java.lang.String ACTION_TRANSFER_EMBEDDED_SUBSCRIPTIONS = "android.service.euicc.action.TRANSFER_EMBEDDED_SUBSCRIPTIONS";
    public static final java.lang.String CATEGORY_EUICC_UI = "android.service.euicc.category.EUICC_UI";
    public static final java.lang.String EUICC_SERVICE_INTERFACE = "android.service.euicc.EuiccService";
    public static final java.lang.String EXTRA_RESOLUTION_ALLOW_POLICY_RULES = "android.service.euicc.extra.RESOLUTION_ALLOW_POLICY_RULES";
    public static final java.lang.String EXTRA_RESOLUTION_CALLING_PACKAGE = "android.service.euicc.extra.RESOLUTION_CALLING_PACKAGE";
    public static final java.lang.String EXTRA_RESOLUTION_CARD_ID = "android.service.euicc.extra.RESOLUTION_CARD_ID";
    public static final java.lang.String EXTRA_RESOLUTION_CONFIRMATION_CODE = "android.service.euicc.extra.RESOLUTION_CONFIRMATION_CODE";
    public static final java.lang.String EXTRA_RESOLUTION_CONFIRMATION_CODE_RETRIED = "android.service.euicc.extra.RESOLUTION_CONFIRMATION_CODE_RETRIED";
    public static final java.lang.String EXTRA_RESOLUTION_CONSENT = "android.service.euicc.extra.RESOLUTION_CONSENT";
    public static final java.lang.String EXTRA_RESOLUTION_PORT_INDEX = "android.service.euicc.extra.RESOLUTION_PORT_INDEX";
    public static final java.lang.String EXTRA_RESOLUTION_SUBSCRIPTION_ID = "android.service.euicc.extra.RESOLUTION_SUBSCRIPTION_ID";
    public static final java.lang.String EXTRA_RESOLUTION_USE_PORT_INDEX = "android.service.euicc.extra.RESOLUTION_USE_PORT_INDEX";
    public static final java.lang.String EXTRA_RESOLVABLE_ERRORS = "android.service.euicc.extra.RESOLVABLE_ERRORS";
    public static final int RESOLVABLE_ERROR_CONFIRMATION_CODE = 1;
    public static final int RESOLVABLE_ERROR_POLICY_RULES = 2;
    public static final int RESULT_FIRST_USER = 1;
    public static final int RESULT_MUST_DEACTIVATE_SIM = -1;

    @java.lang.Deprecated
    public static final int RESULT_NEED_CONFIRMATION_CODE = -2;
    public static final int RESULT_OK = 0;
    public static final int RESULT_RESOLVABLE_ERRORS = -2;
    private static final java.lang.String TAG = "EuiccService";
    private java.util.concurrent.ThreadPoolExecutor mExecutor;
    private final android.service.euicc.IEuiccService.Stub mStubWrapper = new android.service.euicc.EuiccService.IEuiccServiceWrapper();

    public static abstract class OtaStatusChangedCallback {
        public abstract void onOtaStatusChanged(int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResolvableError {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Result {
    }

    public abstract int onDeleteSubscription(int i, java.lang.String str);

    @java.lang.Deprecated
    public abstract int onEraseSubscriptions(int i);

    public abstract android.service.euicc.GetDefaultDownloadableSubscriptionListResult onGetDefaultDownloadableSubscriptionList(int i, boolean z);

    public abstract android.service.euicc.GetDownloadableSubscriptionMetadataResult onGetDownloadableSubscriptionMetadata(int i, android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z);

    public abstract java.lang.String onGetEid(int i);

    public abstract android.telephony.euicc.EuiccInfo onGetEuiccInfo(int i);

    public abstract android.service.euicc.GetEuiccProfileInfoListResult onGetEuiccProfileInfoList(int i);

    public abstract int onGetOtaStatus(int i);

    public abstract int onRetainSubscriptionsForFactoryReset(int i);

    public abstract void onStartOtaIfNecessary(int i, android.service.euicc.EuiccService.OtaStatusChangedCallback otaStatusChangedCallback);

    @java.lang.Deprecated
    public abstract int onSwitchToSubscription(int i, java.lang.String str, boolean z);

    public abstract int onUpdateSubscriptionNickname(int i, java.lang.String str, java.lang.String str2);

    public int encodeSmdxSubjectAndReasonCode(java.lang.String str, java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str) || android.text.TextUtils.isEmpty(str2)) {
            throw new java.lang.IllegalArgumentException("SubjectCode/ReasonCode is empty");
        }
        java.lang.String[] split = str.split("\\.");
        java.lang.String[] split2 = str2.split("\\.");
        if (split.length > 3 || split2.length > 3) {
            throw new java.lang.UnsupportedOperationException("Only three nested layer is supported.");
        }
        int length = 10 << ((3 - split.length) * 4);
        for (java.lang.String str3 : split) {
            int parseInt = java.lang.Integer.parseInt(str3);
            if (parseInt > 15) {
                throw new java.lang.UnsupportedOperationException("SubjectCode exceeds 15");
            }
            length = (length << 4) + parseInt;
        }
        int length2 = length << ((3 - split2.length) * 4);
        for (java.lang.String str4 : split2) {
            int parseInt2 = java.lang.Integer.parseInt(str4);
            if (parseInt2 > 15) {
                throw new java.lang.UnsupportedOperationException("ReasonCode exceeds 15");
            }
            length2 = (length2 << 4) + parseInt2;
        }
        return length2;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mExecutor = new java.util.concurrent.ThreadPoolExecutor(4, 4, 30L, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.LinkedBlockingQueue(), new java.util.concurrent.ThreadFactory() { // from class: android.service.euicc.EuiccService.1
            private final java.util.concurrent.atomic.AtomicInteger mCount = new java.util.concurrent.atomic.AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public java.lang.Thread newThread(java.lang.Runnable runnable) {
                return new java.lang.Thread(runnable, "EuiccService #" + this.mCount.getAndIncrement());
            }
        });
        this.mExecutor.allowCoreThreadTimeOut(true);
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mExecutor.shutdownNow();
        super.onDestroy();
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return this.mStubWrapper;
    }

    public android.service.euicc.GetDownloadableSubscriptionMetadataResult onGetDownloadableSubscriptionMetadata(int i, int i2, android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z) {
        throw new java.lang.UnsupportedOperationException("LPA must override onGetDownloadableSubscriptionMetadata");
    }

    @java.lang.Deprecated
    public android.service.euicc.DownloadSubscriptionResult onDownloadSubscription(int i, android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z, boolean z2, android.os.Bundle bundle) {
        return null;
    }

    public android.service.euicc.DownloadSubscriptionResult onDownloadSubscription(int i, int i2, android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z, boolean z2, android.os.Bundle bundle) {
        throw new java.lang.UnsupportedOperationException("LPA must override onDownloadSubscription");
    }

    @java.lang.Deprecated
    public int onDownloadSubscription(int i, android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z, boolean z2) {
        return Integer.MIN_VALUE;
    }

    public int onSwitchToSubscriptionWithPort(int i, int i2, java.lang.String str, boolean z) {
        throw new java.lang.UnsupportedOperationException("LPA must override onSwitchToSubscriptionWithPort");
    }

    public int onEraseSubscriptions(int i, int i2) {
        throw new java.lang.UnsupportedOperationException("This method must be overridden to enable the ResetOption parameter");
    }

    public long onGetAvailableMemoryInBytes(int i) {
        throw new java.lang.UnsupportedOperationException("The connected LPA does not implementEuiccService#onGetAvailableMemoryInBytes(int)");
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("The connected LPA does not implement EuiccService#dump()");
    }

    public static java.lang.String resultToString(int i) {
        switch (i) {
            case -2:
                return "RESOLVABLE_ERRORS";
            case -1:
                return "MUST_DEACTIVATE_SIM";
            case 0:
                return "OK";
            case 1:
                return "FIRST_USER";
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class IEuiccServiceWrapper extends android.service.euicc.IEuiccService.Stub {
        private IEuiccServiceWrapper() {
        }

        @Override // android.service.euicc.IEuiccService
        public void downloadSubscription(final int i, final int i2, final android.telephony.euicc.DownloadableSubscription downloadableSubscription, final boolean z, final boolean z2, final android.os.Bundle bundle, final android.service.euicc.IDownloadSubscriptionCallback iDownloadSubscriptionCallback) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.1
                @Override // java.lang.Runnable
                public void run() {
                    android.service.euicc.DownloadSubscriptionResult onDownloadSubscription;
                    try {
                        onDownloadSubscription = android.service.euicc.EuiccService.this.onDownloadSubscription(i, i2, downloadableSubscription, z, z2, bundle);
                    } catch (java.lang.AbstractMethodError | java.lang.UnsupportedOperationException e) {
                        android.util.Log.w(android.service.euicc.EuiccService.TAG, "The new onDownloadSubscription(int, int, DownloadableSubscription, boolean, boolean, Bundle) is not implemented. Fall back to the old one.", e);
                        onDownloadSubscription = android.service.euicc.EuiccService.this.onDownloadSubscription(i, downloadableSubscription, z, z2, bundle);
                    }
                    try {
                        iDownloadSubscriptionCallback.onComplete(onDownloadSubscription);
                    } catch (android.os.RemoteException e2) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void getEid(final int i, final android.service.euicc.IGetEidCallback iGetEidCallback) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iGetEidCallback.onSuccess(android.service.euicc.EuiccService.this.onGetEid(i));
                    } catch (android.os.RemoteException e) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void getAvailableMemoryInBytes(final int i, final android.service.euicc.IGetAvailableMemoryInBytesCallback iGetAvailableMemoryInBytesCallback) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService$IEuiccServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.euicc.EuiccService.IEuiccServiceWrapper.this.lambda$getAvailableMemoryInBytes$0(i, iGetAvailableMemoryInBytesCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getAvailableMemoryInBytes$0(int i, android.service.euicc.IGetAvailableMemoryInBytesCallback iGetAvailableMemoryInBytesCallback) {
            java.lang.String message;
            long j;
            try {
                j = android.service.euicc.EuiccService.this.onGetAvailableMemoryInBytes(i);
                message = "";
            } catch (java.lang.UnsupportedOperationException e) {
                message = e.getMessage();
                j = -1;
            }
            try {
                if (!message.isEmpty()) {
                    iGetAvailableMemoryInBytesCallback.onUnsupportedOperationException(message);
                } else {
                    iGetAvailableMemoryInBytesCallback.onSuccess(j);
                }
            } catch (android.os.RemoteException e2) {
            }
        }

        @Override // android.service.euicc.IEuiccService
        public void startOtaIfNecessary(final int i, final android.service.euicc.IOtaStatusChangedCallback iOtaStatusChangedCallback) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.3
                @Override // java.lang.Runnable
                public void run() {
                    android.service.euicc.EuiccService.this.onStartOtaIfNecessary(i, new android.service.euicc.EuiccService.OtaStatusChangedCallback() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.3.1
                        @Override // android.service.euicc.EuiccService.OtaStatusChangedCallback
                        public void onOtaStatusChanged(int i2) {
                            try {
                                iOtaStatusChangedCallback.onOtaStatusChanged(i2);
                            } catch (android.os.RemoteException e) {
                            }
                        }
                    });
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void getOtaStatus(final int i, final android.service.euicc.IGetOtaStatusCallback iGetOtaStatusCallback) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iGetOtaStatusCallback.onSuccess(android.service.euicc.EuiccService.this.onGetOtaStatus(i));
                    } catch (android.os.RemoteException e) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void getDownloadableSubscriptionMetadata(final int i, final int i2, final android.telephony.euicc.DownloadableSubscription downloadableSubscription, final boolean z, final boolean z2, final android.service.euicc.IGetDownloadableSubscriptionMetadataCallback iGetDownloadableSubscriptionMetadataCallback) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.5
                @Override // java.lang.Runnable
                public void run() {
                    android.service.euicc.GetDownloadableSubscriptionMetadataResult onGetDownloadableSubscriptionMetadata;
                    if (z) {
                        try {
                            onGetDownloadableSubscriptionMetadata = android.service.euicc.EuiccService.this.onGetDownloadableSubscriptionMetadata(i, i2, downloadableSubscription, z2);
                        } catch (java.lang.AbstractMethodError | java.lang.UnsupportedOperationException e) {
                            android.util.Log.w(android.service.euicc.EuiccService.TAG, "The new onGetDownloadableSubscriptionMetadata(int, int, DownloadableSubscription, boolean) is not implemented. Fall back to the old one.", e);
                            onGetDownloadableSubscriptionMetadata = android.service.euicc.EuiccService.this.onGetDownloadableSubscriptionMetadata(i, downloadableSubscription, z2);
                        }
                    } else {
                        onGetDownloadableSubscriptionMetadata = android.service.euicc.EuiccService.this.onGetDownloadableSubscriptionMetadata(i, downloadableSubscription, z2);
                    }
                    try {
                        iGetDownloadableSubscriptionMetadataCallback.onComplete(onGetDownloadableSubscriptionMetadata);
                    } catch (android.os.RemoteException e2) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void getDefaultDownloadableSubscriptionList(final int i, final boolean z, final android.service.euicc.IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallback) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iGetDefaultDownloadableSubscriptionListCallback.onComplete(android.service.euicc.EuiccService.this.onGetDefaultDownloadableSubscriptionList(i, z));
                    } catch (android.os.RemoteException e) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void getEuiccProfileInfoList(final int i, final android.service.euicc.IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallback) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iGetEuiccProfileInfoListCallback.onComplete(android.service.euicc.EuiccService.this.onGetEuiccProfileInfoList(i));
                    } catch (android.os.RemoteException e) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void getEuiccInfo(final int i, final android.service.euicc.IGetEuiccInfoCallback iGetEuiccInfoCallback) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iGetEuiccInfoCallback.onSuccess(android.service.euicc.EuiccService.this.onGetEuiccInfo(i));
                    } catch (android.os.RemoteException e) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void deleteSubscription(final int i, final java.lang.String str, final android.service.euicc.IDeleteSubscriptionCallback iDeleteSubscriptionCallback) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.9
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iDeleteSubscriptionCallback.onComplete(android.service.euicc.EuiccService.this.onDeleteSubscription(i, str));
                    } catch (android.os.RemoteException e) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void switchToSubscription(final int i, final int i2, final java.lang.String str, final boolean z, final android.service.euicc.ISwitchToSubscriptionCallback iSwitchToSubscriptionCallback, final boolean z2) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.10
                @Override // java.lang.Runnable
                public void run() {
                    int onSwitchToSubscription;
                    if (z2) {
                        onSwitchToSubscription = android.service.euicc.EuiccService.this.onSwitchToSubscriptionWithPort(i, i2, str, z);
                    } else {
                        onSwitchToSubscription = android.service.euicc.EuiccService.this.onSwitchToSubscription(i, str, z);
                    }
                    try {
                        iSwitchToSubscriptionCallback.onComplete(onSwitchToSubscription);
                    } catch (android.os.RemoteException e) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void updateSubscriptionNickname(final int i, final java.lang.String str, final java.lang.String str2, final android.service.euicc.IUpdateSubscriptionNicknameCallback iUpdateSubscriptionNicknameCallback) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iUpdateSubscriptionNicknameCallback.onComplete(android.service.euicc.EuiccService.this.onUpdateSubscriptionNickname(i, str, str2));
                    } catch (android.os.RemoteException e) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void eraseSubscriptions(final int i, final android.service.euicc.IEraseSubscriptionsCallback iEraseSubscriptionsCallback) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.12
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iEraseSubscriptionsCallback.onComplete(android.service.euicc.EuiccService.this.onEraseSubscriptions(i));
                    } catch (android.os.RemoteException e) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void eraseSubscriptionsWithOptions(final int i, final int i2, final android.service.euicc.IEraseSubscriptionsCallback iEraseSubscriptionsCallback) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.13
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iEraseSubscriptionsCallback.onComplete(android.service.euicc.EuiccService.this.onEraseSubscriptions(i, i2));
                    } catch (android.os.RemoteException e) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void retainSubscriptionsForFactoryReset(final int i, final android.service.euicc.IRetainSubscriptionsForFactoryResetCallback iRetainSubscriptionsForFactoryResetCallback) {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.14
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iRetainSubscriptionsForFactoryResetCallback.onComplete(android.service.euicc.EuiccService.this.onRetainSubscriptionsForFactoryReset(i));
                    } catch (android.os.RemoteException e) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void dump(final android.service.euicc.IEuiccServiceDumpResultCallback iEuiccServiceDumpResultCallback) throws android.os.RemoteException {
            android.service.euicc.EuiccService.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.15
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        java.io.StringWriter stringWriter = new java.io.StringWriter();
                        android.service.euicc.EuiccService.this.dump(new java.io.PrintWriter(stringWriter));
                        iEuiccServiceDumpResultCallback.onComplete(stringWriter.toString());
                    } catch (android.os.RemoteException e) {
                    }
                }
            });
        }
    }
}
