package com.android.server.companion.presence;

@android.annotation.SuppressLint({"LongLogTag"})
/* loaded from: classes.dex */
public class BluetoothCompanionDeviceConnectionListener extends android.bluetooth.BluetoothAdapter.BluetoothConnectionCallback implements com.android.server.companion.AssociationStore.OnChangeListener {
    private static final java.lang.String TAG = "CDM_BluetoothCompanionDeviceConnectionListener";

    @android.annotation.NonNull
    private final com.android.server.companion.AssociationStore mAssociationStore;

    @android.annotation.NonNull
    private final com.android.server.companion.presence.BluetoothCompanionDeviceConnectionListener.Callback mCallback;

    @android.annotation.NonNull
    private final com.android.server.companion.presence.ObservableUuidStore mObservableUuidStore;
    private final android.os.UserManager mUserManager;

    @android.annotation.NonNull
    private final java.util.Map<android.net.MacAddress, android.bluetooth.BluetoothDevice> mAllConnectedDevices = new java.util.HashMap();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mPendingConnectedDevices"})
    final android.util.SparseArray<java.util.Set<android.bluetooth.BluetoothDevice>> mPendingConnectedDevices = new android.util.SparseArray<>();

    interface Callback {
        void onBluetoothCompanionDeviceConnected(int i);

        void onBluetoothCompanionDeviceDisconnected(int i);

        void onDevicePresenceEventByUuid(com.android.server.companion.presence.ObservableUuid observableUuid, int i);
    }

    BluetoothCompanionDeviceConnectionListener(android.os.UserManager userManager, @android.annotation.NonNull com.android.server.companion.AssociationStore associationStore, @android.annotation.NonNull com.android.server.companion.presence.ObservableUuidStore observableUuidStore, @android.annotation.NonNull com.android.server.companion.presence.BluetoothCompanionDeviceConnectionListener.Callback callback) {
        this.mAssociationStore = associationStore;
        this.mObservableUuidStore = observableUuidStore;
        this.mCallback = callback;
        this.mUserManager = userManager;
    }

    public void init(@android.annotation.NonNull android.bluetooth.BluetoothAdapter bluetoothAdapter) {
        bluetoothAdapter.registerBluetoothConnectionCallback(new android.os.HandlerExecutor(android.os.Handler.getMain()), this);
        this.mAssociationStore.registerListener(this);
    }

    public void onDeviceConnected(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice) {
        android.net.MacAddress fromString = android.net.MacAddress.fromString(bluetoothDevice.getAddress());
        int myUserId = android.os.UserHandle.myUserId();
        if (this.mAllConnectedDevices.put(fromString, bluetoothDevice) != null) {
            return;
        }
        if (!this.mUserManager.isUserUnlockingOrUnlocked(android.os.UserHandle.myUserId())) {
            android.util.Slog.i(TAG, "Current user is not in unlocking or unlocked stage yet. Notify the application when the phone is unlocked");
            synchronized (this.mPendingConnectedDevices) {
                java.util.Set<android.bluetooth.BluetoothDevice> set = this.mPendingConnectedDevices.get(myUserId, new java.util.HashSet());
                set.add(bluetoothDevice);
                this.mPendingConnectedDevices.put(myUserId, set);
            }
            return;
        }
        onDeviceConnectivityChanged(bluetoothDevice, true);
    }

    public void onDeviceDisconnected(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice, int i) {
        android.net.MacAddress fromString = android.net.MacAddress.fromString(bluetoothDevice.getAddress());
        int myUserId = android.os.UserHandle.myUserId();
        if (this.mAllConnectedDevices.remove(fromString) == null) {
            return;
        }
        if (!this.mUserManager.isUserUnlockingOrUnlocked(myUserId)) {
            synchronized (this.mPendingConnectedDevices) {
                try {
                    java.util.Set<android.bluetooth.BluetoothDevice> set = this.mPendingConnectedDevices.get(myUserId);
                    if (set != null) {
                        set.remove(bluetoothDevice);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return;
        }
        onDeviceConnectivityChanged(bluetoothDevice, false);
    }

    private void onDeviceConnectivityChanged(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice, boolean z) {
        int myUserId = android.os.UserHandle.myUserId();
        java.util.List<android.companion.AssociationInfo> associationsByAddress = this.mAssociationStore.getAssociationsByAddress(bluetoothDevice.getAddress());
        java.util.List<com.android.server.companion.presence.ObservableUuid> observableUuidsForUser = this.mObservableUuidStore.getObservableUuidsForUser(myUserId);
        android.os.ParcelUuid[] uuids = bluetoothDevice.getUuids();
        java.util.List emptyList = com.android.internal.util.ArrayUtils.isEmpty(uuids) ? java.util.Collections.emptyList() : java.util.Arrays.asList(uuids);
        for (android.companion.AssociationInfo associationInfo : associationsByAddress) {
            if (associationInfo.isNotifyOnDeviceNearby()) {
                int id = associationInfo.getId();
                if (z) {
                    this.mCallback.onBluetoothCompanionDeviceConnected(id);
                } else {
                    this.mCallback.onBluetoothCompanionDeviceDisconnected(id);
                }
            }
        }
        for (com.android.server.companion.presence.ObservableUuid observableUuid : observableUuidsForUser) {
            if (emptyList.contains(observableUuid.getUuid())) {
                this.mCallback.onDevicePresenceEventByUuid(observableUuid, z ? 2 : 3);
            }
        }
    }

    @Override // com.android.server.companion.AssociationStore.OnChangeListener
    public void onAssociationAdded(android.companion.AssociationInfo associationInfo) {
        if (this.mAllConnectedDevices.containsKey(associationInfo.getDeviceMacAddress())) {
            this.mCallback.onBluetoothCompanionDeviceConnected(associationInfo.getId());
        }
    }

    @Override // com.android.server.companion.AssociationStore.OnChangeListener
    public void onAssociationRemoved(android.companion.AssociationInfo associationInfo) {
    }

    @Override // com.android.server.companion.AssociationStore.OnChangeListener
    public void onAssociationUpdated(android.companion.AssociationInfo associationInfo, boolean z) {
        if (!z) {
        } else {
            throw new java.lang.IllegalArgumentException("Address changes are not supported.");
        }
    }
}
