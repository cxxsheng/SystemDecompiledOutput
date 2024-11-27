package com.android.server.media;

/* loaded from: classes2.dex */
interface BluetoothRouteController {

    public interface BluetoothRoutesUpdatedListener {
        void onBluetoothRoutesUpdated();
    }

    @android.annotation.NonNull
    java.util.List<android.media.MediaRoute2Info> getAllBluetoothRoutes();

    @android.annotation.Nullable
    android.media.MediaRoute2Info getSelectedRoute();

    @android.annotation.NonNull
    java.util.List<android.media.MediaRoute2Info> getTransferableRoutes();

    void start(@android.annotation.NonNull android.os.UserHandle userHandle);

    void stop();

    void transferTo(@android.annotation.Nullable java.lang.String str);

    boolean updateVolumeForDevices(int i, int i2);

    @android.annotation.NonNull
    static com.android.server.media.BluetoothRouteController createInstance(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.media.BluetoothRouteController.BluetoothRoutesUpdatedListener bluetoothRoutesUpdatedListener) {
        java.util.Objects.requireNonNull(bluetoothRoutesUpdatedListener);
        android.bluetooth.BluetoothAdapter adapter = ((android.bluetooth.BluetoothManager) context.getSystemService(android.bluetooth.BluetoothManager.class)).getAdapter();
        if (adapter == null || com.android.media.flags.Flags.enableAudioPoliciesDeviceAndBluetoothController()) {
            return new com.android.server.media.BluetoothRouteController.NoOpBluetoothRouteController();
        }
        return new com.android.server.media.LegacyBluetoothRouteController(context, adapter, bluetoothRoutesUpdatedListener);
    }

    public static class NoOpBluetoothRouteController implements com.android.server.media.BluetoothRouteController {
        @Override // com.android.server.media.BluetoothRouteController
        public void start(android.os.UserHandle userHandle) {
        }

        @Override // com.android.server.media.BluetoothRouteController
        public void stop() {
        }

        @Override // com.android.server.media.BluetoothRouteController
        public void transferTo(java.lang.String str) {
        }

        @Override // com.android.server.media.BluetoothRouteController
        public android.media.MediaRoute2Info getSelectedRoute() {
            return null;
        }

        @Override // com.android.server.media.BluetoothRouteController
        public java.util.List<android.media.MediaRoute2Info> getTransferableRoutes() {
            return java.util.Collections.emptyList();
        }

        @Override // com.android.server.media.BluetoothRouteController
        public java.util.List<android.media.MediaRoute2Info> getAllBluetoothRoutes() {
            return java.util.Collections.emptyList();
        }

        @Override // com.android.server.media.BluetoothRouteController
        public boolean updateVolumeForDevices(int i, int i2) {
            return false;
        }
    }
}
