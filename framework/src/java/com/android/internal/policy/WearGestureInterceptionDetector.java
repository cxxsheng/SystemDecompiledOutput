package com.android.internal.policy;

/* loaded from: classes5.dex */
public class WearGestureInterceptionDetector {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "WearGestureInterceptionDetector";
    private int mActivePointerId;
    private boolean mDiscardIntercept;
    private float mDownX;
    private float mDownY;
    private final com.android.internal.policy.DecorView mInstalledDecorView;
    private boolean mSwiping;
    private final float mSwipingStartThreshold;
    private final float mTouchSlop;

    WearGestureInterceptionDetector(android.content.Context context, com.android.internal.policy.DecorView decorView) {
        this.mTouchSlop = android.view.ViewConfiguration.get(context).getScaledTouchSlop();
        this.mInstalledDecorView = decorView;
        this.mSwipingStartThreshold = this.mTouchSlop * 2.0f;
    }

    public static boolean isEnabled(android.content.Context context) {
        if (!context.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_WATCH)) {
            return false;
        }
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16843763});
        boolean z = obtainStyledAttributes.getIndexCount() > 0 ? obtainStyledAttributes.getBoolean(0, true) : true;
        obtainStyledAttributes.recycle();
        return z;
    }

    private int getIndexForValidPointer(android.view.MotionEvent motionEvent) {
        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        if (findPointerIndex == -1) {
            this.mDiscardIntercept = true;
        }
        return findPointerIndex;
    }

    private void updateSwiping(android.view.MotionEvent motionEvent) {
        if (this.mSwiping) {
            return;
        }
        float rawX = motionEvent.getRawX() - this.mDownX;
        float rawY = motionEvent.getRawY() - this.mDownY;
        if ((rawX * rawX) + (rawY * rawY) > this.mTouchSlop * this.mTouchSlop) {
            this.mSwiping = rawX > this.mSwipingStartThreshold && java.lang.Math.abs(rawY) < java.lang.Math.abs(rawX);
        }
    }

    private void updateDiscardIntercept(android.view.MotionEvent motionEvent, int i) {
        if (!this.mSwiping || this.mDiscardIntercept) {
            return;
        }
        if (canScroll(this.mInstalledDecorView, false, this.mDownX < motionEvent.getRawX(), motionEvent.getX(i), motionEvent.getY(i))) {
            this.mDiscardIntercept = true;
        }
    }

    private void resetMembers() {
        this.mDownX = 0.0f;
        this.mDownY = 0.0f;
        this.mSwiping = false;
        this.mDiscardIntercept = false;
    }

    public boolean isIntercepting() {
        return !this.mDiscardIntercept && this.mSwiping;
    }

    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        int indexForValidPointer;
        switch (motionEvent.getActionMasked()) {
            case 0:
                resetMembers();
                this.mDownX = motionEvent.getRawX();
                this.mDownY = motionEvent.getRawY();
                this.mActivePointerId = motionEvent.getPointerId(0);
                break;
            case 1:
            case 3:
                resetMembers();
                break;
            case 2:
                if (!this.mDiscardIntercept && (indexForValidPointer = getIndexForValidPointer(motionEvent)) != -1) {
                    updateSwiping(motionEvent);
                    updateDiscardIntercept(motionEvent, indexForValidPointer);
                    break;
                }
                break;
            case 5:
                this.mActivePointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                break;
            case 6:
                int actionIndex = motionEvent.getActionIndex();
                if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
                    this.mActivePointerId = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0);
                    break;
                }
                break;
        }
        return isIntercepting();
    }

    private boolean canScroll(android.view.View view, boolean z, boolean z2, float f, float f2) {
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                android.view.View childAt = viewGroup.getChildAt(childCount);
                float f3 = f + scrollX;
                if (f3 >= childAt.getLeft() && f3 < childAt.getRight()) {
                    float f4 = f2 + scrollY;
                    if (f4 >= childAt.getTop() && f4 < childAt.getBottom() && canScroll(childAt, true, z2, f3 - childAt.getLeft(), f4 - childAt.getTop())) {
                        return true;
                    }
                }
            }
        }
        if (z) {
            if (view.canScrollHorizontally(z2 ? -1 : 1)) {
                return true;
            }
        }
        return false;
    }
}
