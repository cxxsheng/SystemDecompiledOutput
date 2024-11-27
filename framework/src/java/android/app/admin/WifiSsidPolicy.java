package android.app.admin;

/* loaded from: classes.dex */
public final class WifiSsidPolicy implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.WifiSsidPolicy> CREATOR = new android.os.Parcelable.Creator<android.app.admin.WifiSsidPolicy>() { // from class: android.app.admin.WifiSsidPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.WifiSsidPolicy createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.WifiSsidPolicy(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.WifiSsidPolicy[] newArray(int i) {
            return new android.app.admin.WifiSsidPolicy[i];
        }
    };
    public static final int WIFI_SSID_POLICY_TYPE_ALLOWLIST = 0;
    public static final int WIFI_SSID_POLICY_TYPE_DENYLIST = 1;
    private int mPolicyType;
    private android.util.ArraySet<android.net.wifi.WifiSsid> mSsids;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WifiSsidPolicyType {
    }

    public WifiSsidPolicy(int i, java.util.Set<android.net.wifi.WifiSsid> set) {
        if (set.isEmpty()) {
            throw new java.lang.IllegalArgumentException("SSID list cannot be empty");
        }
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("Invalid policy type");
        }
        this.mPolicyType = i;
        this.mSsids = new android.util.ArraySet<>(set);
    }

    private WifiSsidPolicy(android.os.Parcel parcel) {
        this.mPolicyType = parcel.readInt();
        this.mSsids = parcel.readArraySet(null);
    }

    public java.util.Set<android.net.wifi.WifiSsid> getSsids() {
        return this.mSsids;
    }

    public int getPolicyType() {
        return this.mPolicyType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPolicyType);
        parcel.writeArraySet(this.mSsids);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.app.admin.WifiSsidPolicy)) {
            return false;
        }
        android.app.admin.WifiSsidPolicy wifiSsidPolicy = (android.app.admin.WifiSsidPolicy) obj;
        return this.mPolicyType == wifiSsidPolicy.mPolicyType && java.util.Objects.equals(this.mSsids, wifiSsidPolicy.mSsids);
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mPolicyType), this.mSsids);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
