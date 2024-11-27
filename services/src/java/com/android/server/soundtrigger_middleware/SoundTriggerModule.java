package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
class SoundTriggerModule implements android.os.IBinder.DeathRecipient, com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback {
    private static final java.lang.String TAG = "SoundTriggerModule";
    private final java.util.Set<com.android.server.soundtrigger_middleware.SoundTriggerModule.Session> mActiveSessions = new java.util.HashSet();

    @android.annotation.NonNull
    private final com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl.AudioSessionProvider mAudioSessionProvider;

    @android.annotation.NonNull
    private final com.android.server.soundtrigger_middleware.HalFactory mHalFactory;

    @android.annotation.NonNull
    private com.android.server.soundtrigger_middleware.ISoundTriggerHal mHalService;
    private android.media.soundtrigger.Properties mProperties;

    private enum ModelState {
        INIT,
        LOADED,
        ACTIVE
    }

    SoundTriggerModule(@android.annotation.NonNull com.android.server.soundtrigger_middleware.HalFactory halFactory, @android.annotation.NonNull com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl.AudioSessionProvider audioSessionProvider) {
        java.util.Objects.requireNonNull(halFactory);
        this.mHalFactory = halFactory;
        java.util.Objects.requireNonNull(audioSessionProvider);
        this.mAudioSessionProvider = audioSessionProvider;
        attachToHal();
    }

    @android.annotation.NonNull
    synchronized android.media.soundtrigger_middleware.ISoundTriggerModule attach(@android.annotation.NonNull android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback) {
        com.android.server.soundtrigger_middleware.SoundTriggerModule.Session session;
        session = new com.android.server.soundtrigger_middleware.SoundTriggerModule.Session(iSoundTriggerCallback);
        this.mActiveSessions.add(session);
        return session;
    }

    @android.annotation.NonNull
    synchronized android.media.soundtrigger.Properties getProperties() {
        return this.mProperties;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        java.util.ArrayList arrayList;
        android.util.Slog.w(TAG, "Underlying HAL driver died.");
        synchronized (this) {
            try {
                arrayList = new java.util.ArrayList(this.mActiveSessions.size());
                java.util.Iterator<com.android.server.soundtrigger_middleware.SoundTriggerModule.Session> it = this.mActiveSessions.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().moduleDied());
                }
                this.mActiveSessions.clear();
                reset();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        java.util.Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            try {
                ((android.media.soundtrigger_middleware.ISoundTriggerCallback) it2.next()).onModuleDied();
            } catch (android.os.RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    private void reset() {
        this.mHalService.detach();
        attachToHal();
    }

    private void attachToHal() {
        this.mHalService = null;
        while (this.mHalService == null) {
            try {
                this.mHalService = new com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer(new com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog(new com.android.server.soundtrigger_middleware.SoundTriggerDuplicateModelHandler(this.mHalFactory.create())));
            } catch (java.lang.RuntimeException e) {
                if (!(e.getCause() instanceof android.os.RemoteException)) {
                    throw e;
                }
            }
        }
        this.mHalService.linkToDeath(this);
        this.mHalService.registerCallback(this);
        this.mProperties = this.mHalService.getProperties();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeSession(@android.annotation.NonNull com.android.server.soundtrigger_middleware.SoundTriggerModule.Session session) {
        this.mActiveSessions.remove(session);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback
    public void onResourcesAvailable() {
        java.util.ArrayList arrayList;
        synchronized (this) {
            try {
                arrayList = new java.util.ArrayList(this.mActiveSessions.size());
                java.util.Iterator<com.android.server.soundtrigger_middleware.SoundTriggerModule.Session> it = this.mActiveSessions.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().mCallback);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        java.util.Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            try {
                ((android.media.soundtrigger_middleware.ISoundTriggerCallback) it2.next()).onResourcesAvailable();
            } catch (android.os.RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    private class Session implements android.media.soundtrigger_middleware.ISoundTriggerModule {
        private android.media.soundtrigger_middleware.ISoundTriggerCallback mCallback;
        private final java.util.Map<java.lang.Integer, com.android.server.soundtrigger_middleware.SoundTriggerModule.Session.Model> mLoadedModels;
        private final android.os.IBinder mToken;

        private Session(@android.annotation.NonNull android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback) {
            this.mToken = new android.os.Binder();
            this.mLoadedModels = new java.util.HashMap();
            this.mCallback = iSoundTriggerCallback;
            com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mHalService.clientAttached(this.mToken);
        }

        public void detach() {
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                try {
                    if (this.mCallback == null) {
                        return;
                    }
                    com.android.server.soundtrigger_middleware.SoundTriggerModule.this.removeSession(this);
                    this.mCallback = null;
                    com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mHalService.clientDetached(this.mToken);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public int loadModel(@android.annotation.NonNull android.media.soundtrigger.SoundModel soundModel) {
            int load;
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl.AudioSessionProvider.AudioSession acquireSession = com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mAudioSessionProvider.acquireSession();
                try {
                    checkValid();
                    load = new com.android.server.soundtrigger_middleware.SoundTriggerModule.Session.Model().load(soundModel, acquireSession);
                } catch (java.lang.Exception e) {
                    try {
                        com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mAudioSessionProvider.releaseSession(acquireSession.mSessionHandle);
                    } catch (java.lang.Exception e2) {
                        android.util.Slog.e(com.android.server.soundtrigger_middleware.SoundTriggerModule.TAG, "Failed to release session.", e2);
                    }
                    throw e;
                }
            }
            return load;
        }

        public int loadPhraseModel(@android.annotation.NonNull android.media.soundtrigger.PhraseSoundModel phraseSoundModel) {
            int load;
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl.AudioSessionProvider.AudioSession acquireSession = com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mAudioSessionProvider.acquireSession();
                try {
                    checkValid();
                    load = new com.android.server.soundtrigger_middleware.SoundTriggerModule.Session.Model().load(phraseSoundModel, acquireSession);
                    android.util.Slog.d(com.android.server.soundtrigger_middleware.SoundTriggerModule.TAG, java.lang.String.format("loadPhraseModel()->%d", java.lang.Integer.valueOf(load)));
                } catch (java.lang.Exception e) {
                    try {
                        com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mAudioSessionProvider.releaseSession(acquireSession.mSessionHandle);
                    } catch (java.lang.Exception e2) {
                        android.util.Slog.e(com.android.server.soundtrigger_middleware.SoundTriggerModule.TAG, "Failed to release session.", e2);
                    }
                    throw e;
                }
            }
            return load;
        }

        public void unloadModel(int i) {
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                checkValid();
                com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mAudioSessionProvider.releaseSession(this.mLoadedModels.get(java.lang.Integer.valueOf(i)).unload());
            }
        }

        public android.os.IBinder startRecognition(int i, @android.annotation.NonNull android.media.soundtrigger.RecognitionConfig recognitionConfig) {
            android.os.IBinder startRecognition;
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                checkValid();
                startRecognition = this.mLoadedModels.get(java.lang.Integer.valueOf(i)).startRecognition(recognitionConfig);
            }
            return startRecognition;
        }

        public void stopRecognition(int i) {
            com.android.server.soundtrigger_middleware.SoundTriggerModule.Session.Model model;
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                checkValid();
                model = this.mLoadedModels.get(java.lang.Integer.valueOf(i));
            }
            model.stopRecognition();
        }

        public void forceRecognitionEvent(int i) {
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                checkValid();
                this.mLoadedModels.get(java.lang.Integer.valueOf(i)).forceRecognitionEvent();
            }
        }

        public void setModelParameter(int i, int i2, int i3) {
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                checkValid();
                this.mLoadedModels.get(java.lang.Integer.valueOf(i)).setParameter(i2, i3);
            }
        }

        public int getModelParameter(int i, int i2) {
            int parameter;
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                checkValid();
                parameter = this.mLoadedModels.get(java.lang.Integer.valueOf(i)).getParameter(i2);
            }
            return parameter;
        }

        @android.annotation.Nullable
        public android.media.soundtrigger.ModelParameterRange queryModelParameterSupport(int i, int i2) {
            android.media.soundtrigger.ModelParameterRange queryModelParameterSupport;
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                checkValid();
                queryModelParameterSupport = this.mLoadedModels.get(java.lang.Integer.valueOf(i)).queryModelParameterSupport(i2);
            }
            return queryModelParameterSupport;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.media.soundtrigger_middleware.ISoundTriggerCallback moduleDied() {
            android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback = this.mCallback;
            this.mCallback = null;
            return iSoundTriggerCallback;
        }

        private void checkValid() {
            if (this.mCallback == null) {
                throw new com.android.server.soundtrigger_middleware.RecoverableException(4);
            }
        }

        @android.annotation.NonNull
        public android.os.IBinder asBinder() {
            throw new java.lang.UnsupportedOperationException("This implementation is not intended to be used directly with Binder.");
        }

        private class Model implements com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback {
            public int mHandle;
            private boolean mIsStopping;
            private android.os.IBinder mRecognitionToken;
            private com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl.AudioSessionProvider.AudioSession mSession;
            private com.android.server.soundtrigger_middleware.SoundTriggerModule.ModelState mState;

            private Model() {
                this.mState = com.android.server.soundtrigger_middleware.SoundTriggerModule.ModelState.INIT;
                this.mRecognitionToken = null;
                this.mIsStopping = false;
            }

            @android.annotation.NonNull
            private com.android.server.soundtrigger_middleware.SoundTriggerModule.ModelState getState() {
                return this.mState;
            }

            private void setState(@android.annotation.NonNull com.android.server.soundtrigger_middleware.SoundTriggerModule.ModelState modelState) {
                this.mState = modelState;
                com.android.server.soundtrigger_middleware.SoundTriggerModule.this.notifyAll();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public int load(@android.annotation.NonNull android.media.soundtrigger.SoundModel soundModel, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl.AudioSessionProvider.AudioSession audioSession) {
                this.mSession = audioSession;
                this.mHandle = com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mHalService.loadSoundModel(soundModel, this);
                setState(com.android.server.soundtrigger_middleware.SoundTriggerModule.ModelState.LOADED);
                com.android.server.soundtrigger_middleware.SoundTriggerModule.Session.this.mLoadedModels.put(java.lang.Integer.valueOf(this.mHandle), this);
                return this.mHandle;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public int load(@android.annotation.NonNull android.media.soundtrigger.PhraseSoundModel phraseSoundModel, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl.AudioSessionProvider.AudioSession audioSession) {
                this.mSession = audioSession;
                this.mHandle = com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mHalService.loadPhraseSoundModel(phraseSoundModel, this);
                setState(com.android.server.soundtrigger_middleware.SoundTriggerModule.ModelState.LOADED);
                com.android.server.soundtrigger_middleware.SoundTriggerModule.Session.this.mLoadedModels.put(java.lang.Integer.valueOf(this.mHandle), this);
                return this.mHandle;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public int unload() {
                com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mHalService.unloadSoundModel(this.mHandle);
                com.android.server.soundtrigger_middleware.SoundTriggerModule.Session.this.mLoadedModels.remove(java.lang.Integer.valueOf(this.mHandle));
                return this.mSession.mSessionHandle;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public android.os.IBinder startRecognition(@android.annotation.NonNull android.media.soundtrigger.RecognitionConfig recognitionConfig) {
                if (this.mIsStopping) {
                    throw new com.android.server.soundtrigger_middleware.RecoverableException(5, "Race occurred");
                }
                com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mHalService.startRecognition(this.mHandle, this.mSession.mDeviceHandle, this.mSession.mIoHandle, recognitionConfig);
                this.mRecognitionToken = new android.os.Binder();
                setState(com.android.server.soundtrigger_middleware.SoundTriggerModule.ModelState.ACTIVE);
                return this.mRecognitionToken;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void stopRecognition() {
                synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                    try {
                        if (getState() == com.android.server.soundtrigger_middleware.SoundTriggerModule.ModelState.LOADED) {
                            return;
                        }
                        this.mRecognitionToken = null;
                        this.mIsStopping = true;
                        com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mHalService.stopRecognition(this.mHandle);
                        synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                            this.mIsStopping = false;
                            setState(com.android.server.soundtrigger_middleware.SoundTriggerModule.ModelState.LOADED);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void forceRecognitionEvent() {
                if (getState() != com.android.server.soundtrigger_middleware.SoundTriggerModule.ModelState.ACTIVE) {
                    return;
                }
                com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mHalService.forceRecognitionEvent(this.mHandle);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void setParameter(int i, int i2) {
                com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mHalService.setModelParameter(this.mHandle, com.android.server.soundtrigger_middleware.ConversionUtil.aidl2hidlModelParameter(i), i2);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public int getParameter(int i) {
                return com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mHalService.getModelParameter(this.mHandle, com.android.server.soundtrigger_middleware.ConversionUtil.aidl2hidlModelParameter(i));
            }

            /* JADX INFO: Access modifiers changed from: private */
            @android.annotation.Nullable
            public android.media.soundtrigger.ModelParameterRange queryModelParameterSupport(int i) {
                return com.android.server.soundtrigger_middleware.SoundTriggerModule.this.mHalService.queryParameter(this.mHandle, i);
            }

            @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
            public void recognitionCallback(int i, @android.annotation.NonNull android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys) {
                synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                    try {
                        if (this.mRecognitionToken == null) {
                            return;
                        }
                        recognitionEventSys.token = this.mRecognitionToken;
                        if (!recognitionEventSys.recognitionEvent.recognitionStillActive) {
                            setState(com.android.server.soundtrigger_middleware.SoundTriggerModule.ModelState.LOADED);
                            this.mRecognitionToken = null;
                        }
                        android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback = com.android.server.soundtrigger_middleware.SoundTriggerModule.Session.this.mCallback;
                        if (iSoundTriggerCallback != null) {
                            try {
                                iSoundTriggerCallback.onRecognition(this.mHandle, recognitionEventSys, this.mSession.mSessionHandle);
                            } catch (android.os.RemoteException e) {
                                throw e.rethrowAsRuntimeException();
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
            public void phraseRecognitionCallback(int i, @android.annotation.NonNull android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys) {
                synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                    try {
                        if (this.mRecognitionToken == null) {
                            return;
                        }
                        phraseRecognitionEventSys.token = this.mRecognitionToken;
                        if (!phraseRecognitionEventSys.phraseRecognitionEvent.common.recognitionStillActive) {
                            setState(com.android.server.soundtrigger_middleware.SoundTriggerModule.ModelState.LOADED);
                            this.mRecognitionToken = null;
                        }
                        android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback = com.android.server.soundtrigger_middleware.SoundTriggerModule.Session.this.mCallback;
                        if (iSoundTriggerCallback != null) {
                            try {
                                com.android.server.soundtrigger_middleware.SoundTriggerModule.Session.this.mCallback.onPhraseRecognition(this.mHandle, phraseRecognitionEventSys, this.mSession.mSessionHandle);
                            } catch (android.os.RemoteException e) {
                                throw e.rethrowAsRuntimeException();
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
            public void modelUnloaded(int i) {
                android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback;
                synchronized (com.android.server.soundtrigger_middleware.SoundTriggerModule.this) {
                    iSoundTriggerCallback = com.android.server.soundtrigger_middleware.SoundTriggerModule.Session.this.mCallback;
                }
                if (iSoundTriggerCallback != null) {
                    try {
                        iSoundTriggerCallback.onModelUnloaded(i);
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowAsRuntimeException();
                    }
                }
            }
        }
    }
}
