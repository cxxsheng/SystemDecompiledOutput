package android.companion.virtual;

/* loaded from: classes.dex */
public interface IVirtualDeviceManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.virtual.IVirtualDeviceManager";

    android.companion.virtual.IVirtualDevice createVirtualDevice(android.os.IBinder iBinder, android.content.AttributionSource attributionSource, int i, android.companion.virtual.VirtualDeviceParams virtualDeviceParams, android.companion.virtual.IVirtualDeviceActivityListener iVirtualDeviceActivityListener, android.companion.virtual.IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener) throws android.os.RemoteException;

    int createVirtualDisplay(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.companion.virtual.IVirtualDevice iVirtualDevice, java.lang.String str) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAllPersistentDeviceIds() throws android.os.RemoteException;

    int getAudioPlaybackSessionId(int i) throws android.os.RemoteException;

    int getAudioRecordingSessionId(int i) throws android.os.RemoteException;

    int getDeviceIdForDisplayId(int i) throws android.os.RemoteException;

    int getDevicePolicy(int i, int i2) throws android.os.RemoteException;

    java.lang.CharSequence getDisplayNameForPersistentDeviceId(java.lang.String str) throws android.os.RemoteException;

    android.companion.virtual.VirtualDevice getVirtualDevice(int i) throws android.os.RemoteException;

    java.util.List<android.companion.virtual.VirtualDevice> getVirtualDevices() throws android.os.RemoteException;

    boolean isValidVirtualDeviceId(int i) throws android.os.RemoteException;

    boolean isVirtualDeviceOwnedMirrorDisplay(int i) throws android.os.RemoteException;

    void playSoundEffect(int i, int i2) throws android.os.RemoteException;

    void registerVirtualDeviceListener(android.companion.virtual.IVirtualDeviceListener iVirtualDeviceListener) throws android.os.RemoteException;

    void unregisterVirtualDeviceListener(android.companion.virtual.IVirtualDeviceListener iVirtualDeviceListener) throws android.os.RemoteException;

    public static class Default implements android.companion.virtual.IVirtualDeviceManager {
        @Override // android.companion.virtual.IVirtualDeviceManager
        public android.companion.virtual.IVirtualDevice createVirtualDevice(android.os.IBinder iBinder, android.content.AttributionSource attributionSource, int i, android.companion.virtual.VirtualDeviceParams virtualDeviceParams, android.companion.virtual.IVirtualDeviceActivityListener iVirtualDeviceActivityListener, android.companion.virtual.IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener) throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public java.util.List<android.companion.virtual.VirtualDevice> getVirtualDevices() throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public android.companion.virtual.VirtualDevice getVirtualDevice(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public void registerVirtualDeviceListener(android.companion.virtual.IVirtualDeviceListener iVirtualDeviceListener) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public void unregisterVirtualDeviceListener(android.companion.virtual.IVirtualDeviceListener iVirtualDeviceListener) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public int getDeviceIdForDisplayId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public java.lang.CharSequence getDisplayNameForPersistentDeviceId(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public boolean isValidVirtualDeviceId(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public int getDevicePolicy(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public int createVirtualDisplay(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.companion.virtual.IVirtualDevice iVirtualDevice, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public int getAudioPlaybackSessionId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public int getAudioRecordingSessionId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public void playSoundEffect(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public boolean isVirtualDeviceOwnedMirrorDisplay(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public java.util.List<java.lang.String> getAllPersistentDeviceIds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.virtual.IVirtualDeviceManager {
        static final int TRANSACTION_createVirtualDevice = 1;
        static final int TRANSACTION_createVirtualDisplay = 10;
        static final int TRANSACTION_getAllPersistentDeviceIds = 15;
        static final int TRANSACTION_getAudioPlaybackSessionId = 11;
        static final int TRANSACTION_getAudioRecordingSessionId = 12;
        static final int TRANSACTION_getDeviceIdForDisplayId = 6;
        static final int TRANSACTION_getDevicePolicy = 9;
        static final int TRANSACTION_getDisplayNameForPersistentDeviceId = 7;
        static final int TRANSACTION_getVirtualDevice = 3;
        static final int TRANSACTION_getVirtualDevices = 2;
        static final int TRANSACTION_isValidVirtualDeviceId = 8;
        static final int TRANSACTION_isVirtualDeviceOwnedMirrorDisplay = 14;
        static final int TRANSACTION_playSoundEffect = 13;
        static final int TRANSACTION_registerVirtualDeviceListener = 4;
        static final int TRANSACTION_unregisterVirtualDeviceListener = 5;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.companion.virtual.IVirtualDeviceManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.virtual.IVirtualDeviceManager)) {
                return (android.companion.virtual.IVirtualDeviceManager) queryLocalInterface;
            }
            return new android.companion.virtual.IVirtualDeviceManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createVirtualDevice";
                case 2:
                    return "getVirtualDevices";
                case 3:
                    return "getVirtualDevice";
                case 4:
                    return "registerVirtualDeviceListener";
                case 5:
                    return "unregisterVirtualDeviceListener";
                case 6:
                    return "getDeviceIdForDisplayId";
                case 7:
                    return "getDisplayNameForPersistentDeviceId";
                case 8:
                    return "isValidVirtualDeviceId";
                case 9:
                    return "getDevicePolicy";
                case 10:
                    return "createVirtualDisplay";
                case 11:
                    return "getAudioPlaybackSessionId";
                case 12:
                    return "getAudioRecordingSessionId";
                case 13:
                    return "playSoundEffect";
                case 14:
                    return "isVirtualDeviceOwnedMirrorDisplay";
                case 15:
                    return "getAllPersistentDeviceIds";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.content.AttributionSource attributionSource = (android.content.AttributionSource) parcel.readTypedObject(android.content.AttributionSource.CREATOR);
                    int readInt = parcel.readInt();
                    android.companion.virtual.VirtualDeviceParams virtualDeviceParams = (android.companion.virtual.VirtualDeviceParams) parcel.readTypedObject(android.companion.virtual.VirtualDeviceParams.CREATOR);
                    android.companion.virtual.IVirtualDeviceActivityListener asInterface = android.companion.virtual.IVirtualDeviceActivityListener.Stub.asInterface(parcel.readStrongBinder());
                    android.companion.virtual.IVirtualDeviceSoundEffectListener asInterface2 = android.companion.virtual.IVirtualDeviceSoundEffectListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.companion.virtual.IVirtualDevice createVirtualDevice = createVirtualDevice(readStrongBinder, attributionSource, readInt, virtualDeviceParams, asInterface, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createVirtualDevice);
                    return true;
                case 2:
                    java.util.List<android.companion.virtual.VirtualDevice> virtualDevices = getVirtualDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(virtualDevices, 1);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.companion.virtual.VirtualDevice virtualDevice = getVirtualDevice(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(virtualDevice, 1);
                    return true;
                case 4:
                    android.companion.virtual.IVirtualDeviceListener asInterface3 = android.companion.virtual.IVirtualDeviceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerVirtualDeviceListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.companion.virtual.IVirtualDeviceListener asInterface4 = android.companion.virtual.IVirtualDeviceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterVirtualDeviceListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deviceIdForDisplayId = getDeviceIdForDisplayId(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceIdForDisplayId);
                    return true;
                case 7:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence displayNameForPersistentDeviceId = getDisplayNameForPersistentDeviceId(readString);
                    parcel2.writeNoException();
                    if (displayNameForPersistentDeviceId != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(displayNameForPersistentDeviceId, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isValidVirtualDeviceId = isValidVirtualDeviceId(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isValidVirtualDeviceId);
                    return true;
                case 9:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int devicePolicy = getDevicePolicy(readInt5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(devicePolicy);
                    return true;
                case 10:
                    android.hardware.display.VirtualDisplayConfig virtualDisplayConfig = (android.hardware.display.VirtualDisplayConfig) parcel.readTypedObject(android.hardware.display.VirtualDisplayConfig.CREATOR);
                    android.hardware.display.IVirtualDisplayCallback asInterface5 = android.hardware.display.IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.companion.virtual.IVirtualDevice asInterface6 = android.companion.virtual.IVirtualDevice.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int createVirtualDisplay = createVirtualDisplay(virtualDisplayConfig, asInterface5, asInterface6, readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(createVirtualDisplay);
                    return true;
                case 11:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int audioPlaybackSessionId = getAudioPlaybackSessionId(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeInt(audioPlaybackSessionId);
                    return true;
                case 12:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int audioRecordingSessionId = getAudioRecordingSessionId(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(audioRecordingSessionId);
                    return true;
                case 13:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    playSoundEffect(readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVirtualDeviceOwnedMirrorDisplay = isVirtualDeviceOwnedMirrorDisplay(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVirtualDeviceOwnedMirrorDisplay);
                    return true;
                case 15:
                    java.util.List<java.lang.String> allPersistentDeviceIds = getAllPersistentDeviceIds();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allPersistentDeviceIds);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.virtual.IVirtualDeviceManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR;
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public android.companion.virtual.IVirtualDevice createVirtualDevice(android.os.IBinder iBinder, android.content.AttributionSource attributionSource, int i, android.companion.virtual.VirtualDeviceParams virtualDeviceParams, android.companion.virtual.IVirtualDeviceActivityListener iVirtualDeviceActivityListener, android.companion.virtual.IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(virtualDeviceParams, 0);
                    obtain.writeStrongInterface(iVirtualDeviceActivityListener);
                    obtain.writeStrongInterface(iVirtualDeviceSoundEffectListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.companion.virtual.IVirtualDevice.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public java.util.List<android.companion.virtual.VirtualDevice> getVirtualDevices() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.companion.virtual.VirtualDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public android.companion.virtual.VirtualDevice getVirtualDevice(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.companion.virtual.VirtualDevice) obtain2.readTypedObject(android.companion.virtual.VirtualDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public void registerVirtualDeviceListener(android.companion.virtual.IVirtualDeviceListener iVirtualDeviceListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDeviceListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public void unregisterVirtualDeviceListener(android.companion.virtual.IVirtualDeviceListener iVirtualDeviceListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDeviceListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getDeviceIdForDisplayId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public java.lang.CharSequence getDisplayNameForPersistentDeviceId(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public boolean isValidVirtualDeviceId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getDevicePolicy(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int createVirtualDisplay(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.companion.virtual.IVirtualDevice iVirtualDevice, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(virtualDisplayConfig, 0);
                    obtain.writeStrongInterface(iVirtualDisplayCallback);
                    obtain.writeStrongInterface(iVirtualDevice);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getAudioPlaybackSessionId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getAudioRecordingSessionId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public void playSoundEffect(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public boolean isVirtualDeviceOwnedMirrorDisplay(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public java.util.List<java.lang.String> getAllPersistentDeviceIds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void createVirtualDevice_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }
    }
}
