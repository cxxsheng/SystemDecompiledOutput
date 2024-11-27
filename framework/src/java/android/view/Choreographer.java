package android.view;

/* loaded from: classes4.dex */
public final class Choreographer {
    public static final int CALLBACK_ANIMATION = 1;
    public static final int CALLBACK_COMMIT = 4;
    public static final int CALLBACK_INPUT = 0;
    public static final int CALLBACK_INSETS_ANIMATION = 2;
    private static final int CALLBACK_LAST = 4;
    public static final int CALLBACK_TRAVERSAL = 3;
    private static final boolean DEBUG_FRAMES = false;
    private static final boolean DEBUG_JANK = false;
    private static final long DEFAULT_FRAME_DELAY = 10;
    private static final int MSG_DO_FRAME = 0;
    private static final int MSG_DO_SCHEDULE_CALLBACK = 2;
    private static final int MSG_DO_SCHEDULE_VSYNC = 1;
    private static final java.lang.String TAG = "Choreographer";
    private static volatile android.view.Choreographer mMainInstance;
    private android.view.Choreographer.CallbackRecord mCallbackPool;
    private final android.view.Choreographer.CallbackQueue[] mCallbackQueues;
    private boolean mCallbacksRunning;
    private boolean mDebugPrintNextFrameTimeDelta;
    private final android.view.Choreographer.FrameDisplayEventReceiver mDisplayEventReceiver;
    private int mFPSDivisor;
    private final android.view.Choreographer.FrameData mFrameData;
    android.graphics.FrameInfo mFrameInfo;

    @java.lang.Deprecated
    private long mFrameIntervalNanos;
    private boolean mFrameScheduled;
    private final android.view.Choreographer.FrameHandler mHandler;
    private long mLastFrameIntervalNanos;
    private long mLastFrameTimeNanos;
    private final android.view.DisplayEventReceiver.VsyncEventData mLastVsyncEventData;
    private final java.lang.Object mLock;
    private final android.os.Looper mLooper;
    private static volatile long sFrameDelay = 10;
    private static final java.lang.ThreadLocal<android.view.Choreographer> sThreadInstance = new java.lang.ThreadLocal<android.view.Choreographer>() { // from class: android.view.Choreographer.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public android.view.Choreographer initialValue() {
            android.os.Looper myLooper = android.os.Looper.myLooper();
            if (myLooper == null) {
                throw new java.lang.IllegalStateException("The current thread must have a looper!");
            }
            android.view.Choreographer choreographer = new android.view.Choreographer(myLooper, 0);
            if (myLooper == android.os.Looper.getMainLooper()) {
                android.view.Choreographer.mMainInstance = choreographer;
            }
            return choreographer;
        }
    };
    private static final java.lang.ThreadLocal<android.view.Choreographer> sSfThreadInstance = new java.lang.ThreadLocal<android.view.Choreographer>() { // from class: android.view.Choreographer.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public android.view.Choreographer initialValue() {
            android.os.Looper myLooper = android.os.Looper.myLooper();
            if (myLooper == null) {
                throw new java.lang.IllegalStateException("The current thread must have a looper!");
            }
            return new android.view.Choreographer(myLooper, 1);
        }
    };
    private static final boolean USE_VSYNC = android.os.SystemProperties.getBoolean("debug.choreographer.vsync", true);
    private static final boolean USE_FRAME_TIME = android.os.SystemProperties.getBoolean("debug.choreographer.frametime", true);
    private static final int SKIPPED_FRAME_WARNING_LIMIT = android.os.SystemProperties.getInt("debug.choreographer.skipwarning", 30);
    private static final java.lang.Object FRAME_CALLBACK_TOKEN = new java.lang.Object() { // from class: android.view.Choreographer.3
        public java.lang.String toString() {
            return "FRAME_CALLBACK_TOKEN";
        }
    };
    private static final java.lang.Object VSYNC_CALLBACK_TOKEN = new java.lang.Object() { // from class: android.view.Choreographer.4
        public java.lang.String toString() {
            return "VSYNC_CALLBACK_TOKEN";
        }
    };
    private static final java.lang.String[] CALLBACK_TRACE_TITLES = {"input", "animation", "insets_animation", "traversal", "commit"};

    public interface FrameCallback {
        void doFrame(long j);
    }

    public interface VsyncCallback {
        void onVsync(android.view.Choreographer.FrameData frameData);
    }

    private Choreographer(android.os.Looper looper, int i) {
        this(looper, i, 0L);
    }

    private Choreographer(android.os.Looper looper, int i, long j) {
        android.view.Choreographer.FrameDisplayEventReceiver frameDisplayEventReceiver;
        this.mLock = new java.lang.Object();
        this.mFPSDivisor = 1;
        this.mLastVsyncEventData = new android.view.DisplayEventReceiver.VsyncEventData();
        this.mFrameData = new android.view.Choreographer.FrameData();
        this.mFrameInfo = new android.graphics.FrameInfo();
        this.mLooper = looper;
        this.mHandler = new android.view.Choreographer.FrameHandler(looper);
        if (USE_VSYNC) {
            frameDisplayEventReceiver = new android.view.Choreographer.FrameDisplayEventReceiver(looper, i, j);
        } else {
            frameDisplayEventReceiver = null;
        }
        this.mDisplayEventReceiver = frameDisplayEventReceiver;
        this.mLastFrameTimeNanos = Long.MIN_VALUE;
        this.mFrameIntervalNanos = (long) (1.0E9f / getRefreshRate());
        this.mCallbackQueues = new android.view.Choreographer.CallbackQueue[5];
        for (int i2 = 0; i2 <= 4; i2++) {
            this.mCallbackQueues[i2] = new android.view.Choreographer.CallbackQueue();
        }
        setFPSDivisor(android.os.SystemProperties.getInt(android.view.ThreadedRenderer.DEBUG_FPS_DIVISOR, 1));
    }

    private static float getRefreshRate() {
        return android.hardware.display.DisplayManagerGlobal.getInstance().getDisplayInfo(0).getRefreshRate();
    }

    public static android.view.Choreographer getInstance() {
        return sThreadInstance.get();
    }

    public static android.view.Choreographer getSfInstance() {
        return sSfThreadInstance.get();
    }

    static android.view.Choreographer getInstanceForSurfaceControl(long j, android.os.Looper looper) {
        if (looper == null) {
            throw new java.lang.IllegalStateException("The current thread must have a looper!");
        }
        return new android.view.Choreographer(looper, 0, j);
    }

    public static android.view.Choreographer getMainThreadInstance() {
        return mMainInstance;
    }

    public static void releaseInstance() {
        android.view.Choreographer choreographer = sThreadInstance.get();
        sThreadInstance.remove();
        choreographer.dispose();
    }

    private void dispose() {
        this.mDisplayEventReceiver.dispose();
    }

    void invalidate() {
        dispose();
    }

    boolean isTheLooperSame(android.os.Looper looper) {
        return this.mLooper == looper;
    }

    public android.os.Looper getLooper() {
        return this.mLooper;
    }

    public static long getFrameDelay() {
        return sFrameDelay;
    }

    public static void setFrameDelay(long j) {
        sFrameDelay = j;
    }

    public static long subtractFrameDelay(long j) {
        long j2 = sFrameDelay;
        if (j <= j2) {
            return 0L;
        }
        return j - j2;
    }

    public long getFrameIntervalNanos() {
        long j;
        synchronized (this.mLock) {
            j = this.mLastFrameIntervalNanos;
        }
        return j;
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        java.lang.String str2 = str + "  ";
        printWriter.print(str);
        printWriter.println("Choreographer:");
        printWriter.print(str2);
        printWriter.print("mFrameScheduled=");
        printWriter.println(this.mFrameScheduled);
        printWriter.print(str2);
        printWriter.print("mLastFrameTime=");
        printWriter.println(android.util.TimeUtils.formatUptime(this.mLastFrameTimeNanos / 1000000));
    }

    public void postCallback(int i, java.lang.Runnable runnable, java.lang.Object obj) {
        postCallbackDelayed(i, runnable, obj, 0L);
    }

    public void postCallbackDelayed(int i, java.lang.Runnable runnable, java.lang.Object obj, long j) {
        if (runnable == null) {
            throw new java.lang.IllegalArgumentException("action must not be null");
        }
        if (i < 0 || i > 4) {
            throw new java.lang.IllegalArgumentException("callbackType is invalid");
        }
        postCallbackDelayedInternal(i, runnable, obj, j);
    }

    private void postCallbackDelayedInternal(int i, java.lang.Object obj, java.lang.Object obj2, long j) {
        synchronized (this.mLock) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            long j2 = j + uptimeMillis;
            this.mCallbackQueues[i].addCallbackLocked(j2, obj, obj2);
            if (j2 <= uptimeMillis) {
                scheduleFrameLocked(uptimeMillis);
            } else {
                android.os.Message obtainMessage = this.mHandler.obtainMessage(2, obj);
                obtainMessage.arg1 = i;
                obtainMessage.setAsynchronous(true);
                this.mHandler.sendMessageAtTime(obtainMessage, j2);
            }
        }
    }

    public void postVsyncCallback(android.view.Choreographer.VsyncCallback vsyncCallback) {
        if (vsyncCallback == null) {
            throw new java.lang.IllegalArgumentException("callback must not be null");
        }
        postCallbackDelayedInternal(1, vsyncCallback, VSYNC_CALLBACK_TOKEN, 0L);
    }

    public void removeCallbacks(int i, java.lang.Runnable runnable, java.lang.Object obj) {
        if (i < 0 || i > 4) {
            throw new java.lang.IllegalArgumentException("callbackType is invalid");
        }
        removeCallbacksInternal(i, runnable, obj);
    }

    private void removeCallbacksInternal(int i, java.lang.Object obj, java.lang.Object obj2) {
        synchronized (this.mLock) {
            this.mCallbackQueues[i].removeCallbacksLocked(obj, obj2);
            if (obj != null && obj2 == null) {
                this.mHandler.removeMessages(2, obj);
            }
        }
    }

    public void postFrameCallback(android.view.Choreographer.FrameCallback frameCallback) {
        postFrameCallbackDelayed(frameCallback, 0L);
    }

    public void postFrameCallbackDelayed(android.view.Choreographer.FrameCallback frameCallback, long j) {
        if (frameCallback == null) {
            throw new java.lang.IllegalArgumentException("callback must not be null");
        }
        postCallbackDelayedInternal(1, frameCallback, FRAME_CALLBACK_TOKEN, j);
    }

    public void removeFrameCallback(android.view.Choreographer.FrameCallback frameCallback) {
        if (frameCallback == null) {
            throw new java.lang.IllegalArgumentException("callback must not be null");
        }
        removeCallbacksInternal(1, frameCallback, FRAME_CALLBACK_TOKEN);
    }

    public void removeVsyncCallback(android.view.Choreographer.VsyncCallback vsyncCallback) {
        if (vsyncCallback == null) {
            throw new java.lang.IllegalArgumentException("callback must not be null");
        }
        removeCallbacksInternal(1, vsyncCallback, VSYNC_CALLBACK_TOKEN);
    }

    public long getFrameTime() {
        return getFrameTimeNanos() / 1000000;
    }

    public long getFrameTimeNanos() {
        long nanoTime;
        synchronized (this.mLock) {
            if (!this.mCallbacksRunning) {
                throw new java.lang.IllegalStateException("This method must only be called as part of a callback while a frame is in progress.");
            }
            nanoTime = USE_FRAME_TIME ? this.mLastFrameTimeNanos : java.lang.System.nanoTime();
        }
        return nanoTime;
    }

    public long getLastFrameTimeNanos() {
        long nanoTime;
        synchronized (this.mLock) {
            nanoTime = USE_FRAME_TIME ? this.mLastFrameTimeNanos : java.lang.System.nanoTime();
        }
        return nanoTime;
    }

    public long getExpectedPresentationTimeNanos() {
        return this.mFrameData.getPreferredFrameTimeline().getExpectedPresentationTimeNanos();
    }

    public long getExpectedPresentationTimeMillis() {
        return getExpectedPresentationTimeNanos() / 1000000;
    }

    public long getLatestExpectedPresentTimeNanos() {
        if (this.mDisplayEventReceiver == null) {
            return java.lang.System.nanoTime();
        }
        return this.mDisplayEventReceiver.getLatestVsyncEventData().preferredFrameTimeline().expectedPresentationTime;
    }

    private void scheduleFrameLocked(long j) {
        if (!this.mFrameScheduled) {
            this.mFrameScheduled = true;
            if (USE_VSYNC) {
                if (!isRunningOnLooperThreadLocked()) {
                    android.os.Message obtainMessage = this.mHandler.obtainMessage(1);
                    obtainMessage.setAsynchronous(true);
                    this.mHandler.sendMessageAtFrontOfQueue(obtainMessage);
                    return;
                }
                scheduleVsyncLocked();
                return;
            }
            long max = java.lang.Math.max((this.mLastFrameTimeNanos / 1000000) + sFrameDelay, j);
            android.os.Message obtainMessage2 = this.mHandler.obtainMessage(0);
            obtainMessage2.setAsynchronous(true);
            this.mHandler.sendMessageAtTime(obtainMessage2, max);
        }
    }

    public long getVsyncId() {
        return this.mLastVsyncEventData.preferredFrameTimeline().vsyncId;
    }

    public long getFrameDeadline() {
        return this.mLastVsyncEventData.preferredFrameTimeline().deadline;
    }

    void setFPSDivisor(int i) {
        if (i <= 0) {
            i = 1;
        }
        this.mFPSDivisor = i;
        android.view.ThreadedRenderer.setFPSDivisor(i);
    }

    private void traceMessage(java.lang.String str) {
        android.os.Trace.traceBegin(8L, str);
        android.os.Trace.traceEnd(8L);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01e0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void doFrame(long j, int i, android.view.DisplayEventReceiver.VsyncEventData vsyncEventData) {
        long j2;
        java.lang.Object obj;
        android.view.Choreographer.FrameTimeline frameTimeline;
        long j3;
        boolean z;
        long j4;
        long j5;
        long j6;
        long j7;
        long j8 = vsyncEventData.frameInterval;
        boolean z2 = false;
        try {
            android.view.Choreographer.FrameTimeline update = this.mFrameData.update(j, vsyncEventData);
            if (android.os.Trace.isTagEnabled(8L)) {
                android.os.Trace.traceBegin(8L, "Choreographer#doFrame " + update.mVsyncId);
            }
            java.lang.Object obj2 = this.mLock;
            synchronized (obj2) {
                try {
                    if (!this.mFrameScheduled) {
                        traceMessage("Frame not scheduled");
                        android.view.animation.AnimationUtils.unlockAnimationClock();
                        android.os.Trace.traceEnd(8L);
                        return;
                    }
                    long nanoTime = java.lang.System.nanoTime();
                    long j9 = nanoTime - j;
                    if (j9 >= j8) {
                        if (j8 == 0) {
                            android.util.Log.i(TAG, "Vsync data empty due to timeout");
                            j7 = nanoTime;
                        } else {
                            try {
                                long j10 = nanoTime - (j9 % j8);
                                long j11 = j9 / j8;
                                if (j11 >= SKIPPED_FRAME_WARNING_LIMIT) {
                                    android.util.Log.i(TAG, "Skipped " + j11 + " frames!  The application may be doing too much work on its main thread.");
                                }
                                j7 = j10;
                            } catch (java.lang.Throwable th) {
                                th = th;
                                obj = obj2;
                                j2 = 8;
                                while (true) {
                                    try {
                                        try {
                                            throw th;
                                        } catch (java.lang.Throwable th2) {
                                            th = th2;
                                            android.view.animation.AnimationUtils.unlockAnimationClock();
                                            if (z2) {
                                                android.os.Trace.traceEnd(j2);
                                            }
                                            android.os.Trace.traceEnd(j2);
                                            throw th;
                                        }
                                    } catch (java.lang.Throwable th3) {
                                        th = th3;
                                    }
                                }
                            }
                        }
                        frameTimeline = this.mFrameData.update(j7, this.mDisplayEventReceiver, j9);
                        j3 = j7;
                        z = true;
                    } else {
                        frameTimeline = update;
                        j3 = j;
                        z = false;
                    }
                    try {
                        if (j3 < this.mLastFrameTimeNanos) {
                            try {
                                traceMessage("Frame time goes backward");
                                scheduleVsyncLocked();
                                android.view.animation.AnimationUtils.unlockAnimationClock();
                                if (z) {
                                    j4 = 8;
                                    android.os.Trace.traceEnd(8L);
                                } else {
                                    j4 = 8;
                                }
                                android.os.Trace.traceEnd(j4);
                                return;
                            } catch (java.lang.Throwable th4) {
                                th = th4;
                                obj = obj2;
                                z2 = z;
                                j2 = 8;
                                while (true) {
                                    throw th;
                                }
                            }
                        }
                        if (this.mFPSDivisor > 1) {
                            try {
                                long j12 = j3 - this.mLastFrameTimeNanos;
                                if (j12 < this.mFPSDivisor * j8 && j12 > 0) {
                                    traceMessage("Frame skipped due to FPSDivisor");
                                    scheduleVsyncLocked();
                                    android.view.animation.AnimationUtils.unlockAnimationClock();
                                    if (z) {
                                        j6 = 8;
                                        android.os.Trace.traceEnd(8L);
                                    } else {
                                        j6 = 8;
                                    }
                                    android.os.Trace.traceEnd(j6);
                                    return;
                                }
                                j5 = 8;
                            } catch (java.lang.Throwable th5) {
                                th = th5;
                                obj = obj2;
                                z2 = z;
                                j2 = 8;
                                while (true) {
                                    throw th;
                                }
                            }
                        } else {
                            j5 = 8;
                        }
                        try {
                            obj = obj2;
                            long j13 = j3;
                            try {
                                this.mFrameInfo.setVsync(j, j3, vsyncEventData.preferredFrameTimeline().vsyncId, vsyncEventData.preferredFrameTimeline().deadline, nanoTime, vsyncEventData.frameInterval);
                                this.mFrameScheduled = false;
                                this.mLastFrameTimeNanos = j13;
                                this.mLastFrameIntervalNanos = j8;
                                this.mLastVsyncEventData.copyFrom(vsyncEventData);
                                if (z) {
                                    try {
                                        if (android.os.Trace.isTagEnabled(j5)) {
                                            j2 = j5;
                                            try {
                                                android.os.Trace.traceBegin(j2, java.lang.String.format("Choreographer#doFrame - resynced to %d in %.1fms", java.lang.Long.valueOf(frameTimeline.mVsyncId), java.lang.Float.valueOf((frameTimeline.mDeadlineNanos - nanoTime) * 1.0E-6f)));
                                                android.view.animation.AnimationUtils.lockAnimationClock(j13 / 1000000, frameTimeline.mExpectedPresentationTimeNanos);
                                                this.mFrameInfo.markInputHandlingStart();
                                                doCallbacks(0, j8);
                                                this.mFrameInfo.markAnimationsStart();
                                                doCallbacks(1, j8);
                                                doCallbacks(2, j8);
                                                this.mFrameInfo.markPerformTraversalsStart();
                                                doCallbacks(3, j8);
                                                doCallbacks(4, j8);
                                                android.view.animation.AnimationUtils.unlockAnimationClock();
                                                if (z) {
                                                    android.os.Trace.traceEnd(j2);
                                                }
                                                android.os.Trace.traceEnd(j2);
                                            } catch (java.lang.Throwable th6) {
                                                th = th6;
                                                z2 = z;
                                                android.view.animation.AnimationUtils.unlockAnimationClock();
                                                if (z2) {
                                                }
                                                android.os.Trace.traceEnd(j2);
                                                throw th;
                                            }
                                        }
                                    } catch (java.lang.Throwable th7) {
                                        th = th7;
                                        j2 = j5;
                                        z2 = z;
                                        android.view.animation.AnimationUtils.unlockAnimationClock();
                                        if (z2) {
                                        }
                                        android.os.Trace.traceEnd(j2);
                                        throw th;
                                    }
                                }
                                j2 = j5;
                                android.view.animation.AnimationUtils.lockAnimationClock(j13 / 1000000, frameTimeline.mExpectedPresentationTimeNanos);
                                this.mFrameInfo.markInputHandlingStart();
                                doCallbacks(0, j8);
                                this.mFrameInfo.markAnimationsStart();
                                doCallbacks(1, j8);
                                doCallbacks(2, j8);
                                this.mFrameInfo.markPerformTraversalsStart();
                                doCallbacks(3, j8);
                                doCallbacks(4, j8);
                                android.view.animation.AnimationUtils.unlockAnimationClock();
                                if (z) {
                                }
                                android.os.Trace.traceEnd(j2);
                            } catch (java.lang.Throwable th8) {
                                th = th8;
                                j2 = j5;
                                z2 = z;
                                while (true) {
                                    throw th;
                                }
                            }
                        } catch (java.lang.Throwable th9) {
                            th = th9;
                            obj = obj2;
                        }
                    } catch (java.lang.Throwable th10) {
                        th = th10;
                        obj = obj2;
                        j2 = 8;
                    }
                } catch (java.lang.Throwable th11) {
                    th = th11;
                    obj = obj2;
                    j2 = 8;
                }
            }
        } catch (java.lang.Throwable th12) {
            th = th12;
            j2 = 8;
        }
    }

    void doCallbacks(int i, long j) {
        long j2 = this.mFrameData.mFrameTimeNanos;
        synchronized (this.mLock) {
            long nanoTime = java.lang.System.nanoTime();
            android.view.Choreographer.CallbackRecord extractDueCallbacksLocked = this.mCallbackQueues[i].extractDueCallbacksLocked(nanoTime / 1000000);
            if (extractDueCallbacksLocked == null) {
                return;
            }
            this.mCallbacksRunning = true;
            if (i == 4) {
                long j3 = nanoTime - j2;
                android.os.Trace.traceCounter(8L, "jitterNanos", (int) j3);
                if (j > 0 && j3 >= 2 * j) {
                    long j4 = nanoTime - ((j3 % j) + j);
                    this.mLastFrameTimeNanos = j4;
                    this.mFrameData.update(j4, this.mDisplayEventReceiver, j3);
                }
            }
            try {
                android.os.Trace.traceBegin(8L, CALLBACK_TRACE_TITLES[i]);
                for (android.view.Choreographer.CallbackRecord callbackRecord = extractDueCallbacksLocked; callbackRecord != null; callbackRecord = callbackRecord.next) {
                    callbackRecord.run(this.mFrameData);
                }
                synchronized (this.mLock) {
                    this.mCallbacksRunning = false;
                    while (true) {
                        android.view.Choreographer.CallbackRecord callbackRecord2 = extractDueCallbacksLocked.next;
                        recycleCallbackLocked(extractDueCallbacksLocked);
                        if (callbackRecord2 != null) {
                            extractDueCallbacksLocked = callbackRecord2;
                        }
                    }
                }
                android.os.Trace.traceEnd(8L);
            } catch (java.lang.Throwable th) {
                synchronized (this.mLock) {
                    this.mCallbacksRunning = false;
                    while (true) {
                        android.view.Choreographer.CallbackRecord callbackRecord3 = extractDueCallbacksLocked.next;
                        recycleCallbackLocked(extractDueCallbacksLocked);
                        if (callbackRecord3 == null) {
                            android.os.Trace.traceEnd(8L);
                            throw th;
                        }
                        extractDueCallbacksLocked = callbackRecord3;
                    }
                }
            }
        }
    }

    void doScheduleVsync() {
        synchronized (this.mLock) {
            if (this.mFrameScheduled) {
                scheduleVsyncLocked();
            }
        }
    }

    void doScheduleCallback(int i) {
        synchronized (this.mLock) {
            if (!this.mFrameScheduled) {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                if (this.mCallbackQueues[i].hasDueCallbacksLocked(uptimeMillis)) {
                    scheduleFrameLocked(uptimeMillis);
                }
            }
        }
    }

    private void scheduleVsyncLocked() {
        try {
            android.os.Trace.traceBegin(8L, "Choreographer#scheduleVsyncLocked");
            this.mDisplayEventReceiver.scheduleVsync();
        } finally {
            android.os.Trace.traceEnd(8L);
        }
    }

    private boolean isRunningOnLooperThreadLocked() {
        return android.os.Looper.myLooper() == this.mLooper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.Choreographer.CallbackRecord obtainCallbackLocked(long j, java.lang.Object obj, java.lang.Object obj2) {
        android.view.Choreographer.CallbackRecord callbackRecord = this.mCallbackPool;
        if (callbackRecord == null) {
            callbackRecord = new android.view.Choreographer.CallbackRecord();
        } else {
            this.mCallbackPool = callbackRecord.next;
            callbackRecord.next = null;
        }
        callbackRecord.dueTime = j;
        callbackRecord.action = obj;
        callbackRecord.token = obj2;
        return callbackRecord;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recycleCallbackLocked(android.view.Choreographer.CallbackRecord callbackRecord) {
        callbackRecord.action = null;
        callbackRecord.token = null;
        callbackRecord.next = this.mCallbackPool;
        this.mCallbackPool = callbackRecord;
    }

    public static class FrameTimeline {
        private long mVsyncId = -1;
        private long mExpectedPresentationTimeNanos = -1;
        private long mDeadlineNanos = -1;
        private boolean mInCallback = false;

        FrameTimeline() {
        }

        void setInCallback(boolean z) {
            this.mInCallback = z;
        }

        private void checkInCallback() {
            if (!this.mInCallback) {
                throw new java.lang.IllegalStateException("FrameTimeline is not valid outside of the vsync callback");
            }
        }

        void update(long j, long j2, long j3) {
            this.mVsyncId = j;
            this.mExpectedPresentationTimeNanos = j2;
            this.mDeadlineNanos = j3;
        }

        public long getVsyncId() {
            checkInCallback();
            return this.mVsyncId;
        }

        public long getExpectedPresentationTimeNanos() {
            checkInCallback();
            return this.mExpectedPresentationTimeNanos;
        }

        public long getDeadlineNanos() {
            checkInCallback();
            return this.mDeadlineNanos;
        }
    }

    public static class FrameData {
        private long mFrameTimeNanos;
        private android.view.Choreographer.FrameTimeline[] mFrameTimelines;
        private boolean mInCallback = false;
        private int mPreferredFrameTimelineIndex;

        FrameData() {
            allocateFrameTimelines(7);
        }

        public long getFrameTimeNanos() {
            checkInCallback();
            return this.mFrameTimeNanos;
        }

        public android.view.Choreographer.FrameTimeline[] getFrameTimelines() {
            checkInCallback();
            return this.mFrameTimelines;
        }

        public android.view.Choreographer.FrameTimeline getPreferredFrameTimeline() {
            checkInCallback();
            return this.mFrameTimelines[this.mPreferredFrameTimelineIndex];
        }

        void setInCallback(boolean z) {
            this.mInCallback = z;
            for (int i = 0; i < this.mFrameTimelines.length; i++) {
                this.mFrameTimelines[i].setInCallback(z);
            }
        }

        private void checkInCallback() {
            if (!this.mInCallback) {
                throw new java.lang.IllegalStateException("FrameData is not valid outside of the vsync callback");
            }
        }

        private void allocateFrameTimelines(int i) {
            int max = java.lang.Math.max(1, i);
            if (this.mFrameTimelines == null || this.mFrameTimelines.length != max) {
                this.mFrameTimelines = new android.view.Choreographer.FrameTimeline[max];
                for (int i2 = 0; i2 < this.mFrameTimelines.length; i2++) {
                    this.mFrameTimelines[i2] = new android.view.Choreographer.FrameTimeline();
                }
            }
        }

        android.view.Choreographer.FrameTimeline update(long j, android.view.DisplayEventReceiver.VsyncEventData vsyncEventData) {
            allocateFrameTimelines(vsyncEventData.frameTimelinesLength);
            this.mFrameTimeNanos = j;
            this.mPreferredFrameTimelineIndex = vsyncEventData.preferredFrameTimelineIndex;
            for (int i = 0; i < this.mFrameTimelines.length; i++) {
                android.view.DisplayEventReceiver.VsyncEventData.FrameTimeline frameTimeline = vsyncEventData.frameTimelines[i];
                this.mFrameTimelines[i].update(frameTimeline.vsyncId, frameTimeline.expectedPresentationTime, frameTimeline.deadline);
            }
            return this.mFrameTimelines[this.mPreferredFrameTimelineIndex];
        }

        android.view.Choreographer.FrameTimeline update(long j, android.view.DisplayEventReceiver displayEventReceiver, long j2) {
            long j3 = this.mFrameTimelines[this.mPreferredFrameTimelineIndex].mDeadlineNanos + j2;
            int i = 0;
            while (i < this.mFrameTimelines.length - 1 && this.mFrameTimelines[i].mDeadlineNanos < j3) {
                i++;
            }
            if (this.mFrameTimelines[i].mDeadlineNanos < j3) {
                android.view.DisplayEventReceiver.VsyncEventData latestVsyncEventData = displayEventReceiver.getLatestVsyncEventData();
                if (latestVsyncEventData == null) {
                    android.util.Log.w(android.view.Choreographer.TAG, "Could not get latest VsyncEventData. Did SurfaceFlinger crash?");
                } else {
                    update(j, latestVsyncEventData);
                }
            } else {
                update(j, i);
            }
            return this.mFrameTimelines[this.mPreferredFrameTimelineIndex];
        }

        void update(long j, int i) {
            this.mFrameTimeNanos = j;
            this.mPreferredFrameTimelineIndex = i;
        }
    }

    private final class FrameHandler extends android.os.Handler {
        public FrameHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    android.view.Choreographer.this.doFrame(java.lang.System.nanoTime(), 0, new android.view.DisplayEventReceiver.VsyncEventData());
                    break;
                case 1:
                    android.view.Choreographer.this.doScheduleVsync();
                    break;
                case 2:
                    android.view.Choreographer.this.doScheduleCallback(message.arg1);
                    break;
            }
        }
    }

    private final class FrameDisplayEventReceiver extends android.view.DisplayEventReceiver implements java.lang.Runnable {
        private int mFrame;
        private boolean mHavePendingVsync;
        private final android.view.DisplayEventReceiver.VsyncEventData mLastVsyncEventData;
        private long mTimestampNanos;

        FrameDisplayEventReceiver(android.os.Looper looper, int i, long j) {
            super(looper, i, 0, j);
            this.mLastVsyncEventData = new android.view.DisplayEventReceiver.VsyncEventData();
        }

        @Override // android.view.DisplayEventReceiver
        public void onVsync(long j, long j2, int i, android.view.DisplayEventReceiver.VsyncEventData vsyncEventData) {
            try {
                if (android.os.Trace.isTagEnabled(8L)) {
                    android.os.Trace.traceBegin(8L, "Choreographer#onVsync " + vsyncEventData.preferredFrameTimeline().vsyncId);
                }
                long nanoTime = java.lang.System.nanoTime();
                if (j > nanoTime) {
                    android.util.Log.w(android.view.Choreographer.TAG, "Frame time is " + ((j - nanoTime) * 1.0E-6f) + " ms in the future!  Check that graphics HAL is generating vsync timestamps using the correct timebase.");
                    j = nanoTime;
                }
                if (this.mHavePendingVsync) {
                    android.util.Log.w(android.view.Choreographer.TAG, "Already have a pending vsync event.  There should only be one at a time.");
                } else {
                    this.mHavePendingVsync = true;
                }
                this.mTimestampNanos = j;
                this.mFrame = i;
                this.mLastVsyncEventData.copyFrom(vsyncEventData);
                android.os.Message obtain = android.os.Message.obtain(android.view.Choreographer.this.mHandler, this);
                obtain.setAsynchronous(true);
                android.view.Choreographer.this.mHandler.sendMessageAtTime(obtain, j / 1000000);
            } finally {
                android.os.Trace.traceEnd(8L);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mHavePendingVsync = false;
            android.view.Choreographer.this.doFrame(this.mTimestampNanos, this.mFrame, this.mLastVsyncEventData);
        }
    }

    private static final class CallbackRecord {
        public java.lang.Object action;
        public long dueTime;
        public android.view.Choreographer.CallbackRecord next;
        public java.lang.Object token;

        private CallbackRecord() {
        }

        public void run(long j) {
            if (this.token == android.view.Choreographer.FRAME_CALLBACK_TOKEN) {
                ((android.view.Choreographer.FrameCallback) this.action).doFrame(j);
            } else {
                ((java.lang.Runnable) this.action).run();
            }
        }

        void run(android.view.Choreographer.FrameData frameData) {
            frameData.setInCallback(true);
            if (this.token == android.view.Choreographer.VSYNC_CALLBACK_TOKEN) {
                ((android.view.Choreographer.VsyncCallback) this.action).onVsync(frameData);
            } else {
                run(frameData.getFrameTimeNanos());
            }
            frameData.setInCallback(false);
        }
    }

    private final class CallbackQueue {
        private android.view.Choreographer.CallbackRecord mHead;

        private CallbackQueue() {
        }

        public boolean hasDueCallbacksLocked(long j) {
            return this.mHead != null && this.mHead.dueTime <= j;
        }

        public android.view.Choreographer.CallbackRecord extractDueCallbacksLocked(long j) {
            android.view.Choreographer.CallbackRecord callbackRecord = this.mHead;
            if (callbackRecord == null || callbackRecord.dueTime > j) {
                return null;
            }
            android.view.Choreographer.CallbackRecord callbackRecord2 = callbackRecord.next;
            android.view.Choreographer.CallbackRecord callbackRecord3 = callbackRecord;
            while (true) {
                if (callbackRecord2 == null) {
                    break;
                }
                if (callbackRecord2.dueTime > j) {
                    callbackRecord3.next = null;
                    break;
                }
                callbackRecord3 = callbackRecord2;
                callbackRecord2 = callbackRecord2.next;
            }
            this.mHead = callbackRecord2;
            return callbackRecord;
        }

        public void addCallbackLocked(long j, java.lang.Object obj, java.lang.Object obj2) {
            android.view.Choreographer.CallbackRecord obtainCallbackLocked = android.view.Choreographer.this.obtainCallbackLocked(j, obj, obj2);
            android.view.Choreographer.CallbackRecord callbackRecord = this.mHead;
            if (callbackRecord == null) {
                this.mHead = obtainCallbackLocked;
                return;
            }
            if (j < callbackRecord.dueTime) {
                obtainCallbackLocked.next = callbackRecord;
                this.mHead = obtainCallbackLocked;
                return;
            }
            while (true) {
                if (callbackRecord.next == null) {
                    break;
                }
                if (j < callbackRecord.next.dueTime) {
                    obtainCallbackLocked.next = callbackRecord.next;
                    break;
                }
                callbackRecord = callbackRecord.next;
            }
            callbackRecord.next = obtainCallbackLocked;
        }

        public void removeCallbacksLocked(java.lang.Object obj, java.lang.Object obj2) {
            android.view.Choreographer.CallbackRecord callbackRecord = this.mHead;
            android.view.Choreographer.CallbackRecord callbackRecord2 = null;
            while (callbackRecord != null) {
                android.view.Choreographer.CallbackRecord callbackRecord3 = callbackRecord.next;
                if ((obj == null || callbackRecord.action == obj) && (obj2 == null || callbackRecord.token == obj2)) {
                    if (callbackRecord2 != null) {
                        callbackRecord2.next = callbackRecord3;
                    } else {
                        this.mHead = callbackRecord3;
                    }
                    android.view.Choreographer.this.recycleCallbackLocked(callbackRecord);
                } else {
                    callbackRecord2 = callbackRecord;
                }
                callbackRecord = callbackRecord3;
            }
        }
    }
}
