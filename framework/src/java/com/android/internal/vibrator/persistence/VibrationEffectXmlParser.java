package com.android.internal.vibrator.persistence;

/* loaded from: classes5.dex */
public class VibrationEffectXmlParser {
    public static com.android.internal.vibrator.persistence.XmlSerializedVibration<android.os.VibrationEffect> parseTag(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
        com.android.internal.vibrator.persistence.XmlValidator.checkStartTag(typedXmlPullParser, com.android.internal.vibrator.persistence.XmlConstants.TAG_VIBRATION_EFFECT);
        com.android.internal.vibrator.persistence.XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, new java.lang.String[0]);
        return parseVibrationContent(typedXmlPullParser, i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x003b, code lost:
    
        if (r2.equals(com.android.internal.vibrator.persistence.XmlConstants.TAG_PREDEFINED_EFFECT) != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static com.android.internal.vibrator.persistence.SerializedVibrationEffect parseVibrationContent(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
        com.android.internal.vibrator.persistence.SerializedVibrationEffect serializedVibrationEffect;
        java.lang.String name = typedXmlPullParser.getName();
        int depth = typedXmlPullParser.getDepth();
        char c = 0;
        com.android.internal.vibrator.persistence.XmlValidator.checkParserCondition(com.android.internal.vibrator.persistence.XmlReader.readNextTagWithin(typedXmlPullParser, depth), "Unsupported empty vibration tag", new java.lang.Object[0]);
        java.lang.String name2 = typedXmlPullParser.getName();
        switch (name2.hashCode()) {
            case -1327271112:
                break;
            case -764216799:
                if (name2.equals(com.android.internal.vibrator.persistence.XmlConstants.TAG_WAVEFORM_EFFECT)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 149296439:
                if (name2.equals(com.android.internal.vibrator.persistence.XmlConstants.TAG_PRIMITIVE_EFFECT)) {
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
                serializedVibrationEffect = new com.android.internal.vibrator.persistence.SerializedVibrationEffect(com.android.internal.vibrator.persistence.SerializedPredefinedEffect.Parser.parseNext(typedXmlPullParser, i));
                break;
            case 1:
                java.util.ArrayList arrayList = new java.util.ArrayList();
                do {
                    arrayList.add(com.android.internal.vibrator.persistence.SerializedCompositionPrimitive.Parser.parseNext(typedXmlPullParser));
                } while (com.android.internal.vibrator.persistence.XmlReader.readNextTagWithin(typedXmlPullParser, depth));
                serializedVibrationEffect = new com.android.internal.vibrator.persistence.SerializedVibrationEffect((com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment[]) arrayList.toArray(new com.android.internal.vibrator.persistence.SerializedVibrationEffect.SerializedSegment[arrayList.size()]));
                break;
            case 2:
                serializedVibrationEffect = new com.android.internal.vibrator.persistence.SerializedVibrationEffect(com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform.Parser.parseNext(typedXmlPullParser));
                break;
            default:
                throw new com.android.internal.vibrator.persistence.XmlParserException("Unexpected tag " + typedXmlPullParser.getName() + " in vibration tag " + name);
        }
        com.android.internal.vibrator.persistence.XmlReader.readEndTag(typedXmlPullParser, name, depth);
        return serializedVibrationEffect;
    }
}
