package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class ClippingAmplitudeAndFrequencyAdapter implements com.android.server.vibrator.VibrationSegmentsAdapter {
    ClippingAmplitudeAndFrequencyAdapter() {
    }

    @Override // com.android.server.vibrator.VibrationSegmentsAdapter
    public int adaptToVibrator(android.os.VibratorInfo vibratorInfo, java.util.List<android.os.vibrator.VibrationEffectSegment> list, int i) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.os.vibrator.VibrationEffectSegment vibrationEffectSegment = list.get(i2);
            if (vibrationEffectSegment instanceof android.os.vibrator.RampSegment) {
                list.set(i2, adaptToVibrator(vibratorInfo, (android.os.vibrator.RampSegment) vibrationEffectSegment));
            }
        }
        return i;
    }

    private android.os.vibrator.RampSegment adaptToVibrator(android.os.VibratorInfo vibratorInfo, android.os.vibrator.RampSegment rampSegment) {
        float clampFrequency = clampFrequency(vibratorInfo, rampSegment.getStartFrequencyHz());
        float clampFrequency2 = clampFrequency(vibratorInfo, rampSegment.getEndFrequencyHz());
        return new android.os.vibrator.RampSegment(clampAmplitude(vibratorInfo, clampFrequency, rampSegment.getStartAmplitude()), clampAmplitude(vibratorInfo, clampFrequency2, rampSegment.getEndAmplitude()), clampFrequency, clampFrequency2, (int) rampSegment.getDuration());
    }

    private float clampFrequency(android.os.VibratorInfo vibratorInfo, float f) {
        android.util.Range frequencyRangeHz = vibratorInfo.getFrequencyProfile().getFrequencyRangeHz();
        if (f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || frequencyRangeHz == null) {
            return java.lang.Float.isNaN(vibratorInfo.getResonantFrequencyHz()) ? com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE : vibratorInfo.getResonantFrequencyHz();
        }
        return ((java.lang.Float) frequencyRangeHz.clamp(java.lang.Float.valueOf(f))).floatValue();
    }

    private float clampAmplitude(android.os.VibratorInfo vibratorInfo, float f, float f2) {
        android.os.VibratorInfo.FrequencyProfile frequencyProfile = vibratorInfo.getFrequencyProfile();
        if (frequencyProfile.isEmpty()) {
            return f2;
        }
        return android.util.MathUtils.min(f2, frequencyProfile.getMaxAmplitude(f));
    }
}
