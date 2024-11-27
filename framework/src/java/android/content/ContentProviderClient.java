package android.content;

/* loaded from: classes.dex */
public class ContentProviderClient implements android.content.ContentInterface, java.lang.AutoCloseable {
    private static final java.lang.String TAG = "ContentProviderClient";
    private static android.os.Handler sAnrHandler;
    private android.content.ContentProviderClient.NotRespondingRunnable mAnrRunnable;
    private long mAnrTimeout;
    private final android.content.AttributionSource mAttributionSource;
    private final java.lang.String mAuthority;
    private final dalvik.system.CloseGuard mCloseGuard;
    private final java.util.concurrent.atomic.AtomicBoolean mClosed;
    private final android.content.IContentProvider mContentProvider;
    private final android.content.ContentResolver mContentResolver;
    private final java.lang.String mPackageName;
    private final boolean mStable;

    public ContentProviderClient(android.content.ContentResolver contentResolver, android.content.IContentProvider iContentProvider, boolean z) {
        this(contentResolver, iContentProvider, "unknown", z);
    }

    public ContentProviderClient(android.content.ContentResolver contentResolver, android.content.IContentProvider iContentProvider, java.lang.String str, boolean z) {
        this.mClosed = new java.util.concurrent.atomic.AtomicBoolean();
        this.mCloseGuard = dalvik.system.CloseGuard.get();
        this.mContentResolver = contentResolver;
        this.mContentProvider = iContentProvider;
        this.mPackageName = contentResolver.mPackageName;
        this.mAttributionSource = contentResolver.getAttributionSource();
        this.mAuthority = str;
        this.mStable = z;
        this.mCloseGuard.open("ContentProviderClient.close");
    }

    @android.annotation.SystemApi
    public void setDetectNotResponding(long j) {
        synchronized (android.content.ContentProviderClient.class) {
            this.mAnrTimeout = j;
            if (j > 0) {
                if (this.mAnrRunnable == null) {
                    this.mAnrRunnable = new android.content.ContentProviderClient.NotRespondingRunnable();
                }
                if (sAnrHandler == null) {
                    sAnrHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
                }
                android.os.Binder.allowBlocking(this.mContentProvider.asBinder());
            } else {
                this.mAnrRunnable = null;
                android.os.Binder.defaultBlocking(this.mContentProvider.asBinder());
            }
        }
    }

    private void beforeRemote() {
        if (this.mAnrRunnable != null) {
            sAnrHandler.postDelayed(this.mAnrRunnable, this.mAnrTimeout);
        }
    }

    private void afterRemote() {
        if (this.mAnrRunnable != null) {
            sAnrHandler.removeCallbacks(this.mAnrRunnable);
        }
    }

    public android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2) throws android.os.RemoteException {
        return query(uri, strArr, str, strArr2, str2, null);
    }

    public android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException {
        return query(uri, strArr, android.content.ContentResolver.createSqlQueryBundle(str, strArr2, str2), cancellationSignal);
    }

    @Override // android.content.ContentInterface
    public android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException {
        android.os.ICancellationSignal iCancellationSignal;
        java.util.Objects.requireNonNull(uri, "url");
        beforeRemote();
        if (cancellationSignal != null) {
            try {
                try {
                    cancellationSignal.throwIfCanceled();
                    android.os.ICancellationSignal createCancellationSignal = this.mContentProvider.createCancellationSignal();
                    cancellationSignal.setRemote(createCancellationSignal);
                    iCancellationSignal = createCancellationSignal;
                } catch (android.os.DeadObjectException e) {
                    if (!this.mStable) {
                        this.mContentResolver.unstableProviderDied(this.mContentProvider);
                    }
                    throw e;
                }
            } finally {
                afterRemote();
            }
        } else {
            iCancellationSignal = null;
        }
        android.database.Cursor query = this.mContentProvider.query(this.mAttributionSource, uri, strArr, bundle, iCancellationSignal);
        if (query == null) {
            return null;
        }
        return new android.content.ContentProviderClient.CursorWrapperInner(query);
    }

    @Override // android.content.ContentInterface
    public java.lang.String getType(android.net.Uri uri) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(uri, "url");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.getType(this.mAttributionSource, uri);
            } catch (android.os.DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    @Override // android.content.ContentInterface
    public java.lang.String[] getStreamTypes(android.net.Uri uri, java.lang.String str) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(uri, "url");
        java.util.Objects.requireNonNull(str, "mimeTypeFilter");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.getStreamTypes(this.mAttributionSource, uri, str);
            } catch (android.os.DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    @Override // android.content.ContentInterface
    public final android.net.Uri canonicalize(android.net.Uri uri) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(uri, "url");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.canonicalize(this.mAttributionSource, uri);
            } catch (android.os.DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    @Override // android.content.ContentInterface
    public final android.net.Uri uncanonicalize(android.net.Uri uri) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(uri, "url");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.uncanonicalize(this.mAttributionSource, uri);
            } catch (android.os.DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    @Override // android.content.ContentInterface
    public boolean refresh(android.net.Uri uri, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException {
        android.os.ICancellationSignal createCancellationSignal;
        java.util.Objects.requireNonNull(uri, "url");
        beforeRemote();
        if (cancellationSignal == null) {
            createCancellationSignal = null;
        } else {
            try {
                try {
                    cancellationSignal.throwIfCanceled();
                    createCancellationSignal = this.mContentProvider.createCancellationSignal();
                    cancellationSignal.setRemote(createCancellationSignal);
                } catch (android.os.DeadObjectException e) {
                    if (!this.mStable) {
                        this.mContentResolver.unstableProviderDied(this.mContentProvider);
                    }
                    throw e;
                }
            } finally {
                afterRemote();
            }
        }
        return this.mContentProvider.refresh(this.mAttributionSource, uri, bundle, createCancellationSignal);
    }

    @Override // android.content.ContentInterface
    public int checkUriPermission(android.net.Uri uri, int i, int i2) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(uri, "uri");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.checkUriPermission(this.mAttributionSource, uri, i, i2);
            } catch (android.os.DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    public android.net.Uri insert(android.net.Uri uri, android.content.ContentValues contentValues) throws android.os.RemoteException {
        return insert(uri, contentValues, null);
    }

    @Override // android.content.ContentInterface
    public android.net.Uri insert(android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(uri, "url");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.insert(this.mAttributionSource, uri, contentValues, bundle);
            } catch (android.os.DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    @Override // android.content.ContentInterface
    public int bulkInsert(android.net.Uri uri, android.content.ContentValues[] contentValuesArr) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(uri, "url");
        java.util.Objects.requireNonNull(contentValuesArr, "initialValues");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.bulkInsert(this.mAttributionSource, uri, contentValuesArr);
            } catch (android.os.DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    public int delete(android.net.Uri uri, java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException {
        return delete(uri, android.content.ContentResolver.createSqlQueryBundle(str, strArr));
    }

    @Override // android.content.ContentInterface
    public int delete(android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(uri, "url");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.delete(this.mAttributionSource, uri, bundle);
            } catch (android.os.DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    public int update(android.net.Uri uri, android.content.ContentValues contentValues, java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException {
        return update(uri, contentValues, android.content.ContentResolver.createSqlQueryBundle(str, strArr));
    }

    @Override // android.content.ContentInterface
    public int update(android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(uri, "url");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.update(this.mAttributionSource, uri, contentValues, bundle);
            } catch (android.os.DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    public android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String str) throws android.os.RemoteException, java.io.FileNotFoundException {
        return openFile(uri, str, null);
    }

    @Override // android.content.ContentInterface
    public android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String str, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException {
        android.os.ICancellationSignal createCancellationSignal;
        java.util.Objects.requireNonNull(uri, "url");
        java.util.Objects.requireNonNull(str, "mode");
        beforeRemote();
        if (cancellationSignal == null) {
            createCancellationSignal = null;
        } else {
            try {
                try {
                    cancellationSignal.throwIfCanceled();
                    createCancellationSignal = this.mContentProvider.createCancellationSignal();
                    cancellationSignal.setRemote(createCancellationSignal);
                } catch (android.os.DeadObjectException e) {
                    if (!this.mStable) {
                        this.mContentResolver.unstableProviderDied(this.mContentProvider);
                    }
                    throw e;
                }
            } finally {
                afterRemote();
            }
        }
        return this.mContentProvider.openFile(this.mAttributionSource, uri, str, createCancellationSignal);
    }

    public android.content.res.AssetFileDescriptor openAssetFile(android.net.Uri uri, java.lang.String str) throws android.os.RemoteException, java.io.FileNotFoundException {
        return openAssetFile(uri, str, null);
    }

    @Override // android.content.ContentInterface
    public android.content.res.AssetFileDescriptor openAssetFile(android.net.Uri uri, java.lang.String str, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException {
        android.os.ICancellationSignal createCancellationSignal;
        java.util.Objects.requireNonNull(uri, "url");
        java.util.Objects.requireNonNull(str, "mode");
        beforeRemote();
        if (cancellationSignal == null) {
            createCancellationSignal = null;
        } else {
            try {
                try {
                    cancellationSignal.throwIfCanceled();
                    createCancellationSignal = this.mContentProvider.createCancellationSignal();
                    cancellationSignal.setRemote(createCancellationSignal);
                } catch (android.os.DeadObjectException e) {
                    if (!this.mStable) {
                        this.mContentResolver.unstableProviderDied(this.mContentProvider);
                    }
                    throw e;
                }
            } finally {
                afterRemote();
            }
        }
        return this.mContentProvider.openAssetFile(this.mAttributionSource, uri, str, createCancellationSignal);
    }

    public final android.content.res.AssetFileDescriptor openTypedAssetFileDescriptor(android.net.Uri uri, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException, java.io.FileNotFoundException {
        return openTypedAssetFileDescriptor(uri, str, bundle, null);
    }

    public final android.content.res.AssetFileDescriptor openTypedAssetFileDescriptor(android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException {
        return openTypedAssetFile(uri, str, bundle, cancellationSignal);
    }

    @Override // android.content.ContentInterface
    public final android.content.res.AssetFileDescriptor openTypedAssetFile(android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException {
        android.os.ICancellationSignal iCancellationSignal;
        java.util.Objects.requireNonNull(uri, "uri");
        java.util.Objects.requireNonNull(str, "mimeTypeFilter");
        beforeRemote();
        try {
            if (cancellationSignal == null) {
                iCancellationSignal = null;
            } else {
                try {
                    cancellationSignal.throwIfCanceled();
                    android.os.ICancellationSignal createCancellationSignal = this.mContentProvider.createCancellationSignal();
                    cancellationSignal.setRemote(createCancellationSignal);
                    iCancellationSignal = createCancellationSignal;
                } catch (android.os.DeadObjectException e) {
                    if (!this.mStable) {
                        this.mContentResolver.unstableProviderDied(this.mContentProvider);
                    }
                    throw e;
                }
            }
            return this.mContentProvider.openTypedAssetFile(this.mAttributionSource, uri, str, bundle, iCancellationSignal);
        } finally {
            afterRemote();
        }
    }

    public android.content.ContentProviderResult[] applyBatch(java.util.ArrayList<android.content.ContentProviderOperation> arrayList) throws android.os.RemoteException, android.content.OperationApplicationException {
        return applyBatch(this.mAuthority, arrayList);
    }

    @Override // android.content.ContentInterface
    public android.content.ContentProviderResult[] applyBatch(java.lang.String str, java.util.ArrayList<android.content.ContentProviderOperation> arrayList) throws android.os.RemoteException, android.content.OperationApplicationException {
        java.util.Objects.requireNonNull(arrayList, "operations");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.applyBatch(this.mAttributionSource, str, arrayList);
            } catch (android.os.DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    public android.os.Bundle call(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        return call(this.mAuthority, str, str2, bundle);
    }

    @Override // android.content.ContentInterface
    public android.os.Bundle call(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.Bundle bundle) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(str, android.provider.ContactsContract.Directory.DIRECTORY_AUTHORITY);
        java.util.Objects.requireNonNull(str2, android.provider.CalendarContract.RemindersColumns.METHOD);
        beforeRemote();
        try {
            try {
                return this.mContentProvider.call(this.mAttributionSource, str, str2, str3, bundle);
            } catch (android.os.DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        closeInternal();
    }

    @java.lang.Deprecated
    public boolean release() {
        return closeInternal();
    }

    private boolean closeInternal() {
        this.mCloseGuard.close();
        if (!this.mClosed.compareAndSet(false, true)) {
            return false;
        }
        setDetectNotResponding(0L);
        if (this.mStable) {
            return this.mContentResolver.releaseProvider(this.mContentProvider);
        }
        return this.mContentResolver.releaseUnstableProvider(this.mContentProvider);
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

    public android.content.ContentProvider getLocalContentProvider() {
        return android.content.ContentProvider.coerceToLocalContentProvider(this.mContentProvider);
    }

    @java.lang.Deprecated
    public static void closeQuietly(android.content.ContentProviderClient contentProviderClient) {
        libcore.io.IoUtils.closeQuietly(contentProviderClient);
    }

    @java.lang.Deprecated
    public static void releaseQuietly(android.content.ContentProviderClient contentProviderClient) {
        libcore.io.IoUtils.closeQuietly(contentProviderClient);
    }

    private class NotRespondingRunnable implements java.lang.Runnable {
        private NotRespondingRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            android.util.Log.w(android.content.ContentProviderClient.TAG, "Detected provider not responding: " + android.content.ContentProviderClient.this.mContentProvider);
            android.content.ContentProviderClient.this.mContentResolver.appNotRespondingViaProvider(android.content.ContentProviderClient.this.mContentProvider);
        }
    }

    private final class CursorWrapperInner extends android.database.CrossProcessCursorWrapper {
        private final dalvik.system.CloseGuard mCloseGuard;

        CursorWrapperInner(android.database.Cursor cursor) {
            super(cursor);
            this.mCloseGuard = dalvik.system.CloseGuard.get();
            this.mCloseGuard.open("CursorWrapperInner.close");
        }

        @Override // android.database.CursorWrapper, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.mCloseGuard.close();
            super.close();
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
}
