package android.app.backup;

/* loaded from: classes.dex */
public class FullBackupDataOutput {
    private final android.app.backup.BackupDataOutput mData;
    private final long mQuota;
    private long mSize;
    private final int mTransportFlags;

    public long getQuota() {
        return this.mQuota;
    }

    public int getTransportFlags() {
        return this.mTransportFlags;
    }

    public FullBackupDataOutput(long j) {
        this.mData = null;
        this.mQuota = j;
        this.mSize = 0L;
        this.mTransportFlags = 0;
    }

    public FullBackupDataOutput(long j, int i) {
        this.mData = null;
        this.mQuota = j;
        this.mSize = 0L;
        this.mTransportFlags = i;
    }

    public FullBackupDataOutput(android.os.ParcelFileDescriptor parcelFileDescriptor, long j) {
        this.mData = new android.app.backup.BackupDataOutput(parcelFileDescriptor.getFileDescriptor(), j, 0);
        this.mQuota = j;
        this.mTransportFlags = 0;
    }

    public FullBackupDataOutput(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i) {
        this.mData = new android.app.backup.BackupDataOutput(parcelFileDescriptor.getFileDescriptor(), j, i);
        this.mQuota = j;
        this.mTransportFlags = i;
    }

    public FullBackupDataOutput(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        this(parcelFileDescriptor, -1L, 0);
    }

    public android.app.backup.BackupDataOutput getData() {
        return this.mData;
    }

    public void addSize(long j) {
        if (j > 0) {
            this.mSize += j;
        }
    }

    public long getSize() {
        return this.mSize;
    }
}
