package android.os.vibrator;

/* loaded from: classes3.dex */
public final class MultiVibratorInfo extends android.os.VibratorInfo {
    private static final float EPSILON = 1.0E-5f;
    private static final java.lang.String TAG = "MultiVibratorInfo";

    public MultiVibratorInfo(int i, android.os.VibratorInfo[] vibratorInfoArr) {
        this(i, vibratorInfoArr, frequencyProfileIntersection(vibratorInfoArr));
    }

    private MultiVibratorInfo(int i, android.os.VibratorInfo[] vibratorInfoArr, android.os.VibratorInfo.FrequencyProfile frequencyProfile) {
        super(i, capabilitiesIntersection(vibratorInfoArr, frequencyProfile.isEmpty()), supportedEffectsIntersection(vibratorInfoArr), supportedBrakingIntersection(vibratorInfoArr), supportedPrimitivesAndDurationsIntersection(vibratorInfoArr), integerLimitIntersection(vibratorInfoArr, new java.util.function.Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return java.lang.Integer.valueOf(((android.os.VibratorInfo) obj).getPrimitiveDelayMax());
            }
        }), integerLimitIntersection(vibratorInfoArr, new java.util.function.Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return java.lang.Integer.valueOf(((android.os.VibratorInfo) obj).getCompositionSizeMax());
            }
        }), integerLimitIntersection(vibratorInfoArr, new java.util.function.Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return java.lang.Integer.valueOf(((android.os.VibratorInfo) obj).getPwlePrimitiveDurationMax());
            }
        }), integerLimitIntersection(vibratorInfoArr, new java.util.function.Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return java.lang.Integer.valueOf(((android.os.VibratorInfo) obj).getPwleSizeMax());
            }
        }), floatPropertyIntersection(vibratorInfoArr, new java.util.function.Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return java.lang.Float.valueOf(((android.os.VibratorInfo) obj).getQFactor());
            }
        }), frequencyProfile);
    }

    private static int capabilitiesIntersection(android.os.VibratorInfo[] vibratorInfoArr, boolean z) {
        int i = -1;
        for (android.os.VibratorInfo vibratorInfo : vibratorInfoArr) {
            i = (int) (i & vibratorInfo.getCapabilities());
        }
        if (z) {
            return i & (-513);
        }
        return i;
    }

    private static android.util.SparseBooleanArray supportedBrakingIntersection(android.os.VibratorInfo[] vibratorInfoArr) {
        for (android.os.VibratorInfo vibratorInfo : vibratorInfoArr) {
            if (!vibratorInfo.isBrakingSupportKnown()) {
                return null;
            }
        }
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        android.util.SparseBooleanArray supportedBraking = vibratorInfoArr[0].getSupportedBraking();
        for (int i = 0; i < supportedBraking.size(); i++) {
            int keyAt = supportedBraking.keyAt(i);
            if (supportedBraking.valueAt(i)) {
                int i2 = 1;
                while (true) {
                    if (i2 < vibratorInfoArr.length) {
                        if (!vibratorInfoArr[i2].hasBrakingSupport(keyAt)) {
                            break;
                        }
                        i2++;
                    } else {
                        sparseBooleanArray.put(keyAt, true);
                        break;
                    }
                }
            }
        }
        return sparseBooleanArray;
    }

    private static android.util.SparseBooleanArray supportedEffectsIntersection(android.os.VibratorInfo[] vibratorInfoArr) {
        for (android.os.VibratorInfo vibratorInfo : vibratorInfoArr) {
            if (!vibratorInfo.isEffectSupportKnown()) {
                return null;
            }
        }
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        android.util.SparseBooleanArray supportedEffects = vibratorInfoArr[0].getSupportedEffects();
        for (int i = 0; i < supportedEffects.size(); i++) {
            int keyAt = supportedEffects.keyAt(i);
            if (supportedEffects.valueAt(i)) {
                int i2 = 1;
                while (true) {
                    if (i2 < vibratorInfoArr.length) {
                        if (vibratorInfoArr[i2].isEffectSupported(keyAt) != 1) {
                            break;
                        }
                        i2++;
                    } else {
                        sparseBooleanArray.put(keyAt, true);
                        break;
                    }
                }
            }
        }
        return sparseBooleanArray;
    }

    private static android.util.SparseIntArray supportedPrimitivesAndDurationsIntersection(android.os.VibratorInfo[] vibratorInfoArr) {
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
        android.util.SparseIntArray supportedPrimitives = vibratorInfoArr[0].getSupportedPrimitives();
        for (int i = 0; i < supportedPrimitives.size(); i++) {
            int keyAt = supportedPrimitives.keyAt(i);
            int valueAt = supportedPrimitives.valueAt(i);
            if (valueAt != 0) {
                int i2 = 1;
                while (true) {
                    if (i2 < vibratorInfoArr.length) {
                        int primitiveDuration = vibratorInfoArr[i2].getPrimitiveDuration(keyAt);
                        if (primitiveDuration == 0) {
                            break;
                        }
                        valueAt = java.lang.Math.max(valueAt, primitiveDuration);
                        i2++;
                    } else {
                        sparseIntArray.put(keyAt, valueAt);
                        break;
                    }
                }
            }
        }
        return sparseIntArray;
    }

    private static int integerLimitIntersection(android.os.VibratorInfo[] vibratorInfoArr, java.util.function.Function<android.os.VibratorInfo, java.lang.Integer> function) {
        int i = 0;
        for (android.os.VibratorInfo vibratorInfo : vibratorInfoArr) {
            int intValue = function.apply(vibratorInfo).intValue();
            if (i == 0 || (intValue > 0 && intValue < i)) {
                i = intValue;
            }
        }
        return i;
    }

    private static float floatPropertyIntersection(android.os.VibratorInfo[] vibratorInfoArr, java.util.function.Function<android.os.VibratorInfo, java.lang.Float> function) {
        float floatValue = function.apply(vibratorInfoArr[0]).floatValue();
        if (java.lang.Float.isNaN(floatValue)) {
            return Float.NaN;
        }
        for (int i = 1; i < vibratorInfoArr.length; i++) {
            if (java.lang.Float.compare(floatValue, function.apply(vibratorInfoArr[i]).floatValue()) != 0) {
                return Float.NaN;
            }
        }
        return floatValue;
    }

    private static android.os.VibratorInfo.FrequencyProfile frequencyProfileIntersection(android.os.VibratorInfo[] vibratorInfoArr) {
        float floatPropertyIntersection = floatPropertyIntersection(vibratorInfoArr, new java.util.function.Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Float valueOf;
                valueOf = java.lang.Float.valueOf(((android.os.VibratorInfo) obj).getFrequencyProfile().getFrequencyResolutionHz());
                return valueOf;
            }
        });
        float floatPropertyIntersection2 = floatPropertyIntersection(vibratorInfoArr, new java.util.function.Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return java.lang.Float.valueOf(((android.os.VibratorInfo) obj).getResonantFrequencyHz());
            }
        });
        android.util.Range<java.lang.Float> frequencyRangeIntersection = frequencyRangeIntersection(vibratorInfoArr, floatPropertyIntersection);
        if (frequencyRangeIntersection == null || java.lang.Float.isNaN(floatPropertyIntersection)) {
            return new android.os.VibratorInfo.FrequencyProfile(floatPropertyIntersection2, Float.NaN, floatPropertyIntersection, null);
        }
        int round = java.lang.Math.round(((frequencyRangeIntersection.getUpper().floatValue() - frequencyRangeIntersection.getLower().floatValue()) / floatPropertyIntersection) + 1.0f);
        float[] fArr = new float[round];
        java.util.Arrays.fill(fArr, Float.MAX_VALUE);
        for (android.os.VibratorInfo vibratorInfo : vibratorInfoArr) {
            android.util.Range<java.lang.Float> frequencyRangeHz = vibratorInfo.getFrequencyProfile().getFrequencyRangeHz();
            float[] maxAmplitudes = vibratorInfo.getFrequencyProfile().getMaxAmplitudes();
            int round2 = java.lang.Math.round((frequencyRangeIntersection.getLower().floatValue() - frequencyRangeHz.getLower().floatValue()) / floatPropertyIntersection);
            int i = (round2 + round) - 1;
            if (round2 < 0 || i >= maxAmplitudes.length) {
                android.util.Slog.w(TAG, "Error calculating the intersection of vibrator frequency profiles: attempted to fetch from vibrator " + vibratorInfo.getId() + " max amplitude with bad index " + round2);
                return new android.os.VibratorInfo.FrequencyProfile(floatPropertyIntersection2, Float.NaN, Float.NaN, null);
            }
            for (int i2 = 0; i2 < round; i2++) {
                fArr[i2] = java.lang.Math.min(fArr[i2], maxAmplitudes[round2 + i2]);
            }
        }
        return new android.os.VibratorInfo.FrequencyProfile(floatPropertyIntersection2, frequencyRangeIntersection.getLower().floatValue(), floatPropertyIntersection, fArr);
    }

    private static android.util.Range<java.lang.Float> frequencyRangeIntersection(android.os.VibratorInfo[] vibratorInfoArr, float f) {
        android.util.Range<java.lang.Float> frequencyRangeHz = vibratorInfoArr[0].getFrequencyProfile().getFrequencyRangeHz();
        if (frequencyRangeHz == null) {
            return null;
        }
        float floatValue = frequencyRangeHz.getLower().floatValue();
        float floatValue2 = frequencyRangeHz.getUpper().floatValue();
        for (int i = 1; i < vibratorInfoArr.length; i++) {
            android.util.Range<java.lang.Float> frequencyRangeHz2 = vibratorInfoArr[i].getFrequencyProfile().getFrequencyRangeHz();
            if (frequencyRangeHz2 == null || frequencyRangeHz2.getLower().floatValue() >= floatValue2 || frequencyRangeHz2.getUpper().floatValue() <= floatValue || java.lang.Math.abs(floatValue - frequencyRangeHz2.getLower().floatValue()) % f > EPSILON) {
                return null;
            }
            floatValue = java.lang.Math.max(floatValue, frequencyRangeHz2.getLower().floatValue());
            floatValue2 = java.lang.Math.min(floatValue2, frequencyRangeHz2.getUpper().floatValue());
        }
        if (floatValue2 - floatValue < f) {
            return null;
        }
        return android.util.Range.create(java.lang.Float.valueOf(floatValue), java.lang.Float.valueOf(floatValue2));
    }
}
