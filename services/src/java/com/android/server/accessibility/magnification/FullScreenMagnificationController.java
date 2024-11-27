package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
public class FullScreenMagnificationController implements com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_SET_MAGNIFICATION_SPEC = false;
    private static final java.lang.String LOG_TAG = "FullScreenMagnificationController";
    private boolean mAlwaysOnMagnificationEnabled;
    private final com.android.server.accessibility.magnification.FullScreenMagnificationController.ControllerContext mControllerCtx;
    private final android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification> mDisplays;
    private final java.lang.Object mLock;
    private boolean mMagnificationFollowTypingEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback> mMagnificationInfoChangedCallbacks;
    private final com.android.server.accessibility.magnification.MagnificationThumbnailFeatureFlag mMagnificationThumbnailFeatureFlag;
    private final long mMainThreadId;
    private final com.android.server.accessibility.magnification.MagnificationScaleProvider mScaleProvider;
    private final com.android.server.accessibility.magnification.FullScreenMagnificationController.ScreenStateObserver mScreenStateObserver;
    private final java.util.function.Supplier<android.widget.Scroller> mScrollerSupplier;
    private final android.graphics.Rect mTempRect;

    @android.annotation.NonNull
    private final java.util.function.Supplier<com.android.server.accessibility.magnification.MagnificationThumbnail> mThumbnailSupplier;
    private final java.util.function.Supplier<android.animation.TimeAnimator> mTimeAnimatorSupplier;

    interface MagnificationInfoChangedCallback {
        void onFullScreenMagnificationActivationState(int i, boolean z);

        void onFullScreenMagnificationChanged(int i, @android.annotation.NonNull android.graphics.Region region, @android.annotation.NonNull android.accessibilityservice.MagnificationConfig magnificationConfig);

        void onImeWindowVisibilityChanged(int i, boolean z);

        void onRequestMagnificationSpec(int i, int i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class DisplayMagnification implements com.android.server.wm.WindowManagerInternal.MagnificationCallbacks {
        private boolean mDeleteAfterUnregister;
        private final int mDisplayId;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @android.annotation.Nullable
        private com.android.server.accessibility.magnification.MagnificationThumbnail mMagnificationThumbnail;
        private boolean mRegistered;
        private final com.android.server.accessibility.magnification.FullScreenMagnificationController.SpecAnimationBridge mSpecAnimationBridge;
        private boolean mUnregisterPending;
        private final android.view.MagnificationSpec mCurrentMagnificationSpec = new android.view.MagnificationSpec();
        private final android.graphics.Region mMagnificationRegion = android.graphics.Region.obtain();
        private final android.graphics.Rect mMagnificationBounds = new android.graphics.Rect();
        private final android.graphics.Rect mTempRect = new android.graphics.Rect();
        private final android.graphics.Rect mTempRect1 = new android.graphics.Rect();
        private int mIdOfLastServiceToMagnify = -1;
        private boolean mMagnificationActivated = false;
        private boolean mZoomedOutFromService = false;

        DisplayMagnification(int i) {
            this.mDisplayId = i;
            this.mSpecAnimationBridge = new com.android.server.accessibility.magnification.FullScreenMagnificationController.SpecAnimationBridge(com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mControllerCtx, com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mLock, this.mDisplayId, com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mScrollerSupplier, com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mTimeAnimatorSupplier);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean register() {
            if (com.android.server.accessibility.magnification.FullScreenMagnificationController.this.traceEnabled()) {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.this.logTrace("setMagnificationCallbacks", "displayID=" + this.mDisplayId + ";callback=" + this);
            }
            this.mRegistered = com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mControllerCtx.getWindowManager().setMagnificationCallbacks(this.mDisplayId, this);
            if (!this.mRegistered) {
                android.util.Slog.w(com.android.server.accessibility.magnification.FullScreenMagnificationController.LOG_TAG, "set magnification callbacks fail, displayId:" + this.mDisplayId);
                return false;
            }
            this.mSpecAnimationBridge.setEnabled(true);
            if (com.android.server.accessibility.magnification.FullScreenMagnificationController.this.traceEnabled()) {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.this.logTrace("getMagnificationRegion", "displayID=" + this.mDisplayId + ";region=" + this.mMagnificationRegion);
            }
            com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mControllerCtx.getWindowManager().getMagnificationRegion(this.mDisplayId, this.mMagnificationRegion);
            this.mMagnificationRegion.getBounds(this.mMagnificationBounds);
            createThumbnailIfSupported();
            return true;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void unregister(boolean z) {
            if (this.mRegistered) {
                this.mSpecAnimationBridge.setEnabled(false);
                if (com.android.server.accessibility.magnification.FullScreenMagnificationController.this.traceEnabled()) {
                    com.android.server.accessibility.magnification.FullScreenMagnificationController.this.logTrace("setMagnificationCallbacks", "displayID=" + this.mDisplayId + ";callback=null");
                }
                com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mControllerCtx.getWindowManager().setMagnificationCallbacks(this.mDisplayId, null);
                this.mMagnificationRegion.setEmpty();
                this.mRegistered = false;
                com.android.server.accessibility.magnification.FullScreenMagnificationController.this.unregisterCallbackLocked(this.mDisplayId, z);
                destroyThumbnail();
            }
            this.mUnregisterPending = false;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void unregisterPending(boolean z) {
            this.mDeleteAfterUnregister = z;
            this.mUnregisterPending = true;
            reset(true);
        }

        boolean isRegistered() {
            return this.mRegistered;
        }

        boolean isActivated() {
            return this.mMagnificationActivated;
        }

        float getScale() {
            return this.mCurrentMagnificationSpec.scale;
        }

        float getOffsetX() {
            return this.mCurrentMagnificationSpec.offsetX;
        }

        float getOffsetY() {
            return this.mCurrentMagnificationSpec.offsetY;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean isAtEdge() {
            return isAtLeftEdge() || isAtRightEdge() || isAtTopEdge() || isAtBottomEdge();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean isAtLeftEdge() {
            return getOffsetX() == getMaxOffsetXLocked();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean isAtRightEdge() {
            return getOffsetX() == getMinOffsetXLocked();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean isAtTopEdge() {
            return getOffsetY() == getMaxOffsetYLocked();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean isAtBottomEdge() {
            return getOffsetY() == getMinOffsetYLocked();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        float getCenterX() {
            return (((this.mMagnificationBounds.width() / 2.0f) + this.mMagnificationBounds.left) - getOffsetX()) / getScale();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        float getCenterY() {
            return (((this.mMagnificationBounds.height() / 2.0f) + this.mMagnificationBounds.top) - getOffsetY()) / getScale();
        }

        float getSentScale() {
            return this.mSpecAnimationBridge.mSentMagnificationSpec.scale;
        }

        float getSentOffsetX() {
            return this.mSpecAnimationBridge.mSentMagnificationSpec.offsetX;
        }

        float getSentOffsetY() {
            return this.mSpecAnimationBridge.mSentMagnificationSpec.offsetY;
        }

        @Override // com.android.server.wm.WindowManagerInternal.MagnificationCallbacks
        public void onMagnificationRegionChanged(android.graphics.Region region) {
            com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mControllerCtx.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification) obj).updateMagnificationRegion((android.graphics.Region) obj2);
                }
            }, this, android.graphics.Region.obtain(region)));
        }

        @Override // com.android.server.wm.WindowManagerInternal.MagnificationCallbacks
        public void onRectangleOnScreenRequested(int i, int i2, int i3, int i4) {
            com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mControllerCtx.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda8
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                    ((com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification) obj).requestRectangleOnScreen(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), ((java.lang.Integer) obj5).intValue());
                }
            }, this, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4)));
        }

        @Override // com.android.server.wm.WindowManagerInternal.MagnificationCallbacks
        public void onDisplaySizeChanged() {
            onUserContextChanged();
        }

        @Override // com.android.server.wm.WindowManagerInternal.MagnificationCallbacks
        public void onUserContextChanged() {
            com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mControllerCtx.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((com.android.server.accessibility.magnification.FullScreenMagnificationController) obj).onUserContextChanged(((java.lang.Integer) obj2).intValue());
                }
            }, com.android.server.accessibility.magnification.FullScreenMagnificationController.this, java.lang.Integer.valueOf(this.mDisplayId)));
            synchronized (com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mLock) {
                refreshThumbnail();
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal.MagnificationCallbacks
        public void onImeWindowVisibilityChanged(boolean z) {
            com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mControllerCtx.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda1
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((com.android.server.accessibility.magnification.FullScreenMagnificationController) obj).notifyImeWindowVisibilityChanged(((java.lang.Integer) obj2).intValue(), ((java.lang.Boolean) obj3).booleanValue());
                }
            }, com.android.server.accessibility.magnification.FullScreenMagnificationController.this, java.lang.Integer.valueOf(this.mDisplayId), java.lang.Boolean.valueOf(z)));
        }

        void updateMagnificationRegion(android.graphics.Region region) {
            synchronized (com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mLock) {
                try {
                    if (this.mRegistered) {
                        if (!this.mMagnificationRegion.equals(region)) {
                            this.mMagnificationRegion.set(region);
                            this.mMagnificationRegion.getBounds(this.mMagnificationBounds);
                            refreshThumbnail();
                            if (updateCurrentSpecWithOffsetsLocked(this.mCurrentMagnificationSpec.offsetX, this.mCurrentMagnificationSpec.offsetY)) {
                                sendSpecToAnimation(this.mCurrentMagnificationSpec, null);
                            }
                            onMagnificationChangedLocked();
                        }
                        region.recycle();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void sendSpecToAnimation(android.view.MagnificationSpec magnificationSpec, android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback) {
            if (java.lang.Thread.currentThread().getId() == com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mMainThreadId) {
                this.mSpecAnimationBridge.updateSentSpecMainThread(magnificationSpec, magnificationAnimationCallback);
            } else {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mControllerCtx.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda6
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((com.android.server.accessibility.magnification.FullScreenMagnificationController.SpecAnimationBridge) obj).updateSentSpecMainThread((android.view.MagnificationSpec) obj2, (android.view.accessibility.MagnificationAnimationCallback) obj3);
                    }
                }, this.mSpecAnimationBridge, magnificationSpec, magnificationAnimationCallback));
            }
        }

        void startFlingAnimation(float f, float f2, android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback) {
            if (java.lang.Thread.currentThread().getId() == com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mMainThreadId) {
                this.mSpecAnimationBridge.startFlingAnimation(f, f2, getMinOffsetXLocked(), getMaxOffsetXLocked(), getMinOffsetYLocked(), getMaxOffsetYLocked(), magnificationAnimationCallback);
            } else {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mControllerCtx.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.OctConsumer() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda0
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8) {
                        ((com.android.server.accessibility.magnification.FullScreenMagnificationController.SpecAnimationBridge) obj).startFlingAnimation(((java.lang.Float) obj2).floatValue(), ((java.lang.Float) obj3).floatValue(), ((java.lang.Float) obj4).floatValue(), ((java.lang.Float) obj5).floatValue(), ((java.lang.Float) obj6).floatValue(), ((java.lang.Float) obj7).floatValue(), (android.view.accessibility.MagnificationAnimationCallback) obj8);
                    }
                }, this.mSpecAnimationBridge, java.lang.Float.valueOf(f), java.lang.Float.valueOf(f2), java.lang.Float.valueOf(getMinOffsetXLocked()), java.lang.Float.valueOf(getMaxOffsetXLocked()), java.lang.Float.valueOf(getMinOffsetYLocked()), java.lang.Float.valueOf(getMaxOffsetYLocked()), magnificationAnimationCallback));
            }
        }

        void cancelFlingAnimation() {
            if (java.lang.Thread.currentThread().getId() == com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mMainThreadId) {
                this.mSpecAnimationBridge.cancelFlingAnimation();
                return;
            }
            android.os.Handler handler = com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mControllerCtx.getHandler();
            final com.android.server.accessibility.magnification.FullScreenMagnificationController.SpecAnimationBridge specAnimationBridge = this.mSpecAnimationBridge;
            java.util.Objects.requireNonNull(specAnimationBridge);
            handler.post(new java.lang.Runnable() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.accessibility.magnification.FullScreenMagnificationController.SpecAnimationBridge.this.cancelFlingAnimation();
                }
            });
        }

        int getIdOfLastServiceToMagnify() {
            return this.mIdOfLastServiceToMagnify;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void onMagnificationChangedLocked() {
            float scale = getScale();
            float centerX = getCenterX();
            float centerY = getCenterY();
            final android.accessibilityservice.MagnificationConfig build = new android.accessibilityservice.MagnificationConfig.Builder().setMode(1).setActivated(this.mMagnificationActivated).setScale(scale).setCenterX(centerX).setCenterY(centerY).build();
            com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mMagnificationInfoChangedCallbacks.forEach(new java.util.function.Consumer() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification.this.lambda$onMagnificationChangedLocked$0(build, (com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback) obj);
                }
            });
            if (this.mUnregisterPending && !isActivated()) {
                unregister(this.mDeleteAfterUnregister);
            }
            if (isActivated()) {
                updateThumbnail(scale, centerX, centerY);
            } else {
                hideThumbnail();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMagnificationChangedLocked$0(android.accessibilityservice.MagnificationConfig magnificationConfig, com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback magnificationInfoChangedCallback) {
            magnificationInfoChangedCallback.onFullScreenMagnificationChanged(this.mDisplayId, this.mMagnificationRegion, magnificationConfig);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean magnificationRegionContains(float f, float f2) {
            return this.mMagnificationRegion.contains((int) f, (int) f2);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void getMagnificationBounds(@android.annotation.NonNull android.graphics.Rect rect) {
            rect.set(this.mMagnificationBounds);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void getMagnificationRegion(@android.annotation.NonNull android.graphics.Region region) {
            region.set(this.mMagnificationRegion);
        }

        private android.util.DisplayMetrics getDisplayMetricsForId() {
            android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
            android.view.DisplayInfo displayInfo = com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mDisplayManagerInternal.getDisplayInfo(this.mDisplayId);
            if (displayInfo != null) {
                displayInfo.getLogicalMetrics(displayMetrics, android.content.res.CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO, (android.content.res.Configuration) null);
            } else {
                displayMetrics.setToDefaults();
            }
            return displayMetrics;
        }

        void requestRectangleOnScreen(int i, int i2, int i3, int i4) {
            float f;
            synchronized (com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mLock) {
                try {
                    android.graphics.Rect rect = this.mTempRect;
                    getMagnificationBounds(rect);
                    if (rect.intersects(i, i2, i3, i4)) {
                        android.graphics.Rect rect2 = this.mTempRect1;
                        getMagnifiedFrameInContentCoordsLocked(rect2);
                        float width = rect2.width() / 4.0f;
                        float applyDimension = android.util.TypedValue.applyDimension(1, 10.0f, getDisplayMetricsForId());
                        int i5 = i3 - i;
                        int width2 = rect2.width();
                        float f2 = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                        if (i5 > width2) {
                            if (android.text.TextUtils.getLayoutDirectionFromLocale(java.util.Locale.getDefault()) == 0) {
                                f = i - rect2.left;
                            } else {
                                f = i3 - rect2.right;
                            }
                        } else if (i < rect2.left) {
                            f = (i - rect2.left) - width;
                        } else if (i3 > rect2.right) {
                            f = (i3 - rect2.right) + width;
                        } else {
                            f = 0.0f;
                        }
                        if (i4 - i2 > rect2.height()) {
                            f2 = i2 - rect2.top;
                        } else if (i2 < rect2.top) {
                            f2 = (i2 - rect2.top) - applyDimension;
                        } else if (i4 > rect2.bottom) {
                            f2 = (i4 - rect2.bottom) + applyDimension;
                        }
                        float scale = getScale();
                        offsetMagnifiedRegion(f * scale, f2 * scale, -1);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void getMagnifiedFrameInContentCoordsLocked(android.graphics.Rect rect) {
            float sentScale = getSentScale();
            float sentOffsetX = getSentOffsetX();
            float sentOffsetY = getSentOffsetY();
            getMagnificationBounds(rect);
            rect.offset((int) (-sentOffsetX), (int) (-sentOffsetY));
            rect.scale(1.0f / sentScale);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean setActivated(boolean z) {
            boolean z2 = this.mMagnificationActivated != z;
            if (z2) {
                this.mMagnificationActivated = z;
                com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mMagnificationInfoChangedCallbacks.forEach(new java.util.function.Consumer() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification.this.lambda$setActivated$1((com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback) obj);
                    }
                });
                com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mControllerCtx.getWindowManager().setFullscreenMagnificationActivated(this.mDisplayId, z);
            }
            return z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setActivated$1(com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback magnificationInfoChangedCallback) {
            magnificationInfoChangedCallback.onFullScreenMagnificationActivationState(this.mDisplayId, this.mMagnificationActivated);
        }

        void zoomOutFromService() {
            setScaleAndCenter(1.0f, Float.NaN, Float.NaN, com.android.server.accessibility.magnification.FullScreenMagnificationController.transformToStubCallback(true), 0);
            this.mZoomedOutFromService = true;
        }

        boolean isZoomedOutFromService() {
            return this.mZoomedOutFromService;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean reset(boolean z) {
            return reset(com.android.server.accessibility.magnification.FullScreenMagnificationController.transformToStubCallback(z));
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean reset(android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback) {
            if (!this.mRegistered) {
                return false;
            }
            android.view.MagnificationSpec magnificationSpec = this.mCurrentMagnificationSpec;
            boolean isActivated = isActivated();
            setActivated(false);
            if (isActivated) {
                magnificationSpec.clear();
                onMagnificationChangedLocked();
            }
            this.mIdOfLastServiceToMagnify = -1;
            sendSpecToAnimation(magnificationSpec, magnificationAnimationCallback);
            hideThumbnail();
            return isActivated;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean setScale(float f, float f2, float f3, boolean z, int i) {
            if (!this.mRegistered) {
                return false;
            }
            float constrainScale = com.android.server.accessibility.magnification.MagnificationScaleProvider.constrainScale(f);
            this.mMagnificationRegion.getBounds(this.mTempRect);
            android.view.MagnificationSpec magnificationSpec = this.mCurrentMagnificationSpec;
            float f4 = magnificationSpec.scale;
            float width = (((r7.width() / 2.0f) - magnificationSpec.offsetX) + r7.left) / f4;
            float height = (((r7.height() / 2.0f) - magnificationSpec.offsetY) + r7.top) / f4;
            float f5 = (f2 - magnificationSpec.offsetX) / f4;
            float f6 = (f3 - magnificationSpec.offsetY) / f4;
            float f7 = f4 / constrainScale;
            float f8 = (height - f6) * f7;
            this.mIdOfLastServiceToMagnify = i;
            return setScaleAndCenter(constrainScale, f5 + ((width - f5) * f7), f6 + f8, com.android.server.accessibility.magnification.FullScreenMagnificationController.transformToStubCallback(z), i);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean setScaleAndCenter(float f, float f2, float f3, android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback, int i) {
            if (!this.mRegistered) {
                return false;
            }
            boolean updateMagnificationSpecLocked = updateMagnificationSpecLocked(f, f2, f3) | setActivated(true);
            sendSpecToAnimation(this.mCurrentMagnificationSpec, magnificationAnimationCallback);
            if (isActivated() && i != -1) {
                this.mIdOfLastServiceToMagnify = i;
                com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mMagnificationInfoChangedCallbacks.forEach(new java.util.function.Consumer() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification.this.lambda$setScaleAndCenter$2((com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback) obj);
                    }
                });
            }
            this.mZoomedOutFromService = false;
            return updateMagnificationSpecLocked;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setScaleAndCenter$2(com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback magnificationInfoChangedCallback) {
            magnificationInfoChangedCallback.onRequestMagnificationSpec(this.mDisplayId, this.mIdOfLastServiceToMagnify);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void updateThumbnail(float f, float f2, float f3) {
            if (this.mMagnificationThumbnail != null) {
                this.mMagnificationThumbnail.updateThumbnail(f, f2, f3);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void refreshThumbnail() {
            if (this.mMagnificationThumbnail != null) {
                this.mMagnificationThumbnail.setThumbnailBounds(this.mMagnificationBounds, getScale(), getCenterX(), getCenterY());
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void hideThumbnail() {
            if (this.mMagnificationThumbnail != null) {
                this.mMagnificationThumbnail.hideThumbnail();
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void createThumbnailIfSupported() {
            if (this.mMagnificationThumbnail == null) {
                this.mMagnificationThumbnail = (com.android.server.accessibility.magnification.MagnificationThumbnail) com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mThumbnailSupplier.get();
                refreshThumbnail();
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void destroyThumbnail() {
            if (this.mMagnificationThumbnail != null) {
                hideThumbnail();
                this.mMagnificationThumbnail = null;
            }
        }

        void onThumbnailFeatureFlagChanged() {
            synchronized (com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mLock) {
                destroyThumbnail();
                createThumbnailIfSupported();
            }
        }

        boolean updateMagnificationSpecLocked(float f, float f2, float f3) {
            boolean z;
            if (java.lang.Float.isNaN(f2)) {
                f2 = getCenterX();
            }
            if (java.lang.Float.isNaN(f3)) {
                f3 = getCenterY();
            }
            if (java.lang.Float.isNaN(f)) {
                f = getScale();
            }
            float constrainScale = com.android.server.accessibility.magnification.MagnificationScaleProvider.constrainScale(f);
            if (java.lang.Float.compare(this.mCurrentMagnificationSpec.scale, constrainScale) == 0) {
                z = false;
            } else {
                this.mCurrentMagnificationSpec.scale = constrainScale;
                z = true;
            }
            boolean updateCurrentSpecWithOffsetsLocked = updateCurrentSpecWithOffsetsLocked(((this.mMagnificationBounds.width() / 2.0f) + this.mMagnificationBounds.left) - (f2 * constrainScale), ((this.mMagnificationBounds.height() / 2.0f) + this.mMagnificationBounds.top) - (f3 * constrainScale)) | z;
            if (updateCurrentSpecWithOffsetsLocked) {
                onMagnificationChangedLocked();
            }
            return updateCurrentSpecWithOffsetsLocked;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void offsetMagnifiedRegion(float f, float f2, int i) {
            if (!this.mRegistered) {
                return;
            }
            if (updateCurrentSpecWithOffsetsLocked(this.mCurrentMagnificationSpec.offsetX - f, this.mCurrentMagnificationSpec.offsetY - f2)) {
                onMagnificationChangedLocked();
            }
            if (i != -1) {
                this.mIdOfLastServiceToMagnify = i;
            }
            sendSpecToAnimation(this.mCurrentMagnificationSpec, null);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void startFling(float f, float f2, int i) {
            if (!this.mRegistered || !isActivated()) {
                return;
            }
            if (i != -1) {
                this.mIdOfLastServiceToMagnify = i;
            }
            startFlingAnimation(f, f2, new android.view.accessibility.MagnificationAnimationCallback() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification.1
                public void onResult(boolean z) {
                }

                public void onResult(boolean z, android.view.MagnificationSpec magnificationSpec) {
                    synchronized (com.android.server.accessibility.magnification.FullScreenMagnificationController.this.mLock) {
                        com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification.this.mCurrentMagnificationSpec.setTo(magnificationSpec);
                        com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification.this.onMagnificationChangedLocked();
                    }
                }
            });
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void cancelFling(int i) {
            if (!this.mRegistered) {
                return;
            }
            if (i != -1) {
                this.mIdOfLastServiceToMagnify = i;
            }
            cancelFlingAnimation();
        }

        boolean updateCurrentSpecWithOffsetsLocked(float f, float f2) {
            boolean z;
            float constrain = android.util.MathUtils.constrain(f, getMinOffsetXLocked(), getMaxOffsetXLocked());
            if (java.lang.Float.compare(this.mCurrentMagnificationSpec.offsetX, constrain) == 0) {
                z = false;
            } else {
                this.mCurrentMagnificationSpec.offsetX = constrain;
                z = true;
            }
            float constrain2 = android.util.MathUtils.constrain(f2, getMinOffsetYLocked(), getMaxOffsetYLocked());
            if (java.lang.Float.compare(this.mCurrentMagnificationSpec.offsetY, constrain2) == 0) {
                return z;
            }
            this.mCurrentMagnificationSpec.offsetY = constrain2;
            return true;
        }

        float getMinOffsetXLocked() {
            float width = this.mMagnificationBounds.left + this.mMagnificationBounds.width();
            return width - (this.mCurrentMagnificationSpec.scale * width);
        }

        float getMaxOffsetXLocked() {
            return this.mMagnificationBounds.left - (this.mMagnificationBounds.left * this.mCurrentMagnificationSpec.scale);
        }

        float getMinOffsetYLocked() {
            float height = this.mMagnificationBounds.top + this.mMagnificationBounds.height();
            return height - (this.mCurrentMagnificationSpec.scale * height);
        }

        float getMaxOffsetYLocked() {
            return this.mMagnificationBounds.top - (this.mMagnificationBounds.top * this.mCurrentMagnificationSpec.scale);
        }

        public java.lang.String toString() {
            return "DisplayMagnification[mCurrentMagnificationSpec=" + this.mCurrentMagnificationSpec + ", mMagnificationRegion=" + this.mMagnificationRegion + ", mMagnificationBounds=" + this.mMagnificationBounds + ", mDisplayId=" + this.mDisplayId + ", mIdOfLastServiceToMagnify=" + this.mIdOfLastServiceToMagnify + ", mRegistered=" + this.mRegistered + ", mUnregisterPending=" + this.mUnregisterPending + ']';
        }
    }

    public FullScreenMagnificationController(@android.annotation.NonNull final android.content.Context context, @android.annotation.NonNull com.android.server.accessibility.AccessibilityTraceManager accessibilityTraceManager, @android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback magnificationInfoChangedCallback, @android.annotation.NonNull com.android.server.accessibility.magnification.MagnificationScaleProvider magnificationScaleProvider, @android.annotation.NonNull java.util.concurrent.Executor executor) {
        this(new com.android.server.accessibility.magnification.FullScreenMagnificationController.ControllerContext(context, accessibilityTraceManager, (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class), new android.os.Handler(context.getMainLooper()), context.getResources().getInteger(android.R.integer.config_longAnimTime)), obj, magnificationInfoChangedCallback, magnificationScaleProvider, null, executor, new java.util.function.Supplier() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.widget.Scroller lambda$new$0;
                lambda$new$0 = com.android.server.accessibility.magnification.FullScreenMagnificationController.lambda$new$0(context);
                return lambda$new$0;
            }
        }, new java.util.function.Supplier() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return new android.animation.TimeAnimator();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.widget.Scroller lambda$new$0(android.content.Context context) {
        return new android.widget.Scroller(context);
    }

    @com.android.internal.annotations.VisibleForTesting
    public FullScreenMagnificationController(@android.annotation.NonNull final com.android.server.accessibility.magnification.FullScreenMagnificationController.ControllerContext controllerContext, @android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback magnificationInfoChangedCallback, @android.annotation.NonNull com.android.server.accessibility.magnification.MagnificationScaleProvider magnificationScaleProvider, java.util.function.Supplier<com.android.server.accessibility.magnification.MagnificationThumbnail> supplier, @android.annotation.NonNull java.util.concurrent.Executor executor, java.util.function.Supplier<android.widget.Scroller> supplier2, java.util.function.Supplier<android.animation.TimeAnimator> supplier3) {
        this.mMagnificationInfoChangedCallbacks = new java.util.ArrayList<>();
        this.mDisplays = new android.util.SparseArray<>(0);
        this.mTempRect = new android.graphics.Rect();
        this.mMagnificationFollowTypingEnabled = true;
        this.mAlwaysOnMagnificationEnabled = false;
        this.mControllerCtx = controllerContext;
        this.mLock = obj;
        this.mScrollerSupplier = supplier2;
        this.mTimeAnimatorSupplier = supplier3;
        this.mMainThreadId = this.mControllerCtx.getContext().getMainLooper().getThread().getId();
        this.mScreenStateObserver = new com.android.server.accessibility.magnification.FullScreenMagnificationController.ScreenStateObserver(this.mControllerCtx.getContext(), this);
        addInfoChangedCallback(magnificationInfoChangedCallback);
        this.mScaleProvider = magnificationScaleProvider;
        this.mDisplayManagerInternal = (android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class);
        this.mMagnificationThumbnailFeatureFlag = new com.android.server.accessibility.magnification.MagnificationThumbnailFeatureFlag();
        this.mMagnificationThumbnailFeatureFlag.addOnChangedListener(executor, new java.lang.Runnable() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.this.onMagnificationThumbnailFeatureFlagChanged();
            }
        });
        if (supplier != null) {
            this.mThumbnailSupplier = supplier;
        } else {
            this.mThumbnailSupplier = new java.util.function.Supplier() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    com.android.server.accessibility.magnification.MagnificationThumbnail lambda$new$1;
                    lambda$new$1 = com.android.server.accessibility.magnification.FullScreenMagnificationController.this.lambda$new$1(controllerContext);
                    return lambda$new$1;
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.accessibility.magnification.MagnificationThumbnail lambda$new$1(com.android.server.accessibility.magnification.FullScreenMagnificationController.ControllerContext controllerContext) {
        if (this.mMagnificationThumbnailFeatureFlag.isFeatureFlagEnabled()) {
            return new com.android.server.accessibility.magnification.MagnificationThumbnail(controllerContext.getContext(), (android.view.WindowManager) controllerContext.getContext().getSystemService(android.view.WindowManager.class), new android.os.Handler(controllerContext.getContext().getMainLooper()));
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMagnificationThumbnailFeatureFlagChanged() {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mDisplays.size(); i++) {
                try {
                    onMagnificationThumbnailFeatureFlagChanged(this.mDisplays.keyAt(i));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void onMagnificationThumbnailFeatureFlagChanged(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return;
                }
                displayMagnification.onThumbnailFeatureFlagChanged();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void register(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    displayMagnification = new com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification(i);
                }
                if (displayMagnification.isRegistered()) {
                    return;
                }
                if (displayMagnification.register()) {
                    this.mDisplays.put(i, displayMagnification);
                    this.mScreenStateObserver.registerIfNecessary();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unregister(int i) {
        synchronized (this.mLock) {
            unregisterLocked(i, false);
        }
    }

    public void unregisterAll() {
        synchronized (this.mLock) {
            try {
                android.util.SparseArray<com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification> clone = this.mDisplays.clone();
                for (int i = 0; i < clone.size(); i++) {
                    unregisterLocked(clone.keyAt(i), false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks
    public void onRectangleOnScreenRequested(int i, int i2, int i3, int i4, int i5) {
        synchronized (this.mLock) {
            try {
                if (this.mMagnificationFollowTypingEnabled) {
                    com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                    if (displayMagnification == null) {
                        return;
                    }
                    if (displayMagnification.isActivated()) {
                        android.graphics.Rect rect = this.mTempRect;
                        displayMagnification.getMagnifiedFrameInContentCoordsLocked(rect);
                        if (rect.contains(i2, i3, i4, i5)) {
                            return;
                        }
                        displayMagnification.onRectangleOnScreenRequested(i2, i3, i4, i5);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setMagnificationFollowTypingEnabled(boolean z) {
        this.mMagnificationFollowTypingEnabled = z;
    }

    boolean isMagnificationFollowTypingEnabled() {
        return this.mMagnificationFollowTypingEnabled;
    }

    void setAlwaysOnMagnificationEnabled(boolean z) {
        this.mAlwaysOnMagnificationEnabled = z;
    }

    boolean isAlwaysOnMagnificationEnabled() {
        return this.mAlwaysOnMagnificationEnabled;
    }

    void onUserContextChanged(int i) {
        synchronized (this.mLock) {
            try {
                if (isActivated(i)) {
                    if (isAlwaysOnMagnificationEnabled()) {
                        zoomOutFromService(i);
                    } else {
                        reset(i, true);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onDisplayRemoved(int i) {
        synchronized (this.mLock) {
            unregisterLocked(i, true);
        }
    }

    public boolean isRegistered(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.isRegistered();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isActivated(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.isActivated();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean magnificationRegionContains(int i, float f, float f2) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.magnificationRegionContains(f, f2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void getMagnificationBounds(int i, @android.annotation.NonNull android.graphics.Rect rect) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return;
                }
                displayMagnification.getMagnificationBounds(rect);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void getMagnificationRegion(int i, @android.annotation.NonNull android.graphics.Region region) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return;
                }
                displayMagnification.getMagnificationRegion(region);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public float getScale(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return 1.0f;
                }
                return displayMagnification.getScale();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected float getLastActivatedScale(int i) {
        return getScale(i);
    }

    public float getOffsetX(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                }
                return displayMagnification.getOffsetX();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public float getCenterX(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                }
                return displayMagnification.getCenterX();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAtEdge(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.isAtEdge();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAtLeftEdge(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.isAtLeftEdge();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAtRightEdge(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.isAtRightEdge();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAtTopEdge(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.isAtTopEdge();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAtBottomEdge(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.isAtBottomEdge();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public float getOffsetY(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                }
                return displayMagnification.getOffsetY();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public float getCenterY(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                }
                return displayMagnification.getCenterY();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean reset(int i, boolean z) {
        return reset(i, z ? android.view.accessibility.MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK : null);
    }

    public boolean reset(int i, android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.reset(magnificationAnimationCallback);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean setScale(int i, float f, float f2, float f3, boolean z, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.setScale(f, f2, f3, z, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean setCenter(int i, float f, float f2, boolean z, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.setScaleAndCenter(Float.NaN, f, f2, z ? android.view.accessibility.MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK : null, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean setScaleAndCenter(int i, float f, float f2, float f3, boolean z, int i2) {
        return setScaleAndCenter(i, f, f2, f3, transformToStubCallback(z), i2);
    }

    public boolean setScaleAndCenter(int i, float f, float f2, float f3, android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return false;
                }
                return displayMagnification.setScaleAndCenter(f, f2, f3, magnificationAnimationCallback, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void offsetMagnifiedRegion(int i, float f, float f2, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return;
                }
                displayMagnification.offsetMagnifiedRegion(f, f2, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void startFling(int i, float f, float f2, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return;
                }
                displayMagnification.startFling(f, f2, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void cancelFling(int i, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return;
                }
                displayMagnification.cancelFling(i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getIdOfLastServiceToMagnify(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null) {
                    return -1;
                }
                return displayMagnification.getIdOfLastServiceToMagnify();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void persistScale(int i) {
        float scale = getScale(0);
        if (scale < 1.3f) {
            return;
        }
        this.mScaleProvider.putScale(scale, i);
    }

    public float getPersistedScale(int i) {
        return android.util.MathUtils.constrain(this.mScaleProvider.getScale(i), 1.3f, 8.0f);
    }

    private void zoomOutFromService(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null || !displayMagnification.isActivated()) {
                    return;
                }
                displayMagnification.zoomOutFromService();
            } finally {
            }
        }
    }

    public boolean isZoomedOutFromService(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null || !displayMagnification.isActivated()) {
                    return false;
                }
                return displayMagnification.isZoomedOutFromService();
            } finally {
            }
        }
    }

    public void resetAllIfNeeded(int i) {
        synchronized (this.mLock) {
            for (int i2 = 0; i2 < this.mDisplays.size(); i2++) {
                try {
                    resetIfNeeded(this.mDisplays.keyAt(i2), i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    boolean resetIfNeeded(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification == null || !displayMagnification.isActivated()) {
                    return false;
                }
                displayMagnification.reset(z);
                return true;
            } finally {
            }
        }
    }

    boolean resetIfNeeded(int i, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
                if (displayMagnification != null && displayMagnification.isActivated() && i2 == displayMagnification.getIdOfLastServiceToMagnify()) {
                    displayMagnification.reset(true);
                    return true;
                }
                return false;
            } finally {
            }
        }
    }

    void notifyImeWindowVisibilityChanged(final int i, final boolean z) {
        synchronized (this.mLock) {
            this.mMagnificationInfoChangedCallbacks.forEach(new java.util.function.Consumer() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback) obj).onImeWindowVisibilityChanged(i, z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScreenTurnedOff() {
        this.mControllerCtx.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.accessibility.magnification.FullScreenMagnificationController) obj).resetAllIfNeeded(((java.lang.Boolean) obj2).booleanValue());
            }
        }, this, false));
    }

    void resetAllIfNeeded(boolean z) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mDisplays.size(); i++) {
                try {
                    resetIfNeeded(this.mDisplays.keyAt(i), z);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void unregisterLocked(int i, boolean z) {
        com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification displayMagnification = this.mDisplays.get(i);
        if (displayMagnification == null) {
            return;
        }
        if (!displayMagnification.isRegistered()) {
            if (z) {
                this.mDisplays.remove(i);
            }
        } else if (!displayMagnification.isActivated()) {
            displayMagnification.unregister(z);
        } else {
            displayMagnification.unregisterPending(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterCallbackLocked(int i, boolean z) {
        if (z) {
            this.mDisplays.remove(i);
        }
        boolean z2 = false;
        for (int i2 = 0; i2 < this.mDisplays.size() && !(z2 = this.mDisplays.valueAt(i2).isRegistered()); i2++) {
        }
        if (!z2) {
            this.mScreenStateObserver.unregister();
        }
    }

    void addInfoChangedCallback(@android.annotation.NonNull com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback magnificationInfoChangedCallback) {
        synchronized (this.mLock) {
            this.mMagnificationInfoChangedCallbacks.add(magnificationInfoChangedCallback);
        }
    }

    void removeInfoChangedCallback(@android.annotation.NonNull com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback magnificationInfoChangedCallback) {
        synchronized (this.mLock) {
            this.mMagnificationInfoChangedCallbacks.remove(magnificationInfoChangedCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean traceEnabled() {
        return this.mControllerCtx.getTraceManager().isA11yTracingEnabledForTypes(512L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logTrace(java.lang.String str, java.lang.String str2) {
        this.mControllerCtx.getTraceManager().logTrace("WindowManagerInternal." + str, 512L, str2);
    }

    public java.lang.String toString() {
        return "MagnificationController[, mDisplays=" + this.mDisplays + ", mScaleProvider=" + this.mScaleProvider + "]";
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SpecAnimationBridge implements android.animation.ValueAnimator.AnimatorUpdateListener, android.animation.Animator.AnimatorListener {
        private android.view.accessibility.MagnificationAnimationCallback mAnimationCallback;
        private final com.android.server.accessibility.magnification.FullScreenMagnificationController.ControllerContext mControllerCtx;
        private final int mDisplayId;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean mEnabled;
        private final android.view.MagnificationSpec mEndMagnificationSpec;
        private final java.lang.Object mLock;
        private final android.animation.TimeAnimator mScrollAnimator;
        private final android.widget.Scroller mScroller;
        private final android.view.MagnificationSpec mSentMagnificationSpec;
        private final android.view.MagnificationSpec mStartMagnificationSpec;
        private final android.animation.ValueAnimator mValueAnimator;

        private SpecAnimationBridge(com.android.server.accessibility.magnification.FullScreenMagnificationController.ControllerContext controllerContext, java.lang.Object obj, int i, java.util.function.Supplier<android.widget.Scroller> supplier, java.util.function.Supplier<android.animation.TimeAnimator> supplier2) {
            this.mSentMagnificationSpec = new android.view.MagnificationSpec();
            this.mStartMagnificationSpec = new android.view.MagnificationSpec();
            this.mEndMagnificationSpec = new android.view.MagnificationSpec();
            this.mEnabled = false;
            this.mControllerCtx = controllerContext;
            this.mLock = obj;
            this.mDisplayId = i;
            long animationDuration = this.mControllerCtx.getAnimationDuration();
            this.mValueAnimator = this.mControllerCtx.newValueAnimator();
            this.mValueAnimator.setDuration(animationDuration);
            this.mValueAnimator.setInterpolator(new android.view.animation.DecelerateInterpolator(2.5f));
            this.mValueAnimator.setFloatValues(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f);
            this.mValueAnimator.addUpdateListener(this);
            this.mValueAnimator.addListener(this);
            com.android.server.accessibility.Flags.fullscreenFlingGesture();
            this.mScroller = null;
            this.mScrollAnimator = null;
        }

        private /* synthetic */ void lambda$new$0(android.animation.TimeAnimator timeAnimator, long j, long j2) {
            synchronized (this.mLock) {
                try {
                    if (this.mEnabled) {
                        if (!this.mScroller.computeScrollOffset()) {
                            timeAnimator.end();
                            return;
                        }
                        this.mEndMagnificationSpec.offsetX = this.mScroller.getCurrX();
                        this.mEndMagnificationSpec.offsetY = this.mScroller.getCurrY();
                        setMagnificationSpecLocked(this.mEndMagnificationSpec);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setEnabled(boolean z) {
            synchronized (this.mLock) {
                try {
                    if (z != this.mEnabled) {
                        this.mEnabled = z;
                        if (!this.mEnabled) {
                            this.mSentMagnificationSpec.clear();
                            if (this.mControllerCtx.getTraceManager().isA11yTracingEnabledForTypes(512L)) {
                                this.mControllerCtx.getTraceManager().logTrace("WindowManagerInternal.setMagnificationSpec", 512L, "displayID=" + this.mDisplayId + ";spec=" + this.mSentMagnificationSpec);
                            }
                            this.mControllerCtx.getWindowManager().setMagnificationSpec(this.mDisplayId, this.mSentMagnificationSpec);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void updateSentSpecMainThread(android.view.MagnificationSpec magnificationSpec, android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback) {
            cancelAnimations();
            this.mAnimationCallback = magnificationAnimationCallback;
            synchronized (this.mLock) {
                try {
                    if (!this.mSentMagnificationSpec.equals(magnificationSpec)) {
                        if (this.mAnimationCallback != null) {
                            animateMagnificationSpecLocked(magnificationSpec);
                        } else {
                            setMagnificationSpecLocked(magnificationSpec);
                        }
                    } else {
                        sendEndCallbackMainThread(true);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void sendEndCallbackMainThread(boolean z) {
            if (this.mAnimationCallback != null) {
                this.mAnimationCallback.onResult(z, this.mSentMagnificationSpec);
                this.mAnimationCallback = null;
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void setMagnificationSpecLocked(android.view.MagnificationSpec magnificationSpec) {
            if (this.mEnabled) {
                this.mSentMagnificationSpec.setTo(magnificationSpec);
                if (this.mControllerCtx.getTraceManager().isA11yTracingEnabledForTypes(512L)) {
                    this.mControllerCtx.getTraceManager().logTrace("WindowManagerInternal.setMagnificationSpec", 512L, "displayID=" + this.mDisplayId + ";spec=" + this.mSentMagnificationSpec);
                }
                this.mControllerCtx.getWindowManager().setMagnificationSpec(this.mDisplayId, this.mSentMagnificationSpec);
            }
        }

        private void animateMagnificationSpecLocked(android.view.MagnificationSpec magnificationSpec) {
            this.mEndMagnificationSpec.setTo(magnificationSpec);
            this.mStartMagnificationSpec.setTo(this.mSentMagnificationSpec);
            this.mValueAnimator.start();
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
            synchronized (this.mLock) {
                try {
                    if (this.mEnabled) {
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        android.view.MagnificationSpec magnificationSpec = new android.view.MagnificationSpec();
                        magnificationSpec.scale = this.mStartMagnificationSpec.scale + ((this.mEndMagnificationSpec.scale - this.mStartMagnificationSpec.scale) * animatedFraction);
                        magnificationSpec.offsetX = this.mStartMagnificationSpec.offsetX + ((this.mEndMagnificationSpec.offsetX - this.mStartMagnificationSpec.offsetX) * animatedFraction);
                        magnificationSpec.offsetY = this.mStartMagnificationSpec.offsetY + ((this.mEndMagnificationSpec.offsetY - this.mStartMagnificationSpec.offsetY) * animatedFraction);
                        setMagnificationSpecLocked(magnificationSpec);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            sendEndCallbackMainThread(true);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
            sendEndCallbackMainThread(false);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(android.animation.Animator animator) {
        }

        public void startFlingAnimation(float f, float f2, float f3, float f4, float f5, float f6, android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback) {
            com.android.server.accessibility.Flags.fullscreenFlingGesture();
        }

        void cancelAnimations() {
            if (this.mValueAnimator.isRunning()) {
                this.mValueAnimator.cancel();
            }
            cancelFlingAnimation();
        }

        void cancelFlingAnimation() {
            com.android.server.accessibility.Flags.fullscreenFlingGesture();
        }
    }

    private static class ScreenStateObserver extends android.content.BroadcastReceiver {
        private final android.content.Context mContext;
        private final com.android.server.accessibility.magnification.FullScreenMagnificationController mController;
        private boolean mRegistered = false;

        ScreenStateObserver(android.content.Context context, com.android.server.accessibility.magnification.FullScreenMagnificationController fullScreenMagnificationController) {
            this.mContext = context;
            this.mController = fullScreenMagnificationController;
        }

        public void registerIfNecessary() {
            if (!this.mRegistered) {
                this.mContext.registerReceiver(this, new android.content.IntentFilter("android.intent.action.SCREEN_OFF"));
                this.mRegistered = true;
            }
        }

        public void unregister() {
            if (this.mRegistered) {
                this.mContext.unregisterReceiver(this);
                this.mRegistered = false;
            }
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            this.mController.onScreenTurnedOff();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class ControllerContext {
        private final java.lang.Long mAnimationDuration;
        private final android.content.Context mContext;
        private final android.os.Handler mHandler;
        private final com.android.server.accessibility.AccessibilityTraceManager mTrace;
        private final com.android.server.wm.WindowManagerInternal mWindowManager;

        public ControllerContext(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.accessibility.AccessibilityTraceManager accessibilityTraceManager, @android.annotation.NonNull com.android.server.wm.WindowManagerInternal windowManagerInternal, @android.annotation.NonNull android.os.Handler handler, long j) {
            this.mContext = context;
            this.mTrace = accessibilityTraceManager;
            this.mWindowManager = windowManagerInternal;
            this.mHandler = handler;
            this.mAnimationDuration = java.lang.Long.valueOf(j);
        }

        @android.annotation.NonNull
        public android.content.Context getContext() {
            return this.mContext;
        }

        @android.annotation.NonNull
        public com.android.server.accessibility.AccessibilityTraceManager getTraceManager() {
            return this.mTrace;
        }

        @android.annotation.NonNull
        public com.android.server.wm.WindowManagerInternal getWindowManager() {
            return this.mWindowManager;
        }

        @android.annotation.NonNull
        public android.os.Handler getHandler() {
            return this.mHandler;
        }

        @android.annotation.NonNull
        public android.animation.ValueAnimator newValueAnimator() {
            return new android.animation.ValueAnimator();
        }

        public long getAnimationDuration() {
            return this.mAnimationDuration.longValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public static android.view.accessibility.MagnificationAnimationCallback transformToStubCallback(boolean z) {
        if (z) {
            return android.view.accessibility.MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK;
        }
        return null;
    }
}
