package com.android.server.backup;

/* loaded from: classes5.dex */
public class AccountSyncSettingsBackupHelper extends android.app.backup.BackupHelperWithLogger {
    private static final boolean DEBUG = false;
    private static final java.lang.String JSON_FORMAT_ENCODING = "UTF-8";
    private static final java.lang.String JSON_FORMAT_HEADER_KEY = "account_data";
    private static final int JSON_FORMAT_VERSION = 1;
    private static final java.lang.String KEY_ACCOUNTS = "accounts";
    private static final java.lang.String KEY_ACCOUNT_AUTHORITIES = "authorities";
    private static final java.lang.String KEY_ACCOUNT_NAME = "name";
    private static final java.lang.String KEY_ACCOUNT_TYPE = "type";
    private static final java.lang.String KEY_AUTHORITY_NAME = "name";
    private static final java.lang.String KEY_AUTHORITY_SYNC_ENABLED = "syncEnabled";
    private static final java.lang.String KEY_AUTHORITY_SYNC_STATE = "syncState";
    private static final java.lang.String KEY_MASTER_SYNC_ENABLED = "masterSyncEnabled";
    private static final java.lang.String KEY_VERSION = "version";
    private static final int MD5_BYTE_SIZE = 16;
    private static final java.lang.String STASH_FILE = "/backup/unadded_account_syncsettings.json";
    private static final int STATE_VERSION = 1;
    private static final int SYNC_REQUEST_LATCH_TIMEOUT_SECONDS = 1;
    private static final java.lang.String TAG = "AccountSyncSettingsBackupHelper";
    private android.accounts.AccountManager mAccountManager;
    private android.content.Context mContext;
    private final int mUserId;

    public AccountSyncSettingsBackupHelper(android.content.Context context, int i) {
        this.mContext = context;
        this.mAccountManager = android.accounts.AccountManager.get(this.mContext);
        this.mUserId = i;
    }

    @Override // android.app.backup.BackupHelperWithLogger, android.app.backup.BackupHelper
    public void performBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2) {
        try {
            byte[] bytes = serializeAccountSyncSettingsToJSON(this.mUserId).toString().getBytes("UTF-8");
            byte[] readOldMd5Checksum = readOldMd5Checksum(parcelFileDescriptor);
            byte[] generateMd5Checksum = generateMd5Checksum(bytes);
            if (java.util.Arrays.equals(readOldMd5Checksum, generateMd5Checksum)) {
                android.util.Log.i(TAG, "Old and new MD5 checksums match. Skipping backup.");
            } else {
                int length = bytes.length;
                backupDataOutput.writeEntityHeader(JSON_FORMAT_HEADER_KEY, length);
                backupDataOutput.writeEntityData(bytes, length);
                android.util.Log.i(TAG, "Backup successful.");
            }
            writeNewMd5Checksum(parcelFileDescriptor2, generateMd5Checksum);
        } catch (java.io.IOException | java.security.NoSuchAlgorithmException | org.json.JSONException e) {
            android.util.Log.e(TAG, "Couldn't backup account sync settings\n" + e);
        }
    }

    private org.json.JSONObject serializeAccountSyncSettingsToJSON(int i) throws org.json.JSONException {
        android.accounts.Account[] accountsAsUser = this.mAccountManager.getAccountsAsUser(i);
        android.content.SyncAdapterType[] syncAdapterTypesAsUser = android.content.ContentResolver.getSyncAdapterTypesAsUser(i);
        java.util.HashMap hashMap = new java.util.HashMap();
        for (android.content.SyncAdapterType syncAdapterType : syncAdapterTypesAsUser) {
            if (syncAdapterType.isUserVisible()) {
                if (!hashMap.containsKey(syncAdapterType.accountType)) {
                    hashMap.put(syncAdapterType.accountType, new java.util.ArrayList());
                }
                ((java.util.List) hashMap.get(syncAdapterType.accountType)).add(syncAdapterType.authority);
            }
        }
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        jSONObject.put("version", 1);
        jSONObject.put(KEY_MASTER_SYNC_ENABLED, android.content.ContentResolver.getMasterSyncAutomaticallyAsUser(i));
        org.json.JSONArray jSONArray = new org.json.JSONArray();
        for (android.accounts.Account account : accountsAsUser) {
            java.util.List<java.lang.String> list = (java.util.List) hashMap.get(account.type);
            if (list != null && !list.isEmpty()) {
                org.json.JSONObject jSONObject2 = new org.json.JSONObject();
                jSONObject2.put("name", account.name);
                jSONObject2.put("type", account.type);
                org.json.JSONArray jSONArray2 = new org.json.JSONArray();
                for (java.lang.String str : list) {
                    int isSyncableAsUser = android.content.ContentResolver.getIsSyncableAsUser(account, str, i);
                    boolean syncAutomaticallyAsUser = android.content.ContentResolver.getSyncAutomaticallyAsUser(account, str, i);
                    org.json.JSONObject jSONObject3 = new org.json.JSONObject();
                    jSONObject3.put("name", str);
                    jSONObject3.put(KEY_AUTHORITY_SYNC_STATE, isSyncableAsUser);
                    jSONObject3.put(KEY_AUTHORITY_SYNC_ENABLED, syncAutomaticallyAsUser);
                    jSONArray2.put(jSONObject3);
                }
                jSONObject2.put("authorities", jSONArray2);
                jSONArray.put(jSONObject2);
            }
        }
        jSONObject.put("accounts", jSONArray);
        return jSONObject;
    }

    private byte[] readOldMd5Checksum(android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.FileInputStream(parcelFileDescriptor.getFileDescriptor()));
        byte[] bArr = new byte[16];
        try {
            int readInt = dataInputStream.readInt();
            if (readInt <= 1) {
                for (int i = 0; i < 16; i++) {
                    bArr[i] = dataInputStream.readByte();
                }
            } else {
                android.util.Log.i(TAG, "Backup state version is: " + readInt + " (support only up to version 1" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        } catch (java.io.EOFException e) {
        }
        return bArr;
    }

    private void writeNewMd5Checksum(android.os.ParcelFileDescriptor parcelFileDescriptor, byte[] bArr) throws java.io.IOException {
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(new java.io.BufferedOutputStream(new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor())));
        dataOutputStream.writeInt(1);
        dataOutputStream.write(bArr);
    }

    private byte[] generateMd5Checksum(byte[] bArr) throws java.security.NoSuchAlgorithmException {
        if (bArr == null) {
            return null;
        }
        return java.security.MessageDigest.getInstance(android.security.keystore.KeyProperties.DIGEST_MD5).digest(bArr);
    }

    @Override // android.app.backup.BackupHelperWithLogger, android.app.backup.BackupHelper
    public void restoreEntity(android.app.backup.BackupDataInputStream backupDataInputStream) {
        byte[] bArr = new byte[backupDataInputStream.size()];
        try {
            backupDataInputStream.read(bArr);
            org.json.JSONObject jSONObject = new org.json.JSONObject(new java.lang.String(bArr, "UTF-8"));
            boolean z = jSONObject.getBoolean(KEY_MASTER_SYNC_ENABLED);
            org.json.JSONArray jSONArray = jSONObject.getJSONArray("accounts");
            if (android.content.ContentResolver.getMasterSyncAutomaticallyAsUser(this.mUserId)) {
                android.content.ContentResolver.setMasterSyncAutomaticallyAsUser(false, this.mUserId);
            }
            try {
                restoreFromJsonArray(jSONArray, this.mUserId);
                android.content.ContentResolver.setMasterSyncAutomaticallyAsUser(z, this.mUserId);
                android.util.Log.i(TAG, "Restore successful.");
            } catch (java.lang.Throwable th) {
                android.content.ContentResolver.setMasterSyncAutomaticallyAsUser(z, this.mUserId);
                throw th;
            }
        } catch (java.io.IOException | org.json.JSONException e) {
            android.util.Log.e(TAG, "Couldn't restore account sync settings\n" + e);
        }
    }

    private void restoreFromJsonArray(org.json.JSONArray jSONArray, int i) throws org.json.JSONException {
        java.util.Set<android.accounts.Account> accounts = getAccounts(i);
        org.json.JSONArray jSONArray2 = new org.json.JSONArray();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            org.json.JSONObject jSONObject = (org.json.JSONObject) jSONArray.get(i2);
            try {
                if (accounts.contains(new android.accounts.Account(jSONObject.getString("name"), jSONObject.getString("type")))) {
                    restoreExistingAccountSyncSettingsFromJSON(jSONObject, i);
                } else {
                    jSONArray2.put(jSONObject);
                }
            } catch (java.lang.IllegalArgumentException e) {
            }
        }
        if (jSONArray2.length() > 0) {
            try {
                java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(getStashFile(i));
                try {
                    new java.io.DataOutputStream(fileOutputStream).writeUTF(jSONArray2.toString());
                    fileOutputStream.close();
                    return;
                } finally {
                }
            } catch (java.io.IOException e2) {
                android.util.Log.e(TAG, "unable to write the sync settings to the stash file", e2);
                return;
            }
        }
        java.io.File stashFile = getStashFile(i);
        if (stashFile.exists()) {
            stashFile.delete();
        }
    }

    private void accountAddedInternal(int i) {
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(getStashFile(i));
            try {
                java.lang.String readUTF = new java.io.DataInputStream(fileInputStream).readUTF();
                fileInputStream.close();
                try {
                    restoreFromJsonArray(new org.json.JSONArray(readUTF), i);
                } catch (org.json.JSONException e) {
                    android.util.Log.e(TAG, "there was an error with the stashed sync settings", e);
                }
            } catch (java.lang.Throwable th) {
                try {
                    fileInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.FileNotFoundException e2) {
        } catch (java.io.IOException e3) {
        }
    }

    public static void accountAdded(android.content.Context context, int i) {
        new com.android.server.backup.AccountSyncSettingsBackupHelper(context, i).accountAddedInternal(i);
    }

    private java.util.Set<android.accounts.Account> getAccounts(int i) {
        android.accounts.Account[] accountsAsUser = this.mAccountManager.getAccountsAsUser(i);
        java.util.HashSet hashSet = new java.util.HashSet();
        for (android.accounts.Account account : accountsAsUser) {
            hashSet.add(account);
        }
        return hashSet;
    }

    private void restoreExistingAccountSyncSettingsFromJSON(org.json.JSONObject jSONObject, int i) throws org.json.JSONException {
        org.json.JSONArray jSONArray = jSONObject.getJSONArray("authorities");
        android.accounts.Account account = new android.accounts.Account(jSONObject.getString("name"), jSONObject.getString("type"));
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            org.json.JSONObject jSONObject2 = (org.json.JSONObject) jSONArray.get(i2);
            java.lang.String string = jSONObject2.getString("name");
            boolean z = jSONObject2.getBoolean(KEY_AUTHORITY_SYNC_ENABLED);
            int i3 = jSONObject2.getInt(KEY_AUTHORITY_SYNC_STATE);
            android.content.ContentResolver.setSyncAutomaticallyAsUser(account, string, z, i);
            if (!z) {
                android.content.ContentResolver.setIsSyncableAsUser(account, string, i3 == 0 ? 0 : 2, i);
            }
        }
    }

    @Override // android.app.backup.BackupHelperWithLogger, android.app.backup.BackupHelper
    public void writeNewStateDescription(android.os.ParcelFileDescriptor parcelFileDescriptor) {
    }

    private static java.io.File getStashFile(int i) {
        return new java.io.File(i == 0 ? android.os.Environment.getDataDirectory() : android.os.Environment.getDataSystemCeDirectory(i), STASH_FILE);
    }
}
