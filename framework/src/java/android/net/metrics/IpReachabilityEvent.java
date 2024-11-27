package android.net.metrics;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public final class IpReachabilityEvent implements android.net.metrics.IpConnectivityLog.Event {
    public static final android.os.Parcelable.Creator<android.net.metrics.IpReachabilityEvent> CREATOR = new android.os.Parcelable.Creator<android.net.metrics.IpReachabilityEvent>() { // from class: android.net.metrics.IpReachabilityEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.IpReachabilityEvent createFromParcel(android.os.Parcel parcel) {
            return new android.net.metrics.IpReachabilityEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.IpReachabilityEvent[] newArray(int i) {
            return new android.net.metrics.IpReachabilityEvent[i];
        }
    };
    public static final int NUD_FAILED = 512;
    public static final int NUD_FAILED_ORGANIC = 1024;
    public static final int PROBE = 256;
    public static final int PROVISIONING_LOST = 768;
    public static final int PROVISIONING_LOST_ORGANIC = 1280;
    public final int eventType;

    public IpReachabilityEvent(int i) {
        this.eventType = i;
    }

    private IpReachabilityEvent(android.os.Parcel parcel) {
        this.eventType = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.eventType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return java.lang.String.format("IpReachabilityEvent(%s:%02x)", android.net.metrics.IpReachabilityEvent.Decoder.constants.get(this.eventType & 65280), java.lang.Integer.valueOf(this.eventType & 255));
    }

    public boolean equals(java.lang.Object obj) {
        return obj != null && obj.getClass().equals(android.net.metrics.IpReachabilityEvent.class) && this.eventType == ((android.net.metrics.IpReachabilityEvent) obj).eventType;
    }

    static final class Decoder {
        static final android.util.SparseArray<java.lang.String> constants = com.android.internal.util.MessageUtils.findMessageNames(new java.lang.Class[]{android.net.metrics.IpReachabilityEvent.class}, new java.lang.String[]{"PROBE", "PROVISIONING_", "NUD_"});

        Decoder() {
        }
    }
}
