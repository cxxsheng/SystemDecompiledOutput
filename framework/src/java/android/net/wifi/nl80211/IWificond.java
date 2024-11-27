package android.net.wifi.nl80211;

/* loaded from: classes2.dex */
public interface IWificond extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.net.wifi.nl80211.IWificond";

    java.util.List<android.os.IBinder> GetApInterfaces() throws android.os.RemoteException;

    java.util.List<android.os.IBinder> GetClientInterfaces() throws android.os.RemoteException;

    void RegisterCallback(android.net.wifi.nl80211.IInterfaceEventCallback iInterfaceEventCallback) throws android.os.RemoteException;

    void UnregisterCallback(android.net.wifi.nl80211.IInterfaceEventCallback iInterfaceEventCallback) throws android.os.RemoteException;

    android.net.wifi.nl80211.IApInterface createApInterface(java.lang.String str) throws android.os.RemoteException;

    android.net.wifi.nl80211.IClientInterface createClientInterface(java.lang.String str) throws android.os.RemoteException;

    int[] getAvailable2gChannels() throws android.os.RemoteException;

    int[] getAvailable5gNonDFSChannels() throws android.os.RemoteException;

    int[] getAvailable60gChannels() throws android.os.RemoteException;

    int[] getAvailable6gChannels() throws android.os.RemoteException;

    int[] getAvailableDFSChannels() throws android.os.RemoteException;

    android.net.wifi.nl80211.DeviceWiphyCapabilities getDeviceWiphyCapabilities(java.lang.String str) throws android.os.RemoteException;

    void notifyCountryCodeChanged() throws android.os.RemoteException;

    void registerWificondEventCallback(android.net.wifi.nl80211.IWificondEventCallback iWificondEventCallback) throws android.os.RemoteException;

    boolean tearDownApInterface(java.lang.String str) throws android.os.RemoteException;

    boolean tearDownClientInterface(java.lang.String str) throws android.os.RemoteException;

    void tearDownInterfaces() throws android.os.RemoteException;

    void unregisterWificondEventCallback(android.net.wifi.nl80211.IWificondEventCallback iWificondEventCallback) throws android.os.RemoteException;

    public static class Default implements android.net.wifi.nl80211.IWificond {
        @Override // android.net.wifi.nl80211.IWificond
        public android.net.wifi.nl80211.IApInterface createApInterface(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public android.net.wifi.nl80211.IClientInterface createClientInterface(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public boolean tearDownApInterface(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public boolean tearDownClientInterface(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public void tearDownInterfaces() throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWificond
        public java.util.List<android.os.IBinder> GetClientInterfaces() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public java.util.List<android.os.IBinder> GetApInterfaces() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public int[] getAvailable2gChannels() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public int[] getAvailable5gNonDFSChannels() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public int[] getAvailableDFSChannels() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public int[] getAvailable6gChannels() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public int[] getAvailable60gChannels() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public void RegisterCallback(android.net.wifi.nl80211.IInterfaceEventCallback iInterfaceEventCallback) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWificond
        public void UnregisterCallback(android.net.wifi.nl80211.IInterfaceEventCallback iInterfaceEventCallback) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWificond
        public void registerWificondEventCallback(android.net.wifi.nl80211.IWificondEventCallback iWificondEventCallback) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWificond
        public void unregisterWificondEventCallback(android.net.wifi.nl80211.IWificondEventCallback iWificondEventCallback) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWificond
        public android.net.wifi.nl80211.DeviceWiphyCapabilities getDeviceWiphyCapabilities(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public void notifyCountryCodeChanged() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.wifi.nl80211.IWificond {
        static final int TRANSACTION_GetApInterfaces = 7;
        static final int TRANSACTION_GetClientInterfaces = 6;
        static final int TRANSACTION_RegisterCallback = 13;
        static final int TRANSACTION_UnregisterCallback = 14;
        static final int TRANSACTION_createApInterface = 1;
        static final int TRANSACTION_createClientInterface = 2;
        static final int TRANSACTION_getAvailable2gChannels = 8;
        static final int TRANSACTION_getAvailable5gNonDFSChannels = 9;
        static final int TRANSACTION_getAvailable60gChannels = 12;
        static final int TRANSACTION_getAvailable6gChannels = 11;
        static final int TRANSACTION_getAvailableDFSChannels = 10;
        static final int TRANSACTION_getDeviceWiphyCapabilities = 17;
        static final int TRANSACTION_notifyCountryCodeChanged = 18;
        static final int TRANSACTION_registerWificondEventCallback = 15;
        static final int TRANSACTION_tearDownApInterface = 3;
        static final int TRANSACTION_tearDownClientInterface = 4;
        static final int TRANSACTION_tearDownInterfaces = 5;
        static final int TRANSACTION_unregisterWificondEventCallback = 16;

        public Stub() {
            attachInterface(this, android.net.wifi.nl80211.IWificond.DESCRIPTOR);
        }

        public static android.net.wifi.nl80211.IWificond asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.wifi.nl80211.IWificond)) {
                return (android.net.wifi.nl80211.IWificond) queryLocalInterface;
            }
            return new android.net.wifi.nl80211.IWificond.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createApInterface";
                case 2:
                    return "createClientInterface";
                case 3:
                    return "tearDownApInterface";
                case 4:
                    return "tearDownClientInterface";
                case 5:
                    return "tearDownInterfaces";
                case 6:
                    return "GetClientInterfaces";
                case 7:
                    return "GetApInterfaces";
                case 8:
                    return "getAvailable2gChannels";
                case 9:
                    return "getAvailable5gNonDFSChannels";
                case 10:
                    return "getAvailableDFSChannels";
                case 11:
                    return "getAvailable6gChannels";
                case 12:
                    return "getAvailable60gChannels";
                case 13:
                    return "RegisterCallback";
                case 14:
                    return "UnregisterCallback";
                case 15:
                    return "registerWificondEventCallback";
                case 16:
                    return "unregisterWificondEventCallback";
                case 17:
                    return "getDeviceWiphyCapabilities";
                case 18:
                    return "notifyCountryCodeChanged";
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
                parcel.enforceInterface(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.net.wifi.nl80211.IApInterface createApInterface = createApInterface(readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createApInterface);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.net.wifi.nl80211.IClientInterface createClientInterface = createClientInterface(readString2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createClientInterface);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean tearDownApInterface = tearDownApInterface(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(tearDownApInterface);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean tearDownClientInterface = tearDownClientInterface(readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(tearDownClientInterface);
                    return true;
                case 5:
                    tearDownInterfaces();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.util.List<android.os.IBinder> GetClientInterfaces = GetClientInterfaces();
                    parcel2.writeNoException();
                    parcel2.writeBinderList(GetClientInterfaces);
                    return true;
                case 7:
                    java.util.List<android.os.IBinder> GetApInterfaces = GetApInterfaces();
                    parcel2.writeNoException();
                    parcel2.writeBinderList(GetApInterfaces);
                    return true;
                case 8:
                    int[] available2gChannels = getAvailable2gChannels();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(available2gChannels);
                    return true;
                case 9:
                    int[] available5gNonDFSChannels = getAvailable5gNonDFSChannels();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(available5gNonDFSChannels);
                    return true;
                case 10:
                    int[] availableDFSChannels = getAvailableDFSChannels();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(availableDFSChannels);
                    return true;
                case 11:
                    int[] available6gChannels = getAvailable6gChannels();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(available6gChannels);
                    return true;
                case 12:
                    int[] available60gChannels = getAvailable60gChannels();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(available60gChannels);
                    return true;
                case 13:
                    android.net.wifi.nl80211.IInterfaceEventCallback asInterface = android.net.wifi.nl80211.IInterfaceEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    RegisterCallback(asInterface);
                    return true;
                case 14:
                    android.net.wifi.nl80211.IInterfaceEventCallback asInterface2 = android.net.wifi.nl80211.IInterfaceEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    UnregisterCallback(asInterface2);
                    return true;
                case 15:
                    android.net.wifi.nl80211.IWificondEventCallback asInterface3 = android.net.wifi.nl80211.IWificondEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerWificondEventCallback(asInterface3);
                    return true;
                case 16:
                    android.net.wifi.nl80211.IWificondEventCallback asInterface4 = android.net.wifi.nl80211.IWificondEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterWificondEventCallback(asInterface4);
                    return true;
                case 17:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.net.wifi.nl80211.DeviceWiphyCapabilities deviceWiphyCapabilities = getDeviceWiphyCapabilities(readString5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceWiphyCapabilities, 1);
                    return true;
                case 18:
                    notifyCountryCodeChanged();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.wifi.nl80211.IWificond {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.wifi.nl80211.IWificond.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IWificond
            public android.net.wifi.nl80211.IApInterface createApInterface(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.net.wifi.nl80211.IApInterface.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public android.net.wifi.nl80211.IClientInterface createClientInterface(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.net.wifi.nl80211.IClientInterface.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public boolean tearDownApInterface(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public boolean tearDownClientInterface(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public void tearDownInterfaces() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public java.util.List<android.os.IBinder> GetClientInterfaces() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createBinderArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public java.util.List<android.os.IBinder> GetApInterfaces() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createBinderArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public int[] getAvailable2gChannels() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public int[] getAvailable5gNonDFSChannels() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public int[] getAvailableDFSChannels() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public int[] getAvailable6gChannels() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public int[] getAvailable60gChannels() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public void RegisterCallback(android.net.wifi.nl80211.IInterfaceEventCallback iInterfaceEventCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    obtain.writeStrongInterface(iInterfaceEventCallback);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public void UnregisterCallback(android.net.wifi.nl80211.IInterfaceEventCallback iInterfaceEventCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    obtain.writeStrongInterface(iInterfaceEventCallback);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public void registerWificondEventCallback(android.net.wifi.nl80211.IWificondEventCallback iWificondEventCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    obtain.writeStrongInterface(iWificondEventCallback);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public void unregisterWificondEventCallback(android.net.wifi.nl80211.IWificondEventCallback iWificondEventCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    obtain.writeStrongInterface(iWificondEventCallback);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public android.net.wifi.nl80211.DeviceWiphyCapabilities getDeviceWiphyCapabilities(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.wifi.nl80211.DeviceWiphyCapabilities) obtain2.readTypedObject(android.net.wifi.nl80211.DeviceWiphyCapabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public void notifyCountryCodeChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IWificond.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }
    }
}
