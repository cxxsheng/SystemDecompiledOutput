package com.android.server.vibrator;

/* loaded from: classes2.dex */
abstract class Step implements java.lang.Comparable<com.android.server.vibrator.Step> {
    public final com.android.server.vibrator.VibrationStepConductor conductor;
    public final long startTime;

    @android.annotation.NonNull
    public abstract java.util.List<com.android.server.vibrator.Step> cancel();

    public abstract void cancelImmediately();

    @android.annotation.NonNull
    public abstract java.util.List<com.android.server.vibrator.Step> play();

    Step(com.android.server.vibrator.VibrationStepConductor vibrationStepConductor, long j) {
        this.conductor = vibrationStepConductor;
        this.startTime = j;
    }

    protected com.android.server.vibrator.HalVibration getVibration() {
        return this.conductor.getVibration();
    }

    public boolean isCleanUp() {
        return false;
    }

    public long getVibratorOnDuration() {
        return 0L;
    }

    public boolean acceptVibratorCompleteCallback(int i) {
        return false;
    }

    public long calculateWaitTime() {
        if (this.startTime == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            return 0L;
        }
        return java.lang.Math.max(0L, this.startTime - android.os.SystemClock.uptimeMillis());
    }

    @Override // java.lang.Comparable
    public int compareTo(com.android.server.vibrator.Step step) {
        return java.lang.Long.compare(this.startTime, step.startTime);
    }
}
