package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
public class MagnificationController implements com.android.server.accessibility.magnification.MagnificationConnectionManager.Callback, com.android.server.accessibility.magnification.MagnificationGestureHandler.Callback, com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback, com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "MagnificationController";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks> mAccessibilityCallbacksDelegateArray;
    private final com.android.server.accessibility.magnification.AlwaysOnMagnificationFeatureFlag mAlwaysOnMagnificationFeatureFlag;
    private final com.android.server.accessibility.AccessibilityManagerService mAms;
    private final java.util.concurrent.Executor mBackgroundExecutor;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mCurrentMagnificationModeArray;
    private com.android.server.accessibility.magnification.FullScreenMagnificationController mFullScreenMagnificationController;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseLongArray mFullScreenModeEnabledTimeArray;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseBooleanArray mIsImeVisibleArray;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mLastMagnificationActivatedModeArray;
    private final java.lang.Object mLock;
    private int mMagnificationCapabilities;
    private com.android.server.accessibility.magnification.MagnificationConnectionManager mMagnificationConnectionManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.accessibility.magnification.MagnificationController.DisableMagnificationCallback> mMagnificationEndRunnableSparseArray;
    private final com.android.server.accessibility.magnification.MagnificationScaleProvider mScaleProvider;
    private final boolean mSupportWindowMagnification;
    private final android.graphics.PointF mTempPoint;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.lang.Integer> mTransitionModes;
    private int mUserId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseLongArray mWindowModeEnabledTimeArray;

    public interface TransitionCallBack {
        void onResult(int i, boolean z);
    }

    public MagnificationController(com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService, java.lang.Object obj, android.content.Context context, com.android.server.accessibility.magnification.MagnificationScaleProvider magnificationScaleProvider, java.util.concurrent.Executor executor) {
        this.mTempPoint = new android.graphics.PointF();
        this.mMagnificationEndRunnableSparseArray = new android.util.SparseArray<>();
        this.mMagnificationCapabilities = 1;
        this.mCurrentMagnificationModeArray = new android.util.SparseIntArray();
        this.mLastMagnificationActivatedModeArray = new android.util.SparseIntArray();
        this.mUserId = 0;
        this.mIsImeVisibleArray = new android.util.SparseBooleanArray();
        this.mWindowModeEnabledTimeArray = new android.util.SparseLongArray();
        this.mFullScreenModeEnabledTimeArray = new android.util.SparseLongArray();
        this.mTransitionModes = new android.util.SparseArray<>();
        this.mAccessibilityCallbacksDelegateArray = new android.util.SparseArray<>();
        this.mAms = accessibilityManagerService;
        this.mLock = obj;
        this.mContext = context;
        this.mScaleProvider = magnificationScaleProvider;
        this.mBackgroundExecutor = executor;
        ((com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class)).getAccessibilityController().setUiChangesForAccessibilityCallbacks(this);
        this.mSupportWindowMagnification = context.getPackageManager().hasSystemFeature("android.software.window_magnification");
        this.mAlwaysOnMagnificationFeatureFlag = new com.android.server.accessibility.magnification.AlwaysOnMagnificationFeatureFlag(context);
        com.android.server.accessibility.magnification.AlwaysOnMagnificationFeatureFlag alwaysOnMagnificationFeatureFlag = this.mAlwaysOnMagnificationFeatureFlag;
        java.util.concurrent.Executor executor2 = this.mBackgroundExecutor;
        final com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService2 = this.mAms;
        java.util.Objects.requireNonNull(accessibilityManagerService2);
        alwaysOnMagnificationFeatureFlag.addOnChangedListener(executor2, new java.lang.Runnable() { // from class: com.android.server.accessibility.magnification.MagnificationController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.AccessibilityManagerService.this.updateAlwaysOnMagnification();
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    public MagnificationController(com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService, java.lang.Object obj, android.content.Context context, com.android.server.accessibility.magnification.FullScreenMagnificationController fullScreenMagnificationController, com.android.server.accessibility.magnification.MagnificationConnectionManager magnificationConnectionManager, com.android.server.accessibility.magnification.MagnificationScaleProvider magnificationScaleProvider, java.util.concurrent.Executor executor) {
        this(accessibilityManagerService, obj, context, magnificationScaleProvider, executor);
        this.mFullScreenMagnificationController = fullScreenMagnificationController;
        this.mMagnificationConnectionManager = magnificationConnectionManager;
    }

    @Override // com.android.server.accessibility.magnification.MagnificationConnectionManager.Callback
    public void onPerformScaleAction(int i, float f, boolean z) {
        if (getFullScreenMagnificationController().isActivated(i)) {
            getFullScreenMagnificationController().setScaleAndCenter(i, f, Float.NaN, Float.NaN, false, 0);
            if (z) {
                getFullScreenMagnificationController().persistScale(i);
                return;
            }
            return;
        }
        if (getMagnificationConnectionManager().isWindowMagnifierEnabled(i)) {
            getMagnificationConnectionManager().setScale(i, f);
            if (z) {
                getMagnificationConnectionManager().persistScale(i);
            }
        }
    }

    @Override // com.android.server.accessibility.magnification.MagnificationConnectionManager.Callback
    public void onAccessibilityActionPerformed(int i) {
        updateMagnificationUIControls(i, 2);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler.Callback
    public void onTouchInteractionStart(int i, int i2) {
        handleUserInteractionChanged(i, i2);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler.Callback
    public void onTouchInteractionEnd(int i, int i2) {
        handleUserInteractionChanged(i, i2);
    }

    private void handleUserInteractionChanged(int i, int i2) {
        if (this.mMagnificationCapabilities != 3) {
            return;
        }
        updateMagnificationUIControls(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMagnificationUIControls(int i, int i2) {
        boolean z;
        boolean z2;
        boolean isActivated = isActivated(i, i2);
        synchronized (this.mLock) {
            z = false;
            if (isActivated) {
                try {
                    if (this.mMagnificationCapabilities == 3) {
                        z2 = true;
                        if (isActivated && (this.mMagnificationCapabilities == 3 || this.mMagnificationCapabilities == 2)) {
                            z = true;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            z2 = false;
            if (isActivated) {
                z = true;
            }
        }
        if (z2) {
            getMagnificationConnectionManager().showMagnificationButton(i, i2);
        } else {
            getMagnificationConnectionManager().removeMagnificationButton(i);
        }
        if (!z) {
            getMagnificationConnectionManager().removeMagnificationSettingsPanel(i);
        }
    }

    public boolean supportWindowMagnification() {
        return this.mSupportWindowMagnification;
    }

    public void transitionMagnificationModeLocked(int i, int i2, @android.annotation.NonNull com.android.server.accessibility.magnification.MagnificationController.TransitionCallBack transitionCallBack) {
        if (isActivated(i, i2)) {
            transitionCallBack.onResult(i, true);
            return;
        }
        android.graphics.PointF currentMagnificationCenterLocked = getCurrentMagnificationCenterLocked(i, i2);
        com.android.server.accessibility.magnification.MagnificationController.DisableMagnificationCallback disableMagnificationEndRunnableLocked = getDisableMagnificationEndRunnableLocked(i);
        if (currentMagnificationCenterLocked == null && disableMagnificationEndRunnableLocked == null) {
            transitionCallBack.onResult(i, true);
            return;
        }
        if (disableMagnificationEndRunnableLocked != null) {
            if (disableMagnificationEndRunnableLocked.mCurrentMode != i2) {
                android.util.Slog.w(TAG, "discard duplicate request");
                return;
            } else {
                disableMagnificationEndRunnableLocked.restoreToCurrentMagnificationMode();
                return;
            }
        }
        if (currentMagnificationCenterLocked == null) {
            android.util.Slog.w(TAG, "Invalid center, ignore it");
            transitionCallBack.onResult(i, true);
            return;
        }
        setTransitionState(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        com.android.server.accessibility.magnification.FullScreenMagnificationController fullScreenMagnificationController = getFullScreenMagnificationController();
        com.android.server.accessibility.magnification.MagnificationConnectionManager magnificationConnectionManager = getMagnificationConnectionManager();
        com.android.server.accessibility.magnification.MagnificationController.DisableMagnificationCallback disableMagnificationCallback = new com.android.server.accessibility.magnification.MagnificationController.DisableMagnificationCallback(transitionCallBack, i, i2, getTargetModeScaleFromCurrentMagnification(i, i2), currentMagnificationCenterLocked, true);
        setDisableMagnificationCallbackLocked(i, disableMagnificationCallback);
        if (i2 == 2) {
            fullScreenMagnificationController.reset(i, disableMagnificationCallback);
        } else {
            magnificationConnectionManager.disableWindowMagnification(i, false, disableMagnificationCallback);
        }
    }

    public void transitionMagnificationConfigMode(final int i, android.accessibilityservice.MagnificationConfig magnificationConfig, boolean z, int i2) {
        float scale;
        android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback;
        float centerX;
        float centerY;
        synchronized (this.mLock) {
            try {
                final int mode = magnificationConfig.getMode();
                boolean isActivated = magnificationConfig.isActivated();
                android.graphics.PointF currentMagnificationCenterLocked = getCurrentMagnificationCenterLocked(i, mode);
                android.graphics.PointF pointF = new android.graphics.PointF(magnificationConfig.getCenterX(), magnificationConfig.getCenterY());
                if (currentMagnificationCenterLocked != null) {
                    if (java.lang.Float.isNaN(magnificationConfig.getCenterX())) {
                        centerX = currentMagnificationCenterLocked.x;
                    } else {
                        centerX = magnificationConfig.getCenterX();
                    }
                    if (java.lang.Float.isNaN(magnificationConfig.getCenterY())) {
                        centerY = currentMagnificationCenterLocked.y;
                    } else {
                        centerY = magnificationConfig.getCenterY();
                    }
                    pointF.set(centerX, centerY);
                }
                com.android.server.accessibility.magnification.MagnificationController.DisableMagnificationCallback disableMagnificationEndRunnableLocked = getDisableMagnificationEndRunnableLocked(i);
                if (disableMagnificationEndRunnableLocked != null) {
                    android.util.Slog.w(TAG, "Discard previous animation request");
                    disableMagnificationEndRunnableLocked.setExpiredAndRemoveFromListLocked();
                }
                com.android.server.accessibility.magnification.FullScreenMagnificationController fullScreenMagnificationController = getFullScreenMagnificationController();
                com.android.server.accessibility.magnification.MagnificationConnectionManager magnificationConnectionManager = getMagnificationConnectionManager();
                if (java.lang.Float.isNaN(magnificationConfig.getScale())) {
                    scale = getTargetModeScaleFromCurrentMagnification(i, mode);
                } else {
                    scale = magnificationConfig.getScale();
                }
                try {
                    setTransitionState(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(mode));
                    if (z) {
                        magnificationAnimationCallback = new android.view.accessibility.MagnificationAnimationCallback() { // from class: com.android.server.accessibility.magnification.MagnificationController$$ExternalSyntheticLambda0
                            public final void onResult(boolean z2) {
                                com.android.server.accessibility.magnification.MagnificationController.this.lambda$transitionMagnificationConfigMode$0(i, mode, z2);
                            }
                        };
                    } else {
                        magnificationAnimationCallback = null;
                    }
                    if (mode == 2) {
                        fullScreenMagnificationController.reset(i, false);
                        if (isActivated) {
                            magnificationConnectionManager.enableWindowMagnification(i, scale, pointF.x, pointF.y, magnificationAnimationCallback, i2);
                        } else {
                            magnificationConnectionManager.disableWindowMagnification(i, false);
                        }
                    } else if (mode == 1) {
                        magnificationConnectionManager.disableWindowMagnification(i, false, null);
                        if (isActivated) {
                            if (!fullScreenMagnificationController.isRegistered(i)) {
                                fullScreenMagnificationController.register(i);
                            }
                            fullScreenMagnificationController.setScaleAndCenter(i, scale, pointF.x, pointF.y, magnificationAnimationCallback, i2);
                        } else if (fullScreenMagnificationController.isRegistered(i)) {
                            fullScreenMagnificationController.reset(i, false);
                        }
                    }
                    if (!z) {
                        this.mAms.changeMagnificationMode(i, mode);
                    }
                    setTransitionState(java.lang.Integer.valueOf(i), null);
                } catch (java.lang.Throwable th) {
                    if (!z) {
                        this.mAms.changeMagnificationMode(i, mode);
                    }
                    setTransitionState(java.lang.Integer.valueOf(i), null);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$transitionMagnificationConfigMode$0(int i, int i2, boolean z) {
        this.mAms.changeMagnificationMode(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTransitionState(java.lang.Integer num, java.lang.Integer num2) {
        synchronized (this.mLock) {
            try {
                if (num2 == null && num == null) {
                    this.mTransitionModes.clear();
                } else {
                    this.mTransitionModes.put(num.intValue(), num2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private float getTargetModeScaleFromCurrentMagnification(int i, int i2) {
        if (i2 == 2) {
            return getFullScreenMagnificationController().getScale(i);
        }
        return getMagnificationConnectionManager().getScale(i);
    }

    public boolean hasDisableMagnificationCallback(int i) {
        synchronized (this.mLock) {
            try {
                return getDisableMagnificationEndRunnableLocked(i) != null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setCurrentMagnificationModeAndSwitchDelegate(int i, int i2) {
        this.mCurrentMagnificationModeArray.put(i, i2);
        assignMagnificationWindowManagerDelegateByMode(i, i2);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void assignMagnificationWindowManagerDelegateByMode(int i, int i2) {
        if (i2 == 1) {
            this.mAccessibilityCallbacksDelegateArray.put(i, getFullScreenMagnificationController());
        } else if (i2 == 2) {
            this.mAccessibilityCallbacksDelegateArray.put(i, getMagnificationConnectionManager());
        } else {
            this.mAccessibilityCallbacksDelegateArray.delete(i);
        }
    }

    @Override // com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks
    public void onRectangleOnScreenRequested(int i, int i2, int i3, int i4, int i5) {
        com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks uiChangesForAccessibilityCallbacks;
        synchronized (this.mLock) {
            uiChangesForAccessibilityCallbacks = this.mAccessibilityCallbacksDelegateArray.get(i);
        }
        if (uiChangesForAccessibilityCallbacks != null) {
            uiChangesForAccessibilityCallbacks.onRectangleOnScreenRequested(i, i2, i3, i4, i5);
        }
    }

    @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
    public void onRequestMagnificationSpec(int i, int i2) {
        com.android.server.accessibility.magnification.MagnificationConnectionManager magnificationConnectionManager;
        synchronized (this.mLock) {
            updateMagnificationUIControls(i, 1);
            magnificationConnectionManager = this.mMagnificationConnectionManager;
        }
        if (magnificationConnectionManager != null) {
            this.mMagnificationConnectionManager.disableWindowMagnification(i, false);
        }
    }

    @Override // com.android.server.accessibility.magnification.MagnificationConnectionManager.Callback
    public void onWindowMagnificationActivationState(int i, boolean z) {
        long uptimeMillis;
        float lastActivatedScale;
        if (z) {
            synchronized (this.mLock) {
                this.mWindowModeEnabledTimeArray.put(i, android.os.SystemClock.uptimeMillis());
                setCurrentMagnificationModeAndSwitchDelegate(i, 2);
                this.mLastMagnificationActivatedModeArray.put(i, 2);
            }
            logMagnificationModeWithImeOnIfNeeded(i);
            disableFullScreenMagnificationIfNeeded(i);
        } else {
            synchronized (this.mLock) {
                setCurrentMagnificationModeAndSwitchDelegate(i, 0);
                uptimeMillis = android.os.SystemClock.uptimeMillis() - this.mWindowModeEnabledTimeArray.get(i);
                lastActivatedScale = this.mMagnificationConnectionManager.getLastActivatedScale(i);
            }
            logMagnificationUsageState(2, uptimeMillis, lastActivatedScale);
        }
        updateMagnificationUIControls(i, 2);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationConnectionManager.Callback
    public void onChangeMagnificationMode(int i, int i2) {
        this.mAms.changeMagnificationMode(i, i2);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationConnectionManager.Callback
    public void onSourceBoundsChanged(int i, android.graphics.Rect rect) {
        if (shouldNotifyMagnificationChange(i, 2)) {
            this.mMagnificationConnectionManager.onUserMagnificationScaleChanged(this.mUserId, i, getMagnificationConnectionManager().getScale(i));
            this.mAms.notifyMagnificationChanged(i, new android.graphics.Region(rect), new android.accessibilityservice.MagnificationConfig.Builder().setMode(2).setActivated(getMagnificationConnectionManager().isWindowMagnifierEnabled(i)).setScale(getMagnificationConnectionManager().getScale(i)).setCenterX(rect.exactCenterX()).setCenterY(rect.exactCenterY()).build());
        }
    }

    @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
    public void onFullScreenMagnificationChanged(int i, @android.annotation.NonNull android.graphics.Region region, @android.annotation.NonNull android.accessibilityservice.MagnificationConfig magnificationConfig) {
        if (shouldNotifyMagnificationChange(i, 1)) {
            this.mMagnificationConnectionManager.onUserMagnificationScaleChanged(this.mUserId, i, magnificationConfig.getScale());
            this.mAms.notifyMagnificationChanged(i, region, magnificationConfig);
        }
    }

    private boolean shouldNotifyMagnificationChange(int i, int i2) {
        synchronized (this.mLock) {
            try {
                boolean z = this.mFullScreenMagnificationController != null && this.mFullScreenMagnificationController.isActivated(i);
                boolean z2 = this.mMagnificationConnectionManager != null && this.mMagnificationConnectionManager.isWindowMagnifierEnabled(i);
                java.lang.Integer num = this.mTransitionModes.get(i);
                if (((i2 == 1 && z) || (i2 == 2 && z2)) && num == null) {
                    return true;
                }
                if (z || z2 || num != null) {
                    return num != null && i2 == num.intValue();
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void disableFullScreenMagnificationIfNeeded(int i) {
        com.android.server.accessibility.magnification.FullScreenMagnificationController fullScreenMagnificationController = getFullScreenMagnificationController();
        if ((fullScreenMagnificationController.getIdOfLastServiceToMagnify(i) > 0) || isActivated(i, 1)) {
            fullScreenMagnificationController.reset(i, false);
        }
    }

    @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
    public void onFullScreenMagnificationActivationState(int i, boolean z) {
        long uptimeMillis;
        float lastActivatedScale;
        if (com.android.window.flags.Flags.magnificationAlwaysDrawFullscreenBorder()) {
            getMagnificationConnectionManager().onFullscreenMagnificationActivationChanged(i, z);
        }
        if (z) {
            synchronized (this.mLock) {
                this.mFullScreenModeEnabledTimeArray.put(i, android.os.SystemClock.uptimeMillis());
                setCurrentMagnificationModeAndSwitchDelegate(i, 1);
                this.mLastMagnificationActivatedModeArray.put(i, 1);
            }
            logMagnificationModeWithImeOnIfNeeded(i);
            disableWindowMagnificationIfNeeded(i);
        } else {
            synchronized (this.mLock) {
                setCurrentMagnificationModeAndSwitchDelegate(i, 0);
                uptimeMillis = android.os.SystemClock.uptimeMillis() - this.mFullScreenModeEnabledTimeArray.get(i);
                lastActivatedScale = this.mFullScreenMagnificationController.getLastActivatedScale(i);
            }
            logMagnificationUsageState(1, uptimeMillis, lastActivatedScale);
        }
        updateMagnificationUIControls(i, 1);
    }

    private void disableWindowMagnificationIfNeeded(int i) {
        com.android.server.accessibility.magnification.MagnificationConnectionManager magnificationConnectionManager = getMagnificationConnectionManager();
        if (isActivated(i, 2)) {
            magnificationConnectionManager.disableWindowMagnification(i, false);
        }
    }

    @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
    public void onImeWindowVisibilityChanged(int i, boolean z) {
        synchronized (this.mLock) {
            this.mIsImeVisibleArray.put(i, z);
        }
        getMagnificationConnectionManager().onImeWindowVisibilityChanged(i, z);
        logMagnificationModeWithImeOnIfNeeded(i);
    }

    public int getLastMagnificationActivatedMode(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mLastMagnificationActivatedModeArray.get(i, 1);
        }
        return i2;
    }

    @com.android.internal.annotations.VisibleForTesting
    public void logMagnificationUsageState(int i, long j, float f) {
        com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logMagnificationUsageState(i, j, f);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void logMagnificationModeWithIme(int i) {
        com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logMagnificationModeWithImeOn(i);
    }

    public void updateUserIdIfNeeded(int i) {
        com.android.server.accessibility.magnification.FullScreenMagnificationController fullScreenMagnificationController;
        com.android.server.accessibility.magnification.MagnificationConnectionManager magnificationConnectionManager;
        if (this.mUserId == i) {
            return;
        }
        this.mUserId = i;
        synchronized (this.mLock) {
            fullScreenMagnificationController = this.mFullScreenMagnificationController;
            magnificationConnectionManager = this.mMagnificationConnectionManager;
            this.mAccessibilityCallbacksDelegateArray.clear();
            this.mCurrentMagnificationModeArray.clear();
            this.mLastMagnificationActivatedModeArray.clear();
            this.mIsImeVisibleArray.clear();
        }
        this.mScaleProvider.onUserChanged(i);
        if (fullScreenMagnificationController != null) {
            fullScreenMagnificationController.resetAllIfNeeded(false);
        }
        if (magnificationConnectionManager != null) {
            magnificationConnectionManager.disableAllWindowMagnifiers();
        }
    }

    public void onDisplayRemoved(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mFullScreenMagnificationController != null) {
                    this.mFullScreenMagnificationController.onDisplayRemoved(i);
                }
                if (this.mMagnificationConnectionManager != null) {
                    this.mMagnificationConnectionManager.onDisplayRemoved(i);
                }
                this.mAccessibilityCallbacksDelegateArray.delete(i);
                this.mCurrentMagnificationModeArray.delete(i);
                this.mLastMagnificationActivatedModeArray.delete(i);
                this.mIsImeVisibleArray.delete(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mScaleProvider.onDisplayRemoved(i);
    }

    public void onUserRemoved(int i) {
        this.mScaleProvider.onUserRemoved(i);
    }

    public void setMagnificationCapabilities(int i) {
        this.mMagnificationCapabilities = i;
    }

    public void setMagnificationFollowTypingEnabled(boolean z) {
        getMagnificationConnectionManager().setMagnificationFollowTypingEnabled(z);
        getFullScreenMagnificationController().setMagnificationFollowTypingEnabled(z);
    }

    public void setAlwaysOnMagnificationEnabled(boolean z) {
        getFullScreenMagnificationController().setAlwaysOnMagnificationEnabled(z);
    }

    public boolean isAlwaysOnMagnificationFeatureFlagEnabled() {
        return this.mAlwaysOnMagnificationFeatureFlag.isFeatureFlagEnabled();
    }

    private com.android.server.accessibility.magnification.MagnificationController.DisableMagnificationCallback getDisableMagnificationEndRunnableLocked(int i) {
        return this.mMagnificationEndRunnableSparseArray.get(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDisableMagnificationCallbackLocked(int i, @android.annotation.Nullable com.android.server.accessibility.magnification.MagnificationController.DisableMagnificationCallback disableMagnificationCallback) {
        this.mMagnificationEndRunnableSparseArray.put(i, disableMagnificationCallback);
    }

    private void logMagnificationModeWithImeOnIfNeeded(int i) {
        synchronized (this.mLock) {
            int i2 = this.mCurrentMagnificationModeArray.get(i, 0);
            if (!this.mIsImeVisibleArray.get(i, false) || i2 == 0) {
                return;
            }
            logMagnificationModeWithIme(i2);
        }
    }

    public com.android.server.accessibility.magnification.FullScreenMagnificationController getFullScreenMagnificationController() {
        synchronized (this.mLock) {
            try {
                if (this.mFullScreenMagnificationController == null) {
                    this.mFullScreenMagnificationController = new com.android.server.accessibility.magnification.FullScreenMagnificationController(this.mContext, this.mAms.getTraceManager(), this.mLock, this, this.mScaleProvider, this.mBackgroundExecutor);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mFullScreenMagnificationController;
    }

    public boolean isFullScreenMagnificationControllerInitialized() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mFullScreenMagnificationController != null;
        }
        return z;
    }

    public com.android.server.accessibility.magnification.MagnificationConnectionManager getMagnificationConnectionManager() {
        com.android.server.accessibility.magnification.MagnificationConnectionManager magnificationConnectionManager;
        synchronized (this.mLock) {
            try {
                if (this.mMagnificationConnectionManager == null) {
                    this.mMagnificationConnectionManager = new com.android.server.accessibility.magnification.MagnificationConnectionManager(this.mContext, this.mLock, this, this.mAms.getTraceManager(), this.mScaleProvider);
                }
                magnificationConnectionManager = this.mMagnificationConnectionManager;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return magnificationConnectionManager;
    }

    @android.annotation.Nullable
    private android.graphics.PointF getCurrentMagnificationCenterLocked(int i, int i2) {
        if (i2 == 1) {
            if (this.mMagnificationConnectionManager == null || !this.mMagnificationConnectionManager.isWindowMagnifierEnabled(i)) {
                return null;
            }
            this.mTempPoint.set(this.mMagnificationConnectionManager.getCenterX(i), this.mMagnificationConnectionManager.getCenterY(i));
        } else {
            if (this.mFullScreenMagnificationController == null || !this.mFullScreenMagnificationController.isActivated(i)) {
                return null;
            }
            this.mTempPoint.set(this.mFullScreenMagnificationController.getCenterX(i), this.mFullScreenMagnificationController.getCenterY(i));
        }
        return this.mTempPoint;
    }

    public boolean isActivated(int i, int i2) {
        boolean z = false;
        if (i2 == 1) {
            synchronized (this.mLock) {
                try {
                    if (this.mFullScreenMagnificationController == null) {
                        return false;
                    }
                    z = this.mFullScreenMagnificationController.isActivated(i);
                } finally {
                }
            }
        } else if (i2 == 2) {
            synchronized (this.mLock) {
                try {
                    if (this.mMagnificationConnectionManager == null) {
                        return false;
                    }
                    z = this.mMagnificationConnectionManager.isWindowMagnifierEnabled(i);
                } finally {
                }
            }
        }
        return z;
    }

    private final class DisableMagnificationCallback implements android.view.accessibility.MagnificationAnimationCallback {
        private final boolean mAnimate;
        private final int mCurrentMode;
        private final float mCurrentScale;
        private final int mDisplayId;
        private final int mTargetMode;
        private final com.android.server.accessibility.magnification.MagnificationController.TransitionCallBack mTransitionCallBack;
        private boolean mExpired = false;
        private final android.graphics.PointF mCurrentCenter = new android.graphics.PointF();

        DisableMagnificationCallback(@android.annotation.Nullable com.android.server.accessibility.magnification.MagnificationController.TransitionCallBack transitionCallBack, int i, int i2, float f, android.graphics.PointF pointF, boolean z) {
            this.mTransitionCallBack = transitionCallBack;
            this.mDisplayId = i;
            this.mTargetMode = i2;
            this.mCurrentMode = this.mTargetMode ^ 3;
            this.mCurrentScale = f;
            this.mCurrentCenter.set(pointF);
            this.mAnimate = z;
        }

        public void onResult(boolean z) {
            synchronized (com.android.server.accessibility.magnification.MagnificationController.this.mLock) {
                try {
                    if (this.mExpired) {
                        return;
                    }
                    setExpiredAndRemoveFromListLocked();
                    com.android.server.accessibility.magnification.MagnificationController.this.setTransitionState(java.lang.Integer.valueOf(this.mDisplayId), null);
                    if (z) {
                        adjustCurrentCenterIfNeededLocked();
                        applyMagnificationModeLocked(this.mTargetMode);
                    } else {
                        com.android.server.accessibility.magnification.FullScreenMagnificationController fullScreenMagnificationController = com.android.server.accessibility.magnification.MagnificationController.this.getFullScreenMagnificationController();
                        if (this.mCurrentMode == 1 && !fullScreenMagnificationController.isActivated(this.mDisplayId)) {
                            android.accessibilityservice.MagnificationConfig.Builder builder = new android.accessibilityservice.MagnificationConfig.Builder();
                            android.graphics.Region region = new android.graphics.Region();
                            builder.setMode(1).setActivated(fullScreenMagnificationController.isActivated(this.mDisplayId)).setScale(fullScreenMagnificationController.getScale(this.mDisplayId)).setCenterX(fullScreenMagnificationController.getCenterX(this.mDisplayId)).setCenterY(fullScreenMagnificationController.getCenterY(this.mDisplayId));
                            fullScreenMagnificationController.getMagnificationRegion(this.mDisplayId, region);
                            com.android.server.accessibility.magnification.MagnificationController.this.mAms.notifyMagnificationChanged(this.mDisplayId, region, builder.build());
                        }
                    }
                    com.android.server.accessibility.magnification.MagnificationController.this.updateMagnificationUIControls(this.mDisplayId, this.mTargetMode);
                    if (this.mTransitionCallBack != null) {
                        this.mTransitionCallBack.onResult(this.mDisplayId, z);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void adjustCurrentCenterIfNeededLocked() {
            if (this.mTargetMode == 2) {
                return;
            }
            android.graphics.Region region = new android.graphics.Region();
            com.android.server.accessibility.magnification.MagnificationController.this.getFullScreenMagnificationController().getMagnificationRegion(this.mDisplayId, region);
            if (region.contains((int) this.mCurrentCenter.x, (int) this.mCurrentCenter.y)) {
                return;
            }
            android.graphics.Rect bounds = region.getBounds();
            this.mCurrentCenter.set(bounds.exactCenterX(), bounds.exactCenterY());
        }

        void restoreToCurrentMagnificationMode() {
            synchronized (com.android.server.accessibility.magnification.MagnificationController.this.mLock) {
                try {
                    if (this.mExpired) {
                        return;
                    }
                    setExpiredAndRemoveFromListLocked();
                    com.android.server.accessibility.magnification.MagnificationController.this.setTransitionState(java.lang.Integer.valueOf(this.mDisplayId), null);
                    applyMagnificationModeLocked(this.mCurrentMode);
                    com.android.server.accessibility.magnification.MagnificationController.this.updateMagnificationUIControls(this.mDisplayId, this.mCurrentMode);
                    if (this.mTransitionCallBack != null) {
                        this.mTransitionCallBack.onResult(this.mDisplayId, true);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void setExpiredAndRemoveFromListLocked() {
            this.mExpired = true;
            com.android.server.accessibility.magnification.MagnificationController.this.setDisableMagnificationCallbackLocked(this.mDisplayId, null);
        }

        private void applyMagnificationModeLocked(int i) {
            if (i == 1) {
                com.android.server.accessibility.magnification.FullScreenMagnificationController fullScreenMagnificationController = com.android.server.accessibility.magnification.MagnificationController.this.getFullScreenMagnificationController();
                if (!fullScreenMagnificationController.isRegistered(this.mDisplayId)) {
                    fullScreenMagnificationController.register(this.mDisplayId);
                }
                fullScreenMagnificationController.setScaleAndCenter(this.mDisplayId, this.mCurrentScale, this.mCurrentCenter.x, this.mCurrentCenter.y, this.mAnimate, 0);
                return;
            }
            com.android.server.accessibility.magnification.MagnificationController.this.getMagnificationConnectionManager().enableWindowMagnification(this.mDisplayId, this.mCurrentScale, this.mCurrentCenter.x, this.mCurrentCenter.y, this.mAnimate ? android.view.accessibility.MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK : null, 0);
        }
    }
}
