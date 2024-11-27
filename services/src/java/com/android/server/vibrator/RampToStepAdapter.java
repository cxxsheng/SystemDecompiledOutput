package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class RampToStepAdapter implements com.android.server.vibrator.VibrationSegmentsAdapter {
    private final int mStepDuration;

    RampToStepAdapter(int i) {
        this.mStepDuration = i;
    }

    @Override // com.android.server.vibrator.VibrationSegmentsAdapter
    public int adaptToVibrator(android.os.VibratorInfo vibratorInfo, java.util.List<android.os.vibrator.VibrationEffectSegment> list, int i) {
        if (vibratorInfo.hasCapability(1024L)) {
            return i;
        }
        int size = list.size();
        int i2 = 0;
        while (i2 < size) {
            android.os.vibrator.VibrationEffectSegment vibrationEffectSegment = list.get(i2);
            if (vibrationEffectSegment instanceof android.os.vibrator.RampSegment) {
                java.util.List<android.os.vibrator.StepSegment> convertRampToSteps = convertRampToSteps(vibratorInfo, (android.os.vibrator.RampSegment) vibrationEffectSegment);
                list.remove(i2);
                list.addAll(i2, convertRampToSteps);
                int size2 = convertRampToSteps.size() - 1;
                if (i > i2) {
                    i += size2;
                }
                i2 += size2;
                size += size2;
            }
            i2++;
        }
        return i;
    }

    private java.util.List<android.os.vibrator.StepSegment> convertRampToSteps(android.os.VibratorInfo vibratorInfo, android.os.vibrator.RampSegment rampSegment) {
        int i = 0;
        if (java.lang.Float.compare(rampSegment.getStartAmplitude(), rampSegment.getEndAmplitude()) == 0) {
            return java.util.Arrays.asList(new android.os.vibrator.StepSegment(rampSegment.getStartAmplitude(), fillEmptyFrequency(vibratorInfo, rampSegment.getStartFrequencyHz()), (int) rampSegment.getDuration()));
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int duration = ((int) ((rampSegment.getDuration() + this.mStepDuration) - 1)) / this.mStepDuration;
        while (true) {
            int i2 = duration - 1;
            if (i < i2) {
                float f = i / duration;
                arrayList.add(new android.os.vibrator.StepSegment(android.util.MathUtils.lerp(rampSegment.getStartAmplitude(), rampSegment.getEndAmplitude(), f), android.util.MathUtils.lerp(fillEmptyFrequency(vibratorInfo, rampSegment.getStartFrequencyHz()), fillEmptyFrequency(vibratorInfo, rampSegment.getEndFrequencyHz()), f), this.mStepDuration));
                i++;
            } else {
                arrayList.add(new android.os.vibrator.StepSegment(rampSegment.getEndAmplitude(), fillEmptyFrequency(vibratorInfo, rampSegment.getEndFrequencyHz()), ((int) rampSegment.getDuration()) - (this.mStepDuration * i2)));
                return arrayList;
            }
        }
    }

    private static float fillEmptyFrequency(android.os.VibratorInfo vibratorInfo, float f) {
        return java.lang.Float.isNaN(vibratorInfo.getResonantFrequencyHz()) ? com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE : f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? vibratorInfo.getResonantFrequencyHz() : f;
    }
}
