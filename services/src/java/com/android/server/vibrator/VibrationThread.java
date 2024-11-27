package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class VibrationThread extends java.lang.Thread {
    static final boolean DEBUG = false;
    static final java.lang.String TAG = "VibrationThread";

    @android.annotation.Nullable
    private com.android.server.vibrator.VibrationStepConductor mExecutingConductor;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.vibrator.VibrationStepConductor mRequestedActiveConductor;
    private final com.android.server.vibrator.VibrationThread.VibratorManagerHooks mVibratorManagerHooks;
    private final android.os.PowerManager.WakeLock mWakeLock;
    private final java.lang.Object mLock = new java.lang.Object();
    private boolean mCalledVibrationCompleteCallback = false;

    interface VibratorManagerHooks {
        void cancelSyncedVibration();

        void noteVibratorOff(int i);

        void noteVibratorOn(int i, long j);

        void onVibrationCompleted(long j, @android.annotation.NonNull com.android.server.vibrator.Vibration.EndInfo endInfo);

        void onVibrationThreadReleased(long j);

        boolean prepareSyncedVibration(long j, int[] iArr);

        boolean triggerSyncedVibration(long j);
    }

    VibrationThread(android.os.PowerManager.WakeLock wakeLock, com.android.server.vibrator.VibrationThread.VibratorManagerHooks vibratorManagerHooks) {
        this.mWakeLock = wakeLock;
        this.mVibratorManagerHooks = vibratorManagerHooks;
    }

    boolean runVibrationOnVibrationThread(com.android.server.vibrator.VibrationStepConductor vibrationStepConductor) {
        synchronized (this.mLock) {
            try {
                if (this.mRequestedActiveConductor != null) {
                    android.util.Slog.wtf(TAG, "Attempt to start vibration when one already running");
                    return false;
                }
                this.mRequestedActiveConductor = vibrationStepConductor;
                this.mLock.notifyAll();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        android.os.Process.setThreadPriority(-8);
        while (true) {
            com.android.server.vibrator.VibrationStepConductor waitForVibrationRequest = waitForVibrationRequest();
            java.util.Objects.requireNonNull(waitForVibrationRequest);
            this.mExecutingConductor = waitForVibrationRequest;
            this.mCalledVibrationCompleteCallback = false;
            runCurrentVibrationWithWakeLock();
            if (!this.mExecutingConductor.isFinished()) {
                android.util.Slog.wtf(TAG, "VibrationThread terminated with unfinished vibration");
            }
            synchronized (this.mLock) {
                this.mRequestedActiveConductor = null;
            }
            this.mVibratorManagerHooks.onVibrationThreadReleased(this.mExecutingConductor.getVibration().id);
            synchronized (this.mLock) {
                this.mLock.notifyAll();
            }
            this.mExecutingConductor = null;
        }
    }

    public boolean waitForThreadIdle(long j) {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long j2 = j + elapsedRealtime;
        synchronized (this.mLock) {
            while (this.mRequestedActiveConductor != null) {
                try {
                    if (elapsedRealtime >= j2) {
                        return false;
                    }
                    try {
                        this.mLock.wait(j2 - elapsedRealtime);
                    } catch (java.lang.InterruptedException e) {
                        android.util.Slog.w(TAG, "VibrationThread interrupted waiting to stop, continuing");
                    }
                    elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return true;
        }
    }

    @android.annotation.NonNull
    private com.android.server.vibrator.VibrationStepConductor waitForVibrationRequest() {
        while (true) {
            synchronized (this.mLock) {
                if (this.mRequestedActiveConductor != null) {
                    return this.mRequestedActiveConductor;
                }
                try {
                    this.mLock.wait();
                } catch (java.lang.InterruptedException e) {
                    android.util.Slog.w(TAG, "VibrationThread interrupted waiting to start, continuing");
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isRunningVibrationId(long j) {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = this.mRequestedActiveConductor != null && this.mRequestedActiveConductor.getVibration().id == j;
            } finally {
            }
        }
        return z;
    }

    private void runCurrentVibrationWithWakeLock() {
        this.mWakeLock.setWorkSource(new android.os.WorkSource(this.mExecutingConductor.getVibration().callerInfo.uid));
        this.mWakeLock.acquire();
        try {
            try {
                runCurrentVibrationWithWakeLockAndDeathLink();
            } finally {
                clientVibrationCompleteIfNotAlready(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.FINISHED_UNEXPECTED));
            }
        } finally {
            this.mWakeLock.release();
            this.mWakeLock.setWorkSource(null);
        }
    }

    private void runCurrentVibrationWithWakeLockAndDeathLink() {
        android.os.IBinder iBinder = this.mExecutingConductor.getVibration().callerToken;
        try {
            iBinder.linkToDeath(this.mExecutingConductor, 0);
            try {
                playVibration();
                try {
                    iBinder.unlinkToDeath(this.mExecutingConductor, 0);
                } catch (java.util.NoSuchElementException e) {
                    android.util.Slog.wtf(TAG, "Failed to unlink token", e);
                }
            } catch (java.lang.Throwable th) {
                try {
                    iBinder.unlinkToDeath(this.mExecutingConductor, 0);
                } catch (java.util.NoSuchElementException e2) {
                    android.util.Slog.wtf(TAG, "Failed to unlink token", e2);
                }
                throw th;
            }
        } catch (android.os.RemoteException e3) {
            android.util.Slog.e(TAG, "Error linking vibration to token death", e3);
            clientVibrationCompleteIfNotAlready(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.IGNORED_ERROR_TOKEN));
        }
    }

    private void clientVibrationCompleteIfNotAlready(@android.annotation.NonNull com.android.server.vibrator.Vibration.EndInfo endInfo) {
        if (!this.mCalledVibrationCompleteCallback) {
            this.mCalledVibrationCompleteCallback = true;
            this.mVibratorManagerHooks.onVibrationCompleted(this.mExecutingConductor.getVibration().id, endInfo);
        }
    }

    private void playVibration() {
        com.android.server.vibrator.Vibration.EndInfo calculateVibrationEndInfo;
        android.os.Trace.traceBegin(8388608L, "playVibration");
        try {
            this.mExecutingConductor.prepareToStart();
            while (!this.mExecutingConductor.isFinished()) {
                if (this.mExecutingConductor.waitUntilNextStepIsDue()) {
                    this.mExecutingConductor.runNextStep();
                }
                if (!this.mCalledVibrationCompleteCallback && (calculateVibrationEndInfo = this.mExecutingConductor.calculateVibrationEndInfo()) != null) {
                    clientVibrationCompleteIfNotAlready(calculateVibrationEndInfo);
                }
            }
            android.os.Trace.traceEnd(8388608L);
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(8388608L);
            throw th;
        }
    }
}
