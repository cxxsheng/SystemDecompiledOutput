package android.app.admin;

/* loaded from: classes.dex */
public final class StringPolicyValue extends android.app.admin.PolicyValue<java.lang.String> {
    public static final android.os.Parcelable.Creator<android.app.admin.StringPolicyValue> CREATOR = new android.os.Parcelable.Creator<android.app.admin.StringPolicyValue>() { // from class: android.app.admin.StringPolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.StringPolicyValue createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.StringPolicyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.StringPolicyValue[] newArray(int i) {
            return new android.app.admin.StringPolicyValue[i];
        }
    };

    public StringPolicyValue(java.lang.String str) {
        super(str);
        if (android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled()) {
            android.app.admin.PolicySizeVerifier.enforceMaxStringLength(str, "policyValue");
        }
    }

    private StringPolicyValue(android.os.Parcel parcel) {
        super(parcel.readString());
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(getValue(), ((android.app.admin.StringPolicyValue) obj).getValue());
    }

    public int hashCode() {
        return java.util.Objects.hash(getValue());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "StringPolicyValue { " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(getValue());
    }
}
