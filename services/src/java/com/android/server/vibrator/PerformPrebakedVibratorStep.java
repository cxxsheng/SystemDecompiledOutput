package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class PerformPrebakedVibratorStep extends com.android.server.vibrator.AbstractVibratorStep {
    PerformPrebakedVibratorStep(com.android.server.vibrator.VibrationStepConductor vibrationStepConductor, long j, com.android.server.vibrator.VibratorController vibratorController, android.os.VibrationEffect.Composed composed, int i, long j2) {
        super(vibrationStepConductor, java.lang.Math.max(j, j2), vibratorController, composed, i, j2);
    }

    @Override // com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> play() {
        android.os.Trace.traceBegin(8388608L, "PerformPrebakedVibratorStep");
        try {
            android.os.vibrator.PrebakedSegment prebakedSegment = (android.os.vibrator.VibrationEffectSegment) this.effect.getSegments().get(this.segmentIndex);
            if (!(prebakedSegment instanceof android.os.vibrator.PrebakedSegment)) {
                android.util.Slog.w("VibrationThread", "Ignoring wrong segment for a PerformPrebakedVibratorStep: " + prebakedSegment);
                return nextSteps(1);
            }
            android.os.vibrator.PrebakedSegment prebakedSegment2 = prebakedSegment;
            android.os.VibrationEffect fallback = getVibration().getFallback(prebakedSegment2.getEffectId());
            long on = this.controller.on(prebakedSegment2, getVibration().id);
            handleVibratorOnResult(on);
            getVibration().stats.reportPerformEffect(on, prebakedSegment2);
            if (on != 0 || !prebakedSegment2.shouldFallback() || !(fallback instanceof android.os.VibrationEffect.Composed)) {
                return nextSteps(1);
            }
            com.android.server.vibrator.AbstractVibratorStep nextVibrateStep = this.conductor.nextVibrateStep(this.startTime, this.controller, replaceCurrentSegment((android.os.VibrationEffect.Composed) fallback), this.segmentIndex, this.mPendingVibratorOffDeadline);
            java.util.List<com.android.server.vibrator.Step> play = nextVibrateStep.play();
            handleVibratorOnResult(nextVibrateStep.getVibratorOnDuration());
            return play;
        } finally {
            android.os.Trace.traceEnd(8388608L);
        }
    }

    private android.os.VibrationEffect.Composed replaceCurrentSegment(android.os.VibrationEffect.Composed composed) {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.effect.getSegments());
        int repeatIndex = this.effect.getRepeatIndex();
        arrayList.remove(this.segmentIndex);
        arrayList.addAll(this.segmentIndex, composed.getSegments());
        if (this.segmentIndex < this.effect.getRepeatIndex()) {
            repeatIndex += composed.getSegments().size() - 1;
        }
        return new android.os.VibrationEffect.Composed(arrayList, repeatIndex);
    }
}
