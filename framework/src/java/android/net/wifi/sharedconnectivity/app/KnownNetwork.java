package android.net.wifi.sharedconnectivity.app;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class KnownNetwork implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.wifi.sharedconnectivity.app.KnownNetwork> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.sharedconnectivity.app.KnownNetwork>() { // from class: android.net.wifi.sharedconnectivity.app.KnownNetwork.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.sharedconnectivity.app.KnownNetwork createFromParcel(android.os.Parcel parcel) {
            return android.net.wifi.sharedconnectivity.app.KnownNetwork.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.sharedconnectivity.app.KnownNetwork[] newArray(int i) {
            return new android.net.wifi.sharedconnectivity.app.KnownNetwork[i];
        }
    };
    public static final int NETWORK_SOURCE_CLOUD_SELF = 2;
    public static final int NETWORK_SOURCE_NEARBY_SELF = 1;
    public static final int NETWORK_SOURCE_UNKNOWN = 0;
    private final android.os.Bundle mExtras;
    private final android.net.wifi.sharedconnectivity.app.NetworkProviderInfo mNetworkProviderInfo;
    private final int mNetworkSource;
    private final android.util.ArraySet<java.lang.Integer> mSecurityTypes;
    private final java.lang.String mSsid;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NetworkSource {
    }

    public static final class Builder {
        private android.net.wifi.sharedconnectivity.app.NetworkProviderInfo mNetworkProviderInfo;
        private java.lang.String mSsid;
        private int mNetworkSource = -1;
        private final android.util.ArraySet<java.lang.Integer> mSecurityTypes = new android.util.ArraySet<>();
        private android.os.Bundle mExtras = android.os.Bundle.EMPTY;

        public android.net.wifi.sharedconnectivity.app.KnownNetwork.Builder setNetworkSource(int i) {
            this.mNetworkSource = i;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.KnownNetwork.Builder setSsid(java.lang.String str) {
            this.mSsid = str;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.KnownNetwork.Builder addSecurityType(int i) {
            this.mSecurityTypes.add(java.lang.Integer.valueOf(i));
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.KnownNetwork.Builder setNetworkProviderInfo(android.net.wifi.sharedconnectivity.app.NetworkProviderInfo networkProviderInfo) {
            this.mNetworkProviderInfo = networkProviderInfo;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.KnownNetwork.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.KnownNetwork build() {
            return new android.net.wifi.sharedconnectivity.app.KnownNetwork(this.mNetworkSource, this.mSsid, this.mSecurityTypes, this.mNetworkProviderInfo, this.mExtras);
        }
    }

    private static void validate(int i, java.lang.String str, java.util.Set<java.lang.Integer> set, android.net.wifi.sharedconnectivity.app.NetworkProviderInfo networkProviderInfo) {
        if (i != 0 && i != 2 && i != 1) {
            throw new java.lang.IllegalArgumentException("Illegal network source");
        }
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("SSID must be set");
        }
        if (set.isEmpty()) {
            throw new java.lang.IllegalArgumentException("SecurityTypes must be set");
        }
        if (i == 1 && networkProviderInfo == null) {
            throw new java.lang.IllegalArgumentException("Device info must be provided when network source is NETWORK_SOURCE_NEARBY_SELF");
        }
    }

    private KnownNetwork(int i, java.lang.String str, android.util.ArraySet<java.lang.Integer> arraySet, android.net.wifi.sharedconnectivity.app.NetworkProviderInfo networkProviderInfo, android.os.Bundle bundle) {
        validate(i, str, arraySet, networkProviderInfo);
        this.mNetworkSource = i;
        this.mSsid = str;
        this.mSecurityTypes = new android.util.ArraySet<>((android.util.ArraySet) arraySet);
        this.mNetworkProviderInfo = networkProviderInfo;
        this.mExtras = bundle;
    }

    public int getNetworkSource() {
        return this.mNetworkSource;
    }

    public java.lang.String getSsid() {
        return this.mSsid;
    }

    public java.util.Set<java.lang.Integer> getSecurityTypes() {
        return this.mSecurityTypes;
    }

    public android.net.wifi.sharedconnectivity.app.NetworkProviderInfo getNetworkProviderInfo() {
        return this.mNetworkProviderInfo;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.wifi.sharedconnectivity.app.KnownNetwork)) {
            return false;
        }
        android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork = (android.net.wifi.sharedconnectivity.app.KnownNetwork) obj;
        return this.mNetworkSource == knownNetwork.getNetworkSource() && java.util.Objects.equals(this.mSsid, knownNetwork.getSsid()) && java.util.Objects.equals(this.mSecurityTypes, knownNetwork.getSecurityTypes()) && java.util.Objects.equals(this.mNetworkProviderInfo, knownNetwork.getNetworkProviderInfo());
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mNetworkSource), this.mSsid, this.mSecurityTypes, this.mNetworkProviderInfo);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mNetworkSource);
        parcel.writeString(this.mSsid);
        parcel.writeArraySet(this.mSecurityTypes);
        if (this.mNetworkProviderInfo != null) {
            parcel.writeBoolean(true);
            this.mNetworkProviderInfo.writeToParcel(parcel, i);
        } else {
            parcel.writeBoolean(false);
        }
        parcel.writeBundle(this.mExtras);
    }

    public static android.net.wifi.sharedconnectivity.app.KnownNetwork readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        java.lang.String readString = parcel.readString();
        android.util.ArraySet<? extends java.lang.Object> readArraySet = parcel.readArraySet(null);
        if (parcel.readBoolean()) {
            return new android.net.wifi.sharedconnectivity.app.KnownNetwork(readInt, readString, readArraySet, android.net.wifi.sharedconnectivity.app.NetworkProviderInfo.readFromParcel(parcel), parcel.readBundle());
        }
        return new android.net.wifi.sharedconnectivity.app.KnownNetwork(readInt, readString, readArraySet, null, parcel.readBundle());
    }

    public java.lang.String toString() {
        return "KnownNetwork[NetworkSource=" + this.mNetworkSource + ", ssid=" + this.mSsid + ", securityTypes=" + this.mSecurityTypes.toString() + ", networkProviderInfo=" + this.mNetworkProviderInfo.toString() + ", extras=" + this.mExtras.toString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
