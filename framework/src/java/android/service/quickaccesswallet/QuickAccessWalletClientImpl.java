package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
public class QuickAccessWalletClientImpl implements android.service.quickaccesswallet.QuickAccessWalletClient, android.content.ServiceConnection {
    private static final int MSG_TIMEOUT_SERVICE = 5;
    private static final long SERVICE_CONNECTION_TIMEOUT_MS = 60000;
    public static final java.lang.String SETTING_KEY = "lockscreen_show_wallet";
    private static final java.lang.String TAG = "QAWalletSClient";
    private final android.content.Context mContext;
    private final java.util.Map<android.service.quickaccesswallet.QuickAccessWalletClient.WalletServiceEventListener, java.lang.String> mEventListeners;
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    private boolean mIsConnected;
    private final java.util.concurrent.Executor mLifecycleExecutor;
    private final java.util.Queue<android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller> mRequestQueue;
    private android.service.quickaccesswallet.IQuickAccessWalletService mService;
    private final android.service.quickaccesswallet.QuickAccessWalletServiceInfo mServiceInfo;

    QuickAccessWalletClientImpl(android.content.Context context, java.util.concurrent.Executor executor) {
        this.mContext = context.getApplicationContext();
        this.mServiceInfo = android.service.quickaccesswallet.QuickAccessWalletServiceInfo.tryCreate(context);
        this.mLifecycleExecutor = executor == null ? new android.app.PendingIntent$$ExternalSyntheticLambda0() : executor;
        this.mRequestQueue = new java.util.ArrayDeque();
        this.mEventListeners = new java.util.HashMap(1);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public boolean isWalletServiceAvailable() {
        return this.mServiceInfo != null;
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public boolean isWalletFeatureAvailable() {
        int currentUser = android.app.ActivityManager.getCurrentUser();
        return currentUser == 0 && checkUserSetupComplete() && !new com.android.internal.widget.LockPatternUtils(this.mContext).isUserInLockdown(currentUser);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public boolean isWalletFeatureAvailableWhenDeviceLocked() {
        return checkSecureSetting("lockscreen_show_wallet");
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void getWalletCards(android.service.quickaccesswallet.GetWalletCardsRequest getWalletCardsRequest, android.service.quickaccesswallet.QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback) {
        getWalletCards(this.mContext.getMainExecutor(), getWalletCardsRequest, onWalletCardsRetrievedCallback);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void getWalletCards(java.util.concurrent.Executor executor, final android.service.quickaccesswallet.GetWalletCardsRequest getWalletCardsRequest, final android.service.quickaccesswallet.QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback) {
        if (!isWalletServiceAvailable()) {
            executor.execute(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.quickaccesswallet.QuickAccessWalletClient.OnWalletCardsRetrievedCallback.this.onWalletCardRetrievalError(new android.service.quickaccesswallet.GetWalletCardsError(null, null));
                }
            });
        } else {
            final android.service.quickaccesswallet.QuickAccessWalletClientImpl.AnonymousClass1 anonymousClass1 = new android.service.quickaccesswallet.QuickAccessWalletClientImpl.AnonymousClass1(executor, onWalletCardsRetrievedCallback);
            executeApiCall(new android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller("onWalletCardsRequested") { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl.2
                @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
                public void performApiCall(android.service.quickaccesswallet.IQuickAccessWalletService iQuickAccessWalletService) throws android.os.RemoteException {
                    iQuickAccessWalletService.onWalletCardsRequested(getWalletCardsRequest, anonymousClass1);
                }

                @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
                public void onApiError() {
                    anonymousClass1.onGetWalletCardsFailure(new android.service.quickaccesswallet.GetWalletCardsError(null, null));
                }
            });
        }
    }

    /* renamed from: android.service.quickaccesswallet.QuickAccessWalletClientImpl$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.quickaccesswallet.QuickAccessWalletClientImpl.BaseCallbacks {
        final /* synthetic */ android.service.quickaccesswallet.QuickAccessWalletClient.OnWalletCardsRetrievedCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(java.util.concurrent.Executor executor, android.service.quickaccesswallet.QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback) {
            super();
            this.val$executor = executor;
            this.val$callback = onWalletCardsRetrievedCallback;
        }

        @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.BaseCallbacks, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onGetWalletCardsSuccess(final android.service.quickaccesswallet.GetWalletCardsResponse getWalletCardsResponse) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.service.quickaccesswallet.QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.quickaccesswallet.QuickAccessWalletClient.OnWalletCardsRetrievedCallback.this.onWalletCardsRetrieved(getWalletCardsResponse);
                }
            });
        }

        @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.BaseCallbacks, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onGetWalletCardsFailure(final android.service.quickaccesswallet.GetWalletCardsError getWalletCardsError) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.service.quickaccesswallet.QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.quickaccesswallet.QuickAccessWalletClient.OnWalletCardsRetrievedCallback.this.onWalletCardRetrievalError(getWalletCardsError);
                }
            });
        }
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void selectWalletCard(final android.service.quickaccesswallet.SelectWalletCardRequest selectWalletCardRequest) {
        if (!isWalletServiceAvailable()) {
            return;
        }
        executeApiCall(new android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller("onWalletCardSelected") { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl.3
            @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
            public void performApiCall(android.service.quickaccesswallet.IQuickAccessWalletService iQuickAccessWalletService) throws android.os.RemoteException {
                iQuickAccessWalletService.onWalletCardSelected(selectWalletCardRequest);
            }
        });
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void notifyWalletDismissed() {
        if (!isWalletServiceAvailable()) {
            return;
        }
        executeApiCall(new android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller("onWalletDismissed") { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl.4
            @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
            public void performApiCall(android.service.quickaccesswallet.IQuickAccessWalletService iQuickAccessWalletService) throws android.os.RemoteException {
                iQuickAccessWalletService.onWalletDismissed();
            }
        });
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void addWalletServiceEventListener(android.service.quickaccesswallet.QuickAccessWalletClient.WalletServiceEventListener walletServiceEventListener) {
        addWalletServiceEventListener(this.mContext.getMainExecutor(), walletServiceEventListener);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void addWalletServiceEventListener(java.util.concurrent.Executor executor, final android.service.quickaccesswallet.QuickAccessWalletClient.WalletServiceEventListener walletServiceEventListener) {
        if (!isWalletServiceAvailable()) {
            return;
        }
        final android.service.quickaccesswallet.QuickAccessWalletClientImpl.AnonymousClass5 anonymousClass5 = new android.service.quickaccesswallet.QuickAccessWalletClientImpl.AnonymousClass5(executor, walletServiceEventListener);
        executeApiCall(new android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller("registerListener") { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl.6
            @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
            public void performApiCall(android.service.quickaccesswallet.IQuickAccessWalletService iQuickAccessWalletService) throws android.os.RemoteException {
                java.lang.String obj = java.util.UUID.randomUUID().toString();
                android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest = new android.service.quickaccesswallet.WalletServiceEventListenerRequest(obj);
                android.service.quickaccesswallet.QuickAccessWalletClientImpl.this.mEventListeners.put(walletServiceEventListener, obj);
                iQuickAccessWalletService.registerWalletServiceEventListener(walletServiceEventListenerRequest, anonymousClass5);
            }
        });
    }

    /* renamed from: android.service.quickaccesswallet.QuickAccessWalletClientImpl$5, reason: invalid class name */
    class AnonymousClass5 extends android.service.quickaccesswallet.QuickAccessWalletClientImpl.BaseCallbacks {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.service.quickaccesswallet.QuickAccessWalletClient.WalletServiceEventListener val$listener;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass5(java.util.concurrent.Executor executor, android.service.quickaccesswallet.QuickAccessWalletClient.WalletServiceEventListener walletServiceEventListener) {
            super();
            this.val$executor = executor;
            this.val$listener = walletServiceEventListener;
        }

        @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.BaseCallbacks, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onWalletServiceEvent(final android.service.quickaccesswallet.WalletServiceEvent walletServiceEvent) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.service.quickaccesswallet.QuickAccessWalletClient.WalletServiceEventListener walletServiceEventListener = this.val$listener;
            executor.execute(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.quickaccesswallet.QuickAccessWalletClient.WalletServiceEventListener.this.onWalletServiceEvent(walletServiceEvent);
                }
            });
        }
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void removeWalletServiceEventListener(final android.service.quickaccesswallet.QuickAccessWalletClient.WalletServiceEventListener walletServiceEventListener) {
        if (!isWalletServiceAvailable()) {
            return;
        }
        executeApiCall(new android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller("unregisterListener") { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl.7
            @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
            public void performApiCall(android.service.quickaccesswallet.IQuickAccessWalletService iQuickAccessWalletService) throws android.os.RemoteException {
                java.lang.String str = (java.lang.String) android.service.quickaccesswallet.QuickAccessWalletClientImpl.this.mEventListeners.remove(walletServiceEventListener);
                if (str == null) {
                    return;
                }
                iQuickAccessWalletService.unregisterWalletServiceEventListener(new android.service.quickaccesswallet.WalletServiceEventListenerRequest(str));
            }
        });
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        disconnect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disconnect$1() {
        disconnectInternal(true);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void disconnect() {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                android.service.quickaccesswallet.QuickAccessWalletClientImpl.this.lambda$disconnect$1();
            }
        });
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public android.content.Intent createWalletIntent() {
        if (this.mServiceInfo == null) {
            return null;
        }
        return createIntent(this.mServiceInfo.getWalletActivity(), this.mServiceInfo.getComponentName().getPackageName(), android.service.quickaccesswallet.QuickAccessWalletService.ACTION_VIEW_WALLET);
    }

    /* renamed from: android.service.quickaccesswallet.QuickAccessWalletClientImpl$8, reason: invalid class name */
    class AnonymousClass8 extends android.service.quickaccesswallet.QuickAccessWalletClientImpl.BaseCallbacks {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.service.quickaccesswallet.QuickAccessWalletClient.WalletPendingIntentCallback val$pendingIntentCallback;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass8(java.util.concurrent.Executor executor, android.service.quickaccesswallet.QuickAccessWalletClient.WalletPendingIntentCallback walletPendingIntentCallback) {
            super();
            this.val$executor = executor;
            this.val$pendingIntentCallback = walletPendingIntentCallback;
        }

        @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.BaseCallbacks, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onTargetActivityPendingIntentReceived(final android.app.PendingIntent pendingIntent) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.service.quickaccesswallet.QuickAccessWalletClient.WalletPendingIntentCallback walletPendingIntentCallback = this.val$pendingIntentCallback;
            executor.execute(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$8$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.quickaccesswallet.QuickAccessWalletClient.WalletPendingIntentCallback.this.onWalletPendingIntentRetrieved(pendingIntent);
                }
            });
        }
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public void getWalletPendingIntent(java.util.concurrent.Executor executor, android.service.quickaccesswallet.QuickAccessWalletClient.WalletPendingIntentCallback walletPendingIntentCallback) {
        final android.service.quickaccesswallet.QuickAccessWalletClientImpl.AnonymousClass8 anonymousClass8 = new android.service.quickaccesswallet.QuickAccessWalletClientImpl.AnonymousClass8(executor, walletPendingIntentCallback);
        executeApiCall(new android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller("getTargetActivityPendingIntent") { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl.9
            @Override // android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller
            void performApiCall(android.service.quickaccesswallet.IQuickAccessWalletService iQuickAccessWalletService) throws android.os.RemoteException {
                iQuickAccessWalletService.onTargetActivityIntentRequested(anonymousClass8);
            }
        });
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public android.content.Intent createWalletSettingsIntent() {
        if (this.mServiceInfo == null) {
            return null;
        }
        return createIntent(this.mServiceInfo.getSettingsActivity(), this.mServiceInfo.getComponentName().getPackageName(), android.service.quickaccesswallet.QuickAccessWalletService.ACTION_VIEW_WALLET_SETTINGS);
    }

    private android.content.Intent createIntent(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        if (android.text.TextUtils.isEmpty(str)) {
            str = queryActivityForAction(packageManager, str2, str3);
        }
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        android.content.ComponentName componentName = new android.content.ComponentName(str2, str);
        if (isActivityEnabled(packageManager, componentName)) {
            return new android.content.Intent(str3).setComponent(componentName);
        }
        return null;
    }

    private static java.lang.String queryActivityForAction(android.content.pm.PackageManager packageManager, java.lang.String str, java.lang.String str2) {
        android.content.pm.ResolveInfo resolveActivity = packageManager.resolveActivity(new android.content.Intent(str2).setPackage(str), 0);
        if (resolveActivity == null || resolveActivity.activityInfo == null || !resolveActivity.activityInfo.exported) {
            return null;
        }
        return resolveActivity.activityInfo.name;
    }

    private static boolean isActivityEnabled(android.content.pm.PackageManager packageManager, android.content.ComponentName componentName) {
        int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentName);
        if (componentEnabledSetting == 1) {
            return true;
        }
        if (componentEnabledSetting != 0) {
            return false;
        }
        try {
            return packageManager.getActivityInfo(componentName, 0).isEnabled();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public android.graphics.drawable.Drawable getLogo() {
        if (this.mServiceInfo == null) {
            return null;
        }
        return this.mServiceInfo.getWalletLogo(this.mContext);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public android.graphics.drawable.Drawable getTileIcon() {
        if (this.mServiceInfo == null) {
            return null;
        }
        return this.mServiceInfo.getTileIcon();
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public java.lang.CharSequence getServiceLabel() {
        if (this.mServiceInfo == null) {
            return null;
        }
        return this.mServiceInfo.getServiceLabel(this.mContext);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public java.lang.CharSequence getShortcutShortLabel() {
        if (this.mServiceInfo == null) {
            return null;
        }
        return this.mServiceInfo.getShortcutShortLabel(this.mContext);
    }

    @Override // android.service.quickaccesswallet.QuickAccessWalletClient
    public java.lang.CharSequence getShortcutLongLabel() {
        if (this.mServiceInfo == null) {
            return null;
        }
        return this.mServiceInfo.getShortcutLongLabel(this.mContext);
    }

    private void connect() {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                android.service.quickaccesswallet.QuickAccessWalletClientImpl.this.connectInternal();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectInternal() {
        if (this.mServiceInfo == null) {
            android.util.Log.w(TAG, "Wallet service unavailable");
            return;
        }
        if (this.mIsConnected) {
            return;
        }
        this.mIsConnected = true;
        final android.content.Intent intent = new android.content.Intent(android.service.quickaccesswallet.QuickAccessWalletService.SERVICE_INTERFACE);
        intent.setComponent(this.mServiceInfo.getComponentName());
        final int i = 33;
        this.mLifecycleExecutor.execute(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                android.service.quickaccesswallet.QuickAccessWalletClientImpl.this.lambda$connectInternal$2(intent, i);
            }
        });
        resetServiceConnectionTimeout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectInternal$2(android.content.Intent intent, int i) {
        this.mContext.bindService(intent, this, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onConnectedInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$onServiceConnected$7(android.service.quickaccesswallet.IQuickAccessWalletService iQuickAccessWalletService) {
        if (!this.mIsConnected) {
            android.util.Log.w(TAG, "onConnectInternal but connection closed");
            this.mService = null;
            return;
        }
        this.mService = iQuickAccessWalletService;
        java.util.Iterator it = new java.util.ArrayList(this.mRequestQueue).iterator();
        while (it.hasNext()) {
            android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller apiCaller = (android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller) it.next();
            performApiCallInternal(apiCaller, this.mService);
            this.mRequestQueue.remove(apiCaller);
        }
    }

    private void resetServiceConnectionTimeout() {
        this.mHandler.removeMessages(5);
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                android.service.quickaccesswallet.QuickAccessWalletClientImpl.this.lambda$resetServiceConnectionTimeout$3();
            }
        }, 5, 60000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetServiceConnectionTimeout$3() {
        disconnectInternal(true);
    }

    private void disconnectInternal(boolean z) {
        if (!this.mIsConnected) {
            android.util.Log.w(TAG, "already disconnected");
            return;
        }
        if (z && !this.mEventListeners.isEmpty()) {
            java.util.Iterator<android.service.quickaccesswallet.QuickAccessWalletClient.WalletServiceEventListener> it = this.mEventListeners.keySet().iterator();
            while (it.hasNext()) {
                removeWalletServiceEventListener(it.next());
            }
            this.mHandler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.quickaccesswallet.QuickAccessWalletClientImpl.this.lambda$disconnectInternal$4();
                }
            });
            return;
        }
        this.mIsConnected = false;
        this.mLifecycleExecutor.execute(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.service.quickaccesswallet.QuickAccessWalletClientImpl.this.lambda$disconnectInternal$5();
            }
        });
        this.mService = null;
        this.mEventListeners.clear();
        this.mRequestQueue.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disconnectInternal$4() {
        disconnectInternal(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disconnectInternal$5() {
        this.mContext.unbindService(this);
    }

    private void executeApiCall(final android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller apiCaller) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                android.service.quickaccesswallet.QuickAccessWalletClientImpl.this.lambda$executeApiCall$6(apiCaller);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: executeInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$executeApiCall$6(android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller apiCaller) {
        if (this.mIsConnected && this.mService != null) {
            performApiCallInternal(apiCaller, this.mService);
        } else {
            this.mRequestQueue.add(apiCaller);
            connect();
        }
    }

    private void performApiCallInternal(android.service.quickaccesswallet.QuickAccessWalletClientImpl.ApiCaller apiCaller, android.service.quickaccesswallet.IQuickAccessWalletService iQuickAccessWalletService) {
        if (iQuickAccessWalletService == null) {
            apiCaller.onApiError();
            return;
        }
        try {
            apiCaller.performApiCall(iQuickAccessWalletService);
            resetServiceConnectionTimeout();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "executeInternal error: " + apiCaller.mDesc, e);
            apiCaller.onApiError();
            disconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class ApiCaller {
        private final java.lang.String mDesc;

        abstract void performApiCall(android.service.quickaccesswallet.IQuickAccessWalletService iQuickAccessWalletService) throws android.os.RemoteException;

        private ApiCaller(java.lang.String str) {
            this.mDesc = str;
        }

        void onApiError() {
            android.util.Log.w(android.service.quickaccesswallet.QuickAccessWalletClientImpl.TAG, "api error: " + this.mDesc);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        final android.service.quickaccesswallet.IQuickAccessWalletService asInterface = android.service.quickaccesswallet.IQuickAccessWalletService.Stub.asInterface(iBinder);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletClientImpl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                android.service.quickaccesswallet.QuickAccessWalletClientImpl.this.lambda$onServiceConnected$7(asInterface);
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

    private boolean checkSecureSetting(java.lang.String str) {
        return android.provider.Settings.Secure.getInt(this.mContext.getContentResolver(), str, 0) == 1;
    }

    private boolean checkUserSetupComplete() {
        return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.USER_SETUP_COMPLETE, 0, -2) == 1;
    }

    private static class BaseCallbacks extends android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks.Stub {
        private BaseCallbacks() {
        }

        public void onGetWalletCardsSuccess(android.service.quickaccesswallet.GetWalletCardsResponse getWalletCardsResponse) {
            throw new java.lang.IllegalStateException();
        }

        public void onGetWalletCardsFailure(android.service.quickaccesswallet.GetWalletCardsError getWalletCardsError) {
            throw new java.lang.IllegalStateException();
        }

        public void onWalletServiceEvent(android.service.quickaccesswallet.WalletServiceEvent walletServiceEvent) {
            throw new java.lang.IllegalStateException();
        }

        public void onTargetActivityPendingIntentReceived(android.app.PendingIntent pendingIntent) {
            throw new java.lang.IllegalStateException();
        }
    }
}
