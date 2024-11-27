package co.nstant.in.cbor.encoder;

/* loaded from: classes.dex */
public class NegativeIntegerEncoder extends co.nstant.in.cbor.encoder.AbstractEncoder<co.nstant.in.cbor.model.NegativeInteger> {
    private static final java.math.BigInteger MINUS_ONE = java.math.BigInteger.valueOf(-1);

    public NegativeIntegerEncoder(co.nstant.in.cbor.CborEncoder cborEncoder, java.io.OutputStream outputStream) {
        super(cborEncoder, outputStream);
    }

    @Override // co.nstant.in.cbor.encoder.AbstractEncoder
    public void encode(co.nstant.in.cbor.model.NegativeInteger negativeInteger) throws co.nstant.in.cbor.CborException {
        encodeTypeAndLength(co.nstant.in.cbor.model.MajorType.NEGATIVE_INTEGER, MINUS_ONE.subtract(negativeInteger.getValue()).abs());
    }
}
