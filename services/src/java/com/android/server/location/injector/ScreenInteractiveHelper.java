package com.android.server.location.injector;

/* loaded from: classes2.dex */
public abstract class ScreenInteractiveHelper {
    private final java.util.concurrent.CopyOnWriteArrayList<com.android.server.location.injector.ScreenInteractiveHelper.ScreenInteractiveChangedListener> mListeners = new java.util.concurrent.CopyOnWriteArrayList<>();

    public interface ScreenInteractiveChangedListener {
        void onScreenInteractiveChanged(boolean z);
    }

    public abstract boolean isInteractive();

    public final void addListener(com.android.server.location.injector.ScreenInteractiveHelper.ScreenInteractiveChangedListener screenInteractiveChangedListener) {
        this.mListeners.add(screenInteractiveChangedListener);
    }

    public final void removeListener(com.android.server.location.injector.ScreenInteractiveHelper.ScreenInteractiveChangedListener screenInteractiveChangedListener) {
        this.mListeners.remove(screenInteractiveChangedListener);
    }

    protected final void notifyScreenInteractiveChanged(boolean z) {
        if (com.android.server.location.LocationManagerService.D) {
            android.util.Log.d(com.android.server.location.LocationManagerService.TAG, "screen interactive is now " + z);
        }
        java.util.Iterator<com.android.server.location.injector.ScreenInteractiveHelper.ScreenInteractiveChangedListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onScreenInteractiveChanged(z);
        }
    }
}
