package com.android.server.people.data;

/* loaded from: classes2.dex */
class CallLogQueryHelper {
    private static final android.util.SparseIntArray CALL_TYPE_TO_EVENT_TYPE = new android.util.SparseIntArray();
    private static final java.lang.String TAG = "CallLogQueryHelper";
    private final android.content.Context mContext;
    private final java.util.function.BiConsumer<java.lang.String, com.android.server.people.data.Event> mEventConsumer;
    private long mLastCallTimestamp;

    static {
        CALL_TYPE_TO_EVENT_TYPE.put(1, 11);
        CALL_TYPE_TO_EVENT_TYPE.put(2, 10);
        CALL_TYPE_TO_EVENT_TYPE.put(3, 12);
    }

    CallLogQueryHelper(android.content.Context context, java.util.function.BiConsumer<java.lang.String, com.android.server.people.data.Event> biConsumer) {
        this.mContext = context;
        this.mEventConsumer = biConsumer;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0090 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[Catch: SecurityException -> 0x003a, SYNTHETIC, TRY_LEAVE, TryCatch #3 {SecurityException -> 0x003a, blocks: (B:3:0x001e, B:42:0x0036, B:30:0x0099, B:29:0x0096, B:34:0x0089, B:23:0x0090), top: B:2:0x001e, inners: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean querySince(long j) {
        java.lang.Throwable th;
        android.database.Cursor cursor;
        java.lang.String string;
        long j2;
        long j3;
        int i;
        try {
            android.database.Cursor query = this.mContext.getContentResolver().query(android.provider.CallLog.Calls.CONTENT_URI, new java.lang.String[]{"normalized_number", "date", "duration", com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE}, "date > ?", new java.lang.String[]{java.lang.Long.toString(j)}, "date DESC");
            if (query != null) {
                boolean z = false;
                while (query.moveToNext()) {
                    try {
                        string = query.getString(query.getColumnIndex("normalized_number"));
                        j2 = query.getLong(query.getColumnIndex("date"));
                        j3 = query.getLong(query.getColumnIndex("duration"));
                        i = query.getInt(query.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE));
                        cursor = query;
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        cursor = query;
                    }
                    try {
                        this.mLastCallTimestamp = java.lang.Math.max(this.mLastCallTimestamp, j2);
                        if (addEvent(string, j2, j3, i)) {
                            z = true;
                        }
                        query = cursor;
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        th = th;
                        if (cursor != null) {
                        }
                    }
                }
                query.close();
                return z;
            }
            try {
                android.util.Slog.w(TAG, "Cursor is null when querying call log.");
                if (query != null) {
                    query.close();
                }
                return false;
            } catch (java.lang.Throwable th4) {
                th = th4;
                cursor = query;
            }
            if (cursor != null) {
                throw th;
            }
            try {
                cursor.close();
                throw th;
            } catch (java.lang.Throwable th5) {
                th.addSuppressed(th5);
                throw th;
            }
        } catch (java.lang.SecurityException e) {
            android.util.Slog.e(TAG, "Query call log failed: " + e);
            return false;
        }
    }

    long getLastCallTimestamp() {
        return this.mLastCallTimestamp;
    }

    private boolean addEvent(java.lang.String str, long j, long j2, int i) {
        if (!validateEvent(str, j, i)) {
            return false;
        }
        this.mEventConsumer.accept(str, new com.android.server.people.data.Event.Builder(j, CALL_TYPE_TO_EVENT_TYPE.get(i)).setDurationSeconds((int) j2).build());
        return true;
    }

    private boolean validateEvent(java.lang.String str, long j, int i) {
        return !android.text.TextUtils.isEmpty(str) && j > 0 && CALL_TYPE_TO_EVENT_TYPE.indexOfKey(i) >= 0;
    }
}
