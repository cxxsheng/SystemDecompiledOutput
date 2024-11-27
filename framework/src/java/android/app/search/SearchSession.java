package android.app.search;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SearchSession implements java.lang.AutoCloseable {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = android.app.search.SearchSession.class.getSimpleName();
    private final android.app.search.SearchSessionId mSessionId;
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private final java.util.concurrent.atomic.AtomicBoolean mIsClosed = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final android.os.IBinder mToken = new android.os.Binder();
    private final android.util.ArrayMap<android.app.search.SearchSession.Callback, android.app.search.SearchSession.CallbackWrapper> mRegisteredCallbacks = new android.util.ArrayMap<>();
    private final android.app.search.ISearchUiManager mInterface = android.app.search.ISearchUiManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.SEARCH_UI_SERVICE));

    public interface Callback {
        void onTargetsAvailable(java.util.List<android.app.search.SearchTarget> list);
    }

    SearchSession(android.content.Context context, android.app.search.SearchContext searchContext) {
        this.mSessionId = new android.app.search.SearchSessionId(context.getPackageName() + ":" + java.util.UUID.randomUUID().toString(), context.getUserId());
        searchContext.setPackageName(context.getPackageName());
        try {
            this.mInterface.createSearchSession(searchContext, this.mSessionId, this.mToken);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to search session", e);
            e.rethrowFromSystemServer();
        }
        this.mCloseGuard.open("SearchSession.close");
    }

    public void notifyEvent(android.app.search.Query query, android.app.search.SearchTargetEvent searchTargetEvent) {
        if (this.mIsClosed.get()) {
            throw new java.lang.IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mInterface.notifyEvent(this.mSessionId, query, searchTargetEvent);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to notify event", e);
            e.rethrowFromSystemServer();
        }
    }

    public void query(android.app.search.Query query, java.util.concurrent.Executor executor, java.util.function.Consumer<java.util.List<android.app.search.SearchTarget>> consumer) {
        if (this.mIsClosed.get()) {
            throw new java.lang.IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mInterface.query(this.mSessionId, query, new android.app.search.SearchSession.CallbackWrapper(executor, consumer));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to sort targets", e);
            e.rethrowFromSystemServer();
        }
    }

    public void registerEmptyQueryResultUpdateCallback(java.util.concurrent.Executor executor, final android.app.search.SearchSession.Callback callback) {
        synchronized (this.mRegisteredCallbacks) {
            if (this.mIsClosed.get()) {
                throw new java.lang.IllegalStateException("This client has already been destroyed.");
            }
            if (this.mRegisteredCallbacks.containsKey(callback)) {
                return;
            }
            try {
                java.util.Objects.requireNonNull(callback);
                android.app.search.SearchSession.CallbackWrapper callbackWrapper = new android.app.search.SearchSession.CallbackWrapper(executor, new java.util.function.Consumer() { // from class: android.app.search.SearchSession$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        android.app.search.SearchSession.Callback.this.onTargetsAvailable((java.util.List) obj);
                    }
                });
                this.mInterface.registerEmptyQueryResultUpdateCallback(this.mSessionId, callbackWrapper);
                this.mRegisteredCallbacks.put(callback, callbackWrapper);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to register for empty query result updates", e);
                e.rethrowAsRuntimeException();
            }
        }
    }

    public void unregisterEmptyQueryResultUpdateCallback(android.app.search.SearchSession.Callback callback) {
        synchronized (this.mRegisteredCallbacks) {
            if (this.mIsClosed.get()) {
                throw new java.lang.IllegalStateException("This client has already been destroyed.");
            }
            if (this.mRegisteredCallbacks.containsKey(callback)) {
                try {
                    this.mInterface.unregisterEmptyQueryResultUpdateCallback(this.mSessionId, this.mRegisteredCallbacks.remove(callback));
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "Failed to unregister for empty query result updates", e);
                    e.rethrowAsRuntimeException();
                }
            }
        }
    }

    @java.lang.Deprecated
    public void destroy() {
        if (!this.mIsClosed.getAndSet(true)) {
            this.mCloseGuard.close();
            try {
                this.mInterface.destroySearchSession(this.mSessionId);
                return;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to notify search target event", e);
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
            finalize();
        } catch (java.lang.Throwable th) {
            th.printStackTrace();
        }
    }

    static class CallbackWrapper extends android.app.search.ISearchCallback.Stub {
        private final java.util.function.Consumer<java.util.List<android.app.search.SearchTarget>> mCallback;
        private final java.util.concurrent.Executor mExecutor;

        CallbackWrapper(java.util.concurrent.Executor executor, java.util.function.Consumer<java.util.List<android.app.search.SearchTarget>> consumer) {
            this.mCallback = consumer;
            this.mExecutor = executor;
        }

        @Override // android.app.search.ISearchCallback
        public void onResult(android.content.pm.ParceledListSlice parceledListSlice) {
            android.os.Bundle extras;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                final java.util.List list = parceledListSlice.getList();
                if (list.size() > 0 && (extras = ((android.app.search.SearchTarget) list.get(0)).getExtras()) != null) {
                    extras.putLong("key_ipc_start", android.os.SystemClock.elapsedRealtime());
                }
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.search.SearchSession$CallbackWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.search.SearchSession.CallbackWrapper.this.lambda$onResult$0(list);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0(java.util.List list) {
            this.mCallback.accept(list);
        }
    }
}
