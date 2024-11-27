package android.debug;

/* loaded from: classes.dex */
public interface IAdbManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.debug.IAdbManager";

    void allowDebugging(boolean z, java.lang.String str) throws android.os.RemoteException;

    void allowWirelessDebugging(boolean z, java.lang.String str) throws android.os.RemoteException;

    void clearDebuggingKeys() throws android.os.RemoteException;

    void denyDebugging() throws android.os.RemoteException;

    void denyWirelessDebugging() throws android.os.RemoteException;

    void disablePairing() throws android.os.RemoteException;

    void enablePairingByPairingCode() throws android.os.RemoteException;

    void enablePairingByQrCode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getAdbWirelessPort() throws android.os.RemoteException;

    android.debug.FingerprintAndPairDevice[] getPairedDevices() throws android.os.RemoteException;

    boolean isAdbWifiQrSupported() throws android.os.RemoteException;

    boolean isAdbWifiSupported() throws android.os.RemoteException;

    void registerCallback(android.debug.IAdbCallback iAdbCallback) throws android.os.RemoteException;

    void unpairDevice(java.lang.String str) throws android.os.RemoteException;

    void unregisterCallback(android.debug.IAdbCallback iAdbCallback) throws android.os.RemoteException;

    public static class Default implements android.debug.IAdbManager {
        @Override // android.debug.IAdbManager
        public void allowDebugging(boolean z, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void denyDebugging() throws android.os.RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void clearDebuggingKeys() throws android.os.RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void allowWirelessDebugging(boolean z, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void denyWirelessDebugging() throws android.os.RemoteException {
        }

        @Override // android.debug.IAdbManager
        public android.debug.FingerprintAndPairDevice[] getPairedDevices() throws android.os.RemoteException {
            return null;
        }

        @Override // android.debug.IAdbManager
        public void unpairDevice(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void enablePairingByPairingCode() throws android.os.RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void enablePairingByQrCode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.debug.IAdbManager
        public int getAdbWirelessPort() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.debug.IAdbManager
        public void disablePairing() throws android.os.RemoteException {
        }

        @Override // android.debug.IAdbManager
        public boolean isAdbWifiSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.debug.IAdbManager
        public boolean isAdbWifiQrSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.debug.IAdbManager
        public void registerCallback(android.debug.IAdbCallback iAdbCallback) throws android.os.RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void unregisterCallback(android.debug.IAdbCallback iAdbCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.debug.IAdbManager {
        static final int TRANSACTION_allowDebugging = 1;
        static final int TRANSACTION_allowWirelessDebugging = 4;
        static final int TRANSACTION_clearDebuggingKeys = 3;
        static final int TRANSACTION_denyDebugging = 2;
        static final int TRANSACTION_denyWirelessDebugging = 5;
        static final int TRANSACTION_disablePairing = 11;
        static final int TRANSACTION_enablePairingByPairingCode = 8;
        static final int TRANSACTION_enablePairingByQrCode = 9;
        static final int TRANSACTION_getAdbWirelessPort = 10;
        static final int TRANSACTION_getPairedDevices = 6;
        static final int TRANSACTION_isAdbWifiQrSupported = 13;
        static final int TRANSACTION_isAdbWifiSupported = 12;
        static final int TRANSACTION_registerCallback = 14;
        static final int TRANSACTION_unpairDevice = 7;
        static final int TRANSACTION_unregisterCallback = 15;

        public Stub() {
            attachInterface(this, android.debug.IAdbManager.DESCRIPTOR);
        }

        public static android.debug.IAdbManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.debug.IAdbManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.debug.IAdbManager)) {
                return (android.debug.IAdbManager) queryLocalInterface;
            }
            return new android.debug.IAdbManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "allowDebugging";
                case 2:
                    return "denyDebugging";
                case 3:
                    return "clearDebuggingKeys";
                case 4:
                    return "allowWirelessDebugging";
                case 5:
                    return "denyWirelessDebugging";
                case 6:
                    return "getPairedDevices";
                case 7:
                    return "unpairDevice";
                case 8:
                    return "enablePairingByPairingCode";
                case 9:
                    return "enablePairingByQrCode";
                case 10:
                    return "getAdbWirelessPort";
                case 11:
                    return "disablePairing";
                case 12:
                    return "isAdbWifiSupported";
                case 13:
                    return "isAdbWifiQrSupported";
                case 14:
                    return "registerCallback";
                case 15:
                    return "unregisterCallback";
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
                parcel.enforceInterface(android.debug.IAdbManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.debug.IAdbManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    allowDebugging(readBoolean, readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    denyDebugging();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    clearDebuggingKeys();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean readBoolean2 = parcel.readBoolean();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    allowWirelessDebugging(readBoolean2, readString2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    denyWirelessDebugging();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.debug.FingerprintAndPairDevice[] pairedDevices = getPairedDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(pairedDevices, 1);
                    return true;
                case 7:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unpairDevice(readString3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    enablePairingByPairingCode();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enablePairingByQrCode(readString4, readString5);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int adbWirelessPort = getAdbWirelessPort();
                    parcel2.writeNoException();
                    parcel2.writeInt(adbWirelessPort);
                    return true;
                case 11:
                    disablePairing();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean isAdbWifiSupported = isAdbWifiSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdbWifiSupported);
                    return true;
                case 13:
                    boolean isAdbWifiQrSupported = isAdbWifiQrSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdbWifiQrSupported);
                    return true;
                case 14:
                    android.debug.IAdbCallback asInterface = android.debug.IAdbCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.debug.IAdbCallback asInterface2 = android.debug.IAdbCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.debug.IAdbManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.debug.IAdbManager.DESCRIPTOR;
            }

            @Override // android.debug.IAdbManager
            public void allowDebugging(boolean z, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void denyDebugging() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void clearDebuggingKeys() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void allowWirelessDebugging(boolean z, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void denyWirelessDebugging() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public android.debug.FingerprintAndPairDevice[] getPairedDevices() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.debug.FingerprintAndPairDevice[]) obtain2.createTypedArray(android.debug.FingerprintAndPairDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void unpairDevice(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void enablePairingByPairingCode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void enablePairingByQrCode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public int getAdbWirelessPort() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void disablePairing() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public boolean isAdbWifiSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public boolean isAdbWifiQrSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void registerCallback(android.debug.IAdbCallback iAdbCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iAdbCallback);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void unregisterCallback(android.debug.IAdbCallback iAdbCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.debug.IAdbManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iAdbCallback);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }
    }
}
