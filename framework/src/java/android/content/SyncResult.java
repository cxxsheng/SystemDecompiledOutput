package android.content;

/* loaded from: classes.dex */
public final class SyncResult implements android.os.Parcelable {
    public static final android.content.SyncResult ALREADY_IN_PROGRESS = new android.content.SyncResult(true);
    public static final android.os.Parcelable.Creator<android.content.SyncResult> CREATOR = new android.os.Parcelable.Creator<android.content.SyncResult>() { // from class: android.content.SyncResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.SyncResult createFromParcel(android.os.Parcel parcel) {
            return new android.content.SyncResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.SyncResult[] newArray(int i) {
            return new android.content.SyncResult[i];
        }
    };
    public boolean databaseError;
    public long delayUntil;
    public boolean fullSyncRequested;
    public boolean moreRecordsToGet;
    public boolean partialSyncUnavailable;
    public final android.content.SyncStats stats;
    public final boolean syncAlreadyInProgress;
    public boolean tooManyDeletions;
    public boolean tooManyRetries;

    public SyncResult() {
        this(false);
    }

    private SyncResult(boolean z) {
        this.syncAlreadyInProgress = z;
        this.tooManyDeletions = false;
        this.tooManyRetries = false;
        this.fullSyncRequested = false;
        this.partialSyncUnavailable = false;
        this.moreRecordsToGet = false;
        this.delayUntil = 0L;
        this.stats = new android.content.SyncStats();
    }

    private SyncResult(android.os.Parcel parcel) {
        this.syncAlreadyInProgress = parcel.readInt() != 0;
        this.tooManyDeletions = parcel.readInt() != 0;
        this.tooManyRetries = parcel.readInt() != 0;
        this.databaseError = parcel.readInt() != 0;
        this.fullSyncRequested = parcel.readInt() != 0;
        this.partialSyncUnavailable = parcel.readInt() != 0;
        this.moreRecordsToGet = parcel.readInt() != 0;
        this.delayUntil = parcel.readLong();
        this.stats = new android.content.SyncStats(parcel);
    }

    public boolean hasHardError() {
        return this.stats.numParseExceptions > 0 || this.stats.numConflictDetectedExceptions > 0 || this.stats.numAuthExceptions > 0 || this.tooManyDeletions || this.tooManyRetries || this.databaseError;
    }

    public boolean hasSoftError() {
        return this.syncAlreadyInProgress || this.stats.numIoExceptions > 0;
    }

    public boolean hasError() {
        return hasSoftError() || hasHardError();
    }

    public boolean madeSomeProgress() {
        return (this.stats.numDeletes > 0 && !this.tooManyDeletions) || this.stats.numInserts > 0 || this.stats.numUpdates > 0;
    }

    public void clear() {
        if (this.syncAlreadyInProgress) {
            throw new java.lang.UnsupportedOperationException("you are not allowed to clear the ALREADY_IN_PROGRESS SyncStats");
        }
        this.tooManyDeletions = false;
        this.tooManyRetries = false;
        this.databaseError = false;
        this.fullSyncRequested = false;
        this.partialSyncUnavailable = false;
        this.moreRecordsToGet = false;
        this.delayUntil = 0L;
        this.stats.clear();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.syncAlreadyInProgress ? 1 : 0);
        parcel.writeInt(this.tooManyDeletions ? 1 : 0);
        parcel.writeInt(this.tooManyRetries ? 1 : 0);
        parcel.writeInt(this.databaseError ? 1 : 0);
        parcel.writeInt(this.fullSyncRequested ? 1 : 0);
        parcel.writeInt(this.partialSyncUnavailable ? 1 : 0);
        parcel.writeInt(this.moreRecordsToGet ? 1 : 0);
        parcel.writeLong(this.delayUntil);
        this.stats.writeToParcel(parcel, i);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("SyncResult:");
        if (this.syncAlreadyInProgress) {
            sb.append(" syncAlreadyInProgress: ").append(this.syncAlreadyInProgress);
        }
        if (this.tooManyDeletions) {
            sb.append(" tooManyDeletions: ").append(this.tooManyDeletions);
        }
        if (this.tooManyRetries) {
            sb.append(" tooManyRetries: ").append(this.tooManyRetries);
        }
        if (this.databaseError) {
            sb.append(" databaseError: ").append(this.databaseError);
        }
        if (this.fullSyncRequested) {
            sb.append(" fullSyncRequested: ").append(this.fullSyncRequested);
        }
        if (this.partialSyncUnavailable) {
            sb.append(" partialSyncUnavailable: ").append(this.partialSyncUnavailable);
        }
        if (this.moreRecordsToGet) {
            sb.append(" moreRecordsToGet: ").append(this.moreRecordsToGet);
        }
        if (this.delayUntil > 0) {
            sb.append(" delayUntil: ").append(this.delayUntil);
        }
        sb.append(this.stats);
        return sb.toString();
    }

    public java.lang.String toDebugString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (this.fullSyncRequested) {
            sb.append("f1");
        }
        if (this.partialSyncUnavailable) {
            sb.append("r1");
        }
        if (hasHardError()) {
            sb.append("X1");
        }
        if (this.stats.numParseExceptions > 0) {
            sb.append("e").append(this.stats.numParseExceptions);
        }
        if (this.stats.numConflictDetectedExceptions > 0) {
            sb.append("c").append(this.stats.numConflictDetectedExceptions);
        }
        if (this.stats.numAuthExceptions > 0) {
            sb.append(android.app.backup.FullBackup.APK_TREE_TOKEN).append(this.stats.numAuthExceptions);
        }
        if (this.tooManyDeletions) {
            sb.append("D1");
        }
        if (this.tooManyRetries) {
            sb.append("R1");
        }
        if (this.databaseError) {
            sb.append("b1");
        }
        if (hasSoftError()) {
            sb.append("x1");
        }
        if (this.syncAlreadyInProgress) {
            sb.append("l1");
        }
        if (this.stats.numIoExceptions > 0) {
            sb.append(android.hardware.gnss.GnssSignalType.CODE_TYPE_I).append(this.stats.numIoExceptions);
        }
        return sb.toString();
    }
}
