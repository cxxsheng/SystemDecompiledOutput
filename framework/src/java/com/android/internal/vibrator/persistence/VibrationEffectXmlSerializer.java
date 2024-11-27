package com.android.internal.vibrator.persistence;

/* loaded from: classes5.dex */
public final class VibrationEffectXmlSerializer {
    public static com.android.internal.vibrator.persistence.XmlSerializedVibration<android.os.VibrationEffect> serialize(android.os.VibrationEffect vibrationEffect, int i) throws com.android.internal.vibrator.persistence.XmlSerializerException {
        com.android.internal.vibrator.persistence.XmlValidator.checkSerializerCondition(vibrationEffect instanceof android.os.VibrationEffect.Composed, "Unsupported VibrationEffect type %s", vibrationEffect);
        android.os.VibrationEffect.Composed composed = (android.os.VibrationEffect.Composed) vibrationEffect;
        com.android.internal.vibrator.persistence.XmlValidator.checkSerializerCondition(!composed.getSegments().isEmpty(), "Unsupported empty VibrationEffect %s", vibrationEffect);
        android.os.vibrator.VibrationEffectSegment vibrationEffectSegment = composed.getSegments().get(0);
        if (vibrationEffectSegment instanceof android.os.vibrator.PrebakedSegment) {
            return serializePredefinedEffect(composed, i);
        }
        if (vibrationEffectSegment instanceof android.os.vibrator.PrimitiveSegment) {
            return serializePrimitiveEffect(composed);
        }
        return serializeWaveformEffect(composed);
    }

    private static com.android.internal.vibrator.persistence.SerializedVibrationEffect serializePredefinedEffect(android.os.VibrationEffect.Composed composed, int i) throws com.android.internal.vibrator.persistence.XmlSerializerException {
        java.util.List<android.os.vibrator.VibrationEffectSegment> segments = composed.getSegments();
        com.android.internal.vibrator.persistence.XmlValidator.checkSerializerCondition(composed.getRepeatIndex() == -1, "Unsupported repeating predefined effect %s", composed);
        com.android.internal.vibrator.persistence.XmlValidator.checkSerializerCondition(segments.size() == 1, "Unsupported multiple segments in predefined effect %s", composed);
        return new com.android.internal.vibrator.persistence.SerializedVibrationEffect(serializePrebakedSegment(segments.get(0), i));
    }

    private static com.android.internal.vibrator.persistence.SerializedVibrationEffect serializePrimitiveEffect(android.os.VibrationEffect.Composed composed) throws com.android.internal.vibrator.persistence.XmlSerializerException {
        java.util.List<android.os.vibrator.VibrationEffectSegment> segments = composed.getSegments();
        com.android.internal.vibrator.persistence.XmlValidator.checkSerializerCondition(composed.getRepeatIndex() == -1, "Unsupported repeating primitive composition %s", composed);
        com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment[] serializedSegmentArr = new com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment[segments.size()];
        for (int i = 0; i < segments.size(); i++) {
            serializedSegmentArr[i] = serializePrimitiveSegment(segments.get(i));
        }
        return new com.android.internal.vibrator.persistence.SerializedVibrationEffect(serializedSegmentArr);
    }

    private static com.android.internal.vibrator.persistence.SerializedVibrationEffect serializeWaveformEffect(android.os.VibrationEffect.Composed composed) throws com.android.internal.vibrator.persistence.XmlSerializerException {
        com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform.Builder builder = new com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform.Builder();
        java.util.List<android.os.vibrator.VibrationEffectSegment> segments = composed.getSegments();
        for (int i = 0; i < segments.size(); i++) {
            com.android.internal.vibrator.persistence.XmlValidator.checkSerializerCondition(segments.get(i) instanceof android.os.vibrator.StepSegment, "Unsupported segment for waveform effect %s", segments.get(i));
            android.os.vibrator.StepSegment stepSegment = (android.os.vibrator.StepSegment) segments.get(i);
            if (composed.getRepeatIndex() == i) {
                builder.setRepeatIndexToCurrentEntry();
            }
            com.android.internal.vibrator.persistence.XmlValidator.checkSerializerCondition(java.lang.Float.compare(stepSegment.getFrequencyHz(), 0.0f) == 0, "Unsupported segment with non-default frequency %f", java.lang.Float.valueOf(stepSegment.getFrequencyHz()));
            builder.addDurationAndAmplitude(stepSegment.getDuration(), toAmplitudeInt(stepSegment.getAmplitude()));
        }
        return new com.android.internal.vibrator.persistence.SerializedVibrationEffect(builder.build());
    }

    private static com.android.internal.vibrator.persistence.SerializedPredefinedEffect serializePrebakedSegment(android.os.vibrator.VibrationEffectSegment vibrationEffectSegment, int i) throws com.android.internal.vibrator.persistence.XmlSerializerException {
        com.android.internal.vibrator.persistence.XmlValidator.checkSerializerCondition(vibrationEffectSegment instanceof android.os.vibrator.PrebakedSegment, "Unsupported segment for predefined effect %s", vibrationEffectSegment);
        android.os.vibrator.PrebakedSegment prebakedSegment = (android.os.vibrator.PrebakedSegment) vibrationEffectSegment;
        com.android.internal.vibrator.persistence.XmlConstants.PredefinedEffectName findById = com.android.internal.vibrator.persistence.XmlConstants.PredefinedEffectName.findById(prebakedSegment.getEffectId(), i);
        com.android.internal.vibrator.persistence.XmlValidator.checkSerializerCondition(findById != null, "Unsupported predefined effect id %s", java.lang.Integer.valueOf(prebakedSegment.getEffectId()));
        if ((i & 1) == 0) {
            com.android.internal.vibrator.persistence.XmlValidator.checkSerializerCondition(prebakedSegment.shouldFallback(), "Unsupported predefined effect with should fallback %s", java.lang.Boolean.valueOf(prebakedSegment.shouldFallback()));
        }
        return new com.android.internal.vibrator.persistence.SerializedPredefinedEffect(findById, prebakedSegment.shouldFallback());
    }

    private static com.android.internal.vibrator.persistence.SerializedCompositionPrimitive serializePrimitiveSegment(android.os.vibrator.VibrationEffectSegment vibrationEffectSegment) throws com.android.internal.vibrator.persistence.XmlSerializerException {
        com.android.internal.vibrator.persistence.XmlValidator.checkSerializerCondition(vibrationEffectSegment instanceof android.os.vibrator.PrimitiveSegment, "Unsupported segment for primitive composition %s", vibrationEffectSegment);
        android.os.vibrator.PrimitiveSegment primitiveSegment = (android.os.vibrator.PrimitiveSegment) vibrationEffectSegment;
        com.android.internal.vibrator.persistence.XmlConstants.PrimitiveEffectName findById = com.android.internal.vibrator.persistence.XmlConstants.PrimitiveEffectName.findById(primitiveSegment.getPrimitiveId());
        com.android.internal.vibrator.persistence.XmlValidator.checkSerializerCondition(findById != null, "Unsupported primitive effect id %s", java.lang.Integer.valueOf(primitiveSegment.getPrimitiveId()));
        return new com.android.internal.vibrator.persistence.SerializedCompositionPrimitive(findById, primitiveSegment.getScale(), primitiveSegment.getDelay());
    }

    private static int toAmplitudeInt(float f) {
        if (java.lang.Float.compare(f, -1.0f) == 0) {
            return -1;
        }
        return java.lang.Math.round(f * 255.0f);
    }
}
