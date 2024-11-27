package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class DeviceAdapter implements android.os.CombinedVibration.VibratorAdapter {
    private static final java.lang.String TAG = "DeviceAdapter";
    private final int[] mAvailableVibratorIds;
    private final android.util.SparseArray<com.android.server.vibrator.VibratorController> mAvailableVibrators;
    private final java.util.List<com.android.server.vibrator.VibrationSegmentsAdapter> mSegmentAdapters;

    DeviceAdapter(com.android.server.vibrator.VibrationSettings vibrationSettings, android.util.SparseArray<com.android.server.vibrator.VibratorController> sparseArray) {
        this.mSegmentAdapters = java.util.Arrays.asList(new com.android.server.vibrator.RampToStepAdapter(vibrationSettings.getRampStepDuration()), new com.android.server.vibrator.StepToRampAdapter(), new com.android.server.vibrator.RampDownAdapter(vibrationSettings.getRampDownDuration(), vibrationSettings.getRampStepDuration()), new com.android.server.vibrator.SplitSegmentsAdapter(), new com.android.server.vibrator.ClippingAmplitudeAndFrequencyAdapter());
        this.mAvailableVibrators = sparseArray;
        this.mAvailableVibratorIds = new int[sparseArray.size()];
        for (int i = 0; i < sparseArray.size(); i++) {
            this.mAvailableVibratorIds[i] = sparseArray.keyAt(i);
        }
    }

    android.util.SparseArray<com.android.server.vibrator.VibratorController> getAvailableVibrators() {
        return this.mAvailableVibrators;
    }

    public int[] getAvailableVibratorIds() {
        return this.mAvailableVibratorIds;
    }

    @android.annotation.NonNull
    public android.os.VibrationEffect adaptToVibrator(int i, @android.annotation.NonNull android.os.VibrationEffect vibrationEffect) {
        if (!(vibrationEffect instanceof android.os.VibrationEffect.Composed)) {
            android.util.Slog.wtf(TAG, "Error adapting unsupported vibration effect: " + vibrationEffect);
            return vibrationEffect;
        }
        com.android.server.vibrator.VibratorController vibratorController = this.mAvailableVibrators.get(i);
        if (vibratorController == null) {
            return vibrationEffect;
        }
        android.os.VibratorInfo vibratorInfo = vibratorController.getVibratorInfo();
        android.os.VibrationEffect.Composed composed = (android.os.VibrationEffect.Composed) vibrationEffect;
        java.util.ArrayList arrayList = new java.util.ArrayList(composed.getSegments());
        int repeatIndex = composed.getRepeatIndex();
        int size = this.mSegmentAdapters.size();
        for (int i2 = 0; i2 < size; i2++) {
            repeatIndex = this.mSegmentAdapters.get(i2).adaptToVibrator(vibratorInfo, arrayList, repeatIndex);
        }
        return new android.os.VibrationEffect.Composed(arrayList, repeatIndex);
    }
}
