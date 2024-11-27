package co.nstant.in.cbor.model;

/* loaded from: classes.dex */
public class AbstractFloat extends co.nstant.in.cbor.model.Special {
    private final float value;

    public AbstractFloat(co.nstant.in.cbor.model.SpecialType specialType, float f) {
        super(specialType);
        this.value = f;
    }

    public float getValue() {
        return this.value;
    }

    @Override // co.nstant.in.cbor.model.Special, co.nstant.in.cbor.model.DataItem
    public boolean equals(java.lang.Object obj) {
        if (obj instanceof co.nstant.in.cbor.model.AbstractFloat) {
            return super.equals(obj) && this.value == ((co.nstant.in.cbor.model.AbstractFloat) obj).value;
        }
        return false;
    }

    @Override // co.nstant.in.cbor.model.Special, co.nstant.in.cbor.model.DataItem
    public int hashCode() {
        return super.hashCode() ^ java.util.Objects.hashCode(java.lang.Float.valueOf(this.value));
    }
}
