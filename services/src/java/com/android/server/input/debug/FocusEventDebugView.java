package com.android.server.input.debug;

/* loaded from: classes2.dex */
public class FocusEventDebugView extends android.widget.RelativeLayout {
    private static final int KEY_FADEOUT_DURATION_MILLIS = 1000;
    private static final int KEY_SEPARATION_MARGIN_DP = 16;
    private static final int KEY_TRANSITION_DURATION_MILLIS = 100;
    private static final int KEY_VIEW_MIN_WIDTH_DP = 32;
    private static final int KEY_VIEW_SIDE_PADDING_DP = 16;
    private static final int KEY_VIEW_TEXT_SIZE_SP = 12;
    private static final int KEY_VIEW_VERTICAL_PADDING_DP = 8;
    private static final int OUTER_PADDING_DP = 16;
    private static final double ROTATY_GRAPH_HEIGHT_FRACTION = 0.5d;
    private static final java.lang.String TAG = com.android.server.input.debug.FocusEventDebugView.class.getSimpleName();
    private final android.util.DisplayMetrics mDm;

    @android.annotation.Nullable
    private com.android.server.input.debug.FocusEventDebugGlobalMonitor mFocusEventDebugGlobalMonitor;
    private final int mOuterPadding;

    @android.annotation.Nullable
    private com.android.server.input.debug.FocusEventDebugView.PressedKeyContainer mPressedKeyContainer;
    private final java.util.Map<android.util.Pair<java.lang.Integer, java.lang.Integer>, com.android.server.input.debug.FocusEventDebugView.PressedKeyView> mPressedKeys;

    @android.annotation.Nullable
    private com.android.server.input.debug.FocusEventDebugView.PressedKeyContainer mPressedModifierContainer;

    @android.annotation.Nullable
    private com.android.server.input.debug.RotaryInputGraphView mRotaryInputGraphView;
    private final java.util.function.Supplier<com.android.server.input.debug.RotaryInputGraphView> mRotaryInputGraphViewFactory;

    @android.annotation.Nullable
    private com.android.server.input.debug.RotaryInputValueView mRotaryInputValueView;
    private final java.util.function.Supplier<com.android.server.input.debug.RotaryInputValueView> mRotaryInputValueViewFactory;
    private final com.android.server.input.InputManagerService mService;

    @com.android.internal.annotations.VisibleForTesting
    FocusEventDebugView(android.content.Context context, com.android.server.input.InputManagerService inputManagerService, java.util.function.Supplier<com.android.server.input.debug.RotaryInputValueView> supplier, java.util.function.Supplier<com.android.server.input.debug.RotaryInputGraphView> supplier2) {
        super(context);
        this.mPressedKeys = new java.util.HashMap();
        setFocusableInTouchMode(true);
        this.mService = inputManagerService;
        this.mRotaryInputValueViewFactory = supplier;
        this.mRotaryInputGraphViewFactory = supplier2;
        this.mDm = ((android.widget.RelativeLayout) this).mContext.getResources().getDisplayMetrics();
        this.mOuterPadding = (int) android.util.TypedValue.applyDimension(1, 16.0f, this.mDm);
    }

    public FocusEventDebugView(final android.content.Context context, com.android.server.input.InputManagerService inputManagerService) {
        this(context, inputManagerService, new java.util.function.Supplier() { // from class: com.android.server.input.debug.FocusEventDebugView$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                com.android.server.input.debug.RotaryInputValueView lambda$new$0;
                lambda$new$0 = com.android.server.input.debug.FocusEventDebugView.lambda$new$0(context);
                return lambda$new$0;
            }
        }, new java.util.function.Supplier() { // from class: com.android.server.input.debug.FocusEventDebugView$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                com.android.server.input.debug.RotaryInputGraphView lambda$new$1;
                lambda$new$1 = com.android.server.input.debug.FocusEventDebugView.lambda$new$1(context);
                return lambda$new$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.input.debug.RotaryInputValueView lambda$new$0(android.content.Context context) {
        return new com.android.server.input.debug.RotaryInputValueView(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.input.debug.RotaryInputGraphView lambda$new$1(android.content.Context context) {
        return new com.android.server.input.debug.RotaryInputGraphView(context);
    }

    @Override // android.view.View
    public android.view.WindowInsets onApplyWindowInsets(android.view.WindowInsets windowInsets) {
        int i;
        android.view.RoundedCorner roundedCorner = windowInsets.getRoundedCorner(3);
        if (roundedCorner != null && !windowInsets.isRound()) {
            i = roundedCorner.getRadius();
        } else {
            i = 0;
        }
        android.view.RoundedCorner roundedCorner2 = windowInsets.getRoundedCorner(2);
        if (roundedCorner2 != null && !windowInsets.isRound()) {
            i = java.lang.Math.max(i, roundedCorner2.getRadius());
        }
        if (windowInsets.getDisplayCutout() != null) {
            i = java.lang.Math.max(i, windowInsets.getDisplayCutout().getSafeInsetBottom());
        }
        setPadding(this.mOuterPadding, this.mOuterPadding, this.mOuterPadding, this.mOuterPadding + i);
        setClipToPadding(false);
        invalidate();
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        handleKeyEvent(keyEvent);
        return super.dispatchKeyEvent(keyEvent);
    }

    public void updateShowKeyPresses(final boolean z) {
        post(new java.lang.Runnable() { // from class: com.android.server.input.debug.FocusEventDebugView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.input.debug.FocusEventDebugView.this.lambda$updateShowKeyPresses$2(z);
            }
        });
    }

    public void updateShowRotaryInput(final boolean z) {
        post(new java.lang.Runnable() { // from class: com.android.server.input.debug.FocusEventDebugView$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.input.debug.FocusEventDebugView.this.lambda$updateShowRotaryInput$3(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleUpdateShowKeyPresses, reason: merged with bridge method [inline-methods] */
    public void lambda$updateShowKeyPresses$2(boolean z) {
        if (z == showKeyPresses()) {
            return;
        }
        if (!z) {
            removeView(this.mPressedKeyContainer);
            this.mPressedKeyContainer = null;
            removeView(this.mPressedModifierContainer);
            this.mPressedModifierContainer = null;
            return;
        }
        this.mPressedKeyContainer = new com.android.server.input.debug.FocusEventDebugView.PressedKeyContainer(((android.widget.RelativeLayout) this).mContext);
        this.mPressedKeyContainer.setOrientation(0);
        this.mPressedKeyContainer.setGravity(85);
        this.mPressedKeyContainer.setLayoutDirection(0);
        final android.widget.HorizontalScrollView horizontalScrollView = new android.widget.HorizontalScrollView(((android.widget.RelativeLayout) this).mContext);
        horizontalScrollView.addView(this.mPressedKeyContainer);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        horizontalScrollView.addOnLayoutChangeListener(new android.view.View.OnLayoutChangeListener() { // from class: com.android.server.input.debug.FocusEventDebugView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(android.view.View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                horizontalScrollView.fullScroll(66);
            }
        });
        horizontalScrollView.setHorizontalFadingEdgeEnabled(true);
        android.widget.RelativeLayout.LayoutParams layoutParams = new android.widget.RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(12);
        layoutParams.addRule(11);
        addView(horizontalScrollView, layoutParams);
        this.mPressedModifierContainer = new com.android.server.input.debug.FocusEventDebugView.PressedKeyContainer(((android.widget.RelativeLayout) this).mContext);
        this.mPressedModifierContainer.setOrientation(1);
        this.mPressedModifierContainer.setGravity(83);
        android.widget.RelativeLayout.LayoutParams layoutParams2 = new android.widget.RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(12);
        layoutParams2.addRule(9);
        layoutParams2.addRule(0, horizontalScrollView.getId());
        addView(this.mPressedModifierContainer, layoutParams2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @com.android.internal.annotations.VisibleForTesting
    /* renamed from: handleUpdateShowRotaryInput, reason: merged with bridge method [inline-methods] */
    public void lambda$updateShowRotaryInput$3(boolean z) {
        if (z == showRotaryInput()) {
            return;
        }
        if (!z) {
            this.mFocusEventDebugGlobalMonitor.dispose();
            this.mFocusEventDebugGlobalMonitor = null;
            removeView(this.mRotaryInputValueView);
            this.mRotaryInputValueView = null;
            removeView(this.mRotaryInputGraphView);
            this.mRotaryInputGraphView = null;
            return;
        }
        this.mFocusEventDebugGlobalMonitor = new com.android.server.input.debug.FocusEventDebugGlobalMonitor(this, this.mService);
        this.mRotaryInputValueView = this.mRotaryInputValueViewFactory.get();
        android.widget.RelativeLayout.LayoutParams layoutParams = new android.widget.RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14);
        layoutParams.addRule(12);
        addView(this.mRotaryInputValueView, layoutParams);
        this.mRotaryInputGraphView = this.mRotaryInputGraphViewFactory.get();
        android.widget.RelativeLayout.LayoutParams layoutParams2 = new android.widget.RelativeLayout.LayoutParams(-1, (int) (this.mDm.heightPixels * ROTATY_GRAPH_HEIGHT_FRACTION));
        layoutParams2.addRule(13);
        addView(this.mRotaryInputGraphView, layoutParams2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportKeyEvent$5(android.view.KeyEvent keyEvent) {
        handleKeyEvent(android.view.KeyEvent.obtain(keyEvent));
    }

    public void reportKeyEvent(final android.view.KeyEvent keyEvent) {
        post(new java.lang.Runnable() { // from class: com.android.server.input.debug.FocusEventDebugView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.input.debug.FocusEventDebugView.this.lambda$reportKeyEvent$5(keyEvent);
            }
        });
    }

    public void reportMotionEvent(final android.view.MotionEvent motionEvent) {
        if (motionEvent.getSource() != 4194304) {
            return;
        }
        post(new java.lang.Runnable() { // from class: com.android.server.input.debug.FocusEventDebugView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.input.debug.FocusEventDebugView.this.lambda$reportMotionEvent$6(motionEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportMotionEvent$6(android.view.MotionEvent motionEvent) {
        handleRotaryInput(android.view.MotionEvent.obtain(motionEvent));
    }

    private void handleKeyEvent(android.view.KeyEvent keyEvent) {
        com.android.server.input.debug.FocusEventDebugView.PressedKeyContainer pressedKeyContainer;
        if (!showKeyPresses()) {
            return;
        }
        android.util.Pair<java.lang.Integer, java.lang.Integer> pair = new android.util.Pair<>(java.lang.Integer.valueOf(keyEvent.getDeviceId()), java.lang.Integer.valueOf(keyEvent.getScanCode()));
        if (android.view.KeyEvent.isModifierKey(keyEvent.getKeyCode())) {
            pressedKeyContainer = this.mPressedModifierContainer;
        } else {
            pressedKeyContainer = this.mPressedKeyContainer;
        }
        com.android.server.input.debug.FocusEventDebugView.PressedKeyView pressedKeyView = this.mPressedKeys.get(pair);
        switch (keyEvent.getAction()) {
            case 0:
                if (pressedKeyView != null) {
                    if (keyEvent.getRepeatCount() == 0) {
                        android.util.Slog.w(TAG, "Got key down for " + android.view.KeyEvent.keyCodeToString(keyEvent.getKeyCode()) + " that was already tracked as being down.");
                        break;
                    } else {
                        pressedKeyContainer.handleKeyRepeat(pressedKeyView);
                        break;
                    }
                } else {
                    com.android.server.input.debug.FocusEventDebugView.PressedKeyView pressedKeyView2 = new com.android.server.input.debug.FocusEventDebugView.PressedKeyView(((android.widget.RelativeLayout) this).mContext, getLabel(keyEvent));
                    this.mPressedKeys.put(pair, pressedKeyView2);
                    pressedKeyContainer.handleKeyPressed(pressedKeyView2);
                    break;
                }
            case 1:
                if (pressedKeyView == null) {
                    android.util.Slog.w(TAG, "Got key up for " + android.view.KeyEvent.keyCodeToString(keyEvent.getKeyCode()) + " that was not tracked as being down.");
                    break;
                } else {
                    this.mPressedKeys.remove(pair);
                    pressedKeyContainer.handleKeyRelease(pressedKeyView);
                    break;
                }
        }
        keyEvent.recycle();
    }

    @com.android.internal.annotations.VisibleForTesting
    void handleRotaryInput(android.view.MotionEvent motionEvent) {
        if (!showRotaryInput()) {
            return;
        }
        float axisValue = motionEvent.getAxisValue(26);
        this.mRotaryInputValueView.updateValue(axisValue);
        this.mRotaryInputGraphView.addValue(axisValue, motionEvent.getEventTime());
        motionEvent.recycle();
    }

    private static java.lang.String getLabel(android.view.KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case 3:
                return "◯";
            case 4:
                return "◁";
            case 19:
                return "↑";
            case 20:
                return "↓";
            case 21:
                return "←";
            case 22:
                return "→";
            case 61:
                return "⇥";
            case 62:
                return "␣";
            case 66:
            case 160:
                return "⏎";
            case 67:
                return "⌫";
            case 85:
                return "⏯";
            case 111:
                return "esc";
            case 112:
                return "⌦";
            case 268:
                return "↖";
            case 269:
                return "↙";
            case android.companion.virtualcamera.SensorOrientation.ORIENTATION_270 /* 270 */:
                return "↗";
            case 271:
                return "↘";
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_LOCATION_PROVIDER /* 312 */:
                return "□";
            default:
                int unicodeChar = keyEvent.getUnicodeChar();
                if (unicodeChar != 0) {
                    if ((Integer.MIN_VALUE & unicodeChar) != 0) {
                        return "◌" + java.lang.String.valueOf((char) android.view.KeyCharacterMap.getCombiningChar(Integer.MAX_VALUE & unicodeChar));
                    }
                    return java.lang.String.valueOf((char) unicodeChar);
                }
                java.lang.String keyCodeToString = android.view.KeyEvent.keyCodeToString(keyEvent.getKeyCode());
                if (keyCodeToString.startsWith("KEYCODE_")) {
                    return keyCodeToString.substring(8);
                }
                return keyCodeToString;
        }
    }

    private boolean showKeyPresses() {
        return this.mPressedKeyContainer != null;
    }

    private boolean showRotaryInput() {
        return this.mRotaryInputValueView != null;
    }

    private static class PressedKeyView extends android.widget.TextView {
        private static final android.graphics.ColorFilter sInvertColors = new android.graphics.ColorMatrixColorFilter(new float[]{-1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 255.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, -1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 255.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, -1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 255.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE});

        PressedKeyView(android.content.Context context, java.lang.String str) {
            super(context);
            android.util.DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int applyDimension = (int) android.util.TypedValue.applyDimension(1, 16.0f, displayMetrics);
            int applyDimension2 = (int) android.util.TypedValue.applyDimension(1, 8.0f, displayMetrics);
            int applyDimension3 = (int) android.util.TypedValue.applyDimension(1, 32.0f, displayMetrics);
            int applyDimension4 = (int) android.util.TypedValue.applyDimension(2, 12.0f, displayMetrics);
            setText(str);
            setGravity(17);
            setMinimumWidth(applyDimension3);
            setTextSize(applyDimension4);
            setTypeface(android.graphics.Typeface.SANS_SERIF);
            setBackgroundResource(android.R.drawable.floating_popup_background_light);
            setPaddingRelative(applyDimension, applyDimension2, applyDimension, applyDimension2);
            setHighlighted(true);
        }

        void setHighlighted(boolean z) {
            if (z) {
                setTextColor(android.hardware.audio.common.V2_0.AudioFormat.MAIN_MASK);
                getBackground().setColorFilter(sInvertColors);
            } else {
                setTextColor(-1);
                getBackground().clearColorFilter();
            }
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class PressedKeyContainer extends android.widget.LinearLayout {
        private final android.view.ViewGroup.MarginLayoutParams mPressedKeyLayoutParams;

        PressedKeyContainer(android.content.Context context) {
            super(context);
            int applyDimension = (int) android.util.TypedValue.applyDimension(1, 16.0f, context.getResources().getDisplayMetrics());
            android.animation.LayoutTransition layoutTransition = new android.animation.LayoutTransition();
            layoutTransition.disableTransitionType(2);
            layoutTransition.disableTransitionType(3);
            layoutTransition.disableTransitionType(1);
            layoutTransition.setDuration(100L);
            setLayoutTransition(layoutTransition);
            this.mPressedKeyLayoutParams = new android.view.ViewGroup.MarginLayoutParams(-2, -2);
            if (getOrientation() == 1) {
                this.mPressedKeyLayoutParams.setMargins(0, applyDimension, 0, 0);
            } else {
                this.mPressedKeyLayoutParams.setMargins(applyDimension, 0, 0, 0);
            }
        }

        public void handleKeyPressed(com.android.server.input.debug.FocusEventDebugView.PressedKeyView pressedKeyView) {
            addView(pressedKeyView, getChildCount(), this.mPressedKeyLayoutParams);
            invalidate();
        }

        public void handleKeyRepeat(com.android.server.input.debug.FocusEventDebugView.PressedKeyView pressedKeyView) {
        }

        public void handleKeyRelease(com.android.server.input.debug.FocusEventDebugView.PressedKeyView pressedKeyView) {
            pressedKeyView.setHighlighted(false);
            pressedKeyView.clearAnimation();
            pressedKeyView.animate().alpha(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE).setDuration(1000L).setInterpolator(new android.view.animation.AccelerateInterpolator()).withEndAction(new java.lang.Runnable() { // from class: com.android.server.input.debug.FocusEventDebugView$PressedKeyContainer$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.input.debug.FocusEventDebugView.PressedKeyContainer.this.cleanUpPressedKeyViews();
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void cleanUpPressedKeyViews() {
            int i = 0;
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                android.view.View childAt = getChildAt(i2);
                if (childAt.getAlpha() != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    break;
                }
                childAt.setVisibility(8);
                childAt.clearAnimation();
                i++;
            }
            removeViews(0, i);
            invalidate();
        }
    }
}
