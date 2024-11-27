package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class UserRestrictionPolicyKey extends android.app.admin.PolicyKey {
    public static final android.os.Parcelable.Creator<android.app.admin.UserRestrictionPolicyKey> CREATOR = new android.os.Parcelable.Creator<android.app.admin.UserRestrictionPolicyKey>() { // from class: android.app.admin.UserRestrictionPolicyKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.UserRestrictionPolicyKey createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.UserRestrictionPolicyKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.UserRestrictionPolicyKey[] newArray(int i) {
            return new android.app.admin.UserRestrictionPolicyKey[i];
        }
    };
    private final java.lang.String mRestriction;

    public UserRestrictionPolicyKey(java.lang.String str, java.lang.String str2) {
        super(str);
        if (android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled()) {
            android.app.admin.PolicySizeVerifier.enforceMaxStringLength(str2, "restriction");
        }
        this.mRestriction = (java.lang.String) java.util.Objects.requireNonNull(str2);
    }

    private UserRestrictionPolicyKey(android.os.Parcel parcel) {
        this(parcel.readString(), parcel.readString());
    }

    public java.lang.String getRestriction() {
        return this.mRestriction;
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
        parcel.writeString(this.mRestriction);
    }

    public java.lang.String toString() {
        return "UserRestrictionPolicyKey " + getIdentifier();
    }
}
