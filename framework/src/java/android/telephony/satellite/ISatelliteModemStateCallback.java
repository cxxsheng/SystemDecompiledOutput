package android.telephony.satellite;

/* loaded from: classes3.dex */
public interface ISatelliteModemStateCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.satellite.ISatelliteModemStateCallback";

    void onSatelliteModemStateChanged(int i) throws android.os.RemoteException;

    public static class Default implements android.telephony.satellite.ISatelliteModemStateCallback {
        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onSatelliteModemStateChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.satellite.ISatelliteModemStateCallback {
        static final int TRANSACTION_onSatelliteModemStateChanged = 1;

        public Stub() {
            attachInterface(this, android.telephony.satellite.ISatelliteModemStateCallback.DESCRIPTOR);
        }

        public static android.telephony.satellite.ISatelliteModemStateCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.satellite.ISatelliteModemStateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.satellite.ISatelliteModemStateCallback)) {
                return (android.telephony.satellite.ISatelliteModemStateCallback) queryLocalInterface;
            }
            return new android.telephony.satellite.ISatelliteModemStateCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSatelliteModemStateChanged";
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
                parcel.enforceInterface(android.telephony.satellite.ISatelliteModemStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.satellite.ISatelliteModemStateCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSatelliteModemStateChanged(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.satellite.ISatelliteModemStateCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.satellite.ISatelliteModemStateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteModemStateCallback
            public void onSatelliteModemStateChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.ISatelliteModemStateCallback.DESCRIPTOR);
                    obtain.writeInt(i);
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
