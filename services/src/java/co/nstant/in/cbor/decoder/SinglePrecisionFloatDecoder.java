package co.nstant.in.cbor.decoder;

/* loaded from: classes.dex */
public class SinglePrecisionFloatDecoder extends co.nstant.in.cbor.decoder.AbstractDecoder<co.nstant.in.cbor.model.SinglePrecisionFloat> {
    public SinglePrecisionFloatDecoder(co.nstant.in.cbor.CborDecoder cborDecoder, java.io.InputStream inputStream) {
        super(cborDecoder, inputStream);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // co.nstant.in.cbor.decoder.AbstractDecoder
    public co.nstant.in.cbor.model.SinglePrecisionFloat decode(int i) throws co.nstant.in.cbor.CborException {
        return new co.nstant.in.cbor.model.SinglePrecisionFloat(java.lang.Float.intBitsToFloat((((((((nextSymbol() & 255) | 0) << 8) | (nextSymbol() & 255)) << 8) | (nextSymbol() & 255)) << 8) | (nextSymbol() & 255)));
    }
}
