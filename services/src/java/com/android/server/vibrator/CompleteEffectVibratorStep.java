package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class CompleteEffectVibratorStep extends com.android.server.vibrator.AbstractVibratorStep {
    private final boolean mCancelled;

    CompleteEffectVibratorStep(com.android.server.vibrator.VibrationStepConductor vibrationStepConductor, long j, boolean z, com.android.server.vibrator.VibratorController vibratorController, long j2) {
        super(vibrationStepConductor, j, vibratorController, null, -1, j2);
        this.mCancelled = z;
    }

    @Override // com.android.server.vibrator.Step
    public boolean isCleanUp() {
        return this.mCancelled;
    }

    @Override // com.android.server.vibrator.AbstractVibratorStep, com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> cancel() {
        if (this.mCancelled) {
            return java.util.Arrays.asList(new com.android.server.vibrator.TurnOffVibratorStep(this.conductor, android.os.SystemClock.uptimeMillis(), this.controller));
        }
        return super.cancel();
    }

    @Override // com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> play() {
        android.os.Trace.traceBegin(8388608L, "CompleteEffectVibratorStep");
        try {
            if (this.mVibratorCompleteCallbackReceived) {
                stopVibrating();
                return com.android.server.vibrator.VibrationStepConductor.EMPTY_STEP_LIST;
            }
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            float currentAmplitude = this.controller.getCurrentAmplitude();
            long min = java.lang.Math.min((this.mPendingVibratorOffDeadline - uptimeMillis) - 1000, this.conductor.vibrationSettings.getRampDownDuration());
            long rampStepDuration = this.conductor.vibrationSettings.getRampStepDuration();
            if (currentAmplitude >= 0.001f && min > rampStepDuration) {
                float f = currentAmplitude / (min / rampStepDuration);
                return java.util.Arrays.asList(new com.android.server.vibrator.RampOffVibratorStep(this.conductor, this.startTime, currentAmplitude - f, f, this.controller, this.mCancelled ? uptimeMillis + min : this.mPendingVibratorOffDeadline));
            }
            if (!this.mCancelled) {
                return java.util.Arrays.asList(new com.android.server.vibrator.TurnOffVibratorStep(this.conductor, this.mPendingVibratorOffDeadline, this.controller));
            }
            stopVibrating();
            return com.android.server.vibrator.VibrationStepConductor.EMPTY_STEP_LIST;
        } finally {
            android.os.Trace.traceEnd(8388608L);
        }
    }
}
