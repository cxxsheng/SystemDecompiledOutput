package com.android.internal.vibrator.persistence;

/* loaded from: classes5.dex */
final class SerializedCompositionPrimitive implements com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment {
    private final int mPrimitiveDelayMs;
    private final com.android.internal.vibrator.persistence.XmlConstants.PrimitiveEffectName mPrimitiveName;
    private final float mPrimitiveScale;

    SerializedCompositionPrimitive(com.android.internal.vibrator.persistence.XmlConstants.PrimitiveEffectName primitiveEffectName, float f, int i) {
        this.mPrimitiveName = primitiveEffectName;
        this.mPrimitiveScale = f;
        this.mPrimitiveDelayMs = i;
    }

    @Override // com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment
    public void deserializeIntoComposition(android.os.VibrationEffect.Composition composition) {
        composition.addPrimitive(this.mPrimitiveName.getPrimitiveId(), this.mPrimitiveScale, this.mPrimitiveDelayMs);
    }

    @Override // com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment
    public void write(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.TAG_PRIMITIVE_EFFECT);
        typedXmlSerializer.attribute(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, "name", this.mPrimitiveName.toString());
        if (java.lang.Float.compare(this.mPrimitiveScale, 1.0f) != 0) {
            typedXmlSerializer.attributeFloat(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, "scale", this.mPrimitiveScale);
        }
        if (this.mPrimitiveDelayMs != 0) {
            typedXmlSerializer.attributeInt(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_DELAY_MS, this.mPrimitiveDelayMs);
        }
        typedXmlSerializer.endTag(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, com.android.internal.vibrator.persistence.XmlConstants.TAG_PRIMITIVE_EFFECT);
    }

    public java.lang.String toString() {
        return "SerializedCompositionPrimitive{name=" + this.mPrimitiveName + ", scale=" + this.mPrimitiveScale + ", delayMs=" + this.mPrimitiveDelayMs + '}';
    }

    static final class Parser {
        Parser() {
        }

        static com.android.internal.vibrator.persistence.SerializedCompositionPrimitive parseNext(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
            com.android.internal.vibrator.persistence.XmlValidator.checkStartTag(typedXmlPullParser, com.android.internal.vibrator.persistence.XmlConstants.TAG_PRIMITIVE_EFFECT);
            com.android.internal.vibrator.persistence.XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, "name", com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_DELAY_MS, "scale");
            com.android.internal.vibrator.persistence.XmlConstants.PrimitiveEffectName parsePrimitiveName = parsePrimitiveName(typedXmlPullParser.getAttributeValue(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, "name"));
            float readAttributeFloatInRange = com.android.internal.vibrator.persistence.XmlReader.readAttributeFloatInRange(typedXmlPullParser, "scale", 0.0f, 1.0f, 1.0f);
            int readAttributeIntNonNegative = com.android.internal.vibrator.persistence.XmlReader.readAttributeIntNonNegative(typedXmlPullParser, com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_DELAY_MS, 0);
            com.android.internal.vibrator.persistence.XmlReader.readEndTag(typedXmlPullParser);
            return new com.android.internal.vibrator.persistence.SerializedCompositionPrimitive(parsePrimitiveName, readAttributeFloatInRange, readAttributeIntNonNegative);
        }

        private static com.android.internal.vibrator.persistence.XmlConstants.PrimitiveEffectName parsePrimitiveName(java.lang.String str) throws com.android.internal.vibrator.persistence.XmlParserException {
            if (str == null) {
                throw new com.android.internal.vibrator.persistence.XmlParserException("Missing primitive effect name");
            }
            com.android.internal.vibrator.persistence.XmlConstants.PrimitiveEffectName findByName = com.android.internal.vibrator.persistence.XmlConstants.PrimitiveEffectName.findByName(str);
            if (findByName == null) {
                throw new com.android.internal.vibrator.persistence.XmlParserException("Unexpected primitive effect name " + str);
            }
            return findByName;
        }
    }
}
