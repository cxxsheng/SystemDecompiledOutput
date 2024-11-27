package android.net.vcn;

/* loaded from: classes2.dex */
public final class VcnUnderlyingNetworkPolicy implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.vcn.VcnUnderlyingNetworkPolicy> CREATOR = new android.os.Parcelable.Creator<android.net.vcn.VcnUnderlyingNetworkPolicy>() { // from class: android.net.vcn.VcnUnderlyingNetworkPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.vcn.VcnUnderlyingNetworkPolicy createFromParcel(android.os.Parcel parcel) {
            return new android.net.vcn.VcnUnderlyingNetworkPolicy((android.net.vcn.VcnNetworkPolicyResult) parcel.readParcelable(null, android.net.vcn.VcnNetworkPolicyResult.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.vcn.VcnUnderlyingNetworkPolicy[] newArray(int i) {
            return new android.net.vcn.VcnUnderlyingNetworkPolicy[i];
        }
    };
    private final android.net.vcn.VcnNetworkPolicyResult mVcnNetworkPolicyResult;

    public VcnUnderlyingNetworkPolicy(boolean z, android.net.NetworkCapabilities networkCapabilities) {
        java.util.Objects.requireNonNull(networkCapabilities, "mergedNetworkCapabilities must be nonnull");
        this.mVcnNetworkPolicyResult = new android.net.vcn.VcnNetworkPolicyResult(z, networkCapabilities);
    }

    private VcnUnderlyingNetworkPolicy(android.net.vcn.VcnNetworkPolicyResult vcnNetworkPolicyResult) {
        this.mVcnNetworkPolicyResult = (android.net.vcn.VcnNetworkPolicyResult) java.util.Objects.requireNonNull(vcnNetworkPolicyResult, "vcnNetworkPolicyResult");
    }

    public boolean isTeardownRequested() {
        return this.mVcnNetworkPolicyResult.isTeardownRequested();
    }

    public android.net.NetworkCapabilities getMergedNetworkCapabilities() {
        return this.mVcnNetworkPolicyResult.getNetworkCapabilities();
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mVcnNetworkPolicyResult);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof android.net.vcn.VcnUnderlyingNetworkPolicy) {
            return this.mVcnNetworkPolicyResult.equals(((android.net.vcn.VcnUnderlyingNetworkPolicy) obj).mVcnNetworkPolicyResult);
        }
        return false;
    }

    public java.lang.String toString() {
        return this.mVcnNetworkPolicyResult.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mVcnNetworkPolicyResult, i);
    }
}
