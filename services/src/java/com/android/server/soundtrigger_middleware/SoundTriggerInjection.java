package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class SoundTriggerInjection implements android.media.soundtrigger_middleware.ISoundTriggerInjection, android.os.IBinder.DeathRecipient {
    private static final java.lang.String TAG = "SoundTriggerInjection";
    private final java.lang.Object mClientLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mClientLock"})
    private android.media.soundtrigger_middleware.ISoundTriggerInjection mClient = null;

    @com.android.internal.annotations.GuardedBy({"mClientLock"})
    private android.media.soundtrigger_middleware.IInjectGlobalEvent mGlobalEventInjection = null;

    public void registerClient(android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) {
        synchronized (this.mClientLock) {
            java.util.Objects.requireNonNull(iSoundTriggerInjection);
            if (this.mClient != null) {
                try {
                    this.mClient.onPreempted();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "RemoteException when handling preemption", e);
                }
                this.mClient.asBinder().unlinkToDeath(this, 0);
            }
            this.mClient = iSoundTriggerInjection;
            try {
                this.mClient.asBinder().linkToDeath(this, 0);
                if (this.mGlobalEventInjection != null) {
                    this.mClient.registerGlobalEventInjection(this.mGlobalEventInjection);
                }
            } catch (android.os.RemoteException e2) {
                this.mClient = null;
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        android.util.Slog.wtf(TAG, "Binder died without params");
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied(android.os.IBinder iBinder) {
        synchronized (this.mClientLock) {
            try {
                if (this.mClient != null && iBinder == this.mClient.asBinder()) {
                    this.mClient = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void registerGlobalEventInjection(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) {
        synchronized (this.mClientLock) {
            this.mGlobalEventInjection = iInjectGlobalEvent;
            if (this.mClient == null) {
                return;
            }
            try {
                this.mClient.registerGlobalEventInjection(this.mGlobalEventInjection);
            } catch (android.os.RemoteException e) {
                this.mClient = null;
            }
        }
    }

    public void onRestarted(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) {
        synchronized (this.mClientLock) {
            if (this.mClient == null) {
                return;
            }
            try {
                this.mClient.onRestarted(iInjectGlobalEvent);
            } catch (android.os.RemoteException e) {
                this.mClient = null;
            }
        }
    }

    public void onFrameworkDetached(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) {
        synchronized (this.mClientLock) {
            if (this.mClient == null) {
                return;
            }
            try {
                this.mClient.onFrameworkDetached(iInjectGlobalEvent);
            } catch (android.os.RemoteException e) {
                this.mClient = null;
            }
        }
    }

    public void onClientAttached(android.os.IBinder iBinder, android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) {
        synchronized (this.mClientLock) {
            if (this.mClient == null) {
                return;
            }
            try {
                this.mClient.onClientAttached(iBinder, iInjectGlobalEvent);
            } catch (android.os.RemoteException e) {
                this.mClient = null;
            }
        }
    }

    public void onClientDetached(android.os.IBinder iBinder) {
        synchronized (this.mClientLock) {
            if (this.mClient == null) {
                return;
            }
            try {
                this.mClient.onClientDetached(iBinder);
            } catch (android.os.RemoteException e) {
                this.mClient = null;
            }
        }
    }

    public void onSoundModelLoaded(android.media.soundtrigger.SoundModel soundModel, @android.annotation.Nullable android.media.soundtrigger.Phrase[] phraseArr, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent, android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) {
        synchronized (this.mClientLock) {
            if (this.mClient == null) {
                return;
            }
            try {
                this.mClient.onSoundModelLoaded(soundModel, phraseArr, iInjectModelEvent, iInjectGlobalEvent);
            } catch (android.os.RemoteException e) {
                this.mClient = null;
            }
        }
    }

    public void onParamSet(int i, int i2, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) {
        synchronized (this.mClientLock) {
            if (this.mClient == null) {
                return;
            }
            try {
                this.mClient.onParamSet(i, i2, iInjectModelEvent);
            } catch (android.os.RemoteException e) {
                this.mClient = null;
            }
        }
    }

    public void onRecognitionStarted(int i, android.media.soundtrigger.RecognitionConfig recognitionConfig, android.media.soundtrigger_middleware.IInjectRecognitionEvent iInjectRecognitionEvent, android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) {
        synchronized (this.mClientLock) {
            if (this.mClient == null) {
                return;
            }
            try {
                this.mClient.onRecognitionStarted(i, recognitionConfig, iInjectRecognitionEvent, iInjectModelEvent);
            } catch (android.os.RemoteException e) {
                this.mClient = null;
            }
        }
    }

    public void onRecognitionStopped(android.media.soundtrigger_middleware.IInjectRecognitionEvent iInjectRecognitionEvent) {
        synchronized (this.mClientLock) {
            if (this.mClient == null) {
                return;
            }
            try {
                this.mClient.onRecognitionStopped(iInjectRecognitionEvent);
            } catch (android.os.RemoteException e) {
                this.mClient = null;
            }
        }
    }

    public void onSoundModelUnloaded(android.media.soundtrigger_middleware.IInjectModelEvent iInjectModelEvent) {
        synchronized (this.mClientLock) {
            if (this.mClient == null) {
                return;
            }
            try {
                this.mClient.onSoundModelUnloaded(iInjectModelEvent);
            } catch (android.os.RemoteException e) {
                this.mClient = null;
            }
        }
    }

    public void onPreempted() {
        android.util.Slog.wtf(TAG, "Unexpected preempted!");
    }

    public android.os.IBinder asBinder() {
        android.util.Slog.wtf(TAG, "Unexpected asBinder!");
        throw new java.lang.UnsupportedOperationException("Calling asBinder on a fake binder object");
    }
}
