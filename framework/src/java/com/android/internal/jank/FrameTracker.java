package com.android.internal.jank;

/* loaded from: classes4.dex */
public class FrameTracker extends android.view.SurfaceControl.OnJankDataListener implements android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener {
    private static final int FLUSH_DELAY_MILLISECOND = 60;
    private static final long INVALID_ID = -1;
    private static final int MAX_FLUSH_ATTEMPTS = 3;
    private static final int MAX_LENGTH_EVENT_DESC = 127;
    public static final int NANOS_IN_MILLISECOND = 1000000;
    static final int REASON_CANCEL_NORMAL = 16;
    static final int REASON_CANCEL_NOT_BEGUN = 17;
    static final int REASON_CANCEL_SAME_VSYNC = 18;
    static final int REASON_CANCEL_TIMEOUT = 19;
    static final int REASON_END_NORMAL = 0;
    static final int REASON_END_SURFACE_DESTROYED = 1;
    static final int REASON_END_UNKNOWN = -1;
    private static final java.lang.String TAG = "FrameTracker";
    private final com.android.internal.jank.FrameTracker.ChoreographerWrapper mChoreographer;
    private final com.android.internal.jank.InteractionJankMonitor.Configuration mConfig;
    private final boolean mDeferMonitoring;
    private final int mDisplayId;
    private final android.os.Handler mHandler;
    private final com.android.internal.jank.FrameTracker.FrameTrackerListener mListener;
    private boolean mMetricsFinalized;
    private final com.android.internal.jank.FrameTracker.FrameMetricsWrapper mMetricsWrapper;
    private final android.graphics.HardwareRendererObserver mObserver;
    private final com.android.internal.jank.FrameTracker.ThreadedRendererWrapper mRendererWrapper;
    private final com.android.internal.jank.FrameTracker.StatsLogWrapper mStatsLog;
    private final android.view.ViewRootImpl.SurfaceChangedCallback mSurfaceChangedCallback;
    private android.view.SurfaceControl mSurfaceControl;
    private final com.android.internal.jank.FrameTracker.SurfaceControlWrapper mSurfaceControlWrapper;
    public final boolean mSurfaceOnly;
    private final int mTraceThresholdFrameTimeMillis;
    private final int mTraceThresholdMissedFrames;
    private final com.android.internal.jank.FrameTracker.ViewRootWrapper mViewRoot;
    private java.lang.Runnable mWaitForFinishTimedOut;
    private final android.util.SparseArray<com.android.internal.jank.FrameTracker.JankInfo> mJankInfos = new android.util.SparseArray<>();
    private long mBeginVsyncId = -1;
    private long mEndVsyncId = -1;
    private boolean mCancelled = false;
    private boolean mTracingStarted = false;

    public interface FrameTrackerListener {
        void onCujEvents(com.android.internal.jank.FrameTracker frameTracker, java.lang.String str, int i);

        void triggerPerfetto(com.android.internal.jank.InteractionJankMonitor.Configuration configuration);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Reasons {
    }

    private static class JankInfo {
        long frameVsyncId;
        boolean hwuiCallbackFired;
        boolean isFirstFrame;
        int jankType;
        int refreshRate;
        boolean surfaceControlCallbackFired;
        long totalDurationNanos;

        static com.android.internal.jank.FrameTracker.JankInfo createFromHwuiCallback(long j, long j2, boolean z) {
            return new com.android.internal.jank.FrameTracker.JankInfo(j, true, false, 0, 0, j2, z);
        }

        static com.android.internal.jank.FrameTracker.JankInfo createFromSurfaceControlCallback(long j, int i, int i2) {
            return new com.android.internal.jank.FrameTracker.JankInfo(j, false, true, i, i2, 0L, false);
        }

        private JankInfo(long j, boolean z, boolean z2, int i, int i2, long j2, boolean z3) {
            this.frameVsyncId = j;
            this.hwuiCallbackFired = z;
            this.surfaceControlCallbackFired = z2;
            this.jankType = i;
            this.refreshRate = i2;
            this.totalDurationNanos = j2;
            this.isFirstFrame = z3;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            switch (this.jankType) {
                case 0:
                    sb.append("JANK_NONE");
                    break;
                case 1:
                    sb.append("DISPLAY_HAL");
                    break;
                case 2:
                    sb.append("JANK_SURFACEFLINGER_DEADLINE_MISSED");
                    break;
                case 4:
                    sb.append("JANK_SURFACEFLINGER_GPU_DEADLINE_MISSED");
                    break;
                case 8:
                    sb.append("JANK_APP_DEADLINE_MISSED");
                    break;
                case 16:
                    sb.append("PREDICTION_ERROR");
                    break;
                case 32:
                    sb.append("SURFACE_FLINGER_SCHEDULING");
                    break;
                default:
                    sb.append("UNKNOWN: ").append(this.jankType);
                    break;
            }
            sb.append(", ").append(this.frameVsyncId);
            sb.append(", ").append(this.totalDurationNanos);
            return sb.toString();
        }
    }

    public FrameTracker(com.android.internal.jank.InteractionJankMonitor.Configuration configuration, com.android.internal.jank.FrameTracker.ThreadedRendererWrapper threadedRendererWrapper, com.android.internal.jank.FrameTracker.ViewRootWrapper viewRootWrapper, com.android.internal.jank.FrameTracker.SurfaceControlWrapper surfaceControlWrapper, com.android.internal.jank.FrameTracker.ChoreographerWrapper choreographerWrapper, com.android.internal.jank.FrameTracker.FrameMetricsWrapper frameMetricsWrapper, com.android.internal.jank.FrameTracker.StatsLogWrapper statsLogWrapper, int i, int i2, com.android.internal.jank.FrameTracker.FrameTrackerListener frameTrackerListener) {
        android.graphics.HardwareRendererObserver hardwareRendererObserver;
        this.mSurfaceOnly = configuration.isSurfaceOnly();
        this.mConfig = configuration;
        this.mHandler = configuration.getHandler();
        this.mChoreographer = choreographerWrapper;
        this.mSurfaceControlWrapper = surfaceControlWrapper;
        this.mStatsLog = statsLogWrapper;
        this.mDeferMonitoring = configuration.shouldDeferMonitor();
        this.mRendererWrapper = this.mSurfaceOnly ? null : threadedRendererWrapper;
        this.mMetricsWrapper = this.mSurfaceOnly ? null : frameMetricsWrapper;
        this.mViewRoot = this.mSurfaceOnly ? null : viewRootWrapper;
        if (this.mSurfaceOnly) {
            hardwareRendererObserver = null;
        } else {
            hardwareRendererObserver = new android.graphics.HardwareRendererObserver(this, this.mMetricsWrapper.getTiming(), this.mHandler, false);
        }
        this.mObserver = hardwareRendererObserver;
        this.mTraceThresholdMissedFrames = i;
        this.mTraceThresholdFrameTimeMillis = i2;
        this.mListener = frameTrackerListener;
        this.mDisplayId = configuration.getDisplayId();
        if (this.mSurfaceOnly) {
            this.mSurfaceControl = configuration.getSurfaceControl();
            this.mSurfaceChangedCallback = null;
        } else {
            if (this.mViewRoot.getSurfaceControl().isValid()) {
                this.mSurfaceControl = this.mViewRoot.getSurfaceControl();
            }
            this.mSurfaceChangedCallback = new com.android.internal.jank.FrameTracker.AnonymousClass1();
            this.mViewRoot.addSurfaceChangedCallback(this.mSurfaceChangedCallback);
        }
    }

    /* renamed from: com.android.internal.jank.FrameTracker$1, reason: invalid class name */
    class AnonymousClass1 implements android.view.ViewRootImpl.SurfaceChangedCallback {
        AnonymousClass1() {
        }

        @Override // android.view.ViewRootImpl.SurfaceChangedCallback
        public void surfaceCreated(android.view.SurfaceControl.Transaction transaction) {
            com.android.internal.jank.FrameTracker.this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.internal.jank.FrameTracker$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.jank.FrameTracker.AnonymousClass1.this.lambda$surfaceCreated$0();
                }
            }, 500L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$surfaceCreated$0() {
            if (com.android.internal.jank.FrameTracker.this.mSurfaceControl == null) {
                com.android.internal.jank.FrameTracker.this.mSurfaceControl = com.android.internal.jank.FrameTracker.this.mViewRoot.getSurfaceControl();
                if (com.android.internal.jank.FrameTracker.this.mBeginVsyncId != -1) {
                    com.android.internal.jank.FrameTracker.this.begin();
                }
            }
        }

        @Override // android.view.ViewRootImpl.SurfaceChangedCallback
        public void surfaceReplaced(android.view.SurfaceControl.Transaction transaction) {
        }

        @Override // android.view.ViewRootImpl.SurfaceChangedCallback
        public void surfaceDestroyed() {
            com.android.internal.jank.FrameTracker.this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.internal.jank.FrameTracker$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.jank.FrameTracker.AnonymousClass1.this.lambda$surfaceDestroyed$1();
                }
            }, 50L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$surfaceDestroyed$1() {
            if (!com.android.internal.jank.FrameTracker.this.mMetricsFinalized) {
                com.android.internal.jank.FrameTracker.this.end(1);
                com.android.internal.jank.FrameTracker.this.finish();
            }
        }
    }

    public void begin() {
        long vsyncId = this.mChoreographer.getVsyncId();
        if (this.mBeginVsyncId == -1) {
            this.mBeginVsyncId = this.mDeferMonitoring ? 1 + vsyncId : vsyncId;
        }
        if (this.mSurfaceControl != null) {
            if (this.mDeferMonitoring && vsyncId < this.mBeginVsyncId) {
                markEvent("FT#deferMonitoring", 0L);
                postTraceStartMarker(new java.lang.Runnable() { // from class: com.android.internal.jank.FrameTracker$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.jank.FrameTracker.this.beginInternal();
                    }
                });
            } else {
                beginInternal();
            }
        }
    }

    public void postTraceStartMarker(java.lang.Runnable runnable) {
        this.mChoreographer.mChoreographer.postCallback(0, runnable, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void beginInternal() {
        if (this.mCancelled || this.mEndVsyncId != -1) {
            return;
        }
        this.mTracingStarted = true;
        java.lang.String sessionName = this.mConfig.getSessionName();
        android.os.Trace.asyncTraceForTrackBegin(4096L, sessionName, sessionName, (int) this.mBeginVsyncId);
        markEvent("FT#beginVsync", this.mBeginVsyncId);
        markEvent("FT#layerId", this.mSurfaceControl.getLayerId());
        this.mSurfaceControlWrapper.addJankStatsListener(this, this.mSurfaceControl);
        if (!this.mSurfaceOnly) {
            this.mRendererWrapper.addObserver(this.mObserver);
        }
    }

    public boolean end(int i) {
        if (this.mCancelled || this.mEndVsyncId != -1) {
            return false;
        }
        this.mEndVsyncId = this.mChoreographer.getVsyncId();
        if (this.mBeginVsyncId == -1) {
            return cancel(17);
        }
        if (this.mEndVsyncId <= this.mBeginVsyncId) {
            return cancel(18);
        }
        java.lang.String sessionName = this.mConfig.getSessionName();
        markEvent("FT#end", i);
        markEvent("FT#endVsync", this.mEndVsyncId);
        android.os.Trace.asyncTraceForTrackEnd(4096L, sessionName, (int) this.mBeginVsyncId);
        this.mWaitForFinishTimedOut = new com.android.internal.jank.FrameTracker.AnonymousClass2(sessionName);
        this.mHandler.postDelayed(this.mWaitForFinishTimedOut, 60L);
        notifyCujEvent(com.android.internal.jank.InteractionJankMonitor.ACTION_SESSION_END, i);
        return true;
    }

    /* renamed from: com.android.internal.jank.FrameTracker$2, reason: invalid class name */
    class AnonymousClass2 implements java.lang.Runnable {
        private int mFlushAttempts = 0;
        final /* synthetic */ java.lang.String val$name;

        AnonymousClass2(java.lang.String str) {
            this.val$name = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            long millis;
            if (com.android.internal.jank.FrameTracker.this.mWaitForFinishTimedOut == null || com.android.internal.jank.FrameTracker.this.mMetricsFinalized) {
                return;
            }
            if (com.android.internal.jank.FrameTracker.this.mSurfaceControl != null && com.android.internal.jank.FrameTracker.this.mSurfaceControl.isValid()) {
                android.view.SurfaceControl.Transaction.sendSurfaceFlushJankData(com.android.internal.jank.FrameTracker.this.mSurfaceControl);
            }
            if (this.mFlushAttempts < 3) {
                this.mFlushAttempts++;
                millis = 60;
            } else {
                com.android.internal.jank.FrameTracker frameTracker = com.android.internal.jank.FrameTracker.this;
                final java.lang.String str = this.val$name;
                frameTracker.mWaitForFinishTimedOut = new java.lang.Runnable() { // from class: com.android.internal.jank.FrameTracker$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.jank.FrameTracker.AnonymousClass2.this.lambda$run$0(str);
                    }
                };
                millis = java.util.concurrent.TimeUnit.SECONDS.toMillis(10L);
            }
            com.android.internal.jank.FrameTracker.this.mHandler.postDelayed(com.android.internal.jank.FrameTracker.this.mWaitForFinishTimedOut, millis);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(java.lang.String str) {
            android.util.Log.e(com.android.internal.jank.FrameTracker.TAG, "force finish cuj, time out: " + str);
            com.android.internal.jank.FrameTracker.this.finish();
        }
    }

    public boolean cancel(int i) {
        boolean z = i == 17 || i == 18;
        if (this.mCancelled || !(this.mEndVsyncId == -1 || z)) {
            return false;
        }
        this.mCancelled = true;
        markEvent("FT#cancel", i);
        if (this.mTracingStarted) {
            android.os.Trace.asyncTraceForTrackEnd(4096L, this.mConfig.getSessionName(), (int) this.mBeginVsyncId);
        }
        removeObservers();
        notifyCujEvent(com.android.internal.jank.InteractionJankMonitor.ACTION_SESSION_CANCEL, i);
        return true;
    }

    private void markEvent(java.lang.String str, long j) {
        if (android.os.Trace.isTagEnabled(4096L)) {
            java.lang.String formatSimple = android.text.TextUtils.formatSimple("%s#%s", str, java.lang.Long.valueOf(j));
            if (formatSimple.length() <= 127) {
                android.os.Trace.instantForTrack(4096L, this.mConfig.getSessionName(), formatSimple);
                return;
            }
            throw new java.lang.IllegalArgumentException(android.text.TextUtils.formatSimple("The length of the trace event description <%s> exceeds %d", formatSimple, 127));
        }
    }

    private void notifyCujEvent(java.lang.String str, int i) {
        if (this.mListener == null) {
            return;
        }
        this.mListener.onCujEvents(this, str, i);
    }

    @Override // android.view.SurfaceControl.OnJankDataListener
    public void onJankDataAvailable(final android.view.SurfaceControl.JankData[] jankDataArr) {
        postCallback(new java.lang.Runnable() { // from class: com.android.internal.jank.FrameTracker$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.jank.FrameTracker.this.lambda$onJankDataAvailable$0(jankDataArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onJankDataAvailable$0(android.view.SurfaceControl.JankData[] jankDataArr) {
        if (this.mCancelled || this.mMetricsFinalized) {
            return;
        }
        for (android.view.SurfaceControl.JankData jankData : jankDataArr) {
            if (isInRange(jankData.frameVsyncId)) {
                int refreshRate = com.android.internal.jank.DisplayRefreshRate.getRefreshRate(jankData.frameIntervalNs);
                com.android.internal.jank.FrameTracker.JankInfo findJankInfo = findJankInfo(jankData.frameVsyncId);
                if (findJankInfo != null) {
                    findJankInfo.surfaceControlCallbackFired = true;
                    findJankInfo.jankType = jankData.jankType;
                    findJankInfo.refreshRate = refreshRate;
                } else {
                    this.mJankInfos.put((int) jankData.frameVsyncId, com.android.internal.jank.FrameTracker.JankInfo.createFromSurfaceControlCallback(jankData.frameVsyncId, jankData.jankType, refreshRate));
                }
            }
        }
        processJankInfos();
    }

    public void postCallback(java.lang.Runnable runnable) {
        this.mHandler.post(runnable);
    }

    private com.android.internal.jank.FrameTracker.JankInfo findJankInfo(long j) {
        return this.mJankInfos.get((int) j);
    }

    private boolean isInRange(long j) {
        return j >= this.mBeginVsyncId;
    }

    @Override // android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener
    public void onFrameMetricsAvailable(int i) {
        postCallback(new java.lang.Runnable() { // from class: com.android.internal.jank.FrameTracker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.jank.FrameTracker.this.lambda$onFrameMetricsAvailable$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFrameMetricsAvailable$1() {
        if (this.mCancelled || this.mMetricsFinalized) {
            return;
        }
        long metric = this.mMetricsWrapper.getMetric(8);
        boolean z = this.mMetricsWrapper.getMetric(9) == 1;
        long j = this.mMetricsWrapper.getTiming()[1];
        if (!isInRange(j)) {
            return;
        }
        com.android.internal.jank.FrameTracker.JankInfo findJankInfo = findJankInfo(j);
        if (findJankInfo != null) {
            findJankInfo.hwuiCallbackFired = true;
            findJankInfo.totalDurationNanos = metric;
            findJankInfo.isFirstFrame = z;
        } else {
            this.mJankInfos.put((int) j, com.android.internal.jank.FrameTracker.JankInfo.createFromHwuiCallback(j, metric, z));
        }
        processJankInfos();
    }

    private boolean hasReceivedCallbacksAfterEnd() {
        if (this.mEndVsyncId == -1) {
            return false;
        }
        com.android.internal.jank.FrameTracker.JankInfo valueAt = this.mJankInfos.size() == 0 ? null : this.mJankInfos.valueAt(this.mJankInfos.size() - 1);
        if (valueAt == null || valueAt.frameVsyncId < this.mEndVsyncId) {
            return false;
        }
        for (int size = this.mJankInfos.size() - 1; size >= 0; size--) {
            com.android.internal.jank.FrameTracker.JankInfo valueAt2 = this.mJankInfos.valueAt(size);
            if (valueAt2.frameVsyncId >= this.mEndVsyncId && callbacksReceived(valueAt2)) {
                return true;
            }
        }
        return false;
    }

    private void processJankInfos() {
        if (this.mMetricsFinalized || !hasReceivedCallbacksAfterEnd()) {
            return;
        }
        finish();
    }

    private boolean callbacksReceived(com.android.internal.jank.FrameTracker.JankInfo jankInfo) {
        if (this.mSurfaceOnly) {
            return jankInfo.surfaceControlCallbackFired;
        }
        return jankInfo.hwuiCallbackFired && jankInfo.surfaceControlCallbackFired;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish() {
        int i;
        int i2;
        long j;
        int i3;
        long j2;
        int i4;
        if (this.mMetricsFinalized || this.mCancelled) {
            return;
        }
        int i5 = 1;
        this.mMetricsFinalized = true;
        this.mHandler.removeCallbacks(this.mWaitForFinishTimedOut);
        this.mWaitForFinishTimedOut = null;
        markEvent("FT#finish", this.mJankInfos.size());
        removeObservers();
        java.lang.String sessionName = this.mConfig.getSessionName();
        long j3 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        while (true) {
            if (i6 >= this.mJankInfos.size()) {
                i = i12;
                i2 = i11;
                j = j3;
                break;
            }
            com.android.internal.jank.FrameTracker.JankInfo valueAt = this.mJankInfos.valueAt(i6);
            if (((this.mSurfaceOnly || !valueAt.isFirstFrame) ? 0 : i5) == 0) {
                long j4 = j3;
                i2 = i11;
                if (valueAt.frameVsyncId > this.mEndVsyncId) {
                    i = i12;
                    j = j4;
                    break;
                }
                if (!valueAt.surfaceControlCallbackFired) {
                    i3 = i2;
                } else {
                    i7++;
                    if ((valueAt.jankType & 8) == 0) {
                        i4 = 0;
                    } else {
                        android.util.Log.w(TAG, "Missed App frame:" + valueAt + ", CUJ=" + sessionName);
                        i13++;
                        i4 = i5;
                    }
                    if ((valueAt.jankType & i5) != 0 || (valueAt.jankType & 2) != 0 || (valueAt.jankType & 4) != 0 || (valueAt.jankType & 32) != 0 || (valueAt.jankType & 16) != 0) {
                        android.util.Log.w(TAG, "Missed SF frame:" + valueAt + ", CUJ=" + sessionName);
                        i12++;
                        i4 = i5;
                    }
                    if (i4 != 0) {
                        i3 = i2 + 1;
                        i10++;
                    } else {
                        i8 = java.lang.Math.max(i8, i10);
                        i3 = i2;
                        i10 = 0;
                    }
                    if (valueAt.refreshRate != 0 && valueAt.refreshRate != i9) {
                        i9 = i9 == 0 ? valueAt.refreshRate : i5;
                    }
                    if (!this.mSurfaceOnly && !valueAt.hwuiCallbackFired) {
                        markEvent("FT#MissedHWUICallback", valueAt.frameVsyncId);
                        sessionName = sessionName;
                        android.util.Log.w(TAG, "Missing HWUI jank callback for vsyncId: " + valueAt.frameVsyncId + ", CUJ=" + sessionName);
                    }
                }
                if (this.mSurfaceOnly || !valueAt.hwuiCallbackFired) {
                    i7 = i7;
                    i8 = i8;
                    i11 = i3;
                    j3 = j4;
                    i12 = i12;
                } else {
                    int i14 = i7;
                    int i15 = i8;
                    int i16 = i3;
                    int i17 = i12;
                    long max = java.lang.Math.max(valueAt.totalDurationNanos, j4);
                    if (!valueAt.surfaceControlCallbackFired) {
                        j2 = max;
                        markEvent("FT#MissedSFCallback", valueAt.frameVsyncId);
                        android.util.Log.w(TAG, "Missing SF jank callback for vsyncId: " + valueAt.frameVsyncId + ", CUJ=" + sessionName);
                    } else {
                        j2 = max;
                    }
                    i7 = i14;
                    i8 = i15;
                    i11 = i16;
                    i12 = i17;
                    j3 = j2;
                }
            }
            i6++;
            i5 = 1;
        }
        int max2 = java.lang.Math.max(i8, i10);
        android.os.Trace.traceCounter(4096L, sessionName + "#missedFrames", i2);
        android.os.Trace.traceCounter(4096L, sessionName + "#missedAppFrames", i13);
        android.os.Trace.traceCounter(4096L, sessionName + "#missedSfFrames", i);
        android.os.Trace.traceCounter(4096L, sessionName + "#totalFrames", i7);
        int i18 = i9;
        android.os.Trace.traceCounter(4096L, sessionName + "#maxFrameTimeMillis", (int) (j / 1000000));
        android.os.Trace.traceCounter(4096L, sessionName + "#maxSuccessiveMissedFrames", max2);
        if (this.mListener != null && shouldTriggerPerfetto(i2, (int) j)) {
            this.mListener.triggerPerfetto(this.mConfig);
        }
        if (this.mConfig.logToStatsd()) {
            this.mStatsLog.write(305, this.mDisplayId, i18, this.mConfig.getStatsdInteractionType(), i7, i2, j, i, i13, max2);
        }
    }

    com.android.internal.jank.FrameTracker.ThreadedRendererWrapper getThreadedRenderer() {
        return this.mRendererWrapper;
    }

    com.android.internal.jank.FrameTracker.ViewRootWrapper getViewRoot() {
        return this.mViewRoot;
    }

    private boolean shouldTriggerPerfetto(int i, int i2) {
        return (this.mTraceThresholdMissedFrames != -1 && i >= this.mTraceThresholdMissedFrames) || (!this.mSurfaceOnly && this.mTraceThresholdFrameTimeMillis != -1 && i2 >= this.mTraceThresholdFrameTimeMillis * 1000000);
    }

    public void removeObservers() {
        this.mSurfaceControlWrapper.removeJankStatsListener(this);
        if (!this.mSurfaceOnly) {
            this.mRendererWrapper.removeObserver(this.mObserver);
            if (this.mSurfaceChangedCallback != null) {
                this.mViewRoot.removeSurfaceChangedCallback(this.mSurfaceChangedCallback);
            }
        }
    }

    public static class FrameMetricsWrapper {
        private final android.view.FrameMetrics mFrameMetrics = new android.view.FrameMetrics();

        public long[] getTiming() {
            return this.mFrameMetrics.mTimingData;
        }

        public long getMetric(int i) {
            return this.mFrameMetrics.getMetric(i);
        }
    }

    public static class ThreadedRendererWrapper {
        private final android.view.ThreadedRenderer mRenderer;

        public ThreadedRendererWrapper(android.view.ThreadedRenderer threadedRenderer) {
            this.mRenderer = threadedRenderer;
        }

        public void addObserver(android.graphics.HardwareRendererObserver hardwareRendererObserver) {
            this.mRenderer.addObserver(hardwareRendererObserver);
        }

        public void removeObserver(android.graphics.HardwareRendererObserver hardwareRendererObserver) {
            this.mRenderer.removeObserver(hardwareRendererObserver);
        }
    }

    public static class ViewRootWrapper {
        private final android.view.ViewRootImpl mViewRoot;

        public ViewRootWrapper(android.view.ViewRootImpl viewRootImpl) {
            this.mViewRoot = viewRootImpl;
        }

        public void addSurfaceChangedCallback(android.view.ViewRootImpl.SurfaceChangedCallback surfaceChangedCallback) {
            this.mViewRoot.addSurfaceChangedCallback(surfaceChangedCallback);
        }

        public void removeSurfaceChangedCallback(android.view.ViewRootImpl.SurfaceChangedCallback surfaceChangedCallback) {
            this.mViewRoot.removeSurfaceChangedCallback(surfaceChangedCallback);
        }

        public android.view.SurfaceControl getSurfaceControl() {
            return this.mViewRoot.getSurfaceControl();
        }

        void requestInvalidateRootRenderNode() {
            this.mViewRoot.requestInvalidateRootRenderNode();
        }

        void addWindowCallbacks(android.view.WindowCallbacks windowCallbacks) {
            this.mViewRoot.addWindowCallbacks(windowCallbacks);
        }

        void removeWindowCallbacks(android.view.WindowCallbacks windowCallbacks) {
            this.mViewRoot.removeWindowCallbacks(windowCallbacks);
        }

        android.view.View getView() {
            return this.mViewRoot.getView();
        }

        int dipToPx(int i) {
            return (int) ((this.mViewRoot.mContext.getResources().getDisplayMetrics().density * i) + 0.5f);
        }
    }

    public static class SurfaceControlWrapper {
        public void addJankStatsListener(android.view.SurfaceControl.OnJankDataListener onJankDataListener, android.view.SurfaceControl surfaceControl) {
            android.view.SurfaceControl.addJankDataListener(onJankDataListener, surfaceControl);
        }

        public void removeJankStatsListener(android.view.SurfaceControl.OnJankDataListener onJankDataListener) {
            android.view.SurfaceControl.removeJankDataListener(onJankDataListener);
        }
    }

    public static class ChoreographerWrapper {
        private final android.view.Choreographer mChoreographer;

        public ChoreographerWrapper(android.view.Choreographer choreographer) {
            this.mChoreographer = choreographer;
        }

        public long getVsyncId() {
            return this.mChoreographer.getVsyncId();
        }
    }

    public static class StatsLogWrapper {
        private final com.android.internal.jank.DisplayResolutionTracker mDisplayResolutionTracker;

        public StatsLogWrapper(com.android.internal.jank.DisplayResolutionTracker displayResolutionTracker) {
            this.mDisplayResolutionTracker = displayResolutionTracker;
        }

        public void write(int i, int i2, int i3, int i4, long j, long j2, long j3, long j4, long j5, long j6) {
            com.android.internal.util.FrameworkStatsLog.write(i, i4, j, j2, j3, j4, j5, j6, this.mDisplayResolutionTracker.getResolution(i2), i3);
        }
    }
}
