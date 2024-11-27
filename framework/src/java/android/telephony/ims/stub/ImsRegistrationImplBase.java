package android.telephony.ims.stub;

/* loaded from: classes3.dex */
public class ImsRegistrationImplBase {
    private static final java.lang.String LOG_TAG = "ImsRegistrationImplBase";
    public static final int REASON_ALLOWED_NETWORK_TYPES_CHANGED = 3;
    public static final int REASON_HANDOVER_FAILED = 6;
    public static final int REASON_NON_IMS_CAPABLE_NETWORK = 4;
    public static final int REASON_RADIO_POWER_OFF = 5;
    public static final int REASON_SIM_REFRESH = 2;
    public static final int REASON_SIM_REMOVED = 1;
    public static final int REASON_UNKNOWN = 0;
    public static final int REASON_VOPS_NOT_SUPPORTED = 7;
    private static final int REGISTRATION_STATE_UNKNOWN = -1;
    public static final int REGISTRATION_TECH_3G = 4;
    public static final int REGISTRATION_TECH_CROSS_SIM = 2;
    public static final int REGISTRATION_TECH_IWLAN = 1;
    public static final int REGISTRATION_TECH_LTE = 0;
    public static final int REGISTRATION_TECH_MAX = 5;
    public static final int REGISTRATION_TECH_NONE = -1;
    public static final int REGISTRATION_TECH_NR = 3;
    private final android.telephony.ims.aidl.IImsRegistration mBinder;
    private final com.android.internal.telephony.util.RemoteCallbackListExt<android.telephony.ims.aidl.IImsRegistrationCallback> mCallbacks;
    private final com.android.internal.telephony.util.RemoteCallbackListExt<android.telephony.ims.aidl.IImsRegistrationCallback> mEmergencyCallbacks;
    private android.telephony.ims.ImsReasonInfo mEmergencyLastDisconnectCause;
    private int mEmergencyLastDisconnectRadioTech;
    private int mEmergencyLastDisconnectSuggestedAction;
    private android.telephony.ims.ImsRegistrationAttributes mEmergencyRegistrationAttributes;
    private int mEmergencyRegistrationState;
    private java.util.concurrent.Executor mExecutor;
    private android.telephony.ims.ImsReasonInfo mLastDisconnectCause;
    private int mLastDisconnectRadioTech;
    private int mLastDisconnectSuggestedAction;
    private final java.lang.Object mLock;
    private android.telephony.ims.ImsRegistrationAttributes mRegistrationAttributes;
    private int mRegistrationState;
    private android.net.Uri[] mUris;
    private boolean mUrisSet;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImsDeregistrationReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImsRegistrationTech {
    }

    @android.annotation.SystemApi
    public ImsRegistrationImplBase() {
        this.mBinder = new android.telephony.ims.stub.ImsRegistrationImplBase.AnonymousClass1();
        this.mCallbacks = new com.android.internal.telephony.util.RemoteCallbackListExt<>();
        this.mEmergencyCallbacks = new com.android.internal.telephony.util.RemoteCallbackListExt<>();
        this.mLock = new java.lang.Object();
        this.mRegistrationState = -1;
        this.mEmergencyRegistrationState = -1;
        this.mLastDisconnectCause = new android.telephony.ims.ImsReasonInfo();
        this.mEmergencyLastDisconnectCause = new android.telephony.ims.ImsReasonInfo();
        this.mLastDisconnectSuggestedAction = 0;
        this.mEmergencyLastDisconnectSuggestedAction = 0;
        this.mLastDisconnectRadioTech = -1;
        this.mEmergencyLastDisconnectRadioTech = -1;
        this.mUris = new android.net.Uri[0];
        this.mUrisSet = false;
    }

    @android.annotation.SystemApi
    public ImsRegistrationImplBase(java.util.concurrent.Executor executor) {
        this.mBinder = new android.telephony.ims.stub.ImsRegistrationImplBase.AnonymousClass1();
        this.mCallbacks = new com.android.internal.telephony.util.RemoteCallbackListExt<>();
        this.mEmergencyCallbacks = new com.android.internal.telephony.util.RemoteCallbackListExt<>();
        this.mLock = new java.lang.Object();
        this.mRegistrationState = -1;
        this.mEmergencyRegistrationState = -1;
        this.mLastDisconnectCause = new android.telephony.ims.ImsReasonInfo();
        this.mEmergencyLastDisconnectCause = new android.telephony.ims.ImsReasonInfo();
        this.mLastDisconnectSuggestedAction = 0;
        this.mEmergencyLastDisconnectSuggestedAction = 0;
        this.mLastDisconnectRadioTech = -1;
        this.mEmergencyLastDisconnectRadioTech = -1;
        this.mUris = new android.net.Uri[0];
        this.mUrisSet = false;
        this.mExecutor = executor;
    }

    /* renamed from: android.telephony.ims.stub.ImsRegistrationImplBase$1, reason: invalid class name */
    class AnonymousClass1 extends android.telephony.ims.aidl.IImsRegistration.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$getRegistrationTechnology$0() {
            return java.lang.Integer.valueOf(android.telephony.ims.stub.ImsRegistrationImplBase.this.mRegistrationAttributes == null ? -1 : android.telephony.ims.stub.ImsRegistrationImplBase.this.mRegistrationAttributes.getRegistrationTechnology());
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public int getRegistrationTechnology() throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$getRegistrationTechnology$0;
                    lambda$getRegistrationTechnology$0 = android.telephony.ims.stub.ImsRegistrationImplBase.AnonymousClass1.this.lambda$getRegistrationTechnology$0();
                    return lambda$getRegistrationTechnology$0;
                }
            }, "getRegistrationTechnology")).intValue();
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void addRegistrationCallback(final android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsRegistrationImplBase.AnonymousClass1.this.lambda$addRegistrationCallback$1(iImsRegistrationCallback, atomicReference);
                }
            }, "addRegistrationCallback");
            if (atomicReference.get() != null) {
                throw ((android.os.RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addRegistrationCallback$1(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback, java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                android.telephony.ims.stub.ImsRegistrationImplBase.this.addRegistrationCallback(iImsRegistrationCallback);
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void addEmergencyRegistrationCallback(final android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsRegistrationImplBase.AnonymousClass1.this.lambda$addEmergencyRegistrationCallback$2(iImsRegistrationCallback, atomicReference);
                }
            }, "addEmergencyRegistrationCallback");
            if (atomicReference.get() != null) {
                throw ((android.os.RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addEmergencyRegistrationCallback$2(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback, java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                android.telephony.ims.stub.ImsRegistrationImplBase.this.addEmergencyRegistrationCallback(iImsRegistrationCallback);
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void removeEmergencyRegistrationCallback(final android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsRegistrationImplBase.AnonymousClass1.this.lambda$removeEmergencyRegistrationCallback$3(iImsRegistrationCallback);
                }
            }, "removeEmergencyRegistrationCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeEmergencyRegistrationCallback$3(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) {
            android.telephony.ims.stub.ImsRegistrationImplBase.this.removeEmergencyRegistrationCallback(iImsRegistrationCallback);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeRegistrationCallback$4(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) {
            android.telephony.ims.stub.ImsRegistrationImplBase.this.removeRegistrationCallback(iImsRegistrationCallback);
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void removeRegistrationCallback(final android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsRegistrationImplBase.AnonymousClass1.this.lambda$removeRegistrationCallback$4(iImsRegistrationCallback);
                }
            }, "removeRegistrationCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerFullNetworkRegistration$5(int i, java.lang.String str) {
            android.telephony.ims.stub.ImsRegistrationImplBase.this.triggerFullNetworkRegistration(i, str);
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerFullNetworkRegistration(final int i, final java.lang.String str) {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsRegistrationImplBase.AnonymousClass1.this.lambda$triggerFullNetworkRegistration$5(i, str);
                }
            }, "triggerFullNetworkRegistration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerUpdateSipDelegateRegistration$6() {
            android.telephony.ims.stub.ImsRegistrationImplBase.this.updateSipDelegateRegistration();
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerUpdateSipDelegateRegistration() {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsRegistrationImplBase.AnonymousClass1.this.lambda$triggerUpdateSipDelegateRegistration$6();
                }
            }, "triggerUpdateSipDelegateRegistration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerSipDelegateDeregistration$7() {
            android.telephony.ims.stub.ImsRegistrationImplBase.this.triggerSipDelegateDeregistration();
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerSipDelegateDeregistration() {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsRegistrationImplBase.AnonymousClass1.this.lambda$triggerSipDelegateDeregistration$7();
                }
            }, "triggerSipDelegateDeregistration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerDeregistration$8(int i) {
            android.telephony.ims.stub.ImsRegistrationImplBase.this.triggerDeregistration(i);
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerDeregistration(final int i) {
            executeMethodAsyncNoException(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsRegistrationImplBase.AnonymousClass1.this.lambda$triggerDeregistration$8(i);
                }
            }, "triggerDeregistration");
        }

        private void executeMethodAsync(final java.lang.Runnable runnable, java.lang.String str) throws android.os.RemoteException {
            try {
                java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, android.telephony.ims.stub.ImsRegistrationImplBase.this.mExecutor).join();
            } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
                android.util.Log.w(android.telephony.ims.stub.ImsRegistrationImplBase.LOG_TAG, "ImsRegistrationImplBase Binder - " + str + " exception: " + e.getMessage());
                throw new android.os.RemoteException(e.getMessage());
            }
        }

        private void executeMethodAsyncNoException(final java.lang.Runnable runnable, java.lang.String str) {
            try {
                java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, android.telephony.ims.stub.ImsRegistrationImplBase.this.mExecutor).join();
            } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
                android.util.Log.w(android.telephony.ims.stub.ImsRegistrationImplBase.LOG_TAG, "ImsRegistrationImplBase Binder - " + str + " exception: " + e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResult(final java.util.function.Supplier<T> supplier, java.lang.String str) throws android.os.RemoteException {
            try {
                return (T) java.util.concurrent.CompletableFuture.supplyAsync(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda3
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        java.lang.Object runWithCleanCallingIdentity;
                        runWithCleanCallingIdentity = com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity((java.util.function.Supplier<java.lang.Object>) supplier);
                        return runWithCleanCallingIdentity;
                    }
                }, android.telephony.ims.stub.ImsRegistrationImplBase.this.mExecutor).get();
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                android.util.Log.w(android.telephony.ims.stub.ImsRegistrationImplBase.LOG_TAG, "ImsRegistrationImplBase Binder - " + str + " exception: " + e.getMessage());
                throw new android.os.RemoteException(e.getMessage());
            }
        }
    }

    public final android.telephony.ims.aidl.IImsRegistration getBinder() {
        return this.mBinder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
        this.mCallbacks.register(iImsRegistrationCallback);
        updateNewCallbackWithState(iImsRegistrationCallback, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) {
        this.mCallbacks.unregister(iImsRegistrationCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addEmergencyRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
        this.mEmergencyCallbacks.register(iImsRegistrationCallback);
        updateNewCallbackWithState(iImsRegistrationCallback, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeEmergencyRegistrationCallback(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) {
        this.mEmergencyCallbacks.unregister(iImsRegistrationCallback);
    }

    @android.annotation.SystemApi
    public void updateSipDelegateRegistration() {
    }

    @android.annotation.SystemApi
    public void triggerSipDelegateDeregistration() {
    }

    @android.annotation.SystemApi
    public void triggerFullNetworkRegistration(int i, java.lang.String str) {
    }

    public void triggerDeregistration(int i) {
    }

    @android.annotation.SystemApi
    public final void onRegistered(int i) {
        onRegistered(new android.telephony.ims.ImsRegistrationAttributes.Builder(i).build());
    }

    @android.annotation.SystemApi
    public final void onRegistered(final android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) {
        boolean isEmergency = isEmergency(imsRegistrationAttributes);
        if (isEmergency) {
            updateToEmergencyState(imsRegistrationAttributes, 2);
        } else {
            updateToState(imsRegistrationAttributes, 2);
        }
        broadcastToCallbacksLocked(new java.util.function.Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.telephony.ims.stub.ImsRegistrationImplBase.lambda$onRegistered$0(android.telephony.ims.ImsRegistrationAttributes.this, (android.telephony.ims.aidl.IImsRegistrationCallback) obj);
            }
        }, isEmergency);
    }

    static /* synthetic */ void lambda$onRegistered$0(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) {
        try {
            iImsRegistrationCallback.onRegistered(imsRegistrationAttributes);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, e + "onRegistered(int, Set) - Skipping callback.");
        }
    }

    @android.annotation.SystemApi
    public final void onRegistering(int i) {
        onRegistering(new android.telephony.ims.ImsRegistrationAttributes.Builder(i).build());
    }

    @android.annotation.SystemApi
    public final void onRegistering(final android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) {
        boolean isEmergency = isEmergency(imsRegistrationAttributes);
        if (isEmergency) {
            updateToEmergencyState(imsRegistrationAttributes, 1);
        } else {
            updateToState(imsRegistrationAttributes, 1);
        }
        broadcastToCallbacksLocked(new java.util.function.Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.telephony.ims.stub.ImsRegistrationImplBase.lambda$onRegistering$1(android.telephony.ims.ImsRegistrationAttributes.this, (android.telephony.ims.aidl.IImsRegistrationCallback) obj);
            }
        }, isEmergency);
    }

    static /* synthetic */ void lambda$onRegistering$1(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) {
        try {
            iImsRegistrationCallback.onRegistering(imsRegistrationAttributes);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, e + "onRegistering(int, Set) - Skipping callback.");
        }
    }

    @android.annotation.SystemApi
    public final void onDeregistered(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        onDeregistered(imsReasonInfo, 0, -1);
    }

    @android.annotation.SystemApi
    public final void onDeregistered(android.telephony.ims.ImsReasonInfo imsReasonInfo, int i, int i2) {
        android.telephony.ims.ImsRegistrationAttributes build;
        if (this.mRegistrationAttributes != null) {
            build = new android.telephony.ims.ImsRegistrationAttributes(i2, this.mRegistrationAttributes.getTransportType(), this.mRegistrationAttributes.getAttributeFlags(), this.mRegistrationAttributes.getFeatureTags());
        } else {
            build = new android.telephony.ims.ImsRegistrationAttributes.Builder(i2).build();
        }
        onDeregistered(imsReasonInfo, i, build);
    }

    @android.annotation.SystemApi
    public final void onDeregistered(final android.telephony.ims.ImsReasonInfo imsReasonInfo, final int i, android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) {
        boolean isEmergency = isEmergency(imsRegistrationAttributes);
        final int registrationTechnology = imsRegistrationAttributes.getRegistrationTechnology();
        if (isEmergency) {
            updateToDisconnectedEmergencyState(imsReasonInfo, i, registrationTechnology);
        } else {
            updateToDisconnectedState(imsReasonInfo, i, registrationTechnology);
        }
        if (imsReasonInfo == null) {
            imsReasonInfo = new android.telephony.ims.ImsReasonInfo();
        }
        broadcastToCallbacksLocked(new java.util.function.Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.telephony.ims.stub.ImsRegistrationImplBase.lambda$onDeregistered$2(android.telephony.ims.ImsReasonInfo.this, i, registrationTechnology, (android.telephony.ims.aidl.IImsRegistrationCallback) obj);
            }
        }, isEmergency);
    }

    static /* synthetic */ void lambda$onDeregistered$2(android.telephony.ims.ImsReasonInfo imsReasonInfo, int i, int i2, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) {
        try {
            iImsRegistrationCallback.onDeregistered(imsReasonInfo, i, i2);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, e + "onDeregistered() - Skipping callback.");
        }
    }

    @android.annotation.SystemApi
    public final void onDeregistered(android.telephony.ims.ImsReasonInfo imsReasonInfo, android.telephony.ims.SipDetails sipDetails) {
        onDeregistered(imsReasonInfo, 0, -1, sipDetails);
    }

    @android.annotation.SystemApi
    public final void onDeregistered(final android.telephony.ims.ImsReasonInfo imsReasonInfo, final int i, final int i2, final android.telephony.ims.SipDetails sipDetails) {
        updateToDisconnectedState(imsReasonInfo, i, i2);
        if (imsReasonInfo == null) {
            imsReasonInfo = new android.telephony.ims.ImsReasonInfo();
        }
        broadcastToCallbacksLocked(new java.util.function.Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.telephony.ims.stub.ImsRegistrationImplBase.lambda$onDeregistered$3(android.telephony.ims.ImsReasonInfo.this, i, i2, sipDetails, (android.telephony.ims.aidl.IImsRegistrationCallback) obj);
            }
        }, false);
    }

    static /* synthetic */ void lambda$onDeregistered$3(android.telephony.ims.ImsReasonInfo imsReasonInfo, int i, int i2, android.telephony.ims.SipDetails sipDetails, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) {
        try {
            iImsRegistrationCallback.onDeregisteredWithDetails(imsReasonInfo, i, i2, sipDetails);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, e + "onDeregistered() - Skipping callback.");
        }
    }

    @android.annotation.SystemApi
    public final void onTechnologyChangeFailed(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        android.telephony.ims.ImsRegistrationAttributes build;
        if (this.mRegistrationAttributes != null) {
            build = new android.telephony.ims.ImsRegistrationAttributes(i, this.mRegistrationAttributes.getTransportType(), this.mRegistrationAttributes.getAttributeFlags(), this.mRegistrationAttributes.getFeatureTags());
        } else {
            build = new android.telephony.ims.ImsRegistrationAttributes.Builder(i).build();
        }
        onTechnologyChangeFailed(imsReasonInfo, build);
    }

    @android.annotation.SystemApi
    public final void onTechnologyChangeFailed(final android.telephony.ims.ImsReasonInfo imsReasonInfo, android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) {
        boolean isEmergency = isEmergency(imsRegistrationAttributes);
        final int registrationTechnology = imsRegistrationAttributes.getRegistrationTechnology();
        if (imsReasonInfo == null) {
            imsReasonInfo = new android.telephony.ims.ImsReasonInfo();
        }
        broadcastToCallbacksLocked(new java.util.function.Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.telephony.ims.stub.ImsRegistrationImplBase.lambda$onTechnologyChangeFailed$4(registrationTechnology, imsReasonInfo, (android.telephony.ims.aidl.IImsRegistrationCallback) obj);
            }
        }, isEmergency);
    }

    static /* synthetic */ void lambda$onTechnologyChangeFailed$4(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) {
        try {
            iImsRegistrationCallback.onTechnologyChangeFailed(i, imsReasonInfo);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, e + "onTechnologyChangeFailed() - Skipping callback.");
        }
    }

    @android.annotation.SystemApi
    public final void onSubscriberAssociatedUriChanged(final android.net.Uri[] uriArr) {
        synchronized (this.mLock) {
            this.mUris = (android.net.Uri[]) com.android.internal.util.ArrayUtils.cloneOrNull(uriArr);
            this.mUrisSet = true;
        }
        broadcastToCallbacksLocked(new java.util.function.Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.telephony.ims.stub.ImsRegistrationImplBase.this.lambda$onSubscriberAssociatedUriChanged$5(uriArr, (android.telephony.ims.aidl.IImsRegistrationCallback) obj);
            }
        }, false);
    }

    private boolean isEmergency(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) {
        return (imsRegistrationAttributes == null || (imsRegistrationAttributes.getAttributeFlags() & 2) == 0) ? false : true;
    }

    private void broadcastToCallbacksLocked(java.util.function.Consumer<android.telephony.ims.aidl.IImsRegistrationCallback> consumer, boolean z) {
        if (z) {
            synchronized (this.mEmergencyCallbacks) {
                this.mEmergencyCallbacks.broadcastAction(consumer);
            }
        } else {
            synchronized (this.mCallbacks) {
                this.mCallbacks.broadcastAction(consumer);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onSubscriberAssociatedUriChanged, reason: merged with bridge method [inline-methods] */
    public void lambda$onSubscriberAssociatedUriChanged$5(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback, android.net.Uri[] uriArr) {
        try {
            iImsRegistrationCallback.onSubscriberAssociatedUriChanged(uriArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, e + "onSubscriberAssociatedUriChanged() - Skipping callback.");
        }
    }

    private void updateToState(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes, int i) {
        synchronized (this.mLock) {
            this.mRegistrationAttributes = imsRegistrationAttributes;
            this.mRegistrationState = i;
            this.mLastDisconnectCause = null;
            this.mLastDisconnectSuggestedAction = 0;
            this.mLastDisconnectRadioTech = -1;
        }
    }

    private void updateToEmergencyState(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes, int i) {
        synchronized (this.mLock) {
            this.mEmergencyRegistrationAttributes = imsRegistrationAttributes;
            this.mEmergencyRegistrationState = i;
            this.mEmergencyLastDisconnectCause = null;
            this.mEmergencyLastDisconnectSuggestedAction = 0;
            this.mEmergencyLastDisconnectRadioTech = -1;
        }
    }

    private void updateToDisconnectedState(android.telephony.ims.ImsReasonInfo imsReasonInfo, int i, int i2) {
        synchronized (this.mLock) {
            this.mUrisSet = false;
            this.mUris = null;
            updateToState(new android.telephony.ims.ImsRegistrationAttributes.Builder(-1).build(), 0);
            if (imsReasonInfo != null) {
                this.mLastDisconnectCause = imsReasonInfo;
                this.mLastDisconnectSuggestedAction = i;
                this.mLastDisconnectRadioTech = i2;
            } else {
                android.util.Log.w(LOG_TAG, "updateToDisconnectedState: no ImsReasonInfo provided.");
                this.mLastDisconnectCause = new android.telephony.ims.ImsReasonInfo();
            }
        }
    }

    private void updateToDisconnectedEmergencyState(android.telephony.ims.ImsReasonInfo imsReasonInfo, int i, int i2) {
        synchronized (this.mLock) {
            this.mUrisSet = false;
            this.mUris = null;
            updateToEmergencyState(new android.telephony.ims.ImsRegistrationAttributes.Builder(-1).build(), 0);
            if (imsReasonInfo != null) {
                this.mEmergencyLastDisconnectCause = imsReasonInfo;
                this.mEmergencyLastDisconnectSuggestedAction = i;
                this.mEmergencyLastDisconnectRadioTech = i2;
            } else {
                android.util.Log.w(LOG_TAG, "updateToDisconnectedState: no ImsReasonInfo provided.");
                this.mEmergencyLastDisconnectCause = new android.telephony.ims.ImsReasonInfo();
            }
        }
    }

    private void updateNewCallbackWithState(android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback, boolean z) throws android.os.RemoteException {
        int i;
        android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes;
        android.telephony.ims.ImsReasonInfo imsReasonInfo;
        int i2;
        int i3;
        boolean z2;
        android.net.Uri[] uriArr;
        synchronized (this.mLock) {
            i = z ? this.mEmergencyRegistrationState : this.mRegistrationState;
            imsRegistrationAttributes = z ? this.mEmergencyRegistrationAttributes : this.mRegistrationAttributes;
            imsReasonInfo = z ? this.mEmergencyLastDisconnectCause : this.mLastDisconnectCause;
            i2 = z ? this.mEmergencyLastDisconnectSuggestedAction : this.mLastDisconnectSuggestedAction;
            i3 = z ? this.mEmergencyLastDisconnectRadioTech : this.mLastDisconnectRadioTech;
            z2 = this.mUrisSet;
            uriArr = this.mUris;
        }
        switch (i) {
            case 0:
                iImsRegistrationCallback.onDeregistered(imsReasonInfo, i2, i3);
                break;
            case 1:
                iImsRegistrationCallback.onRegistering(imsRegistrationAttributes);
                break;
            case 2:
                iImsRegistrationCallback.onRegistered(imsRegistrationAttributes);
                break;
        }
        if (z2) {
            lambda$onSubscriberAssociatedUriChanged$5(iImsRegistrationCallback, uriArr);
        }
    }

    public final void setDefaultExecutor(java.util.concurrent.Executor executor) {
        if (this.mExecutor == null) {
            this.mExecutor = executor;
        }
    }

    public final void clearRegistrationCache() {
        synchronized (this.mLock) {
            this.mUris = null;
            this.mUrisSet = false;
        }
    }
}
