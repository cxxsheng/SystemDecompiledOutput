package android.app.backup;

/* loaded from: classes.dex */
public class FileBackupHelper extends android.app.backup.FileBackupHelperBase implements android.app.backup.BackupHelper {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "FileBackupHelper";
    android.content.Context mContext;
    java.lang.String[] mFiles;
    java.io.File mFilesDir;

    @Override // android.app.backup.FileBackupHelperBase, android.app.backup.BackupHelper
    public /* bridge */ /* synthetic */ void writeNewStateDescription(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        super.writeNewStateDescription(parcelFileDescriptor);
    }

    public FileBackupHelper(android.content.Context context, java.lang.String... strArr) {
        super(context);
        this.mContext = context;
        this.mFilesDir = context.getFilesDir();
        this.mFiles = strArr;
    }

    @Override // android.app.backup.BackupHelper
    public void performBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2) {
        java.lang.String[] strArr = this.mFiles;
        java.io.File filesDir = this.mContext.getFilesDir();
        int length = strArr.length;
        java.lang.String[] strArr2 = new java.lang.String[length];
        for (int i = 0; i < length; i++) {
            strArr2[i] = new java.io.File(filesDir, strArr[i]).getAbsolutePath();
        }
        performBackup_checked(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2, strArr2, strArr);
    }

    @Override // android.app.backup.BackupHelper
    public void restoreEntity(android.app.backup.BackupDataInputStream backupDataInputStream) {
        java.lang.String key = backupDataInputStream.getKey();
        if (isKeyInList(key, this.mFiles)) {
            writeFile(new java.io.File(this.mFilesDir, key), backupDataInputStream);
        }
    }
}
