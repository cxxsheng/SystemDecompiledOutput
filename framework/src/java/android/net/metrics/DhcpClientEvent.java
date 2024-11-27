package android.net.metrics;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public final class DhcpClientEvent implements android.net.metrics.IpConnectivityLog.Event {
    public static final android.os.Parcelable.Creator<android.net.metrics.DhcpClientEvent> CREATOR = new android.os.Parcelable.Creator<android.net.metrics.DhcpClientEvent>() { // from class: android.net.metrics.DhcpClientEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.DhcpClientEvent createFromParcel(android.os.Parcel parcel) {
            return new android.net.metrics.DhcpClientEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.DhcpClientEvent[] newArray(int i) {
            return new android.net.metrics.DhcpClientEvent[i];
        }
    };
    public final int durationMs;
    public final java.lang.String msg;

    private DhcpClientEvent(java.lang.String str, int i) {
        this.msg = str;
        this.durationMs = i;
    }

    private DhcpClientEvent(android.os.Parcel parcel) {
        this.msg = parcel.readString();
        this.durationMs = parcel.readInt();
    }

    public static final class Builder {
        private int mDurationMs;
        private java.lang.String mMsg;

        public android.net.metrics.DhcpClientEvent.Builder setMsg(java.lang.String str) {
            this.mMsg = str;
            return this;
        }

        public android.net.metrics.DhcpClientEvent.Builder setDurationMs(int i) {
            this.mDurationMs = i;
            return this;
        }

        public android.net.metrics.DhcpClientEvent build() {
            return new android.net.metrics.DhcpClientEvent(this.mMsg, this.mDurationMs);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.msg);
        parcel.writeInt(this.durationMs);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return java.lang.String.format("DhcpClientEvent(%s, %dms)", this.msg, java.lang.Integer.valueOf(this.durationMs));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !obj.getClass().equals(android.net.metrics.DhcpClientEvent.class)) {
            return false;
        }
        android.net.metrics.DhcpClientEvent dhcpClientEvent = (android.net.metrics.DhcpClientEvent) obj;
        return android.text.TextUtils.equals(this.msg, dhcpClientEvent.msg) && this.durationMs == dhcpClientEvent.durationMs;
    }
}
