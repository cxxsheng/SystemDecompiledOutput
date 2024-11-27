package com.android.server.media;

/* loaded from: classes2.dex */
final class AudioManagerRouteController implements com.android.server.media.DeviceRouteController {
    private static final java.lang.String TAG = "MR2SystemProvider";

    @android.annotation.NonNull
    private final android.media.AudioManager mAudioManager;

    @android.annotation.NonNull
    private final com.android.server.media.BluetoothDeviceRoutesManager mBluetoothRouteController;
    private final int mBuiltInSpeakerSuitabilityStatus;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final com.android.server.media.DeviceRouteController.OnDeviceRouteChangedListener mOnDeviceRouteChangedListener;

    @android.annotation.NonNull
    private android.media.MediaRoute2Info mSelectedRoute;

    @android.annotation.NonNull
    private final android.media.audiopolicy.AudioProductStrategy mStrategyForMedia;

    @android.annotation.NonNull
    private static final android.media.AudioAttributes MEDIA_USAGE_AUDIO_ATTRIBUTES = new android.media.AudioAttributes.Builder().setUsage(1).build();

    @android.annotation.NonNull
    private static final android.util.SparseArray<com.android.server.media.AudioManagerRouteController.SystemRouteInfo> AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO = new android.util.SparseArray<>();

    @android.annotation.NonNull
    private final java.util.Map<java.lang.String, com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder> mRouteIdToAvailableDeviceRoutes = new java.util.HashMap();

    @android.annotation.NonNull
    private final android.media.AudioDeviceCallback mAudioDeviceCallback = new com.android.server.media.AudioManagerRouteController.AudioDeviceCallbackImpl();

    @android.annotation.NonNull
    private final android.media.AudioManager.OnDevicesForAttributesChangedListener mOnDevicesForAttributesChangedListener = new android.media.AudioManager.OnDevicesForAttributesChangedListener() { // from class: com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda0
        public final void onDevicesForAttributesChanged(android.media.AudioAttributes audioAttributes, java.util.List list) {
            com.android.server.media.AudioManagerRouteController.this.onDevicesForAttributesChangedListener(audioAttributes, list);
        }
    };

    static {
        AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.put(2, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(2, "ROUTE_ID_BUILTIN_SPEAKER", android.R.string.date_picker_prev_month_button));
        android.util.SparseArray<com.android.server.media.AudioManagerRouteController.SystemRouteInfo> sparseArray = AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO;
        int i = android.R.string.date_time_set;
        sparseArray.put(3, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(3, "ROUTE_ID_WIRED_HEADSET", i));
        AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.put(4, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(4, "ROUTE_ID_WIRED_HEADPHONES", i));
        android.util.SparseArray<com.android.server.media.AudioManagerRouteController.SystemRouteInfo> sparseArray2 = AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO;
        int i2 = android.R.string.biometric_error_generic;
        sparseArray2.put(8, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(8, "ROUTE_ID_BLUETOOTH_A2DP", i2));
        android.util.SparseArray<com.android.server.media.AudioManagerRouteController.SystemRouteInfo> sparseArray3 = AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO;
        int i3 = android.R.string.date_time_done;
        sparseArray3.put(9, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(9, "ROUTE_ID_HDMI", i3));
        android.util.SparseArray<com.android.server.media.AudioManagerRouteController.SystemRouteInfo> sparseArray4 = AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO;
        int i4 = 13;
        int i5 = android.R.string.date_time;
        sparseArray4.put(13, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(i4, "ROUTE_ID_DOCK", i5));
        android.util.SparseArray<com.android.server.media.AudioManagerRouteController.SystemRouteInfo> sparseArray5 = AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO;
        int i6 = android.R.string.day;
        sparseArray5.put(11, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(11, "ROUTE_ID_USB_DEVICE", i6));
        AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.put(22, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(22, "ROUTE_ID_USB_HEADSET", i6));
        AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.put(10, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(10, "ROUTE_ID_HDMI_ARC", i3));
        AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.put(29, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(29, "ROUTE_ID_HDMI_EARC", i3));
        AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.put(23, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(23, "ROUTE_ID_HEARING_AID", i2));
        int i7 = 26;
        AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.put(26, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(i7, "ROUTE_ID_BLE_HEADSET", i2));
        AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.put(27, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(i7, "ROUTE_ID_BLE_SPEAKER", i2));
        AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.put(30, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(i7, "ROUTE_ID_BLE_BROADCAST", i2));
        int i8 = 0;
        AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.put(6, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(i8, "ROUTE_ID_LINE_DIGITAL", i3));
        AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.put(5, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(i8, "ROUTE_ID_LINE_ANALOG", i3));
        AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.put(19, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(i8, "ROUTE_ID_AUX_LINE", i3));
        AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.put(31, new com.android.server.media.AudioManagerRouteController.SystemRouteInfo(i4, "ROUTE_ID_DOCK_ANALOG", i5));
    }

    @android.annotation.RequiresPermission(anyOf = {"android.permission.MODIFY_AUDIO_ROUTING", "android.permission.QUERY_AUDIO_STATE"})
    AudioManagerRouteController(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.media.AudioManager audioManager, @android.annotation.NonNull android.os.Looper looper, @android.annotation.NonNull android.media.audiopolicy.AudioProductStrategy audioProductStrategy, @android.annotation.NonNull android.bluetooth.BluetoothAdapter bluetoothAdapter, @android.annotation.NonNull com.android.server.media.DeviceRouteController.OnDeviceRouteChangedListener onDeviceRouteChangedListener) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        java.util.Objects.requireNonNull(audioManager);
        this.mAudioManager = audioManager;
        java.util.Objects.requireNonNull(looper);
        this.mHandler = new android.os.Handler(looper);
        java.util.Objects.requireNonNull(audioProductStrategy);
        this.mStrategyForMedia = audioProductStrategy;
        java.util.Objects.requireNonNull(onDeviceRouteChangedListener);
        this.mOnDeviceRouteChangedListener = onDeviceRouteChangedListener;
        this.mBuiltInSpeakerSuitabilityStatus = com.android.server.media.DeviceRouteController.getBuiltInSpeakerSuitabilityStatus(this.mContext);
        this.mBluetoothRouteController = new com.android.server.media.BluetoothDeviceRoutesManager(this.mContext, bluetoothAdapter, new com.android.server.media.BluetoothRouteController.BluetoothRoutesUpdatedListener() { // from class: com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda1
            @Override // com.android.server.media.BluetoothRouteController.BluetoothRoutesUpdatedListener
            public final void onBluetoothRoutesUpdated() {
                com.android.server.media.AudioManagerRouteController.this.rebuildAvailableRoutesAndNotify();
            }
        });
        rebuildAvailableRoutes();
    }

    @Override // com.android.server.media.DeviceRouteController
    @android.annotation.RequiresPermission(anyOf = {"android.permission.MODIFY_AUDIO_ROUTING", "android.permission.QUERY_AUDIO_STATE"})
    public void start(android.os.UserHandle userHandle) {
        this.mBluetoothRouteController.start(userHandle);
        this.mAudioManager.registerAudioDeviceCallback(this.mAudioDeviceCallback, this.mHandler);
        this.mAudioManager.addOnDevicesForAttributesChangedListener(com.android.server.media.AudioRoutingUtils.ATTRIBUTES_MEDIA, new android.os.HandlerExecutor(this.mHandler), this.mOnDevicesForAttributesChangedListener);
    }

    @Override // com.android.server.media.DeviceRouteController
    @android.annotation.RequiresPermission(anyOf = {"android.permission.MODIFY_AUDIO_ROUTING", "android.permission.QUERY_AUDIO_STATE"})
    public void stop() {
        this.mAudioManager.removeOnDevicesForAttributesChangedListener(this.mOnDevicesForAttributesChangedListener);
        this.mAudioManager.unregisterAudioDeviceCallback(this.mAudioDeviceCallback);
        this.mBluetoothRouteController.stop();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.android.server.media.DeviceRouteController
    @android.annotation.NonNull
    public synchronized android.media.MediaRoute2Info getSelectedRoute() {
        return this.mSelectedRoute;
    }

    @Override // com.android.server.media.DeviceRouteController
    @android.annotation.NonNull
    public synchronized java.util.List<android.media.MediaRoute2Info> getAvailableRoutes() {
        return this.mRouteIdToAvailableDeviceRoutes.values().stream().map(new java.util.function.Function() { // from class: com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.media.MediaRoute2Info mediaRoute2Info;
                mediaRoute2Info = ((com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder) obj).mMediaRoute2Info;
                return mediaRoute2Info;
            }
        }).toList();
    }

    @Override // com.android.server.media.DeviceRouteController
    @android.annotation.RequiresPermission("android.permission.MODIFY_AUDIO_ROUTING")
    public synchronized void transferTo(@android.annotation.Nullable java.lang.String str) {
        if (str == null) {
            android.util.Slog.e(TAG, "Unexpected call to AudioPoliciesDeviceRouteController#transferTo(null)");
            return;
        }
        com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder mediaRoute2InfoHolder = this.mRouteIdToAvailableDeviceRoutes.get(str);
        if (mediaRoute2InfoHolder == null) {
            android.util.Slog.w(TAG, "transferTo: Ignoring transfer request to unknown route id : " + str);
            return;
        }
        if (mediaRoute2InfoHolder.mCorrespondsToInactiveBluetoothRoute) {
            this.mBluetoothRouteController.activateBluetoothDeviceWithAddress(mediaRoute2InfoHolder.mMediaRoute2Info.getAddress());
            this.mAudioManager.removePreferredDeviceForStrategy(this.mStrategyForMedia);
        } else {
            this.mAudioManager.setPreferredDeviceForStrategy(this.mStrategyForMedia, new android.media.AudioDeviceAttributes(2, mediaRoute2InfoHolder.mAudioDeviceInfoType, ""));
        }
    }

    @Override // com.android.server.media.DeviceRouteController
    @android.annotation.RequiresPermission(anyOf = {"android.permission.MODIFY_AUDIO_ROUTING", "android.permission.QUERY_AUDIO_STATE"})
    public synchronized boolean updateVolume(int i) {
        rebuildAvailableRoutesAndNotify();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.RequiresPermission(anyOf = {"android.permission.MODIFY_AUDIO_ROUTING", "android.permission.QUERY_AUDIO_STATE"})
    public void onDevicesForAttributesChangedListener(android.media.AudioAttributes audioAttributes, java.util.List<android.media.AudioDeviceAttributes> list) {
        if (audioAttributes.getUsage() == 1) {
            rebuildAvailableRoutesAndNotify();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.RequiresPermission(anyOf = {"android.permission.MODIFY_AUDIO_ROUTING", "android.permission.QUERY_AUDIO_STATE"})
    public synchronized void rebuildAvailableRoutesAndNotify() {
        rebuildAvailableRoutes();
        this.mOnDeviceRouteChangedListener.onDeviceRouteChanged();
    }

    @android.annotation.RequiresPermission(anyOf = {"android.permission.MODIFY_AUDIO_ROUTING", "android.permission.QUERY_AUDIO_STATE"})
    private synchronized void rebuildAvailableRoutes() {
        int type;
        try {
            java.util.List devicesForAttributes = this.mAudioManager.getDevicesForAttributes(MEDIA_USAGE_AUDIO_ATTRIBUTES);
            if (devicesForAttributes.isEmpty()) {
                android.util.Slog.e(TAG, "Unexpected empty list of output devices for media. Using built-in speakers.");
                type = 2;
            } else {
                if (devicesForAttributes.size() > 1) {
                    android.util.Slog.w(TAG, "AudioManager.getDevicesForAttributes returned more than one element. Using the first one.");
                }
                type = ((android.media.AudioDeviceAttributes) devicesForAttributes.get(0)).getType();
            }
            android.media.AudioDeviceInfo[] devices = this.mAudioManager.getDevices(2);
            this.mRouteIdToAvailableDeviceRoutes.clear();
            com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder mediaRoute2InfoHolder = null;
            for (android.media.AudioDeviceInfo audioDeviceInfo : devices) {
                android.media.MediaRoute2Info createMediaRoute2InfoFromAudioDeviceInfo = createMediaRoute2InfoFromAudioDeviceInfo(audioDeviceInfo);
                if (createMediaRoute2InfoFromAudioDeviceInfo != null) {
                    int type2 = audioDeviceInfo.getType();
                    com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder createForAudioManagerRoute = com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder.createForAudioManagerRoute(createMediaRoute2InfoFromAudioDeviceInfo, type2);
                    this.mRouteIdToAvailableDeviceRoutes.put(createMediaRoute2InfoFromAudioDeviceInfo.getId(), createForAudioManagerRoute);
                    if (type == type2) {
                        mediaRoute2InfoHolder = createForAudioManagerRoute;
                    }
                }
            }
            if (this.mRouteIdToAvailableDeviceRoutes.isEmpty()) {
                android.util.Slog.e(TAG, "Ended up with an empty list of routes. Creating a placeholder route.");
                com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder createPlaceholderBuiltinSpeakerRoute = createPlaceholderBuiltinSpeakerRoute();
                this.mRouteIdToAvailableDeviceRoutes.put(createPlaceholderBuiltinSpeakerRoute.mMediaRoute2Info.getId(), createPlaceholderBuiltinSpeakerRoute);
            }
            if (mediaRoute2InfoHolder == null) {
                android.util.Slog.e(TAG, "Could not map this selected device attribute type to an available route: " + type);
                mediaRoute2InfoHolder = this.mRouteIdToAvailableDeviceRoutes.values().iterator().next();
            }
            com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder copyWithVolumeInfoFromAudioManager = mediaRoute2InfoHolder.copyWithVolumeInfoFromAudioManager(this.mAudioManager);
            this.mRouteIdToAvailableDeviceRoutes.put(mediaRoute2InfoHolder.mMediaRoute2Info.getId(), copyWithVolumeInfoFromAudioManager);
            this.mSelectedRoute = copyWithVolumeInfoFromAudioManager.mMediaRoute2Info;
            this.mBluetoothRouteController.getAvailableBluetoothRoutes().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$rebuildAvailableRoutes$1;
                    lambda$rebuildAvailableRoutes$1 = com.android.server.media.AudioManagerRouteController.this.lambda$rebuildAvailableRoutes$1((android.media.MediaRoute2Info) obj);
                    return lambda$rebuildAvailableRoutes$1;
                }
            }).map(new java.util.function.Function() { // from class: com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder.createForInactiveBluetoothRoute((android.media.MediaRoute2Info) obj);
                }
            }).forEach(new java.util.function.Consumer() { // from class: com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.media.AudioManagerRouteController.this.lambda$rebuildAvailableRoutes$2((com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder) obj);
                }
            });
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$rebuildAvailableRoutes$1(android.media.MediaRoute2Info mediaRoute2Info) {
        return !this.mRouteIdToAvailableDeviceRoutes.containsKey(mediaRoute2Info.getId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rebuildAvailableRoutes$2(com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder mediaRoute2InfoHolder) {
        this.mRouteIdToAvailableDeviceRoutes.put(mediaRoute2InfoHolder.mMediaRoute2Info.getId(), mediaRoute2InfoHolder);
    }

    private com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder createPlaceholderBuiltinSpeakerRoute() {
        return com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder.createForAudioManagerRoute(createMediaRoute2Info(null, 2, null, null), 2);
    }

    @android.annotation.Nullable
    private android.media.MediaRoute2Info createMediaRoute2InfoFromAudioDeviceInfo(android.media.AudioDeviceInfo audioDeviceInfo) {
        java.lang.String routeIdForBluetoothAddress;
        java.lang.String address = audioDeviceInfo.getAddress();
        if (android.text.TextUtils.isEmpty(address)) {
            routeIdForBluetoothAddress = null;
        } else {
            routeIdForBluetoothAddress = this.mBluetoothRouteController.getRouteIdForBluetoothAddress(address);
        }
        return createMediaRoute2Info(routeIdForBluetoothAddress, audioDeviceInfo.getType(), audioDeviceInfo.getPort().name(), address);
    }

    @android.annotation.Nullable
    private android.media.MediaRoute2Info createMediaRoute2Info(@android.annotation.Nullable java.lang.String str, int i, @android.annotation.Nullable java.lang.CharSequence charSequence, @android.annotation.Nullable java.lang.String str2) {
        com.android.server.media.AudioManagerRouteController.SystemRouteInfo systemRouteInfo = AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.get(i);
        if (systemRouteInfo == null) {
            return null;
        }
        if (android.text.TextUtils.isEmpty(charSequence)) {
            charSequence = this.mContext.getResources().getText(systemRouteInfo.mNameResource);
        }
        if (str == null) {
            str = systemRouteInfo.mDefaultRouteId;
        }
        android.media.MediaRoute2Info.Builder connectionState = new android.media.MediaRoute2Info.Builder(str, charSequence).setType(systemRouteInfo.mMediaRoute2InfoType).setAddress(str2).setSystemRoute(true).addFeature("android.media.route.feature.LIVE_AUDIO").addFeature("android.media.route.feature.LOCAL_PLAYBACK").setConnectionState(2);
        if (systemRouteInfo.mMediaRoute2InfoType == 2) {
            connectionState.setSuitabilityStatus(this.mBuiltInSpeakerSuitabilityStatus);
        }
        return connectionState.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MediaRoute2InfoHolder {
        public final int mAudioDeviceInfoType;
        public final boolean mCorrespondsToInactiveBluetoothRoute;
        public final android.media.MediaRoute2Info mMediaRoute2Info;

        public static com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder createForAudioManagerRoute(android.media.MediaRoute2Info mediaRoute2Info, int i) {
            return new com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder(mediaRoute2Info, i, false);
        }

        public static com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder createForInactiveBluetoothRoute(android.media.MediaRoute2Info mediaRoute2Info) {
            return new com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder(mediaRoute2Info, 0, true);
        }

        private MediaRoute2InfoHolder(android.media.MediaRoute2Info mediaRoute2Info, int i, boolean z) {
            this.mMediaRoute2Info = mediaRoute2Info;
            this.mAudioDeviceInfoType = i;
            this.mCorrespondsToInactiveBluetoothRoute = z;
        }

        public com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder copyWithVolumeInfoFromAudioManager(android.media.AudioManager audioManager) {
            int i;
            android.media.MediaRoute2Info.Builder builder = new android.media.MediaRoute2Info.Builder(this.mMediaRoute2Info);
            if (audioManager.isVolumeFixed()) {
                i = 0;
            } else {
                i = 1;
            }
            return new com.android.server.media.AudioManagerRouteController.MediaRoute2InfoHolder(builder.setVolumeHandling(i).setVolume(audioManager.getStreamVolume(3)).setVolumeMax(audioManager.getStreamMaxVolume(3)).build(), this.mAudioDeviceInfoType, this.mCorrespondsToInactiveBluetoothRoute);
        }
    }

    private static class SystemRouteInfo {
        public final java.lang.String mDefaultRouteId;
        public final int mMediaRoute2InfoType;
        public final int mNameResource;

        private SystemRouteInfo(int i, java.lang.String str, int i2) {
            this.mMediaRoute2InfoType = i;
            this.mDefaultRouteId = str;
            this.mNameResource = i2;
        }
    }

    private class AudioDeviceCallbackImpl extends android.media.AudioDeviceCallback {
        private AudioDeviceCallbackImpl() {
        }

        @Override // android.media.AudioDeviceCallback
        @android.annotation.RequiresPermission("android.permission.MODIFY_AUDIO_ROUTING")
        public void onAudioDevicesAdded(android.media.AudioDeviceInfo[] audioDeviceInfoArr) {
            for (android.media.AudioDeviceInfo audioDeviceInfo : audioDeviceInfoArr) {
                if (com.android.server.media.AudioManagerRouteController.AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.contains(audioDeviceInfo.getType())) {
                    com.android.server.media.AudioManagerRouteController.this.mAudioManager.removePreferredDeviceForStrategy(com.android.server.media.AudioManagerRouteController.this.mStrategyForMedia);
                    com.android.server.media.AudioManagerRouteController.this.rebuildAvailableRoutesAndNotify();
                    return;
                }
            }
        }

        @Override // android.media.AudioDeviceCallback
        @android.annotation.RequiresPermission(anyOf = {"android.permission.MODIFY_AUDIO_ROUTING", "android.permission.QUERY_AUDIO_STATE"})
        public void onAudioDevicesRemoved(android.media.AudioDeviceInfo[] audioDeviceInfoArr) {
            for (android.media.AudioDeviceInfo audioDeviceInfo : audioDeviceInfoArr) {
                if (com.android.server.media.AudioManagerRouteController.AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.contains(audioDeviceInfo.getType())) {
                    com.android.server.media.AudioManagerRouteController.this.rebuildAvailableRoutesAndNotify();
                    return;
                }
            }
        }
    }
}
