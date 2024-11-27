package com.android.server.media;

/* loaded from: classes2.dex */
final class RemoteDisplayProviderProxy implements android.content.ServiceConnection {
    private com.android.server.media.RemoteDisplayProviderProxy.Connection mActiveConnection;
    private boolean mBound;
    private final android.content.ComponentName mComponentName;
    private boolean mConnectionReady;
    private final android.content.Context mContext;
    private int mDiscoveryMode;
    private android.media.RemoteDisplayState mDisplayState;
    private com.android.server.media.RemoteDisplayProviderProxy.Callback mDisplayStateCallback;
    private final java.lang.Runnable mDisplayStateChanged = new java.lang.Runnable() { // from class: com.android.server.media.RemoteDisplayProviderProxy.1
        @Override // java.lang.Runnable
        public void run() {
            com.android.server.media.RemoteDisplayProviderProxy.this.mScheduledDisplayStateChangedCallback = false;
            if (com.android.server.media.RemoteDisplayProviderProxy.this.mDisplayStateCallback != null) {
                com.android.server.media.RemoteDisplayProviderProxy.this.mDisplayStateCallback.onDisplayStateChanged(com.android.server.media.RemoteDisplayProviderProxy.this, com.android.server.media.RemoteDisplayProviderProxy.this.mDisplayState);
            }
        }
    };
    private final android.os.Handler mHandler = new android.os.Handler();
    private boolean mRunning;
    private boolean mScheduledDisplayStateChangedCallback;
    private java.lang.String mSelectedDisplayId;
    private final int mUserId;
    private static final java.lang.String TAG = "RemoteDisplayProvider";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    public interface Callback {
        void onDisplayStateChanged(com.android.server.media.RemoteDisplayProviderProxy remoteDisplayProviderProxy, android.media.RemoteDisplayState remoteDisplayState);
    }

    public RemoteDisplayProviderProxy(android.content.Context context, android.content.ComponentName componentName, int i) {
        this.mContext = context;
        this.mComponentName = componentName;
        this.mUserId = i;
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "Proxy");
        printWriter.println(str + "  mUserId=" + this.mUserId);
        printWriter.println(str + "  mRunning=" + this.mRunning);
        printWriter.println(str + "  mBound=" + this.mBound);
        printWriter.println(str + "  mActiveConnection=" + this.mActiveConnection);
        printWriter.println(str + "  mConnectionReady=" + this.mConnectionReady);
        printWriter.println(str + "  mDiscoveryMode=" + this.mDiscoveryMode);
        printWriter.println(str + "  mSelectedDisplayId=" + this.mSelectedDisplayId);
        printWriter.println(str + "  mDisplayState=" + this.mDisplayState);
    }

    public void setCallback(com.android.server.media.RemoteDisplayProviderProxy.Callback callback) {
        this.mDisplayStateCallback = callback;
    }

    public android.media.RemoteDisplayState getDisplayState() {
        return this.mDisplayState;
    }

    public void setDiscoveryMode(int i) {
        if (this.mDiscoveryMode != i) {
            this.mDiscoveryMode = i;
            if (this.mConnectionReady) {
                this.mActiveConnection.setDiscoveryMode(i);
            }
            updateBinding();
        }
    }

    public void setSelectedDisplay(java.lang.String str) {
        if (!java.util.Objects.equals(this.mSelectedDisplayId, str)) {
            if (this.mConnectionReady && this.mSelectedDisplayId != null) {
                this.mActiveConnection.disconnect(this.mSelectedDisplayId);
            }
            this.mSelectedDisplayId = str;
            if (this.mConnectionReady && str != null) {
                this.mActiveConnection.connect(str);
            }
            updateBinding();
        }
    }

    public void setDisplayVolume(int i) {
        if (this.mConnectionReady && this.mSelectedDisplayId != null) {
            this.mActiveConnection.setVolume(this.mSelectedDisplayId, i);
        }
    }

    public void adjustDisplayVolume(int i) {
        if (this.mConnectionReady && this.mSelectedDisplayId != null) {
            this.mActiveConnection.adjustVolume(this.mSelectedDisplayId, i);
        }
    }

    public boolean hasComponentName(java.lang.String str, java.lang.String str2) {
        return this.mComponentName.getPackageName().equals(str) && this.mComponentName.getClassName().equals(str2);
    }

    public java.lang.String getFlattenedComponentName() {
        return this.mComponentName.flattenToShortString();
    }

    public void start() {
        if (!this.mRunning) {
            if (DEBUG) {
                android.util.Slog.d(TAG, this + ": Starting");
            }
            this.mRunning = true;
            updateBinding();
        }
    }

    public void stop() {
        if (this.mRunning) {
            if (DEBUG) {
                android.util.Slog.d(TAG, this + ": Stopping");
            }
            this.mRunning = false;
            updateBinding();
        }
    }

    public void rebindIfDisconnected() {
        if (this.mActiveConnection == null && shouldBind()) {
            unbind();
            bind();
        }
    }

    private void updateBinding() {
        if (shouldBind()) {
            bind();
        } else {
            unbind();
        }
    }

    private boolean shouldBind() {
        if (this.mRunning) {
            if (this.mDiscoveryMode != 0 || this.mSelectedDisplayId != null) {
                return true;
            }
            return false;
        }
        return false;
    }

    private void bind() {
        if (!this.mBound) {
            if (DEBUG) {
                android.util.Slog.d(TAG, this + ": Binding");
            }
            android.content.Intent intent = new android.content.Intent("com.android.media.remotedisplay.RemoteDisplayProvider");
            intent.setComponent(this.mComponentName);
            try {
                this.mBound = this.mContext.bindServiceAsUser(intent, this, android.hardware.audio.common.V2_0.AudioFormat.AAC_MAIN, new android.os.UserHandle(this.mUserId));
                if (!this.mBound && DEBUG) {
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
            disconnect();
            this.mContext.unbindService(this);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        if (DEBUG) {
            android.util.Slog.d(TAG, this + ": Connected");
        }
        if (this.mBound) {
            disconnect();
            android.media.IRemoteDisplayProvider asInterface = android.media.IRemoteDisplayProvider.Stub.asInterface(iBinder);
            if (asInterface != null) {
                com.android.server.media.RemoteDisplayProviderProxy.Connection connection = new com.android.server.media.RemoteDisplayProviderProxy.Connection(asInterface);
                if (connection.register()) {
                    this.mActiveConnection = connection;
                    return;
                } else {
                    if (DEBUG) {
                        android.util.Slog.d(TAG, this + ": Registration failed");
                        return;
                    }
                    return;
                }
            }
            android.util.Slog.e(TAG, this + ": Service returned invalid remote display provider binder");
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(android.content.ComponentName componentName) {
        if (DEBUG) {
            android.util.Slog.d(TAG, this + ": Service disconnected");
        }
        disconnect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectionReady(com.android.server.media.RemoteDisplayProviderProxy.Connection connection) {
        if (this.mActiveConnection == connection) {
            this.mConnectionReady = true;
            if (this.mDiscoveryMode != 0) {
                this.mActiveConnection.setDiscoveryMode(this.mDiscoveryMode);
            }
            if (this.mSelectedDisplayId != null) {
                this.mActiveConnection.connect(this.mSelectedDisplayId);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectionDied(com.android.server.media.RemoteDisplayProviderProxy.Connection connection) {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                android.util.Slog.d(TAG, this + ": Service connection died");
            }
            disconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDisplayStateChanged(com.android.server.media.RemoteDisplayProviderProxy.Connection connection, android.media.RemoteDisplayState remoteDisplayState) {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                android.util.Slog.d(TAG, this + ": State changed, state=" + remoteDisplayState);
            }
            setDisplayState(remoteDisplayState);
        }
    }

    private void disconnect() {
        if (this.mActiveConnection != null) {
            if (this.mSelectedDisplayId != null) {
                this.mActiveConnection.disconnect(this.mSelectedDisplayId);
            }
            this.mConnectionReady = false;
            this.mActiveConnection.dispose();
            this.mActiveConnection = null;
            setDisplayState(null);
        }
    }

    private void setDisplayState(android.media.RemoteDisplayState remoteDisplayState) {
        if (!java.util.Objects.equals(this.mDisplayState, remoteDisplayState)) {
            this.mDisplayState = remoteDisplayState;
            if (!this.mScheduledDisplayStateChangedCallback) {
                this.mScheduledDisplayStateChangedCallback = true;
                this.mHandler.post(this.mDisplayStateChanged);
            }
        }
    }

    public java.lang.String toString() {
        return "Service connection " + this.mComponentName.flattenToShortString();
    }

    private final class Connection implements android.os.IBinder.DeathRecipient {
        private final com.android.server.media.RemoteDisplayProviderProxy.ProviderCallback mCallback = new com.android.server.media.RemoteDisplayProviderProxy.ProviderCallback(this);
        private final android.media.IRemoteDisplayProvider mProvider;

        public Connection(android.media.IRemoteDisplayProvider iRemoteDisplayProvider) {
            this.mProvider = iRemoteDisplayProvider;
        }

        public boolean register() {
            try {
                this.mProvider.asBinder().linkToDeath(this, 0);
                this.mProvider.setCallback(this.mCallback);
                com.android.server.media.RemoteDisplayProviderProxy.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.RemoteDisplayProviderProxy.Connection.1
                    @Override // java.lang.Runnable
                    public void run() {
                        com.android.server.media.RemoteDisplayProviderProxy.this.onConnectionReady(com.android.server.media.RemoteDisplayProviderProxy.Connection.this);
                    }
                });
                return true;
            } catch (android.os.RemoteException e) {
                binderDied();
                return false;
            }
        }

        public void dispose() {
            this.mProvider.asBinder().unlinkToDeath(this, 0);
            this.mCallback.dispose();
        }

        public void setDiscoveryMode(int i) {
            try {
                this.mProvider.setDiscoveryMode(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.RemoteDisplayProviderProxy.TAG, "Failed to deliver request to set discovery mode.", e);
            }
        }

        public void connect(java.lang.String str) {
            try {
                this.mProvider.connect(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.RemoteDisplayProviderProxy.TAG, "Failed to deliver request to connect to display.", e);
            }
        }

        public void disconnect(java.lang.String str) {
            try {
                this.mProvider.disconnect(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.RemoteDisplayProviderProxy.TAG, "Failed to deliver request to disconnect from display.", e);
            }
        }

        public void setVolume(java.lang.String str, int i) {
            try {
                this.mProvider.setVolume(str, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.RemoteDisplayProviderProxy.TAG, "Failed to deliver request to set display volume.", e);
            }
        }

        public void adjustVolume(java.lang.String str, int i) {
            try {
                this.mProvider.adjustVolume(str, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.RemoteDisplayProviderProxy.TAG, "Failed to deliver request to adjust display volume.", e);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.media.RemoteDisplayProviderProxy.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.RemoteDisplayProviderProxy.Connection.2
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.media.RemoteDisplayProviderProxy.this.onConnectionDied(com.android.server.media.RemoteDisplayProviderProxy.Connection.this);
                }
            });
        }

        void postStateChanged(final android.media.RemoteDisplayState remoteDisplayState) {
            com.android.server.media.RemoteDisplayProviderProxy.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.RemoteDisplayProviderProxy.Connection.3
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.media.RemoteDisplayProviderProxy.this.onDisplayStateChanged(com.android.server.media.RemoteDisplayProviderProxy.Connection.this, remoteDisplayState);
                }
            });
        }
    }

    private static final class ProviderCallback extends android.media.IRemoteDisplayCallback.Stub {
        private final java.lang.ref.WeakReference<com.android.server.media.RemoteDisplayProviderProxy.Connection> mConnectionRef;

        public ProviderCallback(com.android.server.media.RemoteDisplayProviderProxy.Connection connection) {
            this.mConnectionRef = new java.lang.ref.WeakReference<>(connection);
        }

        public void dispose() {
            this.mConnectionRef.clear();
        }

        public void onStateChanged(android.media.RemoteDisplayState remoteDisplayState) throws android.os.RemoteException {
            com.android.server.media.RemoteDisplayProviderProxy.Connection connection = this.mConnectionRef.get();
            if (connection != null) {
                connection.postStateChanged(remoteDisplayState);
            }
        }
    }
}
