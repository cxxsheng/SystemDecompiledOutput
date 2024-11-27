package co.nstant.in.cbor.model;

/* loaded from: classes.dex */
public abstract class Number extends co.nstant.in.cbor.model.DataItem {
    private final java.math.BigInteger value;

    protected Number(co.nstant.in.cbor.model.MajorType majorType, java.math.BigInteger bigInteger) {
        super(majorType);
        java.util.Objects.requireNonNull(bigInteger);
        this.value = bigInteger;
    }

    public java.math.BigInteger getValue() {
        return this.value;
    }

    @Override // co.nstant.in.cbor.model.DataItem
    public boolean equals(java.lang.Object obj) {
        if (obj instanceof co.nstant.in.cbor.model.Number) {
            return super.equals(obj) && this.value.equals(((co.nstant.in.cbor.model.Number) obj).value);
        }
        return false;
    }

    @Override // co.nstant.in.cbor.model.DataItem
    public int hashCode() {
        return super.hashCode() ^ this.value.hashCode();
    }

    public java.lang.String toString() {
        return this.value.toString();
    }
}
