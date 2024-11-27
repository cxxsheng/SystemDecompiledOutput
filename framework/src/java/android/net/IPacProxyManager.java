package android.net;

/* loaded from: classes2.dex */
public interface IPacProxyManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.net.IPacProxyManager";

    void addListener(android.net.IPacProxyInstalledListener iPacProxyInstalledListener) throws android.os.RemoteException;

    void removeListener(android.net.IPacProxyInstalledListener iPacProxyInstalledListener) throws android.os.RemoteException;

    void setCurrentProxyScriptUrl(android.net.ProxyInfo proxyInfo) throws android.os.RemoteException;

    public static class Default implements android.net.IPacProxyManager {
        @Override // android.net.IPacProxyManager
        public void addListener(android.net.IPacProxyInstalledListener iPacProxyInstalledListener) throws android.os.RemoteException {
        }

        @Override // android.net.IPacProxyManager
        public void removeListener(android.net.IPacProxyInstalledListener iPacProxyInstalledListener) throws android.os.RemoteException {
        }

        @Override // android.net.IPacProxyManager
        public void setCurrentProxyScriptUrl(android.net.ProxyInfo proxyInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.IPacProxyManager {
        static final int TRANSACTION_addListener = 1;
        static final int TRANSACTION_removeListener = 2;
        static final int TRANSACTION_setCurrentProxyScriptUrl = 3;

        public Stub() {
            attachInterface(this, android.net.IPacProxyManager.DESCRIPTOR);
        }

        public static android.net.IPacProxyManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.IPacProxyManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.IPacProxyManager)) {
                return (android.net.IPacProxyManager) queryLocalInterface;
            }
            return new android.net.IPacProxyManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addListener";
                case 2:
                    return "removeListener";
                case 3:
                    return "setCurrentProxyScriptUrl";
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
                parcel.enforceInterface(android.net.IPacProxyManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.IPacProxyManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.net.IPacProxyInstalledListener asInterface = android.net.IPacProxyInstalledListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.net.IPacProxyInstalledListener asInterface2 = android.net.IPacProxyInstalledListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.net.ProxyInfo proxyInfo = (android.net.ProxyInfo) parcel.readTypedObject(android.net.ProxyInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCurrentProxyScriptUrl(proxyInfo);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.IPacProxyManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.IPacProxyManager.DESCRIPTOR;
            }

            @Override // android.net.IPacProxyManager
            public void addListener(android.net.IPacProxyInstalledListener iPacProxyInstalledListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IPacProxyManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iPacProxyInstalledListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IPacProxyManager
            public void removeListener(android.net.IPacProxyInstalledListener iPacProxyInstalledListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IPacProxyManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iPacProxyInstalledListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IPacProxyManager
            public void setCurrentProxyScriptUrl(android.net.ProxyInfo proxyInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IPacProxyManager.DESCRIPTOR);
                    obtain.writeTypedObject(proxyInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
