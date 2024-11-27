package co.nstant.in.cbor.encoder;

/* loaded from: classes.dex */
public class ArrayEncoder extends co.nstant.in.cbor.encoder.AbstractEncoder<co.nstant.in.cbor.model.Array> {
    public ArrayEncoder(co.nstant.in.cbor.CborEncoder cborEncoder, java.io.OutputStream outputStream) {
        super(cborEncoder, outputStream);
    }

    @Override // co.nstant.in.cbor.encoder.AbstractEncoder
    public void encode(co.nstant.in.cbor.model.Array array) throws co.nstant.in.cbor.CborException {
        java.util.List<co.nstant.in.cbor.model.DataItem> dataItems = array.getDataItems();
        if (array.isChunked()) {
            encodeTypeChunked(co.nstant.in.cbor.model.MajorType.ARRAY);
        } else {
            encodeTypeAndLength(co.nstant.in.cbor.model.MajorType.ARRAY, dataItems.size());
        }
        java.util.Iterator<co.nstant.in.cbor.model.DataItem> it = dataItems.iterator();
        while (it.hasNext()) {
            this.encoder.encode(it.next());
        }
    }
}
