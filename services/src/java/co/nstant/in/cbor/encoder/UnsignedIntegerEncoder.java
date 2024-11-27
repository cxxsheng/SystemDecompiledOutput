package co.nstant.in.cbor.encoder;

/* loaded from: classes.dex */
public class UnsignedIntegerEncoder extends co.nstant.in.cbor.encoder.AbstractEncoder<co.nstant.in.cbor.model.UnsignedInteger> {
    public UnsignedIntegerEncoder(co.nstant.in.cbor.CborEncoder cborEncoder, java.io.OutputStream outputStream) {
        super(cborEncoder, outputStream);
    }

    @Override // co.nstant.in.cbor.encoder.AbstractEncoder
    public void encode(co.nstant.in.cbor.model.UnsignedInteger unsignedInteger) throws co.nstant.in.cbor.CborException {
        encodeTypeAndLength(co.nstant.in.cbor.model.MajorType.UNSIGNED_INTEGER, unsignedInteger.getValue());
    }
}
