package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class TurnOffVibratorStep extends com.android.server.vibrator.AbstractVibratorStep {
    TurnOffVibratorStep(com.android.server.vibrator.VibrationStepConductor vibrationStepConductor, long j, com.android.server.vibrator.VibratorController vibratorController) {
        super(vibrationStepConductor, j, vibratorController, null, -1, j);
    }

    @Override // com.android.server.vibrator.Step
    public boolean isCleanUp() {
        return true;
    }

    @Override // com.android.server.vibrator.AbstractVibratorStep, com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> cancel() {
        return java.util.Arrays.asList(new com.android.server.vibrator.TurnOffVibratorStep(this.conductor, android.os.SystemClock.uptimeMillis(), this.controller));
    }

    @Override // com.android.server.vibrator.AbstractVibratorStep, com.android.server.vibrator.Step
    public void cancelImmediately() {
        stopVibrating();
    }

    @Override // com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> play() {
        android.os.Trace.traceBegin(8388608L, "TurnOffVibratorStep");
        try {
            stopVibrating();
            return com.android.server.vibrator.VibrationStepConductor.EMPTY_STEP_LIST;
        } finally {
            android.os.Trace.traceEnd(8388608L);
        }
    }
}
