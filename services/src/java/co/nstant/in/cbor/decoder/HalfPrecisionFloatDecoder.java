package co.nstant.in.cbor.decoder;

/* loaded from: classes.dex */
public class HalfPrecisionFloatDecoder extends co.nstant.in.cbor.decoder.AbstractDecoder<co.nstant.in.cbor.model.HalfPrecisionFloat> {
    public HalfPrecisionFloatDecoder(co.nstant.in.cbor.CborDecoder cborDecoder, java.io.InputStream inputStream) {
        super(cborDecoder, inputStream);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // co.nstant.in.cbor.decoder.AbstractDecoder
    public co.nstant.in.cbor.model.HalfPrecisionFloat decode(int i) throws co.nstant.in.cbor.CborException {
        return new co.nstant.in.cbor.model.HalfPrecisionFloat(toFloat((nextSymbol() << 8) | nextSymbol()));
    }

    private static float toFloat(int i) {
        int i2 = (32768 & i) >> 15;
        int i3 = (i & 31744) >> 10;
        int i4 = i & 1023;
        if (i3 == 0) {
            return (float) ((i2 != 0 ? -1 : 1) * java.lang.Math.pow(2.0d, -14.0d) * (i4 / java.lang.Math.pow(2.0d, 10.0d)));
        }
        if (i3 == 31) {
            if (i4 != 0) {
                return Float.NaN;
            }
            return (i2 != 0 ? -1 : 1) * Float.POSITIVE_INFINITY;
        }
        return (float) ((i2 != 0 ? -1 : 1) * java.lang.Math.pow(2.0d, i3 - 15) * ((i4 / java.lang.Math.pow(2.0d, 10.0d)) + 1.0d));
    }
}
