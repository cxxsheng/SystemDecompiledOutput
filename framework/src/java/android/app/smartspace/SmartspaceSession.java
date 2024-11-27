package android.app.smartspace;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SmartspaceSession implements java.lang.AutoCloseable {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = android.app.smartspace.SmartspaceSession.class.getSimpleName();
    private final android.app.smartspace.SmartspaceSessionId mSessionId;
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private final java.util.concurrent.atomic.AtomicBoolean mIsClosed = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final android.util.ArrayMap<android.app.smartspace.SmartspaceSession.OnTargetsAvailableListener, android.app.smartspace.SmartspaceSession.CallbackWrapper> mRegisteredCallbacks = new android.util.ArrayMap<>();
    private final android.app.smartspace.ISmartspaceManager mInterface = android.app.smartspace.ISmartspaceManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.SMARTSPACE_SERVICE));

    public interface OnTargetsAvailableListener {
        void onTargetsAvailable(java.util.List<android.app.smartspace.SmartspaceTarget> list);
    }

    SmartspaceSession(android.content.Context context, android.app.smartspace.SmartspaceConfig smartspaceConfig) {
        this.mSessionId = new android.app.smartspace.SmartspaceSessionId(context.getPackageName() + ":" + java.util.UUID.randomUUID().toString(), context.getUser());
        try {
            this.mInterface.createSmartspaceSession(smartspaceConfig, this.mSessionId, getToken());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to create Smartspace session", e);
            e.rethrowFromSystemServer();
        }
        this.mCloseGuard.open("SmartspaceSession.close");
    }

    public void notifySmartspaceEvent(android.app.smartspace.SmartspaceTargetEvent smartspaceTargetEvent) {
        if (this.mIsClosed.get()) {
            throw new java.lang.IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mInterface.notifySmartspaceEvent(this.mSessionId, smartspaceTargetEvent);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to notify event", e);
            e.rethrowFromSystemServer();
        }
    }

    public void requestSmartspaceUpdate() {
        if (this.mIsClosed.get()) {
            throw new java.lang.IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mInterface.requestSmartspaceUpdate(this.mSessionId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to request update.", e);
            e.rethrowFromSystemServer();
        }
    }

    public void addOnTargetsAvailableListener(java.util.concurrent.Executor executor, final android.app.smartspace.SmartspaceSession.OnTargetsAvailableListener onTargetsAvailableListener) {
        if (this.mIsClosed.get()) {
            throw new java.lang.IllegalStateException("This client has already been destroyed.");
        }
        if (this.mRegisteredCallbacks.containsKey(onTargetsAvailableListener)) {
            return;
        }
        try {
            java.util.Objects.requireNonNull(onTargetsAvailableListener);
            android.app.smartspace.SmartspaceSession.CallbackWrapper callbackWrapper = new android.app.smartspace.SmartspaceSession.CallbackWrapper(executor, new java.util.function.Consumer() { // from class: android.app.smartspace.SmartspaceSession$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.app.smartspace.SmartspaceSession.OnTargetsAvailableListener.this.onTargetsAvailable((java.util.List) obj);
                }
            });
            this.mRegisteredCallbacks.put(onTargetsAvailableListener, callbackWrapper);
            this.mInterface.registerSmartspaceUpdates(this.mSessionId, callbackWrapper);
            this.mInterface.requestSmartspaceUpdate(this.mSessionId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to register for smartspace updates", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void removeOnTargetsAvailableListener(android.app.smartspace.SmartspaceSession.OnTargetsAvailableListener onTargetsAvailableListener) {
        if (this.mIsClosed.get()) {
            throw new java.lang.IllegalStateException("This client has already been destroyed.");
        }
        if (!this.mRegisteredCallbacks.containsKey(onTargetsAvailableListener)) {
            return;
        }
        try {
            this.mInterface.unregisterSmartspaceUpdates(this.mSessionId, this.mRegisteredCallbacks.remove(onTargetsAvailableListener));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to unregister for smartspace updates", e);
            e.rethrowAsRuntimeException();
        }
    }

    private void destroy() {
        if (!this.mIsClosed.getAndSet(true)) {
            this.mCloseGuard.close();
            try {
                this.mInterface.destroySmartspaceSession(this.mSessionId);
                return;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to notify Smartspace target event", e);
                e.rethrowFromSystemServer();
                return;
            }
        }
        throw new java.lang.IllegalStateException("This client has already been destroyed.");
    }

    protected void finalize() {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            if (!this.mIsClosed.get()) {
                destroy();
            }
            try {
                super.finalize();
            } catch (java.lang.Throwable th) {
                th.printStackTrace();
            }
        } catch (java.lang.Throwable th2) {
            try {
                super.finalize();
            } catch (java.lang.Throwable th3) {
                th3.printStackTrace();
            }
            throw th2;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            destroy();
            finalize();
        } catch (java.lang.Throwable th) {
            th.printStackTrace();
        }
    }

    static class CallbackWrapper extends android.app.smartspace.ISmartspaceCallback.Stub {
        private final java.util.function.Consumer<java.util.List<android.app.smartspace.SmartspaceTarget>> mCallback;
        private final java.util.concurrent.Executor mExecutor;

        CallbackWrapper(java.util.concurrent.Executor executor, java.util.function.Consumer<java.util.List<android.app.smartspace.SmartspaceTarget>> consumer) {
            this.mCallback = consumer;
            this.mExecutor = executor;
        }

        @Override // android.app.smartspace.ISmartspaceCallback
        public void onResult(final android.content.pm.ParceledListSlice parceledListSlice) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.smartspace.SmartspaceSession$CallbackWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.smartspace.SmartspaceSession.CallbackWrapper.this.lambda$onResult$0(parceledListSlice);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0(android.content.pm.ParceledListSlice parceledListSlice) {
            this.mCallback.accept(parceledListSlice.getList());
        }
    }

    private static class Token {
        static final android.os.IBinder sBinder = new android.os.Binder(android.app.smartspace.SmartspaceSession.TAG);

        private Token() {
        }
    }

    private static android.os.IBinder getToken() {
        return android.app.smartspace.SmartspaceSession.Token.sBinder;
    }
}
