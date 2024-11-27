package android.net.wifi.nl80211;

/* loaded from: classes2.dex */
public interface IApInterface extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.net.wifi.nl80211.IApInterface";
    public static final int ENCRYPTION_TYPE_NONE = 0;
    public static final int ENCRYPTION_TYPE_WPA = 1;
    public static final int ENCRYPTION_TYPE_WPA2 = 2;

    java.lang.String getInterfaceName() throws android.os.RemoteException;

    boolean registerCallback(android.net.wifi.nl80211.IApInterfaceEventCallback iApInterfaceEventCallback) throws android.os.RemoteException;

    public static class Default implements android.net.wifi.nl80211.IApInterface {
        @Override // android.net.wifi.nl80211.IApInterface
        public boolean registerCallback(android.net.wifi.nl80211.IApInterfaceEventCallback iApInterfaceEventCallback) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.wifi.nl80211.IApInterface
        public java.lang.String getInterfaceName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.wifi.nl80211.IApInterface {
        static final int TRANSACTION_getInterfaceName = 2;
        static final int TRANSACTION_registerCallback = 1;

        public Stub() {
            attachInterface(this, android.net.wifi.nl80211.IApInterface.DESCRIPTOR);
        }

        public static android.net.wifi.nl80211.IApInterface asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.wifi.nl80211.IApInterface.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.wifi.nl80211.IApInterface)) {
                return (android.net.wifi.nl80211.IApInterface) queryLocalInterface;
            }
            return new android.net.wifi.nl80211.IApInterface.Stub.Proxy(iBinder);
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
                    return "getInterfaceName";
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
                parcel.enforceInterface(android.net.wifi.nl80211.IApInterface.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.wifi.nl80211.IApInterface.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.net.wifi.nl80211.IApInterfaceEventCallback asInterface = android.net.wifi.nl80211.IApInterfaceEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerCallback = registerCallback(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerCallback);
                    return true;
                case 2:
                    java.lang.String interfaceName = getInterfaceName();
                    parcel2.writeNoException();
                    parcel2.writeString(interfaceName);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.wifi.nl80211.IApInterface {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.wifi.nl80211.IApInterface.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IApInterface
            public boolean registerCallback(android.net.wifi.nl80211.IApInterfaceEventCallback iApInterfaceEventCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IApInterface.DESCRIPTOR);
                    obtain.writeStrongInterface(iApInterfaceEventCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IApInterface
            public java.lang.String getInterfaceName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IApInterface.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
