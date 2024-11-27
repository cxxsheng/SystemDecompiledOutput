package android.service.smartspace;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class SmartspaceService extends android.app.Service {
    private static final boolean DEBUG = false;
    public static final java.lang.String SERVICE_INTERFACE = "android.service.smartspace.SmartspaceService";
    private static final java.lang.String TAG = "SmartspaceService";
    private android.os.Handler mHandler;
    private final android.util.ArrayMap<android.app.smartspace.SmartspaceSessionId, java.util.ArrayList<android.service.smartspace.SmartspaceService.CallbackWrapper>> mSessionCallbacks = new android.util.ArrayMap<>();
    private final android.service.smartspace.ISmartspaceService mInterface = new android.service.smartspace.SmartspaceService.AnonymousClass1();

    public abstract void notifySmartspaceEvent(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.SmartspaceTargetEvent smartspaceTargetEvent);

    public abstract void onCreateSmartspaceSession(android.app.smartspace.SmartspaceConfig smartspaceConfig, android.app.smartspace.SmartspaceSessionId smartspaceSessionId);

    public abstract void onDestroy(android.app.smartspace.SmartspaceSessionId smartspaceSessionId);

    public abstract void onDestroySmartspaceSession(android.app.smartspace.SmartspaceSessionId smartspaceSessionId);

    public abstract void onRequestSmartspaceUpdate(android.app.smartspace.SmartspaceSessionId smartspaceSessionId);

    /* renamed from: android.service.smartspace.SmartspaceService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.smartspace.ISmartspaceService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void onCreateSmartspaceSession(android.app.smartspace.SmartspaceConfig smartspaceConfig, android.app.smartspace.SmartspaceSessionId smartspaceSessionId) {
            android.service.smartspace.SmartspaceService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.smartspace.SmartspaceService) obj).doCreateSmartspaceSession((android.app.smartspace.SmartspaceConfig) obj2, (android.app.smartspace.SmartspaceSessionId) obj3);
                }
            }, android.service.smartspace.SmartspaceService.this, smartspaceConfig, smartspaceSessionId));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void notifySmartspaceEvent(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.SmartspaceTargetEvent smartspaceTargetEvent) {
            android.service.smartspace.SmartspaceService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.smartspace.SmartspaceService) obj).notifySmartspaceEvent((android.app.smartspace.SmartspaceSessionId) obj2, (android.app.smartspace.SmartspaceTargetEvent) obj3);
                }
            }, android.service.smartspace.SmartspaceService.this, smartspaceSessionId, smartspaceTargetEvent));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void requestSmartspaceUpdate(android.app.smartspace.SmartspaceSessionId smartspaceSessionId) {
            android.service.smartspace.SmartspaceService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.smartspace.SmartspaceService) obj).doRequestPredictionUpdate((android.app.smartspace.SmartspaceSessionId) obj2);
                }
            }, android.service.smartspace.SmartspaceService.this, smartspaceSessionId));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void registerSmartspaceUpdates(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) {
            android.service.smartspace.SmartspaceService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.smartspace.SmartspaceService) obj).doRegisterSmartspaceUpdates((android.app.smartspace.SmartspaceSessionId) obj2, (android.app.smartspace.ISmartspaceCallback) obj3);
                }
            }, android.service.smartspace.SmartspaceService.this, smartspaceSessionId, iSmartspaceCallback));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void unregisterSmartspaceUpdates(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) {
            android.service.smartspace.SmartspaceService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.smartspace.SmartspaceService) obj).doUnregisterSmartspaceUpdates((android.app.smartspace.SmartspaceSessionId) obj2, (android.app.smartspace.ISmartspaceCallback) obj3);
                }
            }, android.service.smartspace.SmartspaceService.this, smartspaceSessionId, iSmartspaceCallback));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void onDestroySmartspaceSession(android.app.smartspace.SmartspaceSessionId smartspaceSessionId) {
            android.service.smartspace.SmartspaceService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.smartspace.SmartspaceService) obj).doDestroy((android.app.smartspace.SmartspaceSessionId) obj2);
                }
            }, android.service.smartspace.SmartspaceService.this, smartspaceSessionId));
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
        android.util.Slog.w(TAG, "Tried to bind to wrong intent (should be android.service.smartspace.SmartspaceService: " + intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCreateSmartspaceSession(android.app.smartspace.SmartspaceConfig smartspaceConfig, android.app.smartspace.SmartspaceSessionId smartspaceSessionId) {
        this.mSessionCallbacks.put(smartspaceSessionId, new java.util.ArrayList<>());
        onCreateSmartspaceSession(smartspaceConfig, smartspaceSessionId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRegisterSmartspaceUpdates(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) {
        final java.util.ArrayList<android.service.smartspace.SmartspaceService.CallbackWrapper> arrayList = this.mSessionCallbacks.get(smartspaceSessionId);
        if (arrayList == null) {
            android.util.Slog.e(TAG, "Failed to register for updates for unknown session: " + smartspaceSessionId);
        } else if (findCallbackWrapper(arrayList, iSmartspaceCallback) == null) {
            arrayList.add(new android.service.smartspace.SmartspaceService.CallbackWrapper(iSmartspaceCallback, new java.util.function.Consumer() { // from class: android.service.smartspace.SmartspaceService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.smartspace.SmartspaceService.this.lambda$doRegisterSmartspaceUpdates$1(arrayList, (android.service.smartspace.SmartspaceService.CallbackWrapper) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doRegisterSmartspaceUpdates$1(final java.util.ArrayList arrayList, final android.service.smartspace.SmartspaceService.CallbackWrapper callbackWrapper) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.smartspace.SmartspaceService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.service.smartspace.SmartspaceService.this.lambda$doRegisterSmartspaceUpdates$0(arrayList, callbackWrapper);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUnregisterSmartspaceUpdates(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) {
        java.util.ArrayList<android.service.smartspace.SmartspaceService.CallbackWrapper> arrayList = this.mSessionCallbacks.get(smartspaceSessionId);
        if (arrayList == null) {
            android.util.Slog.e(TAG, "Failed to unregister for updates for unknown session: " + smartspaceSessionId);
        } else {
            lambda$doRegisterSmartspaceUpdates$0(arrayList, findCallbackWrapper(arrayList, iSmartspaceCallback));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRequestPredictionUpdate(android.app.smartspace.SmartspaceSessionId smartspaceSessionId) {
        java.util.ArrayList<android.service.smartspace.SmartspaceService.CallbackWrapper> arrayList = this.mSessionCallbacks.get(smartspaceSessionId);
        if (arrayList != null && !arrayList.isEmpty()) {
            onRequestSmartspaceUpdate(smartspaceSessionId);
        }
    }

    private android.service.smartspace.SmartspaceService.CallbackWrapper findCallbackWrapper(java.util.ArrayList<android.service.smartspace.SmartspaceService.CallbackWrapper> arrayList, android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size).isCallback(iSmartspaceCallback)) {
                return arrayList.get(size);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: removeCallbackWrapper, reason: merged with bridge method [inline-methods] */
    public void lambda$doRegisterSmartspaceUpdates$0(java.util.ArrayList<android.service.smartspace.SmartspaceService.CallbackWrapper> arrayList, android.service.smartspace.SmartspaceService.CallbackWrapper callbackWrapper) {
        if (arrayList == null || callbackWrapper == null) {
            return;
        }
        arrayList.remove(callbackWrapper);
        callbackWrapper.destroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDestroy(android.app.smartspace.SmartspaceSessionId smartspaceSessionId) {
        super.onDestroy();
        java.util.ArrayList<android.service.smartspace.SmartspaceService.CallbackWrapper> remove = this.mSessionCallbacks.remove(smartspaceSessionId);
        if (remove != null) {
            remove.forEach(new java.util.function.Consumer() { // from class: android.service.smartspace.SmartspaceService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.service.smartspace.SmartspaceService.CallbackWrapper) obj).destroy();
                }
            });
        }
        onDestroySmartspaceSession(smartspaceSessionId);
    }

    public final void updateSmartspaceTargets(android.app.smartspace.SmartspaceSessionId smartspaceSessionId, java.util.List<android.app.smartspace.SmartspaceTarget> list) {
        java.util.ArrayList<android.service.smartspace.SmartspaceService.CallbackWrapper> arrayList = this.mSessionCallbacks.get(smartspaceSessionId);
        if (arrayList != null) {
            java.util.Iterator<android.service.smartspace.SmartspaceService.CallbackWrapper> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().accept(list);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CallbackWrapper implements java.util.function.Consumer<java.util.List<android.app.smartspace.SmartspaceTarget>>, android.os.IBinder.DeathRecipient {
        private android.app.smartspace.ISmartspaceCallback mCallback;
        private final java.util.function.Consumer<android.service.smartspace.SmartspaceService.CallbackWrapper> mOnBinderDied;

        CallbackWrapper(android.app.smartspace.ISmartspaceCallback iSmartspaceCallback, java.util.function.Consumer<android.service.smartspace.SmartspaceService.CallbackWrapper> consumer) {
            this.mCallback = iSmartspaceCallback;
            this.mOnBinderDied = consumer;
            if (this.mOnBinderDied != null) {
                try {
                    this.mCallback.asBinder().linkToDeath(this, 0);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.smartspace.SmartspaceService.TAG, "Failed to link to death: " + e);
                }
            }
        }

        public boolean isCallback(android.app.smartspace.ISmartspaceCallback iSmartspaceCallback) {
            if (this.mCallback == null) {
                android.util.Slog.e(android.service.smartspace.SmartspaceService.TAG, "Callback is null, likely the binder has died.");
                return false;
            }
            return this.mCallback.asBinder().equals(iSmartspaceCallback.asBinder());
        }

        @Override // java.util.function.Consumer
        public void accept(java.util.List<android.app.smartspace.SmartspaceTarget> list) {
            try {
                if (this.mCallback != null) {
                    this.mCallback.onResult(new android.content.pm.ParceledListSlice(list));
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(android.service.smartspace.SmartspaceService.TAG, "Error sending result:" + e);
            }
        }

        public void destroy() {
            if (this.mCallback != null && this.mOnBinderDied != null) {
                this.mCallback.asBinder().unlinkToDeath(this, 0);
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
