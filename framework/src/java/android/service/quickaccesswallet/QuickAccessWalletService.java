package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
public abstract class QuickAccessWalletService extends android.app.Service {
    public static final java.lang.String ACTION_VIEW_WALLET = "android.service.quickaccesswallet.action.VIEW_WALLET";
    public static final java.lang.String ACTION_VIEW_WALLET_SETTINGS = "android.service.quickaccesswallet.action.VIEW_WALLET_SETTINGS";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.quickaccesswallet.QuickAccessWalletService";
    public static final java.lang.String SERVICE_META_DATA = "android.quickaccesswallet";
    private static final java.lang.String TAG = "QAWalletService";
    public static final java.lang.String TILE_SERVICE_META_DATA = "android.quickaccesswallet.tile";
    private android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks mEventListener;
    private java.lang.String mEventListenerId;
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    private final android.service.quickaccesswallet.IQuickAccessWalletService mInterface = new android.service.quickaccesswallet.QuickAccessWalletService.AnonymousClass1();

    public abstract void onWalletCardSelected(android.service.quickaccesswallet.SelectWalletCardRequest selectWalletCardRequest);

    public abstract void onWalletCardsRequested(android.service.quickaccesswallet.GetWalletCardsRequest getWalletCardsRequest, android.service.quickaccesswallet.GetWalletCardsCallback getWalletCardsCallback);

    public abstract void onWalletDismissed();

    /* renamed from: android.service.quickaccesswallet.QuickAccessWalletService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.quickaccesswallet.IQuickAccessWalletService.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onWalletCardsRequested$0(android.service.quickaccesswallet.GetWalletCardsRequest getWalletCardsRequest, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
            android.service.quickaccesswallet.QuickAccessWalletService.this.onWalletCardsRequestedInternal(getWalletCardsRequest, iQuickAccessWalletServiceCallbacks);
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onWalletCardsRequested(final android.service.quickaccesswallet.GetWalletCardsRequest getWalletCardsRequest, final android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
            android.service.quickaccesswallet.QuickAccessWalletService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.quickaccesswallet.QuickAccessWalletService.AnonymousClass1.this.lambda$onWalletCardsRequested$0(getWalletCardsRequest, iQuickAccessWalletServiceCallbacks);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onWalletCardSelected$1(android.service.quickaccesswallet.SelectWalletCardRequest selectWalletCardRequest) {
            android.service.quickaccesswallet.QuickAccessWalletService.this.onWalletCardSelected(selectWalletCardRequest);
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onWalletCardSelected(final android.service.quickaccesswallet.SelectWalletCardRequest selectWalletCardRequest) {
            android.service.quickaccesswallet.QuickAccessWalletService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.quickaccesswallet.QuickAccessWalletService.AnonymousClass1.this.lambda$onWalletCardSelected$1(selectWalletCardRequest);
                }
            });
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onWalletDismissed() {
            android.os.Handler handler = android.service.quickaccesswallet.QuickAccessWalletService.this.mHandler;
            final android.service.quickaccesswallet.QuickAccessWalletService quickAccessWalletService = android.service.quickaccesswallet.QuickAccessWalletService.this;
            handler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.quickaccesswallet.QuickAccessWalletService.this.onWalletDismissed();
                }
            });
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onTargetActivityIntentRequested(final android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
            android.service.quickaccesswallet.QuickAccessWalletService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.quickaccesswallet.QuickAccessWalletService.AnonymousClass1.this.lambda$onTargetActivityIntentRequested$2(iQuickAccessWalletServiceCallbacks);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTargetActivityIntentRequested$2(android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
            android.service.quickaccesswallet.QuickAccessWalletService.this.onTargetActivityIntentRequestedInternal(iQuickAccessWalletServiceCallbacks);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$registerWalletServiceEventListener$3(android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
            android.service.quickaccesswallet.QuickAccessWalletService.this.registerDismissWalletListenerInternal(walletServiceEventListenerRequest, iQuickAccessWalletServiceCallbacks);
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void registerWalletServiceEventListener(final android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest, final android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
            android.service.quickaccesswallet.QuickAccessWalletService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.quickaccesswallet.QuickAccessWalletService.AnonymousClass1.this.lambda$registerWalletServiceEventListener$3(walletServiceEventListenerRequest, iQuickAccessWalletServiceCallbacks);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$unregisterWalletServiceEventListener$4(android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest) {
            android.service.quickaccesswallet.QuickAccessWalletService.this.unregisterDismissWalletListenerInternal(walletServiceEventListenerRequest);
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void unregisterWalletServiceEventListener(final android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest) {
            android.service.quickaccesswallet.QuickAccessWalletService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.quickaccesswallet.QuickAccessWalletService.AnonymousClass1.this.lambda$unregisterWalletServiceEventListener$4(walletServiceEventListenerRequest);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onWalletCardsRequestedInternal(android.service.quickaccesswallet.GetWalletCardsRequest getWalletCardsRequest, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
        onWalletCardsRequested(getWalletCardsRequest, new android.service.quickaccesswallet.GetWalletCardsCallbackImpl(getWalletCardsRequest, iQuickAccessWalletServiceCallbacks, this.mHandler, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTargetActivityIntentRequestedInternal(android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
        try {
            iQuickAccessWalletServiceCallbacks.onTargetActivityPendingIntentReceived(getTargetActivityPendingIntent());
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Error returning wallet cards", e);
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            android.util.Log.w(TAG, "Wrong action");
            return null;
        }
        return this.mInterface.asBinder();
    }

    public final void sendWalletServiceEvent(final android.service.quickaccesswallet.WalletServiceEvent walletServiceEvent) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.quickaccesswallet.QuickAccessWalletService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.service.quickaccesswallet.QuickAccessWalletService.this.lambda$sendWalletServiceEvent$0(walletServiceEvent);
            }
        });
    }

    public android.app.PendingIntent getTargetActivityPendingIntent() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendWalletServiceEventInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$sendWalletServiceEvent$0(android.service.quickaccesswallet.WalletServiceEvent walletServiceEvent) {
        if (this.mEventListener == null) {
            android.util.Log.i(TAG, "No dismiss listener registered");
            return;
        }
        try {
            this.mEventListener.onWalletServiceEvent(walletServiceEvent);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "onWalletServiceEvent error", e);
            this.mEventListenerId = null;
            this.mEventListener = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerDismissWalletListenerInternal(android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest, android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) {
        this.mEventListenerId = walletServiceEventListenerRequest.getListenerId();
        this.mEventListener = iQuickAccessWalletServiceCallbacks;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterDismissWalletListenerInternal(android.service.quickaccesswallet.WalletServiceEventListenerRequest walletServiceEventListenerRequest) {
        if (this.mEventListenerId != null && this.mEventListenerId.equals(walletServiceEventListenerRequest.getListenerId())) {
            this.mEventListenerId = null;
            this.mEventListener = null;
        } else {
            android.util.Log.w(TAG, "dismiss listener missing or replaced");
        }
    }
}
