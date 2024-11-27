package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class SoundTriggerHalWatchdog implements com.android.server.soundtrigger_middleware.ISoundTriggerHal {
    private static final java.lang.String TAG = "SoundTriggerHalWatchdog";
    private static final long TIMEOUT_MS = 3000;

    @android.annotation.NonNull
    private final com.android.server.soundtrigger_middleware.UptimeTimer mTimer;

    @android.annotation.NonNull
    private final com.android.server.soundtrigger_middleware.ISoundTriggerHal mUnderlying;

    public SoundTriggerHalWatchdog(@android.annotation.NonNull com.android.server.soundtrigger_middleware.ISoundTriggerHal iSoundTriggerHal) {
        java.util.Objects.requireNonNull(iSoundTriggerHal);
        this.mUnderlying = iSoundTriggerHal;
        this.mTimer = new com.android.server.soundtrigger_middleware.UptimeTimer(TAG);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public android.media.soundtrigger.Properties getProperties() {
        com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog watchdog = new com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog();
        try {
            android.media.soundtrigger.Properties properties = this.mUnderlying.getProperties();
            watchdog.close();
            return properties;
        } catch (java.lang.Throwable th) {
            try {
                watchdog.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void registerCallback(com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback globalCallback) {
        com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog watchdog = new com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog();
        try {
            this.mUnderlying.registerCallback(globalCallback);
            watchdog.close();
        } catch (java.lang.Throwable th) {
            try {
                watchdog.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadSoundModel(android.media.soundtrigger.SoundModel soundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
        com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog watchdog = new com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog();
        try {
            int loadSoundModel = this.mUnderlying.loadSoundModel(soundModel, modelCallback);
            watchdog.close();
            return loadSoundModel;
        } catch (java.lang.Throwable th) {
            try {
                watchdog.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadPhraseSoundModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel, com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback modelCallback) {
        com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog watchdog = new com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog();
        try {
            int loadPhraseSoundModel = this.mUnderlying.loadPhraseSoundModel(phraseSoundModel, modelCallback);
            watchdog.close();
            return loadPhraseSoundModel;
        } catch (java.lang.Throwable th) {
            try {
                watchdog.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void unloadSoundModel(int i) {
        com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog watchdog = new com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog();
        try {
            this.mUnderlying.unloadSoundModel(i);
            watchdog.close();
        } catch (java.lang.Throwable th) {
            try {
                watchdog.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void stopRecognition(int i) {
        com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog watchdog = new com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog();
        try {
            this.mUnderlying.stopRecognition(i);
            watchdog.close();
        } catch (java.lang.Throwable th) {
            try {
                watchdog.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void startRecognition(int i, int i2, int i3, android.media.soundtrigger.RecognitionConfig recognitionConfig) {
        com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog watchdog = new com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog();
        try {
            this.mUnderlying.startRecognition(i, i2, i3, recognitionConfig);
            watchdog.close();
        } catch (java.lang.Throwable th) {
            try {
                watchdog.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void forceRecognitionEvent(int i) {
        com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog watchdog = new com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog();
        try {
            this.mUnderlying.forceRecognitionEvent(i);
            watchdog.close();
        } catch (java.lang.Throwable th) {
            try {
                watchdog.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int getModelParameter(int i, int i2) {
        com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog watchdog = new com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog();
        try {
            int modelParameter = this.mUnderlying.getModelParameter(i, i2);
            watchdog.close();
            return modelParameter;
        } catch (java.lang.Throwable th) {
            try {
                watchdog.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void setModelParameter(int i, int i2, int i3) {
        com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog watchdog = new com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog();
        try {
            this.mUnderlying.setModelParameter(i, i2, i3);
            watchdog.close();
        } catch (java.lang.Throwable th) {
            try {
                watchdog.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public android.media.soundtrigger.ModelParameterRange queryParameter(int i, int i2) {
        com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog watchdog = new com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog();
        try {
            android.media.soundtrigger.ModelParameterRange queryParameter = this.mUnderlying.queryParameter(i, i2);
            watchdog.close();
            return queryParameter;
        } catch (java.lang.Throwable th) {
            try {
                watchdog.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
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

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void reboot() {
        this.mUnderlying.reboot();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void detach() {
        this.mUnderlying.detach();
        this.mTimer.quit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Watchdog implements java.lang.AutoCloseable {

        @android.annotation.NonNull
        private final java.lang.Exception mException = new java.lang.Exception();

        @android.annotation.NonNull
        private final com.android.server.soundtrigger_middleware.UptimeTimer.Task mTask;

        Watchdog() {
            this.mTask = com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.this.mTimer.createTask(new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog$Watchdog$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.Watchdog.this.lambda$new$0();
                }
            }, 3000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            android.util.Slog.e(com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.TAG, "HAL deadline expired. Rebooting.", this.mException);
            com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog.this.reboot();
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mTask.cancel();
        }
    }
}
