package android.telephony.ims;

/* loaded from: classes3.dex */
public class ImsRcsManager {
    public static final java.lang.String ACTION_SHOW_CAPABILITY_DISCOVERY_OPT_IN = "android.telephony.ims.action.SHOW_CAPABILITY_DISCOVERY_OPT_IN";
    public static final int CAPABILITY_TYPE_MAX = 3;
    public static final int CAPABILITY_TYPE_NONE = 0;
    public static final int CAPABILITY_TYPE_OPTIONS_UCE = 1;
    public static final int CAPABILITY_TYPE_PRESENCE_UCE = 2;
    private static final java.lang.String TAG = "ImsRcsManager";
    private final java.util.Map<android.telephony.ims.ImsRcsManager.OnAvailabilityChangedListener, android.telephony.ims.ImsRcsManager.AvailabilityCallbackAdapter> mAvailabilityChangedCallbacks = new java.util.HashMap();
    private final android.telephony.BinderCacheManager<android.telephony.ims.aidl.IImsRcsController> mBinderCache;
    private final android.content.Context mContext;
    private final int mSubId;
    private final android.telephony.BinderCacheManager<com.android.internal.telephony.ITelephony> mTelephonyBinderCache;

    @android.annotation.SystemApi
    public interface OnAvailabilityChangedListener {
        void onAvailabilityChanged(int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RcsImsCapabilityFlag {
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class AvailabilityCallbackAdapter {
        private final android.telephony.ims.ImsRcsManager.AvailabilityCallbackAdapter.CapabilityBinder mBinder;

        /* JADX INFO: Access modifiers changed from: private */
        static class CapabilityBinder extends android.telephony.ims.aidl.IImsCapabilityCallback.Stub {
            private final java.util.concurrent.Executor mExecutor;
            private final android.telephony.ims.ImsRcsManager.OnAvailabilityChangedListener mOnAvailabilityChangedListener;

            CapabilityBinder(android.telephony.ims.ImsRcsManager.OnAvailabilityChangedListener onAvailabilityChangedListener, java.util.concurrent.Executor executor) {
                this.mExecutor = executor;
                this.mOnAvailabilityChangedListener = onAvailabilityChangedListener;
            }

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onCapabilitiesStatusChanged(final int i) {
                if (this.mOnAvailabilityChangedListener == null) {
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsRcsManager$AvailabilityCallbackAdapter$CapabilityBinder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.ims.ImsRcsManager.AvailabilityCallbackAdapter.CapabilityBinder.this.lambda$onCapabilitiesStatusChanged$0(i);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onCapabilitiesStatusChanged$0(int i) {
                this.mOnAvailabilityChangedListener.onAvailabilityChanged(i);
            }

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onQueryCapabilityConfiguration(int i, int i2, boolean z) {
            }

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onChangeCapabilityConfigurationError(int i, int i2, int i3) {
            }
        }

        AvailabilityCallbackAdapter(java.util.concurrent.Executor executor, android.telephony.ims.ImsRcsManager.OnAvailabilityChangedListener onAvailabilityChangedListener) {
            this.mBinder = new android.telephony.ims.ImsRcsManager.AvailabilityCallbackAdapter.CapabilityBinder(onAvailabilityChangedListener, executor);
        }

        public final android.telephony.ims.aidl.IImsCapabilityCallback getBinder() {
            return this.mBinder;
        }
    }

    public ImsRcsManager(android.content.Context context, int i, android.telephony.BinderCacheManager<android.telephony.ims.aidl.IImsRcsController> binderCacheManager, android.telephony.BinderCacheManager<com.android.internal.telephony.ITelephony> binderCacheManager2) {
        this.mSubId = i;
        this.mContext = context;
        this.mBinderCache = binderCacheManager;
        this.mTelephonyBinderCache = binderCacheManager2;
    }

    public android.telephony.ims.RcsUceAdapter getUceAdapter() {
        return new android.telephony.ims.RcsUceAdapter(this.mContext, this.mSubId);
    }

    public void registerImsRegistrationCallback(java.util.concurrent.Executor executor, android.telephony.ims.RegistrationManager.RegistrationCallback registrationCallback) throws android.telephony.ims.ImsException {
        if (registrationCallback == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.w(TAG, "Register registration callback: IImsRcsController is null");
            throw new android.telephony.ims.ImsException("Cannot find remote IMS service", 1);
        }
        registrationCallback.setExecutor(executor);
        try {
            iImsRcsController.registerImsRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.toString(), e2.errorCode);
        }
    }

    public void unregisterImsRegistrationCallback(android.telephony.ims.RegistrationManager.RegistrationCallback registrationCallback) {
        if (registrationCallback == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.w(TAG, "Unregister registration callback: IImsRcsController is null");
            throw new java.lang.IllegalStateException("Cannot find remote IMS service");
        }
        try {
            iImsRcsController.unregisterImsRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void getRegistrationState(java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        if (consumer == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null stateCallback.");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.w(TAG, "Get registration state error: IImsRcsController is null");
            throw new java.lang.IllegalStateException("Cannot find remote IMS service");
        }
        try {
            iImsRcsController.getImsRcsRegistrationState(this.mSubId, new android.telephony.ims.ImsRcsManager.AnonymousClass1(executor, consumer));
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            android.util.Log.w(TAG, "Get registration state error: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsRcsManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(0);
                }
            });
        }
    }

    /* renamed from: android.telephony.ims.ImsRcsManager$1, reason: invalid class name */
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
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsRcsManager$1$$ExternalSyntheticLambda0
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

    public void getRegistrationTransportType(java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        if (consumer == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null transportTypeCallback.");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.w(TAG, "Get registration transport type error: IImsRcsController is null");
            throw new java.lang.IllegalStateException("Cannot find remote IMS service");
        }
        try {
            iImsRcsController.getImsRcsRegistrationTransportType(this.mSubId, new android.telephony.ims.ImsRcsManager.AnonymousClass2(executor, consumer));
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            android.util.Log.w(TAG, "Get registration transport type error: " + e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsRcsManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(-1);
                }
            });
        }
    }

    /* renamed from: android.telephony.ims.ImsRcsManager$2, reason: invalid class name */
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
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsRcsManager$2$$ExternalSyntheticLambda0
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

    @android.annotation.SystemApi
    public void addOnAvailabilityChangedListener(java.util.concurrent.Executor executor, android.telephony.ims.ImsRcsManager.OnAvailabilityChangedListener onAvailabilityChangedListener) throws android.telephony.ims.ImsException {
        if (onAvailabilityChangedListener == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-nullOnAvailabilityChangedListener.");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-null Executor.");
        }
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.w(TAG, "Add availability changed listener: IImsRcsController is null");
            throw new android.telephony.ims.ImsException("Cannot find remote IMS service", 1);
        }
        try {
            iImsRcsController.registerRcsAvailabilityCallback(this.mSubId, addAvailabilityChangedListenerToCollection(executor, onAvailabilityChangedListener).getBinder());
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Error calling IImsRcsController#registerRcsAvailabilityCallback", e);
            throw new android.telephony.ims.ImsException("Remote IMS Service is not available", 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.toString(), e2.errorCode);
        }
    }

    @android.annotation.SystemApi
    public void removeOnAvailabilityChangedListener(android.telephony.ims.ImsRcsManager.OnAvailabilityChangedListener onAvailabilityChangedListener) {
        if (onAvailabilityChangedListener == null) {
            throw new java.lang.IllegalArgumentException("Must include a non-nullOnAvailabilityChangedListener.");
        }
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.w(TAG, "Remove availability changed listener: IImsRcsController is null");
            return;
        }
        android.telephony.ims.ImsRcsManager.AvailabilityCallbackAdapter removeAvailabilityChangedListenerFromCollection = removeAvailabilityChangedListenerFromCollection(onAvailabilityChangedListener);
        if (removeAvailabilityChangedListenerFromCollection == null) {
            return;
        }
        try {
            iImsRcsController.unregisterRcsAvailabilityCallback(this.mSubId, removeAvailabilityChangedListenerFromCollection.getBinder());
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Error calling IImsRcsController#unregisterRcsAvailabilityCallback", e);
        }
    }

    @android.annotation.SystemApi
    public boolean isCapable(int i, int i2) throws android.telephony.ims.ImsException {
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.w(TAG, "isCapable: IImsRcsController is null");
            throw new android.telephony.ims.ImsException("Cannot find remote IMS service", 1);
        }
        try {
            return iImsRcsController.isCapable(this.mSubId, i, i2);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Error calling IImsRcsController#isCapable", e);
            throw new android.telephony.ims.ImsException("Remote IMS Service is not available", 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @android.annotation.SystemApi
    public boolean isAvailable(int i, int i2) throws android.telephony.ims.ImsException {
        android.telephony.ims.aidl.IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            android.util.Log.w(TAG, "isAvailable: IImsRcsController is null");
            throw new android.telephony.ims.ImsException("Cannot find remote IMS service", 1);
        }
        try {
            return iImsRcsController.isAvailable(this.mSubId, i, i2);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Error calling IImsRcsController#isAvailable", e);
            throw new android.telephony.ims.ImsException("Remote IMS Service is not available", 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    public void registerImsStateCallback(java.util.concurrent.Executor executor, android.telephony.ims.ImsStateCallback imsStateCallback) throws android.telephony.ims.ImsException {
        java.util.Objects.requireNonNull(imsStateCallback, "Must include a non-null ImsStateCallback.");
        java.util.Objects.requireNonNull(executor, "Must include a non-null Executor.");
        imsStateCallback.init(executor);
        android.telephony.BinderCacheManager<com.android.internal.telephony.ITelephony> binderCacheManager = this.mTelephonyBinderCache;
        java.util.Objects.requireNonNull(imsStateCallback);
        com.android.internal.telephony.ITelephony listenOnBinder = binderCacheManager.listenOnBinder(imsStateCallback, new android.telephony.ims.ImsMmTelManager$$ExternalSyntheticLambda3(imsStateCallback));
        if (listenOnBinder == null) {
            throw new android.telephony.ims.ImsException("Telephony server is down", 1);
        }
        try {
            listenOnBinder.registerImsStateCallback(this.mSubId, 2, imsStateCallback.getCallbackBinder(), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.telephony.ims.ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    public void unregisterImsStateCallback(android.telephony.ims.ImsStateCallback imsStateCallback) {
        java.util.Objects.requireNonNull(imsStateCallback, "Must include a non-null ImsStateCallback.");
        com.android.internal.telephony.ITelephony removeRunnable = this.mTelephonyBinderCache.removeRunnable(imsStateCallback);
        if (removeRunnable != null) {
            try {
                removeRunnable.unregisterImsStateCallback(imsStateCallback.getCallbackBinder());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private android.telephony.ims.ImsRcsManager.AvailabilityCallbackAdapter addAvailabilityChangedListenerToCollection(java.util.concurrent.Executor executor, android.telephony.ims.ImsRcsManager.OnAvailabilityChangedListener onAvailabilityChangedListener) {
        android.telephony.ims.ImsRcsManager.AvailabilityCallbackAdapter availabilityCallbackAdapter = new android.telephony.ims.ImsRcsManager.AvailabilityCallbackAdapter(executor, onAvailabilityChangedListener);
        synchronized (this.mAvailabilityChangedCallbacks) {
            this.mAvailabilityChangedCallbacks.put(onAvailabilityChangedListener, availabilityCallbackAdapter);
        }
        return availabilityCallbackAdapter;
    }

    private android.telephony.ims.ImsRcsManager.AvailabilityCallbackAdapter removeAvailabilityChangedListenerFromCollection(android.telephony.ims.ImsRcsManager.OnAvailabilityChangedListener onAvailabilityChangedListener) {
        android.telephony.ims.ImsRcsManager.AvailabilityCallbackAdapter remove;
        synchronized (this.mAvailabilityChangedCallbacks) {
            remove = this.mAvailabilityChangedCallbacks.remove(onAvailabilityChangedListener);
        }
        return remove;
    }

    private android.telephony.ims.aidl.IImsRcsController getIImsRcsController() {
        return android.telephony.ims.aidl.IImsRcsController.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyImsServiceRegisterer().get());
    }
}
