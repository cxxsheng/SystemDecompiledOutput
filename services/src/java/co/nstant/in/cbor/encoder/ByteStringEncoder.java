package co.nstant.in.cbor.encoder;

/* loaded from: classes.dex */
public class ByteStringEncoder extends co.nstant.in.cbor.encoder.AbstractEncoder<co.nstant.in.cbor.model.ByteString> {
    public ByteStringEncoder(co.nstant.in.cbor.CborEncoder cborEncoder, java.io.OutputStream outputStream) {
        super(cborEncoder, outputStream);
    }

    @Override // co.nstant.in.cbor.encoder.AbstractEncoder
    public void encode(co.nstant.in.cbor.model.ByteString byteString) throws co.nstant.in.cbor.CborException {
        byte[] bytes = byteString.getBytes();
        if (byteString.isChunked()) {
            encodeTypeChunked(co.nstant.in.cbor.model.MajorType.BYTE_STRING);
            if (bytes != null) {
                encode(new co.nstant.in.cbor.model.ByteString(bytes));
                return;
            }
            return;
        }
        if (bytes == null) {
            this.encoder.encode(co.nstant.in.cbor.model.SimpleValue.NULL);
        } else {
            encodeTypeAndLength(co.nstant.in.cbor.model.MajorType.BYTE_STRING, bytes.length);
            write(bytes);
        }
    }
}
