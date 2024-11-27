package android.view;

/* loaded from: classes4.dex */
public final class MotionPredictor {
    private final boolean mIsPredictionEnabled;
    private final long mPtr;

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetNativeMotionPredictorFinalizer();

    private static native long nativeInitialize(int i);

    private static native boolean nativeIsPredictionAvailable(long j, int i, int i2);

    private static native android.view.MotionEvent nativePredict(long j, long j2);

    private static native void nativeRecord(long j, android.view.MotionEvent motionEvent);

    private static class RegistryHolder {
        public static final libcore.util.NativeAllocationRegistry REGISTRY = libcore.util.NativeAllocationRegistry.createMalloced(android.view.MotionPredictor.class.getClassLoader(), android.view.MotionPredictor.nativeGetNativeMotionPredictorFinalizer());

        private RegistryHolder() {
        }
    }

    public MotionPredictor(android.content.Context context) {
        this(context.getResources().getBoolean(com.android.internal.R.bool.config_enableMotionPrediction), context.getResources().getInteger(com.android.internal.R.integer.config_motionPredictionOffsetNanos));
    }

    public MotionPredictor(boolean z, int i) {
        this.mIsPredictionEnabled = z;
        this.mPtr = nativeInitialize(i);
        android.view.MotionPredictor.RegistryHolder.REGISTRY.registerNativeAllocation(this, this.mPtr);
    }

    public void record(android.view.MotionEvent motionEvent) {
        if (!this.mIsPredictionEnabled) {
            return;
        }
        nativeRecord(this.mPtr, motionEvent);
    }

    public android.view.MotionEvent predict(long j) {
        if (!this.mIsPredictionEnabled) {
            return null;
        }
        return nativePredict(this.mPtr, j);
    }

    public boolean isPredictionAvailable(int i, int i2) {
        return this.mIsPredictionEnabled && nativeIsPredictionAvailable(this.mPtr, i, i2);
    }
}
