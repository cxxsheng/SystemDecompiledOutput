package com.android.internal.os;

/* loaded from: classes4.dex */
public final class BinderCallHeavyHitterWatcher {
    private static final float EPSILON = 1.0E-5f;
    private static final java.lang.String TAG = "BinderCallHeavyHitterWatcher";
    private long mBatchStartTimeStamp;
    private com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer[] mCachedCandidateContainers;
    private int mCachedCandidateContainersIndex;
    private int mCurrentInputSize;
    private boolean mEnabled;
    private com.android.internal.util.HeavyHitterSketch<java.lang.Integer> mHeavyHitterSketch;
    private int mInputSize;
    private com.android.internal.os.BinderCallHeavyHitterWatcher.BinderCallHeavyHitterListener mListener;
    private float mThreshold;
    private int mTotalInputSize;
    private static final java.lang.Object sLock = new java.lang.Object();
    private static com.android.internal.os.BinderCallHeavyHitterWatcher sInstance = null;
    private final android.util.SparseArray<com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer> mHeavyHitterCandiates = new android.util.SparseArray<>();
    private final java.util.ArrayList<java.lang.Integer> mCachedCandidateList = new java.util.ArrayList<>();
    private final java.util.ArrayList<java.lang.Float> mCachedCandidateFrequencies = new java.util.ArrayList<>();
    private android.util.ArraySet<java.lang.Integer> mCachedCandidateSet = new android.util.ArraySet<>();
    private final java.lang.Object mLock = new java.lang.Object();

    public interface BinderCallHeavyHitterListener {
        void onHeavyHit(java.util.List<com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer> list, int i, float f, long j);
    }

    public static final class HeavyHitterContainer {
        public java.lang.Class mClass;
        public int mCode;
        public float mFrequency;
        public int mUid;

        public HeavyHitterContainer() {
        }

        public HeavyHitterContainer(com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer heavyHitterContainer) {
            this.mUid = heavyHitterContainer.mUid;
            this.mClass = heavyHitterContainer.mClass;
            this.mCode = heavyHitterContainer.mCode;
            this.mFrequency = heavyHitterContainer.mFrequency;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null || !(obj instanceof com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer)) {
                return false;
            }
            com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer heavyHitterContainer = (com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer) obj;
            return this.mUid == heavyHitterContainer.mUid && this.mClass == heavyHitterContainer.mClass && this.mCode == heavyHitterContainer.mCode && java.lang.Math.abs(this.mFrequency - heavyHitterContainer.mFrequency) < com.android.internal.os.BinderCallHeavyHitterWatcher.EPSILON;
        }

        public int hashCode() {
            return hashCode(this.mUid, this.mClass, this.mCode);
        }

        static int hashCode(int i, java.lang.Class cls, int i2) {
            return (((i * 31) + cls.hashCode()) * 31) + i2;
        }
    }

    public static com.android.internal.os.BinderCallHeavyHitterWatcher getInstance() {
        com.android.internal.os.BinderCallHeavyHitterWatcher binderCallHeavyHitterWatcher;
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new com.android.internal.os.BinderCallHeavyHitterWatcher();
            }
            binderCallHeavyHitterWatcher = sInstance;
        }
        return binderCallHeavyHitterWatcher;
    }

    public void setConfig(boolean z, int i, float f, com.android.internal.os.BinderCallHeavyHitterWatcher.BinderCallHeavyHitterListener binderCallHeavyHitterListener) {
        int i2;
        synchronized (this.mLock) {
            if (!z) {
                if (this.mEnabled) {
                    resetInternalLocked(null, null, 0, 0, 0.0f, 0);
                    this.mEnabled = false;
                }
                return;
            }
            this.mEnabled = true;
            if (f >= EPSILON && f <= 1.0f) {
                if (i == this.mTotalInputSize && java.lang.Math.abs(f - this.mThreshold) < EPSILON) {
                    this.mListener = binderCallHeavyHitterListener;
                    return;
                }
                int i3 = (int) (1.0f / f);
                com.android.internal.util.HeavyHitterSketch<java.lang.Integer> newDefault = com.android.internal.util.HeavyHitterSketch.newDefault();
                float requiredValidationInputRatio = newDefault.getRequiredValidationInputRatio();
                if (java.lang.Float.isNaN(requiredValidationInputRatio)) {
                    i2 = i;
                } else {
                    i2 = (int) (i * (1.0f - requiredValidationInputRatio));
                }
                try {
                    newDefault.setConfig(i, i3);
                    resetInternalLocked(binderCallHeavyHitterListener, newDefault, i2, i, f, i3);
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Log.w(TAG, "Invalid parameter to heavy hitter watcher: " + i + ", " + i3);
                }
            }
        }
    }

    private void resetInternalLocked(com.android.internal.os.BinderCallHeavyHitterWatcher.BinderCallHeavyHitterListener binderCallHeavyHitterListener, com.android.internal.util.HeavyHitterSketch<java.lang.Integer> heavyHitterSketch, int i, int i2, float f, int i3) {
        this.mListener = binderCallHeavyHitterListener;
        this.mHeavyHitterSketch = heavyHitterSketch;
        this.mHeavyHitterCandiates.clear();
        this.mCachedCandidateList.clear();
        this.mCachedCandidateFrequencies.clear();
        this.mCachedCandidateSet.clear();
        this.mInputSize = i;
        this.mTotalInputSize = i2;
        this.mCurrentInputSize = 0;
        this.mThreshold = f;
        this.mBatchStartTimeStamp = android.os.SystemClock.elapsedRealtime();
        initCachedCandidateContainersLocked(i3);
    }

    private void initCachedCandidateContainersLocked(int i) {
        if (i > 0) {
            this.mCachedCandidateContainers = new com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer[i];
            for (int i2 = 0; i2 < this.mCachedCandidateContainers.length; i2++) {
                this.mCachedCandidateContainers[i2] = new com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer();
            }
        } else {
            this.mCachedCandidateContainers = null;
        }
        this.mCachedCandidateContainersIndex = 0;
    }

    private com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer acquireHeavyHitterContainerLocked() {
        com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer[] heavyHitterContainerArr = this.mCachedCandidateContainers;
        int i = this.mCachedCandidateContainersIndex;
        this.mCachedCandidateContainersIndex = i + 1;
        return heavyHitterContainerArr[i];
    }

    private void releaseHeavyHitterContainerLocked(com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer heavyHitterContainer) {
        com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer[] heavyHitterContainerArr = this.mCachedCandidateContainers;
        int i = this.mCachedCandidateContainersIndex - 1;
        this.mCachedCandidateContainersIndex = i;
        heavyHitterContainerArr[i] = heavyHitterContainer;
    }

    public void onTransaction(int i, java.lang.Class cls, int i2) {
        java.util.List<java.lang.Integer> topHeavyHitters;
        int size;
        synchronized (this.mLock) {
            if (this.mEnabled) {
                com.android.internal.util.HeavyHitterSketch<java.lang.Integer> heavyHitterSketch = this.mHeavyHitterSketch;
                if (heavyHitterSketch == null) {
                    return;
                }
                int hashCode = com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer.hashCode(i, cls, i2);
                heavyHitterSketch.add(java.lang.Integer.valueOf(hashCode));
                this.mCurrentInputSize++;
                if (this.mCurrentInputSize == this.mInputSize) {
                    heavyHitterSketch.getCandidates(this.mCachedCandidateList);
                    this.mCachedCandidateSet.addAll(this.mCachedCandidateList);
                    this.mCachedCandidateList.clear();
                } else if (this.mCurrentInputSize > this.mInputSize && this.mCurrentInputSize < this.mTotalInputSize) {
                    if (this.mCachedCandidateSet.contains(java.lang.Integer.valueOf(hashCode))) {
                        if (this.mHeavyHitterCandiates.indexOfKey(hashCode) < 0) {
                            com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer acquireHeavyHitterContainerLocked = acquireHeavyHitterContainerLocked();
                            acquireHeavyHitterContainerLocked.mUid = i;
                            acquireHeavyHitterContainerLocked.mClass = cls;
                            acquireHeavyHitterContainerLocked.mCode = i2;
                            this.mHeavyHitterCandiates.put(hashCode, acquireHeavyHitterContainerLocked);
                        }
                    }
                } else if (this.mCurrentInputSize == this.mTotalInputSize) {
                    if (this.mListener != null && (topHeavyHitters = heavyHitterSketch.getTopHeavyHitters(0, this.mCachedCandidateList, this.mCachedCandidateFrequencies)) != null && (size = topHeavyHitters.size()) > 0) {
                        java.util.ArrayList arrayList = new java.util.ArrayList();
                        for (int i3 = 0; i3 < size; i3++) {
                            com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer heavyHitterContainer = this.mHeavyHitterCandiates.get(topHeavyHitters.get(i3).intValue());
                            if (heavyHitterContainer != null) {
                                com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer heavyHitterContainer2 = new com.android.internal.os.BinderCallHeavyHitterWatcher.HeavyHitterContainer(heavyHitterContainer);
                                heavyHitterContainer2.mFrequency = this.mCachedCandidateFrequencies.get(i3).floatValue();
                                arrayList.add(heavyHitterContainer2);
                            }
                        }
                        this.mListener.onHeavyHit(arrayList, this.mTotalInputSize, this.mThreshold, android.os.SystemClock.elapsedRealtime() - this.mBatchStartTimeStamp);
                    }
                    this.mHeavyHitterSketch.reset();
                    this.mHeavyHitterCandiates.clear();
                    this.mCachedCandidateList.clear();
                    this.mCachedCandidateFrequencies.clear();
                    this.mCachedCandidateSet.clear();
                    this.mCachedCandidateContainersIndex = 0;
                    this.mCurrentInputSize = 0;
                    this.mBatchStartTimeStamp = android.os.SystemClock.elapsedRealtime();
                }
            }
        }
    }
}
