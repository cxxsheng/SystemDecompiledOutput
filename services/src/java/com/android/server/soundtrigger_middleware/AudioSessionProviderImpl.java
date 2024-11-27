package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
class AudioSessionProviderImpl extends com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl.AudioSessionProvider {
    @Override // com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl.AudioSessionProvider
    public native com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl.AudioSessionProvider.AudioSession acquireSession();

    @Override // com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareImpl.AudioSessionProvider
    public native void releaseSession(int i);

    AudioSessionProviderImpl() {
    }
}
