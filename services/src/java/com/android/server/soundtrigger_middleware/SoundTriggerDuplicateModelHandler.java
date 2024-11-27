package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class SoundTriggerDuplicateModelHandler implements com.android.server.soundtrigger_middleware.ISoundTriggerHal {

    @android.annotation.NonNull
    private final com.android.server.soundtrigger_middleware.ISoundTriggerHal mDelegate;
    private com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback mGlobalCallback;
    private final java.util.List<com.android.server.soundtrigger_middleware.SoundTriggerDuplicateModelHandler.ModelData> mModelList = new java.util.ArrayList();

    /* JADX INFO: Access modifiers changed from: private */
    static final class ModelData {
        private int mModelId;
        private java.lang.String mUuid;
        private boolean mWasContended = false;

        ModelData(int i, java.lang.String str) {
            this.mModelId = i;
            this.mUuid = str;
        }

        int getModelId() {
            return this.mModelId;
        }

        java.lang.String getUuid() {
            return this.mUuid;
        }

        boolean getWasContended() {
            return this.mWasContended;
        }

        void setWasContended() {
            this.mWasContended = true;
        }
    }

    public SoundTriggerDuplicateModelHandler(@android.annotation.NonNull com.android.server.soundtrigger_middleware.ISoundTriggerHal iSoundTriggerHal) {
        this.mDelegate = iSoundTriggerHal;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void reboot() {
        this.mDelegate.reboot();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void detach() {
        this.mDelegate.detach();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public android.media.soundtrigger.Properties getProperties() {
        return this.mDelegate.getProperties();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void registerCallback(com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback globalCallback) {
        this.mGlobalCallback = globalCallback;
        this.mDelegate.registerCallback(this.mGlobalCallback);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadSoundModel(android.media.soundtrigger.SoundModel soundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
        int loadSoundModel;
        synchronized (this) {
            checkDuplicateModelUuid(soundModel.uuid);
            loadSoundModel = this.mDelegate.loadSoundModel(soundModel, modelCallback);
            this.mModelList.add(new com.android.server.soundtrigger_middleware.SoundTriggerDuplicateModelHandler.ModelData(loadSoundModel, soundModel.uuid));
        }
        return loadSoundModel;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadPhraseSoundModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
        int loadPhraseSoundModel;
        synchronized (this) {
            checkDuplicateModelUuid(phraseSoundModel.common.uuid);
            loadPhraseSoundModel = this.mDelegate.loadPhraseSoundModel(phraseSoundModel, modelCallback);
            this.mModelList.add(new com.android.server.soundtrigger_middleware.SoundTriggerDuplicateModelHandler.ModelData(loadPhraseSoundModel, phraseSoundModel.common.uuid));
        }
        return loadPhraseSoundModel;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void unloadSoundModel(int i) {
        this.mDelegate.unloadSoundModel(i);
        for (int i2 = 0; i2 < this.mModelList.size(); i2++) {
            if (this.mModelList.get(i2).getModelId() == i) {
                if (this.mModelList.remove(i2).getWasContended()) {
                    this.mGlobalCallback.onResourcesAvailable();
                    return;
                }
                return;
            }
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void stopRecognition(int i) {
        this.mDelegate.stopRecognition(i);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void startRecognition(int i, int i2, int i3, android.media.soundtrigger.RecognitionConfig recognitionConfig) {
        this.mDelegate.startRecognition(i, i2, i3, recognitionConfig);
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
    public void linkToDeath(android.os.IBinder.DeathRecipient deathRecipient) {
        this.mDelegate.linkToDeath(deathRecipient);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void unlinkToDeath(android.os.IBinder.DeathRecipient deathRecipient) {
        this.mDelegate.unlinkToDeath(deathRecipient);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public java.lang.String interfaceDescriptor() {
        return this.mDelegate.interfaceDescriptor();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void flushCallbacks() {
        this.mDelegate.flushCallbacks();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void clientAttached(android.os.IBinder iBinder) {
        this.mDelegate.clientAttached(iBinder);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void clientDetached(android.os.IBinder iBinder) {
        this.mDelegate.clientDetached(iBinder);
    }

    private void checkDuplicateModelUuid(final java.lang.String str) {
        java.util.Optional<com.android.server.soundtrigger_middleware.SoundTriggerDuplicateModelHandler.ModelData> findFirst = this.mModelList.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerDuplicateModelHandler$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$checkDuplicateModelUuid$0;
                lambda$checkDuplicateModelUuid$0 = com.android.server.soundtrigger_middleware.SoundTriggerDuplicateModelHandler.lambda$checkDuplicateModelUuid$0(str, (com.android.server.soundtrigger_middleware.SoundTriggerDuplicateModelHandler.ModelData) obj);
                return lambda$checkDuplicateModelUuid$0;
            }
        }).findFirst();
        if (findFirst.isPresent()) {
            findFirst.get().setWasContended();
            throw new com.android.server.soundtrigger_middleware.RecoverableException(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$checkDuplicateModelUuid$0(java.lang.String str, com.android.server.soundtrigger_middleware.SoundTriggerDuplicateModelHandler.ModelData modelData) {
        return modelData.getUuid().equals(str);
    }
}
