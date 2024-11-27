package android.net.dhcp;

/* loaded from: classes.dex */
public interface IDhcpServer extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$net$dhcp$IDhcpServer".replace('$', '.');
    public static final java.lang.String HASH = "9bd9d687ddb816baf1faabcad0d56ac15b22c56e";
    public static final int STATUS_INVALID_ARGUMENT = 2;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_UNKNOWN_ERROR = 3;
    public static final int VERSION = 21;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void start(android.net.INetworkStackStatusCallback iNetworkStackStatusCallback) throws android.os.RemoteException;

    void startWithCallbacks(android.net.INetworkStackStatusCallback iNetworkStackStatusCallback, android.net.dhcp.IDhcpEventCallbacks iDhcpEventCallbacks) throws android.os.RemoteException;

    void stop(android.net.INetworkStackStatusCallback iNetworkStackStatusCallback) throws android.os.RemoteException;

    void updateParams(android.net.dhcp.DhcpServingParamsParcel dhcpServingParamsParcel, android.net.INetworkStackStatusCallback iNetworkStackStatusCallback) throws android.os.RemoteException;

    public static class Default implements android.net.dhcp.IDhcpServer {
        @Override // android.net.dhcp.IDhcpServer
        public void start(android.net.INetworkStackStatusCallback iNetworkStackStatusCallback) throws android.os.RemoteException {
        }

        @Override // android.net.dhcp.IDhcpServer
        public void startWithCallbacks(android.net.INetworkStackStatusCallback iNetworkStackStatusCallback, android.net.dhcp.IDhcpEventCallbacks iDhcpEventCallbacks) throws android.os.RemoteException {
        }

        @Override // android.net.dhcp.IDhcpServer
        public void updateParams(android.net.dhcp.DhcpServingParamsParcel dhcpServingParamsParcel, android.net.INetworkStackStatusCallback iNetworkStackStatusCallback) throws android.os.RemoteException {
        }

        @Override // android.net.dhcp.IDhcpServer
        public void stop(android.net.INetworkStackStatusCallback iNetworkStackStatusCallback) throws android.os.RemoteException {
        }

        @Override // android.net.dhcp.IDhcpServer
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.dhcp.IDhcpServer
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.dhcp.IDhcpServer {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_startWithCallbacks = 4;
        static final int TRANSACTION_stop = 3;
        static final int TRANSACTION_updateParams = 2;

        public Stub() {
            attachInterface(this, android.net.dhcp.IDhcpServer.DESCRIPTOR);
        }

        public static android.net.dhcp.IDhcpServer asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.dhcp.IDhcpServer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.dhcp.IDhcpServer)) {
                return (android.net.dhcp.IDhcpServer) queryLocalInterface;
            }
            return new android.net.dhcp.IDhcpServer.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.net.dhcp.IDhcpServer.DESCRIPTOR;
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
                    start(android.net.INetworkStackStatusCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 2:
                    updateParams((android.net.dhcp.DhcpServingParamsParcel) parcel.readTypedObject(android.net.dhcp.DhcpServingParamsParcel.CREATOR), android.net.INetworkStackStatusCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 3:
                    stop(android.net.INetworkStackStatusCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 4:
                    startWithCallbacks(android.net.INetworkStackStatusCallback.Stub.asInterface(parcel.readStrongBinder()), android.net.dhcp.IDhcpEventCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.dhcp.IDhcpServer {
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
                return android.net.dhcp.IDhcpServer.DESCRIPTOR;
            }

            @Override // android.net.dhcp.IDhcpServer
            public void start(android.net.INetworkStackStatusCallback iNetworkStackStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.dhcp.IDhcpServer.DESCRIPTOR);
                    obtain.writeStrongInterface(iNetworkStackStatusCallback);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method start is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.dhcp.IDhcpServer
            public void startWithCallbacks(android.net.INetworkStackStatusCallback iNetworkStackStatusCallback, android.net.dhcp.IDhcpEventCallbacks iDhcpEventCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.dhcp.IDhcpServer.DESCRIPTOR);
                    obtain.writeStrongInterface(iNetworkStackStatusCallback);
                    obtain.writeStrongInterface(iDhcpEventCallbacks);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method startWithCallbacks is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.dhcp.IDhcpServer
            public void updateParams(android.net.dhcp.DhcpServingParamsParcel dhcpServingParamsParcel, android.net.INetworkStackStatusCallback iNetworkStackStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.dhcp.IDhcpServer.DESCRIPTOR);
                    obtain.writeTypedObject(dhcpServingParamsParcel, 0);
                    obtain.writeStrongInterface(iNetworkStackStatusCallback);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method updateParams is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.dhcp.IDhcpServer
            public void stop(android.net.INetworkStackStatusCallback iNetworkStackStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.dhcp.IDhcpServer.DESCRIPTOR);
                    obtain.writeStrongInterface(iNetworkStackStatusCallback);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stop is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.dhcp.IDhcpServer
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain();
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.net.dhcp.IDhcpServer.DESCRIPTOR);
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

            @Override // android.net.dhcp.IDhcpServer
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain();
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.net.dhcp.IDhcpServer.DESCRIPTOR);
                            this.mRemote.transact(android.net.dhcp.IDhcpServer.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
