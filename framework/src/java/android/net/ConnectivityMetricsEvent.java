package android.net;

/* loaded from: classes2.dex */
public final class ConnectivityMetricsEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.ConnectivityMetricsEvent> CREATOR = new android.os.Parcelable.Creator<android.net.ConnectivityMetricsEvent>() { // from class: android.net.ConnectivityMetricsEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.ConnectivityMetricsEvent createFromParcel(android.os.Parcel parcel) {
            return new android.net.ConnectivityMetricsEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.ConnectivityMetricsEvent[] newArray(int i) {
            return new android.net.ConnectivityMetricsEvent[i];
        }
    };
    public android.os.Parcelable data;
    public java.lang.String ifname;
    public int netId;
    public long timestamp;
    public long transports;

    public ConnectivityMetricsEvent() {
    }

    private ConnectivityMetricsEvent(android.os.Parcel parcel) {
        this.timestamp = parcel.readLong();
        this.transports = parcel.readLong();
        this.netId = parcel.readInt();
        this.ifname = parcel.readString();
        this.data = parcel.readParcelable(null);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.timestamp);
        parcel.writeLong(this.transports);
        parcel.writeInt(this.netId);
        parcel.writeString(this.ifname);
        parcel.writeParcelable(this.data, 0);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ConnectivityMetricsEvent(");
        sb.append(java.lang.String.format("%tT.%tL", java.lang.Long.valueOf(this.timestamp), java.lang.Long.valueOf(this.timestamp)));
        if (this.netId != 0) {
            sb.append(", ").append("netId=").append(this.netId);
        }
        if (this.ifname != null) {
            sb.append(", ").append(this.ifname);
        }
        sb.append(", transports=").append(java.util.BitSet.valueOf(new long[]{this.transports}));
        sb.append("): ").append(this.data.toString());
        return sb.toString();
    }
}
