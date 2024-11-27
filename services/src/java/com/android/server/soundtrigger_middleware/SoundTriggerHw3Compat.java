package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class SoundTriggerHw3Compat implements com.android.server.soundtrigger_middleware.ISoundTriggerHal {

    @android.annotation.NonNull
    private final android.hardware.soundtrigger3.ISoundTriggerHw mDriver;

    @android.annotation.NonNull
    private final java.lang.Runnable mRebootRunnable;

    public SoundTriggerHw3Compat(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.lang.Runnable runnable) {
        this.mDriver = android.hardware.soundtrigger3.ISoundTriggerHw.Stub.asInterface(iBinder);
        this.mRebootRunnable = runnable;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public android.media.soundtrigger.Properties getProperties() {
        try {
            return this.mDriver.getProperties();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void registerCallback(com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback globalCallback) {
        try {
            this.mDriver.registerGlobalCallback(new com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat.GlobalCallbackAdaper(globalCallback));
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadSoundModel(android.media.soundtrigger.SoundModel soundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
        try {
            return this.mDriver.loadSoundModel(soundModel, new com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat.ModelCallbackAdaper(modelCallback));
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 1) {
                throw new com.android.server.soundtrigger_middleware.RecoverableException(1);
            }
            throw e2;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadPhraseSoundModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
        try {
            return this.mDriver.loadPhraseSoundModel(phraseSoundModel, new com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat.ModelCallbackAdaper(modelCallback));
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 1) {
                throw new com.android.server.soundtrigger_middleware.RecoverableException(1);
            }
            throw e2;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void unloadSoundModel(int i) {
        try {
            this.mDriver.unloadSoundModel(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void startRecognition(int i, int i2, int i3, android.media.soundtrigger.RecognitionConfig recognitionConfig) {
        try {
            this.mDriver.startRecognition(i, i2, i3, recognitionConfig);
        } catch (android.os.ServiceSpecificException e) {
            if (e.errorCode == 1) {
                throw new com.android.server.soundtrigger_middleware.RecoverableException(1);
            }
            throw e;
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void stopRecognition(int i) {
        try {
            this.mDriver.stopRecognition(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void forceRecognitionEvent(int i) {
        try {
            this.mDriver.forceRecognitionEvent(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public android.media.soundtrigger.ModelParameterRange queryParameter(int i, int i2) {
        try {
            return this.mDriver.queryParameter(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int getModelParameter(int i, int i2) {
        try {
            return this.mDriver.getParameter(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void setModelParameter(int i, int i2, int i3) {
        try {
            this.mDriver.setParameter(i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public java.lang.String interfaceDescriptor() {
        try {
            return this.mDriver.asBinder().getInterfaceDescriptor();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void linkToDeath(android.os.IBinder.DeathRecipient deathRecipient) {
        try {
            this.mDriver.asBinder().linkToDeath(deathRecipient, 0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void unlinkToDeath(android.os.IBinder.DeathRecipient deathRecipient) {
        this.mDriver.asBinder().unlinkToDeath(deathRecipient, 0);
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

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void reboot() {
        this.mRebootRunnable.run();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void detach() {
    }

    private static class GlobalCallbackAdaper extends android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback.Stub {

        @android.annotation.NonNull
        private final com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback mDelegate;

        public GlobalCallbackAdaper(@android.annotation.NonNull com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback globalCallback) {
            this.mDelegate = globalCallback;
        }

        public void onResourcesAvailable() {
            this.mDelegate.onResourcesAvailable();
        }

        public int getInterfaceVersion() {
            return 2;
        }

        public java.lang.String getInterfaceHash() {
            return "6b24e60ad261e3ff56106efd86ce6aa7ef5621b0";
        }
    }

    private static class ModelCallbackAdaper extends android.hardware.soundtrigger3.ISoundTriggerHwCallback.Stub {

        @android.annotation.NonNull
        private final com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback mDelegate;

        public ModelCallbackAdaper(com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
            this.mDelegate = modelCallback;
        }

        public void modelUnloaded(int i) {
            this.mDelegate.modelUnloaded(i);
        }

        public void phraseRecognitionCallback(int i, android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent) {
            phraseRecognitionEvent.common.recognitionStillActive |= phraseRecognitionEvent.common.status == 3;
            android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys = new android.media.soundtrigger_middleware.PhraseRecognitionEventSys();
            phraseRecognitionEventSys.phraseRecognitionEvent = phraseRecognitionEvent;
            phraseRecognitionEventSys.halEventReceivedMillis = android.os.SystemClock.elapsedRealtimeNanos();
            this.mDelegate.phraseRecognitionCallback(i, phraseRecognitionEventSys);
        }

        public void recognitionCallback(int i, android.media.soundtrigger.RecognitionEvent recognitionEvent) {
            recognitionEvent.recognitionStillActive |= recognitionEvent.status == 3;
            android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys = new android.media.soundtrigger_middleware.RecognitionEventSys();
            recognitionEventSys.recognitionEvent = recognitionEvent;
            recognitionEventSys.halEventReceivedMillis = android.os.SystemClock.elapsedRealtimeNanos();
            this.mDelegate.recognitionCallback(i, recognitionEventSys);
        }

        public int getInterfaceVersion() {
            return 2;
        }

        public java.lang.String getInterfaceHash() {
            return "6b24e60ad261e3ff56106efd86ce6aa7ef5621b0";
        }
    }
}
