package com.android.server.display;

/* loaded from: classes.dex */
class DisplayDeviceRepository implements com.android.server.display.DisplayAdapter.Listener {
    public static final int DISPLAY_DEVICE_EVENT_ADDED = 1;
    public static final int DISPLAY_DEVICE_EVENT_REMOVED = 3;

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private final java.util.List<com.android.server.display.DisplayDevice> mDisplayDevices = new java.util.ArrayList();

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private final java.util.List<com.android.server.display.DisplayDeviceRepository.Listener> mListeners = new java.util.ArrayList();
    private final com.android.server.display.PersistentDataStore mPersistentDataStore;
    private final com.android.server.display.DisplayManagerService.SyncRoot mSyncRoot;
    private static final java.lang.String TAG = "DisplayDeviceRepository";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);

    public interface Listener {
        void onDisplayDeviceChangedLocked(com.android.server.display.DisplayDevice displayDevice, int i);

        void onDisplayDeviceEventLocked(com.android.server.display.DisplayDevice displayDevice, int i);

        void onTraversalRequested();
    }

    DisplayDeviceRepository(@android.annotation.NonNull com.android.server.display.DisplayManagerService.SyncRoot syncRoot, @android.annotation.NonNull com.android.server.display.PersistentDataStore persistentDataStore) {
        this.mSyncRoot = syncRoot;
        this.mPersistentDataStore = persistentDataStore;
    }

    public void addListener(@android.annotation.NonNull com.android.server.display.DisplayDeviceRepository.Listener listener) {
        this.mListeners.add(listener);
    }

    @Override // com.android.server.display.DisplayAdapter.Listener
    public void onDisplayDeviceEvent(com.android.server.display.DisplayDevice displayDevice, int i) {
        java.lang.String str;
        if (!DEBUG) {
            str = null;
        } else {
            str = "DisplayDeviceRepository#onDisplayDeviceEvent (event=" + i + ")";
            android.os.Trace.beginAsyncSection(str, 0);
        }
        switch (i) {
            case 1:
                handleDisplayDeviceAdded(displayDevice);
                break;
            case 2:
                handleDisplayDeviceChanged(displayDevice);
                break;
            case 3:
                handleDisplayDeviceRemoved(displayDevice);
                break;
        }
        if (DEBUG) {
            android.os.Trace.endAsyncSection(str, 0);
        }
    }

    @Override // com.android.server.display.DisplayAdapter.Listener
    public void onTraversalRequested() {
        int size = this.mListeners.size();
        for (int i = 0; i < size; i++) {
            this.mListeners.get(i).onTraversalRequested();
        }
    }

    public boolean containsLocked(com.android.server.display.DisplayDevice displayDevice) {
        return this.mDisplayDevices.contains(displayDevice);
    }

    public int sizeLocked() {
        return this.mDisplayDevices.size();
    }

    public void forEachLocked(java.util.function.Consumer<com.android.server.display.DisplayDevice> consumer) {
        int size = this.mDisplayDevices.size();
        for (int i = 0; i < size; i++) {
            consumer.accept(this.mDisplayDevices.get(i));
        }
    }

    public com.android.server.display.DisplayDevice getByAddressLocked(@android.annotation.NonNull android.view.DisplayAddress displayAddress) {
        for (int size = this.mDisplayDevices.size() - 1; size >= 0; size--) {
            com.android.server.display.DisplayDevice displayDevice = this.mDisplayDevices.get(size);
            com.android.server.display.DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
            if (displayAddress.equals(displayDeviceInfoLocked.address) || android.view.DisplayAddress.Physical.isPortMatch(displayAddress, displayDeviceInfoLocked.address)) {
                return displayDevice;
            }
        }
        return null;
    }

    public com.android.server.display.DisplayDevice getByUniqueIdLocked(@android.annotation.NonNull java.lang.String str) {
        for (int size = this.mDisplayDevices.size() - 1; size >= 0; size--) {
            com.android.server.display.DisplayDevice displayDevice = this.mDisplayDevices.get(size);
            if (displayDevice.getUniqueId().equals(str)) {
                return displayDevice;
            }
        }
        return null;
    }

    private void handleDisplayDeviceAdded(com.android.server.display.DisplayDevice displayDevice) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
                if (this.mDisplayDevices.contains(displayDevice)) {
                    android.util.Slog.w(TAG, "Attempted to add already added display device: " + displayDeviceInfoLocked);
                    return;
                }
                android.util.Slog.i(TAG, "Display device added: " + displayDeviceInfoLocked);
                displayDevice.mDebugLastLoggedDeviceInfo = displayDeviceInfoLocked;
                this.mDisplayDevices.add(displayDevice);
                sendEventLocked(displayDevice, 1);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void handleDisplayDeviceChanged(com.android.server.display.DisplayDevice displayDevice) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
                if (!this.mDisplayDevices.contains(displayDevice)) {
                    android.util.Slog.w(TAG, "Attempted to change non-existent display device: " + displayDeviceInfoLocked);
                    return;
                }
                if (DEBUG) {
                    android.os.Trace.traceBegin(131072L, "handleDisplayDeviceChanged");
                }
                int diff = displayDevice.mDebugLastLoggedDeviceInfo.diff(displayDeviceInfoLocked);
                if (diff == 1) {
                    android.util.Slog.i(TAG, "Display device changed state: \"" + displayDeviceInfoLocked.name + "\", " + android.view.Display.stateToString(displayDeviceInfoLocked.state));
                } else if (diff != 8) {
                    android.util.Slog.i(TAG, "Display device changed: " + displayDeviceInfoLocked);
                }
                if ((diff & 4) != 0) {
                    try {
                        this.mPersistentDataStore.setColorMode(displayDevice, displayDeviceInfoLocked.colorMode);
                        this.mPersistentDataStore.saveIfNeeded();
                    } catch (java.lang.Throwable th) {
                        this.mPersistentDataStore.saveIfNeeded();
                        throw th;
                    }
                }
                displayDevice.mDebugLastLoggedDeviceInfo = displayDeviceInfoLocked;
                displayDevice.applyPendingDisplayDeviceInfoChangesLocked();
                sendChangedEventLocked(displayDevice, diff);
                if (DEBUG) {
                    android.os.Trace.traceEnd(131072L);
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    private void handleDisplayDeviceRemoved(com.android.server.display.DisplayDevice displayDevice) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
                if (!this.mDisplayDevices.remove(displayDevice)) {
                    android.util.Slog.w(TAG, "Attempted to remove non-existent display device: " + displayDeviceInfoLocked);
                    return;
                }
                android.util.Slog.i(TAG, "Display device removed: " + displayDeviceInfoLocked);
                displayDevice.mDebugLastLoggedDeviceInfo = displayDeviceInfoLocked;
                sendEventLocked(displayDevice, 3);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void sendEventLocked(com.android.server.display.DisplayDevice displayDevice, int i) {
        int size = this.mListeners.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mListeners.get(i2).onDisplayDeviceEventLocked(displayDevice, i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private void sendChangedEventLocked(com.android.server.display.DisplayDevice displayDevice, int i) {
        int size = this.mListeners.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mListeners.get(i2).onDisplayDeviceChangedLocked(displayDevice, i);
        }
    }
}
