package com.android.server.location.injector;

/* loaded from: classes2.dex */
public abstract class LocationPowerSaveModeHelper {
    private final java.util.concurrent.CopyOnWriteArrayList<com.android.server.location.injector.LocationPowerSaveModeHelper.LocationPowerSaveModeChangedListener> mListeners = new java.util.concurrent.CopyOnWriteArrayList<>();

    public interface LocationPowerSaveModeChangedListener {
        void onLocationPowerSaveModeChanged(int i);
    }

    public abstract int getLocationPowerSaveMode();

    public final void addListener(com.android.server.location.injector.LocationPowerSaveModeHelper.LocationPowerSaveModeChangedListener locationPowerSaveModeChangedListener) {
        this.mListeners.add(locationPowerSaveModeChangedListener);
    }

    public final void removeListener(com.android.server.location.injector.LocationPowerSaveModeHelper.LocationPowerSaveModeChangedListener locationPowerSaveModeChangedListener) {
        this.mListeners.remove(locationPowerSaveModeChangedListener);
    }

    protected final void notifyLocationPowerSaveModeChanged(int i) {
        if (com.android.server.location.LocationManagerService.D) {
            android.util.Log.d(com.android.server.location.LocationManagerService.TAG, "location power save mode is now " + android.os.PowerManager.locationPowerSaveModeToString(i));
        }
        com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logLocationPowerSaveMode(i);
        java.util.Iterator<com.android.server.location.injector.LocationPowerSaveModeHelper.LocationPowerSaveModeChangedListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onLocationPowerSaveModeChanged(i);
        }
    }
}
