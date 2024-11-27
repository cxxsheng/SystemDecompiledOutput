package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public interface ISoundTriggerMiddlewareInternal {
    android.media.soundtrigger_middleware.ISoundTriggerModule attach(int i, android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback, boolean z);

    android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModules();
}
