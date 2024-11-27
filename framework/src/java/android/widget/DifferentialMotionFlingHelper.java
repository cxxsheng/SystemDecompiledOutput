package android.widget;

/* loaded from: classes4.dex */
public class DifferentialMotionFlingHelper {
    private final android.content.Context mContext;
    private final int[] mFlingVelocityThresholds;
    private float mLastFlingVelocity;
    private int mLastProcessedAxis;
    private int mLastProcessedDeviceId;
    private int mLastProcessedSource;
    private final android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget mTarget;
    private final android.widget.DifferentialMotionFlingHelper.DifferentialVelocityProvider mVelocityProvider;
    private final android.widget.DifferentialMotionFlingHelper.FlingVelocityThresholdCalculator mVelocityThresholdCalculator;
    private android.view.VelocityTracker mVelocityTracker;
    private final android.widget.flags.FeatureFlags mWidgetFeatureFlags;

    public interface DifferentialMotionFlingTarget {
        float getScaledScrollFactor();

        boolean startDifferentialMotionFling(float f);

        void stopDifferentialMotionFling();
    }

    public interface DifferentialVelocityProvider {
        float getCurrentVelocity(android.view.VelocityTracker velocityTracker, android.view.MotionEvent motionEvent, int i);
    }

    public interface FlingVelocityThresholdCalculator {
        void calculateFlingVelocityThresholds(android.content.Context context, int[] iArr, android.view.MotionEvent motionEvent, int i);
    }

    public DifferentialMotionFlingHelper(android.content.Context context, android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget differentialMotionFlingTarget) {
        this(context, differentialMotionFlingTarget, new android.widget.DifferentialMotionFlingHelper.FlingVelocityThresholdCalculator() { // from class: android.widget.DifferentialMotionFlingHelper$$ExternalSyntheticLambda0
            @Override // android.widget.DifferentialMotionFlingHelper.FlingVelocityThresholdCalculator
            public final void calculateFlingVelocityThresholds(android.content.Context context2, int[] iArr, android.view.MotionEvent motionEvent, int i) {
                android.widget.DifferentialMotionFlingHelper.calculateFlingVelocityThresholds(context2, iArr, motionEvent, i);
            }
        }, new android.widget.DifferentialMotionFlingHelper.DifferentialVelocityProvider() { // from class: android.widget.DifferentialMotionFlingHelper$$ExternalSyntheticLambda1
            @Override // android.widget.DifferentialMotionFlingHelper.DifferentialVelocityProvider
            public final float getCurrentVelocity(android.view.VelocityTracker velocityTracker, android.view.MotionEvent motionEvent, int i) {
                float currentVelocity;
                currentVelocity = android.widget.DifferentialMotionFlingHelper.getCurrentVelocity(velocityTracker, motionEvent, i);
                return currentVelocity;
            }
        }, new android.widget.flags.FeatureFlagsImpl());
    }

    public DifferentialMotionFlingHelper(android.content.Context context, android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget differentialMotionFlingTarget, android.widget.DifferentialMotionFlingHelper.FlingVelocityThresholdCalculator flingVelocityThresholdCalculator, android.widget.DifferentialMotionFlingHelper.DifferentialVelocityProvider differentialVelocityProvider, android.widget.flags.FeatureFlags featureFlags) {
        this.mLastProcessedAxis = -1;
        this.mLastProcessedSource = -1;
        this.mLastProcessedDeviceId = -1;
        this.mFlingVelocityThresholds = new int[]{Integer.MAX_VALUE, 0};
        this.mContext = context;
        this.mTarget = differentialMotionFlingTarget;
        this.mVelocityThresholdCalculator = flingVelocityThresholdCalculator;
        this.mVelocityProvider = differentialVelocityProvider;
        this.mWidgetFeatureFlags = featureFlags;
    }

    public void onMotionEvent(android.view.MotionEvent motionEvent, int i) {
        if (!this.mWidgetFeatureFlags.enablePlatformWidgetDifferentialMotionFling()) {
            return;
        }
        boolean calculateFlingVelocityThresholds = calculateFlingVelocityThresholds(motionEvent, i);
        if (this.mFlingVelocityThresholds[0] == Integer.MAX_VALUE) {
            recycleVelocityTracker();
            return;
        }
        float currentVelocity = getCurrentVelocity(motionEvent, i) * this.mTarget.getScaledScrollFactor();
        float signum = java.lang.Math.signum(currentVelocity);
        if (calculateFlingVelocityThresholds || (signum != java.lang.Math.signum(this.mLastFlingVelocity) && signum != 0.0f)) {
            this.mTarget.stopDifferentialMotionFling();
        }
        if (java.lang.Math.abs(currentVelocity) < this.mFlingVelocityThresholds[0]) {
            return;
        }
        float max = java.lang.Math.max(-this.mFlingVelocityThresholds[1], java.lang.Math.min(currentVelocity, this.mFlingVelocityThresholds[1]));
        this.mLastFlingVelocity = this.mTarget.startDifferentialMotionFling(max) ? max : 0.0f;
    }

    private boolean calculateFlingVelocityThresholds(android.view.MotionEvent motionEvent, int i) {
        int source = motionEvent.getSource();
        int deviceId = motionEvent.getDeviceId();
        if (this.mLastProcessedSource != source || this.mLastProcessedDeviceId != deviceId || this.mLastProcessedAxis != i) {
            this.mVelocityThresholdCalculator.calculateFlingVelocityThresholds(this.mContext, this.mFlingVelocityThresholds, motionEvent, i);
            this.mLastProcessedSource = source;
            this.mLastProcessedDeviceId = deviceId;
            this.mLastProcessedAxis = i;
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void calculateFlingVelocityThresholds(android.content.Context context, int[] iArr, android.view.MotionEvent motionEvent, int i) {
        int source = motionEvent.getSource();
        int deviceId = motionEvent.getDeviceId();
        android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(context);
        iArr[0] = viewConfiguration.getScaledMinimumFlingVelocity(deviceId, i, source);
        iArr[1] = viewConfiguration.getScaledMaximumFlingVelocity(deviceId, i, source);
    }

    private float getCurrentVelocity(android.view.MotionEvent motionEvent, int i) {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = android.view.VelocityTracker.obtain();
        }
        return this.mVelocityProvider.getCurrentVelocity(this.mVelocityTracker, motionEvent, i);
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float getCurrentVelocity(android.view.VelocityTracker velocityTracker, android.view.MotionEvent motionEvent, int i) {
        velocityTracker.addMovement(motionEvent);
        velocityTracker.computeCurrentVelocity(1000);
        return velocityTracker.getAxisVelocity(i);
    }
}
