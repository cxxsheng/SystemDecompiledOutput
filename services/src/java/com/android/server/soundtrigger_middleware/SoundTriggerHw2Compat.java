package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
final class SoundTriggerHw2Compat implements com.android.server.soundtrigger_middleware.ISoundTriggerHal {
    private static final java.lang.String TAG = "SoundTriggerHw2Compat";

    @android.annotation.NonNull
    private final android.os.IHwBinder mBinder;

    @android.annotation.NonNull
    private final android.media.soundtrigger.Properties mProperties;

    @android.annotation.NonNull
    private final java.lang.Runnable mRebootRunnable;

    @android.annotation.NonNull
    private android.hardware.soundtrigger.V2_0.ISoundTriggerHw mUnderlying_2_0;

    @android.annotation.Nullable
    private android.hardware.soundtrigger.V2_1.ISoundTriggerHw mUnderlying_2_1;

    @android.annotation.Nullable
    private android.hardware.soundtrigger.V2_2.ISoundTriggerHw mUnderlying_2_2;

    @android.annotation.Nullable
    private android.hardware.soundtrigger.V2_3.ISoundTriggerHw mUnderlying_2_3;

    @android.annotation.NonNull
    private final java.util.concurrent.ConcurrentMap<java.lang.Integer, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback> mModelCallbacks = new java.util.concurrent.ConcurrentHashMap();

    @android.annotation.NonNull
    private final java.util.Map<android.os.IBinder.DeathRecipient, android.os.IHwBinder.DeathRecipient> mDeathRecipientMap = new java.util.HashMap();

    static com.android.server.soundtrigger_middleware.ISoundTriggerHal create(@android.annotation.NonNull android.hardware.soundtrigger.V2_0.ISoundTriggerHw iSoundTriggerHw, @android.annotation.NonNull java.lang.Runnable runnable, com.android.server.soundtrigger_middleware.ICaptureStateNotifier iCaptureStateNotifier) {
        return create(iSoundTriggerHw.asBinder(), runnable, iCaptureStateNotifier);
    }

    static com.android.server.soundtrigger_middleware.ISoundTriggerHal create(@android.annotation.NonNull android.os.IHwBinder iHwBinder, @android.annotation.NonNull java.lang.Runnable runnable, com.android.server.soundtrigger_middleware.ICaptureStateNotifier iCaptureStateNotifier) {
        com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat soundTriggerHw2Compat = new com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat(iHwBinder, runnable);
        com.android.server.soundtrigger_middleware.SoundTriggerHalMaxModelLimiter soundTriggerHalMaxModelLimiter = new com.android.server.soundtrigger_middleware.SoundTriggerHalMaxModelLimiter(soundTriggerHw2Compat, soundTriggerHw2Compat.mProperties.maxSoundModels);
        if (!soundTriggerHw2Compat.mProperties.concurrentCapture) {
            return new com.android.server.soundtrigger_middleware.SoundTriggerHalConcurrentCaptureHandler(soundTriggerHalMaxModelLimiter, iCaptureStateNotifier);
        }
        return soundTriggerHalMaxModelLimiter;
    }

    private SoundTriggerHw2Compat(@android.annotation.NonNull android.os.IHwBinder iHwBinder, @android.annotation.NonNull java.lang.Runnable runnable) {
        java.util.Objects.requireNonNull(runnable);
        this.mRebootRunnable = runnable;
        java.util.Objects.requireNonNull(iHwBinder);
        this.mBinder = iHwBinder;
        initUnderlying(iHwBinder);
        android.media.soundtrigger.Properties propertiesInternal = getPropertiesInternal();
        java.util.Objects.requireNonNull(propertiesInternal);
        this.mProperties = propertiesInternal;
    }

    private void initUnderlying(android.os.IHwBinder iHwBinder) {
        android.hardware.soundtrigger.V2_3.ISoundTriggerHw asInterface = android.hardware.soundtrigger.V2_3.ISoundTriggerHw.asInterface(iHwBinder);
        if (asInterface != null) {
            this.mUnderlying_2_3 = asInterface;
            this.mUnderlying_2_2 = asInterface;
            this.mUnderlying_2_1 = asInterface;
            this.mUnderlying_2_0 = asInterface;
            return;
        }
        android.hardware.soundtrigger.V2_2.ISoundTriggerHw asInterface2 = android.hardware.soundtrigger.V2_2.ISoundTriggerHw.asInterface(iHwBinder);
        if (asInterface2 != null) {
            this.mUnderlying_2_2 = asInterface2;
            this.mUnderlying_2_1 = asInterface2;
            this.mUnderlying_2_0 = asInterface2;
            this.mUnderlying_2_3 = null;
            return;
        }
        android.hardware.soundtrigger.V2_1.ISoundTriggerHw asInterface3 = android.hardware.soundtrigger.V2_1.ISoundTriggerHw.asInterface(iHwBinder);
        if (asInterface3 != null) {
            this.mUnderlying_2_1 = asInterface3;
            this.mUnderlying_2_0 = asInterface3;
            this.mUnderlying_2_3 = null;
            this.mUnderlying_2_2 = null;
            return;
        }
        android.hardware.soundtrigger.V2_0.ISoundTriggerHw asInterface4 = android.hardware.soundtrigger.V2_0.ISoundTriggerHw.asInterface(iHwBinder);
        if (asInterface4 != null) {
            this.mUnderlying_2_0 = asInterface4;
            this.mUnderlying_2_3 = null;
            this.mUnderlying_2_2 = null;
            this.mUnderlying_2_1 = null;
            return;
        }
        throw new java.lang.RuntimeException("Binder doesn't support ISoundTriggerHw@2.0");
    }

    private static void handleHalStatus(int i, java.lang.String str) {
        if (i != 0) {
            throw new com.android.server.soundtrigger_middleware.HalException(i, str);
        }
    }

    private static void handleHalStatusAllowBusy(int i, java.lang.String str) {
        if (i == (-android.system.OsConstants.EBUSY)) {
            throw new com.android.server.soundtrigger_middleware.RecoverableException(1);
        }
        handleHalStatus(i, str);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void reboot() {
        this.mRebootRunnable.run();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void detach() {
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public android.media.soundtrigger.Properties getProperties() {
        return this.mProperties;
    }

    private android.media.soundtrigger.Properties getPropertiesInternal() {
        try {
            final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(-1);
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            try {
                as2_3().getProperties_2_3(new android.hardware.soundtrigger.V2_3.ISoundTriggerHw.getProperties_2_3Callback() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat$$ExternalSyntheticLambda6
                    @Override // android.hardware.soundtrigger.V2_3.ISoundTriggerHw.getProperties_2_3Callback
                    public final void onValues(int i, android.hardware.soundtrigger.V2_3.Properties properties) {
                        com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.lambda$getPropertiesInternal$0(atomicInteger, atomicReference, i, properties);
                    }
                });
                handleHalStatus(atomicInteger.get(), "getProperties_2_3");
                return com.android.server.soundtrigger_middleware.ConversionUtil.hidl2aidlProperties((android.hardware.soundtrigger.V2_3.Properties) atomicReference.get());
            } catch (com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported e) {
                return getProperties_2_0();
            }
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowAsRuntimeException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getPropertiesInternal$0(java.util.concurrent.atomic.AtomicInteger atomicInteger, java.util.concurrent.atomic.AtomicReference atomicReference, int i, android.hardware.soundtrigger.V2_3.Properties properties) {
        atomicInteger.set(i);
        atomicReference.set(properties);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void registerCallback(com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback globalCallback) {
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadSoundModel(android.media.soundtrigger.SoundModel soundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
        android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel aidl2hidlSoundModel = com.android.server.soundtrigger_middleware.ConversionUtil.aidl2hidlSoundModel(soundModel);
        try {
            try {
                final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(-1);
                final java.util.concurrent.atomic.AtomicInteger atomicInteger2 = new java.util.concurrent.atomic.AtomicInteger(0);
                try {
                    as2_1().loadSoundModel_2_1(aidl2hidlSoundModel, new com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.ModelCallbackWrapper(modelCallback), 0, new android.hardware.soundtrigger.V2_1.ISoundTriggerHw.loadSoundModel_2_1Callback() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat$$ExternalSyntheticLambda7
                        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw.loadSoundModel_2_1Callback
                        public final void onValues(int i, int i2) {
                            com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.lambda$loadSoundModel$1(atomicInteger, atomicInteger2, i, i2);
                        }
                    });
                    handleHalStatus(atomicInteger.get(), "loadSoundModel_2_1");
                    this.mModelCallbacks.put(java.lang.Integer.valueOf(atomicInteger2.get()), modelCallback);
                    return atomicInteger2.get();
                } catch (com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported e) {
                    int loadSoundModel_2_0 = loadSoundModel_2_0(aidl2hidlSoundModel, modelCallback);
                    if (aidl2hidlSoundModel.data != null) {
                        try {
                            aidl2hidlSoundModel.data.close();
                        } catch (java.io.IOException e2) {
                            android.util.Slog.e(TAG, "Failed to close file", e2);
                        }
                    }
                    return loadSoundModel_2_0;
                }
            } catch (android.os.RemoteException e3) {
                throw e3.rethrowAsRuntimeException();
            }
        } finally {
            if (aidl2hidlSoundModel.data != null) {
                try {
                    aidl2hidlSoundModel.data.close();
                } catch (java.io.IOException e4) {
                    android.util.Slog.e(TAG, "Failed to close file", e4);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$loadSoundModel$1(java.util.concurrent.atomic.AtomicInteger atomicInteger, java.util.concurrent.atomic.AtomicInteger atomicInteger2, int i, int i2) {
        atomicInteger.set(i);
        atomicInteger2.set(i2);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadPhraseSoundModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
        android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel aidl2hidlPhraseSoundModel = com.android.server.soundtrigger_middleware.ConversionUtil.aidl2hidlPhraseSoundModel(phraseSoundModel);
        try {
            try {
                final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(-1);
                final java.util.concurrent.atomic.AtomicInteger atomicInteger2 = new java.util.concurrent.atomic.AtomicInteger(0);
                try {
                    as2_1().loadPhraseSoundModel_2_1(aidl2hidlPhraseSoundModel, new com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.ModelCallbackWrapper(modelCallback), 0, new android.hardware.soundtrigger.V2_1.ISoundTriggerHw.loadPhraseSoundModel_2_1Callback() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat$$ExternalSyntheticLambda1
                        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHw.loadPhraseSoundModel_2_1Callback
                        public final void onValues(int i, int i2) {
                            com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.lambda$loadPhraseSoundModel$2(atomicInteger, atomicInteger2, i, i2);
                        }
                    });
                    handleHalStatus(atomicInteger.get(), "loadPhraseSoundModel_2_1");
                    this.mModelCallbacks.put(java.lang.Integer.valueOf(atomicInteger2.get()), modelCallback);
                    return atomicInteger2.get();
                } catch (com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported e) {
                    int loadPhraseSoundModel_2_0 = loadPhraseSoundModel_2_0(aidl2hidlPhraseSoundModel, modelCallback);
                    if (aidl2hidlPhraseSoundModel.common.data != null) {
                        try {
                            aidl2hidlPhraseSoundModel.common.data.close();
                        } catch (java.io.IOException e2) {
                            android.util.Slog.e(TAG, "Failed to close file", e2);
                        }
                    }
                    return loadPhraseSoundModel_2_0;
                }
            } catch (android.os.RemoteException e3) {
                throw e3.rethrowAsRuntimeException();
            }
        } finally {
            if (aidl2hidlPhraseSoundModel.common.data != null) {
                try {
                    aidl2hidlPhraseSoundModel.common.data.close();
                } catch (java.io.IOException e4) {
                    android.util.Slog.e(TAG, "Failed to close file", e4);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$loadPhraseSoundModel$2(java.util.concurrent.atomic.AtomicInteger atomicInteger, java.util.concurrent.atomic.AtomicInteger atomicInteger2, int i, int i2) {
        atomicInteger.set(i);
        atomicInteger2.set(i2);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void unloadSoundModel(int i) {
        try {
            this.mModelCallbacks.remove(java.lang.Integer.valueOf(i));
            handleHalStatus(as2_0().unloadSoundModel(i), "unloadSoundModel");
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void stopRecognition(int i) {
        try {
            handleHalStatus(as2_0().stopRecognition(i), "stopRecognition");
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void startRecognition(int i, int i2, int i3, android.media.soundtrigger.RecognitionConfig recognitionConfig) {
        android.hardware.soundtrigger.V2_3.RecognitionConfig aidl2hidlRecognitionConfig = com.android.server.soundtrigger_middleware.ConversionUtil.aidl2hidlRecognitionConfig(recognitionConfig, i2, i3);
        try {
            try {
                handleHalStatus(as2_3().startRecognition_2_3(i, aidl2hidlRecognitionConfig), "startRecognition_2_3");
            } catch (com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported e) {
                startRecognition_2_1(i, aidl2hidlRecognitionConfig);
            }
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void forceRecognitionEvent(int i) {
        try {
            handleHalStatus(as2_2().getModelState(i), "getModelState");
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported e2) {
            throw e2.throwAsRecoverableException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int getModelParameter(int i, int i2) {
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(-1);
        final java.util.concurrent.atomic.AtomicInteger atomicInteger2 = new java.util.concurrent.atomic.AtomicInteger(0);
        try {
            as2_3().getParameter(i, i2, new android.hardware.soundtrigger.V2_3.ISoundTriggerHw.getParameterCallback() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat$$ExternalSyntheticLambda8
                @Override // android.hardware.soundtrigger.V2_3.ISoundTriggerHw.getParameterCallback
                public final void onValues(int i3, int i4) {
                    com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.lambda$getModelParameter$3(atomicInteger, atomicInteger2, i3, i4);
                }
            });
            handleHalStatus(atomicInteger.get(), "getParameter");
            return atomicInteger2.get();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported e2) {
            throw e2.throwAsRecoverableException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getModelParameter$3(java.util.concurrent.atomic.AtomicInteger atomicInteger, java.util.concurrent.atomic.AtomicInteger atomicInteger2, int i, int i2) {
        atomicInteger.set(i);
        atomicInteger2.set(i2);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void setModelParameter(int i, int i2, int i3) {
        try {
            handleHalStatus(as2_3().setParameter(i, i2, i3), "setParameter");
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported e2) {
            throw e2.throwAsRecoverableException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public android.media.soundtrigger.ModelParameterRange queryParameter(int i, int i2) {
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(-1);
        final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
        try {
            as2_3().queryParameter(i, i2, new android.hardware.soundtrigger.V2_3.ISoundTriggerHw.queryParameterCallback() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat$$ExternalSyntheticLambda2
                @Override // android.hardware.soundtrigger.V2_3.ISoundTriggerHw.queryParameterCallback
                public final void onValues(int i3, android.hardware.soundtrigger.V2_3.OptionalModelParameterRange optionalModelParameterRange) {
                    com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.lambda$queryParameter$4(atomicInteger, atomicReference, i3, optionalModelParameterRange);
                }
            });
            handleHalStatus(atomicInteger.get(), "queryParameter");
            if (((android.hardware.soundtrigger.V2_3.OptionalModelParameterRange) atomicReference.get()).getDiscriminator() != 1) {
                return null;
            }
            return com.android.server.soundtrigger_middleware.ConversionUtil.hidl2aidlModelParameterRange(((android.hardware.soundtrigger.V2_3.OptionalModelParameterRange) atomicReference.get()).range());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported e2) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$queryParameter$4(java.util.concurrent.atomic.AtomicInteger atomicInteger, java.util.concurrent.atomic.AtomicReference atomicReference, int i, android.hardware.soundtrigger.V2_3.OptionalModelParameterRange optionalModelParameterRange) {
        atomicInteger.set(i);
        atomicReference.set(optionalModelParameterRange);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void linkToDeath(final android.os.IBinder.DeathRecipient deathRecipient) {
        android.os.IHwBinder.DeathRecipient deathRecipient2 = new android.os.IHwBinder.DeathRecipient() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat$$ExternalSyntheticLambda3
            public final void serviceDied(long j) {
                deathRecipient.binderDied();
            }
        };
        this.mDeathRecipientMap.put(deathRecipient, deathRecipient2);
        this.mBinder.linkToDeath(deathRecipient2, 0L);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void unlinkToDeath(android.os.IBinder.DeathRecipient deathRecipient) {
        this.mBinder.unlinkToDeath(this.mDeathRecipientMap.remove(deathRecipient));
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public java.lang.String interfaceDescriptor() {
        try {
            return as2_0().interfaceDescriptor();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void flushCallbacks() {
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void clientAttached(android.os.IBinder iBinder) {
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void clientDetached(android.os.IBinder iBinder) {
    }

    private android.media.soundtrigger.Properties getProperties_2_0() throws android.os.RemoteException {
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(-1);
        final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
        as2_0().getProperties(new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.getPropertiesCallback() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat$$ExternalSyntheticLambda5
            @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw.getPropertiesCallback
            public final void onValues(int i, android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties properties) {
                com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.lambda$getProperties_2_0$6(atomicInteger, atomicReference, i, properties);
            }
        });
        handleHalStatus(atomicInteger.get(), "getProperties");
        return com.android.server.soundtrigger_middleware.ConversionUtil.hidl2aidlProperties(com.android.server.soundtrigger_middleware.Hw2CompatUtil.convertProperties_2_0_to_2_3((android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties) atomicReference.get()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getProperties_2_0$6(java.util.concurrent.atomic.AtomicInteger atomicInteger, java.util.concurrent.atomic.AtomicReference atomicReference, int i, android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties properties) {
        atomicInteger.set(i);
        atomicReference.set(properties);
    }

    private int loadSoundModel_2_0(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.SoundModel soundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) throws android.os.RemoteException {
        android.hardware.soundtrigger.V2_0.ISoundTriggerHw.SoundModel convertSoundModel_2_1_to_2_0 = com.android.server.soundtrigger_middleware.Hw2CompatUtil.convertSoundModel_2_1_to_2_0(soundModel);
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(-1);
        final java.util.concurrent.atomic.AtomicInteger atomicInteger2 = new java.util.concurrent.atomic.AtomicInteger(0);
        as2_0().loadSoundModel(convertSoundModel_2_1_to_2_0, new com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.ModelCallbackWrapper(modelCallback), 0, new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadSoundModelCallback() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat$$ExternalSyntheticLambda0
            @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadSoundModelCallback
            public final void onValues(int i, int i2) {
                com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.lambda$loadSoundModel_2_0$7(atomicInteger, atomicInteger2, i, i2);
            }
        });
        handleHalStatus(atomicInteger.get(), "loadSoundModel");
        this.mModelCallbacks.put(java.lang.Integer.valueOf(atomicInteger2.get()), modelCallback);
        return atomicInteger2.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$loadSoundModel_2_0$7(java.util.concurrent.atomic.AtomicInteger atomicInteger, java.util.concurrent.atomic.AtomicInteger atomicInteger2, int i, int i2) {
        atomicInteger.set(i);
        atomicInteger2.set(i2);
    }

    private int loadPhraseSoundModel_2_0(android.hardware.soundtrigger.V2_1.ISoundTriggerHw.PhraseSoundModel phraseSoundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) throws android.os.RemoteException {
        android.hardware.soundtrigger.V2_0.ISoundTriggerHw.PhraseSoundModel convertPhraseSoundModel_2_1_to_2_0 = com.android.server.soundtrigger_middleware.Hw2CompatUtil.convertPhraseSoundModel_2_1_to_2_0(phraseSoundModel);
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(-1);
        final java.util.concurrent.atomic.AtomicInteger atomicInteger2 = new java.util.concurrent.atomic.AtomicInteger(0);
        as2_0().loadPhraseSoundModel(convertPhraseSoundModel_2_1_to_2_0, new com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.ModelCallbackWrapper(modelCallback), 0, new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadPhraseSoundModelCallback() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat$$ExternalSyntheticLambda4
            @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHw.loadPhraseSoundModelCallback
            public final void onValues(int i, int i2) {
                com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.lambda$loadPhraseSoundModel_2_0$8(atomicInteger, atomicInteger2, i, i2);
            }
        });
        handleHalStatus(atomicInteger.get(), "loadSoundModel");
        this.mModelCallbacks.put(java.lang.Integer.valueOf(atomicInteger2.get()), modelCallback);
        return atomicInteger2.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$loadPhraseSoundModel_2_0$8(java.util.concurrent.atomic.AtomicInteger atomicInteger, java.util.concurrent.atomic.AtomicInteger atomicInteger2, int i, int i2) {
        atomicInteger.set(i);
        atomicInteger2.set(i2);
    }

    private void startRecognition_2_1(int i, android.hardware.soundtrigger.V2_3.RecognitionConfig recognitionConfig) {
        try {
            try {
                handleHalStatus(as2_1().startRecognition_2_1(i, com.android.server.soundtrigger_middleware.Hw2CompatUtil.convertRecognitionConfig_2_3_to_2_1(recognitionConfig), new com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.ModelCallbackWrapper(this.mModelCallbacks.get(java.lang.Integer.valueOf(i))), 0), "startRecognition_2_1");
            } catch (com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported e) {
                startRecognition_2_0(i, recognitionConfig);
            }
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowAsRuntimeException();
        }
    }

    private void startRecognition_2_0(int i, android.hardware.soundtrigger.V2_3.RecognitionConfig recognitionConfig) throws android.os.RemoteException {
        handleHalStatus(as2_0().startRecognition(i, com.android.server.soundtrigger_middleware.Hw2CompatUtil.convertRecognitionConfig_2_3_to_2_0(recognitionConfig), new com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.ModelCallbackWrapper(this.mModelCallbacks.get(java.lang.Integer.valueOf(i))), 0), "startRecognition");
    }

    @android.annotation.NonNull
    private android.hardware.soundtrigger.V2_0.ISoundTriggerHw as2_0() {
        return this.mUnderlying_2_0;
    }

    @android.annotation.NonNull
    private android.hardware.soundtrigger.V2_1.ISoundTriggerHw as2_1() throws com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported {
        if (this.mUnderlying_2_1 == null) {
            throw new com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported("Underlying driver version < 2.1");
        }
        return this.mUnderlying_2_1;
    }

    @android.annotation.NonNull
    private android.hardware.soundtrigger.V2_2.ISoundTriggerHw as2_2() throws com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported {
        if (this.mUnderlying_2_2 == null) {
            throw new com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported("Underlying driver version < 2.2");
        }
        return this.mUnderlying_2_2;
    }

    @android.annotation.NonNull
    private android.hardware.soundtrigger.V2_3.ISoundTriggerHw as2_3() throws com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported {
        if (this.mUnderlying_2_3 == null) {
            throw new com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.NotSupported("Underlying driver version < 2.3");
        }
        return this.mUnderlying_2_3;
    }

    private static class NotSupported extends java.lang.Exception {
        NotSupported(java.lang.String str) {
            super(str);
        }

        com.android.server.soundtrigger_middleware.RecoverableException throwAsRecoverableException() {
            throw new com.android.server.soundtrigger_middleware.RecoverableException(2, getMessage());
        }
    }

    private static class ModelCallbackWrapper extends android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.Stub {

        @android.annotation.NonNull
        private final com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback mDelegate;

        private ModelCallbackWrapper(@android.annotation.NonNull com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
            java.util.Objects.requireNonNull(modelCallback);
            this.mDelegate = modelCallback;
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback
        public void recognitionCallback_2_1(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.RecognitionEvent recognitionEvent, int i) {
            android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys = new android.media.soundtrigger_middleware.RecognitionEventSys();
            recognitionEventSys.recognitionEvent = com.android.server.soundtrigger_middleware.ConversionUtil.hidl2aidlRecognitionEvent(recognitionEvent);
            recognitionEventSys.halEventReceivedMillis = android.os.SystemClock.elapsedRealtime();
            this.mDelegate.recognitionCallback(recognitionEvent.header.model, recognitionEventSys);
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback
        public void phraseRecognitionCallback_2_1(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.PhraseRecognitionEvent phraseRecognitionEvent, int i) {
            android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys = new android.media.soundtrigger_middleware.PhraseRecognitionEventSys();
            phraseRecognitionEventSys.phraseRecognitionEvent = com.android.server.soundtrigger_middleware.ConversionUtil.hidl2aidlPhraseRecognitionEvent(phraseRecognitionEvent);
            phraseRecognitionEventSys.halEventReceivedMillis = android.os.SystemClock.elapsedRealtime();
            this.mDelegate.phraseRecognitionCallback(phraseRecognitionEvent.common.header.model, phraseRecognitionEventSys);
        }

        @Override // android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback
        public void soundModelCallback_2_1(android.hardware.soundtrigger.V2_1.ISoundTriggerHwCallback.ModelEvent modelEvent, int i) {
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback
        public void recognitionCallback(android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.RecognitionEvent recognitionEvent, int i) {
            recognitionCallback_2_1(com.android.server.soundtrigger_middleware.Hw2CompatUtil.convertRecognitionEvent_2_0_to_2_1(recognitionEvent), i);
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback
        public void phraseRecognitionCallback(android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.PhraseRecognitionEvent phraseRecognitionEvent, int i) {
            phraseRecognitionCallback_2_1(com.android.server.soundtrigger_middleware.Hw2CompatUtil.convertPhraseRecognitionEvent_2_0_to_2_1(phraseRecognitionEvent), i);
        }

        @Override // android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback
        public void soundModelCallback(android.hardware.soundtrigger.V2_0.ISoundTriggerHwCallback.ModelEvent modelEvent, int i) {
        }
    }
}
