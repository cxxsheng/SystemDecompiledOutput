package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class RampDownAdapter implements com.android.server.vibrator.VibrationSegmentsAdapter {
    private final int mRampDownDuration;
    private final int mStepDuration;

    RampDownAdapter(int i, int i2) {
        this.mRampDownDuration = i;
        this.mStepDuration = i2;
    }

    @Override // com.android.server.vibrator.VibrationSegmentsAdapter
    public int adaptToVibrator(android.os.VibratorInfo vibratorInfo, java.util.List<android.os.vibrator.VibrationEffectSegment> list, int i) {
        if (this.mRampDownDuration <= 0) {
            return i;
        }
        return addRampDownToLoop(list, addRampDownToZeroAmplitudeSegments(list, i));
    }

    private int addRampDownToZeroAmplitudeSegments(java.util.List<android.os.vibrator.VibrationEffectSegment> list, int i) {
        java.util.List<android.os.vibrator.VibrationEffectSegment> list2;
        int size = list.size();
        int i2 = 1;
        while (i2 < size) {
            android.os.vibrator.StepSegment stepSegment = (android.os.vibrator.VibrationEffectSegment) list.get(i2 - 1);
            if (isOffSegment(list.get(i2)) && endsWithNonZeroAmplitude(stepSegment)) {
                long duration = list.get(i2).getDuration();
                if (stepSegment instanceof android.os.vibrator.StepSegment) {
                    android.os.vibrator.StepSegment stepSegment2 = stepSegment;
                    list2 = createStepsDown(stepSegment2.getAmplitude(), stepSegment2.getFrequencyHz(), duration);
                } else if (!(stepSegment instanceof android.os.vibrator.RampSegment)) {
                    list2 = null;
                } else {
                    android.os.vibrator.RampSegment rampSegment = (android.os.vibrator.RampSegment) stepSegment;
                    float endAmplitude = rampSegment.getEndAmplitude();
                    float endFrequencyHz = rampSegment.getEndFrequencyHz();
                    if (duration <= this.mRampDownDuration) {
                        list2 = java.util.Arrays.asList(createRampDown(endAmplitude, endFrequencyHz, duration));
                    } else {
                        list2 = java.util.Arrays.asList(createRampDown(endAmplitude, endFrequencyHz, this.mRampDownDuration), createRampDown(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, endFrequencyHz, duration - this.mRampDownDuration));
                    }
                }
                if (list2 != null) {
                    int size2 = list2.size() - 1;
                    android.os.vibrator.VibrationEffectSegment remove = list.remove(i2);
                    list.addAll(i2, list2);
                    if (i >= i2) {
                        if (i == i2) {
                            list.add(remove);
                            i++;
                            size++;
                        }
                        i += size2;
                    }
                    i2 += size2;
                    size += size2;
                }
            }
            i2++;
        }
        return i;
    }

    private int addRampDownToLoop(java.util.List<android.os.vibrator.VibrationEffectSegment> list, int i) {
        if (i < 0) {
            return i;
        }
        int size = list.size() - 1;
        if (!endsWithNonZeroAmplitude(list.get(size)) || !isOffSegment(list.get(i))) {
            return i;
        }
        android.os.vibrator.StepSegment stepSegment = (android.os.vibrator.VibrationEffectSegment) list.get(size);
        android.os.vibrator.VibrationEffectSegment vibrationEffectSegment = list.get(i);
        long duration = vibrationEffectSegment.getDuration();
        if (duration > this.mRampDownDuration) {
            list.set(i, updateDuration(vibrationEffectSegment, duration - this.mRampDownDuration));
            list.add(i, updateDuration(vibrationEffectSegment, this.mRampDownDuration));
        }
        int i2 = i + 1;
        if (stepSegment instanceof android.os.vibrator.StepSegment) {
            android.os.vibrator.StepSegment stepSegment2 = stepSegment;
            list.addAll(createStepsDown(stepSegment2.getAmplitude(), stepSegment2.getFrequencyHz(), java.lang.Math.min(duration, this.mRampDownDuration)));
        } else if (stepSegment instanceof android.os.vibrator.RampSegment) {
            android.os.vibrator.RampSegment rampSegment = (android.os.vibrator.RampSegment) stepSegment;
            list.add(createRampDown(rampSegment.getEndAmplitude(), rampSegment.getEndFrequencyHz(), java.lang.Math.min(duration, this.mRampDownDuration)));
        }
        return i2;
    }

    private java.util.List<android.os.vibrator.VibrationEffectSegment> createStepsDown(float f, float f2, long j) {
        int min = ((int) java.lang.Math.min(j, this.mRampDownDuration)) / this.mStepDuration;
        float f3 = f / min;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 1; i < min; i++) {
            arrayList.add(new android.os.vibrator.StepSegment(f - (i * f3), f2, this.mStepDuration));
        }
        arrayList.add(new android.os.vibrator.StepSegment(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, f2, ((int) j) - (this.mStepDuration * (min - 1))));
        return arrayList;
    }

    private static android.os.vibrator.RampSegment createRampDown(float f, float f2, long j) {
        return new android.os.vibrator.RampSegment(f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, f2, f2, (int) j);
    }

    private static android.os.vibrator.VibrationEffectSegment updateDuration(android.os.vibrator.VibrationEffectSegment vibrationEffectSegment, long j) {
        if (vibrationEffectSegment instanceof android.os.vibrator.RampSegment) {
            android.os.vibrator.RampSegment rampSegment = (android.os.vibrator.RampSegment) vibrationEffectSegment;
            return new android.os.vibrator.RampSegment(rampSegment.getStartAmplitude(), rampSegment.getEndAmplitude(), rampSegment.getStartFrequencyHz(), rampSegment.getEndFrequencyHz(), (int) j);
        }
        if (vibrationEffectSegment instanceof android.os.vibrator.StepSegment) {
            android.os.vibrator.StepSegment stepSegment = (android.os.vibrator.StepSegment) vibrationEffectSegment;
            return new android.os.vibrator.StepSegment(stepSegment.getAmplitude(), stepSegment.getFrequencyHz(), (int) j);
        }
        return vibrationEffectSegment;
    }

    private static boolean isOffSegment(android.os.vibrator.VibrationEffectSegment vibrationEffectSegment) {
        if (vibrationEffectSegment instanceof android.os.vibrator.StepSegment) {
            return ((android.os.vibrator.StepSegment) vibrationEffectSegment).getAmplitude() == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        if (!(vibrationEffectSegment instanceof android.os.vibrator.RampSegment)) {
            return false;
        }
        android.os.vibrator.RampSegment rampSegment = (android.os.vibrator.RampSegment) vibrationEffectSegment;
        return rampSegment.getStartAmplitude() == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && rampSegment.getEndAmplitude() == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    }

    private static boolean endsWithNonZeroAmplitude(android.os.vibrator.VibrationEffectSegment vibrationEffectSegment) {
        return vibrationEffectSegment instanceof android.os.vibrator.StepSegment ? ((android.os.vibrator.StepSegment) vibrationEffectSegment).getAmplitude() != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE : (vibrationEffectSegment instanceof android.os.vibrator.RampSegment) && ((android.os.vibrator.RampSegment) vibrationEffectSegment).getEndAmplitude() != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    }
}
