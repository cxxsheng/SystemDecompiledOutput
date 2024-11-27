package android.net.wifi.sharedconnectivity.service;

/* loaded from: classes2.dex */
public interface ISharedConnectivityService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.net.wifi.sharedconnectivity.service.ISharedConnectivityService";

    void connectHotspotNetwork(android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork) throws android.os.RemoteException;

    void connectKnownNetwork(android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork) throws android.os.RemoteException;

    void disconnectHotspotNetwork(android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork) throws android.os.RemoteException;

    void forgetKnownNetwork(android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork) throws android.os.RemoteException;

    android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus getHotspotNetworkConnectionStatus() throws android.os.RemoteException;

    java.util.List<android.net.wifi.sharedconnectivity.app.HotspotNetwork> getHotspotNetworks() throws android.os.RemoteException;

    android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus getKnownNetworkConnectionStatus() throws android.os.RemoteException;

    java.util.List<android.net.wifi.sharedconnectivity.app.KnownNetwork> getKnownNetworks() throws android.os.RemoteException;

    android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState getSettingsState() throws android.os.RemoteException;

    void registerCallback(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback iSharedConnectivityCallback) throws android.os.RemoteException;

    void unregisterCallback(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback iSharedConnectivityCallback) throws android.os.RemoteException;

    public static class Default implements android.net.wifi.sharedconnectivity.service.ISharedConnectivityService {
        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void registerCallback(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback iSharedConnectivityCallback) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void unregisterCallback(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback iSharedConnectivityCallback) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void connectHotspotNetwork(android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void disconnectHotspotNetwork(android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void connectKnownNetwork(android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void forgetKnownNetwork(android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public java.util.List<android.net.wifi.sharedconnectivity.app.HotspotNetwork> getHotspotNetworks() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public java.util.List<android.net.wifi.sharedconnectivity.app.KnownNetwork> getKnownNetworks() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState getSettingsState() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus getHotspotNetworkConnectionStatus() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus getKnownNetworkConnectionStatus() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.wifi.sharedconnectivity.service.ISharedConnectivityService {
        static final int TRANSACTION_connectHotspotNetwork = 3;
        static final int TRANSACTION_connectKnownNetwork = 5;
        static final int TRANSACTION_disconnectHotspotNetwork = 4;
        static final int TRANSACTION_forgetKnownNetwork = 6;
        static final int TRANSACTION_getHotspotNetworkConnectionStatus = 10;
        static final int TRANSACTION_getHotspotNetworks = 7;
        static final int TRANSACTION_getKnownNetworkConnectionStatus = 11;
        static final int TRANSACTION_getKnownNetworks = 8;
        static final int TRANSACTION_getSettingsState = 9;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_unregisterCallback = 2;

        public Stub() {
            attachInterface(this, android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
        }

        public static android.net.wifi.sharedconnectivity.service.ISharedConnectivityService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.wifi.sharedconnectivity.service.ISharedConnectivityService)) {
                return (android.net.wifi.sharedconnectivity.service.ISharedConnectivityService) queryLocalInterface;
            }
            return new android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerCallback";
                case 2:
                    return "unregisterCallback";
                case 3:
                    return "connectHotspotNetwork";
                case 4:
                    return "disconnectHotspotNetwork";
                case 5:
                    return "connectKnownNetwork";
                case 6:
                    return "forgetKnownNetwork";
                case 7:
                    return "getHotspotNetworks";
                case 8:
                    return "getKnownNetworks";
                case 9:
                    return "getSettingsState";
                case 10:
                    return "getHotspotNetworkConnectionStatus";
                case 11:
                    return "getKnownNetworkConnectionStatus";
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
                parcel.enforceInterface(android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback asInterface = android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback asInterface2 = android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork = (android.net.wifi.sharedconnectivity.app.HotspotNetwork) parcel.readTypedObject(android.net.wifi.sharedconnectivity.app.HotspotNetwork.CREATOR);
                    parcel.enforceNoDataAvail();
                    connectHotspotNetwork(hotspotNetwork);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork2 = (android.net.wifi.sharedconnectivity.app.HotspotNetwork) parcel.readTypedObject(android.net.wifi.sharedconnectivity.app.HotspotNetwork.CREATOR);
                    parcel.enforceNoDataAvail();
                    disconnectHotspotNetwork(hotspotNetwork2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork = (android.net.wifi.sharedconnectivity.app.KnownNetwork) parcel.readTypedObject(android.net.wifi.sharedconnectivity.app.KnownNetwork.CREATOR);
                    parcel.enforceNoDataAvail();
                    connectKnownNetwork(knownNetwork);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork2 = (android.net.wifi.sharedconnectivity.app.KnownNetwork) parcel.readTypedObject(android.net.wifi.sharedconnectivity.app.KnownNetwork.CREATOR);
                    parcel.enforceNoDataAvail();
                    forgetKnownNetwork(knownNetwork2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.util.List<android.net.wifi.sharedconnectivity.app.HotspotNetwork> hotspotNetworks = getHotspotNetworks();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(hotspotNetworks, 1);
                    return true;
                case 8:
                    java.util.List<android.net.wifi.sharedconnectivity.app.KnownNetwork> knownNetworks = getKnownNetworks();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(knownNetworks, 1);
                    return true;
                case 9:
                    android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState settingsState = getSettingsState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(settingsState, 1);
                    return true;
                case 10:
                    android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus = getHotspotNetworkConnectionStatus();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(hotspotNetworkConnectionStatus, 1);
                    return true;
                case 11:
                    android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus knownNetworkConnectionStatus = getKnownNetworkConnectionStatus();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(knownNetworkConnectionStatus, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.wifi.sharedconnectivity.service.ISharedConnectivityService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR;
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public void registerCallback(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback iSharedConnectivityCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSharedConnectivityCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public void unregisterCallback(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback iSharedConnectivityCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSharedConnectivityCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public void connectHotspotNetwork(android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
                    obtain.writeTypedObject(hotspotNetwork, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public void disconnectHotspotNetwork(android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
                    obtain.writeTypedObject(hotspotNetwork, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public void connectKnownNetwork(android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
                    obtain.writeTypedObject(knownNetwork, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public void forgetKnownNetwork(android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
                    obtain.writeTypedObject(knownNetwork, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public java.util.List<android.net.wifi.sharedconnectivity.app.HotspotNetwork> getHotspotNetworks() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.net.wifi.sharedconnectivity.app.HotspotNetwork.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public java.util.List<android.net.wifi.sharedconnectivity.app.KnownNetwork> getKnownNetworks() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.net.wifi.sharedconnectivity.app.KnownNetwork.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState getSettingsState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState) obtain2.readTypedObject(android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus getHotspotNetworkConnectionStatus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus) obtain2.readTypedObject(android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus getKnownNetworkConnectionStatus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus) obtain2.readTypedObject(android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
