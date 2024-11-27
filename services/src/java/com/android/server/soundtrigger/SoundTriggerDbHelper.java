package com.android.server.soundtrigger;

/* loaded from: classes2.dex */
public class SoundTriggerDbHelper extends android.database.sqlite.SQLiteOpenHelper {
    private static final java.lang.String CREATE_TABLE_ST_SOUND_MODEL = "CREATE TABLE st_sound_model(model_uuid TEXT PRIMARY KEY,vendor_uuid TEXT,data BLOB,model_version INTEGER )";
    static final boolean DBG = false;
    private static final java.lang.String NAME = "st_sound_model.db";
    static final java.lang.String TAG = "SoundTriggerDbHelper";
    private static final int VERSION = 2;

    public interface GenericSoundModelContract {
        public static final java.lang.String KEY_DATA = "data";
        public static final java.lang.String KEY_MODEL_UUID = "model_uuid";
        public static final java.lang.String KEY_MODEL_VERSION = "model_version";
        public static final java.lang.String KEY_VENDOR_UUID = "vendor_uuid";
        public static final java.lang.String TABLE = "st_sound_model";
    }

    public SoundTriggerDbHelper(android.content.Context context) {
        super(context, NAME, (android.database.sqlite.SQLiteDatabase.CursorFactory) null, 2);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_TABLE_ST_SOUND_MODEL);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i == 1) {
            android.util.Slog.d(TAG, "Adding model version column");
            sQLiteDatabase.execSQL("ALTER TABLE st_sound_model ADD COLUMN model_version INTEGER DEFAULT -1");
        }
    }

    public boolean updateGenericSoundModel(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel) {
        boolean z;
        synchronized (this) {
            try {
                android.database.sqlite.SQLiteDatabase writableDatabase = getWritableDatabase();
                android.content.ContentValues contentValues = new android.content.ContentValues();
                contentValues.put("model_uuid", genericSoundModel.getUuid().toString());
                contentValues.put("vendor_uuid", genericSoundModel.getVendorUuid().toString());
                contentValues.put("data", genericSoundModel.getData());
                contentValues.put("model_version", java.lang.Integer.valueOf(genericSoundModel.getVersion()));
                try {
                    z = writableDatabase.insertWithOnConflict(com.android.server.soundtrigger.SoundTriggerDbHelper.GenericSoundModelContract.TABLE, null, contentValues, 5) != -1;
                } finally {
                    writableDatabase.close();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public android.hardware.soundtrigger.SoundTrigger.GenericSoundModel getGenericSoundModel(java.util.UUID uuid) {
        synchronized (this) {
            try {
                android.database.sqlite.SQLiteDatabase readableDatabase = getReadableDatabase();
                android.database.Cursor rawQuery = readableDatabase.rawQuery("SELECT  * FROM st_sound_model WHERE model_uuid= '" + uuid + "'", null);
                try {
                    if (rawQuery.moveToFirst()) {
                        android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel = new android.hardware.soundtrigger.SoundTrigger.GenericSoundModel(uuid, java.util.UUID.fromString(rawQuery.getString(rawQuery.getColumnIndex("vendor_uuid"))), rawQuery.getBlob(rawQuery.getColumnIndex("data")), rawQuery.getInt(rawQuery.getColumnIndex("model_version")));
                        rawQuery.close();
                        readableDatabase.close();
                        return genericSoundModel;
                    }
                    rawQuery.close();
                    readableDatabase.close();
                    return null;
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

    public boolean deleteGenericSoundModel(java.util.UUID uuid) {
        synchronized (this) {
            try {
                android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel = getGenericSoundModel(uuid);
                if (genericSoundModel == null) {
                    return false;
                }
                android.database.sqlite.SQLiteDatabase writableDatabase = getWritableDatabase();
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("model_uuid='");
                sb.append(genericSoundModel.getUuid().toString());
                sb.append("'");
                try {
                    return writableDatabase.delete(com.android.server.soundtrigger.SoundTriggerDbHelper.GenericSoundModelContract.TABLE, sb.toString(), null) != 0;
                } finally {
                    writableDatabase.close();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(java.io.PrintWriter printWriter) {
        synchronized (this) {
            try {
                android.database.sqlite.SQLiteDatabase readableDatabase = getReadableDatabase();
                android.database.Cursor rawQuery = readableDatabase.rawQuery("SELECT  * FROM st_sound_model", null);
                try {
                    printWriter.println("  Enrolled GenericSoundModels:");
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
