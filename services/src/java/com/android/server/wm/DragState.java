package com.android.server.wm;

/* loaded from: classes3.dex */
class DragState {
    private static final java.lang.String ANIMATED_PROPERTY_ALPHA = "alpha";
    private static final java.lang.String ANIMATED_PROPERTY_SCALE = "scale";
    private static final java.lang.String ANIMATED_PROPERTY_X = "x";
    private static final java.lang.String ANIMATED_PROPERTY_Y = "y";
    private static final int DRAG_FLAGS_URI_ACCESS = 3;
    private static final int DRAG_FLAGS_URI_PERMISSIONS = 195;
    private static final long MAX_ANIMATION_DURATION_MS = 375;
    private static final long MIN_ANIMATION_DURATION_MS = 195;

    @android.annotation.Nullable
    private android.animation.ValueAnimator mAnimator;
    boolean mCrossProfileCopyAllowed;
    float mCurrentX;
    float mCurrentY;
    android.content.ClipData mData;
    android.content.ClipDescription mDataDescription;
    com.android.server.wm.DisplayContent mDisplayContent;
    final com.android.server.wm.DragDropController mDragDropController;
    boolean mDragInProgress;
    boolean mDragResult;
    int mFlags;
    com.android.server.wm.DragState.InputInterceptor mInputInterceptor;
    android.view.SurfaceControl mInputSurface;
    private boolean mIsClosing;
    android.os.IBinder mLocalWin;
    float mOriginalAlpha;
    float mOriginalX;
    float mOriginalY;
    int mPid;
    boolean mRelinquishDragSurfaceToDropTarget;
    final com.android.server.wm.WindowManagerService mService;
    int mSourceUserId;
    android.view.SurfaceControl mSurfaceControl;
    float mThumbOffsetX;
    float mThumbOffsetY;
    android.os.IBinder mToken;
    int mTouchSource;
    final android.view.SurfaceControl.Transaction mTransaction;
    int mUid;
    android.view.DragEvent mUnhandledDropEvent;
    float mAnimatedScale = 1.0f;
    volatile boolean mAnimationCompleted = false;
    private final android.view.animation.Interpolator mCubicEaseOutInterpolator = new android.view.animation.DecelerateInterpolator(1.5f);
    private final android.graphics.Point mDisplaySize = new android.graphics.Point();
    private final android.graphics.Rect mTmpClipRect = new android.graphics.Rect();
    java.util.ArrayList<com.android.server.wm.WindowState> mNotifiedWindows = new java.util.ArrayList<>();

    DragState(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DragDropController dragDropController, android.os.IBinder iBinder, android.view.SurfaceControl surfaceControl, int i, android.os.IBinder iBinder2) {
        this.mService = windowManagerService;
        this.mDragDropController = dragDropController;
        this.mToken = iBinder;
        this.mSurfaceControl = surfaceControl;
        this.mFlags = i;
        this.mLocalWin = iBinder2;
        this.mTransaction = windowManagerService.mTransactionFactory.get();
    }

    boolean isClosing() {
        return this.mIsClosing;
    }

    private java.util.concurrent.CompletableFuture<java.lang.Void> showInputSurface() {
        if (this.mInputSurface == null) {
            this.mInputSurface = this.mService.makeSurfaceBuilder(this.mDisplayContent.getSession()).setContainerLayer().setName("Drag and Drop Input Consumer").setCallsite("DragState.showInputSurface").setParent(this.mDisplayContent.getOverlayLayer()).build();
        }
        android.view.InputWindowHandle inputWindowHandle = getInputWindowHandle();
        if (inputWindowHandle == null) {
            android.util.Slog.w("WindowManager", "Drag is in progress but there is no drag window handle.");
            return java.util.concurrent.CompletableFuture.completedFuture(null);
        }
        this.mTmpClipRect.set(0, 0, this.mDisplaySize.x, this.mDisplaySize.y);
        inputWindowHandle.setTrustedOverlay(this.mTransaction, this.mInputSurface, true);
        this.mTransaction.show(this.mInputSurface).setInputWindowInfo(this.mInputSurface, inputWindowHandle).setLayer(this.mInputSurface, Integer.MAX_VALUE).setCrop(this.mInputSurface, this.mTmpClipRect);
        final java.util.concurrent.CompletableFuture<java.lang.Void> completableFuture = new java.util.concurrent.CompletableFuture<>();
        this.mTransaction.addWindowInfosReportedListener(new java.lang.Runnable() { // from class: com.android.server.wm.DragState$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                completableFuture.complete(null);
            }
        }).apply();
        return completableFuture;
    }

    void closeLocked() {
        android.view.SurfaceControl surfaceControl;
        float f;
        float f2;
        this.mIsClosing = true;
        if (this.mInputInterceptor != null) {
            android.util.Slog.d("WindowManager", "Unregistering drag input channel");
            this.mDragDropController.sendHandlerMessage(1, this.mInputInterceptor);
            this.mInputInterceptor = null;
        }
        if (this.mDragInProgress) {
            android.util.Slog.d("WindowManager", "Broadcasting DRAG_ENDED");
            java.util.Iterator<com.android.server.wm.WindowState> it = this.mNotifiedWindows.iterator();
            while (it.hasNext()) {
                com.android.server.wm.WindowState next = it.next();
                if (!this.mDragResult && next.mSession.mPid == this.mPid) {
                    float translateToWindowX = next.translateToWindowX(this.mCurrentX);
                    float translateToWindowY = next.translateToWindowY(this.mCurrentY);
                    if (!relinquishDragSurfaceToDragSource()) {
                        f = translateToWindowX;
                        surfaceControl = null;
                        f2 = translateToWindowY;
                    } else {
                        f = translateToWindowX;
                        f2 = translateToWindowY;
                        surfaceControl = this.mSurfaceControl;
                    }
                } else {
                    surfaceControl = null;
                    f = 0.0f;
                    f2 = 0.0f;
                }
                android.view.DragEvent obtain = android.view.DragEvent.obtain(4, f, f2, this.mThumbOffsetX, this.mThumbOffsetY, null, null, null, surfaceControl, null, this.mDragResult);
                try {
                    android.util.Slog.d("WindowManager", "Sending DRAG_ENDED to " + next);
                    next.mClient.dispatchDragEvent(obtain);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w("WindowManager", "Unable to drag-end window " + next);
                }
                if (com.android.server.wm.WindowManagerService.MY_PID != next.mSession.mPid) {
                    obtain.recycle();
                }
            }
            this.mNotifiedWindows.clear();
            this.mDragInProgress = false;
        }
        if (isFromSource(com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937_MPEG1_Layer1)) {
            this.mService.restorePointerIconLocked(this.mDisplayContent, this.mCurrentX, this.mCurrentY);
            this.mTouchSource = 0;
        }
        if (this.mInputSurface != null) {
            this.mTransaction.remove(this.mInputSurface).apply();
            this.mInputSurface = null;
        }
        if (this.mSurfaceControl != null) {
            if (this.mRelinquishDragSurfaceToDropTarget || relinquishDragSurfaceToDragSource()) {
                this.mDragDropController.sendTimeoutMessage(3, this.mSurfaceControl, 5000L);
            } else {
                this.mTransaction.remove(this.mSurfaceControl).apply();
            }
            this.mSurfaceControl = null;
        }
        if (this.mAnimator != null && !this.mAnimationCompleted) {
            android.util.Slog.wtf("WindowManager", "Unexpectedly destroying mSurfaceControl while animation is running");
        }
        this.mFlags = 0;
        this.mLocalWin = null;
        this.mToken = null;
        this.mData = null;
        this.mThumbOffsetY = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mThumbOffsetX = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mNotifiedWindows = null;
        if (this.mUnhandledDropEvent != null) {
            this.mUnhandledDropEvent.recycle();
            this.mUnhandledDropEvent = null;
        }
        this.mDragDropController.onDragStateClosedLocked(this);
    }

    private android.view.DragEvent createDropEvent(float f, float f2, @android.annotation.Nullable com.android.server.wm.WindowState windowState, boolean z) {
        com.android.internal.view.IDragAndDropPermissions iDragAndDropPermissions;
        if (windowState != null) {
            int userId = android.os.UserHandle.getUserId(windowState.getOwningUid());
            if ((this.mFlags & 256) != 0 && (this.mFlags & 3) != 0 && this.mData != null) {
                iDragAndDropPermissions = new com.android.server.wm.DragAndDropPermissionsHandler(this.mService.mGlobalLock, this.mData, this.mUid, windowState.getOwningPackage(), this.mFlags & 195, this.mSourceUserId, userId);
            } else {
                iDragAndDropPermissions = null;
            }
            if (this.mSourceUserId != userId && this.mData != null) {
                this.mData.fixUris(this.mSourceUserId);
            }
            return obtainDragEvent(3, f, f2, this.mData, targetInterceptsGlobalDrag(windowState), iDragAndDropPermissions);
        }
        return obtainDragEvent(3, f, f2, this.mData, z, null);
    }

    boolean reportDropWindowLock(android.os.IBinder iBinder, float f, float f2) {
        if (this.mAnimator != null) {
            return false;
        }
        com.android.server.wm.WindowState windowState = this.mService.mInputToWindowMap.get(iBinder);
        android.view.DragEvent createDropEvent = createDropEvent(f, f2, null, true);
        if (!isWindowNotified(windowState)) {
            if (this.mDragDropController.notifyUnhandledDrop(createDropEvent, "unhandled-drop")) {
                return true;
            }
            endDragLocked(false, false);
            android.util.Slog.d("WindowManager", "Drop outside a valid window " + windowState);
            return false;
        }
        android.util.Slog.d("WindowManager", "Sending DROP to " + windowState);
        android.os.IBinder asBinder = windowState.mClient.asBinder();
        android.view.DragEvent createDropEvent2 = createDropEvent(f, f2, windowState, false);
        try {
            try {
                windowState.mClient.dispatchDragEvent(createDropEvent2);
                this.mDragDropController.sendTimeoutMessage(0, asBinder, 5000L);
                if (com.android.server.wm.WindowManagerService.MY_PID != windowState.mSession.mPid) {
                    createDropEvent2.recycle();
                }
                this.mToken = asBinder;
                this.mUnhandledDropEvent = createDropEvent;
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w("WindowManager", "can't send drop notification to win " + windowState);
                endDragLocked(false, false);
                if (com.android.server.wm.WindowManagerService.MY_PID != windowState.mSession.mPid) {
                    createDropEvent2.recycle();
                }
                return false;
            }
        } catch (java.lang.Throwable th) {
            if (com.android.server.wm.WindowManagerService.MY_PID != windowState.mSession.mPid) {
                createDropEvent2.recycle();
            }
            throw th;
        }
    }

    class InputInterceptor {
        android.view.InputChannel mClientChannel;
        android.view.InputApplicationHandle mDragApplicationHandle = new android.view.InputApplicationHandle(new android.os.Binder(), "drag", android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS);
        android.view.InputWindowHandle mDragWindowHandle;
        com.android.server.wm.DragInputEventReceiver mInputEventReceiver;

        InputInterceptor(android.view.Display display) {
            this.mClientChannel = com.android.server.wm.DragState.this.mService.mInputManager.createInputChannel("drag");
            this.mInputEventReceiver = new com.android.server.wm.DragInputEventReceiver(this.mClientChannel, com.android.server.wm.DragState.this.mService.mH.getLooper(), com.android.server.wm.DragState.this.mDragDropController);
            this.mDragWindowHandle = new android.view.InputWindowHandle(this.mDragApplicationHandle, display.getDisplayId());
            this.mDragWindowHandle.name = "drag";
            this.mDragWindowHandle.token = this.mClientChannel.getToken();
            this.mDragWindowHandle.layoutParamsType = 2016;
            this.mDragWindowHandle.dispatchingTimeoutMillis = android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
            this.mDragWindowHandle.ownerPid = com.android.server.wm.WindowManagerService.MY_PID;
            this.mDragWindowHandle.ownerUid = com.android.server.wm.WindowManagerService.MY_UID;
            this.mDragWindowHandle.scaleFactor = 1.0f;
            this.mDragWindowHandle.touchableRegion.setEmpty();
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 7928129513685401229L, 0, null, null);
            com.android.server.wm.DragState.this.mService.mRoot.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.DragState$InputInterceptor$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.DragState.InputInterceptor.lambda$new$0((com.android.server.wm.DisplayContent) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$new$0(com.android.server.wm.DisplayContent displayContent) {
            displayContent.getDisplayRotation().pause();
        }

        void tearDown() {
            com.android.server.wm.DragState.this.mService.mInputManager.removeInputChannel(this.mClientChannel.getToken());
            this.mInputEventReceiver.dispose();
            this.mInputEventReceiver = null;
            this.mClientChannel.dispose();
            this.mClientChannel = null;
            this.mDragWindowHandle = null;
            this.mDragApplicationHandle = null;
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 8231481023986546563L, 0, null, null);
            com.android.server.wm.DragState.this.mService.mRoot.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.DragState$InputInterceptor$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.DragState.InputInterceptor.lambda$tearDown$1((com.android.server.wm.DisplayContent) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$tearDown$1(com.android.server.wm.DisplayContent displayContent) {
            displayContent.getDisplayRotation().resume();
        }
    }

    android.view.InputChannel getInputChannel() {
        if (this.mInputInterceptor == null) {
            return null;
        }
        return this.mInputInterceptor.mClientChannel;
    }

    android.view.InputWindowHandle getInputWindowHandle() {
        if (this.mInputInterceptor == null) {
            return null;
        }
        return this.mInputInterceptor.mDragWindowHandle;
    }

    android.os.IBinder getInputToken() {
        if (this.mInputInterceptor == null || this.mInputInterceptor.mClientChannel == null) {
            return null;
        }
        return this.mInputInterceptor.mClientChannel.getToken();
    }

    java.util.concurrent.CompletableFuture<java.lang.Void> register(android.view.Display display) {
        display.getRealSize(this.mDisplaySize);
        android.util.Slog.d("WindowManager", "Registering drag input channel");
        if (this.mInputInterceptor != null) {
            android.util.Slog.e("WindowManager", "Duplicate register of drag input channel");
            return java.util.concurrent.CompletableFuture.completedFuture(null);
        }
        this.mInputInterceptor = new com.android.server.wm.DragState.InputInterceptor(display);
        return showInputSurface();
    }

    void broadcastDragStartedLocked(final float f, final float f2) {
        this.mCurrentX = f;
        this.mOriginalX = f;
        this.mCurrentY = f2;
        this.mOriginalY = f2;
        this.mDataDescription = this.mData != null ? this.mData.getDescription() : null;
        this.mNotifiedWindows.clear();
        this.mDragInProgress = true;
        this.mSourceUserId = android.os.UserHandle.getUserId(this.mUid);
        this.mCrossProfileCopyAllowed = true ^ ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserRestriction(this.mSourceUserId, "no_cross_profile_copy_paste");
        android.util.Slog.d("WindowManager", "Broadcasting DRAG_STARTED at (" + f + ", " + f2 + ")");
        final boolean containsApplicationExtras = containsApplicationExtras(this.mDataDescription);
        this.mService.mRoot.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.DragState$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DragState.this.lambda$broadcastDragStartedLocked$1(f, f2, containsApplicationExtras, (com.android.server.wm.WindowState) obj);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendDragStartedLocked, reason: merged with bridge method [inline-methods] */
    public void lambda$broadcastDragStartedLocked$1(com.android.server.wm.WindowState windowState, float f, float f2, boolean z) {
        boolean targetInterceptsGlobalDrag = targetInterceptsGlobalDrag(windowState);
        if (this.mDragInProgress && isValidDropTarget(windowState, z, targetInterceptsGlobalDrag)) {
            android.util.Slog.d("WindowManager", "Sending DRAG_STARTED to new window " + windowState);
            android.view.DragEvent obtainDragEvent = obtainDragEvent(1, windowState.translateToWindowX(f), windowState.translateToWindowY(f2), targetInterceptsGlobalDrag ? this.mData.copyForTransferWithActivityInfo() : null, false, null);
            try {
                try {
                    windowState.mClient.dispatchDragEvent(obtainDragEvent);
                    this.mNotifiedWindows.add(windowState);
                    if (com.android.server.wm.WindowManagerService.MY_PID == windowState.mSession.mPid) {
                        return;
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w("WindowManager", "Unable to drag-start window " + windowState);
                    if (com.android.server.wm.WindowManagerService.MY_PID == windowState.mSession.mPid) {
                        return;
                    }
                }
                obtainDragEvent.recycle();
            } catch (java.lang.Throwable th) {
                if (com.android.server.wm.WindowManagerService.MY_PID != windowState.mSession.mPid) {
                    obtainDragEvent.recycle();
                }
                throw th;
            }
        }
    }

    private boolean containsApplicationExtras(android.content.ClipDescription clipDescription) {
        if (clipDescription == null) {
            return false;
        }
        return clipDescription.hasMimeType("application/vnd.android.activity") || clipDescription.hasMimeType("application/vnd.android.shortcut") || clipDescription.hasMimeType("application/vnd.android.task");
    }

    private boolean isValidDropTarget(com.android.server.wm.WindowState windowState, boolean z, boolean z2) {
        if (windowState == null) {
            return false;
        }
        boolean z3 = this.mLocalWin == windowState.mClient.asBinder();
        if ((!z3 && !z2 && z) || !windowState.isPotentialDragTarget(z2)) {
            return false;
        }
        boolean z4 = (this.mFlags & 4096) != 0;
        if ((!(((this.mFlags & 256) != 0) || z4) || !targetWindowSupportsGlobalDrag(windowState)) && !z3) {
            return false;
        }
        if (!z4 || z2 || this.mUid == windowState.getUid()) {
            return z2 || this.mCrossProfileCopyAllowed || this.mSourceUserId == android.os.UserHandle.getUserId(windowState.getOwningUid());
        }
        return false;
    }

    private boolean targetWindowSupportsGlobalDrag(com.android.server.wm.WindowState windowState) {
        return windowState.mActivityRecord == null || windowState.mActivityRecord.mTargetSdk >= 24;
    }

    public boolean targetInterceptsGlobalDrag(@android.annotation.Nullable com.android.server.wm.WindowState windowState) {
        return (windowState == null || (windowState.mAttrs.privateFlags & Integer.MIN_VALUE) == 0) ? false : true;
    }

    void sendDragStartedIfNeededLocked(com.android.server.wm.WindowState windowState) {
        if (!this.mDragInProgress || isWindowNotified(windowState)) {
            return;
        }
        lambda$broadcastDragStartedLocked$1(windowState, this.mCurrentX, this.mCurrentY, containsApplicationExtras(this.mDataDescription));
    }

    boolean isWindowNotified(com.android.server.wm.WindowState windowState) {
        java.util.Iterator<com.android.server.wm.WindowState> it = this.mNotifiedWindows.iterator();
        while (it.hasNext()) {
            if (it.next() == windowState) {
                return true;
            }
        }
        return false;
    }

    void endDragLocked(boolean z, boolean z2) {
        this.mDragResult = z;
        this.mRelinquishDragSurfaceToDropTarget = z2;
        if (this.mAnimator != null) {
            return;
        }
        if (!this.mDragResult && !isAccessibilityDragDrop() && !relinquishDragSurfaceToDragSource()) {
            this.mAnimator = createReturnAnimationLocked();
        } else {
            closeLocked();
        }
    }

    void cancelDragLocked(boolean z) {
        if (this.mAnimator != null) {
            return;
        }
        if (!this.mDragInProgress || z || isAccessibilityDragDrop()) {
            closeLocked();
        } else {
            this.mAnimator = createCancelAnimationLocked();
        }
    }

    void updateDragSurfaceLocked(boolean z, float f, float f2) {
        if (this.mAnimator != null) {
            return;
        }
        this.mCurrentX = f;
        this.mCurrentY = f2;
        if (!z) {
            return;
        }
        this.mTransaction.setPosition(this.mSurfaceControl, f - this.mThumbOffsetX, f2 - this.mThumbOffsetY).apply();
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, 12662399232325663L, 20, null, java.lang.String.valueOf(this.mSurfaceControl), java.lang.Long.valueOf((int) (f - this.mThumbOffsetX)), java.lang.Long.valueOf((int) (f2 - this.mThumbOffsetY)));
    }

    boolean isInProgress() {
        return this.mDragInProgress;
    }

    private android.view.DragEvent obtainDragEvent(int i, float f, float f2, android.content.ClipData clipData, boolean z, com.android.internal.view.IDragAndDropPermissions iDragAndDropPermissions) {
        return android.view.DragEvent.obtain(i, f, f2, this.mThumbOffsetX, this.mThumbOffsetY, null, this.mDataDescription, clipData, z ? this.mSurfaceControl : null, iDragAndDropPermissions, false);
    }

    private android.animation.ValueAnimator createReturnAnimationLocked() {
        final android.animation.ValueAnimator ofPropertyValuesHolder = android.animation.ValueAnimator.ofPropertyValuesHolder(android.animation.PropertyValuesHolder.ofFloat(ANIMATED_PROPERTY_X, this.mCurrentX - this.mThumbOffsetX, this.mOriginalX - this.mThumbOffsetX), android.animation.PropertyValuesHolder.ofFloat(ANIMATED_PROPERTY_Y, this.mCurrentY - this.mThumbOffsetY, this.mOriginalY - this.mThumbOffsetY), android.animation.PropertyValuesHolder.ofFloat(ANIMATED_PROPERTY_SCALE, this.mAnimatedScale, this.mAnimatedScale), android.animation.PropertyValuesHolder.ofFloat(ANIMATED_PROPERTY_ALPHA, this.mOriginalAlpha, this.mOriginalAlpha / 2.0f));
        float f = this.mOriginalX - this.mCurrentX;
        float f2 = this.mOriginalY - this.mCurrentY;
        long sqrt = ((long) ((java.lang.Math.sqrt((f * f) + (f2 * f2)) / java.lang.Math.sqrt((this.mDisplaySize.x * this.mDisplaySize.x) + (this.mDisplaySize.y * this.mDisplaySize.y))) * 180.0d)) + MIN_ANIMATION_DURATION_MS;
        com.android.server.wm.DragState.AnimationListener animationListener = new com.android.server.wm.DragState.AnimationListener();
        ofPropertyValuesHolder.setDuration(sqrt);
        ofPropertyValuesHolder.setInterpolator(this.mCubicEaseOutInterpolator);
        ofPropertyValuesHolder.addListener(animationListener);
        ofPropertyValuesHolder.addUpdateListener(animationListener);
        this.mService.mAnimationHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.DragState$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ofPropertyValuesHolder.start();
            }
        });
        return ofPropertyValuesHolder;
    }

    private android.animation.ValueAnimator createCancelAnimationLocked() {
        final android.animation.ValueAnimator ofPropertyValuesHolder = android.animation.ValueAnimator.ofPropertyValuesHolder(android.animation.PropertyValuesHolder.ofFloat(ANIMATED_PROPERTY_X, this.mCurrentX - this.mThumbOffsetX, this.mCurrentX), android.animation.PropertyValuesHolder.ofFloat(ANIMATED_PROPERTY_Y, this.mCurrentY - this.mThumbOffsetY, this.mCurrentY), android.animation.PropertyValuesHolder.ofFloat(ANIMATED_PROPERTY_SCALE, this.mAnimatedScale, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE), android.animation.PropertyValuesHolder.ofFloat(ANIMATED_PROPERTY_ALPHA, this.mOriginalAlpha, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE));
        com.android.server.wm.DragState.AnimationListener animationListener = new com.android.server.wm.DragState.AnimationListener();
        ofPropertyValuesHolder.setDuration(MIN_ANIMATION_DURATION_MS);
        ofPropertyValuesHolder.setInterpolator(this.mCubicEaseOutInterpolator);
        ofPropertyValuesHolder.addListener(animationListener);
        ofPropertyValuesHolder.addUpdateListener(animationListener);
        this.mService.mAnimationHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.DragState$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ofPropertyValuesHolder.start();
            }
        });
        return ofPropertyValuesHolder;
    }

    private boolean isFromSource(int i) {
        return (this.mTouchSource & i) == i;
    }

    void overridePointerIconLocked(int i) {
        this.mTouchSource = i;
        if (isFromSource(com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937_MPEG1_Layer1)) {
            android.hardware.input.InputManagerGlobal.getInstance().setPointerIconType(1021);
        }
    }

    private class AnimationListener implements android.animation.ValueAnimator.AnimatorUpdateListener, android.animation.Animator.AnimatorListener {
        private AnimationListener() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
            android.view.SurfaceControl.Transaction transaction = com.android.server.wm.DragState.this.mService.mTransactionFactory.get();
            try {
                transaction.setPosition(com.android.server.wm.DragState.this.mSurfaceControl, ((java.lang.Float) valueAnimator.getAnimatedValue(com.android.server.wm.DragState.ANIMATED_PROPERTY_X)).floatValue(), ((java.lang.Float) valueAnimator.getAnimatedValue(com.android.server.wm.DragState.ANIMATED_PROPERTY_Y)).floatValue());
                transaction.setAlpha(com.android.server.wm.DragState.this.mSurfaceControl, ((java.lang.Float) valueAnimator.getAnimatedValue(com.android.server.wm.DragState.ANIMATED_PROPERTY_ALPHA)).floatValue());
                transaction.setMatrix(com.android.server.wm.DragState.this.mSurfaceControl, ((java.lang.Float) valueAnimator.getAnimatedValue(com.android.server.wm.DragState.ANIMATED_PROPERTY_SCALE)).floatValue(), com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, ((java.lang.Float) valueAnimator.getAnimatedValue(com.android.server.wm.DragState.ANIMATED_PROPERTY_SCALE)).floatValue());
                transaction.apply();
                transaction.close();
            } catch (java.lang.Throwable th) {
                if (transaction != null) {
                    try {
                        transaction.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            com.android.server.wm.DragState.this.mAnimationCompleted = true;
            com.android.server.wm.DragState.this.mDragDropController.sendHandlerMessage(2, null);
        }
    }

    boolean isAccessibilityDragDrop() {
        return (this.mFlags & 1024) != 0;
    }

    private boolean relinquishDragSurfaceToDragSource() {
        return (this.mFlags & 2048) != 0;
    }
}
