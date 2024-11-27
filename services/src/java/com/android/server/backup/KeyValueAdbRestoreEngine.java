package com.android.server.backup;

/* loaded from: classes.dex */
public class KeyValueAdbRestoreEngine implements java.lang.Runnable {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "KeyValueAdbRestoreEngine";
    private final android.app.IBackupAgent mAgent;
    private final com.android.server.backup.UserBackupManagerService mBackupManagerService;
    private final java.io.File mDataDir;
    private final android.os.ParcelFileDescriptor mInFD;
    private final com.android.server.backup.FileMetadata mInfo;
    private final int mToken;

    public KeyValueAdbRestoreEngine(com.android.server.backup.UserBackupManagerService userBackupManagerService, java.io.File file, com.android.server.backup.FileMetadata fileMetadata, android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.IBackupAgent iBackupAgent, int i) {
        this.mBackupManagerService = userBackupManagerService;
        this.mDataDir = file;
        this.mInfo = fileMetadata;
        this.mInFD = parcelFileDescriptor;
        this.mAgent = iBackupAgent;
        this.mToken = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            invokeAgentForAdbRestore(this.mAgent, this.mInfo, prepareRestoreData(this.mInfo, this.mInFD));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private java.io.File prepareRestoreData(com.android.server.backup.FileMetadata fileMetadata, android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        java.lang.String str = fileMetadata.packageName;
        java.io.File file = new java.io.File(this.mDataDir, str + ".restore");
        java.io.File file2 = new java.io.File(this.mDataDir, str + ".sorted");
        android.app.backup.FullBackup.restoreFile(parcelFileDescriptor, fileMetadata.size, fileMetadata.type, fileMetadata.mode, fileMetadata.mtime, file);
        sortKeyValueData(file, file2);
        return file2;
    }

    private void invokeAgentForAdbRestore(android.app.IBackupAgent iBackupAgent, com.android.server.backup.FileMetadata fileMetadata, java.io.File file) throws java.io.IOException {
        java.lang.String str = fileMetadata.packageName;
        try {
            iBackupAgent.doRestore(android.os.ParcelFileDescriptor.open(file, 268435456), fileMetadata.version, android.os.ParcelFileDescriptor.open(new java.io.File(this.mDataDir, str + com.android.server.backup.keyvalue.KeyValueBackupTask.NEW_STATE_FILE_SUFFIX), 1006632960), this.mToken, this.mBackupManagerService.getBackupManagerBinder());
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Exception calling doRestore on agent: " + e);
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Exception opening file. " + e2);
        }
    }

    private void sortKeyValueData(java.io.File file, java.io.File file2) throws java.io.IOException {
        java.io.FileOutputStream fileOutputStream;
        java.io.FileInputStream fileInputStream = null;
        try {
            java.io.FileInputStream fileInputStream2 = new java.io.FileInputStream(file);
            try {
                fileOutputStream = new java.io.FileOutputStream(file2);
            } catch (java.lang.Throwable th) {
                th = th;
                fileOutputStream = null;
            }
            try {
                copyKeysInLexicalOrder(new android.app.backup.BackupDataInput(fileInputStream2.getFD()), new android.app.backup.BackupDataOutput(fileOutputStream.getFD()));
                libcore.io.IoUtils.closeQuietly(fileInputStream2);
                libcore.io.IoUtils.closeQuietly(fileOutputStream);
            } catch (java.lang.Throwable th2) {
                th = th2;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    libcore.io.IoUtils.closeQuietly(fileInputStream);
                }
                if (fileOutputStream != null) {
                    libcore.io.IoUtils.closeQuietly(fileOutputStream);
                }
                throw th;
            }
        } catch (java.lang.Throwable th3) {
            th = th3;
            fileOutputStream = null;
        }
    }

    private void copyKeysInLexicalOrder(android.app.backup.BackupDataInput backupDataInput, android.app.backup.BackupDataOutput backupDataOutput) throws java.io.IOException {
        java.util.HashMap hashMap = new java.util.HashMap();
        while (backupDataInput.readNextHeader()) {
            java.lang.String key = backupDataInput.getKey();
            int dataSize = backupDataInput.getDataSize();
            if (dataSize < 0) {
                backupDataInput.skipEntityData();
            } else {
                byte[] bArr = new byte[dataSize];
                backupDataInput.readEntityData(bArr, 0, dataSize);
                hashMap.put(key, bArr);
            }
        }
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList(hashMap.keySet());
        java.util.Collections.sort(arrayList);
        for (java.lang.String str : arrayList) {
            byte[] bArr2 = (byte[]) hashMap.get(str);
            backupDataOutput.writeEntityHeader(str, bArr2.length);
            backupDataOutput.writeEntityData(bArr2, bArr2.length);
        }
    }
}
