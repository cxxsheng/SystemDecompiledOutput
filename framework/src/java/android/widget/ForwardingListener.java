package android.widget;

/* loaded from: classes4.dex */
public abstract class ForwardingListener implements android.view.View.OnTouchListener, android.view.View.OnAttachStateChangeListener {
    private int mActivePointerId;
    private java.lang.Runnable mDisallowIntercept;
    private boolean mForwarding;
    private final int mLongPressTimeout;
    private final float mScaledTouchSlop;
    private final android.view.View mSrc;
    private final int mTapTimeout;
    private java.lang.Runnable mTriggerLongPress;

    public abstract com.android.internal.view.menu.ShowableListMenu getPopup();

    public ForwardingListener(android.view.View view) {
        this.mSrc = view;
        view.setLongClickable(true);
        view.addOnAttachStateChangeListener(this);
        this.mScaledTouchSlop = android.view.ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        this.mTapTimeout = android.view.ViewConfiguration.getTapTimeout();
        this.mLongPressTimeout = (this.mTapTimeout + android.view.ViewConfiguration.getLongPressTimeout()) / 2;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
        boolean z;
        boolean z2 = this.mForwarding;
        if (z2) {
            z = onTouchForwarded(motionEvent) || !onForwardingStopped();
        } else {
            z = onTouchObserved(motionEvent) && onForwardingStarted();
            if (z) {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                android.view.MotionEvent obtain = android.view.MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                this.mSrc.onTouchEvent(obtain);
                obtain.recycle();
            }
        }
        this.mForwarding = z;
        return z || z2;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(android.view.View view) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(android.view.View view) {
        this.mForwarding = false;
        this.mActivePointerId = -1;
        if (this.mDisallowIntercept != null) {
            this.mSrc.removeCallbacks(this.mDisallowIntercept);
        }
    }

    protected boolean onForwardingStarted() {
        com.android.internal.view.menu.ShowableListMenu popup = getPopup();
        if (popup != null && !popup.isShowing()) {
            popup.show();
            return true;
        }
        return true;
    }

    protected boolean onForwardingStopped() {
        com.android.internal.view.menu.ShowableListMenu popup = getPopup();
        if (popup != null && popup.isShowing()) {
            popup.dismiss();
            return true;
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean onTouchObserved(android.view.MotionEvent motionEvent) {
        android.view.View view = this.mSrc;
        if (!view.isEnabled()) {
            return false;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.mActivePointerId = motionEvent.getPointerId(0);
                byte b = 0;
                if (this.mDisallowIntercept == null) {
                    this.mDisallowIntercept = new android.widget.ForwardingListener.DisallowIntercept();
                }
                view.postDelayed(this.mDisallowIntercept, this.mTapTimeout);
                if (this.mTriggerLongPress == null) {
                    this.mTriggerLongPress = new android.widget.ForwardingListener.TriggerLongPress();
                }
                view.postDelayed(this.mTriggerLongPress, this.mLongPressTimeout);
                return false;
            case 1:
            case 3:
                clearCallbacks();
                return false;
            case 2:
                int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex >= 0 && !view.pointInView(motionEvent.getX(findPointerIndex), motionEvent.getY(findPointerIndex), this.mScaledTouchSlop)) {
                    clearCallbacks();
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    private void clearCallbacks() {
        if (this.mTriggerLongPress != null) {
            this.mSrc.removeCallbacks(this.mTriggerLongPress);
        }
        if (this.mDisallowIntercept != null) {
            this.mSrc.removeCallbacks(this.mDisallowIntercept);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLongPress() {
        clearCallbacks();
        android.view.View view = this.mSrc;
        if (!view.isEnabled() || view.isLongClickable() || !onForwardingStarted()) {
            return;
        }
        view.getParent().requestDisallowInterceptTouchEvent(true);
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.view.MotionEvent obtain = android.view.MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
        view.onTouchEvent(obtain);
        obtain.recycle();
        this.mForwarding = true;
    }

    private boolean onTouchForwarded(android.view.MotionEvent motionEvent) {
        android.widget.DropDownListView dropDownListView;
        boolean z;
        android.view.View view = this.mSrc;
        com.android.internal.view.menu.ShowableListMenu popup = getPopup();
        if (popup == null || !popup.isShowing() || (dropDownListView = (android.widget.DropDownListView) popup.getListView()) == null || !dropDownListView.isShown()) {
            return false;
        }
        android.view.MotionEvent obtainNoHistory = android.view.MotionEvent.obtainNoHistory(motionEvent);
        view.toGlobalMotionEvent(obtainNoHistory);
        dropDownListView.toLocalMotionEvent(obtainNoHistory);
        boolean onForwardedEvent = dropDownListView.onForwardedEvent(obtainNoHistory, this.mActivePointerId);
        obtainNoHistory.recycle();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            z = false;
        } else {
            z = true;
        }
        if (!onForwardedEvent || !z) {
            return false;
        }
        return true;
    }

    private class DisallowIntercept implements java.lang.Runnable {
        private DisallowIntercept() {
        }

        @Override // java.lang.Runnable
        public void run() {
            android.view.ViewParent parent = android.widget.ForwardingListener.this.mSrc.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    private class TriggerLongPress implements java.lang.Runnable {
        private TriggerLongPress() {
        }

        @Override // java.lang.Runnable
        public void run() {
            android.widget.ForwardingListener.this.onLongPress();
        }
    }
}
