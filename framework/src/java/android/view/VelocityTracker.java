package android.view;

/* loaded from: classes4.dex */
public final class VelocityTracker {
    private static final int ACTIVE_POINTER_ID = -1;
    public static final int VELOCITY_TRACKER_STRATEGY_DEFAULT = -1;
    public static final int VELOCITY_TRACKER_STRATEGY_IMPULSE = 0;
    public static final int VELOCITY_TRACKER_STRATEGY_INT1 = 7;
    public static final int VELOCITY_TRACKER_STRATEGY_INT2 = 8;
    public static final int VELOCITY_TRACKER_STRATEGY_LEGACY = 9;
    public static final int VELOCITY_TRACKER_STRATEGY_LSQ1 = 1;
    public static final int VELOCITY_TRACKER_STRATEGY_LSQ2 = 2;
    public static final int VELOCITY_TRACKER_STRATEGY_LSQ3 = 3;
    public static final int VELOCITY_TRACKER_STRATEGY_WLSQ2_CENTRAL = 5;
    public static final int VELOCITY_TRACKER_STRATEGY_WLSQ2_DELTA = 4;
    public static final int VELOCITY_TRACKER_STRATEGY_WLSQ2_RECENT = 6;
    private long mPtr;
    private final int mStrategy;
    private static final android.util.Pools.SynchronizedPool<android.view.VelocityTracker> sPool = new android.util.Pools.SynchronizedPool<>(2);
    private static final java.util.Map<java.lang.String, java.lang.Integer> STRATEGIES = new android.util.ArrayMap();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VelocityTrackableMotionEventAxis {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VelocityTrackerStrategy {
    }

    private static native void nativeAddMovement(long j, android.view.MotionEvent motionEvent);

    private static native void nativeClear(long j);

    private static native void nativeComputeCurrentVelocity(long j, int i, float f);

    private static native void nativeDispose(long j);

    private static native float nativeGetVelocity(long j, int i, int i2);

    private static native long nativeInitialize(int i);

    private static native boolean nativeIsAxisSupported(int i);

    static {
        STRATEGIES.put("impulse", 0);
        STRATEGIES.put("lsq1", 1);
        STRATEGIES.put("lsq2", 2);
        STRATEGIES.put("lsq3", 3);
        STRATEGIES.put("wlsq2-delta", 4);
        STRATEGIES.put("wlsq2-central", 5);
        STRATEGIES.put("wlsq2-recent", 6);
        STRATEGIES.put("int1", 7);
        STRATEGIES.put("int2", 8);
        STRATEGIES.put("legacy", 9);
    }

    private static int toStrategyId(java.lang.String str) {
        if (STRATEGIES.containsKey(str)) {
            return STRATEGIES.get(str).intValue();
        }
        return -1;
    }

    public static android.view.VelocityTracker obtain() {
        android.view.VelocityTracker acquire = sPool.acquire();
        if (acquire != null) {
            return acquire;
        }
        return new android.view.VelocityTracker(-1);
    }

    @java.lang.Deprecated
    public static android.view.VelocityTracker obtain(java.lang.String str) {
        if (str == null) {
            return obtain();
        }
        return new android.view.VelocityTracker(toStrategyId(str));
    }

    public static android.view.VelocityTracker obtain(int i) {
        return new android.view.VelocityTracker(i);
    }

    public void recycle() {
        if (this.mStrategy == -1) {
            clear();
            sPool.release(this);
        }
    }

    public int getStrategyId() {
        return this.mStrategy;
    }

    private VelocityTracker(int i) {
        if (i == -1) {
            java.lang.String velocityTrackerStrategy = android.hardware.input.InputManagerGlobal.getInstance().getVelocityTrackerStrategy();
            if (velocityTrackerStrategy == null || velocityTrackerStrategy.isEmpty()) {
                this.mStrategy = i;
            } else {
                this.mStrategy = toStrategyId(velocityTrackerStrategy);
            }
        } else {
            this.mStrategy = i;
        }
        this.mPtr = nativeInitialize(this.mStrategy);
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mPtr != 0) {
                nativeDispose(this.mPtr);
                this.mPtr = 0L;
            }
        } finally {
            super.finalize();
        }
    }

    public boolean isAxisSupported(int i) {
        return nativeIsAxisSupported(i);
    }

    public void clear() {
        nativeClear(this.mPtr);
    }

    public void addMovement(android.view.MotionEvent motionEvent) {
        if (motionEvent == null) {
            throw new java.lang.IllegalArgumentException("event must not be null");
        }
        nativeAddMovement(this.mPtr, motionEvent);
    }

    public void computeCurrentVelocity(int i) {
        nativeComputeCurrentVelocity(this.mPtr, i, Float.MAX_VALUE);
    }

    public void computeCurrentVelocity(int i, float f) {
        nativeComputeCurrentVelocity(this.mPtr, i, f);
    }

    public float getXVelocity() {
        return getXVelocity(-1);
    }

    public float getYVelocity() {
        return getYVelocity(-1);
    }

    public float getXVelocity(int i) {
        return nativeGetVelocity(this.mPtr, 0, i);
    }

    public float getYVelocity(int i) {
        return nativeGetVelocity(this.mPtr, 1, i);
    }

    public float getAxisVelocity(int i, int i2) {
        return nativeGetVelocity(this.mPtr, i, i2);
    }

    public float getAxisVelocity(int i) {
        return nativeGetVelocity(this.mPtr, i, -1);
    }
}
