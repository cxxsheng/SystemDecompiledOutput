package co.nstant.in.cbor.encoder;

/* loaded from: classes.dex */
public class DoublePrecisionFloatEncoder extends co.nstant.in.cbor.encoder.AbstractEncoder<co.nstant.in.cbor.model.DoublePrecisionFloat> {
    public DoublePrecisionFloatEncoder(co.nstant.in.cbor.CborEncoder cborEncoder, java.io.OutputStream outputStream) {
        super(cborEncoder, outputStream);
    }

    @Override // co.nstant.in.cbor.encoder.AbstractEncoder
    public void encode(co.nstant.in.cbor.model.DoublePrecisionFloat doublePrecisionFloat) throws co.nstant.in.cbor.CborException {
        write(251);
        long doubleToRawLongBits = java.lang.Double.doubleToRawLongBits(doublePrecisionFloat.getValue());
        write((int) ((doubleToRawLongBits >> 56) & 255));
        write((int) ((doubleToRawLongBits >> 48) & 255));
        write((int) ((doubleToRawLongBits >> 40) & 255));
        write((int) ((doubleToRawLongBits >> 32) & 255));
        write((int) ((doubleToRawLongBits >> 24) & 255));
        write((int) ((doubleToRawLongBits >> 16) & 255));
        write((int) ((doubleToRawLongBits >> 8) & 255));
        write((int) ((doubleToRawLongBits >> 0) & 255));
    }
}
