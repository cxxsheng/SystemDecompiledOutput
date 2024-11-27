package co.nstant.in.cbor.model;

/* loaded from: classes.dex */
public class ByteString extends co.nstant.in.cbor.model.ChunkableDataItem {
    private final byte[] bytes;

    @Override // co.nstant.in.cbor.model.ChunkableDataItem
    public /* bridge */ /* synthetic */ boolean isChunked() {
        return super.isChunked();
    }

    @Override // co.nstant.in.cbor.model.ChunkableDataItem
    public /* bridge */ /* synthetic */ co.nstant.in.cbor.model.ChunkableDataItem setChunked(boolean z) {
        return super.setChunked(z);
    }

    public ByteString(byte[] bArr) {
        super(co.nstant.in.cbor.model.MajorType.BYTE_STRING);
        if (bArr == null) {
            this.bytes = null;
        } else {
            this.bytes = bArr;
        }
    }

    public byte[] getBytes() {
        if (this.bytes == null) {
            return null;
        }
        return this.bytes;
    }

    @Override // co.nstant.in.cbor.model.ChunkableDataItem, co.nstant.in.cbor.model.DataItem
    public boolean equals(java.lang.Object obj) {
        if (obj instanceof co.nstant.in.cbor.model.ByteString) {
            return super.equals(obj) && java.util.Arrays.equals(this.bytes, ((co.nstant.in.cbor.model.ByteString) obj).bytes);
        }
        return false;
    }

    @Override // co.nstant.in.cbor.model.ChunkableDataItem, co.nstant.in.cbor.model.DataItem
    public int hashCode() {
        return super.hashCode() ^ java.util.Arrays.hashCode(this.bytes);
    }
}
