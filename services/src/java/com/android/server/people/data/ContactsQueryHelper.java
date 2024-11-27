package com.android.server.people.data;

/* loaded from: classes2.dex */
class ContactsQueryHelper {
    private static final java.lang.String TAG = "ContactsQueryHelper";
    private android.net.Uri mContactUri;
    private final android.content.Context mContext;
    private boolean mIsStarred;
    private long mLastUpdatedTimestamp;
    private java.lang.String mPhoneNumber;

    ContactsQueryHelper(android.content.Context context) {
        this.mContext = context;
    }

    boolean query(@android.annotation.NonNull java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return false;
        }
        android.net.Uri parse = android.net.Uri.parse(str);
        if ("tel".equals(parse.getScheme())) {
            return queryWithPhoneNumber(parse.getSchemeSpecificPart());
        }
        if ("mailto".equals(parse.getScheme())) {
            return queryWithEmail(parse.getSchemeSpecificPart());
        }
        if (str.startsWith(android.provider.ContactsContract.Contacts.CONTENT_LOOKUP_URI.toString())) {
            return queryWithUri(parse);
        }
        return false;
    }

    boolean querySince(long j) {
        java.lang.String[] strArr = {java.lang.Long.toString(j)};
        return queryContact(android.provider.ContactsContract.Contacts.CONTENT_URI, new java.lang.String[]{"_id", "lookup", "starred", "has_phone_number", "contact_last_updated_timestamp"}, "contact_last_updated_timestamp > ?", strArr);
    }

    @android.annotation.Nullable
    android.net.Uri getContactUri() {
        return this.mContactUri;
    }

    boolean isStarred() {
        return this.mIsStarred;
    }

    @android.annotation.Nullable
    java.lang.String getPhoneNumber() {
        return this.mPhoneNumber;
    }

    long getLastUpdatedTimestamp() {
        return this.mLastUpdatedTimestamp;
    }

    private boolean queryWithPhoneNumber(java.lang.String str) {
        return queryWithUri(android.net.Uri.withAppendedPath(android.provider.ContactsContract.PhoneLookup.CONTENT_FILTER_URI, android.net.Uri.encode(str)));
    }

    private boolean queryWithEmail(java.lang.String str) {
        return queryWithUri(android.net.Uri.withAppendedPath(android.provider.ContactsContract.CommonDataKinds.Email.CONTENT_LOOKUP_URI, android.net.Uri.encode(str)));
    }

    private boolean queryWithUri(@android.annotation.NonNull android.net.Uri uri) {
        return queryContact(uri, new java.lang.String[]{"_id", "lookup", "starred", "has_phone_number"}, null, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00b7 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean queryContact(@android.annotation.NonNull android.net.Uri uri, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String[] strArr2) {
        boolean z;
        boolean z2;
        android.database.Cursor query;
        java.lang.String str2 = null;
        boolean z3 = false;
        try {
            query = this.mContext.getContentResolver().query(uri, strArr, str, strArr2, null);
        } catch (android.database.sqlite.SQLiteException e) {
            e = e;
            z = false;
        } catch (java.lang.IllegalArgumentException e2) {
            e = e2;
            z = false;
        }
        if (query != null) {
            z2 = false;
            z = false;
            while (query.moveToNext()) {
                try {
                    long j = query.getLong(query.getColumnIndex("_id"));
                    str2 = query.getString(query.getColumnIndex("lookup"));
                    this.mContactUri = android.provider.ContactsContract.Contacts.getLookupUri(j, str2);
                    this.mIsStarred = query.getInt(query.getColumnIndex("starred")) != 0;
                    z2 = query.getInt(query.getColumnIndex("has_phone_number")) != 0;
                    int columnIndex = query.getColumnIndex("contact_last_updated_timestamp");
                    if (columnIndex >= 0) {
                        this.mLastUpdatedTimestamp = query.getLong(columnIndex);
                    }
                    z = true;
                } catch (java.lang.Throwable th) {
                    z3 = z2;
                    th = th;
                }
            }
            try {
                query.close();
            } catch (android.database.sqlite.SQLiteException e3) {
                e = e3;
                z3 = z2;
                android.util.Slog.w("SQLite exception when querying contacts.", e);
                z2 = z3;
                if (z) {
                }
            } catch (java.lang.IllegalArgumentException e4) {
                e = e4;
                z3 = z2;
                android.util.Slog.w("Illegal Argument exception when querying contacts.", e);
                z2 = z3;
                if (z) {
                }
            }
            return (z || str2 == null || !z2) ? z : queryPhoneNumber(str2);
        }
        try {
            android.util.Slog.w(TAG, "Cursor is null when querying contact.");
            if (query != null) {
                query.close();
            }
            return false;
        } catch (java.lang.Throwable th2) {
            th = th2;
            z = false;
        }
        if (query != null) {
            try {
                query.close();
            } catch (java.lang.Throwable th3) {
                try {
                    th.addSuppressed(th3);
                } catch (android.database.sqlite.SQLiteException e5) {
                    e = e5;
                    android.util.Slog.w("SQLite exception when querying contacts.", e);
                    z2 = z3;
                    if (z) {
                    }
                } catch (java.lang.IllegalArgumentException e6) {
                    e = e6;
                    android.util.Slog.w("Illegal Argument exception when querying contacts.", e);
                    z2 = z3;
                    if (z) {
                    }
                }
            }
        }
        throw th;
    }

    private boolean queryPhoneNumber(java.lang.String str) {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        android.net.Uri uri = android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        android.database.Cursor query = contentResolver.query(uri, new java.lang.String[]{"data4"}, "lookup = ?", new java.lang.String[]{str}, null);
        try {
            if (query == null) {
                android.util.Slog.w(TAG, "Cursor is null when querying contact phone number.");
                if (query != null) {
                    query.close();
                    return false;
                }
                return false;
            }
            while (query.moveToNext()) {
                int columnIndex = query.getColumnIndex("data4");
                if (columnIndex >= 0) {
                    this.mPhoneNumber = query.getString(columnIndex);
                }
            }
            query.close();
            return true;
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
}
