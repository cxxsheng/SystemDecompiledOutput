package com.android.server.media;

/* loaded from: classes2.dex */
interface DeviceRouteController {

    public interface OnDeviceRouteChangedListener {
        void onDeviceRouteChanged();
    }

    java.util.List<android.media.MediaRoute2Info> getAvailableRoutes();

    @android.annotation.NonNull
    android.media.MediaRoute2Info getSelectedRoute();

    void start(android.os.UserHandle userHandle);

    void stop();

    void transferTo(@android.annotation.Nullable java.lang.String str);

    boolean updateVolume(int i);

    @android.annotation.RequiresPermission("android.permission.MODIFY_AUDIO_ROUTING")
    static com.android.server.media.DeviceRouteController createInstance(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Looper looper, @android.annotation.NonNull com.android.server.media.DeviceRouteController.OnDeviceRouteChangedListener onDeviceRouteChangedListener) {
        android.media.AudioManager audioManager = (android.media.AudioManager) context.getSystemService(android.media.AudioManager.class);
        android.media.audiopolicy.AudioProductStrategy mediaAudioProductStrategy = com.android.server.media.AudioRoutingUtils.getMediaAudioProductStrategy();
        android.bluetooth.BluetoothManager bluetoothManager = (android.bluetooth.BluetoothManager) context.getSystemService(android.bluetooth.BluetoothManager.class);
        android.bluetooth.BluetoothAdapter adapter = bluetoothManager != null ? bluetoothManager.getAdapter() : null;
        if (mediaAudioProductStrategy != null && adapter != null && com.android.media.flags.Flags.enableAudioPoliciesDeviceAndBluetoothController()) {
            return new com.android.server.media.AudioManagerRouteController(context, audioManager, looper, mediaAudioProductStrategy, adapter, onDeviceRouteChangedListener);
        }
        return new com.android.server.media.LegacyDeviceRouteController(context, audioManager, android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio")), onDeviceRouteChangedListener);
    }

    static int getBuiltInSpeakerSuitabilityStatus(@android.annotation.NonNull android.content.Context context) {
        if (!com.android.media.flags.Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()) {
            return 0;
        }
        int integer = context.getResources().getInteger(android.R.integer.config_maximumScreenDimDuration);
        switch (integer) {
        }
        return 0;
    }
}
