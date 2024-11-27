package com.android.server.vibrator;

/* loaded from: classes2.dex */
abstract class AbstractVibratorStep extends com.android.server.vibrator.Step {
    public final com.android.server.vibrator.VibratorController controller;
    public final android.os.VibrationEffect.Composed effect;
    long mPendingVibratorOffDeadline;
    boolean mVibratorCompleteCallbackReceived;
    long mVibratorOnResult;
    public final int segmentIndex;

    AbstractVibratorStep(com.android.server.vibrator.VibrationStepConductor vibrationStepConductor, long j, com.android.server.vibrator.VibratorController vibratorController, android.os.VibrationEffect.Composed composed, int i, long j2) {
        super(vibrationStepConductor, j);
        this.controller = vibratorController;
        this.effect = composed;
        this.segmentIndex = i;
        this.mPendingVibratorOffDeadline = j2;
    }

    public int getVibratorId() {
        return this.controller.getVibratorInfo().getId();
    }

    @Override // com.android.server.vibrator.Step
    public long getVibratorOnDuration() {
        return this.mVibratorOnResult;
    }

    @Override // com.android.server.vibrator.Step
    public boolean acceptVibratorCompleteCallback(int i) {
        if (getVibratorId() != i) {
            return false;
        }
        boolean z = this.mPendingVibratorOffDeadline > android.os.SystemClock.uptimeMillis();
        this.mPendingVibratorOffDeadline = 0L;
        this.mVibratorCompleteCallbackReceived = true;
        return z;
    }

    @Override // com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> cancel() {
        return java.util.Arrays.asList(new com.android.server.vibrator.CompleteEffectVibratorStep(this.conductor, android.os.SystemClock.uptimeMillis(), true, this.controller, this.mPendingVibratorOffDeadline));
    }

    @Override // com.android.server.vibrator.Step
    public void cancelImmediately() {
        if (this.mPendingVibratorOffDeadline > android.os.SystemClock.uptimeMillis()) {
            stopVibrating();
        }
    }

    protected long handleVibratorOnResult(long j) {
        this.mVibratorOnResult = j;
        if (this.mVibratorOnResult > 0) {
            this.mPendingVibratorOffDeadline = android.os.SystemClock.uptimeMillis() + this.mVibratorOnResult + 1000;
        } else {
            this.mPendingVibratorOffDeadline = 0L;
        }
        return this.mVibratorOnResult;
    }

    protected void stopVibrating() {
        this.controller.off();
        getVibration().stats.reportVibratorOff();
        this.mPendingVibratorOffDeadline = 0L;
    }

    protected void changeAmplitude(float f) {
        this.controller.setAmplitude(f);
        getVibration().stats.reportSetAmplitude();
    }

    protected java.util.List<com.android.server.vibrator.Step> nextSteps(int i) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (this.mVibratorOnResult > 0) {
            uptimeMillis += this.mVibratorOnResult;
        }
        return nextSteps(uptimeMillis, i);
    }

    protected java.util.List<com.android.server.vibrator.Step> nextSteps(long j, int i) {
        int i2;
        int i3 = this.segmentIndex + i;
        int size = this.effect.getSegments().size();
        int repeatIndex = this.effect.getRepeatIndex();
        if (i3 >= size && repeatIndex >= 0) {
            int i4 = size - repeatIndex;
            getVibration().stats.reportRepetition((i3 - repeatIndex) / i4);
            i2 = ((i3 - size) % i4) + repeatIndex;
        } else {
            i2 = i3;
        }
        com.android.server.vibrator.AbstractVibratorStep nextVibrateStep = this.conductor.nextVibrateStep(j, this.controller, this.effect, i2, this.mPendingVibratorOffDeadline);
        return nextVibrateStep == null ? com.android.server.vibrator.VibrationStepConductor.EMPTY_STEP_LIST : java.util.Arrays.asList(nextVibrateStep);
    }
}
