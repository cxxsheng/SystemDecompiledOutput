package android.os.vibrator.persistence;

/* loaded from: classes3.dex */
public final class VibrationXmlSerializer {
    public static final int FLAG_ALLOW_HIDDEN_APIS = 1;
    public static final int FLAG_PRETTY_PRINT = 2;
    private static final java.lang.String XML_ENCODING = android.util.Xml.Encoding.UTF_8.name();
    private static final java.lang.String XML_FEATURE_INDENT_OUTPUT = "http://xmlpull.org/v1/doc/features.html#indent-output";

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static void serialize(android.os.VibrationEffect vibrationEffect, java.io.Writer writer) throws android.os.vibrator.persistence.VibrationXmlSerializer.SerializationFailedException, java.io.IOException {
        serialize(vibrationEffect, writer, 0);
    }

    public static void serialize(android.os.VibrationEffect vibrationEffect, java.io.Writer writer, int i) throws android.os.vibrator.persistence.VibrationXmlSerializer.SerializationFailedException, java.io.IOException {
        com.android.internal.vibrator.persistence.XmlSerializedVibration<android.os.VibrationEffect> serializedVibration = toSerializedVibration(vibrationEffect, i);
        com.android.modules.utils.TypedXmlSerializer newFastSerializer = android.util.Xml.newFastSerializer();
        newFastSerializer.setFeature(XML_FEATURE_INDENT_OUTPUT, (i & 2) != 0);
        newFastSerializer.setOutput(writer);
        newFastSerializer.startDocument(XML_ENCODING, false);
        serializedVibration.write(newFastSerializer);
        newFastSerializer.endDocument();
    }

    private static com.android.internal.vibrator.persistence.XmlSerializedVibration<android.os.VibrationEffect> toSerializedVibration(android.os.VibrationEffect vibrationEffect, int i) throws android.os.vibrator.persistence.VibrationXmlSerializer.SerializationFailedException {
        try {
            com.android.internal.vibrator.persistence.XmlSerializedVibration<android.os.VibrationEffect> serialize = com.android.internal.vibrator.persistence.VibrationEffectXmlSerializer.serialize(vibrationEffect, (i & 1) == 0 ? 0 : 1);
            com.android.internal.vibrator.persistence.XmlValidator.checkSerializedVibration(serialize, vibrationEffect);
            return serialize;
        } catch (com.android.internal.vibrator.persistence.XmlSerializerException e) {
            throw new android.os.vibrator.persistence.VibrationXmlSerializer.SerializationFailedException(vibrationEffect, e);
        }
    }

    public static final class SerializationFailedException extends java.lang.RuntimeException {
        SerializationFailedException(android.os.VibrationEffect vibrationEffect, java.lang.Throwable th) {
            super("Serialization failed for vibration effect " + vibrationEffect, th);
        }
    }

    private VibrationXmlSerializer() {
    }
}
