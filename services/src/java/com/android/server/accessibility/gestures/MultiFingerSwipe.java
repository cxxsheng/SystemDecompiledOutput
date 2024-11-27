package com.android.server.accessibility.gestures;

/* loaded from: classes.dex */
class MultiFingerSwipe extends com.android.server.accessibility.gestures.GestureMatcher {
    public static final int DOWN = 3;
    public static final int LEFT = 0;
    private static final float MIN_CM_BETWEEN_SAMPLES = 0.25f;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    private android.graphics.PointF[] mBase;
    private int mCurrentFingerCount;
    private int mDirection;
    private final float mMinPixelsBetweenSamplesX;
    private final float mMinPixelsBetweenSamplesY;
    private int[] mPointerIds;
    private android.graphics.PointF[] mPreviousGesturePoint;
    private final java.util.ArrayList<android.graphics.PointF>[] mStrokeBuffers;
    private int mTargetFingerCount;
    private boolean mTargetFingerCountReached;
    private int mTouchSlop;

    MultiFingerSwipe(android.content.Context context, int i, int i2, int i3, com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener stateChangeListener) {
        super(i3, new android.os.Handler(context.getMainLooper()), stateChangeListener);
        this.mTargetFingerCountReached = false;
        this.mTargetFingerCount = i;
        this.mPointerIds = new int[this.mTargetFingerCount];
        this.mBase = new android.graphics.PointF[this.mTargetFingerCount];
        this.mPreviousGesturePoint = new android.graphics.PointF[this.mTargetFingerCount];
        this.mStrokeBuffers = new java.util.ArrayList[this.mTargetFingerCount];
        this.mDirection = i2;
        android.util.DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float f = displayMetrics.xdpi / com.android.server.accessibility.gestures.GestureUtils.CM_PER_INCH;
        float f2 = displayMetrics.ydpi / com.android.server.accessibility.gestures.GestureUtils.CM_PER_INCH;
        this.mMinPixelsBetweenSamplesX = f * MIN_CM_BETWEEN_SAMPLES;
        this.mMinPixelsBetweenSamplesY = f2 * MIN_CM_BETWEEN_SAMPLES;
        this.mTouchSlop = android.view.ViewConfiguration.get(context).getScaledTouchSlop();
        clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public void clear() {
        this.mTargetFingerCountReached = false;
        this.mCurrentFingerCount = 0;
        for (int i = 0; i < this.mTargetFingerCount; i++) {
            this.mPointerIds[i] = -1;
            if (this.mBase[i] == null) {
                this.mBase[i] = new android.graphics.PointF();
            }
            this.mBase[i].x = Float.NaN;
            this.mBase[i].y = Float.NaN;
            if (this.mPreviousGesturePoint[i] == null) {
                this.mPreviousGesturePoint[i] = new android.graphics.PointF();
            }
            this.mPreviousGesturePoint[i].x = Float.NaN;
            this.mPreviousGesturePoint[i].y = Float.NaN;
            if (this.mStrokeBuffers[i] == null) {
                this.mStrokeBuffers[i] = new java.util.ArrayList<>(100);
            }
            this.mStrokeBuffers[i].clear();
        }
        super.clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mCurrentFingerCount > 0) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        this.mCurrentFingerCount = 1;
        int actionIndex = com.android.server.accessibility.gestures.GestureUtils.getActionIndex(motionEvent2);
        int pointerId = motionEvent2.getPointerId(actionIndex);
        int pointerCount = motionEvent2.getPointerCount() - 1;
        if (pointerId < 0) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        if (this.mPointerIds[pointerCount] != -1) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        this.mPointerIds[pointerCount] = pointerId;
        if (java.lang.Float.isNaN(this.mBase[pointerCount].x) && java.lang.Float.isNaN(this.mBase[pointerCount].y)) {
            float x = motionEvent2.getX(actionIndex);
            float y = motionEvent2.getY(actionIndex);
            if (x < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || y < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                cancelGesture(motionEvent, motionEvent2, i);
                return;
            }
            this.mBase[pointerCount].x = x;
            this.mBase[pointerCount].y = y;
            this.mPreviousGesturePoint[pointerCount].x = x;
            this.mPreviousGesturePoint[pointerCount].y = y;
            return;
        }
        cancelGesture(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onPointerDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (motionEvent.getPointerCount() > this.mTargetFingerCount) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        this.mCurrentFingerCount++;
        if (this.mCurrentFingerCount != motionEvent2.getPointerCount()) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        if (this.mCurrentFingerCount == this.mTargetFingerCount) {
            this.mTargetFingerCountReached = true;
        }
        int actionIndex = com.android.server.accessibility.gestures.GestureUtils.getActionIndex(motionEvent2);
        int pointerId = motionEvent2.getPointerId(actionIndex);
        if (pointerId < 0) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        int i2 = this.mCurrentFingerCount - 1;
        if (this.mPointerIds[i2] != -1) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        this.mPointerIds[i2] = pointerId;
        if (java.lang.Float.isNaN(this.mBase[i2].x) && java.lang.Float.isNaN(this.mBase[i2].y)) {
            float x = motionEvent2.getX(actionIndex);
            float y = motionEvent2.getY(actionIndex);
            if (x < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || y < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                cancelGesture(motionEvent, motionEvent2, i);
                return;
            }
            this.mBase[i2].x = x;
            this.mBase[i2].y = y;
            this.mPreviousGesturePoint[i2].x = x;
            this.mPreviousGesturePoint[i2].y = y;
            return;
        }
        cancelGesture(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onPointerUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (!this.mTargetFingerCountReached) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        this.mCurrentFingerCount--;
        int actionIndex = com.android.server.accessibility.gestures.GestureUtils.getActionIndex(motionEvent);
        int pointerId = motionEvent.getPointerId(actionIndex);
        if (pointerId < 0) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        int binarySearch = java.util.Arrays.binarySearch(this.mPointerIds, pointerId);
        if (binarySearch < 0) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        float x = motionEvent2.getX(actionIndex);
        float y = motionEvent2.getY(actionIndex);
        if (x < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || y < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        float abs = java.lang.Math.abs(x - this.mPreviousGesturePoint[binarySearch].x);
        float abs2 = java.lang.Math.abs(y - this.mPreviousGesturePoint[binarySearch].y);
        if (abs >= this.mMinPixelsBetweenSamplesX || abs2 >= this.mMinPixelsBetweenSamplesY) {
            this.mStrokeBuffers[binarySearch].add(new android.graphics.PointF(x, y));
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onMove(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        for (int i2 = 0; i2 < this.mTargetFingerCount; i2++) {
            if (this.mPointerIds[i2] != -1) {
                if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                    android.util.Slog.d(getGestureName(), "Processing move on finger " + i2);
                }
                int findPointerIndex = motionEvent2.findPointerIndex(this.mPointerIds[i2]);
                if (findPointerIndex < 0) {
                    if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                        android.util.Slog.d(getGestureName(), "Finger " + i2 + " not found in this event. skipping.");
                    }
                } else {
                    float x = motionEvent2.getX(findPointerIndex);
                    float y = motionEvent2.getY(findPointerIndex);
                    if (x < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || y < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                        cancelGesture(motionEvent, motionEvent2, i);
                        return;
                    }
                    float abs = java.lang.Math.abs(x - this.mPreviousGesturePoint[i2].x);
                    float abs2 = java.lang.Math.abs(y - this.mPreviousGesturePoint[i2].y);
                    double hypot = java.lang.Math.hypot(java.lang.Math.abs(x - this.mBase[i2].x), java.lang.Math.abs(y - this.mBase[i2].y));
                    if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                        android.util.Slog.d(getGestureName(), "moveDelta:" + hypot);
                    }
                    if (getState() == 0) {
                        if (hypot < this.mTargetFingerCount * this.mTouchSlop) {
                            continue;
                        } else {
                            if (this.mCurrentFingerCount != this.mTargetFingerCount) {
                                cancelGesture(motionEvent, motionEvent2, i);
                                return;
                            }
                            if (toDirection(x - this.mBase[i2].x, y - this.mBase[i2].y) != this.mDirection) {
                                cancelGesture(motionEvent, motionEvent2, i);
                                return;
                            }
                            startGesture(motionEvent, motionEvent2, i);
                            for (int i3 = 0; i3 < this.mTargetFingerCount; i3++) {
                                this.mStrokeBuffers[i3].add(new android.graphics.PointF(this.mBase[i3]));
                            }
                        }
                    } else if (getState() != 1) {
                        continue;
                    } else if (toDirection(x - this.mBase[i2].x, y - this.mBase[i2].y) != this.mDirection) {
                        cancelGesture(motionEvent, motionEvent2, i);
                        return;
                    } else if (abs >= this.mMinPixelsBetweenSamplesX || abs2 >= this.mMinPixelsBetweenSamplesY) {
                        this.mPreviousGesturePoint[i2].x = x;
                        this.mPreviousGesturePoint[i2].y = y;
                        this.mStrokeBuffers[i2].add(new android.graphics.PointF(x, y));
                    }
                }
            }
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (getState() != 1) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        this.mCurrentFingerCount = 0;
        int actionIndex = com.android.server.accessibility.gestures.GestureUtils.getActionIndex(motionEvent);
        int binarySearch = java.util.Arrays.binarySearch(this.mPointerIds, motionEvent.getPointerId(actionIndex));
        if (binarySearch < 0) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        float x = motionEvent2.getX(actionIndex);
        float y = motionEvent2.getY(actionIndex);
        if (x < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || y < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        float abs = java.lang.Math.abs(x - this.mPreviousGesturePoint[binarySearch].x);
        float abs2 = java.lang.Math.abs(y - this.mPreviousGesturePoint[binarySearch].y);
        if (abs >= this.mMinPixelsBetweenSamplesX || abs2 >= this.mMinPixelsBetweenSamplesY) {
            this.mStrokeBuffers[binarySearch].add(new android.graphics.PointF(x, y));
        }
        recognizeGesture(motionEvent, motionEvent2, i);
    }

    private void recognizeGesture(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        for (int i2 = 0; i2 < this.mTargetFingerCount; i2++) {
            if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                android.util.Slog.d(getGestureName(), "Recognizing finger: " + i2);
            }
            if (this.mStrokeBuffers[i2].size() < 2) {
                android.util.Slog.d(getGestureName(), "Too few points.");
                cancelGesture(motionEvent, motionEvent2, i);
                return;
            }
            java.util.ArrayList<android.graphics.PointF> arrayList = this.mStrokeBuffers[i2];
            if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                android.util.Slog.d(getGestureName(), "path=" + arrayList.toString());
            }
            if (!recognizeGesturePath(motionEvent, motionEvent2, i, arrayList)) {
                cancelGesture(motionEvent, motionEvent2, i);
                return;
            }
        }
        completeGesture(motionEvent, motionEvent2, i);
    }

    private boolean recognizeGesturePath(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i, java.util.ArrayList<android.graphics.PointF> arrayList) {
        motionEvent.getDisplayId();
        int i2 = 0;
        while (i2 < arrayList.size() - 1) {
            android.graphics.PointF pointF = arrayList.get(i2);
            i2++;
            android.graphics.PointF pointF2 = arrayList.get(i2);
            int direction = toDirection(pointF2.x - pointF.x, pointF2.y - pointF.y);
            if (direction != this.mDirection) {
                if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                    android.util.Slog.d(getGestureName(), "Found direction " + directionToString(direction) + " when expecting " + directionToString(this.mDirection));
                }
                return false;
            }
        }
        if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
            android.util.Slog.d(getGestureName(), "Completed.");
        }
        return true;
    }

    private static int toDirection(float f, float f2) {
        return java.lang.Math.abs(f) > java.lang.Math.abs(f2) ? f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? 0 : 1 : f2 < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? 2 : 3;
    }

    public static java.lang.String directionToString(int i) {
        switch (i) {
            case 0:
                return "left";
            case 1:
                return "right";
            case 2:
                return android.net.INetd.IF_STATE_UP;
            case 3:
                return android.net.INetd.IF_STATE_DOWN;
            default:
                return "Unknown Direction";
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected java.lang.String getGestureName() {
        return this.mTargetFingerCount + "-finger Swipe " + directionToString(this.mDirection);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(super.toString());
        if (getState() != 3) {
            sb.append(", mBase: ");
            sb.append(java.util.Arrays.toString(this.mBase));
            sb.append(", mMinPixelsBetweenSamplesX:");
            sb.append(this.mMinPixelsBetweenSamplesX);
            sb.append(", mMinPixelsBetweenSamplesY:");
            sb.append(this.mMinPixelsBetweenSamplesY);
        }
        return sb.toString();
    }
}
