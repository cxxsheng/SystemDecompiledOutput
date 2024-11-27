package android.net.wifi.sharedconnectivity.app;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class KnownNetworkConnectionStatus implements android.os.Parcelable {
    public static final int CONNECTION_STATUS_SAVED = 1;
    public static final int CONNECTION_STATUS_SAVE_FAILED = 2;
    public static final int CONNECTION_STATUS_UNKNOWN = 0;
    public static final android.os.Parcelable.Creator<android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus>() { // from class: android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus createFromParcel(android.os.Parcel parcel) {
            return android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus[] newArray(int i) {
            return new android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus[i];
        }
    };
    private final android.os.Bundle mExtras;
    private final android.net.wifi.sharedconnectivity.app.KnownNetwork mKnownNetwork;
    private final int mStatus;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConnectionStatus {
    }

    public static final class Builder {
        private android.os.Bundle mExtras = android.os.Bundle.EMPTY;
        private android.net.wifi.sharedconnectivity.app.KnownNetwork mKnownNetwork;
        private int mStatus;

        public android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus.Builder setStatus(int i) {
            this.mStatus = i;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus.Builder setKnownNetwork(android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork) {
            this.mKnownNetwork = knownNetwork;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus build() {
            return new android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus(this.mStatus, this.mKnownNetwork, this.mExtras);
        }
    }

    private static void validate(int i) {
        if (i != 0 && i != 1 && i != 2) {
            throw new java.lang.IllegalArgumentException("Illegal connection status");
        }
    }

    private KnownNetworkConnectionStatus(int i, android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork, android.os.Bundle bundle) {
        validate(i);
        this.mStatus = i;
        this.mKnownNetwork = knownNetwork;
        this.mExtras = bundle;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public android.net.wifi.sharedconnectivity.app.KnownNetwork getKnownNetwork() {
        return this.mKnownNetwork;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus)) {
            return false;
        }
        android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus knownNetworkConnectionStatus = (android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus) obj;
        return this.mStatus == knownNetworkConnectionStatus.getStatus() && java.util.Objects.equals(this.mKnownNetwork, knownNetworkConnectionStatus.getKnownNetwork());
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mStatus), this.mKnownNetwork);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
        this.mKnownNetwork.writeToParcel(parcel, i);
        parcel.writeBundle(this.mExtras);
    }

    public static android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus readFromParcel(android.os.Parcel parcel) {
        return new android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus(parcel.readInt(), android.net.wifi.sharedconnectivity.app.KnownNetwork.readFromParcel(parcel), parcel.readBundle());
    }

    public java.lang.String toString() {
        return "KnownNetworkConnectionStatus[status=" + this.mStatus + "known network=" + this.mKnownNetwork.toString() + "extras=" + this.mExtras.toString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
