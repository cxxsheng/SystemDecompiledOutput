package android.telephony.ims;

/* loaded from: classes3.dex */
public class ImsMmTelManager implements android.telephony.ims.RegistrationManager {
    private static final java.lang.String TAG = "ImsMmTelManager";
    public static final int WIFI_MODE_CELLULAR_PREFERRED = 1;
    public static final int WIFI_MODE_UNKNOWN = -1;
    public static final int WIFI_MODE_WIFI_ONLY = 0;
    public static final int WIFI_MODE_WIFI_PREFERRED = 2;
    private static final android.telephony.BinderCacheManager<com.android.internal.telephony.ITelephony> sTelephonyCache = new android.telephony.BinderCacheManager<>(new android.telephony.BinderCacheManager.BinderInterfaceFactory() { // from class: android.telephony.ims.ImsMmTelManager$$ExternalSyntheticLambda0
        @Override // android.telephony.BinderCacheManager.BinderInterfaceFactory
        public final java.lang.Object create() {
            com.android.internal.telephony.ITelephony iTelephonyInterface;
            iTelephonyInterface = android.telephony.ims.ImsMmTelManager.getITelephonyInterface();
            return iTelephonyInterface;
        }
    });
    private final android.telephony.BinderCacheManager<com.android.internal.telephony.ITelephony> mBinderCache;
    private final android.content.Context mContext;
    private final int mSubId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WiFiCallingMode {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static class RegistrationCallback extends android.telephony.ims.RegistrationManager.RegistrationCallback {
        @Override // android.telephony.ims.RegistrationManager.RegistrationCallback
        public void onRegistered(int i) {
        }

        @Override // android.telephony.ims.RegistrationManager.RegistrationCallback
        public void onRegistering(int i) {
        }

        @Override // android.telephony.ims.RegistrationManager.RegistrationCallback
        public void onUnregistered(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }

        @Override // android.telephony.ims.RegistrationManager.RegistrationCallback
        public void onTechnologyChangeFailed(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        }
    }

    public static class CapabilityCallback {
        private final android.telephony.ims.ImsMmTelManager.CapabilityCallback.CapabilityBinder mBinder = new android.telephony.ims.ImsMmTelManager.CapabilityCallback.CapabilityBinder(this);

        /* JADX INFO: Access modifiers changed from: private */
        static class CapabilityBinder extends android.telephony.ims.aidl.IImsCapabilityCallback.Stub {
            private java.util.concurrent.Executor mExecutor;
            private final android.telephony.ims.ImsMmTelManager.CapabilityCallback mLocalCallback;

            CapabilityBinder(android.telephony.ims.ImsMmTelManager.CapabilityCallback capabilityCallback) {
                this.mLocalCallback = capabilityCallback;
            }

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onCapabilitiesStatusChanged(final int i) {
                if (this.mLocalCallback == null) {
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsMmTelManager$CapabilityCallback$CapabilityBinder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.ImsMmTelManager.CapabilityCallback.CapabilityBinder.this.lambda$onCapabilitiesStatusChanged$0(i);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onCapabilitiesStatusChanged$0(int i) {
                this.mLocalCallback.onCapabilitiesStatusChanged(new android.telephony.ims.feature.MmTelFeature.MmTelCapabilities(i));
            }

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onQueryCapabilityConfiguration(int i, int i2, boolean z) {
            }

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onChangeCapabilityConfigurationError(int i, int i2, int i3) {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void setExecutor(java.util.concurrent.Executor executor) {
                this.mExecutor = executor;
            }
        }

        public void onCapabilitiesStatusChanged(android.telephony.ims.feature.MmTelFeature.MmTelCapabilities mmTelCapabilities) {
        }

        public final android.telephony.ims.aidl.IImsCapabilityCallback getBinder() {
            return this.mBinder;
        }

        public final void setExecutor(java.util.concurrent.Executor executor) {
            this.mBinder.setExecutor(executor);
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static android.telephony.ims.ImsMmTelManager createForSubscriptionId(int i) {
        if (!android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            throw new java.lang.IllegalArgumentException("Invalid subscription ID");
        }
        return new android.telephony.ims.ImsMmTelManager(i, sTelephonyCache);
    }

    public ImsMmTelManager(int i, android.telephony.BinderCacheManager<com.android.internal.telephony.ITelephony> binderCacheManager) {
        this(null, i, binderCacheManager);
    }

    public ImsMmTelManager(android.content.Context context, int i, android.telephony.BinderCacheManager<com.android.internal.telephony.ITelephony> binderCacheManager) {
        this.mContext = context;
        this.mSubId = i;
        this.mBinderCache = binderCacheManager;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void registerImsRegistrationCallback(java.util.concurrent.Executor executor, android.telephony.ims.ImsMmTelManager.RegistrationCallback registrationCallback) throws android.telephony.ims.ImsException {
        if (registrationCallback == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        registrationCallback.setExecutor(executor);
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new android.telephony.ims.ImsException("Could not find Telephony Service.", 1);
        }
        try {
            iTelephony.registerImsRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @Override // android.telephony.ims.RegistrationManager
    public void registerImsRegistrationCallback(java.util.concurrent.Executor executor, android.telephony.ims.RegistrationManager.RegistrationCallback registrationCallback) throws android.telephony.ims.ImsException {
        if (registrationCallback == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        registrationCallback.setExecutor(executor);
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new android.telephony.ims.ImsException("Could not find Telephony Service.", 1);
        }
        try {
            iTelephony.registerImsRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void unregisterImsRegistrationCallback(android.telephony.ims.ImsMmTelManager.RegistrationCallback registrationCallback) {
        if (registrationCallback == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.unregisterImsRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.telephony.ims.RegistrationManager
    public void unregisterImsRegistrationCallback(android.telephony.ims.RegistrationManager.RegistrationCallback registrationCallback) {
        if (registrationCallback == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.unregisterImsRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public void registerImsEmergencyRegistrationCallback(java.util.concurrent.Executor executor, android.telephony.ims.RegistrationManager.RegistrationCallback registrationCallback) throws android.telephony.ims.ImsException {
        if (registrationCallback == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        registrationCallback.setExecutor(executor);
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new android.telephony.ims.ImsException("Could not find Telephony Service.", 1);
        }
        try {
            iTelephony.registerImsEmergencyRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        } catch (java.lang.IllegalStateException e3) {
            throw new android.telephony.ims.ImsException(e3.getMessage(), 1);
        }
    }

    @android.annotation.SystemApi
    public void unregisterImsEmergencyRegistrationCallback(android.telephony.ims.RegistrationManager.RegistrationCallback registrationCallback) {
        if (registrationCallback == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            android.util.Log.w(TAG, "Could not find Telephony Service.");
            return;
        }
        try {
            iTelephony.unregisterImsEmergencyRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.telephony.ims.RegistrationManager
    @android.annotation.SystemApi
    public void getRegistrationState(java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        if (consumer == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null callback.");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.getImsMmTelRegistrationState(this.mSubId, new android.telephony.ims.ImsMmTelManager.AnonymousClass1(executor, consumer));
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            android.util.Log.w(TAG, "Error getting registration state: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsMmTelManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(0);
                }
            });
        }
    }

    /* renamed from: android.telephony.ims.ImsMmTelManager$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$stateCallback;

        AnonymousClass1(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$stateCallback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final java.util.function.Consumer consumer = this.val$stateCallback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsMmTelManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(java.lang.Integer.valueOf(i));
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @Override // android.telephony.ims.RegistrationManager
    public void getRegistrationTransportType(java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        if (consumer == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null callback.");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.getImsMmTelRegistrationTransportType(this.mSubId, new android.telephony.ims.ImsMmTelManager.AnonymousClass2(executor, consumer));
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            android.util.Log.w(TAG, "Error getting transport type: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsMmTelManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(-1);
                }
            });
        }
    }

    /* renamed from: android.telephony.ims.ImsMmTelManager$2, reason: invalid class name */
    class AnonymousClass2 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$transportTypeCallback;

        AnonymousClass2(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$transportTypeCallback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final java.util.function.Consumer consumer = this.val$transportTypeCallback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsMmTelManager$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(java.lang.Integer.valueOf(i));
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void registerMmTelCapabilityCallback(java.util.concurrent.Executor executor, android.telephony.ims.ImsMmTelManager.CapabilityCallback capabilityCallback) throws android.telephony.ims.ImsException {
        if (capabilityCallback == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        capabilityCallback.setExecutor(executor);
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new android.telephony.ims.ImsException("Could not find Telephony Service.", 1);
        }
        try {
            iTelephony.registerMmTelCapabilityCallback(this.mSubId, capabilityCallback.getBinder());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        } catch (java.lang.IllegalStateException e3) {
            throw new android.telephony.ims.ImsException(e3.getMessage(), 1);
        }
    }

    public void unregisterMmTelCapabilityCallback(android.telephony.ims.ImsMmTelManager.CapabilityCallback capabilityCallback) {
        if (capabilityCallback == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            android.util.Log.w(TAG, "Could not find Telephony Service.");
            return;
        }
        try {
            iTelephony.unregisterMmTelCapabilityCallback(this.mSubId, capabilityCallback.getBinder());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean isAdvancedCallingSettingEnabled() {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.isAdvancedCallingSettingEnabled(this.mSubId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    @android.annotation.SystemApi
    public void setAdvancedCallingSettingEnabled(boolean z) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setAdvancedCallingSettingEnabled(this.mSubId, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    @android.annotation.SystemApi
    public boolean isCapable(int i, int i2) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.isCapable(this.mSubId, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public boolean isAvailable(int i, int i2) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.isAvailable(this.mSubId, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public void isSupported(int i, int i2, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) throws android.telephony.ims.ImsException {
        if (consumer == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Consumer.");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new android.telephony.ims.ImsException("Could not find Telephony Service.", 1);
        }
        try {
            iTelephony.isMmTelCapabilitySupported(this.mSubId, new android.telephony.ims.ImsMmTelManager.AnonymousClass3(executor, consumer), i, i2);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    /* renamed from: android.telephony.ims.ImsMmTelManager$3, reason: invalid class name */
    class AnonymousClass3 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.function.Consumer val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass3(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final java.util.function.Consumer consumer = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsMmTelManager$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        java.util.function.Consumer consumer2 = consumer;
                        int i2 = i;
                        consumer2.accept(java.lang.Boolean.valueOf(r2 == 1));
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public boolean isVtSettingEnabled() {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.isVtSettingEnabled(this.mSubId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    @android.annotation.SystemApi
    public void setVtSettingEnabled(boolean z) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setVtSettingEnabled(this.mSubId, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    public boolean isVoWiFiSettingEnabled() {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.isVoWiFiSettingEnabled(this.mSubId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    @android.annotation.SystemApi
    public void setVoWiFiSettingEnabled(boolean z) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setVoWiFiSettingEnabled(this.mSubId, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    public boolean isCrossSimCallingEnabled() throws android.telephony.ims.ImsException {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new android.telephony.ims.ImsException("Could not find Telephony Service.", 1);
        }
        try {
            return iTelephony.isCrossSimCallingEnabledByUser(this.mSubId);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @android.annotation.SystemApi
    public void setCrossSimCallingEnabled(boolean z) throws android.telephony.ims.ImsException {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new android.telephony.ims.ImsException("Could not find Telephony Service.", 1);
        }
        try {
            iTelephony.setCrossSimCallingEnabled(this.mSubId, z);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    public boolean isVoWiFiRoamingSettingEnabled() {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.isVoWiFiRoamingSettingEnabled(this.mSubId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    @android.annotation.SystemApi
    public void setVoWiFiRoamingSettingEnabled(boolean z) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setVoWiFiRoamingSettingEnabled(this.mSubId, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    @android.annotation.SystemApi
    public void setVoWiFiNonPersistent(boolean z, int i) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setVoWiFiNonPersistent(this.mSubId, z, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    public int getVoWiFiModeSetting() {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.getVoWiFiModeSetting(this.mSubId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    @android.annotation.SystemApi
    public void setVoWiFiModeSetting(int i) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setVoWiFiModeSetting(this.mSubId, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    @android.annotation.SystemApi
    public int getVoWiFiRoamingModeSetting() {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.getVoWiFiRoamingModeSetting(this.mSubId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    @android.annotation.SystemApi
    public void setVoWiFiRoamingModeSetting(int i) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setVoWiFiRoamingModeSetting(this.mSubId, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    @android.annotation.SystemApi
    public void setRttCapabilitySetting(boolean z) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setRttCapabilitySetting(this.mSubId, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    public boolean isTtyOverVolteEnabled() {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.isTtyOverVolteEnabled(this.mSubId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new java.lang.IllegalArgumentException(e2.getMessage());
            }
            throw new java.lang.RuntimeException(e2.getMessage());
        }
    }

    @android.annotation.SystemApi
    public void getFeatureState(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) throws android.telephony.ims.ImsException {
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        if (consumer == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Consumer.");
        }
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new android.telephony.ims.ImsException("Could not find Telephony Service.", 1);
        }
        try {
            iTelephony.getImsMmTelFeatureState(this.mSubId, new android.telephony.ims.ImsMmTelManager.AnonymousClass4(executor, consumer));
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    /* renamed from: android.telephony.ims.ImsMmTelManager$4, reason: invalid class name */
    class AnonymousClass4 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.function.Consumer val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass4(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final java.util.function.Consumer consumer = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsMmTelManager$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(java.lang.Integer.valueOf(i));
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void registerImsStateCallback(java.util.concurrent.Executor executor, android.telephony.ims.ImsStateCallback imsStateCallback) throws android.telephony.ims.ImsException {
        java.util.Objects.requireNonNull(imsStateCallback, "Must include a non-null ImsStateCallback.");
        java.util.Objects.requireNonNull(executor, "Must include a non-null Executor.");
        imsStateCallback.init(executor);
        android.telephony.BinderCacheManager<com.android.internal.telephony.ITelephony> binderCacheManager = this.mBinderCache;
        java.util.Objects.requireNonNull(imsStateCallback);
        com.android.internal.telephony.ITelephony listenOnBinder = binderCacheManager.listenOnBinder(imsStateCallback, new android.telephony.ims.ImsMmTelManager$$ExternalSyntheticLambda3(imsStateCallback));
        if (listenOnBinder == null) {
            throw new android.telephony.ims.ImsException("Telephony server is down", 1);
        }
        try {
            listenOnBinder.registerImsStateCallback(this.mSubId, 1, imsStateCallback.getCallbackBinder(), getOpPackageName());
        } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    public void unregisterImsStateCallback(android.telephony.ims.ImsStateCallback imsStateCallback) {
        java.util.Objects.requireNonNull(imsStateCallback, "Must include a non-null ImsStateCallback.");
        com.android.internal.telephony.ITelephony removeRunnable = this.mBinderCache.removeRunnable(imsStateCallback);
        if (removeRunnable != null) {
            try {
                removeRunnable.unregisterImsStateCallback(imsStateCallback.getCallbackBinder());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private java.lang.String getOpPackageName() {
        if (this.mContext != null) {
            return this.mContext.getOpPackageName();
        }
        return null;
    }

    private com.android.internal.telephony.ITelephony getITelephony() {
        return this.mBinderCache.getBinder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.internal.telephony.ITelephony getITelephonyInterface() {
        return com.android.internal.telephony.ITelephony.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
    }

    public static java.lang.String wifiCallingModeToString(int i) {
        switch (i) {
            case -1:
                return "UNKNOWN";
            case 0:
                return "WIFI_ONLY";
            case 1:
                return "CELLULAR_PREFERRED";
            case 2:
                return "WIFI_PREFERRED";
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }
}
