package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class RampOffVibratorStep extends com.android.server.vibrator.AbstractVibratorStep {
    private final float mAmplitudeDelta;
    private final float mAmplitudeTarget;

    RampOffVibratorStep(com.android.server.vibrator.VibrationStepConductor vibrationStepConductor, long j, float f, float f2, com.android.server.vibrator.VibratorController vibratorController, long j2) {
        super(vibrationStepConductor, j, vibratorController, null, -1, j2);
        this.mAmplitudeTarget = f;
        this.mAmplitudeDelta = f2;
    }

    @Override // com.android.server.vibrator.Step
    public boolean isCleanUp() {
        return true;
    }

    @Override // com.android.server.vibrator.AbstractVibratorStep, com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> cancel() {
        return java.util.Arrays.asList(new com.android.server.vibrator.TurnOffVibratorStep(this.conductor, android.os.SystemClock.uptimeMillis(), this.controller));
    }

    @Override // com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> play() {
        android.os.Trace.traceBegin(8388608L, "RampOffVibratorStep");
        try {
            if (this.mVibratorCompleteCallbackReceived) {
                stopVibrating();
                return com.android.server.vibrator.VibrationStepConductor.EMPTY_STEP_LIST;
            }
            changeAmplitude(this.mAmplitudeTarget);
            float f = this.mAmplitudeTarget - this.mAmplitudeDelta;
            return f < 0.001f ? java.util.Arrays.asList(new com.android.server.vibrator.TurnOffVibratorStep(this.conductor, this.mPendingVibratorOffDeadline, this.controller)) : java.util.Arrays.asList(new com.android.server.vibrator.RampOffVibratorStep(this.conductor, this.conductor.vibrationSettings.getRampStepDuration() + this.startTime, f, this.mAmplitudeDelta, this.controller, this.mPendingVibratorOffDeadline));
        } finally {
            android.os.Trace.traceEnd(8388608L);
        }
    }
}
