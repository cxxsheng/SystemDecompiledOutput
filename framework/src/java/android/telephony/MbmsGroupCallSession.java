package android.telephony;

/* loaded from: classes3.dex */
public class MbmsGroupCallSession implements java.lang.AutoCloseable {
    private static final java.lang.String LOG_TAG = "MbmsGroupCallSession";

    @android.annotation.SystemApi
    public static final java.lang.String MBMS_GROUP_CALL_SERVICE_ACTION = "android.telephony.action.EmbmsGroupCall";
    public static final java.lang.String MBMS_GROUP_CALL_SERVICE_OVERRIDE_METADATA = "mbms-group-call-service-override";
    private static java.util.concurrent.atomic.AtomicBoolean sIsInitialized = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final android.content.Context mContext;
    private android.telephony.mbms.InternalGroupCallSessionCallback mInternalCallback;
    private android.content.ServiceConnection mServiceConnection;
    private int mSubscriptionId;
    private java.util.concurrent.atomic.AtomicReference<android.telephony.mbms.vendor.IMbmsGroupCallService> mService = new java.util.concurrent.atomic.AtomicReference<>(null);
    private android.os.IBinder.DeathRecipient mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: android.telephony.MbmsGroupCallSession.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.telephony.MbmsGroupCallSession.sIsInitialized.set(false);
            android.telephony.MbmsGroupCallSession.this.mInternalCallback.onError(3, "Received death notification");
        }
    };
    private java.util.Set<android.telephony.mbms.GroupCall> mKnownActiveGroupCalls = new android.util.ArraySet();

    private MbmsGroupCallSession(android.content.Context context, java.util.concurrent.Executor executor, int i, android.telephony.mbms.MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback) {
        this.mContext = context;
        this.mSubscriptionId = i;
        this.mInternalCallback = new android.telephony.mbms.InternalGroupCallSessionCallback(mbmsGroupCallSessionCallback, executor);
    }

    public static android.telephony.MbmsGroupCallSession create(android.content.Context context, int i, java.util.concurrent.Executor executor, final android.telephony.mbms.MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback) {
        if (!sIsInitialized.compareAndSet(false, true)) {
            throw new java.lang.IllegalStateException("Cannot create two instances of MbmsGroupCallSession");
        }
        android.telephony.MbmsGroupCallSession mbmsGroupCallSession = new android.telephony.MbmsGroupCallSession(context, executor, i, mbmsGroupCallSessionCallback);
        final int bindAndInitialize = mbmsGroupCallSession.bindAndInitialize();
        if (bindAndInitialize != 0) {
            sIsInitialized.set(false);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.MbmsGroupCallSession.2
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.MbmsGroupCallSessionCallback.this.onError(bindAndInitialize, null);
                }
            });
            return null;
        }
        return mbmsGroupCallSession;
    }

    public static android.telephony.MbmsGroupCallSession create(android.content.Context context, java.util.concurrent.Executor executor, android.telephony.mbms.MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback) {
        return create(context, android.telephony.SubscriptionManager.getDefaultSubscriptionId(), executor, mbmsGroupCallSessionCallback);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        android.telephony.mbms.vendor.IMbmsGroupCallService iMbmsGroupCallService;
        try {
            iMbmsGroupCallService = this.mService.get();
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            this.mService.set(null);
            sIsInitialized.set(false);
            this.mServiceConnection = null;
            this.mInternalCallback.stop();
            throw th;
        }
        if (iMbmsGroupCallService != null && this.mServiceConnection != null) {
            iMbmsGroupCallService.dispose(this.mSubscriptionId);
            java.util.Iterator<android.telephony.mbms.GroupCall> it = this.mKnownActiveGroupCalls.iterator();
            while (it.hasNext()) {
                it.next().getCallback().stop();
            }
            this.mKnownActiveGroupCalls.clear();
            this.mContext.unbindService(this.mServiceConnection);
            this.mService.set(null);
            sIsInitialized.set(false);
            this.mServiceConnection = null;
            this.mInternalCallback.stop();
            return;
        }
        this.mService.set(null);
        sIsInitialized.set(false);
        this.mServiceConnection = null;
        this.mInternalCallback.stop();
    }

    public android.telephony.mbms.GroupCall startGroupCall(long j, java.util.List<java.lang.Integer> list, java.util.List<java.lang.Integer> list2, java.util.concurrent.Executor executor, android.telephony.mbms.GroupCallCallback groupCallCallback) {
        android.telephony.mbms.vendor.IMbmsGroupCallService iMbmsGroupCallService = this.mService.get();
        if (iMbmsGroupCallService == null) {
            throw new java.lang.IllegalStateException("Middleware not yet bound");
        }
        android.telephony.mbms.InternalGroupCallCallback internalGroupCallCallback = new android.telephony.mbms.InternalGroupCallCallback(groupCallCallback, executor);
        android.telephony.mbms.GroupCall groupCall = new android.telephony.mbms.GroupCall(this.mSubscriptionId, iMbmsGroupCallService, this, j, internalGroupCallCallback);
        this.mKnownActiveGroupCalls.add(groupCall);
        try {
            int startGroupCall = iMbmsGroupCallService.startGroupCall(this.mSubscriptionId, j, list, list2, internalGroupCallCallback);
            if (startGroupCall == -1) {
                close();
                throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
            }
            if (startGroupCall != 0) {
                this.mInternalCallback.onError(startGroupCall, null);
                return null;
            }
            return groupCall;
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "Remote process died");
            this.mService.set(null);
            sIsInitialized.set(false);
            this.mInternalCallback.onError(3, null);
            return null;
        }
    }

    public void onGroupCallStopped(android.telephony.mbms.GroupCall groupCall) {
        this.mKnownActiveGroupCalls.remove(groupCall);
    }

    private int bindAndInitialize() {
        this.mServiceConnection = new android.content.ServiceConnection() { // from class: android.telephony.MbmsGroupCallSession.3
            @Override // android.content.ServiceConnection
            public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                android.telephony.mbms.vendor.IMbmsGroupCallService asInterface = android.telephony.mbms.vendor.IMbmsGroupCallService.Stub.asInterface(iBinder);
                try {
                    int initialize = asInterface.initialize(android.telephony.MbmsGroupCallSession.this.mInternalCallback, android.telephony.MbmsGroupCallSession.this.mSubscriptionId);
                    if (initialize == -1) {
                        android.telephony.MbmsGroupCallSession.this.close();
                        throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
                    }
                    if (initialize != 0) {
                        android.telephony.MbmsGroupCallSession.this.mInternalCallback.onError(initialize, "Error returned during initialization");
                        android.telephony.MbmsGroupCallSession.sIsInitialized.set(false);
                        return;
                    }
                    try {
                        asInterface.asBinder().linkToDeath(android.telephony.MbmsGroupCallSession.this.mDeathRecipient, 0);
                        android.telephony.MbmsGroupCallSession.this.mService.set(asInterface);
                    } catch (android.os.RemoteException e) {
                        android.telephony.MbmsGroupCallSession.this.mInternalCallback.onError(3, "Middleware lost during initialization");
                        android.telephony.MbmsGroupCallSession.sIsInitialized.set(false);
                    }
                } catch (android.os.RemoteException e2) {
                    android.util.Log.e(android.telephony.MbmsGroupCallSession.LOG_TAG, "Service died before initialization");
                    android.telephony.MbmsGroupCallSession.this.mInternalCallback.onError(103, e2.toString());
                    android.telephony.MbmsGroupCallSession.sIsInitialized.set(false);
                } catch (java.lang.RuntimeException e3) {
                    android.util.Log.e(android.telephony.MbmsGroupCallSession.LOG_TAG, "Runtime exception during initialization");
                    android.telephony.MbmsGroupCallSession.this.mInternalCallback.onError(103, e3.toString());
                    android.telephony.MbmsGroupCallSession.sIsInitialized.set(false);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(android.content.ComponentName componentName) {
                android.telephony.MbmsGroupCallSession.sIsInitialized.set(false);
                android.telephony.MbmsGroupCallSession.this.mService.set(null);
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(android.content.ComponentName componentName) {
                android.util.Log.w(android.telephony.MbmsGroupCallSession.LOG_TAG, "bindAndInitialize: Remote service returned null");
                android.telephony.MbmsGroupCallSession.this.mInternalCallback.onError(3, "Middleware service binding returned null");
                android.telephony.MbmsGroupCallSession.sIsInitialized.set(false);
                android.telephony.MbmsGroupCallSession.this.mService.set(null);
                android.telephony.MbmsGroupCallSession.this.mContext.unbindService(this);
            }
        };
        return android.telephony.mbms.MbmsUtils.startBinding(this.mContext, MBMS_GROUP_CALL_SERVICE_ACTION, this.mServiceConnection);
    }
}
