package android.net.vcn;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class VcnNetworkPolicyResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.vcn.VcnNetworkPolicyResult> CREATOR = new android.os.Parcelable.Creator<android.net.vcn.VcnNetworkPolicyResult>() { // from class: android.net.vcn.VcnNetworkPolicyResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.vcn.VcnNetworkPolicyResult createFromParcel(android.os.Parcel parcel) {
            return new android.net.vcn.VcnNetworkPolicyResult(parcel.readBoolean(), (android.net.NetworkCapabilities) parcel.readParcelable(null, android.net.NetworkCapabilities.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.vcn.VcnNetworkPolicyResult[] newArray(int i) {
            return new android.net.vcn.VcnNetworkPolicyResult[i];
        }
    };
    private final boolean mIsTearDownRequested;
    private final android.net.NetworkCapabilities mNetworkCapabilities;

    public VcnNetworkPolicyResult(boolean z, android.net.NetworkCapabilities networkCapabilities) {
        java.util.Objects.requireNonNull(networkCapabilities, "networkCapabilities must be non-null");
        this.mIsTearDownRequested = z;
        this.mNetworkCapabilities = networkCapabilities;
    }

    public boolean isTeardownRequested() {
        return this.mIsTearDownRequested;
    }

    public android.net.NetworkCapabilities getNetworkCapabilities() {
        return this.mNetworkCapabilities;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mIsTearDownRequested), this.mNetworkCapabilities);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.net.vcn.VcnNetworkPolicyResult)) {
            return false;
        }
        android.net.vcn.VcnNetworkPolicyResult vcnNetworkPolicyResult = (android.net.vcn.VcnNetworkPolicyResult) obj;
        return this.mIsTearDownRequested == vcnNetworkPolicyResult.mIsTearDownRequested && this.mNetworkCapabilities.equals(vcnNetworkPolicyResult.mNetworkCapabilities);
    }

    public java.lang.String toString() {
        return "VcnNetworkPolicyResult { mIsTeardownRequested = " + this.mIsTearDownRequested + ", mNetworkCapabilities" + this.mNetworkCapabilities + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsTearDownRequested);
        parcel.writeParcelable(this.mNetworkCapabilities, i);
    }
}
