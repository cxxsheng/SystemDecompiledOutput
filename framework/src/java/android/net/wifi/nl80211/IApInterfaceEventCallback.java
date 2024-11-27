package android.net.wifi.nl80211;

/* loaded from: classes2.dex */
public interface IApInterfaceEventCallback extends android.os.IInterface {
    public static final int BANDWIDTH_160 = 6;
    public static final int BANDWIDTH_20 = 2;
    public static final int BANDWIDTH_20_NOHT = 1;
    public static final int BANDWIDTH_320 = 7;
    public static final int BANDWIDTH_40 = 3;
    public static final int BANDWIDTH_80 = 4;
    public static final int BANDWIDTH_80P80 = 5;
    public static final int BANDWIDTH_INVALID = 0;
    public static final java.lang.String DESCRIPTOR = "android.net.wifi.nl80211.IApInterfaceEventCallback";

    void onConnectedClientsChanged(android.net.wifi.nl80211.NativeWifiClient nativeWifiClient, boolean z) throws android.os.RemoteException;

    void onSoftApChannelSwitched(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.net.wifi.nl80211.IApInterfaceEventCallback {
        @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
        public void onConnectedClientsChanged(android.net.wifi.nl80211.NativeWifiClient nativeWifiClient, boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
        public void onSoftApChannelSwitched(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.wifi.nl80211.IApInterfaceEventCallback {
        static final int TRANSACTION_onConnectedClientsChanged = 1;
        static final int TRANSACTION_onSoftApChannelSwitched = 2;

        public Stub() {
            attachInterface(this, android.net.wifi.nl80211.IApInterfaceEventCallback.DESCRIPTOR);
        }

        public static android.net.wifi.nl80211.IApInterfaceEventCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.wifi.nl80211.IApInterfaceEventCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.wifi.nl80211.IApInterfaceEventCallback)) {
                return (android.net.wifi.nl80211.IApInterfaceEventCallback) queryLocalInterface;
            }
            return new android.net.wifi.nl80211.IApInterfaceEventCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onConnectedClientsChanged";
                case 2:
                    return "onSoftApChannelSwitched";
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
                parcel.enforceInterface(android.net.wifi.nl80211.IApInterfaceEventCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.wifi.nl80211.IApInterfaceEventCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.net.wifi.nl80211.NativeWifiClient nativeWifiClient = (android.net.wifi.nl80211.NativeWifiClient) parcel.readTypedObject(android.net.wifi.nl80211.NativeWifiClient.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConnectedClientsChanged(nativeWifiClient, readBoolean);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSoftApChannelSwitched(readInt, readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.wifi.nl80211.IApInterfaceEventCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.wifi.nl80211.IApInterfaceEventCallback.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
            public void onConnectedClientsChanged(android.net.wifi.nl80211.NativeWifiClient nativeWifiClient, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IApInterfaceEventCallback.DESCRIPTOR);
                    obtain.writeTypedObject(nativeWifiClient, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
            public void onSoftApChannelSwitched(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IApInterfaceEventCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
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
