package android.media.soundtrigger;

/* loaded from: classes2.dex */
public final class SoundTriggerInstrumentation {
    private final android.media.soundtrigger.SoundTriggerInstrumentation.GlobalCallback mClientCallback;
    private final java.util.concurrent.Executor mGlobalCallbackExecutor;
    private final com.android.internal.app.ISoundTriggerService mService;
    private final java.lang.Object mLock = new java.lang.Object();
    private android.media.soundtrigger_middleware.IInjectGlobalEvent mInjectGlobalEvent = null;
    private java.util.Map<android.os.IBinder, android.media.soundtrigger.SoundTriggerInstrumentation.ModelSession> mModelSessionMap = new java.util.HashMap();
    private java.util.Map<android.os.IBinder, android.media.soundtrigger.SoundTriggerInstrumentation.RecognitionSession> mRecognitionSessionMap = new java.util.HashMap();
    private android.os.IBinder mClientToken = null;

    public interface RecognitionCallback {
        void onRecognitionStopped();
    }

    public interface GlobalCallback {
        void onModelLoaded(android.media.soundtrigger.SoundTriggerInstrumentation.ModelSession modelSession);

        default void onPreempted() {
        }

        default void onRestarted() {
        }

        default void onFrameworkDetached() {
        }

        default void onClientAttached() {
        }

        default void onClientDetached() {
        }
    }

    public interface ModelCallback {
        void onRecognitionStarted(android.media.soundtrigger.SoundTriggerInstrumentation.RecognitionSession recognitionSession);

        default void onModelUnloaded() {
        }

        default void onParamSet(int i, int i2) {
        }
    }

    public class ModelSession {
        private final java.util.List<java.util.function.Consumer<android.media.soundtrigger.SoundTriggerInstrumentation.ModelCallback>> mDroppedConsumerList;
        private final android.media.soundtrigger_middleware.IInjectModelEvent mInjectModelEvent;
        private final android.media.soundtrigger.SoundTriggerManager.Model mModel;
        private android.media.soundtrigger.SoundTriggerInstrumentation.ModelCallback mModelCallback;
        private java.util.concurrent.Executor mModelExecutor;
        private final android.hardware.soundtrigger.SoundTrigger.Keyphrase[] mPhrases;

        public void triggerUnloadModel() {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                try {
                    try {
                        this.mInjectModelEvent.triggerUnloadModel();
                        android.media.soundtrigger.SoundTriggerInstrumentation.this.mModelSessionMap.remove(this.mInjectModelEvent.asBinder());
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public android.media.soundtrigger.SoundTriggerManager.Model getSoundModel() {
            return this.mModel;
        }

        public java.util.List<android.hardware.soundtrigger.SoundTrigger.Keyphrase> getPhrases() {
            if (this.mPhrases == null) {
                return new java.util.ArrayList();
            }
            return new java.util.ArrayList(java.util.Arrays.asList(this.mPhrases));
        }

        public boolean isKeyphrase() {
            return this.mPhrases != null;
        }

        public void setModelCallback(java.util.concurrent.Executor executor, final android.media.soundtrigger.SoundTriggerInstrumentation.ModelCallback modelCallback) {
            java.util.Objects.requireNonNull(modelCallback);
            java.util.Objects.requireNonNull(executor);
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                if (this.mModelCallback == null) {
                    for (final java.util.function.Consumer<android.media.soundtrigger.SoundTriggerInstrumentation.ModelCallback> consumer : this.mDroppedConsumerList) {
                        executor.execute(new java.lang.Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$ModelSession$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer.accept(modelCallback);
                            }
                        });
                    }
                    this.mDroppedConsumerList.clear();
                }
                this.mModelCallback = modelCallback;
                this.mModelExecutor = executor;
            }
        }

        public void clearModelCallback() {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                this.mModelCallback = null;
                this.mModelExecutor = null;
            }
        }

        private ModelSession(android.media.soundtrigger.SoundModel soundModel, android.media.soundtrigger.Phrase[] phraseArr, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) {
            this.mModelCallback = null;
            this.mModelExecutor = null;
            this.mDroppedConsumerList = new java.util.ArrayList();
            this.mModel = android.media.soundtrigger.SoundTriggerManager.Model.create(java.util.UUID.fromString(soundModel.uuid), java.util.UUID.fromString(soundModel.vendorUuid), android.hardware.soundtrigger.ConversionUtil.sharedMemoryToByteArray(soundModel.data, soundModel.dataSize));
            if (phraseArr != null) {
                this.mPhrases = new android.hardware.soundtrigger.SoundTrigger.Keyphrase[phraseArr.length];
                int length = phraseArr.length;
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    this.mPhrases[i2] = android.hardware.soundtrigger.ConversionUtil.aidl2apiPhrase(phraseArr[i]);
                    i++;
                    i2++;
                }
            } else {
                this.mPhrases = null;
            }
            this.mInjectModelEvent = iInjectModelEvent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void wrap(final java.util.function.Consumer<android.media.soundtrigger.SoundTriggerInstrumentation.ModelCallback> consumer) {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                if (this.mModelCallback != null) {
                    final android.media.soundtrigger.SoundTriggerInstrumentation.ModelCallback modelCallback = this.mModelCallback;
                    this.mModelExecutor.execute(new java.lang.Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$ModelSession$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            consumer.accept(modelCallback);
                        }
                    });
                } else {
                    this.mDroppedConsumerList.add(consumer);
                }
            }
        }
    }

    public class RecognitionSession {
        private final int mAudioSession;
        private final java.util.List<java.util.function.Consumer<android.media.soundtrigger.SoundTriggerInstrumentation.RecognitionCallback>> mDroppedConsumerList;
        private final android.media.soundtrigger_middleware.IInjectRecognitionEvent mInjectRecognitionEvent;
        private android.media.soundtrigger.SoundTriggerInstrumentation.RecognitionCallback mRecognitionCallback;
        private final android.hardware.soundtrigger.SoundTrigger.RecognitionConfig mRecognitionConfig;
        private java.util.concurrent.Executor mRecognitionExecutor;

        public int getAudioSession() {
            return this.mAudioSession;
        }

        public android.hardware.soundtrigger.SoundTrigger.RecognitionConfig getRecognitionConfig() {
            return this.mRecognitionConfig;
        }

        public void triggerRecognitionEvent(byte[] bArr, java.util.List<android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra> list) {
            android.media.soundtrigger.PhraseRecognitionExtra[] phraseRecognitionExtraArr;
            if (list == null) {
                phraseRecognitionExtraArr = null;
            } else {
                phraseRecognitionExtraArr = new android.media.soundtrigger.PhraseRecognitionExtra[list.size()];
                java.util.Iterator<android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra> it = list.iterator();
                int i = 0;
                while (it.hasNext()) {
                    phraseRecognitionExtraArr[i] = android.hardware.soundtrigger.ConversionUtil.api2aidlPhraseRecognitionExtra(it.next());
                    i++;
                }
            }
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                android.media.soundtrigger.SoundTriggerInstrumentation.this.mRecognitionSessionMap.remove(this.mInjectRecognitionEvent.asBinder());
                try {
                    this.mInjectRecognitionEvent.triggerRecognitionEvent(bArr, phraseRecognitionExtraArr);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        public void triggerAbortRecognition() {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                android.media.soundtrigger.SoundTriggerInstrumentation.this.mRecognitionSessionMap.remove(this.mInjectRecognitionEvent.asBinder());
                try {
                    this.mInjectRecognitionEvent.triggerAbortRecognition();
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        public void setRecognitionCallback(java.util.concurrent.Executor executor, final android.media.soundtrigger.SoundTriggerInstrumentation.RecognitionCallback recognitionCallback) {
            java.util.Objects.requireNonNull(recognitionCallback);
            java.util.Objects.requireNonNull(executor);
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                if (this.mRecognitionCallback == null) {
                    for (final java.util.function.Consumer<android.media.soundtrigger.SoundTriggerInstrumentation.RecognitionCallback> consumer : this.mDroppedConsumerList) {
                        executor.execute(new java.lang.Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$RecognitionSession$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer.accept(recognitionCallback);
                            }
                        });
                    }
                    this.mDroppedConsumerList.clear();
                }
                this.mRecognitionCallback = recognitionCallback;
                this.mRecognitionExecutor = executor;
            }
        }

        public void clearRecognitionCallback() {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                this.mRecognitionCallback = null;
                this.mRecognitionExecutor = null;
            }
        }

        private RecognitionSession(int i, android.media.soundtrigger.RecognitionConfig recognitionConfig, android.media.soundtrigger_middleware.IInjectRecognitionEvent iInjectRecognitionEvent) {
            this.mRecognitionExecutor = null;
            this.mRecognitionCallback = null;
            this.mDroppedConsumerList = new java.util.ArrayList();
            this.mAudioSession = i;
            this.mRecognitionConfig = android.hardware.soundtrigger.ConversionUtil.aidl2apiRecognitionConfig(recognitionConfig);
            this.mInjectRecognitionEvent = iInjectRecognitionEvent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void wrap(final java.util.function.Consumer<android.media.soundtrigger.SoundTriggerInstrumentation.RecognitionCallback> consumer) {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                if (this.mRecognitionCallback != null) {
                    final android.media.soundtrigger.SoundTriggerInstrumentation.RecognitionCallback recognitionCallback = this.mRecognitionCallback;
                    this.mRecognitionExecutor.execute(new java.lang.Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$RecognitionSession$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            consumer.accept(recognitionCallback);
                        }
                    });
                } else {
                    this.mDroppedConsumerList.add(consumer);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Injection extends android.media.soundtrigger_middleware.ISoundTriggerInjection.Stub {
        private Injection() {
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void registerGlobalEventInjection(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                android.media.soundtrigger.SoundTriggerInstrumentation.this.mInjectGlobalEvent = iInjectGlobalEvent;
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onSoundModelLoaded(android.media.soundtrigger.SoundModel soundModel, android.media.soundtrigger.Phrase[] phraseArr, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent, android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                if (iInjectGlobalEvent.asBinder() != android.media.soundtrigger.SoundTriggerInstrumentation.this.mInjectGlobalEvent.asBinder()) {
                    return;
                }
                final android.media.soundtrigger.SoundTriggerInstrumentation.ModelSession modelSession = new android.media.soundtrigger.SoundTriggerInstrumentation.ModelSession(soundModel, phraseArr, iInjectModelEvent);
                android.media.soundtrigger.SoundTriggerInstrumentation.this.mModelSessionMap.put(iInjectModelEvent.asBinder(), modelSession);
                android.media.soundtrigger.SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.soundtrigger.SoundTriggerInstrumentation.Injection.this.lambda$onSoundModelLoaded$0(modelSession);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSoundModelLoaded$0(android.media.soundtrigger.SoundTriggerInstrumentation.ModelSession modelSession) {
            android.media.soundtrigger.SoundTriggerInstrumentation.this.mClientCallback.onModelLoaded(modelSession);
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onSoundModelUnloaded(android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                android.media.soundtrigger.SoundTriggerInstrumentation.ModelSession modelSession = (android.media.soundtrigger.SoundTriggerInstrumentation.ModelSession) android.media.soundtrigger.SoundTriggerInstrumentation.this.mModelSessionMap.remove(iInjectModelEvent.asBinder());
                if (modelSession == null) {
                    return;
                }
                modelSession.wrap(new java.util.function.Consumer() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((android.media.soundtrigger.SoundTriggerInstrumentation.ModelCallback) obj).onModelUnloaded();
                    }
                });
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRecognitionStarted(int i, android.media.soundtrigger.RecognitionConfig recognitionConfig, android.media.soundtrigger_middleware.IInjectRecognitionEvent iInjectRecognitionEvent, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                android.media.soundtrigger.SoundTriggerInstrumentation.ModelSession modelSession = (android.media.soundtrigger.SoundTriggerInstrumentation.ModelSession) android.media.soundtrigger.SoundTriggerInstrumentation.this.mModelSessionMap.get(iInjectModelEvent.asBinder());
                if (modelSession == null) {
                    return;
                }
                final android.media.soundtrigger.SoundTriggerInstrumentation.RecognitionSession recognitionSession = new android.media.soundtrigger.SoundTriggerInstrumentation.RecognitionSession(i, recognitionConfig, iInjectRecognitionEvent);
                android.media.soundtrigger.SoundTriggerInstrumentation.this.mRecognitionSessionMap.put(iInjectRecognitionEvent.asBinder(), recognitionSession);
                modelSession.wrap(new java.util.function.Consumer() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((android.media.soundtrigger.SoundTriggerInstrumentation.ModelCallback) obj).onRecognitionStarted(android.media.soundtrigger.SoundTriggerInstrumentation.RecognitionSession.this);
                    }
                });
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRecognitionStopped(android.media.soundtrigger_middleware.IInjectRecognitionEvent iInjectRecognitionEvent) {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                android.media.soundtrigger.SoundTriggerInstrumentation.RecognitionSession recognitionSession = (android.media.soundtrigger.SoundTriggerInstrumentation.RecognitionSession) android.media.soundtrigger.SoundTriggerInstrumentation.this.mRecognitionSessionMap.remove(iInjectRecognitionEvent.asBinder());
                if (recognitionSession == null) {
                    return;
                }
                recognitionSession.wrap(new java.util.function.Consumer() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((android.media.soundtrigger.SoundTriggerInstrumentation.RecognitionCallback) obj).onRecognitionStopped();
                    }
                });
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onParamSet(final int i, final int i2, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                android.media.soundtrigger.SoundTriggerInstrumentation.ModelSession modelSession = (android.media.soundtrigger.SoundTriggerInstrumentation.ModelSession) android.media.soundtrigger.SoundTriggerInstrumentation.this.mModelSessionMap.get(iInjectModelEvent.asBinder());
                if (modelSession == null) {
                    return;
                }
                modelSession.wrap(new java.util.function.Consumer() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((android.media.soundtrigger.SoundTriggerInstrumentation.ModelCallback) obj).onParamSet(i, i2);
                    }
                });
            }
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onRestarted(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                if (iInjectGlobalEvent.asBinder() != android.media.soundtrigger.SoundTriggerInstrumentation.this.mInjectGlobalEvent.asBinder()) {
                    return;
                }
                android.media.soundtrigger.SoundTriggerInstrumentation.this.mRecognitionSessionMap.clear();
                android.media.soundtrigger.SoundTriggerInstrumentation.this.mModelSessionMap.clear();
                android.media.soundtrigger.SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.soundtrigger.SoundTriggerInstrumentation.Injection.this.lambda$onRestarted$5();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRestarted$5() {
            android.media.soundtrigger.SoundTriggerInstrumentation.this.mClientCallback.onRestarted();
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onFrameworkDetached(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                if (iInjectGlobalEvent.asBinder() != android.media.soundtrigger.SoundTriggerInstrumentation.this.mInjectGlobalEvent.asBinder()) {
                    return;
                }
                android.media.soundtrigger.SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.soundtrigger.SoundTriggerInstrumentation.Injection.this.lambda$onFrameworkDetached$6();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameworkDetached$6() {
            android.media.soundtrigger.SoundTriggerInstrumentation.this.mClientCallback.onFrameworkDetached();
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onClientAttached(android.os.IBinder iBinder, android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                if (iInjectGlobalEvent.asBinder() != android.media.soundtrigger.SoundTriggerInstrumentation.this.mInjectGlobalEvent.asBinder()) {
                    return;
                }
                android.media.soundtrigger.SoundTriggerInstrumentation.this.mClientToken = iBinder;
                android.media.soundtrigger.SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.soundtrigger.SoundTriggerInstrumentation.Injection.this.lambda$onClientAttached$7();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClientAttached$7() {
            android.media.soundtrigger.SoundTriggerInstrumentation.this.mClientCallback.onClientAttached();
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onClientDetached(android.os.IBinder iBinder) {
            synchronized (android.media.soundtrigger.SoundTriggerInstrumentation.this.mLock) {
                if (iBinder != android.media.soundtrigger.SoundTriggerInstrumentation.this.mClientToken) {
                    return;
                }
                android.media.soundtrigger.SoundTriggerInstrumentation.this.mClientToken = null;
                android.media.soundtrigger.SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.soundtrigger.SoundTriggerInstrumentation.Injection.this.lambda$onClientDetached$8();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClientDetached$8() {
            android.media.soundtrigger.SoundTriggerInstrumentation.this.mClientCallback.onClientDetached();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreempted$9() {
            android.media.soundtrigger.SoundTriggerInstrumentation.this.mClientCallback.onPreempted();
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
        public void onPreempted() {
            android.media.soundtrigger.SoundTriggerInstrumentation.this.mGlobalCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation$Injection$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.soundtrigger.SoundTriggerInstrumentation.Injection.this.lambda$onPreempted$9();
                }
            });
        }
    }

    public SoundTriggerInstrumentation(com.android.internal.app.ISoundTriggerService iSoundTriggerService, java.util.concurrent.Executor executor, android.media.soundtrigger.SoundTriggerInstrumentation.GlobalCallback globalCallback) {
        this.mClientCallback = (android.media.soundtrigger.SoundTriggerInstrumentation.GlobalCallback) java.util.Objects.requireNonNull(globalCallback);
        this.mGlobalCallbackExecutor = (java.util.concurrent.Executor) java.util.Objects.requireNonNull(executor);
        this.mService = iSoundTriggerService;
        try {
            iSoundTriggerService.attachInjection(new android.media.soundtrigger.SoundTriggerInstrumentation.Injection());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void triggerRestart() {
        synchronized (this.mLock) {
            if (this.mInjectGlobalEvent == null) {
                throw new java.lang.IllegalStateException("Attempted to trigger HAL restart before registration");
            }
            try {
                this.mInjectGlobalEvent.triggerRestart();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void triggerOnResourcesAvailable() {
        synchronized (this.mLock) {
            if (this.mInjectGlobalEvent == null) {
                throw new java.lang.IllegalStateException("Attempted to trigger HAL resources available before registration");
            }
            try {
                this.mInjectGlobalEvent.triggerOnResourcesAvailable();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setResourceContention(boolean z) {
        synchronized (this.mLock) {
            if (this.mInjectGlobalEvent == null) {
                throw new java.lang.IllegalStateException("Injection interface not set up");
            }
            android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent = this.mInjectGlobalEvent;
            final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
            try {
                iInjectGlobalEvent.setResourceContention(z, new android.media.soundtrigger_middleware.IAcknowledgeEvent.Stub() { // from class: android.media.soundtrigger.SoundTriggerInstrumentation.1
                    @Override // android.media.soundtrigger_middleware.IAcknowledgeEvent
                    public void eventReceived() {
                        countDownLatch.countDown();
                    }
                });
                try {
                    countDownLatch.await();
                } catch (java.lang.InterruptedException e) {
                    throw new java.lang.RuntimeException(e);
                }
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
    }

    public void setInPhoneCallState(boolean z) {
        try {
            this.mService.setInPhoneCallState(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
