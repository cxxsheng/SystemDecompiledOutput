package android.telephony.satellite;

/* loaded from: classes3.dex */
public interface ISatelliteProvisionStateCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.satellite.ISatelliteProvisionStateCallback";

    void onSatelliteProvisionStateChanged(boolean z) throws android.os.RemoteException;

    public static class Default implements android.telephony.satellite.ISatelliteProvisionStateCallback {
        @Override // android.telephony.satellite.ISatelliteProvisionStateCallback
        public void onSatelliteProvisionStateChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.satellite.ISatelliteProvisionStateCallback {
        static final int TRANSACTION_onSatelliteProvisionStateChanged = 1;

        public Stub() {
            attachInterface(this, android.telephony.satellite.ISatelliteProvisionStateCallback.DESCRIPTOR);
        }

        public static android.telephony.satellite.ISatelliteProvisionStateCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.satellite.ISatelliteProvisionStateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.satellite.ISatelliteProvisionStateCallback)) {
                return (android.telephony.satellite.ISatelliteProvisionStateCallback) queryLocalInterface;
            }
            return new android.telephony.satellite.ISatelliteProvisionStateCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSatelliteProvisionStateChanged";
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
                parcel.enforceInterface(android.telephony.satellite.ISatelliteProvisionStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.satellite.ISatelliteProvisionStateCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onSatelliteProvisionStateChanged(readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.satellite.ISatelliteProvisionStateCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.satellite.ISatelliteProvisionStateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteProvisionStateCallback
            public void onSatelliteProvisionStateChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.ISatelliteProvisionStateCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
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
