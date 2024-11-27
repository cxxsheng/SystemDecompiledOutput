package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class SoundTriggerHalMaxModelLimiter implements com.android.server.soundtrigger_middleware.ISoundTriggerHal {

    @android.annotation.NonNull
    private final com.android.server.soundtrigger_middleware.ISoundTriggerHal mDelegate;
    private com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback mGlobalCallback;
    private final int mMaxModels;
    private int mNumLoadedModels = 0;

    public SoundTriggerHalMaxModelLimiter(com.android.server.soundtrigger_middleware.ISoundTriggerHal iSoundTriggerHal, int i) {
        this.mDelegate = iSoundTriggerHal;
        this.mMaxModels = i;
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
            try {
                if (this.mNumLoadedModels == this.mMaxModels) {
                    throw new com.android.server.soundtrigger_middleware.RecoverableException(1);
                }
                loadSoundModel = this.mDelegate.loadSoundModel(soundModel, modelCallback);
                this.mNumLoadedModels++;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return loadSoundModel;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadPhraseSoundModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
        int loadPhraseSoundModel;
        synchronized (this) {
            try {
                if (this.mNumLoadedModels == this.mMaxModels) {
                    throw new com.android.server.soundtrigger_middleware.RecoverableException(1);
                }
                loadPhraseSoundModel = this.mDelegate.loadPhraseSoundModel(phraseSoundModel, modelCallback);
                this.mNumLoadedModels++;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return loadPhraseSoundModel;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void unloadSoundModel(int i) {
        boolean z;
        synchronized (this) {
            int i2 = this.mNumLoadedModels;
            this.mNumLoadedModels = i2 - 1;
            z = i2 == this.mMaxModels;
        }
        try {
            this.mDelegate.unloadSoundModel(i);
            if (z) {
                this.mGlobalCallback.onResourcesAvailable();
            }
        } catch (java.lang.Exception e) {
            synchronized (this) {
                this.mNumLoadedModels++;
                throw e;
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
}
