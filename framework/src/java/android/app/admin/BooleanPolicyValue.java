package android.app.admin;

/* loaded from: classes.dex */
public final class BooleanPolicyValue extends android.app.admin.PolicyValue<java.lang.Boolean> {
    public static final android.os.Parcelable.Creator<android.app.admin.BooleanPolicyValue> CREATOR = new android.os.Parcelable.Creator<android.app.admin.BooleanPolicyValue>() { // from class: android.app.admin.BooleanPolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.BooleanPolicyValue createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.BooleanPolicyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.BooleanPolicyValue[] newArray(int i) {
            return new android.app.admin.BooleanPolicyValue[i];
        }
    };

    public BooleanPolicyValue(boolean z) {
        super(java.lang.Boolean.valueOf(z));
    }

    private BooleanPolicyValue(android.os.Parcel parcel) {
        this(parcel.readBoolean());
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(getValue(), ((android.app.admin.BooleanPolicyValue) obj).getValue());
    }

    public int hashCode() {
        return java.util.Objects.hash(getValue());
    }

    public java.lang.String toString() {
        return "BooleanPolicyValue { mValue= " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(getValue().booleanValue());
    }
}
