package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class DevicePolicyState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.DevicePolicyState> CREATOR = new android.os.Parcelable.Creator<android.app.admin.DevicePolicyState>() { // from class: android.app.admin.DevicePolicyState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.DevicePolicyState createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.DevicePolicyState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.DevicePolicyState[] newArray(int i) {
            return new android.app.admin.DevicePolicyState[i];
        }
    };
    private final java.util.Map<android.os.UserHandle, java.util.Map<android.app.admin.PolicyKey, android.app.admin.PolicyState<?>>> mPolicies;

    public DevicePolicyState(java.util.Map<android.os.UserHandle, java.util.Map<android.app.admin.PolicyKey, android.app.admin.PolicyState<?>>> map) {
        this.mPolicies = (java.util.Map) java.util.Objects.requireNonNull(map);
    }

    private DevicePolicyState(android.os.Parcel parcel) {
        this.mPolicies = new java.util.HashMap();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            android.os.UserHandle of = android.os.UserHandle.of(parcel.readInt());
            this.mPolicies.put(of, new java.util.HashMap());
            int readInt2 = parcel.readInt();
            for (int i2 = 0; i2 < readInt2; i2++) {
                this.mPolicies.get(of).put((android.app.admin.PolicyKey) parcel.readParcelable(android.app.admin.PolicyKey.class.getClassLoader()), (android.app.admin.PolicyState) parcel.readParcelable(android.app.admin.PolicyState.class.getClassLoader()));
            }
        }
    }

    public java.util.Map<android.os.UserHandle, java.util.Map<android.app.admin.PolicyKey, android.app.admin.PolicyState<?>>> getPoliciesForAllUsers() {
        return this.mPolicies;
    }

    public java.util.Map<android.app.admin.PolicyKey, android.app.admin.PolicyState<?>> getPoliciesForUser(android.os.UserHandle userHandle) {
        return this.mPolicies.containsKey(userHandle) ? this.mPolicies.get(userHandle) : new java.util.HashMap();
    }

    public java.lang.String toString() {
        return "DevicePolicyState { mPolicies= " + this.mPolicies + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPolicies.size());
        for (android.os.UserHandle userHandle : this.mPolicies.keySet()) {
            parcel.writeInt(userHandle.getIdentifier());
            parcel.writeInt(this.mPolicies.get(userHandle).size());
            for (android.app.admin.PolicyKey policyKey : this.mPolicies.get(userHandle).keySet()) {
                parcel.writeParcelable(policyKey, i);
                parcel.writeParcelable(this.mPolicies.get(userHandle).get(policyKey), i);
            }
        }
    }
}
