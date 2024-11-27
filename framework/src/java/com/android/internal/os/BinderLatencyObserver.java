package com.android.internal.os;

/* loaded from: classes4.dex */
public class BinderLatencyObserver {
    public static final int BUCKET_COUNT_DEFAULT = 100;
    public static final float BUCKET_SCALE_FACTOR_DEFAULT = 1.125f;
    public static final int FIRST_BUCKET_SIZE_DEFAULT = 5;
    private static final int LAST_HISTOGRAM_BUFFER_SIZE_BYTES = 1000;
    private static final int MAX_ATOM_SIZE_BYTES = 4064;
    public static final int PERIODIC_SAMPLING_INTERVAL_DEFAULT = 10;
    public static final int SHARDING_MODULO_DEFAULT = 1;
    public static final int STATSD_PUSH_INTERVAL_MINUTES_DEFAULT = 360;
    private static final java.lang.String TAG = "BinderLatencyObserver";
    private final android.os.Handler mLatencyObserverHandler;
    private final int mProcessSource;
    private final java.util.Random mRandom;
    private int mShardingOffset;
    private final android.util.ArrayMap<com.android.internal.os.BinderLatencyObserver.LatencyDims, int[]> mLatencyHistograms = new android.util.ArrayMap<>();
    private final java.lang.Object mLock = new java.lang.Object();
    private int mPeriodicSamplingInterval = 10;
    private int mShardingModulo = 1;
    private int mBucketCount = 100;
    private int mFirstBucketSize = 5;
    private float mBucketScaleFactor = 1.125f;
    private int mStatsdPushIntervalMinutes = 360;
    private java.lang.Runnable mLatencyObserverRunnable = new java.lang.Runnable() { // from class: com.android.internal.os.BinderLatencyObserver.1
        @Override // java.lang.Runnable
        public void run() {
            android.util.ArrayMap arrayMap;
            com.android.internal.os.BinderLatencyObserver.this.noteLatencyDelayed();
            synchronized (com.android.internal.os.BinderLatencyObserver.this.mLock) {
                arrayMap = new android.util.ArrayMap(com.android.internal.os.BinderLatencyObserver.this.mLatencyHistograms);
                com.android.internal.os.BinderLatencyObserver.this.mLatencyHistograms.clear();
            }
            com.android.internal.os.BinderTransactionNameResolver binderTransactionNameResolver = new com.android.internal.os.BinderTransactionNameResolver();
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
            int i = 0;
            for (com.android.internal.os.BinderLatencyObserver.LatencyDims latencyDims : arrayMap.keySet()) {
                if (protoOutputStream.getRawSize() + 1000 > com.android.internal.os.BinderLatencyObserver.this.getMaxAtomSizeBytes()) {
                    if (i > 0) {
                        com.android.internal.os.BinderLatencyObserver.this.writeAtomToStatsd(protoOutputStream);
                    }
                    protoOutputStream = new android.util.proto.ProtoOutputStream();
                    i = 0;
                }
                com.android.internal.os.BinderLatencyObserver.this.fillApiStatsProto(protoOutputStream, latencyDims, binderTransactionNameResolver.getMethodName(latencyDims.getBinderClass(), latencyDims.getTransactionCode()), (int[]) arrayMap.get(latencyDims));
                i++;
            }
            if (i > 0) {
                com.android.internal.os.BinderLatencyObserver.this.writeAtomToStatsd(protoOutputStream);
            }
        }
    };
    private com.android.internal.os.BinderLatencyBuckets mLatencyBuckets = new com.android.internal.os.BinderLatencyBuckets(this.mBucketCount, this.mFirstBucketSize, this.mBucketScaleFactor);

    /* JADX INFO: Access modifiers changed from: private */
    public void fillApiStatsProto(android.util.proto.ProtoOutputStream protoOutputStream, com.android.internal.os.BinderLatencyObserver.LatencyDims latencyDims, java.lang.String str, int[] iArr) {
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= this.mBucketCount) {
                break;
            }
            if (iArr[i2] == 0) {
                i2++;
            } else {
                i = i2;
                break;
            }
        }
        int i3 = this.mBucketCount - 1;
        int i4 = this.mBucketCount - 1;
        while (true) {
            if (i4 < 0) {
                break;
            }
            if (iArr[i4] == 0) {
                i4--;
            } else {
                i3 = i4;
                break;
            }
        }
        long start = protoOutputStream.start(2246267895809L);
        long start2 = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1159641169921L, this.mProcessSource);
        protoOutputStream.write(1138166333443L, latencyDims.getBinderClass().getName());
        protoOutputStream.write(1138166333445L, str);
        protoOutputStream.end(start2);
        protoOutputStream.write(1120986464258L, i);
        while (i <= i3) {
            protoOutputStream.write(2220498092035L, iArr[i]);
            i++;
        }
        protoOutputStream.end(start);
    }

    protected int getMaxAtomSizeBytes() {
        return MAX_ATOM_SIZE_BYTES;
    }

    protected void writeAtomToStatsd(android.util.proto.ProtoOutputStream protoOutputStream) {
        com.android.internal.util.FrameworkStatsLog.write(342, protoOutputStream.getBytes(), this.mPeriodicSamplingInterval, this.mShardingModulo, this.mBucketCount, this.mFirstBucketSize, this.mBucketScaleFactor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void noteLatencyDelayed() {
        this.mLatencyObserverHandler.removeCallbacks(this.mLatencyObserverRunnable);
        this.mLatencyObserverHandler.postDelayed(this.mLatencyObserverRunnable, this.mStatsdPushIntervalMinutes * 60 * 1000);
    }

    public static class Injector {
        public java.util.Random getRandomGenerator() {
            return new java.util.Random();
        }

        public android.os.Handler getHandler() {
            return com.android.internal.os.BackgroundThread.getHandler();
        }
    }

    public BinderLatencyObserver(com.android.internal.os.BinderLatencyObserver.Injector injector, int i) {
        this.mRandom = injector.getRandomGenerator();
        this.mLatencyObserverHandler = injector.getHandler();
        this.mProcessSource = i;
        this.mShardingOffset = this.mRandom.nextInt(this.mShardingModulo);
        noteLatencyDelayed();
    }

    public void callEnded(com.android.internal.os.BinderInternal.CallSession callSession) {
        if (callSession == null || callSession.exceptionThrown || !shouldKeepSample()) {
            return;
        }
        com.android.internal.os.BinderLatencyObserver.LatencyDims create = com.android.internal.os.BinderLatencyObserver.LatencyDims.create(callSession.binderClass, callSession.transactionCode);
        if (!shouldCollect(create)) {
            return;
        }
        long elapsedRealtimeMicro = getElapsedRealtimeMicro() - callSession.timeStarted;
        int sampleToBucket = this.mLatencyBuckets.sampleToBucket(elapsedRealtimeMicro > 2147483647L ? Integer.MAX_VALUE : (int) elapsedRealtimeMicro);
        synchronized (this.mLock) {
            int[] iArr = this.mLatencyHistograms.get(create);
            if (iArr == null) {
                iArr = new int[this.mBucketCount];
                this.mLatencyHistograms.put(create, iArr);
            }
            if (iArr[sampleToBucket] < Integer.MAX_VALUE) {
                iArr[sampleToBucket] = iArr[sampleToBucket] + 1;
            }
        }
    }

    protected long getElapsedRealtimeMicro() {
        return android.os.SystemClock.elapsedRealtimeNanos() / 1000;
    }

    protected boolean shouldCollect(com.android.internal.os.BinderLatencyObserver.LatencyDims latencyDims) {
        return (latencyDims.hashCode() + this.mShardingOffset) % this.mShardingModulo == 0;
    }

    protected boolean shouldKeepSample() {
        return this.mRandom.nextInt(this.mPeriodicSamplingInterval) == 0;
    }

    public void setSamplingInterval(int i) {
        if (i <= 0) {
            android.util.Slog.w(TAG, "Ignored invalid sampling interval (value must be positive): " + i);
            return;
        }
        synchronized (this.mLock) {
            if (i != this.mPeriodicSamplingInterval) {
                this.mPeriodicSamplingInterval = i;
                reset();
            }
        }
    }

    public void setShardingModulo(int i) {
        if (i <= 0) {
            android.util.Slog.w(TAG, "Ignored invalid sharding modulo (value must be positive): " + i);
            return;
        }
        synchronized (this.mLock) {
            if (i != this.mShardingModulo) {
                this.mShardingModulo = i;
                this.mShardingOffset = this.mRandom.nextInt(i);
                reset();
            }
        }
    }

    public void setPushInterval(int i) {
        if (i <= 0) {
            android.util.Slog.w(TAG, "Ignored invalid push interval (value must be positive): " + i);
            return;
        }
        synchronized (this.mLock) {
            if (i != this.mStatsdPushIntervalMinutes) {
                this.mStatsdPushIntervalMinutes = i;
                reset();
            }
        }
    }

    public void setHistogramBucketsParams(int i, int i2, float f) {
        synchronized (this.mLock) {
            if (i != this.mBucketCount || i2 != this.mFirstBucketSize || f != this.mBucketScaleFactor) {
                this.mBucketCount = i;
                this.mFirstBucketSize = i2;
                this.mBucketScaleFactor = f;
                this.mLatencyBuckets = new com.android.internal.os.BinderLatencyBuckets(this.mBucketCount, this.mFirstBucketSize, this.mBucketScaleFactor);
                reset();
            }
        }
    }

    public void reset() {
        synchronized (this.mLock) {
            this.mLatencyHistograms.clear();
        }
        noteLatencyDelayed();
    }

    public static class LatencyDims {
        private java.lang.Class<? extends android.os.Binder> mBinderClass;
        private int mHashCode = 0;
        private int mTransactionCode;

        public static com.android.internal.os.BinderLatencyObserver.LatencyDims create(java.lang.Class<? extends android.os.Binder> cls, int i) {
            return new com.android.internal.os.BinderLatencyObserver.LatencyDims(cls, i);
        }

        private LatencyDims(java.lang.Class<? extends android.os.Binder> cls, int i) {
            this.mBinderClass = cls;
            this.mTransactionCode = i;
        }

        public java.lang.Class<? extends android.os.Binder> getBinderClass() {
            return this.mBinderClass;
        }

        public int getTransactionCode() {
            return this.mTransactionCode;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null || !(obj instanceof com.android.internal.os.BinderLatencyObserver.LatencyDims)) {
                return false;
            }
            com.android.internal.os.BinderLatencyObserver.LatencyDims latencyDims = (com.android.internal.os.BinderLatencyObserver.LatencyDims) obj;
            return this.mTransactionCode == latencyDims.getTransactionCode() && this.mBinderClass == latencyDims.getBinderClass();
        }

        public int hashCode() {
            if (this.mHashCode != 0) {
                return this.mHashCode;
            }
            int hashCode = (this.mTransactionCode * 31) + this.mBinderClass.getName().hashCode();
            this.mHashCode = hashCode;
            return hashCode;
        }
    }

    public android.util.ArrayMap<com.android.internal.os.BinderLatencyObserver.LatencyDims, int[]> getLatencyHistograms() {
        return this.mLatencyHistograms;
    }

    public java.lang.Runnable getStatsdPushRunnable() {
        return this.mLatencyObserverRunnable;
    }

    public int getProcessSource() {
        return this.mProcessSource;
    }
}
