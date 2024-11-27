package android.view;

/* loaded from: classes4.dex */
public class SurfaceControlViewHost {
    private static final java.lang.String TAG = "SurfaceControlViewHost";
    private android.view.accessibility.IAccessibilityEmbeddedConnection mAccessibilityEmbeddedConnection;
    private final dalvik.system.CloseGuard mCloseGuard;
    private android.view.ViewRootImpl.ConfigChangedCallback mConfigChangedCallback;
    private boolean mReleased;
    private android.view.ISurfaceControlViewHost mRemoteInterface;
    private android.view.SurfaceControl mSurfaceControl;
    private final android.view.ViewRootImpl mViewRoot;
    private final android.view.WindowlessWindowManager mWm;

    /* JADX INFO: Access modifiers changed from: private */
    final class ISurfaceControlViewHostImpl extends android.view.ISurfaceControlViewHost.Stub {
        private ISurfaceControlViewHostImpl() {
        }

        @Override // android.view.ISurfaceControlViewHost
        public void onConfigurationChanged(final android.content.res.Configuration configuration) {
            if (android.view.SurfaceControlViewHost.this.mViewRoot == null) {
                return;
            }
            android.view.SurfaceControlViewHost.this.mViewRoot.mHandler.post(new java.lang.Runnable() { // from class: android.view.SurfaceControlViewHost$ISurfaceControlViewHostImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.SurfaceControlViewHost.ISurfaceControlViewHostImpl.this.lambda$onConfigurationChanged$0(configuration);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfigurationChanged$0(android.content.res.Configuration configuration) {
            android.view.SurfaceControlViewHost.this.mWm.setConfiguration(configuration);
            if (android.view.SurfaceControlViewHost.this.mViewRoot != null) {
                android.view.SurfaceControlViewHost.this.mViewRoot.forceWmRelayout();
            }
        }

        @Override // android.view.ISurfaceControlViewHost
        public void onDispatchDetachedFromWindow() {
            if (android.view.SurfaceControlViewHost.this.mViewRoot == null) {
                return;
            }
            android.view.SurfaceControlViewHost.this.mViewRoot.mHandler.post(new java.lang.Runnable() { // from class: android.view.SurfaceControlViewHost$ISurfaceControlViewHostImpl$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.SurfaceControlViewHost.ISurfaceControlViewHostImpl.this.lambda$onDispatchDetachedFromWindow$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDispatchDetachedFromWindow$1() {
            android.view.SurfaceControlViewHost.this.release();
        }

        @Override // android.view.ISurfaceControlViewHost
        public void onInsetsChanged(android.view.InsetsState insetsState, final android.graphics.Rect rect) {
            if (android.view.SurfaceControlViewHost.this.mViewRoot != null) {
                android.view.SurfaceControlViewHost.this.mViewRoot.mHandler.post(new java.lang.Runnable() { // from class: android.view.SurfaceControlViewHost$ISurfaceControlViewHostImpl$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.SurfaceControlViewHost.ISurfaceControlViewHostImpl.this.lambda$onInsetsChanged$2(rect);
                    }
                });
            }
            android.view.SurfaceControlViewHost.this.mWm.setInsetsState(insetsState);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInsetsChanged$2(android.graphics.Rect rect) {
            android.view.SurfaceControlViewHost.this.mViewRoot.setOverrideInsetsFrame(rect);
        }

        @Override // android.view.ISurfaceControlViewHost
        public android.window.ISurfaceSyncGroup getSurfaceSyncGroup() {
            final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
            if (java.lang.Thread.currentThread() == android.view.SurfaceControlViewHost.this.mViewRoot.mThread) {
                return android.view.SurfaceControlViewHost.this.mViewRoot.getOrCreateSurfaceSyncGroup().mISurfaceSyncGroup;
            }
            android.view.SurfaceControlViewHost.this.mViewRoot.mHandler.post(new java.lang.Runnable() { // from class: android.view.SurfaceControlViewHost$ISurfaceControlViewHostImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.SurfaceControlViewHost.ISurfaceControlViewHostImpl.this.lambda$getSurfaceSyncGroup$3(completableFuture);
                }
            });
            try {
                return (android.window.ISurfaceSyncGroup) completableFuture.get(1L, java.util.concurrent.TimeUnit.SECONDS);
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException | java.util.concurrent.TimeoutException e) {
                android.util.Log.e(android.view.SurfaceControlViewHost.TAG, "Failed to get SurfaceSyncGroup for SCVH", e);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getSurfaceSyncGroup$3(java.util.concurrent.CompletableFuture completableFuture) {
            completableFuture.complete(android.view.SurfaceControlViewHost.this.mViewRoot.getOrCreateSurfaceSyncGroup().mISurfaceSyncGroup);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$attachParentInterface$4(android.view.ISurfaceControlViewHostParent iSurfaceControlViewHostParent) {
            android.view.SurfaceControlViewHost.this.mWm.setParentInterface(iSurfaceControlViewHostParent);
        }

        @Override // android.view.ISurfaceControlViewHost
        public void attachParentInterface(final android.view.ISurfaceControlViewHostParent iSurfaceControlViewHostParent) {
            android.view.SurfaceControlViewHost.this.mViewRoot.mHandler.post(new java.lang.Runnable() { // from class: android.view.SurfaceControlViewHost$ISurfaceControlViewHostImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.SurfaceControlViewHost.ISurfaceControlViewHostImpl.this.lambda$attachParentInterface$4(iSurfaceControlViewHostParent);
                }
            });
        }
    }

    public static final class SurfacePackage implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.SurfaceControlViewHost.SurfacePackage> CREATOR = new android.os.Parcelable.Creator<android.view.SurfaceControlViewHost.SurfacePackage>() { // from class: android.view.SurfaceControlViewHost.SurfacePackage.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.SurfaceControlViewHost.SurfacePackage createFromParcel(android.os.Parcel parcel) {
                return new android.view.SurfaceControlViewHost.SurfacePackage(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.SurfaceControlViewHost.SurfacePackage[] newArray(int i) {
                return new android.view.SurfaceControlViewHost.SurfacePackage[i];
            }
        };
        private final android.view.accessibility.IAccessibilityEmbeddedConnection mAccessibilityEmbeddedConnection;
        private final android.window.InputTransferToken mInputTransferToken;
        private final android.view.ISurfaceControlViewHost mRemoteInterface;
        private android.view.SurfaceControl mSurfaceControl;

        SurfacePackage(android.view.SurfaceControl surfaceControl, android.view.accessibility.IAccessibilityEmbeddedConnection iAccessibilityEmbeddedConnection, android.window.InputTransferToken inputTransferToken, android.view.ISurfaceControlViewHost iSurfaceControlViewHost) {
            this.mSurfaceControl = surfaceControl;
            this.mAccessibilityEmbeddedConnection = iAccessibilityEmbeddedConnection;
            this.mInputTransferToken = inputTransferToken;
            this.mRemoteInterface = iSurfaceControlViewHost;
        }

        public SurfacePackage(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
            android.view.SurfaceControl surfaceControl = surfacePackage.mSurfaceControl;
            if (surfaceControl != null && surfaceControl.isValid()) {
                this.mSurfaceControl = new android.view.SurfaceControl(surfaceControl, "SurfacePackage");
            }
            this.mAccessibilityEmbeddedConnection = surfacePackage.mAccessibilityEmbeddedConnection;
            this.mInputTransferToken = surfacePackage.mInputTransferToken;
            this.mRemoteInterface = surfacePackage.mRemoteInterface;
        }

        private SurfacePackage(android.os.Parcel parcel) {
            this.mSurfaceControl = new android.view.SurfaceControl();
            this.mSurfaceControl.readFromParcel(parcel);
            this.mSurfaceControl.setUnreleasedWarningCallSite("SurfacePackage(Parcel)");
            this.mAccessibilityEmbeddedConnection = android.view.accessibility.IAccessibilityEmbeddedConnection.Stub.asInterface(parcel.readStrongBinder());
            this.mInputTransferToken = android.window.InputTransferToken.CREATOR.createFromParcel(parcel);
            this.mRemoteInterface = android.view.ISurfaceControlViewHost.Stub.asInterface(parcel.readStrongBinder());
        }

        public android.view.SurfaceControl getSurfaceControl() {
            return this.mSurfaceControl;
        }

        public android.view.accessibility.IAccessibilityEmbeddedConnection getAccessibilityEmbeddedConnection() {
            return this.mAccessibilityEmbeddedConnection;
        }

        public android.view.ISurfaceControlViewHost getRemoteInterface() {
            return this.mRemoteInterface;
        }

        public void notifyConfigurationChanged(android.content.res.Configuration configuration) {
            try {
                getRemoteInterface().onConfigurationChanged(configuration);
            } catch (android.os.RemoteException e) {
                e.rethrowAsRuntimeException();
            }
        }

        public void notifyDetachedFromWindow() {
            try {
                getRemoteInterface().onDispatchDetachedFromWindow();
            } catch (android.os.RemoteException e) {
                e.rethrowAsRuntimeException();
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            this.mSurfaceControl.writeToParcel(parcel, i);
            parcel.writeStrongBinder(this.mAccessibilityEmbeddedConnection.asBinder());
            this.mInputTransferToken.writeToParcel(parcel, i);
            parcel.writeStrongBinder(this.mRemoteInterface.asBinder());
        }

        public void release() {
            if (this.mSurfaceControl != null) {
                this.mSurfaceControl.release();
            }
            this.mSurfaceControl = null;
        }

        public android.window.InputTransferToken getInputTransferToken() {
            return this.mInputTransferToken;
        }

        public java.lang.String toString() {
            return "{inputTransferToken=" + getInputTransferToken() + " remoteInterface=" + getRemoteInterface() + "}";
        }
    }

    public SurfaceControlViewHost(android.content.Context context, android.view.Display display, android.view.WindowlessWindowManager windowlessWindowManager, java.lang.String str) {
        this.mCloseGuard = dalvik.system.CloseGuard.get();
        this.mReleased = false;
        this.mRemoteInterface = new android.view.SurfaceControlViewHost.ISurfaceControlViewHostImpl();
        this.mSurfaceControl = windowlessWindowManager.mRootSurface;
        this.mWm = windowlessWindowManager;
        this.mViewRoot = new android.view.ViewRootImpl(context, display, this.mWm, new android.view.WindowlessWindowLayout());
        this.mCloseGuard.openWithCallSite("release", str);
        setConfigCallback(context, display);
        android.view.WindowManagerGlobal.getInstance().addWindowlessRoot(this.mViewRoot);
        this.mAccessibilityEmbeddedConnection = this.mViewRoot.getAccessibilityEmbeddedConnection();
    }

    public SurfaceControlViewHost(android.content.Context context, android.view.Display display, android.os.IBinder iBinder) {
        this(context, display, iBinder == null ? null : new android.window.InputTransferToken(iBinder), "untracked");
    }

    public SurfaceControlViewHost(android.content.Context context, android.view.Display display, android.window.InputTransferToken inputTransferToken) {
        this(context, display, inputTransferToken, "untracked");
    }

    public SurfaceControlViewHost(android.content.Context context, android.view.Display display, android.window.InputTransferToken inputTransferToken, java.lang.String str) {
        this.mCloseGuard = dalvik.system.CloseGuard.get();
        this.mReleased = false;
        this.mRemoteInterface = new android.view.SurfaceControlViewHost.ISurfaceControlViewHostImpl();
        this.mSurfaceControl = new android.view.SurfaceControl.Builder().setContainerLayer().setName(TAG).setCallsite("SurfaceControlViewHost[" + str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).build();
        this.mWm = new android.view.WindowlessWindowManager(context.getResources().getConfiguration(), this.mSurfaceControl, inputTransferToken);
        this.mViewRoot = new android.view.ViewRootImpl(context, display, this.mWm, new android.view.WindowlessWindowLayout());
        this.mCloseGuard.openWithCallSite("release", str);
        setConfigCallback(context, display);
        android.view.WindowManagerGlobal.getInstance().addWindowlessRoot(this.mViewRoot);
        this.mAccessibilityEmbeddedConnection = this.mViewRoot.getAccessibilityEmbeddedConnection();
    }

    private void setConfigCallback(android.content.Context context, final android.view.Display display) {
        final android.os.IBinder windowContextToken = context.getWindowContextToken();
        this.mConfigChangedCallback = new android.view.ViewRootImpl.ConfigChangedCallback() { // from class: android.view.SurfaceControlViewHost$$ExternalSyntheticLambda0
            @Override // android.view.ViewRootImpl.ConfigChangedCallback
            public final void onConfigurationChanged(android.content.res.Configuration configuration) {
                android.view.SurfaceControlViewHost.lambda$setConfigCallback$0(android.os.IBinder.this, display, configuration);
            }
        };
        android.view.ViewRootImpl.addConfigCallback(this.mConfigChangedCallback);
    }

    static /* synthetic */ void lambda$setConfigCallback$0(android.os.IBinder iBinder, android.view.Display display, android.content.res.Configuration configuration) {
        if (iBinder instanceof android.window.WindowTokenClient) {
            ((android.window.WindowTokenClient) iBinder).onConfigurationChanged(configuration, display.getDisplayId(), true);
        }
    }

    protected void finalize() throws java.lang.Throwable {
        if (this.mReleased) {
            return;
        }
        if (this.mCloseGuard != null) {
            this.mCloseGuard.warnIfOpen();
        }
        doRelease(false);
    }

    public android.view.SurfaceControlViewHost.SurfacePackage getSurfacePackage() {
        if (this.mSurfaceControl != null && this.mAccessibilityEmbeddedConnection != null) {
            return new android.view.SurfaceControlViewHost.SurfacePackage(new android.view.SurfaceControl(this.mSurfaceControl, "getSurfacePackage"), this.mAccessibilityEmbeddedConnection, getInputTransferToken(), this.mRemoteInterface);
        }
        return null;
    }

    public android.view.AttachedSurfaceControl getRootSurfaceControl() {
        return this.mViewRoot;
    }

    public void setView(android.view.View view, int i, int i2) {
        setView(view, new android.view.WindowManager.LayoutParams(i, i2, 2, 0, -2));
    }

    public void setView(android.view.View view, android.view.WindowManager.LayoutParams layoutParams) {
        java.util.Objects.requireNonNull(view);
        layoutParams.flags |= 16777216;
        addWindowToken(layoutParams);
        view.setLayoutParams(layoutParams);
        this.mViewRoot.setView(view, layoutParams, null);
        android.view.ViewRootImpl viewRootImpl = this.mViewRoot;
        final android.view.WindowlessWindowManager windowlessWindowManager = this.mWm;
        java.util.Objects.requireNonNull(windowlessWindowManager);
        viewRootImpl.setBackKeyCallbackForWindowlessWindow(new java.util.function.Predicate() { // from class: android.view.SurfaceControlViewHost$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.view.WindowlessWindowManager.this.forwardBackKeyToParent((android.view.KeyEvent) obj);
            }
        });
    }

    public android.view.View getView() {
        return this.mViewRoot.getView();
    }

    public android.view.IWindow getWindowToken() {
        return this.mViewRoot.mWindow;
    }

    public android.view.WindowlessWindowManager getWindowlessWM() {
        return this.mWm;
    }

    public void relayout(android.view.WindowManager.LayoutParams layoutParams, android.view.WindowlessWindowManager.ResizeCompleteCallback resizeCompleteCallback) {
        this.mViewRoot.setLayoutParams(layoutParams, false);
        this.mViewRoot.setReportNextDraw(true, "scvh_relayout");
        this.mWm.setCompletionCallback(this.mViewRoot.mWindow.asBinder(), resizeCompleteCallback);
    }

    public void relayout(android.view.WindowManager.LayoutParams layoutParams) {
        this.mViewRoot.setLayoutParams(layoutParams, false);
    }

    public void relayout(int i, int i2) {
        relayout(new android.view.WindowManager.LayoutParams(i, i2, 2, 0, -2));
    }

    public void release() {
        doRelease(true);
    }

    private void doRelease(boolean z) {
        if (this.mConfigChangedCallback != null) {
            android.view.ViewRootImpl.removeConfigCallback(this.mConfigChangedCallback);
            this.mConfigChangedCallback = null;
        }
        this.mViewRoot.die(z);
        android.view.WindowManagerGlobal.getInstance().removeWindowlessRoot(this.mViewRoot);
        this.mReleased = true;
        this.mCloseGuard.close();
    }

    public android.window.InputTransferToken getInputTransferToken() {
        return this.mWm.getInputTransferToken(getWindowToken().asBinder());
    }

    private void addWindowToken(android.view.WindowManager.LayoutParams layoutParams) {
        layoutParams.token = ((android.view.WindowManager) this.mViewRoot.mContext.getSystemService(android.content.Context.WINDOW_SERVICE)).getDefaultToken();
    }

    public boolean transferTouchGestureToHost() {
        if (this.mViewRoot == null) {
            return false;
        }
        android.view.WindowManager windowManager = (android.view.WindowManager) this.mViewRoot.mContext.getSystemService(android.content.Context.WINDOW_SERVICE);
        android.window.InputTransferToken inputTransferToken = getInputTransferToken();
        android.window.InputTransferToken inputTransferToken2 = this.mWm.mHostInputTransferToken;
        if (inputTransferToken == null || inputTransferToken2 == null) {
            android.util.Log.w(TAG, "Failed to transferTouchGestureToHost. Host or embedded token is null");
            return false;
        }
        return windowManager.transferTouchGesture(getInputTransferToken(), this.mWm.mHostInputTransferToken);
    }
}
