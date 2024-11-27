package com.android.server.accessibility.gestures;

/* loaded from: classes.dex */
public class MultiFingerMultiTap extends com.android.server.accessibility.gestures.GestureMatcher {
    private android.graphics.PointF[] mBases;
    protected int mCompletedTapCount;
    private int mDoubleTapSlop;
    private java.util.ArrayList<android.graphics.PointF> mExcludedPointsForDownSlopChecked;
    protected boolean mIsTargetFingerCountReached;
    final int mTargetFingerCount;
    final int mTargetTapCount;
    private int mTouchSlop;

    public MultiFingerMultiTap(android.content.Context context, int i, int i2, int i3, com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener stateChangeListener) {
        super(i3, new android.os.Handler(context.getMainLooper()), stateChangeListener);
        this.mIsTargetFingerCountReached = false;
        com.android.internal.util.Preconditions.checkArgument(i >= 2);
        com.android.internal.util.Preconditions.checkArgumentPositive(i2, "Tap count must greater than 0.");
        this.mTargetTapCount = i2;
        this.mTargetFingerCount = i;
        this.mDoubleTapSlop = android.view.ViewConfiguration.get(context).getScaledDoubleTapSlop() * i;
        this.mTouchSlop = android.view.ViewConfiguration.get(context).getScaledTouchSlop() * i;
        this.mBases = new android.graphics.PointF[this.mTargetFingerCount];
        for (int i4 = 0; i4 < this.mBases.length; i4++) {
            this.mBases[i4] = new android.graphics.PointF();
        }
        this.mExcludedPointsForDownSlopChecked = new java.util.ArrayList<>(this.mTargetFingerCount);
        clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public void clear() {
        this.mCompletedTapCount = 0;
        this.mIsTargetFingerCountReached = false;
        for (int i = 0; i < this.mBases.length; i++) {
            this.mBases[i].set(Float.NaN, Float.NaN);
        }
        this.mExcludedPointsForDownSlopChecked.clear();
        super.clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mCompletedTapCount == this.mTargetTapCount) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        cancelAfterTapTimeout(motionEvent, motionEvent2, i);
        if (this.mCompletedTapCount == 0) {
            initBaseLocation(motionEvent2);
            return;
        }
        android.graphics.PointF findNearestPoint = findNearestPoint(motionEvent2, this.mDoubleTapSlop, true);
        if (findNearestPoint != null) {
            int actionIndex = motionEvent.getActionIndex();
            findNearestPoint.set(motionEvent.getX(actionIndex), motionEvent.getY(actionIndex));
        } else {
            cancelGesture(motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        cancelAfterDoubleTapTimeout(motionEvent, motionEvent2, i);
        android.graphics.PointF findNearestPoint = findNearestPoint(motionEvent2, this.mTouchSlop, false);
        if ((getState() == 1 || getState() == 0) && findNearestPoint != null) {
            if (this.mIsTargetFingerCountReached) {
                this.mCompletedTapCount++;
                this.mIsTargetFingerCountReached = false;
                this.mExcludedPointsForDownSlopChecked.clear();
            }
            if (this.mCompletedTapCount == 1) {
                startGesture(motionEvent, motionEvent2, i);
            }
            if (this.mCompletedTapCount == this.mTargetTapCount) {
                completeAfterDoubleTapTimeout(motionEvent, motionEvent2, i);
                return;
            }
            return;
        }
        cancelGesture(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onMove(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (findNearestPoint(motionEvent2, this.mTouchSlop, false) == null) {
            cancelGesture(motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onPointerDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        android.graphics.PointF findNearestPoint;
        cancelAfterTapTimeout(motionEvent, motionEvent2, i);
        int pointerCount = motionEvent.getPointerCount();
        if (pointerCount > this.mTargetFingerCount || this.mIsTargetFingerCountReached) {
            this.mIsTargetFingerCountReached = false;
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        if (this.mCompletedTapCount == 0) {
            findNearestPoint = initBaseLocation(motionEvent2);
        } else {
            findNearestPoint = findNearestPoint(motionEvent2, this.mDoubleTapSlop, true);
        }
        if ((getState() == 1 || getState() == 0) && findNearestPoint != null) {
            if (pointerCount == this.mTargetFingerCount) {
                this.mIsTargetFingerCountReached = true;
            }
            int actionIndex = motionEvent.getActionIndex();
            findNearestPoint.set(motionEvent.getX(actionIndex), motionEvent.getY(actionIndex));
            return;
        }
        cancelGesture(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onPointerUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (!this.mIsTargetFingerCountReached) {
            cancelGesture(motionEvent, motionEvent2, i);
        } else if (getState() == 1 || getState() == 0) {
            cancelAfterTapTimeout(motionEvent, motionEvent2, i);
        } else {
            cancelGesture(motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public java.lang.String getGestureName() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(this.mTargetFingerCount);
        sb.append("-Finger ");
        if (this.mTargetTapCount == 1) {
            sb.append("Single");
        } else if (this.mTargetTapCount == 2) {
            sb.append("Double");
        } else if (this.mTargetTapCount == 3) {
            sb.append("Triple");
        } else if (this.mTargetTapCount > 3) {
            sb.append(this.mTargetTapCount);
        }
        sb.append(" Tap");
        return sb.toString();
    }

    private android.graphics.PointF initBaseLocation(android.view.MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        android.graphics.PointF pointF = this.mBases[motionEvent.getPointerCount() - 1];
        if (java.lang.Float.isNaN(pointF.x) && java.lang.Float.isNaN(pointF.y)) {
            pointF.set(motionEvent.getX(actionIndex), motionEvent.getY(actionIndex));
        }
        return pointF;
    }

    private android.graphics.PointF findNearestPoint(android.view.MotionEvent motionEvent, float f, boolean z) {
        float f2 = Float.MAX_VALUE;
        android.graphics.PointF pointF = null;
        for (int i = 0; i < this.mBases.length; i++) {
            android.graphics.PointF pointF2 = this.mBases[i];
            if ((!java.lang.Float.isNaN(pointF2.x) || !java.lang.Float.isNaN(pointF2.y)) && (!z || !this.mExcludedPointsForDownSlopChecked.contains(pointF2))) {
                int actionIndex = motionEvent.getActionIndex();
                float x = pointF2.x - motionEvent.getX(actionIndex);
                float y = pointF2.y - motionEvent.getY(actionIndex);
                if (x == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && y == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    if (z) {
                        this.mExcludedPointsForDownSlopChecked.add(pointF2);
                    }
                    return pointF2;
                }
                float hypot = (float) java.lang.Math.hypot(x, y);
                if (f2 > hypot) {
                    pointF = pointF2;
                    f2 = hypot;
                }
            }
        }
        if (f2 >= f) {
            return null;
        }
        if (z) {
            this.mExcludedPointsForDownSlopChecked.add(pointF);
        }
        return pointF;
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(super.toString());
        if (getState() != 3) {
            sb.append(", CompletedTapCount: ");
            sb.append(this.mCompletedTapCount);
            sb.append(", IsTargetFingerCountReached: ");
            sb.append(this.mIsTargetFingerCountReached);
            sb.append(", Bases: ");
            sb.append(java.util.Arrays.toString(this.mBases));
            sb.append(", ExcludedPointsForDownSlopChecked: ");
            sb.append(this.mExcludedPointsForDownSlopChecked.toString());
        }
        return sb.toString();
    }
}
