package android.net;

/* loaded from: classes2.dex */
public interface IIpConnectivityMetrics extends android.os.IInterface {
    boolean addNetdEventCallback(int i, android.net.INetdEventCallback iNetdEventCallback) throws android.os.RemoteException;

    void logDefaultNetworkEvent(android.net.Network network, int i, boolean z, android.net.LinkProperties linkProperties, android.net.NetworkCapabilities networkCapabilities, android.net.Network network2, int i2, android.net.LinkProperties linkProperties2, android.net.NetworkCapabilities networkCapabilities2) throws android.os.RemoteException;

    void logDefaultNetworkValidity(boolean z) throws android.os.RemoteException;

    int logEvent(android.net.ConnectivityMetricsEvent connectivityMetricsEvent) throws android.os.RemoteException;

    boolean removeNetdEventCallback(int i) throws android.os.RemoteException;

    public static class Default implements android.net.IIpConnectivityMetrics {
        @Override // android.net.IIpConnectivityMetrics
        public int logEvent(android.net.ConnectivityMetricsEvent connectivityMetricsEvent) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.net.IIpConnectivityMetrics
        public void logDefaultNetworkValidity(boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.IIpConnectivityMetrics
        public void logDefaultNetworkEvent(android.net.Network network, int i, boolean z, android.net.LinkProperties linkProperties, android.net.NetworkCapabilities networkCapabilities, android.net.Network network2, int i2, android.net.LinkProperties linkProperties2, android.net.NetworkCapabilities networkCapabilities2) throws android.os.RemoteException {
        }

        @Override // android.net.IIpConnectivityMetrics
        public boolean addNetdEventCallback(int i, android.net.INetdEventCallback iNetdEventCallback) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.IIpConnectivityMetrics
        public boolean removeNetdEventCallback(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.IIpConnectivityMetrics {
        public static final java.lang.String DESCRIPTOR = "android.net.IIpConnectivityMetrics";
        static final int TRANSACTION_addNetdEventCallback = 4;
        static final int TRANSACTION_logDefaultNetworkEvent = 3;
        static final int TRANSACTION_logDefaultNetworkValidity = 2;
        static final int TRANSACTION_logEvent = 1;
        static final int TRANSACTION_removeNetdEventCallback = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.net.IIpConnectivityMetrics asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.IIpConnectivityMetrics)) {
                return (android.net.IIpConnectivityMetrics) queryLocalInterface;
            }
            return new android.net.IIpConnectivityMetrics.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "logEvent";
                case 2:
                    return "logDefaultNetworkValidity";
                case 3:
                    return "logDefaultNetworkEvent";
                case 4:
                    return "addNetdEventCallback";
                case 5:
                    return "removeNetdEventCallback";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.net.ConnectivityMetricsEvent connectivityMetricsEvent = (android.net.ConnectivityMetricsEvent) parcel.readTypedObject(android.net.ConnectivityMetricsEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    int logEvent = logEvent(connectivityMetricsEvent);
                    parcel2.writeNoException();
                    parcel2.writeInt(logEvent);
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    logDefaultNetworkValidity(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.net.Network network = (android.net.Network) parcel.readTypedObject(android.net.Network.CREATOR);
                    int readInt = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    android.net.LinkProperties linkProperties = (android.net.LinkProperties) parcel.readTypedObject(android.net.LinkProperties.CREATOR);
                    android.net.NetworkCapabilities networkCapabilities = (android.net.NetworkCapabilities) parcel.readTypedObject(android.net.NetworkCapabilities.CREATOR);
                    android.net.Network network2 = (android.net.Network) parcel.readTypedObject(android.net.Network.CREATOR);
                    int readInt2 = parcel.readInt();
                    android.net.LinkProperties linkProperties2 = (android.net.LinkProperties) parcel.readTypedObject(android.net.LinkProperties.CREATOR);
                    android.net.NetworkCapabilities networkCapabilities2 = (android.net.NetworkCapabilities) parcel.readTypedObject(android.net.NetworkCapabilities.CREATOR);
                    parcel.enforceNoDataAvail();
                    logDefaultNetworkEvent(network, readInt, readBoolean2, linkProperties, networkCapabilities, network2, readInt2, linkProperties2, networkCapabilities2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    android.net.INetdEventCallback asInterface = android.net.INetdEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean addNetdEventCallback = addNetdEventCallback(readInt3, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addNetdEventCallback);
                    return true;
                case 5:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeNetdEventCallback = removeNetdEventCallback(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeNetdEventCallback);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.IIpConnectivityMetrics {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.IIpConnectivityMetrics.Stub.DESCRIPTOR;
            }

            @Override // android.net.IIpConnectivityMetrics
            public int logEvent(android.net.ConnectivityMetricsEvent connectivityMetricsEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IIpConnectivityMetrics.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(connectivityMetricsEvent, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpConnectivityMetrics
            public void logDefaultNetworkValidity(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IIpConnectivityMetrics.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpConnectivityMetrics
            public void logDefaultNetworkEvent(android.net.Network network, int i, boolean z, android.net.LinkProperties linkProperties, android.net.NetworkCapabilities networkCapabilities, android.net.Network network2, int i2, android.net.LinkProperties linkProperties2, android.net.NetworkCapabilities networkCapabilities2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IIpConnectivityMetrics.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(network, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(linkProperties, 0);
                    obtain.writeTypedObject(networkCapabilities, 0);
                    obtain.writeTypedObject(network2, 0);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(linkProperties2, 0);
                    obtain.writeTypedObject(networkCapabilities2, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpConnectivityMetrics
            public boolean addNetdEventCallback(int i, android.net.INetdEventCallback iNetdEventCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IIpConnectivityMetrics.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iNetdEventCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpConnectivityMetrics
            public boolean removeNetdEventCallback(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IIpConnectivityMetrics.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
