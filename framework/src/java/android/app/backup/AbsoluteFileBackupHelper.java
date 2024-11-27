package android.app.backup;

/* loaded from: classes.dex */
public class AbsoluteFileBackupHelper extends android.app.backup.FileBackupHelperBase implements android.app.backup.BackupHelper {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "AbsoluteFileBackupHelper";
    android.content.Context mContext;
    java.lang.String[] mFiles;

    @Override // android.app.backup.FileBackupHelperBase, android.app.backup.BackupHelper
    public /* bridge */ /* synthetic */ void writeNewStateDescription(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        super.writeNewStateDescription(parcelFileDescriptor);
    }

    public AbsoluteFileBackupHelper(android.content.Context context, java.lang.String... strArr) {
        super(context);
        this.mContext = context;
        this.mFiles = strArr;
    }

    @Override // android.app.backup.BackupHelper
    public void performBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2) {
        performBackup_checked(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2, this.mFiles, this.mFiles);
    }

    @Override // android.app.backup.BackupHelper
    public void restoreEntity(android.app.backup.BackupDataInputStream backupDataInputStream) {
        java.lang.String key = backupDataInputStream.getKey();
        if (isKeyInList(key, this.mFiles)) {
            writeFile(new java.io.File(key), backupDataInputStream);
        }
    }
}
