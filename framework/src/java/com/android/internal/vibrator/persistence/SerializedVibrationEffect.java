package com.android.internal.vibrator.persistence;

/* loaded from: classes5.dex */
final class SerializedVibrationEffect implements com.android.internal.vibrator.persistence.XmlSerializedVibration<android.os.VibrationEffect> {
    private final com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment[] mSegments;

    interface SerializedSegment {
        void deserializeIntoComposition(android.os.VibrationEffect.Composition composition);

        void write(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException;
    }

    SerializedVibrationEffect(com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment serializedSegment) {
        java.util.Objects.requireNonNull(serializedSegment);
        this.mSegments = new com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment[]{serializedSegment};
    }

    SerializedVibrationEffect(com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment[] serializedSegmentArr) {
        java.util.Objects.requireNonNull(serializedSegmentArr);
        com.android.internal.util.Preconditions.checkArgument(serializedSegmentArr.length > 0, "Unsupported empty vibration");
        this.mSegments = serializedSegmentArr;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.internal.vibrator.persistence.XmlSerializedVibration
    public android.os.VibrationEffect deserialize() {
        android.os.VibrationEffect.Composition startComposition = android.os.VibrationEffect.startComposition();
        for (com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment serializedSegment : this.mSegments) {
            serializedSegment.deserializeIntoComposition(startComposition);
        }
        return startComposition.compose();
    }

    @Override // com.android.internal.vibrator.persistence.XmlSerializedVibration
    public void write(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.TAG_VIBRATION_EFFECT);
        writeContent(typedXmlSerializer);
        typedXmlSerializer.endTag(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.TAG_VIBRATION_EFFECT);
    }

    @Override // com.android.internal.vibrator.persistence.XmlSerializedVibration
    public void writeContent(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        for (com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment serializedSegment : this.mSegments) {
            serializedSegment.write(typedXmlSerializer);
        }
    }

    public java.lang.String toString() {
        return "SerializedVibrationEffect{segments=" + java.util.Arrays.toString(this.mSegments) + '}';
    }
}
