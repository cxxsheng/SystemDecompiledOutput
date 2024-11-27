package com.android.server.location.injector;

/* loaded from: classes2.dex */
public abstract class AppForegroundHelper {
    protected static final int FOREGROUND_IMPORTANCE_CUTOFF = 125;
    private final java.util.concurrent.CopyOnWriteArrayList<com.android.server.location.injector.AppForegroundHelper.AppForegroundListener> mListeners = new java.util.concurrent.CopyOnWriteArrayList<>();

    public interface AppForegroundListener {
        void onAppForegroundChanged(int i, boolean z);
    }

    public abstract boolean isAppForeground(int i);

    protected static boolean isForeground(int i) {
        return i <= 125;
    }

    public final void addListener(com.android.server.location.injector.AppForegroundHelper.AppForegroundListener appForegroundListener) {
        this.mListeners.add(appForegroundListener);
    }

    public final void removeListener(com.android.server.location.injector.AppForegroundHelper.AppForegroundListener appForegroundListener) {
        this.mListeners.remove(appForegroundListener);
    }

    protected final void notifyAppForeground(int i, boolean z) {
        java.util.Iterator<com.android.server.location.injector.AppForegroundHelper.AppForegroundListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onAppForegroundChanged(i, z);
        }
    }
}
