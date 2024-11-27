package android.app.prediction;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class AppPredictor {
    private static final java.lang.String TAG = android.app.prediction.AppPredictor.class.getSimpleName();
    private final android.app.prediction.AppPredictionSessionId mSessionId;
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private final java.util.concurrent.atomic.AtomicBoolean mIsClosed = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final android.util.ArrayMap<android.app.prediction.AppPredictor.Callback, android.app.prediction.AppPredictor.CallbackWrapper> mRegisteredCallbacks = new android.util.ArrayMap<>();
    private final android.app.prediction.IPredictionManager mPredictionManager = android.app.prediction.IPredictionManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.APP_PREDICTION_SERVICE));

    public interface Callback {
        void onTargetsAvailable(java.util.List<android.app.prediction.AppTarget> list);
    }

    AppPredictor(android.content.Context context, android.app.prediction.AppPredictionContext appPredictionContext) {
        this.mSessionId = new android.app.prediction.AppPredictionSessionId(context.getPackageName() + ":" + java.util.UUID.randomUUID(), context.getUserId());
        try {
            this.mPredictionManager.createPredictionSession(appPredictionContext, this.mSessionId, getToken());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to create predictor", e);
            e.rethrowAsRuntimeException();
        }
        this.mCloseGuard.open("AppPredictor.close");
    }

    public void notifyAppTargetEvent(android.app.prediction.AppTargetEvent appTargetEvent) {
        if (this.mIsClosed.get()) {
            throw new java.lang.IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mPredictionManager.notifyAppTargetEvent(this.mSessionId, appTargetEvent);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to notify app target event", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void notifyLaunchLocationShown(java.lang.String str, java.util.List<android.app.prediction.AppTargetId> list) {
        if (this.mIsClosed.get()) {
            throw new java.lang.IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mPredictionManager.notifyLaunchLocationShown(this.mSessionId, str, new android.content.pm.ParceledListSlice(list));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to notify location shown event", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void registerPredictionUpdates(java.util.concurrent.Executor executor, android.app.prediction.AppPredictor.Callback callback) {
        synchronized (this.mRegisteredCallbacks) {
            registerPredictionUpdatesLocked(executor, callback);
        }
    }

    private void registerPredictionUpdatesLocked(java.util.concurrent.Executor executor, final android.app.prediction.AppPredictor.Callback callback) {
        if (this.mIsClosed.get()) {
            throw new java.lang.IllegalStateException("This client has already been destroyed.");
        }
        if (this.mRegisteredCallbacks.containsKey(callback)) {
            return;
        }
        try {
            java.util.Objects.requireNonNull(callback);
            android.app.prediction.AppPredictor.CallbackWrapper callbackWrapper = new android.app.prediction.AppPredictor.CallbackWrapper(executor, new java.util.function.Consumer() { // from class: android.app.prediction.AppPredictor$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.app.prediction.AppPredictor.Callback.this.onTargetsAvailable((java.util.List) obj);
                }
            });
            this.mPredictionManager.registerPredictionUpdates(this.mSessionId, callbackWrapper);
            this.mRegisteredCallbacks.put(callback, callbackWrapper);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to register for prediction updates", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void unregisterPredictionUpdates(android.app.prediction.AppPredictor.Callback callback) {
        synchronized (this.mRegisteredCallbacks) {
            unregisterPredictionUpdatesLocked(callback);
        }
    }

    private void unregisterPredictionUpdatesLocked(android.app.prediction.AppPredictor.Callback callback) {
        if (this.mIsClosed.get()) {
            throw new java.lang.IllegalStateException("This client has already been destroyed.");
        }
        if (!this.mRegisteredCallbacks.containsKey(callback)) {
            return;
        }
        try {
            this.mPredictionManager.unregisterPredictionUpdates(this.mSessionId, this.mRegisteredCallbacks.remove(callback));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to unregister for prediction updates", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void requestPredictionUpdate() {
        if (this.mIsClosed.get()) {
            throw new java.lang.IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mPredictionManager.requestPredictionUpdate(this.mSessionId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to request prediction update", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void sortTargets(java.util.List<android.app.prediction.AppTarget> list, java.util.concurrent.Executor executor, java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> consumer) {
        if (this.mIsClosed.get()) {
            throw new java.lang.IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mPredictionManager.sortAppTargets(this.mSessionId, new android.content.pm.ParceledListSlice(list), new android.app.prediction.AppPredictor.CallbackWrapper(executor, consumer));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to sort targets", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void requestServiceFeatures(java.util.concurrent.Executor executor, java.util.function.Consumer<android.os.Bundle> consumer) {
        if (this.mIsClosed.get()) {
            throw new java.lang.IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mPredictionManager.requestServiceFeatures(this.mSessionId, new android.app.prediction.AppPredictor.RemoteCallbackWrapper(executor, consumer));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to request service feature info", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void destroy() {
        if (!this.mIsClosed.getAndSet(true)) {
            this.mCloseGuard.close();
            synchronized (this.mRegisteredCallbacks) {
                destroySessionLocked();
            }
            return;
        }
        throw new java.lang.IllegalStateException("This client has already been destroyed.");
    }

    private void destroySessionLocked() {
        try {
            this.mPredictionManager.onDestroyPredictionSession(this.mSessionId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to notify app target event", e);
            e.rethrowAsRuntimeException();
        }
        this.mRegisteredCallbacks.clear();
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            if (!this.mIsClosed.get()) {
                destroy();
            }
        } finally {
            super.finalize();
        }
    }

    public android.app.prediction.AppPredictionSessionId getSessionId() {
        return this.mSessionId;
    }

    static class CallbackWrapper extends android.app.prediction.IPredictionCallback.Stub {
        private final java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> mCallback;
        private final java.util.concurrent.Executor mExecutor;

        CallbackWrapper(java.util.concurrent.Executor executor, java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> consumer) {
            this.mCallback = consumer;
            this.mExecutor = executor;
        }

        @Override // android.app.prediction.IPredictionCallback
        public void onResult(final android.content.pm.ParceledListSlice parceledListSlice) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.prediction.AppPredictor$CallbackWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.prediction.AppPredictor.CallbackWrapper.this.lambda$onResult$0(parceledListSlice);
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

    static class RemoteCallbackWrapper extends android.os.IRemoteCallback.Stub {
        private final java.util.function.Consumer<android.os.Bundle> mCallback;
        private final java.util.concurrent.Executor mExecutor;

        RemoteCallbackWrapper(java.util.concurrent.Executor executor, java.util.function.Consumer<android.os.Bundle> consumer) {
            this.mExecutor = executor;
            this.mCallback = consumer;
        }

        @Override // android.os.IRemoteCallback
        public void sendResult(final android.os.Bundle bundle) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.prediction.AppPredictor$RemoteCallbackWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.prediction.AppPredictor.RemoteCallbackWrapper.this.lambda$sendResult$0(bundle);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendResult$0(android.os.Bundle bundle) {
            this.mCallback.accept(bundle);
        }
    }

    private static class Token {
        static final android.os.IBinder sBinder = new android.os.Binder(android.app.prediction.AppPredictor.TAG);

        private Token() {
        }
    }

    private static android.os.IBinder getToken() {
        return android.app.prediction.AppPredictor.Token.sBinder;
    }
}
