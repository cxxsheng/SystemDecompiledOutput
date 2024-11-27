package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class FinishSequentialEffectStep extends com.android.server.vibrator.Step {
    public final com.android.server.vibrator.StartSequentialEffectStep startedStep;

    FinishSequentialEffectStep(com.android.server.vibrator.StartSequentialEffectStep startSequentialEffectStep) {
        super(startSequentialEffectStep.conductor, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME);
        this.startedStep = startSequentialEffectStep;
    }

    @Override // com.android.server.vibrator.Step
    public boolean isCleanUp() {
        return true;
    }

    @Override // com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> play() {
        android.os.Trace.traceBegin(8388608L, "FinishSequentialEffectStep");
        try {
            this.conductor.vibratorManagerHooks.noteVibratorOff(this.conductor.getVibration().callerInfo.uid);
            com.android.server.vibrator.Step nextStep = this.startedStep.nextStep();
            java.util.List<com.android.server.vibrator.Step> asList = nextStep == null ? com.android.server.vibrator.VibrationStepConductor.EMPTY_STEP_LIST : java.util.Arrays.asList(nextStep);
            android.os.Trace.traceEnd(8388608L);
            return asList;
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(8388608L);
            throw th;
        }
    }

    @Override // com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> cancel() {
        cancelImmediately();
        return com.android.server.vibrator.VibrationStepConductor.EMPTY_STEP_LIST;
    }

    @Override // com.android.server.vibrator.Step
    public void cancelImmediately() {
        this.conductor.vibratorManagerHooks.noteVibratorOff(this.conductor.getVibration().callerInfo.uid);
    }
}
