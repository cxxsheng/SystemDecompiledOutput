package android.media.soundtrigger;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class SoundTriggerManager {
    private static final boolean DBG = false;
    public static final java.lang.String EXTRA_MESSAGE_TYPE = "android.media.soundtrigger.MESSAGE_TYPE";
    public static final java.lang.String EXTRA_RECOGNITION_EVENT = "android.media.soundtrigger.RECOGNITION_EVENT";
    public static final java.lang.String EXTRA_STATUS = "android.media.soundtrigger.STATUS";
    public static final int FLAG_MESSAGE_TYPE_RECOGNITION_ERROR = 1;
    public static final int FLAG_MESSAGE_TYPE_RECOGNITION_EVENT = 0;
    public static final int FLAG_MESSAGE_TYPE_RECOGNITION_PAUSED = 2;
    public static final int FLAG_MESSAGE_TYPE_RECOGNITION_RESUMED = 3;
    public static final int FLAG_MESSAGE_TYPE_UNKNOWN = -1;
    private static final java.lang.String TAG = "SoundTriggerManager";
    private final android.content.Context mContext;
    private final com.android.internal.app.ISoundTriggerService mSoundTriggerService;
    private final com.android.internal.app.ISoundTriggerSession mSoundTriggerSession;
    private final android.os.IBinder mBinderToken = new android.os.Binder();
    private final java.util.HashMap<java.util.UUID, android.media.soundtrigger.SoundTriggerDetector> mReceiverInstanceMap = new java.util.HashMap<>();

    public SoundTriggerManager(android.content.Context context, com.android.internal.app.ISoundTriggerService iSoundTriggerService) {
        try {
            android.media.permission.Identity identity = new android.media.permission.Identity();
            identity.packageName = android.app.ActivityThread.currentOpPackageName();
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                android.hardware.soundtrigger.SoundTrigger.ModuleProperties orElse = iSoundTriggerService.listModuleProperties(identity).stream().filter(new java.util.function.Predicate() { // from class: android.media.soundtrigger.SoundTriggerManager$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        return android.media.soundtrigger.SoundTriggerManager.lambda$new$0((android.hardware.soundtrigger.SoundTrigger.ModuleProperties) obj);
                    }
                }).findFirst().orElse(null);
                if (orElse != null) {
                    this.mSoundTriggerSession = iSoundTriggerService.attachAsOriginator(identity, orElse, this.mBinderToken);
                } else {
                    this.mSoundTriggerSession = null;
                }
                if (create != null) {
                    create.close();
                }
                this.mContext = context;
                this.mSoundTriggerService = iSoundTriggerService;
            } finally {
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ boolean lambda$new$0(android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties) {
        return !moduleProperties.getSupportedModelArch().equals("injection");
    }

    public android.media.soundtrigger.SoundTriggerManager createManagerForModule(android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties) {
        return new android.media.soundtrigger.SoundTriggerManager(this.mContext, this.mSoundTriggerService, (android.hardware.soundtrigger.SoundTrigger.ModuleProperties) java.util.Objects.requireNonNull(moduleProperties));
    }

    public android.media.soundtrigger.SoundTriggerManager createManagerForTestModule() {
        return new android.media.soundtrigger.SoundTriggerManager(this.mContext, this.mSoundTriggerService, getTestModuleProperties());
    }

    private final android.hardware.soundtrigger.SoundTrigger.ModuleProperties getTestModuleProperties() {
        android.hardware.soundtrigger.SoundTrigger.ModuleProperties orElse = listModuleProperties().stream().filter(new java.util.function.Predicate() { // from class: android.media.soundtrigger.SoundTriggerManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean equals;
                equals = ((android.hardware.soundtrigger.SoundTrigger.ModuleProperties) obj).getSupportedModelArch().equals("injection");
                return equals;
            }
        }).findFirst().orElse(null);
        if (orElse == null) {
            throw new java.lang.AssertionError("Fake ST HAL should always be available");
        }
        return orElse;
    }

    private SoundTriggerManager(android.content.Context context, com.android.internal.app.ISoundTriggerService iSoundTriggerService, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties) {
        try {
            android.media.permission.Identity identity = new android.media.permission.Identity();
            identity.packageName = android.app.ActivityThread.currentOpPackageName();
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                this.mSoundTriggerSession = iSoundTriggerService.attachAsOriginator(identity, (android.hardware.soundtrigger.SoundTrigger.ModuleProperties) java.util.Objects.requireNonNull(moduleProperties), this.mBinderToken);
                if (create != null) {
                    create.close();
                }
                this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
                this.mSoundTriggerService = (com.android.internal.app.ISoundTriggerService) java.util.Objects.requireNonNull(iSoundTriggerService);
            } finally {
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> listModuleProperties() {
        try {
            com.android.internal.app.ISoundTriggerService asInterface = com.android.internal.app.ISoundTriggerService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.SOUND_TRIGGER_SERVICE));
            android.media.permission.Identity identity = new android.media.permission.Identity();
            identity.packageName = android.app.ActivityThread.currentOpPackageName();
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> listModuleProperties = asInterface.listModuleProperties(identity);
                if (create != null) {
                    create.close();
                }
                return listModuleProperties;
            } finally {
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void updateModel(android.media.soundtrigger.SoundTriggerManager.Model model) {
        if (this.mSoundTriggerSession == null) {
            throw new java.lang.IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            this.mSoundTriggerSession.updateSoundModel(model.getGenericSoundModel());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public android.media.soundtrigger.SoundTriggerManager.Model getModel(java.util.UUID uuid) {
        if (this.mSoundTriggerSession == null) {
            throw new java.lang.IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            android.hardware.soundtrigger.SoundTrigger.GenericSoundModel soundModel = this.mSoundTriggerSession.getSoundModel(new android.os.ParcelUuid((java.util.UUID) java.util.Objects.requireNonNull(uuid)));
            if (soundModel == null) {
                return null;
            }
            return new android.media.soundtrigger.SoundTriggerManager.Model(soundModel);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void deleteModel(java.util.UUID uuid) {
        if (this.mSoundTriggerSession == null) {
            throw new java.lang.IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            this.mSoundTriggerSession.deleteSoundModel(new android.os.ParcelUuid((java.util.UUID) java.util.Objects.requireNonNull(uuid)));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public android.media.soundtrigger.SoundTriggerDetector createSoundTriggerDetector(java.util.UUID uuid, android.media.soundtrigger.SoundTriggerDetector.Callback callback, android.os.Handler handler) {
        if (this.mSoundTriggerSession == null) {
            throw new java.lang.IllegalStateException("No underlying SoundTriggerModule available");
        }
        this.mReceiverInstanceMap.get(uuid);
        try {
            android.media.soundtrigger.SoundTriggerDetector soundTriggerDetector = new android.media.soundtrigger.SoundTriggerDetector(this.mSoundTriggerSession, this.mSoundTriggerSession.getSoundModel(new android.os.ParcelUuid((java.util.UUID) java.util.Objects.requireNonNull(uuid))), callback, handler);
            this.mReceiverInstanceMap.put(uuid, soundTriggerDetector);
            return soundTriggerDetector;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class Model {
        private android.hardware.soundtrigger.SoundTrigger.GenericSoundModel mGenericSoundModel;

        Model(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel) {
            this.mGenericSoundModel = genericSoundModel;
        }

        public static android.media.soundtrigger.SoundTriggerManager.Model create(java.util.UUID uuid, java.util.UUID uuid2, byte[] bArr, int i) {
            java.util.Objects.requireNonNull(uuid);
            java.util.Objects.requireNonNull(uuid2);
            return new android.media.soundtrigger.SoundTriggerManager.Model(new android.hardware.soundtrigger.SoundTrigger.GenericSoundModel(uuid, uuid2, bArr, i));
        }

        public static android.media.soundtrigger.SoundTriggerManager.Model create(java.util.UUID uuid, java.util.UUID uuid2, byte[] bArr) {
            return create(uuid, uuid2, bArr, -1);
        }

        public java.util.UUID getModelUuid() {
            return this.mGenericSoundModel.getUuid();
        }

        public java.util.UUID getVendorUuid() {
            return this.mGenericSoundModel.getVendorUuid();
        }

        public int getVersion() {
            return this.mGenericSoundModel.getVersion();
        }

        public byte[] getModelData() {
            return this.mGenericSoundModel.getData();
        }

        android.hardware.soundtrigger.SoundTrigger.GenericSoundModel getGenericSoundModel() {
            return this.mGenericSoundModel;
        }

        public android.hardware.soundtrigger.SoundTrigger.SoundModel getSoundModel() {
            return this.mGenericSoundModel;
        }
    }

    public int loadSoundModel(android.hardware.soundtrigger.SoundTrigger.SoundModel soundModel) {
        if (this.mSoundTriggerSession == null) {
            throw new java.lang.IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            switch (soundModel.getType()) {
                case 0:
                    return this.mSoundTriggerSession.loadKeyphraseSoundModel((android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) soundModel);
                case 1:
                    return this.mSoundTriggerSession.loadGenericSoundModel((android.hardware.soundtrigger.SoundTrigger.GenericSoundModel) soundModel);
                default:
                    android.util.Slog.e(TAG, "Unkown model type");
                    return Integer.MIN_VALUE;
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int startRecognition(java.util.UUID uuid, android.os.Bundle bundle, android.content.ComponentName componentName, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig) {
        java.util.Objects.requireNonNull(uuid);
        java.util.Objects.requireNonNull(componentName);
        java.util.Objects.requireNonNull(recognitionConfig);
        if (this.mSoundTriggerSession == null) {
            throw new java.lang.IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            return this.mSoundTriggerSession.startRecognitionForService(new android.os.ParcelUuid(uuid), bundle, componentName, recognitionConfig);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int stopRecognition(java.util.UUID uuid) {
        if (this.mSoundTriggerSession == null) {
            throw new java.lang.IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            return this.mSoundTriggerSession.stopRecognitionForService(new android.os.ParcelUuid((java.util.UUID) java.util.Objects.requireNonNull(uuid)));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int unloadSoundModel(java.util.UUID uuid) {
        if (this.mSoundTriggerSession == null) {
            throw new java.lang.IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            return this.mSoundTriggerSession.unloadSoundModel(new android.os.ParcelUuid((java.util.UUID) java.util.Objects.requireNonNull(uuid)));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isRecognitionActive(java.util.UUID uuid) {
        if (uuid == null || this.mSoundTriggerSession == null) {
            return false;
        }
        try {
            return this.mSoundTriggerSession.isRecognitionActive(new android.os.ParcelUuid(uuid));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getDetectionServiceOperationsTimeout() {
        try {
            return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), android.provider.Settings.Global.SOUND_TRIGGER_DETECTION_SERVICE_OP_TIMEOUT);
        } catch (android.provider.Settings.SettingNotFoundException e) {
            return Integer.MAX_VALUE;
        }
    }

    public int getModelState(java.util.UUID uuid) {
        if (this.mSoundTriggerSession == null) {
            throw new java.lang.IllegalStateException("No underlying SoundTriggerModule available");
        }
        if (uuid == null) {
            return Integer.MIN_VALUE;
        }
        try {
            return this.mSoundTriggerSession.getModelState(new android.os.ParcelUuid(uuid));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.soundtrigger.SoundTrigger.ModuleProperties getModuleProperties() {
        if (this.mSoundTriggerSession == null) {
            return null;
        }
        try {
            return this.mSoundTriggerSession.getModuleProperties();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setParameter(java.util.UUID uuid, int i, int i2) {
        if (this.mSoundTriggerSession == null) {
            throw new java.lang.IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            return this.mSoundTriggerSession.setParameter(new android.os.ParcelUuid((java.util.UUID) java.util.Objects.requireNonNull(uuid)), i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getParameter(java.util.UUID uuid, int i) {
        if (this.mSoundTriggerSession == null) {
            throw new java.lang.IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            return this.mSoundTriggerSession.getParameter(new android.os.ParcelUuid((java.util.UUID) java.util.Objects.requireNonNull(uuid)), i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter(java.util.UUID uuid, int i) {
        if (this.mSoundTriggerSession == null) {
            throw new java.lang.IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            return this.mSoundTriggerSession.queryParameter(new android.os.ParcelUuid((java.util.UUID) java.util.Objects.requireNonNull(uuid)), i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.media.soundtrigger.SoundTriggerInstrumentation attachInstrumentation(java.util.concurrent.Executor executor, android.media.soundtrigger.SoundTriggerInstrumentation.GlobalCallback globalCallback) {
        return new android.media.soundtrigger.SoundTriggerInstrumentation(com.android.internal.app.ISoundTriggerService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.SOUND_TRIGGER_SERVICE)), executor, globalCallback);
    }
}
