package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class SetAmplitudeVibratorStep extends com.android.server.vibrator.AbstractVibratorStep {
    static final int REPEATING_EFFECT_ON_DURATION = 5000;

    SetAmplitudeVibratorStep(com.android.server.vibrator.VibrationStepConductor vibrationStepConductor, long j, com.android.server.vibrator.VibratorController vibratorController, android.os.VibrationEffect.Composed composed, int i, long j2) {
        super(vibrationStepConductor, j, vibratorController, composed, i, j2);
    }

    @Override // com.android.server.vibrator.AbstractVibratorStep, com.android.server.vibrator.Step
    public boolean acceptVibratorCompleteCallback(int i) {
        return super.acceptVibratorCompleteCallback(i) && android.os.SystemClock.uptimeMillis() < this.startTime && this.controller.getCurrentAmplitude() > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    }

    @Override // com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> play() {
        android.os.Trace.traceBegin(8388608L, "SetAmplitudeVibratorStep");
        try {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            long j = uptimeMillis - this.startTime;
            if (this.mVibratorCompleteCallbackReceived && j < 0) {
                turnVibratorBackOn(-j);
                return java.util.Arrays.asList(new com.android.server.vibrator.SetAmplitudeVibratorStep(this.conductor, this.startTime, this.controller, this.effect, this.segmentIndex, this.mPendingVibratorOffDeadline));
            }
            android.os.vibrator.StepSegment stepSegment = (android.os.vibrator.VibrationEffectSegment) this.effect.getSegments().get(this.segmentIndex);
            if (!(stepSegment instanceof android.os.vibrator.StepSegment)) {
                android.util.Slog.w("VibrationThread", "Ignoring wrong segment for a SetAmplitudeVibratorStep: " + stepSegment);
                return nextSteps(this.startTime, 1);
            }
            android.os.vibrator.StepSegment stepSegment2 = stepSegment;
            if (stepSegment2.getDuration() == 0) {
                return nextSteps(this.startTime, 1);
            }
            float amplitude = stepSegment2.getAmplitude();
            if (amplitude != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                if (this.startTime >= this.mPendingVibratorOffDeadline) {
                    long vibratorOnDuration = getVibratorOnDuration(this.effect, this.segmentIndex);
                    if (vibratorOnDuration > 0) {
                        startVibrating(vibratorOnDuration);
                    }
                }
                changeAmplitude(amplitude);
            } else if (this.mPendingVibratorOffDeadline > uptimeMillis) {
                stopVibrating();
            }
            return nextSteps(this.startTime + stepSegment.getDuration(), 1);
        } finally {
            android.os.Trace.traceEnd(8388608L);
        }
    }

    private void turnVibratorBackOn(long j) {
        long vibratorOnDuration = getVibratorOnDuration(this.effect, this.segmentIndex);
        if (vibratorOnDuration <= 0) {
            return;
        }
        long j2 = vibratorOnDuration + j;
        float currentAmplitude = this.controller.getCurrentAmplitude();
        if (startVibrating(j2) > 0) {
            changeAmplitude(currentAmplitude);
        }
    }

    private long startVibrating(long j) {
        long on = this.controller.on(j, getVibration().id);
        handleVibratorOnResult(on);
        getVibration().stats.reportVibratorOn(on);
        return on;
    }

    private long getVibratorOnDuration(android.os.VibrationEffect.Composed composed, int i) {
        java.util.List segments = composed.getSegments();
        int size = segments.size();
        int repeatIndex = composed.getRepeatIndex();
        int i2 = i;
        long j = 0;
        while (i2 < size) {
            android.os.vibrator.StepSegment stepSegment = (android.os.vibrator.VibrationEffectSegment) segments.get(i2);
            if (!(stepSegment instanceof android.os.vibrator.StepSegment) || (stepSegment.getDuration() > 0 && stepSegment.getAmplitude() == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE)) {
                break;
            }
            j += stepSegment.getDuration();
            i2++;
            if (i2 == size && repeatIndex >= 0) {
                i2 = repeatIndex;
                repeatIndex = -1;
            }
            if (i2 == i) {
                return java.lang.Math.max(j, 5000L);
            }
        }
        if (i2 == size && composed.getRepeatIndex() < 0) {
            return j + this.conductor.vibrationSettings.getRampDownDuration();
        }
        return j;
    }
}
