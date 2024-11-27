package android.telephony;

/* loaded from: classes3.dex */
public class MbmsStreamingSession implements java.lang.AutoCloseable {
    private static final java.lang.String LOG_TAG = "MbmsStreamingSession";

    @android.annotation.SystemApi
    public static final java.lang.String MBMS_STREAMING_SERVICE_ACTION = "android.telephony.action.EmbmsStreaming";
    public static final java.lang.String MBMS_STREAMING_SERVICE_OVERRIDE_METADATA = "mbms-streaming-service-override";
    private static java.util.concurrent.atomic.AtomicBoolean sIsInitialized = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final android.content.Context mContext;
    private android.telephony.mbms.InternalStreamingSessionCallback mInternalCallback;
    private android.content.ServiceConnection mServiceConnection;
    private int mSubscriptionId;
    private java.util.concurrent.atomic.AtomicReference<android.telephony.mbms.vendor.IMbmsStreamingService> mService = new java.util.concurrent.atomic.AtomicReference<>(null);
    private android.os.IBinder.DeathRecipient mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: android.telephony.MbmsStreamingSession.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.telephony.MbmsStreamingSession.sIsInitialized.set(false);
            android.telephony.MbmsStreamingSession.this.sendErrorToApp(3, "Received death notification");
        }
    };
    private java.util.Set<android.telephony.mbms.StreamingService> mKnownActiveStreamingServices = new android.util.ArraySet();

    private MbmsStreamingSession(android.content.Context context, java.util.concurrent.Executor executor, int i, android.telephony.mbms.MbmsStreamingSessionCallback mbmsStreamingSessionCallback) {
        this.mSubscriptionId = -1;
        this.mContext = context;
        this.mSubscriptionId = i;
        this.mInternalCallback = new android.telephony.mbms.InternalStreamingSessionCallback(mbmsStreamingSessionCallback, executor);
    }

    public static android.telephony.MbmsStreamingSession create(android.content.Context context, java.util.concurrent.Executor executor, int i, final android.telephony.mbms.MbmsStreamingSessionCallback mbmsStreamingSessionCallback) {
        if (!sIsInitialized.compareAndSet(false, true)) {
            throw new java.lang.IllegalStateException("Cannot create two instances of MbmsStreamingSession");
        }
        android.telephony.MbmsStreamingSession mbmsStreamingSession = new android.telephony.MbmsStreamingSession(context, executor, i, mbmsStreamingSessionCallback);
        final int bindAndInitialize = mbmsStreamingSession.bindAndInitialize();
        if (bindAndInitialize != 0) {
            sIsInitialized.set(false);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.MbmsStreamingSession.2
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.MbmsStreamingSessionCallback.this.onError(bindAndInitialize, null);
                }
            });
            return null;
        }
        return mbmsStreamingSession;
    }

    public static android.telephony.MbmsStreamingSession create(android.content.Context context, java.util.concurrent.Executor executor, android.telephony.mbms.MbmsStreamingSessionCallback mbmsStreamingSessionCallback) {
        return create(context, executor, android.telephony.SubscriptionManager.getDefaultSubscriptionId(), mbmsStreamingSessionCallback);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        android.telephony.mbms.vendor.IMbmsStreamingService iMbmsStreamingService;
        try {
            iMbmsStreamingService = this.mService.get();
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            this.mService.set(null);
            sIsInitialized.set(false);
            this.mServiceConnection = null;
            this.mInternalCallback.stop();
            throw th;
        }
        if (iMbmsStreamingService != null && this.mServiceConnection != null) {
            iMbmsStreamingService.dispose(this.mSubscriptionId);
            java.util.Iterator<android.telephony.mbms.StreamingService> it = this.mKnownActiveStreamingServices.iterator();
            while (it.hasNext()) {
                it.next().getCallback().stop();
            }
            this.mKnownActiveStreamingServices.clear();
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

    public void requestUpdateStreamingServices(java.util.List<java.lang.String> list) {
        android.telephony.mbms.vendor.IMbmsStreamingService iMbmsStreamingService = this.mService.get();
        if (iMbmsStreamingService == null) {
            throw new java.lang.IllegalStateException("Middleware not yet bound");
        }
        try {
            int requestUpdateStreamingServices = iMbmsStreamingService.requestUpdateStreamingServices(this.mSubscriptionId, list);
            if (requestUpdateStreamingServices == -1) {
                close();
                throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
            }
            if (requestUpdateStreamingServices != 0) {
                sendErrorToApp(requestUpdateStreamingServices, null);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "Remote process died");
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public android.telephony.mbms.StreamingService startStreaming(android.telephony.mbms.StreamingServiceInfo streamingServiceInfo, java.util.concurrent.Executor executor, android.telephony.mbms.StreamingServiceCallback streamingServiceCallback) {
        android.telephony.mbms.vendor.IMbmsStreamingService iMbmsStreamingService = this.mService.get();
        if (iMbmsStreamingService == null) {
            throw new java.lang.IllegalStateException("Middleware not yet bound");
        }
        android.telephony.mbms.InternalStreamingServiceCallback internalStreamingServiceCallback = new android.telephony.mbms.InternalStreamingServiceCallback(streamingServiceCallback, executor);
        android.telephony.mbms.StreamingService streamingService = new android.telephony.mbms.StreamingService(this.mSubscriptionId, iMbmsStreamingService, this, streamingServiceInfo, internalStreamingServiceCallback);
        this.mKnownActiveStreamingServices.add(streamingService);
        try {
            int startStreaming = iMbmsStreamingService.startStreaming(this.mSubscriptionId, streamingServiceInfo.getServiceId(), internalStreamingServiceCallback);
            if (startStreaming == -1) {
                close();
                throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
            }
            if (startStreaming != 0) {
                sendErrorToApp(startStreaming, null);
                return null;
            }
            return streamingService;
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "Remote process died");
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
            return null;
        }
    }

    public void onStreamingServiceStopped(android.telephony.mbms.StreamingService streamingService) {
        this.mKnownActiveStreamingServices.remove(streamingService);
    }

    private int bindAndInitialize() {
        this.mServiceConnection = new android.content.ServiceConnection() { // from class: android.telephony.MbmsStreamingSession.3
            @Override // android.content.ServiceConnection
            public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                android.telephony.mbms.vendor.IMbmsStreamingService asInterface = android.telephony.mbms.vendor.IMbmsStreamingService.Stub.asInterface(iBinder);
                try {
                    int initialize = asInterface.initialize(android.telephony.MbmsStreamingSession.this.mInternalCallback, android.telephony.MbmsStreamingSession.this.mSubscriptionId);
                    if (initialize == -1) {
                        android.telephony.MbmsStreamingSession.this.close();
                        throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
                    }
                    if (initialize != 0) {
                        android.telephony.MbmsStreamingSession.this.sendErrorToApp(initialize, "Error returned during initialization");
                        android.telephony.MbmsStreamingSession.sIsInitialized.set(false);
                        return;
                    }
                    try {
                        asInterface.asBinder().linkToDeath(android.telephony.MbmsStreamingSession.this.mDeathRecipient, 0);
                        android.telephony.MbmsStreamingSession.this.mService.set(asInterface);
                    } catch (android.os.RemoteException e) {
                        android.telephony.MbmsStreamingSession.this.sendErrorToApp(3, "Middleware lost during initialization");
                        android.telephony.MbmsStreamingSession.sIsInitialized.set(false);
                    }
                } catch (android.os.RemoteException e2) {
                    android.util.Log.e(android.telephony.MbmsStreamingSession.LOG_TAG, "Service died before initialization");
                    android.telephony.MbmsStreamingSession.this.sendErrorToApp(103, e2.toString());
                    android.telephony.MbmsStreamingSession.sIsInitialized.set(false);
                } catch (java.lang.RuntimeException e3) {
                    android.util.Log.e(android.telephony.MbmsStreamingSession.LOG_TAG, "Runtime exception during initialization");
                    android.telephony.MbmsStreamingSession.this.sendErrorToApp(103, e3.toString());
                    android.telephony.MbmsStreamingSession.sIsInitialized.set(false);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(android.content.ComponentName componentName) {
                android.telephony.MbmsStreamingSession.sIsInitialized.set(false);
                android.telephony.MbmsStreamingSession.this.mService.set(null);
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(android.content.ComponentName componentName) {
                android.util.Log.w(android.telephony.MbmsStreamingSession.LOG_TAG, "bindAndInitialize: Remote service returned null");
                android.telephony.MbmsStreamingSession.this.sendErrorToApp(3, "Middleware service binding returned null");
                android.telephony.MbmsStreamingSession.sIsInitialized.set(false);
                android.telephony.MbmsStreamingSession.this.mService.set(null);
                android.telephony.MbmsStreamingSession.this.mContext.unbindService(this);
            }
        };
        return android.telephony.mbms.MbmsUtils.startBinding(this.mContext, MBMS_STREAMING_SERVICE_ACTION, this.mServiceConnection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorToApp(int i, java.lang.String str) {
        try {
            this.mInternalCallback.onError(i, str);
        } catch (android.os.RemoteException e) {
        }
    }
}
