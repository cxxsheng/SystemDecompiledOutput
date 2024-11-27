package android.app.admin;

/* loaded from: classes.dex */
public final class ComponentNamePolicyValue extends android.app.admin.PolicyValue<android.content.ComponentName> {
    public static final android.os.Parcelable.Creator<android.app.admin.ComponentNamePolicyValue> CREATOR = new android.os.Parcelable.Creator<android.app.admin.ComponentNamePolicyValue>() { // from class: android.app.admin.ComponentNamePolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.ComponentNamePolicyValue createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.ComponentNamePolicyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.ComponentNamePolicyValue[] newArray(int i) {
            return new android.app.admin.ComponentNamePolicyValue[i];
        }
    };

    public ComponentNamePolicyValue(android.content.ComponentName componentName) {
        super(componentName);
        if (android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled()) {
            android.app.admin.PolicySizeVerifier.enforceMaxComponentNameLength(componentName);
        }
    }

    private ComponentNamePolicyValue(android.os.Parcel parcel) {
        this((android.content.ComponentName) parcel.readParcelable(android.content.ComponentName.class.getClassLoader()));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(getValue(), ((android.app.admin.ComponentNamePolicyValue) obj).getValue());
    }

    public int hashCode() {
        return java.util.Objects.hash(getValue());
    }

    public java.lang.String toString() {
        return "ComponentNamePolicyValue { mValue= " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(getValue(), i);
    }
}
