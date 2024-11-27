package com.android.server.media;

/* loaded from: classes2.dex */
class BluetoothDeviceRoutesManager {
    private static final java.lang.String HEARING_AID_ROUTE_ID_PREFIX = "HEARING_AID_";
    private static final java.lang.String LE_AUDIO_ROUTE_ID_PREFIX = "LE_AUDIO_";
    private static final java.lang.String TAG = "MR2SystemProvider";

    @android.annotation.NonNull
    private final com.android.server.media.BluetoothDeviceRoutesManager.AdapterStateChangedReceiver mAdapterStateChangedReceiver;

    @android.annotation.NonNull
    private java.util.Map<java.lang.String, android.bluetooth.BluetoothDevice> mAddressToBondedDevice;

    @android.annotation.NonNull
    private final android.bluetooth.BluetoothAdapter mBluetoothAdapter;

    @android.annotation.NonNull
    private final com.android.server.media.BluetoothProfileMonitor mBluetoothProfileMonitor;

    @android.annotation.NonNull
    private final java.util.Map<java.lang.String, com.android.server.media.BluetoothDeviceRoutesManager.BluetoothRouteInfo> mBluetoothRoutes;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.media.BluetoothDeviceRoutesManager.DeviceStateChangedReceiver mDeviceStateChangedReceiver;

    @android.annotation.NonNull
    private final com.android.server.media.BluetoothRouteController.BluetoothRoutesUpdatedListener mListener;

    private static class BluetoothRouteInfo {
        private android.bluetooth.BluetoothDevice mBtDevice;
        private android.util.SparseBooleanArray mConnectedProfiles;
        private android.media.MediaRoute2Info mRoute;

        private BluetoothRouteInfo() {
        }
    }

    BluetoothDeviceRoutesManager(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.bluetooth.BluetoothAdapter bluetoothAdapter, @android.annotation.NonNull com.android.server.media.BluetoothRouteController.BluetoothRoutesUpdatedListener bluetoothRoutesUpdatedListener) {
        this(context, bluetoothAdapter, new com.android.server.media.BluetoothProfileMonitor(context, bluetoothAdapter), bluetoothRoutesUpdatedListener);
    }

    @com.android.internal.annotations.VisibleForTesting
    BluetoothDeviceRoutesManager(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.bluetooth.BluetoothAdapter bluetoothAdapter, @android.annotation.NonNull com.android.server.media.BluetoothProfileMonitor bluetoothProfileMonitor, @android.annotation.NonNull com.android.server.media.BluetoothRouteController.BluetoothRoutesUpdatedListener bluetoothRoutesUpdatedListener) {
        this.mAdapterStateChangedReceiver = new com.android.server.media.BluetoothDeviceRoutesManager.AdapterStateChangedReceiver();
        this.mDeviceStateChangedReceiver = new com.android.server.media.BluetoothDeviceRoutesManager.DeviceStateChangedReceiver();
        this.mAddressToBondedDevice = new java.util.HashMap();
        this.mBluetoothRoutes = new java.util.HashMap();
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        java.util.Objects.requireNonNull(bluetoothAdapter);
        this.mBluetoothAdapter = bluetoothAdapter;
        java.util.Objects.requireNonNull(bluetoothProfileMonitor);
        this.mBluetoothProfileMonitor = bluetoothProfileMonitor;
        java.util.Objects.requireNonNull(bluetoothRoutesUpdatedListener);
        this.mListener = bluetoothRoutesUpdatedListener;
    }

    public void start(android.os.UserHandle userHandle) {
        this.mBluetoothProfileMonitor.start();
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mContext.registerReceiverAsUser(this.mAdapterStateChangedReceiver, userHandle, intentFilter, null, null);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter2.addAction("android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED");
        intentFilter2.addAction("android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter2.addAction("android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED");
        this.mContext.registerReceiverAsUser(this.mDeviceStateChangedReceiver, userHandle, intentFilter2, null, null);
        updateBluetoothRoutes();
    }

    public void stop() {
        this.mContext.unregisterReceiver(this.mAdapterStateChangedReceiver);
        this.mContext.unregisterReceiver(this.mDeviceStateChangedReceiver);
    }

    @android.annotation.Nullable
    public synchronized java.lang.String getRouteIdForBluetoothAddress(@android.annotation.Nullable java.lang.String str) {
        java.lang.String str2;
        android.bluetooth.BluetoothDevice bluetoothDevice = this.mAddressToBondedDevice.get(str);
        if (bluetoothDevice != null) {
            str2 = createBluetoothRoute(bluetoothDevice).mRoute.getId();
        } else {
            str2 = null;
        }
        return str2;
    }

    public synchronized void activateBluetoothDeviceWithAddress(java.lang.String str) {
        com.android.server.media.BluetoothDeviceRoutesManager.BluetoothRouteInfo bluetoothRouteInfo = this.mBluetoothRoutes.get(str);
        if (bluetoothRouteInfo == null) {
            android.util.Slog.w(TAG, "activateBluetoothDeviceWithAddress: Ignoring unknown address " + str);
            return;
        }
        this.mBluetoothAdapter.setActiveDevice(bluetoothRouteInfo.mBtDevice, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBluetoothRoutes() {
        java.util.Set<android.bluetooth.BluetoothDevice> bondedDevices = this.mBluetoothAdapter.getBondedDevices();
        synchronized (this) {
            try {
                this.mBluetoothRoutes.clear();
                if (bondedDevices == null) {
                    android.util.Log.w(TAG, "BluetoothAdapter.getBondedDevices returned null.");
                    return;
                }
                this.mAddressToBondedDevice = (java.util.Map) bondedDevices.stream().collect(java.util.stream.Collectors.toMap(new java.util.function.Function() { // from class: com.android.server.media.BluetoothDeviceRoutesManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        return ((android.bluetooth.BluetoothDevice) obj).getAddress();
                    }
                }, java.util.function.Function.identity()));
                for (android.bluetooth.BluetoothDevice bluetoothDevice : bondedDevices) {
                    if (bluetoothDevice.isConnected()) {
                        com.android.server.media.BluetoothDeviceRoutesManager.BluetoothRouteInfo createBluetoothRoute = createBluetoothRoute(bluetoothDevice);
                        if (createBluetoothRoute.mConnectedProfiles.size() > 0) {
                            this.mBluetoothRoutes.put(bluetoothDevice.getAddress(), createBluetoothRoute);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    public java.util.List<android.media.MediaRoute2Info> getAvailableBluetoothRoutes() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.HashSet hashSet = new java.util.HashSet();
        synchronized (this) {
            try {
                for (com.android.server.media.BluetoothDeviceRoutesManager.BluetoothRouteInfo bluetoothRouteInfo : this.mBluetoothRoutes.values()) {
                    if (hashSet.add(bluetoothRouteInfo.mRoute.getId())) {
                        arrayList.add(bluetoothRouteInfo.mRoute);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyBluetoothRoutesUpdated() {
        this.mListener.onBluetoothRoutesUpdated();
    }

    private com.android.server.media.BluetoothDeviceRoutesManager.BluetoothRouteInfo createBluetoothRoute(android.bluetooth.BluetoothDevice bluetoothDevice) {
        java.lang.String name;
        int i;
        com.android.server.media.BluetoothDeviceRoutesManager.BluetoothRouteInfo bluetoothRouteInfo = new com.android.server.media.BluetoothDeviceRoutesManager.BluetoothRouteInfo();
        bluetoothRouteInfo.mBtDevice = bluetoothDevice;
        if (com.android.media.flags.Flags.enableUseOfBluetoothDeviceGetAliasForMr2infoGetName()) {
            name = bluetoothDevice.getAlias();
        } else {
            name = bluetoothDevice.getName();
        }
        if (android.text.TextUtils.isEmpty(name)) {
            name = this.mContext.getResources().getText(android.R.string.unknownName).toString();
        }
        java.lang.String address = bluetoothDevice.getAddress();
        bluetoothRouteInfo.mConnectedProfiles = new android.util.SparseBooleanArray();
        if (this.mBluetoothProfileMonitor.isProfileSupported(2, bluetoothDevice)) {
            bluetoothRouteInfo.mConnectedProfiles.put(2, true);
        }
        if (!this.mBluetoothProfileMonitor.isProfileSupported(21, bluetoothDevice)) {
            i = 8;
        } else {
            bluetoothRouteInfo.mConnectedProfiles.put(21, true);
            address = HEARING_AID_ROUTE_ID_PREFIX + this.mBluetoothProfileMonitor.getGroupId(21, bluetoothDevice);
            i = 23;
        }
        if (this.mBluetoothProfileMonitor.isProfileSupported(22, bluetoothDevice)) {
            bluetoothRouteInfo.mConnectedProfiles.put(22, true);
            address = LE_AUDIO_ROUTE_ID_PREFIX + this.mBluetoothProfileMonitor.getGroupId(22, bluetoothDevice);
            i = 26;
        }
        bluetoothRouteInfo.mRoute = new android.media.MediaRoute2Info.Builder(address, name).addFeature("android.media.route.feature.LIVE_AUDIO").addFeature("android.media.route.feature.LOCAL_PLAYBACK").setConnectionState(0).setDescription(this.mContext.getResources().getText(android.R.string.biometric_error_generic).toString()).setType(i).setAddress(bluetoothDevice.getAddress()).build();
        return bluetoothRouteInfo;
    }

    private class AdapterStateChangedReceiver extends android.content.BroadcastReceiver {
        private AdapterStateChangedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            boolean z;
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
            if (intExtra == 10 || intExtra == 13) {
                synchronized (com.android.server.media.BluetoothDeviceRoutesManager.this) {
                    com.android.server.media.BluetoothDeviceRoutesManager.this.mBluetoothRoutes.clear();
                }
                com.android.server.media.BluetoothDeviceRoutesManager.this.notifyBluetoothRoutesUpdated();
            } else if (intExtra == 12) {
                com.android.server.media.BluetoothDeviceRoutesManager.this.updateBluetoothRoutes();
                synchronized (com.android.server.media.BluetoothDeviceRoutesManager.this) {
                    z = !com.android.server.media.BluetoothDeviceRoutesManager.this.mBluetoothRoutes.isEmpty();
                }
                if (z) {
                    com.android.server.media.BluetoothDeviceRoutesManager.this.notifyBluetoothRoutesUpdated();
                }
            }
        }
    }

    private class DeviceStateChangedReceiver extends android.content.BroadcastReceiver {
        private DeviceStateChangedReceiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            char c;
            java.lang.String action = intent.getAction();
            switch (action.hashCode()) {
                case -1765714821:
                    if (action.equals("android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -612790895:
                    if (action.equals("android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1244161670:
                    if (action.equals("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                    com.android.server.media.BluetoothDeviceRoutesManager.this.updateBluetoothRoutes();
                    com.android.server.media.BluetoothDeviceRoutesManager.this.notifyBluetoothRoutesUpdated();
                    break;
            }
        }
    }
}
