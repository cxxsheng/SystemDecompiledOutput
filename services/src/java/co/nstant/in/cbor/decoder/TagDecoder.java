package co.nstant.in.cbor.decoder;

/* loaded from: classes.dex */
public class TagDecoder extends co.nstant.in.cbor.decoder.AbstractDecoder<co.nstant.in.cbor.model.Tag> {
    public TagDecoder(co.nstant.in.cbor.CborDecoder cborDecoder, java.io.InputStream inputStream) {
        super(cborDecoder, inputStream);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // co.nstant.in.cbor.decoder.AbstractDecoder
    public co.nstant.in.cbor.model.Tag decode(int i) throws co.nstant.in.cbor.CborException {
        return new co.nstant.in.cbor.model.Tag(getLength(i));
    }
}
