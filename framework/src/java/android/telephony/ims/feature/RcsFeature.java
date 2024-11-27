package android.telephony.ims.feature;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class RcsFeature extends android.telephony.ims.feature.ImsFeature {
    private static final java.lang.String LOG_TAG = "RcsFeature";
    private android.telephony.ims.stub.CapabilityExchangeEventListener mCapExchangeEventListener;
    private android.telephony.ims.stub.RcsCapabilityExchangeImplBase mCapabilityExchangeImpl;
    private java.util.concurrent.Executor mExecutor;
    private final android.telephony.ims.feature.RcsFeature.RcsFeatureBinder mImsRcsBinder;

    /* JADX INFO: Access modifiers changed from: private */
    static final class RcsFeatureBinder extends android.telephony.ims.aidl.IImsRcsFeature.Stub {
        private java.util.concurrent.Executor mExecutor;
        private final android.telephony.ims.feature.RcsFeature mReference;

        RcsFeatureBinder(android.telephony.ims.feature.RcsFeature rcsFeature, java.util.concurrent.Executor executor) {
            this.mReference = rcsFeature;
            this.mExecutor = executor;
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public int queryCapabilityStatus() throws android.os.RemoteException {
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$queryCapabilityStatus$0;
                    lambda$queryCapabilityStatus$0 = android.telephony.ims.feature.RcsFeature.RcsFeatureBinder.this.lambda$queryCapabilityStatus$0();
                    return lambda$queryCapabilityStatus$0;
                }
            }, "queryCapabilityStatus")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$queryCapabilityStatus$0() {
            return java.lang.Integer.valueOf(this.mReference.queryCapabilityStatus().mCapabilities);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addCapabilityCallback$1(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
            this.mReference.addCapabilityCallback(iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void addCapabilityCallback(final android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.RcsFeature.RcsFeatureBinder.this.lambda$addCapabilityCallback$1(iImsCapabilityCallback);
                }
            }, "addCapabilityCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeCapabilityCallback$2(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
            this.mReference.removeCapabilityCallback(iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void removeCapabilityCallback(final android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.RcsFeature.RcsFeatureBinder.this.lambda$removeCapabilityCallback$2(iImsCapabilityCallback);
                }
            }, "removeCapabilityCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$changeCapabilitiesConfiguration$3(android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
            this.mReference.requestChangeEnabledCapabilities(capabilityChangeRequest, iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void changeCapabilitiesConfiguration(final android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest, final android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.RcsFeature.RcsFeatureBinder.this.lambda$changeCapabilitiesConfiguration$3(capabilityChangeRequest, iImsCapabilityCallback);
                }
            }, "changeCapabilitiesConfiguration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$queryCapabilityConfiguration$4(int i, int i2, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) {
            this.mReference.queryCapabilityConfigurationInternal(i, i2, iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void queryCapabilityConfiguration(final int i, final int i2, final android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.RcsFeature.RcsFeatureBinder.this.lambda$queryCapabilityConfiguration$4(i, i2, iImsCapabilityCallback);
                }
            }, "queryCapabilityConfiguration");
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public int getFeatureState() throws android.os.RemoteException {
            final android.telephony.ims.feature.RcsFeature rcsFeature = this.mReference;
            java.util.Objects.requireNonNull(rcsFeature);
            return ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    return java.lang.Integer.valueOf(android.telephony.ims.feature.RcsFeature.this.getFeatureState());
                }
            }, "getFeatureState")).intValue();
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void setCapabilityExchangeEventListener(android.telephony.ims.aidl.ICapabilityExchangeEventListener iCapabilityExchangeEventListener) throws android.os.RemoteException {
            final android.telephony.ims.aidl.CapabilityExchangeAidlWrapper capabilityExchangeAidlWrapper = iCapabilityExchangeEventListener != null ? new android.telephony.ims.aidl.CapabilityExchangeAidlWrapper(iCapabilityExchangeEventListener) : null;
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.RcsFeature.RcsFeatureBinder.this.lambda$setCapabilityExchangeEventListener$5(capabilityExchangeAidlWrapper);
                }
            }, "setCapabilityExchangeEventListener");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setCapabilityExchangeEventListener$5(android.telephony.ims.stub.CapabilityExchangeEventListener capabilityExchangeEventListener) {
            this.mReference.setCapabilityExchangeEventListener(capabilityExchangeEventListener);
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void publishCapabilities(final java.lang.String str, android.telephony.ims.aidl.IPublishResponseCallback iPublishResponseCallback) throws android.os.RemoteException {
            final android.telephony.ims.aidl.RcsPublishResponseAidlWrapper rcsPublishResponseAidlWrapper = new android.telephony.ims.aidl.RcsPublishResponseAidlWrapper(iPublishResponseCallback);
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.RcsFeature.RcsFeatureBinder.this.lambda$publishCapabilities$6(str, rcsPublishResponseAidlWrapper);
                }
            }, "publishCapabilities");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$publishCapabilities$6(java.lang.String str, android.telephony.ims.stub.RcsCapabilityExchangeImplBase.PublishResponseCallback publishResponseCallback) {
            this.mReference.getCapabilityExchangeImplBaseInternal().publishCapabilities(str, publishResponseCallback);
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void subscribeForCapabilities(final java.util.List<android.net.Uri> list, android.telephony.ims.aidl.ISubscribeResponseCallback iSubscribeResponseCallback) throws android.os.RemoteException {
            final android.telephony.ims.aidl.RcsSubscribeResponseAidlWrapper rcsSubscribeResponseAidlWrapper = new android.telephony.ims.aidl.RcsSubscribeResponseAidlWrapper(iSubscribeResponseCallback);
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.RcsFeature.RcsFeatureBinder.this.lambda$subscribeForCapabilities$7(list, rcsSubscribeResponseAidlWrapper);
                }
            }, "subscribeForCapabilities");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$subscribeForCapabilities$7(java.util.List list, android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback subscribeResponseCallback) {
            this.mReference.getCapabilityExchangeImplBaseInternal().subscribeForCapabilities(list, subscribeResponseCallback);
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void sendOptionsCapabilityRequest(final android.net.Uri uri, final java.util.List<java.lang.String> list, android.telephony.ims.aidl.IOptionsResponseCallback iOptionsResponseCallback) throws android.os.RemoteException {
            final android.telephony.ims.aidl.RcsOptionsResponseAidlWrapper rcsOptionsResponseAidlWrapper = new android.telephony.ims.aidl.RcsOptionsResponseAidlWrapper(iOptionsResponseCallback);
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.feature.RcsFeature.RcsFeatureBinder.this.lambda$sendOptionsCapabilityRequest$8(uri, list, rcsOptionsResponseAidlWrapper);
                }
            }, "sendOptionsCapabilityRequest");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendOptionsCapabilityRequest$8(android.net.Uri uri, java.util.List list, android.telephony.ims.stub.RcsCapabilityExchangeImplBase.OptionsResponseCallback optionsResponseCallback) {
            this.mReference.getCapabilityExchangeImplBaseInternal().sendOptionsCapabilityRequest(uri, new java.util.HashSet(list), optionsResponseCallback);
        }

        private void executeMethodAsync(final java.lang.Runnable runnable, java.lang.String str) throws android.os.RemoteException {
            try {
                java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, this.mExecutor).join();
            } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
                android.util.Log.w(android.telephony.ims.feature.RcsFeature.LOG_TAG, "RcsFeatureBinder - " + str + " exception: " + e.getMessage());
                throw new android.os.RemoteException(e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResult(final java.util.function.Supplier<T> supplier, java.lang.String str) throws android.os.RemoteException {
            try {
                return (T) java.util.concurrent.CompletableFuture.supplyAsync(new java.util.function.Supplier() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda10
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        java.lang.Object runWithCleanCallingIdentity;
                        runWithCleanCallingIdentity = com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity((java.util.function.Supplier<java.lang.Object>) supplier);
                        return runWithCleanCallingIdentity;
                    }
                }, this.mExecutor).get();
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                android.util.Log.w(android.telephony.ims.feature.RcsFeature.LOG_TAG, "RcsFeatureBinder - " + str + " exception: " + e.getMessage());
                throw new android.os.RemoteException(e.getMessage());
            }
        }
    }

    public static class RcsImsCapabilities extends android.telephony.ims.feature.ImsFeature.Capabilities {
        public static final int CAPABILITY_TYPE_MAX = 3;
        public static final int CAPABILITY_TYPE_NONE = 0;
        public static final int CAPABILITY_TYPE_OPTIONS_UCE = 1;
        public static final int CAPABILITY_TYPE_PRESENCE_UCE = 2;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface RcsImsCapabilityFlag {
        }

        public RcsImsCapabilities(int i) {
            super(i);
        }

        private RcsImsCapabilities(android.telephony.ims.feature.ImsFeature.Capabilities capabilities) {
            super(capabilities.getMask());
        }

        @Override // android.telephony.ims.feature.ImsFeature.Capabilities
        public void addCapabilities(int i) {
            super.addCapabilities(i);
        }

        @Override // android.telephony.ims.feature.ImsFeature.Capabilities
        public void removeCapabilities(int i) {
            super.removeCapabilities(i);
        }

        @Override // android.telephony.ims.feature.ImsFeature.Capabilities
        public boolean isCapable(int i) {
            return super.isCapable(i);
        }
    }

    public RcsFeature() {
        this.mImsRcsBinder = new android.telephony.ims.feature.RcsFeature.RcsFeatureBinder(this, this.mExecutor);
    }

    public RcsFeature(java.util.concurrent.Executor executor) {
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("executor can not be null.");
        }
        this.mExecutor = executor;
        this.mImsRcsBinder = new android.telephony.ims.feature.RcsFeature.RcsFeatureBinder(this, this.mExecutor);
    }

    @Override // android.telephony.ims.feature.ImsFeature
    public void initialize(android.content.Context context, int i) {
        super.initialize(context, i);
        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.feature.RcsFeature$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.telephony.ims.feature.RcsFeature.this.lambda$initialize$0();
            }
        });
    }

    @Override // android.telephony.ims.feature.ImsFeature
    public final android.telephony.ims.feature.RcsFeature.RcsImsCapabilities queryCapabilityStatus() {
        return new android.telephony.ims.feature.RcsFeature.RcsImsCapabilities(super.queryCapabilityStatus());
    }

    public final void notifyCapabilitiesStatusChanged(android.telephony.ims.feature.RcsFeature.RcsImsCapabilities rcsImsCapabilities) {
        if (rcsImsCapabilities == null) {
            throw new java.lang.IllegalArgumentException("RcsImsCapabilities must be non-null!");
        }
        super.notifyCapabilitiesStatusChanged((android.telephony.ims.feature.ImsFeature.Capabilities) rcsImsCapabilities);
    }

    @Override // android.telephony.ims.feature.ImsFeature
    public boolean queryCapabilityConfiguration(int i, int i2) {
        return false;
    }

    @Override // android.telephony.ims.feature.ImsFeature
    public void changeEnabledCapabilities(android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest, android.telephony.ims.feature.ImsFeature.CapabilityCallbackProxy capabilityCallbackProxy) {
    }

    public android.telephony.ims.stub.RcsCapabilityExchangeImplBase createCapabilityExchangeImpl(android.telephony.ims.stub.CapabilityExchangeEventListener capabilityExchangeEventListener) {
        return new android.telephony.ims.stub.RcsCapabilityExchangeImplBase();
    }

    public void destroyCapabilityExchangeImpl(android.telephony.ims.stub.RcsCapabilityExchangeImplBase rcsCapabilityExchangeImplBase) {
    }

    @Override // android.telephony.ims.feature.ImsFeature
    public void onFeatureRemoved() {
    }

    @Override // android.telephony.ims.feature.ImsFeature
    /* renamed from: onFeatureReady, reason: merged with bridge method [inline-methods] */
    public void lambda$initialize$0() {
    }

    @Override // android.telephony.ims.feature.ImsFeature
    public final android.telephony.ims.aidl.IImsRcsFeature getBinder() {
        return this.mImsRcsBinder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCapabilityExchangeEventListener(android.telephony.ims.stub.CapabilityExchangeEventListener capabilityExchangeEventListener) {
        synchronized (this.mLock) {
            this.mCapExchangeEventListener = capabilityExchangeEventListener;
            if (this.mCapExchangeEventListener != null) {
                initRcsCapabilityExchangeImplBase(this.mCapExchangeEventListener);
            } else {
                if (this.mCapabilityExchangeImpl != null) {
                    destroyCapabilityExchangeImpl(this.mCapabilityExchangeImpl);
                }
                this.mCapabilityExchangeImpl = null;
            }
        }
    }

    private void initRcsCapabilityExchangeImplBase(android.telephony.ims.stub.CapabilityExchangeEventListener capabilityExchangeEventListener) {
        synchronized (this.mLock) {
            if (this.mCapabilityExchangeImpl != null) {
                destroyCapabilityExchangeImpl(this.mCapabilityExchangeImpl);
            }
            this.mCapabilityExchangeImpl = createCapabilityExchangeImpl(capabilityExchangeEventListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.telephony.ims.stub.RcsCapabilityExchangeImplBase getCapabilityExchangeImplBaseInternal() {
        android.telephony.ims.stub.RcsCapabilityExchangeImplBase rcsCapabilityExchangeImplBase;
        synchronized (this.mLock) {
            if (this.mCapabilityExchangeImpl == null) {
                throw new java.lang.IllegalStateException("Session is not available.");
            }
            rcsCapabilityExchangeImplBase = this.mCapabilityExchangeImpl;
        }
        return rcsCapabilityExchangeImplBase;
    }

    public final void setDefaultExecutor(java.util.concurrent.Executor executor) {
        if (this.mImsRcsBinder.mExecutor == null) {
            this.mExecutor = executor;
            this.mImsRcsBinder.mExecutor = executor;
        }
    }
}
