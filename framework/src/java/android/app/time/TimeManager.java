package android.app.time;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class TimeManager {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "time.TimeManager";
    private android.util.ArrayMap<android.app.time.TimeManager.TimeZoneDetectorListener, android.app.time.TimeManager.TimeZoneDetectorListener> mTimeZoneDetectorListeners;
    private android.app.time.ITimeZoneDetectorListener mTimeZoneDetectorReceiver;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.app.timezonedetector.ITimeZoneDetectorService mITimeZoneDetectorService = android.app.timezonedetector.ITimeZoneDetectorService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("time_zone_detector"));
    private final android.app.timedetector.ITimeDetectorService mITimeDetectorService = android.app.timedetector.ITimeDetectorService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("time_detector"));

    @java.lang.FunctionalInterface
    public interface TimeZoneDetectorListener {
        void onChange();
    }

    public android.app.time.TimeZoneCapabilitiesAndConfig getTimeZoneCapabilitiesAndConfig() {
        try {
            return this.mITimeZoneDetectorService.getCapabilitiesAndConfig();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.time.TimeCapabilitiesAndConfig getTimeCapabilitiesAndConfig() {
        try {
            return this.mITimeDetectorService.getCapabilitiesAndConfig();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateTimeConfiguration(android.app.time.TimeConfiguration timeConfiguration) {
        try {
            return this.mITimeDetectorService.updateConfiguration(timeConfiguration);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateTimeZoneConfiguration(android.app.time.TimeZoneConfiguration timeZoneConfiguration) {
        try {
            return this.mITimeZoneDetectorService.updateConfiguration(timeZoneConfiguration);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addTimeZoneDetectorListener(final java.util.concurrent.Executor executor, final android.app.time.TimeManager.TimeZoneDetectorListener timeZoneDetectorListener) {
        synchronized (this.mLock) {
            if (this.mTimeZoneDetectorListeners == null) {
                this.mTimeZoneDetectorListeners = new android.util.ArrayMap<>();
            } else if (this.mTimeZoneDetectorListeners.containsKey(timeZoneDetectorListener)) {
                return;
            }
            if (this.mTimeZoneDetectorReceiver == null) {
                this.mTimeZoneDetectorReceiver = new android.app.time.ITimeZoneDetectorListener.Stub() { // from class: android.app.time.TimeManager.1
                    @Override // android.app.time.ITimeZoneDetectorListener
                    public void onChange() {
                        android.app.time.TimeManager.this.notifyTimeZoneDetectorListeners();
                    }
                };
                try {
                    this.mITimeZoneDetectorService.addListener(this.mTimeZoneDetectorReceiver);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            this.mTimeZoneDetectorListeners.put(timeZoneDetectorListener, new android.app.time.TimeManager.TimeZoneDetectorListener() { // from class: android.app.time.TimeManager$$ExternalSyntheticLambda1
                @Override // android.app.time.TimeManager.TimeZoneDetectorListener
                public final void onChange() {
                    android.app.time.TimeManager.lambda$addTimeZoneDetectorListener$0(executor, timeZoneDetectorListener);
                }
            });
        }
    }

    static /* synthetic */ void lambda$addTimeZoneDetectorListener$0(java.util.concurrent.Executor executor, final android.app.time.TimeManager.TimeZoneDetectorListener timeZoneDetectorListener) {
        java.util.Objects.requireNonNull(timeZoneDetectorListener);
        executor.execute(new java.lang.Runnable() { // from class: android.app.time.TimeManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.app.time.TimeManager.TimeZoneDetectorListener.this.onChange();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTimeZoneDetectorListeners() {
        synchronized (this.mLock) {
            if (this.mTimeZoneDetectorListeners != null && !this.mTimeZoneDetectorListeners.isEmpty()) {
                android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mTimeZoneDetectorListeners);
                int size = arrayMap.size();
                for (int i = 0; i < size; i++) {
                    ((android.app.time.TimeManager.TimeZoneDetectorListener) arrayMap.valueAt(i)).onChange();
                }
            }
        }
    }

    public void removeTimeZoneDetectorListener(android.app.time.TimeManager.TimeZoneDetectorListener timeZoneDetectorListener) {
        synchronized (this.mLock) {
            if (this.mTimeZoneDetectorListeners != null && !this.mTimeZoneDetectorListeners.isEmpty()) {
                this.mTimeZoneDetectorListeners.remove(timeZoneDetectorListener);
                if (this.mTimeZoneDetectorListeners.isEmpty()) {
                    try {
                        try {
                            this.mITimeZoneDetectorService.removeListener(this.mTimeZoneDetectorReceiver);
                        } catch (android.os.RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    } finally {
                        this.mTimeZoneDetectorReceiver = null;
                    }
                }
            }
        }
    }

    public void suggestExternalTime(android.app.time.ExternalTimeSuggestion externalTimeSuggestion) {
        try {
            this.mITimeDetectorService.suggestExternalTime(externalTimeSuggestion);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.time.TimeState getTimeState() {
        try {
            return this.mITimeDetectorService.getTimeState();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean confirmTime(android.app.time.UnixEpochTime unixEpochTime) {
        try {
            return this.mITimeDetectorService.confirmTime(unixEpochTime);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setManualTime(android.app.time.UnixEpochTime unixEpochTime) {
        try {
            android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion = new android.app.timedetector.ManualTimeSuggestion(unixEpochTime);
            manualTimeSuggestion.addDebugInfo("TimeManager.setTime()");
            manualTimeSuggestion.addDebugInfo("UID: " + android.os.Process.myUid());
            manualTimeSuggestion.addDebugInfo("UserHandle: " + android.os.Process.myUserHandle());
            manualTimeSuggestion.addDebugInfo("Process: " + android.os.Process.myProcessName());
            return this.mITimeDetectorService.setManualTime(manualTimeSuggestion);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.time.TimeZoneState getTimeZoneState() {
        try {
            return this.mITimeZoneDetectorService.getTimeZoneState();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean confirmTimeZone(java.lang.String str) {
        try {
            return this.mITimeZoneDetectorService.confirmTimeZone(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setManualTimeZone(java.lang.String str) {
        try {
            android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion = new android.app.timezonedetector.ManualTimeZoneSuggestion(str);
            manualTimeZoneSuggestion.addDebugInfo("TimeManager.setManualTimeZone()");
            manualTimeZoneSuggestion.addDebugInfo("UID: " + android.os.Process.myUid());
            manualTimeZoneSuggestion.addDebugInfo("Process: " + android.os.Process.myProcessName());
            return this.mITimeZoneDetectorService.setManualTimeZone(manualTimeZoneSuggestion);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
