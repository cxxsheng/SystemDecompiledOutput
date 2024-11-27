package android.net.wifi.nl80211;

/* loaded from: classes2.dex */
public interface IInterfaceEventCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.net.wifi.nl80211.IInterfaceEventCallback";

    void OnApInterfaceReady(android.net.wifi.nl80211.IApInterface iApInterface) throws android.os.RemoteException;

    void OnApTorndownEvent(android.net.wifi.nl80211.IApInterface iApInterface) throws android.os.RemoteException;

    void OnClientInterfaceReady(android.net.wifi.nl80211.IClientInterface iClientInterface) throws android.os.RemoteException;

    void OnClientTorndownEvent(android.net.wifi.nl80211.IClientInterface iClientInterface) throws android.os.RemoteException;

    public static class Default implements android.net.wifi.nl80211.IInterfaceEventCallback {
        @Override // android.net.wifi.nl80211.IInterfaceEventCallback
        public void OnClientInterfaceReady(android.net.wifi.nl80211.IClientInterface iClientInterface) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IInterfaceEventCallback
        public void OnApInterfaceReady(android.net.wifi.nl80211.IApInterface iApInterface) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IInterfaceEventCallback
        public void OnClientTorndownEvent(android.net.wifi.nl80211.IClientInterface iClientInterface) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IInterfaceEventCallback
        public void OnApTorndownEvent(android.net.wifi.nl80211.IApInterface iApInterface) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.wifi.nl80211.IInterfaceEventCallback {
        static final int TRANSACTION_OnApInterfaceReady = 2;
        static final int TRANSACTION_OnApTorndownEvent = 4;
        static final int TRANSACTION_OnClientInterfaceReady = 1;
        static final int TRANSACTION_OnClientTorndownEvent = 3;

        public Stub() {
            attachInterface(this, android.net.wifi.nl80211.IInterfaceEventCallback.DESCRIPTOR);
        }

        public static android.net.wifi.nl80211.IInterfaceEventCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.wifi.nl80211.IInterfaceEventCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.wifi.nl80211.IInterfaceEventCallback)) {
                return (android.net.wifi.nl80211.IInterfaceEventCallback) queryLocalInterface;
            }
            return new android.net.wifi.nl80211.IInterfaceEventCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "OnClientInterfaceReady";
                case 2:
                    return "OnApInterfaceReady";
                case 3:
                    return "OnClientTorndownEvent";
                case 4:
                    return "OnApTorndownEvent";
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
                parcel.enforceInterface(android.net.wifi.nl80211.IInterfaceEventCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.wifi.nl80211.IInterfaceEventCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.net.wifi.nl80211.IClientInterface asInterface = android.net.wifi.nl80211.IClientInterface.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    OnClientInterfaceReady(asInterface);
                    return true;
                case 2:
                    android.net.wifi.nl80211.IApInterface asInterface2 = android.net.wifi.nl80211.IApInterface.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    OnApInterfaceReady(asInterface2);
                    return true;
                case 3:
                    android.net.wifi.nl80211.IClientInterface asInterface3 = android.net.wifi.nl80211.IClientInterface.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    OnClientTorndownEvent(asInterface3);
                    return true;
                case 4:
                    android.net.wifi.nl80211.IApInterface asInterface4 = android.net.wifi.nl80211.IApInterface.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    OnApTorndownEvent(asInterface4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.wifi.nl80211.IInterfaceEventCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.wifi.nl80211.IInterfaceEventCallback.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IInterfaceEventCallback
            public void OnClientInterfaceReady(android.net.wifi.nl80211.IClientInterface iClientInterface) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IInterfaceEventCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iClientInterface);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IInterfaceEventCallback
            public void OnApInterfaceReady(android.net.wifi.nl80211.IApInterface iApInterface) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IInterfaceEventCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iApInterface);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IInterfaceEventCallback
            public void OnClientTorndownEvent(android.net.wifi.nl80211.IClientInterface iClientInterface) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IInterfaceEventCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iClientInterface);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IInterfaceEventCallback
            public void OnApTorndownEvent(android.net.wifi.nl80211.IApInterface iApInterface) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IInterfaceEventCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iApInterface);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
