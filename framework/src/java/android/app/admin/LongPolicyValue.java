package android.app.admin;

/* loaded from: classes.dex */
public final class LongPolicyValue extends android.app.admin.PolicyValue<java.lang.Long> {
    public static final android.os.Parcelable.Creator<android.app.admin.LongPolicyValue> CREATOR = new android.os.Parcelable.Creator<android.app.admin.LongPolicyValue>() { // from class: android.app.admin.LongPolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.LongPolicyValue createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.LongPolicyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.LongPolicyValue[] newArray(int i) {
            return new android.app.admin.LongPolicyValue[i];
        }
    };

    public LongPolicyValue(long j) {
        super(java.lang.Long.valueOf(j));
    }

    private LongPolicyValue(android.os.Parcel parcel) {
        this(parcel.readLong());
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(getValue(), ((android.app.admin.LongPolicyValue) obj).getValue());
    }

    public int hashCode() {
        return java.util.Objects.hash(getValue());
    }

    public java.lang.String toString() {
        return "LongPolicyValue { mValue= " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(getValue().longValue());
    }
}
