package co.nstant.in.cbor.model;

/* loaded from: classes.dex */
public class UnicodeString extends co.nstant.in.cbor.model.ChunkableDataItem {
    private final java.lang.String string;

    @Override // co.nstant.in.cbor.model.ChunkableDataItem
    public /* bridge */ /* synthetic */ boolean isChunked() {
        return super.isChunked();
    }

    @Override // co.nstant.in.cbor.model.ChunkableDataItem
    public /* bridge */ /* synthetic */ co.nstant.in.cbor.model.ChunkableDataItem setChunked(boolean z) {
        return super.setChunked(z);
    }

    public UnicodeString(java.lang.String str) {
        super(co.nstant.in.cbor.model.MajorType.UNICODE_STRING);
        this.string = str;
    }

    public java.lang.String toString() {
        if (this.string == null) {
            return "null";
        }
        return this.string;
    }

    public java.lang.String getString() {
        return this.string;
    }

    @Override // co.nstant.in.cbor.model.ChunkableDataItem, co.nstant.in.cbor.model.DataItem
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof co.nstant.in.cbor.model.UnicodeString) || !super.equals(obj)) {
            return false;
        }
        co.nstant.in.cbor.model.UnicodeString unicodeString = (co.nstant.in.cbor.model.UnicodeString) obj;
        if (this.string == null) {
            return unicodeString.string == null;
        }
        return this.string.equals(unicodeString.string);
    }

    @Override // co.nstant.in.cbor.model.ChunkableDataItem, co.nstant.in.cbor.model.DataItem
    public int hashCode() {
        if (this.string == null) {
            return 0;
        }
        return super.hashCode() + this.string.hashCode();
    }
}
