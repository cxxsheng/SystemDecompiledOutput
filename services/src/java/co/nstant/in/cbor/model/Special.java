package co.nstant.in.cbor.model;

/* loaded from: classes.dex */
public class Special extends co.nstant.in.cbor.model.DataItem {
    public static final co.nstant.in.cbor.model.Special BREAK = new co.nstant.in.cbor.model.Special(co.nstant.in.cbor.model.SpecialType.BREAK);
    private final co.nstant.in.cbor.model.SpecialType specialType;

    protected Special(co.nstant.in.cbor.model.SpecialType specialType) {
        super(co.nstant.in.cbor.model.MajorType.SPECIAL);
        java.util.Objects.requireNonNull(specialType);
        this.specialType = specialType;
    }

    public co.nstant.in.cbor.model.SpecialType getSpecialType() {
        return this.specialType;
    }

    @Override // co.nstant.in.cbor.model.DataItem
    public boolean equals(java.lang.Object obj) {
        if (obj instanceof co.nstant.in.cbor.model.Special) {
            return super.equals(obj) && this.specialType == ((co.nstant.in.cbor.model.Special) obj).specialType;
        }
        return false;
    }

    @Override // co.nstant.in.cbor.model.DataItem
    public int hashCode() {
        return super.hashCode() ^ java.util.Objects.hashCode(this.specialType);
    }

    public java.lang.String toString() {
        return this.specialType.name();
    }
}
