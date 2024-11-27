package android.net.metrics;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public final class IpManagerEvent implements android.net.metrics.IpConnectivityLog.Event {
    public static final int COMPLETE_LIFECYCLE = 3;
    public static final android.os.Parcelable.Creator<android.net.metrics.IpManagerEvent> CREATOR = new android.os.Parcelable.Creator<android.net.metrics.IpManagerEvent>() { // from class: android.net.metrics.IpManagerEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.IpManagerEvent createFromParcel(android.os.Parcel parcel) {
            return new android.net.metrics.IpManagerEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.IpManagerEvent[] newArray(int i) {
            return new android.net.metrics.IpManagerEvent[i];
        }
    };
    public static final int ERROR_INTERFACE_NOT_FOUND = 8;
    public static final int ERROR_INVALID_PROVISIONING = 7;
    public static final int ERROR_STARTING_IPREACHABILITYMONITOR = 6;
    public static final int ERROR_STARTING_IPV4 = 4;
    public static final int ERROR_STARTING_IPV6 = 5;
    public static final int PROVISIONING_FAIL = 2;
    public static final int PROVISIONING_OK = 1;
    public final long durationMs;
    public final int eventType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    public IpManagerEvent(int i, long j) {
        this.eventType = i;
        this.durationMs = j;
    }

    private IpManagerEvent(android.os.Parcel parcel) {
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
        return java.lang.String.format("IpManagerEvent(%s, %dms)", android.net.metrics.IpManagerEvent.Decoder.constants.get(this.eventType), java.lang.Long.valueOf(this.durationMs));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !obj.getClass().equals(android.net.metrics.IpManagerEvent.class)) {
            return false;
        }
        android.net.metrics.IpManagerEvent ipManagerEvent = (android.net.metrics.IpManagerEvent) obj;
        return this.eventType == ipManagerEvent.eventType && this.durationMs == ipManagerEvent.durationMs;
    }

    static final class Decoder {
        static final android.util.SparseArray<java.lang.String> constants = com.android.internal.util.MessageUtils.findMessageNames(new java.lang.Class[]{android.net.metrics.IpManagerEvent.class}, new java.lang.String[]{"PROVISIONING_", "COMPLETE_", "ERROR_"});

        Decoder() {
        }
    }
}
