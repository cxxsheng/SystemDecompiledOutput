package com.android.server.companion.presence;

@android.annotation.SuppressLint({"LongLogTag"})
/* loaded from: classes.dex */
class BleCompanionDeviceScanner implements com.android.server.companion.AssociationStore.OnChangeListener {
    private static final android.bluetooth.le.ScanSettings SCAN_SETTINGS = new android.bluetooth.le.ScanSettings.Builder().setCallbackType(6).setScanMode(0).build();
    private static final java.lang.String TAG = "CDM_BleCompanionDeviceScanner";

    @android.annotation.NonNull
    private final com.android.server.companion.AssociationStore mAssociationStore;

    @android.annotation.Nullable
    private android.bluetooth.le.BluetoothLeScanner mBleScanner;

    @android.annotation.Nullable
    private android.bluetooth.BluetoothAdapter mBtAdapter;

    @android.annotation.NonNull
    private final com.android.server.companion.presence.BleCompanionDeviceScanner.Callback mCallback;
    private boolean mScanning = false;
    private final android.bluetooth.le.ScanCallback mScanCallback = new android.bluetooth.le.ScanCallback() { // from class: com.android.server.companion.presence.BleCompanionDeviceScanner.2
        @Override // android.bluetooth.le.ScanCallback
        public void onScanResult(int i, android.bluetooth.le.ScanResult scanResult) {
            android.bluetooth.BluetoothDevice device = scanResult.getDevice();
            switch (i) {
                case 2:
                    com.android.server.companion.presence.BleCompanionDeviceScanner.this.notifyDeviceFound(device);
                    break;
                case 3:
                default:
                    android.util.Slog.wtf(com.android.server.companion.presence.BleCompanionDeviceScanner.TAG, "Unexpected callback " + com.android.server.companion.presence.BleCompanionDeviceScanner.nameForBleScanCallbackType(i));
                    break;
                case 4:
                    com.android.server.companion.presence.BleCompanionDeviceScanner.this.notifyDeviceLost(device);
                    break;
            }
        }

        @Override // android.bluetooth.le.ScanCallback
        public void onScanFailed(int i) {
            com.android.server.companion.presence.BleCompanionDeviceScanner.this.mScanning = false;
        }
    };

    interface Callback {
        void onBleCompanionDeviceFound(int i);

        void onBleCompanionDeviceLost(int i);
    }

    BleCompanionDeviceScanner(@android.annotation.NonNull com.android.server.companion.AssociationStore associationStore, @android.annotation.NonNull com.android.server.companion.presence.BleCompanionDeviceScanner.Callback callback) {
        this.mAssociationStore = associationStore;
        this.mCallback = callback;
    }

    void init(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.bluetooth.BluetoothAdapter bluetoothAdapter) {
        if (this.mBtAdapter != null) {
            throw new java.lang.IllegalStateException(getClass().getSimpleName() + " is already initialized");
        }
        java.util.Objects.requireNonNull(bluetoothAdapter);
        this.mBtAdapter = bluetoothAdapter;
        checkBleState();
        registerBluetoothStateBroadcastReceiver(context);
        this.mAssociationStore.registerListener(this);
    }

    final void restartScan() {
        enforceInitialized();
        if (this.mBleScanner == null) {
            return;
        }
        stopScanIfNeeded();
        startScan();
    }

    @Override // com.android.server.companion.AssociationStore.OnChangeListener
    public void onAssociationChanged(int i, android.companion.AssociationInfo associationInfo) {
        if (android.os.Looper.getMainLooper().isCurrentThread()) {
            restartScan();
        } else {
            new android.os.Handler(android.os.Looper.getMainLooper()).post(new java.lang.Runnable() { // from class: com.android.server.companion.presence.BleCompanionDeviceScanner$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.companion.presence.BleCompanionDeviceScanner.this.restartScan();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkBleState() {
        enforceInitialized();
        boolean isLeEnabled = this.mBtAdapter.isLeEnabled();
        if (!isLeEnabled || this.mBleScanner == null) {
            if (!isLeEnabled && this.mBleScanner == null) {
                return;
            }
            if (isLeEnabled) {
                this.mBleScanner = this.mBtAdapter.getBluetoothLeScanner();
                if (this.mBleScanner == null) {
                    return;
                }
                startScan();
                return;
            }
            stopScanIfNeeded();
            this.mBleScanner = null;
        }
    }

    void startScan() {
        java.lang.String deviceMacAddressAsString;
        enforceInitialized();
        android.util.Slog.i(TAG, "startBleScan()");
        if (this.mScanning) {
            android.util.Slog.w(TAG, "Scan is already in progress.");
            return;
        }
        if (this.mBleScanner == null) {
            android.util.Slog.w(TAG, "BLE is not available.");
            return;
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        for (android.companion.AssociationInfo associationInfo : this.mAssociationStore.getAssociations()) {
            if (associationInfo.isNotifyOnDeviceNearby() && (deviceMacAddressAsString = associationInfo.getDeviceMacAddressAsString()) != null) {
                hashSet.add(deviceMacAddressAsString);
            }
        }
        if (hashSet.isEmpty()) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(hashSet.size());
        java.util.Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            arrayList.add(new android.bluetooth.le.ScanFilter.Builder().setDeviceAddress((java.lang.String) it.next()).build());
        }
        if (this.mBtAdapter.isLeEnabled()) {
            try {
                this.mBleScanner.startScan(arrayList, SCAN_SETTINGS, this.mScanCallback);
                this.mScanning = true;
                return;
            } catch (java.lang.IllegalStateException e) {
                android.util.Slog.w(TAG, "Exception while starting BLE scanning", e);
                return;
            }
        }
        android.util.Slog.w(TAG, "BLE scanning is not turned on");
    }

    void stopScanIfNeeded() {
        enforceInitialized();
        android.util.Slog.i(TAG, "stopBleScan()");
        if (!this.mScanning) {
            return;
        }
        if (this.mBtAdapter.isLeEnabled()) {
            try {
                this.mBleScanner.stopScan(this.mScanCallback);
            } catch (java.lang.IllegalStateException e) {
                android.util.Slog.w(TAG, "Exception while stopping BLE scanning", e);
            }
        } else {
            android.util.Slog.w(TAG, "BLE scanning is not turned on");
        }
        this.mScanning = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDeviceFound(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice) {
        java.util.Iterator<android.companion.AssociationInfo> it = this.mAssociationStore.getAssociationsByAddress(bluetoothDevice.getAddress()).iterator();
        while (it.hasNext()) {
            this.mCallback.onBleCompanionDeviceFound(it.next().getId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDeviceLost(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice) {
        java.util.Iterator<android.companion.AssociationInfo> it = this.mAssociationStore.getAssociationsByAddress(bluetoothDevice.getAddress()).iterator();
        while (it.hasNext()) {
            this.mCallback.onBleCompanionDeviceLost(it.next().getId());
        }
    }

    private void registerBluetoothStateBroadcastReceiver(android.content.Context context) {
        android.content.BroadcastReceiver broadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.companion.presence.BleCompanionDeviceScanner.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", -1);
                intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
                com.android.server.companion.presence.BleCompanionDeviceScanner.this.checkBleState();
            }
        };
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.BLE_STATE_CHANGED");
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void enforceInitialized() {
        if (this.mBtAdapter != null) {
            return;
        }
        throw new java.lang.IllegalStateException(getClass().getSimpleName() + " is not initialized");
    }

    private static java.lang.String nameForBtState(int i) {
        return android.bluetooth.BluetoothAdapter.nameForState(i) + "(" + i + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String nameForBleScanCallbackType(int i) {
        java.lang.String str;
        switch (i) {
            case 1:
                str = "ALL_MATCHES";
                break;
            case 2:
                str = "FIRST_MATCH";
                break;
            case 3:
            default:
                str = "Unknown";
                break;
            case 4:
                str = "MATCH_LOST";
                break;
        }
        return str + "(" + i + ")";
    }

    private static java.lang.String nameForBleScanErrorCode(int i) {
        java.lang.String str;
        switch (i) {
            case 1:
                str = "ALREADY_STARTED";
                break;
            case 2:
                str = "APPLICATION_REGISTRATION_FAILED";
                break;
            case 3:
                str = "INTERNAL_ERROR";
                break;
            case 4:
                str = "FEATURE_UNSUPPORTED";
                break;
            case 5:
                str = "OUT_OF_HARDWARE_RESOURCES";
                break;
            case 6:
                str = "SCANNING_TOO_FREQUENTLY";
                break;
            default:
                str = "Unknown";
                break;
        }
        return str + "(" + i + ")";
    }
}
