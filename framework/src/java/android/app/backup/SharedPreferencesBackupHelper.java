package android.app.backup;

/* loaded from: classes.dex */
public class SharedPreferencesBackupHelper extends android.app.backup.FileBackupHelperBase implements android.app.backup.BackupHelper {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "SharedPreferencesBackupHelper";
    private android.content.Context mContext;
    private java.lang.String[] mPrefGroups;

    @Override // android.app.backup.FileBackupHelperBase, android.app.backup.BackupHelper
    public /* bridge */ /* synthetic */ void writeNewStateDescription(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        super.writeNewStateDescription(parcelFileDescriptor);
    }

    public SharedPreferencesBackupHelper(android.content.Context context, java.lang.String... strArr) {
        super(context);
        this.mContext = context;
        this.mPrefGroups = strArr;
    }

    @Override // android.app.backup.BackupHelper
    public void performBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2) {
        android.content.Context context = this.mContext;
        android.app.QueuedWork.waitToFinish();
        java.lang.String[] strArr = this.mPrefGroups;
        int length = strArr.length;
        java.lang.String[] strArr2 = new java.lang.String[length];
        for (int i = 0; i < length; i++) {
            strArr2[i] = context.getSharedPrefsFile(strArr[i]).getAbsolutePath();
        }
        performBackup_checked(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2, strArr2, strArr);
    }

    @Override // android.app.backup.BackupHelper
    public void restoreEntity(android.app.backup.BackupDataInputStream backupDataInputStream) {
        android.content.Context context = this.mContext;
        java.lang.String key = backupDataInputStream.getKey();
        if (isKeyInList(key, this.mPrefGroups)) {
            writeFile(context.getSharedPrefsFile(key).getAbsoluteFile(), backupDataInputStream);
        }
    }
}
