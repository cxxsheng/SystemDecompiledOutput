package com.android.server.location.injector;

/* loaded from: classes2.dex */
public abstract class DeviceIdleHelper {
    private final java.util.concurrent.CopyOnWriteArrayList<com.android.server.location.injector.DeviceIdleHelper.DeviceIdleListener> mListeners = new java.util.concurrent.CopyOnWriteArrayList<>();

    public interface DeviceIdleListener {
        void onDeviceIdleChanged(boolean z);
    }

    public abstract boolean isDeviceIdle();

    protected abstract void registerInternal();

    protected abstract void unregisterInternal();

    protected DeviceIdleHelper() {
    }

    public final synchronized void addListener(com.android.server.location.injector.DeviceIdleHelper.DeviceIdleListener deviceIdleListener) {
        if (this.mListeners.add(deviceIdleListener) && this.mListeners.size() == 1) {
            registerInternal();
        }
    }

    public final synchronized void removeListener(com.android.server.location.injector.DeviceIdleHelper.DeviceIdleListener deviceIdleListener) {
        if (this.mListeners.remove(deviceIdleListener) && this.mListeners.isEmpty()) {
            unregisterInternal();
        }
    }

    protected final void notifyDeviceIdleChanged() {
        boolean isDeviceIdle = isDeviceIdle();
        java.util.Iterator<com.android.server.location.injector.DeviceIdleHelper.DeviceIdleListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onDeviceIdleChanged(isDeviceIdle);
        }
    }
}
