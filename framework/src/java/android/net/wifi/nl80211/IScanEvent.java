package android.net.wifi.nl80211;

/* loaded from: classes2.dex */
public interface IScanEvent extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.net.wifi.nl80211.IScanEvent";

    void OnScanFailed() throws android.os.RemoteException;

    void OnScanRequestFailed(int i) throws android.os.RemoteException;

    void OnScanResultReady() throws android.os.RemoteException;

    public static class Default implements android.net.wifi.nl80211.IScanEvent {
        @Override // android.net.wifi.nl80211.IScanEvent
        public void OnScanResultReady() throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IScanEvent
        public void OnScanFailed() throws android.os.RemoteException {
        }

        @Override // android.net.wifi.nl80211.IScanEvent
        public void OnScanRequestFailed(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.wifi.nl80211.IScanEvent {
        static final int TRANSACTION_OnScanFailed = 2;
        static final int TRANSACTION_OnScanRequestFailed = 3;
        static final int TRANSACTION_OnScanResultReady = 1;

        public Stub() {
            attachInterface(this, android.net.wifi.nl80211.IScanEvent.DESCRIPTOR);
        }

        public static android.net.wifi.nl80211.IScanEvent asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.wifi.nl80211.IScanEvent.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.wifi.nl80211.IScanEvent)) {
                return (android.net.wifi.nl80211.IScanEvent) queryLocalInterface;
            }
            return new android.net.wifi.nl80211.IScanEvent.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "OnScanResultReady";
                case 2:
                    return "OnScanFailed";
                case 3:
                    return "OnScanRequestFailed";
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
                parcel.enforceInterface(android.net.wifi.nl80211.IScanEvent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.wifi.nl80211.IScanEvent.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    OnScanResultReady();
                    return true;
                case 2:
                    OnScanFailed();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    OnScanRequestFailed(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.wifi.nl80211.IScanEvent {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.wifi.nl80211.IScanEvent.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IScanEvent
            public void OnScanResultReady() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IScanEvent.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IScanEvent
            public void OnScanFailed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IScanEvent.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IScanEvent
            public void OnScanRequestFailed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.wifi.nl80211.IScanEvent.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
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
