package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class SplitSegmentsAdapter implements com.android.server.vibrator.VibrationSegmentsAdapter {
    SplitSegmentsAdapter() {
    }

    @Override // com.android.server.vibrator.VibrationSegmentsAdapter
    public int adaptToVibrator(android.os.VibratorInfo vibratorInfo, java.util.List<android.os.vibrator.VibrationEffectSegment> list, int i) {
        if (!vibratorInfo.hasCapability(1024L)) {
            return i;
        }
        int pwlePrimitiveDurationMax = vibratorInfo.getPwlePrimitiveDurationMax();
        if (pwlePrimitiveDurationMax <= 0) {
            return i;
        }
        int size = list.size();
        int i2 = 0;
        while (i2 < size) {
            if (list.get(i2) instanceof android.os.vibrator.RampSegment) {
                android.os.vibrator.RampSegment rampSegment = list.get(i2);
                int duration = ((((int) rampSegment.getDuration()) + pwlePrimitiveDurationMax) - 1) / pwlePrimitiveDurationMax;
                if (duration > 1) {
                    list.remove(i2);
                    list.addAll(i2, splitRampSegment(vibratorInfo, rampSegment, duration));
                    int i3 = duration - 1;
                    if (i > i2) {
                        i += i3;
                    }
                    i2 += i3;
                    size += i3;
                }
            }
            i2++;
        }
        return i;
    }

    private static java.util.List<android.os.vibrator.RampSegment> splitRampSegment(android.os.VibratorInfo vibratorInfo, android.os.vibrator.RampSegment rampSegment, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList(i);
        float fillEmptyFrequency = fillEmptyFrequency(vibratorInfo, rampSegment.getStartFrequencyHz());
        float fillEmptyFrequency2 = fillEmptyFrequency(vibratorInfo, rampSegment.getEndFrequencyHz());
        long duration = rampSegment.getDuration() / i;
        long j = 0;
        float startAmplitude = rampSegment.getStartAmplitude();
        float f = fillEmptyFrequency;
        for (int i2 = 1; i2 < i; i2++) {
            j += duration;
            float duration2 = j / rampSegment.getDuration();
            android.os.vibrator.RampSegment rampSegment2 = new android.os.vibrator.RampSegment(startAmplitude, android.util.MathUtils.lerp(rampSegment.getStartAmplitude(), rampSegment.getEndAmplitude(), duration2), f, android.util.MathUtils.lerp(fillEmptyFrequency, fillEmptyFrequency2, duration2), (int) duration);
            arrayList.add(rampSegment2);
            startAmplitude = rampSegment2.getEndAmplitude();
            f = rampSegment2.getEndFrequencyHz();
        }
        arrayList.add(new android.os.vibrator.RampSegment(startAmplitude, rampSegment.getEndAmplitude(), f, fillEmptyFrequency2, (int) (rampSegment.getDuration() - j)));
        return arrayList;
    }

    private static float fillEmptyFrequency(android.os.VibratorInfo vibratorInfo, float f) {
        if (java.lang.Float.isNaN(vibratorInfo.getResonantFrequencyHz())) {
            return f;
        }
        return f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? vibratorInfo.getResonantFrequencyHz() : f;
    }
}
