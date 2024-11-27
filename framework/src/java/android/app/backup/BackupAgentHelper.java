package android.app.backup;

/* loaded from: classes.dex */
public class BackupAgentHelper extends android.app.backup.BackupAgent {
    static final java.lang.String TAG = "BackupAgentHelper";
    android.app.backup.BackupHelperDispatcher mDispatcher = new android.app.backup.BackupHelperDispatcher();

    @Override // android.app.backup.BackupAgent
    public void onBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2) throws java.io.IOException {
        this.mDispatcher.performBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2);
    }

    @Override // android.app.backup.BackupAgent
    public void onRestore(android.app.backup.BackupDataInput backupDataInput, int i, android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        this.mDispatcher.performRestore(backupDataInput, i, parcelFileDescriptor);
    }

    public android.app.backup.BackupHelperDispatcher getDispatcher() {
        return this.mDispatcher;
    }

    public void addHelper(java.lang.String str, android.app.backup.BackupHelper backupHelper) {
        this.mDispatcher.addHelper(str, backupHelper);
    }
}
