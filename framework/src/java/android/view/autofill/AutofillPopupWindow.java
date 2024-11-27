package android.view.autofill;

/* loaded from: classes4.dex */
public class AutofillPopupWindow extends android.widget.PopupWindow {
    private static final java.lang.String TAG = "AutofillPopupWindow";
    private boolean mFullScreen;
    private final android.view.View.OnAttachStateChangeListener mOnAttachStateChangeListener = new android.view.View.OnAttachStateChangeListener() { // from class: android.view.autofill.AutofillPopupWindow.1
        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(android.view.View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(android.view.View view) {
            android.view.autofill.AutofillPopupWindow.this.dismiss();
        }
    };
    private android.view.WindowManager.LayoutParams mWindowLayoutParams;
    private final android.view.autofill.AutofillPopupWindow.WindowPresenter mWindowPresenter;

    public AutofillPopupWindow(android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) {
        this.mWindowPresenter = new android.view.autofill.AutofillPopupWindow.WindowPresenter(iAutofillWindowPresenter);
        setTouchModal(false);
        setOutsideTouchable(true);
        setInputMethodMode(2);
        setFocusable(true);
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

    public void update(final android.view.View view, int i, int i2, int i3, int i4, android.graphics.Rect rect) {
        int i5;
        int i6;
        int i7;
        android.view.View view2 = view;
        int i8 = i3;
        this.mFullScreen = i8 == -1;
        setWindowLayoutType(this.mFullScreen ? 2008 : 1005);
        if (this.mFullScreen) {
            android.graphics.Rect boundsExcludingNavigationBarAndCutout = android.window.WindowMetricsHelper.getBoundsExcludingNavigationBarAndCutout(((android.view.WindowManager) view.getContext().getSystemService(android.view.WindowManager.class)).getCurrentWindowMetrics());
            int width = boundsExcludingNavigationBarAndCutout.width();
            if (i4 == -1) {
                i7 = 0;
            } else {
                i7 = boundsExcludingNavigationBarAndCutout.height() - i4;
            }
            i8 = width;
            i6 = i7;
            i5 = 0;
        } else if (rect != null) {
            final int[] iArr = {rect.left, rect.top};
            android.view.View view3 = new android.view.View(view.getContext()) { // from class: android.view.autofill.AutofillPopupWindow.2
                @Override // android.view.View
                public void getLocationOnScreen(int[] iArr2) {
                    iArr2[0] = iArr[0];
                    iArr2[1] = iArr[1];
                }

                @Override // android.view.View
                public int getAccessibilityViewId() {
                    return view.getAccessibilityViewId();
                }

                @Override // android.view.View
                public android.view.ViewTreeObserver getViewTreeObserver() {
                    return view.getViewTreeObserver();
                }

                @Override // android.view.View
                public android.os.IBinder getApplicationWindowToken() {
                    return view.getApplicationWindowToken();
                }

                @Override // android.view.View
                public android.view.View getRootView() {
                    return view.getRootView();
                }

                @Override // android.view.View
                public int getLayoutDirection() {
                    return view.getLayoutDirection();
                }

                @Override // android.view.View
                public void getWindowDisplayFrame(android.graphics.Rect rect2) {
                    view.getWindowDisplayFrame(rect2);
                }

                @Override // android.view.View
                public void addOnAttachStateChangeListener(android.view.View.OnAttachStateChangeListener onAttachStateChangeListener) {
                    view.addOnAttachStateChangeListener(onAttachStateChangeListener);
                }

                @Override // android.view.View
                public void removeOnAttachStateChangeListener(android.view.View.OnAttachStateChangeListener onAttachStateChangeListener) {
                    view.removeOnAttachStateChangeListener(onAttachStateChangeListener);
                }

                @Override // android.view.View
                public boolean isAttachedToWindow() {
                    return view.isAttachedToWindow();
                }

                @Override // android.view.View
                public boolean requestRectangleOnScreen(android.graphics.Rect rect2, boolean z) {
                    return view.requestRectangleOnScreen(rect2, z);
                }

                @Override // android.view.View
                public android.os.IBinder getWindowToken() {
                    return view.getWindowToken();
                }
            };
            view3.setLeftTopRightBottom(rect.left, rect.top, rect.right, rect.bottom);
            view3.setScrollX(view.getScrollX());
            view3.setScrollY(view.getScrollY());
            view.setOnScrollChangeListener(new android.view.View.OnScrollChangeListener() { // from class: android.view.autofill.AutofillPopupWindow$$ExternalSyntheticLambda0
                @Override // android.view.View.OnScrollChangeListener
                public final void onScrollChange(android.view.View view4, int i9, int i10, int i11, int i12) {
                    android.view.autofill.AutofillPopupWindow.lambda$update$0(iArr, view4, i9, i10, i11, i12);
                }
            });
            view3.setWillNotDraw(true);
            i5 = i;
            i6 = i2;
            view2 = view3;
        } else {
            i5 = i;
            i6 = i2;
        }
        if (!this.mFullScreen) {
            setAnimationStyle(-1);
        } else if (i4 == -1) {
            setAnimationStyle(0);
        } else {
            setAnimationStyle(com.android.internal.R.style.AutofillHalfScreenAnimation);
        }
        if (!isShowing()) {
            setWidth(i8);
            setHeight(i4);
            showAsDropDown(view2, i5, i6);
            return;
        }
        update(view2, i5, i6, i8, i4);
    }

    static /* synthetic */ void lambda$update$0(int[] iArr, android.view.View view, int i, int i2, int i3, int i4) {
        iArr[0] = iArr[0] - (i - i3);
        iArr[1] = iArr[1] - (i2 - i4);
    }

    @Override // android.widget.PopupWindow
    protected void update(android.view.View view, android.view.WindowManager.LayoutParams layoutParams) {
        this.mWindowPresenter.show(layoutParams, getTransitionEpicenter(), isLayoutInsetDecor(), view != null ? view.getLayoutDirection() : 3);
    }

    @Override // android.widget.PopupWindow
    protected boolean findDropDownPosition(android.view.View view, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, boolean z) {
        if (this.mFullScreen) {
            layoutParams.x = i;
            layoutParams.y = i2;
            layoutParams.width = i3;
            layoutParams.height = i4;
            layoutParams.gravity = i5;
            return false;
        }
        return super.findDropDownPosition(view, layoutParams, i, i2, i3, i4, i5, z);
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(android.view.View view, int i, int i2, int i3) {
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v(TAG, "showAsDropDown(): anchor=" + view + ", xoff=" + i + ", yoff=" + i2 + ", isShowing(): " + isShowing());
        }
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
        this.mWindowPresenter.show(createPopupLayoutParams, getTransitionEpicenter(), isLayoutInsetDecor(), view.getLayoutDirection());
    }

    @Override // android.widget.PopupWindow
    protected void attachToAnchor(android.view.View view, int i, int i2, int i3) {
        super.attachToAnchor(view, i, i2, i3);
        view.addOnAttachStateChangeListener(this.mOnAttachStateChangeListener);
    }

    @Override // android.widget.PopupWindow
    protected void detachFromAnchor() {
        android.view.View anchor = getAnchor();
        if (anchor != null) {
            anchor.removeOnAttachStateChangeListener(this.mOnAttachStateChangeListener);
        }
        super.detachFromAnchor();
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        if (!isShowing() || isTransitioningToDismiss()) {
            return;
        }
        setShowing(false);
        setTransitioningToDismiss(true);
        this.mWindowPresenter.hide(getTransitionEpicenter());
        detachFromAnchor();
        if (getOnDismissListener() != null) {
            getOnDismissListener().onDismiss();
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

    private class WindowPresenter {
        final android.view.autofill.IAutofillWindowPresenter mPresenter;

        WindowPresenter(android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) {
            this.mPresenter = iAutofillWindowPresenter;
        }

        void show(android.view.WindowManager.LayoutParams layoutParams, android.graphics.Rect rect, boolean z, int i) {
            try {
                this.mPresenter.show(layoutParams, rect, z, i);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.view.autofill.AutofillPopupWindow.TAG, "Error showing fill window", e);
                e.rethrowFromSystemServer();
            }
        }

        void hide(android.graphics.Rect rect) {
            try {
                this.mPresenter.hide(rect);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.view.autofill.AutofillPopupWindow.TAG, "Error hiding fill window", e);
                e.rethrowFromSystemServer();
            }
        }
    }
}
