package com.android.server.locksettings;

/* loaded from: classes2.dex */
class LockSettingsStorage {
    private static final java.lang.String CHILD_PROFILE_LOCK_FILE = "gatekeeper.profile.key";
    private static final java.lang.String COLUMN_KEY = "name";
    private static final java.lang.String COLUMN_USERID = "user";
    private static final java.lang.String REBOOT_ESCROW_FILE = "reboot.escrow.key";
    private static final java.lang.String REBOOT_ESCROW_SERVER_BLOB_FILE = "reboot.escrow.server.blob.key";
    private static final java.lang.String REPAIR_MODE_DIRECTORY = "repair-mode/";
    private static final java.lang.String REPAIR_MODE_PERSISTENT_FILE = "pst";
    private static final java.lang.String SYNTHETIC_PASSWORD_DIRECTORY = "spblob/";
    private static final java.lang.String TABLE = "locksettings";
    private static final java.lang.String TAG = "LockSettingsStorage";
    private final android.content.Context mContext;
    private final com.android.server.locksettings.LockSettingsStorage.DatabaseHelper mOpenHelper;
    private com.android.server.pdb.PersistentDataBlockManagerInternal mPersistentDataBlockManagerInternal;
    private static final java.lang.String COLUMN_VALUE = "value";
    private static final java.lang.String[] COLUMNS_FOR_QUERY = {COLUMN_VALUE};
    private static final java.lang.String[] COLUMNS_FOR_PREFETCH = {"name", COLUMN_VALUE};
    private static final java.lang.Object DEFAULT = new java.lang.Object();
    private static final java.lang.String[] SETTINGS_TO_BACKUP = {"lock_screen_owner_info_enabled", "lock_screen_owner_info", "lock_pattern_visible_pattern", "lockscreen.power_button_instantly_locks"};
    private final com.android.server.locksettings.LockSettingsStorage.Cache mCache = new com.android.server.locksettings.LockSettingsStorage.Cache();
    private final java.lang.Object mFileWriteLock = new java.lang.Object();

    public interface Callback {
        void initialize(android.database.sqlite.SQLiteDatabase sQLiteDatabase);
    }

    public LockSettingsStorage(android.content.Context context) {
        this.mContext = context;
        this.mOpenHelper = new com.android.server.locksettings.LockSettingsStorage.DatabaseHelper(context);
    }

    public void setDatabaseOnCreateCallback(com.android.server.locksettings.LockSettingsStorage.Callback callback) {
        this.mOpenHelper.setCallback(callback);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public void writeKeyValue(java.lang.String str, java.lang.String str2, int i) {
        writeKeyValue(this.mOpenHelper.getWritableDatabase(), str, str2, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean isAutoPinConfirmSettingEnabled(int i) {
        return getBoolean("lockscreen.auto_pin_confirm", false, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void writeKeyValue(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, java.lang.String str2, int i) {
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("name", str);
        contentValues.put(COLUMN_USERID, java.lang.Integer.valueOf(i));
        contentValues.put(COLUMN_VALUE, str2);
        sQLiteDatabase.beginTransaction();
        try {
            sQLiteDatabase.delete(TABLE, "name=? AND user=?", new java.lang.String[]{str, java.lang.Integer.toString(i)});
            sQLiteDatabase.insert(TABLE, null, contentValues);
            sQLiteDatabase.setTransactionSuccessful();
            this.mCache.putKeyValue(str, str2, i);
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public java.lang.String readKeyValue(java.lang.String str, java.lang.String str2, int i) {
        synchronized (this.mCache) {
            try {
                if (this.mCache.hasKeyValue(str, i)) {
                    return this.mCache.peekKeyValue(str, str2, i);
                }
                int version = this.mCache.getVersion();
                java.lang.Object obj = DEFAULT;
                android.database.Cursor query = this.mOpenHelper.getReadableDatabase().query(TABLE, COLUMNS_FOR_QUERY, "user=? AND name=?", new java.lang.String[]{java.lang.Integer.toString(i), str}, null, null, null);
                if (query != null) {
                    if (query.moveToFirst()) {
                        obj = query.getString(0);
                    }
                    query.close();
                }
                this.mCache.putKeyValueIfUnchanged(str, obj, i, version);
                return obj == DEFAULT ? str2 : (java.lang.String) obj;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isKeyValueCached(java.lang.String str, int i) {
        return this.mCache.hasKeyValue(str, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isUserPrefetched(int i) {
        return this.mCache.isFetched(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void removeKey(java.lang.String str, int i) {
        removeKey(this.mOpenHelper.getWritableDatabase(), str, i);
    }

    private void removeKey(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, int i) {
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("name", str);
        contentValues.put(COLUMN_USERID, java.lang.Integer.valueOf(i));
        sQLiteDatabase.beginTransaction();
        try {
            sQLiteDatabase.delete(TABLE, "name=? AND user=?", new java.lang.String[]{str, java.lang.Integer.toString(i)});
            sQLiteDatabase.setTransactionSuccessful();
            this.mCache.removeKey(str, i);
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public void prefetchUser(int i) {
        synchronized (this.mCache) {
            try {
                if (this.mCache.isFetched(i)) {
                    return;
                }
                this.mCache.setFetched(i);
                int version = this.mCache.getVersion();
                android.database.Cursor query = this.mOpenHelper.getReadableDatabase().query(TABLE, COLUMNS_FOR_PREFETCH, "user=?", new java.lang.String[]{java.lang.Integer.toString(i)}, null, null, null);
                if (query != null) {
                    while (query.moveToNext()) {
                        this.mCache.putKeyValueIfUnchanged(query.getString(0), query.getString(1), i, version);
                    }
                    query.close();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeChildProfileLock(int i) {
        deleteFile(getChildProfileLockFile(i));
    }

    public void writeChildProfileLock(int i, byte[] bArr) {
        writeFile(getChildProfileLockFile(i), bArr);
    }

    public byte[] readChildProfileLock(int i) {
        return readFile(getChildProfileLockFile(i));
    }

    public boolean hasChildProfileLock(int i) {
        return hasFile(getChildProfileLockFile(i));
    }

    public void writeRebootEscrow(int i, byte[] bArr) {
        writeFile(getRebootEscrowFile(i), bArr);
    }

    public byte[] readRebootEscrow(int i) {
        return readFile(getRebootEscrowFile(i));
    }

    public boolean hasRebootEscrow(int i) {
        return hasFile(getRebootEscrowFile(i));
    }

    public void removeRebootEscrow(int i) {
        deleteFile(getRebootEscrowFile(i));
    }

    public void writeRebootEscrowServerBlob(byte[] bArr) {
        writeFile(getRebootEscrowServerBlobFile(), bArr);
    }

    public byte[] readRebootEscrowServerBlob() {
        return readFile(getRebootEscrowServerBlobFile());
    }

    public boolean hasRebootEscrowServerBlob() {
        return hasFile(getRebootEscrowServerBlobFile());
    }

    public void removeRebootEscrowServerBlob() {
        deleteFile(getRebootEscrowServerBlobFile());
    }

    private boolean hasFile(java.io.File file) {
        byte[] readFile = readFile(file);
        return readFile != null && readFile.length > 0;
    }

    private byte[] readFile(java.io.File file) {
        synchronized (this.mCache) {
            try {
                if (this.mCache.hasFile(file)) {
                    return this.mCache.peekFile(file);
                }
                int version = this.mCache.getVersion();
                byte[] bArr = null;
                try {
                    java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(file, com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
                    try {
                        int length = (int) randomAccessFile.length();
                        bArr = new byte[length];
                        randomAccessFile.readFully(bArr, 0, length);
                        randomAccessFile.close();
                        randomAccessFile.close();
                    } finally {
                    }
                } catch (java.io.FileNotFoundException e) {
                } catch (java.io.IOException e2) {
                    android.util.Slog.e(TAG, "Cannot read file " + e2);
                }
                this.mCache.putFileIfUnchanged(file, bArr, version);
                return bArr;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void fsyncDirectory(java.io.File file) {
        try {
            java.nio.channels.FileChannel open = java.nio.channels.FileChannel.open(file.toPath(), java.nio.file.StandardOpenOption.READ);
            try {
                open.force(true);
                open.close();
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Error syncing directory: " + file, e);
        }
    }

    private void writeFile(java.io.File file, byte[] bArr) {
        writeFile(file, bArr, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0043 A[Catch: all -> 0x0019, TryCatch #2 {, blocks: (B:4:0x0003, B:10:0x0015, B:12:0x0043, B:13:0x004a, B:14:0x004f, B:25:0x0051, B:26:0x0054, B:21:0x003d), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void writeFile(java.io.File file, byte[] bArr, boolean z) {
        java.io.FileOutputStream fileOutputStream;
        java.io.IOException e;
        synchronized (this.mFileWriteLock) {
            android.util.AtomicFile atomicFile = new android.util.AtomicFile(file);
            java.io.FileOutputStream fileOutputStream2 = null;
            try {
                try {
                    fileOutputStream = atomicFile.startWrite();
                } catch (java.io.IOException e2) {
                    fileOutputStream = null;
                    e = e2;
                } catch (java.lang.Throwable th) {
                    th = th;
                    atomicFile.failWrite(fileOutputStream2);
                    throw th;
                }
                try {
                    fileOutputStream.write(bArr);
                    atomicFile.finishWrite(fileOutputStream);
                    atomicFile.failWrite(null);
                } catch (java.io.IOException e3) {
                    e = e3;
                    android.util.Slog.e(TAG, "Error writing file " + file, e);
                    atomicFile.failWrite(fileOutputStream);
                    if (z) {
                    }
                    this.mCache.putFile(file, bArr);
                }
                if (z) {
                    fsyncDirectory(file.getParentFile());
                }
                this.mCache.putFile(file, bArr);
            } catch (java.lang.Throwable th2) {
                th = th2;
                fileOutputStream2 = fileOutputStream;
                atomicFile.failWrite(fileOutputStream2);
                throw th;
            }
        }
    }

    private void deleteFile(java.io.File file) {
        synchronized (this.mFileWriteLock) {
            if (file.exists()) {
                try {
                    java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(file, "rws");
                    try {
                        randomAccessFile.write(new byte[(int) randomAccessFile.length()]);
                        randomAccessFile.close();
                    } catch (java.lang.Throwable th) {
                        try {
                            randomAccessFile.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (java.lang.Exception e) {
                    android.util.Slog.w(TAG, "Failed to zeroize " + file, e);
                }
            }
            new android.util.AtomicFile(file).delete();
            this.mCache.putFile(file, null);
        }
    }

    public byte getLockPatternSize(int i) {
        long longValue = java.lang.Long.valueOf(readKeyValue("lock_pattern_size", "-1", i)).longValue();
        if (longValue > 0 && longValue < 128) {
            return (byte) longValue;
        }
        return (byte) 3;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.io.File getChildProfileLockFile(int i) {
        return getLockCredentialFileForUser(i, CHILD_PROFILE_LOCK_FILE);
    }

    @com.android.internal.annotations.VisibleForTesting
    java.io.File getRebootEscrowFile(int i) {
        return getLockCredentialFileForUser(i, REBOOT_ESCROW_FILE);
    }

    @com.android.internal.annotations.VisibleForTesting
    java.io.File getRebootEscrowServerBlobFile() {
        return getLockCredentialFileForUser(0, REBOOT_ESCROW_SERVER_BLOB_FILE);
    }

    private java.io.File getLockCredentialFileForUser(int i, java.lang.String str) {
        if (i == 0) {
            return new java.io.File(android.os.Environment.getDataSystemDirectory(), str);
        }
        return new java.io.File(android.os.Environment.getUserSystemDirectory(i), str);
    }

    @com.android.internal.annotations.VisibleForTesting
    java.io.File getRepairModePersistentDataFile() {
        return new java.io.File(new java.io.File(android.os.Environment.getMetadataDirectory(), REPAIR_MODE_DIRECTORY), REPAIR_MODE_PERSISTENT_FILE);
    }

    public com.android.server.locksettings.LockSettingsStorage.PersistentData readRepairModePersistentData() {
        byte[] readFile = readFile(getRepairModePersistentDataFile());
        if (readFile == null) {
            return com.android.server.locksettings.LockSettingsStorage.PersistentData.NONE;
        }
        return com.android.server.locksettings.LockSettingsStorage.PersistentData.fromBytes(readFile);
    }

    public void writeRepairModePersistentData(int i, int i2, byte[] bArr) {
        writeFile(getRepairModePersistentDataFile(), com.android.server.locksettings.LockSettingsStorage.PersistentData.toBytes(i, i2, 0, bArr));
    }

    public void deleteRepairModePersistentData() {
        deleteFile(getRepairModePersistentDataFile());
    }

    public void writeSyntheticPasswordState(int i, long j, java.lang.String str, byte[] bArr) {
        ensureSyntheticPasswordDirectoryForUser(i);
        writeFile(getSyntheticPasswordStateFileForUser(i, j, str), bArr, false);
    }

    public byte[] readSyntheticPasswordState(int i, long j, java.lang.String str) {
        return readFile(getSyntheticPasswordStateFileForUser(i, j, str));
    }

    public void deleteSyntheticPasswordState(int i, long j, java.lang.String str) {
        deleteFile(getSyntheticPasswordStateFileForUser(i, j, str));
    }

    public void syncSyntheticPasswordState(int i) {
        fsyncDirectory(getSyntheticPasswordDirectoryForUser(i));
    }

    public java.util.Map<java.lang.Integer, java.util.List<java.lang.Long>> listSyntheticPasswordProtectorsForAllUsers(java.lang.String str) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (android.content.pm.UserInfo userInfo : android.os.UserManager.get(this.mContext).getUsers()) {
            arrayMap.put(java.lang.Integer.valueOf(userInfo.id), listSyntheticPasswordProtectorsForUser(str, userInfo.id));
        }
        return arrayMap;
    }

    public java.util.List<java.lang.Long> listSyntheticPasswordProtectorsForUser(java.lang.String str, int i) {
        java.io.File syntheticPasswordDirectoryForUser = getSyntheticPasswordDirectoryForUser(i);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.io.File[] listFiles = syntheticPasswordDirectoryForUser.listFiles();
        if (listFiles == null) {
            return arrayList;
        }
        for (java.io.File file : listFiles) {
            java.lang.String[] split = file.getName().split("\\.");
            if (split.length == 2 && split[1].equals(str)) {
                try {
                    arrayList.add(java.lang.Long.valueOf(java.lang.Long.parseUnsignedLong(split[0], 16)));
                } catch (java.lang.NumberFormatException e) {
                    android.util.Slog.e(TAG, "Failed to parse protector ID " + split[0]);
                }
            }
        }
        return arrayList;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.io.File getSyntheticPasswordDirectoryForUser(int i) {
        return new java.io.File(android.os.Environment.getDataSystemDeDirectory(i), SYNTHETIC_PASSWORD_DIRECTORY);
    }

    private void ensureSyntheticPasswordDirectoryForUser(int i) {
        java.io.File syntheticPasswordDirectoryForUser = getSyntheticPasswordDirectoryForUser(i);
        if (!syntheticPasswordDirectoryForUser.exists()) {
            syntheticPasswordDirectoryForUser.mkdir();
        }
    }

    private java.io.File getSyntheticPasswordStateFileForUser(int i, long j, java.lang.String str) {
        return new java.io.File(getSyntheticPasswordDirectoryForUser(i), android.text.TextUtils.formatSimple("%016x.%s", new java.lang.Object[]{java.lang.Long.valueOf(j), str}));
    }

    public void removeUser(int i) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
        if (((android.os.UserManager) this.mContext.getSystemService(COLUMN_USERID)).getProfileParent(i) == null) {
            deleteFile(getRebootEscrowFile(i));
        } else {
            removeChildProfileLock(i);
        }
        java.io.File syntheticPasswordDirectoryForUser = getSyntheticPasswordDirectoryForUser(i);
        try {
            writableDatabase.beginTransaction();
            writableDatabase.delete(TABLE, "user='" + i + "'", null);
            writableDatabase.setTransactionSuccessful();
            this.mCache.removeUser(i);
            this.mCache.purgePath(syntheticPasswordDirectoryForUser);
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public void setBoolean(java.lang.String str, boolean z, int i) {
        setString(str, z ? "1" : "0", i);
    }

    public void setLong(java.lang.String str, long j, int i) {
        setString(str, java.lang.Long.toString(j), i);
    }

    public void setInt(java.lang.String str, int i, int i2) {
        setString(str, java.lang.Integer.toString(i), i2);
    }

    public void setString(java.lang.String str, java.lang.String str2, int i) {
        com.android.internal.util.Preconditions.checkArgument(!com.android.internal.widget.LockPatternUtils.isSpecialUserId(i), "cannot store lock settings for special user: %d", new java.lang.Object[]{java.lang.Integer.valueOf(i)});
        writeKeyValue(str, str2, i);
        if (com.android.internal.util.ArrayUtils.contains(SETTINGS_TO_BACKUP, str)) {
            android.app.backup.BackupManager.dataChanged(com.android.server.backup.UserBackupManagerService.SETTINGS_PACKAGE);
        }
    }

    public boolean getBoolean(java.lang.String str, boolean z, int i) {
        java.lang.String string = getString(str, null, i);
        if (android.text.TextUtils.isEmpty(string)) {
            return z;
        }
        return string.equals("1") || string.equals("true");
    }

    public long getLong(java.lang.String str, long j, int i) {
        java.lang.String string = getString(str, null, i);
        return android.text.TextUtils.isEmpty(string) ? j : java.lang.Long.parseLong(string);
    }

    public int getInt(java.lang.String str, int i, int i2) {
        java.lang.String string = getString(str, null, i2);
        return android.text.TextUtils.isEmpty(string) ? i : java.lang.Integer.parseInt(string);
    }

    public java.lang.String getString(java.lang.String str, java.lang.String str2, int i) {
        if (com.android.internal.widget.LockPatternUtils.isSpecialUserId(i)) {
            return null;
        }
        return readKeyValue(str, str2, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    void closeDatabase() {
        this.mOpenHelper.close();
    }

    @com.android.internal.annotations.VisibleForTesting
    void clearCache() {
        this.mCache.clear();
    }

    @android.annotation.Nullable
    com.android.server.pdb.PersistentDataBlockManagerInternal getPersistentDataBlockManager() {
        if (this.mPersistentDataBlockManagerInternal == null) {
            this.mPersistentDataBlockManagerInternal = (com.android.server.pdb.PersistentDataBlockManagerInternal) com.android.server.LocalServices.getService(com.android.server.pdb.PersistentDataBlockManagerInternal.class);
        }
        return this.mPersistentDataBlockManagerInternal;
    }

    public void writePersistentDataBlock(int i, int i2, int i3, byte[] bArr) {
        com.android.server.pdb.PersistentDataBlockManagerInternal persistentDataBlockManager = getPersistentDataBlockManager();
        if (persistentDataBlockManager == null) {
            return;
        }
        persistentDataBlockManager.setFrpCredentialHandle(com.android.server.locksettings.LockSettingsStorage.PersistentData.toBytes(i, i2, i3, bArr));
    }

    public com.android.server.locksettings.LockSettingsStorage.PersistentData readPersistentDataBlock() {
        com.android.server.pdb.PersistentDataBlockManagerInternal persistentDataBlockManager = getPersistentDataBlockManager();
        if (persistentDataBlockManager == null) {
            return com.android.server.locksettings.LockSettingsStorage.PersistentData.NONE;
        }
        try {
            return com.android.server.locksettings.LockSettingsStorage.PersistentData.fromBytes(persistentDataBlockManager.getFrpCredentialHandle());
        } catch (java.lang.IllegalStateException e) {
            android.util.Slog.e(TAG, "Error reading persistent data block", e);
            return com.android.server.locksettings.LockSettingsStorage.PersistentData.NONE;
        }
    }

    public void deactivateFactoryResetProtectionWithoutSecret() {
        com.android.server.pdb.PersistentDataBlockManagerInternal persistentDataBlockManager = getPersistentDataBlockManager();
        if (persistentDataBlockManager != null) {
            persistentDataBlockManager.deactivateFactoryResetProtectionWithoutSecret();
        } else {
            android.util.Slog.wtf(TAG, "Failed to get PersistentDataBlockManagerInternal");
        }
    }

    public boolean isFactoryResetProtectionActive() {
        android.service.persistentdata.PersistentDataBlockManager persistentDataBlockManager = (android.service.persistentdata.PersistentDataBlockManager) this.mContext.getSystemService(android.service.persistentdata.PersistentDataBlockManager.class);
        if (persistentDataBlockManager != null) {
            return persistentDataBlockManager.isFactoryResetProtectionActive();
        }
        android.util.Slog.wtf(TAG, "Failed to get PersistentDataBlockManager");
        return false;
    }

    public static class PersistentData {
        public static final com.android.server.locksettings.LockSettingsStorage.PersistentData NONE = new com.android.server.locksettings.LockSettingsStorage.PersistentData(0, com.android.server.am.ProcessList.INVALID_ADJ, 0, null);
        public static final int TYPE_NONE = 0;
        public static final int TYPE_SP_GATEKEEPER = 1;
        public static final int TYPE_SP_WEAVER = 2;
        static final byte VERSION_1 = 1;
        static final int VERSION_1_HEADER_SIZE = 10;
        final byte[] payload;
        final int qualityForUi;
        final int type;
        final int userId;

        private PersistentData(int i, int i2, int i3, byte[] bArr) {
            this.type = i;
            this.userId = i2;
            this.qualityForUi = i3;
            this.payload = bArr;
        }

        public boolean isBadFormatFromAndroid14Beta() {
            return (this.type == 1 || this.type == 2) && com.android.server.locksettings.SyntheticPasswordManager.PasswordData.isBadFormatFromAndroid14Beta(this.payload);
        }

        public static com.android.server.locksettings.LockSettingsStorage.PersistentData fromBytes(byte[] bArr) {
            if (bArr == null || bArr.length == 0) {
                return NONE;
            }
            java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr));
            try {
                byte readByte = dataInputStream.readByte();
                if (readByte == 1) {
                    int readByte2 = dataInputStream.readByte() & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE;
                    int readInt = dataInputStream.readInt();
                    int readInt2 = dataInputStream.readInt();
                    int length = bArr.length - 10;
                    byte[] bArr2 = new byte[length];
                    java.lang.System.arraycopy(bArr, 10, bArr2, 0, length);
                    return new com.android.server.locksettings.LockSettingsStorage.PersistentData(readByte2, readInt, readInt2, bArr2);
                }
                android.util.Slog.wtf(com.android.server.locksettings.LockSettingsStorage.TAG, "Unknown PersistentData version code: " + ((int) readByte));
                return NONE;
            } catch (java.io.IOException e) {
                android.util.Slog.wtf(com.android.server.locksettings.LockSettingsStorage.TAG, "Could not parse PersistentData", e);
                return NONE;
            }
        }

        public static byte[] toBytes(int i, int i2, int i3, byte[] bArr) {
            if (i == 0) {
                com.android.internal.util.Preconditions.checkArgument(bArr == null, "TYPE_NONE must have empty payload");
                return null;
            }
            if (bArr != null && bArr.length > 0) {
                r0 = true;
            }
            com.android.internal.util.Preconditions.checkArgument(r0, "empty payload must only be used with TYPE_NONE");
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(bArr.length + 10);
            java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeByte(1);
                dataOutputStream.writeByte(i);
                dataOutputStream.writeInt(i2);
                dataOutputStream.writeInt(i3);
                dataOutputStream.write(bArr);
                return byteArrayOutputStream.toByteArray();
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalStateException("ByteArrayOutputStream cannot throw IOException");
            }
        }
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        java.util.Iterator it = android.os.UserManager.get(this.mContext).getUsers().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            android.content.pm.UserInfo userInfo = (android.content.pm.UserInfo) it.next();
            java.io.File syntheticPasswordDirectoryForUser = getSyntheticPasswordDirectoryForUser(userInfo.id);
            indentingPrintWriter.println(android.text.TextUtils.formatSimple("User %d [%s]:", new java.lang.Object[]{java.lang.Integer.valueOf(userInfo.id), syntheticPasswordDirectoryForUser}));
            indentingPrintWriter.increaseIndent();
            java.io.File[] listFiles = syntheticPasswordDirectoryForUser.listFiles();
            if (listFiles != null) {
                java.util.Arrays.sort(listFiles);
                int length = listFiles.length;
                while (r3 < length) {
                    java.io.File file = listFiles[r3];
                    indentingPrintWriter.println(android.text.TextUtils.formatSimple("%6d %s %s", new java.lang.Object[]{java.lang.Long.valueOf(file.length()), com.android.server.locksettings.LockSettingsService.timestampToString(file.lastModified()), file.getName()}));
                    r3++;
                }
            } else {
                indentingPrintWriter.println("[Not found]");
            }
            indentingPrintWriter.decreaseIndent();
        }
        java.io.File repairModePersistentDataFile = getRepairModePersistentDataFile();
        if (repairModePersistentDataFile.exists()) {
            indentingPrintWriter.println(android.text.TextUtils.formatSimple("Repair Mode [%s]:", new java.lang.Object[]{repairModePersistentDataFile.getParent()}));
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(android.text.TextUtils.formatSimple("%6d %s %s", new java.lang.Object[]{java.lang.Long.valueOf(repairModePersistentDataFile.length()), com.android.server.locksettings.LockSettingsService.timestampToString(repairModePersistentDataFile.lastModified()), repairModePersistentDataFile.getName()}));
            com.android.server.locksettings.LockSettingsStorage.PersistentData readRepairModePersistentData = readRepairModePersistentData();
            indentingPrintWriter.println(android.text.TextUtils.formatSimple("type: %d, user id: %d, payload size: %d", new java.lang.Object[]{java.lang.Integer.valueOf(readRepairModePersistentData.type), java.lang.Integer.valueOf(readRepairModePersistentData.userId), java.lang.Integer.valueOf(readRepairModePersistentData.payload != null ? readRepairModePersistentData.payload.length : 0)}));
            indentingPrintWriter.decreaseIndent();
        }
    }

    static class DatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {
        private static final java.lang.String DATABASE_NAME = "locksettings.db";
        private static final int DATABASE_VERSION = 2;
        private static final int IDLE_CONNECTION_TIMEOUT_MS = 30000;
        private static final java.lang.String TAG = "LockSettingsDB";
        private com.android.server.locksettings.LockSettingsStorage.Callback mCallback;

        public DatabaseHelper(android.content.Context context) {
            super(context, DATABASE_NAME, (android.database.sqlite.SQLiteDatabase.CursorFactory) null, 2);
            setWriteAheadLoggingEnabled(false);
            setIdleConnectionTimeout(30000L);
        }

        public void setCallback(com.android.server.locksettings.LockSettingsStorage.Callback callback) {
            this.mCallback = callback;
        }

        private void createTable(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE locksettings (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,user INTEGER,value TEXT);");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            createTable(sQLiteDatabase);
            if (this.mCallback != null) {
                this.mCallback.initialize(sQLiteDatabase);
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (i == 1) {
                i = 2;
            }
            if (i != 2) {
                android.util.Slog.w(TAG, "Failed to upgrade database!");
            }
        }
    }

    private static class Cache {
        private final android.util.ArrayMap<com.android.server.locksettings.LockSettingsStorage.Cache.CacheKey, java.lang.Object> mCache;
        private final com.android.server.locksettings.LockSettingsStorage.Cache.CacheKey mCacheKey;
        private int mVersion;

        private Cache() {
            this.mCache = new android.util.ArrayMap<>();
            this.mCacheKey = new com.android.server.locksettings.LockSettingsStorage.Cache.CacheKey();
            this.mVersion = 0;
        }

        java.lang.String peekKeyValue(java.lang.String str, java.lang.String str2, int i) {
            java.lang.Object peek = peek(0, str, i);
            return peek == com.android.server.locksettings.LockSettingsStorage.DEFAULT ? str2 : (java.lang.String) peek;
        }

        boolean hasKeyValue(java.lang.String str, int i) {
            return contains(0, str, i);
        }

        void putKeyValue(java.lang.String str, java.lang.String str2, int i) {
            put(0, str, str2, i);
        }

        void putKeyValueIfUnchanged(java.lang.String str, java.lang.Object obj, int i, int i2) {
            putIfUnchanged(0, str, obj, i, i2);
        }

        void removeKey(java.lang.String str, int i) {
            remove(0, str, i);
        }

        byte[] peekFile(java.io.File file) {
            return copyOf((byte[]) peek(1, file.toString(), -1));
        }

        boolean hasFile(java.io.File file) {
            return contains(1, file.toString(), -1);
        }

        void putFile(java.io.File file, byte[] bArr) {
            put(1, file.toString(), copyOf(bArr), -1);
        }

        void putFileIfUnchanged(java.io.File file, byte[] bArr, int i) {
            putIfUnchanged(1, file.toString(), copyOf(bArr), -1, i);
        }

        void setFetched(int i) {
            put(2, "", "true", i);
        }

        boolean isFetched(int i) {
            return contains(2, "", i);
        }

        private synchronized void remove(int i, java.lang.String str, int i2) {
            this.mCache.remove(this.mCacheKey.set(i, str, i2));
            this.mVersion++;
        }

        private synchronized void put(int i, java.lang.String str, java.lang.Object obj, int i2) {
            this.mCache.put(new com.android.server.locksettings.LockSettingsStorage.Cache.CacheKey().set(i, str, i2), obj);
            this.mVersion++;
        }

        private synchronized void putIfUnchanged(int i, java.lang.String str, java.lang.Object obj, int i2, int i3) {
            if (!contains(i, str, i2) && this.mVersion == i3) {
                this.mCache.put(new com.android.server.locksettings.LockSettingsStorage.Cache.CacheKey().set(i, str, i2), obj);
            }
        }

        private synchronized boolean contains(int i, java.lang.String str, int i2) {
            return this.mCache.containsKey(this.mCacheKey.set(i, str, i2));
        }

        private synchronized java.lang.Object peek(int i, java.lang.String str, int i2) {
            return this.mCache.get(this.mCacheKey.set(i, str, i2));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized int getVersion() {
            return this.mVersion;
        }

        synchronized void removeUser(int i) {
            try {
                for (int size = this.mCache.size() - 1; size >= 0; size--) {
                    if (this.mCache.keyAt(size).userId == i) {
                        this.mCache.removeAt(size);
                    }
                }
                this.mVersion++;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        private byte[] copyOf(byte[] bArr) {
            if (bArr != null) {
                return java.util.Arrays.copyOf(bArr, bArr.length);
            }
            return null;
        }

        synchronized void purgePath(java.io.File file) {
            try {
                java.lang.String obj = file.toString();
                for (int size = this.mCache.size() - 1; size >= 0; size--) {
                    com.android.server.locksettings.LockSettingsStorage.Cache.CacheKey keyAt = this.mCache.keyAt(size);
                    if (keyAt.type == 1 && keyAt.key.startsWith(obj)) {
                        this.mCache.removeAt(size);
                    }
                }
                this.mVersion++;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        synchronized void clear() {
            this.mCache.clear();
            this.mVersion++;
        }

        private static final class CacheKey {
            static final int TYPE_FETCHED = 2;
            static final int TYPE_FILE = 1;
            static final int TYPE_KEY_VALUE = 0;
            java.lang.String key;
            int type;
            int userId;

            private CacheKey() {
            }

            public com.android.server.locksettings.LockSettingsStorage.Cache.CacheKey set(int i, java.lang.String str, int i2) {
                this.type = i;
                this.key = str;
                this.userId = i2;
                return this;
            }

            public boolean equals(java.lang.Object obj) {
                if (!(obj instanceof com.android.server.locksettings.LockSettingsStorage.Cache.CacheKey)) {
                    return false;
                }
                com.android.server.locksettings.LockSettingsStorage.Cache.CacheKey cacheKey = (com.android.server.locksettings.LockSettingsStorage.Cache.CacheKey) obj;
                return this.userId == cacheKey.userId && this.type == cacheKey.type && java.util.Objects.equals(this.key, cacheKey.key);
            }

            public int hashCode() {
                return (((java.util.Objects.hashCode(this.key) * 31) + this.userId) * 31) + this.type;
            }
        }
    }
}
