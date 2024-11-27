package android.net.metrics;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public final class ApfStats implements android.net.metrics.IpConnectivityLog.Event {
    public static final android.os.Parcelable.Creator<android.net.metrics.ApfStats> CREATOR = new android.os.Parcelable.Creator<android.net.metrics.ApfStats>() { // from class: android.net.metrics.ApfStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.ApfStats createFromParcel(android.os.Parcel parcel) {
            return new android.net.metrics.ApfStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.ApfStats[] newArray(int i) {
            return new android.net.metrics.ApfStats[i];
        }
    };
    public final int droppedRas;
    public final long durationMs;
    public final int matchingRas;
    public final int maxProgramSize;
    public final int parseErrors;
    public final int programUpdates;
    public final int programUpdatesAll;
    public final int programUpdatesAllowingMulticast;
    public final int receivedRas;
    public final int zeroLifetimeRas;

    private ApfStats(android.os.Parcel parcel) {
        this.durationMs = parcel.readLong();
        this.receivedRas = parcel.readInt();
        this.matchingRas = parcel.readInt();
        this.droppedRas = parcel.readInt();
        this.zeroLifetimeRas = parcel.readInt();
        this.parseErrors = parcel.readInt();
        this.programUpdates = parcel.readInt();
        this.programUpdatesAll = parcel.readInt();
        this.programUpdatesAllowingMulticast = parcel.readInt();
        this.maxProgramSize = parcel.readInt();
    }

    private ApfStats(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this.durationMs = j;
        this.receivedRas = i;
        this.matchingRas = i2;
        this.droppedRas = i3;
        this.zeroLifetimeRas = i4;
        this.parseErrors = i5;
        this.programUpdates = i6;
        this.programUpdatesAll = i7;
        this.programUpdatesAllowingMulticast = i8;
        this.maxProgramSize = i9;
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private int mDroppedRas;
        private long mDurationMs;
        private int mMatchingRas;
        private int mMaxProgramSize;
        private int mParseErrors;
        private int mProgramUpdates;
        private int mProgramUpdatesAll;
        private int mProgramUpdatesAllowingMulticast;
        private int mReceivedRas;
        private int mZeroLifetimeRas;

        public android.net.metrics.ApfStats.Builder setDurationMs(long j) {
            this.mDurationMs = j;
            return this;
        }

        public android.net.metrics.ApfStats.Builder setReceivedRas(int i) {
            this.mReceivedRas = i;
            return this;
        }

        public android.net.metrics.ApfStats.Builder setMatchingRas(int i) {
            this.mMatchingRas = i;
            return this;
        }

        public android.net.metrics.ApfStats.Builder setDroppedRas(int i) {
            this.mDroppedRas = i;
            return this;
        }

        public android.net.metrics.ApfStats.Builder setZeroLifetimeRas(int i) {
            this.mZeroLifetimeRas = i;
            return this;
        }

        public android.net.metrics.ApfStats.Builder setParseErrors(int i) {
            this.mParseErrors = i;
            return this;
        }

        public android.net.metrics.ApfStats.Builder setProgramUpdates(int i) {
            this.mProgramUpdates = i;
            return this;
        }

        public android.net.metrics.ApfStats.Builder setProgramUpdatesAll(int i) {
            this.mProgramUpdatesAll = i;
            return this;
        }

        public android.net.metrics.ApfStats.Builder setProgramUpdatesAllowingMulticast(int i) {
            this.mProgramUpdatesAllowingMulticast = i;
            return this;
        }

        public android.net.metrics.ApfStats.Builder setMaxProgramSize(int i) {
            this.mMaxProgramSize = i;
            return this;
        }

        public android.net.metrics.ApfStats build() {
            return new android.net.metrics.ApfStats(this.mDurationMs, this.mReceivedRas, this.mMatchingRas, this.mDroppedRas, this.mZeroLifetimeRas, this.mParseErrors, this.mProgramUpdates, this.mProgramUpdatesAll, this.mProgramUpdatesAllowingMulticast, this.mMaxProgramSize);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.durationMs);
        parcel.writeInt(this.receivedRas);
        parcel.writeInt(this.matchingRas);
        parcel.writeInt(this.droppedRas);
        parcel.writeInt(this.zeroLifetimeRas);
        parcel.writeInt(this.parseErrors);
        parcel.writeInt(this.programUpdates);
        parcel.writeInt(this.programUpdatesAll);
        parcel.writeInt(this.programUpdatesAllowingMulticast);
        parcel.writeInt(this.maxProgramSize);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "ApfStats(" + java.lang.String.format("%dms ", java.lang.Long.valueOf(this.durationMs)) + java.lang.String.format("%dB RA: {", java.lang.Integer.valueOf(this.maxProgramSize)) + java.lang.String.format("%d received, ", java.lang.Integer.valueOf(this.receivedRas)) + java.lang.String.format("%d matching, ", java.lang.Integer.valueOf(this.matchingRas)) + java.lang.String.format("%d dropped, ", java.lang.Integer.valueOf(this.droppedRas)) + java.lang.String.format("%d zero lifetime, ", java.lang.Integer.valueOf(this.zeroLifetimeRas)) + java.lang.String.format("%d parse errors}, ", java.lang.Integer.valueOf(this.parseErrors)) + java.lang.String.format("updates: {all: %d, RAs: %d, allow multicast: %d})", java.lang.Integer.valueOf(this.programUpdatesAll), java.lang.Integer.valueOf(this.programUpdates), java.lang.Integer.valueOf(this.programUpdatesAllowingMulticast));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !obj.getClass().equals(android.net.metrics.ApfStats.class)) {
            return false;
        }
        android.net.metrics.ApfStats apfStats = (android.net.metrics.ApfStats) obj;
        return this.durationMs == apfStats.durationMs && this.receivedRas == apfStats.receivedRas && this.matchingRas == apfStats.matchingRas && this.droppedRas == apfStats.droppedRas && this.zeroLifetimeRas == apfStats.zeroLifetimeRas && this.parseErrors == apfStats.parseErrors && this.programUpdates == apfStats.programUpdates && this.programUpdatesAll == apfStats.programUpdatesAll && this.programUpdatesAllowingMulticast == apfStats.programUpdatesAllowingMulticast && this.maxProgramSize == apfStats.maxProgramSize;
    }
}
