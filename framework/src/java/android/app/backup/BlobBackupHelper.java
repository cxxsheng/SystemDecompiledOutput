package android.app.backup;

/* loaded from: classes.dex */
public abstract class BlobBackupHelper extends android.app.backup.BackupHelperWithLogger {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "BlobBackupHelper";
    private final int mCurrentBlobVersion;
    private final java.lang.String[] mKeys;

    protected abstract void applyRestoredPayload(java.lang.String str, byte[] bArr);

    protected abstract byte[] getBackupPayload(java.lang.String str);

    public BlobBackupHelper(int i, java.lang.String... strArr) {
        this.mCurrentBlobVersion = i;
        this.mKeys = strArr;
    }

    private android.util.ArrayMap<java.lang.String, java.lang.Long> readOldState(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap = new android.util.ArrayMap<>();
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.FileInputStream(parcelFileDescriptor.getFileDescriptor()));
        try {
            int readInt = dataInputStream.readInt();
            if (readInt > this.mCurrentBlobVersion) {
                android.util.Log.w(TAG, "Prior state from unrecognized version " + readInt);
            } else {
                int readInt2 = dataInputStream.readInt();
                for (int i = 0; i < readInt2; i++) {
                    arrayMap.put(dataInputStream.readUTF(), java.lang.Long.valueOf(dataInputStream.readLong()));
                }
            }
        } catch (java.io.EOFException e) {
            arrayMap.clear();
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "Error examining prior backup state " + e2.getMessage());
            arrayMap.clear();
        }
        return arrayMap;
    }

    private void writeBackupState(android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        try {
            java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
            dataOutputStream.writeInt(this.mCurrentBlobVersion);
            int size = arrayMap != null ? arrayMap.size() : 0;
            dataOutputStream.writeInt(size);
            for (int i = 0; i < size; i++) {
                java.lang.String keyAt = arrayMap.keyAt(i);
                long longValue = arrayMap.valueAt(i).longValue();
                dataOutputStream.writeUTF(keyAt);
                dataOutputStream.writeLong(longValue);
            }
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "Unable to write updated state", e);
        }
    }

    private byte[] deflate(byte[] bArr) {
        if (bArr != null) {
            try {
                java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                new java.io.DataOutputStream(byteArrayOutputStream).writeInt(this.mCurrentBlobVersion);
                java.util.zip.DeflaterOutputStream deflaterOutputStream = new java.util.zip.DeflaterOutputStream(byteArrayOutputStream);
                deflaterOutputStream.write(bArr);
                deflaterOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            } catch (java.io.IOException e) {
                android.util.Log.w(TAG, "Unable to process payload: " + e.getMessage());
            }
        }
        return null;
    }

    private byte[] inflate(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
            int readInt = new java.io.DataInputStream(byteArrayInputStream).readInt();
            if (readInt > this.mCurrentBlobVersion) {
                android.util.Log.w(TAG, "Saved payload from unrecognized version " + readInt);
                return null;
            }
            java.util.zip.InflaterInputStream inflaterInputStream = new java.util.zip.InflaterInputStream(byteArrayInputStream);
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            byte[] bArr2 = new byte[4096];
            while (true) {
                int read = inflaterInputStream.read(bArr2);
                if (read > 0) {
                    byteArrayOutputStream.write(bArr2, 0, read);
                } else {
                    inflaterInputStream.close();
                    byteArrayOutputStream.flush();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, "Unable to process restored payload: " + e.getMessage());
            return null;
        }
    }

    private long checksum(byte[] bArr) {
        if (bArr != null) {
            try {
                java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
                java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
                byte[] bArr2 = new byte[4096];
                while (true) {
                    int read = byteArrayInputStream.read(bArr2);
                    if (read >= 0) {
                        crc32.update(bArr2, 0, read);
                    } else {
                        return crc32.getValue();
                    }
                }
            } catch (java.lang.Exception e) {
                return -1L;
            }
        } else {
            return -1L;
        }
    }

    @Override // android.app.backup.BackupHelperWithLogger, android.app.backup.BackupHelper
    public void performBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2) {
        android.util.ArrayMap<java.lang.String, java.lang.Long> readOldState = readOldState(parcelFileDescriptor);
        android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap = new android.util.ArrayMap<>();
        try {
            try {
                for (java.lang.String str : this.mKeys) {
                    byte[] deflate = deflate(getBackupPayload(str));
                    long checksum = checksum(deflate);
                    arrayMap.put(str, java.lang.Long.valueOf(checksum));
                    java.lang.Long l = readOldState.get(str);
                    if (l == null || checksum != l.longValue()) {
                        if (deflate != null) {
                            backupDataOutput.writeEntityHeader(str, deflate.length);
                            backupDataOutput.writeEntityData(deflate, deflate.length);
                        } else {
                            backupDataOutput.writeEntityHeader(str, -1);
                        }
                    }
                }
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "Unable to record notification state: " + e.getMessage());
                arrayMap.clear();
            }
        } finally {
            writeBackupState(arrayMap, parcelFileDescriptor2);
        }
    }

    @Override // android.app.backup.BackupHelperWithLogger, android.app.backup.BackupHelper
    public void restoreEntity(android.app.backup.BackupDataInputStream backupDataInputStream) {
        java.lang.String key = backupDataInputStream.getKey();
        int i = 0;
        while (i < this.mKeys.length && !key.equals(this.mKeys[i])) {
            try {
                i++;
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "Exception restoring entity " + key + " : " + e.getMessage());
                return;
            }
        }
        if (i >= this.mKeys.length) {
            android.util.Log.e(TAG, "Unrecognized key " + key + ", ignoring");
            return;
        }
        byte[] bArr = new byte[backupDataInputStream.size()];
        backupDataInputStream.read(bArr);
        applyRestoredPayload(key, inflate(bArr));
    }

    @Override // android.app.backup.BackupHelperWithLogger, android.app.backup.BackupHelper
    public void writeNewStateDescription(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        writeBackupState(null, parcelFileDescriptor);
    }
}
