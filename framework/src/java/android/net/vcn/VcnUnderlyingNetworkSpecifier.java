package android.net.vcn;

/* loaded from: classes2.dex */
public final class VcnUnderlyingNetworkSpecifier extends android.net.NetworkSpecifier implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.vcn.VcnUnderlyingNetworkSpecifier> CREATOR = new android.os.Parcelable.Creator<android.net.vcn.VcnUnderlyingNetworkSpecifier>() { // from class: android.net.vcn.VcnUnderlyingNetworkSpecifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.vcn.VcnUnderlyingNetworkSpecifier createFromParcel(android.os.Parcel parcel) {
            return new android.net.vcn.VcnUnderlyingNetworkSpecifier(parcel.createIntArray());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.vcn.VcnUnderlyingNetworkSpecifier[] newArray(int i) {
            return new android.net.vcn.VcnUnderlyingNetworkSpecifier[i];
        }
    };
    private final int[] mSubIds;

    public VcnUnderlyingNetworkSpecifier(int[] iArr) {
        this.mSubIds = (int[]) java.util.Objects.requireNonNull(iArr, "subIds were null");
    }

    public int[] getSubIds() {
        return this.mSubIds;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeIntArray(this.mSubIds);
    }

    public int hashCode() {
        return java.util.Arrays.hashCode(this.mSubIds);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.net.vcn.VcnUnderlyingNetworkSpecifier)) {
            return false;
        }
        return java.util.Arrays.equals(this.mSubIds, ((android.net.vcn.VcnUnderlyingNetworkSpecifier) obj).mSubIds);
    }

    public java.lang.String toString() {
        return "VcnUnderlyingNetworkSpecifier [mSubIds = " + java.util.Arrays.toString(this.mSubIds) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.net.NetworkSpecifier
    public boolean canBeSatisfiedBy(android.net.NetworkSpecifier networkSpecifier) {
        if (networkSpecifier instanceof android.net.TelephonyNetworkSpecifier) {
            return com.android.internal.util.ArrayUtils.contains(this.mSubIds, ((android.net.TelephonyNetworkSpecifier) networkSpecifier).getSubscriptionId());
        }
        return equals(networkSpecifier);
    }
}
