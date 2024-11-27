package com.android.internal.vibrator.persistence;

/* loaded from: classes5.dex */
final class SerializedAmplitudeStepWaveform implements com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment {
    private final int[] mAmplitudes;
    private final int mRepeatIndex;
    private final long[] mTimings;

    private SerializedAmplitudeStepWaveform(long[] jArr, int[] iArr, int i) {
        this.mTimings = jArr;
        this.mAmplitudes = iArr;
        this.mRepeatIndex = i;
    }

    @Override // com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment
    public void deserializeIntoComposition(android.os.VibrationEffect.Composition composition) {
        composition.addEffect(android.os.VibrationEffect.createWaveform(this.mTimings, this.mAmplitudes, this.mRepeatIndex));
    }

    @Override // com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment
    public void write(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.TAG_WAVEFORM_EFFECT);
        for (int i = 0; i < this.mTimings.length; i++) {
            if (i == this.mRepeatIndex) {
                typedXmlSerializer.startTag(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.TAG_REPEATING);
            }
            writeWaveformEntry(typedXmlSerializer, i);
        }
        if (this.mRepeatIndex >= 0) {
            typedXmlSerializer.endTag(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.TAG_REPEATING);
        }
        typedXmlSerializer.endTag(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.TAG_WAVEFORM_EFFECT);
    }

    private void writeWaveformEntry(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, int i) throws java.io.IOException {
        typedXmlSerializer.startTag(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.TAG_WAVEFORM_ENTRY);
        if (this.mAmplitudes[i] == -1) {
            typedXmlSerializer.attribute(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_AMPLITUDE, "default");
        } else {
            typedXmlSerializer.attributeInt(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_AMPLITUDE, this.mAmplitudes[i]);
        }
        typedXmlSerializer.attributeLong(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_DURATION_MS, this.mTimings[i]);
        typedXmlSerializer.endTag(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.TAG_WAVEFORM_ENTRY);
    }

    public java.lang.String toString() {
        return "SerializedAmplitudeStepWaveform{timings=" + java.util.Arrays.toString(this.mTimings) + ", amplitudes=" + java.util.Arrays.toString(this.mAmplitudes) + ", repeatIndex=" + this.mRepeatIndex + '}';
    }

    static final class Builder {
        private final android.util.LongArray mTimings = new android.util.LongArray();
        private final android.util.IntArray mAmplitudes = new android.util.IntArray();
        private int mRepeatIndex = -1;

        Builder() {
        }

        void addDurationAndAmplitude(long j, int i) {
            this.mTimings.add(j);
            this.mAmplitudes.add(i);
        }

        void setRepeatIndexToCurrentEntry() {
            this.mRepeatIndex = this.mTimings.size();
        }

        boolean hasNonZeroDuration() {
            for (int i = 0; i < this.mTimings.size(); i++) {
                if (this.mTimings.get(i) > 0) {
                    return true;
                }
            }
            return false;
        }

        com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform build() {
            return new com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform(this.mTimings.toArray(), this.mAmplitudes.toArray(), this.mRepeatIndex);
        }
    }

    static final class Parser {
        Parser() {
        }

        static com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform parseNext(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
            com.android.internal.vibrator.persistence.XmlValidator.checkStartTag(typedXmlPullParser, com.android.internal.vibrator.persistence.XmlConstants.TAG_WAVEFORM_EFFECT);
            com.android.internal.vibrator.persistence.XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, new java.lang.String[0]);
            com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform.Builder builder = new com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform.Builder();
            int depth = typedXmlPullParser.getDepth();
            while (com.android.internal.vibrator.persistence.XmlReader.readNextTagWithin(typedXmlPullParser, depth) && !com.android.internal.vibrator.persistence.XmlConstants.TAG_REPEATING.equals(typedXmlPullParser.getName())) {
                parseWaveformEntry(typedXmlPullParser, builder);
            }
            if (com.android.internal.vibrator.persistence.XmlConstants.TAG_REPEATING.equals(typedXmlPullParser.getName())) {
                parseRepeating(typedXmlPullParser, builder);
            }
            com.android.internal.vibrator.persistence.XmlValidator.checkParserCondition(builder.hasNonZeroDuration(), "Unexpected %s tag with total duration zero", com.android.internal.vibrator.persistence.XmlConstants.TAG_WAVEFORM_EFFECT);
            com.android.internal.vibrator.persistence.XmlReader.readEndTag(typedXmlPullParser, com.android.internal.vibrator.persistence.XmlConstants.TAG_WAVEFORM_EFFECT, depth);
            return builder.build();
        }

        private static void parseRepeating(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform.Builder builder) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
            com.android.internal.vibrator.persistence.XmlValidator.checkStartTag(typedXmlPullParser, com.android.internal.vibrator.persistence.XmlConstants.TAG_REPEATING);
            boolean z = false;
            com.android.internal.vibrator.persistence.XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, new java.lang.String[0]);
            builder.setRepeatIndexToCurrentEntry();
            int depth = typedXmlPullParser.getDepth();
            while (com.android.internal.vibrator.persistence.XmlReader.readNextTagWithin(typedXmlPullParser, depth)) {
                parseWaveformEntry(typedXmlPullParser, builder);
                z = true;
            }
            com.android.internal.vibrator.persistence.XmlValidator.checkParserCondition(z, "Unexpected empty %s tag", com.android.internal.vibrator.persistence.XmlConstants.TAG_REPEATING);
            com.android.internal.vibrator.persistence.XmlReader.readEndTag(typedXmlPullParser, com.android.internal.vibrator.persistence.XmlConstants.TAG_REPEATING, depth);
        }

        private static void parseWaveformEntry(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform.Builder builder) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
            int readAttributeIntInRange;
            com.android.internal.vibrator.persistence.XmlValidator.checkStartTag(typedXmlPullParser, com.android.internal.vibrator.persistence.XmlConstants.TAG_WAVEFORM_ENTRY);
            com.android.internal.vibrator.persistence.XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_DURATION_MS, com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_AMPLITUDE);
            if ("default".equals(typedXmlPullParser.getAttributeValue(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_AMPLITUDE))) {
                readAttributeIntInRange = -1;
            } else {
                readAttributeIntInRange = com.android.internal.vibrator.persistence.XmlReader.readAttributeIntInRange(typedXmlPullParser, com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_AMPLITUDE, 0, 255);
            }
            builder.addDurationAndAmplitude(com.android.internal.vibrator.persistence.XmlReader.readAttributeIntNonNegative(typedXmlPullParser, com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_DURATION_MS), readAttributeIntInRange);
            com.android.internal.vibrator.persistence.XmlReader.readEndTag(typedXmlPullParser);
        }
    }
}
