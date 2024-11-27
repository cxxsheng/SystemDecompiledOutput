package co.nstant.in.cbor.decoder;

/* loaded from: classes.dex */
public class DoublePrecisionFloatDecoder extends co.nstant.in.cbor.decoder.AbstractDecoder<co.nstant.in.cbor.model.DoublePrecisionFloat> {
    public DoublePrecisionFloatDecoder(co.nstant.in.cbor.CborDecoder cborDecoder, java.io.InputStream inputStream) {
        super(cborDecoder, inputStream);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // co.nstant.in.cbor.decoder.AbstractDecoder
    public co.nstant.in.cbor.model.DoublePrecisionFloat decode(int i) throws co.nstant.in.cbor.CborException {
        return new co.nstant.in.cbor.model.DoublePrecisionFloat(java.lang.Double.longBitsToDouble((((((((((((((((nextSymbol() & 255) | 0) << 8) | (nextSymbol() & 255)) << 8) | (nextSymbol() & 255)) << 8) | (nextSymbol() & 255)) << 8) | (nextSymbol() & 255)) << 8) | (nextSymbol() & 255)) << 8) | (nextSymbol() & 255)) << 8) | (nextSymbol() & 255)));
    }
}
