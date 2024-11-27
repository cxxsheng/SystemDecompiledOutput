package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class VibrationStepConductor implements android.os.IBinder.DeathRecipient {
    static final long CALLBACKS_EXTRA_TIMEOUT = 1000;
    private static final boolean DEBUG = false;
    static final java.util.List<com.android.server.vibrator.Step> EMPTY_STEP_LIST = new java.util.ArrayList();
    static final float RAMP_OFF_AMPLITUDE_MIN = 0.001f;
    private static final java.lang.String TAG = "VibrationThread";
    private final com.android.server.vibrator.DeviceAdapter mDeviceAdapter;
    private int mPendingVibrateSteps;
    private int mRemainingStartSequentialEffectSteps;

    @android.annotation.Nullable
    private final java.util.concurrent.CompletableFuture<java.lang.Void> mRequestVibrationParamsFuture;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.IntArray mSignalVibratorsComplete;
    private int mSuccessfulVibratorOnSteps;
    private final com.android.server.vibrator.HalVibration mVibration;
    private final com.android.server.vibrator.VibrationScaler mVibrationScaler;
    public final com.android.server.vibrator.VibrationSettings vibrationSettings;
    public final com.android.server.vibrator.VibrationThread.VibratorManagerHooks vibratorManagerHooks;
    private final java.util.PriorityQueue<com.android.server.vibrator.Step> mNextSteps = new java.util.PriorityQueue<>();
    private final java.util.Queue<com.android.server.vibrator.Step> mPendingOnVibratorCompleteSteps = new java.util.LinkedList();
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.vibrator.Vibration.EndInfo mSignalCancel = null;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSignalCancelImmediate = false;

    @android.annotation.Nullable
    private com.android.server.vibrator.Vibration.EndInfo mCancelledVibrationEndInfo = null;
    private boolean mCancelledImmediately = false;

    VibrationStepConductor(com.android.server.vibrator.HalVibration halVibration, com.android.server.vibrator.VibrationSettings vibrationSettings, com.android.server.vibrator.DeviceAdapter deviceAdapter, com.android.server.vibrator.VibrationScaler vibrationScaler, java.util.concurrent.CompletableFuture<java.lang.Void> completableFuture, com.android.server.vibrator.VibrationThread.VibratorManagerHooks vibratorManagerHooks) {
        this.mVibration = halVibration;
        this.vibrationSettings = vibrationSettings;
        this.mDeviceAdapter = deviceAdapter;
        this.mVibrationScaler = vibrationScaler;
        this.mRequestVibrationParamsFuture = completableFuture;
        this.vibratorManagerHooks = vibratorManagerHooks;
        this.mSignalVibratorsComplete = new android.util.IntArray(this.mDeviceAdapter.getAvailableVibratorIds().length);
    }

    @android.annotation.Nullable
    com.android.server.vibrator.AbstractVibratorStep nextVibrateStep(long j, com.android.server.vibrator.VibratorController vibratorController, android.os.VibrationEffect.Composed composed, int i, long j2) {
        int i2;
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        if (i < composed.getSegments().size()) {
            i2 = i;
        } else {
            i2 = composed.getRepeatIndex();
        }
        if (i2 < 0) {
            return new com.android.server.vibrator.CompleteEffectVibratorStep(this, j, false, vibratorController, j2);
        }
        android.os.vibrator.VibrationEffectSegment vibrationEffectSegment = (android.os.vibrator.VibrationEffectSegment) composed.getSegments().get(i2);
        if (vibrationEffectSegment instanceof android.os.vibrator.PrebakedSegment) {
            return new com.android.server.vibrator.PerformPrebakedVibratorStep(this, j, vibratorController, composed, i2, j2);
        }
        if (vibrationEffectSegment instanceof android.os.vibrator.PrimitiveSegment) {
            return new com.android.server.vibrator.ComposePrimitivesVibratorStep(this, j, vibratorController, composed, i2, j2);
        }
        if (vibrationEffectSegment instanceof android.os.vibrator.RampSegment) {
            return new com.android.server.vibrator.ComposePwleVibratorStep(this, j, vibratorController, composed, i2, j2);
        }
        return new com.android.server.vibrator.SetAmplitudeVibratorStep(this, j, vibratorController, composed, i2, j2);
    }

    public void prepareToStart() {
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        if (!this.mVibration.callerInfo.attrs.isFlagSet(16)) {
            if (android.os.vibrator.Flags.adaptiveHapticsEnabled()) {
                waitForVibrationParamsIfRequired();
            }
            this.mVibration.scaleEffects(this.mVibrationScaler);
        } else {
            this.mVibration.resolveEffects(this.mVibrationScaler.getDefaultVibrationAmplitude());
        }
        this.mVibration.adaptToDevice(this.mDeviceAdapter);
        android.os.CombinedVibration.Sequential sequential = toSequential(this.mVibration.getEffectToPlay());
        this.mPendingVibrateSteps++;
        this.mRemainingStartSequentialEffectSteps = sequential.getEffects().size();
        this.mNextSteps.offer(new com.android.server.vibrator.StartSequentialEffectStep(this, sequential));
        this.mVibration.stats.reportStarted();
    }

    public com.android.server.vibrator.HalVibration getVibration() {
        return this.mVibration;
    }

    android.util.SparseArray<com.android.server.vibrator.VibratorController> getVibrators() {
        return this.mDeviceAdapter.getAvailableVibrators();
    }

    public boolean isFinished() {
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        if (this.mCancelledImmediately) {
            return true;
        }
        return this.mPendingOnVibratorCompleteSteps.isEmpty() && this.mNextSteps.isEmpty();
    }

    @android.annotation.Nullable
    public com.android.server.vibrator.Vibration.EndInfo calculateVibrationEndInfo() {
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        if (this.mCancelledVibrationEndInfo != null) {
            return this.mCancelledVibrationEndInfo;
        }
        if (this.mPendingVibrateSteps > 0 || this.mRemainingStartSequentialEffectSteps > 0) {
            return null;
        }
        if (this.mSuccessfulVibratorOnSteps > 0) {
            return new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.FINISHED);
        }
        return new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.IGNORED_UNSUPPORTED);
    }

    public boolean waitUntilNextStepIsDue() {
        com.android.server.vibrator.Step peek;
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        processAllNotifySignals();
        if (this.mCancelledImmediately) {
            return false;
        }
        if (!this.mPendingOnVibratorCompleteSteps.isEmpty() || (peek = this.mNextSteps.peek()) == null) {
            return true;
        }
        long calculateWaitTime = peek.calculateWaitTime();
        if (calculateWaitTime <= 0) {
            return true;
        }
        synchronized (this.mLock) {
            if (hasPendingNotifySignalLocked()) {
                return false;
            }
            try {
                this.mLock.wait(calculateWaitTime);
            } catch (java.lang.InterruptedException e) {
            }
            return false;
        }
    }

    @android.annotation.Nullable
    private com.android.server.vibrator.Step pollNext() {
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        if (!this.mPendingOnVibratorCompleteSteps.isEmpty()) {
            return this.mPendingOnVibratorCompleteSteps.poll();
        }
        return this.mNextSteps.poll();
    }

    public void runNextStep() {
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        com.android.server.vibrator.Step pollNext = pollNext();
        if (pollNext != null) {
            java.util.List<com.android.server.vibrator.Step> play = pollNext.play();
            if (pollNext.getVibratorOnDuration() > 0) {
                this.mSuccessfulVibratorOnSteps++;
            }
            if (pollNext instanceof com.android.server.vibrator.StartSequentialEffectStep) {
                this.mRemainingStartSequentialEffectSteps--;
            }
            if (!pollNext.isCleanUp()) {
                this.mPendingVibrateSteps--;
            }
            for (int i = 0; i < play.size(); i++) {
                this.mPendingVibrateSteps += !play.get(i).isCleanUp() ? 1 : 0;
            }
            this.mNextSteps.addAll(play);
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        notifyCancelled(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.CANCELLED_BINDER_DIED), false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0041, code lost:
    
        if (r3.mSignalCancelImmediate == false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void notifyCancelled(@android.annotation.NonNull com.android.server.vibrator.Vibration.EndInfo endInfo, boolean z) {
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(false);
        }
        if (endInfo == null || !endInfo.status.name().startsWith("CANCEL")) {
            android.util.Slog.w(TAG, "Vibration cancel requested with bad signal=" + endInfo + ", using CANCELLED_UNKNOWN_REASON to ensure cancellation.");
            endInfo = new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.CANCELLED_BY_UNKNOWN_REASON);
        }
        synchronized (this.mLock) {
            if (z) {
                try {
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (this.mSignalCancel == null) {
                this.mSignalCancelImmediate = z | this.mSignalCancelImmediate;
                if (this.mSignalCancel == null) {
                    this.mSignalCancel = endInfo;
                }
                if (this.mRequestVibrationParamsFuture != null) {
                    this.mRequestVibrationParamsFuture.cancel(true);
                }
                this.mLock.notify();
            }
        }
    }

    public void notifyVibratorComplete(int i) {
        synchronized (this.mLock) {
            this.mSignalVibratorsComplete.add(i);
            this.mLock.notify();
        }
    }

    public void notifySyncedVibrationComplete() {
        synchronized (this.mLock) {
            try {
                for (int i : this.mDeviceAdapter.getAvailableVibratorIds()) {
                    this.mSignalVibratorsComplete.add(i);
                }
                this.mLock.notify();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean wasNotifiedToCancel() {
        boolean z;
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(false);
        }
        synchronized (this.mLock) {
            z = this.mSignalCancel != null;
        }
        return z;
    }

    private void waitForVibrationParamsIfRequired() {
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        if (this.mRequestVibrationParamsFuture == null) {
            return;
        }
        try {
            this.mRequestVibrationParamsFuture.orTimeout(this.vibrationSettings.getRequestVibrationParamsTimeoutMs(), java.util.concurrent.TimeUnit.MILLISECONDS).get();
        } catch (java.lang.Throwable th) {
            android.util.Slog.w(TAG, "Failed to retrieve vibration params.", th);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean hasPendingNotifySignalLocked() {
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        if (this.mSignalCancel == null || this.mCancelledVibrationEndInfo != null) {
            return (this.mSignalCancelImmediate && !this.mCancelledImmediately) || this.mSignalVibratorsComplete.size() > 0;
        }
        return true;
    }

    private void processAllNotifySignals() {
        int[] iArr;
        com.android.server.vibrator.Vibration.EndInfo endInfo;
        boolean z = true;
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        synchronized (this.mLock) {
            try {
                iArr = null;
                if (!this.mSignalCancelImmediate) {
                    z = false;
                    endInfo = null;
                } else {
                    if (this.mCancelledImmediately) {
                        android.util.Slog.wtf(TAG, "Immediate cancellation signal processed twice");
                    }
                    endInfo = this.mSignalCancel;
                }
                if (this.mSignalCancel != null && this.mCancelledVibrationEndInfo == null) {
                    endInfo = this.mSignalCancel;
                }
                if (!z && this.mSignalVibratorsComplete.size() > 0) {
                    iArr = this.mSignalVibratorsComplete.toArray();
                    this.mSignalVibratorsComplete.clear();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            processCancelImmediately(endInfo);
            return;
        }
        if (endInfo != null) {
            processCancel(endInfo);
        }
        if (iArr != null) {
            processVibratorsComplete(iArr);
        }
    }

    public void processCancel(com.android.server.vibrator.Vibration.EndInfo endInfo) {
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        this.mCancelledVibrationEndInfo = endInfo;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (true) {
            com.android.server.vibrator.Step pollNext = pollNext();
            if (pollNext != null) {
                arrayList.addAll(pollNext.cancel());
            } else {
                this.mPendingVibrateSteps = 0;
                this.mNextSteps.addAll(arrayList);
                return;
            }
        }
    }

    public void processCancelImmediately(com.android.server.vibrator.Vibration.EndInfo endInfo) {
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        this.mCancelledImmediately = true;
        this.mCancelledVibrationEndInfo = endInfo;
        while (true) {
            com.android.server.vibrator.Step pollNext = pollNext();
            if (pollNext != null) {
                pollNext.cancelImmediately();
            } else {
                this.mPendingVibrateSteps = 0;
                return;
            }
        }
    }

    private void processVibratorsComplete(@android.annotation.NonNull int[] iArr) {
        if (android.os.Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        for (int i : iArr) {
            java.util.Iterator<com.android.server.vibrator.Step> it = this.mNextSteps.iterator();
            while (true) {
                if (it.hasNext()) {
                    com.android.server.vibrator.Step next = it.next();
                    if (next.acceptVibratorCompleteCallback(i)) {
                        it.remove();
                        this.mPendingOnVibratorCompleteSteps.offer(next);
                        break;
                    }
                }
            }
        }
    }

    private static android.os.CombinedVibration.Sequential toSequential(android.os.CombinedVibration combinedVibration) {
        if (combinedVibration instanceof android.os.CombinedVibration.Sequential) {
            return (android.os.CombinedVibration.Sequential) combinedVibration;
        }
        return android.os.CombinedVibration.startSequential().addNext(combinedVibration).combine();
    }

    private static void expectIsVibrationThread(boolean z) {
        if ((java.lang.Thread.currentThread() instanceof com.android.server.vibrator.VibrationThread) != z) {
            android.util.Slog.wtfStack("VibrationStepConductor", "Thread caller assertion failed, expected isVibrationThread=" + z);
        }
    }
}
