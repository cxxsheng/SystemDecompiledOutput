package com.android.server.people.data;

/* loaded from: classes2.dex */
class MmsQueryHelper {
    private static final long MILLIS_PER_SECONDS = 1000;
    private static final android.util.SparseIntArray MSG_BOX_TO_EVENT_TYPE = new android.util.SparseIntArray();
    private static final java.lang.String TAG = "MmsQueryHelper";
    private final android.content.Context mContext;
    private java.lang.String mCurrentCountryIso;
    private final java.util.function.BiConsumer<java.lang.String, com.android.server.people.data.Event> mEventConsumer;
    private long mLastMessageTimestamp;

    static {
        MSG_BOX_TO_EVENT_TYPE.put(1, 9);
        MSG_BOX_TO_EVENT_TYPE.put(2, 8);
    }

    MmsQueryHelper(android.content.Context context, java.util.function.BiConsumer<java.lang.String, com.android.server.people.data.Event> biConsumer) {
        this.mContext = context;
        this.mEventConsumer = biConsumer;
        this.mCurrentCountryIso = com.android.server.people.data.Utils.getCurrentCountryIso(this.mContext);
    }

    boolean querySince(long j) {
        java.lang.String[] strArr = {"_id", "date", "msg_box"};
        java.lang.String[] strArr2 = {java.lang.Long.toString(j / 1000)};
        android.os.Binder.allowBlockingForCurrentThread();
        try {
            android.database.Cursor query = this.mContext.getContentResolver().query(android.provider.Telephony.Mms.CONTENT_URI, strArr, "date > ?", strArr2, null);
            boolean z = false;
            try {
                if (query == null) {
                    android.util.Slog.w(TAG, "Cursor is null when querying MMS table.");
                    if (query != null) {
                        query.close();
                    }
                    return false;
                }
                while (query.moveToNext()) {
                    java.lang.String string = query.getString(query.getColumnIndex("_id"));
                    long j2 = query.getLong(query.getColumnIndex("date")) * 1000;
                    int i = query.getInt(query.getColumnIndex("msg_box"));
                    this.mLastMessageTimestamp = java.lang.Math.max(this.mLastMessageTimestamp, j2);
                    java.lang.String mmsAddress = getMmsAddress(string, i);
                    if (mmsAddress != null && addEvent(mmsAddress, j2, i)) {
                        z = true;
                    }
                }
                query.close();
                return z;
            } finally {
            }
        } finally {
            android.os.Binder.defaultBlockingForCurrentThread();
        }
    }

    long getLastMessageTimestamp() {
        return this.mLastMessageTimestamp;
    }

    @android.annotation.Nullable
    private java.lang.String getMmsAddress(java.lang.String str, int i) {
        android.database.Cursor query = this.mContext.getContentResolver().query(android.provider.Telephony.Mms.Addr.getAddrUriForMessage(str), new java.lang.String[]{"address", com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE}, null, null, null);
        try {
            if (query != null) {
                java.lang.String str2 = null;
                while (query.moveToNext()) {
                    int i2 = query.getInt(query.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE));
                    if ((i == 1 && i2 == 137) || (i == 2 && i2 == 151)) {
                        str2 = query.getString(query.getColumnIndex("address"));
                    }
                }
                query.close();
                if (!android.provider.Telephony.Mms.isPhoneNumber(str2)) {
                    return null;
                }
                return android.telephony.PhoneNumberUtils.formatNumberToE164(str2, this.mCurrentCountryIso);
            }
            android.util.Slog.w(TAG, "Cursor is null when querying MMS address table.");
            if (query != null) {
                query.close();
            }
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

    private boolean addEvent(java.lang.String str, long j, int i) {
        if (!validateEvent(str, j, i)) {
            return false;
        }
        this.mEventConsumer.accept(str, new com.android.server.people.data.Event(j, MSG_BOX_TO_EVENT_TYPE.get(i)));
        return true;
    }

    private boolean validateEvent(java.lang.String str, long j, int i) {
        return !android.text.TextUtils.isEmpty(str) && j > 0 && MSG_BOX_TO_EVENT_TYPE.indexOfKey(i) >= 0;
    }
}
