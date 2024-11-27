package android.service.search;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class SearchUiService extends android.app.Service {
    private static final boolean DEBUG = false;
    public static final java.lang.String SERVICE_INTERFACE = "android.service.search.SearchUiService";
    private static final java.lang.String TAG = "SearchUiService";
    private android.os.Handler mHandler;
    private final android.util.ArrayMap<android.app.search.SearchSessionId, java.util.ArrayList<android.service.search.SearchUiService.CallbackWrapper>> mSessionEmptyQueryResultCallbacks = new android.util.ArrayMap<>();
    private final android.service.search.ISearchUiService mInterface = new android.service.search.SearchUiService.AnonymousClass1();

    public abstract void onDestroy(android.app.search.SearchSessionId searchSessionId);

    public abstract void onNotifyEvent(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.SearchTargetEvent searchTargetEvent);

    public abstract void onQuery(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, java.util.function.Consumer<java.util.List<android.app.search.SearchTarget>> consumer);

    /* renamed from: android.service.search.SearchUiService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.search.ISearchUiService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.search.ISearchUiService
        public void onCreateSearchSession(android.app.search.SearchContext searchContext, android.app.search.SearchSessionId searchSessionId) {
            android.service.search.SearchUiService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.search.SearchUiService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.search.SearchUiService) obj).onSearchSessionCreated((android.app.search.SearchContext) obj2, (android.app.search.SearchSessionId) obj3);
                }
            }, android.service.search.SearchUiService.this, searchContext, searchSessionId));
            android.service.search.SearchUiService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.search.SearchUiService$1$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.search.SearchUiService) obj).onCreateSearchSession((android.app.search.SearchContext) obj2, (android.app.search.SearchSessionId) obj3);
                }
            }, android.service.search.SearchUiService.this, searchContext, searchSessionId));
        }

        @Override // android.service.search.ISearchUiService
        public void onQuery(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.ISearchCallback iSearchCallback) {
            android.service.search.SearchUiService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.service.search.SearchUiService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.service.search.SearchUiService) obj).onQuery((android.app.search.SearchSessionId) obj2, (android.app.search.Query) obj3, (android.service.search.SearchUiService.CallbackWrapper) obj4);
                }
            }, android.service.search.SearchUiService.this, searchSessionId, query, new android.service.search.SearchUiService.CallbackWrapper(iSearchCallback, null)));
        }

        @Override // android.service.search.ISearchUiService
        public void onNotifyEvent(android.app.search.SearchSessionId searchSessionId, android.app.search.Query query, android.app.search.SearchTargetEvent searchTargetEvent) {
            android.service.search.SearchUiService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.service.search.SearchUiService$1$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.service.search.SearchUiService) obj).onNotifyEvent((android.app.search.SearchSessionId) obj2, (android.app.search.Query) obj3, (android.app.search.SearchTargetEvent) obj4);
                }
            }, android.service.search.SearchUiService.this, searchSessionId, query, searchTargetEvent));
        }

        @Override // android.service.search.ISearchUiService
        public void onRegisterEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) {
            android.service.search.SearchUiService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.search.SearchUiService$1$$ExternalSyntheticLambda6
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.search.SearchUiService) obj).doRegisterEmptyQueryResultUpdateCallback((android.app.search.SearchSessionId) obj2, (android.app.search.ISearchCallback) obj3);
                }
            }, android.service.search.SearchUiService.this, searchSessionId, iSearchCallback));
        }

        @Override // android.service.search.ISearchUiService
        public void onUnregisterEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) {
            android.service.search.SearchUiService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.search.SearchUiService$1$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.search.SearchUiService) obj).doUnregisterEmptyQueryResultUpdateCallback((android.app.search.SearchSessionId) obj2, (android.app.search.ISearchCallback) obj3);
                }
            }, android.service.search.SearchUiService.this, searchSessionId, iSearchCallback));
        }

        @Override // android.service.search.ISearchUiService
        public void onDestroy(android.app.search.SearchSessionId searchSessionId) {
            android.service.search.SearchUiService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.search.SearchUiService$1$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.search.SearchUiService) obj).doDestroy((android.app.search.SearchSessionId) obj2);
                }
            }, android.service.search.SearchUiService.this, searchSessionId));
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
        android.util.Slog.w(TAG, "Tried to bind to wrong intent (should be android.service.search.SearchUiService: " + intent);
        return null;
    }

    @java.lang.Deprecated
    public void onCreateSearchSession(android.app.search.SearchContext searchContext, android.app.search.SearchSessionId searchSessionId) {
    }

    public void onSearchSessionCreated(android.app.search.SearchContext searchContext, android.app.search.SearchSessionId searchSessionId) {
        this.mSessionEmptyQueryResultCallbacks.put(searchSessionId, new java.util.ArrayList<>());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRegisterEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) {
        final java.util.ArrayList<android.service.search.SearchUiService.CallbackWrapper> arrayList = this.mSessionEmptyQueryResultCallbacks.get(searchSessionId);
        if (arrayList == null) {
            android.util.Slog.e(TAG, "Failed to register for updates for unknown session: " + searchSessionId);
        } else if (findCallbackWrapper(arrayList, iSearchCallback) == null) {
            arrayList.add(new android.service.search.SearchUiService.CallbackWrapper(iSearchCallback, new java.util.function.Consumer() { // from class: android.service.search.SearchUiService$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.search.SearchUiService.this.lambda$doRegisterEmptyQueryResultUpdateCallback$1(arrayList, (android.service.search.SearchUiService.CallbackWrapper) obj);
                }
            }));
            if (arrayList.size() == 1) {
                onStartUpdateEmptyQueryResult();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doRegisterEmptyQueryResultUpdateCallback$1(final java.util.ArrayList arrayList, final android.service.search.SearchUiService.CallbackWrapper callbackWrapper) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.search.SearchUiService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.service.search.SearchUiService.this.lambda$doRegisterEmptyQueryResultUpdateCallback$0(arrayList, callbackWrapper);
            }
        });
    }

    public void onStartUpdateEmptyQueryResult() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUnregisterEmptyQueryResultUpdateCallback(android.app.search.SearchSessionId searchSessionId, android.app.search.ISearchCallback iSearchCallback) {
        java.util.ArrayList<android.service.search.SearchUiService.CallbackWrapper> arrayList = this.mSessionEmptyQueryResultCallbacks.get(searchSessionId);
        if (arrayList == null) {
            android.util.Slog.e(TAG, "Failed to unregister for updates for unknown session: " + searchSessionId);
        } else {
            lambda$doRegisterEmptyQueryResultUpdateCallback$0(arrayList, findCallbackWrapper(arrayList, iSearchCallback));
        }
    }

    private android.service.search.SearchUiService.CallbackWrapper findCallbackWrapper(java.util.ArrayList<android.service.search.SearchUiService.CallbackWrapper> arrayList, android.app.search.ISearchCallback iSearchCallback) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size).isCallback(iSearchCallback)) {
                return arrayList.get(size);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: removeCallbackWrapper, reason: merged with bridge method [inline-methods] */
    public void lambda$doRegisterEmptyQueryResultUpdateCallback$0(java.util.ArrayList<android.service.search.SearchUiService.CallbackWrapper> arrayList, android.service.search.SearchUiService.CallbackWrapper callbackWrapper) {
        if (arrayList == null || callbackWrapper == null) {
            return;
        }
        arrayList.remove(callbackWrapper);
        callbackWrapper.destroy();
        if (arrayList.isEmpty()) {
            onStopUpdateEmptyQueryResult();
        }
    }

    public void onStopUpdateEmptyQueryResult() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDestroy(android.app.search.SearchSessionId searchSessionId) {
        super.onDestroy();
        onDestroy(searchSessionId);
    }

    public final void updateEmptyQueryResult(android.app.search.SearchSessionId searchSessionId, java.util.List<android.app.search.SearchTarget> list) {
        java.util.ArrayList<android.service.search.SearchUiService.CallbackWrapper> arrayList = this.mSessionEmptyQueryResultCallbacks.get(searchSessionId);
        if (arrayList != null) {
            java.util.Iterator<android.service.search.SearchUiService.CallbackWrapper> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().accept(list);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CallbackWrapper implements java.util.function.Consumer<java.util.List<android.app.search.SearchTarget>>, android.os.IBinder.DeathRecipient {
        private android.app.search.ISearchCallback mCallback;
        private final java.util.function.Consumer<android.service.search.SearchUiService.CallbackWrapper> mOnBinderDied;

        CallbackWrapper(android.app.search.ISearchCallback iSearchCallback, java.util.function.Consumer<android.service.search.SearchUiService.CallbackWrapper> consumer) {
            this.mCallback = iSearchCallback;
            this.mOnBinderDied = consumer;
            if (this.mOnBinderDied != null) {
                try {
                    this.mCallback.asBinder().linkToDeath(this, 0);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.search.SearchUiService.TAG, "Failed to link to death:" + e);
                }
            }
        }

        public boolean isCallback(android.app.search.ISearchCallback iSearchCallback) {
            if (this.mCallback == null) {
                android.util.Slog.e(android.service.search.SearchUiService.TAG, "Callback is null, likely the binder has died.");
                return false;
            }
            return this.mCallback.asBinder().equals(iSearchCallback.asBinder());
        }

        @Override // java.util.function.Consumer
        public void accept(java.util.List<android.app.search.SearchTarget> list) {
            try {
                if (this.mCallback != null) {
                    this.mCallback.onResult(new android.content.pm.ParceledListSlice(list));
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(android.service.search.SearchUiService.TAG, "Error sending result:" + e);
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
