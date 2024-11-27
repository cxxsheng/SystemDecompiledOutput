package android.content;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class CursorLoader extends android.content.AsyncTaskLoader<android.database.Cursor> {
    android.os.CancellationSignal mCancellationSignal;
    android.database.Cursor mCursor;
    final android.content.Loader<android.database.Cursor>.ForceLoadContentObserver mObserver;
    java.lang.String[] mProjection;
    java.lang.String mSelection;
    java.lang.String[] mSelectionArgs;
    java.lang.String mSortOrder;
    android.net.Uri mUri;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.content.AsyncTaskLoader
    public android.database.Cursor loadInBackground() {
        synchronized (this) {
            if (isLoadInBackgroundCanceled()) {
                throw new android.os.OperationCanceledException();
            }
            this.mCancellationSignal = new android.os.CancellationSignal();
        }
        try {
            android.database.Cursor query = getContext().getContentResolver().query(this.mUri, this.mProjection, this.mSelection, this.mSelectionArgs, this.mSortOrder, this.mCancellationSignal);
            if (query != null) {
                try {
                    query.getCount();
                    query.registerContentObserver(this.mObserver);
                } catch (java.lang.RuntimeException e) {
                    query.close();
                    throw e;
                }
            }
            synchronized (this) {
                this.mCancellationSignal = null;
            }
            return query;
        } catch (java.lang.Throwable th) {
            synchronized (this) {
                this.mCancellationSignal = null;
                throw th;
            }
        }
    }

    @Override // android.content.AsyncTaskLoader
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
        synchronized (this) {
            if (this.mCancellationSignal != null) {
                this.mCancellationSignal.cancel();
            }
        }
    }

    @Override // android.content.Loader
    public void deliverResult(android.database.Cursor cursor) {
        if (isReset()) {
            if (cursor != null) {
                cursor.close();
                return;
            }
            return;
        }
        android.database.Cursor cursor2 = this.mCursor;
        this.mCursor = cursor;
        if (isStarted()) {
            super.deliverResult((android.content.CursorLoader) cursor);
        }
        if (cursor2 != null && cursor2 != cursor && !cursor2.isClosed()) {
            cursor2.close();
        }
    }

    public CursorLoader(android.content.Context context) {
        super(context);
        this.mObserver = new android.content.Loader.ForceLoadContentObserver();
    }

    public CursorLoader(android.content.Context context, android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2) {
        super(context);
        this.mObserver = new android.content.Loader.ForceLoadContentObserver();
        this.mUri = uri;
        this.mProjection = strArr;
        this.mSelection = str;
        this.mSelectionArgs = strArr2;
        this.mSortOrder = str2;
    }

    @Override // android.content.Loader
    protected void onStartLoading() {
        if (this.mCursor != null) {
            deliverResult(this.mCursor);
        }
        if (takeContentChanged() || this.mCursor == null) {
            forceLoad();
        }
    }

    @Override // android.content.Loader
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override // android.content.AsyncTaskLoader
    public void onCanceled(android.database.Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override // android.content.Loader
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (this.mCursor != null && !this.mCursor.isClosed()) {
            this.mCursor.close();
        }
        this.mCursor = null;
    }

    public android.net.Uri getUri() {
        return this.mUri;
    }

    public void setUri(android.net.Uri uri) {
        this.mUri = uri;
    }

    public java.lang.String[] getProjection() {
        return this.mProjection;
    }

    public void setProjection(java.lang.String[] strArr) {
        this.mProjection = strArr;
    }

    public java.lang.String getSelection() {
        return this.mSelection;
    }

    public void setSelection(java.lang.String str) {
        this.mSelection = str;
    }

    public java.lang.String[] getSelectionArgs() {
        return this.mSelectionArgs;
    }

    public void setSelectionArgs(java.lang.String[] strArr) {
        this.mSelectionArgs = strArr;
    }

    public java.lang.String getSortOrder() {
        return this.mSortOrder;
    }

    public void setSortOrder(java.lang.String str) {
        this.mSortOrder = str;
    }

    @Override // android.content.AsyncTaskLoader, android.content.Loader
    public void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        printWriter.print(str);
        printWriter.print("mUri=");
        printWriter.println(this.mUri);
        printWriter.print(str);
        printWriter.print("mProjection=");
        printWriter.println(java.util.Arrays.toString(this.mProjection));
        printWriter.print(str);
        printWriter.print("mSelection=");
        printWriter.println(this.mSelection);
        printWriter.print(str);
        printWriter.print("mSelectionArgs=");
        printWriter.println(java.util.Arrays.toString(this.mSelectionArgs));
        printWriter.print(str);
        printWriter.print("mSortOrder=");
        printWriter.println(this.mSortOrder);
        printWriter.print(str);
        printWriter.print("mCursor=");
        printWriter.println(this.mCursor);
        printWriter.print(str);
        printWriter.print("mContentChanged=");
        printWriter.println(this.mContentChanged);
    }
}
