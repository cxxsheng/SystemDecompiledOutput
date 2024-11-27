package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class PolicyState<V> implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.PolicyState<?>> CREATOR = new android.os.Parcelable.Creator<android.app.admin.PolicyState<?>>() { // from class: android.app.admin.PolicyState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.PolicyState<?> createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.PolicyState<>(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.PolicyState<?>[] newArray(int i) {
            return new android.app.admin.PolicyState[i];
        }
    };
    private android.app.admin.PolicyValue<V> mCurrentResolvedPolicy;
    private final java.util.LinkedHashMap<android.app.admin.EnforcingAdmin, android.app.admin.PolicyValue<V>> mPoliciesSetByAdmins;
    private android.app.admin.ResolutionMechanism<V> mResolutionMechanism;

    public PolicyState(java.util.LinkedHashMap<android.app.admin.EnforcingAdmin, android.app.admin.PolicyValue<V>> linkedHashMap, android.app.admin.PolicyValue<V> policyValue, android.app.admin.ResolutionMechanism<V> resolutionMechanism) {
        this.mPoliciesSetByAdmins = new java.util.LinkedHashMap<>();
        java.util.Objects.requireNonNull(linkedHashMap);
        java.util.Objects.requireNonNull(resolutionMechanism);
        this.mPoliciesSetByAdmins.putAll(linkedHashMap);
        this.mCurrentResolvedPolicy = policyValue;
        this.mResolutionMechanism = resolutionMechanism;
    }

    private PolicyState(android.os.Parcel parcel) {
        this.mPoliciesSetByAdmins = new java.util.LinkedHashMap<>();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mPoliciesSetByAdmins.put((android.app.admin.EnforcingAdmin) parcel.readParcelable(android.app.admin.EnforcingAdmin.class.getClassLoader()), (android.app.admin.PolicyValue) parcel.readParcelable(android.app.admin.PolicyValue.class.getClassLoader()));
        }
        this.mCurrentResolvedPolicy = (android.app.admin.PolicyValue) parcel.readParcelable(android.app.admin.PolicyValue.class.getClassLoader());
        this.mResolutionMechanism = (android.app.admin.ResolutionMechanism) parcel.readParcelable(android.app.admin.ResolutionMechanism.class.getClassLoader());
    }

    public java.util.LinkedHashMap<android.app.admin.EnforcingAdmin, V> getPoliciesSetByAdmins() {
        java.util.LinkedHashMap<android.app.admin.EnforcingAdmin, V> linkedHashMap = new java.util.LinkedHashMap<>();
        for (android.app.admin.EnforcingAdmin enforcingAdmin : this.mPoliciesSetByAdmins.keySet()) {
            linkedHashMap.put(enforcingAdmin, this.mPoliciesSetByAdmins.get(enforcingAdmin).getValue());
        }
        return linkedHashMap;
    }

    public V getCurrentResolvedPolicy() {
        if (this.mCurrentResolvedPolicy == null) {
            return null;
        }
        return this.mCurrentResolvedPolicy.getValue();
    }

    public android.app.admin.ResolutionMechanism<V> getResolutionMechanism() {
        return this.mResolutionMechanism;
    }

    public java.lang.String toString() {
        return "PolicyState { mPoliciesSetByAdmins= " + this.mPoliciesSetByAdmins + ", mCurrentResolvedPolicy= " + this.mCurrentResolvedPolicy + ", mResolutionMechanism= " + this.mResolutionMechanism + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPoliciesSetByAdmins.size());
        for (android.app.admin.EnforcingAdmin enforcingAdmin : this.mPoliciesSetByAdmins.keySet()) {
            parcel.writeParcelable(enforcingAdmin, i);
            parcel.writeParcelable(this.mPoliciesSetByAdmins.get(enforcingAdmin), i);
        }
        parcel.writeParcelable(this.mCurrentResolvedPolicy, i);
        parcel.writeParcelable(this.mResolutionMechanism, i);
    }
}
