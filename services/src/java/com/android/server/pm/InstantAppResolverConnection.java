package com.android.server.pm;

/* loaded from: classes2.dex */
final class InstantAppResolverConnection implements android.os.IBinder.DeathRecipient {
    private static final long BIND_SERVICE_TIMEOUT_MS;
    private static final long CALL_SERVICE_TIMEOUT_MS;
    private static final boolean DEBUG_INSTANT;
    private static final int STATE_BINDING = 1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PENDING = 2;
    private static final java.lang.String TAG = "PackageManager";
    private final android.content.Context mContext;
    private final android.content.Intent mIntent;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.app.IInstantAppResolver mRemoteInstance;
    private final java.lang.Object mLock = new java.lang.Object();
    private final com.android.server.pm.InstantAppResolverConnection.GetInstantAppResolveInfoCaller mGetInstantAppResolveInfoCaller = new com.android.server.pm.InstantAppResolverConnection.GetInstantAppResolveInfoCaller();
    private final android.content.ServiceConnection mServiceConnection = new com.android.server.pm.InstantAppResolverConnection.MyServiceConnection();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mBindState = 0;
    private final android.os.Handler mBgHandler = com.android.internal.os.BackgroundThread.getHandler();

    public static abstract class PhaseTwoCallback {
        /* JADX INFO: Access modifiers changed from: package-private */
        public abstract void onPhaseTwoResolved(java.util.List<android.content.pm.InstantAppResolveInfo> list, long j);
    }

    static {
        BIND_SERVICE_TIMEOUT_MS = android.os.Build.IS_ENG ? 500L : 300L;
        CALL_SERVICE_TIMEOUT_MS = android.os.Build.IS_ENG ? 200L : 100L;
        DEBUG_INSTANT = android.os.Build.IS_DEBUGGABLE;
    }

    public InstantAppResolverConnection(android.content.Context context, android.content.ComponentName componentName, java.lang.String str) {
        this.mContext = context;
        this.mIntent = new android.content.Intent(str).setComponent(componentName);
    }

    public java.util.List<android.content.pm.InstantAppResolveInfo> getInstantAppResolveInfoList(android.content.pm.InstantAppRequestInfo instantAppRequestInfo) throws com.android.server.pm.InstantAppResolverConnection.ConnectionException {
        throwIfCalledOnMainThread();
        try {
            try {
                try {
                    try {
                        java.util.List<android.content.pm.InstantAppResolveInfo> instantAppResolveInfoList = this.mGetInstantAppResolveInfoCaller.getInstantAppResolveInfoList(getRemoteInstanceLazy(instantAppRequestInfo.getToken()), instantAppRequestInfo);
                        synchronized (this.mLock) {
                            this.mLock.notifyAll();
                        }
                        return instantAppResolveInfoList;
                    } catch (android.os.RemoteException e) {
                        synchronized (this.mLock) {
                            this.mLock.notifyAll();
                            return null;
                        }
                    } catch (java.util.concurrent.TimeoutException e2) {
                        throw new com.android.server.pm.InstantAppResolverConnection.ConnectionException(2);
                    }
                } catch (java.util.concurrent.TimeoutException e3) {
                    throw new com.android.server.pm.InstantAppResolverConnection.ConnectionException(1);
                }
            } catch (java.lang.InterruptedException e4) {
                throw new com.android.server.pm.InstantAppResolverConnection.ConnectionException(3);
            }
        } catch (java.lang.Throwable th) {
            synchronized (this.mLock) {
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    /* renamed from: com.android.server.pm.InstantAppResolverConnection$1, reason: invalid class name */
    class AnonymousClass1 extends android.os.IRemoteCallback.Stub {
        final /* synthetic */ com.android.server.pm.InstantAppResolverConnection.PhaseTwoCallback val$callback;
        final /* synthetic */ android.os.Handler val$callbackHandler;
        final /* synthetic */ long val$startTime;

        AnonymousClass1(android.os.Handler handler, com.android.server.pm.InstantAppResolverConnection.PhaseTwoCallback phaseTwoCallback, long j) {
            this.val$callbackHandler = handler;
            this.val$callback = phaseTwoCallback;
            this.val$startTime = j;
        }

        public void sendResult(android.os.Bundle bundle) throws android.os.RemoteException {
            final java.util.ArrayList parcelableArrayList = bundle.getParcelableArrayList("android.app.extra.RESOLVE_INFO", android.content.pm.InstantAppResolveInfo.class);
            android.os.Handler handler = this.val$callbackHandler;
            final com.android.server.pm.InstantAppResolverConnection.PhaseTwoCallback phaseTwoCallback = this.val$callback;
            final long j = this.val$startTime;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.pm.InstantAppResolverConnection$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.InstantAppResolverConnection.PhaseTwoCallback.this.onPhaseTwoResolved(parcelableArrayList, j);
                }
            });
        }
    }

    public void getInstantAppIntentFilterList(android.content.pm.InstantAppRequestInfo instantAppRequestInfo, com.android.server.pm.InstantAppResolverConnection.PhaseTwoCallback phaseTwoCallback, android.os.Handler handler, long j) throws com.android.server.pm.InstantAppResolverConnection.ConnectionException {
        try {
            getRemoteInstanceLazy(instantAppRequestInfo.getToken()).getInstantAppIntentFilterList(instantAppRequestInfo, new com.android.server.pm.InstantAppResolverConnection.AnonymousClass1(handler, phaseTwoCallback, j));
        } catch (android.os.RemoteException e) {
        } catch (java.lang.InterruptedException e2) {
            throw new com.android.server.pm.InstantAppResolverConnection.ConnectionException(3);
        } catch (java.util.concurrent.TimeoutException e3) {
            throw new com.android.server.pm.InstantAppResolverConnection.ConnectionException(1);
        }
    }

    private android.app.IInstantAppResolver getRemoteInstanceLazy(java.lang.String str) throws com.android.server.pm.InstantAppResolverConnection.ConnectionException, java.util.concurrent.TimeoutException, java.lang.InterruptedException {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return bind(str);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void waitForBindLocked(java.lang.String str) throws java.util.concurrent.TimeoutException, java.lang.InterruptedException {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        while (this.mBindState != 0 && this.mRemoteInstance == null) {
            long uptimeMillis2 = BIND_SERVICE_TIMEOUT_MS - (android.os.SystemClock.uptimeMillis() - uptimeMillis);
            if (uptimeMillis2 <= 0) {
                throw new java.util.concurrent.TimeoutException("[" + str + "] Didn't bind to resolver in time!");
            }
            this.mLock.wait(uptimeMillis2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x014a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.app.IInstantAppResolver bind(java.lang.String str) throws com.android.server.pm.InstantAppResolverConnection.ConnectionException, java.util.concurrent.TimeoutException, java.lang.InterruptedException {
        boolean z;
        java.lang.Throwable th;
        boolean z2;
        synchronized (this.mLock) {
            try {
                if (this.mRemoteInstance != null) {
                    return this.mRemoteInstance;
                }
                if (this.mBindState == 2) {
                    if (DEBUG_INSTANT) {
                        android.util.Slog.i(TAG, "[" + str + "] Previous bind timed out; waiting for connection");
                    }
                    try {
                        waitForBindLocked(str);
                        if (this.mRemoteInstance != null) {
                            return this.mRemoteInstance;
                        }
                    } catch (java.util.concurrent.TimeoutException e) {
                        z = true;
                    }
                }
                z = false;
                if (this.mBindState == 1) {
                    if (DEBUG_INSTANT) {
                        android.util.Slog.i(TAG, "[" + str + "] Another thread is binding; waiting for connection");
                    }
                    waitForBindLocked(str);
                    if (this.mRemoteInstance == null) {
                        throw new com.android.server.pm.InstantAppResolverConnection.ConnectionException(1);
                    }
                    return this.mRemoteInstance;
                }
                this.mBindState = 1;
                android.app.IInstantAppResolver iInstantAppResolver = null;
                if (z) {
                    try {
                        if (DEBUG_INSTANT) {
                            android.util.Slog.i(TAG, "[" + str + "] Previous connection never established; rebinding");
                        }
                        try {
                            this.mContext.unbindService(this.mServiceConnection);
                        } catch (java.lang.Exception e2) {
                            android.util.Slog.e(TAG, "[" + str + "] Service already unbound", e2);
                        }
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        z2 = false;
                        synchronized (this.mLock) {
                            try {
                                if (z2 && iInstantAppResolver == null) {
                                    this.mBindState = 2;
                                } else {
                                    this.mBindState = 0;
                                }
                                this.mLock.notifyAll();
                            } finally {
                            }
                        }
                        throw th;
                    }
                }
                if (DEBUG_INSTANT) {
                    android.util.Slog.v(TAG, "[" + str + "] Binding to instant app resolver");
                }
                z2 = this.mContext.bindServiceAsUser(this.mIntent, this.mServiceConnection, android.hardware.audio.common.V2_0.AudioFormat.AAC_MAIN, android.os.UserHandle.SYSTEM);
                try {
                    if (!z2) {
                        android.util.Slog.w(TAG, "[" + str + "] Failed to bind to: " + this.mIntent);
                        throw new com.android.server.pm.InstantAppResolverConnection.ConnectionException(1);
                    }
                    synchronized (this.mLock) {
                        waitForBindLocked(str);
                        iInstantAppResolver = this.mRemoteInstance;
                    }
                    synchronized (this.mLock) {
                        try {
                            if (z2 && iInstantAppResolver == null) {
                                this.mBindState = 2;
                            } else {
                                this.mBindState = 0;
                            }
                            this.mLock.notifyAll();
                        } finally {
                        }
                    }
                    return iInstantAppResolver;
                } catch (java.lang.Throwable th3) {
                    th = th3;
                    synchronized (this.mLock) {
                    }
                }
            } finally {
            }
        }
    }

    private void throwIfCalledOnMainThread() {
        if (java.lang.Thread.currentThread() == this.mContext.getMainLooper().getThread()) {
            throw new java.lang.RuntimeException("Cannot invoke on the main thread");
        }
    }

    void optimisticBind() {
        this.mBgHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.InstantAppResolverConnection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.InstantAppResolverConnection.this.lambda$optimisticBind$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$optimisticBind$0() {
        try {
            if (bind("Optimistic Bind") != null && DEBUG_INSTANT) {
                android.util.Slog.i(TAG, "Optimistic bind succeeded.");
            }
        } catch (com.android.server.pm.InstantAppResolverConnection.ConnectionException | java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
            android.util.Slog.e(TAG, "Optimistic bind failed.", e);
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        if (DEBUG_INSTANT) {
            android.util.Slog.d(TAG, "Binder to instant app resolver died");
        }
        synchronized (this.mLock) {
            handleBinderDiedLocked();
        }
        optimisticBind();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void handleBinderDiedLocked() {
        if (this.mRemoteInstance != null) {
            try {
                this.mRemoteInstance.asBinder().unlinkToDeath(this, 0);
            } catch (java.util.NoSuchElementException e) {
            }
        }
        this.mRemoteInstance = null;
        try {
            this.mContext.unbindService(this.mServiceConnection);
        } catch (java.lang.Exception e2) {
        }
    }

    public static class ConnectionException extends java.lang.Exception {
        public static final int FAILURE_BIND = 1;
        public static final int FAILURE_CALL = 2;
        public static final int FAILURE_INTERRUPTED = 3;
        public final int failure;

        public ConnectionException(int i) {
            this.failure = i;
        }
    }

    private final class MyServiceConnection implements android.content.ServiceConnection {
        private MyServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            if (com.android.server.pm.InstantAppResolverConnection.DEBUG_INSTANT) {
                android.util.Slog.d(com.android.server.pm.InstantAppResolverConnection.TAG, "Connected to instant app resolver");
            }
            synchronized (com.android.server.pm.InstantAppResolverConnection.this.mLock) {
                com.android.server.pm.InstantAppResolverConnection.this.mRemoteInstance = android.app.IInstantAppResolver.Stub.asInterface(iBinder);
                if (com.android.server.pm.InstantAppResolverConnection.this.mBindState == 2) {
                    com.android.server.pm.InstantAppResolverConnection.this.mBindState = 0;
                }
                try {
                    iBinder.linkToDeath(com.android.server.pm.InstantAppResolverConnection.this, 0);
                } catch (android.os.RemoteException e) {
                    com.android.server.pm.InstantAppResolverConnection.this.handleBinderDiedLocked();
                }
                com.android.server.pm.InstantAppResolverConnection.this.mLock.notifyAll();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            if (com.android.server.pm.InstantAppResolverConnection.DEBUG_INSTANT) {
                android.util.Slog.d(com.android.server.pm.InstantAppResolverConnection.TAG, "Disconnected from instant app resolver");
            }
            synchronized (com.android.server.pm.InstantAppResolverConnection.this.mLock) {
                com.android.server.pm.InstantAppResolverConnection.this.handleBinderDiedLocked();
            }
        }
    }

    private static final class GetInstantAppResolveInfoCaller extends android.util.TimedRemoteCaller<java.util.List<android.content.pm.InstantAppResolveInfo>> {
        private final android.os.IRemoteCallback mCallback;

        public GetInstantAppResolveInfoCaller() {
            super(com.android.server.pm.InstantAppResolverConnection.CALL_SERVICE_TIMEOUT_MS);
            this.mCallback = new android.os.IRemoteCallback.Stub() { // from class: com.android.server.pm.InstantAppResolverConnection.GetInstantAppResolveInfoCaller.1
                public void sendResult(android.os.Bundle bundle) throws android.os.RemoteException {
                    com.android.server.pm.InstantAppResolverConnection.GetInstantAppResolveInfoCaller.this.onRemoteMethodResult(bundle.getParcelableArrayList("android.app.extra.RESOLVE_INFO", android.content.pm.InstantAppResolveInfo.class), bundle.getInt("android.app.extra.SEQUENCE", -1));
                }
            };
        }

        public java.util.List<android.content.pm.InstantAppResolveInfo> getInstantAppResolveInfoList(android.app.IInstantAppResolver iInstantAppResolver, android.content.pm.InstantAppRequestInfo instantAppRequestInfo) throws android.os.RemoteException, java.util.concurrent.TimeoutException {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iInstantAppResolver.getInstantAppResolveInfoList(instantAppRequestInfo, onBeforeRemoteCall, this.mCallback);
            return (java.util.List) getResultTimed(onBeforeRemoteCall);
        }
    }
}
