package com.android.server.locksettings.recoverablekeystore.storage;

/* loaded from: classes2.dex */
public class CleanupManager {
    private static final java.lang.String TAG = "CleanupManager";
    private final com.android.server.locksettings.recoverablekeystore.storage.ApplicationKeyStorage mApplicationKeyStorage;
    private final com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb mDatabase;
    private java.util.Map<java.lang.Integer, java.lang.Long> mSerialNumbers;
    private final com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage mSnapshotStorage;
    private final android.os.UserManager mUserManager;

    public static com.android.server.locksettings.recoverablekeystore.storage.CleanupManager getInstance(android.content.Context context, com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage recoverySnapshotStorage, com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb recoverableKeyStoreDb, com.android.server.locksettings.recoverablekeystore.storage.ApplicationKeyStorage applicationKeyStorage) {
        return new com.android.server.locksettings.recoverablekeystore.storage.CleanupManager(recoverySnapshotStorage, recoverableKeyStoreDb, android.os.UserManager.get(context), applicationKeyStorage);
    }

    @com.android.internal.annotations.VisibleForTesting
    CleanupManager(com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage recoverySnapshotStorage, com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb recoverableKeyStoreDb, android.os.UserManager userManager, com.android.server.locksettings.recoverablekeystore.storage.ApplicationKeyStorage applicationKeyStorage) {
        this.mSnapshotStorage = recoverySnapshotStorage;
        this.mDatabase = recoverableKeyStoreDb;
        this.mUserManager = userManager;
        this.mApplicationKeyStorage = applicationKeyStorage;
    }

    public synchronized void registerRecoveryAgent(int i, int i2) {
        try {
            if (this.mSerialNumbers == null) {
                verifyKnownUsers();
            }
            java.lang.Long l = this.mSerialNumbers.get(java.lang.Integer.valueOf(i));
            if (l == null) {
                l = -1L;
            }
            if (l.longValue() != -1) {
                return;
            }
            long serialNumberForUser = this.mUserManager.getSerialNumberForUser(android.os.UserHandle.of(i));
            if (serialNumberForUser != -1) {
                storeUserSerialNumber(i, serialNumberForUser);
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void verifyKnownUsers() {
        try {
            this.mSerialNumbers = this.mDatabase.getUserSerialNumbers();
            java.util.ArrayList<java.lang.Integer> arrayList = new java.util.ArrayList<java.lang.Integer>() { // from class: com.android.server.locksettings.recoverablekeystore.storage.CleanupManager.1
            };
            for (java.util.Map.Entry<java.lang.Integer, java.lang.Long> entry : this.mSerialNumbers.entrySet()) {
                java.lang.Integer key = entry.getKey();
                java.lang.Long value = entry.getValue();
                if (value == null) {
                    value = -1L;
                }
                long serialNumberForUser = this.mUserManager.getSerialNumberForUser(android.os.UserHandle.of(key.intValue()));
                if (serialNumberForUser == -1) {
                    arrayList.add(key);
                    removeDataForUser(key.intValue());
                } else if (value.longValue() == -1) {
                    storeUserSerialNumber(key.intValue(), serialNumberForUser);
                } else if (value.longValue() != serialNumberForUser) {
                    arrayList.add(key);
                    removeDataForUser(key.intValue());
                    storeUserSerialNumber(key.intValue(), serialNumberForUser);
                }
            }
            java.util.Iterator<java.lang.Integer> it = arrayList.iterator();
            while (it.hasNext()) {
                this.mSerialNumbers.remove(it.next());
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private void storeUserSerialNumber(int i, long j) {
        android.util.Log.d(TAG, "Storing serial number for user " + i + ".");
        this.mSerialNumbers.put(java.lang.Integer.valueOf(i), java.lang.Long.valueOf(j));
        this.mDatabase.setUserSerialNumber(i, j);
    }

    private void removeDataForUser(int i) {
        android.util.Log.d(TAG, "Removing data for user " + i + ".");
        for (java.lang.Integer num : this.mDatabase.getRecoveryAgents(i)) {
            this.mSnapshotStorage.remove(num.intValue());
            removeAllKeysForRecoveryAgent(i, num.intValue());
        }
        this.mDatabase.removeUserFromAllTables(i);
    }

    private void removeAllKeysForRecoveryAgent(int i, int i2) {
        for (java.lang.String str : this.mDatabase.getAllKeys(i, i2, this.mDatabase.getPlatformKeyGenerationId(i)).keySet()) {
            try {
                this.mApplicationKeyStorage.deleteEntry(i, i2, str);
            } catch (android.os.ServiceSpecificException e) {
                android.util.Log.e(TAG, "Error while removing recoverable key " + str + " : " + e);
            }
        }
    }
}
