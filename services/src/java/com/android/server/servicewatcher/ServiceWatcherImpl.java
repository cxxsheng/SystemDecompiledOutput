package com.android.server.servicewatcher;

/* loaded from: classes2.dex */
class ServiceWatcherImpl<TBoundServiceInfo extends com.android.server.servicewatcher.ServiceWatcher.BoundServiceInfo> implements com.android.server.servicewatcher.ServiceWatcher, com.android.server.servicewatcher.ServiceWatcher.ServiceChangedListener {
    static final long RETRY_DELAY_MS = 15000;
    final android.content.Context mContext;
    final android.os.Handler mHandler;
    private final com.android.internal.content.PackageMonitor mPackageMonitor = new com.android.internal.content.PackageMonitor() { // from class: com.android.server.servicewatcher.ServiceWatcherImpl.1
        public boolean onPackageChanged(java.lang.String str, int i, java.lang.String[] strArr) {
            return true;
        }

        public void onSomePackagesChanged() {
            com.android.server.servicewatcher.ServiceWatcherImpl.this.onServiceChanged(false);
        }
    };

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mRegistered = false;

    @com.android.internal.annotations.GuardedBy({"this"})
    private com.android.server.servicewatcher.ServiceWatcherImpl<TBoundServiceInfo>.MyServiceConnection mServiceConnection = new com.android.server.servicewatcher.ServiceWatcherImpl.MyServiceConnection(null);

    @android.annotation.Nullable
    final com.android.server.servicewatcher.ServiceWatcher.ServiceListener<? super TBoundServiceInfo> mServiceListener;
    final com.android.server.servicewatcher.ServiceWatcher.ServiceSupplier<TBoundServiceInfo> mServiceSupplier;
    final java.lang.String mTag;
    static final java.lang.String TAG = "ServiceWatcher";
    static final boolean D = android.util.Log.isLoggable(TAG, 3);

    ServiceWatcherImpl(android.content.Context context, android.os.Handler handler, java.lang.String str, com.android.server.servicewatcher.ServiceWatcher.ServiceSupplier<TBoundServiceInfo> serviceSupplier, com.android.server.servicewatcher.ServiceWatcher.ServiceListener<? super TBoundServiceInfo> serviceListener) {
        this.mContext = context;
        this.mHandler = handler;
        this.mTag = str;
        this.mServiceSupplier = serviceSupplier;
        this.mServiceListener = serviceListener;
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher
    public boolean checkServiceResolves() {
        return this.mServiceSupplier.hasMatchingService();
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher
    public synchronized void register() {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        this.mRegistered = true;
        this.mPackageMonitor.register(this.mContext, android.os.UserHandle.ALL, this.mHandler);
        this.mServiceSupplier.register(this);
        onServiceChanged(false);
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher
    public synchronized void unregister() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mServiceSupplier.unregister();
        this.mPackageMonitor.unregister();
        this.mRegistered = false;
        onServiceChanged(false);
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceChangedListener
    public synchronized void onServiceChanged() {
        onServiceChanged(false);
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher
    public synchronized void runOnBinder(final com.android.server.servicewatcher.ServiceWatcher.BinderOperation binderOperation) {
        final com.android.server.servicewatcher.ServiceWatcherImpl<TBoundServiceInfo>.MyServiceConnection myServiceConnection = this.mServiceConnection;
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.servicewatcher.ServiceWatcherImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.servicewatcher.ServiceWatcherImpl.MyServiceConnection.this.runOnBinder(binderOperation);
            }
        });
    }

    synchronized void onServiceChanged(boolean z) {
        TBoundServiceInfo tboundserviceinfo;
        try {
            if (this.mRegistered) {
                tboundserviceinfo = this.mServiceSupplier.getServiceInfo();
            } else {
                tboundserviceinfo = null;
            }
            if ((z | (!this.mServiceConnection.isConnected())) || !java.util.Objects.equals(this.mServiceConnection.getBoundServiceInfo(), tboundserviceinfo)) {
                android.util.Log.i(TAG, "[" + this.mTag + "] chose new implementation " + tboundserviceinfo);
                final com.android.server.servicewatcher.ServiceWatcherImpl<TBoundServiceInfo>.MyServiceConnection myServiceConnection = this.mServiceConnection;
                final com.android.server.servicewatcher.ServiceWatcherImpl<TBoundServiceInfo>.MyServiceConnection myServiceConnection2 = new com.android.server.servicewatcher.ServiceWatcherImpl.MyServiceConnection(tboundserviceinfo);
                this.mServiceConnection = myServiceConnection2;
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.servicewatcher.ServiceWatcherImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.servicewatcher.ServiceWatcherImpl.lambda$onServiceChanged$1(com.android.server.servicewatcher.ServiceWatcherImpl.MyServiceConnection.this, myServiceConnection2);
                    }
                });
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onServiceChanged$1(com.android.server.servicewatcher.ServiceWatcherImpl.MyServiceConnection myServiceConnection, com.android.server.servicewatcher.ServiceWatcherImpl.MyServiceConnection myServiceConnection2) {
        myServiceConnection.unbind();
        myServiceConnection2.bind();
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.servicewatcher.ServiceWatcher$BoundServiceInfo] */
    public java.lang.String toString() {
        com.android.server.servicewatcher.ServiceWatcherImpl<TBoundServiceInfo>.MyServiceConnection myServiceConnection;
        synchronized (this) {
            myServiceConnection = this.mServiceConnection;
        }
        return myServiceConnection.getBoundServiceInfo().toString();
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher
    public void dump(java.io.PrintWriter printWriter) {
        com.android.server.servicewatcher.ServiceWatcherImpl<TBoundServiceInfo>.MyServiceConnection myServiceConnection;
        synchronized (this) {
            myServiceConnection = this.mServiceConnection;
        }
        printWriter.println("target service=" + myServiceConnection.getBoundServiceInfo());
        printWriter.println("connected=" + myServiceConnection.isConnected());
    }

    /* JADX INFO: Access modifiers changed from: private */
    class MyServiceConnection implements android.content.ServiceConnection {

        @android.annotation.Nullable
        private volatile android.os.IBinder mBinder;

        @android.annotation.Nullable
        private final TBoundServiceInfo mBoundServiceInfo;

        @android.annotation.Nullable
        private java.lang.Runnable mRebinder;

        MyServiceConnection(@android.annotation.Nullable TBoundServiceInfo tboundserviceinfo) {
            this.mBoundServiceInfo = tboundserviceinfo;
        }

        @android.annotation.Nullable
        TBoundServiceInfo getBoundServiceInfo() {
            return this.mBoundServiceInfo;
        }

        boolean isConnected() {
            return this.mBinder != null;
        }

        void bind() {
            com.android.internal.util.Preconditions.checkState(android.os.Looper.myLooper() == com.android.server.servicewatcher.ServiceWatcherImpl.this.mHandler.getLooper());
            if (this.mBoundServiceInfo == null) {
                return;
            }
            if (com.android.server.servicewatcher.ServiceWatcherImpl.D) {
                android.util.Log.d(com.android.server.servicewatcher.ServiceWatcherImpl.TAG, "[" + com.android.server.servicewatcher.ServiceWatcherImpl.this.mTag + "] binding to " + this.mBoundServiceInfo);
            }
            this.mRebinder = null;
            try {
                if (!com.android.server.servicewatcher.ServiceWatcherImpl.this.mContext.bindServiceAsUser(new android.content.Intent(this.mBoundServiceInfo.getAction()).setComponent(this.mBoundServiceInfo.getComponentName()), this, 1073741829, com.android.server.servicewatcher.ServiceWatcherImpl.this.mHandler, android.os.UserHandle.of(this.mBoundServiceInfo.getUserId()))) {
                    android.util.Log.e(com.android.server.servicewatcher.ServiceWatcherImpl.TAG, "[" + com.android.server.servicewatcher.ServiceWatcherImpl.this.mTag + "] unexpected bind failure - retrying later");
                    this.mRebinder = new java.lang.Runnable() { // from class: com.android.server.servicewatcher.ServiceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.servicewatcher.ServiceWatcherImpl.MyServiceConnection.this.bind();
                        }
                    };
                    com.android.server.servicewatcher.ServiceWatcherImpl.this.mHandler.postDelayed(this.mRebinder, com.android.server.servicewatcher.ServiceWatcherImpl.RETRY_DELAY_MS);
                }
            } catch (java.lang.SecurityException e) {
                android.util.Log.e(com.android.server.servicewatcher.ServiceWatcherImpl.TAG, "[" + com.android.server.servicewatcher.ServiceWatcherImpl.this.mTag + "] " + this.mBoundServiceInfo + " bind failed", e);
            }
        }

        void unbind() {
            com.android.internal.util.Preconditions.checkState(android.os.Looper.myLooper() == com.android.server.servicewatcher.ServiceWatcherImpl.this.mHandler.getLooper());
            if (this.mBoundServiceInfo == null) {
                return;
            }
            if (com.android.server.servicewatcher.ServiceWatcherImpl.D) {
                android.util.Log.d(com.android.server.servicewatcher.ServiceWatcherImpl.TAG, "[" + com.android.server.servicewatcher.ServiceWatcherImpl.this.mTag + "] unbinding from " + this.mBoundServiceInfo);
            }
            if (this.mRebinder != null) {
                com.android.server.servicewatcher.ServiceWatcherImpl.this.mHandler.removeCallbacks(this.mRebinder);
                this.mRebinder = null;
            } else {
                com.android.server.servicewatcher.ServiceWatcherImpl.this.mContext.unbindService(this);
            }
            onServiceDisconnected(this.mBoundServiceInfo.getComponentName());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void runOnBinder(com.android.server.servicewatcher.ServiceWatcher.BinderOperation binderOperation) {
            com.android.internal.util.Preconditions.checkState(android.os.Looper.myLooper() == com.android.server.servicewatcher.ServiceWatcherImpl.this.mHandler.getLooper());
            if (this.mBinder == null) {
                binderOperation.onError(new android.os.DeadObjectException());
                return;
            }
            try {
                binderOperation.run(this.mBinder);
            } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                android.util.Log.e(com.android.server.servicewatcher.ServiceWatcherImpl.TAG, "[" + com.android.server.servicewatcher.ServiceWatcherImpl.this.mTag + "] error running operation on " + this.mBoundServiceInfo, e);
                binderOperation.onError(e);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            com.android.internal.util.Preconditions.checkState(android.os.Looper.myLooper() == com.android.server.servicewatcher.ServiceWatcherImpl.this.mHandler.getLooper());
            com.android.internal.util.Preconditions.checkState(this.mBinder == null);
            android.util.Log.i(com.android.server.servicewatcher.ServiceWatcherImpl.TAG, "[" + com.android.server.servicewatcher.ServiceWatcherImpl.this.mTag + "] connected to " + componentName.toShortString());
            this.mBinder = iBinder;
            if (com.android.server.servicewatcher.ServiceWatcherImpl.this.mServiceListener != null) {
                try {
                    com.android.server.servicewatcher.ServiceWatcherImpl.this.mServiceListener.onBind(iBinder, this.mBoundServiceInfo);
                } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                    android.util.Log.e(com.android.server.servicewatcher.ServiceWatcherImpl.TAG, "[" + com.android.server.servicewatcher.ServiceWatcherImpl.this.mTag + "] error running operation on " + this.mBoundServiceInfo, e);
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(android.content.ComponentName componentName) {
            com.android.internal.util.Preconditions.checkState(android.os.Looper.myLooper() == com.android.server.servicewatcher.ServiceWatcherImpl.this.mHandler.getLooper());
            if (this.mBinder == null) {
                return;
            }
            android.util.Log.i(com.android.server.servicewatcher.ServiceWatcherImpl.TAG, "[" + com.android.server.servicewatcher.ServiceWatcherImpl.this.mTag + "] disconnected from " + this.mBoundServiceInfo);
            this.mBinder = null;
            if (com.android.server.servicewatcher.ServiceWatcherImpl.this.mServiceListener != null) {
                com.android.server.servicewatcher.ServiceWatcherImpl.this.mServiceListener.onUnbind();
            }
        }

        @Override // android.content.ServiceConnection
        public final void onBindingDied(android.content.ComponentName componentName) {
            com.android.internal.util.Preconditions.checkState(android.os.Looper.myLooper() == com.android.server.servicewatcher.ServiceWatcherImpl.this.mHandler.getLooper());
            android.util.Log.w(com.android.server.servicewatcher.ServiceWatcherImpl.TAG, "[" + com.android.server.servicewatcher.ServiceWatcherImpl.this.mTag + "] " + this.mBoundServiceInfo + " died");
            com.android.server.servicewatcher.ServiceWatcherImpl.this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.servicewatcher.ServiceWatcherImpl$MyServiceConnection$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.servicewatcher.ServiceWatcherImpl.MyServiceConnection.this.lambda$onBindingDied$0();
                }
            }, 500L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBindingDied$0() {
            com.android.server.servicewatcher.ServiceWatcherImpl.this.onServiceChanged(true);
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(android.content.ComponentName componentName) {
            android.util.Log.e(com.android.server.servicewatcher.ServiceWatcherImpl.TAG, "[" + com.android.server.servicewatcher.ServiceWatcherImpl.this.mTag + "] " + this.mBoundServiceInfo + " has null binding");
        }
    }
}
