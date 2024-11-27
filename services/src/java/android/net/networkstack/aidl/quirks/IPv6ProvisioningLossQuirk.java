package android.net.networkstack.aidl.quirks;

/* loaded from: classes.dex */
public final class IPv6ProvisioningLossQuirk {
    public final int mDetectionCount;
    public final long mQuirkExpiry;

    public IPv6ProvisioningLossQuirk(int i, long j) {
        this.mDetectionCount = i;
        this.mQuirkExpiry = j;
    }

    public android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable toStableParcelable() {
        android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable iPv6ProvisioningLossQuirkParcelable = new android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable();
        iPv6ProvisioningLossQuirkParcelable.detectionCount = this.mDetectionCount;
        iPv6ProvisioningLossQuirkParcelable.quirkExpiry = this.mQuirkExpiry;
        return iPv6ProvisioningLossQuirkParcelable;
    }

    public static android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirk fromStableParcelable(@android.annotation.Nullable android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirkParcelable iPv6ProvisioningLossQuirkParcelable) {
        if (iPv6ProvisioningLossQuirkParcelable == null) {
            return null;
        }
        return new android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirk(iPv6ProvisioningLossQuirkParcelable.detectionCount, iPv6ProvisioningLossQuirkParcelable.quirkExpiry);
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (obj == null || android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirk.class != obj.getClass()) {
            return false;
        }
        android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirk iPv6ProvisioningLossQuirk = (android.net.networkstack.aidl.quirks.IPv6ProvisioningLossQuirk) obj;
        return this.mDetectionCount == iPv6ProvisioningLossQuirk.mDetectionCount && this.mQuirkExpiry == iPv6ProvisioningLossQuirk.mQuirkExpiry;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mDetectionCount), java.lang.Long.valueOf(this.mQuirkExpiry));
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("detection count: ");
        stringBuffer.append(this.mDetectionCount);
        stringBuffer.append(", quirk expiry: ");
        stringBuffer.append(this.mQuirkExpiry);
        return stringBuffer.toString();
    }
}
