package co.nstant.in.cbor.model;

/* loaded from: classes.dex */
public class DoublePrecisionFloat extends co.nstant.in.cbor.model.Special {
    private final double value;

    public DoublePrecisionFloat(double d) {
        super(co.nstant.in.cbor.model.SpecialType.IEEE_754_DOUBLE_PRECISION_FLOAT);
        this.value = d;
    }

    public double getValue() {
        return this.value;
    }

    @Override // co.nstant.in.cbor.model.Special, co.nstant.in.cbor.model.DataItem
    public boolean equals(java.lang.Object obj) {
        if (obj instanceof co.nstant.in.cbor.model.DoublePrecisionFloat) {
            return super.equals(obj) && this.value == ((co.nstant.in.cbor.model.DoublePrecisionFloat) obj).value;
        }
        return false;
    }

    @Override // co.nstant.in.cbor.model.Special, co.nstant.in.cbor.model.DataItem
    public int hashCode() {
        return super.hashCode() ^ java.util.Objects.hashCode(java.lang.Double.valueOf(this.value));
    }

    @Override // co.nstant.in.cbor.model.Special
    public java.lang.String toString() {
        return java.lang.String.valueOf(this.value);
    }
}
