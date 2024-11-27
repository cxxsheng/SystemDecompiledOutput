package android.hardware.location;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class ContextHubManager {
    public static final int AUTHORIZATION_DENIED = 0;
    public static final int AUTHORIZATION_DENIED_GRACE_PERIOD = 1;
    public static final int AUTHORIZATION_GRANTED = 2;
    public static final int EVENT_CLIENT_AUTHORIZATION = 7;
    public static final int EVENT_HUB_RESET = 6;
    public static final int EVENT_NANOAPP_ABORTED = 4;
    public static final int EVENT_NANOAPP_DISABLED = 3;
    public static final int EVENT_NANOAPP_ENABLED = 2;
    public static final int EVENT_NANOAPP_LOADED = 0;
    public static final int EVENT_NANOAPP_MESSAGE = 5;
    public static final int EVENT_NANOAPP_UNLOADED = 1;
    public static final java.lang.String EXTRA_CLIENT_AUTHORIZATION_STATE = "android.hardware.location.extra.CLIENT_AUTHORIZATION_STATE";
    public static final java.lang.String EXTRA_CONTEXT_HUB_INFO = "android.hardware.location.extra.CONTEXT_HUB_INFO";
    public static final java.lang.String EXTRA_EVENT_TYPE = "android.hardware.location.extra.EVENT_TYPE";
    public static final java.lang.String EXTRA_MESSAGE = "android.hardware.location.extra.MESSAGE";
    public static final java.lang.String EXTRA_NANOAPP_ABORT_CODE = "android.hardware.location.extra.NANOAPP_ABORT_CODE";
    public static final java.lang.String EXTRA_NANOAPP_ID = "android.hardware.location.extra.NANOAPP_ID";
    private static final java.lang.String TAG = "ContextHubManager";
    private android.hardware.location.ContextHubManager.Callback mCallback;
    private android.os.Handler mCallbackHandler;
    private final android.hardware.location.IContextHubCallback.Stub mClientCallback = new android.hardware.location.ContextHubManager.AnonymousClass3();

    @java.lang.Deprecated
    private android.hardware.location.ContextHubManager.ICallback mLocalCallback;
    private final android.os.Looper mMainLooper;
    private final android.hardware.location.IContextHubService mService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AuthorizationState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Event {
    }

    @java.lang.Deprecated
    public interface ICallback {
        void onMessageReceipt(int i, int i2, android.hardware.location.ContextHubMessage contextHubMessage);
    }

    @java.lang.Deprecated
    public static abstract class Callback {
        public abstract void onMessageReceipt(int i, int i2, android.hardware.location.ContextHubMessage contextHubMessage);

        protected Callback() {
        }
    }

    @java.lang.Deprecated
    public int[] getContextHubHandles() {
        try {
            return this.mService.getContextHubHandles();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public android.hardware.location.ContextHubInfo getContextHubInfo(int i) {
        try {
            return this.mService.getContextHubInfo(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public int loadNanoApp(int i, android.hardware.location.NanoApp nanoApp) {
        try {
            return this.mService.loadNanoApp(i, nanoApp);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public int unloadNanoApp(int i) {
        try {
            return this.mService.unloadNanoApp(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public android.hardware.location.NanoAppInstanceInfo getNanoAppInstanceInfo(int i) {
        try {
            return this.mService.getNanoAppInstanceInfo(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public int[] findNanoAppOnHub(int i, android.hardware.location.NanoAppFilter nanoAppFilter) {
        try {
            return this.mService.findNanoAppOnHub(i, nanoAppFilter);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public int sendMessage(int i, int i2, android.hardware.location.ContextHubMessage contextHubMessage) {
        try {
            return this.mService.sendMessage(i, i2, contextHubMessage);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.hardware.location.ContextHubInfo> getContextHubs() {
        try {
            return this.mService.getContextHubs();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private android.hardware.location.IContextHubTransactionCallback createQueryCallback(final android.hardware.location.ContextHubTransaction<java.util.List<android.hardware.location.NanoAppState>> contextHubTransaction) {
        return new android.hardware.location.IContextHubTransactionCallback.Stub() { // from class: android.hardware.location.ContextHubManager.1
            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onQueryResponse(int i, java.util.List<android.hardware.location.NanoAppState> list) {
                contextHubTransaction.setResponse(new android.hardware.location.ContextHubTransaction.Response(i, list));
            }

            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onTransactionComplete(int i) {
                android.util.Log.e(android.hardware.location.ContextHubManager.TAG, "Received a non-query callback on a query request");
                contextHubTransaction.setResponse(new android.hardware.location.ContextHubTransaction.Response(7, null));
            }
        };
    }

    public android.hardware.location.ContextHubTransaction<java.lang.Void> loadNanoApp(android.hardware.location.ContextHubInfo contextHubInfo, android.hardware.location.NanoAppBinary nanoAppBinary) {
        java.util.Objects.requireNonNull(contextHubInfo, "ContextHubInfo cannot be null");
        java.util.Objects.requireNonNull(nanoAppBinary, "NanoAppBinary cannot be null");
        android.hardware.location.ContextHubTransaction<java.lang.Void> contextHubTransaction = new android.hardware.location.ContextHubTransaction<>(0);
        try {
            this.mService.loadNanoAppOnHub(contextHubInfo.getId(), android.hardware.location.ContextHubTransactionHelper.createTransactionCallback(contextHubTransaction), nanoAppBinary);
            return contextHubTransaction;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.location.ContextHubTransaction<java.lang.Void> unloadNanoApp(android.hardware.location.ContextHubInfo contextHubInfo, long j) {
        java.util.Objects.requireNonNull(contextHubInfo, "ContextHubInfo cannot be null");
        android.hardware.location.ContextHubTransaction<java.lang.Void> contextHubTransaction = new android.hardware.location.ContextHubTransaction<>(1);
        try {
            this.mService.unloadNanoAppFromHub(contextHubInfo.getId(), android.hardware.location.ContextHubTransactionHelper.createTransactionCallback(contextHubTransaction), j);
            return contextHubTransaction;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.location.ContextHubTransaction<java.lang.Void> enableNanoApp(android.hardware.location.ContextHubInfo contextHubInfo, long j) {
        java.util.Objects.requireNonNull(contextHubInfo, "ContextHubInfo cannot be null");
        android.hardware.location.ContextHubTransaction<java.lang.Void> contextHubTransaction = new android.hardware.location.ContextHubTransaction<>(2);
        try {
            this.mService.enableNanoApp(contextHubInfo.getId(), android.hardware.location.ContextHubTransactionHelper.createTransactionCallback(contextHubTransaction), j);
            return contextHubTransaction;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.location.ContextHubTransaction<java.lang.Void> disableNanoApp(android.hardware.location.ContextHubInfo contextHubInfo, long j) {
        java.util.Objects.requireNonNull(contextHubInfo, "ContextHubInfo cannot be null");
        android.hardware.location.ContextHubTransaction<java.lang.Void> contextHubTransaction = new android.hardware.location.ContextHubTransaction<>(3);
        try {
            this.mService.disableNanoApp(contextHubInfo.getId(), android.hardware.location.ContextHubTransactionHelper.createTransactionCallback(contextHubTransaction), j);
            return contextHubTransaction;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.location.ContextHubTransaction<java.util.List<android.hardware.location.NanoAppState>> queryNanoApps(android.hardware.location.ContextHubInfo contextHubInfo) {
        java.util.Objects.requireNonNull(contextHubInfo, "ContextHubInfo cannot be null");
        android.hardware.location.ContextHubTransaction<java.util.List<android.hardware.location.NanoAppState>> contextHubTransaction = new android.hardware.location.ContextHubTransaction<>(4);
        try {
            this.mService.queryNanoApps(contextHubInfo.getId(), createQueryCallback(contextHubTransaction));
            return contextHubTransaction;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public int registerCallback(android.hardware.location.ContextHubManager.Callback callback) {
        return registerCallback(callback, null);
    }

    @java.lang.Deprecated
    public int registerCallback(android.hardware.location.ContextHubManager.ICallback iCallback) {
        if (this.mLocalCallback != null) {
            android.util.Log.w(TAG, "Max number of local callbacks reached!");
            return -1;
        }
        this.mLocalCallback = iCallback;
        return 0;
    }

    @java.lang.Deprecated
    public int registerCallback(android.hardware.location.ContextHubManager.Callback callback, android.os.Handler handler) {
        synchronized (this) {
            if (this.mCallback != null) {
                android.util.Log.w(TAG, "Max number of callbacks reached!");
                return -1;
            }
            this.mCallback = callback;
            if (handler == null) {
                handler = new android.os.Handler(this.mMainLooper);
            }
            this.mCallbackHandler = handler;
            return 0;
        }
    }

    /* renamed from: android.hardware.location.ContextHubManager$2, reason: invalid class name */
    class AnonymousClass2 extends android.hardware.location.IContextHubClientCallback.Stub {
        final /* synthetic */ android.hardware.location.ContextHubClientCallback val$callback;
        final /* synthetic */ android.hardware.location.ContextHubClient val$client;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass2(java.util.concurrent.Executor executor, android.hardware.location.ContextHubClientCallback contextHubClientCallback, android.hardware.location.ContextHubClient contextHubClient) {
            this.val$executor = executor;
            this.val$callback = contextHubClientCallback;
            this.val$client = contextHubClient;
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onMessageFromNanoApp(final android.hardware.location.NanoAppMessage nanoAppMessage) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.hardware.location.ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final android.hardware.location.ContextHubClient contextHubClient = this.val$client;
            executor.execute(new java.lang.Runnable() { // from class: android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.location.ContextHubManager.AnonymousClass2.lambda$onMessageFromNanoApp$0(android.hardware.location.ContextHubClientCallback.this, contextHubClient, nanoAppMessage);
                }
            });
        }

        static /* synthetic */ void lambda$onMessageFromNanoApp$0(android.hardware.location.ContextHubClientCallback contextHubClientCallback, android.hardware.location.ContextHubClient contextHubClient, android.hardware.location.NanoAppMessage nanoAppMessage) {
            contextHubClientCallback.onMessageFromNanoApp(contextHubClient, nanoAppMessage);
            if (android.chre.flags.Flags.reliableMessage() && android.chre.flags.Flags.reliableMessageImplementation() && nanoAppMessage.isReliable()) {
                contextHubClient.reliableMessageCallbackFinished(nanoAppMessage.getMessageSequenceNumber(), (byte) 0);
            } else {
                contextHubClient.callbackFinished();
            }
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onHubReset() {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.hardware.location.ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final android.hardware.location.ContextHubClient contextHubClient = this.val$client;
            executor.execute(new java.lang.Runnable() { // from class: android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.location.ContextHubManager.AnonymousClass2.lambda$onHubReset$1(android.hardware.location.ContextHubClientCallback.this, contextHubClient);
                }
            });
        }

        static /* synthetic */ void lambda$onHubReset$1(android.hardware.location.ContextHubClientCallback contextHubClientCallback, android.hardware.location.ContextHubClient contextHubClient) {
            contextHubClientCallback.onHubReset(contextHubClient);
            contextHubClient.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppAborted(final long j, final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.hardware.location.ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final android.hardware.location.ContextHubClient contextHubClient = this.val$client;
            executor.execute(new java.lang.Runnable() { // from class: android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.location.ContextHubManager.AnonymousClass2.lambda$onNanoAppAborted$2(android.hardware.location.ContextHubClientCallback.this, contextHubClient, j, i);
                }
            });
        }

        static /* synthetic */ void lambda$onNanoAppAborted$2(android.hardware.location.ContextHubClientCallback contextHubClientCallback, android.hardware.location.ContextHubClient contextHubClient, long j, int i) {
            contextHubClientCallback.onNanoAppAborted(contextHubClient, j, i);
            contextHubClient.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppLoaded(final long j) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.hardware.location.ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final android.hardware.location.ContextHubClient contextHubClient = this.val$client;
            executor.execute(new java.lang.Runnable() { // from class: android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.location.ContextHubManager.AnonymousClass2.lambda$onNanoAppLoaded$3(android.hardware.location.ContextHubClientCallback.this, contextHubClient, j);
                }
            });
        }

        static /* synthetic */ void lambda$onNanoAppLoaded$3(android.hardware.location.ContextHubClientCallback contextHubClientCallback, android.hardware.location.ContextHubClient contextHubClient, long j) {
            contextHubClientCallback.onNanoAppLoaded(contextHubClient, j);
            contextHubClient.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppUnloaded(final long j) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.hardware.location.ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final android.hardware.location.ContextHubClient contextHubClient = this.val$client;
            executor.execute(new java.lang.Runnable() { // from class: android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.location.ContextHubManager.AnonymousClass2.lambda$onNanoAppUnloaded$4(android.hardware.location.ContextHubClientCallback.this, contextHubClient, j);
                }
            });
        }

        static /* synthetic */ void lambda$onNanoAppUnloaded$4(android.hardware.location.ContextHubClientCallback contextHubClientCallback, android.hardware.location.ContextHubClient contextHubClient, long j) {
            contextHubClientCallback.onNanoAppUnloaded(contextHubClient, j);
            contextHubClient.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppEnabled(final long j) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.hardware.location.ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final android.hardware.location.ContextHubClient contextHubClient = this.val$client;
            executor.execute(new java.lang.Runnable() { // from class: android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.location.ContextHubManager.AnonymousClass2.lambda$onNanoAppEnabled$5(android.hardware.location.ContextHubClientCallback.this, contextHubClient, j);
                }
            });
        }

        static /* synthetic */ void lambda$onNanoAppEnabled$5(android.hardware.location.ContextHubClientCallback contextHubClientCallback, android.hardware.location.ContextHubClient contextHubClient, long j) {
            contextHubClientCallback.onNanoAppEnabled(contextHubClient, j);
            contextHubClient.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppDisabled(final long j) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.hardware.location.ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final android.hardware.location.ContextHubClient contextHubClient = this.val$client;
            executor.execute(new java.lang.Runnable() { // from class: android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.location.ContextHubManager.AnonymousClass2.lambda$onNanoAppDisabled$6(android.hardware.location.ContextHubClientCallback.this, contextHubClient, j);
                }
            });
        }

        static /* synthetic */ void lambda$onNanoAppDisabled$6(android.hardware.location.ContextHubClientCallback contextHubClientCallback, android.hardware.location.ContextHubClient contextHubClient, long j) {
            contextHubClientCallback.onNanoAppDisabled(contextHubClient, j);
            contextHubClient.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onClientAuthorizationChanged(final long j, final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.hardware.location.ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final android.hardware.location.ContextHubClient contextHubClient = this.val$client;
            executor.execute(new java.lang.Runnable() { // from class: android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.location.ContextHubManager.AnonymousClass2.lambda$onClientAuthorizationChanged$7(android.hardware.location.ContextHubClientCallback.this, contextHubClient, j, i);
                }
            });
        }

        static /* synthetic */ void lambda$onClientAuthorizationChanged$7(android.hardware.location.ContextHubClientCallback contextHubClientCallback, android.hardware.location.ContextHubClient contextHubClient, long j, int i) {
            contextHubClientCallback.onClientAuthorizationChanged(contextHubClient, j, i);
            contextHubClient.callbackFinished();
        }
    }

    private android.hardware.location.IContextHubClientCallback createClientCallback(android.hardware.location.ContextHubClient contextHubClient, android.hardware.location.ContextHubClientCallback contextHubClientCallback, java.util.concurrent.Executor executor) {
        return new android.hardware.location.ContextHubManager.AnonymousClass2(executor, contextHubClientCallback, contextHubClient);
    }

    public android.hardware.location.ContextHubClient createClient(android.content.Context context, android.hardware.location.ContextHubInfo contextHubInfo, java.util.concurrent.Executor executor, android.hardware.location.ContextHubClientCallback contextHubClientCallback) {
        java.lang.String str;
        java.lang.String currentPackageName;
        java.util.Objects.requireNonNull(contextHubClientCallback, "Callback cannot be null");
        java.util.Objects.requireNonNull(contextHubInfo, "ContextHubInfo cannot be null");
        java.util.Objects.requireNonNull(executor, "Executor cannot be null");
        android.hardware.location.ContextHubClient contextHubClient = new android.hardware.location.ContextHubClient(contextHubInfo, false);
        android.hardware.location.IContextHubClientCallback createClientCallback = createClientCallback(contextHubClient, contextHubClientCallback, executor);
        if (context == null) {
            str = null;
        } else {
            str = context.getAttributionTag();
        }
        if (context != null) {
            currentPackageName = context.getPackageName();
        } else {
            currentPackageName = android.app.ActivityThread.currentPackageName();
        }
        try {
            contextHubClient.setClientProxy(this.mService.createClient(contextHubInfo.getId(), createClientCallback, str, currentPackageName));
            return contextHubClient;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.location.ContextHubClient createClient(android.hardware.location.ContextHubInfo contextHubInfo, android.hardware.location.ContextHubClientCallback contextHubClientCallback, java.util.concurrent.Executor executor) {
        return createClient((android.content.Context) null, contextHubInfo, executor, contextHubClientCallback);
    }

    public android.hardware.location.ContextHubClient createClient(android.hardware.location.ContextHubInfo contextHubInfo, android.hardware.location.ContextHubClientCallback contextHubClientCallback) {
        return createClient((android.content.Context) null, contextHubInfo, new android.os.HandlerExecutor(android.os.Handler.getMain()), contextHubClientCallback);
    }

    public android.hardware.location.ContextHubClient createClient(android.content.Context context, android.hardware.location.ContextHubInfo contextHubInfo, android.app.PendingIntent pendingIntent, long j) {
        java.lang.String str;
        java.util.Objects.requireNonNull(pendingIntent);
        java.util.Objects.requireNonNull(contextHubInfo);
        if (pendingIntent.isImmutable()) {
            throw new java.lang.IllegalArgumentException("PendingIntent must be mutable");
        }
        android.hardware.location.ContextHubClient contextHubClient = new android.hardware.location.ContextHubClient(contextHubInfo, true);
        if (context == null) {
            str = null;
        } else {
            str = context.getAttributionTag();
        }
        try {
            contextHubClient.setClientProxy(this.mService.createPendingIntentClient(contextHubInfo.getId(), pendingIntent, j, str));
            return contextHubClient;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.location.ContextHubClient createClient(android.hardware.location.ContextHubInfo contextHubInfo, android.app.PendingIntent pendingIntent, long j) {
        return createClient((android.content.Context) null, contextHubInfo, pendingIntent, j);
    }

    public long[] getPreloadedNanoAppIds(android.hardware.location.ContextHubInfo contextHubInfo) {
        java.util.Objects.requireNonNull(contextHubInfo, "hubInfo cannot be null");
        try {
            long[] preloadedNanoAppIds = this.mService.getPreloadedNanoAppIds(contextHubInfo);
            if (preloadedNanoAppIds == null) {
                return new long[0];
            }
            return preloadedNanoAppIds;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean enableTestMode() {
        try {
            return this.mService.setTestMode(true);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean disableTestMode() {
        try {
            return this.mService.setTestMode(false);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public int unregisterCallback(android.hardware.location.ContextHubManager.Callback callback) {
        synchronized (this) {
            if (callback != this.mCallback) {
                android.util.Log.w(TAG, "Cannot recognize callback!");
                return -1;
            }
            this.mCallback = null;
            this.mCallbackHandler = null;
            return 0;
        }
    }

    @java.lang.Deprecated
    public synchronized int unregisterCallback(android.hardware.location.ContextHubManager.ICallback iCallback) {
        if (iCallback != this.mLocalCallback) {
            android.util.Log.w(TAG, "Cannot recognize local callback!");
            return -1;
        }
        this.mLocalCallback = null;
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void invokeOnMessageReceiptCallback(int i, int i2, android.hardware.location.ContextHubMessage contextHubMessage) {
        if (this.mCallback != null) {
            this.mCallback.onMessageReceipt(i, i2, contextHubMessage);
        }
    }

    /* renamed from: android.hardware.location.ContextHubManager$3, reason: invalid class name */
    class AnonymousClass3 extends android.hardware.location.IContextHubCallback.Stub {
        AnonymousClass3() {
        }

        @Override // android.hardware.location.IContextHubCallback
        public void onMessageReceipt(final int i, final int i2, final android.hardware.location.ContextHubMessage contextHubMessage) {
            synchronized (android.hardware.location.ContextHubManager.this) {
                if (android.hardware.location.ContextHubManager.this.mCallback != null) {
                    android.hardware.location.ContextHubManager.this.mCallbackHandler.post(new java.lang.Runnable() { // from class: android.hardware.location.ContextHubManager$3$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.location.ContextHubManager.AnonymousClass3.this.lambda$onMessageReceipt$0(i, i2, contextHubMessage);
                        }
                    });
                } else if (android.hardware.location.ContextHubManager.this.mLocalCallback != null) {
                    android.hardware.location.ContextHubManager.this.mLocalCallback.onMessageReceipt(i, i2, contextHubMessage);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageReceipt$0(int i, int i2, android.hardware.location.ContextHubMessage contextHubMessage) {
            android.hardware.location.ContextHubManager.this.invokeOnMessageReceiptCallback(i, i2, contextHubMessage);
        }
    }

    public ContextHubManager(android.hardware.location.IContextHubService iContextHubService, android.os.Looper looper) {
        java.util.Objects.requireNonNull(iContextHubService, "service cannot be null");
        java.util.Objects.requireNonNull(looper, "mainLooper cannot be null");
        this.mService = iContextHubService;
        this.mMainLooper = looper;
        try {
            this.mService.registerCallback(this.mClientCallback);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
