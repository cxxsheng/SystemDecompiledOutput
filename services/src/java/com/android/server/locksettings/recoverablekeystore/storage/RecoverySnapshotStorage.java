package com.android.server.locksettings.recoverablekeystore.storage;

/* loaded from: classes2.dex */
public class RecoverySnapshotStorage {
    private static final java.lang.String ROOT_PATH = "system";
    private static final java.lang.String STORAGE_PATH = "recoverablekeystore/snapshots/";
    private static final java.lang.String TAG = "RecoverySnapshotStorage";

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.SparseArray<android.security.keystore.recovery.KeyChainSnapshot> mSnapshotByUid = new android.util.SparseArray<>();
    private final java.io.File rootDirectory;

    public static com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage newInstance() {
        return new com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage(new java.io.File(android.os.Environment.getDataDirectory(), "system"));
    }

    @com.android.internal.annotations.VisibleForTesting
    public RecoverySnapshotStorage(java.io.File file) {
        this.rootDirectory = file;
    }

    public synchronized void put(int i, android.security.keystore.recovery.KeyChainSnapshot keyChainSnapshot) {
        this.mSnapshotByUid.put(i, keyChainSnapshot);
        try {
            writeToDisk(i, keyChainSnapshot);
        } catch (java.io.IOException | java.security.cert.CertificateEncodingException e) {
            android.util.Log.e(TAG, java.lang.String.format(java.util.Locale.US, "Error persisting snapshot for %d to disk", java.lang.Integer.valueOf(i)), e);
        }
    }

    @android.annotation.Nullable
    public synchronized android.security.keystore.recovery.KeyChainSnapshot get(int i) {
        android.security.keystore.recovery.KeyChainSnapshot keyChainSnapshot = this.mSnapshotByUid.get(i);
        if (keyChainSnapshot != null) {
            return keyChainSnapshot;
        }
        try {
            return readFromDisk(i);
        } catch (com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException | java.io.IOException e) {
            android.util.Log.e(TAG, java.lang.String.format(java.util.Locale.US, "Error reading snapshot for %d from disk", java.lang.Integer.valueOf(i)), e);
            return null;
        }
    }

    public synchronized void remove(int i) {
        this.mSnapshotByUid.remove(i);
        getSnapshotFile(i).delete();
    }

    private void writeToDisk(int i, android.security.keystore.recovery.KeyChainSnapshot keyChainSnapshot) throws java.io.IOException, java.security.cert.CertificateEncodingException {
        java.io.File snapshotFile = getSnapshotFile(i);
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(snapshotFile);
            try {
                com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSerializer.serialize(keyChainSnapshot, fileOutputStream);
                fileOutputStream.close();
            } catch (java.lang.Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.IOException | java.security.cert.CertificateEncodingException e) {
            snapshotFile.delete();
            throw e;
        }
    }

    @android.annotation.Nullable
    private android.security.keystore.recovery.KeyChainSnapshot readFromDisk(int i) throws java.io.IOException, com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException {
        java.io.File snapshotFile = getSnapshotFile(i);
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(snapshotFile);
            try {
                android.security.keystore.recovery.KeyChainSnapshot deserialize = com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotDeserializer.deserialize(fileInputStream);
                fileInputStream.close();
                return deserialize;
            } catch (java.lang.Throwable th) {
                try {
                    fileInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException | java.io.IOException e) {
            snapshotFile.delete();
            throw e;
        }
    }

    private java.io.File getSnapshotFile(int i) {
        return new java.io.File(getStorageFolder(), getSnapshotFileName(i));
    }

    private java.lang.String getSnapshotFileName(int i) {
        return java.lang.String.format(java.util.Locale.US, "%d.xml", java.lang.Integer.valueOf(i));
    }

    private java.io.File getStorageFolder() {
        java.io.File file = new java.io.File(this.rootDirectory, STORAGE_PATH);
        file.mkdirs();
        return file;
    }
}
