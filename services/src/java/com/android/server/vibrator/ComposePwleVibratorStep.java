package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class ComposePwleVibratorStep extends com.android.server.vibrator.AbstractVibratorStep {
    private static final int DEFAULT_PWLE_SIZE_LIMIT = 100;

    ComposePwleVibratorStep(com.android.server.vibrator.VibrationStepConductor vibrationStepConductor, long j, com.android.server.vibrator.VibratorController vibratorController, android.os.VibrationEffect.Composed composed, int i, long j2) {
        super(vibrationStepConductor, java.lang.Math.max(j, j2), vibratorController, composed, i, j2);
    }

    @Override // com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> play() {
        android.os.Trace.traceBegin(8388608L, "ComposePwleStep");
        try {
            int pwleSizeMax = this.controller.getVibratorInfo().getPwleSizeMax();
            android.os.VibrationEffect.Composed composed = this.effect;
            int i = this.segmentIndex;
            if (pwleSizeMax <= 0) {
                pwleSizeMax = 100;
            }
            java.util.List<android.os.vibrator.RampSegment> unrollRampSegments = unrollRampSegments(composed, i, pwleSizeMax);
            if (unrollRampSegments.isEmpty()) {
                android.util.Slog.w("VibrationThread", "Ignoring wrong segment for a ComposePwleStep: " + this.effect.getSegments().get(this.segmentIndex));
                return nextSteps(1);
            }
            android.os.vibrator.RampSegment[] rampSegmentArr = (android.os.vibrator.RampSegment[]) unrollRampSegments.toArray(new android.os.vibrator.RampSegment[unrollRampSegments.size()]);
            long on = this.controller.on(rampSegmentArr, getVibration().id);
            handleVibratorOnResult(on);
            getVibration().stats.reportComposePwle(on, rampSegmentArr);
            return nextSteps(unrollRampSegments.size());
        } finally {
            android.os.Trace.traceEnd(8388608L);
        }
    }

    private java.util.List<android.os.vibrator.RampSegment> unrollRampSegments(android.os.VibrationEffect.Composed composed, int i, int i2) {
        java.util.ArrayList arrayList = new java.util.ArrayList(i2);
        int size = composed.getSegments().size();
        int repeatIndex = composed.getRepeatIndex();
        float f = 1.0f;
        int i3 = i2;
        while (arrayList.size() <= i2) {
            if (i == size) {
                if (repeatIndex < 0) {
                    break;
                }
                i = repeatIndex;
            }
            android.os.vibrator.VibrationEffectSegment vibrationEffectSegment = (android.os.vibrator.VibrationEffectSegment) composed.getSegments().get(i);
            if (!(vibrationEffectSegment instanceof android.os.vibrator.RampSegment)) {
                break;
            }
            android.os.vibrator.RampSegment rampSegment = (android.os.vibrator.RampSegment) vibrationEffectSegment;
            arrayList.add(rampSegment);
            if (isBetterBreakPosition(arrayList, f, i2)) {
                f = rampSegment.getEndAmplitude();
                i3 = arrayList.size();
            }
            i++;
        }
        if (arrayList.size() <= i2) {
            return arrayList;
        }
        return arrayList.subList(0, i3);
    }

    private boolean isBetterBreakPosition(java.util.List<android.os.vibrator.RampSegment> list, float f, int i) {
        float endAmplitude = list.get(list.size() - 1).getEndAmplitude();
        int size = list.size();
        if (size > i) {
            return false;
        }
        if (endAmplitude == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return true;
        }
        return size >= i / 2 && endAmplitude <= f;
    }
}
