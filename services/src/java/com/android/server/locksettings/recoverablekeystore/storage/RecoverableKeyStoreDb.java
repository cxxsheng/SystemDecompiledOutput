package com.android.server.locksettings.recoverablekeystore.storage;

/* loaded from: classes2.dex */
public class RecoverableKeyStoreDb {
    private static final java.lang.String CERT_PATH_ENCODING = "PkiPath";
    private static final int IDLE_TIMEOUT_SECONDS = 30;
    private static final int LAST_SYNCED_AT_UNSYNCED = -1;
    private static final java.lang.String TAG = "RecoverableKeyStoreDb";
    private final com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDbHelper mKeyStoreDbHelper;
    private final com.android.server.locksettings.recoverablekeystore.TestOnlyInsecureCertificateHelper mTestOnlyInsecureCertificateHelper = new com.android.server.locksettings.recoverablekeystore.TestOnlyInsecureCertificateHelper();

    public static com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb newInstance(android.content.Context context) {
        com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDbHelper recoverableKeyStoreDbHelper = new com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDbHelper(context);
        recoverableKeyStoreDbHelper.setWriteAheadLoggingEnabled(true);
        recoverableKeyStoreDbHelper.setIdleConnectionTimeout(30L);
        return new com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb(recoverableKeyStoreDbHelper);
    }

    private RecoverableKeyStoreDb(com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDbHelper recoverableKeyStoreDbHelper) {
        this.mKeyStoreDbHelper = recoverableKeyStoreDbHelper;
    }

    public long insertKey(int i, int i2, java.lang.String str, com.android.server.locksettings.recoverablekeystore.WrappedKey wrappedKey) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("user_id", java.lang.Integer.valueOf(i));
        contentValues.put(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, java.lang.Integer.valueOf(i2));
        contentValues.put("alias", str);
        contentValues.put("nonce", wrappedKey.getNonce());
        contentValues.put("wrapped_key", wrappedKey.getKeyMaterial());
        contentValues.put("last_synced_at", (java.lang.Integer) (-1));
        contentValues.put("platform_key_generation_id", java.lang.Integer.valueOf(wrappedKey.getPlatformKeyGenerationId()));
        contentValues.put("recovery_status", java.lang.Integer.valueOf(wrappedKey.getRecoveryStatus()));
        byte[] keyMetadata = wrappedKey.getKeyMetadata();
        if (keyMetadata == null) {
            contentValues.putNull("key_metadata");
        } else {
            contentValues.put("key_metadata", keyMetadata);
        }
        return writableDatabase.replace("keys", null, contentValues);
    }

    @android.annotation.Nullable
    public com.android.server.locksettings.recoverablekeystore.WrappedKey getKey(int i, java.lang.String str) {
        byte[] blob;
        android.database.Cursor query = this.mKeyStoreDbHelper.getReadableDatabase().query("keys", new java.lang.String[]{"_id", "nonce", "wrapped_key", "platform_key_generation_id", "recovery_status", "key_metadata"}, "uid = ? AND alias = ?", new java.lang.String[]{java.lang.Integer.toString(i), str}, null, null, null);
        try {
            int count = query.getCount();
            if (count != 0) {
                if (count > 1) {
                    android.util.Log.wtf(TAG, java.lang.String.format(java.util.Locale.US, "%d WrappedKey entries found for uid=%d alias='%s'. Should only ever be 0 or 1.", java.lang.Integer.valueOf(count), java.lang.Integer.valueOf(i), str));
                    query.close();
                    return null;
                }
                query.moveToFirst();
                byte[] blob2 = query.getBlob(query.getColumnIndexOrThrow("nonce"));
                byte[] blob3 = query.getBlob(query.getColumnIndexOrThrow("wrapped_key"));
                int i2 = query.getInt(query.getColumnIndexOrThrow("platform_key_generation_id"));
                int i3 = query.getInt(query.getColumnIndexOrThrow("recovery_status"));
                int columnIndexOrThrow = query.getColumnIndexOrThrow("key_metadata");
                if (query.isNull(columnIndexOrThrow)) {
                    blob = null;
                } else {
                    blob = query.getBlob(columnIndexOrThrow);
                }
                com.android.server.locksettings.recoverablekeystore.WrappedKey wrappedKey = new com.android.server.locksettings.recoverablekeystore.WrappedKey(blob2, blob3, blob, i2, i3);
                query.close();
                return wrappedKey;
            }
            query.close();
            return null;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public boolean removeKey(int i, java.lang.String str) {
        return this.mKeyStoreDbHelper.getWritableDatabase().delete("keys", "uid = ? AND alias = ?", new java.lang.String[]{java.lang.Integer.toString(i), str}) > 0;
    }

    @android.annotation.NonNull
    public java.util.Map<java.lang.String, java.lang.Integer> getStatusForAllKeys(int i) {
        android.database.Cursor query = this.mKeyStoreDbHelper.getReadableDatabase().query("keys", new java.lang.String[]{"_id", "alias", "recovery_status"}, "uid = ?", new java.lang.String[]{java.lang.Integer.toString(i)}, null, null, null);
        try {
            java.util.HashMap hashMap = new java.util.HashMap();
            while (query.moveToNext()) {
                hashMap.put(query.getString(query.getColumnIndexOrThrow("alias")), java.lang.Integer.valueOf(query.getInt(query.getColumnIndexOrThrow("recovery_status"))));
            }
            query.close();
            return hashMap;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public int setRecoveryStatus(int i, java.lang.String str, int i2) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("recovery_status", java.lang.Integer.valueOf(i2));
        return writableDatabase.update("keys", contentValues, "uid = ? AND alias = ?", new java.lang.String[]{java.lang.String.valueOf(i), str});
    }

    @android.annotation.NonNull
    public java.util.Map<java.lang.String, com.android.server.locksettings.recoverablekeystore.WrappedKey> getAllKeys(int i, int i2, int i3) {
        byte[] blob;
        android.database.Cursor query = this.mKeyStoreDbHelper.getReadableDatabase().query("keys", new java.lang.String[]{"_id", "nonce", "wrapped_key", "alias", "recovery_status", "key_metadata"}, "user_id = ? AND uid = ? AND platform_key_generation_id = ?", new java.lang.String[]{java.lang.Integer.toString(i), java.lang.Integer.toString(i2), java.lang.Integer.toString(i3)}, null, null, null);
        try {
            java.util.HashMap hashMap = new java.util.HashMap();
            while (query.moveToNext()) {
                byte[] blob2 = query.getBlob(query.getColumnIndexOrThrow("nonce"));
                byte[] blob3 = query.getBlob(query.getColumnIndexOrThrow("wrapped_key"));
                java.lang.String string = query.getString(query.getColumnIndexOrThrow("alias"));
                int i4 = query.getInt(query.getColumnIndexOrThrow("recovery_status"));
                int columnIndexOrThrow = query.getColumnIndexOrThrow("key_metadata");
                if (query.isNull(columnIndexOrThrow)) {
                    blob = null;
                } else {
                    blob = query.getBlob(columnIndexOrThrow);
                }
                hashMap.put(string, new com.android.server.locksettings.recoverablekeystore.WrappedKey(blob2, blob3, blob, i3, i4));
            }
            query.close();
            return hashMap;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public long setPlatformKeyGenerationId(int i, int i2) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("user_id", java.lang.Integer.valueOf(i));
        contentValues.put("platform_key_generation_id", java.lang.Integer.valueOf(i2));
        java.lang.String[] strArr = {java.lang.String.valueOf(i)};
        ensureUserMetadataEntryExists(i);
        invalidateKeysForUser(i);
        return writableDatabase.update("user_metadata", contentValues, "user_id = ?", strArr);
    }

    @android.annotation.NonNull
    public java.util.Map<java.lang.Integer, java.lang.Long> getUserSerialNumbers() {
        android.database.Cursor query = this.mKeyStoreDbHelper.getReadableDatabase().query("user_metadata", new java.lang.String[]{"user_id", "user_serial_number"}, null, new java.lang.String[0], null, null, null);
        try {
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            while (query.moveToNext()) {
                arrayMap.put(java.lang.Integer.valueOf(query.getInt(query.getColumnIndexOrThrow("user_id"))), java.lang.Long.valueOf(query.getLong(query.getColumnIndexOrThrow("user_serial_number"))));
            }
            query.close();
            return arrayMap;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public long setUserSerialNumber(int i, long j) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("user_id", java.lang.Integer.valueOf(i));
        contentValues.put("user_serial_number", java.lang.Long.valueOf(j));
        java.lang.String[] strArr = {java.lang.String.valueOf(i)};
        ensureUserMetadataEntryExists(i);
        return writableDatabase.update("user_metadata", contentValues, "user_id = ?", strArr);
    }

    public long setBadRemoteGuessCounter(int i, int i2) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("user_id", java.lang.Integer.valueOf(i));
        contentValues.put("bad_remote_guess_counter", java.lang.Integer.valueOf(i2));
        java.lang.String[] strArr = {java.lang.String.valueOf(i)};
        ensureUserMetadataEntryExists(i);
        return writableDatabase.update("user_metadata", contentValues, "user_id = ?", strArr);
    }

    public int getBadRemoteGuessCounter(int i) {
        android.database.Cursor query = this.mKeyStoreDbHelper.getReadableDatabase().query("user_metadata", new java.lang.String[]{"bad_remote_guess_counter"}, "user_id = ?", new java.lang.String[]{java.lang.Integer.toString(i)}, null, null, null);
        try {
            if (query.getCount() != 0) {
                query.moveToFirst();
                int i2 = query.getInt(query.getColumnIndexOrThrow("bad_remote_guess_counter"));
                query.close();
                return i2;
            }
            query.close();
            return 0;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void invalidateKeysForUser(int i) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("recovery_status", (java.lang.Integer) 3);
        writableDatabase.update("keys", contentValues, "user_id = ?", new java.lang.String[]{java.lang.String.valueOf(i)});
    }

    public void invalidateKeysForUserIdOnCustomScreenLock(int i) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("recovery_status", (java.lang.Integer) 3);
        writableDatabase.update("keys", contentValues, "user_id = ?", new java.lang.String[]{java.lang.String.valueOf(i)});
    }

    public int getPlatformKeyGenerationId(int i) {
        android.database.Cursor query = this.mKeyStoreDbHelper.getReadableDatabase().query("user_metadata", new java.lang.String[]{"platform_key_generation_id"}, "user_id = ?", new java.lang.String[]{java.lang.Integer.toString(i)}, null, null, null);
        try {
            if (query.getCount() != 0) {
                query.moveToFirst();
                int i2 = query.getInt(query.getColumnIndexOrThrow("platform_key_generation_id"));
                query.close();
                return i2;
            }
            query.close();
            return -1;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public long setRecoveryServicePublicKey(int i, int i2, java.security.PublicKey publicKey) {
        return setBytes(i, i2, "public_key", publicKey.getEncoded());
    }

    @android.annotation.Nullable
    public java.lang.Long getRecoveryServiceCertSerial(int i, int i2, @android.annotation.NonNull java.lang.String str) {
        return getLong(i, i2, str, "cert_serial");
    }

    public long setRecoveryServiceCertSerial(int i, int i2, @android.annotation.NonNull java.lang.String str, long j) {
        return setLong(i, i2, str, "cert_serial", j);
    }

    @android.annotation.Nullable
    public java.security.cert.CertPath getRecoveryServiceCertPath(int i, int i2, @android.annotation.NonNull java.lang.String str) {
        byte[] bytes = getBytes(i, i2, str, "cert_path");
        if (bytes == null) {
            return null;
        }
        try {
            return decodeCertPath(bytes);
        } catch (java.security.cert.CertificateException e) {
            android.util.Log.wtf(TAG, java.lang.String.format(java.util.Locale.US, "Recovery service CertPath entry cannot be decoded for userId=%d uid=%d.", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)), e);
            return null;
        }
    }

    public long setRecoveryServiceCertPath(int i, int i2, @android.annotation.NonNull java.lang.String str, java.security.cert.CertPath certPath) throws java.security.cert.CertificateEncodingException {
        if (certPath.getCertificates().size() == 0) {
            throw new java.security.cert.CertificateEncodingException("No certificate contained in the cert path.");
        }
        return setBytes(i, i2, str, "cert_path", certPath.getEncoded(CERT_PATH_ENCODING));
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.Integer> getRecoveryAgents(int i) {
        android.database.Cursor query = this.mKeyStoreDbHelper.getReadableDatabase().query("recovery_service_metadata", new java.lang.String[]{com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID}, "user_id = ?", new java.lang.String[]{java.lang.Integer.toString(i)}, null, null, null);
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList(query.getCount());
            while (query.moveToNext()) {
                arrayList.add(java.lang.Integer.valueOf(query.getInt(query.getColumnIndexOrThrow(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID))));
            }
            query.close();
            return arrayList;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @android.annotation.Nullable
    public java.security.PublicKey getRecoveryServicePublicKey(int i, int i2) {
        byte[] bytes = getBytes(i, i2, "public_key");
        if (bytes == null) {
            return null;
        }
        try {
            return decodeX509Key(bytes);
        } catch (java.security.spec.InvalidKeySpecException e) {
            android.util.Log.wtf(TAG, java.lang.String.format(java.util.Locale.US, "Recovery service public key entry cannot be decoded for userId=%d uid=%d.", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
            return null;
        }
    }

    public long setRecoverySecretTypes(int i, int i2, int[] iArr) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        final java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",");
        java.util.Arrays.stream(iArr).forEach(new java.util.function.IntConsumer() { // from class: com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i3) {
                com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb.lambda$setRecoverySecretTypes$0(stringJoiner, i3);
            }
        });
        contentValues.put("secret_types", stringJoiner.toString());
        ensureRecoveryServiceMetadataEntryExists(i, i2);
        return writableDatabase.update("recovery_service_metadata", contentValues, "user_id = ? AND uid = ?", new java.lang.String[]{java.lang.String.valueOf(i), java.lang.String.valueOf(i2)});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setRecoverySecretTypes$0(java.util.StringJoiner stringJoiner, int i) {
        stringJoiner.add(java.lang.Integer.toString(i));
    }

    @android.annotation.NonNull
    public int[] getRecoverySecretTypes(int i, int i2) {
        android.database.Cursor query = this.mKeyStoreDbHelper.getReadableDatabase().query("recovery_service_metadata", new java.lang.String[]{"_id", "user_id", com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, "secret_types"}, "user_id = ? AND uid = ?", new java.lang.String[]{java.lang.Integer.toString(i), java.lang.Integer.toString(i2)}, null, null, null);
        try {
            int count = query.getCount();
            if (count == 0) {
                int[] iArr = new int[0];
                query.close();
                return iArr;
            }
            if (count > 1) {
                android.util.Log.wtf(TAG, java.lang.String.format(java.util.Locale.US, "%d deviceId entries found for userId=%d uid=%d. Should only ever be 0 or 1.", java.lang.Integer.valueOf(count), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
                int[] iArr2 = new int[0];
                query.close();
                return iArr2;
            }
            query.moveToFirst();
            int columnIndexOrThrow = query.getColumnIndexOrThrow("secret_types");
            if (query.isNull(columnIndexOrThrow)) {
                int[] iArr3 = new int[0];
                query.close();
                return iArr3;
            }
            java.lang.String string = query.getString(columnIndexOrThrow);
            if (android.text.TextUtils.isEmpty(string)) {
                int[] iArr4 = new int[0];
                query.close();
                return iArr4;
            }
            java.lang.String[] split = string.split(",");
            int[] iArr5 = new int[split.length];
            for (int i3 = 0; i3 < split.length; i3++) {
                try {
                    iArr5[i3] = java.lang.Integer.parseInt(split[i3]);
                } catch (java.lang.NumberFormatException e) {
                    android.util.Log.wtf(TAG, "String format error " + e);
                }
            }
            query.close();
            return iArr5;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public long setActiveRootOfTrust(int i, int i2, @android.annotation.Nullable java.lang.String str) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        new android.content.ContentValues().put("active_root_of_trust", str);
        ensureRecoveryServiceMetadataEntryExists(i, i2);
        return writableDatabase.update("recovery_service_metadata", r1, "user_id = ? AND uid = ?", new java.lang.String[]{java.lang.String.valueOf(i), java.lang.String.valueOf(i2)});
    }

    @android.annotation.Nullable
    public java.lang.String getActiveRootOfTrust(int i, int i2) {
        android.database.Cursor query = this.mKeyStoreDbHelper.getReadableDatabase().query("recovery_service_metadata", new java.lang.String[]{"_id", "user_id", com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, "active_root_of_trust"}, "user_id = ? AND uid = ?", new java.lang.String[]{java.lang.Integer.toString(i), java.lang.Integer.toString(i2)}, null, null, null);
        try {
            int count = query.getCount();
            if (count == 0) {
                query.close();
                return null;
            }
            if (count > 1) {
                android.util.Log.wtf(TAG, java.lang.String.format(java.util.Locale.US, "%d deviceId entries found for userId=%d uid=%d. Should only ever be 0 or 1.", java.lang.Integer.valueOf(count), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
                query.close();
                return null;
            }
            query.moveToFirst();
            int columnIndexOrThrow = query.getColumnIndexOrThrow("active_root_of_trust");
            if (query.isNull(columnIndexOrThrow)) {
                query.close();
                return null;
            }
            java.lang.String string = query.getString(columnIndexOrThrow);
            if (android.text.TextUtils.isEmpty(string)) {
                query.close();
                return null;
            }
            query.close();
            return string;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public long setCounterId(int i, int i2, long j) {
        return setLong(i, i2, "counter_id", j);
    }

    @android.annotation.Nullable
    public java.lang.Long getCounterId(int i, int i2) {
        return getLong(i, i2, "counter_id");
    }

    public long setServerParams(int i, int i2, byte[] bArr) {
        return setBytes(i, i2, "server_params", bArr);
    }

    @android.annotation.Nullable
    public byte[] getServerParams(int i, int i2) {
        return getBytes(i, i2, "server_params");
    }

    public long setSnapshotVersion(int i, int i2, long j) {
        return setLong(i, i2, "snapshot_version", j);
    }

    @android.annotation.Nullable
    public java.lang.Long getSnapshotVersion(int i, int i2) {
        return getLong(i, i2, "snapshot_version");
    }

    public long setShouldCreateSnapshot(int i, int i2, boolean z) {
        return setLong(i, i2, "should_create_snapshot", z ? 1L : 0L);
    }

    public boolean getShouldCreateSnapshot(int i, int i2) {
        java.lang.Long l = getLong(i, i2, "should_create_snapshot");
        return (l == null || l.longValue() == 0) ? false : true;
    }

    private java.lang.Long getLong(int i, int i2, java.lang.String str) {
        android.database.Cursor query = this.mKeyStoreDbHelper.getReadableDatabase().query("recovery_service_metadata", new java.lang.String[]{"_id", "user_id", com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, str}, "user_id = ? AND uid = ?", new java.lang.String[]{java.lang.Integer.toString(i), java.lang.Integer.toString(i2)}, null, null, null);
        try {
            int count = query.getCount();
            if (count == 0) {
                query.close();
                return null;
            }
            if (count > 1) {
                android.util.Log.wtf(TAG, java.lang.String.format(java.util.Locale.US, "%d entries found for userId=%d uid=%d. Should only ever be 0 or 1.", java.lang.Integer.valueOf(count), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
                query.close();
                return null;
            }
            query.moveToFirst();
            int columnIndexOrThrow = query.getColumnIndexOrThrow(str);
            if (query.isNull(columnIndexOrThrow)) {
                query.close();
                return null;
            }
            java.lang.Long valueOf = java.lang.Long.valueOf(query.getLong(columnIndexOrThrow));
            query.close();
            return valueOf;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private long setLong(int i, int i2, java.lang.String str, long j) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        new android.content.ContentValues().put(str, java.lang.Long.valueOf(j));
        java.lang.String[] strArr = {java.lang.Integer.toString(i), java.lang.Integer.toString(i2)};
        ensureRecoveryServiceMetadataEntryExists(i, i2);
        return writableDatabase.update("recovery_service_metadata", r1, "user_id = ? AND uid = ?", strArr);
    }

    private byte[] getBytes(int i, int i2, java.lang.String str) {
        android.database.Cursor query = this.mKeyStoreDbHelper.getReadableDatabase().query("recovery_service_metadata", new java.lang.String[]{"_id", "user_id", com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, str}, "user_id = ? AND uid = ?", new java.lang.String[]{java.lang.Integer.toString(i), java.lang.Integer.toString(i2)}, null, null, null);
        try {
            int count = query.getCount();
            if (count == 0) {
                query.close();
                return null;
            }
            if (count > 1) {
                android.util.Log.wtf(TAG, java.lang.String.format(java.util.Locale.US, "%d entries found for userId=%d uid=%d. Should only ever be 0 or 1.", java.lang.Integer.valueOf(count), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
                query.close();
                return null;
            }
            query.moveToFirst();
            int columnIndexOrThrow = query.getColumnIndexOrThrow(str);
            if (query.isNull(columnIndexOrThrow)) {
                query.close();
                return null;
            }
            byte[] blob = query.getBlob(columnIndexOrThrow);
            query.close();
            return blob;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private long setBytes(int i, int i2, java.lang.String str, byte[] bArr) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        new android.content.ContentValues().put(str, bArr);
        java.lang.String[] strArr = {java.lang.Integer.toString(i), java.lang.Integer.toString(i2)};
        ensureRecoveryServiceMetadataEntryExists(i, i2);
        return writableDatabase.update("recovery_service_metadata", r1, "user_id = ? AND uid = ?", strArr);
    }

    private byte[] getBytes(int i, int i2, java.lang.String str, java.lang.String str2) {
        android.database.Cursor query = this.mKeyStoreDbHelper.getReadableDatabase().query("root_of_trust", new java.lang.String[]{"_id", "user_id", com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, "root_alias", str2}, "user_id = ? AND uid = ? AND root_alias = ?", new java.lang.String[]{java.lang.Integer.toString(i), java.lang.Integer.toString(i2), this.mTestOnlyInsecureCertificateHelper.getDefaultCertificateAliasIfEmpty(str)}, null, null, null);
        try {
            int count = query.getCount();
            if (count == 0) {
                query.close();
                return null;
            }
            if (count > 1) {
                android.util.Log.wtf(TAG, java.lang.String.format(java.util.Locale.US, "%d entries found for userId=%d uid=%d. Should only ever be 0 or 1.", java.lang.Integer.valueOf(count), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
                query.close();
                return null;
            }
            query.moveToFirst();
            int columnIndexOrThrow = query.getColumnIndexOrThrow(str2);
            if (query.isNull(columnIndexOrThrow)) {
                query.close();
                return null;
            }
            byte[] blob = query.getBlob(columnIndexOrThrow);
            query.close();
            return blob;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private long setBytes(int i, int i2, java.lang.String str, java.lang.String str2, byte[] bArr) {
        java.lang.String defaultCertificateAliasIfEmpty = this.mTestOnlyInsecureCertificateHelper.getDefaultCertificateAliasIfEmpty(str);
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        new android.content.ContentValues().put(str2, bArr);
        java.lang.String[] strArr = {java.lang.Integer.toString(i), java.lang.Integer.toString(i2), defaultCertificateAliasIfEmpty};
        ensureRootOfTrustEntryExists(i, i2, defaultCertificateAliasIfEmpty);
        return writableDatabase.update("root_of_trust", r1, "user_id = ? AND uid = ? AND root_alias = ?", strArr);
    }

    private java.lang.Long getLong(int i, int i2, java.lang.String str, java.lang.String str2) {
        android.database.Cursor query = this.mKeyStoreDbHelper.getReadableDatabase().query("root_of_trust", new java.lang.String[]{"_id", "user_id", com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, "root_alias", str2}, "user_id = ? AND uid = ? AND root_alias = ?", new java.lang.String[]{java.lang.Integer.toString(i), java.lang.Integer.toString(i2), this.mTestOnlyInsecureCertificateHelper.getDefaultCertificateAliasIfEmpty(str)}, null, null, null);
        try {
            int count = query.getCount();
            if (count == 0) {
                query.close();
                return null;
            }
            if (count > 1) {
                android.util.Log.wtf(TAG, java.lang.String.format(java.util.Locale.US, "%d entries found for userId=%d uid=%d. Should only ever be 0 or 1.", java.lang.Integer.valueOf(count), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
                query.close();
                return null;
            }
            query.moveToFirst();
            int columnIndexOrThrow = query.getColumnIndexOrThrow(str2);
            if (query.isNull(columnIndexOrThrow)) {
                query.close();
                return null;
            }
            java.lang.Long valueOf = java.lang.Long.valueOf(query.getLong(columnIndexOrThrow));
            query.close();
            return valueOf;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private long setLong(int i, int i2, java.lang.String str, java.lang.String str2, long j) {
        java.lang.String defaultCertificateAliasIfEmpty = this.mTestOnlyInsecureCertificateHelper.getDefaultCertificateAliasIfEmpty(str);
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        new android.content.ContentValues().put(str2, java.lang.Long.valueOf(j));
        java.lang.String[] strArr = {java.lang.Integer.toString(i), java.lang.Integer.toString(i2), defaultCertificateAliasIfEmpty};
        ensureRootOfTrustEntryExists(i, i2, defaultCertificateAliasIfEmpty);
        return writableDatabase.update("root_of_trust", r1, "user_id = ? AND uid = ? AND root_alias = ?", strArr);
    }

    public void removeUserFromAllTables(int i) {
        removeUserFromKeysTable(i);
        removeUserFromUserMetadataTable(i);
        removeUserFromRecoveryServiceMetadataTable(i);
        removeUserFromRootOfTrustTable(i);
    }

    private boolean removeUserFromKeysTable(int i) {
        return this.mKeyStoreDbHelper.getWritableDatabase().delete("keys", "user_id = ?", new java.lang.String[]{java.lang.Integer.toString(i)}) > 0;
    }

    private boolean removeUserFromUserMetadataTable(int i) {
        return this.mKeyStoreDbHelper.getWritableDatabase().delete("user_metadata", "user_id = ?", new java.lang.String[]{java.lang.Integer.toString(i)}) > 0;
    }

    private boolean removeUserFromRecoveryServiceMetadataTable(int i) {
        return this.mKeyStoreDbHelper.getWritableDatabase().delete("recovery_service_metadata", "user_id = ?", new java.lang.String[]{java.lang.Integer.toString(i)}) > 0;
    }

    private boolean removeUserFromRootOfTrustTable(int i) {
        return this.mKeyStoreDbHelper.getWritableDatabase().delete("root_of_trust", "user_id = ?", new java.lang.String[]{java.lang.Integer.toString(i)}) > 0;
    }

    private void ensureRecoveryServiceMetadataEntryExists(int i, int i2) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("user_id", java.lang.Integer.valueOf(i));
        contentValues.put(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, java.lang.Integer.valueOf(i2));
        writableDatabase.insertWithOnConflict("recovery_service_metadata", null, contentValues, 4);
    }

    private void ensureRootOfTrustEntryExists(int i, int i2, java.lang.String str) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("user_id", java.lang.Integer.valueOf(i));
        contentValues.put(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, java.lang.Integer.valueOf(i2));
        contentValues.put("root_alias", str);
        writableDatabase.insertWithOnConflict("root_of_trust", null, contentValues, 4);
    }

    private void ensureUserMetadataEntryExists(int i) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("user_id", java.lang.Integer.valueOf(i));
        writableDatabase.insertWithOnConflict("user_metadata", null, contentValues, 4);
    }

    public void close() {
        this.mKeyStoreDbHelper.close();
    }

    @android.annotation.Nullable
    private static java.security.PublicKey decodeX509Key(byte[] bArr) throws java.security.spec.InvalidKeySpecException {
        try {
            return java.security.KeyFactory.getInstance("EC").generatePublic(new java.security.spec.X509EncodedKeySpec(bArr));
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @android.annotation.Nullable
    private static java.security.cert.CertPath decodeCertPath(byte[] bArr) throws java.security.cert.CertificateException {
        try {
            return java.security.cert.CertificateFactory.getInstance("X.509").generateCertPath(new java.io.ByteArrayInputStream(bArr), CERT_PATH_ENCODING);
        } catch (java.security.cert.CertificateException e) {
            throw new java.lang.RuntimeException(e);
        }
    }
}
