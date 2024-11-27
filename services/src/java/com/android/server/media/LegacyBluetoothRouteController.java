package com.android.server.media;

/* loaded from: classes2.dex */
class LegacyBluetoothRouteController implements com.android.server.media.BluetoothRouteController {
    private static final java.lang.String HEARING_AID_ROUTE_ID_PREFIX = "HEARING_AID_";
    private static final java.lang.String LE_AUDIO_ROUTE_ID_PREFIX = "LE_AUDIO_";
    private android.bluetooth.BluetoothA2dp mA2dpProfile;
    private final android.media.AudioManager mAudioManager;
    private final android.bluetooth.BluetoothAdapter mBluetoothAdapter;
    private final android.content.Context mContext;
    private android.bluetooth.BluetoothHearingAid mHearingAidProfile;
    private android.bluetooth.BluetoothLeAudio mLeAudioProfile;
    private final com.android.server.media.BluetoothRouteController.BluetoothRoutesUpdatedListener mListener;
    private static final java.lang.String TAG = "LBtRouteProvider";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private final java.util.Map<java.lang.String, com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo> mBluetoothRoutes = new java.util.HashMap();
    private final java.util.List<com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo> mActiveRoutes = new java.util.ArrayList();
    private final android.util.SparseIntArray mVolumeMap = new android.util.SparseIntArray();
    private final com.android.server.media.LegacyBluetoothRouteController.BluetoothProfileListener mProfileListener = new com.android.server.media.LegacyBluetoothRouteController.BluetoothProfileListener();
    private final com.android.server.media.LegacyBluetoothRouteController.AdapterStateChangedReceiver mAdapterStateChangedReceiver = new com.android.server.media.LegacyBluetoothRouteController.AdapterStateChangedReceiver();
    private final com.android.server.media.LegacyBluetoothRouteController.DeviceStateChangedReceiver mDeviceStateChangedReceiver = new com.android.server.media.LegacyBluetoothRouteController.DeviceStateChangedReceiver();

    LegacyBluetoothRouteController(android.content.Context context, android.bluetooth.BluetoothAdapter bluetoothAdapter, com.android.server.media.BluetoothRouteController.BluetoothRoutesUpdatedListener bluetoothRoutesUpdatedListener) {
        this.mContext = context;
        this.mBluetoothAdapter = bluetoothAdapter;
        this.mListener = bluetoothRoutesUpdatedListener;
        this.mAudioManager = (android.media.AudioManager) this.mContext.getSystemService("audio");
        buildBluetoothRoutes();
    }

    @Override // com.android.server.media.BluetoothRouteController
    public void start(android.os.UserHandle userHandle) {
        this.mBluetoothAdapter.getProfileProxy(this.mContext, this.mProfileListener, 2);
        this.mBluetoothAdapter.getProfileProxy(this.mContext, this.mProfileListener, 21);
        this.mBluetoothAdapter.getProfileProxy(this.mContext, this.mProfileListener, 22);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mContext.registerReceiverAsUser(this.mAdapterStateChangedReceiver, userHandle, intentFilter, null, null);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED");
        intentFilter2.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter2.addAction("android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED");
        intentFilter2.addAction("android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter2.addAction("android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED");
        intentFilter2.addAction("android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED");
        this.mContext.registerReceiverAsUser(this.mDeviceStateChangedReceiver, userHandle, intentFilter2, null, null);
    }

    @Override // com.android.server.media.BluetoothRouteController
    public void stop() {
        this.mContext.unregisterReceiver(this.mAdapterStateChangedReceiver);
        this.mContext.unregisterReceiver(this.mDeviceStateChangedReceiver);
    }

    @Override // com.android.server.media.BluetoothRouteController
    public void transferTo(@android.annotation.Nullable java.lang.String str) {
        if (str == null) {
            clearActiveDevices();
            return;
        }
        com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo findBluetoothRouteWithRouteId = findBluetoothRouteWithRouteId(str);
        if (findBluetoothRouteWithRouteId == null) {
            android.util.Slog.w(TAG, "transferTo: Unknown route. ID=" + str);
            return;
        }
        if (this.mBluetoothAdapter != null) {
            this.mBluetoothAdapter.setActiveDevice(findBluetoothRouteWithRouteId.mBtDevice, 0);
        }
    }

    private com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo findBluetoothRouteWithRouteId(java.lang.String str) {
        if (str == null) {
            return null;
        }
        for (com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo bluetoothRouteInfo : this.mBluetoothRoutes.values()) {
            if (android.text.TextUtils.equals(bluetoothRouteInfo.mRoute.getId(), str)) {
                return bluetoothRouteInfo;
            }
        }
        return null;
    }

    private void clearActiveDevices() {
        if (this.mBluetoothAdapter != null) {
            this.mBluetoothAdapter.removeActiveDevice(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void buildBluetoothRoutes() {
        this.mBluetoothRoutes.clear();
        java.util.Set<android.bluetooth.BluetoothDevice> bondedDevices = this.mBluetoothAdapter.getBondedDevices();
        if (bondedDevices != null) {
            for (android.bluetooth.BluetoothDevice bluetoothDevice : bondedDevices) {
                if (bluetoothDevice.isConnected()) {
                    com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo createBluetoothRoute = createBluetoothRoute(bluetoothDevice);
                    if (createBluetoothRoute.mConnectedProfiles.size() > 0) {
                        this.mBluetoothRoutes.put(bluetoothDevice.getAddress(), createBluetoothRoute);
                    }
                }
            }
        }
    }

    @Override // com.android.server.media.BluetoothRouteController
    @android.annotation.Nullable
    public android.media.MediaRoute2Info getSelectedRoute() {
        if (this.mActiveRoutes.isEmpty()) {
            return null;
        }
        return this.mActiveRoutes.get(0).mRoute;
    }

    @Override // com.android.server.media.BluetoothRouteController
    @android.annotation.NonNull
    public java.util.List<android.media.MediaRoute2Info> getTransferableRoutes() {
        java.util.List<android.media.MediaRoute2Info> allBluetoothRoutes = getAllBluetoothRoutes();
        java.util.Iterator<com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo> it = this.mActiveRoutes.iterator();
        while (it.hasNext()) {
            allBluetoothRoutes.remove(it.next().mRoute);
        }
        return allBluetoothRoutes;
    }

    @Override // com.android.server.media.BluetoothRouteController
    @android.annotation.NonNull
    public java.util.List<android.media.MediaRoute2Info> getAllBluetoothRoutes() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        android.media.MediaRoute2Info selectedRoute = getSelectedRoute();
        if (selectedRoute != null) {
            arrayList.add(selectedRoute);
            arrayList2.add(selectedRoute.getId());
        }
        for (com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo bluetoothRouteInfo : this.mBluetoothRoutes.values()) {
            if (!arrayList2.contains(bluetoothRouteInfo.mRoute.getId())) {
                arrayList.add(bluetoothRouteInfo.mRoute);
                arrayList2.add(bluetoothRouteInfo.mRoute.getId());
            }
        }
        return arrayList;
    }

    @Override // com.android.server.media.BluetoothRouteController
    public boolean updateVolumeForDevices(int i, int i2) {
        int i3;
        boolean z = false;
        if ((134217728 & i) != 0) {
            i3 = 23;
        } else if ((i & android.hardware.audio.common.V2_0.AudioDevice.OUT_ALL_A2DP) != 0) {
            i3 = 8;
        } else {
            if ((i & 536870912) == 0) {
                return false;
            }
            i3 = 26;
        }
        this.mVolumeMap.put(i3, i2);
        for (com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo bluetoothRouteInfo : this.mActiveRoutes) {
            if (bluetoothRouteInfo.mRoute.getType() == i3) {
                bluetoothRouteInfo.mRoute = new android.media.MediaRoute2Info.Builder(bluetoothRouteInfo.mRoute).setVolume(i2).build();
                z = true;
            }
        }
        if (z) {
            notifyBluetoothRoutesUpdated();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyBluetoothRoutesUpdated() {
        if (this.mListener != null) {
            this.mListener.onBluetoothRoutesUpdated();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo createBluetoothRoute(android.bluetooth.BluetoothDevice bluetoothDevice) {
        java.lang.String name;
        int i;
        com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo bluetoothRouteInfo = new com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo();
        bluetoothRouteInfo.mBtDevice = bluetoothDevice;
        java.lang.String address = bluetoothDevice.getAddress();
        if (com.android.media.flags.Flags.enableUseOfBluetoothDeviceGetAliasForMr2infoGetName()) {
            name = bluetoothDevice.getAlias();
        } else {
            name = bluetoothDevice.getName();
        }
        if (android.text.TextUtils.isEmpty(name)) {
            name = this.mContext.getResources().getText(android.R.string.unknownName).toString();
        }
        bluetoothRouteInfo.mConnectedProfiles = new android.util.SparseBooleanArray();
        if (this.mA2dpProfile != null && this.mA2dpProfile.getConnectedDevices().contains(bluetoothDevice)) {
            bluetoothRouteInfo.mConnectedProfiles.put(2, true);
        }
        if (this.mHearingAidProfile != null && this.mHearingAidProfile.getConnectedDevices().contains(bluetoothDevice)) {
            bluetoothRouteInfo.mConnectedProfiles.put(21, true);
            address = HEARING_AID_ROUTE_ID_PREFIX + this.mHearingAidProfile.getHiSyncId(bluetoothDevice);
            i = 23;
        } else {
            i = 8;
        }
        if (this.mLeAudioProfile != null && this.mLeAudioProfile.getConnectedDevices().contains(bluetoothDevice)) {
            bluetoothRouteInfo.mConnectedProfiles.put(22, true);
            address = LE_AUDIO_ROUTE_ID_PREFIX + this.mLeAudioProfile.getGroupId(bluetoothDevice);
            i = 26;
        }
        bluetoothRouteInfo.mRoute = new android.media.MediaRoute2Info.Builder(address, name).addFeature("android.media.route.feature.LIVE_AUDIO").addFeature("android.media.route.feature.LOCAL_PLAYBACK").setConnectionState(0).setDescription(this.mContext.getResources().getText(android.R.string.biometric_error_generic).toString()).setType(i).setVolumeHandling(1).setVolumeMax(this.mAudioManager.getStreamMaxVolume(3)).setAddress(bluetoothDevice.getAddress()).build();
        return bluetoothRouteInfo;
    }

    private void setRouteConnectionState(@android.annotation.NonNull com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo bluetoothRouteInfo, int i) {
        if (bluetoothRouteInfo == null) {
            android.util.Slog.w(TAG, "setRouteConnectionState: route shouldn't be null");
            return;
        }
        if (bluetoothRouteInfo.mRoute.getConnectionState() == i) {
            return;
        }
        android.media.MediaRoute2Info.Builder connectionState = new android.media.MediaRoute2Info.Builder(bluetoothRouteInfo.mRoute).setConnectionState(i);
        connectionState.setType(bluetoothRouteInfo.getRouteType());
        if (i == 2) {
            connectionState.setVolume(this.mVolumeMap.get(bluetoothRouteInfo.getRouteType(), 0));
        }
        bluetoothRouteInfo.mRoute = connectionState.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addActiveRoute(com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo bluetoothRouteInfo) {
        if (bluetoothRouteInfo == null) {
            android.util.Slog.w(TAG, "addActiveRoute: btRoute is null");
            return;
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "Adding active route: " + bluetoothRouteInfo.mRoute);
        }
        if (this.mActiveRoutes.contains(bluetoothRouteInfo)) {
            android.util.Slog.w(TAG, "addActiveRoute: btRoute is already added.");
        } else {
            setRouteConnectionState(bluetoothRouteInfo, 2);
            this.mActiveRoutes.add(bluetoothRouteInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeActiveRoute(com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo bluetoothRouteInfo) {
        if (DEBUG) {
            android.util.Log.d(TAG, "Removing active route: " + bluetoothRouteInfo.mRoute);
        }
        if (this.mActiveRoutes.remove(bluetoothRouteInfo)) {
            setRouteConnectionState(bluetoothRouteInfo, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearActiveRoutesWithType(int i) {
        if (DEBUG) {
            android.util.Log.d(TAG, "Clearing active routes with type. type=" + i);
        }
        java.util.Iterator<com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo> it = this.mActiveRoutes.iterator();
        while (it.hasNext()) {
            com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo next = it.next();
            if (next.mRoute.getType() == i) {
                it.remove();
                setRouteConnectionState(next, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addActiveDevices(android.bluetooth.BluetoothDevice bluetoothDevice) {
        com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo bluetoothRouteInfo = this.mBluetoothRoutes.get(bluetoothDevice.getAddress());
        if (bluetoothRouteInfo == null) {
            bluetoothRouteInfo = createBluetoothRoute(bluetoothDevice);
            this.mBluetoothRoutes.put(bluetoothDevice.getAddress(), bluetoothRouteInfo);
        }
        addActiveRoute(bluetoothRouteInfo);
        for (com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo bluetoothRouteInfo2 : this.mBluetoothRoutes.values()) {
            if (android.text.TextUtils.equals(bluetoothRouteInfo2.mRoute.getId(), bluetoothRouteInfo.mRoute.getId()) && !android.text.TextUtils.equals(bluetoothRouteInfo2.mBtDevice.getAddress(), bluetoothRouteInfo.mBtDevice.getAddress())) {
                addActiveRoute(bluetoothRouteInfo2);
            }
        }
    }

    private static class BluetoothRouteInfo {
        private android.bluetooth.BluetoothDevice mBtDevice;
        private android.util.SparseBooleanArray mConnectedProfiles;
        private android.media.MediaRoute2Info mRoute;

        private BluetoothRouteInfo() {
        }

        int getRouteType() {
            if (this.mConnectedProfiles.get(21, false)) {
                return 23;
            }
            if (this.mConnectedProfiles.get(22, false)) {
                return 26;
            }
            return 8;
        }
    }

    private final class BluetoothProfileListener implements android.bluetooth.BluetoothProfile.ServiceListener {
        private BluetoothProfileListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, android.bluetooth.BluetoothProfile bluetoothProfile) {
            java.util.List activeDevices;
            switch (i) {
                case 2:
                    com.android.server.media.LegacyBluetoothRouteController.this.mA2dpProfile = (android.bluetooth.BluetoothA2dp) bluetoothProfile;
                    activeDevices = com.android.server.media.LegacyBluetoothRouteController.this.mBluetoothAdapter.getActiveDevices(2);
                    break;
                case 21:
                    com.android.server.media.LegacyBluetoothRouteController.this.mHearingAidProfile = (android.bluetooth.BluetoothHearingAid) bluetoothProfile;
                    activeDevices = com.android.server.media.LegacyBluetoothRouteController.this.mBluetoothAdapter.getActiveDevices(21);
                    break;
                case 22:
                    com.android.server.media.LegacyBluetoothRouteController.this.mLeAudioProfile = (android.bluetooth.BluetoothLeAudio) bluetoothProfile;
                    activeDevices = com.android.server.media.LegacyBluetoothRouteController.this.mBluetoothAdapter.getActiveDevices(22);
                    break;
                default:
                    return;
            }
            for (android.bluetooth.BluetoothDevice bluetoothDevice : bluetoothProfile.getConnectedDevices()) {
                com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo bluetoothRouteInfo = (com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo) com.android.server.media.LegacyBluetoothRouteController.this.mBluetoothRoutes.get(bluetoothDevice.getAddress());
                if (bluetoothRouteInfo == null) {
                    bluetoothRouteInfo = com.android.server.media.LegacyBluetoothRouteController.this.createBluetoothRoute(bluetoothDevice);
                    com.android.server.media.LegacyBluetoothRouteController.this.mBluetoothRoutes.put(bluetoothDevice.getAddress(), bluetoothRouteInfo);
                }
                if (activeDevices.contains(bluetoothDevice)) {
                    com.android.server.media.LegacyBluetoothRouteController.this.addActiveRoute(bluetoothRouteInfo);
                }
            }
            com.android.server.media.LegacyBluetoothRouteController.this.notifyBluetoothRoutesUpdated();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            switch (i) {
                case 2:
                    com.android.server.media.LegacyBluetoothRouteController.this.mA2dpProfile = null;
                    break;
                case 21:
                    com.android.server.media.LegacyBluetoothRouteController.this.mHearingAidProfile = null;
                    break;
                case 22:
                    com.android.server.media.LegacyBluetoothRouteController.this.mLeAudioProfile = null;
                    break;
            }
        }
    }

    private class AdapterStateChangedReceiver extends android.content.BroadcastReceiver {
        private AdapterStateChangedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
            if (intExtra == 10 || intExtra == 13) {
                com.android.server.media.LegacyBluetoothRouteController.this.mBluetoothRoutes.clear();
                com.android.server.media.LegacyBluetoothRouteController.this.notifyBluetoothRoutesUpdated();
            } else if (intExtra == 12) {
                com.android.server.media.LegacyBluetoothRouteController.this.buildBluetoothRoutes();
                if (!com.android.server.media.LegacyBluetoothRouteController.this.mBluetoothRoutes.isEmpty()) {
                    com.android.server.media.LegacyBluetoothRouteController.this.notifyBluetoothRoutesUpdated();
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
            android.bluetooth.BluetoothDevice bluetoothDevice = (android.bluetooth.BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", android.bluetooth.BluetoothDevice.class);
            java.lang.String action = intent.getAction();
            switch (action.hashCode()) {
                case -1765714821:
                    if (action.equals("android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -749511570:
                    if (action.equals("android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -612790895:
                    if (action.equals("android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 487423555:
                    if (action.equals("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1176349464:
                    if (action.equals("android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1244161670:
                    if (action.equals("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED")) {
                        c = 3;
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
                    com.android.server.media.LegacyBluetoothRouteController.this.clearActiveRoutesWithType(8);
                    if (bluetoothDevice != null) {
                        if (com.android.server.media.LegacyBluetoothRouteController.DEBUG) {
                            android.util.Log.d(com.android.server.media.LegacyBluetoothRouteController.TAG, "Setting active a2dp devices. device=" + bluetoothDevice);
                        }
                        com.android.server.media.LegacyBluetoothRouteController.this.addActiveDevices(bluetoothDevice);
                    }
                    com.android.server.media.LegacyBluetoothRouteController.this.notifyBluetoothRoutesUpdated();
                    break;
                case 1:
                    com.android.server.media.LegacyBluetoothRouteController.this.clearActiveRoutesWithType(23);
                    if (bluetoothDevice != null) {
                        if (com.android.server.media.LegacyBluetoothRouteController.DEBUG) {
                            android.util.Log.d(com.android.server.media.LegacyBluetoothRouteController.TAG, "Setting active hearing aid devices. device=" + bluetoothDevice);
                        }
                        com.android.server.media.LegacyBluetoothRouteController.this.addActiveDevices(bluetoothDevice);
                    }
                    com.android.server.media.LegacyBluetoothRouteController.this.notifyBluetoothRoutesUpdated();
                    break;
                case 2:
                    com.android.server.media.LegacyBluetoothRouteController.this.clearActiveRoutesWithType(26);
                    if (bluetoothDevice != null) {
                        if (com.android.server.media.LegacyBluetoothRouteController.DEBUG) {
                            android.util.Log.d(com.android.server.media.LegacyBluetoothRouteController.TAG, "Setting active le audio devices. device=" + bluetoothDevice);
                        }
                        com.android.server.media.LegacyBluetoothRouteController.this.addActiveDevices(bluetoothDevice);
                    }
                    com.android.server.media.LegacyBluetoothRouteController.this.notifyBluetoothRoutesUpdated();
                    break;
                case 3:
                    handleConnectionStateChanged(2, intent, bluetoothDevice);
                    break;
                case 4:
                    handleConnectionStateChanged(21, intent, bluetoothDevice);
                    break;
                case 5:
                    handleConnectionStateChanged(22, intent, bluetoothDevice);
                    break;
            }
        }

        private void handleConnectionStateChanged(int i, android.content.Intent intent, android.bluetooth.BluetoothDevice bluetoothDevice) {
            int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
            com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo bluetoothRouteInfo = (com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo) com.android.server.media.LegacyBluetoothRouteController.this.mBluetoothRoutes.get(bluetoothDevice.getAddress());
            if (intExtra == 2) {
                if (bluetoothRouteInfo == null) {
                    com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo createBluetoothRoute = com.android.server.media.LegacyBluetoothRouteController.this.createBluetoothRoute(bluetoothDevice);
                    if (createBluetoothRoute.mConnectedProfiles.size() > 0) {
                        com.android.server.media.LegacyBluetoothRouteController.this.mBluetoothRoutes.put(bluetoothDevice.getAddress(), createBluetoothRoute);
                        com.android.server.media.LegacyBluetoothRouteController.this.notifyBluetoothRoutesUpdated();
                        return;
                    }
                    return;
                }
                bluetoothRouteInfo.mConnectedProfiles.put(i, true);
                return;
            }
            if ((intExtra == 3 || intExtra == 0) && bluetoothRouteInfo != null) {
                bluetoothRouteInfo.mConnectedProfiles.delete(i);
                if (bluetoothRouteInfo.mConnectedProfiles.size() == 0) {
                    com.android.server.media.LegacyBluetoothRouteController.this.removeActiveRoute((com.android.server.media.LegacyBluetoothRouteController.BluetoothRouteInfo) com.android.server.media.LegacyBluetoothRouteController.this.mBluetoothRoutes.remove(bluetoothDevice.getAddress()));
                    com.android.server.media.LegacyBluetoothRouteController.this.notifyBluetoothRoutesUpdated();
                }
            }
        }
    }
}
