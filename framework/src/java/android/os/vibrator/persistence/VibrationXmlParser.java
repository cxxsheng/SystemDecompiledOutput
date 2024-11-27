package android.os.vibrator.persistence;

/* loaded from: classes3.dex */
public final class VibrationXmlParser {
    public static final java.lang.String APPLICATION_VIBRATION_XML_MIME_TYPE = "application/vnd.android.haptics.vibration+xml";
    public static final int FLAG_ALLOW_HIDDEN_APIS = 1;
    private static final java.lang.String TAG = "VibrationXmlParser";

    /* JADX INFO: Access modifiers changed from: private */
    interface ElementParser<T> {
        T parse(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws java.io.IOException, com.android.internal.vibrator.persistence.XmlParserException;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static boolean isSupportedMimeType(java.lang.String str) {
        return APPLICATION_VIBRATION_XML_MIME_TYPE.equals(str);
    }

    public static android.os.VibrationEffect parseVibrationEffect(java.io.Reader reader) throws java.io.IOException {
        return parseVibrationEffect(reader, 0);
    }

    public static android.os.VibrationEffect parseVibrationEffect(java.io.Reader reader, int i) throws java.io.IOException {
        try {
            return (android.os.VibrationEffect) parseDocumentInternal(reader, i, new android.os.vibrator.persistence.VibrationXmlParser.ElementParser() { // from class: android.os.vibrator.persistence.VibrationXmlParser$$ExternalSyntheticLambda1
                @Override // android.os.vibrator.persistence.VibrationXmlParser.ElementParser
                public final java.lang.Object parse(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i2) {
                    android.os.VibrationEffect parseVibrationEffectInternal;
                    parseVibrationEffectInternal = android.os.vibrator.persistence.VibrationXmlParser.parseVibrationEffectInternal(typedXmlPullParser, i2);
                    return parseVibrationEffectInternal;
                }
            });
        } catch (com.android.internal.vibrator.persistence.XmlParserException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.w(TAG, "Error parsing vibration XML", e);
            return null;
        }
    }

    public static android.os.vibrator.persistence.ParsedVibration parseDocument(java.io.Reader reader) throws java.io.IOException {
        return parseDocument(reader, 0);
    }

    public static android.os.vibrator.persistence.ParsedVibration parseDocument(java.io.Reader reader, int i) throws java.io.IOException {
        try {
            return (android.os.vibrator.persistence.ParsedVibration) parseDocumentInternal(reader, i, new android.os.vibrator.persistence.VibrationXmlParser.ElementParser() { // from class: android.os.vibrator.persistence.VibrationXmlParser$$ExternalSyntheticLambda0
                @Override // android.os.vibrator.persistence.VibrationXmlParser.ElementParser
                public final java.lang.Object parse(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i2) {
                    android.os.vibrator.persistence.ParsedVibration parseElementInternal;
                    parseElementInternal = android.os.vibrator.persistence.VibrationXmlParser.parseElementInternal(typedXmlPullParser, i2);
                    return parseElementInternal;
                }
            });
        } catch (com.android.internal.vibrator.persistence.XmlParserException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.w(TAG, "Error parsing vibration/vibration-select XML", e);
            return null;
        }
    }

    public static android.os.vibrator.persistence.ParsedVibration parseElement(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws java.io.IOException, android.os.vibrator.persistence.VibrationXmlParser.VibrationXmlParserException {
        try {
            return parseElementInternal(typedXmlPullParser, i);
        } catch (com.android.internal.vibrator.persistence.XmlParserException e) {
            throw new android.os.vibrator.persistence.VibrationXmlParser.VibrationXmlParserException("Error parsing vibration-select.", e);
        }
    }

    public static final class VibrationXmlParserException extends java.lang.Exception {
        private VibrationXmlParserException(java.lang.String str, java.lang.Throwable th) {
            super(str, th);
        }

        private VibrationXmlParserException(java.lang.String str) {
            super(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static android.os.vibrator.persistence.ParsedVibration parseElementInternal(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws java.io.IOException, com.android.internal.vibrator.persistence.XmlParserException {
        char c;
        com.android.internal.vibrator.persistence.XmlValidator.checkStartTag(typedXmlPullParser);
        java.lang.String name = typedXmlPullParser.getName();
        switch (name.hashCode()) {
            case 9931052:
                if (name.equals(com.android.internal.vibrator.persistence.XmlConstants.TAG_VIBRATION_EFFECT)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 409994391:
                if (name.equals(com.android.internal.vibrator.persistence.XmlConstants.TAG_VIBRATION_SELECT)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return new android.os.vibrator.persistence.ParsedVibration(parseVibrationEffectInternal(typedXmlPullParser, i));
            case 1:
                return parseVibrationSelectInternal(typedXmlPullParser, i);
            default:
                throw new com.android.internal.vibrator.persistence.XmlParserException("Unexpected tag name when parsing element: " + name);
        }
    }

    private static android.os.vibrator.persistence.ParsedVibration parseVibrationSelectInternal(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws java.io.IOException, com.android.internal.vibrator.persistence.XmlParserException {
        com.android.internal.vibrator.persistence.XmlValidator.checkStartTag(typedXmlPullParser, com.android.internal.vibrator.persistence.XmlConstants.TAG_VIBRATION_SELECT);
        com.android.internal.vibrator.persistence.XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, new java.lang.String[0]);
        int depth = typedXmlPullParser.getDepth();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (com.android.internal.vibrator.persistence.XmlReader.readNextTagWithin(typedXmlPullParser, depth)) {
            arrayList.add(parseVibrationEffectInternal(typedXmlPullParser, i));
        }
        return new android.os.vibrator.persistence.ParsedVibration(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.VibrationEffect parseVibrationEffectInternal(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws java.io.IOException, com.android.internal.vibrator.persistence.XmlParserException {
        return com.android.internal.vibrator.persistence.VibrationEffectXmlParser.parseTag(typedXmlPullParser, (i & 1) == 0 ? 0 : 1).deserialize();
    }

    private static <T> T parseDocumentInternal(java.io.Reader reader, int i, android.os.vibrator.persistence.VibrationXmlParser.ElementParser<T> elementParser) throws java.io.IOException, com.android.internal.vibrator.persistence.XmlParserException, org.xmlpull.v1.XmlPullParserException {
        com.android.modules.utils.TypedXmlPullParser newFastPullParser = android.util.Xml.newFastPullParser();
        newFastPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
        newFastPullParser.setInput(reader);
        com.android.internal.vibrator.persistence.XmlReader.readDocumentStart(newFastPullParser);
        T parse = elementParser.parse(newFastPullParser, i);
        com.android.internal.vibrator.persistence.XmlReader.readDocumentEndTag(newFastPullParser);
        return parse;
    }

    private VibrationXmlParser() {
    }
}
