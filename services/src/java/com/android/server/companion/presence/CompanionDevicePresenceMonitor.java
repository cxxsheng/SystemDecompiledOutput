package com.android.server.companion.presence;

@android.annotation.SuppressLint({"LongLogTag"})
/* loaded from: classes.dex */
public class CompanionDevicePresenceMonitor implements com.android.server.companion.AssociationStore.OnChangeListener, com.android.server.companion.presence.BluetoothCompanionDeviceConnectionListener.Callback, com.android.server.companion.presence.BleCompanionDeviceScanner.Callback {
    static final boolean DEBUG = false;
    private static final java.lang.String TAG = "CDM_CompanionDevicePresenceMonitor";

    @android.annotation.NonNull
    private final com.android.server.companion.AssociationStore mAssociationStore;

    @android.annotation.NonNull
    private final com.android.server.companion.presence.BleCompanionDeviceScanner mBleScanner;

    @android.annotation.NonNull
    private final com.android.server.companion.presence.BluetoothCompanionDeviceConnectionListener mBtConnectionListener;

    @android.annotation.NonNull
    private final com.android.server.companion.presence.CompanionDevicePresenceMonitor.Callback mCallback;

    @android.annotation.NonNull
    private final com.android.server.companion.presence.ObservableUuidStore mObservableUuidStore;

    @android.annotation.NonNull
    private final java.util.Set<java.lang.Integer> mConnectedBtDevices = new java.util.HashSet();

    @android.annotation.NonNull
    private final java.util.Set<java.lang.Integer> mNearbyBleDevices = new java.util.HashSet();

    @android.annotation.NonNull
    private final java.util.Set<java.lang.Integer> mReportedSelfManagedDevices = new java.util.HashSet();

    @android.annotation.NonNull
    private final java.util.Set<android.os.ParcelUuid> mConnectedUuidDevices = new java.util.HashSet();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mBtDisconnectedDevices"})
    private final java.util.Set<java.lang.Integer> mBtDisconnectedDevices = new java.util.HashSet();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mBtDisconnectedDevices"})
    private final android.util.SparseBooleanArray mBtDisconnectedDevicesBlePresence = new android.util.SparseBooleanArray();

    @android.annotation.NonNull
    private final java.util.Set<java.lang.Integer> mSimulated = new java.util.HashSet();
    private final com.android.server.companion.presence.CompanionDevicePresenceMonitor.SimulatedDevicePresenceSchedulerHelper mSchedulerHelper = new com.android.server.companion.presence.CompanionDevicePresenceMonitor.SimulatedDevicePresenceSchedulerHelper();
    private final com.android.server.companion.presence.CompanionDevicePresenceMonitor.BleDeviceDisappearedScheduler mBleDeviceDisappearedScheduler = new com.android.server.companion.presence.CompanionDevicePresenceMonitor.BleDeviceDisappearedScheduler();

    public interface Callback {
        void onDeviceAppeared(int i);

        void onDeviceDisappeared(int i);

        void onDevicePresenceEvent(int i, int i2);

        void onDevicePresenceEventByUuid(com.android.server.companion.presence.ObservableUuid observableUuid, int i);
    }

    public CompanionDevicePresenceMonitor(android.os.UserManager userManager, @android.annotation.NonNull com.android.server.companion.AssociationStore associationStore, @android.annotation.NonNull com.android.server.companion.presence.ObservableUuidStore observableUuidStore, @android.annotation.NonNull com.android.server.companion.presence.CompanionDevicePresenceMonitor.Callback callback) {
        this.mAssociationStore = associationStore;
        this.mObservableUuidStore = observableUuidStore;
        this.mCallback = callback;
        this.mBtConnectionListener = new com.android.server.companion.presence.BluetoothCompanionDeviceConnectionListener(userManager, associationStore, this.mObservableUuidStore, this);
        this.mBleScanner = new com.android.server.companion.presence.BleCompanionDeviceScanner(associationStore, this);
    }

    public void init(android.content.Context context) {
        android.bluetooth.BluetoothAdapter defaultAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            this.mBtConnectionListener.init(defaultAdapter);
            this.mBleScanner.init(context, defaultAdapter);
        } else {
            android.util.Log.w(TAG, "BluetoothAdapter is NOT available.");
        }
        this.mAssociationStore.registerListener(this);
    }

    public java.util.Set<android.os.ParcelUuid> getCurrentConnectedUuidDevices() {
        return this.mConnectedUuidDevices;
    }

    public void removeCurrentConnectedUuidDevice(android.os.ParcelUuid parcelUuid) {
        this.mConnectedUuidDevices.remove(parcelUuid);
    }

    public boolean isDevicePresent(int i) {
        return this.mReportedSelfManagedDevices.contains(java.lang.Integer.valueOf(i)) || this.mConnectedBtDevices.contains(java.lang.Integer.valueOf(i)) || this.mNearbyBleDevices.contains(java.lang.Integer.valueOf(i)) || this.mSimulated.contains(java.lang.Integer.valueOf(i));
    }

    public boolean isDeviceUuidPresent(android.os.ParcelUuid parcelUuid) {
        return this.mConnectedUuidDevices.contains(parcelUuid);
    }

    public boolean isBtConnected(int i) {
        return this.mConnectedBtDevices.contains(java.lang.Integer.valueOf(i));
    }

    public boolean isBlePresent(int i) {
        return this.mNearbyBleDevices.contains(java.lang.Integer.valueOf(i));
    }

    public boolean isSimulatePresent(int i) {
        return this.mSimulated.contains(java.lang.Integer.valueOf(i));
    }

    public void onSelfManagedDeviceConnected(int i) {
        onDevicePresenceEvent(this.mReportedSelfManagedDevices, i, 4);
    }

    public void onSelfManagedDeviceDisconnected(int i) {
        onDevicePresenceEvent(this.mReportedSelfManagedDevices, i, 5);
    }

    public void onSelfManagedDeviceReporterBinderDied(int i) {
        onDevicePresenceEvent(this.mReportedSelfManagedDevices, i, 5);
    }

    @Override // com.android.server.companion.presence.BluetoothCompanionDeviceConnectionListener.Callback
    public void onBluetoothCompanionDeviceConnected(int i) {
        synchronized (this.mBtDisconnectedDevices) {
            try {
                if (this.mBtDisconnectedDevices.contains(java.lang.Integer.valueOf(i))) {
                    android.util.Slog.i(TAG, "Device ( " + i + " ) is reconnected within 10s.");
                    this.mBleDeviceDisappearedScheduler.unScheduleDeviceDisappeared(i);
                }
                android.util.Slog.i(TAG, "onBluetoothCompanionDeviceConnected: associationId( " + i + " )");
                onDevicePresenceEvent(this.mConnectedBtDevices, i, 2);
                if (canStopBleScan()) {
                    this.mBleScanner.stopScanIfNeeded();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.companion.presence.BluetoothCompanionDeviceConnectionListener.Callback
    public void onBluetoothCompanionDeviceDisconnected(int i) {
        android.util.Slog.i(TAG, "onBluetoothCompanionDeviceDisconnected associationId( " + i + " )");
        this.mBleScanner.startScan();
        onDevicePresenceEvent(this.mConnectedBtDevices, i, 3);
        if (isBlePresent(i)) {
            synchronized (this.mBtDisconnectedDevices) {
                this.mBtDisconnectedDevices.add(java.lang.Integer.valueOf(i));
            }
            this.mBleDeviceDisappearedScheduler.scheduleBleDeviceDisappeared(i);
        }
    }

    @Override // com.android.server.companion.presence.BluetoothCompanionDeviceConnectionListener.Callback
    public void onDevicePresenceEventByUuid(com.android.server.companion.presence.ObservableUuid observableUuid, int i) {
        android.os.ParcelUuid uuid = observableUuid.getUuid();
        switch (i) {
            case 2:
                if (!this.mConnectedUuidDevices.add(uuid)) {
                    android.util.Slog.w(TAG, "Uuid= " + uuid + "is ALREADY reported as present by this event=" + i);
                    break;
                }
                break;
            case 3:
                if (!this.mConnectedUuidDevices.remove(uuid)) {
                    android.util.Slog.w(TAG, "UUID= " + uuid + " was NOT reported as present by this event= " + i);
                    return;
                }
                break;
        }
        this.mCallback.onDevicePresenceEventByUuid(observableUuid, i);
    }

    @Override // com.android.server.companion.presence.BleCompanionDeviceScanner.Callback
    public void onBleCompanionDeviceFound(int i) {
        onDevicePresenceEvent(this.mNearbyBleDevices, i, 0);
        synchronized (this.mBtDisconnectedDevices) {
            try {
                boolean z = this.mBtDisconnectedDevicesBlePresence.get(i);
                if (this.mBtDisconnectedDevices.contains(java.lang.Integer.valueOf(i)) && z) {
                    this.mBleDeviceDisappearedScheduler.unScheduleDeviceDisappeared(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.companion.presence.BleCompanionDeviceScanner.Callback
    public void onBleCompanionDeviceLost(int i) {
        onDevicePresenceEvent(this.mNearbyBleDevices, i, 1);
    }

    public void simulateDeviceEvent(int i, int i2) {
        enforceCallerShellOrRoot();
        enforceAssociationExists(i);
        switch (i2) {
            case 0:
                simulateDeviceAppeared(i, i2);
                return;
            case 1:
                simulateDeviceDisappeared(i, i2);
                return;
            case 2:
                onBluetoothCompanionDeviceConnected(i);
                return;
            case 3:
                onBluetoothCompanionDeviceDisconnected(i);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Event: " + i2 + "is not supported");
        }
    }

    public void simulateDeviceEventByUuid(com.android.server.companion.presence.ObservableUuid observableUuid, int i) {
        enforceCallerShellOrRoot();
        onDevicePresenceEventByUuid(observableUuid, i);
    }

    private void simulateDeviceAppeared(int i, int i2) {
        onDevicePresenceEvent(this.mSimulated, i, i2);
        this.mSchedulerHelper.scheduleOnDeviceGoneCallForSimulatedDevicePresence(i);
    }

    private void simulateDeviceDisappeared(int i, int i2) {
        this.mSchedulerHelper.unscheduleOnDeviceGoneCallForSimulatedDevicePresence(i);
        onDevicePresenceEvent(this.mSimulated, i, i2);
    }

    private void enforceAssociationExists(int i) {
        if (this.mAssociationStore.getAssociationById(i) == null) {
            throw new java.lang.IllegalArgumentException("Association with id " + i + " does not exist.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onDevicePresenceEvent(@android.annotation.NonNull java.util.Set<java.lang.Integer> set, int i, int i2) {
        android.util.Slog.i(TAG, "onDevicePresenceEvent() id=" + i + ", event=" + i2);
        switch (i2) {
            case 0:
                synchronized (this.mBtDisconnectedDevices) {
                    try {
                        if (this.mBtDisconnectedDevices.contains(java.lang.Integer.valueOf(i))) {
                            android.util.Slog.i(TAG, "Device ( " + i + " ) is present, do not need to send the callback with event ( 0 ).");
                            this.mBtDisconnectedDevicesBlePresence.append(i, true);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (!set.add(java.lang.Integer.valueOf(i))) {
                    android.util.Slog.w(TAG, "Association with id " + i + " is ALREADY reported as present by this source, event=" + i2);
                }
                this.mCallback.onDeviceAppeared(i);
                this.mCallback.onDevicePresenceEvent(i, i2);
                return;
            case 1:
            case 3:
            case 5:
                if (!set.remove(java.lang.Integer.valueOf(i))) {
                    android.util.Slog.w(TAG, "Association with id " + i + " was NOT reported as present by this source, event= " + i2);
                    return;
                }
                this.mCallback.onDeviceDisappeared(i);
                this.mCallback.onDevicePresenceEvent(i, i2);
                return;
            case 2:
            case 4:
                if (!set.add(java.lang.Integer.valueOf(i))) {
                }
                this.mCallback.onDeviceAppeared(i);
                this.mCallback.onDevicePresenceEvent(i, i2);
                return;
            default:
                android.util.Slog.e(TAG, "Event: " + i2 + " is not supported");
                return;
        }
    }

    @Override // com.android.server.companion.AssociationStore.OnChangeListener
    public void onAssociationRemoved(@android.annotation.NonNull android.companion.AssociationInfo associationInfo) {
        int id = associationInfo.getId();
        this.mConnectedBtDevices.remove(java.lang.Integer.valueOf(id));
        this.mNearbyBleDevices.remove(java.lang.Integer.valueOf(id));
        this.mReportedSelfManagedDevices.remove(java.lang.Integer.valueOf(id));
        this.mSimulated.remove(java.lang.Integer.valueOf(id));
        this.mBtDisconnectedDevices.remove(java.lang.Integer.valueOf(id));
        this.mBtDisconnectedDevicesBlePresence.delete(id);
    }

    public android.util.SparseArray<java.util.Set<android.bluetooth.BluetoothDevice>> getPendingConnectedDevices() {
        android.util.SparseArray<java.util.Set<android.bluetooth.BluetoothDevice>> sparseArray;
        synchronized (this.mBtConnectionListener.mPendingConnectedDevices) {
            sparseArray = this.mBtConnectionListener.mPendingConnectedDevices;
        }
        return sparseArray;
    }

    private static void enforceCallerShellOrRoot() {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid == 2000 || callingUid == 0) {
        } else {
            throw new java.lang.SecurityException("Caller is neither Shell nor Root");
        }
    }

    private boolean canStopBleScan() {
        for (android.companion.AssociationInfo associationInfo : this.mAssociationStore.getAssociations()) {
            int id = associationInfo.getId();
            synchronized (this.mBtDisconnectedDevices) {
                try {
                    if (associationInfo.isNotifyOnDeviceNearby()) {
                        if (isBtConnected(id)) {
                            if (!isBlePresent(id) || !this.mBtDisconnectedDevices.isEmpty()) {
                            }
                        }
                        android.util.Slog.i(TAG, "The BLE scan cannot be stopped, device( " + id + " ) is not yet connected OR the BLE is not current present Or is pending to report BLE lost");
                        return false;
                    }
                } finally {
                }
            }
        }
        return true;
    }

    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        printWriter.append("Companion Device Present: ");
        if (this.mConnectedBtDevices.isEmpty() && this.mNearbyBleDevices.isEmpty() && this.mReportedSelfManagedDevices.isEmpty()) {
            printWriter.append("<empty>\n");
            return;
        }
        printWriter.append("\n");
        printWriter.append("  Connected Bluetooth Devices: ");
        if (this.mConnectedBtDevices.isEmpty()) {
            printWriter.append("<empty>\n");
        } else {
            printWriter.append("\n");
            java.util.Iterator<java.lang.Integer> it = this.mConnectedBtDevices.iterator();
            while (it.hasNext()) {
                printWriter.append("    ").append((java.lang.CharSequence) this.mAssociationStore.getAssociationById(it.next().intValue()).toShortString()).append('\n');
            }
        }
        printWriter.append("  Nearby BLE Devices: ");
        if (this.mNearbyBleDevices.isEmpty()) {
            printWriter.append("<empty>\n");
        } else {
            printWriter.append("\n");
            java.util.Iterator<java.lang.Integer> it2 = this.mNearbyBleDevices.iterator();
            while (it2.hasNext()) {
                printWriter.append("    ").append((java.lang.CharSequence) this.mAssociationStore.getAssociationById(it2.next().intValue()).toShortString()).append('\n');
            }
        }
        printWriter.append("  Self-Reported Devices: ");
        if (this.mReportedSelfManagedDevices.isEmpty()) {
            printWriter.append("<empty>\n");
            return;
        }
        printWriter.append("\n");
        java.util.Iterator<java.lang.Integer> it3 = this.mReportedSelfManagedDevices.iterator();
        while (it3.hasNext()) {
            printWriter.append("    ").append((java.lang.CharSequence) this.mAssociationStore.getAssociationById(it3.next().intValue()).toShortString()).append('\n');
        }
    }

    private class SimulatedDevicePresenceSchedulerHelper extends android.os.Handler {
        SimulatedDevicePresenceSchedulerHelper() {
            super(android.os.Looper.getMainLooper());
        }

        void scheduleOnDeviceGoneCallForSimulatedDevicePresence(int i) {
            if (hasMessages(i)) {
                removeMessages(i);
            }
            sendEmptyMessageDelayed(i, 60000L);
        }

        void unscheduleOnDeviceGoneCallForSimulatedDevicePresence(int i) {
            removeMessages(i);
        }

        @Override // android.os.Handler
        public void handleMessage(@android.annotation.NonNull android.os.Message message) {
            int i = message.what;
            if (com.android.server.companion.presence.CompanionDevicePresenceMonitor.this.mSimulated.contains(java.lang.Integer.valueOf(i))) {
                com.android.server.companion.presence.CompanionDevicePresenceMonitor.this.onDevicePresenceEvent(com.android.server.companion.presence.CompanionDevicePresenceMonitor.this.mSimulated, i, 1);
            }
        }
    }

    private class BleDeviceDisappearedScheduler extends android.os.Handler {
        BleDeviceDisappearedScheduler() {
            super(android.os.Looper.getMainLooper());
        }

        void scheduleBleDeviceDisappeared(int i) {
            if (hasMessages(i)) {
                removeMessages(i);
            }
            android.util.Slog.i(com.android.server.companion.presence.CompanionDevicePresenceMonitor.TAG, "scheduleBleDeviceDisappeared for Device: ( " + i + " ).");
            sendEmptyMessageDelayed(i, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        }

        void unScheduleDeviceDisappeared(int i) {
            if (hasMessages(i)) {
                android.util.Slog.i(com.android.server.companion.presence.CompanionDevicePresenceMonitor.TAG, "unScheduleDeviceDisappeared for Device( " + i + " )");
                synchronized (com.android.server.companion.presence.CompanionDevicePresenceMonitor.this.mBtDisconnectedDevices) {
                    com.android.server.companion.presence.CompanionDevicePresenceMonitor.this.mBtDisconnectedDevices.remove(java.lang.Integer.valueOf(i));
                    com.android.server.companion.presence.CompanionDevicePresenceMonitor.this.mBtDisconnectedDevicesBlePresence.delete(i);
                }
                removeMessages(i);
            }
        }

        @Override // android.os.Handler
        public void handleMessage(@android.annotation.NonNull android.os.Message message) {
            int i = message.what;
            synchronized (com.android.server.companion.presence.CompanionDevicePresenceMonitor.this.mBtDisconnectedDevices) {
                try {
                    boolean z = com.android.server.companion.presence.CompanionDevicePresenceMonitor.this.mBtDisconnectedDevicesBlePresence.get(i);
                    if (com.android.server.companion.presence.CompanionDevicePresenceMonitor.this.mBtDisconnectedDevices.contains(java.lang.Integer.valueOf(i)) && !z) {
                        android.util.Slog.i(com.android.server.companion.presence.CompanionDevicePresenceMonitor.TAG, "Device ( " + i + " ) is likely BLE out of range, sending callback with event ( 1 )");
                        com.android.server.companion.presence.CompanionDevicePresenceMonitor.this.onDevicePresenceEvent(com.android.server.companion.presence.CompanionDevicePresenceMonitor.this.mNearbyBleDevices, i, 1);
                    }
                    com.android.server.companion.presence.CompanionDevicePresenceMonitor.this.mBtDisconnectedDevices.remove(java.lang.Integer.valueOf(i));
                    com.android.server.companion.presence.CompanionDevicePresenceMonitor.this.mBtDisconnectedDevicesBlePresence.delete(i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
