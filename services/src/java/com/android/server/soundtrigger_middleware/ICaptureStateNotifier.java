package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
interface ICaptureStateNotifier {

    public interface Listener {
        void onCaptureStateChange(boolean z);
    }

    boolean registerListener(@android.annotation.NonNull com.android.server.soundtrigger_middleware.ICaptureStateNotifier.Listener listener);

    void unregisterListener(@android.annotation.NonNull com.android.server.soundtrigger_middleware.ICaptureStateNotifier.Listener listener);
}
