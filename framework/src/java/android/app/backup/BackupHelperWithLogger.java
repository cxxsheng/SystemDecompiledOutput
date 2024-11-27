package android.app.backup;

/* loaded from: classes.dex */
public abstract class BackupHelperWithLogger implements android.app.backup.BackupHelper {
    private boolean mIsLoggerSet = false;
    private android.app.backup.BackupRestoreEventLogger mLogger;

    @Override // android.app.backup.BackupHelper
    public abstract void performBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2);

    @Override // android.app.backup.BackupHelper
    public abstract void restoreEntity(android.app.backup.BackupDataInputStream backupDataInputStream);

    @Override // android.app.backup.BackupHelper
    public abstract void writeNewStateDescription(android.os.ParcelFileDescriptor parcelFileDescriptor);

    public android.app.backup.BackupRestoreEventLogger getLogger() {
        return this.mLogger;
    }

    public void setLogger(android.app.backup.BackupRestoreEventLogger backupRestoreEventLogger) {
        this.mLogger = backupRestoreEventLogger;
        this.mIsLoggerSet = true;
    }

    public boolean isLoggerSet() {
        return this.mIsLoggerSet;
    }
}
