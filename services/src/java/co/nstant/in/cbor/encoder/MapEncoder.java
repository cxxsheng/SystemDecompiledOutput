package co.nstant.in.cbor.encoder;

/* loaded from: classes.dex */
public class MapEncoder extends co.nstant.in.cbor.encoder.AbstractEncoder<co.nstant.in.cbor.model.Map> {
    public MapEncoder(co.nstant.in.cbor.CborEncoder cborEncoder, java.io.OutputStream outputStream) {
        super(cborEncoder, outputStream);
    }

    @Override // co.nstant.in.cbor.encoder.AbstractEncoder
    public void encode(co.nstant.in.cbor.model.Map map) throws co.nstant.in.cbor.CborException {
        java.util.Collection<co.nstant.in.cbor.model.DataItem> keys = map.getKeys();
        if (map.isChunked()) {
            encodeTypeChunked(co.nstant.in.cbor.model.MajorType.MAP);
        } else {
            encodeTypeAndLength(co.nstant.in.cbor.model.MajorType.MAP, keys.size());
        }
        if (keys.isEmpty()) {
            return;
        }
        if (map.isChunked()) {
            for (co.nstant.in.cbor.model.DataItem dataItem : keys) {
                this.encoder.encode(dataItem);
                this.encoder.encode(map.get(dataItem));
            }
            this.encoder.encode(co.nstant.in.cbor.model.Special.BREAK);
            return;
        }
        java.util.TreeMap treeMap = new java.util.TreeMap(new java.util.Comparator<byte[]>() { // from class: co.nstant.in.cbor.encoder.MapEncoder.1
            @Override // java.util.Comparator
            public int compare(byte[] bArr, byte[] bArr2) {
                if (bArr.length < bArr2.length) {
                    return -1;
                }
                if (bArr.length > bArr2.length) {
                    return 1;
                }
                for (int i = 0; i < bArr.length; i++) {
                    if (bArr[i] < bArr2[i]) {
                        return -1;
                    }
                    if (bArr[i] > bArr2[i]) {
                        return 1;
                    }
                }
                return 0;
            }
        });
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        co.nstant.in.cbor.CborEncoder cborEncoder = new co.nstant.in.cbor.CborEncoder(byteArrayOutputStream);
        for (co.nstant.in.cbor.model.DataItem dataItem2 : keys) {
            cborEncoder.encode(dataItem2);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.reset();
            cborEncoder.encode(map.get(dataItem2));
            byte[] byteArray2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.reset();
            treeMap.put(byteArray, byteArray2);
        }
        for (java.util.Map.Entry entry : treeMap.entrySet()) {
            write((byte[]) entry.getKey());
            write((byte[]) entry.getValue());
        }
    }
}
