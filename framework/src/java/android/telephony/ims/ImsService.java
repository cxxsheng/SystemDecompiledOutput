package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class ImsService extends android.app.Service {
    public static final long CAPABILITY_EMERGENCY_OVER_MMTEL = 1;
    public static final long CAPABILITY_SIP_DELEGATE_CREATION = 2;
    public static final long CAPABILITY_SUPPORTS_SIMULTANEOUS_CALLING = 8;
    public static final long CAPABILITY_TERMINAL_BASED_CALL_WAITING = 4;
    private static final java.lang.String LOG_TAG = "ImsService";
    public static final java.lang.String SERVICE_INTERFACE = "android.telephony.ims.ImsService";
    private java.util.concurrent.Executor mExecutor;
    private android.telephony.ims.aidl.IImsServiceControllerListener mListener;
    public static final long CAPABILITY_MAX_INDEX = java.lang.Long.numberOfTrailingZeros(8);
    private static final java.util.Map<java.lang.Long, java.lang.String> CAPABILITIES_LOG_MAP = java.util.Map.of(1L, "EMERGENCY_OVER_MMTEL", 2L, "SIP_DELEGATE_CREATION");
    private final android.util.SparseArray<android.util.SparseArray<android.telephony.ims.feature.ImsFeature>> mFeaturesBySlot = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.util.SparseBooleanArray> mCreateImsFeatureWithSlotIdFlagMap = new android.util.SparseArray<>();
    private final java.lang.Object mListenerLock = new java.lang.Object();
    private final java.lang.Object mExecutorLock = new java.lang.Object();
    protected final android.os.IBinder mImsServiceController = new android.telephony.ims.ImsService.AnonymousClass1();
    private final android.os.IBinder.DeathRecipient mDeathRecipient = new android.telephony.ims.ImsService.AnonymousClass2();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImsServiceCapability {
    }

    public static class Listener extends android.telephony.ims.aidl.IImsServiceControllerListener.Stub {
        @Override // android.telephony.ims.aidl.IImsServiceControllerListener
        public void onUpdateSupportedImsFeatures(android.telephony.ims.stub.ImsFeatureConfiguration imsFeatureConfiguration) {
        }
    }

    /* renamed from: android.telephony.ims.ImsService$1, reason: invalid class name */
    class AnonymousClass1 extends android.telephony.ims.aidl.IImsServiceController.Stub {
        AnonymousClass1() {
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void setListener(android.telephony.ims.aidl.IImsServiceControllerListener iImsServiceControllerListener) {
            synchronized (android.telephony.ims.ImsService.this.mListenerLock) {
                if (android.telephony.ims.ImsService.this.mListener != null && android.telephony.ims.ImsService.this.mListener.asBinder().isBinderAlive()) {
                    try {
                        android.telephony.ims.ImsService.this.mListener.asBinder().unlinkToDeath(android.telephony.ims.ImsService.this.mDeathRecipient, 0);
                    } catch (java.util.NoSuchElementException e) {
                        android.util.Log.w(android.telephony.ims.ImsService.LOG_TAG, "IImsServiceControllerListener does not exist");
                    }
                }
                android.telephony.ims.ImsService.this.mListener = iImsServiceControllerListener;
                if (android.telephony.ims.ImsService.this.mListener == null) {
                    android.telephony.ims.ImsService.this.executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.ImsService.AnonymousClass1.this.lambda$setListener$0();
                        }
                    }, "releaseResource");
                    return;
                }
                try {
                    android.telephony.ims.ImsService.this.mListener.asBinder().linkToDeath(android.telephony.ims.ImsService.this.mDeathRecipient, 0);
                    android.util.Log.i(android.telephony.ims.ImsService.LOG_TAG, "setListener: register linkToDeath");
                } catch (android.os.RemoteException e2) {
                    android.telephony.ims.ImsService.this.executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.ImsService.AnonymousClass1.this.lambda$setListener$1();
                        }
                    }, "releaseResource");
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setListener$0() {
            android.telephony.ims.ImsService.this.releaseResource();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setListener$1() {
            android.telephony.ims.ImsService.this.releaseResource();
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public android.telephony.ims.aidl.IImsMmTelFeature createMmTelFeature(final int i, final int i2) {
            android.telephony.ims.feature.MmTelFeature mmTelFeature = (android.telephony.ims.feature.MmTelFeature) android.telephony.ims.ImsService.this.getImsFeature(i, 1);
            if (mmTelFeature == null) {
                return (android.telephony.ims.aidl.IImsMmTelFeature) android.telephony.ims.ImsService.this.executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        android.telephony.ims.aidl.IImsMmTelFeature lambda$createMmTelFeature$2;
                        lambda$createMmTelFeature$2 = android.telephony.ims.ImsService.AnonymousClass1.this.lambda$createMmTelFeature$2(i, i2);
                        return lambda$createMmTelFeature$2;
                    }
                }, "createMmTelFeature");
            }
            return mmTelFeature.getBinder();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.telephony.ims.aidl.IImsMmTelFeature lambda$createMmTelFeature$2(int i, int i2) {
            return android.telephony.ims.ImsService.this.createMmTelFeatureInternal(i, i2);
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public android.telephony.ims.aidl.IImsMmTelFeature createEmergencyOnlyMmTelFeature(final int i) {
            android.telephony.ims.feature.MmTelFeature mmTelFeature = (android.telephony.ims.feature.MmTelFeature) android.telephony.ims.ImsService.this.getImsFeature(i, 1);
            if (mmTelFeature == null) {
                return (android.telephony.ims.aidl.IImsMmTelFeature) android.telephony.ims.ImsService.this.executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda11
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        android.telephony.ims.aidl.IImsMmTelFeature lambda$createEmergencyOnlyMmTelFeature$3;
                        lambda$createEmergencyOnlyMmTelFeature$3 = android.telephony.ims.ImsService.AnonymousClass1.this.lambda$createEmergencyOnlyMmTelFeature$3(i);
                        return lambda$createEmergencyOnlyMmTelFeature$3;
                    }
                }, "createEmergencyOnlyMmTelFeature");
            }
            return mmTelFeature.getBinder();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.telephony.ims.aidl.IImsMmTelFeature lambda$createEmergencyOnlyMmTelFeature$3(int i) {
            return android.telephony.ims.ImsService.this.createEmergencyOnlyMmTelFeatureInternal(i);
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public android.telephony.ims.aidl.IImsRcsFeature createRcsFeature(final int i, final int i2) {
            android.telephony.ims.feature.RcsFeature rcsFeature = (android.telephony.ims.feature.RcsFeature) android.telephony.ims.ImsService.this.getImsFeature(i, 2);
            if (rcsFeature == null) {
                return (android.telephony.ims.aidl.IImsRcsFeature) android.telephony.ims.ImsService.this.executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda14
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        android.telephony.ims.aidl.IImsRcsFeature lambda$createRcsFeature$4;
                        lambda$createRcsFeature$4 = android.telephony.ims.ImsService.AnonymousClass1.this.lambda$createRcsFeature$4(i, i2);
                        return lambda$createRcsFeature$4;
                    }
                }, "createRcsFeature");
            }
            return rcsFeature.getBinder();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.telephony.ims.aidl.IImsRcsFeature lambda$createRcsFeature$4(int i, int i2) {
            return android.telephony.ims.ImsService.this.createRcsFeatureInternal(i, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addFeatureStatusCallback$5(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            android.telephony.ims.ImsService.this.addImsFeatureStatusCallback(i, i2, iImsFeatureStatusCallback);
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void addFeatureStatusCallback(final int i, final int i2, final com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            android.telephony.ims.ImsService.this.executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsService.AnonymousClass1.this.lambda$addFeatureStatusCallback$5(i, i2, iImsFeatureStatusCallback);
                }
            }, "addFeatureStatusCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeFeatureStatusCallback$6(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            android.telephony.ims.ImsService.this.removeImsFeatureStatusCallback(i, i2, iImsFeatureStatusCallback);
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void removeFeatureStatusCallback(final int i, final int i2, final com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            android.telephony.ims.ImsService.this.executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsService.AnonymousClass1.this.lambda$removeFeatureStatusCallback$6(i, i2, iImsFeatureStatusCallback);
                }
            }, "removeFeatureStatusCallback");
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void removeImsFeature(final int i, final int i2, boolean z) {
            if (z && android.telephony.ims.ImsService.this.isImsFeatureCreatedForSlot(i, i2)) {
                android.util.Log.w(android.telephony.ims.ImsService.LOG_TAG, "Do not remove Ims feature for compatibility");
            } else {
                android.telephony.ims.ImsService.this.executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.ims.ImsService.AnonymousClass1.this.lambda$removeImsFeature$7(i, i2);
                    }
                }, "removeImsFeature");
                android.telephony.ims.ImsService.this.setImsFeatureCreatedForSlot(i, i2, false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeImsFeature$7(int i, int i2) {
            android.telephony.ims.ImsService.this.removeImsFeature(i, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.telephony.ims.stub.ImsFeatureConfiguration lambda$querySupportedImsFeatures$8() {
            return android.telephony.ims.ImsService.this.querySupportedImsFeatures();
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public android.telephony.ims.stub.ImsFeatureConfiguration querySupportedImsFeatures() {
            return (android.telephony.ims.stub.ImsFeatureConfiguration) android.telephony.ims.ImsService.this.executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda12
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.telephony.ims.stub.ImsFeatureConfiguration lambda$querySupportedImsFeatures$8;
                    lambda$querySupportedImsFeatures$8 = android.telephony.ims.ImsService.AnonymousClass1.this.lambda$querySupportedImsFeatures$8();
                    return lambda$querySupportedImsFeatures$8;
                }
            }, "ImsFeatureConfiguration");
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public long getImsServiceCapabilities() {
            return ((java.lang.Long) android.telephony.ims.ImsService.this.executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Long lambda$getImsServiceCapabilities$9;
                    lambda$getImsServiceCapabilities$9 = android.telephony.ims.ImsService.AnonymousClass1.this.lambda$getImsServiceCapabilities$9();
                    return lambda$getImsServiceCapabilities$9;
                }
            }, "getImsServiceCapabilities")).longValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Long lambda$getImsServiceCapabilities$9() {
            long imsServiceCapabilities = android.telephony.ims.ImsService.this.getImsServiceCapabilities();
            long sanitizeCapabilities = android.telephony.ims.ImsService.sanitizeCapabilities(imsServiceCapabilities);
            if (imsServiceCapabilities != sanitizeCapabilities) {
                android.util.Log.w(android.telephony.ims.ImsService.LOG_TAG, "removing invalid bits from field: 0x" + java.lang.Long.toHexString(imsServiceCapabilities ^ sanitizeCapabilities));
            }
            return java.lang.Long.valueOf(sanitizeCapabilities);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyImsServiceReadyForFeatureCreation$10() {
            android.telephony.ims.ImsService.this.readyForFeatureCreation();
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void notifyImsServiceReadyForFeatureCreation() {
            android.telephony.ims.ImsService.this.executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsService.AnonymousClass1.this.lambda$notifyImsServiceReadyForFeatureCreation$10();
                }
            }, "notifyImsServiceReadyForFeatureCreation");
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public android.telephony.ims.aidl.IImsConfig getConfig(final int i, final int i2) {
            return (android.telephony.ims.aidl.IImsConfig) android.telephony.ims.ImsService.this.executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda13
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.telephony.ims.aidl.IImsConfig lambda$getConfig$11;
                    lambda$getConfig$11 = android.telephony.ims.ImsService.AnonymousClass1.this.lambda$getConfig$11(i, i2);
                    return lambda$getConfig$11;
                }
            }, "getConfig");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.telephony.ims.aidl.IImsConfig lambda$getConfig$11(int i, int i2) {
            android.telephony.ims.stub.ImsConfigImplBase configForSubscription = android.telephony.ims.ImsService.this.getConfigForSubscription(i, i2);
            if (configForSubscription != null) {
                configForSubscription.setDefaultExecutor(android.telephony.ims.ImsService.this.getCachedExecutor());
                return configForSubscription.getIImsConfig();
            }
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public android.telephony.ims.aidl.IImsRegistration getRegistration(final int i, final int i2) {
            return (android.telephony.ims.aidl.IImsRegistration) android.telephony.ims.ImsService.this.executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda15
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.telephony.ims.aidl.IImsRegistration lambda$getRegistration$12;
                    lambda$getRegistration$12 = android.telephony.ims.ImsService.AnonymousClass1.this.lambda$getRegistration$12(i, i2);
                    return lambda$getRegistration$12;
                }
            }, "getRegistration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.telephony.ims.aidl.IImsRegistration lambda$getRegistration$12(int i, int i2) {
            android.telephony.ims.stub.ImsRegistrationImplBase registrationForSubscription = android.telephony.ims.ImsService.this.getRegistrationForSubscription(i, i2);
            if (registrationForSubscription != null) {
                registrationForSubscription.setDefaultExecutor(android.telephony.ims.ImsService.this.getCachedExecutor());
                return registrationForSubscription.getBinder();
            }
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public android.telephony.ims.aidl.ISipTransport getSipTransport(final int i) {
            return (android.telephony.ims.aidl.ISipTransport) android.telephony.ims.ImsService.this.executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.telephony.ims.aidl.ISipTransport lambda$getSipTransport$13;
                    lambda$getSipTransport$13 = android.telephony.ims.ImsService.AnonymousClass1.this.lambda$getSipTransport$13(i);
                    return lambda$getSipTransport$13;
                }
            }, "getSipTransport");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.telephony.ims.aidl.ISipTransport lambda$getSipTransport$13(int i) {
            android.telephony.ims.stub.SipTransportImplBase sipTransport = android.telephony.ims.ImsService.this.getSipTransport(i);
            if (sipTransport != null) {
                sipTransport.setDefaultExecutor(android.telephony.ims.ImsService.this.getCachedExecutor());
                return sipTransport.getBinder();
            }
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void enableIms(final int i, final int i2) {
            android.telephony.ims.ImsService.this.executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsService.AnonymousClass1.this.lambda$enableIms$14(i, i2);
                }
            }, "enableIms");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$enableIms$14(int i, int i2) {
            android.telephony.ims.ImsService.this.enableImsForSubscription(i, i2);
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void disableIms(final int i, final int i2) {
            android.telephony.ims.ImsService.this.executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsService.AnonymousClass1.this.lambda$disableIms$15(i, i2);
                }
            }, "disableIms");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$disableIms$15(int i, int i2) {
            android.telephony.ims.ImsService.this.disableImsForSubscription(i, i2);
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void resetIms(final int i, final int i2) {
            android.telephony.ims.ImsService.this.executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsService.AnonymousClass1.this.lambda$resetIms$16(i, i2);
                }
            }, "resetIms");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$resetIms$16(int i, int i2) {
            android.telephony.ims.ImsService.this.resetImsInternal(i, i2);
        }
    }

    /* renamed from: android.telephony.ims.ImsService$2, reason: invalid class name */
    class AnonymousClass2 implements android.os.IBinder.DeathRecipient {
        AnonymousClass2() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Log.w(android.telephony.ims.ImsService.LOG_TAG, "IImsServiceControllerListener binder to framework has died. Cleaning up");
            android.telephony.ims.ImsService.this.executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.ImsService$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsService.AnonymousClass2.this.lambda$binderDied$0();
                }
            }, "releaseResource");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$binderDied$0() {
            android.telephony.ims.ImsService.this.releaseResource();
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            android.util.Log.i(LOG_TAG, "ImsService Bound.");
            return this.mImsServiceController;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.concurrent.Executor getCachedExecutor() {
        java.util.concurrent.Executor executor;
        synchronized (this.mExecutorLock) {
            if (this.mExecutor == null) {
                java.util.concurrent.Executor executor2 = getExecutor();
                if (executor2 == null) {
                    executor2 = new android.app.PendingIntent$$ExternalSyntheticLambda0();
                }
                this.mExecutor = executor2;
            }
            executor = this.mExecutor;
        }
        return executor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.telephony.ims.aidl.IImsMmTelFeature createMmTelFeatureInternal(int i, int i2) {
        android.telephony.ims.feature.MmTelFeature createMmTelFeatureForSubscription = createMmTelFeatureForSubscription(i, i2);
        if (createMmTelFeatureForSubscription != null) {
            setupFeature(createMmTelFeatureForSubscription, i, 1);
            createMmTelFeatureForSubscription.setDefaultExecutor(getCachedExecutor());
            return createMmTelFeatureForSubscription.getBinder();
        }
        android.util.Log.e(LOG_TAG, "createMmTelFeatureInternal: null feature returned.");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.telephony.ims.aidl.IImsMmTelFeature createEmergencyOnlyMmTelFeatureInternal(int i) {
        android.telephony.ims.feature.MmTelFeature createEmergencyOnlyMmTelFeature = createEmergencyOnlyMmTelFeature(i);
        if (createEmergencyOnlyMmTelFeature != null) {
            setupFeature(createEmergencyOnlyMmTelFeature, i, 1);
            createEmergencyOnlyMmTelFeature.setDefaultExecutor(getCachedExecutor());
            return createEmergencyOnlyMmTelFeature.getBinder();
        }
        android.util.Log.e(LOG_TAG, "createEmergencyOnlyMmTelFeatureInternal: null feature returned.");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.telephony.ims.aidl.IImsRcsFeature createRcsFeatureInternal(int i, int i2) {
        android.telephony.ims.feature.RcsFeature createRcsFeatureForSubscription = createRcsFeatureForSubscription(i, i2);
        if (createRcsFeatureForSubscription != null) {
            createRcsFeatureForSubscription.setDefaultExecutor(getCachedExecutor());
            setupFeature(createRcsFeatureForSubscription, i, 2);
            return createRcsFeatureForSubscription.getBinder();
        }
        android.util.Log.e(LOG_TAG, "createRcsFeatureInternal: null feature returned.");
        return null;
    }

    private void setupFeature(android.telephony.ims.feature.ImsFeature imsFeature, int i, int i2) {
        imsFeature.initialize(this, i);
        addImsFeature(i, i2, imsFeature);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addImsFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        synchronized (this.mFeaturesBySlot) {
            android.util.SparseArray<android.telephony.ims.feature.ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                android.util.Log.w(LOG_TAG, "Can not add ImsFeatureStatusCallback - no features on slot " + i);
                return;
            }
            android.telephony.ims.feature.ImsFeature imsFeature = sparseArray.get(i2);
            if (imsFeature != null) {
                imsFeature.addImsFeatureStatusCallback(iImsFeatureStatusCallback);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeImsFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        synchronized (this.mFeaturesBySlot) {
            android.util.SparseArray<android.telephony.ims.feature.ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                android.util.Log.w(LOG_TAG, "Can not remove ImsFeatureStatusCallback - no features on slot " + i);
                return;
            }
            android.telephony.ims.feature.ImsFeature imsFeature = sparseArray.get(i2);
            if (imsFeature != null) {
                imsFeature.removeImsFeatureStatusCallback(iImsFeatureStatusCallback);
            }
        }
    }

    private void addImsFeature(int i, int i2, android.telephony.ims.feature.ImsFeature imsFeature) {
        synchronized (this.mFeaturesBySlot) {
            android.util.SparseArray<android.telephony.ims.feature.ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                sparseArray = new android.util.SparseArray<>();
                this.mFeaturesBySlot.put(i, sparseArray);
            }
            sparseArray.put(i2, imsFeature);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeImsFeature(int i, int i2) {
        notifySubscriptionRemoved(i);
        synchronized (this.mFeaturesBySlot) {
            android.util.SparseArray<android.telephony.ims.feature.ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                android.util.Log.w(LOG_TAG, "Can not remove ImsFeature. No ImsFeatures exist on slot " + i);
                return;
            }
            android.telephony.ims.feature.ImsFeature imsFeature = sparseArray.get(i2);
            if (imsFeature == null) {
                android.util.Log.w(LOG_TAG, "Can not remove ImsFeature. No feature with type " + i2 + " exists on slot " + i);
            } else {
                imsFeature.onFeatureRemoved();
                sparseArray.remove(i2);
            }
        }
    }

    public android.telephony.ims.feature.ImsFeature getImsFeature(int i, int i2) {
        synchronized (this.mFeaturesBySlot) {
            android.util.SparseArray<android.telephony.ims.feature.ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                return null;
            }
            return sparseArray.get(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImsFeatureCreatedForSlot(int i, int i2, boolean z) {
        synchronized (this.mCreateImsFeatureWithSlotIdFlagMap) {
            getImsFeatureCreatedForSlot(i).put(i2, z);
        }
    }

    public boolean isImsFeatureCreatedForSlot(int i, int i2) {
        boolean z;
        synchronized (this.mCreateImsFeatureWithSlotIdFlagMap) {
            z = getImsFeatureCreatedForSlot(i).get(i2);
        }
        return z;
    }

    private android.util.SparseBooleanArray getImsFeatureCreatedForSlot(int i) {
        android.util.SparseBooleanArray sparseBooleanArray = this.mCreateImsFeatureWithSlotIdFlagMap.get(i);
        if (sparseBooleanArray == null) {
            android.util.SparseBooleanArray sparseBooleanArray2 = new android.util.SparseBooleanArray();
            this.mCreateImsFeatureWithSlotIdFlagMap.put(i, sparseBooleanArray2);
            return sparseBooleanArray2;
        }
        return sparseBooleanArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseResource() {
        android.util.Log.w(LOG_TAG, "cleaning up features");
        synchronized (this.mFeaturesBySlot) {
            for (int i = 0; i < this.mFeaturesBySlot.size(); i++) {
                android.util.SparseArray<android.telephony.ims.feature.ImsFeature> valueAt = this.mFeaturesBySlot.valueAt(i);
                if (valueAt != null) {
                    for (int i2 = 0; i2 < valueAt.size(); i2++) {
                        android.telephony.ims.feature.ImsFeature valueAt2 = valueAt.valueAt(i2);
                        if (valueAt2 != null) {
                            valueAt2.onFeatureRemoved();
                        }
                    }
                    valueAt.clear();
                }
            }
            this.mFeaturesBySlot.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeMethodAsync(final java.lang.Runnable runnable, java.lang.String str) {
        try {
            java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.ims.ImsService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                }
            }, getCachedExecutor()).join();
        } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
            android.util.Log.w(LOG_TAG, "ImsService Binder - " + str + " exception: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> T executeMethodAsyncForResult(final java.util.function.Supplier<T> supplier, java.lang.String str) {
        try {
            return (T) java.util.concurrent.CompletableFuture.supplyAsync(new java.util.function.Supplier() { // from class: android.telephony.ims.ImsService$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Object runWithCleanCallingIdentity;
                    runWithCleanCallingIdentity = com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity((java.util.function.Supplier<java.lang.Object>) supplier);
                    return runWithCleanCallingIdentity;
                }
            }, getCachedExecutor()).get();
        } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
            android.util.Log.w(LOG_TAG, "ImsService Binder - " + str + " exception: " + e.getMessage());
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetImsInternal(int i, int i2) {
        try {
            resetIms(i);
        } catch (java.lang.UnsupportedOperationException e) {
            disableImsForSubscription(i, i2);
        }
    }

    public android.telephony.ims.stub.ImsFeatureConfiguration querySupportedImsFeatures() {
        return new android.telephony.ims.stub.ImsFeatureConfiguration();
    }

    public final void onUpdateSupportedImsFeatures(android.telephony.ims.stub.ImsFeatureConfiguration imsFeatureConfiguration) throws android.os.RemoteException {
        android.telephony.ims.aidl.IImsServiceControllerListener iImsServiceControllerListener;
        synchronized (this.mListenerLock) {
            if (this.mListener == null) {
                throw new java.lang.IllegalStateException("Framework is not ready");
            }
            iImsServiceControllerListener = this.mListener;
        }
        iImsServiceControllerListener.onUpdateSupportedImsFeatures(imsFeatureConfiguration);
    }

    public long getImsServiceCapabilities() {
        return 0L;
    }

    public void readyForFeatureCreation() {
    }

    public void enableImsForSubscription(int i, int i2) {
        enableIms(i);
    }

    public void disableImsForSubscription(int i, int i2) {
        disableIms(i);
    }

    private void notifySubscriptionRemoved(int i) {
        android.telephony.ims.stub.ImsRegistrationImplBase registration = getRegistration(i);
        if (registration != null) {
            registration.clearRegistrationCache();
        }
        android.telephony.ims.stub.ImsConfigImplBase config = getConfig(i);
        if (config != null) {
            config.clearConfigurationCache();
        }
    }

    @java.lang.Deprecated
    public void enableIms(int i) {
    }

    @java.lang.Deprecated
    public void disableIms(int i) {
    }

    public void resetIms(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    public android.telephony.ims.feature.MmTelFeature createMmTelFeatureForSubscription(int i, int i2) {
        setImsFeatureCreatedForSlot(i, 1, true);
        return createMmTelFeature(i);
    }

    public android.telephony.ims.feature.RcsFeature createRcsFeatureForSubscription(int i, int i2) {
        setImsFeatureCreatedForSlot(i, 2, true);
        return createRcsFeature(i);
    }

    public android.telephony.ims.feature.MmTelFeature createEmergencyOnlyMmTelFeature(int i) {
        setImsFeatureCreatedForSlot(i, 1, true);
        return createMmTelFeature(i);
    }

    @java.lang.Deprecated
    public android.telephony.ims.feature.MmTelFeature createMmTelFeature(int i) {
        return null;
    }

    @java.lang.Deprecated
    public android.telephony.ims.feature.RcsFeature createRcsFeature(int i) {
        return null;
    }

    public android.telephony.ims.stub.ImsConfigImplBase getConfigForSubscription(int i, int i2) {
        return getConfig(i);
    }

    public android.telephony.ims.stub.ImsRegistrationImplBase getRegistrationForSubscription(int i, int i2) {
        return getRegistration(i);
    }

    @java.lang.Deprecated
    public android.telephony.ims.stub.ImsConfigImplBase getConfig(int i) {
        return new android.telephony.ims.stub.ImsConfigImplBase();
    }

    @java.lang.Deprecated
    public android.telephony.ims.stub.ImsRegistrationImplBase getRegistration(int i) {
        return new android.telephony.ims.stub.ImsRegistrationImplBase();
    }

    public android.telephony.ims.stub.SipTransportImplBase getSipTransport(int i) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long sanitizeCapabilities(long j) {
        return j & (~((-1) << ((int) (CAPABILITY_MAX_INDEX + 1)))) & (-2);
    }

    public static java.lang.String getCapabilitiesString(long j) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("capabilities={ ");
        long j2 = -1;
        for (long j3 = 0; (j & j2) != 0 && j3 <= 63; j3++) {
            long j4 = 1 << ((int) j3);
            if ((j & j4) != 0) {
                stringBuffer.append(CAPABILITIES_LOG_MAP.getOrDefault(java.lang.Long.valueOf(j4), j4 + "?"));
                stringBuffer.append(" ");
            }
            j2 <<= 1;
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public java.util.concurrent.Executor getExecutor() {
        return new android.app.PendingIntent$$ExternalSyntheticLambda0();
    }
}
