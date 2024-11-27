package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class SoundTriggerMiddlewareValidation implements com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal, com.android.server.soundtrigger_middleware.Dumpable {
    private static final java.lang.String TAG = "SoundTriggerMiddlewareValidation";

    @android.annotation.NonNull
    private final com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal mDelegate;
    private java.util.Map<java.lang.Integer, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleState> mModules;

    private enum ModuleStatus {
        ALIVE,
        DETACHED,
        DEAD
    }

    private class ModuleState {

        @android.annotation.NonNull
        public android.media.soundtrigger.Properties properties;
        public java.util.Set<com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session> sessions;

        private ModuleState(@android.annotation.NonNull android.media.soundtrigger.Properties properties) {
            this.sessions = new java.util.HashSet();
            this.properties = properties;
        }
    }

    public SoundTriggerMiddlewareValidation(@android.annotation.NonNull com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal) {
        this.mDelegate = iSoundTriggerMiddlewareInternal;
    }

    @android.annotation.NonNull
    static java.lang.RuntimeException handleException(@android.annotation.NonNull java.lang.Exception exc) {
        if (exc instanceof com.android.server.soundtrigger_middleware.RecoverableException) {
            throw new android.os.ServiceSpecificException(((com.android.server.soundtrigger_middleware.RecoverableException) exc).errorCode, exc.getMessage());
        }
        android.util.Slog.wtf(TAG, "Unexpected exception", exc);
        throw new android.os.ServiceSpecificException(5, exc.getMessage());
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    @android.annotation.NonNull
    public android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModules() {
        android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModules;
        synchronized (this) {
            try {
                try {
                    listModules = this.mDelegate.listModules();
                    int i = 0;
                    if (this.mModules == null) {
                        this.mModules = new java.util.HashMap(listModules.length);
                        int length = listModules.length;
                        while (i < length) {
                            android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor soundTriggerModuleDescriptor = listModules[i];
                            this.mModules.put(java.lang.Integer.valueOf(soundTriggerModuleDescriptor.handle), new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleState(soundTriggerModuleDescriptor.properties));
                            i++;
                        }
                    } else {
                        if (listModules.length != this.mModules.size()) {
                            throw new java.lang.RuntimeException("listModules must always return the same result.");
                        }
                        int length2 = listModules.length;
                        while (i < length2) {
                            android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor soundTriggerModuleDescriptor2 = listModules[i];
                            if (!this.mModules.containsKey(java.lang.Integer.valueOf(soundTriggerModuleDescriptor2.handle))) {
                                throw new java.lang.RuntimeException("listModules must always return the same result.");
                            }
                            this.mModules.get(java.lang.Integer.valueOf(soundTriggerModuleDescriptor2.handle)).properties = soundTriggerModuleDescriptor2.properties;
                            i++;
                        }
                    }
                } catch (java.lang.Exception e) {
                    throw handleException(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return listModules;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    @android.annotation.NonNull
    public android.media.soundtrigger_middleware.ISoundTriggerModule attach(int i, @android.annotation.NonNull android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
        com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session session;
        java.util.Objects.requireNonNull(iSoundTriggerCallback);
        java.util.Objects.requireNonNull(iSoundTriggerCallback.asBinder());
        synchronized (this) {
            try {
                if (this.mModules == null) {
                    throw new java.lang.IllegalStateException("Client must call listModules() prior to attaching.");
                }
                if (!this.mModules.containsKey(java.lang.Integer.valueOf(i))) {
                    throw new java.lang.IllegalArgumentException("Invalid handle: " + i);
                }
                try {
                    session = new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session(i, iSoundTriggerCallback);
                    session.attach(this.mDelegate.attach(i, session.getCallbackWrapper(), z));
                } catch (java.lang.Exception e) {
                    throw handleException(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return session;
    }

    public java.lang.String toString() {
        return this.mDelegate.toString();
    }

    @Override // com.android.server.soundtrigger_middleware.Dumpable
    public void dump(java.io.PrintWriter printWriter) {
        synchronized (this) {
            try {
                if (this.mModules != null) {
                    java.util.Iterator<java.lang.Integer> it = this.mModules.keySet().iterator();
                    while (it.hasNext()) {
                        int intValue = it.next().intValue();
                        com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleState moduleState = this.mModules.get(java.lang.Integer.valueOf(intValue));
                        printWriter.println("=========================================");
                        printWriter.printf("Module %d\n%s\n", java.lang.Integer.valueOf(intValue), com.android.server.soundtrigger_middleware.ObjectPrinter.print(moduleState.properties, 16));
                        printWriter.println("=========================================");
                        java.util.Iterator<com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session> it2 = moduleState.sessions.iterator();
                        while (it2.hasNext()) {
                            it2.next().dump(printWriter);
                        }
                    }
                } else {
                    printWriter.println("Modules have not yet been enumerated.");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        printWriter.println();
        if (this.mDelegate instanceof com.android.server.soundtrigger_middleware.Dumpable) {
            ((com.android.server.soundtrigger_middleware.Dumpable) this.mDelegate).dump(printWriter);
        }
    }

    static class ModelState {
        android.media.soundtrigger.RecognitionConfig config;
        final java.lang.String description;
        com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity activityState = com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity.LOADED;
        private final java.util.Map<java.lang.Integer, android.media.soundtrigger.ModelParameterRange> parameterSupport = new java.util.HashMap();

        enum Activity {
            LOADED,
            ACTIVE,
            PREEMPTED
        }

        ModelState(android.media.soundtrigger.SoundModel soundModel) {
            this.description = com.android.server.soundtrigger_middleware.ObjectPrinter.print(soundModel, 16);
        }

        ModelState(android.media.soundtrigger.PhraseSoundModel phraseSoundModel) {
            this.description = com.android.server.soundtrigger_middleware.ObjectPrinter.print(phraseSoundModel, 16);
        }

        void checkSupported(int i) {
            if (!this.parameterSupport.containsKey(java.lang.Integer.valueOf(i))) {
                throw new java.lang.IllegalStateException("Parameter has not been checked for support.");
            }
            if (this.parameterSupport.get(java.lang.Integer.valueOf(i)) == null) {
                throw new java.lang.IllegalArgumentException("Paramater is not supported.");
            }
        }

        void checkSupported(int i, int i2) {
            if (!this.parameterSupport.containsKey(java.lang.Integer.valueOf(i))) {
                throw new java.lang.IllegalStateException("Parameter has not been checked for support.");
            }
            android.media.soundtrigger.ModelParameterRange modelParameterRange = this.parameterSupport.get(java.lang.Integer.valueOf(i));
            if (modelParameterRange == null) {
                throw new java.lang.IllegalArgumentException("Paramater is not supported.");
            }
            com.android.internal.util.Preconditions.checkArgumentInRange(i2, modelParameterRange.minInclusive, modelParameterRange.maxInclusive, "value");
        }
    }

    private class Session extends android.media.soundtrigger_middleware.ISoundTriggerModule.Stub {
        private final com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session.CallbackWrapper mCallbackWrapper;
        private android.media.soundtrigger_middleware.ISoundTriggerModule mDelegate;
        private final int mHandle;

        @android.annotation.NonNull
        private final java.util.Map<java.lang.Integer, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState> mLoadedModels = new java.util.HashMap();
        private com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus mState = com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.ALIVE;
        private final android.media.permission.Identity mOriginatorIdentity = android.media.permission.IdentityContext.get();

        Session(int i, @android.annotation.NonNull android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback) {
            this.mCallbackWrapper = new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session.CallbackWrapper(iSoundTriggerCallback);
            this.mHandle = i;
        }

        android.media.soundtrigger_middleware.ISoundTriggerCallback getCallbackWrapper() {
            return this.mCallbackWrapper;
        }

        void attach(@android.annotation.NonNull android.media.soundtrigger_middleware.ISoundTriggerModule iSoundTriggerModule) {
            this.mDelegate = iSoundTriggerModule;
            ((com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleState) com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this.mModules.get(java.lang.Integer.valueOf(this.mHandle))).sessions.add(this);
        }

        public int loadModel(@android.annotation.NonNull android.media.soundtrigger.SoundModel soundModel) {
            int loadModel;
            com.android.server.soundtrigger_middleware.ValidationUtil.validateGenericModel(soundModel);
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                try {
                    if (this.mState == com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.DETACHED) {
                        throw new java.lang.IllegalStateException("Module has been detached.");
                    }
                    try {
                        loadModel = this.mDelegate.loadModel(soundModel);
                        this.mLoadedModels.put(java.lang.Integer.valueOf(loadModel), new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState(soundModel));
                    } catch (java.lang.Exception e) {
                        throw com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.handleException(e);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return loadModel;
        }

        public int loadPhraseModel(@android.annotation.NonNull android.media.soundtrigger.PhraseSoundModel phraseSoundModel) {
            int loadPhraseModel;
            com.android.server.soundtrigger_middleware.ValidationUtil.validatePhraseModel(phraseSoundModel);
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                try {
                    if (this.mState == com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.DETACHED) {
                        throw new java.lang.IllegalStateException("Module has been detached.");
                    }
                    try {
                        loadPhraseModel = this.mDelegate.loadPhraseModel(phraseSoundModel);
                        this.mLoadedModels.put(java.lang.Integer.valueOf(loadPhraseModel), new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState(phraseSoundModel));
                    } catch (java.lang.Exception e) {
                        throw com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.handleException(e);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return loadPhraseModel;
        }

        public void unloadModel(int i) {
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                try {
                    if (this.mState == com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.DETACHED) {
                        throw new java.lang.IllegalStateException("Module has been detached.");
                    }
                    com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState modelState = this.mLoadedModels.get(java.lang.Integer.valueOf(i));
                    if (modelState == null) {
                        throw new java.lang.IllegalStateException("Invalid handle: " + i);
                    }
                    if (modelState.activityState != com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity.LOADED && modelState.activityState != com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity.PREEMPTED) {
                        throw new java.lang.IllegalStateException("Model with handle: " + i + " has invalid state for unloading");
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            try {
                this.mDelegate.unloadModel(i);
                synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                    this.mLoadedModels.remove(java.lang.Integer.valueOf(i));
                }
            } catch (java.lang.Exception e) {
                throw com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.handleException(e);
            }
        }

        public android.os.IBinder startRecognition(int i, @android.annotation.NonNull android.media.soundtrigger.RecognitionConfig recognitionConfig) {
            android.os.IBinder startRecognition;
            com.android.server.soundtrigger_middleware.ValidationUtil.validateRecognitionConfig(recognitionConfig);
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                if (this.mState == com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.DETACHED) {
                    throw new java.lang.IllegalStateException("Module has been detached.");
                }
                com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState modelState = this.mLoadedModels.get(java.lang.Integer.valueOf(i));
                if (modelState == null) {
                    throw new java.lang.IllegalStateException("Invalid handle: " + i);
                }
                com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity activity = modelState.activityState;
                if (activity != com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity.LOADED && activity != com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity.PREEMPTED) {
                    throw new java.lang.IllegalStateException("Model with handle: " + i + " has invalid state for starting recognition");
                }
                try {
                    startRecognition = this.mDelegate.startRecognition(i, recognitionConfig);
                    modelState.config = recognitionConfig;
                    modelState.activityState = com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity.ACTIVE;
                } catch (java.lang.Exception e) {
                    throw com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.handleException(e);
                }
            }
            return startRecognition;
        }

        public void stopRecognition(int i) {
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                if (this.mState == com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.DETACHED) {
                    throw new java.lang.IllegalStateException("Module has been detached.");
                }
                if (this.mLoadedModels.get(java.lang.Integer.valueOf(i)) == null) {
                    throw new java.lang.IllegalStateException("Invalid handle: " + i);
                }
            }
            try {
                this.mDelegate.stopRecognition(i);
                synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                    try {
                        com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState modelState = this.mLoadedModels.get(java.lang.Integer.valueOf(i));
                        if (modelState == null) {
                            return;
                        }
                        if (modelState.activityState != com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity.PREEMPTED) {
                            modelState.activityState = com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity.LOADED;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } catch (java.lang.Exception e) {
                throw com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.handleException(e);
            }
        }

        public void forceRecognitionEvent(int i) {
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                if (this.mState == com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.DETACHED) {
                    throw new java.lang.IllegalStateException("Module has been detached.");
                }
                com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState modelState = this.mLoadedModels.get(java.lang.Integer.valueOf(i));
                if (modelState == null) {
                    throw new java.lang.IllegalStateException("Invalid handle: " + i);
                }
                try {
                    if (modelState.activityState == com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity.ACTIVE) {
                        this.mDelegate.forceRecognitionEvent(i);
                    }
                } catch (java.lang.Exception e) {
                    throw com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.handleException(e);
                }
            }
        }

        public void setModelParameter(int i, int i2, int i3) {
            com.android.server.soundtrigger_middleware.ValidationUtil.validateModelParameter(i2);
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                try {
                    if (this.mState == com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.DETACHED) {
                        throw new java.lang.IllegalStateException("Module has been detached.");
                    }
                    com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState modelState = this.mLoadedModels.get(java.lang.Integer.valueOf(i));
                    if (modelState == null) {
                        throw new java.lang.IllegalStateException("Invalid handle: " + i);
                    }
                    modelState.checkSupported(i2, i3);
                    try {
                        this.mDelegate.setModelParameter(i, i2, i3);
                    } catch (java.lang.Exception e) {
                        throw com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.handleException(e);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public int getModelParameter(int i, int i2) {
            int modelParameter;
            com.android.server.soundtrigger_middleware.ValidationUtil.validateModelParameter(i2);
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                try {
                    if (this.mState == com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.DETACHED) {
                        throw new java.lang.IllegalStateException("Module has been detached.");
                    }
                    com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState modelState = this.mLoadedModels.get(java.lang.Integer.valueOf(i));
                    if (modelState == null) {
                        throw new java.lang.IllegalStateException("Invalid handle: " + i);
                    }
                    modelState.checkSupported(i2);
                    try {
                        modelParameter = this.mDelegate.getModelParameter(i, i2);
                    } catch (java.lang.Exception e) {
                        throw com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.handleException(e);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return modelParameter;
        }

        @android.annotation.Nullable
        public android.media.soundtrigger.ModelParameterRange queryModelParameterSupport(int i, int i2) {
            android.media.soundtrigger.ModelParameterRange queryModelParameterSupport;
            com.android.server.soundtrigger_middleware.ValidationUtil.validateModelParameter(i2);
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                try {
                    if (this.mState == com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.DETACHED) {
                        throw new java.lang.IllegalStateException("Module has been detached.");
                    }
                    com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState modelState = this.mLoadedModels.get(java.lang.Integer.valueOf(i));
                    if (modelState == null) {
                        throw new java.lang.IllegalStateException("Invalid handle: " + i);
                    }
                    try {
                        queryModelParameterSupport = this.mDelegate.queryModelParameterSupport(i, i2);
                        modelState.parameterSupport.put(java.lang.Integer.valueOf(i2), queryModelParameterSupport);
                    } catch (java.lang.Exception e) {
                        throw com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.handleException(e);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return queryModelParameterSupport;
        }

        public void detach() {
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                if (this.mState == com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.DETACHED) {
                    throw new java.lang.IllegalStateException("Module has already been detached.");
                }
                if (this.mState == com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.ALIVE && !this.mLoadedModels.isEmpty()) {
                    throw new java.lang.IllegalStateException("Cannot detach while models are loaded.");
                }
                try {
                    detachInternal();
                } catch (java.lang.Exception e) {
                    throw com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.handleException(e);
                }
            }
        }

        public java.lang.String toString() {
            return java.util.Objects.toString(this.mDelegate);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void detachInternal() {
            try {
                this.mDelegate.detach();
                this.mState = com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.DETACHED;
                this.mCallbackWrapper.detached();
                ((com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleState) com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this.mModules.get(java.lang.Integer.valueOf(this.mHandle))).sessions.remove(this);
            } catch (android.os.RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }

        void dump(java.io.PrintWriter printWriter) {
            if (this.mState == com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.ALIVE) {
                printWriter.println("-------------------------------");
                printWriter.printf("Session %s, client: %s\n", toString(), com.android.server.soundtrigger_middleware.ObjectPrinter.print(this.mOriginatorIdentity, 16));
                printWriter.println("Loaded models (handle, active, description):");
                printWriter.println();
                printWriter.println("-------------------------------");
                for (java.util.Map.Entry<java.lang.Integer, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState> entry : this.mLoadedModels.entrySet()) {
                    printWriter.print(entry.getKey());
                    printWriter.print('\t');
                    printWriter.print(entry.getValue().activityState.name());
                    printWriter.print('\t');
                    printWriter.print(entry.getValue().description);
                    printWriter.println();
                }
                printWriter.println();
                return;
            }
            printWriter.printf("Session %s is dead", toString());
            printWriter.println();
        }

        class CallbackWrapper implements android.media.soundtrigger_middleware.ISoundTriggerCallback, android.os.IBinder.DeathRecipient {
            private final android.media.soundtrigger_middleware.ISoundTriggerCallback mCallback;

            CallbackWrapper(android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback) {
                this.mCallback = iSoundTriggerCallback;
                try {
                    this.mCallback.asBinder().linkToDeath(this, 0);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            }

            void detached() {
                this.mCallback.asBinder().unlinkToDeath(this, 0);
            }

            public void onRecognition(int i, @android.annotation.NonNull android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys, int i2) {
                synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                    try {
                        com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState modelState = (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState) com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session.this.mLoadedModels.get(java.lang.Integer.valueOf(i));
                        if (!recognitionEventSys.recognitionEvent.recognitionStillActive) {
                            modelState.activityState = com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity.LOADED;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                try {
                    this.mCallback.onRecognition(i, recognitionEventSys, i2);
                } catch (java.lang.Exception e) {
                    android.util.Slog.w(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.TAG, "Client callback exception.", e);
                }
            }

            public void onPhraseRecognition(int i, @android.annotation.NonNull android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) {
                synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                    try {
                        com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState modelState = (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState) com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session.this.mLoadedModels.get(java.lang.Integer.valueOf(i));
                        if (!phraseRecognitionEventSys.phraseRecognitionEvent.common.recognitionStillActive) {
                            modelState.activityState = com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity.LOADED;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                try {
                    this.mCallback.onPhraseRecognition(i, phraseRecognitionEventSys, i2);
                } catch (java.lang.Exception e) {
                    android.util.Slog.w(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.TAG, "Client callback exception.", e);
                }
            }

            public void onModelUnloaded(int i) {
                synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                    ((com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState) com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session.this.mLoadedModels.get(java.lang.Integer.valueOf(i))).activityState = com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity.PREEMPTED;
                }
                try {
                    this.mCallback.onModelUnloaded(i);
                } catch (java.lang.Exception e) {
                    android.util.Slog.w(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.TAG, "Client callback exception.", e);
                }
            }

            public void onResourcesAvailable() {
                try {
                    this.mCallback.onResourcesAvailable();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.TAG, "Client callback exception.", e);
                }
            }

            public void onModuleDied() {
                synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                    com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session.this.mState = com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModuleStatus.DEAD;
                }
                try {
                    this.mCallback.onModuleDied();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.TAG, "Client callback exception.", e);
                }
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                android.util.SparseArray sparseArray = new android.util.SparseArray();
                synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                    try {
                        for (java.util.Map.Entry entry : com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session.this.mLoadedModels.entrySet()) {
                            sparseArray.put(((java.lang.Integer) entry.getKey()).intValue(), ((com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState) entry.getValue()).activityState);
                        }
                    } finally {
                    }
                }
                for (int i = 0; i < sparseArray.size(); i++) {
                    try {
                        if (sparseArray.valueAt(i) == com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState.Activity.ACTIVE) {
                            com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session.this.mDelegate.stopRecognition(sparseArray.keyAt(i));
                        }
                        com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session.this.mDelegate.unloadModel(sparseArray.keyAt(i));
                    } catch (java.lang.Exception e) {
                        throw com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.handleException(e);
                    }
                }
                synchronized (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.this) {
                    try {
                        for (java.util.Map.Entry entry2 : com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session.this.mLoadedModels.entrySet()) {
                            if (sparseArray.get(((java.lang.Integer) entry2.getKey()).intValue()) != ((com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.ModelState) entry2.getValue()).activityState) {
                                android.util.Slog.e(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.TAG, "Unexpected state update in binderDied. Race occurred!");
                            }
                        }
                        if (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session.this.mLoadedModels.size() != sparseArray.size()) {
                            android.util.Slog.e(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.TAG, "Unexpected state update in binderDied. Race occurred!");
                        }
                        try {
                            com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session.this.detachInternal();
                        } catch (java.lang.Exception e2) {
                            throw com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.handleException(e2);
                        }
                    } finally {
                    }
                }
            }

            public android.os.IBinder asBinder() {
                return this.mCallback.asBinder();
            }

            public java.lang.String toString() {
                return java.util.Objects.toString(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareValidation.Session.this.mDelegate);
            }
        }
    }
}
