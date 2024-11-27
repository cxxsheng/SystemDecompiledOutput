package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class HapticFeedbackCustomization {
    private static final java.lang.String ATTRIBUTE_ID = "id";
    private static final java.lang.String TAG = "HapticFeedbackCustomization";
    private static final java.lang.String TAG_CONSTANT = "constant";
    private static final java.lang.String TAG_CONSTANTS = "haptic-feedback-constants";

    HapticFeedbackCustomization() {
    }

    @android.annotation.Nullable
    static android.util.SparseArray<android.os.VibrationEffect> loadVibrations(android.content.res.Resources resources, android.os.VibratorInfo vibratorInfo) throws com.android.server.vibrator.HapticFeedbackCustomization.CustomizationParserException, java.io.IOException {
        try {
            return loadVibrationsInternal(resources, vibratorInfo);
        } catch (android.os.vibrator.persistence.VibrationXmlParser.VibrationXmlParserException | com.android.internal.vibrator.persistence.XmlParserException | org.xmlpull.v1.XmlPullParserException e) {
            throw new com.android.server.vibrator.HapticFeedbackCustomization.CustomizationParserException("Error parsing haptic feedback customization file.", e);
        }
    }

    @android.annotation.Nullable
    private static android.util.SparseArray<android.os.VibrationEffect> loadVibrationsInternal(android.content.res.Resources resources, android.os.VibratorInfo vibratorInfo) throws com.android.server.vibrator.HapticFeedbackCustomization.CustomizationParserException, java.io.IOException, android.os.vibrator.persistence.VibrationXmlParser.VibrationXmlParserException, com.android.internal.vibrator.persistence.XmlParserException, org.xmlpull.v1.XmlPullParserException {
        if (!android.os.vibrator.Flags.hapticFeedbackVibrationOemCustomizationEnabled()) {
            android.util.Slog.d(TAG, "Haptic feedback customization feature is not enabled.");
            return null;
        }
        java.lang.String string = resources.getString(android.R.string.config_extensionFallbackServiceName);
        if (android.text.TextUtils.isEmpty(string)) {
            android.util.Slog.d(TAG, "Customization file not configured.");
            return null;
        }
        try {
            java.io.FileReader fileReader = new java.io.FileReader(string);
            com.android.modules.utils.TypedXmlPullParser newFastPullParser = android.util.Xml.newFastPullParser();
            newFastPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
            newFastPullParser.setInput(fileReader);
            com.android.internal.vibrator.persistence.XmlReader.readDocumentStartTag(newFastPullParser, TAG_CONSTANTS);
            com.android.internal.vibrator.persistence.XmlValidator.checkTagHasNoUnexpectedAttributes(newFastPullParser, new java.lang.String[0]);
            int depth = newFastPullParser.getDepth();
            android.util.SparseArray<android.os.VibrationEffect> sparseArray = new android.util.SparseArray<>();
            while (com.android.internal.vibrator.persistence.XmlReader.readNextTagWithin(newFastPullParser, depth)) {
                com.android.internal.vibrator.persistence.XmlValidator.checkStartTag(newFastPullParser, TAG_CONSTANT);
                int depth2 = newFastPullParser.getDepth();
                com.android.internal.vibrator.persistence.XmlValidator.checkTagHasNoUnexpectedAttributes(newFastPullParser, new java.lang.String[]{ATTRIBUTE_ID});
                int readAttributeIntNonNegative = com.android.internal.vibrator.persistence.XmlReader.readAttributeIntNonNegative(newFastPullParser, ATTRIBUTE_ID);
                if (sparseArray.contains(readAttributeIntNonNegative)) {
                    throw new com.android.server.vibrator.HapticFeedbackCustomization.CustomizationParserException("Multiple customizations found for effect " + readAttributeIntNonNegative);
                }
                com.android.internal.vibrator.persistence.XmlValidator.checkParserCondition(com.android.internal.vibrator.persistence.XmlReader.readNextTagWithin(newFastPullParser, depth2), "Unsupported empty customization tag for effect " + readAttributeIntNonNegative, new java.lang.Object[0]);
                android.os.vibrator.persistence.ParsedVibration parseElement = android.os.vibrator.persistence.VibrationXmlParser.parseElement(newFastPullParser, 1);
                if (parseElement == null) {
                    throw new com.android.server.vibrator.HapticFeedbackCustomization.CustomizationParserException("Unable to parse vibration element for effect " + readAttributeIntNonNegative);
                }
                android.os.VibrationEffect resolve = parseElement.resolve(vibratorInfo);
                if (resolve != null) {
                    if (resolve.getDuration() == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                        throw new com.android.server.vibrator.HapticFeedbackCustomization.CustomizationParserException(java.lang.String.format("Vibration for effect ID %d is repeating, which is not allowed as a haptic feedback: %s", java.lang.Integer.valueOf(readAttributeIntNonNegative), resolve));
                    }
                    sparseArray.put(readAttributeIntNonNegative, resolve);
                }
                com.android.internal.vibrator.persistence.XmlReader.readEndTag(newFastPullParser, TAG_CONSTANT, depth2);
            }
            com.android.internal.vibrator.persistence.XmlReader.readEndTag(newFastPullParser, TAG_CONSTANTS, depth);
            com.android.internal.vibrator.persistence.XmlReader.readDocumentEndTag(newFastPullParser);
            return sparseArray;
        } catch (java.io.FileNotFoundException e) {
            android.util.Slog.d(TAG, "Specified customization file not found.");
            return null;
        }
    }

    static final class CustomizationParserException extends java.lang.Exception {
        private CustomizationParserException(java.lang.String str) {
            super(str);
        }

        private CustomizationParserException(java.lang.String str, java.lang.Throwable th) {
            super(str, th);
        }
    }
}
