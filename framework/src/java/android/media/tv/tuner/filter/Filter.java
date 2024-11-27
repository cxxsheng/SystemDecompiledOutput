package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class Filter implements java.lang.AutoCloseable {
    public static final int MONITOR_EVENT_IP_CID_CHANGE = 2;
    public static final int MONITOR_EVENT_SCRAMBLING_STATUS = 1;
    public static final int SCRAMBLING_STATUS_NOT_SCRAMBLED = 2;
    public static final int SCRAMBLING_STATUS_SCRAMBLED = 4;
    public static final int SCRAMBLING_STATUS_UNKNOWN = 1;
    public static final int STATUS_DATA_READY = 1;
    public static final int STATUS_HIGH_WATER = 4;
    public static final int STATUS_LOW_WATER = 2;
    public static final int STATUS_NO_DATA = 16;
    public static final int STATUS_OVERFLOW = 8;
    public static final int SUBTYPE_AUDIO = 3;
    public static final int SUBTYPE_DOWNLOAD = 5;
    public static final int SUBTYPE_IP = 13;
    public static final int SUBTYPE_IP_PAYLOAD = 12;
    public static final int SUBTYPE_MMTP = 10;
    public static final int SUBTYPE_NTP = 11;
    public static final int SUBTYPE_PAYLOAD_THROUGH = 14;
    public static final int SUBTYPE_PCR = 8;
    public static final int SUBTYPE_PES = 2;
    public static final int SUBTYPE_PTP = 16;
    public static final int SUBTYPE_RECORD = 6;
    public static final int SUBTYPE_SECTION = 1;
    public static final int SUBTYPE_TEMI = 9;
    public static final int SUBTYPE_TLV = 15;
    public static final int SUBTYPE_TS = 7;
    public static final int SUBTYPE_UNDEFINED = 0;
    public static final int SUBTYPE_VIDEO = 4;
    private static final java.lang.String TAG = "Filter";
    public static final int TYPE_ALP = 16;
    public static final int TYPE_IP = 4;
    public static final int TYPE_MMTP = 2;
    public static final int TYPE_TLV = 8;
    public static final int TYPE_TS = 1;
    public static final int TYPE_UNDEFINED = 0;
    private android.media.tv.tuner.filter.FilterCallback mCallback;
    private java.util.concurrent.Executor mExecutor;
    private final long mId;
    private int mMainType;
    private long mNativeContext;
    private android.media.tv.tuner.filter.Filter mSource;
    private boolean mStarted;
    private int mSubtype;
    private final java.lang.Object mCallbackLock = new java.lang.Object();
    private boolean mIsClosed = false;
    private boolean mIsStarted = false;
    private boolean mIsShared = false;
    private final java.lang.Object mLock = new java.lang.Object();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MonitorEventMask {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScramblingStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Status {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Subtype {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private native java.lang.String nativeAcquireSharedFilterToken();

    private native int nativeClose();

    private native int nativeConfigureFilter(int i, int i2, android.media.tv.tuner.filter.FilterConfiguration filterConfiguration);

    private native int nativeConfigureMonitorEvent(int i);

    private native int nativeFlushFilter();

    private native void nativeFreeSharedFilterToken(java.lang.String str);

    private native int nativeGetId();

    private native long nativeGetId64Bit();

    private native int nativeRead(byte[] bArr, long j, long j2);

    private native int nativeSetDataSizeDelayHint(int i);

    private native int nativeSetDataSource(android.media.tv.tuner.filter.Filter filter);

    private native int nativeSetTimeDelayHint(int i);

    private native int nativeStartFilter();

    private native int nativeStopFilter();

    private Filter(long j) {
        this.mId = j;
    }

    private void onFilterStatus(final int i) {
        synchronized (this.mCallbackLock) {
            if (this.mCallback != null && this.mExecutor != null) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.filter.Filter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.filter.Filter.this.lambda$onFilterStatus$0(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFilterStatus$0(int i) {
        android.media.tv.tuner.filter.FilterCallback filterCallback;
        synchronized (this.mCallbackLock) {
            filterCallback = this.mCallback;
        }
        if (filterCallback != null) {
            try {
                filterCallback.onFilterStatusChanged(this, i);
            } catch (java.lang.NullPointerException e) {
                android.util.Log.d(TAG, "catch exception:" + e);
            }
        }
        if (filterCallback != null) {
            filterCallback.onFilterStatusChanged(this, i);
        }
    }

    private void onFilterEvent(final android.media.tv.tuner.filter.FilterEvent[] filterEventArr) {
        synchronized (this.mCallbackLock) {
            if (this.mCallback == null || this.mExecutor == null) {
                for (android.media.tv.tuner.filter.FilterEvent filterEvent : filterEventArr) {
                    if (filterEvent instanceof android.media.tv.tuner.filter.MediaEvent) {
                        ((android.media.tv.tuner.filter.MediaEvent) filterEvent).release();
                    }
                }
            } else {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.filter.Filter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.filter.Filter.this.lambda$onFilterEvent$1(filterEventArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFilterEvent$1(android.media.tv.tuner.filter.FilterEvent[] filterEventArr) {
        android.media.tv.tuner.filter.FilterCallback filterCallback;
        synchronized (this.mCallbackLock) {
            filterCallback = this.mCallback;
        }
        if (filterCallback != null) {
            try {
                filterCallback.onFilterEvent(this, filterEventArr);
                return;
            } catch (java.lang.NullPointerException e) {
                android.util.Log.d(TAG, "catch exception:" + e);
                return;
            }
        }
        for (android.media.tv.tuner.filter.FilterEvent filterEvent : filterEventArr) {
            if (filterEvent instanceof android.media.tv.tuner.filter.MediaEvent) {
                ((android.media.tv.tuner.filter.MediaEvent) filterEvent).release();
            }
        }
    }

    public void setType(int i, int i2) {
        this.mMainType = i;
        this.mSubtype = android.media.tv.tuner.TunerUtils.getFilterSubtype(i, i2);
    }

    public void setCallback(android.media.tv.tuner.filter.FilterCallback filterCallback, java.util.concurrent.Executor executor) {
        synchronized (this.mCallbackLock) {
            this.mCallback = filterCallback;
            this.mExecutor = executor;
        }
    }

    public android.media.tv.tuner.filter.FilterCallback getCallback() {
        android.media.tv.tuner.filter.FilterCallback filterCallback;
        synchronized (this.mCallbackLock) {
            filterCallback = this.mCallback;
        }
        return filterCallback;
    }

    public int configure(android.media.tv.tuner.filter.FilterConfiguration filterConfiguration) {
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            android.media.tv.tuner.filter.Settings settings = filterConfiguration.getSettings();
            int type = settings == null ? this.mSubtype : settings.getType();
            if (this.mMainType != filterConfiguration.getType() || this.mSubtype != type) {
                throw new java.lang.IllegalArgumentException("Invalid filter config. filter main type=" + this.mMainType + ", filter subtype=" + this.mSubtype + ". config main type=" + filterConfiguration.getType() + ", config subtype=" + type);
            }
            if ((settings instanceof android.media.tv.tuner.filter.RecordSettings) && ((android.media.tv.tuner.filter.RecordSettings) settings).getScIndexType() == 4 && !android.media.tv.tuner.TunerVersionChecker.isHigherOrEqualVersionTo(196608)) {
                android.util.Log.e(TAG, "Tuner version " + android.media.tv.tuner.TunerVersionChecker.getTunerVersion() + " does not support VVC");
                return 1;
            }
            return nativeConfigureFilter(filterConfiguration.getType(), type, filterConfiguration);
        }
    }

    public int getId() {
        int nativeGetId;
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            nativeGetId = nativeGetId();
        }
        return nativeGetId;
    }

    public long getIdLong() {
        long nativeGetId64Bit;
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            nativeGetId64Bit = nativeGetId64Bit();
        }
        return nativeGetId64Bit;
    }

    public int setMonitorEventMask(int i) {
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            if (!android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "setMonitorEventMask")) {
                return 1;
            }
            return nativeConfigureMonitorEvent(i);
        }
    }

    public int setDataSource(android.media.tv.tuner.filter.Filter filter) {
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            if (this.mSource != null) {
                throw new java.lang.IllegalStateException("Data source is existing");
            }
            int nativeSetDataSource = nativeSetDataSource(filter);
            if (nativeSetDataSource == 0) {
                this.mSource = filter;
            }
            return nativeSetDataSource;
        }
    }

    public int start() {
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            int nativeStartFilter = nativeStartFilter();
            if (nativeStartFilter == 0) {
                this.mIsStarted = true;
            }
            return nativeStartFilter;
        }
    }

    public int stop() {
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            int nativeStopFilter = nativeStopFilter();
            if (nativeStopFilter == 0) {
                this.mIsStarted = false;
            }
            return nativeStopFilter;
        }
    }

    public int flush() {
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 3;
            }
            return nativeFlushFilter();
        }
    }

    public int read(byte[] bArr, long j, long j2) {
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                return 0;
            }
            return nativeRead(bArr, j, java.lang.Math.min(j2, bArr.length - j));
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mCallbackLock) {
            this.mCallback = null;
            this.mExecutor = null;
        }
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            int nativeClose = nativeClose();
            if (nativeClose != 0) {
                android.media.tv.tuner.TunerUtils.throwExceptionForResult(nativeClose, "Failed to close filter.");
            } else {
                this.mIsStarted = false;
                this.mIsClosed = true;
            }
        }
    }

    public java.lang.String acquireSharedFilterToken() {
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (!this.mIsStarted && !this.mIsShared) {
                java.lang.String nativeAcquireSharedFilterToken = nativeAcquireSharedFilterToken();
                if (nativeAcquireSharedFilterToken != null) {
                    this.mIsShared = true;
                }
                return nativeAcquireSharedFilterToken;
            }
            android.util.Log.d(TAG, "Acquire shared filter in a wrong state, started: " + this.mIsStarted + "shared: " + this.mIsShared);
            return null;
        }
    }

    public void freeSharedFilterToken(java.lang.String str) {
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            if (this.mIsShared) {
                nativeFreeSharedFilterToken(str);
                this.mIsShared = false;
            }
        }
    }

    public int delayCallbackForDurationMillis(long j) {
        int nativeSetTimeDelayHint;
        if (!android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "setTimeDelayHint")) {
            return 1;
        }
        if (j >= 0 && j <= 2147483647L) {
            synchronized (this.mLock) {
                nativeSetTimeDelayHint = nativeSetTimeDelayHint((int) j);
            }
            return nativeSetTimeDelayHint;
        }
        return 4;
    }

    public int delayCallbackUntilBytesAccumulated(int i) {
        int nativeSetDataSizeDelayHint;
        if (!android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "setTimeDelayHint")) {
            return 1;
        }
        synchronized (this.mLock) {
            nativeSetDataSizeDelayHint = nativeSetDataSizeDelayHint(i);
        }
        return nativeSetDataSizeDelayHint;
    }
}
