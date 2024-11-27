package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
public class FullScreenMagnificationGestureHandler extends com.android.server.accessibility.magnification.MagnificationGestureHandler {
    private static final float MAX_SCALE = 8.0f;
    private static final float MIN_SCALE = 1.0f;

    @com.android.internal.annotations.VisibleForTesting
    static final int OVERSCROLL_LEFT_EDGE = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final int OVERSCROLL_NONE = 0;

    @com.android.internal.annotations.VisibleForTesting
    static final int OVERSCROLL_RIGHT_EDGE = 2;

    @com.android.internal.annotations.VisibleForTesting
    static final int OVERSCROLL_VERTICAL_EDGE = 3;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State mCurrentState;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DelegatingState mDelegatingState;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DetectingState mDetectingState;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.accessibility.magnification.FullScreenMagnificationController mFullScreenMagnificationController;
    private final com.android.server.accessibility.magnification.FullScreenMagnificationVibrationHelper mFullScreenMagnificationVibrationHelper;

    @com.android.internal.annotations.VisibleForTesting
    boolean mIsSinglePanningEnabled;
    private final boolean mIsWatch;
    private final com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback mMagnificationInfoChangedCallback;

    @android.annotation.NonNull
    private final com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MagnificationLogger mMagnificationLogger;
    private final int mMaximumVelocity;
    private final int mMinimumVelocity;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.OverscrollHandler mOverscrollHandler;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.PanningScalingState mPanningScalingState;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State mPreviousState;
    private final com.android.server.accessibility.magnification.WindowMagnificationPromptController mPromptController;
    private final com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.ScreenStateReceiver mScreenStateReceiver;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.SinglePanningState mSinglePanningState;
    private android.view.MotionEvent.PointerCoords[] mTempPointerCoords;
    private android.view.MotionEvent.PointerProperties[] mTempPointerProperties;

    @android.annotation.Nullable
    private android.view.VelocityTracker mVelocityTracker;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.ViewportDraggingState mViewportDraggingState;
    private static final boolean DEBUG_STATE_TRANSITIONS = com.android.server.accessibility.magnification.MagnificationGestureHandler.DEBUG_ALL | false;
    private static final boolean DEBUG_DETECTING = com.android.server.accessibility.magnification.MagnificationGestureHandler.DEBUG_ALL | false;
    private static final boolean DEBUG_PANNING_SCALING = com.android.server.accessibility.magnification.MagnificationGestureHandler.DEBUG_ALL | false;

    interface MagnificationLogger {
        void logMagnificationTripleTap(boolean z);

        void logMagnificationTwoFingerTripleTap(boolean z);
    }

    public @interface OverscrollState {
    }

    public FullScreenMagnificationGestureHandler(android.content.Context context, com.android.server.accessibility.magnification.FullScreenMagnificationController fullScreenMagnificationController, com.android.server.accessibility.AccessibilityTraceManager accessibilityTraceManager, com.android.server.accessibility.magnification.MagnificationGestureHandler.Callback callback, boolean z, boolean z2, boolean z3, @android.annotation.NonNull com.android.server.accessibility.magnification.WindowMagnificationPromptController windowMagnificationPromptController, int i, com.android.server.accessibility.magnification.FullScreenMagnificationVibrationHelper fullScreenMagnificationVibrationHelper) {
        this(context, fullScreenMagnificationController, accessibilityTraceManager, callback, z, z2, z3, windowMagnificationPromptController, i, fullScreenMagnificationVibrationHelper, null, android.view.ViewConfiguration.get(context));
    }

    @com.android.internal.annotations.VisibleForTesting
    FullScreenMagnificationGestureHandler(android.content.Context context, com.android.server.accessibility.magnification.FullScreenMagnificationController fullScreenMagnificationController, com.android.server.accessibility.AccessibilityTraceManager accessibilityTraceManager, com.android.server.accessibility.magnification.MagnificationGestureHandler.Callback callback, boolean z, boolean z2, boolean z3, @android.annotation.NonNull com.android.server.accessibility.magnification.WindowMagnificationPromptController windowMagnificationPromptController, int i, com.android.server.accessibility.magnification.FullScreenMagnificationVibrationHelper fullScreenMagnificationVibrationHelper, com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MagnificationLogger magnificationLogger, android.view.ViewConfiguration viewConfiguration) {
        super(i, z, z2, z3, accessibilityTraceManager, callback);
        if (com.android.server.accessibility.magnification.MagnificationGestureHandler.DEBUG_ALL) {
            android.util.Log.i(this.mLogTag, "FullScreenMagnificationGestureHandler(detectSingleFingerTripleTap = " + z + ", detectTwoFingerTripleTap = " + z2 + ", detectShortcutTrigger = " + z3 + ")");
        }
        com.android.server.accessibility.Flags.fullscreenFlingGesture();
        this.mMinimumVelocity = 0;
        this.mMaximumVelocity = 0;
        this.mFullScreenMagnificationController = fullScreenMagnificationController;
        this.mMagnificationInfoChangedCallback = new com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.1
            @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
            public void onRequestMagnificationSpec(int i2, int i3) {
            }

            @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
            public void onFullScreenMagnificationActivationState(int i2, boolean z4) {
                if (i2 == com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId && !z4) {
                    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectingState.setShortcutTriggered(false);
                }
            }

            @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
            public void onImeWindowVisibilityChanged(int i2, boolean z4) {
            }

            @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
            public void onFullScreenMagnificationChanged(int i2, @android.annotation.NonNull android.graphics.Region region, @android.annotation.NonNull android.accessibilityservice.MagnificationConfig magnificationConfig) {
            }
        };
        this.mFullScreenMagnificationController.addInfoChangedCallback(this.mMagnificationInfoChangedCallback);
        this.mPromptController = windowMagnificationPromptController;
        if (magnificationLogger == null) {
            this.mMagnificationLogger = new com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MagnificationLogger() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.2
                @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MagnificationLogger
                public void logMagnificationTripleTap(boolean z4) {
                    com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logMagnificationTripleTap(z4);
                }

                @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MagnificationLogger
                public void logMagnificationTwoFingerTripleTap(boolean z4) {
                    com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logMagnificationTwoFingerTripleTap(z4);
                }
            };
        } else {
            this.mMagnificationLogger = magnificationLogger;
        }
        this.mDelegatingState = new com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DelegatingState();
        com.android.server.accessibility.Flags.enableMagnificationMultipleFingerMultipleTapGesture();
        this.mDetectingState = new com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DetectingState(context);
        com.android.server.accessibility.Flags.enableMagnificationMultipleFingerMultipleTapGesture();
        this.mViewportDraggingState = new com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.ViewportDraggingState();
        this.mPanningScalingState = new com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.PanningScalingState(context);
        this.mSinglePanningState = new com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.SinglePanningState(context);
        this.mFullScreenMagnificationVibrationHelper = fullScreenMagnificationVibrationHelper;
        setSinglePanningEnabled(context.getResources().getBoolean(android.R.bool.config_enableWallpaperService));
        this.mOverscrollHandler = new com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.OverscrollHandler();
        this.mIsWatch = context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
        if (this.mDetectShortcutTrigger) {
            this.mScreenStateReceiver = new com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.ScreenStateReceiver(context, this);
            this.mScreenStateReceiver.register();
        } else {
            this.mScreenStateReceiver = null;
        }
        transitionTo(this.mDetectingState);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setSinglePanningEnabled(boolean z) {
        this.mIsSinglePanningEnabled = z;
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    void onMotionEventInternal(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (motionEvent.getActionMasked() == 0) {
            cancelFling();
        }
        handleEventWith(this.mCurrentState, motionEvent, motionEvent2, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleEventWith(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State state, android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        this.mPanningScalingState.mScrollGestureDetector.onTouchEvent(motionEvent);
        this.mPanningScalingState.mScaleGestureDetector.onTouchEvent(motionEvent);
        this.mSinglePanningState.mScrollGestureDetector.onTouchEvent(motionEvent);
        try {
            state.onMotionEvent(motionEvent, motionEvent2, i);
        } catch (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.GestureException e) {
            android.util.Slog.e(this.mLogTag, "Error processing motion event", e);
            clearAndTransitionToStateDetecting();
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void clearEvents(int i) {
        if (i == 4098) {
            clearAndTransitionToStateDetecting();
        }
        super.clearEvents(i);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onDestroy() {
        if (DEBUG_STATE_TRANSITIONS) {
            android.util.Slog.i(this.mLogTag, "onDestroy(); delayed = " + com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo.toString(this.mDetectingState.mDelayedEventQueue));
        }
        if (this.mScreenStateReceiver != null) {
            this.mScreenStateReceiver.unregister();
        }
        this.mPromptController.onDestroy();
        this.mFullScreenMagnificationController.resetIfNeeded(this.mDisplayId, 0);
        this.mFullScreenMagnificationController.removeInfoChangedCallback(this.mMagnificationInfoChangedCallback);
        clearAndTransitionToStateDetecting();
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public void handleShortcutTriggered() {
        if (this.mFullScreenMagnificationController.isActivated(this.mDisplayId)) {
            zoomOff();
            clearAndTransitionToStateDetecting();
        } else {
            this.mDetectingState.toggleShortcutTriggered();
        }
        if (this.mDetectingState.isShortcutTriggered()) {
            this.mPromptController.showNotificationIfNeeded();
            zoomToScale(1.0f, Float.NaN, Float.NaN);
        }
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public int getMode() {
        return 1;
    }

    void clearAndTransitionToStateDetecting() {
        this.mCurrentState = this.mDetectingState;
        this.mDetectingState.clear();
        this.mViewportDraggingState.clear();
        this.mPanningScalingState.clear();
    }

    private android.view.MotionEvent.PointerCoords[] getTempPointerCoordsWithMinSize(int i) {
        int length = this.mTempPointerCoords != null ? this.mTempPointerCoords.length : 0;
        if (length < i) {
            android.view.MotionEvent.PointerCoords[] pointerCoordsArr = this.mTempPointerCoords;
            this.mTempPointerCoords = new android.view.MotionEvent.PointerCoords[i];
            if (pointerCoordsArr != null) {
                java.lang.System.arraycopy(pointerCoordsArr, 0, this.mTempPointerCoords, 0, length);
            }
        }
        while (length < i) {
            this.mTempPointerCoords[length] = new android.view.MotionEvent.PointerCoords();
            length++;
        }
        return this.mTempPointerCoords;
    }

    private android.view.MotionEvent.PointerProperties[] getTempPointerPropertiesWithMinSize(int i) {
        int length = this.mTempPointerProperties != null ? this.mTempPointerProperties.length : 0;
        if (length < i) {
            android.view.MotionEvent.PointerProperties[] pointerPropertiesArr = this.mTempPointerProperties;
            this.mTempPointerProperties = new android.view.MotionEvent.PointerProperties[i];
            if (pointerPropertiesArr != null) {
                java.lang.System.arraycopy(pointerPropertiesArr, 0, this.mTempPointerProperties, 0, length);
            }
        }
        while (length < i) {
            this.mTempPointerProperties[length] = new android.view.MotionEvent.PointerProperties();
            length++;
        }
        return this.mTempPointerProperties;
    }

    @com.android.internal.annotations.VisibleForTesting
    void transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State state) {
        if (DEBUG_STATE_TRANSITIONS) {
            android.util.Slog.i(this.mLogTag, (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State.nameOf(this.mCurrentState) + " -> " + com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State.nameOf(state) + " at " + java.util.Arrays.asList((java.lang.StackTraceElement[]) java.util.Arrays.copyOfRange(new java.lang.RuntimeException().getStackTrace(), 1, 5))).replace(getClass().getName(), ""));
        }
        this.mPreviousState = this.mCurrentState;
        if (state == this.mPanningScalingState) {
            this.mPanningScalingState.prepareForState();
        }
        this.mCurrentState = state;
    }

    interface State {
        void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) throws com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.GestureException;

        default void clear() {
        }

        default java.lang.String name() {
            return getClass().getSimpleName();
        }

        static java.lang.String nameOf(@android.annotation.Nullable com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State state) {
            return state != null ? state.name() : "null";
        }
    }

    final class PanningScalingState extends android.view.GestureDetector.SimpleOnGestureListener implements android.view.ScaleGestureDetector.OnScaleGestureListener, com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State {

        @com.android.internal.annotations.VisibleForTesting
        static final float CHECK_DETECTING_PASS_PERSISTED_SCALE_THRESHOLD = 0.2f;

        @com.android.internal.annotations.VisibleForTesting
        static final float PASSING_PERSISTED_SCALE_THRESHOLD = 0.01f;
        private final android.content.Context mContext;

        @com.android.internal.annotations.VisibleForTesting
        boolean mDetectingPassPersistedScale;
        float mInitialScaleFactor = -1.0f;
        private final android.view.ScaleGestureDetector mScaleGestureDetector;

        @com.android.internal.annotations.VisibleForTesting
        boolean mScaling;
        final float mScalingThreshold;
        private final android.view.GestureDetector mScrollGestureDetector;

        PanningScalingState(android.content.Context context) {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            context.getResources().getValue(android.R.dimen.config_progressBarCornerRadius, typedValue, false);
            this.mContext = context;
            this.mScalingThreshold = typedValue.getFloat();
            this.mScaleGestureDetector = new android.view.ScaleGestureDetector(context, this, android.os.Handler.getMain());
            this.mScaleGestureDetector.setQuickScaleEnabled(false);
            this.mScrollGestureDetector = new android.view.GestureDetector(context, this, android.os.Handler.getMain());
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 6 && motionEvent.getPointerCount() == 2 && com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mPreviousState == com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mViewportDraggingState) {
                if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mIsSinglePanningEnabled) {
                    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mOverscrollHandler.setScaleAndCenterToEdgeIfNeeded();
                    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mOverscrollHandler.clearEdgeState();
                }
                persistScaleAndTransitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mViewportDraggingState);
                return;
            }
            if (actionMasked == 1 || actionMasked == 3) {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.onPanningFinished(motionEvent);
                if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mIsSinglePanningEnabled) {
                    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mOverscrollHandler.setScaleAndCenterToEdgeIfNeeded();
                    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mOverscrollHandler.clearEdgeState();
                }
                persistScaleAndTransitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectingState);
            }
        }

        void prepareForState() {
            checkShouldDetectPassPersistedScale();
        }

        private void checkShouldDetectPassPersistedScale() {
            if (this.mDetectingPassPersistedScale) {
                return;
            }
            float scale = com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.getScale(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId);
            float persistedScale = com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.getPersistedScale(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId);
            this.mDetectingPassPersistedScale = java.lang.Math.abs(scale - persistedScale) / persistedScale >= CHECK_DETECTING_PASS_PERSISTED_SCALE_THRESHOLD;
        }

        public void persistScaleAndTransitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State state) {
            if (!com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mIsWatch) {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.persistScale(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId);
            }
            clear();
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(state);
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0040  */
        @com.android.internal.annotations.VisibleForTesting
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        void setScaleAndClearIfNeeded(float f, float f2, float f3) {
            float f4;
            if (this.mDetectingPassPersistedScale) {
                float persistedScale = com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.getPersistedScale(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId);
                if (java.lang.Math.abs(f - persistedScale) / persistedScale < 0.01f) {
                    android.os.Vibrator vibrator = (android.os.Vibrator) this.mContext.getSystemService(android.os.Vibrator.class);
                    if (vibrator != null) {
                        vibrator.vibrate(android.os.VibrationEffect.createPredefined(2));
                    }
                    clear();
                    f4 = persistedScale;
                    if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DEBUG_PANNING_SCALING) {
                        android.util.Slog.i(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mLogTag, "Scaled content to: " + f4 + "x");
                    }
                    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.setScale(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, f4, f2, f3, false, 0);
                    checkShouldDetectPassPersistedScale();
                }
            }
            f4 = f;
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DEBUG_PANNING_SCALING) {
            }
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.setScale(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, f4, f2, f3, false, 0);
            checkShouldDetectPassPersistedScale();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2) {
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mCurrentState != com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mPanningScalingState) {
                return true;
            }
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DEBUG_PANNING_SCALING) {
                android.util.Slog.i(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mLogTag, "Panned content by scrollX: " + f + " scrollY: " + f2);
            }
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.onPan(motionEvent2);
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.offsetMagnifiedRegion(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, f, f2, 0);
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mIsSinglePanningEnabled) {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mOverscrollHandler.onScrollStateChanged(motionEvent, motionEvent2);
            }
            return true;
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(android.view.ScaleGestureDetector scaleGestureDetector) {
            if (!this.mScaling) {
                if (this.mInitialScaleFactor < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    this.mInitialScaleFactor = scaleGestureDetector.getScaleFactor();
                    return false;
                }
                this.mScaling = java.lang.Math.abs(scaleGestureDetector.getScaleFactor() - this.mInitialScaleFactor) > this.mScalingThreshold;
                return this.mScaling;
            }
            float scale = com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.getScale(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId);
            float scaleFactor = scaleGestureDetector.getScaleFactor() * scale;
            if (scaleFactor > 8.0f && scaleFactor > scale) {
                scaleFactor = 8.0f;
            } else if (scaleFactor < 1.0f && scaleFactor < scale) {
                scaleFactor = 1.0f;
            }
            setScaleAndClearIfNeeded(scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
            return true;
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(android.view.ScaleGestureDetector scaleGestureDetector) {
            return com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mCurrentState == com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mPanningScalingState;
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(android.view.ScaleGestureDetector scaleGestureDetector) {
            clear();
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public void clear() {
            this.mInitialScaleFactor = -1.0f;
            this.mScaling = false;
            this.mDetectingPassPersistedScale = false;
        }

        public java.lang.String toString() {
            return "PanningScalingState{mInitialScaleFactor=" + this.mInitialScaleFactor + ", mScaling=" + this.mScaling + '}';
        }
    }

    final class ViewportDraggingStateWithMultiFinger extends com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.ViewportDraggingState {
        ViewportDraggingStateWithMultiFinger() {
            super();
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.ViewportDraggingState, com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) throws com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.GestureException {
            int actionMasked = motionEvent.getActionMasked();
            switch (actionMasked) {
                case 0:
                    throw new com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.GestureException("Unexpected event type: " + android.view.MotionEvent.actionToString(actionMasked));
                case 1:
                case 3:
                    if (this.mScaleToRecoverAfterDraggingEnd >= 1.0f) {
                        com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.zoomToScale(this.mScaleToRecoverAfterDraggingEnd, motionEvent.getX(), motionEvent.getY());
                    } else {
                        com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.zoomOff();
                    }
                    clear();
                    this.mScaleToRecoverAfterDraggingEnd = Float.NaN;
                    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectingState);
                    return;
                case 2:
                    if (motionEvent.getPointerCount() > 2) {
                        throw new com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.GestureException("Should have one pointer down.");
                    }
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.magnificationRegionContains(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, x, y)) {
                        com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.setCenter(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, x, y, this.mLastMoveOutsideMagnifiedRegion, 0);
                        this.mLastMoveOutsideMagnifiedRegion = false;
                        return;
                    } else {
                        this.mLastMoveOutsideMagnifiedRegion = true;
                        return;
                    }
                case 4:
                default:
                    return;
                case 5:
                    clearAndTransitToPanningScalingState();
                    return;
            }
        }
    }

    class ViewportDraggingState implements com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State {
        protected boolean mLastMoveOutsideMagnifiedRegion;

        @com.android.internal.annotations.VisibleForTesting
        protected float mScaleToRecoverAfterDraggingEnd = Float.NaN;

        ViewportDraggingState() {
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) throws com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.GestureException {
            int actionMasked = motionEvent.getActionMasked();
            switch (actionMasked) {
                case 0:
                case 6:
                    throw new com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.GestureException("Unexpected event type: " + android.view.MotionEvent.actionToString(actionMasked));
                case 1:
                case 3:
                    if (this.mScaleToRecoverAfterDraggingEnd >= 1.0f) {
                        com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.zoomToScale(this.mScaleToRecoverAfterDraggingEnd, motionEvent.getX(), motionEvent.getY());
                    } else {
                        com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.zoomOff();
                    }
                    clear();
                    this.mScaleToRecoverAfterDraggingEnd = Float.NaN;
                    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectingState);
                    return;
                case 2:
                    if (motionEvent.getPointerCount() != 1) {
                        throw new com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.GestureException("Should have one pointer down.");
                    }
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.magnificationRegionContains(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, x, y)) {
                        com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.setCenter(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, x, y, this.mLastMoveOutsideMagnifiedRegion, 0);
                        this.mLastMoveOutsideMagnifiedRegion = false;
                        return;
                    } else {
                        this.mLastMoveOutsideMagnifiedRegion = true;
                        return;
                    }
                case 4:
                default:
                    return;
                case 5:
                    clearAndTransitToPanningScalingState();
                    return;
            }
        }

        private boolean isAlwaysOnMagnificationEnabled() {
            return com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.isAlwaysOnMagnificationEnabled();
        }

        public void prepareForZoomInTemporary(boolean z) {
            boolean z2;
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.isActivated(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId)) {
                if (z) {
                    z2 = isAlwaysOnMagnificationEnabled();
                } else {
                    z2 = true;
                }
            } else {
                z2 = false;
            }
            this.mScaleToRecoverAfterDraggingEnd = z2 ? com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.getScale(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId) : Float.NaN;
        }

        protected void clearAndTransitToPanningScalingState() {
            float f = this.mScaleToRecoverAfterDraggingEnd;
            clear();
            this.mScaleToRecoverAfterDraggingEnd = f;
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mPanningScalingState);
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public void clear() {
            this.mLastMoveOutsideMagnifiedRegion = false;
            this.mScaleToRecoverAfterDraggingEnd = Float.NaN;
        }

        public java.lang.String toString() {
            return "ViewportDraggingState{mScaleToRecoverAfterDraggingEnd=" + this.mScaleToRecoverAfterDraggingEnd + ", mLastMoveOutsideMagnifiedRegion=" + this.mLastMoveOutsideMagnifiedRegion + '}';
        }
    }

    final class DelegatingState implements com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State {
        public long mLastDelegatedDownEventTime;

        DelegatingState() {
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
            switch (motionEvent.getActionMasked()) {
                case 0:
                    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDelegatingState);
                    this.mLastDelegatedDownEventTime = motionEvent.getDownTime();
                    break;
                case 1:
                case 3:
                    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectingState);
                    break;
            }
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.getNext() != null) {
                motionEvent.setDownTime(this.mLastDelegatedDownEventTime);
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.dispatchTransformedEvent(motionEvent, motionEvent2, i);
            }
        }
    }

    final class DetectingStateWithMultiFinger extends com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DetectingState {
        private static final int TWO_FINGER_GESTURE_MAX_TAPS = 2;
        private int mCompletedTapCount;
        private boolean mIsTwoFingerCountReached;

        DetectingStateWithMultiFinger(android.content.Context context) {
            super(context);
            this.mIsTwoFingerCountReached = false;
            this.mCompletedTapCount = 0;
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DetectingState, com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
            cacheDelayedMotionEvent(motionEvent, motionEvent2, i);
            switch (motionEvent.getActionMasked()) {
                case 0:
                    this.mLastDetectingDownEventTime = motionEvent.getDownTime();
                    this.mHandler.removeMessages(2);
                    this.mFirstPointerDownLocation.set(motionEvent.getX(), motionEvent.getY());
                    if (!com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.magnificationRegionContains(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, motionEvent.getX(), motionEvent.getY())) {
                        transitionToDelegatingStateAndClear();
                        break;
                    } else if (isMultiTapTriggered(2)) {
                        afterLongTapTimeoutTransitionToDraggingState(motionEvent);
                        break;
                    } else if (isTapOutOfDistanceSlop()) {
                        transitionToDelegatingStateAndClear();
                        break;
                    } else if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectSingleFingerTripleTap || com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectTwoFingerTripleTap || isActivated()) {
                        afterMultiTapTimeoutTransitionToDelegatingState();
                        break;
                    } else {
                        transitionToDelegatingStateAndClear();
                        break;
                    }
                    break;
                case 1:
                    this.mHandler.removeMessages(1);
                    this.mHandler.removeMessages(3);
                    if (!com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.magnificationRegionContains(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, motionEvent.getX(), motionEvent.getY())) {
                        transitionToDelegatingStateAndClear();
                        break;
                    } else if (isMultiFingerMultiTapTriggered(2, motionEvent)) {
                        onTripleTap(motionEvent);
                        break;
                    } else if (isMultiTapTriggered(3)) {
                        onTripleTap(motionEvent);
                        break;
                    } else if (isFingerDown()) {
                        if ((timeBetween(this.mLastDown, this.mLastUp) >= this.mLongTapMinDelay || com.android.server.accessibility.gestures.GestureUtils.distance(this.mLastDown, this.mLastUp) >= this.mSwipeMinDistance) && this.mCompletedTapCount == 0) {
                            transitionToDelegatingStateAndClear();
                            break;
                        }
                    }
                    break;
                case 2:
                    if (isFingerDown() && com.android.server.accessibility.gestures.GestureUtils.distance(this.mLastDown, motionEvent) > this.mSwipeMinDistance) {
                        if (isMultiTapTriggered(2) && motionEvent.getPointerCount() == 1) {
                            transitionToViewportDraggingStateAndClear(motionEvent);
                            break;
                        } else if (isMultiFingerMultiTapTriggered(1, motionEvent) && motionEvent.getPointerCount() == 2) {
                            transitionToViewportDraggingStateAndClear(motionEvent);
                            break;
                        } else if (isActivated() && motionEvent.getPointerCount() == 2) {
                            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mIsSinglePanningEnabled && com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.overscrollState(motionEvent, this.mFirstPointerDownLocation) == 3) {
                                transitionToDelegatingStateAndClear();
                            }
                            transitToPanningScalingStateAndClear();
                            break;
                        } else if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mIsSinglePanningEnabled && isActivated() && motionEvent.getPointerCount() == 1) {
                            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.overscrollState(motionEvent, this.mFirstPointerDownLocation) == 3) {
                                transitionToDelegatingStateAndClear();
                            }
                            transitToSinglePanningStateAndClear();
                            break;
                        } else if (!this.mIsTwoFingerCountReached) {
                            transitionToDelegatingStateAndClear();
                            break;
                        }
                    } else if (isActivated() && pointerDownValid(this.mSecondPointerDownLocation) && com.android.server.accessibility.gestures.GestureUtils.distanceClosestPointerToPoint(this.mSecondPointerDownLocation, motionEvent) > this.mSwipeMinDistance) {
                        storePointerDownLocation(this.mSecondPointerDownLocation, motionEvent);
                        this.mHandler.sendEmptyMessageDelayed(3, android.view.ViewConfiguration.getTapTimeout());
                        break;
                    }
                    break;
                case 5:
                    this.mIsTwoFingerCountReached = com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectTwoFingerTripleTap && motionEvent.getPointerCount() == 2;
                    this.mHandler.removeMessages(2);
                    if (motionEvent.getPointerCount() == 2) {
                        if (isMultiFingerMultiTapTriggered(1, motionEvent)) {
                            afterLongTapTimeoutTransitionToDraggingState(motionEvent);
                            break;
                        } else {
                            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectTwoFingerTripleTap) {
                                afterMultiTapTimeoutTransitionToDelegatingState();
                            }
                            if (isActivated()) {
                                storePointerDownLocation(this.mSecondPointerDownLocation, motionEvent);
                                this.mHandler.sendEmptyMessageDelayed(3, android.view.ViewConfiguration.getTapTimeout());
                                break;
                            }
                        }
                    } else {
                        transitionToDelegatingStateAndClear();
                        break;
                    }
                    break;
                case 6:
                    if (!this.mIsTwoFingerCountReached) {
                        transitionToDelegatingStateAndClear();
                        break;
                    }
                    break;
            }
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DetectingState, com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public void clear() {
            this.mCompletedTapCount = 0;
            setShortcutTriggered(false);
            removePendingDelayedMessages();
            clearDelayedMotionEvents();
            this.mFirstPointerDownLocation.set(Float.NaN, Float.NaN);
            this.mSecondPointerDownLocation.set(Float.NaN, Float.NaN);
        }

        private boolean isMultiFingerMultiTapTriggered(int i, android.view.MotionEvent motionEvent) {
            if (motionEvent.getActionMasked() == 1 && this.mIsTwoFingerCountReached) {
                this.mCompletedTapCount++;
                this.mIsTwoFingerCountReached = false;
            }
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectTwoFingerTripleTap && this.mCompletedTapCount > 1) {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mMagnificationLogger.logMagnificationTwoFingerTripleTap(!isActivated());
            }
            return com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectTwoFingerTripleTap && this.mCompletedTapCount == i;
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DetectingState
        void transitionToDelegatingStateAndClear() {
            this.mCompletedTapCount = 0;
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDelegatingState);
            sendDelayedMotionEvents();
            removePendingDelayedMessages();
            this.mFirstPointerDownLocation.set(Float.NaN, Float.NaN);
            this.mSecondPointerDownLocation.set(Float.NaN, Float.NaN);
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DetectingState
        void transitionToViewportDraggingStateAndClear(android.view.MotionEvent motionEvent) {
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DEBUG_DETECTING) {
                android.util.Slog.i(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mLogTag, "onTripleTapAndHold()");
            }
            boolean z = this.mShortcutTriggered;
            if (!z) {
                boolean z2 = !isActivated();
                if (this.mCompletedTapCount == 1) {
                    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mMagnificationLogger.logMagnificationTwoFingerTripleTap(z2);
                } else {
                    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mMagnificationLogger.logMagnificationTripleTap(z2);
                }
            }
            clear();
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mViewportDraggingState.prepareForZoomInTemporary(z);
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.zoomInTemporary(motionEvent.getX(), motionEvent.getY(), z);
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mViewportDraggingState);
        }
    }

    class DetectingState implements com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State, android.os.Handler.Callback {
        protected static final int MESSAGE_ON_TRIPLE_TAP_AND_HOLD = 1;
        protected static final int MESSAGE_TRANSITION_TO_DELEGATING_STATE = 2;
        protected static final int MESSAGE_TRANSITION_TO_PANNINGSCALING_STATE = 3;
        protected com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo mDelayedEventQueue;
        protected long mLastDetectingDownEventTime;
        protected android.view.MotionEvent mLastDown;
        protected android.view.MotionEvent mLastUp;
        final int mMultiTapMaxDelay;
        final int mMultiTapMaxDistance;
        protected android.view.MotionEvent mPreLastDown;
        protected android.view.MotionEvent mPreLastUp;

        @com.android.internal.annotations.VisibleForTesting
        boolean mShortcutTriggered;
        final int mSwipeMinDistance;
        protected android.graphics.PointF mFirstPointerDownLocation = new android.graphics.PointF(Float.NaN, Float.NaN);
        protected android.graphics.PointF mSecondPointerDownLocation = new android.graphics.PointF(Float.NaN, Float.NaN);

        @com.android.internal.annotations.VisibleForTesting
        android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), this);
        final int mLongTapMinDelay = android.view.ViewConfiguration.getLongPressTimeout();

        DetectingState(android.content.Context context) {
            this.mMultiTapMaxDelay = android.view.ViewConfiguration.getDoubleTapTimeout() + context.getResources().getInteger(android.R.integer.config_screenBrightnessCapForWearBedtimeMode);
            this.mSwipeMinDistance = android.view.ViewConfiguration.get(context).getScaledTouchSlop();
            this.mMultiTapMaxDistance = android.view.ViewConfiguration.get(context).getScaledDoubleTapSlop();
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(android.os.Message message) {
            int i = message.what;
            switch (i) {
                case 1:
                    android.view.MotionEvent motionEvent = (android.view.MotionEvent) message.obj;
                    transitionToViewportDraggingStateAndClear(motionEvent);
                    motionEvent.recycle();
                    return true;
                case 2:
                    transitionToDelegatingStateAndClear();
                    return true;
                case 3:
                    transitToPanningScalingStateAndClear();
                    return true;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown message type: " + i);
            }
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
            cacheDelayedMotionEvent(motionEvent, motionEvent2, i);
            switch (motionEvent.getActionMasked()) {
                case 0:
                    this.mLastDetectingDownEventTime = motionEvent.getDownTime();
                    this.mHandler.removeMessages(2);
                    this.mFirstPointerDownLocation.set(motionEvent.getX(), motionEvent.getY());
                    if (!com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.magnificationRegionContains(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, motionEvent.getX(), motionEvent.getY())) {
                        transitionToDelegatingStateAndClear();
                        break;
                    } else if (isMultiTapTriggered(2)) {
                        afterLongTapTimeoutTransitionToDraggingState(motionEvent);
                        break;
                    } else if (isTapOutOfDistanceSlop()) {
                        transitionToDelegatingStateAndClear();
                        break;
                    } else if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectSingleFingerTripleTap || isActivated()) {
                        afterMultiTapTimeoutTransitionToDelegatingState();
                        break;
                    } else {
                        transitionToDelegatingStateAndClear();
                        break;
                    }
                    break;
                case 1:
                    this.mHandler.removeMessages(1);
                    if (!com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.magnificationRegionContains(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, motionEvent.getX(), motionEvent.getY())) {
                        transitionToDelegatingStateAndClear();
                        break;
                    } else if (isMultiTapTriggered(3)) {
                        onTripleTap(motionEvent);
                        break;
                    } else if (isFingerDown()) {
                        if (timeBetween(this.mLastDown, this.mLastUp) >= this.mLongTapMinDelay || com.android.server.accessibility.gestures.GestureUtils.distance(this.mLastDown, this.mLastUp) >= this.mSwipeMinDistance) {
                            transitionToDelegatingStateAndClear();
                            break;
                        }
                    }
                    break;
                case 2:
                    if (isFingerDown() && com.android.server.accessibility.gestures.GestureUtils.distance(this.mLastDown, motionEvent) > this.mSwipeMinDistance) {
                        if (isMultiTapTriggered(2) && motionEvent.getPointerCount() == 1) {
                            transitionToViewportDraggingStateAndClear(motionEvent);
                            break;
                        } else if (isActivated() && motionEvent.getPointerCount() == 2) {
                            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mIsSinglePanningEnabled && com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.overscrollState(motionEvent, this.mFirstPointerDownLocation) == 3) {
                                transitionToDelegatingStateAndClear();
                            }
                            transitToPanningScalingStateAndClear();
                            break;
                        } else if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mIsSinglePanningEnabled && isActivated() && motionEvent.getPointerCount() == 1) {
                            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.overscrollState(motionEvent, this.mFirstPointerDownLocation) == 3) {
                                transitionToDelegatingStateAndClear();
                            }
                            transitToSinglePanningStateAndClear();
                            break;
                        } else {
                            transitionToDelegatingStateAndClear();
                            break;
                        }
                    } else if (isActivated() && pointerDownValid(this.mSecondPointerDownLocation) && com.android.server.accessibility.gestures.GestureUtils.distanceClosestPointerToPoint(this.mSecondPointerDownLocation, motionEvent) > this.mSwipeMinDistance) {
                        transitToPanningScalingStateAndClear();
                        break;
                    }
                    break;
                case 5:
                    if (isActivated() && motionEvent.getPointerCount() == 2) {
                        storePointerDownLocation(this.mSecondPointerDownLocation, motionEvent);
                        this.mHandler.sendEmptyMessageDelayed(3, android.view.ViewConfiguration.getTapTimeout());
                        break;
                    } else {
                        transitionToDelegatingStateAndClear();
                        break;
                    }
                case 6:
                    transitionToDelegatingStateAndClear();
                    break;
            }
        }

        protected void storePointerDownLocation(android.graphics.PointF pointF, android.view.MotionEvent motionEvent) {
            int actionIndex = motionEvent.getActionIndex();
            pointF.set(motionEvent.getX(actionIndex), motionEvent.getY(actionIndex));
        }

        protected boolean pointerDownValid(android.graphics.PointF pointF) {
            return (java.lang.Float.isNaN(pointF.x) && java.lang.Float.isNaN(pointF.y)) ? false : true;
        }

        protected void transitToPanningScalingStateAndClear() {
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mPanningScalingState);
            clear();
        }

        protected void transitToSinglePanningStateAndClear() {
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mSinglePanningState);
            clear();
        }

        public boolean isMultiTapTriggered(int i) {
            boolean z = false;
            if (this.mShortcutTriggered) {
                return tapCount() + 2 >= i;
            }
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectSingleFingerTripleTap && tapCount() >= i && isMultiTap(this.mPreLastDown, this.mLastDown) && isMultiTap(this.mPreLastUp, this.mLastUp)) {
                z = true;
            }
            if (z && i > 2) {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mMagnificationLogger.logMagnificationTripleTap(!isActivated());
            }
            return z;
        }

        private boolean isMultiTap(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2) {
            return com.android.server.accessibility.gestures.GestureUtils.isMultiTap(motionEvent, motionEvent2, this.mMultiTapMaxDelay, this.mMultiTapMaxDistance);
        }

        public boolean isFingerDown() {
            return this.mLastDown != null;
        }

        protected long timeBetween(@android.annotation.Nullable android.view.MotionEvent motionEvent, @android.annotation.Nullable android.view.MotionEvent motionEvent2) {
            if (motionEvent == null && motionEvent2 == null) {
                return 0L;
            }
            return java.lang.Math.abs(timeOf(motionEvent) - timeOf(motionEvent2));
        }

        private long timeOf(@android.annotation.Nullable android.view.MotionEvent motionEvent) {
            if (motionEvent != null) {
                return motionEvent.getEventTime();
            }
            return Long.MIN_VALUE;
        }

        public int tapCount() {
            return com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo.countOf(this.mDelayedEventQueue, 1);
        }

        public void afterMultiTapTimeoutTransitionToDelegatingState() {
            this.mHandler.sendEmptyMessageDelayed(2, this.mMultiTapMaxDelay);
        }

        public void afterLongTapTimeoutTransitionToDraggingState(android.view.MotionEvent motionEvent) {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, android.view.MotionEvent.obtain(motionEvent)), android.view.ViewConfiguration.getLongPressTimeout());
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public void clear() {
            setShortcutTriggered(false);
            removePendingDelayedMessages();
            clearDelayedMotionEvents();
            this.mFirstPointerDownLocation.set(Float.NaN, Float.NaN);
            this.mSecondPointerDownLocation.set(Float.NaN, Float.NaN);
        }

        protected void removePendingDelayedMessages() {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
        }

        protected void cacheDelayedMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
            if (motionEvent.getActionMasked() == 0) {
                this.mPreLastDown = this.mLastDown;
                this.mLastDown = android.view.MotionEvent.obtain(motionEvent);
            } else if (motionEvent.getActionMasked() == 1) {
                this.mPreLastUp = this.mLastUp;
                this.mLastUp = android.view.MotionEvent.obtain(motionEvent);
            }
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo obtain = com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo.obtain(motionEvent, motionEvent2, i);
            if (this.mDelayedEventQueue == null) {
                this.mDelayedEventQueue = obtain;
                return;
            }
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo motionEventInfo = this.mDelayedEventQueue;
            while (motionEventInfo.mNext != null) {
                motionEventInfo = motionEventInfo.mNext;
            }
            motionEventInfo.mNext = obtain;
        }

        protected void sendDelayedMotionEvents() {
            if (this.mDelayedEventQueue == null) {
                return;
            }
            long min = java.lang.Math.min(android.os.SystemClock.uptimeMillis() - this.mLastDetectingDownEventTime, this.mMultiTapMaxDelay);
            do {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo motionEventInfo = this.mDelayedEventQueue;
                this.mDelayedEventQueue = motionEventInfo.mNext;
                motionEventInfo.event.setDownTime(motionEventInfo.event.getDownTime() + min);
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.handleEventWith(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDelegatingState, motionEventInfo.event, motionEventInfo.rawEvent, motionEventInfo.policyFlags);
                motionEventInfo.recycle();
            } while (this.mDelayedEventQueue != null);
        }

        protected void clearDelayedMotionEvents() {
            while (this.mDelayedEventQueue != null) {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo motionEventInfo = this.mDelayedEventQueue;
                this.mDelayedEventQueue = motionEventInfo.mNext;
                motionEventInfo.recycle();
            }
            this.mPreLastDown = null;
            this.mPreLastUp = null;
            this.mLastDown = null;
            this.mLastUp = null;
        }

        void transitionToDelegatingStateAndClear() {
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDelegatingState);
            sendDelayedMotionEvents();
            removePendingDelayedMessages();
            this.mSecondPointerDownLocation.set(Float.NaN, Float.NaN);
        }

        protected void onTripleTap(android.view.MotionEvent motionEvent) {
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DEBUG_DETECTING) {
                android.util.Slog.i(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mLogTag, "onTripleTap(); delayed: " + com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo.toString(this.mDelayedEventQueue));
            }
            if (!isActivated() || this.mShortcutTriggered) {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mPromptController.showNotificationIfNeeded();
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.zoomOn(motionEvent.getX(), motionEvent.getY());
            } else {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.zoomOff();
            }
            clear();
        }

        protected boolean isActivated() {
            return com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.isActivated(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId);
        }

        void transitionToViewportDraggingStateAndClear(android.view.MotionEvent motionEvent) {
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DEBUG_DETECTING) {
                android.util.Slog.i(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mLogTag, "onTripleTapAndHold()");
            }
            boolean z = this.mShortcutTriggered;
            if (!z) {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mMagnificationLogger.logMagnificationTripleTap(!isActivated());
            }
            clear();
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mViewportDraggingState.prepareForZoomInTemporary(z);
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.zoomInTemporary(motionEvent.getX(), motionEvent.getY(), z);
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mViewportDraggingState);
        }

        public java.lang.String toString() {
            return "DetectingState{tapCount()=" + tapCount() + ", mShortcutTriggered=" + this.mShortcutTriggered + ", mDelayedEventQueue=" + com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo.toString(this.mDelayedEventQueue) + '}';
        }

        void toggleShortcutTriggered() {
            setShortcutTriggered(!this.mShortcutTriggered);
        }

        void setShortcutTriggered(boolean z) {
            if (this.mShortcutTriggered == z) {
                return;
            }
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DEBUG_DETECTING) {
                android.util.Slog.i(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mLogTag, "setShortcutTriggered(" + z + ")");
            }
            this.mShortcutTriggered = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isShortcutTriggered() {
            return this.mShortcutTriggered;
        }

        boolean isTapOutOfDistanceSlop() {
            if (!com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectSingleFingerTripleTap || this.mPreLastDown == null || this.mLastDown == null) {
                return false;
            }
            boolean z = com.android.server.accessibility.gestures.GestureUtils.distance(this.mPreLastDown, this.mLastDown) > ((double) this.mMultiTapMaxDistance);
            if (tapCount() > 0) {
                return z;
            }
            return z && !com.android.server.accessibility.gestures.GestureUtils.isTimedOut(this.mPreLastDown, this.mLastDown, this.mMultiTapMaxDelay);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zoomInTemporary(float f, float f2, boolean z) {
        float scale = this.mFullScreenMagnificationController.getScale(this.mDisplayId);
        float constrain = android.util.MathUtils.constrain(this.mFullScreenMagnificationController.getPersistedScale(this.mDisplayId), 1.0f, 8.0f);
        if (!(!this.mFullScreenMagnificationController.isActivated(this.mDisplayId) || z || this.mFullScreenMagnificationController.isZoomedOutFromService(this.mDisplayId))) {
            constrain = scale + 1.0f;
        }
        zoomToScale(constrain, f, f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zoomOn(float f, float f2) {
        if (DEBUG_DETECTING) {
            android.util.Slog.i(this.mLogTag, "zoomOn(" + f + ", " + f2 + ")");
        }
        zoomToScale(android.util.MathUtils.constrain(this.mFullScreenMagnificationController.getPersistedScale(this.mDisplayId), 1.0f, 8.0f), f, f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zoomToScale(float f, float f2, float f3) {
        this.mFullScreenMagnificationController.setScaleAndCenter(this.mDisplayId, android.util.MathUtils.constrain(f, 1.0f, 8.0f), f2, f3, true, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zoomOff() {
        if (DEBUG_DETECTING) {
            android.util.Slog.i(this.mLogTag, "zoomOff()");
        }
        this.mFullScreenMagnificationController.reset(this.mDisplayId, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.view.MotionEvent recycleAndNullify(@android.annotation.Nullable android.view.MotionEvent motionEvent) {
        if (motionEvent != null) {
            motionEvent.recycle();
            return null;
        }
        return null;
    }

    public java.lang.String toString() {
        return "MagnificationGesture{mDetectingState=" + this.mDetectingState + ", mDelegatingState=" + this.mDelegatingState + ", mMagnifiedInteractionState=" + this.mPanningScalingState + ", mViewportDraggingState=" + this.mViewportDraggingState + ", mSinglePanningState=" + this.mSinglePanningState + ", mDetectSingleFingerTripleTap=" + this.mDetectSingleFingerTripleTap + ", mDetectShortcutTrigger=" + this.mDetectShortcutTrigger + ", mCurrentState=" + com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State.nameOf(this.mCurrentState) + ", mPreviousState=" + com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State.nameOf(this.mPreviousState) + ", mMagnificationController=" + this.mFullScreenMagnificationController + ", mDisplayId=" + this.mDisplayId + ", mIsSinglePanningEnabled=" + this.mIsSinglePanningEnabled + ", mOverscrollHandler=" + this.mOverscrollHandler + '}';
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int overscrollState(android.view.MotionEvent motionEvent, android.graphics.PointF pointF) {
        if (!pointerValid(pointF)) {
            return 0;
        }
        float x = motionEvent.getX() - pointF.x;
        float y = motionEvent.getY() - pointF.y;
        if (this.mFullScreenMagnificationController.isAtLeftEdge(this.mDisplayId) && x > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return 1;
        }
        if (this.mFullScreenMagnificationController.isAtRightEdge(this.mDisplayId) && x < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return 2;
        }
        if (!this.mFullScreenMagnificationController.isAtTopEdge(this.mDisplayId) || y <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return (!this.mFullScreenMagnificationController.isAtBottomEdge(this.mDisplayId) || y >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) ? 0 : 3;
        }
        return 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean pointerValid(android.graphics.PointF pointF) {
        return (java.lang.Float.isNaN(pointF.x) && java.lang.Float.isNaN(pointF.y)) ? false : true;
    }

    private static final class MotionEventInfo {
        private static final int MAX_POOL_SIZE = 10;
        private static final java.lang.Object sLock = new java.lang.Object();
        private static com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo sPool;
        private static int sPoolSize;
        public android.view.MotionEvent event;
        private boolean mInPool;
        private com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo mNext;
        public int policyFlags;
        public android.view.MotionEvent rawEvent;

        private MotionEventInfo() {
        }

        public static com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo obtain(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo obtainInternal;
            synchronized (sLock) {
                obtainInternal = obtainInternal();
                obtainInternal.initialize(motionEvent, motionEvent2, i);
            }
            return obtainInternal;
        }

        @android.annotation.NonNull
        private static com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo obtainInternal() {
            if (sPoolSize > 0) {
                sPoolSize--;
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo motionEventInfo = sPool;
                sPool = motionEventInfo.mNext;
                motionEventInfo.mNext = null;
                motionEventInfo.mInPool = false;
                return motionEventInfo;
            }
            return new com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo();
        }

        private void initialize(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
            this.event = android.view.MotionEvent.obtain(motionEvent);
            this.rawEvent = android.view.MotionEvent.obtain(motionEvent2);
            this.policyFlags = i;
        }

        public void recycle() {
            synchronized (sLock) {
                try {
                    if (this.mInPool) {
                        throw new java.lang.IllegalStateException("Already recycled.");
                    }
                    clear();
                    if (sPoolSize < 10) {
                        sPoolSize++;
                        this.mNext = sPool;
                        sPool = this;
                        this.mInPool = true;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void clear() {
            this.event = com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.recycleAndNullify(this.event);
            this.rawEvent = com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.recycleAndNullify(this.rawEvent);
            this.policyFlags = 0;
        }

        static int countOf(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo motionEventInfo, int i) {
            if (motionEventInfo == null) {
                return 0;
            }
            return (motionEventInfo.event.getAction() == i ? 1 : 0) + countOf(motionEventInfo.mNext, i);
        }

        public static java.lang.String toString(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo motionEventInfo) {
            if (motionEventInfo == null) {
                return "";
            }
            return android.view.MotionEvent.actionToString(motionEventInfo.event.getAction()).replace("ACTION_", "") + " " + toString(motionEventInfo.mNext);
        }
    }

    private static class ScreenStateReceiver extends android.content.BroadcastReceiver {
        private final android.content.Context mContext;
        private final com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler mGestureHandler;

        ScreenStateReceiver(android.content.Context context, com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler) {
            this.mContext = context;
            this.mGestureHandler = fullScreenMagnificationGestureHandler;
        }

        public void register() {
            this.mContext.registerReceiver(this, new android.content.IntentFilter("android.intent.action.SCREEN_OFF"));
        }

        public void unregister() {
            this.mContext.unregisterReceiver(this);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            this.mGestureHandler.mDetectingState.setShortcutTriggered(false);
        }
    }

    private static class GestureException extends java.lang.Exception {
        GestureException(java.lang.String str) {
            super(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPan(android.view.MotionEvent motionEvent) {
        com.android.server.accessibility.Flags.fullscreenFlingGesture();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPanningFinished(android.view.MotionEvent motionEvent) {
        com.android.server.accessibility.Flags.fullscreenFlingGesture();
    }

    private void cancelFling() {
        com.android.server.accessibility.Flags.fullscreenFlingGesture();
    }

    final class SinglePanningState extends android.view.GestureDetector.SimpleOnGestureListener implements com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State {
        private com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MotionEventInfo mEvent;
        private final android.view.GestureDetector mScrollGestureDetector;

        SinglePanningState(android.content.Context context) {
            this.mScrollGestureDetector = new android.view.GestureDetector(context, this, android.os.Handler.getMain());
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
            switch (motionEvent.getActionMasked()) {
                case 1:
                    com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.onPanningFinished(motionEvent);
                    break;
                case 2:
                default:
                    return;
                case 3:
                    break;
            }
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mOverscrollHandler.setScaleAndCenterToEdgeIfNeeded();
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mOverscrollHandler.clearEdgeState();
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDetectingState);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2) {
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mCurrentState != com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mSinglePanningState) {
                return true;
            }
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.onPan(motionEvent2);
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.offsetMagnifiedRegion(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, f, f2, 0);
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DEBUG_PANNING_SCALING) {
                android.util.Slog.i(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mLogTag, "SinglePanningState Panned content by scrollX: " + f + " scrollY: " + f2 + " isAtEdge: " + com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.isAtEdge(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId));
            }
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mOverscrollHandler.onScrollStateChanged(motionEvent, motionEvent2);
            return true;
        }

        private void vibrateIfNeeded() {
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.isAtLeftEdge(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId) || com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.isAtRightEdge(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId)) {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationVibrationHelper.vibrateIfSettingEnabled();
            }
        }

        public java.lang.String toString() {
            return "SinglePanningState{isEdgeOfView=" + com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.isAtEdge(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId);
        }
    }

    final class OverscrollHandler {

        @com.android.internal.annotations.VisibleForTesting
        int mOverscrollState = 0;
        private final android.graphics.PointF mPivotEdge = new android.graphics.PointF(Float.NaN, Float.NaN);
        private final android.graphics.PointF mReachedEdgeCoord = new android.graphics.PointF(Float.NaN, Float.NaN);
        private boolean mEdgeCooldown = false;

        OverscrollHandler() {
        }

        protected boolean warpEffectReset(android.view.MotionEvent motionEvent) {
            float calculateOverscrollScale = calculateOverscrollScale(motionEvent);
            if (calculateOverscrollScale < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                return false;
            }
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.setScaleAndCenter(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, calculateOverscrollScale, this.mPivotEdge.x, this.mPivotEdge.y, true, 0);
            return calculateOverscrollScale == 1.0f;
        }

        private float calculateOverscrollScale(android.view.MotionEvent motionEvent) {
            float x = motionEvent.getX() - this.mReachedEdgeCoord.x;
            if ((this.mOverscrollState == 1 && x < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) || (this.mOverscrollState == 2 && x > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE)) {
                clearEdgeState();
                return -1.0f;
            }
            float hypot = (float) java.lang.Math.hypot(java.lang.Math.abs(x), java.lang.Math.abs(motionEvent.getY() - this.mReachedEdgeCoord.y));
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.getMagnificationBounds(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, new android.graphics.Rect());
            float width = hypot / r0.width();
            float width2 = r0.width() * 0.05f;
            if (this.mEdgeCooldown && hypot > width2) {
                this.mEdgeCooldown = false;
            }
            return android.util.MathUtils.constrain((1.0f - width) * getSensitivityScale(), 1.0f, com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.getPersistedScale(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId));
        }

        private float getSensitivityScale() {
            float persistedScale = com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.getPersistedScale(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId);
            float f = 1.0f;
            if (persistedScale >= 1.7f && persistedScale >= 2.0f) {
                if (persistedScale < 2.2f) {
                    f = 0.95f;
                } else if (persistedScale < 2.5f) {
                    f = 1.1f;
                } else if (persistedScale < 2.7f) {
                    f = 1.3f;
                }
            }
            return persistedScale * f;
        }

        private void vibrateIfNeeded(android.view.MotionEvent motionEvent) {
            if (this.mOverscrollState != 0) {
                return;
            }
            if ((com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.isAtLeftEdge(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId) || com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.isAtRightEdge(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId)) && !this.mEdgeCooldown) {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationVibrationHelper.vibrateIfSettingEnabled();
            }
        }

        private void setPivotEdge(android.view.MotionEvent motionEvent) {
            if (!com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.pointerValid(this.mPivotEdge)) {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.getMagnificationBounds(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, new android.graphics.Rect());
                if (this.mOverscrollState == 1) {
                    this.mPivotEdge.set(r0.left, com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.getCenterY(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId));
                } else if (this.mOverscrollState == 2) {
                    this.mPivotEdge.set(r0.right, com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.getCenterY(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId));
                }
                this.mReachedEdgeCoord.set(motionEvent.getX(), motionEvent.getY());
                this.mEdgeCooldown = true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onScrollStateChanged(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2) {
            if (com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.isAtEdge(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId)) {
                vibrateIfNeeded(motionEvent2);
                setPivotEdge(motionEvent2);
            }
            switch (this.mOverscrollState) {
                case 0:
                    onNoOverscroll(motionEvent, motionEvent2);
                    break;
                case 1:
                case 2:
                    onHorizontalOverscroll(motionEvent2);
                    break;
                case 3:
                    onVerticalOverscroll();
                    break;
                default:
                    android.util.Slog.d(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mLogTag, "Invalid overscroll state");
                    break;
            }
        }

        public void onNoOverscroll(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2) {
            this.mOverscrollState = com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.overscrollState(motionEvent2, new android.graphics.PointF(motionEvent.getX(), motionEvent.getY()));
        }

        public void onVerticalOverscroll() {
            clearEdgeState();
            com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDelegatingState);
        }

        public void onHorizontalOverscroll(android.view.MotionEvent motionEvent) {
            if (warpEffectReset(motionEvent)) {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.reset(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, true);
                clearEdgeState();
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.transitionTo(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDelegatingState);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setScaleAndCenterToEdgeIfNeeded() {
            if (this.mOverscrollState == 1 || this.mOverscrollState == 2) {
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.setScaleAndCenter(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId, com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mFullScreenMagnificationController.getPersistedScale(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.this.mDisplayId), this.mPivotEdge.x, this.mPivotEdge.y, true, 0);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearEdgeState() {
            this.mOverscrollState = 0;
            this.mPivotEdge.set(Float.NaN, Float.NaN);
            this.mReachedEdgeCoord.set(Float.NaN, Float.NaN);
            this.mEdgeCooldown = false;
        }

        public java.lang.String toString() {
            return "OverscrollHandler {mOverscrollState=" + this.mOverscrollState + "mPivotEdge.x=" + this.mPivotEdge.x + "mPivotEdge.y=" + this.mPivotEdge.y + "mReachedEdgeCoord.x=" + this.mReachedEdgeCoord.x + "mReachedEdgeCoord.y=" + this.mReachedEdgeCoord.y + "mEdgeCooldown=" + this.mEdgeCooldown + "}";
        }
    }
}
