package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class SoundTriggerHalEnforcer implements com.android.server.soundtrigger_middleware.ISoundTriggerHal {
    private static final java.lang.String TAG = "SoundTriggerHalEnforcer";
    private final java.util.Map<java.lang.Integer, com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelState> mModelStates = new java.util.HashMap();
    private final com.android.server.soundtrigger_middleware.ISoundTriggerHal mUnderlying;

    private enum ModelState {
        INACTIVE,
        ACTIVE,
        PENDING_STOP
    }

    public SoundTriggerHalEnforcer(com.android.server.soundtrigger_middleware.ISoundTriggerHal iSoundTriggerHal) {
        this.mUnderlying = iSoundTriggerHal;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public android.media.soundtrigger.Properties getProperties() {
        try {
            return this.mUnderlying.getProperties();
        } catch (java.lang.RuntimeException e) {
            throw handleException(e);
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void registerCallback(com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback globalCallback) {
        try {
            this.mUnderlying.registerCallback(globalCallback);
        } catch (java.lang.RuntimeException e) {
            throw handleException(e);
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadSoundModel(android.media.soundtrigger.SoundModel soundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
        int loadSoundModel;
        try {
            synchronized (this.mModelStates) {
                loadSoundModel = this.mUnderlying.loadSoundModel(soundModel, new com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelCallbackEnforcer(modelCallback));
                this.mModelStates.put(java.lang.Integer.valueOf(loadSoundModel), com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelState.INACTIVE);
            }
            return loadSoundModel;
        } catch (java.lang.RuntimeException e) {
            throw handleException(e);
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadPhraseSoundModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
        int loadPhraseSoundModel;
        try {
            synchronized (this.mModelStates) {
                loadPhraseSoundModel = this.mUnderlying.loadPhraseSoundModel(phraseSoundModel, new com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelCallbackEnforcer(modelCallback));
                this.mModelStates.put(java.lang.Integer.valueOf(loadPhraseSoundModel), com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelState.INACTIVE);
            }
            return loadPhraseSoundModel;
        } catch (java.lang.RuntimeException e) {
            throw handleException(e);
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void unloadSoundModel(int i) {
        try {
            this.mUnderlying.unloadSoundModel(i);
            synchronized (this.mModelStates) {
                this.mModelStates.remove(java.lang.Integer.valueOf(i));
            }
        } catch (java.lang.RuntimeException e) {
            throw handleException(e);
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void stopRecognition(int i) {
        try {
            synchronized (this.mModelStates) {
                this.mModelStates.replace(java.lang.Integer.valueOf(i), com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelState.PENDING_STOP);
            }
            this.mUnderlying.stopRecognition(i);
            synchronized (this.mModelStates) {
                this.mModelStates.replace(java.lang.Integer.valueOf(i), com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelState.INACTIVE);
            }
        } catch (java.lang.RuntimeException e) {
            throw handleException(e);
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void startRecognition(int i, int i2, int i3, android.media.soundtrigger.RecognitionConfig recognitionConfig) {
        try {
            synchronized (this.mModelStates) {
                this.mUnderlying.startRecognition(i, i2, i3, recognitionConfig);
                this.mModelStates.replace(java.lang.Integer.valueOf(i), com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelState.ACTIVE);
            }
        } catch (java.lang.RuntimeException e) {
            throw handleException(e);
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void forceRecognitionEvent(int i) {
        try {
            this.mUnderlying.forceRecognitionEvent(i);
        } catch (java.lang.RuntimeException e) {
            throw handleException(e);
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int getModelParameter(int i, int i2) {
        try {
            return this.mUnderlying.getModelParameter(i, i2);
        } catch (java.lang.RuntimeException e) {
            throw handleException(e);
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void setModelParameter(int i, int i2, int i3) {
        try {
            this.mUnderlying.setModelParameter(i, i2, i3);
        } catch (java.lang.RuntimeException e) {
            throw handleException(e);
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public android.media.soundtrigger.ModelParameterRange queryParameter(int i, int i2) {
        try {
            return this.mUnderlying.queryParameter(i, i2);
        } catch (java.lang.RuntimeException e) {
            throw handleException(e);
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void linkToDeath(android.os.IBinder.DeathRecipient deathRecipient) {
        this.mUnderlying.linkToDeath(deathRecipient);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void unlinkToDeath(android.os.IBinder.DeathRecipient deathRecipient) {
        this.mUnderlying.unlinkToDeath(deathRecipient);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public java.lang.String interfaceDescriptor() {
        return this.mUnderlying.interfaceDescriptor();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void flushCallbacks() {
        this.mUnderlying.flushCallbacks();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void clientAttached(android.os.IBinder iBinder) {
        this.mUnderlying.clientAttached(iBinder);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void clientDetached(android.os.IBinder iBinder) {
        this.mUnderlying.clientDetached(iBinder);
    }

    private java.lang.RuntimeException handleException(java.lang.RuntimeException runtimeException) {
        if (runtimeException instanceof com.android.server.soundtrigger_middleware.RecoverableException) {
            throw runtimeException;
        }
        if (runtimeException.getCause() instanceof android.os.DeadObjectException) {
            android.util.Slog.e(TAG, "HAL died");
            throw new com.android.server.soundtrigger_middleware.RecoverableException(4);
        }
        android.util.Slog.e(TAG, "Exception caught from HAL, rebooting HAL");
        reboot();
        throw runtimeException;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void reboot() {
        this.mUnderlying.reboot();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void detach() {
        this.mUnderlying.detach();
    }

    private class ModelCallbackEnforcer implements com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback {
        private final com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback mUnderlying;

        private ModelCallbackEnforcer(com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
            this.mUnderlying = modelCallback;
        }

        @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
        public void recognitionCallback(int i, android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys) {
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.mModelStates) {
                try {
                    if (((com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelState) com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.mModelStates.get(java.lang.Integer.valueOf(i))) == null) {
                        android.util.Slog.wtfStack(com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.TAG, "Unexpected recognition event for model: " + i);
                        com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.reboot();
                        return;
                    }
                    if (recognitionEventSys.recognitionEvent.recognitionStillActive && recognitionEventSys.recognitionEvent.status != 0 && recognitionEventSys.recognitionEvent.status != 3) {
                        android.util.Slog.wtfStack(com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.TAG, "recognitionStillActive is only allowed when the recognition status is SUCCESS");
                        com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.reboot();
                    } else {
                        if (!recognitionEventSys.recognitionEvent.recognitionStillActive) {
                            com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.mModelStates.replace(java.lang.Integer.valueOf(i), com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelState.INACTIVE);
                        }
                        this.mUnderlying.recognitionCallback(i, recognitionEventSys);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
        public void phraseRecognitionCallback(int i, android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys) {
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.mModelStates) {
                try {
                    if (((com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelState) com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.mModelStates.get(java.lang.Integer.valueOf(i))) == null) {
                        android.util.Slog.wtfStack(com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.TAG, "Unexpected recognition event for model: " + i);
                        com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.reboot();
                        return;
                    }
                    if (phraseRecognitionEventSys.phraseRecognitionEvent.common.recognitionStillActive && phraseRecognitionEventSys.phraseRecognitionEvent.common.status != 0 && phraseRecognitionEventSys.phraseRecognitionEvent.common.status != 3) {
                        android.util.Slog.wtfStack(com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.TAG, "recognitionStillActive is only allowed when the recognition status is SUCCESS");
                        com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.reboot();
                    } else {
                        if (!phraseRecognitionEventSys.phraseRecognitionEvent.common.recognitionStillActive) {
                            com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.mModelStates.replace(java.lang.Integer.valueOf(i), com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelState.INACTIVE);
                        }
                        this.mUnderlying.phraseRecognitionCallback(i, phraseRecognitionEventSys);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
        public void modelUnloaded(int i) {
            synchronized (com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.mModelStates) {
                try {
                    com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelState modelState = (com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelState) com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.mModelStates.get(java.lang.Integer.valueOf(i));
                    if (modelState == null) {
                        android.util.Slog.wtfStack(com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.TAG, "Unexpected unload event for model: " + i);
                        com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.reboot();
                        return;
                    }
                    if (modelState == com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.ModelState.ACTIVE) {
                        android.util.Slog.wtfStack(com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.TAG, "Trying to unload an active model: " + i);
                        com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.reboot();
                        return;
                    }
                    com.android.server.soundtrigger_middleware.SoundTriggerHalEnforcer.this.mModelStates.remove(java.lang.Integer.valueOf(i));
                    this.mUnderlying.modelUnloaded(i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
