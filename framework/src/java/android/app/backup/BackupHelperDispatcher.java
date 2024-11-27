package android.app.backup;

/* loaded from: classes.dex */
public class BackupHelperDispatcher {
    private static final java.lang.String TAG = "BackupHelperDispatcher";
    java.util.TreeMap<java.lang.String, android.app.backup.BackupHelper> mHelpers = new java.util.TreeMap<>();

    private static native int allocateHeader_native(android.app.backup.BackupHelperDispatcher.Header header, java.io.FileDescriptor fileDescriptor);

    private static native int readHeader_native(android.app.backup.BackupHelperDispatcher.Header header, java.io.FileDescriptor fileDescriptor);

    private static native int skipChunk_native(java.io.FileDescriptor fileDescriptor, int i);

    private static native int writeHeader_native(android.app.backup.BackupHelperDispatcher.Header header, java.io.FileDescriptor fileDescriptor, int i);

    private static class Header {
        int chunkSize;
        java.lang.String keyPrefix;

        private Header() {
        }
    }

    public void addHelper(java.lang.String str, android.app.backup.BackupHelper backupHelper) {
        this.mHelpers.put(str, backupHelper);
    }

    public void performBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2) throws java.io.IOException {
        android.app.backup.BackupHelperDispatcher.Header header = new android.app.backup.BackupHelperDispatcher.Header();
        java.util.TreeMap treeMap = (java.util.TreeMap) this.mHelpers.clone();
        if (parcelFileDescriptor != null) {
            java.io.FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            while (true) {
                int readHeader_native = readHeader_native(header, fileDescriptor);
                if (readHeader_native < 0) {
                    break;
                }
                if (readHeader_native == 0) {
                    android.app.backup.BackupHelper backupHelper = (android.app.backup.BackupHelper) treeMap.get(header.keyPrefix);
                    android.util.Log.d(TAG, "handling existing helper '" + header.keyPrefix + "' " + backupHelper);
                    if (backupHelper != null) {
                        doOneBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2, header, backupHelper);
                        treeMap.remove(header.keyPrefix);
                    } else {
                        skipChunk_native(fileDescriptor, header.chunkSize);
                    }
                }
            }
        }
        for (java.util.Map.Entry entry : treeMap.entrySet()) {
            header.keyPrefix = (java.lang.String) entry.getKey();
            android.util.Log.d(TAG, "handling new helper '" + header.keyPrefix + "'");
            doOneBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2, header, (android.app.backup.BackupHelper) entry.getValue());
        }
    }

    private void doOneBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.app.backup.BackupHelperDispatcher.Header header, android.app.backup.BackupHelper backupHelper) throws java.io.IOException {
        java.io.FileDescriptor fileDescriptor = parcelFileDescriptor2.getFileDescriptor();
        int allocateHeader_native = allocateHeader_native(header, fileDescriptor);
        if (allocateHeader_native < 0) {
            throw new java.io.IOException("allocateHeader_native failed (error " + allocateHeader_native + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        backupDataOutput.setKeyPrefix(header.keyPrefix);
        backupHelper.performBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2);
        int writeHeader_native = writeHeader_native(header, fileDescriptor, allocateHeader_native);
        if (writeHeader_native != 0) {
            throw new java.io.IOException("writeHeader_native failed (error " + writeHeader_native + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public void performRestore(android.app.backup.BackupDataInput backupDataInput, int i, android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        android.app.backup.BackupDataInputStream backupDataInputStream = new android.app.backup.BackupDataInputStream(backupDataInput);
        boolean z = false;
        while (backupDataInput.readNextHeader()) {
            java.lang.String key = backupDataInput.getKey();
            int indexOf = key.indexOf(58);
            if (indexOf > 0) {
                android.app.backup.BackupHelper backupHelper = this.mHelpers.get(key.substring(0, indexOf));
                if (backupHelper != null) {
                    backupDataInputStream.dataSize = backupDataInput.getDataSize();
                    backupDataInputStream.key = key.substring(indexOf + 1);
                    backupHelper.restoreEntity(backupDataInputStream);
                } else if (!z) {
                    android.util.Log.w(TAG, "Couldn't find helper for: '" + key + "'");
                    z = true;
                }
            } else if (!z) {
                android.util.Log.w(TAG, "Entity with no prefix: '" + key + "'");
                z = true;
            }
            backupDataInput.skipEntityData();
        }
        java.util.Iterator<android.app.backup.BackupHelper> it = this.mHelpers.values().iterator();
        while (it.hasNext()) {
            it.next().writeNewStateDescription(parcelFileDescriptor);
        }
    }
}
