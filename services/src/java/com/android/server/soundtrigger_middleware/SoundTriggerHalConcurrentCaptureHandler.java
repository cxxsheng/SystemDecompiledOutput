package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class SoundTriggerHalConcurrentCaptureHandler implements com.android.server.soundtrigger_middleware.ISoundTriggerHal, com.android.server.soundtrigger_middleware.ICaptureStateNotifier.Listener {
    private boolean mCaptureState;

    @android.annotation.NonNull
    private final com.android.server.soundtrigger_middleware.ISoundTriggerHal mDelegate;
    private com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback mGlobalCallback;

    @android.annotation.NonNull
    private final com.android.server.soundtrigger_middleware.ICaptureStateNotifier mNotifier;

    @android.annotation.NonNull
    private final java.lang.Object mStartStopLock = new java.lang.Object();

    @android.annotation.NonNull
    private final java.util.Map<java.lang.Integer, com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.LoadedModel> mLoadedModels = new java.util.concurrent.ConcurrentHashMap();

    @android.annotation.NonNull
    private final java.util.Set<java.lang.Integer> mActiveModels = new java.util.HashSet();

    @android.annotation.NonNull
    private final java.util.Map<android.os.IBinder.DeathRecipient, android.os.IBinder.DeathRecipient> mDeathRecipientMap = new java.util.concurrent.ConcurrentHashMap();

    @android.annotation.NonNull
    private final com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.CallbackThread mCallbackThread = new com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.CallbackThread();

    /* JADX INFO: Access modifiers changed from: private */
    static class LoadedModel {

        @android.annotation.NonNull
        public final com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback callback;
        public final int type;

        LoadedModel(int i, @android.annotation.NonNull com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
            this.type = i;
            this.callback = modelCallback;
        }
    }

    public SoundTriggerHalConcurrentCaptureHandler(@android.annotation.NonNull com.android.server.soundtrigger_middleware.ISoundTriggerHal iSoundTriggerHal, @android.annotation.NonNull com.android.server.soundtrigger_middleware.ICaptureStateNotifier iCaptureStateNotifier) {
        this.mDelegate = iSoundTriggerHal;
        this.mNotifier = iCaptureStateNotifier;
        this.mCaptureState = this.mNotifier.registerListener(this);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void startRecognition(int i, int i2, int i3, android.media.soundtrigger.RecognitionConfig recognitionConfig) {
        synchronized (this.mStartStopLock) {
            synchronized (this.mActiveModels) {
                if (this.mCaptureState) {
                    throw new com.android.server.soundtrigger_middleware.RecoverableException(1);
                }
                this.mDelegate.startRecognition(i, i2, i3, recognitionConfig);
                this.mActiveModels.add(java.lang.Integer.valueOf(i));
            }
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void stopRecognition(int i) {
        boolean remove;
        synchronized (this.mStartStopLock) {
            try {
                synchronized (this.mActiveModels) {
                    remove = this.mActiveModels.remove(java.lang.Integer.valueOf(i));
                }
                if (remove) {
                    this.mDelegate.stopRecognition(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mCallbackThread.flush();
    }

    @Override // com.android.server.soundtrigger_middleware.ICaptureStateNotifier.Listener
    public void onCaptureStateChange(boolean z) {
        synchronized (this.mStartStopLock) {
            try {
                if (z) {
                    abortAllActiveModels();
                } else if (this.mGlobalCallback != null) {
                    this.mGlobalCallback.onResourcesAvailable();
                }
                this.mCaptureState = z;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void abortAllActiveModels() {
        final int intValue;
        while (true) {
            synchronized (this.mActiveModels) {
                try {
                    java.util.Iterator<java.lang.Integer> it = this.mActiveModels.iterator();
                    if (!it.hasNext()) {
                        return;
                    }
                    intValue = it.next().intValue();
                    this.mActiveModels.remove(java.lang.Integer.valueOf(intValue));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mDelegate.stopRecognition(intValue);
            final com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.LoadedModel loadedModel = this.mLoadedModels.get(java.lang.Integer.valueOf(intValue));
            this.mCallbackThread.push(new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.notifyAbort(intValue, loadedModel);
                }
            });
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadSoundModel(android.media.soundtrigger.SoundModel soundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
        int loadSoundModel = this.mDelegate.loadSoundModel(soundModel, new com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.CallbackWrapper(modelCallback));
        this.mLoadedModels.put(java.lang.Integer.valueOf(loadSoundModel), new com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.LoadedModel(1, modelCallback));
        return loadSoundModel;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadPhraseSoundModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
        int loadPhraseSoundModel = this.mDelegate.loadPhraseSoundModel(phraseSoundModel, new com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.CallbackWrapper(modelCallback));
        this.mLoadedModels.put(java.lang.Integer.valueOf(loadPhraseSoundModel), new com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.LoadedModel(0, modelCallback));
        return loadPhraseSoundModel;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void unloadSoundModel(int i) {
        this.mLoadedModels.remove(java.lang.Integer.valueOf(i));
        this.mDelegate.unloadSoundModel(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerCallback$1(final com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback globalCallback) {
        com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.CallbackThread callbackThread = this.mCallbackThread;
        java.util.Objects.requireNonNull(globalCallback);
        callbackThread.push(new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback.this.onResourcesAvailable();
            }
        });
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void registerCallback(final com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback globalCallback) {
        this.mGlobalCallback = new com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda1
            @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback
            public final void onResourcesAvailable() {
                com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.this.lambda$registerCallback$1(globalCallback);
            }
        };
        this.mDelegate.registerCallback(this.mGlobalCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$linkToDeath$2(final android.os.IBinder.DeathRecipient deathRecipient) {
        com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.CallbackThread callbackThread = this.mCallbackThread;
        java.util.Objects.requireNonNull(deathRecipient);
        callbackThread.push(new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                deathRecipient.binderDied();
            }
        });
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void linkToDeath(final android.os.IBinder.DeathRecipient deathRecipient) {
        android.os.IBinder.DeathRecipient deathRecipient2 = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda3
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.this.lambda$linkToDeath$2(deathRecipient);
            }
        };
        this.mDelegate.linkToDeath(deathRecipient2);
        this.mDeathRecipientMap.put(deathRecipient, deathRecipient2);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void unlinkToDeath(android.os.IBinder.DeathRecipient deathRecipient) {
        this.mDelegate.unlinkToDeath(this.mDeathRecipientMap.remove(deathRecipient));
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CallbackWrapper implements com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback {

        @android.annotation.NonNull
        private final com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback mDelegateCallback;

        private CallbackWrapper(@android.annotation.NonNull com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
            this.mDelegateCallback = modelCallback;
        }

        @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
        public void recognitionCallback(final int i, final android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys) {
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.this.mActiveModels) {
                try {
                    if (com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.this.mActiveModels.contains(java.lang.Integer.valueOf(i))) {
                        if (!recognitionEventSys.recognitionEvent.recognitionStillActive) {
                            com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.this.mActiveModels.remove(java.lang.Integer.valueOf(i));
                        }
                        com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.this.mCallbackThread.push(new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler$CallbackWrapper$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.CallbackWrapper.this.lambda$recognitionCallback$0(i, recognitionEventSys);
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$recognitionCallback$0(int i, android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys) {
            this.mDelegateCallback.recognitionCallback(i, recognitionEventSys);
        }

        @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
        public void phraseRecognitionCallback(final int i, final android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys) {
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.this.mActiveModels) {
                try {
                    if (com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.this.mActiveModels.contains(java.lang.Integer.valueOf(i))) {
                        if (!phraseRecognitionEventSys.phraseRecognitionEvent.common.recognitionStillActive) {
                            com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.this.mActiveModels.remove(java.lang.Integer.valueOf(i));
                        }
                        com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.this.mCallbackThread.push(new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler$CallbackWrapper$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.CallbackWrapper.this.lambda$phraseRecognitionCallback$1(i, phraseRecognitionEventSys);
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$phraseRecognitionCallback$1(int i, android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys) {
            this.mDelegateCallback.phraseRecognitionCallback(i, phraseRecognitionEventSys);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$modelUnloaded$2(int i) {
            this.mDelegateCallback.modelUnloaded(i);
        }

        @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
        public void modelUnloaded(final int i) {
            com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.this.mCallbackThread.push(new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler$CallbackWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.CallbackWrapper.this.lambda$modelUnloaded$2(i);
                }
            });
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void flushCallbacks() {
        this.mDelegate.flushCallbacks();
        this.mCallbackThread.flush();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void clientAttached(android.os.IBinder iBinder) {
        this.mDelegate.clientAttached(iBinder);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void clientDetached(android.os.IBinder iBinder) {
        this.mDelegate.clientDetached(iBinder);
    }

    private static class CallbackThread implements java.lang.Runnable {
        private final java.util.Queue<java.lang.Runnable> mList = new java.util.LinkedList();
        private int mPushCount = 0;
        private int mProcessedCount = 0;
        private boolean mQuitting = false;
        private final java.lang.Thread mThread = new java.lang.Thread(this, "STHAL Concurrent Capture Handler Callback");

        CallbackThread() {
            this.mThread.start();
        }

        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    java.lang.Runnable pop = pop();
                    if (pop == null) {
                        return;
                    }
                    pop.run();
                    synchronized (this.mList) {
                        this.mProcessedCount++;
                        this.mList.notifyAll();
                    }
                } catch (java.lang.InterruptedException e) {
                    return;
                }
            }
        }

        boolean push(java.lang.Runnable runnable) {
            synchronized (this.mList) {
                try {
                    if (this.mQuitting) {
                        return false;
                    }
                    this.mList.add(runnable);
                    this.mPushCount++;
                    this.mList.notifyAll();
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void flush() {
            try {
                synchronized (this.mList) {
                    try {
                        int i = this.mPushCount;
                        while (this.mProcessedCount != i) {
                            this.mList.wait();
                        }
                    } finally {
                    }
                }
            } catch (java.lang.InterruptedException e) {
            }
        }

        void quit() {
            synchronized (this.mList) {
                this.mQuitting = true;
                this.mList.notifyAll();
            }
        }

        @android.annotation.Nullable
        private java.lang.Runnable pop() throws java.lang.InterruptedException {
            synchronized (this.mList) {
                while (this.mList.isEmpty() && !this.mQuitting) {
                    try {
                        this.mList.wait();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (this.mList.isEmpty() && this.mQuitting) {
                    return null;
                }
                return this.mList.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifyAbort(int i, com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler.LoadedModel loadedModel) {
        switch (loadedModel.type) {
            case 0:
                loadedModel.callback.phraseRecognitionCallback(i, com.android.server.soundtrigger_middleware.AidlUtil.newAbortPhraseEvent());
                break;
            case 1:
                loadedModel.callback.recognitionCallback(i, com.android.server.soundtrigger_middleware.AidlUtil.newAbortEvent());
                break;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void detach() {
        this.mDelegate.detach();
        this.mNotifier.unregisterListener(this);
        this.mCallbackThread.quit();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void reboot() {
        this.mDelegate.reboot();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public android.media.soundtrigger.Properties getProperties() {
        return this.mDelegate.getProperties();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void forceRecognitionEvent(int i) {
        this.mDelegate.forceRecognitionEvent(i);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int getModelParameter(int i, int i2) {
        return this.mDelegate.getModelParameter(i, i2);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void setModelParameter(int i, int i2, int i3) {
        this.mDelegate.setModelParameter(i, i2, i3);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public android.media.soundtrigger.ModelParameterRange queryParameter(int i, int i2) {
        return this.mDelegate.queryParameter(i, i2);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public java.lang.String interfaceDescriptor() {
        return this.mDelegate.interfaceDescriptor();
    }
}
