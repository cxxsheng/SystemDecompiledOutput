package co.nstant.in.cbor.model;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ChunkableDataItem extends co.nstant.in.cbor.model.DataItem {
    private boolean chunked;

    protected ChunkableDataItem(co.nstant.in.cbor.model.MajorType majorType) {
        super(majorType);
        this.chunked = false;
    }

    public boolean isChunked() {
        return this.chunked;
    }

    public co.nstant.in.cbor.model.ChunkableDataItem setChunked(boolean z) {
        this.chunked = z;
        return this;
    }

    @Override // co.nstant.in.cbor.model.DataItem
    public boolean equals(java.lang.Object obj) {
        if (obj instanceof co.nstant.in.cbor.model.ChunkableDataItem) {
            return super.equals(obj) && this.chunked == ((co.nstant.in.cbor.model.ChunkableDataItem) obj).chunked;
        }
        return false;
    }

    @Override // co.nstant.in.cbor.model.DataItem
    public int hashCode() {
        return super.hashCode() ^ java.util.Objects.hashCode(java.lang.Boolean.valueOf(this.chunked));
    }
}
