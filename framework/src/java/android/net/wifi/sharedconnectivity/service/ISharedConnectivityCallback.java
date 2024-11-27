package android.net.wifi.sharedconnectivity.service;

/* loaded from: classes2.dex */
public interface ISharedConnectivityCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback";

    void onHotspotNetworkConnectionStatusChanged(android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) throws android.os.RemoteException;

    void onHotspotNetworksUpdated(java.util.List<android.net.wifi.sharedconnectivity.app.HotspotNetwork> list) throws android.os.RemoteException;

    void onKnownNetworkConnectionStatusChanged(android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus knownNetworkConnectionStatus) throws android.os.RemoteException;

    void onKnownNetworksUpdated(java.util.List<android.net.wifi.sharedconnectivity.app.KnownNetwork> list) throws android.os.RemoteException;

    void onServiceConnected() throws android.os.RemoteException;

    void onSharedConnectivitySettingsChanged(android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState sharedConnectivitySettingsState) throws android.os.RemoteException;

    public static class Default implements android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback {
        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onHotspotNetworksUpdated(java.util.List<android.net.wifi.sharedconnectivity.app.HotspotNetwork> list) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onHotspotNetworkConnectionStatusChanged(android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onKnownNetworksUpdated(java.util.List<android.net.wifi.sharedconnectivity.app.KnownNetwork> list) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onKnownNetworkConnectionStatusChanged(android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus knownNetworkConnectionStatus) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onSharedConnectivitySettingsChanged(android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState sharedConnectivitySettingsState) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onServiceConnected() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback {
        static final int TRANSACTION_onHotspotNetworkConnectionStatusChanged = 2;
        static final int TRANSACTION_onHotspotNetworksUpdated = 1;
        static final int TRANSACTION_onKnownNetworkConnectionStatusChanged = 4;
        static final int TRANSACTION_onKnownNetworksUpdated = 3;
        static final int TRANSACTION_onServiceConnected = 6;
        static final int TRANSACTION_onSharedConnectivitySettingsChanged = 5;

        public Stub() {
            attachInterface(this, android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.DESCRIPTOR);
        }

        public static android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback)) {
                return (android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback) queryLocalInterface;
            }
            return new android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onHotspotNetworksUpdated";
                case 2:
                    return "onHotspotNetworkConnectionStatusChanged";
                case 3:
                    return "onKnownNetworksUpdated";
                case 4:
                    return "onKnownNetworkConnectionStatusChanged";
                case 5:
                    return "onSharedConnectivitySettingsChanged";
                case 6:
                    return "onServiceConnected";
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
                parcel.enforceInterface(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.net.wifi.sharedconnectivity.app.HotspotNetwork.CREATOR);
                    parcel.enforceNoDataAvail();
                    onHotspotNetworksUpdated(createTypedArrayList);
                    return true;
                case 2:
                    android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus = (android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus) parcel.readTypedObject(android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    onHotspotNetworkConnectionStatusChanged(hotspotNetworkConnectionStatus);
                    return true;
                case 3:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.net.wifi.sharedconnectivity.app.KnownNetwork.CREATOR);
                    parcel.enforceNoDataAvail();
                    onKnownNetworksUpdated(createTypedArrayList2);
                    return true;
                case 4:
                    android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus knownNetworkConnectionStatus = (android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus) parcel.readTypedObject(android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    onKnownNetworkConnectionStatusChanged(knownNetworkConnectionStatus);
                    return true;
                case 5:
                    android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState sharedConnectivitySettingsState = (android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState) parcel.readTypedObject(android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSharedConnectivitySettingsChanged(sharedConnectivitySettingsState);
                    return true;
                case 6:
                    onServiceConnected();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.DESCRIPTOR;
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onHotspotNetworksUpdated(java.util.List<android.net.wifi.sharedconnectivity.app.HotspotNetwork> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onHotspotNetworkConnectionStatusChanged(android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.DESCRIPTOR);
                    obtain.writeTypedObject(hotspotNetworkConnectionStatus, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onKnownNetworksUpdated(java.util.List<android.net.wifi.sharedconnectivity.app.KnownNetwork> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onKnownNetworkConnectionStatusChanged(android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus knownNetworkConnectionStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.DESCRIPTOR);
                    obtain.writeTypedObject(knownNetworkConnectionStatus, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onSharedConnectivitySettingsChanged(android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState sharedConnectivitySettingsState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.DESCRIPTOR);
                    obtain.writeTypedObject(sharedConnectivitySettingsState, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onServiceConnected() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
