package android.net.metrics;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public final class NetworkEvent implements android.net.metrics.IpConnectivityLog.Event {
    public static final android.os.Parcelable.Creator<android.net.metrics.NetworkEvent> CREATOR = new android.os.Parcelable.Creator<android.net.metrics.NetworkEvent>() { // from class: android.net.metrics.NetworkEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.NetworkEvent createFromParcel(android.os.Parcel parcel) {
            return new android.net.metrics.NetworkEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.NetworkEvent[] newArray(int i) {
            return new android.net.metrics.NetworkEvent[i];
        }
    };
    public static final int NETWORK_CAPTIVE_PORTAL_FOUND = 4;
    public static final int NETWORK_CONNECTED = 1;
    public static final int NETWORK_CONSECUTIVE_DNS_TIMEOUT_FOUND = 12;
    public static final int NETWORK_DISCONNECTED = 7;
    public static final int NETWORK_FIRST_VALIDATION_PORTAL_FOUND = 10;
    public static final int NETWORK_FIRST_VALIDATION_SUCCESS = 8;
    public static final int NETWORK_LINGER = 5;
    public static final int NETWORK_PARTIAL_CONNECTIVITY = 13;
    public static final int NETWORK_REVALIDATION_PORTAL_FOUND = 11;
    public static final int NETWORK_REVALIDATION_SUCCESS = 9;
    public static final int NETWORK_UNLINGER = 6;
    public static final int NETWORK_VALIDATED = 2;
    public static final int NETWORK_VALIDATION_FAILED = 3;
    public final long durationMs;
    public final int eventType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    public NetworkEvent(int i, long j) {
        this.eventType = i;
        this.durationMs = j;
    }

    public NetworkEvent(int i) {
        this(i, 0L);
    }

    private NetworkEvent(android.os.Parcel parcel) {
        this.eventType = parcel.readInt();
        this.durationMs = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.eventType);
        parcel.writeLong(this.durationMs);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return java.lang.String.format("NetworkEvent(%s, %dms)", android.net.metrics.NetworkEvent.Decoder.constants.get(this.eventType), java.lang.Long.valueOf(this.durationMs));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !obj.getClass().equals(android.net.metrics.NetworkEvent.class)) {
            return false;
        }
        android.net.metrics.NetworkEvent networkEvent = (android.net.metrics.NetworkEvent) obj;
        return this.eventType == networkEvent.eventType && this.durationMs == networkEvent.durationMs;
    }

    static final class Decoder {
        static final android.util.SparseArray<java.lang.String> constants = com.android.internal.util.MessageUtils.findMessageNames(new java.lang.Class[]{android.net.metrics.NetworkEvent.class}, new java.lang.String[]{"NETWORK_"});

        Decoder() {
        }
    }
}
