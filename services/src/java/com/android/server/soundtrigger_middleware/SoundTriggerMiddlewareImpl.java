package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class SoundTriggerMiddlewareImpl implements com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal {
    private static final java.lang.String TAG = "SoundTriggerMiddlewareImpl";
    private final com.android.server.soundtrigger_middleware.SoundTriggerModule[] mModules;

    public static abstract class AudioSessionProvider {
        public abstract com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl.AudioSessionProvider.AudioSession acquireSession();

        public abstract void releaseSession(int i);

        public static final class AudioSession {
            final int mDeviceHandle;
            final int mIoHandle;
            final int mSessionHandle;

            AudioSession(int i, int i2, int i3) {
                this.mSessionHandle = i;
                this.mIoHandle = i2;
                this.mDeviceHandle = i3;
            }
        }
    }

    public SoundTriggerMiddlewareImpl(@android.annotation.NonNull com.android.server.soundtrigger_middleware.HalFactory[] halFactoryArr, @android.annotation.NonNull com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl.AudioSessionProvider audioSessionProvider) {
        java.util.ArrayList arrayList = new java.util.ArrayList(halFactoryArr.length);
        for (com.android.server.soundtrigger_middleware.HalFactory halFactory : halFactoryArr) {
            try {
                arrayList.add(new com.android.server.soundtrigger_middleware.SoundTriggerModule(halFactory, audioSessionProvider));
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Failed to add a SoundTriggerModule instance", e);
            }
        }
        this.mModules = (com.android.server.soundtrigger_middleware.SoundTriggerModule[]) arrayList.toArray(new com.android.server.soundtrigger_middleware.SoundTriggerModule[0]);
    }

    public SoundTriggerMiddlewareImpl(@android.annotation.NonNull com.android.server.soundtrigger_middleware.HalFactory halFactory, @android.annotation.NonNull com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl.AudioSessionProvider audioSessionProvider) {
        this(new com.android.server.soundtrigger_middleware.HalFactory[]{halFactory}, audioSessionProvider);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    @android.annotation.NonNull
    public android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModules() {
        android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] soundTriggerModuleDescriptorArr = new android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[this.mModules.length];
        for (int i = 0; i < this.mModules.length; i++) {
            android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor soundTriggerModuleDescriptor = new android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor();
            soundTriggerModuleDescriptor.handle = i;
            soundTriggerModuleDescriptor.properties = this.mModules[i].getProperties();
            soundTriggerModuleDescriptorArr[i] = soundTriggerModuleDescriptor;
        }
        return soundTriggerModuleDescriptorArr;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    @android.annotation.NonNull
    public android.media.soundtrigger_middleware.ISoundTriggerModule attach(int i, @android.annotation.NonNull android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
        return this.mModules[i].attach(iSoundTriggerCallback);
    }
}
