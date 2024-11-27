package com.android.server.accessibility.gestures;

/* loaded from: classes.dex */
class Swipe extends com.android.server.accessibility.gestures.GestureMatcher {
    private static final float ANGLE_THRESHOLD = 0.0f;
    public static final int DOWN = 3;
    public static final int GESTURE_CONFIRM_CM = 1;
    public static final int LEFT = 0;
    public static final long MAX_TIME_TO_CONTINUE_SWIPE_MS = 350;
    public static final long MAX_TIME_TO_START_SWIPE_MS = 150;
    private static final float MIN_CM_BETWEEN_SAMPLES = 0.25f;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    private long mBaseTime;
    private float mBaseX;
    private float mBaseY;
    private int[] mDirections;
    private final float mGestureDetectionThresholdPixels;
    private final float mMinPixelsBetweenSamplesX;
    private final float mMinPixelsBetweenSamplesY;
    private float mPreviousGestureX;
    private float mPreviousGestureY;
    private final java.util.ArrayList<android.graphics.PointF> mStrokeBuffer;
    private int mTouchSlop;

    Swipe(android.content.Context context, int i, int i2, com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener stateChangeListener) {
        this(context, new int[]{i}, i2, stateChangeListener);
    }

    Swipe(android.content.Context context, int i, int i2, int i3, com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener stateChangeListener) {
        this(context, new int[]{i, i2}, i3, stateChangeListener);
    }

    private Swipe(android.content.Context context, int[] iArr, int i, com.android.server.accessibility.gestures.GestureMatcher.StateChangeListener stateChangeListener) {
        super(i, new android.os.Handler(context.getMainLooper()), stateChangeListener);
        this.mStrokeBuffer = new java.util.ArrayList<>(100);
        this.mDirections = iArr;
        android.util.DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.mGestureDetectionThresholdPixels = android.util.TypedValue.applyDimension(5, com.android.server.accessibility.gestures.GestureUtils.MM_PER_CM, displayMetrics) * 1.0f;
        float f = displayMetrics.xdpi / 2.54f;
        float f2 = displayMetrics.ydpi / 2.54f;
        this.mMinPixelsBetweenSamplesX = f * MIN_CM_BETWEEN_SAMPLES;
        this.mMinPixelsBetweenSamplesY = f2 * MIN_CM_BETWEEN_SAMPLES;
        this.mTouchSlop = android.view.ViewConfiguration.get(context).getScaledTouchSlop();
        clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public void clear() {
        this.mBaseX = Float.NaN;
        this.mBaseY = Float.NaN;
        this.mBaseTime = 0L;
        this.mPreviousGestureX = Float.NaN;
        this.mPreviousGestureY = Float.NaN;
        this.mStrokeBuffer.clear();
        super.clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (java.lang.Float.isNaN(this.mBaseX) && java.lang.Float.isNaN(this.mBaseY)) {
            this.mBaseX = motionEvent2.getX();
            this.mBaseY = motionEvent2.getY();
            this.mBaseTime = motionEvent2.getEventTime();
            this.mPreviousGestureX = this.mBaseX;
            this.mPreviousGestureY = this.mBaseY;
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onMove(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        float x = motionEvent2.getX();
        float y = motionEvent2.getY();
        long eventTime = motionEvent2.getEventTime();
        float abs = java.lang.Math.abs(x - this.mPreviousGestureX);
        float abs2 = java.lang.Math.abs(y - this.mPreviousGestureY);
        double hypot = java.lang.Math.hypot(java.lang.Math.abs(x - this.mBaseX), java.lang.Math.abs(y - this.mBaseY));
        long j = eventTime - this.mBaseTime;
        if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
            android.util.Slog.d(getGestureName(), "moveDelta:" + java.lang.Double.toString(hypot) + " mGestureDetectionThreshold: " + java.lang.Float.toString(this.mGestureDetectionThresholdPixels));
        }
        if (getState() == 0) {
            if (hypot < this.mTouchSlop) {
                return;
            }
            if (this.mStrokeBuffer.size() == 0) {
                if (toDirection(x - this.mBaseX, y - this.mBaseY) == this.mDirections[0]) {
                    this.mStrokeBuffer.add(new android.graphics.PointF(this.mBaseX, this.mBaseY));
                } else {
                    cancelGesture(motionEvent, motionEvent2, i);
                    return;
                }
            }
        }
        if (hypot > this.mGestureDetectionThresholdPixels) {
            this.mBaseX = x;
            this.mBaseY = y;
            this.mBaseTime = eventTime;
            startGesture(motionEvent, motionEvent2, i);
        } else if (getState() == 0) {
            if (j > 150) {
                cancelGesture(motionEvent, motionEvent2, i);
                return;
            }
        } else if (getState() == 1 && j > 350) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        if (abs >= this.mMinPixelsBetweenSamplesX || abs2 >= this.mMinPixelsBetweenSamplesY) {
            this.mPreviousGestureX = x;
            this.mPreviousGestureY = y;
            this.mStrokeBuffer.add(new android.graphics.PointF(x, y));
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (getState() != 1) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        float x = motionEvent2.getX();
        float y = motionEvent2.getY();
        float abs = java.lang.Math.abs(x - this.mPreviousGestureX);
        float abs2 = java.lang.Math.abs(y - this.mPreviousGestureY);
        if (abs >= this.mMinPixelsBetweenSamplesX || abs2 >= this.mMinPixelsBetweenSamplesY) {
            this.mStrokeBuffer.add(new android.graphics.PointF(x, y));
        }
        recognizeGesture(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onPointerDown(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        cancelGesture(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    protected void onPointerUp(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        cancelGesture(motionEvent, motionEvent2, i);
    }

    private void recognizeGesture(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        if (this.mStrokeBuffer.size() < 2) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        java.util.ArrayList<android.graphics.PointF> arrayList = new java.util.ArrayList<>();
        android.graphics.PointF pointF = this.mStrokeBuffer.get(0);
        arrayList.add(pointF);
        android.graphics.PointF pointF2 = null;
        int i2 = 0;
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        for (int i3 = 1; i3 < this.mStrokeBuffer.size(); i3++) {
            pointF2 = this.mStrokeBuffer.get(i3);
            if (i2 > 0) {
                float f4 = i2;
                float f5 = f / f4;
                float f6 = f2 / f4;
                android.graphics.PointF pointF3 = new android.graphics.PointF((f3 * f5) + pointF.x, (f3 * f6) + pointF.y);
                float f7 = pointF2.x - pointF3.x;
                float f8 = pointF2.y - pointF3.y;
                float sqrt = (float) java.lang.Math.sqrt((f7 * f7) + (f8 * f8));
                if ((f5 * (f7 / sqrt)) + (f6 * (f8 / sqrt)) < 0.0f) {
                    arrayList.add(pointF3);
                    f = 0.0f;
                    f2 = 0.0f;
                    pointF = pointF3;
                    i2 = 0;
                }
            }
            float f9 = pointF2.x - pointF.x;
            float f10 = pointF2.y - pointF.y;
            f3 = (float) java.lang.Math.sqrt((f9 * f9) + (f10 * f10));
            i2++;
            f += f9 / f3;
            f2 += f10 / f3;
        }
        arrayList.add(pointF2);
        if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
            android.util.Slog.d(getGestureName(), "path=" + arrayList.toString());
        }
        recognizeGesturePath(motionEvent, motionEvent2, i, arrayList);
    }

    private void recognizeGesturePath(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i, java.util.ArrayList<android.graphics.PointF> arrayList) {
        motionEvent.getDisplayId();
        if (arrayList.size() != this.mDirections.length + 1) {
            cancelGesture(motionEvent, motionEvent2, i);
            return;
        }
        int i2 = 0;
        while (i2 < arrayList.size() - 1) {
            android.graphics.PointF pointF = arrayList.get(i2);
            int i3 = i2 + 1;
            android.graphics.PointF pointF2 = arrayList.get(i3);
            int direction = toDirection(pointF2.x - pointF.x, pointF2.y - pointF.y);
            if (direction == this.mDirections[i2]) {
                i2 = i3;
            } else {
                if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
                    android.util.Slog.d(getGestureName(), "Found direction " + directionToString(direction) + " when expecting " + directionToString(this.mDirections[i2]));
                }
                cancelGesture(motionEvent, motionEvent2, i);
                return;
            }
        }
        if (com.android.server.accessibility.gestures.TouchExplorer.DEBUG) {
            android.util.Slog.d(getGestureName(), "Completed.");
        }
        completeGesture(motionEvent, motionEvent2, i);
    }

    private static int toDirection(float f, float f2) {
        return java.lang.Math.abs(f) > java.lang.Math.abs(f2) ? f < 0.0f ? 0 : 1 : f2 < 0.0f ? 2 : 3;
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
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Swipe ");
        sb.append(directionToString(this.mDirections[0]));
        for (int i = 1; i < this.mDirections.length; i++) {
            sb.append(" and ");
            sb.append(directionToString(this.mDirections[i]));
        }
        return sb.toString();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(super.toString());
        if (getState() != 3) {
            sb.append(", mBaseX: ");
            sb.append(this.mBaseX);
            sb.append(", mBaseY: ");
            sb.append(this.mBaseY);
            sb.append(", mGestureDetectionThreshold:");
            sb.append(this.mGestureDetectionThresholdPixels);
            sb.append(", mMinPixelsBetweenSamplesX:");
            sb.append(this.mMinPixelsBetweenSamplesX);
            sb.append(", mMinPixelsBetweenSamplesY:");
            sb.append(this.mMinPixelsBetweenSamplesY);
        }
        return sb.toString();
    }
}
