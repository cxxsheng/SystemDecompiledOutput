package com.android.server.audio;

/* loaded from: classes.dex */
public class DefaultAudioPolicyFacade implements com.android.server.audio.AudioPolicyFacade, android.os.IBinder.DeathRecipient {
    private static final java.lang.String AUDIO_POLICY_SERVICE_NAME = "media.audio_policy";
    private static final java.lang.String TAG = "DefaultAudioPolicyFacade";

    @com.android.internal.annotations.GuardedBy({"mServiceLock"})
    private android.media.IAudioPolicyService mAudioPolicy;
    private final java.lang.Object mServiceLock = new java.lang.Object();

    public DefaultAudioPolicyFacade() {
        try {
            getAudioPolicyOrInit();
        } catch (java.lang.IllegalStateException e) {
            android.util.Log.e(TAG, "Failed to initialize APM connection", e);
        }
    }

    @Override // com.android.server.audio.AudioPolicyFacade
    public boolean isHotwordStreamSupported(boolean z) {
        android.media.IAudioPolicyService audioPolicyOrInit = getAudioPolicyOrInit();
        try {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                boolean isHotwordStreamSupported = audioPolicyOrInit.isHotwordStreamSupported(z);
                if (create != null) {
                    create.close();
                }
                return isHotwordStreamSupported;
            } finally {
            }
        } catch (android.os.RemoteException e) {
            resetServiceConnection(audioPolicyOrInit.asBinder());
            throw new java.lang.IllegalStateException(e);
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        android.util.Log.wtf(TAG, "Unexpected binderDied without IBinder object");
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied(@android.annotation.NonNull android.os.IBinder iBinder) {
        resetServiceConnection(iBinder);
    }

    private void resetServiceConnection(@android.annotation.Nullable android.os.IBinder iBinder) {
        synchronized (this.mServiceLock) {
            try {
                if (this.mAudioPolicy != null && this.mAudioPolicy.asBinder().equals(iBinder)) {
                    this.mAudioPolicy.asBinder().unlinkToDeath(this, 0);
                    this.mAudioPolicy = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    private android.media.IAudioPolicyService getAudioPolicy() {
        android.media.IAudioPolicyService iAudioPolicyService;
        synchronized (this.mServiceLock) {
            iAudioPolicyService = this.mAudioPolicy;
        }
        return iAudioPolicyService;
    }

    @android.annotation.NonNull
    private android.media.IAudioPolicyService getAudioPolicyOrInit() {
        synchronized (this.mServiceLock) {
            try {
                if (this.mAudioPolicy != null) {
                    return this.mAudioPolicy;
                }
                android.media.IAudioPolicyService asInterface = android.media.IAudioPolicyService.Stub.asInterface(android.os.ServiceManager.checkService(AUDIO_POLICY_SERVICE_NAME));
                if (asInterface == null) {
                    throw new java.lang.IllegalStateException("DefaultAudioPolicyFacade: Unable to connect to AudioPolicy");
                }
                try {
                    asInterface.asBinder().linkToDeath(this, 0);
                    this.mAudioPolicy = asInterface;
                    return this.mAudioPolicy;
                } catch (android.os.RemoteException e) {
                    throw new java.lang.IllegalStateException("DefaultAudioPolicyFacade: Unable to link deathListener to AudioPolicy", e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
