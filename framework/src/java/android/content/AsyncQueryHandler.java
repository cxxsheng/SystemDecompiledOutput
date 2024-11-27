package android.content;

/* loaded from: classes.dex */
public abstract class AsyncQueryHandler extends android.os.Handler {
    private static final int EVENT_ARG_DELETE = 4;
    private static final int EVENT_ARG_INSERT = 2;
    private static final int EVENT_ARG_QUERY = 1;
    private static final int EVENT_ARG_UPDATE = 3;
    private static final java.lang.String TAG = "AsyncQuery";
    private static final boolean localLOGV = false;
    private static android.os.Looper sLooper = null;
    final java.lang.ref.WeakReference<android.content.ContentResolver> mResolver;
    private android.os.Handler mWorkerThreadHandler;

    /* JADX INFO: Access modifiers changed from: protected */
    public static final class WorkerArgs {
        public java.lang.Object cookie;
        public android.os.Handler handler;
        public java.lang.String orderBy;
        public java.lang.String[] projection;
        public java.lang.Object result;
        public java.lang.String selection;
        public java.lang.String[] selectionArgs;
        public android.net.Uri uri;
        public android.content.ContentValues values;

        protected WorkerArgs() {
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public class WorkerHandler extends android.os.Handler {
        public WorkerHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.database.Cursor cursor;
            android.content.ContentResolver contentResolver = android.content.AsyncQueryHandler.this.mResolver.get();
            if (contentResolver == null) {
                return;
            }
            android.content.AsyncQueryHandler.WorkerArgs workerArgs = (android.content.AsyncQueryHandler.WorkerArgs) message.obj;
            int i = message.what;
            switch (message.arg1) {
                case 1:
                    try {
                        cursor = contentResolver.query(workerArgs.uri, workerArgs.projection, workerArgs.selection, workerArgs.selectionArgs, workerArgs.orderBy);
                        if (cursor != null) {
                            cursor.getCount();
                        }
                    } catch (java.lang.Exception e) {
                        android.util.Log.w(android.content.AsyncQueryHandler.TAG, "Exception thrown during handling EVENT_ARG_QUERY", e);
                        cursor = null;
                    }
                    workerArgs.result = cursor;
                    break;
                case 2:
                    workerArgs.result = contentResolver.insert(workerArgs.uri, workerArgs.values);
                    break;
                case 3:
                    workerArgs.result = java.lang.Integer.valueOf(contentResolver.update(workerArgs.uri, workerArgs.values, workerArgs.selection, workerArgs.selectionArgs));
                    break;
                case 4:
                    workerArgs.result = java.lang.Integer.valueOf(contentResolver.delete(workerArgs.uri, workerArgs.selection, workerArgs.selectionArgs));
                    break;
            }
            android.os.Message obtainMessage = workerArgs.handler.obtainMessage(i);
            obtainMessage.obj = workerArgs;
            obtainMessage.arg1 = message.arg1;
            obtainMessage.sendToTarget();
        }
    }

    public AsyncQueryHandler(android.content.ContentResolver contentResolver) {
        this.mResolver = new java.lang.ref.WeakReference<>(contentResolver);
        synchronized (android.content.AsyncQueryHandler.class) {
            if (sLooper == null) {
                android.os.HandlerThread handlerThread = new android.os.HandlerThread("AsyncQueryWorker");
                handlerThread.start();
                sLooper = handlerThread.getLooper();
            }
        }
        this.mWorkerThreadHandler = createHandler(sLooper);
    }

    protected android.os.Handler createHandler(android.os.Looper looper) {
        return new android.content.AsyncQueryHandler.WorkerHandler(looper);
    }

    public void startQuery(int i, java.lang.Object obj, android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2) {
        android.os.Message obtainMessage = this.mWorkerThreadHandler.obtainMessage(i);
        obtainMessage.arg1 = 1;
        android.content.AsyncQueryHandler.WorkerArgs workerArgs = new android.content.AsyncQueryHandler.WorkerArgs();
        workerArgs.handler = this;
        workerArgs.uri = uri;
        workerArgs.projection = strArr;
        workerArgs.selection = str;
        workerArgs.selectionArgs = strArr2;
        workerArgs.orderBy = str2;
        workerArgs.cookie = obj;
        obtainMessage.obj = workerArgs;
        this.mWorkerThreadHandler.sendMessage(obtainMessage);
    }

    public final void cancelOperation(int i) {
        this.mWorkerThreadHandler.removeMessages(i);
    }

    public final void startInsert(int i, java.lang.Object obj, android.net.Uri uri, android.content.ContentValues contentValues) {
        android.os.Message obtainMessage = this.mWorkerThreadHandler.obtainMessage(i);
        obtainMessage.arg1 = 2;
        android.content.AsyncQueryHandler.WorkerArgs workerArgs = new android.content.AsyncQueryHandler.WorkerArgs();
        workerArgs.handler = this;
        workerArgs.uri = uri;
        workerArgs.cookie = obj;
        workerArgs.values = contentValues;
        obtainMessage.obj = workerArgs;
        this.mWorkerThreadHandler.sendMessage(obtainMessage);
    }

    public final void startUpdate(int i, java.lang.Object obj, android.net.Uri uri, android.content.ContentValues contentValues, java.lang.String str, java.lang.String[] strArr) {
        android.os.Message obtainMessage = this.mWorkerThreadHandler.obtainMessage(i);
        obtainMessage.arg1 = 3;
        android.content.AsyncQueryHandler.WorkerArgs workerArgs = new android.content.AsyncQueryHandler.WorkerArgs();
        workerArgs.handler = this;
        workerArgs.uri = uri;
        workerArgs.cookie = obj;
        workerArgs.values = contentValues;
        workerArgs.selection = str;
        workerArgs.selectionArgs = strArr;
        obtainMessage.obj = workerArgs;
        this.mWorkerThreadHandler.sendMessage(obtainMessage);
    }

    public final void startDelete(int i, java.lang.Object obj, android.net.Uri uri, java.lang.String str, java.lang.String[] strArr) {
        android.os.Message obtainMessage = this.mWorkerThreadHandler.obtainMessage(i);
        obtainMessage.arg1 = 4;
        android.content.AsyncQueryHandler.WorkerArgs workerArgs = new android.content.AsyncQueryHandler.WorkerArgs();
        workerArgs.handler = this;
        workerArgs.uri = uri;
        workerArgs.cookie = obj;
        workerArgs.selection = str;
        workerArgs.selectionArgs = strArr;
        obtainMessage.obj = workerArgs;
        this.mWorkerThreadHandler.sendMessage(obtainMessage);
    }

    protected void onQueryComplete(int i, java.lang.Object obj, android.database.Cursor cursor) {
    }

    protected void onInsertComplete(int i, java.lang.Object obj, android.net.Uri uri) {
    }

    protected void onUpdateComplete(int i, java.lang.Object obj, int i2) {
    }

    protected void onDeleteComplete(int i, java.lang.Object obj, int i2) {
    }

    @Override // android.os.Handler
    public void handleMessage(android.os.Message message) {
        android.content.AsyncQueryHandler.WorkerArgs workerArgs = (android.content.AsyncQueryHandler.WorkerArgs) message.obj;
        int i = message.what;
        switch (message.arg1) {
            case 1:
                onQueryComplete(i, workerArgs.cookie, (android.database.Cursor) workerArgs.result);
                break;
            case 2:
                onInsertComplete(i, workerArgs.cookie, (android.net.Uri) workerArgs.result);
                break;
            case 3:
                onUpdateComplete(i, workerArgs.cookie, ((java.lang.Integer) workerArgs.result).intValue());
                break;
            case 4:
                onDeleteComplete(i, workerArgs.cookie, ((java.lang.Integer) workerArgs.result).intValue());
                break;
        }
    }
}
