package com.android.server.voiceinteraction;

/* loaded from: classes2.dex */
public class DatabaseHelper extends android.database.sqlite.SQLiteOpenHelper implements com.android.server.voiceinteraction.IEnrolledModelDb {
    private static final java.lang.String CREATE_TABLE_SOUND_MODEL = "CREATE TABLE sound_model(model_uuid TEXT,vendor_uuid TEXT,keyphrase_id INTEGER,type INTEGER,data BLOB,recognition_modes INTEGER,locale TEXT,hint_text TEXT,users TEXT,model_version INTEGER,PRIMARY KEY (keyphrase_id,locale,users))";
    static final boolean DBG = false;
    private static final java.lang.String NAME = "sound_model.db";
    static final java.lang.String TAG = "SoundModelDBHelper";
    private static final int VERSION = 7;

    public interface SoundModelContract {
        public static final java.lang.String KEY_DATA = "data";
        public static final java.lang.String KEY_HINT_TEXT = "hint_text";
        public static final java.lang.String KEY_KEYPHRASE_ID = "keyphrase_id";
        public static final java.lang.String KEY_LOCALE = "locale";
        public static final java.lang.String KEY_MODEL_UUID = "model_uuid";
        public static final java.lang.String KEY_MODEL_VERSION = "model_version";
        public static final java.lang.String KEY_RECOGNITION_MODES = "recognition_modes";
        public static final java.lang.String KEY_TYPE = "type";
        public static final java.lang.String KEY_USERS = "users";
        public static final java.lang.String KEY_VENDOR_UUID = "vendor_uuid";
        public static final java.lang.String TABLE = "sound_model";
    }

    public DatabaseHelper(android.content.Context context) {
        super(context, NAME, (android.database.sqlite.SQLiteDatabase.CursorFactory) null, 7);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_TABLE_SOUND_MODEL);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < 4) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS sound_model");
            onCreate(sQLiteDatabase);
        } else if (i == 4) {
            android.util.Slog.d(TAG, "Adding vendor UUID column");
            sQLiteDatabase.execSQL("ALTER TABLE sound_model ADD COLUMN vendor_uuid TEXT");
            i++;
        }
        if (i == 5) {
            android.database.Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM sound_model", null);
            java.util.ArrayList<com.android.server.voiceinteraction.DatabaseHelper.SoundModelRecord> arrayList = new java.util.ArrayList();
            try {
                if (rawQuery.moveToFirst()) {
                    do {
                        try {
                            arrayList.add(new com.android.server.voiceinteraction.DatabaseHelper.SoundModelRecord(5, rawQuery));
                        } catch (java.lang.Exception e) {
                            android.util.Slog.e(TAG, "Failed to extract V5 record", e);
                        }
                    } while (rawQuery.moveToNext());
                }
                rawQuery.close();
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS sound_model");
                onCreate(sQLiteDatabase);
                for (com.android.server.voiceinteraction.DatabaseHelper.SoundModelRecord soundModelRecord : arrayList) {
                    if (soundModelRecord.ifViolatesV6PrimaryKeyIsFirstOfAnyDuplicates(arrayList)) {
                        try {
                            long writeToDatabase = soundModelRecord.writeToDatabase(6, sQLiteDatabase);
                            if (writeToDatabase == -1) {
                                android.util.Slog.e(TAG, "Database write failed " + soundModelRecord.modelUuid + ": " + writeToDatabase);
                            }
                        } catch (java.lang.Exception e2) {
                            android.util.Slog.e(TAG, "Failed to update V6 record " + soundModelRecord.modelUuid, e2);
                        }
                    }
                }
                i++;
            } catch (java.lang.Throwable th) {
                rawQuery.close();
                throw th;
            }
        }
        if (i == 6) {
            android.util.Slog.d(TAG, "Adding model version column");
            sQLiteDatabase.execSQL("ALTER TABLE sound_model ADD COLUMN model_version INTEGER DEFAULT -1");
        }
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public boolean updateKeyphraseSoundModel(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) {
        synchronized (this) {
            try {
                android.database.sqlite.SQLiteDatabase writableDatabase = getWritableDatabase();
                android.content.ContentValues contentValues = new android.content.ContentValues();
                contentValues.put("model_uuid", keyphraseSoundModel.getUuid().toString());
                if (keyphraseSoundModel.getVendorUuid() != null) {
                    contentValues.put("vendor_uuid", keyphraseSoundModel.getVendorUuid().toString());
                }
                contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, (java.lang.Integer) 0);
                contentValues.put("data", keyphraseSoundModel.getData());
                contentValues.put("model_version", java.lang.Integer.valueOf(keyphraseSoundModel.getVersion()));
                if (keyphraseSoundModel.getKeyphrases() == null || keyphraseSoundModel.getKeyphrases().length != 1) {
                    return false;
                }
                contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_KEYPHRASE_ID, java.lang.Integer.valueOf(keyphraseSoundModel.getKeyphrases()[0].getId()));
                contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_RECOGNITION_MODES, java.lang.Integer.valueOf(keyphraseSoundModel.getKeyphrases()[0].getRecognitionModes()));
                contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_USERS, getCommaSeparatedString(keyphraseSoundModel.getKeyphrases()[0].getUsers()));
                contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_LOCALE, keyphraseSoundModel.getKeyphrases()[0].getLocale().toLanguageTag());
                contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_HINT_TEXT, keyphraseSoundModel.getKeyphrases()[0].getText());
                try {
                    return writableDatabase.insertWithOnConflict(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.TABLE, null, contentValues, 5) != -1;
                } finally {
                    writableDatabase.close();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public boolean deleteKeyphraseSoundModel(int i, int i2, java.lang.String str) {
        java.lang.String languageTag = java.util.Locale.forLanguageTag(str).toLanguageTag();
        synchronized (this) {
            try {
                android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel = getKeyphraseSoundModel(i, i2, languageTag);
                if (keyphraseSoundModel == null) {
                    return false;
                }
                android.database.sqlite.SQLiteDatabase writableDatabase = getWritableDatabase();
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("model_uuid='");
                sb.append(keyphraseSoundModel.getUuid().toString());
                sb.append("'");
                try {
                    return writableDatabase.delete(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.TABLE, sb.toString(), null) != 0;
                } finally {
                    writableDatabase.close();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, int i2, java.lang.String str) {
        android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel validKeyphraseSoundModelForUser;
        java.lang.String languageTag = java.util.Locale.forLanguageTag(str).toLanguageTag();
        synchronized (this) {
            validKeyphraseSoundModelForUser = getValidKeyphraseSoundModelForUser("SELECT  * FROM sound_model WHERE keyphrase_id= '" + i + "' AND " + com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_LOCALE + "='" + languageTag + "'", i2);
        }
        return validKeyphraseSoundModelForUser;
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(java.lang.String str, int i, java.lang.String str2) {
        android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel validKeyphraseSoundModelForUser;
        java.lang.String languageTag = java.util.Locale.forLanguageTag(str2).toLanguageTag();
        synchronized (this) {
            validKeyphraseSoundModelForUser = getValidKeyphraseSoundModelForUser("SELECT  * FROM sound_model WHERE hint_text= '" + str + "' AND " + com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_LOCALE + "='" + languageTag + "'", i);
        }
        return validKeyphraseSoundModelForUser;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00ce, code lost:
    
        r13 = new android.hardware.soundtrigger.SoundTrigger.Keyphrase[]{new android.hardware.soundtrigger.SoundTrigger.Keyphrase(r8, r9, r10, r11, r12)};
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00da, code lost:
    
        if (r5 == null) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00dc, code lost:
    
        r11 = java.util.UUID.fromString(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00e3, code lost:
    
        r0 = new android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel(java.util.UUID.fromString(r3), r11, r6, r13, r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ef, code lost:
    
        r2.close();
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00f5, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00e2, code lost:
    
        r11 = null;
     */
    /* JADX WARN: Finally extract failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel getValidKeyphraseSoundModelForUser(java.lang.String str, int i) {
        boolean z;
        android.database.sqlite.SQLiteDatabase readableDatabase = getReadableDatabase();
        java.lang.String str2 = null;
        android.database.Cursor rawQuery = readableDatabase.rawQuery(str, null);
        try {
            if (rawQuery.moveToFirst()) {
                while (true) {
                    if (rawQuery.getInt(rawQuery.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE)) == 0) {
                        java.lang.String string = rawQuery.getString(rawQuery.getColumnIndex("model_uuid"));
                        if (string != null) {
                            int columnIndex = rawQuery.getColumnIndex("vendor_uuid");
                            java.lang.String string2 = columnIndex != -1 ? rawQuery.getString(columnIndex) : str2;
                            int i2 = rawQuery.getInt(rawQuery.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_KEYPHRASE_ID));
                            byte[] blob = rawQuery.getBlob(rawQuery.getColumnIndex("data"));
                            int i3 = rawQuery.getInt(rawQuery.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_RECOGNITION_MODES));
                            int[] arrayForCommaSeparatedString = getArrayForCommaSeparatedString(rawQuery.getString(rawQuery.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_USERS)));
                            java.util.Locale forLanguageTag = java.util.Locale.forLanguageTag(rawQuery.getString(rawQuery.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_LOCALE)));
                            java.lang.String string3 = rawQuery.getString(rawQuery.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_HINT_TEXT));
                            int i4 = rawQuery.getInt(rawQuery.getColumnIndex("model_version"));
                            if (arrayForCommaSeparatedString != null) {
                                int length = arrayForCommaSeparatedString.length;
                                int i5 = 0;
                                while (true) {
                                    if (i5 >= length) {
                                        z = false;
                                        break;
                                    }
                                    if (i == arrayForCommaSeparatedString[i5]) {
                                        z = true;
                                        break;
                                    }
                                    i5++;
                                }
                                if (z) {
                                    break;
                                }
                            } else {
                                android.util.Slog.w(TAG, "Ignoring SoundModel since it doesn't specify users");
                            }
                        } else {
                            android.util.Slog.w(TAG, "Ignoring SoundModel since it doesn't specify an ID");
                        }
                    }
                    if (!rawQuery.moveToNext()) {
                        break;
                    }
                    str2 = null;
                }
            }
            rawQuery.close();
            readableDatabase.close();
            return null;
        } catch (java.lang.Throwable th) {
            rawQuery.close();
            readableDatabase.close();
            throw th;
        }
    }

    private static java.lang.String getCommaSeparatedString(int[] iArr) {
        if (iArr == null) {
            return "";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < iArr.length; i++) {
            if (i != 0) {
                sb.append(',');
            }
            sb.append(iArr[i]);
        }
        return sb.toString();
    }

    private static int[] getArrayForCommaSeparatedString(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        java.lang.String[] split = str.split(",");
        int[] iArr = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            iArr[i] = java.lang.Integer.parseInt(split[i]);
        }
        return iArr;
    }

    private static class SoundModelRecord {
        public final byte[] data;
        public final java.lang.String hintText;
        public final int keyphraseId;
        public final java.lang.String locale;
        public final java.lang.String modelUuid;
        public final int recognitionModes;
        public final int type;
        public final java.lang.String users;
        public final java.lang.String vendorUuid;

        public SoundModelRecord(int i, android.database.Cursor cursor) {
            this.modelUuid = cursor.getString(cursor.getColumnIndex("model_uuid"));
            if (i >= 5) {
                this.vendorUuid = cursor.getString(cursor.getColumnIndex("vendor_uuid"));
            } else {
                this.vendorUuid = null;
            }
            this.keyphraseId = cursor.getInt(cursor.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_KEYPHRASE_ID));
            this.type = cursor.getInt(cursor.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE));
            this.data = cursor.getBlob(cursor.getColumnIndex("data"));
            this.recognitionModes = cursor.getInt(cursor.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_RECOGNITION_MODES));
            this.locale = cursor.getString(cursor.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_LOCALE));
            this.hintText = cursor.getString(cursor.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_HINT_TEXT));
            this.users = cursor.getString(cursor.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_USERS));
        }

        private boolean V6PrimaryKeyMatches(com.android.server.voiceinteraction.DatabaseHelper.SoundModelRecord soundModelRecord) {
            return this.keyphraseId == soundModelRecord.keyphraseId && stringComparisonHelper(this.locale, soundModelRecord.locale) && stringComparisonHelper(this.users, soundModelRecord.users);
        }

        public boolean ifViolatesV6PrimaryKeyIsFirstOfAnyDuplicates(java.util.List<com.android.server.voiceinteraction.DatabaseHelper.SoundModelRecord> list) {
            for (com.android.server.voiceinteraction.DatabaseHelper.SoundModelRecord soundModelRecord : list) {
                if (this != soundModelRecord && V6PrimaryKeyMatches(soundModelRecord) && !java.util.Arrays.equals(this.data, soundModelRecord.data)) {
                    return false;
                }
            }
            java.util.Iterator<com.android.server.voiceinteraction.DatabaseHelper.SoundModelRecord> it = list.iterator();
            while (it.hasNext()) {
                com.android.server.voiceinteraction.DatabaseHelper.SoundModelRecord next = it.next();
                if (V6PrimaryKeyMatches(next)) {
                    return this == next;
                }
            }
            return true;
        }

        public long writeToDatabase(int i, android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("model_uuid", this.modelUuid);
            if (i >= 5) {
                contentValues.put("vendor_uuid", this.vendorUuid);
            }
            contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_KEYPHRASE_ID, java.lang.Integer.valueOf(this.keyphraseId));
            contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, java.lang.Integer.valueOf(this.type));
            contentValues.put("data", this.data);
            contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_RECOGNITION_MODES, java.lang.Integer.valueOf(this.recognitionModes));
            contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_LOCALE, this.locale);
            contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_HINT_TEXT, this.hintText);
            contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_USERS, this.users);
            return sQLiteDatabase.insertWithOnConflict(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.TABLE, null, contentValues, 5);
        }

        private static boolean stringComparisonHelper(java.lang.String str, java.lang.String str2) {
            if (str != null) {
                return str.equals(str2);
            }
            return str == str2;
        }
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public void dump(java.io.PrintWriter printWriter) {
        synchronized (this) {
            try {
                android.database.sqlite.SQLiteDatabase readableDatabase = getReadableDatabase();
                android.database.Cursor rawQuery = readableDatabase.rawQuery("SELECT  * FROM sound_model", null);
                try {
                    printWriter.println("  Enrolled KeyphraseSoundModels:");
                    if (rawQuery.moveToFirst()) {
                        java.lang.String[] columnNames = rawQuery.getColumnNames();
                        do {
                            for (java.lang.String str : columnNames) {
                                int columnIndex = rawQuery.getColumnIndex(str);
                                switch (rawQuery.getType(columnIndex)) {
                                    case 0:
                                        printWriter.printf("    %s: null\n", str);
                                        break;
                                    case 1:
                                        printWriter.printf("    %s: %d\n", str, java.lang.Integer.valueOf(rawQuery.getInt(columnIndex)));
                                        break;
                                    case 2:
                                        printWriter.printf("    %s: %f\n", str, java.lang.Float.valueOf(rawQuery.getFloat(columnIndex)));
                                        break;
                                    case 3:
                                        printWriter.printf("    %s: %s\n", str, rawQuery.getString(columnIndex));
                                        break;
                                    case 4:
                                        printWriter.printf("    %s: data blob\n", str);
                                        break;
                                }
                            }
                            printWriter.println();
                        } while (rawQuery.moveToNext());
                    }
                    rawQuery.close();
                    readableDatabase.close();
                } catch (java.lang.Throwable th) {
                    rawQuery.close();
                    readableDatabase.close();
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }
}
