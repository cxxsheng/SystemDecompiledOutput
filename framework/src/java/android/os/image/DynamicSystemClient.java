package android.os.image;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class DynamicSystemClient {
    public static final java.lang.String ACTION_HIDE_NOTIFICATION = "android.os.image.action.HIDE_NOTIFICATION";
    public static final java.lang.String ACTION_NOTIFY_IF_IN_USE = "android.os.image.action.NOTIFY_IF_IN_USE";
    public static final java.lang.String ACTION_NOTIFY_KEYGUARD_DISMISSED = "android.os.image.action.NOTIFY_KEYGUARD_DISMISSED";
    public static final java.lang.String ACTION_START_INSTALL = "android.os.image.action.START_INSTALL";
    public static final int CAUSE_ERROR_EXCEPTION = 6;
    public static final int CAUSE_ERROR_INVALID_URL = 4;
    public static final int CAUSE_ERROR_IO = 3;
    public static final int CAUSE_ERROR_IPC = 5;
    public static final int CAUSE_INSTALL_CANCELLED = 2;
    public static final int CAUSE_INSTALL_COMPLETED = 1;
    public static final int CAUSE_NOT_SPECIFIED = 0;
    public static final java.lang.String KEY_ENABLE_WHEN_COMPLETED = "KEY_ENABLE_WHEN_COMPLETED";
    public static final java.lang.String KEY_EXCEPTION_DETAIL = "KEY_EXCEPTION_DETAIL";
    public static final java.lang.String KEY_INSTALLED_SIZE = "KEY_INSTALLED_SIZE";
    public static final java.lang.String KEY_KEYGUARD_USE_DEFAULT_STRINGS = "KEY_KEYGUARD_USE_DEFAULT_STRINGS";
    public static final java.lang.String KEY_ONE_SHOT = "KEY_ONE_SHOT";
    public static final java.lang.String KEY_SYSTEM_SIZE = "KEY_SYSTEM_SIZE";
    public static final java.lang.String KEY_USERDATA_SIZE = "KEY_USERDATA_SIZE";
    public static final int MSG_POST_STATUS = 3;
    public static final int MSG_REGISTER_LISTENER = 1;
    public static final int MSG_UNREGISTER_LISTENER = 2;
    public static final int STATUS_IN_PROGRESS = 2;
    public static final int STATUS_IN_USE = 4;
    public static final int STATUS_NOT_STARTED = 1;
    public static final int STATUS_READY = 3;
    public static final int STATUS_UNKNOWN = 0;
    private static final java.lang.String TAG = "DynamicSystemClient";
    private boolean mBound;
    private final android.content.Context mContext;
    private java.util.concurrent.Executor mExecutor;
    private android.os.image.DynamicSystemClient.OnStatusChangedListener mListener;
    private android.os.Messenger mService;
    private final android.os.image.DynamicSystemClient.DynSystemServiceConnection mConnection = new android.os.image.DynamicSystemClient.DynSystemServiceConnection();
    private final android.os.Messenger mMessenger = new android.os.Messenger(new android.os.image.DynamicSystemClient.IncomingHandler(this));

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InstallationStatus {
    }

    public interface OnStatusChangedListener {
        void onStatusChanged(int i, int i2, long j, java.lang.Throwable th);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StatusChangedCause {
    }

    private static class IncomingHandler extends android.os.Handler {
        private final java.lang.ref.WeakReference<android.os.image.DynamicSystemClient> mWeakClient;

        IncomingHandler(android.os.image.DynamicSystemClient dynamicSystemClient) {
            super(android.os.Looper.getMainLooper());
            this.mWeakClient = new java.lang.ref.WeakReference<>(dynamicSystemClient);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.os.image.DynamicSystemClient dynamicSystemClient = this.mWeakClient.get();
            if (dynamicSystemClient != null) {
                dynamicSystemClient.handleMessage(message);
            }
        }
    }

    private class DynSystemServiceConnection implements android.content.ServiceConnection {
        private DynSystemServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            android.util.Slog.v(android.os.image.DynamicSystemClient.TAG, "onServiceConnected: " + componentName);
            android.os.image.DynamicSystemClient.this.mService = new android.os.Messenger(iBinder);
            try {
                android.os.Message obtain = android.os.Message.obtain((android.os.Handler) null, 1);
                obtain.replyTo = android.os.image.DynamicSystemClient.this.mMessenger;
                android.os.image.DynamicSystemClient.this.mService.send(obtain);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(android.os.image.DynamicSystemClient.TAG, "Unable to get status from installation service");
                android.os.image.DynamicSystemClient.this.notifyOnStatusChangedListener(0, 5, 0L, e);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            android.util.Slog.v(android.os.image.DynamicSystemClient.TAG, "onServiceDisconnected: " + componentName);
            android.os.image.DynamicSystemClient.this.mService = null;
        }
    }

    @android.annotation.SystemApi
    public DynamicSystemClient(android.content.Context context) {
        this.mContext = context;
    }

    public void setOnStatusChangedListener(java.util.concurrent.Executor executor, android.os.image.DynamicSystemClient.OnStatusChangedListener onStatusChangedListener) {
        this.mListener = onStatusChangedListener;
        this.mExecutor = executor;
    }

    public void setOnStatusChangedListener(android.os.image.DynamicSystemClient.OnStatusChangedListener onStatusChangedListener) {
        this.mListener = onStatusChangedListener;
        this.mExecutor = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOnStatusChangedListener(final int i, final int i2, final long j, final java.lang.Throwable th) {
        if (this.mListener != null) {
            if (this.mExecutor != null) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.image.DynamicSystemClient$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.image.DynamicSystemClient.this.lambda$notifyOnStatusChangedListener$0(i, i2, j, th);
                    }
                });
            } else {
                this.mListener.onStatusChanged(i, i2, j, th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyOnStatusChangedListener$0(int i, int i2, long j, java.lang.Throwable th) {
        this.mListener.onStatusChanged(i, i2, j, th);
    }

    @android.annotation.SystemApi
    public void bind() {
        android.content.Intent intent = new android.content.Intent();
        intent.setClassName("com.android.dynsystem", "com.android.dynsystem.DynamicSystemInstallationService");
        this.mContext.bindService(intent, this.mConnection, 1);
        this.mBound = true;
    }

    @android.annotation.SystemApi
    public void unbind() {
        if (!this.mBound) {
            return;
        }
        if (this.mService != null) {
            try {
                android.os.Message obtain = android.os.Message.obtain((android.os.Handler) null, 2);
                obtain.replyTo = this.mMessenger;
                this.mService.send(obtain);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Unable to unregister from installation service");
            }
        }
        this.mContext.unbindService(this.mConnection);
        this.mBound = false;
    }

    @android.annotation.SystemApi
    public void start(android.net.Uri uri, long j) {
        start(uri, j, 0L);
    }

    public void start(android.net.Uri uri, long j, long j2) {
        android.content.Intent intent = new android.content.Intent();
        intent.setClassName("com.android.dynsystem", "com.android.dynsystem.VerificationActivity");
        intent.setData(uri);
        intent.setAction(ACTION_START_INSTALL);
        intent.setFlags(268435456);
        intent.putExtra(KEY_SYSTEM_SIZE, j);
        intent.putExtra(KEY_USERDATA_SIZE, j2);
        this.mContext.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMessage(android.os.Message message) {
        switch (message.what) {
            case 3:
                int i = message.arg1;
                int i2 = message.arg2;
                android.os.Bundle bundle = (android.os.Bundle) message.obj;
                long j = bundle.getLong(KEY_INSTALLED_SIZE);
                android.os.ParcelableException parcelableException = (android.os.ParcelableException) bundle.getSerializable(KEY_EXCEPTION_DETAIL, android.os.ParcelableException.class);
                notifyOnStatusChangedListener(i, i2, j, parcelableException == null ? null : parcelableException.getCause());
                break;
        }
    }
}
