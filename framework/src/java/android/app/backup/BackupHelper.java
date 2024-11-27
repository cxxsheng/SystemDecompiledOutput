package android.app.backup;

/* loaded from: classes.dex */
public interface BackupHelper {
    void performBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2);

    void restoreEntity(android.app.backup.BackupDataInputStream backupDataInputStream);

    void writeNewStateDescription(android.os.ParcelFileDescriptor parcelFileDescriptor);
}
