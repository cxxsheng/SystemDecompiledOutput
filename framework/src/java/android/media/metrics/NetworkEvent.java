package android.media.metrics;

/* loaded from: classes2.dex */
public final class NetworkEvent extends android.media.metrics.Event implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.metrics.NetworkEvent> CREATOR = new android.os.Parcelable.Creator<android.media.metrics.NetworkEvent>() { // from class: android.media.metrics.NetworkEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.metrics.NetworkEvent[] newArray(int i) {
            return new android.media.metrics.NetworkEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.metrics.NetworkEvent createFromParcel(android.os.Parcel parcel) {
            return new android.media.metrics.NetworkEvent(parcel);
        }
    };
    public static final int NETWORK_TYPE_2G = 4;
    public static final int NETWORK_TYPE_3G = 5;
    public static final int NETWORK_TYPE_4G = 6;
    public static final int NETWORK_TYPE_5G_NSA = 7;
    public static final int NETWORK_TYPE_5G_SA = 8;
    public static final int NETWORK_TYPE_ETHERNET = 3;
    public static final int NETWORK_TYPE_OFFLINE = 9;
    public static final int NETWORK_TYPE_OTHER = 1;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int NETWORK_TYPE_WIFI = 2;
    private final int mNetworkType;
    private final long mTimeSinceCreatedMillis;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NetworkType {
    }

    public static java.lang.String networkTypeToString(int i) {
        switch (i) {
            case 0:
                return "NETWORK_TYPE_UNKNOWN";
            case 1:
                return "NETWORK_TYPE_OTHER";
            case 2:
                return "NETWORK_TYPE_WIFI";
            case 3:
                return "NETWORK_TYPE_ETHERNET";
            case 4:
                return "NETWORK_TYPE_2G";
            case 5:
                return "NETWORK_TYPE_3G";
            case 6:
                return "NETWORK_TYPE_4G";
            case 7:
                return "NETWORK_TYPE_5G_NSA";
            case 8:
                return "NETWORK_TYPE_5G_SA";
            case 9:
                return "NETWORK_TYPE_OFFLINE";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    private NetworkEvent(int i, long j, android.os.Bundle bundle) {
        this.mNetworkType = i;
        this.mTimeSinceCreatedMillis = j;
        this.mMetricsBundle = bundle == null ? null : bundle.deepCopy();
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    @Override // android.media.metrics.Event
    public long getTimeSinceCreatedMillis() {
        return this.mTimeSinceCreatedMillis;
    }

    @Override // android.media.metrics.Event
    public android.os.Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }

    public java.lang.String toString() {
        return "NetworkEvent { networkType = " + this.mNetworkType + ", timeSinceCreatedMillis = " + this.mTimeSinceCreatedMillis + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.metrics.NetworkEvent networkEvent = (android.media.metrics.NetworkEvent) obj;
        if (this.mNetworkType == networkEvent.mNetworkType && this.mTimeSinceCreatedMillis == networkEvent.mTimeSinceCreatedMillis) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mNetworkType), java.lang.Long.valueOf(this.mTimeSinceCreatedMillis));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mNetworkType);
        parcel.writeLong(this.mTimeSinceCreatedMillis);
        parcel.writeBundle(this.mMetricsBundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private NetworkEvent(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        long readLong = parcel.readLong();
        android.os.Bundle readBundle = parcel.readBundle();
        this.mNetworkType = readInt;
        this.mTimeSinceCreatedMillis = readLong;
        this.mMetricsBundle = readBundle;
    }

    public static final class Builder {
        private int mNetworkType = 0;
        private long mTimeSinceCreatedMillis = -1;
        private android.os.Bundle mMetricsBundle = new android.os.Bundle();

        public android.media.metrics.NetworkEvent.Builder setNetworkType(int i) {
            this.mNetworkType = i;
            return this;
        }

        public android.media.metrics.NetworkEvent.Builder setTimeSinceCreatedMillis(long j) {
            this.mTimeSinceCreatedMillis = j;
            return this;
        }

        public android.media.metrics.NetworkEvent.Builder setMetricsBundle(android.os.Bundle bundle) {
            this.mMetricsBundle = bundle;
            return this;
        }

        public android.media.metrics.NetworkEvent build() {
            return new android.media.metrics.NetworkEvent(this.mNetworkType, this.mTimeSinceCreatedMillis, this.mMetricsBundle);
        }
    }
}
