package android.content.integrity;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class Rule implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.integrity.Rule> CREATOR = new android.os.Parcelable.Creator<android.content.integrity.Rule>() { // from class: android.content.integrity.Rule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.integrity.Rule createFromParcel(android.os.Parcel parcel) {
            return new android.content.integrity.Rule(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.integrity.Rule[] newArray(int i) {
            return new android.content.integrity.Rule[i];
        }
    };
    public static final int DENY = 0;
    public static final int FORCE_ALLOW = 1;
    private final int mEffect;
    private final android.content.integrity.IntegrityFormula mFormula;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Effect {
    }

    public Rule(android.content.integrity.IntegrityFormula integrityFormula, int i) {
        com.android.internal.util.Preconditions.checkArgument(isValidEffect(i), "Unknown effect: %d", java.lang.Integer.valueOf(i));
        this.mFormula = (android.content.integrity.IntegrityFormula) java.util.Objects.requireNonNull(integrityFormula);
        this.mEffect = i;
    }

    Rule(android.os.Parcel parcel) {
        this.mFormula = android.content.integrity.IntegrityFormula.readFromParcel(parcel);
        this.mEffect = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.content.integrity.IntegrityFormula.writeToParcel(this.mFormula, parcel, i);
        parcel.writeInt(this.mEffect);
    }

    public android.content.integrity.IntegrityFormula getFormula() {
        return this.mFormula;
    }

    public int getEffect() {
        return this.mEffect;
    }

    public java.lang.String toString() {
        return java.lang.String.format("Rule: %s, %s", this.mFormula, effectToString(this.mEffect));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.content.integrity.Rule rule = (android.content.integrity.Rule) obj;
        if (this.mEffect == rule.mEffect && java.util.Objects.equals(this.mFormula, rule.mFormula)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mFormula, java.lang.Integer.valueOf(this.mEffect));
    }

    private static java.lang.String effectToString(int i) {
        switch (i) {
            case 0:
                return "DENY";
            case 1:
                return "FORCE_ALLOW";
            default:
                throw new java.lang.IllegalArgumentException("Unknown effect " + i);
        }
    }

    private static boolean isValidEffect(int i) {
        return i == 0 || i == 1;
    }
}
