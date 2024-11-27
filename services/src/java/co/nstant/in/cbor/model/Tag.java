package co.nstant.in.cbor.model;

/* loaded from: classes.dex */
public class Tag extends co.nstant.in.cbor.model.DataItem {
    private final long value;

    public Tag(long j) {
        super(co.nstant.in.cbor.model.MajorType.TAG);
        this.value = j;
    }

    public long getValue() {
        return this.value;
    }

    @Override // co.nstant.in.cbor.model.DataItem
    public boolean equals(java.lang.Object obj) {
        if (obj instanceof co.nstant.in.cbor.model.Tag) {
            return super.equals(obj) && this.value == ((co.nstant.in.cbor.model.Tag) obj).value;
        }
        return false;
    }

    @Override // co.nstant.in.cbor.model.DataItem
    public int hashCode() {
        return super.hashCode() ^ java.util.Objects.hashCode(java.lang.Long.valueOf(this.value));
    }

    public java.lang.String toString() {
        return "Tag(" + this.value + ")";
    }
}
