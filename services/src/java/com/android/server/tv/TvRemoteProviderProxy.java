package com.android.server.tv;

/* loaded from: classes2.dex */
final class TvRemoteProviderProxy implements android.content.ServiceConnection {
    protected static final java.lang.String SERVICE_INTERFACE = "com.android.media.tv.remoteprovider.TvRemoteProvider";
    private boolean mBound;
    private final android.content.ComponentName mComponentName;
    private boolean mConnected;
    private final android.content.Context mContext;
    private final java.lang.Object mLock;
    private boolean mRunning;
    private final int mUid;
    private final int mUserId;
    private static final java.lang.String TAG = "TvRemoteProviderProxy";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 2);

    TvRemoteProviderProxy(android.content.Context context, java.lang.Object obj, android.content.ComponentName componentName, int i, int i2) {
        this.mContext = context;
        this.mLock = obj;
        this.mComponentName = componentName;
        this.mUserId = i;
        this.mUid = i2;
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "Proxy");
        printWriter.println(str + "  mUserId=" + this.mUserId);
        printWriter.println(str + "  mRunning=" + this.mRunning);
        printWriter.println(str + "  mBound=" + this.mBound);
        printWriter.println(str + "  mConnected=" + this.mConnected);
    }

    public boolean hasComponentName(java.lang.String str, java.lang.String str2) {
        return this.mComponentName.getPackageName().equals(str) && this.mComponentName.getClassName().equals(str2);
    }

    public void start() {
        if (!this.mRunning) {
            if (DEBUG) {
                android.util.Slog.d(TAG, this + ": Starting");
            }
            this.mRunning = true;
            bind();
        }
    }

    public void stop() {
        if (this.mRunning) {
            if (DEBUG) {
                android.util.Slog.d(TAG, this + ": Stopping");
            }
            this.mRunning = false;
            unbind();
        }
    }

    public void rebindIfDisconnected() {
        if (this.mRunning && !this.mConnected) {
            unbind();
            bind();
        }
    }

    private void bind() {
        if (!this.mBound) {
            if (DEBUG) {
                android.util.Slog.d(TAG, this + ": Binding");
            }
            android.content.Intent intent = new android.content.Intent(SERVICE_INTERFACE);
            intent.setComponent(this.mComponentName);
            try {
                this.mBound = this.mContext.bindServiceAsUser(intent, this, android.hardware.audio.common.V2_0.AudioFormat.AAC_MAIN, new android.os.UserHandle(this.mUserId));
                if (DEBUG && !this.mBound) {
                    android.util.Slog.d(TAG, this + ": Bind failed");
                }
            } catch (java.lang.SecurityException e) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, this + ": Bind failed", e);
                }
            }
        }
    }

    private void unbind() {
        if (this.mBound) {
            if (DEBUG) {
                android.util.Slog.d(TAG, this + ": Unbinding");
            }
            this.mBound = false;
            this.mContext.unbindService(this);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        if (DEBUG) {
            android.util.Slog.d(TAG, this + ": onServiceConnected()");
        }
        this.mConnected = true;
        android.media.tv.ITvRemoteProvider asInterface = android.media.tv.ITvRemoteProvider.Stub.asInterface(iBinder);
        if (asInterface == null) {
            android.util.Slog.e(TAG, this + ": Invalid binder");
            return;
        }
        try {
            asInterface.setRemoteServiceInputSink(new com.android.server.tv.TvRemoteServiceInput(this.mLock, asInterface));
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, this + ": Failed remote call to setRemoteServiceInputSink");
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(android.content.ComponentName componentName) {
        this.mConnected = false;
        if (DEBUG) {
            android.util.Slog.d(TAG, this + ": onServiceDisconnected()");
        }
    }
}
