package com.android.server.wm;

/* loaded from: classes3.dex */
class ScreenRotationAnimation {
    private static final java.lang.String TAG = "WindowManager";
    private boolean mAnimRunning;
    private android.view.SurfaceControl mBackColorSurface;
    private final android.content.Context mContext;
    private int mCurRotation;
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private float mEndLuma;
    private android.view.SurfaceControl mEnterBlackFrameLayer;
    private com.android.server.wm.BlackFrame mEnteringBlackFrame;
    private boolean mFinishAnimReady;
    private long mFinishAnimStartTime;
    private final int mOriginalHeight;
    private final int mOriginalRotation;
    private final int mOriginalWidth;
    private android.view.animation.Animation mRotateAlphaAnimation;
    private android.view.animation.Animation mRotateEnterAnimation;
    private android.view.animation.Animation mRotateExitAnimation;
    private android.view.SurfaceControl[] mRoundedCornerOverlay;
    private android.view.SurfaceControl mScreenshotLayer;
    private final com.android.server.wm.WindowManagerService mService;
    private float mStartLuma;
    private boolean mStarted;
    private com.android.server.wm.ScreenRotationAnimation.SurfaceRotationAnimationController mSurfaceRotationAnimationController;
    private final float[] mTmpFloats = new float[9];
    private final android.view.animation.Transformation mRotateExitTransformation = new android.view.animation.Transformation();
    private final android.view.animation.Transformation mRotateEnterTransformation = new android.view.animation.Transformation();
    private final android.graphics.Matrix mSnapshotInitialMatrix = new android.graphics.Matrix();

    /* JADX WARN: Removed duplicated region for block: B:28:0x017c A[Catch: OutOfResourcesException -> 0x00c0, TryCatch #0 {OutOfResourcesException -> 0x00c0, blocks: (B:62:0x009f, B:64:0x00a5, B:66:0x00ab, B:68:0x00c3, B:70:0x00d0, B:72:0x00d6, B:28:0x017c, B:31:0x0191, B:34:0x019b, B:37:0x0227, B:39:0x026e, B:41:0x0274, B:43:0x027c, B:26:0x0113, B:60:0x0149), top: B:61:0x009f }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0191 A[Catch: OutOfResourcesException -> 0x00c0, TryCatch #0 {OutOfResourcesException -> 0x00c0, blocks: (B:62:0x009f, B:64:0x00a5, B:66:0x00ab, B:68:0x00c3, B:70:0x00d0, B:72:0x00d6, B:28:0x017c, B:31:0x0191, B:34:0x019b, B:37:0x0227, B:39:0x026e, B:41:0x0274, B:43:0x027c, B:26:0x0113, B:60:0x0149), top: B:61:0x009f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    ScreenRotationAnimation(com.android.server.wm.DisplayContent displayContent, int i) {
        boolean z;
        android.window.ScreenCapture.ScreenshotHardwareBuffer captureDisplay;
        boolean z2;
        this.mService = displayContent.mWmService;
        this.mContext = this.mService.mContext;
        this.mDisplayContent = displayContent;
        android.graphics.Rect bounds = displayContent.getBounds();
        int width = bounds.width();
        int height = bounds.height();
        android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
        int i2 = displayInfo.rotation;
        this.mOriginalRotation = i;
        int deltaRotation = android.util.RotationUtils.deltaRotation(i, i2);
        boolean z3 = deltaRotation == 1 || deltaRotation == 3;
        this.mOriginalWidth = z3 ? height : width;
        this.mOriginalHeight = z3 ? width : height;
        int i3 = displayInfo.logicalWidth;
        int i4 = displayInfo.logicalHeight;
        boolean z4 = (i3 > this.mOriginalWidth) == (i4 > this.mOriginalHeight) && !(i3 == this.mOriginalWidth && i4 == this.mOriginalHeight);
        this.mSurfaceRotationAnimationController = new com.android.server.wm.ScreenRotationAnimation.SurfaceRotationAnimationController();
        boolean hasSecureWindowOnScreen = displayContent.hasSecureWindowOnScreen();
        int displayId = displayContent.getDisplayId();
        android.view.SurfaceControl.Transaction transaction = this.mService.mTransactionFactory.get();
        if (z4) {
            try {
            } catch (android.view.Surface.OutOfResourcesException e) {
                android.util.Slog.w(TAG, "Unable to allocate freeze surface", e);
            }
            if (!com.android.window.flags.Flags.deleteCaptureDisplay()) {
                android.view.DisplayAddress.Physical physical = displayInfo.address;
                if (!(physical instanceof android.view.DisplayAddress.Physical)) {
                    android.util.Slog.e(TAG, "Display does not have a physical address: " + displayId);
                    return;
                }
                android.os.IBinder physicalDisplayToken = com.android.server.display.DisplayControl.getPhysicalDisplayToken(physical.getPhysicalDisplayId());
                if (physicalDisplayToken != null) {
                    setSkipScreenshotForRoundedCornerOverlays(false, transaction);
                    this.mRoundedCornerOverlay = displayContent.findRoundedCornerOverlays();
                    z = hasSecureWindowOnScreen;
                    captureDisplay = android.window.ScreenCapture.captureDisplay(new android.window.ScreenCapture.DisplayCaptureArgs.Builder(physicalDisplayToken).setSourceCrop(new android.graphics.Rect(0, 0, width, height)).setAllowProtected(true).setCaptureSecureLayers(true).setHintForSeamlessTransition(true).build());
                    if (captureDisplay != null) {
                        android.util.Slog.w(TAG, "Unable to take screenshot of display " + displayId);
                        return;
                    }
                    if (!captureDisplay.containsSecureLayers()) {
                        z2 = z;
                    } else {
                        z2 = true;
                    }
                    this.mBackColorSurface = displayContent.makeChildSurface(null).setName("BackColorSurface").setColorLayer().setCallsite("ScreenRotationAnimation").build();
                    this.mScreenshotLayer = displayContent.makeOverlay().setName("RotationLayer").setOpaque(true).setSecure(z2).setCallsite("ScreenRotationAnimation").setBLASTLayer().build();
                    com.android.server.wm.InputMonitor.setTrustedOverlayInputInfo(this.mScreenshotLayer, transaction, displayId, "RotationLayer");
                    this.mEnterBlackFrameLayer = displayContent.makeOverlay().setName("EnterBlackFrameLayer").setContainerLayer().setCallsite("ScreenRotationAnimation").build();
                    android.hardware.HardwareBuffer hardwareBuffer = captureDisplay.getHardwareBuffer();
                    android.os.Trace.traceBegin(32L, "ScreenRotationAnimation#getMedianBorderLuma");
                    this.mStartLuma = com.android.internal.policy.TransitionAnimation.getBorderLuma(hardwareBuffer, captureDisplay.getColorSpace());
                    android.os.Trace.traceEnd(32L);
                    transaction.setLayer(this.mScreenshotLayer, 2010000);
                    transaction.reparent(this.mBackColorSurface, displayContent.getSurfaceControl());
                    transaction.setDimmingEnabled(this.mScreenshotLayer, !captureDisplay.containsHdrLayers());
                    transaction.setLayer(this.mBackColorSurface, -1);
                    transaction.setColor(this.mBackColorSurface, new float[]{this.mStartLuma, this.mStartLuma, this.mStartLuma});
                    transaction.setAlpha(this.mBackColorSurface, 1.0f);
                    transaction.setBuffer(this.mScreenshotLayer, hardwareBuffer);
                    transaction.setColorSpace(this.mScreenshotLayer, captureDisplay.getColorSpace());
                    transaction.show(this.mScreenshotLayer);
                    transaction.show(this.mBackColorSurface);
                    hardwareBuffer.close();
                    if (this.mRoundedCornerOverlay != null) {
                        for (android.view.SurfaceControl surfaceControl : this.mRoundedCornerOverlay) {
                            if (surfaceControl.isValid()) {
                                transaction.hide(surfaceControl);
                            }
                        }
                    }
                    if (this.mScreenshotLayer != null && z4) {
                        displayContent.getPendingTransaction().setGeometry(this.mScreenshotLayer, new android.graphics.Rect(0, 0, this.mOriginalWidth, this.mOriginalHeight), new android.graphics.Rect(0, 0, i3, i4), 0);
                    }
                    com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_SURFACE_ALLOC, 8010999385228654193L, 0, null, java.lang.String.valueOf(this.mScreenshotLayer));
                    if (i == i2) {
                        setRotation(transaction, i2);
                    } else {
                        this.mCurRotation = i2;
                        this.mSnapshotInitialMatrix.reset();
                        setRotationTransform(transaction, this.mSnapshotInitialMatrix);
                    }
                    transaction.apply();
                    return;
                }
                android.util.Slog.e(TAG, "Display token is null.");
                return;
            }
        }
        z = hasSecureWindowOnScreen;
        if (z4) {
            setSkipScreenshotForRoundedCornerOverlays(false, transaction);
            captureDisplay = android.window.ScreenCapture.captureLayers(new android.window.ScreenCapture.LayerCaptureArgs.Builder(displayContent.getSurfaceControl()).setCaptureSecureLayers(true).setAllowProtected(true).setSourceCrop(new android.graphics.Rect(0, 0, width, height)).setHintForSeamlessTransition(true).build());
        } else {
            captureDisplay = android.window.ScreenCapture.captureLayers(new android.window.ScreenCapture.LayerCaptureArgs.Builder(displayContent.getSurfaceControl()).setCaptureSecureLayers(true).setAllowProtected(true).setSourceCrop(new android.graphics.Rect(0, 0, width, height)).setHintForSeamlessTransition(true).build());
        }
        if (captureDisplay != null) {
        }
    }

    void setSkipScreenshotForRoundedCornerOverlays(final boolean z, final android.view.SurfaceControl.Transaction transaction) {
        this.mDisplayContent.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.ScreenRotationAnimation$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.ScreenRotationAnimation.lambda$setSkipScreenshotForRoundedCornerOverlays$0(transaction, z, (com.android.server.wm.WindowState) obj);
            }
        }, false);
        if (!z) {
            transaction.apply(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setSkipScreenshotForRoundedCornerOverlays$0(android.view.SurfaceControl.Transaction transaction, boolean z, com.android.server.wm.WindowState windowState) {
        if (!windowState.mToken.mRoundedCornerOverlay || !windowState.isVisible() || !windowState.mWinAnimator.hasSurface()) {
            return;
        }
        transaction.setSkipScreenshot(windowState.mWinAnimator.mSurfaceController.mSurfaceControl, z);
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1133871366145L, this.mStarted);
        protoOutputStream.write(1133871366146L, this.mAnimRunning);
        protoOutputStream.end(start);
    }

    boolean hasScreenshot() {
        return this.mScreenshotLayer != null;
    }

    private void setRotationTransform(android.view.SurfaceControl.Transaction transaction, android.graphics.Matrix matrix) {
        if (this.mScreenshotLayer == null) {
            return;
        }
        matrix.getValues(this.mTmpFloats);
        transaction.setPosition(this.mScreenshotLayer, this.mTmpFloats[2], this.mTmpFloats[5]);
        transaction.setMatrix(this.mScreenshotLayer, this.mTmpFloats[0], this.mTmpFloats[3], this.mTmpFloats[1], this.mTmpFloats[4]);
        transaction.setAlpha(this.mScreenshotLayer, 1.0f);
        transaction.show(this.mScreenshotLayer);
    }

    public void printTo(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("mSurface=");
        printWriter.print(this.mScreenshotLayer);
        printWriter.print(str);
        printWriter.print("mEnteringBlackFrame=");
        printWriter.println(this.mEnteringBlackFrame);
        if (this.mEnteringBlackFrame != null) {
            this.mEnteringBlackFrame.printTo(str + "  ", printWriter);
        }
        printWriter.print(str);
        printWriter.print("mCurRotation=");
        printWriter.print(this.mCurRotation);
        printWriter.print(" mOriginalRotation=");
        printWriter.println(this.mOriginalRotation);
        printWriter.print(str);
        printWriter.print("mOriginalWidth=");
        printWriter.print(this.mOriginalWidth);
        printWriter.print(" mOriginalHeight=");
        printWriter.println(this.mOriginalHeight);
        printWriter.print(str);
        printWriter.print("mStarted=");
        printWriter.print(this.mStarted);
        printWriter.print(" mAnimRunning=");
        printWriter.print(this.mAnimRunning);
        printWriter.print(" mFinishAnimReady=");
        printWriter.print(this.mFinishAnimReady);
        printWriter.print(" mFinishAnimStartTime=");
        printWriter.println(this.mFinishAnimStartTime);
        printWriter.print(str);
        printWriter.print("mRotateExitAnimation=");
        printWriter.print(this.mRotateExitAnimation);
        printWriter.print(" ");
        this.mRotateExitTransformation.printShortString(printWriter);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("mRotateEnterAnimation=");
        printWriter.print(this.mRotateEnterAnimation);
        printWriter.print(" ");
        this.mRotateEnterTransformation.printShortString(printWriter);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("mSnapshotInitialMatrix=");
        this.mSnapshotInitialMatrix.dump(printWriter);
        printWriter.println();
    }

    public void setRotation(android.view.SurfaceControl.Transaction transaction, int i) {
        this.mCurRotation = i;
        com.android.server.wm.utils.CoordinateTransforms.computeRotationMatrix(android.util.RotationUtils.deltaRotation(i, this.mOriginalRotation), this.mOriginalWidth, this.mOriginalHeight, this.mSnapshotInitialMatrix);
        setRotationTransform(transaction, this.mSnapshotInitialMatrix);
    }

    private boolean startAnimation(android.view.SurfaceControl.Transaction transaction, long j, float f, int i, int i2, int i3, int i4) {
        boolean z;
        if (this.mScreenshotLayer == null) {
            return false;
        }
        if (this.mStarted) {
            return true;
        }
        this.mStarted = true;
        int deltaRotation = android.util.RotationUtils.deltaRotation(this.mCurRotation, this.mOriginalRotation);
        if (i3 == 0 || i4 == 0) {
            switch (deltaRotation) {
                case 0:
                    this.mRotateExitAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mContext, android.R.anim.screen_rotate_0_exit);
                    this.mRotateEnterAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mContext, android.R.anim.rotation_animation_enter);
                    break;
                case 1:
                    this.mRotateExitAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mContext, android.R.anim.screen_rotate_plus_90_exit);
                    this.mRotateEnterAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mContext, android.R.anim.screen_rotate_plus_90_enter);
                    break;
                case 2:
                    this.mRotateExitAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mContext, android.R.anim.screen_rotate_180_exit);
                    this.mRotateEnterAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mContext, android.R.anim.screen_rotate_180_enter);
                    break;
                case 3:
                    this.mRotateExitAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mContext, android.R.anim.screen_rotate_minus_90_exit);
                    this.mRotateEnterAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mContext, android.R.anim.screen_rotate_minus_90_enter);
                    break;
            }
            z = false;
        } else {
            this.mRotateExitAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mContext, i3);
            this.mRotateEnterAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mContext, i4);
            this.mRotateAlphaAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mContext, android.R.anim.screen_rotate_alpha);
            z = true;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -6586462455018013482L, 0, null, java.lang.String.valueOf(z), java.lang.String.valueOf(android.view.Surface.rotationToString(this.mCurRotation)), java.lang.String.valueOf(android.view.Surface.rotationToString(this.mOriginalRotation)));
        this.mRotateExitAnimation.initialize(i, i2, this.mOriginalWidth, this.mOriginalHeight);
        this.mRotateExitAnimation.restrictDuration(j);
        this.mRotateExitAnimation.scaleCurrentDuration(f);
        this.mRotateEnterAnimation.initialize(i, i2, this.mOriginalWidth, this.mOriginalHeight);
        this.mRotateEnterAnimation.restrictDuration(j);
        this.mRotateEnterAnimation.scaleCurrentDuration(f);
        this.mAnimRunning = false;
        this.mFinishAnimReady = false;
        this.mFinishAnimStartTime = -1L;
        if (z) {
            this.mRotateAlphaAnimation.restrictDuration(j);
            this.mRotateAlphaAnimation.scaleCurrentDuration(f);
        }
        if (z && this.mEnteringBlackFrame == null) {
            try {
                this.mEnteringBlackFrame = new com.android.server.wm.BlackFrame(this.mService.mTransactionFactory, transaction, new android.graphics.Rect(-i, -i2, i * 2, i2 * 2), new android.graphics.Rect(0, 0, i, i2), 2010000, this.mDisplayContent, false, this.mEnterBlackFrameLayer);
            } catch (android.view.Surface.OutOfResourcesException e) {
                android.util.Slog.w(TAG, "Unable to allocate black surface", e);
            }
        }
        if (z) {
            this.mSurfaceRotationAnimationController.startCustomAnimation();
        } else {
            this.mSurfaceRotationAnimationController.startScreenRotationAnimation();
        }
        return true;
    }

    public boolean dismiss(android.view.SurfaceControl.Transaction transaction, long j, float f, int i, int i2, int i3, int i4) {
        if (this.mScreenshotLayer == null) {
            return false;
        }
        if (!this.mStarted) {
            this.mEndLuma = com.android.internal.policy.TransitionAnimation.getBorderLuma(this.mDisplayContent.getWindowingLayer(), i, i2);
            startAnimation(transaction, j, f, i, i2, i3, i4);
        }
        if (!this.mStarted) {
            return false;
        }
        this.mFinishAnimReady = true;
        return true;
    }

    public void kill() {
        if (this.mSurfaceRotationAnimationController != null) {
            this.mSurfaceRotationAnimationController.cancel();
            this.mSurfaceRotationAnimationController = null;
        }
        if (this.mScreenshotLayer != null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_SURFACE_ALLOC, -5825336546511998057L, 0, null, java.lang.String.valueOf(this.mScreenshotLayer));
            android.view.SurfaceControl.Transaction transaction = this.mService.mTransactionFactory.get();
            if (this.mScreenshotLayer.isValid()) {
                transaction.remove(this.mScreenshotLayer);
            }
            this.mScreenshotLayer = null;
            if (this.mEnterBlackFrameLayer != null) {
                if (this.mEnterBlackFrameLayer.isValid()) {
                    transaction.remove(this.mEnterBlackFrameLayer);
                }
                this.mEnterBlackFrameLayer = null;
            }
            if (this.mBackColorSurface != null) {
                if (this.mBackColorSurface.isValid()) {
                    transaction.remove(this.mBackColorSurface);
                }
                this.mBackColorSurface = null;
            }
            if (this.mRoundedCornerOverlay != null) {
                if (this.mDisplayContent.getRotationAnimation() == null || this.mDisplayContent.getRotationAnimation() == this) {
                    setSkipScreenshotForRoundedCornerOverlays(true, transaction);
                    for (android.view.SurfaceControl surfaceControl : this.mRoundedCornerOverlay) {
                        if (surfaceControl.isValid()) {
                            transaction.show(surfaceControl);
                        }
                    }
                }
                this.mRoundedCornerOverlay = null;
            }
            transaction.apply();
        }
        if (this.mEnteringBlackFrame != null) {
            this.mEnteringBlackFrame.kill();
            this.mEnteringBlackFrame = null;
        }
        if (this.mRotateExitAnimation != null) {
            this.mRotateExitAnimation.cancel();
            this.mRotateExitAnimation = null;
        }
        if (this.mRotateEnterAnimation != null) {
            this.mRotateEnterAnimation.cancel();
            this.mRotateEnterAnimation = null;
        }
        if (this.mRotateAlphaAnimation != null) {
            this.mRotateAlphaAnimation.cancel();
            this.mRotateAlphaAnimation = null;
        }
    }

    public boolean isAnimating() {
        return this.mSurfaceRotationAnimationController != null && this.mSurfaceRotationAnimationController.isAnimating();
    }

    public boolean isRotating() {
        return this.mCurRotation != this.mOriginalRotation;
    }

    class SurfaceRotationAnimationController {
        private com.android.server.wm.SurfaceAnimator mDisplayAnimator;
        private com.android.server.wm.SurfaceAnimator mEnterBlackFrameAnimator;
        private com.android.server.wm.SurfaceAnimator mRotateScreenAnimator;
        private com.android.server.wm.SurfaceAnimator mScreenshotRotationAnimator;

        SurfaceRotationAnimationController() {
        }

        void startCustomAnimation() {
            try {
                com.android.server.wm.ScreenRotationAnimation.this.mService.mSurfaceAnimationRunner.deferStartingAnimations();
                this.mRotateScreenAnimator = startScreenshotAlphaAnimation();
                this.mDisplayAnimator = startDisplayRotation();
                if (com.android.server.wm.ScreenRotationAnimation.this.mEnteringBlackFrame != null) {
                    this.mEnterBlackFrameAnimator = startEnterBlackFrameAnimation();
                }
            } finally {
                com.android.server.wm.ScreenRotationAnimation.this.mService.mSurfaceAnimationRunner.continueStartingAnimations();
            }
        }

        void startScreenRotationAnimation() {
            try {
                com.android.server.wm.ScreenRotationAnimation.this.mService.mSurfaceAnimationRunner.deferStartingAnimations();
                this.mDisplayAnimator = startDisplayRotation();
                this.mScreenshotRotationAnimator = startScreenshotRotationAnimation();
                startColorAnimation();
            } finally {
                com.android.server.wm.ScreenRotationAnimation.this.mService.mSurfaceAnimationRunner.continueStartingAnimations();
            }
        }

        private com.android.server.wm.SimpleSurfaceAnimatable.Builder initializeBuilder() {
            com.android.server.wm.SimpleSurfaceAnimatable.Builder builder = new com.android.server.wm.SimpleSurfaceAnimatable.Builder();
            final com.android.server.wm.DisplayContent displayContent = com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent;
            java.util.Objects.requireNonNull(displayContent);
            com.android.server.wm.SimpleSurfaceAnimatable.Builder syncTransactionSupplier = builder.setSyncTransactionSupplier(new java.util.function.Supplier() { // from class: com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    return com.android.server.wm.DisplayContent.this.getSyncTransaction();
                }
            });
            final com.android.server.wm.DisplayContent displayContent2 = com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent;
            java.util.Objects.requireNonNull(displayContent2);
            com.android.server.wm.SimpleSurfaceAnimatable.Builder pendingTransactionSupplier = syncTransactionSupplier.setPendingTransactionSupplier(new java.util.function.Supplier() { // from class: com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    return com.android.server.wm.DisplayContent.this.getPendingTransaction();
                }
            });
            final com.android.server.wm.DisplayContent displayContent3 = com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent;
            java.util.Objects.requireNonNull(displayContent3);
            com.android.server.wm.SimpleSurfaceAnimatable.Builder commitTransactionRunnable = pendingTransactionSupplier.setCommitTransactionRunnable(new java.lang.Runnable() { // from class: com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.DisplayContent.this.commitPendingTransaction();
                }
            });
            final com.android.server.wm.DisplayContent displayContent4 = com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent;
            java.util.Objects.requireNonNull(displayContent4);
            return commitTransactionRunnable.setAnimationLeashSupplier(new java.util.function.Supplier() { // from class: com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    return com.android.server.wm.DisplayContent.this.makeOverlay();
                }
            });
        }

        private com.android.server.wm.SurfaceAnimator startDisplayRotation() {
            com.android.server.wm.SurfaceAnimator startAnimation = startAnimation(initializeBuilder().setAnimationLeashParent(com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getSurfaceControl()).setSurfaceControl(com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getWindowingLayer()).setParentSurfaceControl(com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getSurfaceControl()).setWidth(com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getSurfaceWidth()).setHeight(com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getSurfaceHeight()).build(), createWindowAnimationSpec(com.android.server.wm.ScreenRotationAnimation.this.mRotateEnterAnimation), new com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda0(this));
            android.graphics.Rect bounds = com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getBounds();
            com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getPendingTransaction().setWindowCrop(startAnimation.mLeash, bounds.width(), bounds.height());
            return startAnimation;
        }

        private com.android.server.wm.SurfaceAnimator startScreenshotAlphaAnimation() {
            return startAnimation(initializeBuilder().setSurfaceControl(com.android.server.wm.ScreenRotationAnimation.this.mScreenshotLayer).setAnimationLeashParent(com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getOverlayLayer()).setWidth(com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getSurfaceWidth()).setHeight(com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getSurfaceHeight()).build(), createWindowAnimationSpec(com.android.server.wm.ScreenRotationAnimation.this.mRotateAlphaAnimation), new com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda0(this));
        }

        private com.android.server.wm.SurfaceAnimator startEnterBlackFrameAnimation() {
            return startAnimation(initializeBuilder().setSurfaceControl(com.android.server.wm.ScreenRotationAnimation.this.mEnterBlackFrameLayer).setAnimationLeashParent(com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getOverlayLayer()).build(), createWindowAnimationSpec(com.android.server.wm.ScreenRotationAnimation.this.mRotateEnterAnimation), new com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda0(this));
        }

        private com.android.server.wm.SurfaceAnimator startScreenshotRotationAnimation() {
            return startAnimation(initializeBuilder().setSurfaceControl(com.android.server.wm.ScreenRotationAnimation.this.mScreenshotLayer).setAnimationLeashParent(com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getOverlayLayer()).build(), createWindowAnimationSpec(com.android.server.wm.ScreenRotationAnimation.this.mRotateExitAnimation), new com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda0(this));
        }

        private void startColorAnimation() {
            final int integer = com.android.server.wm.ScreenRotationAnimation.this.mContext.getResources().getInteger(android.R.integer.config_screenBrightnessDark);
            com.android.server.wm.SurfaceAnimationRunner surfaceAnimationRunner = com.android.server.wm.ScreenRotationAnimation.this.mService.mSurfaceAnimationRunner;
            final float[] fArr = new float[3];
            final int rgb = android.graphics.Color.rgb(com.android.server.wm.ScreenRotationAnimation.this.mStartLuma, com.android.server.wm.ScreenRotationAnimation.this.mStartLuma, com.android.server.wm.ScreenRotationAnimation.this.mStartLuma);
            final int rgb2 = android.graphics.Color.rgb(com.android.server.wm.ScreenRotationAnimation.this.mEndLuma, com.android.server.wm.ScreenRotationAnimation.this.mEndLuma, com.android.server.wm.ScreenRotationAnimation.this.mEndLuma);
            final long currentAnimatorScale = integer * ((long) com.android.server.wm.ScreenRotationAnimation.this.mService.getCurrentAnimatorScale());
            final android.animation.ArgbEvaluator argbEvaluator = android.animation.ArgbEvaluator.getInstance();
            surfaceAnimationRunner.startAnimation(new com.android.server.wm.LocalAnimationAdapter.AnimationSpec() { // from class: com.android.server.wm.ScreenRotationAnimation.SurfaceRotationAnimationController.1
                @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                public long getDuration() {
                    return currentAnimatorScale;
                }

                @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                public void apply(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, long j) {
                    android.graphics.Color valueOf = android.graphics.Color.valueOf(((java.lang.Integer) argbEvaluator.evaluate(getFraction(j), java.lang.Integer.valueOf(rgb), java.lang.Integer.valueOf(rgb2))).intValue());
                    fArr[0] = valueOf.red();
                    fArr[1] = valueOf.green();
                    fArr[2] = valueOf.blue();
                    if (surfaceControl.isValid()) {
                        transaction.setColor(surfaceControl, fArr);
                    }
                }

                @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
                    printWriter.println(str + "startLuma=" + com.android.server.wm.ScreenRotationAnimation.this.mStartLuma + " endLuma=" + com.android.server.wm.ScreenRotationAnimation.this.mEndLuma + " durationMs=" + integer);
                }

                @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                public void dumpDebugInner(android.util.proto.ProtoOutputStream protoOutputStream) {
                    long start = protoOutputStream.start(1146756268036L);
                    protoOutputStream.write(1108101562369L, com.android.server.wm.ScreenRotationAnimation.this.mStartLuma);
                    protoOutputStream.write(1108101562370L, com.android.server.wm.ScreenRotationAnimation.this.mEndLuma);
                    protoOutputStream.write(1112396529667L, integer);
                    protoOutputStream.end(start);
                }
            }, com.android.server.wm.ScreenRotationAnimation.this.mBackColorSurface, com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getPendingTransaction(), null);
        }

        private com.android.server.wm.WindowAnimationSpec createWindowAnimationSpec(android.view.animation.Animation animation) {
            return new com.android.server.wm.WindowAnimationSpec(animation, new android.graphics.Point(0, 0), false, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        }

        private com.android.server.wm.SurfaceAnimator startAnimation(com.android.server.wm.SurfaceAnimator.Animatable animatable, com.android.server.wm.LocalAnimationAdapter.AnimationSpec animationSpec, com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
            com.android.server.wm.SurfaceAnimator surfaceAnimator = new com.android.server.wm.SurfaceAnimator(animatable, onAnimationFinishedCallback, com.android.server.wm.ScreenRotationAnimation.this.mService);
            surfaceAnimator.startAnimation(com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getPendingTransaction(), new com.android.server.wm.LocalAnimationAdapter(animationSpec, com.android.server.wm.ScreenRotationAnimation.this.mService.mSurfaceAnimationRunner), false, 2);
            return surfaceAnimator;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onAnimationEnd(int i, com.android.server.wm.AnimationAdapter animationAdapter) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ScreenRotationAnimation.this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (isAnimating()) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 6883897856740637908L, 1, null, java.lang.Long.valueOf(i), java.lang.String.valueOf(this.mDisplayAnimator != null ? java.lang.Boolean.valueOf(this.mDisplayAnimator.isAnimating()) : null), java.lang.String.valueOf(this.mEnterBlackFrameAnimator != null ? java.lang.Boolean.valueOf(this.mEnterBlackFrameAnimator.isAnimating()) : null), java.lang.String.valueOf(this.mRotateScreenAnimator != null ? java.lang.Boolean.valueOf(this.mRotateScreenAnimator.isAnimating()) : null), java.lang.String.valueOf(this.mScreenshotRotationAnimator != null ? java.lang.Boolean.valueOf(this.mScreenshotRotationAnimator.isAnimating()) : null));
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -3943622313307983155L, 0, null, null);
                    this.mEnterBlackFrameAnimator = null;
                    this.mScreenshotRotationAnimator = null;
                    this.mRotateScreenAnimator = null;
                    com.android.server.wm.ScreenRotationAnimation.this.mService.mAnimator.mBulkUpdateParams |= 1;
                    if (com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.getRotationAnimation() == com.android.server.wm.ScreenRotationAnimation.this) {
                        com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.setRotationAnimation(null);
                        if (com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.mDisplayRotationCompatPolicy != null) {
                            com.android.server.wm.ScreenRotationAnimation.this.mDisplayContent.mDisplayRotationCompatPolicy.onScreenRotationAnimationFinished();
                        }
                    } else {
                        com.android.server.wm.ScreenRotationAnimation.this.kill();
                    }
                    com.android.server.wm.ScreenRotationAnimation.this.mService.updateRotation(false, false);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        public void cancel() {
            if (this.mEnterBlackFrameAnimator != null) {
                this.mEnterBlackFrameAnimator.cancelAnimation();
            }
            if (this.mScreenshotRotationAnimator != null) {
                this.mScreenshotRotationAnimator.cancelAnimation();
            }
            if (this.mRotateScreenAnimator != null) {
                this.mRotateScreenAnimator.cancelAnimation();
            }
            if (this.mDisplayAnimator != null) {
                this.mDisplayAnimator.cancelAnimation();
            }
            if (com.android.server.wm.ScreenRotationAnimation.this.mBackColorSurface != null) {
                com.android.server.wm.ScreenRotationAnimation.this.mService.mSurfaceAnimationRunner.onAnimationCancelled(com.android.server.wm.ScreenRotationAnimation.this.mBackColorSurface);
            }
        }

        public boolean isAnimating() {
            return (this.mDisplayAnimator != null && this.mDisplayAnimator.isAnimating()) || (this.mEnterBlackFrameAnimator != null && this.mEnterBlackFrameAnimator.isAnimating()) || ((this.mRotateScreenAnimator != null && this.mRotateScreenAnimator.isAnimating()) || (this.mScreenshotRotationAnimator != null && this.mScreenshotRotationAnimator.isAnimating()));
        }
    }
}
