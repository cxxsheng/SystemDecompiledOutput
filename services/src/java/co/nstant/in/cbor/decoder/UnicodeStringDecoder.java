package co.nstant.in.cbor.decoder;

/* loaded from: classes.dex */
public class UnicodeStringDecoder extends co.nstant.in.cbor.decoder.AbstractDecoder<co.nstant.in.cbor.model.UnicodeString> {
    public UnicodeStringDecoder(co.nstant.in.cbor.CborDecoder cborDecoder, java.io.InputStream inputStream) {
        super(cborDecoder, inputStream);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // co.nstant.in.cbor.decoder.AbstractDecoder
    public co.nstant.in.cbor.model.UnicodeString decode(int i) throws co.nstant.in.cbor.CborException {
        long length = getLength(i);
        if (length == -1) {
            if (this.decoder.isAutoDecodeInfinitiveUnicodeStrings()) {
                return decodeInfinitiveLength();
            }
            co.nstant.in.cbor.model.UnicodeString unicodeString = new co.nstant.in.cbor.model.UnicodeString(null);
            unicodeString.setChunked(true);
            return unicodeString;
        }
        return decodeFixedLength(length);
    }

    private co.nstant.in.cbor.model.UnicodeString decodeInfinitiveLength() throws co.nstant.in.cbor.CborException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        while (true) {
            co.nstant.in.cbor.model.DataItem decodeNext = this.decoder.decodeNext();
            if (decodeNext == null) {
                throw new co.nstant.in.cbor.CborException("Unexpected end of stream");
            }
            co.nstant.in.cbor.model.MajorType majorType = decodeNext.getMajorType();
            if (!co.nstant.in.cbor.model.Special.BREAK.equals(decodeNext)) {
                if (majorType == co.nstant.in.cbor.model.MajorType.UNICODE_STRING) {
                    byte[] bytes = ((co.nstant.in.cbor.model.UnicodeString) decodeNext).toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
                    byteArrayOutputStream.write(bytes, 0, bytes.length);
                } else {
                    throw new co.nstant.in.cbor.CborException("Unexpected major type " + majorType);
                }
            } else {
                return new co.nstant.in.cbor.model.UnicodeString(new java.lang.String(byteArrayOutputStream.toByteArray(), java.nio.charset.StandardCharsets.UTF_8));
            }
        }
    }

    private co.nstant.in.cbor.model.UnicodeString decodeFixedLength(long j) throws co.nstant.in.cbor.CborException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream((int) j);
        for (long j2 = 0; j2 < j; j2++) {
            byteArrayOutputStream.write(nextSymbol());
        }
        return new co.nstant.in.cbor.model.UnicodeString(new java.lang.String(byteArrayOutputStream.toByteArray(), java.nio.charset.StandardCharsets.UTF_8));
    }
}
