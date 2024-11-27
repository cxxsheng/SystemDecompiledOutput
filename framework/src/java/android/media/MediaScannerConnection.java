package android.media;

/* loaded from: classes2.dex */
public class MediaScannerConnection implements android.content.ServiceConnection {
    private static final java.lang.String TAG = "MediaScannerConnection";
    private final android.media.MediaScannerConnection.MediaScannerConnectionClient mClient;

    @java.lang.Deprecated
    private boolean mConnected;
    private final android.content.Context mContext;

    @java.lang.Deprecated
    private final android.media.IMediaScannerListener.Stub mListener = new android.media.IMediaScannerListener.Stub() { // from class: android.media.MediaScannerConnection.1
        @Override // android.media.IMediaScannerListener
        public void scanCompleted(java.lang.String str, android.net.Uri uri) {
        }
    };
    private android.content.ContentProviderClient mProvider;

    @java.lang.Deprecated
    private android.media.IMediaScannerService mService;

    public interface MediaScannerConnectionClient extends android.media.MediaScannerConnection.OnScanCompletedListener {
        void onMediaScannerConnected();
    }

    public interface OnScanCompletedListener {
        void onScanCompleted(java.lang.String str, android.net.Uri uri);
    }

    public MediaScannerConnection(android.content.Context context, android.media.MediaScannerConnection.MediaScannerConnectionClient mediaScannerConnectionClient) {
        this.mContext = context;
        this.mClient = mediaScannerConnectionClient;
    }

    public void connect() {
        synchronized (this) {
            if (this.mProvider == null) {
                this.mProvider = this.mContext.getContentResolver().acquireContentProviderClient("media");
                if (this.mClient != null) {
                    this.mClient.onMediaScannerConnected();
                }
            }
        }
    }

    public void disconnect() {
        synchronized (this) {
            if (this.mProvider != null) {
                this.mProvider.close();
                this.mProvider = null;
            }
        }
    }

    public synchronized boolean isConnected() {
        return this.mProvider != null;
    }

    public void scanFile(final java.lang.String str, java.lang.String str2) {
        synchronized (this) {
            if (this.mProvider == null) {
                throw new java.lang.IllegalStateException("not connected to MediaScannerService");
            }
            com.android.internal.os.BackgroundThread.getExecutor().execute(new java.lang.Runnable() { // from class: android.media.MediaScannerConnection$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaScannerConnection.this.lambda$scanFile$0(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scanFile$0(java.lang.String str) {
        runCallBack(this.mContext, this.mClient, str, scanFileQuietly(this.mProvider, new java.io.File(str)));
    }

    public static void scanFile(final android.content.Context context, final java.lang.String[] strArr, java.lang.String[] strArr2, final android.media.MediaScannerConnection.OnScanCompletedListener onScanCompletedListener) {
        com.android.internal.os.BackgroundThread.getExecutor().execute(new java.lang.Runnable() { // from class: android.media.MediaScannerConnection$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.media.MediaScannerConnection.lambda$scanFile$1(android.content.Context.this, strArr, onScanCompletedListener);
            }
        });
    }

    static /* synthetic */ void lambda$scanFile$1(android.content.Context context, java.lang.String[] strArr, android.media.MediaScannerConnection.OnScanCompletedListener onScanCompletedListener) {
        android.content.ContentProviderClient acquireContentProviderClient = context.getContentResolver().acquireContentProviderClient("media");
        try {
            for (java.lang.String str : strArr) {
                runCallBack(context, onScanCompletedListener, str, scanFileQuietly(acquireContentProviderClient, new java.io.File(str)));
            }
            if (acquireContentProviderClient != null) {
                acquireContentProviderClient.close();
            }
        } catch (java.lang.Throwable th) {
            if (acquireContentProviderClient != null) {
                try {
                    acquireContentProviderClient.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static android.net.Uri scanFileQuietly(android.content.ContentProviderClient contentProviderClient, java.io.File file) {
        android.net.Uri uri = null;
        try {
            uri = android.provider.MediaStore.scanFile(android.content.ContentResolver.wrap(contentProviderClient), file.getCanonicalFile());
            android.util.Log.d(TAG, "Scanned " + file + " to " + uri);
            return uri;
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to scan " + file + ": " + e);
            return uri;
        }
    }

    private static void runCallBack(android.content.Context context, android.media.MediaScannerConnection.OnScanCompletedListener onScanCompletedListener, java.lang.String str, android.net.Uri uri) {
        if (onScanCompletedListener != null) {
            try {
                onScanCompletedListener.onScanCompleted(str, uri);
            } catch (java.lang.Throwable th) {
                if (context.getApplicationInfo().targetSdkVersion >= 30) {
                    throw th;
                }
                android.util.Log.w(TAG, "Ignoring exception from callback for backward compatibility", th);
            }
        }
    }

    @java.lang.Deprecated
    static class ClientProxy implements android.media.MediaScannerConnection.MediaScannerConnectionClient {
        final android.media.MediaScannerConnection.OnScanCompletedListener mClient;
        android.media.MediaScannerConnection mConnection;
        final java.lang.String[] mMimeTypes;
        int mNextPath;
        final java.lang.String[] mPaths;

        ClientProxy(java.lang.String[] strArr, java.lang.String[] strArr2, android.media.MediaScannerConnection.OnScanCompletedListener onScanCompletedListener) {
            this.mPaths = strArr;
            this.mMimeTypes = strArr2;
            this.mClient = onScanCompletedListener;
        }

        @Override // android.media.MediaScannerConnection.MediaScannerConnectionClient
        public void onMediaScannerConnected() {
        }

        @Override // android.media.MediaScannerConnection.OnScanCompletedListener
        public void onScanCompleted(java.lang.String str, android.net.Uri uri) {
        }

        void scanNextPath() {
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(android.content.ComponentName componentName) {
    }
}
