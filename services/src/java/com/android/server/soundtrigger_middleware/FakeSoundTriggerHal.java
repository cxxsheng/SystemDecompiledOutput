package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class FakeSoundTriggerHal extends android.hardware.soundtrigger3.ISoundTriggerHw.Stub {
    private static final java.lang.String TAG = "FakeSoundTriggerHal";
    private static final int THRESHOLD_MAX = 10;
    private static final int THRESHOLD_MIN = -10;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.os.IBinder.DeathRecipient mDeathRecipient;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.GlobalCallbackDispatcher mGlobalCallbackDispatcher;
    private final com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.InjectionDispatcher mInjectionDispatcher;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsResourceContended = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<java.lang.Integer, com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession> mModelSessionMap = new java.util.HashMap();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mModelKeyCounter = 101;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsDead = false;
    private final android.media.soundtrigger.Properties mProperties = createDefaultProperties();
    private final android.media.soundtrigger_middleware.IInjectGlobalEvent.Stub mGlobalEventSession = new com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.AnonymousClass1();

    static class ExecutorHolder {
        static final java.util.concurrent.Executor CALLBACK_EXECUTOR = java.util.concurrent.Executors.newSingleThreadExecutor();
        static final java.util.concurrent.Executor INJECTION_EXECUTOR = java.util.concurrent.Executors.newSingleThreadExecutor();

        ExecutorHolder() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ModelSession extends android.media.soundtrigger_middleware.IInjectModelEvent.Stub {
        private final com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.CallbackDispatcher mCallbackDispatcher;
        private final boolean mIsKeyphrase;

        @com.android.internal.annotations.GuardedBy({"FakeSoundTriggerHal.this.mLock"})
        private boolean mIsUnloaded;
        private final int mModelHandle;

        @com.android.internal.annotations.GuardedBy({"FakeSoundTriggerHal.this.mLock"})
        @android.annotation.Nullable
        private com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession mRecognitionSession;

        @com.android.internal.annotations.GuardedBy({"FakeSoundTriggerHal.this.mLock"})
        private int mThreshold;

        private ModelSession(int i, com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.CallbackDispatcher callbackDispatcher, boolean z) {
            this.mThreshold = 0;
            this.mIsUnloaded = false;
            this.mModelHandle = i;
            this.mCallbackDispatcher = callbackDispatcher;
            this.mIsKeyphrase = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession startRecognitionForModel() {
            com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession recognitionSession;
            synchronized (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mLock) {
                this.mRecognitionSession = new com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession();
                recognitionSession = this.mRecognitionSession;
            }
            return recognitionSession;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession stopRecognitionForModel() {
            com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession recognitionSession;
            synchronized (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mLock) {
                recognitionSession = this.mRecognitionSession;
                this.mRecognitionSession = null;
            }
            return recognitionSession;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void forceRecognitionForModel() {
            synchronized (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mLock) {
                try {
                    if (this.mIsKeyphrase) {
                        final android.media.soundtrigger.PhraseRecognitionEvent createDefaultKeyphraseEvent = com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.createDefaultKeyphraseEvent(3);
                        this.mCallbackDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda2
                            public final void acceptOrThrow(java.lang.Object obj) {
                                com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.lambda$forceRecognitionForModel$0(createDefaultKeyphraseEvent, (android.hardware.soundtrigger3.ISoundTriggerHwCallback) obj);
                            }
                        });
                    } else {
                        final android.media.soundtrigger.RecognitionEvent createDefaultEvent = com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.createDefaultEvent(3);
                        this.mCallbackDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda3
                            public final void acceptOrThrow(java.lang.Object obj) {
                                com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.lambda$forceRecognitionForModel$1(createDefaultEvent, (android.hardware.soundtrigger3.ISoundTriggerHwCallback) obj);
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$forceRecognitionForModel$0(android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent, android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws java.lang.Exception {
            iSoundTriggerHwCallback.phraseRecognitionCallback(this.mModelHandle, phraseRecognitionEvent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$forceRecognitionForModel$1(android.media.soundtrigger.RecognitionEvent recognitionEvent, android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws java.lang.Exception {
            iSoundTriggerHwCallback.recognitionCallback(this.mModelHandle, recognitionEvent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setThresholdFactor(int i) {
            synchronized (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mLock) {
                this.mThreshold = i;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getThresholdFactor() {
            int i;
            synchronized (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mLock) {
                i = this.mThreshold;
            }
            return i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean getIsUnloaded() {
            boolean z;
            synchronized (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mLock) {
                z = this.mIsUnloaded;
            }
            return z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession getRecogSession() {
            com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession recognitionSession;
            synchronized (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mLock) {
                recognitionSession = this.mRecognitionSession;
            }
            return recognitionSession;
        }

        public void triggerUnloadModel() {
            synchronized (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mLock) {
                try {
                    if (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mIsDead || this.mIsUnloaded) {
                        return;
                    }
                    if (this.mRecognitionSession != null) {
                        this.mRecognitionSession.triggerAbortRecognition();
                    }
                    this.mIsUnloaded = true;
                    this.mCallbackDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda0
                        public final void acceptOrThrow(java.lang.Object obj) {
                            com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.lambda$triggerUnloadModel$2((android.hardware.soundtrigger3.ISoundTriggerHwCallback) obj);
                        }
                    });
                    if (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.getNumLoadedModelsLocked() == com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mProperties.maxSoundModels - 1 && !com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mIsResourceContended) {
                        com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mGlobalCallbackDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda1
                            public final void acceptOrThrow(java.lang.Object obj) {
                                ((android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback) obj).onResourcesAvailable();
                            }
                        });
                    }
                } finally {
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerUnloadModel$2(android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws java.lang.Exception {
            iSoundTriggerHwCallback.modelUnloaded(this.mModelHandle);
        }

        /* JADX INFO: Access modifiers changed from: private */
        class RecognitionSession extends android.media.soundtrigger_middleware.IInjectRecognitionEvent.Stub {
            private RecognitionSession() {
            }

            public void triggerRecognitionEvent(byte[] bArr, @android.annotation.Nullable android.media.soundtrigger.PhraseRecognitionExtra[] phraseRecognitionExtraArr) {
                synchronized (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mLock) {
                    try {
                        if (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mIsDead || com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.mRecognitionSession != this) {
                            return;
                        }
                        com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.mRecognitionSession = null;
                        if (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.mIsKeyphrase) {
                            final android.media.soundtrigger.PhraseRecognitionEvent createDefaultKeyphraseEvent = com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.createDefaultKeyphraseEvent(0);
                            createDefaultKeyphraseEvent.common.data = bArr;
                            if (phraseRecognitionExtraArr != null) {
                                createDefaultKeyphraseEvent.phraseExtras = phraseRecognitionExtraArr;
                            }
                            com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.mCallbackDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$RecognitionSession$$ExternalSyntheticLambda2
                                public final void acceptOrThrow(java.lang.Object obj) {
                                    com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession.this.lambda$triggerRecognitionEvent$0(createDefaultKeyphraseEvent, (android.hardware.soundtrigger3.ISoundTriggerHwCallback) obj);
                                }
                            });
                        } else {
                            final android.media.soundtrigger.RecognitionEvent createDefaultEvent = com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.createDefaultEvent(0);
                            createDefaultEvent.data = bArr;
                            com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.mCallbackDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$RecognitionSession$$ExternalSyntheticLambda3
                                public final void acceptOrThrow(java.lang.Object obj) {
                                    com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession.this.lambda$triggerRecognitionEvent$1(createDefaultEvent, (android.hardware.soundtrigger3.ISoundTriggerHwCallback) obj);
                                }
                            });
                        }
                    } finally {
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$triggerRecognitionEvent$0(android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent, android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws java.lang.Exception {
                iSoundTriggerHwCallback.phraseRecognitionCallback(com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.mModelHandle, phraseRecognitionEvent);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$triggerRecognitionEvent$1(android.media.soundtrigger.RecognitionEvent recognitionEvent, android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws java.lang.Exception {
                iSoundTriggerHwCallback.recognitionCallback(com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.mModelHandle, recognitionEvent);
            }

            public void triggerAbortRecognition() {
                synchronized (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mLock) {
                    try {
                        if (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mIsDead || com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.mRecognitionSession != this) {
                            return;
                        }
                        com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.mRecognitionSession = null;
                        if (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.mIsKeyphrase) {
                            com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.mCallbackDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$RecognitionSession$$ExternalSyntheticLambda0
                                public final void acceptOrThrow(java.lang.Object obj) {
                                    com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession.this.lambda$triggerAbortRecognition$2((android.hardware.soundtrigger3.ISoundTriggerHwCallback) obj);
                                }
                            });
                        } else {
                            com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.mCallbackDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$ModelSession$RecognitionSession$$ExternalSyntheticLambda1
                                public final void acceptOrThrow(java.lang.Object obj) {
                                    com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession.this.lambda$triggerAbortRecognition$3((android.hardware.soundtrigger3.ISoundTriggerHwCallback) obj);
                                }
                            });
                        }
                    } finally {
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$triggerAbortRecognition$2(android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws java.lang.Exception {
                iSoundTriggerHwCallback.phraseRecognitionCallback(com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.mModelHandle, com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.createDefaultKeyphraseEvent(1));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$triggerAbortRecognition$3(android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws java.lang.Exception {
                iSoundTriggerHwCallback.recognitionCallback(com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this.mModelHandle, com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.createDefaultEvent(1));
            }
        }
    }

    public FakeSoundTriggerHal(android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) {
        this.mGlobalCallbackDispatcher = null;
        this.mInjectionDispatcher = new com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.InjectionDispatcher(iSoundTriggerInjection);
        this.mGlobalCallbackDispatcher = null;
        this.mInjectionDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda0
            public final void acceptOrThrow(java.lang.Object obj) {
                com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.lambda$new$0((android.media.soundtrigger_middleware.ISoundTriggerInjection) obj);
            }
        });
    }

    /* renamed from: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$1, reason: invalid class name */
    class AnonymousClass1 extends android.media.soundtrigger_middleware.IInjectGlobalEvent.Stub {
        AnonymousClass1() {
        }

        public void triggerRestart() {
            synchronized (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mLock) {
                try {
                    if (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mIsDead) {
                        return;
                    }
                    com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mIsDead = true;
                    com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mInjectionDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$1$$ExternalSyntheticLambda2
                        public final void acceptOrThrow(java.lang.Object obj) {
                            com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.AnonymousClass1.this.lambda$triggerRestart$0((android.media.soundtrigger_middleware.ISoundTriggerInjection) obj);
                        }
                    });
                    com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mModelSessionMap.clear();
                    if (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mDeathRecipient != null) {
                        final android.os.IBinder.DeathRecipient deathRecipient = com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mDeathRecipient;
                        com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ExecutorHolder.CALLBACK_EXECUTOR.execute(new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$1$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.AnonymousClass1.this.lambda$triggerRestart$1(deathRecipient);
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerRestart$0(android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) throws java.lang.Exception {
            iSoundTriggerInjection.onRestarted(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerRestart$1(android.os.IBinder.DeathRecipient deathRecipient) {
            try {
                deathRecipient.binderDied(com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.asBinder());
            } catch (java.lang.Throwable th) {
                android.util.Slog.wtf(com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.TAG, "Callback dispatch threw", th);
            }
        }

        public void setResourceContention(boolean z, final android.media.soundtrigger_middleware.IAcknowledgeEvent iAcknowledgeEvent) {
            synchronized (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mLock) {
                try {
                    if (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mIsDead) {
                        return;
                    }
                    boolean z2 = com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mIsResourceContended;
                    com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mIsResourceContended = z;
                    com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mInjectionDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$1$$ExternalSyntheticLambda0
                        public final void acceptOrThrow(java.lang.Object obj) {
                            iAcknowledgeEvent.eventReceived();
                        }
                    });
                    if (!com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mIsResourceContended && z2) {
                        com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mGlobalCallbackDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$1$$ExternalSyntheticLambda1
                            public final void acceptOrThrow(java.lang.Object obj) {
                                ((android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback) obj).onResourcesAvailable();
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void triggerOnResourcesAvailable() {
            synchronized (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mLock) {
                try {
                    if (com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mIsDead) {
                        return;
                    }
                    com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.mGlobalCallbackDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$1$$ExternalSyntheticLambda4
                        public final void acceptOrThrow(java.lang.Object obj) {
                            ((android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback) obj).onResourcesAvailable();
                        }
                    });
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) throws java.lang.Exception {
        iSoundTriggerInjection.registerGlobalEventInjection(this.mGlobalEventSession);
    }

    public android.media.soundtrigger_middleware.IInjectGlobalEvent getGlobalEventInjection() {
        return this.mGlobalEventSession;
    }

    public void linkToDeath(android.os.IBinder.DeathRecipient deathRecipient, int i) {
        synchronized (this.mLock) {
            try {
                if (this.mDeathRecipient != null) {
                    android.util.Slog.wtf(TAG, "Received two death recipients concurrently");
                }
                this.mDeathRecipient = deathRecipient;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean unlinkToDeath(android.os.IBinder.DeathRecipient deathRecipient, int i) {
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    return false;
                }
                if (this.mDeathRecipient != deathRecipient) {
                    throw new java.util.NoSuchElementException();
                }
                this.mDeathRecipient = null;
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.media.soundtrigger.Properties getProperties() throws android.os.RemoteException {
        android.media.soundtrigger.Properties properties;
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new android.os.DeadObjectException();
                }
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    this.mProperties.writeToParcel(obtain, 0);
                    obtain.setDataPosition(0);
                    properties = (android.media.soundtrigger.Properties) android.media.soundtrigger.Properties.CREATOR.createFromParcel(obtain);
                } finally {
                    obtain.recycle();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return properties;
    }

    public void registerGlobalCallback(android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallback) throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new android.os.DeadObjectException();
                }
                this.mGlobalCallbackDispatcher = new com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.GlobalCallbackDispatcher(iSoundTriggerHwGlobalCallback);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int loadSoundModel(final android.media.soundtrigger.SoundModel soundModel, android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws android.os.RemoteException {
        int i;
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new android.os.DeadObjectException();
                }
                if (this.mIsResourceContended || getNumLoadedModelsLocked() == this.mProperties.maxSoundModels) {
                    throw new android.os.ServiceSpecificException(1);
                }
                i = this.mModelKeyCounter;
                this.mModelKeyCounter = i + 1;
                final com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession modelSession = new com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession(i, new com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.CallbackDispatcher(iSoundTriggerHwCallback), false);
                this.mModelSessionMap.put(java.lang.Integer.valueOf(i), modelSession);
                this.mInjectionDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda4
                    public final void acceptOrThrow(java.lang.Object obj) {
                        com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.lambda$loadSoundModel$1(soundModel, modelSession, (android.media.soundtrigger_middleware.ISoundTriggerInjection) obj);
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadSoundModel$1(android.media.soundtrigger.SoundModel soundModel, com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession modelSession, android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) throws java.lang.Exception {
        iSoundTriggerInjection.onSoundModelLoaded(soundModel, (android.media.soundtrigger.Phrase[]) null, modelSession, this.mGlobalEventSession);
    }

    public int loadPhraseSoundModel(final android.media.soundtrigger.PhraseSoundModel phraseSoundModel, android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) throws android.os.RemoteException {
        int i;
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new android.os.DeadObjectException();
                }
                if (this.mIsResourceContended || getNumLoadedModelsLocked() == this.mProperties.maxSoundModels) {
                    throw new android.os.ServiceSpecificException(1);
                }
                i = this.mModelKeyCounter;
                this.mModelKeyCounter = i + 1;
                final com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession modelSession = new com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession(i, new com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.CallbackDispatcher(iSoundTriggerHwCallback), true);
                this.mModelSessionMap.put(java.lang.Integer.valueOf(i), modelSession);
                this.mInjectionDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda5
                    public final void acceptOrThrow(java.lang.Object obj) {
                        com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.this.lambda$loadPhraseSoundModel$2(phraseSoundModel, modelSession, (android.media.soundtrigger_middleware.ISoundTriggerInjection) obj);
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPhraseSoundModel$2(android.media.soundtrigger.PhraseSoundModel phraseSoundModel, com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession modelSession, android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) throws java.lang.Exception {
        iSoundTriggerInjection.onSoundModelLoaded(phraseSoundModel.common, phraseSoundModel.phrases, modelSession, this.mGlobalEventSession);
    }

    public void unloadSoundModel(int i) throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new android.os.DeadObjectException();
                }
                final com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession modelSession = this.mModelSessionMap.get(java.lang.Integer.valueOf(i));
                if (modelSession == null) {
                    android.util.Slog.wtf(TAG, "Attempted to unload model which was never loaded");
                }
                if (modelSession.getRecogSession() != null) {
                    android.util.Slog.wtf(TAG, "Session unloaded before recog stopped!");
                }
                if (modelSession.getIsUnloaded()) {
                    return;
                }
                this.mInjectionDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda1
                    public final void acceptOrThrow(java.lang.Object obj) {
                        ((android.media.soundtrigger_middleware.ISoundTriggerInjection) obj).onSoundModelUnloaded(com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.this);
                    }
                });
                if (getNumLoadedModelsLocked() == this.mProperties.maxSoundModels - 1 && !this.mIsResourceContended) {
                    this.mGlobalCallbackDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda2
                        public final void acceptOrThrow(java.lang.Object obj) {
                            ((android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback) obj).onResourcesAvailable();
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void startRecognition(int i, int i2, int i3, final android.media.soundtrigger.RecognitionConfig recognitionConfig) throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new android.os.DeadObjectException();
                }
                final com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession modelSession = this.mModelSessionMap.get(java.lang.Integer.valueOf(i));
                if (modelSession == null) {
                    android.util.Slog.wtf(TAG, "Attempted to start recognition with invalid handle");
                }
                if (this.mIsResourceContended) {
                    throw new android.os.ServiceSpecificException(1);
                }
                if (modelSession.getIsUnloaded()) {
                    throw new android.os.ServiceSpecificException(1);
                }
                final com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession startRecognitionForModel = modelSession.startRecognitionForModel();
                this.mInjectionDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda6
                    public final void acceptOrThrow(java.lang.Object obj) {
                        ((android.media.soundtrigger_middleware.ISoundTriggerInjection) obj).onRecognitionStarted(-1, recognitionConfig, startRecognitionForModel, modelSession);
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void stopRecognition(int i) throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new android.os.DeadObjectException();
                }
                com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession modelSession = this.mModelSessionMap.get(java.lang.Integer.valueOf(i));
                if (modelSession == null) {
                    android.util.Slog.wtf(TAG, "Attempted to stop recognition with invalid handle");
                }
                final com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession stopRecognitionForModel = modelSession.stopRecognitionForModel();
                if (stopRecognitionForModel != null) {
                    this.mInjectionDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda7
                        public final void acceptOrThrow(java.lang.Object obj) {
                            ((android.media.soundtrigger_middleware.ISoundTriggerInjection) obj).onRecognitionStopped(com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession.RecognitionSession.this);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void forceRecognitionEvent(int i) throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new android.os.DeadObjectException();
                }
                com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession modelSession = this.mModelSessionMap.get(java.lang.Integer.valueOf(i));
                if (modelSession == null) {
                    android.util.Slog.wtf(TAG, "Attempted to force recognition with invalid handle");
                }
                if (modelSession.getRecogSession() == null) {
                    return;
                }
                modelSession.forceRecognitionForModel();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public android.media.soundtrigger.ModelParameterRange queryParameter(int i, int i2) throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new android.os.DeadObjectException();
                }
                if (this.mModelSessionMap.get(java.lang.Integer.valueOf(i)) == null) {
                    android.util.Slog.wtf(TAG, "Attempted to get param with invalid handle");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (i2 == 0) {
            android.media.soundtrigger.ModelParameterRange modelParameterRange = new android.media.soundtrigger.ModelParameterRange();
            modelParameterRange.minInclusive = -10;
            modelParameterRange.maxInclusive = 10;
            return modelParameterRange;
        }
        return null;
    }

    public int getParameter(int i, int i2) throws android.os.RemoteException {
        int thresholdFactor;
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new android.os.DeadObjectException();
                }
                com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession modelSession = this.mModelSessionMap.get(java.lang.Integer.valueOf(i));
                if (modelSession == null) {
                    android.util.Slog.wtf(TAG, "Attempted to get param with invalid handle");
                }
                if (i2 != 0) {
                    throw new java.lang.IllegalArgumentException();
                }
                thresholdFactor = modelSession.getThresholdFactor();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return thresholdFactor;
    }

    public void setParameter(int i, final int i2, final int i3) throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new android.os.DeadObjectException();
                }
                final com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession modelSession = this.mModelSessionMap.get(java.lang.Integer.valueOf(i));
                if (modelSession == null) {
                    android.util.Slog.wtf(TAG, "Attempted to get param with invalid handle");
                }
                if (i2 == 0 || (i3 >= -10 && i3 <= 10)) {
                    modelSession.setThresholdFactor(i3);
                    this.mInjectionDispatcher.wrap(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$$ExternalSyntheticLambda3
                        public final void acceptOrThrow(java.lang.Object obj) {
                            ((android.media.soundtrigger_middleware.ISoundTriggerInjection) obj).onParamSet(i2, i3, modelSession);
                        }
                    });
                } else {
                    throw new java.lang.IllegalArgumentException();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getInterfaceVersion() throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                if (this.mIsDead) {
                    throw new android.os.DeadObjectException();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return 2;
    }

    public java.lang.String getInterfaceHash() throws android.os.RemoteException {
        synchronized (this.mLock) {
            if (this.mIsDead) {
                throw new android.os.DeadObjectException();
            }
        }
        return "6b24e60ad261e3ff56106efd86ce6aa7ef5621b0";
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public int getNumLoadedModelsLocked() {
        java.util.Iterator<com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ModelSession> it = this.mModelSessionMap.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (!it.next().getIsUnloaded()) {
                i++;
            }
        }
        return i;
    }

    private static android.media.soundtrigger.Properties createDefaultProperties() {
        android.media.soundtrigger.Properties properties = new android.media.soundtrigger.Properties();
        properties.implementor = com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME;
        properties.description = "AOSP fake STHAL";
        properties.version = 1;
        properties.uuid = "00000001-0002-0003-0004-deadbeefabcd";
        properties.supportedModelArch = "injection";
        properties.maxSoundModels = 8;
        properties.maxKeyPhrases = 2;
        properties.maxUsers = 2;
        properties.recognitionModes = 9;
        properties.captureTransition = true;
        properties.maxBufferMs = 5000;
        properties.concurrentCapture = true;
        properties.triggerInEvent = false;
        properties.powerConsumptionMw = 0;
        properties.audioCapabilities = 0;
        return properties;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.media.soundtrigger.RecognitionEvent createDefaultEvent(int i) {
        android.media.soundtrigger.RecognitionEvent recognitionEvent = new android.media.soundtrigger.RecognitionEvent();
        recognitionEvent.status = i;
        recognitionEvent.type = 1;
        recognitionEvent.captureAvailable = true;
        recognitionEvent.captureDelayMs = 50;
        recognitionEvent.capturePreambleMs = 200;
        recognitionEvent.triggerInData = false;
        recognitionEvent.audioConfig = null;
        recognitionEvent.data = new byte[0];
        recognitionEvent.recognitionStillActive = false;
        return recognitionEvent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.media.soundtrigger.PhraseRecognitionEvent createDefaultKeyphraseEvent(int i) {
        android.media.soundtrigger.RecognitionEvent createDefaultEvent = createDefaultEvent(i);
        createDefaultEvent.type = 0;
        android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent = new android.media.soundtrigger.PhraseRecognitionEvent();
        phraseRecognitionEvent.common = createDefaultEvent;
        phraseRecognitionEvent.phraseExtras = new android.media.soundtrigger.PhraseRecognitionExtra[0];
        return phraseRecognitionEvent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CallbackDispatcher {
        private final android.hardware.soundtrigger3.ISoundTriggerHwCallback mCallback;

        private CallbackDispatcher(android.hardware.soundtrigger3.ISoundTriggerHwCallback iSoundTriggerHwCallback) {
            this.mCallback = iSoundTriggerHwCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void wrap(final com.android.internal.util.FunctionalUtils.ThrowingConsumer<android.hardware.soundtrigger3.ISoundTriggerHwCallback> throwingConsumer) {
            com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ExecutorHolder.CALLBACK_EXECUTOR.execute(new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$CallbackDispatcher$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.CallbackDispatcher.this.lambda$wrap$0(throwingConsumer);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$wrap$0(com.android.internal.util.FunctionalUtils.ThrowingConsumer throwingConsumer) {
            try {
                throwingConsumer.accept(this.mCallback);
            } catch (java.lang.Throwable th) {
                android.util.Slog.wtf(com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.TAG, "Callback dispatch threw", th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class GlobalCallbackDispatcher {
        private final android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback mCallback;

        private GlobalCallbackDispatcher(android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallback) {
            this.mCallback = iSoundTriggerHwGlobalCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void wrap(final com.android.internal.util.FunctionalUtils.ThrowingConsumer<android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback> throwingConsumer) {
            com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ExecutorHolder.CALLBACK_EXECUTOR.execute(new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$GlobalCallbackDispatcher$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.GlobalCallbackDispatcher.this.lambda$wrap$0(throwingConsumer);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$wrap$0(com.android.internal.util.FunctionalUtils.ThrowingConsumer throwingConsumer) {
            try {
                throwingConsumer.accept(this.mCallback);
            } catch (java.lang.Throwable th) {
                android.util.Slog.wtf(com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.TAG, "Callback dispatch threw", th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class InjectionDispatcher {
        private final android.media.soundtrigger_middleware.ISoundTriggerInjection mInjection;

        private InjectionDispatcher(android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) {
            this.mInjection = iSoundTriggerInjection;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void wrap(final com.android.internal.util.FunctionalUtils.ThrowingConsumer<android.media.soundtrigger_middleware.ISoundTriggerInjection> throwingConsumer) {
            com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ExecutorHolder.INJECTION_EXECUTOR.execute(new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeSoundTriggerHal$InjectionDispatcher$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.InjectionDispatcher.this.lambda$wrap$0(throwingConsumer);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$wrap$0(com.android.internal.util.FunctionalUtils.ThrowingConsumer throwingConsumer) {
            try {
                throwingConsumer.accept(this.mInjection);
            } catch (java.lang.Throwable th) {
                android.util.Slog.wtf(com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.TAG, "Callback dispatch threw", th);
            }
        }
    }
}
