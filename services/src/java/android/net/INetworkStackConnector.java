package android.net;

/* loaded from: classes.dex */
public interface INetworkStackConnector extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$net$INetworkStackConnector".replace('$', '.');
    public static final java.lang.String HASH = "9bd9d687ddb816baf1faabcad0d56ac15b22c56e";
    public static final int VERSION = 21;

    void allowTestUid(int i, android.net.INetworkStackStatusCallback iNetworkStackStatusCallback) throws android.os.RemoteException;

    void fetchIpMemoryStore(android.net.IIpMemoryStoreCallbacks iIpMemoryStoreCallbacks) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void makeDhcpServer(java.lang.String str, android.net.dhcp.DhcpServingParamsParcel dhcpServingParamsParcel, android.net.dhcp.IDhcpServerCallbacks iDhcpServerCallbacks) throws android.os.RemoteException;

    void makeIpClient(java.lang.String str, android.net.ip.IIpClientCallbacks iIpClientCallbacks) throws android.os.RemoteException;

    void makeNetworkMonitor(android.net.Network network, java.lang.String str, android.net.INetworkMonitorCallbacks iNetworkMonitorCallbacks) throws android.os.RemoteException;

    public static class Default implements android.net.INetworkStackConnector {
        @Override // android.net.INetworkStackConnector
        public void makeDhcpServer(java.lang.String str, android.net.dhcp.DhcpServingParamsParcel dhcpServingParamsParcel, android.net.dhcp.IDhcpServerCallbacks iDhcpServerCallbacks) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkStackConnector
        public void makeNetworkMonitor(android.net.Network network, java.lang.String str, android.net.INetworkMonitorCallbacks iNetworkMonitorCallbacks) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkStackConnector
        public void makeIpClient(java.lang.String str, android.net.ip.IIpClientCallbacks iIpClientCallbacks) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkStackConnector
        public void fetchIpMemoryStore(android.net.IIpMemoryStoreCallbacks iIpMemoryStoreCallbacks) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkStackConnector
        public void allowTestUid(int i, android.net.INetworkStackStatusCallback iNetworkStackStatusCallback) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkStackConnector
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.INetworkStackConnector
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.INetworkStackConnector {
        static final int TRANSACTION_allowTestUid = 5;
        static final int TRANSACTION_fetchIpMemoryStore = 4;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_makeDhcpServer = 1;
        static final int TRANSACTION_makeIpClient = 3;
        static final int TRANSACTION_makeNetworkMonitor = 2;

        public Stub() {
            attachInterface(this, android.net.INetworkStackConnector.DESCRIPTOR);
        }

        public static android.net.INetworkStackConnector asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.INetworkStackConnector.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.INetworkStackConnector)) {
                return (android.net.INetworkStackConnector) queryLocalInterface;
            }
            return new android.net.INetworkStackConnector.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.net.INetworkStackConnector.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    makeDhcpServer(parcel.readString(), (android.net.dhcp.DhcpServingParamsParcel) parcel.readTypedObject(android.net.dhcp.DhcpServingParamsParcel.CREATOR), android.net.dhcp.IDhcpServerCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 2:
                    makeNetworkMonitor((android.net.Network) parcel.readTypedObject(android.net.Network.CREATOR), parcel.readString(), android.net.INetworkMonitorCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 3:
                    makeIpClient(parcel.readString(), android.net.ip.IIpClientCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 4:
                    fetchIpMemoryStore(android.net.IIpMemoryStoreCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 5:
                    allowTestUid(parcel.readInt(), android.net.INetworkStackStatusCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.INetworkStackConnector {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.INetworkStackConnector.DESCRIPTOR;
            }

            @Override // android.net.INetworkStackConnector
            public void makeDhcpServer(java.lang.String str, android.net.dhcp.DhcpServingParamsParcel dhcpServingParamsParcel, android.net.dhcp.IDhcpServerCallbacks iDhcpServerCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkStackConnector.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(dhcpServingParamsParcel, 0);
                    obtain.writeStrongInterface(iDhcpServerCallbacks);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method makeDhcpServer is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkStackConnector
            public void makeNetworkMonitor(android.net.Network network, java.lang.String str, android.net.INetworkMonitorCallbacks iNetworkMonitorCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkStackConnector.DESCRIPTOR);
                    obtain.writeTypedObject(network, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iNetworkMonitorCallbacks);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method makeNetworkMonitor is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkStackConnector
            public void makeIpClient(java.lang.String str, android.net.ip.IIpClientCallbacks iIpClientCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkStackConnector.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iIpClientCallbacks);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method makeIpClient is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkStackConnector
            public void fetchIpMemoryStore(android.net.IIpMemoryStoreCallbacks iIpMemoryStoreCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkStackConnector.DESCRIPTOR);
                    obtain.writeStrongInterface(iIpMemoryStoreCallbacks);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method fetchIpMemoryStore is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkStackConnector
            public void allowTestUid(int i, android.net.INetworkStackStatusCallback iNetworkStackStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkStackConnector.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iNetworkStackStatusCallback);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method allowTestUid is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkStackConnector
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain();
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.net.INetworkStackConnector.DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.net.INetworkStackConnector
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain();
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.net.INetworkStackConnector.DESCRIPTOR);
                            this.mRemote.transact(android.net.INetworkStackConnector.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
                            obtain2.readException();
                            this.mCachedHash = obtain2.readString();
                            obtain2.recycle();
                            obtain.recycle();
                        } catch (java.lang.Throwable th) {
                            obtain2.recycle();
                            obtain.recycle();
                            throw th;
                        }
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }
    }
}
