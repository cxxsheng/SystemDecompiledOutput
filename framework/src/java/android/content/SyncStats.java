package android.content;

/* loaded from: classes.dex */
public class SyncStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.SyncStats> CREATOR = new android.os.Parcelable.Creator<android.content.SyncStats>() { // from class: android.content.SyncStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.SyncStats createFromParcel(android.os.Parcel parcel) {
            return new android.content.SyncStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.SyncStats[] newArray(int i) {
            return new android.content.SyncStats[i];
        }
    };
    public long numAuthExceptions;
    public long numConflictDetectedExceptions;
    public long numDeletes;
    public long numEntries;
    public long numInserts;
    public long numIoExceptions;
    public long numParseExceptions;
    public long numSkippedEntries;
    public long numUpdates;

    public SyncStats() {
        this.numAuthExceptions = 0L;
        this.numIoExceptions = 0L;
        this.numParseExceptions = 0L;
        this.numConflictDetectedExceptions = 0L;
        this.numInserts = 0L;
        this.numUpdates = 0L;
        this.numDeletes = 0L;
        this.numEntries = 0L;
        this.numSkippedEntries = 0L;
    }

    public SyncStats(android.os.Parcel parcel) {
        this.numAuthExceptions = parcel.readLong();
        this.numIoExceptions = parcel.readLong();
        this.numParseExceptions = parcel.readLong();
        this.numConflictDetectedExceptions = parcel.readLong();
        this.numInserts = parcel.readLong();
        this.numUpdates = parcel.readLong();
        this.numDeletes = parcel.readLong();
        this.numEntries = parcel.readLong();
        this.numSkippedEntries = parcel.readLong();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(" stats [");
        if (this.numAuthExceptions > 0) {
            sb.append(" numAuthExceptions: ").append(this.numAuthExceptions);
        }
        if (this.numIoExceptions > 0) {
            sb.append(" numIoExceptions: ").append(this.numIoExceptions);
        }
        if (this.numParseExceptions > 0) {
            sb.append(" numParseExceptions: ").append(this.numParseExceptions);
        }
        if (this.numConflictDetectedExceptions > 0) {
            sb.append(" numConflictDetectedExceptions: ").append(this.numConflictDetectedExceptions);
        }
        if (this.numInserts > 0) {
            sb.append(" numInserts: ").append(this.numInserts);
        }
        if (this.numUpdates > 0) {
            sb.append(" numUpdates: ").append(this.numUpdates);
        }
        if (this.numDeletes > 0) {
            sb.append(" numDeletes: ").append(this.numDeletes);
        }
        if (this.numEntries > 0) {
            sb.append(" numEntries: ").append(this.numEntries);
        }
        if (this.numSkippedEntries > 0) {
            sb.append(" numSkippedEntries: ").append(this.numSkippedEntries);
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public void clear() {
        this.numAuthExceptions = 0L;
        this.numIoExceptions = 0L;
        this.numParseExceptions = 0L;
        this.numConflictDetectedExceptions = 0L;
        this.numInserts = 0L;
        this.numUpdates = 0L;
        this.numDeletes = 0L;
        this.numEntries = 0L;
        this.numSkippedEntries = 0L;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.numAuthExceptions);
        parcel.writeLong(this.numIoExceptions);
        parcel.writeLong(this.numParseExceptions);
        parcel.writeLong(this.numConflictDetectedExceptions);
        parcel.writeLong(this.numInserts);
        parcel.writeLong(this.numUpdates);
        parcel.writeLong(this.numDeletes);
        parcel.writeLong(this.numEntries);
        parcel.writeLong(this.numSkippedEntries);
    }
}
