package com.android.internal.view.inline;

/* loaded from: classes5.dex */
public final class InlineTooltipUi extends android.widget.PopupWindow implements java.lang.AutoCloseable {
    private static final int FIRST_TIME_SHOW_DEFAULT_DELAY_MS = 250;
    private static final java.lang.String TAG = "InlineTooltipUi";
    private final android.view.ViewGroup mContentContainer;
    private com.android.internal.view.inline.InlineTooltipUi.DelayShowRunnable mDelayShowTooltip;
    private boolean mHasEverDetached;
    private boolean mShowing;
    private android.view.WindowManager.LayoutParams mWindowLayoutParams;
    private final android.view.WindowManager mWm;
    private boolean mDelayShowAtStart = true;
    private boolean mDelaying = false;
    private final android.graphics.Rect mTmpRect = new android.graphics.Rect();
    private final android.view.View.OnAttachStateChangeListener mAnchorOnAttachStateChangeListener = new android.view.View.OnAttachStateChangeListener() { // from class: com.android.internal.view.inline.InlineTooltipUi.1
        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(android.view.View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(android.view.View view) {
            com.android.internal.view.inline.InlineTooltipUi.this.mHasEverDetached = true;
            com.android.internal.view.inline.InlineTooltipUi.this.dismiss();
        }
    };
    private final android.view.View.OnLayoutChangeListener mAnchoredOnLayoutChangeListener = new android.view.View.OnLayoutChangeListener() { // from class: com.android.internal.view.inline.InlineTooltipUi.2
        int mHeight;

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(android.view.View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            int i9;
            if (!com.android.internal.view.inline.InlineTooltipUi.this.mHasEverDetached && this.mHeight != (i9 = i4 - i2)) {
                this.mHeight = i9;
                com.android.internal.view.inline.InlineTooltipUi.this.adjustPosition();
            }
        }
    };
    private int mShowDelayConfigMs = android.provider.DeviceConfig.getInt(android.content.Context.AUTOFILL_MANAGER_SERVICE, android.view.autofill.AutofillFeatureFlags.DEVICE_CONFIG_AUTOFILL_TOOLTIP_SHOW_UP_DELAY, 250);

    public InlineTooltipUi(android.content.Context context) {
        this.mContentContainer = new android.widget.LinearLayout(new android.content.ContextWrapper(context));
        this.mWm = (android.view.WindowManager) context.getSystemService(android.view.WindowManager.class);
        setTouchModal(false);
        setOutsideTouchable(true);
        setInputMethodMode(2);
        setFocusable(false);
    }

    public void setTooltipView(android.widget.inline.InlineContentView inlineContentView) {
        this.mContentContainer.removeAllViews();
        this.mContentContainer.addView(inlineContentView);
        this.mContentContainer.setVisibility(0);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        dismiss();
    }

    @Override // android.widget.PopupWindow
    protected boolean hasContentView() {
        return true;
    }

    @Override // android.widget.PopupWindow
    protected boolean hasDecorView() {
        return true;
    }

    @Override // android.widget.PopupWindow
    protected android.view.WindowManager.LayoutParams getDecorViewLayoutParams() {
        return this.mWindowLayoutParams;
    }

    public void update(android.view.View view) {
        if (view == null) {
            android.view.View anchor = getAnchor();
            if (anchor != null) {
                removeDelayShowTooltip(anchor);
                return;
            }
            return;
        }
        if (this.mDelayShowAtStart) {
            this.mDelayShowAtStart = false;
            this.mDelaying = true;
            if (this.mDelayShowTooltip == null) {
                this.mDelayShowTooltip = new com.android.internal.view.inline.InlineTooltipUi.DelayShowRunnable(view);
            }
            int i = this.mShowDelayConfigMs;
            try {
                i = (int) (i * android.view.WindowManager.fixScale(android.provider.Settings.Global.getFloat(view.getContext().getContentResolver(), "animator_duration_scale")));
            } catch (android.provider.Settings.SettingNotFoundException e) {
            }
            view.postDelayed(this.mDelayShowTooltip, i);
            return;
        }
        if (!this.mDelaying) {
            updateInner(view);
        }
    }

    private void removeDelayShowTooltip(android.view.View view) {
        if (this.mDelayShowTooltip != null) {
            view.removeCallbacks(this.mDelayShowTooltip);
            this.mDelayShowTooltip = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInner(android.view.View view) {
        if (this.mHasEverDetached) {
            return;
        }
        setWindowLayoutType(1005);
        int preferHeight = (-view.getHeight()) - getPreferHeight(view);
        if (!isShowing()) {
            setWidth(-2);
            setHeight(-2);
            showAsDropDown(view, 0, preferHeight, 49);
            return;
        }
        update(view, 0, preferHeight, -2, -2);
    }

    private int getPreferHeight(android.view.View view) {
        int height = this.mContentContainer.getHeight();
        return height == 0 ? view.getHeight() : height;
    }

    @Override // android.widget.PopupWindow
    protected boolean findDropDownPosition(android.view.View view, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, boolean z) {
        boolean findDropDownPosition = super.findDropDownPosition(view, layoutParams, i, i2, i3, i4, i5, z);
        java.lang.Object parent = view.getParent();
        if (parent instanceof android.view.View) {
            android.graphics.Rect rect = this.mTmpRect;
            ((android.view.View) parent).getGlobalVisibleRect(rect);
            if (findDropDownPosition) {
                layoutParams.y = rect.top - getPreferHeight(view);
            } else {
                layoutParams.y = rect.bottom + 1;
            }
        }
        return findDropDownPosition;
    }

    @Override // android.widget.PopupWindow
    protected void update(android.view.View view, android.view.WindowManager.LayoutParams layoutParams) {
        if (view.isVisibleToUser()) {
            show(layoutParams);
        } else {
            hide();
        }
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(android.view.View view, int i, int i2, int i3) {
        if (isShowing()) {
            return;
        }
        setShowing(true);
        setDropDown(true);
        attachToAnchor(view, i, i2, i3);
        android.view.WindowManager.LayoutParams createPopupLayoutParams = createPopupLayoutParams(view.getWindowToken());
        this.mWindowLayoutParams = createPopupLayoutParams;
        updateAboveAnchor(findDropDownPosition(view, createPopupLayoutParams, i, i2, createPopupLayoutParams.width, createPopupLayoutParams.height, i3, getAllowScrollingAnchorParent()));
        createPopupLayoutParams.accessibilityIdOfAnchor = view.getAccessibilityViewId();
        createPopupLayoutParams.packageName = view.getContext().getPackageName();
        show(createPopupLayoutParams);
    }

    @Override // android.widget.PopupWindow
    protected void attachToAnchor(android.view.View view, int i, int i2, int i3) {
        super.attachToAnchor(view, i, i2, i3);
        view.addOnAttachStateChangeListener(this.mAnchorOnAttachStateChangeListener);
    }

    @Override // android.widget.PopupWindow
    protected void detachFromAnchor() {
        android.view.View anchor = getAnchor();
        if (anchor != null) {
            anchor.removeOnAttachStateChangeListener(this.mAnchorOnAttachStateChangeListener);
            removeDelayShowTooltip(anchor);
        }
        this.mHasEverDetached = true;
        super.detachFromAnchor();
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        if (!isShowing() || isTransitioningToDismiss()) {
            return;
        }
        setTransitioningToDismiss(true);
        hide();
        detachFromAnchor();
        if (getOnDismissListener() != null) {
            getOnDismissListener().onDismiss();
        }
        super.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustPosition() {
        android.view.View anchor = getAnchor();
        if (anchor == null) {
            return;
        }
        update(anchor);
    }

    private void show(android.view.WindowManager.LayoutParams layoutParams) {
        this.mWindowLayoutParams = layoutParams;
        try {
            layoutParams.packageName = "android";
            layoutParams.setTitle("Autofill Inline Tooltip");
            if (!this.mShowing) {
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "show()");
                }
                layoutParams.flags = 40;
                layoutParams.privateFlags |= 4194304;
                this.mContentContainer.addOnLayoutChangeListener(this.mAnchoredOnLayoutChangeListener);
                this.mWm.addView(this.mContentContainer, layoutParams);
                this.mShowing = true;
                return;
            }
            this.mWm.updateViewLayout(this.mContentContainer, layoutParams);
        } catch (android.view.WindowManager.BadTokenException e) {
            android.util.Slog.d(TAG, "Failed with token " + layoutParams.token + " gone.");
        } catch (java.lang.IllegalStateException e2) {
            android.util.Slog.wtf(TAG, "Exception showing window " + layoutParams, e2);
        }
    }

    private void hide() {
        try {
            if (this.mShowing) {
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "hide()");
                }
                this.mContentContainer.removeOnLayoutChangeListener(this.mAnchoredOnLayoutChangeListener);
                this.mWm.removeView(this.mContentContainer);
                this.mShowing = false;
            }
        } catch (java.lang.IllegalStateException e) {
            android.util.Slog.e(TAG, "Exception hiding window ", e);
        }
    }

    @Override // android.widget.PopupWindow
    public int getAnimationStyle() {
        throw new java.lang.IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public android.graphics.drawable.Drawable getBackground() {
        throw new java.lang.IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public android.view.View getContentView() {
        throw new java.lang.IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public float getElevation() {
        throw new java.lang.IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public android.transition.Transition getEnterTransition() {
        throw new java.lang.IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public android.transition.Transition getExitTransition() {
        throw new java.lang.IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public void setBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        throw new java.lang.IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public void setContentView(android.view.View view) {
        if (view != null) {
            throw new java.lang.IllegalStateException("You can't call this!");
        }
    }

    @Override // android.widget.PopupWindow
    public void setElevation(float f) {
        throw new java.lang.IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public void setEnterTransition(android.transition.Transition transition) {
        throw new java.lang.IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public void setExitTransition(android.transition.Transition transition) {
        throw new java.lang.IllegalStateException("You can't call this!");
    }

    @Override // android.widget.PopupWindow
    public void setTouchInterceptor(android.view.View.OnTouchListener onTouchListener) {
        throw new java.lang.IllegalStateException("You can't call this!");
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        if (this.mContentContainer != null) {
            printWriter.print(str);
            printWriter.print("Window: ");
            java.lang.String str2 = str + "  ";
            printWriter.println();
            printWriter.print(str2);
            printWriter.print("showing: ");
            printWriter.println(this.mShowing);
            printWriter.print(str2);
            printWriter.print("view: ");
            printWriter.println(this.mContentContainer);
            if (this.mWindowLayoutParams != null) {
                printWriter.print(str2);
                printWriter.print("params: ");
                printWriter.println(this.mWindowLayoutParams);
            }
            printWriter.print(str2);
            printWriter.print("screen coordinates: ");
            if (this.mContentContainer == null) {
                printWriter.println("N/A");
                return;
            }
            int[] locationOnScreen = this.mContentContainer.getLocationOnScreen();
            printWriter.print(locationOnScreen[0]);
            printWriter.print("x");
            printWriter.println(locationOnScreen[1]);
        }
    }

    private class DelayShowRunnable implements java.lang.Runnable {
        java.lang.ref.WeakReference<android.view.View> mAnchor;

        DelayShowRunnable(android.view.View view) {
            this.mAnchor = new java.lang.ref.WeakReference<>(view);
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.internal.view.inline.InlineTooltipUi.this.mDelaying = false;
            android.view.View view = this.mAnchor.get();
            if (view != null) {
                com.android.internal.view.inline.InlineTooltipUi.this.updateInner(view);
            }
        }

        public void setAnchor(android.view.View view) {
            this.mAnchor.clear();
            this.mAnchor = new java.lang.ref.WeakReference<>(view);
        }
    }
}
