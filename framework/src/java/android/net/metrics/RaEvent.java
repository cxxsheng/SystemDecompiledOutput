package android.net.metrics;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public final class RaEvent implements android.net.metrics.IpConnectivityLog.Event {
    public static final android.os.Parcelable.Creator<android.net.metrics.RaEvent> CREATOR = new android.os.Parcelable.Creator<android.net.metrics.RaEvent>() { // from class: android.net.metrics.RaEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.RaEvent createFromParcel(android.os.Parcel parcel) {
            return new android.net.metrics.RaEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.RaEvent[] newArray(int i) {
            return new android.net.metrics.RaEvent[i];
        }
    };
    private static final long NO_LIFETIME = -1;
    public final long dnsslLifetime;
    public final long prefixPreferredLifetime;
    public final long prefixValidLifetime;
    public final long rdnssLifetime;
    public final long routeInfoLifetime;
    public final long routerLifetime;

    public RaEvent(long j, long j2, long j3, long j4, long j5, long j6) {
        this.routerLifetime = j;
        this.prefixValidLifetime = j2;
        this.prefixPreferredLifetime = j3;
        this.routeInfoLifetime = j4;
        this.rdnssLifetime = j5;
        this.dnsslLifetime = j6;
    }

    private RaEvent(android.os.Parcel parcel) {
        this.routerLifetime = parcel.readLong();
        this.prefixValidLifetime = parcel.readLong();
        this.prefixPreferredLifetime = parcel.readLong();
        this.routeInfoLifetime = parcel.readLong();
        this.rdnssLifetime = parcel.readLong();
        this.dnsslLifetime = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.routerLifetime);
        parcel.writeLong(this.prefixValidLifetime);
        parcel.writeLong(this.prefixPreferredLifetime);
        parcel.writeLong(this.routeInfoLifetime);
        parcel.writeLong(this.rdnssLifetime);
        parcel.writeLong(this.dnsslLifetime);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "RaEvent(lifetimes: " + java.lang.String.format("router=%ds, ", java.lang.Long.valueOf(this.routerLifetime)) + java.lang.String.format("prefix_valid=%ds, ", java.lang.Long.valueOf(this.prefixValidLifetime)) + java.lang.String.format("prefix_preferred=%ds, ", java.lang.Long.valueOf(this.prefixPreferredLifetime)) + java.lang.String.format("route_info=%ds, ", java.lang.Long.valueOf(this.routeInfoLifetime)) + java.lang.String.format("rdnss=%ds, ", java.lang.Long.valueOf(this.rdnssLifetime)) + java.lang.String.format("dnssl=%ds)", java.lang.Long.valueOf(this.dnsslLifetime));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !obj.getClass().equals(android.net.metrics.RaEvent.class)) {
            return false;
        }
        android.net.metrics.RaEvent raEvent = (android.net.metrics.RaEvent) obj;
        return this.routerLifetime == raEvent.routerLifetime && this.prefixValidLifetime == raEvent.prefixValidLifetime && this.prefixPreferredLifetime == raEvent.prefixPreferredLifetime && this.routeInfoLifetime == raEvent.routeInfoLifetime && this.rdnssLifetime == raEvent.rdnssLifetime && this.dnsslLifetime == raEvent.dnsslLifetime;
    }

    public static final class Builder {
        long routerLifetime = -1;
        long prefixValidLifetime = -1;
        long prefixPreferredLifetime = -1;
        long routeInfoLifetime = -1;
        long rdnssLifetime = -1;
        long dnsslLifetime = -1;

        public android.net.metrics.RaEvent build() {
            return new android.net.metrics.RaEvent(this.routerLifetime, this.prefixValidLifetime, this.prefixPreferredLifetime, this.routeInfoLifetime, this.rdnssLifetime, this.dnsslLifetime);
        }

        public android.net.metrics.RaEvent.Builder updateRouterLifetime(long j) {
            this.routerLifetime = updateLifetime(this.routerLifetime, j);
            return this;
        }

        public android.net.metrics.RaEvent.Builder updatePrefixValidLifetime(long j) {
            this.prefixValidLifetime = updateLifetime(this.prefixValidLifetime, j);
            return this;
        }

        public android.net.metrics.RaEvent.Builder updatePrefixPreferredLifetime(long j) {
            this.prefixPreferredLifetime = updateLifetime(this.prefixPreferredLifetime, j);
            return this;
        }

        public android.net.metrics.RaEvent.Builder updateRouteInfoLifetime(long j) {
            this.routeInfoLifetime = updateLifetime(this.routeInfoLifetime, j);
            return this;
        }

        public android.net.metrics.RaEvent.Builder updateRdnssLifetime(long j) {
            this.rdnssLifetime = updateLifetime(this.rdnssLifetime, j);
            return this;
        }

        public android.net.metrics.RaEvent.Builder updateDnsslLifetime(long j) {
            this.dnsslLifetime = updateLifetime(this.dnsslLifetime, j);
            return this;
        }

        private long updateLifetime(long j, long j2) {
            if (j == -1) {
                return j2;
            }
            return java.lang.Math.min(j, j2);
        }
    }
}
