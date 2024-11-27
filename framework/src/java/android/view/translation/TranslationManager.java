package android.view.translation;

/* loaded from: classes4.dex */
public final class TranslationManager {
    public static final java.lang.String EXTRA_CAPABILITIES = "translation_capabilities";
    public static final int STATUS_SYNC_CALL_FAIL = 2;
    public static final int STATUS_SYNC_CALL_SUCCESS = 1;
    static final int SYNC_CALLS_TIMEOUT_MS = 60000;
    private static final java.lang.String TAG = "TranslationManager";
    private final android.content.Context mContext;
    private final android.view.translation.ITranslationManager mService;
    private static final java.security.SecureRandom ID_GENERATOR = new java.security.SecureRandom();
    private static final java.util.concurrent.atomic.AtomicInteger sAvailableRequestId = new java.util.concurrent.atomic.AtomicInteger(1);
    private final android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.Integer>, java.util.ArrayList<android.app.PendingIntent>> mTranslationCapabilityUpdateListeners = new android.util.ArrayMap<>();
    private final java.util.Map<java.util.function.Consumer<android.view.translation.TranslationCapability>, android.os.IRemoteCallback> mCapabilityCallbacks = new android.util.ArrayMap();
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.IntArray mTranslatorIds = new android.util.IntArray();
    private final android.os.Handler mHandler = android.os.Handler.createAsync(android.os.Looper.getMainLooper());

    public TranslationManager(android.content.Context context, android.view.translation.ITranslationManager iTranslationManager) {
        this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context, "context cannot be null");
        this.mService = iTranslationManager;
    }

    public void createOnDeviceTranslator(android.view.translation.TranslationContext translationContext, final java.util.concurrent.Executor executor, final java.util.function.Consumer<android.view.translation.Translator> consumer) {
        java.util.Objects.requireNonNull(translationContext, "translationContext cannot be null");
        java.util.Objects.requireNonNull(executor, "executor cannot be null");
        java.util.Objects.requireNonNull(consumer, "callback cannot be null");
        synchronized (this.mLock) {
            while (true) {
                final int abs = java.lang.Math.abs(ID_GENERATOR.nextInt());
                if (abs != 0 && this.mTranslatorIds.indexOf(abs) < 0) {
                    new android.view.translation.Translator(this.mContext, translationContext, abs, this, this.mHandler, this.mService, new java.util.function.Consumer() { // from class: android.view.translation.TranslationManager$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            android.view.translation.TranslationManager.this.lambda$createOnDeviceTranslator$4(executor, consumer, abs, (android.view.translation.Translator) obj);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOnDeviceTranslator$4(final java.util.concurrent.Executor executor, final java.util.function.Consumer consumer, int i, final android.view.translation.Translator translator) {
        if (translator == null) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.view.translation.TranslationManager$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.view.translation.TranslationManager$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            r1.accept(null);
                        }
                    });
                }
            });
            return;
        }
        synchronized (this.mLock) {
            this.mTranslatorIds.add(i);
        }
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.view.translation.TranslationManager$$ExternalSyntheticLambda2
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
                executor.execute(new java.lang.Runnable() { // from class: android.view.translation.TranslationManager$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        r1.accept(r2);
                    }
                });
            }
        });
    }

    @java.lang.Deprecated
    public android.view.translation.Translator createOnDeviceTranslator(android.view.translation.TranslationContext translationContext) {
        int abs;
        java.util.Objects.requireNonNull(translationContext, "translationContext cannot be null");
        synchronized (this.mLock) {
            while (true) {
                abs = java.lang.Math.abs(ID_GENERATOR.nextInt());
                if (abs != 0 && this.mTranslatorIds.indexOf(abs) < 0) {
                    break;
                }
            }
            android.view.translation.Translator translator = new android.view.translation.Translator(this.mContext, translationContext, abs, this, this.mHandler, this.mService);
            translator.start();
            try {
                if (!translator.isSessionCreated()) {
                    return null;
                }
                this.mTranslatorIds.add(abs);
                return translator;
            } catch (android.view.translation.Translator.ServiceBinderReceiver.TimeoutException e) {
                android.util.Log.e(TAG, "Timed out getting create session: " + e);
                return null;
            }
        }
    }

    @java.lang.Deprecated
    public android.view.translation.Translator createTranslator(android.view.translation.TranslationContext translationContext) {
        return createOnDeviceTranslator(translationContext);
    }

    public java.util.Set<android.view.translation.TranslationCapability> getOnDeviceTranslationCapabilities(int i, int i2) {
        try {
            android.os.SynchronousResultReceiver synchronousResultReceiver = new android.os.SynchronousResultReceiver();
            this.mService.onTranslationCapabilitiesRequest(i, i2, synchronousResultReceiver, this.mContext.getUserId());
            android.os.SynchronousResultReceiver.Result awaitResult = synchronousResultReceiver.awaitResult(60000L);
            if (awaitResult.resultCode != 1) {
                return java.util.Collections.emptySet();
            }
            android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) awaitResult.bundle.getParcelable(EXTRA_CAPABILITIES, android.content.pm.ParceledListSlice.class);
            return new android.util.ArraySet(parceledListSlice == null ? null : parceledListSlice.getList());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.util.concurrent.TimeoutException e2) {
            android.util.Log.e(TAG, "Timed out getting supported translation capabilities: " + e2);
            return java.util.Collections.emptySet();
        }
    }

    @java.lang.Deprecated
    public java.util.Set<android.view.translation.TranslationCapability> getTranslationCapabilities(int i, int i2) {
        return getOnDeviceTranslationCapabilities(i, i2);
    }

    public void addOnDeviceTranslationCapabilityUpdateListener(java.util.concurrent.Executor executor, java.util.function.Consumer<android.view.translation.TranslationCapability> consumer) {
        java.util.Objects.requireNonNull(executor, "executor should not be null");
        java.util.Objects.requireNonNull(consumer, "capability listener should not be null");
        synchronized (this.mLock) {
            if (this.mCapabilityCallbacks.containsKey(consumer)) {
                android.util.Log.w(TAG, "addOnDeviceTranslationCapabilityUpdateListener: the listener for " + consumer + " already registered; ignoring.");
                return;
            }
            android.view.translation.TranslationManager.TranslationCapabilityRemoteCallback translationCapabilityRemoteCallback = new android.view.translation.TranslationManager.TranslationCapabilityRemoteCallback(executor, consumer);
            try {
                this.mService.registerTranslationCapabilityCallback(translationCapabilityRemoteCallback, this.mContext.getUserId());
                this.mCapabilityCallbacks.put(consumer, translationCapabilityRemoteCallback);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public void addOnDeviceTranslationCapabilityUpdateListener(int i, int i2, android.app.PendingIntent pendingIntent) {
        java.util.Objects.requireNonNull(pendingIntent, "pending intent should not be null");
        synchronized (this.mLock) {
            this.mTranslationCapabilityUpdateListeners.computeIfAbsent(new android.util.Pair<>(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)), new java.util.function.Function() { // from class: android.view.translation.TranslationManager$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return android.view.translation.TranslationManager.lambda$addOnDeviceTranslationCapabilityUpdateListener$5((android.util.Pair) obj);
                }
            }).add(pendingIntent);
        }
    }

    static /* synthetic */ java.util.ArrayList lambda$addOnDeviceTranslationCapabilityUpdateListener$5(android.util.Pair pair) {
        return new java.util.ArrayList();
    }

    @java.lang.Deprecated
    public void addTranslationCapabilityUpdateListener(int i, int i2, android.app.PendingIntent pendingIntent) {
        addOnDeviceTranslationCapabilityUpdateListener(i, i2, pendingIntent);
    }

    public void removeOnDeviceTranslationCapabilityUpdateListener(java.util.function.Consumer<android.view.translation.TranslationCapability> consumer) {
        java.util.Objects.requireNonNull(consumer, "capability callback should not be null");
        synchronized (this.mLock) {
            android.os.IRemoteCallback iRemoteCallback = this.mCapabilityCallbacks.get(consumer);
            if (iRemoteCallback == null) {
                android.util.Log.w(TAG, "removeOnDeviceTranslationCapabilityUpdateListener: the capability listener not found; ignoring.");
                return;
            }
            try {
                this.mService.unregisterTranslationCapabilityCallback(iRemoteCallback, this.mContext.getUserId());
                this.mCapabilityCallbacks.remove(consumer);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public void removeOnDeviceTranslationCapabilityUpdateListener(int i, int i2, android.app.PendingIntent pendingIntent) {
        java.util.Objects.requireNonNull(pendingIntent, "pending intent should not be null");
        synchronized (this.mLock) {
            android.util.Pair pair = new android.util.Pair(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
            if (this.mTranslationCapabilityUpdateListeners.containsKey(pair)) {
                java.util.ArrayList<android.app.PendingIntent> arrayList = this.mTranslationCapabilityUpdateListeners.get(pair);
                if (arrayList.contains(pendingIntent)) {
                    arrayList.remove(pendingIntent);
                } else {
                    android.util.Log.w(TAG, "pending intent=" + pendingIntent + " does not exist in mTranslationCapabilityUpdateListeners");
                }
            } else {
                android.util.Log.w(TAG, "format pair=" + pair + " does not exist in mTranslationCapabilityUpdateListeners");
            }
        }
    }

    @java.lang.Deprecated
    public void removeTranslationCapabilityUpdateListener(int i, int i2, android.app.PendingIntent pendingIntent) {
        removeOnDeviceTranslationCapabilityUpdateListener(i, i2, pendingIntent);
    }

    public android.app.PendingIntent getOnDeviceTranslationSettingsActivityIntent() {
        com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(60000);
        try {
            this.mService.getServiceSettingsActivity(syncResultReceiver, this.mContext.getUserId());
            try {
                return (android.app.PendingIntent) syncResultReceiver.getParcelableResult();
            } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e) {
                android.util.Log.e(TAG, "Fail to get translation service settings activity.");
                return null;
            }
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public android.app.PendingIntent getTranslationSettingsActivityIntent() {
        return getOnDeviceTranslationSettingsActivityIntent();
    }

    void removeTranslator(int i) {
        synchronized (this.mLock) {
            int indexOf = this.mTranslatorIds.indexOf(i);
            if (indexOf >= 0) {
                this.mTranslatorIds.remove(indexOf);
            }
        }
    }

    java.util.concurrent.atomic.AtomicInteger getAvailableRequestId() {
        java.util.concurrent.atomic.AtomicInteger atomicInteger;
        synchronized (this.mLock) {
            atomicInteger = sAvailableRequestId;
        }
        return atomicInteger;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TranslationCapabilityRemoteCallback extends android.os.IRemoteCallback.Stub {
        private final java.util.concurrent.Executor mExecutor;
        private final java.util.function.Consumer<android.view.translation.TranslationCapability> mListener;

        TranslationCapabilityRemoteCallback(java.util.concurrent.Executor executor, java.util.function.Consumer<android.view.translation.TranslationCapability> consumer) {
            this.mExecutor = executor;
            this.mListener = consumer;
        }

        @Override // android.os.IRemoteCallback
        public void sendResult(final android.os.Bundle bundle) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.view.translation.TranslationManager$TranslationCapabilityRemoteCallback$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.view.translation.TranslationManager.TranslationCapabilityRemoteCallback.this.lambda$sendResult$1(bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendResult$1(final android.os.Bundle bundle) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.view.translation.TranslationManager$TranslationCapabilityRemoteCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.translation.TranslationManager.TranslationCapabilityRemoteCallback.this.lambda$sendResult$0(bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: onTranslationCapabilityUpdate, reason: merged with bridge method [inline-methods] */
        public void lambda$sendResult$0(android.os.Bundle bundle) {
            this.mListener.accept((android.view.translation.TranslationCapability) bundle.getParcelable(android.view.translation.TranslationManager.EXTRA_CAPABILITIES, android.view.translation.TranslationCapability.class));
        }
    }
}
