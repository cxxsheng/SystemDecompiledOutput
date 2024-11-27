package co.nstant.in.cbor.encoder;

/* loaded from: classes.dex */
public class UnicodeStringEncoder extends co.nstant.in.cbor.encoder.AbstractEncoder<co.nstant.in.cbor.model.UnicodeString> {
    public UnicodeStringEncoder(co.nstant.in.cbor.CborEncoder cborEncoder, java.io.OutputStream outputStream) {
        super(cborEncoder, outputStream);
    }

    @Override // co.nstant.in.cbor.encoder.AbstractEncoder
    public void encode(co.nstant.in.cbor.model.UnicodeString unicodeString) throws co.nstant.in.cbor.CborException {
        java.lang.String string = unicodeString.getString();
        if (unicodeString.isChunked()) {
            encodeTypeChunked(co.nstant.in.cbor.model.MajorType.UNICODE_STRING);
            if (string != null) {
                encode(new co.nstant.in.cbor.model.UnicodeString(string));
                return;
            }
            return;
        }
        if (string == null) {
            this.encoder.encode(co.nstant.in.cbor.model.SimpleValue.NULL);
            return;
        }
        byte[] bytes = string.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        encodeTypeAndLength(co.nstant.in.cbor.model.MajorType.UNICODE_STRING, bytes.length);
        write(bytes);
    }
}
