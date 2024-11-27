package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
class DeviceActivityMonitorImpl implements com.android.server.timezonedetector.DeviceActivityMonitor {
    private static final boolean DBG = false;
    private static final java.lang.String LOG_TAG = "time_zone_detector";

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.List<com.android.server.timezonedetector.DeviceActivityMonitor.Listener> mListeners = new java.util.ArrayList();

    static com.android.server.timezonedetector.DeviceActivityMonitor create(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler) {
        return new com.android.server.timezonedetector.DeviceActivityMonitorImpl(context, handler);
    }

    private DeviceActivityMonitorImpl(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler) {
        final android.content.ContentResolver contentResolver = context.getContentResolver();
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("airplane_mode_on"), true, new android.database.ContentObserver(handler) { // from class: com.android.server.timezonedetector.DeviceActivityMonitorImpl.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                try {
                    if (android.provider.Settings.Global.getInt(contentResolver, "airplane_mode_on") == 0) {
                        com.android.server.timezonedetector.DeviceActivityMonitorImpl.this.notifyFlightComplete();
                    }
                } catch (android.provider.Settings.SettingNotFoundException e) {
                    android.util.Slog.e(com.android.server.timezonedetector.DeviceActivityMonitorImpl.LOG_TAG, "Unable to read airplane mode state", e);
                }
            }
        });
    }

    @Override // com.android.server.timezonedetector.DeviceActivityMonitor
    public synchronized void addListener(com.android.server.timezonedetector.DeviceActivityMonitor.Listener listener) {
        java.util.Objects.requireNonNull(listener);
        this.mListeners.add(listener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyFlightComplete() {
        java.util.ArrayList arrayList;
        synchronized (this) {
            arrayList = new java.util.ArrayList(this.mListeners);
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((com.android.server.timezonedetector.DeviceActivityMonitor.Listener) it.next()).onFlightComplete();
        }
    }

    @Override // com.android.server.timezonedetector.Dumpable
    public void dump(android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String[] strArr) {
    }
}
