package android.service.dreams;

/* loaded from: classes3.dex */
public abstract class DreamOverlayService extends android.app.Service {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "DreamOverlayService";
    private android.service.dreams.DreamOverlayService.OverlayClient mCurrentClient;
    private android.service.dreams.IDreamOverlay mDreamOverlay = new android.service.dreams.IDreamOverlay.Stub() { // from class: android.service.dreams.DreamOverlayService.1
        @Override // android.service.dreams.IDreamOverlay
        public void getClient(android.service.dreams.IDreamOverlayClientCallback iDreamOverlayClientCallback) {
            try {
                iDreamOverlayClientCallback.onDreamOverlayClient(new android.service.dreams.DreamOverlayService.OverlayClient(android.service.dreams.DreamOverlayService.this));
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.service.dreams.DreamOverlayService.TAG, "could not send client to callback", e);
            }
        }
    };
    private java.util.concurrent.Executor mExecutor;

    public abstract void onStartDream(android.view.WindowManager.LayoutParams layoutParams);

    /* JADX INFO: Access modifiers changed from: private */
    static class OverlayClient extends android.service.dreams.IDreamOverlayClient.Stub {
        private android.content.ComponentName mDreamComponent;
        android.service.dreams.IDreamOverlayCallback mDreamOverlayCallback;
        private final android.service.dreams.DreamOverlayService mService;
        private boolean mShowComplications;

        OverlayClient(android.service.dreams.DreamOverlayService dreamOverlayService) {
            this.mService = dreamOverlayService;
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void startDream(android.view.WindowManager.LayoutParams layoutParams, android.service.dreams.IDreamOverlayCallback iDreamOverlayCallback, java.lang.String str, boolean z) throws android.os.RemoteException {
            this.mDreamComponent = android.content.ComponentName.unflattenFromString(str);
            this.mShowComplications = z;
            this.mDreamOverlayCallback = iDreamOverlayCallback;
            this.mService.startDream(this, layoutParams);
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void wakeUp() {
            this.mService.wakeUp(this);
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void endDream() {
            this.mService.endDream(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onExitRequested() {
            try {
                this.mDreamOverlayCallback.onExitRequested();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.service.dreams.DreamOverlayService.TAG, "Could not request exit:" + e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean shouldShowComplications() {
            return this.mShowComplications;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.content.ComponentName getComponent() {
            return this.mDreamComponent;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startDream(final android.service.dreams.DreamOverlayService.OverlayClient overlayClient, final android.view.WindowManager.LayoutParams layoutParams) {
        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.dreams.DreamOverlayService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                android.service.dreams.DreamOverlayService.this.lambda$startDream$0(overlayClient, layoutParams);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDream$0(android.service.dreams.DreamOverlayService.OverlayClient overlayClient, android.view.WindowManager.LayoutParams layoutParams) {
        lambda$endDream$1(this.mCurrentClient);
        this.mCurrentClient = overlayClient;
        onStartDream(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endDream(final android.service.dreams.DreamOverlayService.OverlayClient overlayClient) {
        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.dreams.DreamOverlayService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.service.dreams.DreamOverlayService.this.lambda$endDream$1(overlayClient);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: endDreamInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$endDream$1(android.service.dreams.DreamOverlayService.OverlayClient overlayClient) {
        if (overlayClient == null || overlayClient != this.mCurrentClient) {
            return;
        }
        onEndDream();
        this.mCurrentClient = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wakeUp(final android.service.dreams.DreamOverlayService.OverlayClient overlayClient) {
        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.dreams.DreamOverlayService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.service.dreams.DreamOverlayService.this.lambda$wakeUp$2(overlayClient);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$wakeUp$2(android.service.dreams.DreamOverlayService.OverlayClient overlayClient) {
        if (this.mCurrentClient != overlayClient) {
            return;
        }
        onWakeUp();
    }

    public DreamOverlayService() {
    }

    public DreamOverlayService(java.util.concurrent.Executor executor) {
        this.mExecutor = executor;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (this.mExecutor == null) {
            this.mExecutor = getMainExecutor();
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return this.mDreamOverlay.asBinder();
    }

    public void onWakeUp() {
    }

    public void onEndDream() {
    }

    public final void requestExit() {
        if (this.mCurrentClient == null) {
            throw new java.lang.IllegalStateException("requested exit with no dream present");
        }
        this.mCurrentClient.onExitRequested();
    }

    public final boolean shouldShowComplications() {
        if (this.mCurrentClient == null) {
            throw new java.lang.IllegalStateException("requested if should show complication when no dream active");
        }
        return this.mCurrentClient.shouldShowComplications();
    }

    public final android.content.ComponentName getDreamComponent() {
        if (this.mCurrentClient == null) {
            throw new java.lang.IllegalStateException("requested dream component when no dream active");
        }
        return this.mCurrentClient.getComponent();
    }
}
