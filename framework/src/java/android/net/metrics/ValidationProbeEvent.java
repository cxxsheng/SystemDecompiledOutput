package android.net.metrics;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public final class ValidationProbeEvent implements android.net.metrics.IpConnectivityLog.Event {
    public static final android.os.Parcelable.Creator<android.net.metrics.ValidationProbeEvent> CREATOR = new android.os.Parcelable.Creator<android.net.metrics.ValidationProbeEvent>() { // from class: android.net.metrics.ValidationProbeEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.ValidationProbeEvent createFromParcel(android.os.Parcel parcel) {
            return new android.net.metrics.ValidationProbeEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.ValidationProbeEvent[] newArray(int i) {
            return new android.net.metrics.ValidationProbeEvent[i];
        }
    };
    public static final int DNS_FAILURE = 0;
    public static final int DNS_SUCCESS = 1;
    private static final int FIRST_VALIDATION = 256;
    public static final int PROBE_DNS = 0;
    public static final int PROBE_FALLBACK = 4;
    public static final int PROBE_HTTP = 1;
    public static final int PROBE_HTTPS = 2;
    public static final int PROBE_PAC = 3;
    public static final int PROBE_PRIVDNS = 5;
    private static final int REVALIDATION = 512;
    public final long durationMs;
    public final int probeType;
    public final int returnCode;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ReturnCode {
    }

    private ValidationProbeEvent(long j, int i, int i2) {
        this.durationMs = j;
        this.probeType = i;
        this.returnCode = i2;
    }

    private ValidationProbeEvent(android.os.Parcel parcel) {
        this.durationMs = parcel.readLong();
        this.probeType = parcel.readInt();
        this.returnCode = parcel.readInt();
    }

    public static final class Builder {
        private long mDurationMs;
        private int mProbeType;
        private int mReturnCode;

        public android.net.metrics.ValidationProbeEvent.Builder setDurationMs(long j) {
            this.mDurationMs = j;
            return this;
        }

        public android.net.metrics.ValidationProbeEvent.Builder setProbeType(int i, boolean z) {
            this.mProbeType = android.net.metrics.ValidationProbeEvent.makeProbeType(i, z);
            return this;
        }

        public android.net.metrics.ValidationProbeEvent.Builder setReturnCode(int i) {
            this.mReturnCode = i;
            return this;
        }

        public android.net.metrics.ValidationProbeEvent build() {
            return new android.net.metrics.ValidationProbeEvent(this.mDurationMs, this.mProbeType, this.mReturnCode);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.durationMs);
        parcel.writeInt(this.probeType);
        parcel.writeInt(this.returnCode);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int makeProbeType(int i, boolean z) {
        return (i & 255) | (z ? 256 : 512);
    }

    public static java.lang.String getProbeName(int i) {
        return android.net.metrics.ValidationProbeEvent.Decoder.constants.get(i & 255, "PROBE_???");
    }

    private static java.lang.String getValidationStage(int i) {
        return android.net.metrics.ValidationProbeEvent.Decoder.constants.get(i & 65280, "UNKNOWN");
    }

    public java.lang.String toString() {
        return java.lang.String.format("ValidationProbeEvent(%s:%d %s, %dms)", getProbeName(this.probeType), java.lang.Integer.valueOf(this.returnCode), getValidationStage(this.probeType), java.lang.Long.valueOf(this.durationMs));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !obj.getClass().equals(android.net.metrics.ValidationProbeEvent.class)) {
            return false;
        }
        android.net.metrics.ValidationProbeEvent validationProbeEvent = (android.net.metrics.ValidationProbeEvent) obj;
        return this.durationMs == validationProbeEvent.durationMs && this.probeType == validationProbeEvent.probeType && this.returnCode == validationProbeEvent.returnCode;
    }

    static final class Decoder {
        static final android.util.SparseArray<java.lang.String> constants = com.android.internal.util.MessageUtils.findMessageNames(new java.lang.Class[]{android.net.metrics.ValidationProbeEvent.class}, new java.lang.String[]{"PROBE_", "FIRST_", "REVALIDATION"});

        Decoder() {
        }
    }
}
