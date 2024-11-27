package com.android.server.people.data;

/* loaded from: classes2.dex */
class SmsQueryHelper {
    private static final android.util.SparseIntArray SMS_TYPE_TO_EVENT_TYPE = new android.util.SparseIntArray();
    private static final java.lang.String TAG = "SmsQueryHelper";
    private final android.content.Context mContext;
    private final java.lang.String mCurrentCountryIso;
    private final java.util.function.BiConsumer<java.lang.String, com.android.server.people.data.Event> mEventConsumer;
    private long mLastMessageTimestamp;

    static {
        SMS_TYPE_TO_EVENT_TYPE.put(1, 9);
        SMS_TYPE_TO_EVENT_TYPE.put(2, 8);
    }

    SmsQueryHelper(android.content.Context context, java.util.function.BiConsumer<java.lang.String, com.android.server.people.data.Event> biConsumer) {
        this.mContext = context;
        this.mEventConsumer = biConsumer;
        this.mCurrentCountryIso = com.android.server.people.data.Utils.getCurrentCountryIso(this.mContext);
    }

    boolean querySince(long j) {
        java.lang.String[] strArr = {"_id", "date", com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, "address"};
        java.lang.String[] strArr2 = {java.lang.Long.toString(j)};
        android.os.Binder.allowBlockingForCurrentThread();
        try {
            android.database.Cursor query = this.mContext.getContentResolver().query(android.provider.Telephony.Sms.CONTENT_URI, strArr, "date > ?", strArr2, null);
            boolean z = false;
            try {
                if (query == null) {
                    android.util.Slog.w(TAG, "Cursor is null when querying SMS table.");
                    if (query != null) {
                        query.close();
                    }
                    return false;
                }
                while (query.moveToNext()) {
                    query.getString(query.getColumnIndex("_id"));
                    long j2 = query.getLong(query.getColumnIndex("date"));
                    int i = query.getInt(query.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE));
                    java.lang.String formatNumberToE164 = android.telephony.PhoneNumberUtils.formatNumberToE164(query.getString(query.getColumnIndex("address")), this.mCurrentCountryIso);
                    this.mLastMessageTimestamp = java.lang.Math.max(this.mLastMessageTimestamp, j2);
                    if (formatNumberToE164 != null && addEvent(formatNumberToE164, j2, i)) {
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

    private boolean addEvent(java.lang.String str, long j, int i) {
        if (!validateEvent(str, j, i)) {
            return false;
        }
        this.mEventConsumer.accept(str, new com.android.server.people.data.Event(j, SMS_TYPE_TO_EVENT_TYPE.get(i)));
        return true;
    }

    private boolean validateEvent(java.lang.String str, long j, int i) {
        return !android.text.TextUtils.isEmpty(str) && j > 0 && SMS_TYPE_TO_EVENT_TYPE.indexOfKey(i) >= 0;
    }
}
