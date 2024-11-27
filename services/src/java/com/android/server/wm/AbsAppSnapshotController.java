package com.android.server.wm;

/* loaded from: classes3.dex */
abstract class AbsAppSnapshotController<TYPE extends com.android.server.wm.WindowContainer, CACHE extends com.android.server.wm.SnapshotCache<TYPE>> {

    @com.android.internal.annotations.VisibleForTesting
    static final int SNAPSHOT_MODE_APP_THEME = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final int SNAPSHOT_MODE_NONE = 2;

    @com.android.internal.annotations.VisibleForTesting
    static final int SNAPSHOT_MODE_REAL = 0;
    static final java.lang.String TAG = "WindowManager";
    protected CACHE mCache;
    protected com.android.server.wm.Transition.ChangeInfo mCurrentChangeInfo;
    protected final float mHighResSnapshotScale = initSnapshotScale();
    protected final boolean mIsRunningOnIoT;
    protected final boolean mIsRunningOnTv;
    protected final com.android.server.wm.WindowManagerService mService;
    private boolean mSnapshotEnabled;

    @android.annotation.Nullable
    protected abstract com.android.server.wm.ActivityRecord findAppTokenForSnapshot(TYPE type);

    protected abstract android.graphics.Rect getLetterboxInsets(com.android.server.wm.ActivityRecord activityRecord);

    abstract android.app.ActivityManager.TaskDescription getTaskDescription(TYPE type);

    abstract com.android.server.wm.ActivityRecord getTopActivity(TYPE type);

    abstract com.android.server.wm.ActivityRecord getTopFullscreenActivity(TYPE type);

    protected abstract boolean use16BitFormat();

    AbsAppSnapshotController(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        this.mIsRunningOnTv = this.mService.mContext.getPackageManager().hasSystemFeature("android.software.leanback");
        this.mIsRunningOnIoT = this.mService.mContext.getPackageManager().hasSystemFeature("android.hardware.type.embedded");
    }

    protected float initSnapshotScale() {
        return java.lang.Math.max(java.lang.Math.min(this.mService.mContext.getResources().getFloat(android.R.dimen.config_dialogCornerRadius), 1.0f), 0.1f);
    }

    protected void initialize(CACHE cache) {
        this.mCache = cache;
    }

    void setSnapshotEnabled(boolean z) {
        this.mSnapshotEnabled = z;
    }

    boolean shouldDisableSnapshots() {
        return this.mIsRunningOnTv || this.mIsRunningOnIoT || !this.mSnapshotEnabled;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.window.TaskSnapshot captureSnapshot(TYPE type) {
        switch (getSnapshotMode(type)) {
            case 0:
                return snapshot(type);
            case 1:
                return drawAppThemeSnapshot(type);
            case 2:
                return null;
            default:
                return null;
        }
    }

    final android.window.TaskSnapshot recordSnapshotInner(TYPE type) {
        android.window.TaskSnapshot captureSnapshot;
        if (shouldDisableSnapshots() || (captureSnapshot = captureSnapshot(type)) == null) {
            return null;
        }
        this.mCache.putSnapshot(type, captureSnapshot);
        return captureSnapshot;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getSnapshotMode(TYPE type) {
        com.android.server.wm.ActivityRecord topActivity;
        int activityType = type.getActivityType();
        if (activityType == 3 || activityType == 5) {
            return 2;
        }
        if (activityType == 2 || (topActivity = getTopActivity(type)) == null || !topActivity.shouldUseAppThemeSnapshot()) {
            return 0;
        }
        return 1;
    }

    @android.annotation.Nullable
    android.window.TaskSnapshot snapshot(TYPE type) {
        return snapshot(type, this.mHighResSnapshotScale);
    }

    @android.annotation.Nullable
    android.window.TaskSnapshot snapshot(TYPE type, float f) {
        android.window.TaskSnapshot.Builder builder = new android.window.TaskSnapshot.Builder();
        android.graphics.Rect prepareTaskSnapshot = prepareTaskSnapshot(type, builder);
        if (prepareTaskSnapshot == null) {
            return null;
        }
        android.os.Trace.traceBegin(32L, "createSnapshot");
        android.window.ScreenCapture.ScreenshotHardwareBuffer createSnapshot = createSnapshot(type, f, prepareTaskSnapshot, builder);
        android.os.Trace.traceEnd(32L);
        if (createSnapshot == null) {
            return null;
        }
        builder.setCaptureTime(android.os.SystemClock.elapsedRealtimeNanos());
        builder.setSnapshot(createSnapshot.getHardwareBuffer());
        builder.setColorSpace(createSnapshot.getColorSpace());
        return validateSnapshot(builder.build());
    }

    private static android.window.TaskSnapshot validateSnapshot(@android.annotation.NonNull android.window.TaskSnapshot taskSnapshot) {
        android.hardware.HardwareBuffer hardwareBuffer = taskSnapshot.getHardwareBuffer();
        if (hardwareBuffer.getWidth() == 0 || hardwareBuffer.getHeight() == 0) {
            hardwareBuffer.close();
            android.util.Slog.e(TAG, "Invalid snapshot dimensions " + hardwareBuffer.getWidth() + "x" + hardwareBuffer.getHeight());
            return null;
        }
        return taskSnapshot;
    }

    @android.annotation.Nullable
    android.window.ScreenCapture.ScreenshotHardwareBuffer createSnapshot(@android.annotation.NonNull TYPE type, float f, android.graphics.Rect rect, android.window.TaskSnapshot.Builder builder) {
        android.view.SurfaceControl[] surfaceControlArr;
        if (type.getSurfaceControl() == null) {
            return null;
        }
        com.android.server.wm.WindowState windowState = type.getDisplayContent().mInputMethodWindow;
        boolean z = (windowState == null || windowState.getSurfaceControl() == null || type.getDisplayContent().shouldImeAttachedToApp()) ? false : true;
        com.android.server.wm.WindowState navigationBar = type.getDisplayContent().getDisplayPolicy().getNavigationBar();
        boolean z2 = navigationBar != null;
        if (z && z2) {
            surfaceControlArr = new android.view.SurfaceControl[]{windowState.getSurfaceControl(), navigationBar.getSurfaceControl()};
        } else if (z || z2) {
            surfaceControlArr = new android.view.SurfaceControl[1];
            surfaceControlArr[0] = z ? windowState.getSurfaceControl() : navigationBar.getSurfaceControl();
        } else {
            surfaceControlArr = new android.view.SurfaceControl[0];
        }
        builder.setHasImeSurface((z || windowState == null || !windowState.isVisible()) ? false : true);
        android.window.ScreenCapture.ScreenshotHardwareBuffer captureLayersExcluding = android.window.ScreenCapture.captureLayersExcluding(type.getSurfaceControl(), rect, f, builder.getPixelFormat(), surfaceControlArr);
        if (isInvalidHardwareBuffer(captureLayersExcluding == null ? null : captureLayersExcluding.getHardwareBuffer())) {
            return null;
        }
        return captureLayersExcluding;
    }

    static boolean isInvalidHardwareBuffer(android.hardware.HardwareBuffer hardwareBuffer) {
        return hardwareBuffer == null || hardwareBuffer.isClosed() || hardwareBuffer.getWidth() <= 1 || hardwareBuffer.getHeight() <= 1;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    android.graphics.Rect prepareTaskSnapshot(TYPE type, android.window.TaskSnapshot.Builder builder) {
        android.util.Pair<com.android.server.wm.ActivityRecord, com.android.server.wm.WindowState> checkIfReadyToSnapshot = checkIfReadyToSnapshot(type);
        if (checkIfReadyToSnapshot == null) {
            return null;
        }
        com.android.server.wm.ActivityRecord activityRecord = (com.android.server.wm.ActivityRecord) checkIfReadyToSnapshot.first;
        com.android.server.wm.WindowState windowState = (com.android.server.wm.WindowState) checkIfReadyToSnapshot.second;
        android.graphics.Rect systemBarInsets = getSystemBarInsets(windowState.getFrame(), windowState.getInsetsStateWithVisibilityOverride());
        android.graphics.Rect letterboxInsets = getLetterboxInsets(activityRecord);
        com.android.server.wm.utils.InsetUtils.addInsets(systemBarInsets, letterboxInsets);
        builder.setIsRealSnapshot(true);
        builder.setId(java.lang.System.currentTimeMillis());
        builder.setContentInsets(systemBarInsets);
        builder.setLetterboxInsets(letterboxInsets);
        boolean z = windowState.getAttrs().format != -1;
        boolean hasWallpaper = windowState.hasWallpaper();
        int pixelFormat = builder.getPixelFormat();
        if (pixelFormat == 0) {
            if (use16BitFormat() && activityRecord.fillsParent() && (!z || !hasWallpaper)) {
                pixelFormat = 4;
            } else {
                pixelFormat = 1;
            }
        }
        boolean z2 = android.graphics.PixelFormat.formatHasAlpha(pixelFormat) && (!activityRecord.fillsParent() || z);
        builder.setTopActivityComponent(activityRecord.mActivityComponent);
        builder.setPixelFormat(pixelFormat);
        builder.setIsTranslucent(z2);
        builder.setWindowingMode(type.getWindowingMode());
        builder.setAppearance(getAppearance(type));
        android.content.res.Configuration configuration = activityRecord.getTask().getConfiguration();
        int displayRotation = configuration.windowConfiguration.getDisplayRotation();
        android.graphics.Rect rect = new android.graphics.Rect();
        android.graphics.Point point = new android.graphics.Point();
        com.android.server.wm.Transition.ChangeInfo changeInfo = this.mCurrentChangeInfo;
        if (changeInfo != null && changeInfo.mRotation != displayRotation) {
            rect.set(changeInfo.mAbsoluteBounds);
            point.set(changeInfo.mAbsoluteBounds.right, changeInfo.mAbsoluteBounds.bottom);
            builder.setRotation(changeInfo.mRotation);
            builder.setOrientation(changeInfo.mAbsoluteBounds.height() < changeInfo.mAbsoluteBounds.width() ? 2 : 1);
        } else {
            android.content.res.Configuration configuration2 = type.getConfiguration();
            rect.set(configuration2.windowConfiguration.getBounds());
            android.graphics.Rect bounds = configuration.windowConfiguration.getBounds();
            point.set(bounds.width(), bounds.height());
            builder.setRotation(displayRotation);
            builder.setOrientation(configuration2.orientation);
        }
        rect.offsetTo(0, 0);
        builder.setTaskSize(point);
        return rect;
    }

    android.util.Pair<com.android.server.wm.ActivityRecord, com.android.server.wm.WindowState> checkIfReadyToSnapshot(TYPE type) {
        com.android.server.wm.ActivityRecord findAppTokenForSnapshot;
        if (!this.mService.mPolicy.isScreenOn() || (findAppTokenForSnapshot = findAppTokenForSnapshot(type)) == null || findAppTokenForSnapshot.hasCommittedReparentToAnimationLeash()) {
            return null;
        }
        com.android.server.wm.WindowState findMainWindow = findAppTokenForSnapshot.findMainWindow();
        if (findMainWindow == null) {
            android.util.Slog.w(TAG, "Failed to take screenshot. No main window for " + type);
            return null;
        }
        if (findAppTokenForSnapshot.hasFixedRotationTransform()) {
            return null;
        }
        return new android.util.Pair<>(findAppTokenForSnapshot, findMainWindow);
    }

    private android.window.TaskSnapshot drawAppThemeSnapshot(TYPE type) {
        com.android.server.wm.WindowState findMainWindow;
        com.android.server.wm.ActivityRecord topActivity = getTopActivity(type);
        if (topActivity == null || (findMainWindow = topActivity.findMainWindow()) == null) {
            return null;
        }
        android.app.ActivityManager.TaskDescription taskDescription = getTaskDescription(type);
        int alphaComponent = com.android.internal.graphics.ColorUtils.setAlphaComponent(taskDescription.getBackgroundColor(), 255);
        android.view.WindowManager.LayoutParams attrs = findMainWindow.getAttrs();
        android.graphics.Rect bounds = type.getBounds();
        android.graphics.Rect systemBarInsets = getSystemBarInsets(findMainWindow.getFrame(), findMainWindow.getInsetsStateWithVisibilityOverride());
        android.window.SnapshotDrawerUtils.SystemBarBackgroundPainter systemBarBackgroundPainter = new android.window.SnapshotDrawerUtils.SystemBarBackgroundPainter(attrs.flags, attrs.privateFlags, attrs.insetsFlags.appearance, taskDescription, this.mHighResSnapshotScale, findMainWindow.getRequestedVisibleTypes());
        int width = bounds.width();
        int height = bounds.height();
        int i = (int) (width * this.mHighResSnapshotScale);
        int i2 = (int) (height * this.mHighResSnapshotScale);
        android.graphics.RenderNode create = android.graphics.RenderNode.create("SnapshotController", null);
        create.setLeftTopRightBottom(0, 0, i, i2);
        create.setClipToBounds(false);
        android.graphics.RecordingCanvas start = create.start(i, i2);
        start.drawColor(alphaComponent);
        systemBarBackgroundPainter.setInsets(systemBarInsets);
        systemBarBackgroundPainter.drawDecors(start, (android.graphics.Rect) null);
        create.end(start);
        android.graphics.Bitmap createHardwareBitmap = android.view.ThreadedRenderer.createHardwareBitmap(create, i, i2);
        if (createHardwareBitmap == null) {
            return null;
        }
        android.graphics.Rect rect = new android.graphics.Rect(systemBarInsets);
        android.graphics.Rect letterboxInsets = getLetterboxInsets(topActivity);
        com.android.server.wm.utils.InsetUtils.addInsets(rect, letterboxInsets);
        return validateSnapshot(new android.window.TaskSnapshot(java.lang.System.currentTimeMillis(), android.os.SystemClock.elapsedRealtimeNanos(), topActivity.mActivityComponent, createHardwareBitmap.getHardwareBuffer(), createHardwareBitmap.getColorSpace(), findMainWindow.getConfiguration().orientation, findMainWindow.getWindowConfiguration().getRotation(), new android.graphics.Point(width, height), rect, letterboxInsets, false, false, type.getWindowingMode(), getAppearance(type), false, false));
    }

    static android.graphics.Rect getSystemBarInsets(android.graphics.Rect rect, android.view.InsetsState insetsState) {
        return insetsState.calculateInsets(rect, android.view.WindowInsets.Type.systemBars(), false).toRect();
    }

    private int getAppearance(TYPE type) {
        com.android.server.wm.WindowState windowState;
        com.android.server.wm.ActivityRecord topFullscreenActivity = getTopFullscreenActivity(type);
        if (topFullscreenActivity != null) {
            windowState = topFullscreenActivity.getTopFullscreenOpaqueWindow();
        } else {
            windowState = null;
        }
        if (windowState != null) {
            return windowState.mAttrs.insetsFlags.appearance;
        }
        return 0;
    }

    void onAppRemoved(com.android.server.wm.ActivityRecord activityRecord) {
        this.mCache.onAppRemoved(activityRecord);
    }

    void onAppDied(com.android.server.wm.ActivityRecord activityRecord) {
        this.mCache.onAppDied(activityRecord);
    }

    boolean isAnimatingByRecents(@android.annotation.NonNull com.android.server.wm.Task task) {
        return task.isAnimatingByRecents();
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "mHighResSnapshotScale=" + this.mHighResSnapshotScale);
        printWriter.println(str + "mSnapshotEnabled=" + this.mSnapshotEnabled);
        this.mCache.dump(printWriter, str);
    }
}
