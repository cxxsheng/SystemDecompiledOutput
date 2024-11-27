package android.net;

/* loaded from: classes2.dex */
public interface IPacProxyInstalledListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.net.IPacProxyInstalledListener";

    void onPacProxyInstalled(android.net.Network network, android.net.ProxyInfo proxyInfo) throws android.os.RemoteException;

    public static class Default implements android.net.IPacProxyInstalledListener {
        @Override // android.net.IPacProxyInstalledListener
        public void onPacProxyInstalled(android.net.Network network, android.net.ProxyInfo proxyInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.IPacProxyInstalledListener {
        static final int TRANSACTION_onPacProxyInstalled = 1;

        public Stub() {
            attachInterface(this, android.net.IPacProxyInstalledListener.DESCRIPTOR);
        }

        public static android.net.IPacProxyInstalledListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.IPacProxyInstalledListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.IPacProxyInstalledListener)) {
                return (android.net.IPacProxyInstalledListener) queryLocalInterface;
            }
            return new android.net.IPacProxyInstalledListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onPacProxyInstalled";
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
                parcel.enforceInterface(android.net.IPacProxyInstalledListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.IPacProxyInstalledListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.net.Network network = (android.net.Network) parcel.readTypedObject(android.net.Network.CREATOR);
                    android.net.ProxyInfo proxyInfo = (android.net.ProxyInfo) parcel.readTypedObject(android.net.ProxyInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPacProxyInstalled(network, proxyInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.IPacProxyInstalledListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.IPacProxyInstalledListener.DESCRIPTOR;
            }

            @Override // android.net.IPacProxyInstalledListener
            public void onPacProxyInstalled(android.net.Network network, android.net.ProxyInfo proxyInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.IPacProxyInstalledListener.DESCRIPTOR);
                    obtain.writeTypedObject(network, 0);
                    obtain.writeTypedObject(proxyInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
