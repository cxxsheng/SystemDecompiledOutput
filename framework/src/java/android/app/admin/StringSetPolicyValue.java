package android.app.admin;

/* loaded from: classes.dex */
public final class StringSetPolicyValue extends android.app.admin.PolicyValue<java.util.Set<java.lang.String>> {
    public static final android.os.Parcelable.Creator<android.app.admin.StringSetPolicyValue> CREATOR = new android.os.Parcelable.Creator<android.app.admin.StringSetPolicyValue>() { // from class: android.app.admin.StringSetPolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.StringSetPolicyValue createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.StringSetPolicyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.StringSetPolicyValue[] newArray(int i) {
            return new android.app.admin.StringSetPolicyValue[i];
        }
    };

    public StringSetPolicyValue(java.util.Set<java.lang.String> set) {
        super(set);
        if (android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled()) {
            java.util.Iterator<java.lang.String> it = set.iterator();
            while (it.hasNext()) {
                android.app.admin.PolicySizeVerifier.enforceMaxStringLength(it.next(), "policyValue");
            }
        }
    }

    public StringSetPolicyValue(android.os.Parcel parcel) {
        this(readValues(parcel));
    }

    private static java.util.Set<java.lang.String> readValues(android.os.Parcel parcel) {
        java.util.HashSet hashSet = new java.util.HashSet();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            hashSet.add(parcel.readString());
        }
        return hashSet;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(getValue(), ((android.app.admin.StringSetPolicyValue) obj).getValue());
    }

    public int hashCode() {
        return java.util.Objects.hash(getValue());
    }

    public java.lang.String toString() {
        return "StringSetPolicyValue { " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(getValue().size());
        java.util.Iterator<java.lang.String> it = getValue().iterator();
        while (it.hasNext()) {
            parcel.writeString(it.next());
        }
    }
}
