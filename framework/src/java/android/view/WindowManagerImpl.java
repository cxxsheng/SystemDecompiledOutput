package android.view;

/* loaded from: classes4.dex */
public final class WindowManagerImpl implements android.view.WindowManager {
    private static final java.lang.String TAG = "WindowManager";
    public final android.content.Context mContext;
    private android.os.IBinder mDefaultToken;
    private final android.view.WindowManagerGlobal mGlobal;
    private final java.util.ArrayList<android.view.WindowManagerImpl.OnFpsCallbackListenerProxy> mOnFpsCallbackListenerProxies;
    private final android.view.Window mParentWindow;
    private final android.os.IBinder mWindowContextToken;
    private final android.window.WindowMetricsController mWindowMetricsController;

    public WindowManagerImpl(android.content.Context context) {
        this(context, null, null);
    }

    private WindowManagerImpl(android.content.Context context, android.view.Window window, android.os.IBinder iBinder) {
        this.mGlobal = android.view.WindowManagerGlobal.getInstance();
        this.mOnFpsCallbackListenerProxies = new java.util.ArrayList<>();
        this.mContext = context;
        this.mParentWindow = window;
        this.mWindowContextToken = iBinder;
        this.mWindowMetricsController = new android.window.WindowMetricsController(this.mContext);
    }

    public android.view.WindowManagerImpl createLocalWindowManager(android.view.Window window) {
        return new android.view.WindowManagerImpl(this.mContext, window, this.mWindowContextToken);
    }

    public android.view.WindowManagerImpl createPresentationWindowManager(android.content.Context context) {
        return new android.view.WindowManagerImpl(context, this.mParentWindow, this.mWindowContextToken);
    }

    public static android.view.WindowManager createWindowContextWindowManager(android.content.Context context) {
        return new android.view.WindowManagerImpl(context, null, context.getWindowContextToken());
    }

    public void setDefaultToken(android.os.IBinder iBinder) {
        this.mDefaultToken = iBinder;
    }

    @Override // android.view.ViewManager
    public void addView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        applyTokens(layoutParams);
        this.mGlobal.addView(view, layoutParams, this.mContext.getDisplayNoVerify(), this.mParentWindow, this.mContext.getUserId());
    }

    @Override // android.view.ViewManager
    public void updateViewLayout(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        applyTokens(layoutParams);
        this.mGlobal.updateViewLayout(view, layoutParams);
    }

    private void applyTokens(android.view.ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof android.view.WindowManager.LayoutParams)) {
            throw new java.lang.IllegalArgumentException("Params must be WindowManager.LayoutParams");
        }
        android.view.WindowManager.LayoutParams layoutParams2 = (android.view.WindowManager.LayoutParams) layoutParams;
        assertWindowContextTypeMatches(layoutParams2.type);
        if (this.mDefaultToken != null && this.mParentWindow == null && layoutParams2.token == null) {
            layoutParams2.token = this.mDefaultToken;
        }
        layoutParams2.mWindowContextToken = this.mWindowContextToken;
    }

    private void assertWindowContextTypeMatches(int i) {
        if (!(this.mContext instanceof android.window.WindowProvider)) {
            return;
        }
        if (i >= 1000 && i <= 1999) {
            return;
        }
        android.window.WindowProvider windowProvider = (android.window.WindowProvider) this.mContext;
        if (windowProvider.getWindowType() == i) {
            return;
        }
        java.lang.IllegalArgumentException illegalArgumentException = new java.lang.IllegalArgumentException("Window type mismatch. Window Context's window type is " + windowProvider.getWindowType() + ", while LayoutParams' type is set to " + i + ". Please create another Window Context via createWindowContext(getDisplay(), " + i + ", null) to add window with type:" + i);
        if (!android.window.WindowProviderService.isWindowProviderService(windowProvider.getWindowContextOptions())) {
            throw illegalArgumentException;
        }
        android.os.StrictMode.onIncorrectContextUsed("WindowContext's window type must match type in WindowManager.LayoutParams", illegalArgumentException);
    }

    @Override // android.view.ViewManager
    public void removeView(android.view.View view) {
        this.mGlobal.removeView(view, false);
    }

    @Override // android.view.WindowManager
    public void removeViewImmediate(android.view.View view) {
        this.mGlobal.removeView(view, true);
    }

    @Override // android.view.WindowManager
    public void requestAppKeyboardShortcuts(final android.view.WindowManager.KeyboardShortcutsReceiver keyboardShortcutsReceiver, int i) {
        try {
            android.view.WindowManagerGlobal.getWindowManagerService().requestAppKeyboardShortcuts(new com.android.internal.os.IResultReceiver.Stub() { // from class: android.view.WindowManagerImpl.1
                @Override // com.android.internal.os.IResultReceiver
                public void send(int i2, android.os.Bundle bundle) throws android.os.RemoteException {
                    keyboardShortcutsReceiver.onKeyboardShortcutsReceived(bundle.getParcelableArrayList(android.view.WindowManager.PARCEL_KEY_SHORTCUTS_ARRAY, android.view.KeyboardShortcutGroup.class));
                }
            }, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public void requestImeKeyboardShortcuts(final android.view.WindowManager.KeyboardShortcutsReceiver keyboardShortcutsReceiver, int i) {
        try {
            android.view.WindowManagerGlobal.getWindowManagerService().requestImeKeyboardShortcuts(new com.android.internal.os.IResultReceiver.Stub() { // from class: android.view.WindowManagerImpl.2
                @Override // com.android.internal.os.IResultReceiver
                public void send(int i2, android.os.Bundle bundle) throws android.os.RemoteException {
                    keyboardShortcutsReceiver.onKeyboardShortcutsReceived(bundle.getParcelableArrayList(android.view.WindowManager.PARCEL_KEY_SHORTCUTS_ARRAY, android.view.KeyboardShortcutGroup.class));
                }
            }, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public android.view.Display getDefaultDisplay() {
        return this.mContext.getDisplayNoVerify();
    }

    @Override // android.view.WindowManager
    public android.graphics.Region getCurrentImeTouchRegion() {
        try {
            return android.view.WindowManagerGlobal.getWindowManagerService().getCurrentImeTouchRegion();
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    @Override // android.view.WindowManager
    public void setShouldShowWithInsecureKeyguard(int i, boolean z) {
        try {
            android.view.WindowManagerGlobal.getWindowManagerService().setShouldShowWithInsecureKeyguard(i, z);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.view.WindowManager
    public void setShouldShowSystemDecors(int i, boolean z) {
        try {
            android.view.WindowManagerGlobal.getWindowManagerService().setShouldShowSystemDecors(i, z);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.view.WindowManager
    public boolean shouldShowSystemDecors(int i) {
        try {
            return android.view.WindowManagerGlobal.getWindowManagerService().shouldShowSystemDecors(i);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @Override // android.view.WindowManager
    public void setDisplayImePolicy(int i, int i2) {
        try {
            android.view.WindowManagerGlobal.getWindowManagerService().setDisplayImePolicy(i, i2);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.view.WindowManager
    public int getDisplayImePolicy(int i) {
        try {
            return android.view.WindowManagerGlobal.getWindowManagerService().getDisplayImePolicy(i);
        } catch (android.os.RemoteException e) {
            return 1;
        }
    }

    @Override // android.view.WindowManager
    public boolean isGlobalKey(int i) {
        try {
            return android.view.WindowManagerGlobal.getWindowManagerService().isGlobalKey(i);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @Override // android.view.WindowManager
    public android.view.WindowMetrics getCurrentWindowMetrics() {
        return this.mWindowMetricsController.getCurrentWindowMetrics();
    }

    @Override // android.view.WindowManager
    public android.view.WindowMetrics getMaximumWindowMetrics() {
        return this.mWindowMetricsController.getMaximumWindowMetrics();
    }

    @Override // android.view.WindowManager
    public java.util.Set<android.view.WindowMetrics> getPossibleMaximumWindowMetrics(int i) {
        return this.mWindowMetricsController.getPossibleMaximumWindowMetrics(i);
    }

    @Override // android.view.WindowManager
    public void holdLock(android.os.IBinder iBinder, int i) {
        try {
            android.view.WindowManagerGlobal.getWindowManagerService().holdLock(iBinder, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public boolean isCrossWindowBlurEnabled() {
        return android.view.CrossWindowBlurListeners.getInstance().isCrossWindowBlurEnabled();
    }

    @Override // android.view.WindowManager
    public void addCrossWindowBlurEnabledListener(java.util.function.Consumer<java.lang.Boolean> consumer) {
        addCrossWindowBlurEnabledListener(this.mContext.getMainExecutor(), consumer);
    }

    @Override // android.view.WindowManager
    public void addCrossWindowBlurEnabledListener(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) {
        android.view.CrossWindowBlurListeners.getInstance().addListener(executor, consumer);
    }

    @Override // android.view.WindowManager
    public void removeCrossWindowBlurEnabledListener(java.util.function.Consumer<java.lang.Boolean> consumer) {
        android.view.CrossWindowBlurListeners.getInstance().removeListener(consumer);
    }

    @Override // android.view.WindowManager
    public void addProposedRotationListener(java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(intConsumer, "listener must not be null");
        android.os.IBinder token = android.content.Context.getToken(this.mContext);
        if (token == null) {
            throw new java.lang.UnsupportedOperationException("The context of this window manager instance must be a UI context, e.g. an Activity or a Context created by Context#createWindowContext()");
        }
        this.mGlobal.registerProposedRotationListener(token, executor, intConsumer);
    }

    @Override // android.view.WindowManager
    public void removeProposedRotationListener(java.util.function.IntConsumer intConsumer) {
        this.mGlobal.unregisterProposedRotationListener(android.content.Context.getToken(this.mContext), intConsumer);
    }

    @Override // android.view.WindowManager
    public boolean isTaskSnapshotSupported() {
        try {
            return android.view.WindowManagerGlobal.getWindowManagerService().isTaskSnapshotSupported();
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @Override // android.view.WindowManager
    public void registerTaskFpsCallback(int i, java.util.concurrent.Executor executor, android.window.TaskFpsCallback taskFpsCallback) {
        android.view.WindowManagerImpl.OnFpsCallbackListenerProxy onFpsCallbackListenerProxy = new android.view.WindowManagerImpl.OnFpsCallbackListenerProxy(executor, taskFpsCallback);
        try {
            android.view.WindowManagerGlobal.getWindowManagerService().registerTaskFpsCallback(i, onFpsCallbackListenerProxy);
            synchronized (this.mOnFpsCallbackListenerProxies) {
                this.mOnFpsCallbackListenerProxies.add(onFpsCallbackListenerProxy);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public void unregisterTaskFpsCallback(android.window.TaskFpsCallback taskFpsCallback) {
        synchronized (this.mOnFpsCallbackListenerProxies) {
            java.util.Iterator<android.view.WindowManagerImpl.OnFpsCallbackListenerProxy> it = this.mOnFpsCallbackListenerProxies.iterator();
            while (it.hasNext()) {
                android.view.WindowManagerImpl.OnFpsCallbackListenerProxy next = it.next();
                if (next.mCallback == taskFpsCallback) {
                    try {
                        android.view.WindowManagerGlobal.getWindowManagerService().unregisterTaskFpsCallback(next);
                        it.remove();
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OnFpsCallbackListenerProxy extends android.window.ITaskFpsCallback.Stub {
        private final android.window.TaskFpsCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        private OnFpsCallbackListenerProxy(java.util.concurrent.Executor executor, android.window.TaskFpsCallback taskFpsCallback) {
            this.mExecutor = executor;
            this.mCallback = taskFpsCallback;
        }

        @Override // android.window.ITaskFpsCallback
        public void onFpsReported(final float f) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.view.WindowManagerImpl$OnFpsCallbackListenerProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.WindowManagerImpl.OnFpsCallbackListenerProxy.this.lambda$onFpsReported$0(f);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFpsReported$0(float f) {
            this.mCallback.onFpsReported(f);
        }
    }

    @Override // android.view.WindowManager
    public android.graphics.Bitmap snapshotTaskForRecents(int i) {
        try {
            return android.view.WindowManagerGlobal.getWindowManagerService().snapshotTaskForRecents(i);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
            return null;
        }
    }

    @Override // android.view.WindowManager
    public android.os.IBinder getDefaultToken() {
        return this.mDefaultToken;
    }

    @Override // android.view.WindowManager
    public java.util.List<android.content.ComponentName> notifyScreenshotListeners(int i) {
        try {
            return java.util.List.copyOf(android.view.WindowManagerGlobal.getWindowManagerService().notifyScreenshotListeners(i));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public boolean replaceContentOnDisplayWithMirror(int i, android.view.Window window) {
        android.view.View peekDecorView = window.peekDecorView();
        if (peekDecorView == null) {
            android.util.Log.e(TAG, "replaceContentOnDisplayWithMirror: Window's decorView was null.");
            return false;
        }
        android.view.ViewRootImpl viewRootImpl = peekDecorView.getViewRootImpl();
        if (viewRootImpl == null) {
            android.util.Log.e(TAG, "replaceContentOnDisplayWithMirror: Window's viewRootImpl was null.");
            return false;
        }
        android.view.SurfaceControl surfaceControl = viewRootImpl.getSurfaceControl();
        if (!surfaceControl.isValid()) {
            android.util.Log.e(TAG, "replaceContentOnDisplayWithMirror: Window's SC is invalid.");
            return false;
        }
        return replaceContentOnDisplayWithSc(i, android.view.SurfaceControl.mirrorSurface(surfaceControl));
    }

    @Override // android.view.WindowManager
    public boolean replaceContentOnDisplayWithSc(int i, android.view.SurfaceControl surfaceControl) {
        if (!surfaceControl.isValid()) {
            android.util.Log.e(TAG, "replaceContentOnDisplayWithSc: Invalid SC.");
            return false;
        }
        try {
            return android.view.WindowManagerGlobal.getWindowManagerService().replaceContentOnDisplay(i, surfaceControl);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    @Override // android.view.WindowManager
    public void registerTrustedPresentationListener(android.os.IBinder iBinder, android.window.TrustedPresentationThresholds trustedPresentationThresholds, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) {
        java.util.Objects.requireNonNull(iBinder, "window must not be null");
        java.util.Objects.requireNonNull(trustedPresentationThresholds, "thresholds must not be null");
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(consumer, "listener must not be null");
        this.mGlobal.registerTrustedPresentationListener(iBinder, trustedPresentationThresholds, executor, consumer);
    }

    @Override // android.view.WindowManager
    public void unregisterTrustedPresentationListener(java.util.function.Consumer<java.lang.Boolean> consumer) {
        java.util.Objects.requireNonNull(consumer, "listener must not be null");
        this.mGlobal.unregisterTrustedPresentationListener(consumer);
    }

    @Override // android.view.WindowManager
    public android.window.InputTransferToken registerBatchedSurfaceControlInputReceiver(int i, android.window.InputTransferToken inputTransferToken, android.view.SurfaceControl surfaceControl, android.view.Choreographer choreographer, android.view.SurfaceControlInputReceiver surfaceControlInputReceiver) {
        java.util.Objects.requireNonNull(inputTransferToken);
        java.util.Objects.requireNonNull(surfaceControl);
        java.util.Objects.requireNonNull(choreographer);
        java.util.Objects.requireNonNull(surfaceControlInputReceiver);
        return this.mGlobal.registerBatchedSurfaceControlInputReceiver(i, inputTransferToken, surfaceControl, choreographer, surfaceControlInputReceiver);
    }

    @Override // android.view.WindowManager
    public android.window.InputTransferToken registerUnbatchedSurfaceControlInputReceiver(int i, android.window.InputTransferToken inputTransferToken, android.view.SurfaceControl surfaceControl, android.os.Looper looper, android.view.SurfaceControlInputReceiver surfaceControlInputReceiver) {
        java.util.Objects.requireNonNull(inputTransferToken);
        java.util.Objects.requireNonNull(surfaceControl);
        java.util.Objects.requireNonNull(looper);
        java.util.Objects.requireNonNull(surfaceControlInputReceiver);
        return this.mGlobal.registerUnbatchedSurfaceControlInputReceiver(i, inputTransferToken, surfaceControl, looper, surfaceControlInputReceiver);
    }

    @Override // android.view.WindowManager
    public void unregisterSurfaceControlInputReceiver(android.view.SurfaceControl surfaceControl) {
        java.util.Objects.requireNonNull(surfaceControl);
        this.mGlobal.unregisterSurfaceControlInputReceiver(surfaceControl);
    }

    @Override // android.view.WindowManager
    public android.os.IBinder getSurfaceControlInputClientToken(android.view.SurfaceControl surfaceControl) {
        java.util.Objects.requireNonNull(surfaceControl);
        return this.mGlobal.getSurfaceControlInputClientToken(surfaceControl);
    }

    @Override // android.view.WindowManager
    public boolean transferTouchGesture(android.window.InputTransferToken inputTransferToken, android.window.InputTransferToken inputTransferToken2) {
        java.util.Objects.requireNonNull(inputTransferToken);
        java.util.Objects.requireNonNull(inputTransferToken2);
        return this.mGlobal.transferTouchGesture(inputTransferToken, inputTransferToken2);
    }

    @Override // android.view.WindowManager
    public int addScreenRecordingCallback(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        if (com.android.window.flags.Flags.screenRecordingCallbacks()) {
            java.util.Objects.requireNonNull(executor, "executor must not be null");
            java.util.Objects.requireNonNull(consumer, "callback must not be null");
            return android.view.ScreenRecordingCallbacks.getInstance().addCallback(executor, consumer);
        }
        return 0;
    }

    @Override // android.view.WindowManager
    public void removeScreenRecordingCallback(java.util.function.Consumer<java.lang.Integer> consumer) {
        if (com.android.window.flags.Flags.screenRecordingCallbacks()) {
            java.util.Objects.requireNonNull(consumer, "callback must not be null");
            android.view.ScreenRecordingCallbacks.getInstance().removeCallback(consumer);
        }
    }
}
