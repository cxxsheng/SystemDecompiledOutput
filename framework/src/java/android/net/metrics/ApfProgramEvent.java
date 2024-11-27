package android.net.metrics;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public final class ApfProgramEvent implements android.net.metrics.IpConnectivityLog.Event {
    public static final android.os.Parcelable.Creator<android.net.metrics.ApfProgramEvent> CREATOR = new android.os.Parcelable.Creator<android.net.metrics.ApfProgramEvent>() { // from class: android.net.metrics.ApfProgramEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.ApfProgramEvent createFromParcel(android.os.Parcel parcel) {
            return new android.net.metrics.ApfProgramEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.ApfProgramEvent[] newArray(int i) {
            return new android.net.metrics.ApfProgramEvent[i];
        }
    };
    public static final int FLAG_HAS_IPV4_ADDRESS = 1;
    public static final int FLAG_MULTICAST_FILTER_ON = 0;
    public final long actualLifetime;
    public final int currentRas;
    public final int filteredRas;
    public final int flags;
    public final long lifetime;
    public final int programLength;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    private ApfProgramEvent(long j, long j2, int i, int i2, int i3, int i4) {
        this.lifetime = j;
        this.actualLifetime = j2;
        this.filteredRas = i;
        this.currentRas = i2;
        this.programLength = i3;
        this.flags = i4;
    }

    private ApfProgramEvent(android.os.Parcel parcel) {
        this.lifetime = parcel.readLong();
        this.actualLifetime = parcel.readLong();
        this.filteredRas = parcel.readInt();
        this.currentRas = parcel.readInt();
        this.programLength = parcel.readInt();
        this.flags = parcel.readInt();
    }

    public static final class Builder {
        private long mActualLifetime;
        private int mCurrentRas;
        private int mFilteredRas;
        private int mFlags;
        private long mLifetime;
        private int mProgramLength;

        public android.net.metrics.ApfProgramEvent.Builder setLifetime(long j) {
            this.mLifetime = j;
            return this;
        }

        public android.net.metrics.ApfProgramEvent.Builder setActualLifetime(long j) {
            this.mActualLifetime = j;
            return this;
        }

        public android.net.metrics.ApfProgramEvent.Builder setFilteredRas(int i) {
            this.mFilteredRas = i;
            return this;
        }

        public android.net.metrics.ApfProgramEvent.Builder setCurrentRas(int i) {
            this.mCurrentRas = i;
            return this;
        }

        public android.net.metrics.ApfProgramEvent.Builder setProgramLength(int i) {
            this.mProgramLength = i;
            return this;
        }

        public android.net.metrics.ApfProgramEvent.Builder setFlags(boolean z, boolean z2) {
            this.mFlags = android.net.metrics.ApfProgramEvent.flagsFor(z, z2);
            return this;
        }

        public android.net.metrics.ApfProgramEvent build() {
            return new android.net.metrics.ApfProgramEvent(this.mLifetime, this.mActualLifetime, this.mFilteredRas, this.mCurrentRas, this.mProgramLength, this.mFlags);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.lifetime);
        parcel.writeLong(this.actualLifetime);
        parcel.writeInt(this.filteredRas);
        parcel.writeInt(this.currentRas);
        parcel.writeInt(this.programLength);
        parcel.writeInt(this.flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return java.lang.String.format("ApfProgramEvent(%d/%d RAs %dB %ds/%s %s)", java.lang.Integer.valueOf(this.filteredRas), java.lang.Integer.valueOf(this.currentRas), java.lang.Integer.valueOf(this.programLength), java.lang.Long.valueOf(this.actualLifetime), this.lifetime < Long.MAX_VALUE ? this.lifetime + android.app.blob.XmlTags.TAG_SESSION : "forever", namesOf(this.flags));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !obj.getClass().equals(android.net.metrics.ApfProgramEvent.class)) {
            return false;
        }
        android.net.metrics.ApfProgramEvent apfProgramEvent = (android.net.metrics.ApfProgramEvent) obj;
        return this.lifetime == apfProgramEvent.lifetime && this.actualLifetime == apfProgramEvent.actualLifetime && this.filteredRas == apfProgramEvent.filteredRas && this.currentRas == apfProgramEvent.currentRas && this.programLength == apfProgramEvent.programLength && this.flags == apfProgramEvent.flags;
    }

    public static int flagsFor(boolean z, boolean z2) {
        int i;
        if (!z) {
            i = 0;
        } else {
            i = 2;
        }
        if (z2) {
            return i | 1;
        }
        return i;
    }

    private static java.lang.String namesOf(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList(java.lang.Integer.bitCount(i));
        java.util.BitSet valueOf = java.util.BitSet.valueOf(new long[]{i & Integer.MAX_VALUE});
        for (int nextSetBit = valueOf.nextSetBit(0); nextSetBit >= 0; nextSetBit = valueOf.nextSetBit(nextSetBit + 1)) {
            arrayList.add(android.net.metrics.ApfProgramEvent.Decoder.constants.get(nextSetBit));
        }
        return android.text.TextUtils.join(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER, arrayList);
    }

    static final class Decoder {
        static final android.util.SparseArray<java.lang.String> constants = com.android.internal.util.MessageUtils.findMessageNames(new java.lang.Class[]{android.net.metrics.ApfProgramEvent.class}, new java.lang.String[]{"FLAG_"});

        Decoder() {
        }
    }
}
