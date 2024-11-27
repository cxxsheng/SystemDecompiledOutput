package android.service.remotelockscreenvalidation;

/* loaded from: classes3.dex */
public class RemoteLockscreenValidationClientImpl implements android.service.remotelockscreenvalidation.RemoteLockscreenValidationClient, android.content.ServiceConnection {
    private static final java.lang.String TAG = android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.class.getSimpleName();
    private final android.content.Context mContext;
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    private boolean mIsConnected;
    private final boolean mIsServiceAvailable;
    private final java.util.concurrent.Executor mLifecycleExecutor;
    private final java.util.Queue<android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.Call> mRequestQueue;
    private android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService mService;
    private android.content.pm.ServiceInfo mServiceInfo;

    RemoteLockscreenValidationClientImpl(android.content.Context context, java.util.concurrent.Executor executor, android.content.ComponentName componentName) {
        this.mContext = context.getApplicationContext();
        this.mIsServiceAvailable = isServiceAvailable(this.mContext, componentName);
        this.mLifecycleExecutor = executor == null ? new android.app.PendingIntent$$ExternalSyntheticLambda0() : executor;
        this.mRequestQueue = new java.util.ArrayDeque();
    }

    @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClient
    public boolean isServiceAvailable() {
        return this.mIsServiceAvailable;
    }

    @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClient
    public void validateLockscreenGuess(final byte[] bArr, final android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback iRemoteLockscreenValidationCallback) {
        try {
            if (!isServiceAvailable()) {
                iRemoteLockscreenValidationCallback.onFailure("Service is not available");
                return;
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error while failing for service unavailable", e);
        }
        executeApiCall(new android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.Call() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.Call
            public void exec(android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService iRemoteLockscreenValidationService) throws android.os.RemoteException {
                iRemoteLockscreenValidationService.validateLockscreenGuess(bArr, iRemoteLockscreenValidationCallback);
            }

            @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.Call
            void onError(java.lang.String str) {
                try {
                    iRemoteLockscreenValidationCallback.onFailure(str);
                } catch (android.os.RemoteException e2) {
                    android.util.Log.e(android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.TAG, "Error while failing validateLockscreenGuess", e2);
                }
            }
        });
    }

    @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClient
    public void disconnect() {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.this.disconnectInternal();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnectInternal() {
        if (!this.mIsConnected) {
            android.util.Log.w(TAG, "already disconnected");
            return;
        }
        this.mIsConnected = false;
        this.mLifecycleExecutor.execute(new java.lang.Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.this.lambda$disconnectInternal$0();
            }
        });
        this.mService = null;
        this.mRequestQueue.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disconnectInternal$0() {
        this.mContext.unbindService(this);
    }

    private void connect() {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.this.connectInternal();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectInternal() {
        if (this.mServiceInfo == null) {
            android.util.Log.w(TAG, "RemoteLockscreenValidation service unavailable");
            return;
        }
        if (this.mIsConnected) {
            return;
        }
        this.mIsConnected = true;
        final android.content.Intent intent = new android.content.Intent(android.service.remotelockscreenvalidation.RemoteLockscreenValidationService.SERVICE_INTERFACE);
        intent.setComponent(this.mServiceInfo.getComponentName());
        final int i = 33;
        this.mLifecycleExecutor.execute(new java.lang.Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.this.lambda$connectInternal$1(intent, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectInternal$1(android.content.Intent intent, int i) {
        this.mContext.bindService(intent, this, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onConnectedInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$onServiceConnected$3(android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService iRemoteLockscreenValidationService) {
        if (!this.mIsConnected) {
            android.util.Log.w(TAG, "onConnectInternal but connection closed");
            this.mService = null;
            return;
        }
        this.mService = iRemoteLockscreenValidationService;
        java.util.Iterator it = new java.util.ArrayList(this.mRequestQueue).iterator();
        while (it.hasNext()) {
            android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.Call call = (android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.Call) it.next();
            performApiCallInternal(call, this.mService);
            this.mRequestQueue.remove(call);
        }
    }

    private boolean isServiceAvailable(android.content.Context context, android.content.ComponentName componentName) {
        this.mServiceInfo = getServiceInfo(context, componentName);
        if (this.mServiceInfo == null) {
            return false;
        }
        if (!android.Manifest.permission.BIND_REMOTE_LOCKSCREEN_VALIDATION_SERVICE.equals(this.mServiceInfo.permission)) {
            android.util.Log.w(TAG, android.text.TextUtils.formatSimple("%s/%s does not require permission %s", this.mServiceInfo.packageName, this.mServiceInfo.name, android.Manifest.permission.BIND_REMOTE_LOCKSCREEN_VALIDATION_SERVICE));
            return false;
        }
        return true;
    }

    private android.content.pm.ServiceInfo getServiceInfo(android.content.Context context, android.content.ComponentName componentName) {
        try {
            return context.getPackageManager().getServiceInfo(componentName, android.content.pm.PackageManager.ComponentInfoFlags.of(128L));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, android.text.TextUtils.formatSimple("Cannot resolve service %s", componentName.getClassName()));
            return null;
        }
    }

    private void executeApiCall(final android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.Call call) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.this.lambda$executeApiCall$2(call);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: executeInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$executeApiCall$2(android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.Call call) {
        if (this.mIsConnected && this.mService != null) {
            performApiCallInternal(call, this.mService);
        } else {
            this.mRequestQueue.add(call);
            connect();
        }
    }

    private void performApiCallInternal(android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.Call call, android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService iRemoteLockscreenValidationService) {
        if (iRemoteLockscreenValidationService == null) {
            call.onError("Service is null");
            return;
        }
        try {
            call.exec(iRemoteLockscreenValidationService);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "executeInternal error", e);
            call.onError(e.getMessage());
            disconnect();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        final android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService asInterface = android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService.Stub.asInterface(iBinder);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.this.lambda$onServiceConnected$3(asInterface);
            }
        });
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(android.content.ComponentName componentName) {
    }

    @Override // android.content.ServiceConnection
    public void onBindingDied(android.content.ComponentName componentName) {
        disconnect();
    }

    @Override // android.content.ServiceConnection
    public void onNullBinding(android.content.ComponentName componentName) {
        disconnect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class Call {
        abstract void exec(android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService iRemoteLockscreenValidationService) throws android.os.RemoteException;

        abstract void onError(java.lang.String str);

        private Call() {
        }
    }
}
