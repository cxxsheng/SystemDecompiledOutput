package co.nstant.in.cbor.decoder;

/* loaded from: classes.dex */
public class ArrayDecoder extends co.nstant.in.cbor.decoder.AbstractDecoder<co.nstant.in.cbor.model.Array> {
    public ArrayDecoder(co.nstant.in.cbor.CborDecoder cborDecoder, java.io.InputStream inputStream) {
        super(cborDecoder, inputStream);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // co.nstant.in.cbor.decoder.AbstractDecoder
    public co.nstant.in.cbor.model.Array decode(int i) throws co.nstant.in.cbor.CborException {
        long length = getLength(i);
        if (length == -1) {
            return decodeInfinitiveLength();
        }
        return decodeFixedLength(length);
    }

    private co.nstant.in.cbor.model.Array decodeInfinitiveLength() throws co.nstant.in.cbor.CborException {
        co.nstant.in.cbor.model.Array array = new co.nstant.in.cbor.model.Array();
        array.setChunked(true);
        if (this.decoder.isAutoDecodeInfinitiveArrays()) {
            while (true) {
                co.nstant.in.cbor.model.DataItem decodeNext = this.decoder.decodeNext();
                if (decodeNext == null) {
                    throw new co.nstant.in.cbor.CborException("Unexpected end of stream");
                }
                if (co.nstant.in.cbor.model.Special.BREAK.equals(decodeNext)) {
                    array.add(co.nstant.in.cbor.model.Special.BREAK);
                    break;
                }
                array.add(decodeNext);
            }
        }
        return array;
    }

    private co.nstant.in.cbor.model.Array decodeFixedLength(long j) throws co.nstant.in.cbor.CborException {
        co.nstant.in.cbor.model.Array array = new co.nstant.in.cbor.model.Array();
        for (long j2 = 0; j2 < j; j2++) {
            co.nstant.in.cbor.model.DataItem decodeNext = this.decoder.decodeNext();
            if (decodeNext == null) {
                throw new co.nstant.in.cbor.CborException("Unexpected end of stream");
            }
            array.add(decodeNext);
        }
        return array;
    }
}
