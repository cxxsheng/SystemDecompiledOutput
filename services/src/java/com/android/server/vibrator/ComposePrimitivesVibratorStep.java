package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class ComposePrimitivesVibratorStep extends com.android.server.vibrator.AbstractVibratorStep {
    private static final int DEFAULT_COMPOSITION_SIZE_LIMIT = 100;

    ComposePrimitivesVibratorStep(com.android.server.vibrator.VibrationStepConductor vibrationStepConductor, long j, com.android.server.vibrator.VibratorController vibratorController, android.os.VibrationEffect.Composed composed, int i, long j2) {
        super(vibrationStepConductor, java.lang.Math.max(j, j2), vibratorController, composed, i, j2);
    }

    @Override // com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> play() {
        android.os.Trace.traceBegin(8388608L, "ComposePrimitivesStep");
        try {
            int compositionSizeMax = this.controller.getVibratorInfo().getCompositionSizeMax();
            android.os.VibrationEffect.Composed composed = this.effect;
            int i = this.segmentIndex;
            if (compositionSizeMax <= 0) {
                compositionSizeMax = 100;
            }
            java.util.List<android.os.vibrator.PrimitiveSegment> unrollPrimitiveSegments = unrollPrimitiveSegments(composed, i, compositionSizeMax);
            if (unrollPrimitiveSegments.isEmpty()) {
                android.util.Slog.w("VibrationThread", "Ignoring wrong segment for a ComposePrimitivesStep: " + this.effect.getSegments().get(this.segmentIndex));
                return nextSteps(1);
            }
            android.os.vibrator.PrimitiveSegment[] primitiveSegmentArr = (android.os.vibrator.PrimitiveSegment[]) unrollPrimitiveSegments.toArray(new android.os.vibrator.PrimitiveSegment[unrollPrimitiveSegments.size()]);
            long on = this.controller.on(primitiveSegmentArr, getVibration().id);
            handleVibratorOnResult(on);
            getVibration().stats.reportComposePrimitives(on, primitiveSegmentArr);
            return nextSteps(unrollPrimitiveSegments.size());
        } finally {
            android.os.Trace.traceEnd(8388608L);
        }
    }

    private java.util.List<android.os.vibrator.PrimitiveSegment> unrollPrimitiveSegments(android.os.VibrationEffect.Composed composed, int i, int i2) {
        java.util.ArrayList arrayList = new java.util.ArrayList(i2);
        int size = composed.getSegments().size();
        int repeatIndex = composed.getRepeatIndex();
        while (arrayList.size() < i2) {
            if (i == size) {
                if (repeatIndex < 0) {
                    break;
                }
                i = repeatIndex;
            }
            android.os.vibrator.PrimitiveSegment primitiveSegment = (android.os.vibrator.VibrationEffectSegment) composed.getSegments().get(i);
            if (!(primitiveSegment instanceof android.os.vibrator.PrimitiveSegment)) {
                break;
            }
            arrayList.add(primitiveSegment);
            i++;
        }
        return arrayList;
    }
}
