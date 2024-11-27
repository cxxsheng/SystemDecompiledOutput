package android.app;

/* loaded from: classes.dex */
public class DownloadManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final java.lang.String ACTION_DOWNLOAD_COMPLETE = "android.intent.action.DOWNLOAD_COMPLETE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_DOWNLOAD_COMPLETED = "android.intent.action.DOWNLOAD_COMPLETED";
    public static final java.lang.String ACTION_NOTIFICATION_CLICKED = "android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED";
    public static final java.lang.String ACTION_VIEW_DOWNLOADS = "android.intent.action.VIEW_DOWNLOADS";
    public static final java.lang.String COLUMN_ALLOW_WRITE = "allow_write";
    public static final java.lang.String COLUMN_DESCRIPTION = "description";
    public static final java.lang.String COLUMN_DESTINATION = "destination";
    public static final java.lang.String COLUMN_FILE_NAME_HINT = "hint";
    public static final java.lang.String COLUMN_ID = "_id";
    public static final java.lang.String COLUMN_MEDIAPROVIDER_URI = "mediaprovider_uri";
    public static final java.lang.String COLUMN_MEDIASTORE_URI = "mediastore_uri";
    public static final java.lang.String COLUMN_REASON = "reason";
    public static final java.lang.String COLUMN_STATUS = "status";
    public static final java.lang.String COLUMN_TITLE = "title";
    public static final java.lang.String COLUMN_URI = "uri";
    public static final int ERROR_BLOCKED = 1010;
    public static final int ERROR_CANNOT_RESUME = 1008;
    public static final int ERROR_DEVICE_NOT_FOUND = 1007;
    public static final int ERROR_FILE_ALREADY_EXISTS = 1009;
    public static final int ERROR_FILE_ERROR = 1001;
    public static final int ERROR_HTTP_DATA_ERROR = 1004;
    public static final int ERROR_INSUFFICIENT_SPACE = 1006;
    public static final int ERROR_TOO_MANY_REDIRECTS = 1005;
    public static final int ERROR_UNHANDLED_HTTP_CODE = 1002;
    public static final int ERROR_UNKNOWN = 1000;
    public static final java.lang.String EXTRA_DOWNLOAD_ID = "extra_download_id";
    public static final java.lang.String EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS = "extra_click_download_ids";
    public static final java.lang.String INTENT_EXTRAS_SORT_BY_SIZE = "android.app.DownloadManager.extra_sortBySize";
    private static final java.lang.String NON_DOWNLOADMANAGER_DOWNLOAD = "non-dwnldmngr-download-dont-retry2download";
    public static final int PAUSED_MANUAL = 5;
    public static final int PAUSED_QUEUED_FOR_WIFI = 3;
    public static final int PAUSED_UNKNOWN = 4;
    public static final int PAUSED_WAITING_FOR_NETWORK = 2;
    public static final int PAUSED_WAITING_TO_RETRY = 1;
    public static final int STATUS_FAILED = 16;
    public static final int STATUS_PAUSED = 4;
    public static final int STATUS_PENDING = 1;
    public static final int STATUS_RUNNING = 2;
    public static final int STATUS_SUCCESSFUL = 8;
    private boolean mAccessFilename;
    private android.net.Uri mBaseUri = android.provider.Downloads.Impl.CONTENT_URI;
    private final java.lang.String mPackageName;
    private final android.content.ContentResolver mResolver;

    @java.lang.Deprecated
    public static final java.lang.String COLUMN_LOCAL_FILENAME = "local_filename";
    public static final java.lang.String COLUMN_MEDIA_TYPE = "media_type";
    public static final java.lang.String COLUMN_TOTAL_SIZE_BYTES = "total_size";
    public static final java.lang.String COLUMN_LAST_MODIFIED_TIMESTAMP = "last_modified_timestamp";
    public static final java.lang.String COLUMN_BYTES_DOWNLOADED_SO_FAR = "bytes_so_far";
    public static final java.lang.String COLUMN_LOCAL_URI = "local_uri";
    public static final java.lang.String[] UNDERLYING_COLUMNS = {"_id", COLUMN_LOCAL_FILENAME, "mediaprovider_uri", "destination", "title", "description", "uri", "status", "hint", COLUMN_MEDIA_TYPE, COLUMN_TOTAL_SIZE_BYTES, COLUMN_LAST_MODIFIED_TIMESTAMP, COLUMN_BYTES_DOWNLOADED_SO_FAR, "allow_write", COLUMN_LOCAL_URI, "reason"};

    public static class Request {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        @java.lang.Deprecated
        public static final int NETWORK_BLUETOOTH = 4;
        public static final int NETWORK_MOBILE = 1;
        public static final int NETWORK_WIFI = 2;
        private static final int SCANNABLE_VALUE_NO = 2;
        private static final int SCANNABLE_VALUE_YES = 0;
        public static final int VISIBILITY_HIDDEN = 2;
        public static final int VISIBILITY_VISIBLE = 0;
        public static final int VISIBILITY_VISIBLE_NOTIFY_COMPLETED = 1;
        public static final int VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION = 3;
        private java.lang.CharSequence mDescription;
        private android.net.Uri mDestinationUri;
        private java.lang.String mMimeType;
        private java.lang.CharSequence mTitle;
        private android.net.Uri mUri;
        private java.util.List<android.util.Pair<java.lang.String, java.lang.String>> mRequestHeaders = new java.util.ArrayList();
        private int mAllowedNetworkTypes = -1;
        private boolean mRoamingAllowed = true;
        private boolean mMeteredAllowed = true;
        private int mFlags = 0;
        private boolean mIsVisibleInDownloadsUi = true;
        private boolean mScannable = false;
        private int mNotificationVisibility = 0;

        public Request(android.net.Uri uri) {
            if (uri == null) {
                throw new java.lang.NullPointerException();
            }
            java.lang.String scheme = uri.getScheme();
            if (scheme == null || (!scheme.equals(android.content.IntentFilter.SCHEME_HTTP) && !scheme.equals(android.content.IntentFilter.SCHEME_HTTPS))) {
                throw new java.lang.IllegalArgumentException("Can only download HTTP/HTTPS URIs: " + uri);
            }
            this.mUri = uri;
        }

        Request(java.lang.String str) {
            this.mUri = android.net.Uri.parse(str);
        }

        public android.app.DownloadManager.Request setDestinationUri(android.net.Uri uri) {
            this.mDestinationUri = uri;
            return this;
        }

        public android.app.DownloadManager.Request setDestinationInExternalFilesDir(android.content.Context context, java.lang.String str, java.lang.String str2) {
            java.io.File externalFilesDir = context.getExternalFilesDir(str);
            if (externalFilesDir == null) {
                throw new java.lang.IllegalStateException("Failed to get external storage files directory");
            }
            if (externalFilesDir.exists()) {
                if (!externalFilesDir.isDirectory()) {
                    throw new java.lang.IllegalStateException(externalFilesDir.getAbsolutePath() + " already exists and is not a directory");
                }
            } else if (!externalFilesDir.mkdirs()) {
                throw new java.lang.IllegalStateException("Unable to create directory: " + externalFilesDir.getAbsolutePath());
            }
            setDestinationFromBase(externalFilesDir, str2);
            return this;
        }

        public android.app.DownloadManager.Request setDestinationInExternalPublicDir(java.lang.String str, java.lang.String str2) {
            java.io.File externalStoragePublicDirectory = android.os.Environment.getExternalStoragePublicDirectory(str);
            if (externalStoragePublicDirectory == null) {
                throw new java.lang.IllegalStateException("Failed to get external storage public directory");
            }
            android.app.Application initialApplication = android.app.AppGlobals.getInitialApplication();
            if (initialApplication.getApplicationInfo().targetSdkVersion >= 29 || !android.os.Environment.isExternalStorageLegacy()) {
                try {
                    android.content.ContentProviderClient acquireContentProviderClient = initialApplication.getContentResolver().acquireContentProviderClient("downloads");
                    try {
                        android.os.Bundle bundle = new android.os.Bundle();
                        bundle.putString(android.provider.Downloads.DIR_TYPE, str);
                        acquireContentProviderClient.call(android.provider.Downloads.CALL_CREATE_EXTERNAL_PUBLIC_DIR, null, bundle);
                        if (acquireContentProviderClient != null) {
                            acquireContentProviderClient.close();
                        }
                    } finally {
                    }
                } catch (android.os.RemoteException e) {
                    throw new java.lang.IllegalStateException("Unable to create directory: " + externalStoragePublicDirectory.getAbsolutePath(), e);
                }
            } else if (externalStoragePublicDirectory.exists()) {
                if (!externalStoragePublicDirectory.isDirectory()) {
                    throw new java.lang.IllegalStateException(externalStoragePublicDirectory.getAbsolutePath() + " already exists and is not a directory");
                }
            } else if (!externalStoragePublicDirectory.mkdirs()) {
                throw new java.lang.IllegalStateException("Unable to create directory: " + externalStoragePublicDirectory.getAbsolutePath());
            }
            setDestinationFromBase(externalStoragePublicDirectory, str2);
            return this;
        }

        private void setDestinationFromBase(java.io.File file, java.lang.String str) {
            if (str == null) {
                throw new java.lang.NullPointerException("subPath cannot be null");
            }
            this.mDestinationUri = android.net.Uri.withAppendedPath(android.net.Uri.fromFile(file), str);
        }

        @java.lang.Deprecated
        public void allowScanningByMediaScanner() {
            this.mScannable = true;
        }

        public android.app.DownloadManager.Request addRequestHeader(java.lang.String str, java.lang.String str2) {
            if (str == null) {
                throw new java.lang.NullPointerException("header cannot be null");
            }
            if (str.contains(":")) {
                throw new java.lang.IllegalArgumentException("header may not contain ':'");
            }
            if (str2 == null) {
                str2 = "";
            }
            this.mRequestHeaders.add(android.util.Pair.create(str, str2));
            return this;
        }

        public android.app.DownloadManager.Request setTitle(java.lang.CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }

        public android.app.DownloadManager.Request setDescription(java.lang.CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public android.app.DownloadManager.Request setMimeType(java.lang.String str) {
            this.mMimeType = str;
            return this;
        }

        @java.lang.Deprecated
        public android.app.DownloadManager.Request setShowRunningNotification(boolean z) {
            return z ? setNotificationVisibility(0) : setNotificationVisibility(2);
        }

        public android.app.DownloadManager.Request setNotificationVisibility(int i) {
            this.mNotificationVisibility = i;
            return this;
        }

        public android.app.DownloadManager.Request setAllowedNetworkTypes(int i) {
            this.mAllowedNetworkTypes = i;
            return this;
        }

        public android.app.DownloadManager.Request setAllowedOverRoaming(boolean z) {
            this.mRoamingAllowed = z;
            return this;
        }

        public android.app.DownloadManager.Request setAllowedOverMetered(boolean z) {
            this.mMeteredAllowed = z;
            return this;
        }

        public android.app.DownloadManager.Request setRequiresCharging(boolean z) {
            if (z) {
                this.mFlags |= 1;
            } else {
                this.mFlags &= -2;
            }
            return this;
        }

        public android.app.DownloadManager.Request setRequiresDeviceIdle(boolean z) {
            if (z) {
                this.mFlags |= 2;
            } else {
                this.mFlags &= -3;
            }
            return this;
        }

        @java.lang.Deprecated
        public android.app.DownloadManager.Request setVisibleInDownloadsUi(boolean z) {
            this.mIsVisibleInDownloadsUi = z;
            return this;
        }

        android.content.ContentValues toContentValues(java.lang.String str) {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("uri", this.mUri.toString());
            contentValues.put(android.provider.Downloads.Impl.COLUMN_IS_PUBLIC_API, (java.lang.Boolean) true);
            contentValues.put(android.provider.Downloads.Impl.COLUMN_NOTIFICATION_PACKAGE, str);
            if (this.mDestinationUri != null) {
                contentValues.put("destination", (java.lang.Integer) 4);
                contentValues.put("hint", this.mDestinationUri.toString());
            } else {
                contentValues.put("destination", (java.lang.Integer) 2);
            }
            contentValues.put(android.provider.Downloads.Impl.COLUMN_MEDIA_SCANNED, java.lang.Integer.valueOf(this.mScannable ? 0 : 2));
            if (!this.mRequestHeaders.isEmpty()) {
                encodeHttpHeaders(contentValues);
            }
            putIfNonNull(contentValues, "title", this.mTitle);
            putIfNonNull(contentValues, "description", this.mDescription);
            putIfNonNull(contentValues, "mimetype", this.mMimeType);
            contentValues.put(android.provider.Downloads.Impl.COLUMN_VISIBILITY, java.lang.Integer.valueOf(this.mNotificationVisibility));
            contentValues.put("allowed_network_types", java.lang.Integer.valueOf(this.mAllowedNetworkTypes));
            contentValues.put(android.provider.Downloads.Impl.COLUMN_ALLOW_ROAMING, java.lang.Boolean.valueOf(this.mRoamingAllowed));
            contentValues.put("allow_metered", java.lang.Boolean.valueOf(this.mMeteredAllowed));
            contentValues.put("flags", java.lang.Integer.valueOf(this.mFlags));
            contentValues.put(android.provider.Downloads.Impl.COLUMN_IS_VISIBLE_IN_DOWNLOADS_UI, java.lang.Boolean.valueOf(this.mIsVisibleInDownloadsUi));
            return contentValues;
        }

        private void encodeHttpHeaders(android.content.ContentValues contentValues) {
            int i = 0;
            for (android.util.Pair<java.lang.String, java.lang.String> pair : this.mRequestHeaders) {
                contentValues.put(android.provider.Downloads.Impl.RequestHeaders.INSERT_KEY_PREFIX + i, pair.first + ": " + pair.second);
                i++;
            }
        }

        private void putIfNonNull(android.content.ContentValues contentValues, java.lang.String str, java.lang.Object obj) {
            if (obj != null) {
                contentValues.put(str, obj.toString());
            }
        }
    }

    public static class Query {
        public static final int ORDER_ASCENDING = 1;
        public static final int ORDER_DESCENDING = 2;
        private long[] mIds = null;
        private java.lang.Integer mStatusFlags = null;
        private java.lang.String mFilterString = null;
        private java.lang.String mOrderByColumn = android.provider.Downloads.Impl.COLUMN_LAST_MODIFICATION;
        private int mOrderDirection = 2;
        private boolean mOnlyIncludeVisibleInDownloadsUi = false;

        public android.app.DownloadManager.Query setFilterById(long... jArr) {
            this.mIds = jArr;
            return this;
        }

        public android.app.DownloadManager.Query setFilterByString(java.lang.String str) {
            this.mFilterString = str;
            return this;
        }

        public android.app.DownloadManager.Query setFilterByStatus(int i) {
            this.mStatusFlags = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.app.DownloadManager.Query setOnlyIncludeVisibleInDownloadsUi(boolean z) {
            this.mOnlyIncludeVisibleInDownloadsUi = z;
            return this;
        }

        public android.app.DownloadManager.Query orderBy(java.lang.String str, int i) {
            if (i != 1 && i != 2) {
                throw new java.lang.IllegalArgumentException("Invalid direction: " + i);
            }
            if (str.equals(android.app.DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)) {
                this.mOrderByColumn = android.provider.Downloads.Impl.COLUMN_LAST_MODIFICATION;
            } else if (str.equals(android.app.DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) {
                this.mOrderByColumn = android.provider.Downloads.Impl.COLUMN_TOTAL_BYTES;
            } else {
                throw new java.lang.IllegalArgumentException("Cannot order by " + str);
            }
            this.mOrderDirection = i;
            return this;
        }

        android.database.Cursor runQuery(android.content.ContentResolver contentResolver, java.lang.String[] strArr, android.net.Uri uri) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int length = this.mIds == null ? 0 : this.mIds.length;
            if (this.mFilterString != null) {
                length++;
            }
            java.lang.String[] strArr2 = new java.lang.String[length];
            if (length > 0) {
                if (this.mIds != null) {
                    arrayList.add(android.app.DownloadManager.getWhereClauseForIds(this.mIds));
                    android.app.DownloadManager.getWhereArgsForIds(this.mIds, strArr2);
                }
                if (this.mFilterString != null) {
                    arrayList.add("title LIKE ?");
                    strArr2[length - 1] = "%" + this.mFilterString + "%";
                }
            }
            if (this.mStatusFlags != null) {
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                if ((this.mStatusFlags.intValue() & 1) != 0) {
                    arrayList2.add(statusClause("=", 190));
                }
                if ((this.mStatusFlags.intValue() & 2) != 0) {
                    arrayList2.add(statusClause("=", 192));
                }
                if ((this.mStatusFlags.intValue() & 4) != 0) {
                    arrayList2.add(statusClause("=", 193));
                    arrayList2.add(statusClause("=", 194));
                    arrayList2.add(statusClause("=", 195));
                    arrayList2.add(statusClause("=", 196));
                    arrayList2.add(statusClause("=", 197));
                }
                if ((this.mStatusFlags.intValue() & 8) != 0) {
                    arrayList2.add(statusClause("=", 200));
                }
                if ((this.mStatusFlags.intValue() & 16) != 0) {
                    arrayList2.add(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + statusClause(">=", 400) + " AND " + statusClause("<", 600) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                }
                arrayList.add(joinStrings(" OR ", arrayList2));
            }
            if (this.mOnlyIncludeVisibleInDownloadsUi) {
                arrayList.add("is_visible_in_downloads_ui != '0'");
            }
            arrayList.add("deleted != '1'");
            return contentResolver.query(uri, strArr, joinStrings(" AND ", arrayList), strArr2, this.mOrderByColumn + " " + (this.mOrderDirection == 1 ? "ASC" : "DESC"));
        }

        private java.lang.String joinStrings(java.lang.String str, java.lang.Iterable<java.lang.String> iterable) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            boolean z = true;
            for (java.lang.String str2 : iterable) {
                if (!z) {
                    sb.append(str);
                }
                sb.append(str2);
                z = false;
            }
            return sb.toString();
        }

        private java.lang.String statusClause(java.lang.String str, int i) {
            return "status" + str + "'" + i + "'";
        }
    }

    public DownloadManager(android.content.Context context) {
        this.mResolver = context.getContentResolver();
        this.mPackageName = context.getPackageName();
        this.mAccessFilename = context.getApplicationInfo().targetSdkVersion < 24;
    }

    public void setAccessAllDownloads(boolean z) {
        if (z) {
            this.mBaseUri = android.provider.Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI;
        } else {
            this.mBaseUri = android.provider.Downloads.Impl.CONTENT_URI;
        }
    }

    public void setAccessFilename(boolean z) {
        this.mAccessFilename = z;
    }

    @android.annotation.SystemApi
    public void onMediaStoreDownloadsDeleted(android.util.LongSparseArray<java.lang.String> longSparseArray) {
        try {
            android.content.ContentProviderClient acquireUnstableContentProviderClient = this.mResolver.acquireUnstableContentProviderClient(this.mBaseUri);
            try {
                android.os.Bundle bundle = new android.os.Bundle();
                long[] jArr = new long[longSparseArray.size()];
                java.lang.String[] strArr = new java.lang.String[longSparseArray.size()];
                for (int size = longSparseArray.size() - 1; size >= 0; size--) {
                    jArr[size] = longSparseArray.keyAt(size);
                    strArr[size] = longSparseArray.valueAt(size);
                }
                bundle.putLongArray(android.provider.Downloads.EXTRA_IDS, jArr);
                bundle.putStringArray("mime_types", strArr);
                acquireUnstableContentProviderClient.call(android.provider.Downloads.CALL_MEDIASTORE_DOWNLOADS_DELETED, null, bundle);
                if (acquireUnstableContentProviderClient != null) {
                    acquireUnstableContentProviderClient.close();
                }
            } finally {
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public long enqueue(android.app.DownloadManager.Request request) {
        android.net.Uri insert = this.mResolver.insert(android.provider.Downloads.Impl.CONTENT_URI, request.toContentValues(this.mPackageName));
        if (insert == null) {
            return -1L;
        }
        return java.lang.Long.parseLong(insert.getLastPathSegment());
    }

    public int markRowDeleted(long... jArr) {
        if (jArr == null || jArr.length == 0) {
            throw new java.lang.IllegalArgumentException("input param 'ids' can't be null");
        }
        return this.mResolver.delete(this.mBaseUri, getWhereClauseForIds(jArr), getWhereArgsForIds(jArr));
    }

    public int remove(long... jArr) {
        return markRowDeleted(jArr);
    }

    public android.database.Cursor query(android.app.DownloadManager.Query query) {
        return query(query, UNDERLYING_COLUMNS);
    }

    public android.database.Cursor query(android.app.DownloadManager.Query query, java.lang.String[] strArr) {
        android.database.Cursor runQuery = query.runQuery(this.mResolver, strArr, this.mBaseUri);
        if (runQuery == null) {
            return null;
        }
        return new android.app.DownloadManager.CursorTranslator(runQuery, this.mBaseUri, this.mAccessFilename);
    }

    public android.os.ParcelFileDescriptor openDownloadedFile(long j) throws java.io.FileNotFoundException {
        return this.mResolver.openFileDescriptor(getDownloadUri(j), "r");
    }

    public android.net.Uri getUriForDownloadedFile(long j) {
        android.database.Cursor cursor = null;
        try {
            android.database.Cursor query = query(new android.app.DownloadManager.Query().setFilterById(j));
            if (query == null) {
                if (query != null) {
                    query.close();
                }
                return null;
            }
            try {
                if (!query.moveToFirst() || 8 != query.getInt(query.getColumnIndexOrThrow("status"))) {
                    if (query != null) {
                        query.close();
                    }
                    return null;
                }
                android.net.Uri withAppendedId = android.content.ContentUris.withAppendedId(android.provider.Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI, j);
                if (query != null) {
                    query.close();
                }
                return withAppendedId;
            } catch (java.lang.Throwable th) {
                th = th;
                cursor = query;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    public java.lang.String getMimeTypeForDownloadedFile(long j) {
        java.lang.Throwable th;
        android.database.Cursor cursor;
        try {
            cursor = query(new android.app.DownloadManager.Query().setFilterById(j));
            if (cursor == null) {
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            }
            try {
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
                java.lang.String string = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEDIA_TYPE));
                if (cursor != null) {
                    cursor.close();
                }
                return string;
            } catch (java.lang.Throwable th2) {
                th = th2;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (java.lang.Throwable th3) {
            th = th3;
            cursor = null;
        }
    }

    public void restartDownload(long... jArr) {
        android.database.Cursor query = query(new android.app.DownloadManager.Query().setFilterById(jArr));
        try {
            query.moveToFirst();
            while (!query.isAfterLast()) {
                int i = query.getInt(query.getColumnIndex("status"));
                if (i != 8 && i != 16) {
                    throw new java.lang.IllegalArgumentException("Cannot restart incomplete download: " + query.getLong(query.getColumnIndex("_id")));
                }
                query.moveToNext();
            }
            query.close();
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put(android.provider.Downloads.Impl.COLUMN_CURRENT_BYTES, (java.lang.Integer) 0);
            contentValues.put(android.provider.Downloads.Impl.COLUMN_TOTAL_BYTES, (java.lang.Integer) (-1));
            contentValues.putNull("_data");
            contentValues.put("status", (java.lang.Integer) 190);
            contentValues.put(android.provider.Downloads.Impl.COLUMN_FAILED_CONNECTIONS, (java.lang.Integer) 0);
            this.mResolver.update(this.mBaseUri, contentValues, getWhereClauseForIds(jArr), getWhereArgsForIds(jArr));
        } catch (java.lang.Throwable th) {
            query.close();
            throw th;
        }
    }

    public void forceDownload(long... jArr) {
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("status", (java.lang.Integer) 190);
        contentValues.put(android.provider.Downloads.Impl.COLUMN_CONTROL, (java.lang.Integer) 0);
        contentValues.put(android.provider.Downloads.Impl.COLUMN_BYPASS_RECOMMENDED_SIZE_LIMIT, (java.lang.Integer) 1);
        this.mResolver.update(this.mBaseUri, contentValues, getWhereClauseForIds(jArr), getWhereArgsForIds(jArr));
    }

    public int pauseDownload(long j) {
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("status", (java.lang.Integer) 197);
        return this.mResolver.update(android.content.ContentUris.withAppendedId(this.mBaseUri, j), contentValues, null, null);
    }

    public int resumeDownload(long j) {
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("status", (java.lang.Integer) 192);
        return this.mResolver.update(android.content.ContentUris.withAppendedId(this.mBaseUri, j), contentValues, null, null);
    }

    public static java.lang.Long getMaxBytesOverMobile(android.content.Context context) {
        try {
            return java.lang.Long.valueOf(android.provider.Settings.Global.getLong(context.getContentResolver(), android.provider.Settings.Global.DOWNLOAD_MAX_BYTES_OVER_MOBILE));
        } catch (android.provider.Settings.SettingNotFoundException e) {
            return null;
        }
    }

    public boolean rename(android.content.Context context, long j, java.lang.String str) {
        if (!android.os.FileUtils.isValidFatFilename(str)) {
            throw new java.lang.SecurityException(str + " is not a valid filename");
        }
        android.database.Cursor query = query(new android.app.DownloadManager.Query().setFilterById(j));
        try {
            if (query == null) {
                throw new java.lang.IllegalStateException("Missing cursor for download id=" + j);
            }
            if (query.moveToFirst()) {
                if (query.getInt(query.getColumnIndexOrThrow("status")) != 8) {
                    throw new java.lang.IllegalStateException("Download is not completed yet: " + android.database.DatabaseUtils.dumpCurrentRowToString(query));
                }
                java.lang.String string = query.getString(query.getColumnIndexOrThrow(COLUMN_LOCAL_FILENAME));
                if (string == null) {
                    throw new java.lang.IllegalStateException("Download doesn't have a valid file path: " + android.database.DatabaseUtils.dumpCurrentRowToString(query));
                }
                if (!new java.io.File(string).exists()) {
                    throw new java.lang.IllegalStateException("Downloaded file doesn't exist anymore: " + android.database.DatabaseUtils.dumpCurrentRowToString(query));
                }
                if (query != null) {
                    query.close();
                }
                java.io.File file = new java.io.File(string);
                java.io.File file2 = new java.io.File(file.getParentFile(), str);
                if (file2.exists()) {
                    throw new java.lang.IllegalStateException("File already exists: " + file2);
                }
                if (!file.renameTo(file2)) {
                    throw new java.lang.IllegalStateException("Failed to rename file from " + file + " to " + file2);
                }
                android.provider.MediaStore.scanFile(this.mResolver, file);
                android.provider.MediaStore.scanFile(this.mResolver, file2);
                android.content.ContentValues contentValues = new android.content.ContentValues();
                contentValues.put("title", str);
                contentValues.put("_data", file2.toString());
                contentValues.putNull("mediaprovider_uri");
                long[] jArr = {j};
                return this.mResolver.update(this.mBaseUri, contentValues, getWhereClauseForIds(jArr), getWhereArgsForIds(jArr)) == 1;
            }
            throw new java.lang.IllegalStateException("Missing download id=" + j);
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

    public static java.lang.Long getRecommendedMaxBytesOverMobile(android.content.Context context) {
        try {
            return java.lang.Long.valueOf(android.provider.Settings.Global.getLong(context.getContentResolver(), android.provider.Settings.Global.DOWNLOAD_RECOMMENDED_MAX_BYTES_OVER_MOBILE));
        } catch (android.provider.Settings.SettingNotFoundException e) {
            return null;
        }
    }

    public static boolean isActiveNetworkExpensive(android.content.Context context) {
        return false;
    }

    public static long getActiveNetworkWarningBytes(android.content.Context context) {
        return -1L;
    }

    @java.lang.Deprecated
    public long addCompletedDownload(java.lang.String str, java.lang.String str2, boolean z, java.lang.String str3, java.lang.String str4, long j, boolean z2) {
        return addCompletedDownload(str, str2, z, str3, str4, j, z2, false, null, null);
    }

    @java.lang.Deprecated
    public long addCompletedDownload(java.lang.String str, java.lang.String str2, boolean z, java.lang.String str3, java.lang.String str4, long j, boolean z2, android.net.Uri uri, android.net.Uri uri2) {
        return addCompletedDownload(str, str2, z, str3, str4, j, z2, false, uri, uri2);
    }

    @java.lang.Deprecated
    public long addCompletedDownload(java.lang.String str, java.lang.String str2, boolean z, java.lang.String str3, java.lang.String str4, long j, boolean z2, boolean z3) {
        return addCompletedDownload(str, str2, z, str3, str4, j, z2, z3, null, null);
    }

    @java.lang.Deprecated
    public long addCompletedDownload(java.lang.String str, java.lang.String str2, boolean z, java.lang.String str3, java.lang.String str4, long j, boolean z2, boolean z3, android.net.Uri uri, android.net.Uri uri2) {
        android.app.DownloadManager.Request request;
        validateArgumentIsNonEmpty("title", str);
        validateArgumentIsNonEmpty("description", str2);
        validateArgumentIsNonEmpty("path", str4);
        validateArgumentIsNonEmpty("mimeType", str3);
        if (j < 0) {
            throw new java.lang.IllegalArgumentException(" invalid value for param: totalBytes");
        }
        if (uri != null) {
            request = new android.app.DownloadManager.Request(uri);
        } else {
            request = new android.app.DownloadManager.Request(NON_DOWNLOADMANAGER_DOWNLOAD);
        }
        request.setTitle(str).setDescription(str2).setMimeType(str3);
        if (uri2 != null) {
            request.addRequestHeader("Referer", uri2.toString());
        }
        android.content.ContentValues contentValues = request.toContentValues(null);
        contentValues.put("destination", (java.lang.Integer) 6);
        contentValues.put("_data", str4);
        contentValues.put("mimetype", resolveMimeType(new java.io.File(str4)));
        contentValues.put("status", (java.lang.Integer) 200);
        contentValues.put(android.provider.Downloads.Impl.COLUMN_TOTAL_BYTES, java.lang.Long.valueOf(j));
        int i = 2;
        contentValues.put(android.provider.Downloads.Impl.COLUMN_MEDIA_SCANNED, java.lang.Integer.valueOf(z ? 0 : 2));
        if (z2) {
            i = 3;
        }
        contentValues.put(android.provider.Downloads.Impl.COLUMN_VISIBILITY, java.lang.Integer.valueOf(i));
        contentValues.put("allow_write", java.lang.Integer.valueOf(z3 ? 1 : 0));
        android.net.Uri insert = this.mResolver.insert(android.provider.Downloads.Impl.CONTENT_URI, contentValues);
        if (insert == null) {
            return -1L;
        }
        return java.lang.Long.parseLong(insert.getLastPathSegment());
    }

    private static java.lang.String resolveMimeType(java.io.File file) {
        java.lang.String mimeTypeFromExtension;
        java.lang.String extractFileExtension = extractFileExtension(file.getPath());
        if (extractFileExtension == null || (mimeTypeFromExtension = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extractFileExtension.toLowerCase(java.util.Locale.ROOT))) == null) {
            return "application/octet-stream";
        }
        return mimeTypeFromExtension;
    }

    private static java.lang.String extractDisplayName(java.lang.String str) {
        if (str == null) {
            return null;
        }
        if (str.indexOf(47) == -1) {
            return str;
        }
        if (str.endsWith("/")) {
            str = str.substring(0, str.length() - 1);
        }
        return str.substring(str.lastIndexOf(47) + 1);
    }

    private static java.lang.String extractFileExtension(java.lang.String str) {
        java.lang.String extractDisplayName;
        int lastIndexOf;
        if (str == null || (lastIndexOf = (extractDisplayName = extractDisplayName(str)).lastIndexOf(46)) == -1) {
            return null;
        }
        return extractDisplayName.substring(lastIndexOf + 1);
    }

    private static void validateArgumentIsNonEmpty(java.lang.String str, java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str2)) {
            throw new java.lang.IllegalArgumentException(str + " can't be null");
        }
    }

    public android.net.Uri getDownloadUri(long j) {
        return android.content.ContentUris.withAppendedId(android.provider.Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI, j);
    }

    static java.lang.String getWhereClauseForIds(long[] jArr) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
        for (int i = 0; i < jArr.length; i++) {
            if (i > 0) {
                sb.append("OR ");
            }
            sb.append("_id");
            sb.append(" = ? ");
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    static java.lang.String[] getWhereArgsForIds(long[] jArr) {
        return getWhereArgsForIds(jArr, new java.lang.String[jArr.length]);
    }

    static java.lang.String[] getWhereArgsForIds(long[] jArr, java.lang.String[] strArr) {
        for (int i = 0; i < jArr.length; i++) {
            strArr[i] = java.lang.Long.toString(jArr[i]);
        }
        return strArr;
    }

    private static class CursorTranslator extends android.database.CursorWrapper {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final boolean mAccessFilename;
        private final android.net.Uri mBaseUri;

        public CursorTranslator(android.database.Cursor cursor, android.net.Uri uri, boolean z) {
            super(cursor);
            this.mBaseUri = uri;
            this.mAccessFilename = z;
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public int getInt(int i) {
            return (int) getLong(i);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public long getLong(int i) {
            if (getColumnName(i).equals("reason")) {
                return getReason(super.getInt(getColumnIndex("status")));
            }
            if (getColumnName(i).equals("status")) {
                return translateStatus(super.getInt(getColumnIndex("status")));
            }
            return super.getLong(i);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.database.CursorWrapper, android.database.Cursor
        public java.lang.String getString(int i) {
            char c;
            java.lang.String columnName = getColumnName(i);
            switch (columnName.hashCode()) {
                case -1204869480:
                    if (columnName.equals(android.app.DownloadManager.COLUMN_LOCAL_URI)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 22072411:
                    if (columnName.equals(android.app.DownloadManager.COLUMN_LOCAL_FILENAME)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return getLocalUri();
                case 1:
                    if (!this.mAccessFilename) {
                        throw new java.lang.SecurityException("COLUMN_LOCAL_FILENAME is deprecated; use ContentResolver.openFileDescriptor() instead");
                    }
                    break;
            }
            return super.getString(i);
        }

        private java.lang.String getLocalUri() {
            long j = getLong(getColumnIndex("destination"));
            if (j == 4 || j == 0 || j == 6) {
                java.lang.String string = super.getString(getColumnIndex(android.app.DownloadManager.COLUMN_LOCAL_FILENAME));
                if (string == null) {
                    return null;
                }
                return android.net.Uri.fromFile(new java.io.File(string)).toString();
            }
            return android.content.ContentUris.withAppendedId(android.provider.Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI, getLong(getColumnIndex("_id"))).toString();
        }

        private long getReason(int i) {
            switch (translateStatus(i)) {
                case 4:
                    return getPausedReason(i);
                case 16:
                    return getErrorCode(i);
                default:
                    return 0L;
            }
        }

        private long getPausedReason(int i) {
            switch (i) {
                case 194:
                    return 1L;
                case 195:
                    return 2L;
                case 196:
                    return 3L;
                case 197:
                    return 5L;
                default:
                    return 4L;
            }
        }

        private long getErrorCode(int i) {
            if ((400 <= i && i < 488) || (500 <= i && i < 600)) {
                return i;
            }
            switch (i) {
                case 198:
                    return 1006L;
                case 199:
                    return 1007L;
                case 488:
                    return 1009L;
                case 489:
                    return 1008L;
                case 492:
                    return 1001L;
                case 493:
                case 494:
                    return 1002L;
                case 495:
                    return 1004L;
                case 497:
                    return 1005L;
                default:
                    return 1000L;
            }
        }

        private int translateStatus(int i) {
            switch (i) {
                case 190:
                    return 1;
                case 191:
                case 198:
                case 199:
                default:
                    return 16;
                case 192:
                    return 2;
                case 193:
                case 194:
                case 195:
                case 196:
                case 197:
                    return 4;
                case 200:
                    return 8;
            }
        }
    }
}
