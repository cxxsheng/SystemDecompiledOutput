package com.android.internal.infra;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public abstract class AbstractRemoteService<S extends com.android.internal.infra.AbstractRemoteService<S, I>, I extends android.os.IInterface> implements android.os.IBinder.DeathRecipient {
    protected static final int LAST_PRIVATE_MSG = 2;
    private static final int MSG_BIND = 1;
    private static final int MSG_UNBIND = 2;
    public static final long PERMANENT_BOUND_TIMEOUT_MS = 0;
    private static final int SERVICE_NOT_EXIST = -1;
    private final int mBindingFlags;
    private boolean mBound;
    private boolean mCompleted;
    protected final android.content.ComponentName mComponentName;
    private boolean mConnecting;
    private final android.content.Context mContext;
    private boolean mDestroyed;
    protected final android.os.Handler mHandler;
    private final android.content.Intent mIntent;
    private long mNextUnbind;
    protected I mService;
    private boolean mServiceDied;
    private final int mUserId;
    public final boolean mVerbose;
    private final com.android.internal.infra.AbstractRemoteService.VultureCallback<S> mVultureCallback;
    protected final java.lang.String mTag = getClass().getSimpleName();
    private final android.content.ServiceConnection mServiceConnection = new com.android.internal.infra.AbstractRemoteService.RemoteServiceConnection();
    protected final java.util.ArrayList<com.android.internal.infra.AbstractRemoteService.BasePendingRequest<S, I>> mUnfinishedRequests = new java.util.ArrayList<>();
    private int mServiceExitReason = -1;
    private int mServiceExitSubReason = -1;

    public interface AsyncRequest<I extends android.os.IInterface> {
        void run(I i) throws android.os.RemoteException;
    }

    public interface VultureCallback<T> {
        void onServiceDied(T t);
    }

    protected abstract I getServiceInterface(android.os.IBinder iBinder);

    protected abstract long getTimeoutIdleBindMillis();

    abstract void handleBindFailure();

    protected abstract void handleOnDestroy();

    abstract void handlePendingRequestWhileUnBound(com.android.internal.infra.AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest);

    abstract void handlePendingRequests();

    AbstractRemoteService(android.content.Context context, java.lang.String str, android.content.ComponentName componentName, int i, com.android.internal.infra.AbstractRemoteService.VultureCallback<S> vultureCallback, android.os.Handler handler, int i2, boolean z) {
        this.mContext = context;
        this.mVultureCallback = vultureCallback;
        this.mVerbose = z;
        this.mComponentName = componentName;
        this.mIntent = new android.content.Intent(str).setComponent(this.mComponentName);
        this.mUserId = i;
        this.mHandler = new android.os.Handler(handler.getLooper());
        this.mBindingFlags = i2;
    }

    public final void destroy() {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.internal.infra.AbstractRemoteService$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.internal.infra.AbstractRemoteService) obj).handleDestroy();
            }
        }, this));
    }

    public final boolean isDestroyed() {
        return this.mDestroyed;
    }

    public final android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnConnectedStateChangedInternal(boolean z) {
        handleOnConnectedStateChanged(z);
        if (z) {
            handlePendingRequests();
        }
    }

    protected void handleOnConnectedStateChanged(boolean z) {
    }

    protected long getRemoteRequestMillis() {
        throw new java.lang.UnsupportedOperationException("not implemented by " + getClass());
    }

    public final I getServiceInterface() {
        return this.mService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDestroy() {
        if (checkIfDestroyed()) {
            return;
        }
        handleOnDestroy();
        handleEnsureUnbound();
        this.mDestroyed = true;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.internal.infra.AbstractRemoteService$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.internal.infra.AbstractRemoteService) obj).handleBinderDied();
            }
        }, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBinderDied() {
        if (checkIfDestroyed()) {
            return;
        }
        if (this.mService != null) {
            this.mService.asBinder().unlinkToDeath(this, 0);
        }
        updateServicelicationExitInfo(this.mComponentName, this.mUserId);
        this.mConnecting = true;
        this.mService = null;
        this.mServiceDied = true;
        cancelScheduledUnbind();
        this.mVultureCallback.onServiceDied(this);
        handleBindFailure();
    }

    private void updateServicelicationExitInfo(android.content.ComponentName componentName, int i) {
        android.content.pm.ParceledListSlice<android.app.ApplicationExitInfo> parceledListSlice;
        try {
            parceledListSlice = android.app.ActivityManager.getService().getHistoricalProcessExitReasons(componentName.getPackageName(), 0, 1, i);
        } catch (android.os.RemoteException e) {
            parceledListSlice = null;
        }
        if (parceledListSlice == null) {
            return;
        }
        java.util.List list = parceledListSlice.getList();
        if (list.isEmpty()) {
            return;
        }
        android.app.ApplicationExitInfo applicationExitInfo = (android.app.ApplicationExitInfo) list.get(0);
        this.mServiceExitReason = applicationExitInfo.getReason();
        this.mServiceExitSubReason = applicationExitInfo.getSubReason();
        if (this.mVerbose) {
            android.util.Slog.v(this.mTag, "updateServicelicationExitInfo: exitReason=" + android.app.ApplicationExitInfo.reasonCodeToString(this.mServiceExitReason) + " exitSubReason= " + android.app.ApplicationExitInfo.subreasonToString(this.mServiceExitSubReason));
        }
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.append((java.lang.CharSequence) str).append("service:").println();
        printWriter.append((java.lang.CharSequence) str).append("  ").append("userId=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mUserId)).println();
        printWriter.append((java.lang.CharSequence) str).append("  ").append("componentName=").append((java.lang.CharSequence) this.mComponentName.flattenToString()).println();
        printWriter.append((java.lang.CharSequence) str).append("  ").append("destroyed=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mDestroyed)).println();
        printWriter.append((java.lang.CharSequence) str).append("  ").append("numUnfinishedRequests=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mUnfinishedRequests.size())).println();
        printWriter.append((java.lang.CharSequence) str).append("  ").append("bound=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mBound));
        boolean handleIsBound = handleIsBound();
        printWriter.append((java.lang.CharSequence) str).append("  ").append("connected=").append((java.lang.CharSequence) java.lang.String.valueOf(handleIsBound));
        long timeoutIdleBindMillis = getTimeoutIdleBindMillis();
        if (handleIsBound) {
            if (timeoutIdleBindMillis > 0) {
                printWriter.append(" (unbind in : ");
                android.util.TimeUtils.formatDuration(this.mNextUnbind - android.os.SystemClock.elapsedRealtime(), printWriter);
                printWriter.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            } else {
                printWriter.append(" (permanently bound)");
            }
        }
        printWriter.println();
        if (this.mServiceExitReason != -1) {
            printWriter.append((java.lang.CharSequence) str).append("  ").append("serviceExistReason=").append((java.lang.CharSequence) android.app.ApplicationExitInfo.reasonCodeToString(this.mServiceExitReason));
            printWriter.println();
        }
        if (this.mServiceExitSubReason != -1) {
            printWriter.append((java.lang.CharSequence) str).append("  ").append("serviceExistSubReason=").append((java.lang.CharSequence) android.app.ApplicationExitInfo.subreasonToString(this.mServiceExitSubReason));
            printWriter.println();
        }
        printWriter.append((java.lang.CharSequence) str).append("mBindingFlags=").println(this.mBindingFlags);
        printWriter.append((java.lang.CharSequence) str).append("idleTimeout=").append((java.lang.CharSequence) java.lang.Long.toString(timeoutIdleBindMillis / 1000)).append("s\n");
        printWriter.append((java.lang.CharSequence) str).append("requestTimeout=");
        try {
            printWriter.append((java.lang.CharSequence) java.lang.Long.toString(getRemoteRequestMillis() / 1000)).append("s\n");
        } catch (java.lang.UnsupportedOperationException e) {
            printWriter.append("not supported\n");
        }
        printWriter.println();
    }

    protected void scheduleRequest(com.android.internal.infra.AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.internal.infra.AbstractRemoteService$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.internal.infra.AbstractRemoteService) obj).handlePendingRequest((com.android.internal.infra.AbstractRemoteService.BasePendingRequest) obj2);
            }
        }, this, basePendingRequest));
    }

    void finishRequest(com.android.internal.infra.AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.internal.infra.AbstractRemoteService$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.internal.infra.AbstractRemoteService) obj).handleFinishRequest((com.android.internal.infra.AbstractRemoteService.BasePendingRequest) obj2);
            }
        }, this, basePendingRequest));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFinishRequest(com.android.internal.infra.AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest) {
        synchronized (this.mUnfinishedRequests) {
            this.mUnfinishedRequests.remove(basePendingRequest);
        }
        if (this.mUnfinishedRequests.isEmpty()) {
            scheduleUnbind();
        }
    }

    protected void scheduleAsyncRequest(com.android.internal.infra.AbstractRemoteService.AsyncRequest<I> asyncRequest) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.internal.infra.AbstractRemoteService$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.internal.infra.AbstractRemoteService) obj).handlePendingRequest((com.android.internal.infra.AbstractRemoteService.MyAsyncPendingRequest) obj2);
            }
        }, this, new com.android.internal.infra.AbstractRemoteService.MyAsyncPendingRequest(this, asyncRequest)));
    }

    protected void executeAsyncRequest(com.android.internal.infra.AbstractRemoteService.AsyncRequest<I> asyncRequest) {
        handlePendingRequest(new com.android.internal.infra.AbstractRemoteService.MyAsyncPendingRequest(this, asyncRequest));
    }

    private void cancelScheduledUnbind() {
        this.mHandler.removeMessages(2);
    }

    protected void scheduleBind() {
        if (this.mHandler.hasMessages(1)) {
            if (this.mVerbose) {
                android.util.Slog.v(this.mTag, "scheduleBind(): already scheduled");
                return;
            }
            return;
        }
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.internal.infra.AbstractRemoteService$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.internal.infra.AbstractRemoteService) obj).handleEnsureBound();
            }
        }, this).setWhat(1));
    }

    protected void scheduleUnbind() {
        scheduleUnbind(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleUnbind(boolean z) {
        long timeoutIdleBindMillis = getTimeoutIdleBindMillis();
        if (timeoutIdleBindMillis <= 0) {
            if (this.mVerbose) {
                android.util.Slog.v(this.mTag, "not scheduling unbind when value is " + timeoutIdleBindMillis);
                return;
            }
            return;
        }
        if (!z) {
            timeoutIdleBindMillis = 0;
        }
        cancelScheduledUnbind();
        this.mNextUnbind = android.os.SystemClock.elapsedRealtime() + timeoutIdleBindMillis;
        if (this.mVerbose) {
            android.util.Slog.v(this.mTag, "unbinding in " + timeoutIdleBindMillis + "ms: " + this.mNextUnbind);
        }
        this.mHandler.sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.internal.infra.AbstractRemoteService$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.internal.infra.AbstractRemoteService) obj).handleUnbind();
            }
        }, this).setWhat(2), timeoutIdleBindMillis);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUnbind() {
        if (checkIfDestroyed()) {
            return;
        }
        handleEnsureUnbound();
    }

    protected final void handlePendingRequest(com.android.internal.infra.AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest) {
        if (checkIfDestroyed() || this.mCompleted) {
            return;
        }
        if (!handleIsBound()) {
            if (this.mVerbose) {
                android.util.Slog.v(this.mTag, "handlePendingRequest(): queuing " + basePendingRequest);
            }
            handlePendingRequestWhileUnBound(basePendingRequest);
            handleEnsureBound();
            return;
        }
        if (this.mVerbose) {
            android.util.Slog.v(this.mTag, "handlePendingRequest(): " + basePendingRequest);
        }
        synchronized (this.mUnfinishedRequests) {
            this.mUnfinishedRequests.add(basePendingRequest);
        }
        cancelScheduledUnbind();
        basePendingRequest.run();
        if (basePendingRequest.isFinal()) {
            this.mCompleted = true;
        }
    }

    private boolean handleIsBound() {
        return this.mService != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleEnsureBound() {
        if (handleIsBound() || this.mConnecting) {
            return;
        }
        if (this.mVerbose) {
            android.util.Slog.v(this.mTag, "ensureBound()");
        }
        this.mConnecting = true;
        int i = 67112961 | this.mBindingFlags;
        boolean bindServiceAsUser = this.mContext.bindServiceAsUser(this.mIntent, this.mServiceConnection, i, this.mHandler, new android.os.UserHandle(this.mUserId));
        this.mBound = true;
        if (!bindServiceAsUser) {
            android.util.Slog.w(this.mTag, "could not bind to " + this.mIntent + " using flags " + i);
            this.mConnecting = false;
            if (!this.mServiceDied) {
                handleBinderDied();
            }
        }
    }

    private void handleEnsureUnbound() {
        if (handleIsBound() || this.mConnecting) {
            if (this.mVerbose) {
                android.util.Slog.v(this.mTag, "ensureUnbound()");
            }
            this.mConnecting = false;
            if (handleIsBound()) {
                handleOnConnectedStateChangedInternal(false);
                if (this.mService != null) {
                    this.mService.asBinder().unlinkToDeath(this, 0);
                    this.mService = null;
                }
            }
            this.mNextUnbind = 0L;
            if (this.mBound) {
                this.mContext.unbindService(this.mServiceConnection);
                this.mBound = false;
            }
        }
    }

    private class RemoteServiceConnection implements android.content.ServiceConnection {
        private RemoteServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            if (com.android.internal.infra.AbstractRemoteService.this.mVerbose) {
                android.util.Slog.v(com.android.internal.infra.AbstractRemoteService.this.mTag, "onServiceConnected()");
            }
            if (com.android.internal.infra.AbstractRemoteService.this.mDestroyed || !com.android.internal.infra.AbstractRemoteService.this.mConnecting) {
                android.util.Slog.wtf(com.android.internal.infra.AbstractRemoteService.this.mTag, "onServiceConnected() was dispatched after unbindService.");
                return;
            }
            com.android.internal.infra.AbstractRemoteService.this.mConnecting = false;
            try {
                iBinder.linkToDeath(com.android.internal.infra.AbstractRemoteService.this, 0);
                com.android.internal.infra.AbstractRemoteService.this.mService = (I) com.android.internal.infra.AbstractRemoteService.this.getServiceInterface(iBinder);
                com.android.internal.infra.AbstractRemoteService.this.mServiceExitReason = -1;
                com.android.internal.infra.AbstractRemoteService.this.mServiceExitSubReason = -1;
                com.android.internal.infra.AbstractRemoteService.this.handleOnConnectedStateChangedInternal(true);
                com.android.internal.infra.AbstractRemoteService.this.mServiceDied = false;
            } catch (android.os.RemoteException e) {
                com.android.internal.infra.AbstractRemoteService.this.handleBinderDied();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            if (com.android.internal.infra.AbstractRemoteService.this.mVerbose) {
                android.util.Slog.v(com.android.internal.infra.AbstractRemoteService.this.mTag, "onServiceDisconnected()");
            }
            com.android.internal.infra.AbstractRemoteService.this.mConnecting = true;
            com.android.internal.infra.AbstractRemoteService.this.mService = null;
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(android.content.ComponentName componentName) {
            if (com.android.internal.infra.AbstractRemoteService.this.mVerbose) {
                android.util.Slog.v(com.android.internal.infra.AbstractRemoteService.this.mTag, "onBindingDied()");
            }
            com.android.internal.infra.AbstractRemoteService.this.scheduleUnbind(false);
        }
    }

    private boolean checkIfDestroyed() {
        if (this.mDestroyed && this.mVerbose) {
            android.util.Slog.v(this.mTag, "Not handling operation as service for " + this.mComponentName + " is already destroyed");
        }
        return this.mDestroyed;
    }

    public java.lang.String toString() {
        return getClass().getSimpleName() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + this.mComponentName + " " + java.lang.System.identityHashCode(this) + (this.mService != null ? " (bound)" : " (unbound)") + (this.mDestroyed ? " (destroyed)" : "") + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public static abstract class BasePendingRequest<S extends com.android.internal.infra.AbstractRemoteService<S, I>, I extends android.os.IInterface> implements java.lang.Runnable {
        boolean mCancelled;
        boolean mCompleted;
        final java.lang.ref.WeakReference<S> mWeakService;
        protected final java.lang.String mTag = getClass().getSimpleName();
        protected final java.lang.Object mLock = new java.lang.Object();

        BasePendingRequest(S s) {
            this.mWeakService = new java.lang.ref.WeakReference<>(s);
        }

        protected final S getService() {
            return this.mWeakService.get();
        }

        protected final boolean finish() {
            synchronized (this.mLock) {
                if (!this.mCompleted && !this.mCancelled) {
                    this.mCompleted = true;
                    S s = this.mWeakService.get();
                    if (s != null) {
                        s.finishRequest(this);
                    }
                    onFinished();
                    return true;
                }
                return false;
            }
        }

        void onFinished() {
        }

        protected void onFailed() {
        }

        protected final boolean isCancelledLocked() {
            return this.mCancelled;
        }

        public boolean cancel() {
            synchronized (this.mLock) {
                if (!this.mCancelled && !this.mCompleted) {
                    this.mCancelled = true;
                    S s = this.mWeakService.get();
                    if (s != null) {
                        s.finishRequest(this);
                    }
                    onCancel();
                    return true;
                }
                return false;
            }
        }

        void onCancel() {
        }

        protected boolean isFinal() {
            return false;
        }

        protected boolean isRequestCompleted() {
            boolean z;
            synchronized (this.mLock) {
                z = this.mCompleted;
            }
            return z;
        }
    }

    public static abstract class PendingRequest<S extends com.android.internal.infra.AbstractRemoteService<S, I>, I extends android.os.IInterface> extends com.android.internal.infra.AbstractRemoteService.BasePendingRequest<S, I> {
        private final android.os.Handler mServiceHandler;
        private final java.lang.Runnable mTimeoutTrigger;

        protected abstract void onTimeout(S s);

        protected PendingRequest(final S s) {
            super(s);
            this.mServiceHandler = s.mHandler;
            this.mTimeoutTrigger = new java.lang.Runnable() { // from class: com.android.internal.infra.AbstractRemoteService$PendingRequest$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.infra.AbstractRemoteService.PendingRequest.this.lambda$new$0(s);
                }
            };
            this.mServiceHandler.postAtTime(this.mTimeoutTrigger, android.os.SystemClock.uptimeMillis() + s.getRemoteRequestMillis());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(com.android.internal.infra.AbstractRemoteService abstractRemoteService) {
            synchronized (this.mLock) {
                if (this.mCancelled) {
                    return;
                }
                this.mCompleted = true;
                S s = this.mWeakService.get();
                if (s != null) {
                    android.util.Slog.w(this.mTag, "timed out after " + abstractRemoteService.getRemoteRequestMillis() + " ms");
                    s.finishRequest(this);
                    onTimeout(s);
                    return;
                }
                android.util.Slog.w(this.mTag, "timed out (no service)");
            }
        }

        @Override // com.android.internal.infra.AbstractRemoteService.BasePendingRequest
        final void onFinished() {
            this.mServiceHandler.removeCallbacks(this.mTimeoutTrigger);
        }

        @Override // com.android.internal.infra.AbstractRemoteService.BasePendingRequest
        final void onCancel() {
            this.mServiceHandler.removeCallbacks(this.mTimeoutTrigger);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class MyAsyncPendingRequest<S extends com.android.internal.infra.AbstractRemoteService<S, I>, I extends android.os.IInterface> extends com.android.internal.infra.AbstractRemoteService.BasePendingRequest<S, I> {
        private static final java.lang.String TAG = com.android.internal.infra.AbstractRemoteService.MyAsyncPendingRequest.class.getSimpleName();
        private final com.android.internal.infra.AbstractRemoteService.AsyncRequest<I> mRequest;

        protected MyAsyncPendingRequest(S s, com.android.internal.infra.AbstractRemoteService.AsyncRequest<I> asyncRequest) {
            super(s);
            this.mRequest = asyncRequest;
        }

        @Override // java.lang.Runnable
        public void run() {
            S service = getService();
            try {
                if (service == null) {
                    return;
                }
                try {
                    this.mRequest.run(service.mService);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "exception handling async request (" + this + "): " + e);
                }
            } finally {
                finish();
            }
        }
    }
}
