package co.nstant.in.cbor.encoder;

/* loaded from: classes.dex */
public class SinglePrecisionFloatEncoder extends co.nstant.in.cbor.encoder.AbstractEncoder<co.nstant.in.cbor.model.SinglePrecisionFloat> {
    public SinglePrecisionFloatEncoder(co.nstant.in.cbor.CborEncoder cborEncoder, java.io.OutputStream outputStream) {
        super(cborEncoder, outputStream);
    }

    @Override // co.nstant.in.cbor.encoder.AbstractEncoder
    public void encode(co.nstant.in.cbor.model.SinglePrecisionFloat singlePrecisionFloat) throws co.nstant.in.cbor.CborException {
        write(250);
        int floatToRawIntBits = java.lang.Float.floatToRawIntBits(singlePrecisionFloat.getValue());
        write((floatToRawIntBits >> 24) & 255);
        write((floatToRawIntBits >> 16) & 255);
        write((floatToRawIntBits >> 8) & 255);
        write((floatToRawIntBits >> 0) & 255);
    }
}
