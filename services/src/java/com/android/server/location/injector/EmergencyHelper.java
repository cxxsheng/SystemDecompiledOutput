package com.android.server.location.injector;

/* loaded from: classes2.dex */
public abstract class EmergencyHelper {
    private final java.util.concurrent.CopyOnWriteArrayList<com.android.server.location.injector.EmergencyHelper.EmergencyStateChangedListener> mListeners = new java.util.concurrent.CopyOnWriteArrayList<>();

    public interface EmergencyStateChangedListener {
        void onStateChanged();
    }

    public abstract boolean isInEmergency(long j);

    protected EmergencyHelper() {
    }

    public void addOnEmergencyStateChangedListener(com.android.server.location.injector.EmergencyHelper.EmergencyStateChangedListener emergencyStateChangedListener) {
        this.mListeners.add(emergencyStateChangedListener);
    }

    public void removeOnEmergencyStateChangedListener(com.android.server.location.injector.EmergencyHelper.EmergencyStateChangedListener emergencyStateChangedListener) {
        this.mListeners.remove(emergencyStateChangedListener);
    }

    protected final void dispatchEmergencyStateChanged() {
        java.util.Iterator<com.android.server.location.injector.EmergencyHelper.EmergencyStateChangedListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onStateChanged();
        }
    }
}
