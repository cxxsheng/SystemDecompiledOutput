package com.android.server.midi;

/* loaded from: classes2.dex */
public class MidiService extends android.media.midi.IMidiManager.Stub {
    private static final int MAX_CONNECTIONS_PER_CLIENT = 64;
    private static final int MAX_DEVICE_SERVERS_PER_UID = 16;
    private static final int MAX_LISTENERS_PER_CLIENT = 16;
    private static final java.lang.String MIDI_LEGACY_STRING = "MIDI 1.0";
    private static final java.lang.String MIDI_UNIVERSAL_STRING = "MIDI 2.0";
    private static final java.lang.String TAG = "MidiService";
    private int mBluetoothServiceUid;
    private final android.content.Context mContext;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.os.UserManager mUserManager;
    private static final java.util.UUID MIDI_SERVICE = java.util.UUID.fromString("03B80E5A-EDE8-4B33-A751-6CE34EC4C700");
    private static final android.media.midi.MidiDeviceInfo[] EMPTY_DEVICE_INFO_ARRAY = new android.media.midi.MidiDeviceInfo[0];
    private static final java.lang.String[] EMPTY_STRING_ARRAY = new java.lang.String[0];
    private final java.util.HashMap<android.os.IBinder, com.android.server.midi.MidiService.Client> mClients = new java.util.HashMap<>();
    private final java.util.HashMap<android.media.midi.MidiDeviceInfo, com.android.server.midi.MidiService.Device> mDevicesByInfo = new java.util.HashMap<>();
    private final java.util.HashMap<android.bluetooth.BluetoothDevice, com.android.server.midi.MidiService.Device> mBluetoothDevices = new java.util.HashMap<>();
    private final java.util.HashMap<android.bluetooth.BluetoothDevice, android.media.midi.MidiDevice> mBleMidiDeviceMap = new java.util.HashMap<>();
    private final java.util.HashMap<android.os.IBinder, com.android.server.midi.MidiService.Device> mDevicesByServer = new java.util.HashMap<>();
    private int mNextDeviceId = 1;
    private final java.lang.Object mUsbMidiLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mUsbMidiLock"})
    private final java.util.HashMap<java.lang.String, java.lang.Integer> mUsbMidiLegacyDeviceOpenCount = new java.util.HashMap<>();

    @com.android.internal.annotations.GuardedBy({"mUsbMidiLock"})
    private final java.util.HashSet<java.lang.String> mUsbMidiUniversalDeviceInUse = new java.util.HashSet<>();
    private final java.util.HashSet<android.os.ParcelUuid> mNonMidiUUIDs = new java.util.HashSet<>();
    private final com.android.internal.content.PackageMonitor mPackageMonitor = new com.android.internal.content.PackageMonitor() { // from class: com.android.server.midi.MidiService.1
        @android.annotation.RequiresPermission("android.permission.INTERACT_ACROSS_USERS")
        public void onPackageAdded(java.lang.String str, int i) {
            com.android.server.midi.MidiService.this.addPackageDeviceServers(str, getChangingUserId());
        }

        @android.annotation.RequiresPermission("android.permission.INTERACT_ACROSS_USERS")
        public void onPackageModified(java.lang.String str) {
            com.android.server.midi.MidiService.this.removePackageDeviceServers(str, getChangingUserId());
            com.android.server.midi.MidiService.this.addPackageDeviceServers(str, getChangingUserId());
        }

        public void onPackageRemoved(java.lang.String str, int i) {
            com.android.server.midi.MidiService.this.removePackageDeviceServers(str, getChangingUserId());
        }
    };
    private final android.content.BroadcastReceiver mBleMidiReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.midi.MidiService.2
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            char c;
            java.lang.String action = intent.getAction();
            if (action == null) {
                android.util.Log.w(com.android.server.midi.MidiService.TAG, "MidiService, action is null");
            }
            switch (action.hashCode()) {
                case -377527494:
                    if (action.equals("android.bluetooth.device.action.UUID")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -301431627:
                    if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1821585647:
                    if (action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 2116862345:
                    if (action.equals("android.bluetooth.device.action.BOND_STATE_CHANGED")) {
                        c = 2;
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
                    android.util.Log.d(com.android.server.midi.MidiService.TAG, "ACTION_ACL_CONNECTED");
                    com.android.server.midi.MidiService.dumpIntentExtras(intent);
                    if (!com.android.server.midi.MidiService.isBleTransport(intent)) {
                        android.util.Log.i(com.android.server.midi.MidiService.TAG, "No BLE transport - NOT MIDI");
                        break;
                    } else {
                        android.util.Log.d(com.android.server.midi.MidiService.TAG, "BLE Device");
                        android.bluetooth.BluetoothDevice bluetoothDevice = (android.bluetooth.BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", android.bluetooth.BluetoothDevice.class);
                        com.android.server.midi.MidiService.this.dumpUuids(bluetoothDevice);
                        if (com.android.server.midi.MidiService.this.hasNonMidiUuids(bluetoothDevice)) {
                            android.util.Log.d(com.android.server.midi.MidiService.TAG, "Non-MIDI service UUIDs found. NOT MIDI");
                            break;
                        } else {
                            android.util.Log.d(com.android.server.midi.MidiService.TAG, "Potential MIDI Device.");
                            com.android.server.midi.MidiService.this.openBluetoothDevice(bluetoothDevice);
                            break;
                        }
                    }
                case 1:
                    android.util.Log.d(com.android.server.midi.MidiService.TAG, "ACTION_ACL_DISCONNECTED");
                    android.bluetooth.BluetoothDevice bluetoothDevice2 = (android.bluetooth.BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", android.bluetooth.BluetoothDevice.class);
                    if (com.android.server.midi.MidiService.this.isBLEMIDIDevice(bluetoothDevice2)) {
                        com.android.server.midi.MidiService.this.closeBluetoothDevice(bluetoothDevice2);
                        break;
                    }
                    break;
                case 2:
                case 3:
                    android.util.Log.d(com.android.server.midi.MidiService.TAG, "ACTION_UUID");
                    android.bluetooth.BluetoothDevice bluetoothDevice3 = (android.bluetooth.BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", android.bluetooth.BluetoothDevice.class);
                    com.android.server.midi.MidiService.this.dumpUuids(bluetoothDevice3);
                    if (com.android.server.midi.MidiService.this.isBLEMIDIDevice(bluetoothDevice3)) {
                        android.util.Log.d(com.android.server.midi.MidiService.TAG, "BT MIDI DEVICE");
                        com.android.server.midi.MidiService.this.openBluetoothDevice(bluetoothDevice3);
                        break;
                    }
                    break;
            }
        }
    };

    public static class Lifecycle extends com.android.server.SystemService {
        private com.android.server.midi.MidiService mMidiService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mMidiService = new com.android.server.midi.MidiService(getContext());
            publishBinderService("midi", this.mMidiService);
        }

        @Override // com.android.server.SystemService
        @android.annotation.RequiresPermission(allOf = {"android.permission.INTERACT_ACROSS_USERS"}, anyOf = {"android.permission.QUERY_USERS", "android.permission.CREATE_USERS", "android.permission.MANAGE_USERS"})
        public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mMidiService.onStartOrUnlockUser(targetUser, false);
        }

        @Override // com.android.server.SystemService
        @android.annotation.RequiresPermission(allOf = {"android.permission.INTERACT_ACROSS_USERS"}, anyOf = {"android.permission.QUERY_USERS", "android.permission.CREATE_USERS", "android.permission.MANAGE_USERS"})
        public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mMidiService.onStartOrUnlockUser(targetUser, true);
        }
    }

    private final class Client implements android.os.IBinder.DeathRecipient {
        private static final java.lang.String TAG = "MidiService.Client";
        private final android.os.IBinder mToken;
        private final java.util.HashMap<android.os.IBinder, android.media.midi.IMidiDeviceListener> mListeners = new java.util.HashMap<>();
        private final java.util.HashMap<android.os.IBinder, com.android.server.midi.MidiService.DeviceConnection> mDeviceConnections = new java.util.HashMap<>();
        private final int mUid = android.os.Binder.getCallingUid();
        private final int mPid = android.os.Binder.getCallingPid();

        public Client(android.os.IBinder iBinder) {
            this.mToken = iBinder;
        }

        public int getUid() {
            return this.mUid;
        }

        private int getUserId() {
            return android.os.UserHandle.getUserId(this.mUid);
        }

        public void addListener(android.media.midi.IMidiDeviceListener iMidiDeviceListener) {
            if (this.mListeners.size() >= 16) {
                throw new java.lang.SecurityException("too many MIDI listeners for UID = " + this.mUid);
            }
            this.mListeners.put(iMidiDeviceListener.asBinder(), iMidiDeviceListener);
        }

        public void removeListener(android.media.midi.IMidiDeviceListener iMidiDeviceListener) {
            this.mListeners.remove(iMidiDeviceListener.asBinder());
            if (this.mListeners.size() == 0 && this.mDeviceConnections.size() == 0) {
                close();
            }
        }

        @android.annotation.RequiresPermission(anyOf = {"android.permission.INTERACT_ACROSS_USERS_FULL", "android.permission.INTERACT_ACROSS_USERS", "android.permission.INTERACT_ACROSS_PROFILES"})
        public void addDeviceConnection(com.android.server.midi.MidiService.Device device, android.media.midi.IMidiDeviceOpenCallback iMidiDeviceOpenCallback, int i) {
            android.util.Log.d(TAG, "addDeviceConnection() device:" + device + " userId:" + i);
            if (this.mDeviceConnections.size() >= 64) {
                android.util.Log.i(TAG, "too many MIDI connections for UID = " + this.mUid);
                throw new java.lang.SecurityException("too many MIDI connections for UID = " + this.mUid);
            }
            com.android.server.midi.MidiService.DeviceConnection deviceConnection = com.android.server.midi.MidiService.this.new DeviceConnection(device, this, iMidiDeviceOpenCallback);
            this.mDeviceConnections.put(deviceConnection.getToken(), deviceConnection);
            device.addDeviceConnection(deviceConnection, i);
        }

        public void removeDeviceConnection(android.os.IBinder iBinder) {
            com.android.server.midi.MidiService.DeviceConnection remove = this.mDeviceConnections.remove(iBinder);
            if (remove != null) {
                remove.getDevice().removeDeviceConnection(remove);
            }
            if (this.mListeners.size() == 0 && this.mDeviceConnections.size() == 0) {
                close();
            }
        }

        public void removeDeviceConnection(com.android.server.midi.MidiService.DeviceConnection deviceConnection) {
            this.mDeviceConnections.remove(deviceConnection.getToken());
            if (this.mListeners.size() == 0 && this.mDeviceConnections.size() == 0) {
                close();
            }
        }

        public void deviceAdded(com.android.server.midi.MidiService.Device device) {
            if (!device.isUidAllowed(this.mUid) || !device.isUserIdAllowed(getUserId())) {
                return;
            }
            android.media.midi.MidiDeviceInfo deviceInfo = device.getDeviceInfo();
            try {
                java.util.Iterator<android.media.midi.IMidiDeviceListener> it = this.mListeners.values().iterator();
                while (it.hasNext()) {
                    it.next().onDeviceAdded(deviceInfo);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "remote exception", e);
            }
        }

        public void deviceRemoved(com.android.server.midi.MidiService.Device device) {
            if (!device.isUidAllowed(this.mUid) || !device.isUserIdAllowed(getUserId())) {
                return;
            }
            android.media.midi.MidiDeviceInfo deviceInfo = device.getDeviceInfo();
            try {
                java.util.Iterator<android.media.midi.IMidiDeviceListener> it = this.mListeners.values().iterator();
                while (it.hasNext()) {
                    it.next().onDeviceRemoved(deviceInfo);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "remote exception", e);
            }
        }

        public void deviceStatusChanged(com.android.server.midi.MidiService.Device device, android.media.midi.MidiDeviceStatus midiDeviceStatus) {
            if (!device.isUidAllowed(this.mUid) || !device.isUserIdAllowed(getUserId())) {
                return;
            }
            try {
                java.util.Iterator<android.media.midi.IMidiDeviceListener> it = this.mListeners.values().iterator();
                while (it.hasNext()) {
                    it.next().onDeviceStatusChanged(midiDeviceStatus);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "remote exception", e);
            }
        }

        private void close() {
            synchronized (com.android.server.midi.MidiService.this.mClients) {
                com.android.server.midi.MidiService.this.mClients.remove(this.mToken);
                this.mToken.unlinkToDeath(this, 0);
            }
            for (com.android.server.midi.MidiService.DeviceConnection deviceConnection : this.mDeviceConnections.values()) {
                deviceConnection.getDevice().removeDeviceConnection(deviceConnection);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Log.d(TAG, "Client died: " + this);
            close();
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("Client: UID: ");
            sb.append(this.mUid);
            sb.append(" PID: ");
            sb.append(this.mPid);
            sb.append(" listener count: ");
            sb.append(this.mListeners.size());
            sb.append(" Device Connections:");
            for (com.android.server.midi.MidiService.DeviceConnection deviceConnection : this.mDeviceConnections.values()) {
                sb.append(" <device ");
                sb.append(deviceConnection.getDevice().getDeviceInfo().getId());
                sb.append(">");
            }
            return sb.toString();
        }
    }

    private com.android.server.midi.MidiService.Client getClient(android.os.IBinder iBinder) {
        com.android.server.midi.MidiService.Client client;
        synchronized (this.mClients) {
            try {
                client = this.mClients.get(iBinder);
                if (client == null) {
                    client = new com.android.server.midi.MidiService.Client(iBinder);
                    try {
                        iBinder.linkToDeath(client, 0);
                        this.mClients.put(iBinder, client);
                    } catch (android.os.RemoteException e) {
                        return null;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return client;
    }

    private final class Device implements android.os.IBinder.DeathRecipient {
        private static final java.lang.String TAG = "MidiService.Device";
        private final android.bluetooth.BluetoothDevice mBluetoothDevice;
        private final java.util.ArrayList<com.android.server.midi.MidiService.DeviceConnection> mDeviceConnections;
        private java.util.concurrent.atomic.AtomicInteger mDeviceConnectionsAdded;
        private java.util.concurrent.atomic.AtomicInteger mDeviceConnectionsRemoved;
        private android.media.midi.MidiDeviceInfo mDeviceInfo;
        private android.media.midi.MidiDeviceStatus mDeviceStatus;
        private java.time.Instant mPreviousCounterInstant;
        private android.media.midi.IMidiDeviceServer mServer;
        private android.content.ServiceConnection mServiceConnection;
        private final android.content.pm.ServiceInfo mServiceInfo;
        private java.util.concurrent.atomic.AtomicInteger mTotalInputBytes;
        private java.util.concurrent.atomic.AtomicInteger mTotalOutputBytes;
        private java.util.concurrent.atomic.AtomicLong mTotalTimeConnectedNs;
        private final int mUid;
        private final int mUserId;

        public Device(android.media.midi.IMidiDeviceServer iMidiDeviceServer, android.media.midi.MidiDeviceInfo midiDeviceInfo, android.content.pm.ServiceInfo serviceInfo, int i, int i2) {
            this.mDeviceConnections = new java.util.ArrayList<>();
            this.mDeviceConnectionsAdded = new java.util.concurrent.atomic.AtomicInteger();
            this.mDeviceConnectionsRemoved = new java.util.concurrent.atomic.AtomicInteger();
            this.mTotalTimeConnectedNs = new java.util.concurrent.atomic.AtomicLong();
            this.mPreviousCounterInstant = null;
            this.mTotalInputBytes = new java.util.concurrent.atomic.AtomicInteger();
            this.mTotalOutputBytes = new java.util.concurrent.atomic.AtomicInteger();
            this.mDeviceInfo = midiDeviceInfo;
            this.mServiceInfo = serviceInfo;
            this.mUid = i;
            this.mUserId = i2;
            this.mBluetoothDevice = (android.bluetooth.BluetoothDevice) midiDeviceInfo.getProperties().getParcelable("bluetooth_device", android.bluetooth.BluetoothDevice.class);
            setDeviceServer(iMidiDeviceServer);
        }

        public Device(android.bluetooth.BluetoothDevice bluetoothDevice) {
            this.mDeviceConnections = new java.util.ArrayList<>();
            this.mDeviceConnectionsAdded = new java.util.concurrent.atomic.AtomicInteger();
            this.mDeviceConnectionsRemoved = new java.util.concurrent.atomic.AtomicInteger();
            this.mTotalTimeConnectedNs = new java.util.concurrent.atomic.AtomicLong();
            this.mPreviousCounterInstant = null;
            this.mTotalInputBytes = new java.util.concurrent.atomic.AtomicInteger();
            this.mTotalOutputBytes = new java.util.concurrent.atomic.AtomicInteger();
            this.mBluetoothDevice = bluetoothDevice;
            this.mServiceInfo = null;
            this.mUid = com.android.server.midi.MidiService.this.mBluetoothServiceUid;
            this.mUserId = android.os.UserHandle.getUserId(this.mUid);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDeviceServer(android.media.midi.IMidiDeviceServer iMidiDeviceServer) {
            android.util.Log.i(TAG, "setDeviceServer()");
            if (iMidiDeviceServer != null) {
                if (this.mServer != null) {
                    android.util.Log.e(TAG, "mServer already set in setDeviceServer");
                    return;
                }
                android.os.IBinder asBinder = iMidiDeviceServer.asBinder();
                try {
                    asBinder.linkToDeath(this, 0);
                    this.mServer = iMidiDeviceServer;
                    com.android.server.midi.MidiService.this.mDevicesByServer.put(asBinder, this);
                } catch (android.os.RemoteException e) {
                    this.mServer = null;
                    return;
                }
            } else if (this.mServer != null) {
                iMidiDeviceServer = this.mServer;
                this.mServer = null;
                android.os.IBinder asBinder2 = iMidiDeviceServer.asBinder();
                com.android.server.midi.MidiService.this.mDevicesByServer.remove(asBinder2);
                this.mDeviceStatus = null;
                try {
                    iMidiDeviceServer.closeDevice();
                    asBinder2.unlinkToDeath(this, 0);
                } catch (android.os.RemoteException e2) {
                }
            }
            if (this.mDeviceConnections != null) {
                synchronized (this.mDeviceConnections) {
                    try {
                        java.util.Iterator<com.android.server.midi.MidiService.DeviceConnection> it = this.mDeviceConnections.iterator();
                        while (it.hasNext()) {
                            it.next().notifyClient(iMidiDeviceServer);
                        }
                    } finally {
                    }
                }
            }
        }

        public android.media.midi.MidiDeviceInfo getDeviceInfo() {
            return this.mDeviceInfo;
        }

        public void setDeviceInfo(android.media.midi.MidiDeviceInfo midiDeviceInfo) {
            this.mDeviceInfo = midiDeviceInfo;
        }

        public android.media.midi.MidiDeviceStatus getDeviceStatus() {
            return this.mDeviceStatus;
        }

        public void setDeviceStatus(android.media.midi.MidiDeviceStatus midiDeviceStatus) {
            this.mDeviceStatus = midiDeviceStatus;
        }

        public android.media.midi.IMidiDeviceServer getDeviceServer() {
            return this.mServer;
        }

        public android.content.pm.ServiceInfo getServiceInfo() {
            return this.mServiceInfo;
        }

        public java.lang.String getPackageName() {
            if (this.mServiceInfo == null) {
                return null;
            }
            return this.mServiceInfo.packageName;
        }

        public int getUid() {
            return this.mUid;
        }

        public int getUserId() {
            return this.mUserId;
        }

        public boolean isUidAllowed(int i) {
            return !this.mDeviceInfo.isPrivate() || this.mUid == i;
        }

        public boolean isUserIdAllowed(int i) {
            return this.mDeviceInfo.getType() != 2 || this.mUserId == i;
        }

        @android.annotation.RequiresPermission(anyOf = {"android.permission.INTERACT_ACROSS_USERS_FULL", "android.permission.INTERACT_ACROSS_USERS", "android.permission.INTERACT_ACROSS_PROFILES"})
        public void addDeviceConnection(com.android.server.midi.MidiService.DeviceConnection deviceConnection, int i) {
            android.content.Intent intent;
            android.util.Log.d(TAG, "addDeviceConnection() [A] connection:" + deviceConnection);
            synchronized (this.mDeviceConnections) {
                try {
                    this.mDeviceConnectionsAdded.incrementAndGet();
                    if (this.mPreviousCounterInstant == null) {
                        this.mPreviousCounterInstant = java.time.Instant.now();
                    }
                    android.util.Log.d(TAG, "  mServer:" + this.mServer);
                    if (this.mServer != null) {
                        android.util.Log.i(TAG, "++++ A");
                        this.mDeviceConnections.add(deviceConnection);
                        deviceConnection.notifyClient(this.mServer);
                    } else if (this.mServiceConnection == null && (this.mServiceInfo != null || this.mBluetoothDevice != null)) {
                        android.util.Log.i(TAG, "++++ B");
                        this.mDeviceConnections.add(deviceConnection);
                        this.mServiceConnection = new android.content.ServiceConnection() { // from class: com.android.server.midi.MidiService.Device.1
                            @Override // android.content.ServiceConnection
                            public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                                android.media.midi.IMidiDeviceServer asInterface;
                                android.util.Log.i(com.android.server.midi.MidiService.Device.TAG, "++++ onServiceConnected() mBluetoothDevice:" + com.android.server.midi.MidiService.Device.this.mBluetoothDevice);
                                if (com.android.server.midi.MidiService.Device.this.mBluetoothDevice != null) {
                                    android.media.midi.IBluetoothMidiService asInterface2 = android.media.midi.IBluetoothMidiService.Stub.asInterface(iBinder);
                                    android.util.Log.i(com.android.server.midi.MidiService.Device.TAG, "++++ mBluetoothMidiService:" + asInterface2);
                                    if (asInterface2 != null) {
                                        try {
                                            asInterface = android.media.midi.IMidiDeviceServer.Stub.asInterface(asInterface2.addBluetoothDevice(com.android.server.midi.MidiService.Device.this.mBluetoothDevice));
                                        } catch (android.os.RemoteException e) {
                                            android.util.Log.e(com.android.server.midi.MidiService.Device.TAG, "Could not call addBluetoothDevice()", e);
                                        }
                                    }
                                    asInterface = null;
                                } else {
                                    asInterface = android.media.midi.IMidiDeviceServer.Stub.asInterface(iBinder);
                                }
                                com.android.server.midi.MidiService.Device.this.setDeviceServer(asInterface);
                            }

                            @Override // android.content.ServiceConnection
                            public void onServiceDisconnected(android.content.ComponentName componentName) {
                                com.android.server.midi.MidiService.Device.this.setDeviceServer(null);
                                com.android.server.midi.MidiService.Device.this.mServiceConnection = null;
                            }
                        };
                        if (this.mBluetoothDevice != null) {
                            intent = new android.content.Intent("android.media.midi.BluetoothMidiService");
                            intent.setComponent(new android.content.ComponentName("com.android.bluetoothmidiservice", "com.android.bluetoothmidiservice.BluetoothMidiService"));
                        } else if (!com.android.server.midi.MidiService.this.isUmpDevice(this.mDeviceInfo)) {
                            intent = new android.content.Intent("android.media.midi.MidiDeviceService");
                            intent.setComponent(new android.content.ComponentName(this.mServiceInfo.packageName, this.mServiceInfo.name));
                        } else {
                            intent = new android.content.Intent("android.media.midi.MidiUmpDeviceService");
                            intent.setComponent(new android.content.ComponentName(this.mServiceInfo.packageName, this.mServiceInfo.name));
                        }
                        if (!com.android.server.midi.MidiService.this.mContext.bindServiceAsUser(intent, this.mServiceConnection, 1, android.os.UserHandle.of(this.mUserId))) {
                            android.util.Log.e(TAG, "Unable to bind service: " + intent);
                            setDeviceServer(null);
                            this.mServiceConnection = null;
                        }
                    } else {
                        android.util.Log.e(TAG, "No way to connect to device in addDeviceConnection");
                        deviceConnection.notifyClient(null);
                    }
                } finally {
                }
            }
        }

        public void removeDeviceConnection(com.android.server.midi.MidiService.DeviceConnection deviceConnection) {
            synchronized (com.android.server.midi.MidiService.this.mDevicesByInfo) {
                synchronized (this.mDeviceConnections) {
                    try {
                        int incrementAndGet = this.mDeviceConnectionsRemoved.incrementAndGet();
                        if (this.mPreviousCounterInstant != null) {
                            this.mTotalTimeConnectedNs.addAndGet(java.time.Duration.between(this.mPreviousCounterInstant, java.time.Instant.now()).toNanos());
                        }
                        if (incrementAndGet >= this.mDeviceConnectionsAdded.get()) {
                            this.mPreviousCounterInstant = null;
                        } else {
                            this.mPreviousCounterInstant = java.time.Instant.now();
                        }
                        logMetrics(false);
                        this.mDeviceConnections.remove(deviceConnection);
                        if (deviceConnection.getDevice().getDeviceInfo().getType() == 1) {
                            synchronized (com.android.server.midi.MidiService.this.mUsbMidiLock) {
                                com.android.server.midi.MidiService.this.removeUsbMidiDeviceLocked(deviceConnection.getDevice().getDeviceInfo());
                            }
                        }
                        if (this.mDeviceConnections.size() == 0 && this.mServiceConnection != null) {
                            com.android.server.midi.MidiService.this.mContext.unbindService(this.mServiceConnection);
                            this.mServiceConnection = null;
                            if (this.mBluetoothDevice != null) {
                                closeLocked();
                            } else {
                                setDeviceServer(null);
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public void closeLocked() {
            synchronized (this.mDeviceConnections) {
                try {
                    java.util.Iterator<com.android.server.midi.MidiService.DeviceConnection> it = this.mDeviceConnections.iterator();
                    while (it.hasNext()) {
                        com.android.server.midi.MidiService.DeviceConnection next = it.next();
                        if (next.getDevice().getDeviceInfo().getType() == 1) {
                            synchronized (com.android.server.midi.MidiService.this.mUsbMidiLock) {
                                com.android.server.midi.MidiService.this.removeUsbMidiDeviceLocked(next.getDevice().getDeviceInfo());
                            }
                        }
                        next.getClient().removeDeviceConnection(next);
                    }
                    this.mDeviceConnections.clear();
                    if (this.mPreviousCounterInstant != null) {
                        java.time.Instant now = java.time.Instant.now();
                        this.mTotalTimeConnectedNs.addAndGet(java.time.Duration.between(this.mPreviousCounterInstant, now).toNanos());
                        this.mPreviousCounterInstant = now;
                    }
                    logMetrics(true);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            setDeviceServer(null);
            if (this.mServiceInfo == null) {
                com.android.server.midi.MidiService.this.removeDeviceLocked(this);
            } else {
                this.mDeviceStatus = new android.media.midi.MidiDeviceStatus(this.mDeviceInfo);
            }
            if (this.mBluetoothDevice != null) {
                com.android.server.midi.MidiService.this.mBluetoothDevices.remove(this.mBluetoothDevice);
            }
        }

        private void logMetrics(boolean z) {
            int i = this.mDeviceConnectionsAdded.get();
            if (this.mDeviceInfo != null && i > 0) {
                java.lang.String str = "false";
                android.media.MediaMetrics.Item item = new android.media.MediaMetrics.Item("audio.midi").setUid(this.mUid).set(android.media.MediaMetrics.Property.DEVICE_ID, java.lang.Integer.valueOf(this.mDeviceInfo.getId())).set(android.media.MediaMetrics.Property.INPUT_PORT_COUNT, java.lang.Integer.valueOf(this.mDeviceInfo.getInputPortCount())).set(android.media.MediaMetrics.Property.OUTPUT_PORT_COUNT, java.lang.Integer.valueOf(this.mDeviceInfo.getOutputPortCount())).set(android.media.MediaMetrics.Property.HARDWARE_TYPE, java.lang.Integer.valueOf(this.mDeviceInfo.getType())).set(android.media.MediaMetrics.Property.DURATION_NS, java.lang.Long.valueOf(this.mTotalTimeConnectedNs.get())).set(android.media.MediaMetrics.Property.OPENED_COUNT, java.lang.Integer.valueOf(i)).set(android.media.MediaMetrics.Property.CLOSED_COUNT, java.lang.Integer.valueOf(this.mDeviceConnectionsRemoved.get())).set(android.media.MediaMetrics.Property.DEVICE_DISCONNECTED, z ? "true" : "false").set(android.media.MediaMetrics.Property.IS_SHARED, !this.mDeviceInfo.isPrivate() ? "true" : "false").set(android.media.MediaMetrics.Property.SUPPORTS_MIDI_UMP, com.android.server.midi.MidiService.this.isUmpDevice(this.mDeviceInfo) ? "true" : "false");
                android.media.MediaMetrics.Key key = android.media.MediaMetrics.Property.USING_ALSA;
                if (this.mDeviceInfo.getProperties().get("alsa_card") != null) {
                    str = "true";
                }
                item.set(key, str).set(android.media.MediaMetrics.Property.EVENT, "deviceClosed").set(android.media.MediaMetrics.Property.TOTAL_INPUT_BYTES, java.lang.Integer.valueOf(this.mTotalInputBytes.get())).set(android.media.MediaMetrics.Property.TOTAL_OUTPUT_BYTES, java.lang.Integer.valueOf(this.mTotalOutputBytes.get())).record();
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Log.d(TAG, "Device died: " + this);
            synchronized (com.android.server.midi.MidiService.this.mDevicesByInfo) {
                closeLocked();
            }
        }

        public void updateTotalBytes(int i, int i2) {
            this.mTotalInputBytes.set(i);
            this.mTotalOutputBytes.set(i2);
        }

        public java.lang.String toString() {
            return "Device Info: " + this.mDeviceInfo + " Status: " + this.mDeviceStatus + " UID: " + this.mUid + " DeviceConnection count: " + this.mDeviceConnections.size() + " mServiceConnection: " + this.mServiceConnection;
        }
    }

    private final class DeviceConnection {
        private static final java.lang.String TAG = "MidiService.DeviceConnection";
        private android.media.midi.IMidiDeviceOpenCallback mCallback;
        private final com.android.server.midi.MidiService.Client mClient;
        private final com.android.server.midi.MidiService.Device mDevice;
        private final android.os.IBinder mToken = new android.os.Binder();

        public DeviceConnection(com.android.server.midi.MidiService.Device device, com.android.server.midi.MidiService.Client client, android.media.midi.IMidiDeviceOpenCallback iMidiDeviceOpenCallback) {
            this.mDevice = device;
            this.mClient = client;
            this.mCallback = iMidiDeviceOpenCallback;
        }

        public com.android.server.midi.MidiService.Device getDevice() {
            return this.mDevice;
        }

        public com.android.server.midi.MidiService.Client getClient() {
            return this.mClient;
        }

        public android.os.IBinder getToken() {
            return this.mToken;
        }

        public void notifyClient(android.media.midi.IMidiDeviceServer iMidiDeviceServer) {
            android.util.Log.d(TAG, "notifyClient");
            if (this.mCallback != null) {
                try {
                    this.mCallback.onDeviceOpened(iMidiDeviceServer, iMidiDeviceServer == null ? null : this.mToken);
                } catch (android.os.RemoteException e) {
                }
                this.mCallback = null;
            }
        }

        public java.lang.String toString() {
            if (this.mDevice == null || this.mDevice.getDeviceInfo() == null) {
                return "null";
            }
            return "" + this.mDevice.getDeviceInfo().getId();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isBLEMIDIDevice(android.bluetooth.BluetoothDevice bluetoothDevice) {
        android.os.ParcelUuid[] uuids = bluetoothDevice.getUuids();
        if (uuids != null) {
            for (android.os.ParcelUuid parcelUuid : uuids) {
                if (parcelUuid.getUuid().equals(MIDI_SERVICE)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void dumpIntentExtras(android.content.Intent intent) {
        android.util.Log.d(TAG, "Intent: " + intent.getAction());
        android.os.Bundle extras = intent.getExtras();
        if (extras != null) {
            for (java.lang.String str : extras.keySet()) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("  ");
                sb.append(str);
                sb.append(" : ");
                sb.append(extras.get(str) != null ? extras.get(str) : "NULL");
                android.util.Log.d(TAG, sb.toString());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isBleTransport(android.content.Intent intent) {
        android.os.Bundle extras = intent.getExtras();
        return extras != null && extras.getInt("android.bluetooth.device.extra.TRANSPORT", 0) == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpUuids(android.bluetooth.BluetoothDevice bluetoothDevice) {
        android.os.ParcelUuid[] uuids = bluetoothDevice.getUuids();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("dumpUuids(");
        sb.append(bluetoothDevice);
        sb.append(") numParcels:");
        sb.append(uuids != null ? uuids.length : 0);
        android.util.Log.d(TAG, sb.toString());
        if (uuids == null) {
            android.util.Log.d(TAG, "No UUID Parcels");
            return;
        }
        for (android.os.ParcelUuid parcelUuid : uuids) {
            android.util.Log.d(TAG, " uuid:" + parcelUuid.getUuid());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasNonMidiUuids(android.bluetooth.BluetoothDevice bluetoothDevice) {
        android.os.ParcelUuid[] uuids = bluetoothDevice.getUuids();
        if (uuids != null) {
            for (android.os.ParcelUuid parcelUuid : uuids) {
                if (this.mNonMidiUUIDs.contains(parcelUuid)) {
                    return true;
                }
            }
        }
        return false;
    }

    public MidiService(android.content.Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        this.mPackageMonitor.register(this.mContext, (android.os.Looper) null, android.os.UserHandle.ALL, true);
        this.mBluetoothServiceUid = -1;
        this.mNonMidiUUIDs.add(android.bluetooth.BluetoothUuid.A2DP_SINK);
        this.mNonMidiUUIDs.add(android.bluetooth.BluetoothUuid.A2DP_SOURCE);
        this.mNonMidiUUIDs.add(android.bluetooth.BluetoothUuid.ADV_AUDIO_DIST);
        this.mNonMidiUUIDs.add(android.bluetooth.BluetoothUuid.AVRCP_CONTROLLER);
        this.mNonMidiUUIDs.add(android.bluetooth.BluetoothUuid.HFP);
        this.mNonMidiUUIDs.add(android.bluetooth.BluetoothUuid.HSP);
        this.mNonMidiUUIDs.add(android.bluetooth.BluetoothUuid.HID);
        this.mNonMidiUUIDs.add(android.bluetooth.BluetoothUuid.LE_AUDIO);
        this.mNonMidiUUIDs.add(android.bluetooth.BluetoothUuid.HOGP);
        this.mNonMidiUUIDs.add(android.bluetooth.BluetoothUuid.HEARING_AID);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.RequiresPermission(allOf = {"android.permission.INTERACT_ACROSS_USERS"}, anyOf = {"android.permission.QUERY_USERS", "android.permission.CREATE_USERS", "android.permission.MANAGE_USERS"})
    public void onStartOrUnlockUser(com.android.server.SystemService.TargetUser targetUser, boolean z) {
        int i;
        android.content.pm.PackageInfo packageInfo;
        android.util.Log.d(TAG, "onStartOrUnlockUser " + targetUser.getUserIdentifier() + " matchDirectBootUnaware: " + z);
        if (!z) {
            i = 128;
        } else {
            i = 262272;
        }
        java.util.List queryIntentServicesAsUser = this.mPackageManager.queryIntentServicesAsUser(new android.content.Intent("android.media.midi.MidiDeviceService"), i, targetUser.getUserIdentifier());
        if (queryIntentServicesAsUser != null) {
            int size = queryIntentServicesAsUser.size();
            for (int i2 = 0; i2 < size; i2++) {
                android.content.pm.ServiceInfo serviceInfo = ((android.content.pm.ResolveInfo) queryIntentServicesAsUser.get(i2)).serviceInfo;
                if (serviceInfo != null) {
                    addLegacyPackageDeviceServer(serviceInfo, targetUser.getUserIdentifier());
                }
            }
        }
        java.util.List queryIntentServicesAsUser2 = this.mPackageManager.queryIntentServicesAsUser(new android.content.Intent("android.media.midi.MidiUmpDeviceService"), i, targetUser.getUserIdentifier());
        if (queryIntentServicesAsUser2 != null) {
            int size2 = queryIntentServicesAsUser2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                android.content.pm.ServiceInfo serviceInfo2 = ((android.content.pm.ResolveInfo) queryIntentServicesAsUser2.get(i3)).serviceInfo;
                if (serviceInfo2 != null) {
                    addUmpPackageDeviceServer(serviceInfo2, targetUser.getUserIdentifier());
                }
            }
        }
        android.os.UserHandle mainUser = this.mUserManager.getMainUser();
        if (mainUser == null || targetUser.getUserIdentifier() == mainUser.getIdentifier()) {
            try {
                packageInfo = this.mPackageManager.getPackageInfoAsUser("com.android.bluetoothmidiservice", 0, targetUser.getUserIdentifier());
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                packageInfo = null;
            }
            if (packageInfo != null && packageInfo.applicationInfo != null) {
                this.mBluetoothServiceUid = packageInfo.applicationInfo.uid;
            }
        }
    }

    public void registerListener(android.os.IBinder iBinder, android.media.midi.IMidiDeviceListener iMidiDeviceListener) {
        com.android.server.midi.MidiService.Client client = getClient(iBinder);
        if (client == null) {
            return;
        }
        client.addListener(iMidiDeviceListener);
        updateStickyDeviceStatus(client.mUid, iMidiDeviceListener);
    }

    public void unregisterListener(android.os.IBinder iBinder, android.media.midi.IMidiDeviceListener iMidiDeviceListener) {
        com.android.server.midi.MidiService.Client client = getClient(iBinder);
        if (client == null) {
            return;
        }
        client.removeListener(iMidiDeviceListener);
    }

    private void updateStickyDeviceStatus(int i, android.media.midi.IMidiDeviceListener iMidiDeviceListener) {
        int userId = android.os.UserHandle.getUserId(i);
        synchronized (this.mDevicesByInfo) {
            for (com.android.server.midi.MidiService.Device device : this.mDevicesByInfo.values()) {
                if (device.isUidAllowed(i) && device.isUserIdAllowed(userId)) {
                    try {
                        android.media.midi.MidiDeviceStatus deviceStatus = device.getDeviceStatus();
                        if (deviceStatus != null) {
                            iMidiDeviceListener.onDeviceStatusChanged(deviceStatus);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(TAG, "remote exception", e);
                    }
                }
            }
        }
    }

    public android.media.midi.MidiDeviceInfo[] getDevices() {
        return getDevicesForTransport(1);
    }

    public android.media.midi.MidiDeviceInfo[] getDevicesForTransport(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = getCallingUserId();
        synchronized (this.mDevicesByInfo) {
            try {
                for (com.android.server.midi.MidiService.Device device : this.mDevicesByInfo.values()) {
                    if (device.isUidAllowed(callingUid) && device.isUserIdAllowed(callingUserId)) {
                        if (i == 2) {
                            if (isUmpDevice(device.getDeviceInfo())) {
                                arrayList.add(device.getDeviceInfo());
                            }
                        } else if (i == 1 && !isUmpDevice(device.getDeviceInfo())) {
                            arrayList.add(device.getDeviceInfo());
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return (android.media.midi.MidiDeviceInfo[]) arrayList.toArray(EMPTY_DEVICE_INFO_ARRAY);
    }

    public void openDevice(android.os.IBinder iBinder, android.media.midi.MidiDeviceInfo midiDeviceInfo, android.media.midi.IMidiDeviceOpenCallback iMidiDeviceOpenCallback) {
        com.android.server.midi.MidiService.Device device;
        com.android.server.midi.MidiService.Client client = getClient(iBinder);
        android.util.Log.d(TAG, "openDevice() client:" + client);
        if (client == null) {
            return;
        }
        synchronized (this.mDevicesByInfo) {
            device = this.mDevicesByInfo.get(midiDeviceInfo);
            android.util.Log.d(TAG, "  device:" + device);
            if (device == null) {
                throw new java.lang.IllegalArgumentException("device does not exist: " + midiDeviceInfo);
            }
            if (!device.isUidAllowed(android.os.Binder.getCallingUid())) {
                throw new java.lang.SecurityException("Attempt to open private device with wrong UID");
            }
            if (!device.isUserIdAllowed(getCallingUserId())) {
                throw new java.lang.SecurityException("Attempt to open virtual device with wrong user id");
            }
        }
        if (midiDeviceInfo.getType() == 1) {
            synchronized (this.mUsbMidiLock) {
                try {
                    if (isUsbMidiDeviceInUseLocked(midiDeviceInfo)) {
                        throw new java.lang.IllegalArgumentException("device already in use: " + midiDeviceInfo);
                    }
                    addUsbMidiDeviceLocked(midiDeviceInfo);
                } finally {
                }
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.util.Log.i(TAG, "addDeviceConnection() [B] device:" + device);
            client.addDeviceConnection(device, iMidiDeviceOpenCallback, getCallingUserId());
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openBluetoothDevice(final android.bluetooth.BluetoothDevice bluetoothDevice) {
        android.util.Log.d(TAG, "openBluetoothDevice() device: " + bluetoothDevice);
        ((android.media.midi.MidiManager) this.mContext.getSystemService(android.media.midi.MidiManager.class)).openBluetoothDevice(bluetoothDevice, new android.media.midi.MidiManager.OnDeviceOpenedListener() { // from class: com.android.server.midi.MidiService.3
            @Override // android.media.midi.MidiManager.OnDeviceOpenedListener
            public void onDeviceOpened(android.media.midi.MidiDevice midiDevice) {
                synchronized (com.android.server.midi.MidiService.this.mBleMidiDeviceMap) {
                    android.util.Log.i(com.android.server.midi.MidiService.TAG, "onDeviceOpened() device:" + midiDevice);
                    com.android.server.midi.MidiService.this.mBleMidiDeviceMap.put(bluetoothDevice, midiDevice);
                }
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeBluetoothDevice(android.bluetooth.BluetoothDevice bluetoothDevice) {
        android.media.midi.MidiDevice remove;
        android.util.Log.d(TAG, "closeBluetoothDevice() device: " + bluetoothDevice);
        synchronized (this.mBleMidiDeviceMap) {
            remove = this.mBleMidiDeviceMap.remove(bluetoothDevice);
        }
        if (remove != null) {
            try {
                remove.close();
            } catch (java.io.IOException e) {
                android.util.Log.e(TAG, "Exception closing BLE-MIDI device" + e);
            }
        }
    }

    public void openBluetoothDevice(android.os.IBinder iBinder, android.bluetooth.BluetoothDevice bluetoothDevice, android.media.midi.IMidiDeviceOpenCallback iMidiDeviceOpenCallback) {
        com.android.server.midi.MidiService.Device device;
        android.util.Log.d(TAG, "openBluetoothDevice()");
        com.android.server.midi.MidiService.Client client = getClient(iBinder);
        if (client == null) {
            return;
        }
        android.util.Log.i(TAG, "alloc device...");
        synchronized (this.mDevicesByInfo) {
            try {
                device = this.mBluetoothDevices.get(bluetoothDevice);
                if (device == null) {
                    device = new com.android.server.midi.MidiService.Device(bluetoothDevice);
                    this.mBluetoothDevices.put(bluetoothDevice, device);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.util.Log.i(TAG, "device: " + device);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.util.Log.i(TAG, "addDeviceConnection() [C] device:" + device);
            client.addDeviceConnection(device, iMidiDeviceOpenCallback, getCallingUserId());
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void closeDevice(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        com.android.server.midi.MidiService.Client client = getClient(iBinder);
        if (client == null) {
            return;
        }
        client.removeDeviceConnection(iBinder2);
    }

    public android.media.midi.MidiDeviceInfo registerDeviceServer(android.media.midi.IMidiDeviceServer iMidiDeviceServer, int i, int i2, java.lang.String[] strArr, java.lang.String[] strArr2, android.os.Bundle bundle, int i3, int i4) {
        android.media.midi.MidiDeviceInfo addDeviceLocked;
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = getCallingUserId();
        if (i3 == 1 && callingUid != 1000) {
            throw new java.lang.SecurityException("only system can create USB devices");
        }
        if (i3 == 3 && callingUid != this.mBluetoothServiceUid) {
            throw new java.lang.SecurityException("only MidiBluetoothService can create Bluetooth devices");
        }
        synchronized (this.mDevicesByInfo) {
            addDeviceLocked = addDeviceLocked(i3, i, i2, strArr, strArr2, bundle, iMidiDeviceServer, null, false, callingUid, i4, callingUserId);
        }
        return addDeviceLocked;
    }

    public void unregisterDeviceServer(android.media.midi.IMidiDeviceServer iMidiDeviceServer) {
        synchronized (this.mDevicesByInfo) {
            try {
                com.android.server.midi.MidiService.Device device = this.mDevicesByServer.get(iMidiDeviceServer.asBinder());
                if (device != null) {
                    device.closeLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.media.midi.MidiDeviceInfo getServiceDeviceInfo(java.lang.String str, java.lang.String str2) {
        int callingUid = android.os.Binder.getCallingUid();
        synchronized (this.mDevicesByInfo) {
            try {
                for (com.android.server.midi.MidiService.Device device : this.mDevicesByInfo.values()) {
                    android.content.pm.ServiceInfo serviceInfo = device.getServiceInfo();
                    if (serviceInfo != null && str.equals(serviceInfo.packageName) && str2.equals(serviceInfo.name)) {
                        if (device.isUidAllowed(callingUid)) {
                            return device.getDeviceInfo();
                        }
                        android.util.EventLog.writeEvent(1397638484, "185796676", -1, "");
                        return null;
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.media.midi.MidiDeviceStatus getDeviceStatus(android.media.midi.MidiDeviceInfo midiDeviceInfo) {
        com.android.server.midi.MidiService.Device device = this.mDevicesByInfo.get(midiDeviceInfo);
        if (device == null) {
            throw new java.lang.IllegalArgumentException("no such device for " + midiDeviceInfo);
        }
        int callingUid = android.os.Binder.getCallingUid();
        if (device.isUidAllowed(callingUid)) {
            return device.getDeviceStatus();
        }
        android.util.Log.e(TAG, "getDeviceStatus() invalid UID = " + callingUid);
        android.util.EventLog.writeEvent(1397638484, "203549963", java.lang.Integer.valueOf(callingUid), "getDeviceStatus: invalid uid");
        return null;
    }

    public void setDeviceStatus(android.media.midi.IMidiDeviceServer iMidiDeviceServer, android.media.midi.MidiDeviceStatus midiDeviceStatus) {
        com.android.server.midi.MidiService.Device device = this.mDevicesByServer.get(iMidiDeviceServer.asBinder());
        if (device != null) {
            if (android.os.Binder.getCallingUid() != device.getUid()) {
                throw new java.lang.SecurityException("setDeviceStatus() caller UID " + android.os.Binder.getCallingUid() + " does not match device's UID " + device.getUid());
            }
            device.setDeviceStatus(midiDeviceStatus);
            notifyDeviceStatusChanged(device, midiDeviceStatus);
        }
    }

    private void notifyDeviceStatusChanged(com.android.server.midi.MidiService.Device device, android.media.midi.MidiDeviceStatus midiDeviceStatus) {
        synchronized (this.mClients) {
            try {
                java.util.Iterator<com.android.server.midi.MidiService.Client> it = this.mClients.values().iterator();
                while (it.hasNext()) {
                    it.next().deviceStatusChanged(device, midiDeviceStatus);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private android.media.midi.MidiDeviceInfo addDeviceLocked(int i, int i2, int i3, java.lang.String[] strArr, java.lang.String[] strArr2, android.os.Bundle bundle, android.media.midi.IMidiDeviceServer iMidiDeviceServer, android.content.pm.ServiceInfo serviceInfo, boolean z, int i4, int i5, int i6) {
        android.bluetooth.BluetoothDevice bluetoothDevice;
        android.util.Log.d(TAG, "addDeviceLocked()" + i4 + " type:" + i);
        java.util.Iterator<com.android.server.midi.MidiService.Device> it = this.mDevicesByInfo.values().iterator();
        int i7 = 0;
        while (it.hasNext()) {
            if (it.next().getUid() == i4) {
                i7++;
            }
        }
        if (i7 >= 16) {
            throw new java.lang.SecurityException("too many MIDI devices already created for UID = " + i4);
        }
        int i8 = this.mNextDeviceId;
        this.mNextDeviceId = i8 + 1;
        android.media.midi.MidiDeviceInfo midiDeviceInfo = new android.media.midi.MidiDeviceInfo(i, i8, i2, i3, strArr, strArr2, bundle, z, i5);
        com.android.server.midi.MidiService.Device device = null;
        if (iMidiDeviceServer != null) {
            try {
                iMidiDeviceServer.setDeviceInfo(midiDeviceInfo);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException in setDeviceInfo()");
                return null;
            }
        }
        if (i != 3) {
            bluetoothDevice = null;
        } else {
            android.bluetooth.BluetoothDevice bluetoothDevice2 = (android.bluetooth.BluetoothDevice) bundle.getParcelable("bluetooth_device", android.bluetooth.BluetoothDevice.class);
            com.android.server.midi.MidiService.Device device2 = this.mBluetoothDevices.get(bluetoothDevice2);
            if (device2 != null) {
                device2.setDeviceInfo(midiDeviceInfo);
            }
            bluetoothDevice = bluetoothDevice2;
            device = device2;
        }
        if (device == null) {
            device = new com.android.server.midi.MidiService.Device(iMidiDeviceServer, midiDeviceInfo, serviceInfo, i4, i6);
        }
        this.mDevicesByInfo.put(midiDeviceInfo, device);
        if (bluetoothDevice != null) {
            this.mBluetoothDevices.put(bluetoothDevice, device);
        }
        synchronized (this.mClients) {
            try {
                java.util.Iterator<com.android.server.midi.MidiService.Client> it2 = this.mClients.values().iterator();
                while (it2.hasNext()) {
                    it2.next().deviceAdded(device);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return midiDeviceInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeDeviceLocked(com.android.server.midi.MidiService.Device device) {
        android.media.midi.IMidiDeviceServer deviceServer = device.getDeviceServer();
        if (deviceServer != null) {
            this.mDevicesByServer.remove(deviceServer.asBinder());
        }
        this.mDevicesByInfo.remove(device.getDeviceInfo());
        synchronized (this.mClients) {
            try {
                java.util.Iterator<com.android.server.midi.MidiService.Client> it = this.mClients.values().iterator();
                while (it.hasNext()) {
                    it.next().deviceRemoved(device);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.RequiresPermission("android.permission.INTERACT_ACROSS_USERS")
    public void addPackageDeviceServers(java.lang.String str, int i) {
        try {
            android.content.pm.ServiceInfo[] serviceInfoArr = this.mPackageManager.getPackageInfoAsUser(str, 262276, i).services;
            if (serviceInfoArr == null) {
                return;
            }
            for (int i2 = 0; i2 < serviceInfoArr.length; i2++) {
                addLegacyPackageDeviceServer(serviceInfoArr[i2], i);
                addUmpPackageDeviceServer(serviceInfoArr[i2], i);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(TAG, "handlePackageUpdate could not find package " + str, e);
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:102:? -> B:98:0x0219). Please report as a decompilation issue!!! */
    private void addLegacyPackageDeviceServer(android.content.pm.ServiceInfo serviceInfo, int i) {
        android.content.res.XmlResourceParser xmlResourceParser;
        int i2;
        java.util.ArrayList arrayList;
        java.util.ArrayList arrayList2;
        int i3;
        java.util.ArrayList arrayList3;
        java.util.ArrayList arrayList4;
        android.content.res.XmlResourceParser xmlResourceParser2;
        java.util.HashMap<android.media.midi.MidiDeviceInfo, com.android.server.midi.MidiService.Device> hashMap;
        java.lang.String str;
        java.lang.String str2;
        android.content.res.XmlResourceParser xmlResourceParser3 = null;
        try {
            try {
                if (serviceInfo == null) {
                    android.util.Log.w(TAG, "Skipping null service info");
                    return;
                }
                if (!"android.permission.BIND_MIDI_DEVICE_SERVICE".equals(serviceInfo.permission)) {
                    return;
                }
                android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(this.mPackageManager, "android.media.midi.MidiDeviceService");
                try {
                    if (loadXmlMetaData == null) {
                        android.util.Log.w(TAG, "loading xml metadata failed");
                        if (loadXmlMetaData != null) {
                            loadXmlMetaData.close();
                            return;
                        }
                        return;
                    }
                    try {
                        java.util.ArrayList arrayList5 = new java.util.ArrayList();
                        java.util.ArrayList arrayList6 = new java.util.ArrayList();
                        int i4 = 0;
                        int i5 = 0;
                        int i6 = 0;
                        boolean z = false;
                        android.os.Bundle bundle = null;
                        while (true) {
                            int next = loadXmlMetaData.next();
                            if (next == 1) {
                                loadXmlMetaData.close();
                                return;
                            }
                            if (next == 2) {
                                java.lang.String name = loadXmlMetaData.getName();
                                if ("device".equals(name)) {
                                    if (bundle != null) {
                                        android.util.Log.w(TAG, "nested <device> elements in metadata for " + serviceInfo.packageName);
                                        i3 = i4;
                                        arrayList3 = arrayList6;
                                        arrayList4 = arrayList5;
                                        xmlResourceParser2 = loadXmlMetaData;
                                        i4 = i3;
                                        loadXmlMetaData = xmlResourceParser2;
                                        arrayList6 = arrayList3;
                                        arrayList5 = arrayList4;
                                    } else {
                                        bundle = new android.os.Bundle();
                                        bundle.putParcelable("service_info", serviceInfo);
                                        int attributeCount = loadXmlMetaData.getAttributeCount();
                                        int i7 = i4;
                                        z = i7;
                                        while (i7 < attributeCount) {
                                            java.lang.String attributeName = loadXmlMetaData.getAttributeName(i7);
                                            java.lang.String attributeValue = loadXmlMetaData.getAttributeValue(i7);
                                            if ("private".equals(attributeName)) {
                                                z = "true".equals(attributeValue);
                                            } else {
                                                bundle.putString(attributeName, attributeValue);
                                            }
                                            i7++;
                                        }
                                        i5 = i4;
                                        i6 = i5;
                                    }
                                } else if ("input-port".equals(name)) {
                                    if (bundle == null) {
                                        android.util.Log.w(TAG, "<input-port> outside of <device> in metadata for " + serviceInfo.packageName);
                                        i3 = i4;
                                        arrayList3 = arrayList6;
                                        arrayList4 = arrayList5;
                                        xmlResourceParser2 = loadXmlMetaData;
                                        i4 = i3;
                                        loadXmlMetaData = xmlResourceParser2;
                                        arrayList6 = arrayList3;
                                        arrayList5 = arrayList4;
                                    } else {
                                        i5++;
                                        int attributeCount2 = loadXmlMetaData.getAttributeCount();
                                        int i8 = i4;
                                        while (true) {
                                            if (i8 >= attributeCount2) {
                                                str2 = null;
                                                break;
                                            }
                                            java.lang.String attributeName2 = loadXmlMetaData.getAttributeName(i8);
                                            str2 = loadXmlMetaData.getAttributeValue(i8);
                                            if ("name".equals(attributeName2)) {
                                                break;
                                            } else {
                                                i8++;
                                            }
                                        }
                                        arrayList5.add(str2);
                                    }
                                } else if ("output-port".equals(name)) {
                                    if (bundle == null) {
                                        android.util.Log.w(TAG, "<output-port> outside of <device> in metadata for " + serviceInfo.packageName);
                                        i3 = i4;
                                        arrayList3 = arrayList6;
                                        arrayList4 = arrayList5;
                                        xmlResourceParser2 = loadXmlMetaData;
                                        i4 = i3;
                                        loadXmlMetaData = xmlResourceParser2;
                                        arrayList6 = arrayList3;
                                        arrayList5 = arrayList4;
                                    } else {
                                        i6++;
                                        int attributeCount3 = loadXmlMetaData.getAttributeCount();
                                        int i9 = i4;
                                        while (true) {
                                            if (i9 >= attributeCount3) {
                                                str = null;
                                                break;
                                            }
                                            java.lang.String attributeName3 = loadXmlMetaData.getAttributeName(i9);
                                            str = loadXmlMetaData.getAttributeValue(i9);
                                            if ("name".equals(attributeName3)) {
                                                break;
                                            } else {
                                                i9++;
                                            }
                                        }
                                        arrayList6.add(str);
                                    }
                                }
                                i2 = i4;
                                arrayList = arrayList6;
                                arrayList2 = arrayList5;
                                xmlResourceParser = loadXmlMetaData;
                            } else if (next != 3) {
                                i2 = i4;
                                arrayList = arrayList6;
                                arrayList2 = arrayList5;
                                xmlResourceParser = loadXmlMetaData;
                            } else if (!"device".equals(loadXmlMetaData.getName())) {
                                i2 = i4;
                                arrayList = arrayList6;
                                arrayList2 = arrayList5;
                                xmlResourceParser = loadXmlMetaData;
                            } else if (bundle != null) {
                                if (i5 == 0 && i6 == 0) {
                                    android.util.Log.w(TAG, "<device> with no ports in metadata for " + serviceInfo.packageName);
                                    i3 = i4;
                                    arrayList3 = arrayList6;
                                    arrayList4 = arrayList5;
                                    xmlResourceParser2 = loadXmlMetaData;
                                } else {
                                    try {
                                        int i10 = this.mPackageManager.getApplicationInfoAsUser(serviceInfo.packageName, i4, i).uid;
                                        java.util.HashMap<android.media.midi.MidiDeviceInfo, com.android.server.midi.MidiService.Device> hashMap2 = this.mDevicesByInfo;
                                        synchronized (hashMap2) {
                                            try {
                                                hashMap = hashMap2;
                                                i2 = i4;
                                                arrayList = arrayList6;
                                                arrayList2 = arrayList5;
                                                xmlResourceParser = loadXmlMetaData;
                                            } catch (java.lang.Throwable th) {
                                                th = th;
                                                hashMap = hashMap2;
                                                xmlResourceParser = loadXmlMetaData;
                                                throw th;
                                            }
                                            try {
                                                addDeviceLocked(2, i5, i6, (java.lang.String[]) arrayList5.toArray(EMPTY_STRING_ARRAY), (java.lang.String[]) arrayList6.toArray(EMPTY_STRING_ARRAY), bundle, null, serviceInfo, z, i10, -1, i);
                                                try {
                                                    arrayList2.clear();
                                                    arrayList.clear();
                                                    bundle = null;
                                                } catch (java.lang.Exception e) {
                                                    e = e;
                                                    xmlResourceParser3 = xmlResourceParser;
                                                    android.util.Log.w(TAG, "Unable to load component info " + serviceInfo.toString(), e);
                                                    if (xmlResourceParser3 != null) {
                                                        xmlResourceParser3.close();
                                                        return;
                                                    }
                                                    return;
                                                } catch (java.lang.Throwable th2) {
                                                    th = th2;
                                                    xmlResourceParser3 = xmlResourceParser;
                                                    if (xmlResourceParser3 != null) {
                                                        xmlResourceParser3.close();
                                                    }
                                                    throw th;
                                                }
                                            } catch (java.lang.Throwable th3) {
                                                th = th3;
                                                throw th;
                                            }
                                        }
                                    } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                                        i3 = i4;
                                        arrayList3 = arrayList6;
                                        arrayList4 = arrayList5;
                                        xmlResourceParser2 = loadXmlMetaData;
                                        android.util.Log.e(TAG, "could not fetch ApplicationInfo for " + serviceInfo.packageName);
                                    }
                                }
                                i4 = i3;
                                loadXmlMetaData = xmlResourceParser2;
                                arrayList6 = arrayList3;
                                arrayList5 = arrayList4;
                            } else {
                                i2 = i4;
                                arrayList = arrayList6;
                                arrayList2 = arrayList5;
                                xmlResourceParser = loadXmlMetaData;
                            }
                            i4 = i2;
                            loadXmlMetaData = xmlResourceParser;
                            arrayList6 = arrayList;
                            arrayList5 = arrayList2;
                        }
                    } catch (java.lang.Exception e3) {
                        e = e3;
                        xmlResourceParser = loadXmlMetaData;
                    } catch (java.lang.Throwable th4) {
                        th = th4;
                        xmlResourceParser = loadXmlMetaData;
                    }
                } catch (java.lang.Exception e4) {
                    e = e4;
                    xmlResourceParser3 = loadXmlMetaData;
                } catch (java.lang.Throwable th5) {
                    th = th5;
                    xmlResourceParser3 = loadXmlMetaData;
                }
            } catch (java.lang.Exception e5) {
                e = e5;
            }
        } catch (java.lang.Throwable th6) {
            th = th6;
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:107:? -> B:103:0x0234). Please report as a decompilation issue!!! */
    @android.annotation.RequiresPermission("android.permission.INTERACT_ACROSS_USERS")
    private void addUmpPackageDeviceServer(android.content.pm.ServiceInfo serviceInfo, int i) {
        android.content.res.XmlResourceParser xmlResourceParser;
        int i2;
        java.util.ArrayList arrayList;
        int i3;
        java.util.ArrayList arrayList2;
        android.content.res.XmlResourceParser xmlResourceParser2;
        java.util.HashMap<android.media.midi.MidiDeviceInfo, com.android.server.midi.MidiService.Device> hashMap;
        java.lang.String str;
        android.content.res.XmlResourceParser xmlResourceParser3 = null;
        try {
            try {
                if (serviceInfo == null) {
                    android.util.Log.w(TAG, "Skipping null service info");
                    return;
                }
                if (!"android.permission.BIND_MIDI_DEVICE_SERVICE".equals(serviceInfo.permission)) {
                    return;
                }
                if (!android.media.midi.Flags.virtualUmp()) {
                    android.util.Log.w(TAG, "Skipping MIDI device service " + serviceInfo.packageName + ": virtual UMP flag not enabled");
                    return;
                }
                android.content.pm.PackageManager.Property property = this.mPackageManager.getProperty("android.media.midi.MidiUmpDeviceService", new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name));
                if (property == null) {
                    android.util.Log.w(TAG, "Getting MidiUmpDeviceService property failed");
                    return;
                }
                int resourceId = property.getResourceId();
                if (resourceId == 0) {
                    android.util.Log.w(TAG, "Getting MidiUmpDeviceService resourceId failed");
                    return;
                }
                android.content.res.Resources resourcesForApplication = this.mPackageManager.getResourcesForApplication(serviceInfo.packageName);
                if (resourcesForApplication == null) {
                    android.util.Log.w(TAG, "Getting resource failed " + serviceInfo.packageName);
                    return;
                }
                android.content.res.XmlResourceParser xml = resourcesForApplication.getXml(resourceId);
                try {
                    if (xml == null) {
                        android.util.Log.w(TAG, "Getting XML failed " + resourceId);
                        if (xml != null) {
                            xml.close();
                            return;
                        }
                        return;
                    }
                    try {
                        try {
                            java.util.ArrayList arrayList3 = new java.util.ArrayList();
                            int i4 = 0;
                            int i5 = 0;
                            boolean z = false;
                            android.os.Bundle bundle = null;
                            while (true) {
                                int next = xml.next();
                                if (next == 1) {
                                    xml.close();
                                    return;
                                }
                                if (next == 2) {
                                    java.lang.String name = xml.getName();
                                    if ("device".equals(name)) {
                                        if (bundle != null) {
                                            android.util.Log.w(TAG, "nested <device> elements in metadata for " + serviceInfo.packageName);
                                            i3 = i4;
                                            arrayList2 = arrayList3;
                                            xmlResourceParser2 = xml;
                                            xml = xmlResourceParser2;
                                            i4 = i3;
                                            arrayList3 = arrayList2;
                                        } else {
                                            bundle = new android.os.Bundle();
                                            bundle.putParcelable("service_info", serviceInfo);
                                            int attributeCount = xml.getAttributeCount();
                                            int i6 = i4;
                                            z = i6;
                                            while (i6 < attributeCount) {
                                                java.lang.String attributeName = xml.getAttributeName(i6);
                                                java.lang.String attributeValue = xml.getAttributeValue(i6);
                                                if ("private".equals(attributeName)) {
                                                    z = "true".equals(attributeValue);
                                                } else {
                                                    bundle.putString(attributeName, attributeValue);
                                                }
                                                i6++;
                                            }
                                            i5 = i4;
                                        }
                                    } else if ("port".equals(name)) {
                                        if (bundle == null) {
                                            android.util.Log.w(TAG, "<port> outside of <device> in metadata for " + serviceInfo.packageName);
                                            i3 = i4;
                                            arrayList2 = arrayList3;
                                            xmlResourceParser2 = xml;
                                            xml = xmlResourceParser2;
                                            i4 = i3;
                                            arrayList3 = arrayList2;
                                        } else {
                                            i5++;
                                            int attributeCount2 = xml.getAttributeCount();
                                            int i7 = i4;
                                            while (true) {
                                                if (i7 >= attributeCount2) {
                                                    str = null;
                                                    break;
                                                }
                                                java.lang.String attributeName2 = xml.getAttributeName(i7);
                                                str = xml.getAttributeValue(i7);
                                                if ("name".equals(attributeName2)) {
                                                    break;
                                                } else {
                                                    i7++;
                                                }
                                            }
                                            arrayList3.add(str);
                                        }
                                    }
                                    i2 = i4;
                                    arrayList = arrayList3;
                                    xmlResourceParser = xml;
                                } else if (next != 3) {
                                    i2 = i4;
                                    arrayList = arrayList3;
                                    xmlResourceParser = xml;
                                } else if (!"device".equals(xml.getName())) {
                                    i2 = i4;
                                    arrayList = arrayList3;
                                    xmlResourceParser = xml;
                                } else if (bundle != null) {
                                    if (i5 == 0) {
                                        android.util.Log.w(TAG, "<device> with no ports in metadata for " + serviceInfo.packageName);
                                        i3 = i4;
                                        arrayList2 = arrayList3;
                                        xmlResourceParser2 = xml;
                                    } else {
                                        try {
                                            int i8 = this.mPackageManager.getApplicationInfoAsUser(serviceInfo.packageName, i4, i).uid;
                                            java.util.HashMap<android.media.midi.MidiDeviceInfo, com.android.server.midi.MidiService.Device> hashMap2 = this.mDevicesByInfo;
                                            synchronized (hashMap2) {
                                                try {
                                                    hashMap = hashMap2;
                                                    i2 = i4;
                                                    arrayList = arrayList3;
                                                    xmlResourceParser = xml;
                                                } catch (java.lang.Throwable th) {
                                                    th = th;
                                                    hashMap = hashMap2;
                                                    xmlResourceParser = xml;
                                                    throw th;
                                                }
                                                try {
                                                    addDeviceLocked(2, i5, i5, (java.lang.String[]) arrayList3.toArray(EMPTY_STRING_ARRAY), (java.lang.String[]) arrayList3.toArray(EMPTY_STRING_ARRAY), bundle, null, serviceInfo, z, i8, 17, i);
                                                    try {
                                                        arrayList.clear();
                                                        bundle = null;
                                                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                                        xmlResourceParser3 = xmlResourceParser;
                                                        if (xmlResourceParser3 == null) {
                                                            return;
                                                        }
                                                        xmlResourceParser3.close();
                                                        return;
                                                    } catch (java.lang.Exception e2) {
                                                        e = e2;
                                                        xmlResourceParser3 = xmlResourceParser;
                                                        android.util.Log.w(TAG, "Unable to load component info " + serviceInfo.toString(), e);
                                                        if (xmlResourceParser3 == null) {
                                                            return;
                                                        }
                                                        xmlResourceParser3.close();
                                                        return;
                                                    } catch (java.lang.Throwable th2) {
                                                        th = th2;
                                                        xmlResourceParser3 = xmlResourceParser;
                                                        if (xmlResourceParser3 != null) {
                                                            xmlResourceParser3.close();
                                                        }
                                                        throw th;
                                                    }
                                                } catch (java.lang.Throwable th3) {
                                                    th = th3;
                                                    throw th;
                                                }
                                            }
                                        } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
                                            i3 = i4;
                                            arrayList2 = arrayList3;
                                            xmlResourceParser2 = xml;
                                            android.util.Log.e(TAG, "could not fetch ApplicationInfo for " + serviceInfo.packageName);
                                        }
                                    }
                                    xml = xmlResourceParser2;
                                    i4 = i3;
                                    arrayList3 = arrayList2;
                                } else {
                                    i2 = i4;
                                    arrayList = arrayList3;
                                    xmlResourceParser = xml;
                                }
                                xml = xmlResourceParser;
                                i4 = i2;
                                arrayList3 = arrayList;
                            }
                        } catch (java.lang.Exception e4) {
                            e = e4;
                            xmlResourceParser = xml;
                        } catch (java.lang.Throwable th4) {
                            th = th4;
                            xmlResourceParser = xml;
                        }
                    } catch (android.content.pm.PackageManager.NameNotFoundException e5) {
                        xmlResourceParser = xml;
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e6) {
                    xmlResourceParser3 = xml;
                } catch (java.lang.Exception e7) {
                    e = e7;
                    xmlResourceParser3 = xml;
                } catch (java.lang.Throwable th5) {
                    th = th5;
                    xmlResourceParser3 = xml;
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e8) {
            } catch (java.lang.Exception e9) {
                e = e9;
            }
        } catch (java.lang.Throwable th6) {
            th = th6;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removePackageDeviceServers(java.lang.String str, int i) {
        synchronized (this.mDevicesByInfo) {
            try {
                java.util.Iterator<com.android.server.midi.MidiService.Device> it = this.mDevicesByInfo.values().iterator();
                while (it.hasNext()) {
                    com.android.server.midi.MidiService.Device next = it.next();
                    if (str.equals(next.getPackageName()) && next.getUserId() == i) {
                        it.remove();
                        removeDeviceLocked(next);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void updateTotalBytes(android.media.midi.IMidiDeviceServer iMidiDeviceServer, int i, int i2) {
        synchronized (this.mDevicesByInfo) {
            try {
                com.android.server.midi.MidiService.Device device = this.mDevicesByServer.get(iMidiDeviceServer.asBinder());
                if (device != null) {
                    device.updateTotalBytes(i, i2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
            indentingPrintWriter.println("MIDI Manager State:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("Devices:");
            indentingPrintWriter.increaseIndent();
            synchronized (this.mDevicesByInfo) {
                try {
                    java.util.Iterator<com.android.server.midi.MidiService.Device> it = this.mDevicesByInfo.values().iterator();
                    while (it.hasNext()) {
                        indentingPrintWriter.println(it.next().toString());
                    }
                } finally {
                }
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Clients:");
            indentingPrintWriter.increaseIndent();
            synchronized (this.mClients) {
                try {
                    java.util.Iterator<com.android.server.midi.MidiService.Client> it2 = this.mClients.values().iterator();
                    while (it2.hasNext()) {
                        indentingPrintWriter.println(it2.next().toString());
                    }
                } finally {
                }
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUsbMidiLock"})
    private boolean isUsbMidiDeviceInUseLocked(android.media.midi.MidiDeviceInfo midiDeviceInfo) {
        java.lang.String string = midiDeviceInfo.getProperties().getString("name");
        if (string.length() < MIDI_LEGACY_STRING.length()) {
            return false;
        }
        java.lang.String extractUsbDeviceName = extractUsbDeviceName(string);
        java.lang.String extractUsbDeviceTag = extractUsbDeviceTag(string);
        android.util.Log.i(TAG, "Checking " + extractUsbDeviceName + " " + extractUsbDeviceTag);
        if (this.mUsbMidiUniversalDeviceInUse.contains(extractUsbDeviceName)) {
            return true;
        }
        return extractUsbDeviceTag.equals(MIDI_UNIVERSAL_STRING) && this.mUsbMidiLegacyDeviceOpenCount.containsKey(extractUsbDeviceName);
    }

    @com.android.internal.annotations.GuardedBy({"mUsbMidiLock"})
    void addUsbMidiDeviceLocked(android.media.midi.MidiDeviceInfo midiDeviceInfo) {
        java.lang.String string = midiDeviceInfo.getProperties().getString("name");
        if (string.length() < MIDI_LEGACY_STRING.length()) {
            return;
        }
        java.lang.String extractUsbDeviceName = extractUsbDeviceName(string);
        java.lang.String extractUsbDeviceTag = extractUsbDeviceTag(string);
        android.util.Log.i(TAG, "Adding " + extractUsbDeviceName + " " + extractUsbDeviceTag);
        if (extractUsbDeviceTag.equals(MIDI_UNIVERSAL_STRING)) {
            this.mUsbMidiUniversalDeviceInUse.add(extractUsbDeviceName);
        } else if (extractUsbDeviceTag.equals(MIDI_LEGACY_STRING)) {
            this.mUsbMidiLegacyDeviceOpenCount.put(extractUsbDeviceName, java.lang.Integer.valueOf(this.mUsbMidiLegacyDeviceOpenCount.getOrDefault(extractUsbDeviceName, 0).intValue() + 1));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUsbMidiLock"})
    void removeUsbMidiDeviceLocked(android.media.midi.MidiDeviceInfo midiDeviceInfo) {
        java.lang.String string = midiDeviceInfo.getProperties().getString("name");
        if (string.length() < MIDI_LEGACY_STRING.length()) {
            return;
        }
        java.lang.String extractUsbDeviceName = extractUsbDeviceName(string);
        java.lang.String extractUsbDeviceTag = extractUsbDeviceTag(string);
        android.util.Log.i(TAG, "Removing " + extractUsbDeviceName + " " + extractUsbDeviceTag);
        if (extractUsbDeviceTag.equals(MIDI_UNIVERSAL_STRING)) {
            this.mUsbMidiUniversalDeviceInUse.remove(extractUsbDeviceName);
            return;
        }
        if (extractUsbDeviceTag.equals(MIDI_LEGACY_STRING) && this.mUsbMidiLegacyDeviceOpenCount.containsKey(extractUsbDeviceName)) {
            int intValue = this.mUsbMidiLegacyDeviceOpenCount.get(extractUsbDeviceName).intValue();
            if (intValue > 1) {
                this.mUsbMidiLegacyDeviceOpenCount.put(extractUsbDeviceName, java.lang.Integer.valueOf(intValue - 1));
            } else {
                this.mUsbMidiLegacyDeviceOpenCount.remove(extractUsbDeviceName);
            }
        }
    }

    java.lang.String extractUsbDeviceName(java.lang.String str) {
        return str.substring(0, str.length() - MIDI_LEGACY_STRING.length());
    }

    java.lang.String extractUsbDeviceTag(java.lang.String str) {
        return str.substring(str.length() - MIDI_LEGACY_STRING.length());
    }

    private int getCallingUserId() {
        return android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUmpDevice(android.media.midi.MidiDeviceInfo midiDeviceInfo) {
        return midiDeviceInfo.getDefaultProtocol() != -1;
    }
}
