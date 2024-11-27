package co.nstant.in.cbor.model;

/* loaded from: classes.dex */
public class DataItem {
    private final co.nstant.in.cbor.model.MajorType majorType;
    private co.nstant.in.cbor.model.Tag tag;

    protected DataItem(co.nstant.in.cbor.model.MajorType majorType) {
        this.majorType = majorType;
        java.util.Objects.requireNonNull(majorType, "majorType is null");
    }

    public co.nstant.in.cbor.model.MajorType getMajorType() {
        return this.majorType;
    }

    public void setTag(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("tag number must be 0 or greater");
        }
        this.tag = new co.nstant.in.cbor.model.Tag(i);
    }

    public void setTag(co.nstant.in.cbor.model.Tag tag) {
        java.util.Objects.requireNonNull(tag, "tag is null");
        this.tag = tag;
    }

    public void removeTag() {
        this.tag = null;
    }

    public co.nstant.in.cbor.model.Tag getTag() {
        return this.tag;
    }

    public boolean hasTag() {
        return this.tag != null;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof co.nstant.in.cbor.model.DataItem)) {
            return false;
        }
        co.nstant.in.cbor.model.DataItem dataItem = (co.nstant.in.cbor.model.DataItem) obj;
        return this.tag != null ? this.tag.equals(dataItem.tag) && this.majorType == dataItem.majorType : dataItem.tag == null && this.majorType == dataItem.majorType;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.majorType, this.tag);
    }

    protected void assertTrue(boolean z, java.lang.String str) {
        if (!z) {
            throw new java.lang.IllegalArgumentException(str);
        }
    }
}
