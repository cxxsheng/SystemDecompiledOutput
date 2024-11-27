package android.app.admin;

/* loaded from: classes.dex */
public final class IntegerPolicyValue extends android.app.admin.PolicyValue<java.lang.Integer> {
    public static final android.os.Parcelable.Creator<android.app.admin.IntegerPolicyValue> CREATOR = new android.os.Parcelable.Creator<android.app.admin.IntegerPolicyValue>() { // from class: android.app.admin.IntegerPolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.IntegerPolicyValue createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.IntegerPolicyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.IntegerPolicyValue[] newArray(int i) {
            return new android.app.admin.IntegerPolicyValue[i];
        }
    };

    public IntegerPolicyValue(int i) {
        super(java.lang.Integer.valueOf(i));
    }

    private IntegerPolicyValue(android.os.Parcel parcel) {
        this(parcel.readInt());
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(getValue(), ((android.app.admin.IntegerPolicyValue) obj).getValue());
    }

    public int hashCode() {
        return java.util.Objects.hash(getValue());
    }

    public java.lang.String toString() {
        return "IntegerPolicyValue { mValue= " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(getValue().intValue());
    }
}
