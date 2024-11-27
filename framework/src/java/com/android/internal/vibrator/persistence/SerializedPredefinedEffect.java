package com.android.internal.vibrator.persistence;

/* loaded from: classes5.dex */
final class SerializedPredefinedEffect implements com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment {
    private final com.android.internal.vibrator.persistence.XmlConstants.PredefinedEffectName mEffectName;
    private final boolean mShouldFallback;

    SerializedPredefinedEffect(com.android.internal.vibrator.persistence.XmlConstants.PredefinedEffectName predefinedEffectName, boolean z) {
        this.mEffectName = predefinedEffectName;
        this.mShouldFallback = z;
    }

    @Override // com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment
    public void deserializeIntoComposition(android.os.VibrationEffect.Composition composition) {
        composition.addEffect(android.os.VibrationEffect.get(this.mEffectName.getEffectId(), this.mShouldFallback));
    }

    @Override // com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment
    public void write(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.TAG_PREDEFINED_EFFECT);
        typedXmlSerializer.attribute(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, "name", this.mEffectName.toString());
        if (!this.mShouldFallback) {
            typedXmlSerializer.attributeBoolean(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_FALLBACK, this.mShouldFallback);
        }
        typedXmlSerializer.endTag(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.TAG_PREDEFINED_EFFECT);
    }

    public java.lang.String toString() {
        return "SerializedPredefinedEffect{name=" + this.mEffectName + ", fallback=" + this.mShouldFallback + '}';
    }

    static final class Parser {
        Parser() {
        }

        static com.android.internal.vibrator.persistence.SerializedPredefinedEffect parseNext(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
            com.android.internal.vibrator.persistence.XmlValidator.checkStartTag(typedXmlPullParser, com.android.internal.vibrator.persistence.XmlConstants.TAG_PREDEFINED_EFFECT);
            boolean z = (i & 1) != 0;
            if (z) {
                com.android.internal.vibrator.persistence.XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, "name", com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_FALLBACK);
            } else {
                com.android.internal.vibrator.persistence.XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, "name");
            }
            java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, "name");
            if (attributeValue == null) {
                throw new com.android.internal.vibrator.persistence.XmlParserException("Missing predefined effect name");
            }
            com.android.internal.vibrator.persistence.XmlConstants.PredefinedEffectName findByName = com.android.internal.vibrator.persistence.XmlConstants.PredefinedEffectName.findByName(attributeValue, i);
            if (findByName != null) {
                boolean attributeBoolean = z ? typedXmlPullParser.getAttributeBoolean(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_FALLBACK, true) : true;
                com.android.internal.vibrator.persistence.XmlReader.readEndTag(typedXmlPullParser);
                return new com.android.internal.vibrator.persistence.SerializedPredefinedEffect(findByName, attributeBoolean);
            }
            throw new com.android.internal.vibrator.persistence.XmlParserException("Unexpected predefined effect name " + attributeValue);
        }
    }
}
