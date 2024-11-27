package android.content;

/* loaded from: classes.dex */
public abstract class ContentResolver implements android.content.ContentInterface {
    public static final java.lang.String ANY_CURSOR_ITEM_TYPE = "vnd.android.cursor.item/*";
    public static final java.lang.String CONTENT_SERVICE_NAME = "content";
    public static final java.lang.String CURSOR_DIR_BASE_TYPE = "vnd.android.cursor.dir";
    public static final java.lang.String CURSOR_ITEM_BASE_TYPE = "vnd.android.cursor.item";
    public static final boolean DEPRECATE_DATA_COLUMNS = true;
    public static final java.lang.String DEPRECATE_DATA_PREFIX = "/mnt/content/";
    private static final boolean ENABLE_CONTENT_SAMPLE = false;
    public static final java.lang.String EXTRA_HONORED_ARGS = "android.content.extra.HONORED_ARGS";
    public static final java.lang.String EXTRA_REFRESH_SUPPORTED = "android.content.extra.REFRESH_SUPPORTED";
    public static final java.lang.String EXTRA_SIZE = "android.content.extra.SIZE";
    public static final java.lang.String EXTRA_TOTAL_COUNT = "android.content.extra.TOTAL_COUNT";

    @java.lang.Deprecated
    public static final java.lang.String MIME_TYPE_DEFAULT = "application/octet-stream";
    public static final int NOTIFY_DELETE = 16;
    public static final int NOTIFY_INSERT = 4;
    public static final int NOTIFY_NO_DELAY = 32768;
    public static final int NOTIFY_SKIP_NOTIFY_FOR_DESCENDANTS = 2;
    public static final int NOTIFY_SYNC_TO_NETWORK = 1;
    public static final int NOTIFY_UPDATE = 8;
    public static final java.lang.String QUERY_ARG_GROUP_COLUMNS = "android:query-arg-group-columns";
    public static final java.lang.String QUERY_ARG_LIMIT = "android:query-arg-limit";
    public static final java.lang.String QUERY_ARG_OFFSET = "android:query-arg-offset";
    public static final java.lang.String QUERY_ARG_SORT_COLLATION = "android:query-arg-sort-collation";
    public static final java.lang.String QUERY_ARG_SORT_COLUMNS = "android:query-arg-sort-columns";
    public static final java.lang.String QUERY_ARG_SORT_DIRECTION = "android:query-arg-sort-direction";
    public static final java.lang.String QUERY_ARG_SORT_LOCALE = "android:query-arg-sort-locale";
    public static final java.lang.String QUERY_ARG_SQL_GROUP_BY = "android:query-arg-sql-group-by";
    public static final java.lang.String QUERY_ARG_SQL_HAVING = "android:query-arg-sql-having";
    public static final java.lang.String QUERY_ARG_SQL_LIMIT = "android:query-arg-sql-limit";
    public static final java.lang.String QUERY_ARG_SQL_SELECTION = "android:query-arg-sql-selection";
    public static final java.lang.String QUERY_ARG_SQL_SELECTION_ARGS = "android:query-arg-sql-selection-args";
    public static final java.lang.String QUERY_ARG_SQL_SORT_ORDER = "android:query-arg-sql-sort-order";
    public static final int QUERY_SORT_DIRECTION_ASCENDING = 0;
    public static final int QUERY_SORT_DIRECTION_DESCENDING = 1;
    public static final java.lang.String REMOTE_CALLBACK_ERROR = "error";
    public static final java.lang.String REMOTE_CALLBACK_RESULT = "result";
    public static final java.lang.String SCHEME_ANDROID_RESOURCE = "android.resource";
    public static final java.lang.String SCHEME_CONTENT = "content";
    public static final java.lang.String SCHEME_FILE = "file";
    public static final int SYNC_ERROR_AUTHENTICATION = 2;
    public static final int SYNC_ERROR_CONFLICT = 5;
    public static final int SYNC_ERROR_INTERNAL = 8;
    public static final int SYNC_ERROR_IO = 3;
    public static final int SYNC_ERROR_PARSE = 4;
    public static final int SYNC_ERROR_SYNC_ALREADY_IN_PROGRESS = 1;
    public static final int SYNC_ERROR_TOO_MANY_DELETIONS = 6;
    public static final int SYNC_ERROR_TOO_MANY_RETRIES = 7;
    public static final int SYNC_EXEMPTION_NONE = 0;
    public static final int SYNC_EXEMPTION_PROMOTE_BUCKET = 1;
    public static final int SYNC_EXEMPTION_PROMOTE_BUCKET_WITH_TEMP = 2;

    @java.lang.Deprecated
    public static final java.lang.String SYNC_EXTRAS_ACCOUNT = "account";
    public static final java.lang.String SYNC_EXTRAS_DISALLOW_METERED = "allow_metered";
    public static final java.lang.String SYNC_EXTRAS_DISCARD_LOCAL_DELETIONS = "discard_deletions";
    public static final java.lang.String SYNC_EXTRAS_DO_NOT_RETRY = "do_not_retry";
    public static final java.lang.String SYNC_EXTRAS_EXPECTED_DOWNLOAD = "expected_download";
    public static final java.lang.String SYNC_EXTRAS_EXPECTED_UPLOAD = "expected_upload";
    public static final java.lang.String SYNC_EXTRAS_EXPEDITED = "expedited";

    @java.lang.Deprecated
    public static final java.lang.String SYNC_EXTRAS_FORCE = "force";
    public static final java.lang.String SYNC_EXTRAS_IGNORE_BACKOFF = "ignore_backoff";
    public static final java.lang.String SYNC_EXTRAS_IGNORE_SETTINGS = "ignore_settings";
    public static final java.lang.String SYNC_EXTRAS_INITIALIZE = "initialize";
    public static final java.lang.String SYNC_EXTRAS_MANUAL = "force";
    public static final java.lang.String SYNC_EXTRAS_OVERRIDE_TOO_MANY_DELETIONS = "deletions_override";
    public static final java.lang.String SYNC_EXTRAS_PRIORITY = "sync_priority";
    public static final java.lang.String SYNC_EXTRAS_REQUIRE_CHARGING = "require_charging";
    public static final java.lang.String SYNC_EXTRAS_SCHEDULE_AS_EXPEDITED_JOB = "schedule_as_expedited_job";
    public static final java.lang.String SYNC_EXTRAS_UPLOAD = "upload";
    public static final int SYNC_OBSERVER_TYPE_ACTIVE = 4;
    public static final int SYNC_OBSERVER_TYPE_ALL = Integer.MAX_VALUE;
    public static final int SYNC_OBSERVER_TYPE_PENDING = 2;
    public static final int SYNC_OBSERVER_TYPE_SETTINGS = 1;
    public static final int SYNC_OBSERVER_TYPE_STATUS = 8;
    public static final java.lang.String SYNC_VIRTUAL_EXTRAS_EXEMPTION_FLAG = "v_exemption";
    private static final java.lang.String TAG = "ContentResolver";
    private static volatile android.content.IContentService sContentService;
    private final android.content.Context mContext;

    @java.lang.Deprecated
    final java.lang.String mPackageName;
    private final java.util.Random mRandom;
    final int mTargetSdkVersion;
    final android.content.ContentInterface mWrapped;
    public static final android.content.Intent ACTION_SYNC_CONN_STATUS_CHANGED = new android.content.Intent("com.android.sync.SYNC_CONN_STATUS_CHANGED");
    private static final java.lang.String[] SYNC_ERROR_NAMES = {"already-in-progress", "authentication-error", "io-error", "parse-error", com.android.internal.telephony.HbpcdLookup.PATH_MCC_SID_CONFLICT, "too-many-deletions", "too-many-retries", "internal-error"};
    private static final int SLOW_THRESHOLD_MILLIS = android.os.Build.HW_TIMEOUT_MULTIPLIER * 500;
    public static final int CONTENT_PROVIDER_PUBLISH_TIMEOUT_MILLIS = android.os.Build.HW_TIMEOUT_MULTIPLIER * 10000;
    public static final int CONTENT_PROVIDER_READY_TIMEOUT_MILLIS = CONTENT_PROVIDER_PUBLISH_TIMEOUT_MILLIS + (android.os.Build.HW_TIMEOUT_MULTIPLIER * 10000);
    private static final int CONTENT_PROVIDER_TIMEOUT_MILLIS = android.os.Build.HW_TIMEOUT_MULTIPLIER * 3000;
    private static final int REMOTE_CONTENT_PROVIDER_TIMEOUT_MILLIS = CONTENT_PROVIDER_READY_TIMEOUT_MILLIS + CONTENT_PROVIDER_TIMEOUT_MILLIS;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NotifyFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface QueryCollator {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SortDirection {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SyncExemption {
    }

    protected abstract android.content.IContentProvider acquireProvider(android.content.Context context, java.lang.String str);

    protected abstract android.content.IContentProvider acquireUnstableProvider(android.content.Context context, java.lang.String str);

    public abstract boolean releaseProvider(android.content.IContentProvider iContentProvider);

    public abstract boolean releaseUnstableProvider(android.content.IContentProvider iContentProvider);

    public abstract void unstableProviderDied(android.content.IContentProvider iContentProvider);

    public static java.lang.String syncErrorToString(int i) {
        if (i >= 1 && i <= SYNC_ERROR_NAMES.length) {
            return SYNC_ERROR_NAMES[i - 1];
        }
        return java.lang.String.valueOf(i);
    }

    public static int syncErrorStringToInt(java.lang.String str) {
        int length = SYNC_ERROR_NAMES.length;
        for (int i = 0; i < length; i++) {
            if (SYNC_ERROR_NAMES[i].equals(str)) {
                return i + 1;
            }
        }
        if (str != null) {
            try {
                return java.lang.Integer.parseInt(str);
            } catch (java.lang.NumberFormatException e) {
                android.util.Log.d(TAG, "error parsing sync error: " + str);
            }
        }
        return 0;
    }

    public ContentResolver(android.content.Context context) {
        this(context, null);
    }

    public ContentResolver(android.content.Context context, android.content.ContentInterface contentInterface) {
        this.mRandom = new java.util.Random();
        this.mContext = context == null ? android.app.ActivityThread.currentApplication() : context;
        this.mPackageName = this.mContext.getOpPackageName();
        this.mTargetSdkVersion = this.mContext.getApplicationInfo().targetSdkVersion;
        this.mWrapped = contentInterface;
    }

    public static android.content.ContentResolver wrap(android.content.ContentInterface contentInterface) {
        java.util.Objects.requireNonNull(contentInterface);
        return new android.content.ContentResolver(null, contentInterface) { // from class: android.content.ContentResolver.1
            @Override // android.content.ContentResolver
            public void unstableProviderDied(android.content.IContentProvider iContentProvider) {
                throw new java.lang.UnsupportedOperationException();
            }

            @Override // android.content.ContentResolver
            public boolean releaseUnstableProvider(android.content.IContentProvider iContentProvider) {
                throw new java.lang.UnsupportedOperationException();
            }

            @Override // android.content.ContentResolver
            public boolean releaseProvider(android.content.IContentProvider iContentProvider) {
                throw new java.lang.UnsupportedOperationException();
            }

            @Override // android.content.ContentResolver
            protected android.content.IContentProvider acquireUnstableProvider(android.content.Context context, java.lang.String str) {
                throw new java.lang.UnsupportedOperationException();
            }

            @Override // android.content.ContentResolver
            protected android.content.IContentProvider acquireProvider(android.content.Context context, java.lang.String str) {
                throw new java.lang.UnsupportedOperationException();
            }
        };
    }

    public static android.content.ContentResolver wrap(android.content.ContentProvider contentProvider) {
        return wrap((android.content.ContentInterface) contentProvider);
    }

    public static android.content.ContentResolver wrap(android.content.ContentProviderClient contentProviderClient) {
        return wrap((android.content.ContentInterface) contentProviderClient);
    }

    protected android.content.IContentProvider acquireExistingProvider(android.content.Context context, java.lang.String str) {
        return acquireProvider(context, str);
    }

    public void appNotRespondingViaProvider(android.content.IContentProvider iContentProvider) {
        throw new java.lang.UnsupportedOperationException("appNotRespondingViaProvider");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.ContentInterface
    public final java.lang.String getType(android.net.Uri uri) {
        android.content.IContentProvider iContentProvider;
        java.util.Objects.requireNonNull(uri, "url");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.getType(uri);
            }
            try {
                iContentProvider = acquireProvider(uri);
            } catch (java.lang.Exception e) {
                iContentProvider = null;
            }
            try {
                if (iContentProvider != null) {
                    try {
                        android.content.ContentResolver.StringResultListener stringResultListener = new android.content.ContentResolver.StringResultListener();
                        iContentProvider.getTypeAsync(this.mContext.getAttributionSource(), uri, new android.os.RemoteCallback(stringResultListener));
                        stringResultListener.waitForResult(CONTENT_PROVIDER_TIMEOUT_MILLIS);
                        if (stringResultListener.exception == null) {
                            return (java.lang.String) stringResultListener.result;
                        }
                        throw stringResultListener.exception;
                    } catch (android.os.RemoteException e2) {
                        try {
                            releaseProvider(iContentProvider);
                        } catch (java.lang.NullPointerException e3) {
                        }
                        return null;
                    } catch (java.lang.Exception e4) {
                        android.util.Log.w(TAG, "Failed to get type for: " + uri + " (" + e4.getMessage() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                        try {
                            releaseProvider(iContentProvider);
                        } catch (java.lang.NullPointerException e5) {
                        }
                        return null;
                    }
                }
                if (!"content".equals(uri.getScheme())) {
                    return null;
                }
                try {
                    android.content.ContentResolver.StringResultListener stringResultListener2 = new android.content.ContentResolver.StringResultListener();
                    android.app.ActivityManager.getService().getMimeTypeFilterAsync(android.content.ContentProvider.getUriWithoutUserId(uri), resolveUserId(uri), new android.os.RemoteCallback(stringResultListener2));
                    stringResultListener2.waitForResult(REMOTE_CONTENT_PROVIDER_TIMEOUT_MILLIS);
                    if (stringResultListener2.exception == null) {
                        return (java.lang.String) stringResultListener2.result;
                    }
                    throw stringResultListener2.exception;
                } catch (android.os.RemoteException e6) {
                    return null;
                } catch (java.lang.Exception e7) {
                    android.util.Log.w(TAG, "Failed to get type for: " + uri + " (" + e7.getMessage() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                    return null;
                }
            } finally {
                try {
                    releaseProvider(iContentProvider);
                } catch (java.lang.NullPointerException e8) {
                }
            }
        } catch (android.os.RemoteException e9) {
            return null;
        }
    }

    private static abstract class ResultListener<T> implements android.os.RemoteCallback.OnResultListener {
        public boolean done;
        public java.lang.RuntimeException exception;
        public T result;

        protected abstract T getResultFromBundle(android.os.Bundle bundle);

        private ResultListener() {
        }

        @Override // android.os.RemoteCallback.OnResultListener
        public void onResult(android.os.Bundle bundle) {
            synchronized (this) {
                android.os.ParcelableException parcelableException = (android.os.ParcelableException) bundle.getParcelable("error", android.os.ParcelableException.class);
                if (parcelableException != null) {
                    java.lang.Throwable cause = parcelableException.getCause();
                    if (cause instanceof java.lang.RuntimeException) {
                        this.exception = (java.lang.RuntimeException) cause;
                    } else {
                        this.exception = new java.lang.RuntimeException(cause);
                    }
                } else {
                    this.result = getResultFromBundle(bundle);
                }
                this.done = true;
                notifyAll();
            }
        }

        public void waitForResult(long j) {
            synchronized (this) {
                if (!this.done) {
                    try {
                        wait(j);
                    } catch (java.lang.InterruptedException e) {
                    }
                }
            }
        }
    }

    private static class StringResultListener extends android.content.ContentResolver.ResultListener<java.lang.String> {
        private StringResultListener() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.content.ContentResolver.ResultListener
        public java.lang.String getResultFromBundle(android.os.Bundle bundle) {
            return bundle.getString("result");
        }
    }

    private static class UriResultListener extends android.content.ContentResolver.ResultListener<android.net.Uri> {
        private UriResultListener() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.content.ContentResolver.ResultListener
        public android.net.Uri getResultFromBundle(android.os.Bundle bundle) {
            return (android.net.Uri) bundle.getParcelable("result", android.net.Uri.class);
        }
    }

    @Override // android.content.ContentInterface
    public java.lang.String[] getStreamTypes(android.net.Uri uri, java.lang.String str) {
        java.util.Objects.requireNonNull(uri, "url");
        java.util.Objects.requireNonNull(str, "mimeTypeFilter");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.getStreamTypes(uri, str);
            }
            android.content.IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                return null;
            }
            try {
                return acquireProvider.getStreamTypes(this.mContext.getAttributionSource(), uri, str);
            } catch (android.os.RemoteException e) {
                return null;
            } finally {
                releaseProvider(acquireProvider);
            }
        } catch (android.os.RemoteException e2) {
            return null;
        }
    }

    public final android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2) {
        return query(uri, strArr, str, strArr2, str2, null);
    }

    public final android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2, android.os.CancellationSignal cancellationSignal) {
        return query(uri, strArr, createSqlQueryBundle(str, strArr2, str2), cancellationSignal);
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:69:? A[SYNTHETIC] */
    @Override // android.content.ContentInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) {
        java.lang.Throwable th;
        android.content.IContentProvider iContentProvider;
        android.database.Cursor cursor;
        android.content.IContentProvider iContentProvider2;
        android.os.ICancellationSignal iCancellationSignal;
        android.content.IContentProvider iContentProvider3;
        android.content.IContentProvider iContentProvider4;
        java.util.Objects.requireNonNull(uri, "uri");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.query(uri, strArr, bundle, cancellationSignal);
            }
            android.content.IContentProvider acquireUnstableProvider = acquireUnstableProvider(uri);
            if (acquireUnstableProvider == null) {
                return null;
            }
            try {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                if (cancellationSignal != null) {
                    cancellationSignal.throwIfCanceled();
                    android.os.ICancellationSignal createCancellationSignal = acquireUnstableProvider.createCancellationSignal();
                    cancellationSignal.setRemote(createCancellationSignal);
                    iCancellationSignal = createCancellationSignal;
                } else {
                    iCancellationSignal = null;
                }
                try {
                    cursor = acquireUnstableProvider.query(this.mContext.getAttributionSource(), uri, strArr, bundle, iCancellationSignal);
                    iContentProvider4 = null;
                } catch (android.os.DeadObjectException e) {
                    unstableProviderDied(acquireUnstableProvider);
                    android.content.IContentProvider acquireProvider = acquireProvider(uri);
                    if (acquireProvider == null) {
                        if (cancellationSignal != null) {
                            cancellationSignal.setRemote(null);
                        }
                        if (acquireUnstableProvider != null) {
                            releaseUnstableProvider(acquireUnstableProvider);
                        }
                        if (acquireProvider != null) {
                            releaseProvider(acquireProvider);
                        }
                        return null;
                    }
                    try {
                        iContentProvider3 = acquireProvider;
                    } catch (android.os.RemoteException e2) {
                        iContentProvider3 = acquireProvider;
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        iContentProvider3 = acquireProvider;
                    }
                    try {
                        cursor = acquireProvider.query(this.mContext.getAttributionSource(), uri, strArr, bundle, iCancellationSignal);
                        iContentProvider4 = iContentProvider3;
                    } catch (android.os.RemoteException e3) {
                        cursor = null;
                        iContentProvider2 = iContentProvider3;
                        if (cursor != null) {
                        }
                        if (cancellationSignal != null) {
                        }
                        if (acquireUnstableProvider != null) {
                        }
                        if (iContentProvider2 != null) {
                        }
                        return null;
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        th = th;
                        cursor = null;
                        iContentProvider = iContentProvider3;
                        if (cursor != null) {
                        }
                        if (cancellationSignal != null) {
                        }
                        if (acquireUnstableProvider != null) {
                        }
                        if (iContentProvider == null) {
                        }
                    }
                }
                if (cursor == null) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (cancellationSignal != null) {
                        cancellationSignal.setRemote(null);
                    }
                    if (acquireUnstableProvider != null) {
                        releaseUnstableProvider(acquireUnstableProvider);
                    }
                    if (iContentProvider4 != null) {
                        releaseProvider(iContentProvider4);
                    }
                    return null;
                }
                try {
                    cursor.getCount();
                    iContentProvider3 = iContentProvider4;
                    android.database.Cursor cursor2 = cursor;
                    try {
                        maybeLogQueryToEventLog(android.os.SystemClock.uptimeMillis() - uptimeMillis, uri, strArr, bundle);
                        android.content.ContentResolver.CursorWrapperInner cursorWrapperInner = new android.content.ContentResolver.CursorWrapperInner(cursor2, iContentProvider3 != null ? iContentProvider3 : acquireProvider(uri));
                        if (cancellationSignal != null) {
                            cancellationSignal.setRemote(null);
                        }
                        if (acquireUnstableProvider != null) {
                            releaseUnstableProvider(acquireUnstableProvider);
                        }
                        return cursorWrapperInner;
                    } catch (android.os.RemoteException e4) {
                        cursor = cursor2;
                        iContentProvider2 = iContentProvider3;
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (cancellationSignal != null) {
                            cancellationSignal.setRemote(null);
                        }
                        if (acquireUnstableProvider != null) {
                            releaseUnstableProvider(acquireUnstableProvider);
                        }
                        if (iContentProvider2 != null) {
                            releaseProvider(iContentProvider2);
                        }
                        return null;
                    } catch (java.lang.Throwable th4) {
                        th = th4;
                        cursor = cursor2;
                        iContentProvider = iContentProvider3;
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (cancellationSignal != null) {
                            cancellationSignal.setRemote(null);
                        }
                        if (acquireUnstableProvider != null) {
                            releaseUnstableProvider(acquireUnstableProvider);
                        }
                        if (iContentProvider == null) {
                            throw th;
                        }
                        releaseProvider(iContentProvider);
                        throw th;
                    }
                } catch (android.os.RemoteException e5) {
                    iContentProvider3 = iContentProvider4;
                } catch (java.lang.Throwable th5) {
                    iContentProvider3 = iContentProvider4;
                    th = th5;
                }
            } catch (android.os.RemoteException e6) {
                iContentProvider2 = null;
                cursor = null;
                if (cursor != null) {
                }
                if (cancellationSignal != null) {
                }
                if (acquireUnstableProvider != null) {
                }
                if (iContentProvider2 != null) {
                }
                return null;
            } catch (java.lang.Throwable th6) {
                th = th6;
                iContentProvider = null;
                cursor = null;
                if (cursor != null) {
                }
                if (cancellationSignal != null) {
                }
                if (acquireUnstableProvider != null) {
                }
                if (iContentProvider == null) {
                }
            }
        } catch (android.os.RemoteException e7) {
            return null;
        }
    }

    public final android.net.Uri canonicalizeOrElse(android.net.Uri uri) {
        android.net.Uri canonicalize = canonicalize(uri);
        return canonicalize != null ? canonicalize : uri;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.ContentInterface
    public final android.net.Uri canonicalize(android.net.Uri uri) {
        java.util.Objects.requireNonNull(uri, "url");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.canonicalize(uri);
            }
            android.content.IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                return null;
            }
            try {
                android.content.ContentResolver.UriResultListener uriResultListener = new android.content.ContentResolver.UriResultListener();
                acquireProvider.canonicalizeAsync(this.mContext.getAttributionSource(), uri, new android.os.RemoteCallback(uriResultListener));
                uriResultListener.waitForResult(CONTENT_PROVIDER_TIMEOUT_MILLIS);
                if (uriResultListener.exception != null) {
                    throw uriResultListener.exception;
                }
                return (android.net.Uri) uriResultListener.result;
            } catch (android.os.RemoteException e) {
                return null;
            } finally {
                releaseProvider(acquireProvider);
            }
        } catch (android.os.RemoteException e2) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.ContentInterface
    public final android.net.Uri uncanonicalize(android.net.Uri uri) {
        java.util.Objects.requireNonNull(uri, "url");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.uncanonicalize(uri);
            }
            android.content.IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                return null;
            }
            try {
                android.content.ContentResolver.UriResultListener uriResultListener = new android.content.ContentResolver.UriResultListener();
                acquireProvider.uncanonicalizeAsync(this.mContext.getAttributionSource(), uri, new android.os.RemoteCallback(uriResultListener));
                uriResultListener.waitForResult(CONTENT_PROVIDER_TIMEOUT_MILLIS);
                if (uriResultListener.exception != null) {
                    throw uriResultListener.exception;
                }
                return (android.net.Uri) uriResultListener.result;
            } catch (android.os.RemoteException e) {
                return null;
            } finally {
                releaseProvider(acquireProvider);
            }
        } catch (android.os.RemoteException e2) {
            return null;
        }
    }

    @Override // android.content.ContentInterface
    public final boolean refresh(android.net.Uri uri, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) {
        android.os.ICancellationSignal createCancellationSignal;
        java.util.Objects.requireNonNull(uri, "url");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.refresh(uri, bundle, cancellationSignal);
            }
            android.content.IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                return false;
            }
            if (cancellationSignal == null) {
                createCancellationSignal = null;
            } else {
                try {
                    cancellationSignal.throwIfCanceled();
                    createCancellationSignal = acquireProvider.createCancellationSignal();
                    cancellationSignal.setRemote(createCancellationSignal);
                } catch (android.os.RemoteException e) {
                    return false;
                } finally {
                    releaseProvider(acquireProvider);
                }
            }
            return acquireProvider.refresh(this.mContext.getAttributionSource(), uri, bundle, createCancellationSignal);
        } catch (android.os.RemoteException e2) {
            return false;
        }
    }

    @Override // android.content.ContentInterface
    @android.annotation.SystemApi
    public int checkUriPermission(android.net.Uri uri, int i, int i2) {
        java.util.Objects.requireNonNull(uri, "uri");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.checkUriPermission(uri, i, i2);
            }
            try {
                android.content.ContentProviderClient acquireUnstableContentProviderClient = acquireUnstableContentProviderClient(uri);
                try {
                    int checkUriPermission = acquireUnstableContentProviderClient.checkUriPermission(uri, i, i2);
                    if (acquireUnstableContentProviderClient != null) {
                        acquireUnstableContentProviderClient.close();
                    }
                    return checkUriPermission;
                } finally {
                }
            } catch (android.os.RemoteException e) {
                return -1;
            }
        } catch (android.os.RemoteException e2) {
            return -1;
        }
    }

    public final java.io.InputStream openInputStream(android.net.Uri uri) throws java.io.FileNotFoundException {
        java.util.Objects.requireNonNull(uri, "uri");
        java.lang.String scheme = uri.getScheme();
        if (SCHEME_ANDROID_RESOURCE.equals(scheme)) {
            android.content.ContentResolver.OpenResourceIdResult resourceId = getResourceId(uri);
            try {
                return resourceId.r.openRawResource(resourceId.id);
            } catch (android.content.res.Resources.NotFoundException e) {
                throw new java.io.FileNotFoundException("Resource does not exist: " + uri);
            }
        }
        if ("file".equals(scheme)) {
            return new java.io.FileInputStream(uri.getPath());
        }
        android.content.res.AssetFileDescriptor openAssetFileDescriptor = openAssetFileDescriptor(uri, "r", null);
        if (openAssetFileDescriptor == null) {
            return null;
        }
        try {
            return openAssetFileDescriptor.createInputStream();
        } catch (java.io.IOException e2) {
            throw new java.io.FileNotFoundException("Unable to create stream");
        }
    }

    public final java.io.OutputStream openOutputStream(android.net.Uri uri) throws java.io.FileNotFoundException {
        return openOutputStream(uri, "w");
    }

    public final java.io.OutputStream openOutputStream(android.net.Uri uri, java.lang.String str) throws java.io.FileNotFoundException {
        android.content.res.AssetFileDescriptor openAssetFileDescriptor = openAssetFileDescriptor(uri, str, null);
        if (openAssetFileDescriptor == null) {
            return null;
        }
        try {
            return openAssetFileDescriptor.createOutputStream();
        } catch (java.io.IOException e) {
            throw new java.io.FileNotFoundException("Unable to create stream");
        }
    }

    @Override // android.content.ContentInterface
    public final android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String str, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        try {
            return this.mWrapped != null ? this.mWrapped.openFile(uri, str, cancellationSignal) : openFileDescriptor(uri, str, cancellationSignal);
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public final android.os.ParcelFileDescriptor openFileDescriptor(android.net.Uri uri, java.lang.String str) throws java.io.FileNotFoundException {
        return openFileDescriptor(uri, str, null);
    }

    public final android.os.ParcelFileDescriptor openFileDescriptor(android.net.Uri uri, java.lang.String str, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.openFile(uri, str, cancellationSignal);
            }
            android.content.res.AssetFileDescriptor openAssetFileDescriptor = openAssetFileDescriptor(uri, str, cancellationSignal);
            if (openAssetFileDescriptor == null) {
                return null;
            }
            if (openAssetFileDescriptor.getDeclaredLength() < 0) {
                return openAssetFileDescriptor.getParcelFileDescriptor();
            }
            try {
                openAssetFileDescriptor.close();
            } catch (java.io.IOException e) {
            }
            throw new java.io.FileNotFoundException("Not a whole file");
        } catch (android.os.RemoteException e2) {
            return null;
        }
    }

    @Override // android.content.ContentInterface
    public final android.content.res.AssetFileDescriptor openAssetFile(android.net.Uri uri, java.lang.String str, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        try {
            return this.mWrapped != null ? this.mWrapped.openAssetFile(uri, str, cancellationSignal) : openAssetFileDescriptor(uri, str, cancellationSignal);
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public final android.content.res.AssetFileDescriptor openAssetFileDescriptor(android.net.Uri uri, java.lang.String str) throws java.io.FileNotFoundException {
        return openAssetFileDescriptor(uri, str, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:68:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0188  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final android.content.res.AssetFileDescriptor openAssetFileDescriptor(android.net.Uri uri, java.lang.String str, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        android.content.IContentProvider iContentProvider;
        android.os.ICancellationSignal createCancellationSignal;
        android.content.res.AssetFileDescriptor openAssetFile;
        java.util.Objects.requireNonNull(uri, "uri");
        java.util.Objects.requireNonNull(str, "mode");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.openAssetFile(uri, str, cancellationSignal);
            }
            java.lang.String scheme = uri.getScheme();
            if (SCHEME_ANDROID_RESOURCE.equals(scheme)) {
                if (!"r".equals(str)) {
                    throw new java.io.FileNotFoundException("Can't write resources: " + uri);
                }
                android.content.ContentResolver.OpenResourceIdResult resourceId = getResourceId(uri);
                try {
                    return resourceId.r.openRawResourceFd(resourceId.id);
                } catch (android.content.res.Resources.NotFoundException e) {
                    throw new java.io.FileNotFoundException("Resource does not exist: " + uri);
                }
            }
            if ("file".equals(scheme)) {
                return new android.content.res.AssetFileDescriptor(android.os.ParcelFileDescriptor.open(new java.io.File(uri.getPath()), android.os.ParcelFileDescriptor.parseMode(str)), 0L, -1L);
            }
            if ("r".equals(str)) {
                return openTypedAssetFileDescriptor(uri, "*/*", null, cancellationSignal);
            }
            android.content.IContentProvider acquireUnstableProvider = acquireUnstableProvider(uri);
            if (acquireUnstableProvider == null) {
                throw new java.io.FileNotFoundException("No content provider: " + uri);
            }
            try {
                if (cancellationSignal != null) {
                    try {
                        cancellationSignal.throwIfCanceled();
                        createCancellationSignal = acquireUnstableProvider.createCancellationSignal();
                        cancellationSignal.setRemote(createCancellationSignal);
                    } catch (android.os.RemoteException e2) {
                        throw new java.io.FileNotFoundException("Failed opening content provider: " + uri);
                    } catch (java.io.FileNotFoundException e3) {
                        throw e3;
                    } catch (java.lang.Throwable th) {
                        th = th;
                        iContentProvider = null;
                        if (cancellationSignal != null) {
                        }
                        if (iContentProvider != null) {
                        }
                        if (acquireUnstableProvider != null) {
                        }
                        throw th;
                    }
                } else {
                    createCancellationSignal = null;
                }
                try {
                    try {
                        openAssetFile = acquireUnstableProvider.openAssetFile(this.mContext.getAttributionSource(), uri, str, createCancellationSignal);
                    } catch (android.os.RemoteException e4) {
                        throw new java.io.FileNotFoundException("Failed opening content provider: " + uri);
                    } catch (java.io.FileNotFoundException e5) {
                        throw e5;
                    }
                } catch (android.os.DeadObjectException e6) {
                    unstableProviderDied(acquireUnstableProvider);
                    iContentProvider = acquireProvider(uri);
                    if (iContentProvider == null) {
                        throw new java.io.FileNotFoundException("No content provider: " + uri);
                    }
                    openAssetFile = iContentProvider.openAssetFile(this.mContext.getAttributionSource(), uri, str, createCancellationSignal);
                    if (openAssetFile == null) {
                        if (cancellationSignal != null) {
                            cancellationSignal.setRemote(null);
                        }
                        if (iContentProvider != null) {
                            releaseProvider(iContentProvider);
                        }
                        if (acquireUnstableProvider != null) {
                            releaseUnstableProvider(acquireUnstableProvider);
                        }
                        return null;
                    }
                }
                if (openAssetFile == null) {
                    if (cancellationSignal != null) {
                        cancellationSignal.setRemote(null);
                    }
                    if (acquireUnstableProvider != null) {
                        releaseUnstableProvider(acquireUnstableProvider);
                    }
                    return null;
                }
                iContentProvider = null;
                if (iContentProvider == null) {
                    iContentProvider = acquireProvider(uri);
                }
                releaseUnstableProvider(acquireUnstableProvider);
                try {
                } catch (android.os.RemoteException e7) {
                } catch (java.io.FileNotFoundException e8) {
                    throw e8;
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    acquireUnstableProvider = null;
                }
                try {
                    android.content.res.AssetFileDescriptor assetFileDescriptor = new android.content.res.AssetFileDescriptor(new android.content.ContentResolver.ParcelFileDescriptorInner(openAssetFile.getParcelFileDescriptor(), iContentProvider), openAssetFile.getStartOffset(), openAssetFile.getDeclaredLength());
                    if (cancellationSignal != null) {
                        cancellationSignal.setRemote(null);
                    }
                    return assetFileDescriptor;
                } catch (android.os.RemoteException e9) {
                    throw new java.io.FileNotFoundException("Failed opening content provider: " + uri);
                } catch (java.io.FileNotFoundException e10) {
                    throw e10;
                } catch (java.lang.Throwable th3) {
                    th = th3;
                    acquireUnstableProvider = null;
                    iContentProvider = null;
                    if (cancellationSignal != null) {
                        cancellationSignal.setRemote(null);
                    }
                    if (iContentProvider != null) {
                        releaseProvider(iContentProvider);
                    }
                    if (acquireUnstableProvider != null) {
                        releaseUnstableProvider(acquireUnstableProvider);
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th4) {
                th = th4;
            }
        } catch (android.os.RemoteException e11) {
            return null;
        }
    }

    @Override // android.content.ContentInterface
    public final android.content.res.AssetFileDescriptor openTypedAssetFile(android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.openTypedAssetFile(uri, str, bundle, cancellationSignal);
            }
            return openTypedAssetFileDescriptor(uri, str, bundle, cancellationSignal);
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public final android.content.res.AssetFileDescriptor openTypedAssetFileDescriptor(android.net.Uri uri, java.lang.String str, android.os.Bundle bundle) throws java.io.FileNotFoundException {
        return openTypedAssetFileDescriptor(uri, str, bundle, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:50:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0138  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final android.content.res.AssetFileDescriptor openTypedAssetFileDescriptor(android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        android.content.IContentProvider iContentProvider;
        android.os.ICancellationSignal iCancellationSignal;
        android.content.res.AssetFileDescriptor openTypedAssetFile;
        java.util.Objects.requireNonNull(uri, "uri");
        java.util.Objects.requireNonNull(str, "mimeType");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.openTypedAssetFile(uri, str, bundle, cancellationSignal);
            }
            android.content.IContentProvider acquireUnstableProvider = acquireUnstableProvider(uri);
            if (acquireUnstableProvider == null) {
                throw new java.io.FileNotFoundException("No content provider: " + uri);
            }
            try {
                if (cancellationSignal != null) {
                    try {
                        cancellationSignal.throwIfCanceled();
                        android.os.ICancellationSignal createCancellationSignal = acquireUnstableProvider.createCancellationSignal();
                        cancellationSignal.setRemote(createCancellationSignal);
                        iCancellationSignal = createCancellationSignal;
                    } catch (android.os.RemoteException e) {
                        throw new java.io.FileNotFoundException("Failed opening content provider: " + uri);
                    } catch (java.io.FileNotFoundException e2) {
                        throw e2;
                    } catch (java.lang.Throwable th) {
                        th = th;
                        iContentProvider = null;
                        if (cancellationSignal != null) {
                            cancellationSignal.setRemote(null);
                        }
                        if (iContentProvider != null) {
                            releaseProvider(iContentProvider);
                        }
                        if (acquireUnstableProvider != null) {
                            releaseUnstableProvider(acquireUnstableProvider);
                        }
                        throw th;
                    }
                } else {
                    iCancellationSignal = null;
                }
                try {
                    openTypedAssetFile = acquireUnstableProvider.openTypedAssetFile(this.mContext.getAttributionSource(), uri, str, bundle, iCancellationSignal);
                } catch (android.os.DeadObjectException e3) {
                    unstableProviderDied(acquireUnstableProvider);
                    iContentProvider = acquireProvider(uri);
                    try {
                        if (iContentProvider == null) {
                            throw new java.io.FileNotFoundException("No content provider: " + uri);
                        }
                        try {
                            openTypedAssetFile = iContentProvider.openTypedAssetFile(this.mContext.getAttributionSource(), uri, str, bundle, iCancellationSignal);
                            if (openTypedAssetFile == null) {
                                if (cancellationSignal != null) {
                                    cancellationSignal.setRemote(null);
                                }
                                if (iContentProvider != null) {
                                    releaseProvider(iContentProvider);
                                }
                                if (acquireUnstableProvider != null) {
                                    releaseUnstableProvider(acquireUnstableProvider);
                                }
                                return null;
                            }
                            iContentProvider = iContentProvider;
                        } catch (android.os.RemoteException e4) {
                            throw new java.io.FileNotFoundException("Failed opening content provider: " + uri);
                        } catch (java.io.FileNotFoundException e5) {
                            throw e5;
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            if (cancellationSignal != null) {
                            }
                            if (iContentProvider != null) {
                            }
                            if (acquireUnstableProvider != null) {
                            }
                            throw th;
                        }
                    } catch (android.os.RemoteException e6) {
                    } catch (java.io.FileNotFoundException e7) {
                        throw e7;
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        iContentProvider = str;
                    }
                }
                if (openTypedAssetFile == null) {
                    if (cancellationSignal != null) {
                        cancellationSignal.setRemote(null);
                    }
                    if (acquireUnstableProvider != null) {
                        releaseUnstableProvider(acquireUnstableProvider);
                    }
                    return null;
                }
                iContentProvider = null;
                if (iContentProvider == null) {
                    try {
                        iContentProvider = acquireProvider(uri);
                    } catch (android.os.RemoteException e8) {
                        throw new java.io.FileNotFoundException("Failed opening content provider: " + uri);
                    } catch (java.io.FileNotFoundException e9) {
                        throw e9;
                    }
                }
                releaseUnstableProvider(acquireUnstableProvider);
                try {
                    try {
                        android.content.res.AssetFileDescriptor assetFileDescriptor = new android.content.res.AssetFileDescriptor(new android.content.ContentResolver.ParcelFileDescriptorInner(openTypedAssetFile.getParcelFileDescriptor(), iContentProvider), openTypedAssetFile.getStartOffset(), openTypedAssetFile.getDeclaredLength(), openTypedAssetFile.getExtras());
                        if (cancellationSignal != null) {
                            cancellationSignal.setRemote(null);
                        }
                        return assetFileDescriptor;
                    } catch (android.os.RemoteException e10) {
                        throw new java.io.FileNotFoundException("Failed opening content provider: " + uri);
                    } catch (java.io.FileNotFoundException e11) {
                        throw e11;
                    } catch (java.lang.Throwable th4) {
                        th = th4;
                        iContentProvider = null;
                        acquireUnstableProvider = null;
                        if (cancellationSignal != null) {
                        }
                        if (iContentProvider != null) {
                        }
                        if (acquireUnstableProvider != null) {
                        }
                        throw th;
                    }
                } catch (android.os.RemoteException e12) {
                } catch (java.io.FileNotFoundException e13) {
                    throw e13;
                } catch (java.lang.Throwable th5) {
                    th = th5;
                    acquireUnstableProvider = null;
                }
            } catch (java.lang.Throwable th6) {
                th = th6;
            }
        } catch (android.os.RemoteException e14) {
            return null;
        }
    }

    public class OpenResourceIdResult {
        public int id;
        public android.content.res.Resources r;

        public OpenResourceIdResult() {
        }
    }

    public android.content.ContentResolver.OpenResourceIdResult getResourceId(android.net.Uri uri) throws java.io.FileNotFoundException {
        int parseInt;
        java.lang.String authority = uri.getAuthority();
        if (android.text.TextUtils.isEmpty(authority)) {
            throw new java.io.FileNotFoundException("No authority: " + uri);
        }
        try {
            android.content.res.Resources resourcesForApplication = this.mContext.getPackageManager().getResourcesForApplication(authority);
            java.util.List<java.lang.String> pathSegments = uri.getPathSegments();
            if (pathSegments == null) {
                throw new java.io.FileNotFoundException("No path: " + uri);
            }
            int size = pathSegments.size();
            if (size == 1) {
                try {
                    parseInt = java.lang.Integer.parseInt(pathSegments.get(0));
                } catch (java.lang.NumberFormatException e) {
                    throw new java.io.FileNotFoundException("Single path segment is not a resource ID: " + uri);
                }
            } else if (size == 2) {
                parseInt = resourcesForApplication.getIdentifier(pathSegments.get(1), pathSegments.get(0), authority);
            } else {
                throw new java.io.FileNotFoundException("More than two path segments: " + uri);
            }
            if (parseInt == 0) {
                throw new java.io.FileNotFoundException("No resource found for: " + uri);
            }
            android.content.ContentResolver.OpenResourceIdResult openResourceIdResult = new android.content.ContentResolver.OpenResourceIdResult();
            openResourceIdResult.r = resourcesForApplication;
            openResourceIdResult.id = parseInt;
            return openResourceIdResult;
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            throw new java.io.FileNotFoundException("No package found for authority: " + uri);
        }
    }

    public final android.net.Uri insert(android.net.Uri uri, android.content.ContentValues contentValues) {
        return insert(uri, contentValues, null);
    }

    @Override // android.content.ContentInterface
    public final android.net.Uri insert(android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) {
        java.util.Objects.requireNonNull(uri, "url");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.insert(uri, contentValues, bundle);
            }
            android.content.IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                throw new java.lang.IllegalArgumentException("Unknown URL " + uri);
            }
            try {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                android.net.Uri insert = acquireProvider.insert(this.mContext.getAttributionSource(), uri, contentValues, bundle);
                maybeLogUpdateToEventLog(android.os.SystemClock.uptimeMillis() - uptimeMillis, uri, "insert", null);
                return insert;
            } catch (android.os.RemoteException e) {
                return null;
            } finally {
                releaseProvider(acquireProvider);
            }
        } catch (android.os.RemoteException e2) {
            return null;
        }
    }

    @Override // android.content.ContentInterface
    public android.content.ContentProviderResult[] applyBatch(java.lang.String str, java.util.ArrayList<android.content.ContentProviderOperation> arrayList) throws android.os.RemoteException, android.content.OperationApplicationException {
        java.util.Objects.requireNonNull(str, android.provider.ContactsContract.Directory.DIRECTORY_AUTHORITY);
        java.util.Objects.requireNonNull(arrayList, "operations");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.applyBatch(str, arrayList);
            }
            android.content.ContentProviderClient acquireContentProviderClient = acquireContentProviderClient(str);
            if (acquireContentProviderClient == null) {
                throw new java.lang.IllegalArgumentException("Unknown authority " + str);
            }
            try {
                return acquireContentProviderClient.applyBatch(arrayList);
            } finally {
                acquireContentProviderClient.release();
            }
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    @Override // android.content.ContentInterface
    public final int bulkInsert(android.net.Uri uri, android.content.ContentValues[] contentValuesArr) {
        java.util.Objects.requireNonNull(uri, "url");
        java.util.Objects.requireNonNull(contentValuesArr, "values");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.bulkInsert(uri, contentValuesArr);
            }
            android.content.IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                throw new java.lang.IllegalArgumentException("Unknown URL " + uri);
            }
            try {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                int bulkInsert = acquireProvider.bulkInsert(this.mContext.getAttributionSource(), uri, contentValuesArr);
                maybeLogUpdateToEventLog(android.os.SystemClock.uptimeMillis() - uptimeMillis, uri, "bulkinsert", null);
                return bulkInsert;
            } catch (android.os.RemoteException e) {
                return 0;
            } finally {
                releaseProvider(acquireProvider);
            }
        } catch (android.os.RemoteException e2) {
            return 0;
        }
    }

    public final int delete(android.net.Uri uri, java.lang.String str, java.lang.String[] strArr) {
        return delete(uri, createSqlQueryBundle(str, strArr));
    }

    @Override // android.content.ContentInterface
    public final int delete(android.net.Uri uri, android.os.Bundle bundle) {
        java.util.Objects.requireNonNull(uri, "url");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.delete(uri, bundle);
            }
            android.content.IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                throw new java.lang.IllegalArgumentException("Unknown URL " + uri);
            }
            try {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                int delete = acquireProvider.delete(this.mContext.getAttributionSource(), uri, bundle);
                maybeLogUpdateToEventLog(android.os.SystemClock.uptimeMillis() - uptimeMillis, uri, "delete", null);
                releaseProvider(acquireProvider);
                return delete;
            } catch (android.os.RemoteException e) {
                releaseProvider(acquireProvider);
                return -1;
            } catch (java.lang.Throwable th) {
                releaseProvider(acquireProvider);
                throw th;
            }
        } catch (android.os.RemoteException e2) {
            return 0;
        }
    }

    public final int update(android.net.Uri uri, android.content.ContentValues contentValues, java.lang.String str, java.lang.String[] strArr) {
        return update(uri, contentValues, createSqlQueryBundle(str, strArr));
    }

    @Override // android.content.ContentInterface
    public final int update(android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) {
        java.util.Objects.requireNonNull(uri, "uri");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.update(uri, contentValues, bundle);
            }
            android.content.IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                throw new java.lang.IllegalArgumentException("Unknown URI " + uri);
            }
            try {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                int update = acquireProvider.update(this.mContext.getAttributionSource(), uri, contentValues, bundle);
                maybeLogUpdateToEventLog(android.os.SystemClock.uptimeMillis() - uptimeMillis, uri, "update", null);
                releaseProvider(acquireProvider);
                return update;
            } catch (android.os.RemoteException e) {
                releaseProvider(acquireProvider);
                return -1;
            } catch (java.lang.Throwable th) {
                releaseProvider(acquireProvider);
                throw th;
            }
        } catch (android.os.RemoteException e2) {
            return 0;
        }
    }

    public final android.os.Bundle call(android.net.Uri uri, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        return call(uri.getAuthority(), str, str2, bundle);
    }

    @Override // android.content.ContentInterface
    public final android.os.Bundle call(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.Bundle bundle) {
        java.util.Objects.requireNonNull(str, android.provider.ContactsContract.Directory.DIRECTORY_AUTHORITY);
        java.util.Objects.requireNonNull(str2, android.provider.CalendarContract.RemindersColumns.METHOD);
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.call(str, str2, str3, bundle);
            }
            android.content.IContentProvider acquireProvider = acquireProvider(str);
            if (acquireProvider == null) {
                throw new java.lang.IllegalArgumentException("Unknown authority " + str);
            }
            try {
                android.os.Bundle call = acquireProvider.call(this.mContext.getAttributionSource(), str, str2, str3, bundle);
                android.os.Bundle.setDefusable(call, true);
                return call;
            } catch (android.os.RemoteException e) {
                return null;
            } finally {
                releaseProvider(acquireProvider);
            }
        } catch (android.os.RemoteException e2) {
            return null;
        }
    }

    public final android.content.IContentProvider acquireProvider(android.net.Uri uri) {
        java.lang.String authority;
        if ("content".equals(uri.getScheme()) && (authority = uri.getAuthority()) != null) {
            return acquireProvider(this.mContext, authority);
        }
        return null;
    }

    public final android.content.IContentProvider acquireExistingProvider(android.net.Uri uri) {
        java.lang.String authority;
        if ("content".equals(uri.getScheme()) && (authority = uri.getAuthority()) != null) {
            return acquireExistingProvider(this.mContext, authority);
        }
        return null;
    }

    public final android.content.IContentProvider acquireProvider(java.lang.String str) {
        if (str == null) {
            return null;
        }
        return acquireProvider(this.mContext, str);
    }

    public final android.content.IContentProvider acquireUnstableProvider(android.net.Uri uri) {
        if ("content".equals(uri.getScheme()) && uri.getAuthority() != null) {
            return acquireUnstableProvider(this.mContext, uri.getAuthority());
        }
        return null;
    }

    public final android.content.IContentProvider acquireUnstableProvider(java.lang.String str) {
        if (str == null) {
            return null;
        }
        return acquireUnstableProvider(this.mContext, str);
    }

    public final android.content.ContentProviderClient acquireContentProviderClient(android.net.Uri uri) {
        java.util.Objects.requireNonNull(uri, "uri");
        android.content.IContentProvider acquireProvider = acquireProvider(uri);
        if (acquireProvider != null) {
            return new android.content.ContentProviderClient(this, acquireProvider, uri.getAuthority(), true);
        }
        return null;
    }

    public final android.content.ContentProviderClient acquireContentProviderClient(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "name");
        android.content.IContentProvider acquireProvider = acquireProvider(str);
        if (acquireProvider != null) {
            return new android.content.ContentProviderClient(this, acquireProvider, str, true);
        }
        return null;
    }

    public final android.content.ContentProviderClient acquireUnstableContentProviderClient(android.net.Uri uri) {
        java.util.Objects.requireNonNull(uri, "uri");
        android.content.IContentProvider acquireUnstableProvider = acquireUnstableProvider(uri);
        if (acquireUnstableProvider != null) {
            return new android.content.ContentProviderClient(this, acquireUnstableProvider, uri.getAuthority(), false);
        }
        return null;
    }

    public final android.content.ContentProviderClient acquireUnstableContentProviderClient(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "name");
        android.content.IContentProvider acquireUnstableProvider = acquireUnstableProvider(str);
        if (acquireUnstableProvider != null) {
            return new android.content.ContentProviderClient(this, acquireUnstableProvider, str, false);
        }
        return null;
    }

    public final void registerContentObserver(android.net.Uri uri, boolean z, android.database.ContentObserver contentObserver) {
        java.util.Objects.requireNonNull(uri, "uri");
        java.util.Objects.requireNonNull(contentObserver, "observer");
        registerContentObserver(android.content.ContentProvider.getUriWithoutUserId(uri), z, contentObserver, android.content.ContentProvider.getUserIdFromUri(uri, this.mContext.getUserId()));
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public final void registerContentObserverAsUser(android.net.Uri uri, boolean z, android.database.ContentObserver contentObserver, android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(uri, "uri");
        java.util.Objects.requireNonNull(contentObserver, "observer");
        java.util.Objects.requireNonNull(userHandle, "userHandle");
        registerContentObserver(android.content.ContentProvider.getUriWithoutUserId(uri), z, contentObserver, userHandle.getIdentifier());
    }

    public final void registerContentObserver(android.net.Uri uri, boolean z, android.database.ContentObserver contentObserver, int i) {
        try {
            getContentService().registerContentObserver(uri, z, contentObserver.getContentObserver(), i, this.mTargetSdkVersion);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void unregisterContentObserver(android.database.ContentObserver contentObserver) {
        java.util.Objects.requireNonNull(contentObserver, "observer");
        try {
            android.database.IContentObserver releaseContentObserver = contentObserver.releaseContentObserver();
            if (releaseContentObserver != null) {
                getContentService().unregisterContentObserver(releaseContentObserver);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyChange(android.net.Uri uri, android.database.ContentObserver contentObserver) {
        notifyChange(uri, contentObserver, true);
    }

    @java.lang.Deprecated
    public void notifyChange(android.net.Uri uri, android.database.ContentObserver contentObserver, boolean z) {
        notifyChange(uri, contentObserver, z ? 1 : 0);
    }

    public void notifyChange(android.net.Uri uri, android.database.ContentObserver contentObserver, int i) {
        java.util.Objects.requireNonNull(uri, "uri");
        notifyChange(android.content.ContentProvider.getUriWithoutUserId(uri), contentObserver, i, android.content.ContentProvider.getUserIdFromUri(uri, this.mContext.getUserId()));
    }

    @java.lang.Deprecated
    public void notifyChange(java.lang.Iterable<android.net.Uri> iterable, android.database.ContentObserver contentObserver, int i) {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Objects.requireNonNull(arrayList);
        iterable.forEach(new java.util.function.Consumer() { // from class: android.content.ContentResolver$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList.add((android.net.Uri) obj);
            }
        });
        notifyChange((java.util.Collection<android.net.Uri>) arrayList, contentObserver, i);
    }

    public void notifyChange(java.util.Collection<android.net.Uri> collection, android.database.ContentObserver contentObserver, int i) {
        java.util.Objects.requireNonNull(collection, "uris");
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        for (android.net.Uri uri : collection) {
            int userIdFromUri = android.content.ContentProvider.getUserIdFromUri(uri, this.mContext.getUserId());
            java.util.ArrayList arrayList = (java.util.ArrayList) sparseArray.get(userIdFromUri);
            if (arrayList == null) {
                arrayList = new java.util.ArrayList();
                sparseArray.put(userIdFromUri, arrayList);
            }
            arrayList.add(android.content.ContentProvider.getUriWithoutUserId(uri));
        }
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            int keyAt = sparseArray.keyAt(i2);
            java.util.ArrayList arrayList2 = (java.util.ArrayList) sparseArray.valueAt(i2);
            notifyChange((android.net.Uri[]) arrayList2.toArray(new android.net.Uri[arrayList2.size()]), contentObserver, i, keyAt);
        }
    }

    @java.lang.Deprecated
    public void notifyChange(android.net.Uri uri, android.database.ContentObserver contentObserver, boolean z, int i) {
        notifyChange(uri, contentObserver, z ? 1 : 0, i);
    }

    public void notifyChange(android.net.Uri uri, android.database.ContentObserver contentObserver, int i, int i2) {
        notifyChange(new android.net.Uri[]{uri}, contentObserver, i, i2);
    }

    public void notifyChange(android.net.Uri[] uriArr, android.database.ContentObserver contentObserver, int i, int i2) {
        try {
            getContentService().notifyChange(uriArr, contentObserver == null ? null : contentObserver.getContentObserver(), contentObserver != null && contentObserver.deliverSelfNotifications(), i, i2, this.mTargetSdkVersion, this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void takePersistableUriPermission(android.net.Uri uri, int i) {
        java.util.Objects.requireNonNull(uri, "uri");
        try {
            android.app.UriGrantsManager.getService().takePersistableUriPermission(android.content.ContentProvider.getUriWithoutUserId(uri), i, null, resolveUserId(uri));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void takePersistableUriPermission(java.lang.String str, android.net.Uri uri, int i) {
        java.util.Objects.requireNonNull(str, "toPackage");
        java.util.Objects.requireNonNull(uri, "uri");
        try {
            android.app.UriGrantsManager.getService().takePersistableUriPermission(android.content.ContentProvider.getUriWithoutUserId(uri), i, str, resolveUserId(uri));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releasePersistableUriPermission(android.net.Uri uri, int i) {
        java.util.Objects.requireNonNull(uri, "uri");
        try {
            android.app.UriGrantsManager.getService().releasePersistableUriPermission(android.content.ContentProvider.getUriWithoutUserId(uri), i, null, resolveUserId(uri));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.UriPermission> getPersistedUriPermissions() {
        try {
            return android.app.UriGrantsManager.getService().getUriPermissions(this.mPackageName, true, true).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.UriPermission> getOutgoingPersistedUriPermissions() {
        try {
            return android.app.UriGrantsManager.getService().getUriPermissions(this.mPackageName, false, true).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.UriPermission> getOutgoingUriPermissions() {
        try {
            return android.app.UriGrantsManager.getService().getUriPermissions(this.mPackageName, false, false).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void startSync(android.net.Uri uri, android.os.Bundle bundle) {
        android.accounts.Account account;
        if (bundle == null) {
            account = null;
        } else {
            java.lang.String string = bundle.getString("account");
            if (android.text.TextUtils.isEmpty(string)) {
                account = null;
            } else {
                account = new android.accounts.Account(string, "com.google");
            }
            bundle.remove("account");
        }
        requestSync(account, uri != null ? uri.getAuthority() : null, bundle);
    }

    public static void requestSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) {
        requestSyncAsUser(account, str, android.os.UserHandle.myUserId(), bundle);
    }

    public static void requestSyncAsUser(android.accounts.Account account, java.lang.String str, int i, android.os.Bundle bundle) {
        if (bundle == null) {
            throw new java.lang.IllegalArgumentException("Must specify extras.");
        }
        try {
            getContentService().syncAsUser(new android.content.SyncRequest.Builder().setSyncAdapter(account, str).setExtras(bundle).syncOnce().build(), i, android.app.ActivityThread.currentPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void requestSync(android.content.SyncRequest syncRequest) {
        try {
            getContentService().sync(syncRequest, android.app.ActivityThread.currentPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void validateSyncExtrasBundle(android.os.Bundle bundle) {
        try {
            java.util.Iterator<java.lang.String> it = bundle.keySet().iterator();
            while (it.hasNext()) {
                java.lang.Object obj = bundle.get(it.next());
                if (obj != null && !(obj instanceof java.lang.Long) && !(obj instanceof java.lang.Integer) && !(obj instanceof java.lang.Boolean) && !(obj instanceof java.lang.Float) && !(obj instanceof java.lang.Double) && !(obj instanceof java.lang.String) && !(obj instanceof android.accounts.Account)) {
                    throw new java.lang.IllegalArgumentException("unexpected value type: " + obj.getClass().getName());
                }
            }
        } catch (java.lang.IllegalArgumentException e) {
            throw e;
        } catch (java.lang.RuntimeException e2) {
            throw new java.lang.IllegalArgumentException("error unparceling Bundle", e2);
        }
    }

    @java.lang.Deprecated
    public void cancelSync(android.net.Uri uri) {
        cancelSync(null, uri != null ? uri.getAuthority() : null);
    }

    public static void cancelSync(android.accounts.Account account, java.lang.String str) {
        try {
            getContentService().cancelSync(account, str, null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void cancelSyncAsUser(android.accounts.Account account, java.lang.String str, int i) {
        try {
            getContentService().cancelSyncAsUser(account, str, null, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.content.SyncAdapterType[] getSyncAdapterTypes() {
        try {
            return getContentService().getSyncAdapterTypes();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.content.SyncAdapterType[] getSyncAdapterTypesAsUser(int i) {
        try {
            return getContentService().getSyncAdapterTypesAsUser(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static java.lang.String[] getSyncAdapterPackagesForAuthorityAsUser(java.lang.String str, int i) {
        try {
            return getContentService().getSyncAdapterPackagesForAuthorityAsUser(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static java.lang.String getSyncAdapterPackageAsUser(java.lang.String str, java.lang.String str2, int i) {
        try {
            return getContentService().getSyncAdapterPackageAsUser(str, str2, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean getSyncAutomatically(android.accounts.Account account, java.lang.String str) {
        try {
            return getContentService().getSyncAutomatically(account, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean getSyncAutomaticallyAsUser(android.accounts.Account account, java.lang.String str, int i) {
        try {
            return getContentService().getSyncAutomaticallyAsUser(account, str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setSyncAutomatically(android.accounts.Account account, java.lang.String str, boolean z) {
        setSyncAutomaticallyAsUser(account, str, z, android.os.UserHandle.myUserId());
    }

    public static void setSyncAutomaticallyAsUser(android.accounts.Account account, java.lang.String str, boolean z, int i) {
        try {
            getContentService().setSyncAutomaticallyAsUser(account, str, z, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean hasInvalidScheduleAsEjExtras(android.os.Bundle bundle) {
        return bundle.getBoolean(SYNC_EXTRAS_REQUIRE_CHARGING) || bundle.getBoolean(SYNC_EXTRAS_EXPEDITED);
    }

    public static void addPeriodicSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, long j) {
        validateSyncExtrasBundle(bundle);
        if (invalidPeriodicExtras(bundle)) {
            throw new java.lang.IllegalArgumentException("illegal extras were set");
        }
        try {
            getContentService().addPeriodicSync(account, str, bundle, j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean invalidPeriodicExtras(android.os.Bundle bundle) {
        return bundle.getBoolean("force", false) || bundle.getBoolean(SYNC_EXTRAS_DO_NOT_RETRY, false) || bundle.getBoolean(SYNC_EXTRAS_IGNORE_BACKOFF, false) || bundle.getBoolean(SYNC_EXTRAS_IGNORE_SETTINGS, false) || bundle.getBoolean(SYNC_EXTRAS_INITIALIZE, false) || bundle.getBoolean("force", false) || bundle.getBoolean(SYNC_EXTRAS_EXPEDITED, false) || bundle.getBoolean(SYNC_EXTRAS_SCHEDULE_AS_EXPEDITED_JOB, false);
    }

    public static void removePeriodicSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) {
        validateSyncExtrasBundle(bundle);
        try {
            getContentService().removePeriodicSync(account, str, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void cancelSync(android.content.SyncRequest syncRequest) {
        if (syncRequest == null) {
            throw new java.lang.IllegalArgumentException("request cannot be null");
        }
        try {
            getContentService().cancelRequest(syncRequest);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static java.util.List<android.content.PeriodicSync> getPeriodicSyncs(android.accounts.Account account, java.lang.String str) {
        try {
            return getContentService().getPeriodicSyncs(account, str, null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int getIsSyncable(android.accounts.Account account, java.lang.String str) {
        try {
            return getContentService().getIsSyncable(account, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int getIsSyncableAsUser(android.accounts.Account account, java.lang.String str, int i) {
        try {
            return getContentService().getIsSyncableAsUser(account, str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setIsSyncable(android.accounts.Account account, java.lang.String str, int i) {
        try {
            getContentService().setIsSyncable(account, str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setIsSyncableAsUser(android.accounts.Account account, java.lang.String str, int i, int i2) {
        try {
            getContentService().setIsSyncableAsUser(account, str, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean getMasterSyncAutomatically() {
        try {
            return getContentService().getMasterSyncAutomatically();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean getMasterSyncAutomaticallyAsUser(int i) {
        try {
            return getContentService().getMasterSyncAutomaticallyAsUser(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setMasterSyncAutomatically(boolean z) {
        setMasterSyncAutomaticallyAsUser(z, android.os.UserHandle.myUserId());
    }

    public static void setMasterSyncAutomaticallyAsUser(boolean z, int i) {
        try {
            getContentService().setMasterSyncAutomaticallyAsUser(z, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isSyncActive(android.accounts.Account account, java.lang.String str) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("account must not be null");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("authority must not be null");
        }
        try {
            return getContentService().isSyncActive(account, str, null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public static android.content.SyncInfo getCurrentSync() {
        try {
            java.util.List<android.content.SyncInfo> currentSyncs = getContentService().getCurrentSyncs();
            if (currentSyncs.isEmpty()) {
                return null;
            }
            return currentSyncs.get(0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static java.util.List<android.content.SyncInfo> getCurrentSyncs() {
        try {
            return getContentService().getCurrentSyncs();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static java.util.List<android.content.SyncInfo> getCurrentSyncsAsUser(int i) {
        try {
            return getContentService().getCurrentSyncsAsUser(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.content.SyncStatusInfo getSyncStatus(android.accounts.Account account, java.lang.String str) {
        try {
            return getContentService().getSyncStatus(account, str, null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.content.SyncStatusInfo getSyncStatusAsUser(android.accounts.Account account, java.lang.String str, int i) {
        try {
            return getContentService().getSyncStatusAsUser(account, str, null, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isSyncPending(android.accounts.Account account, java.lang.String str) {
        return isSyncPendingAsUser(account, str, android.os.UserHandle.myUserId());
    }

    public static boolean isSyncPendingAsUser(android.accounts.Account account, java.lang.String str, int i) {
        try {
            return getContentService().isSyncPendingAsUser(account, str, null, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static java.lang.Object addStatusChangeListener(int i, final android.content.SyncStatusObserver syncStatusObserver) {
        if (syncStatusObserver == null) {
            throw new java.lang.IllegalArgumentException("you passed in a null callback");
        }
        try {
            android.content.ISyncStatusObserver.Stub stub = new android.content.ISyncStatusObserver.Stub() { // from class: android.content.ContentResolver.2
                @Override // android.content.ISyncStatusObserver
                public void onStatusChanged(int i2) throws android.os.RemoteException {
                    android.content.SyncStatusObserver.this.onStatusChanged(i2);
                }
            };
            getContentService().addStatusChangeListener(i, stub);
            return stub;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void removeStatusChangeListener(java.lang.Object obj) {
        if (obj == null) {
            throw new java.lang.IllegalArgumentException("you passed in a null handle");
        }
        try {
            getContentService().removeStatusChangeListener((android.content.ISyncStatusObserver.Stub) obj);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void putCache(android.net.Uri uri, android.os.Bundle bundle) {
        try {
            getContentService().putCache(this.mContext.getPackageName(), uri, bundle, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.os.Bundle getCache(android.net.Uri uri) {
        try {
            android.os.Bundle cache = getContentService().getCache(this.mContext.getPackageName(), uri, this.mContext.getUserId());
            if (cache != null) {
                cache.setClassLoader(this.mContext.getClassLoader());
            }
            return cache;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getTargetSdkVersion() {
        return this.mTargetSdkVersion;
    }

    private int samplePercentForDuration(long j) {
        if (j >= SLOW_THRESHOLD_MILLIS) {
            return 100;
        }
        return ((int) ((j * 100) / SLOW_THRESHOLD_MILLIS)) + 1;
    }

    private void maybeLogQueryToEventLog(long j, android.net.Uri uri, java.lang.String[] strArr, android.os.Bundle bundle) {
    }

    private void maybeLogUpdateToEventLog(long j, android.net.Uri uri, java.lang.String str, java.lang.String str2) {
    }

    private final class CursorWrapperInner extends android.database.CrossProcessCursorWrapper {
        private final dalvik.system.CloseGuard mCloseGuard;
        private final android.content.IContentProvider mContentProvider;
        private final java.util.concurrent.atomic.AtomicBoolean mProviderReleased;

        CursorWrapperInner(android.database.Cursor cursor, android.content.IContentProvider iContentProvider) {
            super(cursor);
            this.mProviderReleased = new java.util.concurrent.atomic.AtomicBoolean();
            this.mCloseGuard = dalvik.system.CloseGuard.get();
            this.mContentProvider = iContentProvider;
            this.mCloseGuard.open("CursorWrapperInner.close");
        }

        @Override // android.database.CursorWrapper, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.mCloseGuard.close();
            super.close();
            if (this.mProviderReleased.compareAndSet(false, true)) {
                android.content.ContentResolver.this.releaseProvider(this.mContentProvider);
            }
        }

        protected void finalize() throws java.lang.Throwable {
            try {
                if (this.mCloseGuard != null) {
                    this.mCloseGuard.warnIfOpen();
                }
                close();
            } finally {
                super.finalize();
            }
        }
    }

    private final class ParcelFileDescriptorInner extends android.os.ParcelFileDescriptor {
        private final android.content.IContentProvider mContentProvider;
        private final java.util.concurrent.atomic.AtomicBoolean mProviderReleased;

        ParcelFileDescriptorInner(android.os.ParcelFileDescriptor parcelFileDescriptor, android.content.IContentProvider iContentProvider) {
            super(parcelFileDescriptor);
            this.mProviderReleased = new java.util.concurrent.atomic.AtomicBoolean();
            this.mContentProvider = iContentProvider;
        }

        @Override // android.os.ParcelFileDescriptor
        public void releaseResources() {
            if (this.mProviderReleased.compareAndSet(false, true)) {
                android.content.ContentResolver.this.releaseProvider(this.mContentProvider);
            }
        }
    }

    public static android.content.IContentService getContentService() {
        if (sContentService != null) {
            return sContentService;
        }
        sContentService = android.content.IContentService.Stub.asInterface(android.os.ServiceManager.getService("content"));
        return sContentService;
    }

    public java.lang.String getPackageName() {
        return this.mContext.getOpPackageName();
    }

    public java.lang.String getAttributionTag() {
        return this.mContext.getAttributionTag();
    }

    public android.content.AttributionSource getAttributionSource() {
        return this.mContext.getAttributionSource();
    }

    public int resolveUserId(android.net.Uri uri) {
        return android.content.ContentProvider.getUserIdFromUri(uri, this.mContext.getUserId());
    }

    public int getUserId() {
        return this.mContext.getUserId();
    }

    @java.lang.Deprecated
    public android.graphics.drawable.Drawable getTypeDrawable(java.lang.String str) {
        return getTypeInfo(str).getIcon().loadDrawable(this.mContext);
    }

    public final android.content.ContentResolver.MimeTypeInfo getTypeInfo(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        return com.android.internal.util.MimeIconUtils.getTypeInfo(str);
    }

    public static final class MimeTypeInfo {
        private final java.lang.CharSequence mContentDescription;
        private final android.graphics.drawable.Icon mIcon;
        private final java.lang.CharSequence mLabel;

        public MimeTypeInfo(android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
            this.mIcon = (android.graphics.drawable.Icon) java.util.Objects.requireNonNull(icon);
            this.mLabel = (java.lang.CharSequence) java.util.Objects.requireNonNull(charSequence);
            this.mContentDescription = (java.lang.CharSequence) java.util.Objects.requireNonNull(charSequence2);
        }

        public android.graphics.drawable.Icon getIcon() {
            return this.mIcon;
        }

        public java.lang.CharSequence getLabel() {
            return this.mLabel;
        }

        public java.lang.CharSequence getContentDescription() {
            return this.mContentDescription;
        }
    }

    public static android.os.Bundle createSqlQueryBundle(java.lang.String str, java.lang.String[] strArr) {
        return createSqlQueryBundle(str, strArr, null);
    }

    public static android.os.Bundle createSqlQueryBundle(java.lang.String str, java.lang.String[] strArr, java.lang.String str2) {
        if (str == null && strArr == null && str2 == null) {
            return null;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        if (str != null) {
            bundle.putString(QUERY_ARG_SQL_SELECTION, str);
        }
        if (strArr != null) {
            bundle.putStringArray(QUERY_ARG_SQL_SELECTION_ARGS, strArr);
        }
        if (str2 != null) {
            bundle.putString(QUERY_ARG_SQL_SORT_ORDER, str2);
        }
        return bundle;
    }

    public static android.os.Bundle includeSqlSelectionArgs(android.os.Bundle bundle, java.lang.String str, java.lang.String[] strArr) {
        if (str != null) {
            bundle.putString(QUERY_ARG_SQL_SELECTION, str);
        }
        if (strArr != null) {
            bundle.putStringArray(QUERY_ARG_SQL_SELECTION_ARGS, strArr);
        }
        return bundle;
    }

    public static java.lang.String createSqlSortClause(android.os.Bundle bundle) {
        java.lang.String[] stringArray = bundle.getStringArray(QUERY_ARG_SORT_COLUMNS);
        if (stringArray == null || stringArray.length == 0) {
            throw new java.lang.IllegalArgumentException("Can't create sort clause without columns.");
        }
        java.lang.String join = android.text.TextUtils.join(", ", stringArray);
        int i = bundle.getInt(QUERY_ARG_SORT_COLLATION, 3);
        if (i == 0 || i == 1) {
            join = join + " COLLATE NOCASE";
        }
        int i2 = bundle.getInt(QUERY_ARG_SORT_DIRECTION, Integer.MIN_VALUE);
        if (i2 != Integer.MIN_VALUE) {
            switch (i2) {
                case 0:
                    return join + " ASC";
                case 1:
                    return join + " DESC";
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported sort direction value. See ContentResolver documentation for details.");
            }
        }
        return join;
    }

    public android.graphics.Bitmap loadThumbnail(android.net.Uri uri, android.util.Size size, android.os.CancellationSignal cancellationSignal) throws java.io.IOException {
        return loadThumbnail(this, uri, size, cancellationSignal, 1);
    }

    public static android.graphics.Bitmap loadThumbnail(final android.content.ContentInterface contentInterface, final android.net.Uri uri, final android.util.Size size, final android.os.CancellationSignal cancellationSignal, final int i) throws java.io.IOException {
        java.util.Objects.requireNonNull(contentInterface);
        java.util.Objects.requireNonNull(uri);
        java.util.Objects.requireNonNull(size);
        final android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable(EXTRA_SIZE, new android.graphics.Point(size.getWidth(), size.getHeight()));
        final android.system.Int64Ref int64Ref = new android.system.Int64Ref(0L);
        android.graphics.Bitmap decodeBitmap = android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource((java.util.concurrent.Callable<android.content.res.AssetFileDescriptor>) new java.util.concurrent.Callable() { // from class: android.content.ContentResolver$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final java.lang.Object call() {
                return android.content.ContentResolver.lambda$loadThumbnail$0(android.content.ContentInterface.this, uri, bundle, cancellationSignal, int64Ref);
            }
        }), new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: android.content.ContentResolver$$ExternalSyntheticLambda1
            @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
            public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
                android.content.ContentResolver.lambda$loadThumbnail$1(i, cancellationSignal, size, imageDecoder, imageInfo, source);
            }
        });
        if (int64Ref.value != 0) {
            int width = decodeBitmap.getWidth();
            int height = decodeBitmap.getHeight();
            android.graphics.Matrix matrix = new android.graphics.Matrix();
            matrix.setRotate(int64Ref.value, width / 2, height / 2);
            return android.graphics.Bitmap.createBitmap(decodeBitmap, 0, 0, width, height, matrix, false);
        }
        return decodeBitmap;
    }

    static /* synthetic */ android.content.res.AssetFileDescriptor lambda$loadThumbnail$0(android.content.ContentInterface contentInterface, android.net.Uri uri, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal, android.system.Int64Ref int64Ref) throws java.lang.Exception {
        android.content.res.AssetFileDescriptor openTypedAssetFile = contentInterface.openTypedAssetFile(uri, com.google.android.mms.ContentType.IMAGE_UNSPECIFIED, bundle, cancellationSignal);
        int64Ref.value = openTypedAssetFile.getExtras() != null ? r2.getInt(android.provider.DocumentsContract.EXTRA_ORIENTATION, 0) : 0L;
        return openTypedAssetFile;
    }

    static /* synthetic */ void lambda$loadThumbnail$1(int i, android.os.CancellationSignal cancellationSignal, android.util.Size size, android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
        imageDecoder.setAllocator(i);
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        int max = java.lang.Math.max(imageInfo.getSize().getWidth() / size.getWidth(), imageInfo.getSize().getHeight() / size.getHeight());
        if (max > 1) {
            imageDecoder.setTargetSampleSize(max);
        }
    }

    public static void onDbCorruption(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        try {
            getContentService().onDbCorruption(str, str2, android.util.Log.getStackTraceString(th));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public static android.net.Uri decodeFromFile(java.io.File file) {
        return translateDeprecatedDataPath(file.getAbsolutePath());
    }

    @android.annotation.SystemApi
    public static java.io.File encodeToFile(android.net.Uri uri) {
        return new java.io.File(translateDeprecatedDataPath(uri));
    }

    public static android.net.Uri translateDeprecatedDataPath(java.lang.String str) {
        return android.net.Uri.parse(new android.net.Uri.Builder().scheme("content").encodedOpaquePart("//" + str.substring(DEPRECATE_DATA_PREFIX.length())).build().toString());
    }

    public static java.lang.String translateDeprecatedDataPath(android.net.Uri uri) {
        return DEPRECATE_DATA_PREFIX + uri.getEncodedSchemeSpecificPart().substring(2);
    }
}
