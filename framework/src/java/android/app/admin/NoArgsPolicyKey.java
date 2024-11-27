package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class NoArgsPolicyKey extends android.app.admin.PolicyKey {
    public static final android.os.Parcelable.Creator<android.app.admin.NoArgsPolicyKey> CREATOR = new android.os.Parcelable.Creator<android.app.admin.NoArgsPolicyKey>() { // from class: android.app.admin.NoArgsPolicyKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.NoArgsPolicyKey createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.NoArgsPolicyKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.NoArgsPolicyKey[] newArray(int i) {
            return new android.app.admin.NoArgsPolicyKey[i];
        }
    };

    public NoArgsPolicyKey(java.lang.String str) {
        super(str);
    }

    private NoArgsPolicyKey(android.os.Parcel parcel) {
        this(parcel.readString());
    }

    @Override // android.app.admin.PolicyKey
    public void writeToBundle(android.os.Bundle bundle) {
        bundle.putString(android.app.admin.PolicyUpdateReceiver.EXTRA_POLICY_KEY, getIdentifier());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(getIdentifier());
    }

    public java.lang.String toString() {
        return "DefaultPolicyKey " + getIdentifier();
    }
}
