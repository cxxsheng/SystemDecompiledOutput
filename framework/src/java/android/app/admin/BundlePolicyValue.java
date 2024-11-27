package android.app.admin;

/* loaded from: classes.dex */
public final class BundlePolicyValue extends android.app.admin.PolicyValue<android.os.Bundle> {
    public static final android.os.Parcelable.Creator<android.app.admin.BundlePolicyValue> CREATOR = new android.os.Parcelable.Creator<android.app.admin.BundlePolicyValue>() { // from class: android.app.admin.BundlePolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.BundlePolicyValue createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.BundlePolicyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.BundlePolicyValue[] newArray(int i) {
            return new android.app.admin.BundlePolicyValue[i];
        }
    };

    public BundlePolicyValue(android.os.Bundle bundle) {
        super(bundle);
        if (android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled()) {
            android.app.admin.PolicySizeVerifier.enforceMaxParcelableFieldsLength(bundle);
        }
    }

    private BundlePolicyValue(android.os.Parcel parcel) {
        this(parcel.readBundle());
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(getValue(), ((android.app.admin.BundlePolicyValue) obj).getValue());
    }

    public int hashCode() {
        return java.util.Objects.hash(getValue());
    }

    public java.lang.String toString() {
        return "BundlePolicyValue { mValue= " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBundle(getValue());
    }
}
