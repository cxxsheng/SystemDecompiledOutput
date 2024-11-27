package com.android.server.input;

/* loaded from: classes2.dex */
final class BatteryController {

    @com.android.internal.annotations.VisibleForTesting
    static final long POLLING_PERIOD_MILLIS = 10000;

    @com.android.internal.annotations.VisibleForTesting
    static final long USI_BATTERY_VALIDITY_DURATION_MILLIS = 3600000;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.input.BatteryController.BluetoothBatteryManager.BluetoothBatteryListener mBluetoothBatteryListener;
    private final com.android.server.input.BatteryController.BluetoothBatteryManager mBluetoothBatteryManager;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.Integer, com.android.server.input.BatteryController.DeviceMonitor> mDeviceMonitors;
    private final android.os.Handler mHandler;
    private final android.hardware.input.InputManager.InputDeviceListener mInputDeviceListener;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsInteractive;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsPolling;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.Integer, com.android.server.input.BatteryController.ListenerRecord> mListenerRecords;
    private final java.lang.Object mLock;
    private final com.android.server.input.NativeInputManagerService mNative;
    private final com.android.server.input.UEventManager mUEventManager;
    private static final java.lang.String TAG = com.android.server.input.BatteryController.class.getSimpleName();
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    @com.android.internal.annotations.VisibleForTesting
    interface BluetoothBatteryManager {

        @com.android.internal.annotations.VisibleForTesting
        public interface BluetoothBatteryListener {
            void onBluetoothBatteryChanged(long j, java.lang.String str, int i);
        }

        void addBatteryListener(com.android.server.input.BatteryController.BluetoothBatteryManager.BluetoothBatteryListener bluetoothBatteryListener);

        void addMetadataListener(java.lang.String str, android.bluetooth.BluetoothAdapter.OnMetadataChangedListener onMetadataChangedListener);

        int getBatteryLevel(java.lang.String str);

        byte[] getMetadata(java.lang.String str, int i);

        void removeBatteryListener(com.android.server.input.BatteryController.BluetoothBatteryManager.BluetoothBatteryListener bluetoothBatteryListener);

        void removeMetadataListener(java.lang.String str, android.bluetooth.BluetoothAdapter.OnMetadataChangedListener onMetadataChangedListener);
    }

    BatteryController(android.content.Context context, com.android.server.input.NativeInputManagerService nativeInputManagerService, android.os.Looper looper, com.android.server.input.UEventManager uEventManager) {
        this(context, nativeInputManagerService, looper, uEventManager, new com.android.server.input.BatteryController.LocalBluetoothBatteryManager(context, looper));
    }

    @com.android.internal.annotations.VisibleForTesting
    BatteryController(android.content.Context context, com.android.server.input.NativeInputManagerService nativeInputManagerService, android.os.Looper looper, com.android.server.input.UEventManager uEventManager, com.android.server.input.BatteryController.BluetoothBatteryManager bluetoothBatteryManager) {
        this.mLock = new java.lang.Object();
        this.mListenerRecords = new android.util.ArrayMap<>();
        this.mDeviceMonitors = new android.util.ArrayMap<>();
        this.mIsPolling = false;
        this.mIsInteractive = true;
        this.mInputDeviceListener = new android.hardware.input.InputManager.InputDeviceListener() { // from class: com.android.server.input.BatteryController.1
            @Override // android.hardware.input.InputManager.InputDeviceListener
            public void onInputDeviceAdded(int i) {
                synchronized (com.android.server.input.BatteryController.this.mLock) {
                    try {
                        if (com.android.server.input.BatteryController.this.isUsiDevice(i) && !com.android.server.input.BatteryController.this.mDeviceMonitors.containsKey(java.lang.Integer.valueOf(i))) {
                            com.android.server.input.BatteryController.this.mDeviceMonitors.put(java.lang.Integer.valueOf(i), com.android.server.input.BatteryController.this.new UsiDeviceMonitor(i));
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.hardware.input.InputManager.InputDeviceListener
            public void onInputDeviceRemoved(int i) {
            }

            @Override // android.hardware.input.InputManager.InputDeviceListener
            public void onInputDeviceChanged(int i) {
                synchronized (com.android.server.input.BatteryController.this.mLock) {
                    try {
                        com.android.server.input.BatteryController.DeviceMonitor deviceMonitor = (com.android.server.input.BatteryController.DeviceMonitor) com.android.server.input.BatteryController.this.mDeviceMonitors.get(java.lang.Integer.valueOf(i));
                        if (deviceMonitor == null) {
                            return;
                        }
                        deviceMonitor.onConfiguration(android.os.SystemClock.uptimeMillis());
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mContext = context;
        this.mNative = nativeInputManagerService;
        this.mHandler = new android.os.Handler(looper);
        this.mUEventManager = uEventManager;
        this.mBluetoothBatteryManager = bluetoothBatteryManager;
    }

    public void systemRunning() {
        android.hardware.input.InputManager inputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        java.util.Objects.requireNonNull(inputManager);
        inputManager.registerInputDeviceListener(this.mInputDeviceListener, this.mHandler);
        for (int i : inputManager.getInputDeviceIds()) {
            this.mInputDeviceListener.onInputDeviceAdded(i);
        }
    }

    public void registerBatteryListener(int i, @android.annotation.NonNull android.hardware.input.IInputDeviceBatteryListener iInputDeviceBatteryListener, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.input.BatteryController.ListenerRecord listenerRecord = this.mListenerRecords.get(java.lang.Integer.valueOf(i2));
                if (listenerRecord == null) {
                    listenerRecord = new com.android.server.input.BatteryController.ListenerRecord(i2, iInputDeviceBatteryListener);
                    try {
                        iInputDeviceBatteryListener.asBinder().linkToDeath(listenerRecord.mDeathRecipient, 0);
                        this.mListenerRecords.put(java.lang.Integer.valueOf(i2), listenerRecord);
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "Battery listener added for pid " + i2);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.i(TAG, "Client died before battery listener could be registered.");
                        return;
                    }
                }
                if (listenerRecord.mListener.asBinder() != iInputDeviceBatteryListener.asBinder()) {
                    throw new java.lang.SecurityException("Cannot register a new battery listener when there is already another registered listener for pid " + i2);
                }
                if (!listenerRecord.mMonitoredDevices.add(java.lang.Integer.valueOf(i))) {
                    throw new java.lang.IllegalArgumentException("The battery listener for pid " + i2 + " is already monitoring deviceId " + i);
                }
                com.android.server.input.BatteryController.DeviceMonitor deviceMonitor = this.mDeviceMonitors.get(java.lang.Integer.valueOf(i));
                if (deviceMonitor == null) {
                    deviceMonitor = new com.android.server.input.BatteryController.DeviceMonitor(i);
                    this.mDeviceMonitors.put(java.lang.Integer.valueOf(i), deviceMonitor);
                    updateBluetoothBatteryMonitoring();
                }
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Battery listener for pid " + i2 + " is monitoring deviceId " + i);
                }
                updatePollingLocked(true);
                notifyBatteryListener(listenerRecord, deviceMonitor.getBatteryStateForReporting());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static void notifyBatteryListener(com.android.server.input.BatteryController.ListenerRecord listenerRecord, com.android.server.input.BatteryController.State state) {
        try {
            listenerRecord.mListener.onBatteryStateChanged(state);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to notify listener", e);
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Notified battery listener from pid " + listenerRecord.mPid + " of state of deviceId " + ((android.hardware.input.IInputDeviceBatteryState) state).deviceId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyAllListenersForDevice(final com.android.server.input.BatteryController.State state) {
        synchronized (this.mLock) {
            try {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Notifying all listeners of battery state: " + state);
                }
                this.mListenerRecords.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.input.BatteryController$$ExternalSyntheticLambda6
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        com.android.server.input.BatteryController.lambda$notifyAllListenersForDevice$0(com.android.server.input.BatteryController.State.this, (java.lang.Integer) obj, (com.android.server.input.BatteryController.ListenerRecord) obj2);
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$notifyAllListenersForDevice$0(com.android.server.input.BatteryController.State state, java.lang.Integer num, com.android.server.input.BatteryController.ListenerRecord listenerRecord) {
        if (listenerRecord.mMonitoredDevices.contains(java.lang.Integer.valueOf(((android.hardware.input.IInputDeviceBatteryState) state).deviceId))) {
            notifyBatteryListener(listenerRecord, state);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updatePollingLocked(boolean z) {
        if (!this.mIsInteractive || !anyOf(this.mDeviceMonitors, new java.util.function.Predicate() { // from class: com.android.server.input.BatteryController$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return ((com.android.server.input.BatteryController.DeviceMonitor) obj).requiresPolling();
            }
        })) {
            this.mIsPolling = false;
            this.mHandler.removeCallbacks(new com.android.server.input.BatteryController$$ExternalSyntheticLambda8(this));
        } else {
            if (this.mIsPolling) {
                return;
            }
            this.mIsPolling = true;
            this.mHandler.postDelayed(new com.android.server.input.BatteryController$$ExternalSyntheticLambda8(this), z ? 10000L : 0L);
        }
    }

    private <R> R processInputDevice(int i, R r, java.util.function.Function<android.view.InputDevice, R> function) {
        android.hardware.input.InputManager inputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        java.util.Objects.requireNonNull(inputManager);
        android.view.InputDevice inputDevice = inputManager.getInputDevice(i);
        return inputDevice == null ? r : function.apply(inputDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getInputDeviceName(int i) {
        return (java.lang.String) processInputDevice(i, "<none>", new java.util.function.Function() { // from class: com.android.server.input.BatteryController$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.view.InputDevice) obj).getName();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasBattery(int i) {
        return ((java.lang.Boolean) processInputDevice(i, false, new java.util.function.Function() { // from class: com.android.server.input.BatteryController$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return java.lang.Boolean.valueOf(((android.view.InputDevice) obj).hasBattery());
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUsiDevice(int i) {
        return ((java.lang.Boolean) processInputDevice(i, false, new java.util.function.Function() { // from class: com.android.server.input.BatteryController$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean lambda$isUsiDevice$1;
                lambda$isUsiDevice$1 = com.android.server.input.BatteryController.lambda$isUsiDevice$1((android.view.InputDevice) obj);
                return lambda$isUsiDevice$1;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$isUsiDevice$1(android.view.InputDevice inputDevice) {
        return java.lang.Boolean.valueOf(inputDevice.getHostUsiVersion() != null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public android.bluetooth.BluetoothDevice getBluetoothDevice(int i) {
        return getBluetoothDevice(this.mContext, (java.lang.String) processInputDevice(i, null, new java.util.function.Function() { // from class: com.android.server.input.BatteryController$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.view.InputDevice) obj).getBluetoothAddress();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public static android.bluetooth.BluetoothDevice getBluetoothDevice(android.content.Context context, java.lang.String str) {
        if (str == null) {
            return null;
        }
        android.bluetooth.BluetoothManager bluetoothManager = (android.bluetooth.BluetoothManager) context.getSystemService(android.bluetooth.BluetoothManager.class);
        java.util.Objects.requireNonNull(bluetoothManager);
        return bluetoothManager.getAdapter().getRemoteDevice(str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.input.BatteryController.DeviceMonitor getDeviceMonitorOrThrowLocked(int i) {
        com.android.server.input.BatteryController.DeviceMonitor deviceMonitor = this.mDeviceMonitors.get(java.lang.Integer.valueOf(i));
        java.util.Objects.requireNonNull(deviceMonitor, "Maps are out of sync: Cannot find device state for deviceId " + i);
        return deviceMonitor;
    }

    public void unregisterBatteryListener(int i, @android.annotation.NonNull android.hardware.input.IInputDeviceBatteryListener iInputDeviceBatteryListener, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.input.BatteryController.ListenerRecord listenerRecord = this.mListenerRecords.get(java.lang.Integer.valueOf(i2));
                if (listenerRecord == null) {
                    throw new java.lang.IllegalArgumentException("Cannot unregister battery callback: No listener registered for pid " + i2);
                }
                if (listenerRecord.mListener.asBinder() != iInputDeviceBatteryListener.asBinder()) {
                    throw new java.lang.IllegalArgumentException("Cannot unregister battery callback: The listener is not the one that is registered for pid " + i2);
                }
                if (!listenerRecord.mMonitoredDevices.contains(java.lang.Integer.valueOf(i))) {
                    throw new java.lang.IllegalArgumentException("Cannot unregister battery callback: The device is not being monitored for deviceId " + i);
                }
                unregisterRecordLocked(listenerRecord, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void unregisterRecordLocked(com.android.server.input.BatteryController.ListenerRecord listenerRecord, int i) {
        int i2 = listenerRecord.mPid;
        if (!listenerRecord.mMonitoredDevices.remove(java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalStateException("Cannot unregister battery callback: The deviceId " + i + " is not being monitored by pid " + i2);
        }
        if (!hasRegisteredListenerForDeviceLocked(i)) {
            com.android.server.input.BatteryController.DeviceMonitor deviceMonitorOrThrowLocked = getDeviceMonitorOrThrowLocked(i);
            if (!deviceMonitorOrThrowLocked.isPersistent()) {
                deviceMonitorOrThrowLocked.onMonitorDestroy();
                this.mDeviceMonitors.remove(java.lang.Integer.valueOf(i));
            }
        }
        if (listenerRecord.mMonitoredDevices.isEmpty()) {
            listenerRecord.mListener.asBinder().unlinkToDeath(listenerRecord.mDeathRecipient, 0);
            this.mListenerRecords.remove(java.lang.Integer.valueOf(i2));
            if (DEBUG) {
                android.util.Slog.d(TAG, "Battery listener removed for pid " + i2);
            }
        }
        updatePollingLocked(false);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean hasRegisteredListenerForDeviceLocked(int i) {
        for (int i2 = 0; i2 < this.mListenerRecords.size(); i2++) {
            if (this.mListenerRecords.valueAt(i2).mMonitoredDevices.contains(java.lang.Integer.valueOf(i))) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleListeningProcessDied(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.input.BatteryController.ListenerRecord listenerRecord = this.mListenerRecords.get(java.lang.Integer.valueOf(i));
                if (listenerRecord == null) {
                    return;
                }
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Removing battery listener for pid " + i + " because the process died");
                }
                java.util.Iterator<java.lang.Integer> it = listenerRecord.mMonitoredDevices.iterator();
                while (it.hasNext()) {
                    unregisterRecordLocked(listenerRecord, it.next().intValue());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUEventNotification(int i, long j) {
        synchronized (this.mLock) {
            try {
                com.android.server.input.BatteryController.DeviceMonitor deviceMonitor = this.mDeviceMonitors.get(java.lang.Integer.valueOf(i));
                if (deviceMonitor == null) {
                    return;
                }
                deviceMonitor.onUEvent(j);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePollEvent() {
        synchronized (this.mLock) {
            try {
                if (this.mIsPolling) {
                    final long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    this.mDeviceMonitors.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.input.BatteryController$$ExternalSyntheticLambda9
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            ((com.android.server.input.BatteryController.DeviceMonitor) obj2).onPoll(uptimeMillis);
                        }
                    });
                    this.mHandler.postDelayed(new com.android.server.input.BatteryController$$ExternalSyntheticLambda8(this), 10000L);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMonitorTimeout(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.input.BatteryController.DeviceMonitor deviceMonitor = this.mDeviceMonitors.get(java.lang.Integer.valueOf(i));
                if (deviceMonitor == null) {
                    return;
                }
                deviceMonitor.onTimeout(android.os.SystemClock.uptimeMillis());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBluetoothBatteryLevelChange(long j, final java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.input.BatteryController.DeviceMonitor deviceMonitor = (com.android.server.input.BatteryController.DeviceMonitor) findIf(this.mDeviceMonitors, new java.util.function.Predicate() { // from class: com.android.server.input.BatteryController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$handleBluetoothBatteryLevelChange$3;
                        lambda$handleBluetoothBatteryLevelChange$3 = com.android.server.input.BatteryController.lambda$handleBluetoothBatteryLevelChange$3(str, (com.android.server.input.BatteryController.DeviceMonitor) obj);
                        return lambda$handleBluetoothBatteryLevelChange$3;
                    }
                });
                if (deviceMonitor != null) {
                    deviceMonitor.onBluetoothBatteryChanged(j, i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$handleBluetoothBatteryLevelChange$3(java.lang.String str, com.android.server.input.BatteryController.DeviceMonitor deviceMonitor) {
        return deviceMonitor.mBluetoothDevice != null && str.equals(deviceMonitor.mBluetoothDevice.getAddress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBluetoothMetadataChange(@android.annotation.NonNull final android.bluetooth.BluetoothDevice bluetoothDevice, int i, @android.annotation.Nullable byte[] bArr) {
        synchronized (this.mLock) {
            try {
                com.android.server.input.BatteryController.DeviceMonitor deviceMonitor = (com.android.server.input.BatteryController.DeviceMonitor) findIf(this.mDeviceMonitors, new java.util.function.Predicate() { // from class: com.android.server.input.BatteryController$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$handleBluetoothMetadataChange$4;
                        lambda$handleBluetoothMetadataChange$4 = com.android.server.input.BatteryController.lambda$handleBluetoothMetadataChange$4(bluetoothDevice, (com.android.server.input.BatteryController.DeviceMonitor) obj);
                        return lambda$handleBluetoothMetadataChange$4;
                    }
                });
                if (deviceMonitor != null) {
                    deviceMonitor.onBluetoothMetadataChanged(android.os.SystemClock.uptimeMillis(), i, bArr);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$handleBluetoothMetadataChange$4(android.bluetooth.BluetoothDevice bluetoothDevice, com.android.server.input.BatteryController.DeviceMonitor deviceMonitor) {
        return bluetoothDevice.equals(deviceMonitor.mBluetoothDevice);
    }

    public android.hardware.input.IInputDeviceBatteryState getBatteryState(int i) {
        synchronized (this.mLock) {
            try {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                com.android.server.input.BatteryController.DeviceMonitor deviceMonitor = this.mDeviceMonitors.get(java.lang.Integer.valueOf(i));
                if (deviceMonitor == null) {
                    return queryBatteryStateFromNative(i, uptimeMillis, hasBattery(i));
                }
                deviceMonitor.onPoll(uptimeMillis);
                return deviceMonitor.getBatteryStateForReporting();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onInteractiveChanged(boolean z) {
        synchronized (this.mLock) {
            this.mIsInteractive = z;
            updatePollingLocked(false);
        }
    }

    public void notifyStylusGestureStarted(int i, long j) {
        synchronized (this.mLock) {
            try {
                com.android.server.input.BatteryController.DeviceMonitor deviceMonitor = this.mDeviceMonitors.get(java.lang.Integer.valueOf(i));
                if (deviceMonitor == null) {
                    return;
                }
                deviceMonitor.onStylusGestureStarted(j);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(java.io.PrintWriter printWriter) {
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println(TAG + ":");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("State: Polling = " + this.mIsPolling + ", Interactive = " + this.mIsInteractive);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Listeners: ");
                sb.append(this.mListenerRecords.size());
                sb.append(" battery listeners");
                indentingPrintWriter.println(sb.toString());
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mListenerRecords.size(); i++) {
                    indentingPrintWriter.println(i + ": " + this.mListenerRecords.valueAt(i));
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("Device Monitors: " + this.mDeviceMonitors.size() + " monitors");
                indentingPrintWriter.increaseIndent();
                for (int i2 = 0; i2 < this.mDeviceMonitors.size(); i2++) {
                    indentingPrintWriter.println(i2 + ": " + this.mDeviceMonitors.valueAt(i2));
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void monitor() {
        synchronized (this.mLock) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ListenerRecord {
        public final android.os.IBinder.DeathRecipient mDeathRecipient;
        public final android.hardware.input.IInputDeviceBatteryListener mListener;
        public final java.util.Set<java.lang.Integer> mMonitoredDevices = new android.util.ArraySet();
        public final int mPid;

        ListenerRecord(final int i, android.hardware.input.IInputDeviceBatteryListener iInputDeviceBatteryListener) {
            this.mPid = i;
            this.mListener = iInputDeviceBatteryListener;
            this.mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.input.BatteryController$ListenerRecord$$ExternalSyntheticLambda0
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    com.android.server.input.BatteryController.ListenerRecord.this.lambda$new$0(i);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(int i) {
            com.android.server.input.BatteryController.this.handleListeningProcessDied(i);
        }

        public java.lang.String toString() {
            return "pid=" + this.mPid + ", monitored devices=" + java.util.Arrays.toString(this.mMonitoredDevices.toArray());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.input.BatteryController.State queryBatteryStateFromNative(int i, long j, boolean z) {
        return new com.android.server.input.BatteryController.State(i, j, z, z ? this.mNative.getBatteryStatus(i) : 1, z ? this.mNative.getBatteryCapacity(i) / 100.0f : Float.NaN);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBluetoothBatteryMonitoring() {
        synchronized (this.mLock) {
            try {
                if (anyOf(this.mDeviceMonitors, new java.util.function.Predicate() { // from class: com.android.server.input.BatteryController$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$updateBluetoothBatteryMonitoring$5;
                        lambda$updateBluetoothBatteryMonitoring$5 = com.android.server.input.BatteryController.lambda$updateBluetoothBatteryMonitoring$5((com.android.server.input.BatteryController.DeviceMonitor) obj);
                        return lambda$updateBluetoothBatteryMonitoring$5;
                    }
                })) {
                    if (this.mBluetoothBatteryListener == null) {
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "Registering bluetooth battery listener");
                        }
                        this.mBluetoothBatteryListener = new com.android.server.input.BatteryController.BluetoothBatteryManager.BluetoothBatteryListener() { // from class: com.android.server.input.BatteryController$$ExternalSyntheticLambda11
                            @Override // com.android.server.input.BatteryController.BluetoothBatteryManager.BluetoothBatteryListener
                            public final void onBluetoothBatteryChanged(long j, java.lang.String str, int i) {
                                com.android.server.input.BatteryController.this.handleBluetoothBatteryLevelChange(j, str, i);
                            }
                        };
                        this.mBluetoothBatteryManager.addBatteryListener(this.mBluetoothBatteryListener);
                    }
                } else if (this.mBluetoothBatteryListener != null) {
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "Unregistering bluetooth battery listener");
                    }
                    this.mBluetoothBatteryManager.removeBatteryListener(this.mBluetoothBatteryListener);
                    this.mBluetoothBatteryListener = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateBluetoothBatteryMonitoring$5(com.android.server.input.BatteryController.DeviceMonitor deviceMonitor) {
        return deviceMonitor.mBluetoothDevice != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DeviceMonitor {

        @android.annotation.Nullable
        private android.bluetooth.BluetoothDevice mBluetoothDevice;

        @android.annotation.Nullable
        private android.bluetooth.BluetoothAdapter.OnMetadataChangedListener mBluetoothMetadataListener;
        protected final com.android.server.input.BatteryController.State mState;

        @android.annotation.Nullable
        private com.android.server.input.BatteryController.UEventBatteryListener mUEventBatteryListener;
        protected boolean mHasBattery = false;
        long mBluetoothEventTime = 0;
        int mBluetoothBatteryLevel = -1;
        int mBluetoothMetadataBatteryLevel = -1;
        int mBluetoothMetadataBatteryStatus = 1;

        DeviceMonitor(int i) {
            this.mState = new com.android.server.input.BatteryController.State(i);
            configureDeviceMonitor(android.os.SystemClock.uptimeMillis());
        }

        protected void processChangesAndNotify(long j, java.util.function.Consumer<java.lang.Long> consumer) {
            com.android.server.input.BatteryController.State batteryStateForReporting = getBatteryStateForReporting();
            consumer.accept(java.lang.Long.valueOf(j));
            com.android.server.input.BatteryController.State batteryStateForReporting2 = getBatteryStateForReporting();
            if (!batteryStateForReporting.equalsIgnoringUpdateTime(batteryStateForReporting2)) {
                com.android.server.input.BatteryController.this.notifyAllListenersForDevice(batteryStateForReporting2);
            }
        }

        public void onConfiguration(long j) {
            processChangesAndNotify(j, new java.util.function.Consumer() { // from class: com.android.server.input.BatteryController$DeviceMonitor$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.input.BatteryController.DeviceMonitor.this.configureDeviceMonitor(((java.lang.Long) obj).longValue());
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void configureDeviceMonitor(long j) {
            int i = ((android.hardware.input.IInputDeviceBatteryState) this.mState).deviceId;
            if (this.mHasBattery != com.android.server.input.BatteryController.this.hasBattery(((android.hardware.input.IInputDeviceBatteryState) this.mState).deviceId)) {
                this.mHasBattery = !this.mHasBattery;
                if (this.mHasBattery) {
                    startNativeMonitoring();
                } else {
                    stopNativeMonitoring();
                }
                updateBatteryStateFromNative(j);
            }
            android.bluetooth.BluetoothDevice bluetoothDevice = com.android.server.input.BatteryController.this.getBluetoothDevice(i);
            if (!java.util.Objects.equals(this.mBluetoothDevice, bluetoothDevice)) {
                if (com.android.server.input.BatteryController.DEBUG) {
                    java.lang.String str = com.android.server.input.BatteryController.TAG;
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("Bluetooth device is now ");
                    sb.append(bluetoothDevice != null ? "" : "not");
                    sb.append(" present for deviceId ");
                    sb.append(i);
                    android.util.Slog.d(str, sb.toString());
                }
                this.mBluetoothBatteryLevel = -1;
                stopBluetoothMetadataMonitoring();
                this.mBluetoothDevice = bluetoothDevice;
                com.android.server.input.BatteryController.this.updateBluetoothBatteryMonitoring();
                if (this.mBluetoothDevice != null) {
                    this.mBluetoothBatteryLevel = com.android.server.input.BatteryController.this.mBluetoothBatteryManager.getBatteryLevel(this.mBluetoothDevice.getAddress());
                    startBluetoothMetadataMonitoring(j);
                }
            }
        }

        private void startNativeMonitoring() {
            java.lang.String batteryDevicePath = com.android.server.input.BatteryController.this.mNative.getBatteryDevicePath(((android.hardware.input.IInputDeviceBatteryState) this.mState).deviceId);
            if (batteryDevicePath == null) {
                return;
            }
            final int i = ((android.hardware.input.IInputDeviceBatteryState) this.mState).deviceId;
            this.mUEventBatteryListener = new com.android.server.input.BatteryController.UEventBatteryListener() { // from class: com.android.server.input.BatteryController.DeviceMonitor.1
                @Override // com.android.server.input.BatteryController.UEventBatteryListener
                public void onBatteryUEvent(long j) {
                    com.android.server.input.BatteryController.this.handleUEventNotification(i, j);
                }
            };
            com.android.server.input.BatteryController.this.mUEventManager.addListener(this.mUEventBatteryListener, "DEVPATH=" + formatDevPath(batteryDevicePath));
        }

        private java.lang.String formatDevPath(@android.annotation.NonNull java.lang.String str) {
            return str.startsWith("/sys") ? str.substring(4) : str;
        }

        private void stopNativeMonitoring() {
            if (this.mUEventBatteryListener != null) {
                com.android.server.input.BatteryController.this.mUEventManager.removeListener(this.mUEventBatteryListener);
                this.mUEventBatteryListener = null;
            }
        }

        private void startBluetoothMetadataMonitoring(long j) {
            java.util.Objects.requireNonNull(this.mBluetoothDevice);
            final com.android.server.input.BatteryController batteryController = com.android.server.input.BatteryController.this;
            this.mBluetoothMetadataListener = new android.bluetooth.BluetoothAdapter.OnMetadataChangedListener() { // from class: com.android.server.input.BatteryController$DeviceMonitor$$ExternalSyntheticLambda1
                public final void onMetadataChanged(android.bluetooth.BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
                    com.android.server.input.BatteryController.this.handleBluetoothMetadataChange(bluetoothDevice, i, bArr);
                }
            };
            com.android.server.input.BatteryController.this.mBluetoothBatteryManager.addMetadataListener(this.mBluetoothDevice.getAddress(), this.mBluetoothMetadataListener);
            updateBluetoothMetadataState(j, 18, com.android.server.input.BatteryController.this.mBluetoothBatteryManager.getMetadata(this.mBluetoothDevice.getAddress(), 18));
            updateBluetoothMetadataState(j, 19, com.android.server.input.BatteryController.this.mBluetoothBatteryManager.getMetadata(this.mBluetoothDevice.getAddress(), 19));
        }

        private void stopBluetoothMetadataMonitoring() {
            if (this.mBluetoothMetadataListener == null) {
                return;
            }
            java.util.Objects.requireNonNull(this.mBluetoothDevice);
            com.android.server.input.BatteryController.this.mBluetoothBatteryManager.removeMetadataListener(this.mBluetoothDevice.getAddress(), this.mBluetoothMetadataListener);
            this.mBluetoothMetadataListener = null;
            this.mBluetoothMetadataBatteryLevel = -1;
            this.mBluetoothMetadataBatteryStatus = 1;
        }

        public void onMonitorDestroy() {
            stopNativeMonitoring();
            stopBluetoothMetadataMonitoring();
            this.mBluetoothDevice = null;
            com.android.server.input.BatteryController.this.updateBluetoothBatteryMonitoring();
        }

        protected void updateBatteryStateFromNative(long j) {
            this.mState.updateIfChanged(com.android.server.input.BatteryController.this.queryBatteryStateFromNative(((android.hardware.input.IInputDeviceBatteryState) this.mState).deviceId, j, this.mHasBattery));
        }

        public void onPoll(long j) {
            processChangesAndNotify(j, new com.android.server.input.BatteryController$DeviceMonitor$$ExternalSyntheticLambda0(this));
        }

        public void onUEvent(long j) {
            processChangesAndNotify(j, new com.android.server.input.BatteryController$DeviceMonitor$$ExternalSyntheticLambda0(this));
        }

        public void onBluetoothBatteryChanged(long j, final int i) {
            processChangesAndNotify(j, new java.util.function.Consumer() { // from class: com.android.server.input.BatteryController$DeviceMonitor$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.input.BatteryController.DeviceMonitor.this.lambda$onBluetoothBatteryChanged$0(i, (java.lang.Long) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBluetoothBatteryChanged$0(int i, java.lang.Long l) {
            this.mBluetoothBatteryLevel = i;
            this.mBluetoothEventTime = l.longValue();
        }

        public void onBluetoothMetadataChanged(long j, final int i, @android.annotation.Nullable final byte[] bArr) {
            processChangesAndNotify(j, new java.util.function.Consumer() { // from class: com.android.server.input.BatteryController$DeviceMonitor$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.input.BatteryController.DeviceMonitor.this.lambda$onBluetoothMetadataChanged$1(i, bArr, (java.lang.Long) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBluetoothMetadataChanged$1(int i, byte[] bArr, java.lang.Long l) {
            updateBluetoothMetadataState(l.longValue(), i, bArr);
        }

        private void updateBluetoothMetadataState(long j, int i, @android.annotation.Nullable byte[] bArr) {
            int i2;
            switch (i) {
                case 18:
                    this.mBluetoothEventTime = j;
                    this.mBluetoothMetadataBatteryLevel = -1;
                    if (bArr != null) {
                        try {
                            this.mBluetoothMetadataBatteryLevel = java.lang.Integer.parseInt(new java.lang.String(bArr));
                            break;
                        } catch (java.lang.NumberFormatException e) {
                            android.util.Slog.wtf(com.android.server.input.BatteryController.TAG, "Failed to parse bluetooth METADATA_MAIN_BATTERY with value '" + new java.lang.String(bArr) + "' for device " + this.mBluetoothDevice);
                            return;
                        }
                    }
                    break;
                case 19:
                    this.mBluetoothEventTime = j;
                    if (bArr != null) {
                        if (java.lang.Boolean.parseBoolean(new java.lang.String(bArr))) {
                            i2 = 2;
                        } else {
                            i2 = 3;
                        }
                        this.mBluetoothMetadataBatteryStatus = i2;
                        break;
                    } else {
                        this.mBluetoothMetadataBatteryStatus = 1;
                        break;
                    }
            }
        }

        public boolean requiresPolling() {
            return true;
        }

        public boolean isPersistent() {
            return false;
        }

        public void onTimeout(long j) {
        }

        public void onStylusGestureStarted(long j) {
        }

        public com.android.server.input.BatteryController.State getBatteryStateForReporting() {
            return (com.android.server.input.BatteryController.State) java.util.Objects.requireNonNullElseGet(resolveBluetoothBatteryState(), new java.util.function.Supplier() { // from class: com.android.server.input.BatteryController$DeviceMonitor$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    com.android.server.input.BatteryController.State lambda$getBatteryStateForReporting$2;
                    lambda$getBatteryStateForReporting$2 = com.android.server.input.BatteryController.DeviceMonitor.this.lambda$getBatteryStateForReporting$2();
                    return lambda$getBatteryStateForReporting$2;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ com.android.server.input.BatteryController.State lambda$getBatteryStateForReporting$2() {
            return new com.android.server.input.BatteryController.State(this.mState);
        }

        @android.annotation.Nullable
        protected com.android.server.input.BatteryController.State resolveBluetoothBatteryState() {
            int i;
            if (this.mBluetoothMetadataBatteryLevel >= 0 && this.mBluetoothMetadataBatteryLevel <= 100) {
                i = this.mBluetoothMetadataBatteryLevel;
            } else if (this.mBluetoothBatteryLevel >= 0 && this.mBluetoothBatteryLevel <= 100) {
                i = this.mBluetoothBatteryLevel;
            } else {
                return null;
            }
            return new com.android.server.input.BatteryController.State(((android.hardware.input.IInputDeviceBatteryState) this.mState).deviceId, this.mBluetoothEventTime, true, this.mBluetoothMetadataBatteryStatus, i / 100.0f);
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("DeviceId=");
            sb.append(((android.hardware.input.IInputDeviceBatteryState) this.mState).deviceId);
            sb.append(", Name='");
            sb.append(com.android.server.input.BatteryController.this.getInputDeviceName(((android.hardware.input.IInputDeviceBatteryState) this.mState).deviceId));
            sb.append("', NativeBattery=");
            sb.append(this.mState);
            sb.append(", UEventListener=");
            sb.append(this.mUEventBatteryListener != null ? "added" : "none");
            sb.append(", BluetoothState=");
            sb.append(resolveBluetoothBatteryState());
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class UsiDeviceMonitor extends com.android.server.input.BatteryController.DeviceMonitor {

        @android.annotation.Nullable
        private java.lang.Runnable mValidityTimeoutCallback;

        UsiDeviceMonitor(int i) {
            super(i);
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public void onPoll(long j) {
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public void onUEvent(long j) {
            processChangesAndNotify(j, new java.util.function.Consumer() { // from class: com.android.server.input.BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.input.BatteryController.UsiDeviceMonitor.this.lambda$onUEvent$0((java.lang.Long) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUEvent$0(java.lang.Long l) {
            updateBatteryStateFromNative(l.longValue());
            markUsiBatteryValid();
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public void onStylusGestureStarted(long j) {
            processChangesAndNotify(j, new java.util.function.Consumer() { // from class: com.android.server.input.BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.input.BatteryController.UsiDeviceMonitor.this.lambda$onStylusGestureStarted$1((java.lang.Long) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStylusGestureStarted$1(java.lang.Long l) {
            if (!(this.mValidityTimeoutCallback != null) && ((android.hardware.input.IInputDeviceBatteryState) this.mState).capacity == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                return;
            }
            markUsiBatteryValid();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTimeout$2(java.lang.Long l) {
            markUsiBatteryInvalid();
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public void onTimeout(long j) {
            processChangesAndNotify(j, new java.util.function.Consumer() { // from class: com.android.server.input.BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.input.BatteryController.UsiDeviceMonitor.this.lambda$onTimeout$2((java.lang.Long) obj);
                }
            });
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public void onConfiguration(long j) {
            super.onConfiguration(j);
            if (!this.mHasBattery) {
                throw new java.lang.IllegalStateException("UsiDeviceMonitor: USI devices are always expected to report a valid battery, but no battery was detected!");
            }
        }

        private void markUsiBatteryValid() {
            if (this.mValidityTimeoutCallback != null) {
                com.android.server.input.BatteryController.this.mHandler.removeCallbacks(this.mValidityTimeoutCallback);
            } else {
                final int i = ((android.hardware.input.IInputDeviceBatteryState) this.mState).deviceId;
                this.mValidityTimeoutCallback = new java.lang.Runnable() { // from class: com.android.server.input.BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.input.BatteryController.UsiDeviceMonitor.this.lambda$markUsiBatteryValid$3(i);
                    }
                };
            }
            com.android.server.input.BatteryController.this.mHandler.postDelayed(this.mValidityTimeoutCallback, 3600000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$markUsiBatteryValid$3(int i) {
            com.android.server.input.BatteryController.this.handleMonitorTimeout(i);
        }

        private void markUsiBatteryInvalid() {
            if (this.mValidityTimeoutCallback == null) {
                return;
            }
            com.android.server.input.BatteryController.this.mHandler.removeCallbacks(this.mValidityTimeoutCallback);
            this.mValidityTimeoutCallback = null;
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public com.android.server.input.BatteryController.State getBatteryStateForReporting() {
            return (com.android.server.input.BatteryController.State) java.util.Objects.requireNonNullElseGet(resolveBluetoothBatteryState(), new java.util.function.Supplier() { // from class: com.android.server.input.BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    com.android.server.input.BatteryController.State lambda$getBatteryStateForReporting$4;
                    lambda$getBatteryStateForReporting$4 = com.android.server.input.BatteryController.UsiDeviceMonitor.this.lambda$getBatteryStateForReporting$4();
                    return lambda$getBatteryStateForReporting$4;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ com.android.server.input.BatteryController.State lambda$getBatteryStateForReporting$4() {
            return this.mValidityTimeoutCallback != null ? new com.android.server.input.BatteryController.State(this.mState) : new com.android.server.input.BatteryController.State(((android.hardware.input.IInputDeviceBatteryState) this.mState).deviceId);
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public boolean requiresPolling() {
            return false;
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public boolean isPersistent() {
            return true;
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(super.toString());
            sb.append(", UsiStateIsValid=");
            sb.append(this.mValidityTimeoutCallback != null);
            return sb.toString();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static abstract class UEventBatteryListener extends com.android.server.input.UEventManager.UEventListener {
        public abstract void onBatteryUEvent(long j);

        UEventBatteryListener() {
        }

        @Override // com.android.server.input.UEventManager.UEventListener
        public void onUEvent(android.os.UEventObserver.UEvent uEvent) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (com.android.server.input.BatteryController.DEBUG) {
                android.util.Slog.d(com.android.server.input.BatteryController.TAG, "UEventListener: Received UEvent: " + uEvent + " eventTime: " + uptimeMillis);
            }
            if (!"CHANGE".equalsIgnoreCase(uEvent.get("ACTION")) || !"POWER_SUPPLY".equalsIgnoreCase(uEvent.get("SUBSYSTEM"))) {
                return;
            }
            onBatteryUEvent(uptimeMillis);
        }
    }

    private static class LocalBluetoothBatteryManager implements com.android.server.input.BatteryController.BluetoothBatteryManager {

        @com.android.internal.annotations.GuardedBy({"mBroadcastReceiver"})
        private final android.content.BroadcastReceiver mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.input.BatteryController.LocalBluetoothBatteryManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                android.bluetooth.BluetoothDevice bluetoothDevice;
                if (!"android.bluetooth.device.action.BATTERY_LEVEL_CHANGED".equals(intent.getAction()) || (bluetoothDevice = (android.bluetooth.BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", android.bluetooth.BluetoothDevice.class)) == null) {
                    return;
                }
                int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BATTERY_LEVEL", -1);
                synchronized (com.android.server.input.BatteryController.LocalBluetoothBatteryManager.this.mBroadcastReceiver) {
                    try {
                        if (com.android.server.input.BatteryController.LocalBluetoothBatteryManager.this.mRegisteredListener != null) {
                            com.android.server.input.BatteryController.LocalBluetoothBatteryManager.this.mRegisteredListener.onBluetoothBatteryChanged(android.os.SystemClock.uptimeMillis(), bluetoothDevice.getAddress(), intExtra);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        private final android.content.Context mContext;
        private final java.util.concurrent.Executor mExecutor;

        @com.android.internal.annotations.GuardedBy({"mBroadcastReceiver"})
        @android.annotation.Nullable
        private com.android.server.input.BatteryController.BluetoothBatteryManager.BluetoothBatteryListener mRegisteredListener;

        LocalBluetoothBatteryManager(android.content.Context context, android.os.Looper looper) {
            this.mContext = context;
            this.mExecutor = new android.os.HandlerExecutor(new android.os.Handler(looper));
        }

        @Override // com.android.server.input.BatteryController.BluetoothBatteryManager
        public void addBatteryListener(com.android.server.input.BatteryController.BluetoothBatteryManager.BluetoothBatteryListener bluetoothBatteryListener) {
            synchronized (this.mBroadcastReceiver) {
                try {
                    if (this.mRegisteredListener != null) {
                        throw new java.lang.IllegalStateException("Only one bluetooth battery listener can be registered at once.");
                    }
                    this.mRegisteredListener = bluetoothBatteryListener;
                    this.mContext.registerReceiver(this.mBroadcastReceiver, new android.content.IntentFilter("android.bluetooth.device.action.BATTERY_LEVEL_CHANGED"));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.input.BatteryController.BluetoothBatteryManager
        public void removeBatteryListener(com.android.server.input.BatteryController.BluetoothBatteryManager.BluetoothBatteryListener bluetoothBatteryListener) {
            synchronized (this.mBroadcastReceiver) {
                try {
                    if (!bluetoothBatteryListener.equals(this.mRegisteredListener)) {
                        throw new java.lang.IllegalStateException("Listener is not registered.");
                    }
                    this.mRegisteredListener = null;
                    this.mContext.unregisterReceiver(this.mBroadcastReceiver);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.input.BatteryController.BluetoothBatteryManager
        public int getBatteryLevel(java.lang.String str) {
            return com.android.server.input.BatteryController.getBluetoothDevice(this.mContext, str).getBatteryLevel();
        }

        @Override // com.android.server.input.BatteryController.BluetoothBatteryManager
        public void addMetadataListener(java.lang.String str, android.bluetooth.BluetoothAdapter.OnMetadataChangedListener onMetadataChangedListener) {
            android.bluetooth.BluetoothManager bluetoothManager = (android.bluetooth.BluetoothManager) this.mContext.getSystemService(android.bluetooth.BluetoothManager.class);
            java.util.Objects.requireNonNull(bluetoothManager);
            bluetoothManager.getAdapter().addOnMetadataChangedListener(com.android.server.input.BatteryController.getBluetoothDevice(this.mContext, str), this.mExecutor, onMetadataChangedListener);
        }

        @Override // com.android.server.input.BatteryController.BluetoothBatteryManager
        public void removeMetadataListener(java.lang.String str, android.bluetooth.BluetoothAdapter.OnMetadataChangedListener onMetadataChangedListener) {
            android.bluetooth.BluetoothManager bluetoothManager = (android.bluetooth.BluetoothManager) this.mContext.getSystemService(android.bluetooth.BluetoothManager.class);
            java.util.Objects.requireNonNull(bluetoothManager);
            bluetoothManager.getAdapter().removeOnMetadataChangedListener(com.android.server.input.BatteryController.getBluetoothDevice(this.mContext, str), onMetadataChangedListener);
        }

        @Override // com.android.server.input.BatteryController.BluetoothBatteryManager
        public byte[] getMetadata(java.lang.String str, int i) {
            return com.android.server.input.BatteryController.getBluetoothDevice(this.mContext, str).getMetadata(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class State extends android.hardware.input.IInputDeviceBatteryState {
        State(int i) {
            reset(i);
        }

        State(android.hardware.input.IInputDeviceBatteryState iInputDeviceBatteryState) {
            copyFrom(iInputDeviceBatteryState);
        }

        State(int i, long j, boolean z, int i2, float f) {
            initialize(i, j, z, i2, f);
        }

        public void updateIfChanged(android.hardware.input.IInputDeviceBatteryState iInputDeviceBatteryState) {
            if (!equalsIgnoringUpdateTime(iInputDeviceBatteryState)) {
                copyFrom(iInputDeviceBatteryState);
            }
        }

        public void reset(int i) {
            initialize(i, 0L, false, 1, Float.NaN);
        }

        private void copyFrom(android.hardware.input.IInputDeviceBatteryState iInputDeviceBatteryState) {
            initialize(iInputDeviceBatteryState.deviceId, iInputDeviceBatteryState.updateTime, iInputDeviceBatteryState.isPresent, iInputDeviceBatteryState.status, iInputDeviceBatteryState.capacity);
        }

        private void initialize(int i, long j, boolean z, int i2, float f) {
            ((android.hardware.input.IInputDeviceBatteryState) this).deviceId = i;
            ((android.hardware.input.IInputDeviceBatteryState) this).updateTime = j;
            ((android.hardware.input.IInputDeviceBatteryState) this).isPresent = z;
            ((android.hardware.input.IInputDeviceBatteryState) this).status = i2;
            ((android.hardware.input.IInputDeviceBatteryState) this).capacity = f;
        }

        public boolean equalsIgnoringUpdateTime(android.hardware.input.IInputDeviceBatteryState iInputDeviceBatteryState) {
            long j = ((android.hardware.input.IInputDeviceBatteryState) this).updateTime;
            ((android.hardware.input.IInputDeviceBatteryState) this).updateTime = iInputDeviceBatteryState.updateTime;
            boolean equals = equals(iInputDeviceBatteryState);
            ((android.hardware.input.IInputDeviceBatteryState) this).updateTime = j;
            return equals;
        }

        public java.lang.String toString() {
            if (!((android.hardware.input.IInputDeviceBatteryState) this).isPresent) {
                return "State{<not present>}";
            }
            return "State{time=" + ((android.hardware.input.IInputDeviceBatteryState) this).updateTime + ", isPresent=" + ((android.hardware.input.IInputDeviceBatteryState) this).isPresent + ", status=" + ((android.hardware.input.IInputDeviceBatteryState) this).status + ", capacity=" + ((android.hardware.input.IInputDeviceBatteryState) this).capacity + "}";
        }
    }

    private static <K, V> boolean anyOf(android.util.ArrayMap<K, V> arrayMap, java.util.function.Predicate<V> predicate) {
        return findIf(arrayMap, predicate) != null;
    }

    private static <K, V> V findIf(android.util.ArrayMap<K, V> arrayMap, java.util.function.Predicate<V> predicate) {
        for (int i = 0; i < arrayMap.size(); i++) {
            V valueAt = arrayMap.valueAt(i);
            if (predicate.test(valueAt)) {
                return valueAt;
            }
        }
        return null;
    }
}
