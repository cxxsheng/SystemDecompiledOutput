package android.service.appprediction;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class AppPredictionService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.service.appprediction.AppPredictionService";
    private static final java.lang.String TAG = "AppPredictionService";
    private android.os.Handler mHandler;
    private final android.util.ArrayMap<android.app.prediction.AppPredictionSessionId, java.util.ArrayList<android.service.appprediction.AppPredictionService.CallbackWrapper>> mSessionCallbacks = new android.util.ArrayMap<>();
    private final android.service.appprediction.IPredictionService mInterface = new android.service.appprediction.AppPredictionService.AnonymousClass1();

    public abstract void onAppTargetEvent(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.AppTargetEvent appTargetEvent);

    public abstract void onLaunchLocationShown(android.app.prediction.AppPredictionSessionId appPredictionSessionId, java.lang.String str, java.util.List<android.app.prediction.AppTargetId> list);

    public abstract void onRequestPredictionUpdate(android.app.prediction.AppPredictionSessionId appPredictionSessionId);

    public abstract void onSortAppTargets(android.app.prediction.AppPredictionSessionId appPredictionSessionId, java.util.List<android.app.prediction.AppTarget> list, android.os.CancellationSignal cancellationSignal, java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> consumer);

    /* renamed from: android.service.appprediction.AppPredictionService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.appprediction.IPredictionService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.appprediction.IPredictionService
        public void onCreatePredictionSession(android.app.prediction.AppPredictionContext appPredictionContext, android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
            android.service.appprediction.AppPredictionService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.appprediction.AppPredictionService$1$$ExternalSyntheticLambda8
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.appprediction.AppPredictionService) obj).doCreatePredictionSession((android.app.prediction.AppPredictionContext) obj2, (android.app.prediction.AppPredictionSessionId) obj3);
                }
            }, android.service.appprediction.AppPredictionService.this, appPredictionContext, appPredictionSessionId));
        }

        @Override // android.service.appprediction.IPredictionService
        public void notifyAppTargetEvent(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.AppTargetEvent appTargetEvent) {
            android.service.appprediction.AppPredictionService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.appprediction.AppPredictionService$1$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.appprediction.AppPredictionService) obj).onAppTargetEvent((android.app.prediction.AppPredictionSessionId) obj2, (android.app.prediction.AppTargetEvent) obj3);
                }
            }, android.service.appprediction.AppPredictionService.this, appPredictionSessionId, appTargetEvent));
        }

        @Override // android.service.appprediction.IPredictionService
        public void notifyLaunchLocationShown(android.app.prediction.AppPredictionSessionId appPredictionSessionId, java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) {
            android.service.appprediction.AppPredictionService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.service.appprediction.AppPredictionService$1$$ExternalSyntheticLambda5
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.service.appprediction.AppPredictionService) obj).onLaunchLocationShown((android.app.prediction.AppPredictionSessionId) obj2, (java.lang.String) obj3, (java.util.List) obj4);
                }
            }, android.service.appprediction.AppPredictionService.this, appPredictionSessionId, str, parceledListSlice.getList()));
        }

        @Override // android.service.appprediction.IPredictionService
        public void sortAppTargets(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.content.pm.ParceledListSlice parceledListSlice, android.app.prediction.IPredictionCallback iPredictionCallback) {
            android.service.appprediction.AppPredictionService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: android.service.appprediction.AppPredictionService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                    ((android.service.appprediction.AppPredictionService) obj).onSortAppTargets((android.app.prediction.AppPredictionSessionId) obj2, (java.util.List) obj3, (android.os.CancellationSignal) obj4, (android.service.appprediction.AppPredictionService.CallbackWrapper) obj5);
                }
            }, android.service.appprediction.AppPredictionService.this, appPredictionSessionId, parceledListSlice.getList(), null, new android.service.appprediction.AppPredictionService.CallbackWrapper(iPredictionCallback, null)));
        }

        @Override // android.service.appprediction.IPredictionService
        public void registerPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) {
            android.service.appprediction.AppPredictionService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.appprediction.AppPredictionService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.appprediction.AppPredictionService) obj).doRegisterPredictionUpdates((android.app.prediction.AppPredictionSessionId) obj2, (android.app.prediction.IPredictionCallback) obj3);
                }
            }, android.service.appprediction.AppPredictionService.this, appPredictionSessionId, iPredictionCallback));
        }

        @Override // android.service.appprediction.IPredictionService
        public void unregisterPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) {
            android.service.appprediction.AppPredictionService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.appprediction.AppPredictionService$1$$ExternalSyntheticLambda7
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.appprediction.AppPredictionService) obj).doUnregisterPredictionUpdates((android.app.prediction.AppPredictionSessionId) obj2, (android.app.prediction.IPredictionCallback) obj3);
                }
            }, android.service.appprediction.AppPredictionService.this, appPredictionSessionId, iPredictionCallback));
        }

        @Override // android.service.appprediction.IPredictionService
        public void requestPredictionUpdate(android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
            android.service.appprediction.AppPredictionService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.appprediction.AppPredictionService$1$$ExternalSyntheticLambda6
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.appprediction.AppPredictionService) obj).doRequestPredictionUpdate((android.app.prediction.AppPredictionSessionId) obj2);
                }
            }, android.service.appprediction.AppPredictionService.this, appPredictionSessionId));
        }

        @Override // android.service.appprediction.IPredictionService
        public void onDestroyPredictionSession(android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
            android.service.appprediction.AppPredictionService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.appprediction.AppPredictionService$1$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.appprediction.AppPredictionService) obj).doDestroyPredictionSession((android.app.prediction.AppPredictionSessionId) obj2);
                }
            }, android.service.appprediction.AppPredictionService.this, appPredictionSessionId));
        }

        @Override // android.service.appprediction.IPredictionService
        public void requestServiceFeatures(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.os.IRemoteCallback iRemoteCallback) {
            android.service.appprediction.AppPredictionService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.appprediction.AppPredictionService$1$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.appprediction.AppPredictionService) obj).onRequestServiceFeatures((android.app.prediction.AppPredictionSessionId) obj2, (android.service.appprediction.AppPredictionService.RemoteCallbackWrapper) obj3);
                }
            }, android.service.appprediction.AppPredictionService.this, appPredictionSessionId, new android.service.appprediction.AppPredictionService.RemoteCallbackWrapper(iRemoteCallback, null)));
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        android.util.Log.w(TAG, "Tried to bind to wrong intent (should be android.service.appprediction.AppPredictionService: " + intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCreatePredictionSession(android.app.prediction.AppPredictionContext appPredictionContext, android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
        this.mSessionCallbacks.put(appPredictionSessionId, new java.util.ArrayList<>());
        onCreatePredictionSession(appPredictionContext, appPredictionSessionId);
    }

    public void onCreatePredictionSession(android.app.prediction.AppPredictionContext appPredictionContext, android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRegisterPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) {
        final java.util.ArrayList<android.service.appprediction.AppPredictionService.CallbackWrapper> arrayList = this.mSessionCallbacks.get(appPredictionSessionId);
        if (arrayList == null) {
            android.util.Slog.e(TAG, "Failed to register for updates for unknown session: " + appPredictionSessionId);
        } else if (findCallbackWrapper(arrayList, iPredictionCallback) == null) {
            arrayList.add(new android.service.appprediction.AppPredictionService.CallbackWrapper(iPredictionCallback, new java.util.function.Consumer() { // from class: android.service.appprediction.AppPredictionService$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.appprediction.AppPredictionService.this.lambda$doRegisterPredictionUpdates$1(arrayList, (android.service.appprediction.AppPredictionService.CallbackWrapper) obj);
                }
            }));
            if (arrayList.size() == 1) {
                onStartPredictionUpdates();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doRegisterPredictionUpdates$1(final java.util.ArrayList arrayList, final android.service.appprediction.AppPredictionService.CallbackWrapper callbackWrapper) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.appprediction.AppPredictionService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.service.appprediction.AppPredictionService.this.lambda$doRegisterPredictionUpdates$0(arrayList, callbackWrapper);
            }
        });
    }

    public void onStartPredictionUpdates() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUnregisterPredictionUpdates(android.app.prediction.AppPredictionSessionId appPredictionSessionId, android.app.prediction.IPredictionCallback iPredictionCallback) {
        java.util.ArrayList<android.service.appprediction.AppPredictionService.CallbackWrapper> arrayList = this.mSessionCallbacks.get(appPredictionSessionId);
        if (arrayList == null) {
            android.util.Slog.e(TAG, "Failed to unregister for updates for unknown session: " + appPredictionSessionId);
        } else {
            lambda$doRegisterPredictionUpdates$0(arrayList, findCallbackWrapper(arrayList, iPredictionCallback));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: removeCallbackWrapper, reason: merged with bridge method [inline-methods] */
    public void lambda$doRegisterPredictionUpdates$0(java.util.ArrayList<android.service.appprediction.AppPredictionService.CallbackWrapper> arrayList, android.service.appprediction.AppPredictionService.CallbackWrapper callbackWrapper) {
        if (arrayList == null || callbackWrapper == null) {
            return;
        }
        arrayList.remove(callbackWrapper);
        callbackWrapper.destroy();
        if (arrayList.isEmpty()) {
            onStopPredictionUpdates();
        }
    }

    public void onStopPredictionUpdates() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRequestPredictionUpdate(android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
        java.util.ArrayList<android.service.appprediction.AppPredictionService.CallbackWrapper> arrayList = this.mSessionCallbacks.get(appPredictionSessionId);
        if (arrayList != null && !arrayList.isEmpty()) {
            onRequestPredictionUpdate(appPredictionSessionId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDestroyPredictionSession(android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
        java.util.ArrayList<android.service.appprediction.AppPredictionService.CallbackWrapper> remove = this.mSessionCallbacks.remove(appPredictionSessionId);
        if (remove != null) {
            remove.forEach(new java.util.function.Consumer() { // from class: android.service.appprediction.AppPredictionService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.service.appprediction.AppPredictionService.CallbackWrapper) obj).destroy();
                }
            });
        }
        onDestroyPredictionSession(appPredictionSessionId);
    }

    public void onDestroyPredictionSession(android.app.prediction.AppPredictionSessionId appPredictionSessionId) {
    }

    public void onRequestServiceFeatures(android.app.prediction.AppPredictionSessionId appPredictionSessionId, java.util.function.Consumer<android.os.Bundle> consumer) {
    }

    public final void updatePredictions(android.app.prediction.AppPredictionSessionId appPredictionSessionId, java.util.List<android.app.prediction.AppTarget> list) {
        java.util.ArrayList<android.service.appprediction.AppPredictionService.CallbackWrapper> arrayList = this.mSessionCallbacks.get(appPredictionSessionId);
        if (arrayList != null) {
            java.util.Iterator<android.service.appprediction.AppPredictionService.CallbackWrapper> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().accept(list);
            }
        }
    }

    private android.service.appprediction.AppPredictionService.CallbackWrapper findCallbackWrapper(java.util.ArrayList<android.service.appprediction.AppPredictionService.CallbackWrapper> arrayList, android.app.prediction.IPredictionCallback iPredictionCallback) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size).isCallback(iPredictionCallback)) {
                return arrayList.get(size);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CallbackWrapper implements java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>>, android.os.IBinder.DeathRecipient {
        private android.app.prediction.IPredictionCallback mCallback;
        private final java.util.function.Consumer<android.service.appprediction.AppPredictionService.CallbackWrapper> mOnBinderDied;

        CallbackWrapper(android.app.prediction.IPredictionCallback iPredictionCallback, java.util.function.Consumer<android.service.appprediction.AppPredictionService.CallbackWrapper> consumer) {
            this.mCallback = iPredictionCallback;
            this.mOnBinderDied = consumer;
            if (this.mOnBinderDied != null) {
                try {
                    this.mCallback.asBinder().linkToDeath(this, 0);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.appprediction.AppPredictionService.TAG, "Failed to link to death: " + e);
                }
            }
        }

        public boolean isCallback(android.app.prediction.IPredictionCallback iPredictionCallback) {
            if (this.mCallback == null) {
                android.util.Slog.e(android.service.appprediction.AppPredictionService.TAG, "Callback is null, likely the binder has died.");
                return false;
            }
            return this.mCallback.asBinder().equals(iPredictionCallback.asBinder());
        }

        public void destroy() {
            if (this.mCallback != null && this.mOnBinderDied != null) {
                this.mCallback.asBinder().unlinkToDeath(this, 0);
            }
        }

        @Override // java.util.function.Consumer
        public void accept(java.util.List<android.app.prediction.AppTarget> list) {
            try {
                if (this.mCallback != null) {
                    this.mCallback.onResult(new android.content.pm.ParceledListSlice(list));
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(android.service.appprediction.AppPredictionService.TAG, "Error sending result:" + e);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            destroy();
            this.mCallback = null;
            if (this.mOnBinderDied != null) {
                this.mOnBinderDied.accept(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class RemoteCallbackWrapper implements java.util.function.Consumer<android.os.Bundle>, android.os.IBinder.DeathRecipient {
        private android.os.IRemoteCallback mCallback;
        private final java.util.function.Consumer<android.service.appprediction.AppPredictionService.RemoteCallbackWrapper> mOnBinderDied;

        RemoteCallbackWrapper(android.os.IRemoteCallback iRemoteCallback, java.util.function.Consumer<android.service.appprediction.AppPredictionService.RemoteCallbackWrapper> consumer) {
            this.mCallback = iRemoteCallback;
            this.mOnBinderDied = consumer;
            if (this.mOnBinderDied != null) {
                try {
                    this.mCallback.asBinder().linkToDeath(this, 0);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.appprediction.AppPredictionService.TAG, "Failed to link to death: " + e);
                }
            }
        }

        public void destroy() {
            if (this.mCallback != null && this.mOnBinderDied != null) {
                this.mCallback.asBinder().unlinkToDeath(this, 0);
            }
        }

        @Override // java.util.function.Consumer
        public void accept(android.os.Bundle bundle) {
            try {
                if (this.mCallback != null) {
                    this.mCallback.sendResult(bundle);
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(android.service.appprediction.AppPredictionService.TAG, "Error sending result:" + e);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            destroy();
            this.mCallback = null;
            if (this.mOnBinderDied != null) {
                this.mOnBinderDied.accept(this);
            }
        }
    }
}
