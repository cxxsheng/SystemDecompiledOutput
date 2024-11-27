package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class StepToRampAdapter implements com.android.server.vibrator.VibrationSegmentsAdapter {
    StepToRampAdapter() {
    }

    @Override // com.android.server.vibrator.VibrationSegmentsAdapter
    public int adaptToVibrator(android.os.VibratorInfo vibratorInfo, java.util.List<android.os.vibrator.VibrationEffectSegment> list, int i) {
        if (!vibratorInfo.hasCapability(1024L)) {
            return i;
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.os.vibrator.StepSegment stepSegment = (android.os.vibrator.VibrationEffectSegment) list.get(i2);
            if (isStep(stepSegment)) {
                android.os.vibrator.StepSegment stepSegment2 = stepSegment;
                if (stepSegment2.getFrequencyHz() != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    list.set(i2, convertStepToRamp(vibratorInfo, stepSegment2));
                }
            }
        }
        for (int i3 = 0; i3 < size; i3++) {
            if (list.get(i3) instanceof android.os.vibrator.RampSegment) {
                for (int i4 = i3 - 1; i4 >= 0 && isStep(list.get(i4)); i4--) {
                    list.set(i4, convertStepToRamp(vibratorInfo, list.get(i4)));
                }
                for (int i5 = i3 + 1; i5 < size && isStep(list.get(i5)); i5++) {
                    list.set(i5, convertStepToRamp(vibratorInfo, list.get(i5)));
                }
            }
        }
        return i;
    }

    private static android.os.vibrator.RampSegment convertStepToRamp(android.os.VibratorInfo vibratorInfo, android.os.vibrator.StepSegment stepSegment) {
        float fillEmptyFrequency = fillEmptyFrequency(vibratorInfo, stepSegment.getFrequencyHz());
        return new android.os.vibrator.RampSegment(stepSegment.getAmplitude(), stepSegment.getAmplitude(), fillEmptyFrequency, fillEmptyFrequency, (int) stepSegment.getDuration());
    }

    private static boolean isStep(android.os.vibrator.VibrationEffectSegment vibrationEffectSegment) {
        return vibrationEffectSegment instanceof android.os.vibrator.StepSegment;
    }

    private static float fillEmptyFrequency(android.os.VibratorInfo vibratorInfo, float f) {
        if (java.lang.Float.isNaN(vibratorInfo.getResonantFrequencyHz())) {
            return f;
        }
        return f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? vibratorInfo.getResonantFrequencyHz() : f;
    }
}
