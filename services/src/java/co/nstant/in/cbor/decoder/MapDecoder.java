package co.nstant.in.cbor.decoder;

/* loaded from: classes.dex */
public class MapDecoder extends co.nstant.in.cbor.decoder.AbstractDecoder<co.nstant.in.cbor.model.Map> {
    public MapDecoder(co.nstant.in.cbor.CborDecoder cborDecoder, java.io.InputStream inputStream) {
        super(cborDecoder, inputStream);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // co.nstant.in.cbor.decoder.AbstractDecoder
    public co.nstant.in.cbor.model.Map decode(int i) throws co.nstant.in.cbor.CborException {
        long length = getLength(i);
        if (length == -1) {
            return decodeInfinitiveLength();
        }
        return decodeFixedLength(length);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x004d, code lost:
    
        throw new co.nstant.in.cbor.CborException("Unexpected end of stream");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private co.nstant.in.cbor.model.Map decodeInfinitiveLength() throws co.nstant.in.cbor.CborException {
        co.nstant.in.cbor.model.Map map = new co.nstant.in.cbor.model.Map();
        map.setChunked(true);
        if (this.decoder.isAutoDecodeInfinitiveMaps()) {
            while (true) {
                co.nstant.in.cbor.model.DataItem decodeNext = this.decoder.decodeNext();
                if (co.nstant.in.cbor.model.Special.BREAK.equals(decodeNext)) {
                    break;
                }
                co.nstant.in.cbor.model.DataItem decodeNext2 = this.decoder.decodeNext();
                if (decodeNext == null || decodeNext2 == null) {
                    break;
                }
                if (this.decoder.isRejectDuplicateKeys() && map.get(decodeNext) != null) {
                    throw new co.nstant.in.cbor.CborException("Duplicate key found in map");
                }
                map.put(decodeNext, decodeNext2);
            }
        }
        return map;
    }

    private co.nstant.in.cbor.model.Map decodeFixedLength(long j) throws co.nstant.in.cbor.CborException {
        co.nstant.in.cbor.model.Map map = new co.nstant.in.cbor.model.Map((int) j);
        for (long j2 = 0; j2 < j; j2++) {
            co.nstant.in.cbor.model.DataItem decodeNext = this.decoder.decodeNext();
            co.nstant.in.cbor.model.DataItem decodeNext2 = this.decoder.decodeNext();
            if (decodeNext == null || decodeNext2 == null) {
                throw new co.nstant.in.cbor.CborException("Unexpected end of stream");
            }
            if (this.decoder.isRejectDuplicateKeys() && map.get(decodeNext) != null) {
                throw new co.nstant.in.cbor.CborException("Duplicate key found in map");
            }
            map.put(decodeNext, decodeNext2);
        }
        return map;
    }
}
