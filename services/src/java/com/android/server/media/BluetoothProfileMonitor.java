package com.android.server.media;

/* loaded from: classes2.dex */
class BluetoothProfileMonitor {
    static final long GROUP_ID_NO_GROUP = -1;

    @android.annotation.Nullable
    private android.bluetooth.BluetoothA2dp mA2dpProfile;

    @android.annotation.NonNull
    private final android.bluetooth.BluetoothAdapter mBluetoothAdapter;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.Nullable
    private android.bluetooth.BluetoothHearingAid mHearingAidProfile;

    @android.annotation.Nullable
    private android.bluetooth.BluetoothLeAudio mLeAudioProfile;

    @android.annotation.NonNull
    private final com.android.server.media.BluetoothProfileMonitor.ProfileListener mProfileListener = new com.android.server.media.BluetoothProfileMonitor.ProfileListener();

    BluetoothProfileMonitor(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.bluetooth.BluetoothAdapter bluetoothAdapter) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        java.util.Objects.requireNonNull(bluetoothAdapter);
        this.mBluetoothAdapter = bluetoothAdapter;
    }

    void start() {
        this.mBluetoothAdapter.getProfileProxy(this.mContext, this.mProfileListener, 2);
        this.mBluetoothAdapter.getProfileProxy(this.mContext, this.mProfileListener, 21);
        this.mBluetoothAdapter.getProfileProxy(this.mContext, this.mProfileListener, 22);
    }

    boolean isProfileSupported(int i, @android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice) {
        android.bluetooth.BluetoothProfile bluetoothProfile;
        synchronized (this) {
            try {
                switch (i) {
                    case 2:
                        bluetoothProfile = this.mA2dpProfile;
                        break;
                    case 21:
                        bluetoothProfile = this.mHearingAidProfile;
                        break;
                    case 22:
                        bluetoothProfile = this.mLeAudioProfile;
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException(i + " is not supported as Bluetooth profile");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (bluetoothProfile == null) {
            return false;
        }
        return bluetoothProfile.getConnectedDevices().contains(bluetoothDevice);
    }

    long getGroupId(int i, @android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice) {
        synchronized (this) {
            try {
                switch (i) {
                    case 2:
                        return -1L;
                    case 21:
                        return this.mHearingAidProfile != null ? this.mHearingAidProfile.getHiSyncId(bluetoothDevice) : -1L;
                    case 22:
                        if (this.mLeAudioProfile != null) {
                            r0 = this.mLeAudioProfile.getGroupId(bluetoothDevice);
                        }
                        return r0;
                    default:
                        throw new java.lang.IllegalArgumentException(i + " is not supported as Bluetooth profile");
                }
            } finally {
            }
        }
    }

    private final class ProfileListener implements android.bluetooth.BluetoothProfile.ServiceListener {
        private ProfileListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, android.bluetooth.BluetoothProfile bluetoothProfile) {
            synchronized (com.android.server.media.BluetoothProfileMonitor.this) {
                try {
                    switch (i) {
                        case 2:
                            com.android.server.media.BluetoothProfileMonitor.this.mA2dpProfile = (android.bluetooth.BluetoothA2dp) bluetoothProfile;
                            break;
                        case 21:
                            com.android.server.media.BluetoothProfileMonitor.this.mHearingAidProfile = (android.bluetooth.BluetoothHearingAid) bluetoothProfile;
                            break;
                        case 22:
                            com.android.server.media.BluetoothProfileMonitor.this.mLeAudioProfile = (android.bluetooth.BluetoothLeAudio) bluetoothProfile;
                            break;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            synchronized (com.android.server.media.BluetoothProfileMonitor.this) {
                try {
                    switch (i) {
                        case 2:
                            com.android.server.media.BluetoothProfileMonitor.this.mA2dpProfile = null;
                            break;
                        case 21:
                            com.android.server.media.BluetoothProfileMonitor.this.mHearingAidProfile = null;
                            break;
                        case 22:
                            com.android.server.media.BluetoothProfileMonitor.this.mLeAudioProfile = null;
                            break;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
