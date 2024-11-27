package android.telephony.satellite;

/* loaded from: classes3.dex */
public interface INtnSignalStrengthCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.satellite.INtnSignalStrengthCallback";

    void onNtnSignalStrengthChanged(android.telephony.satellite.NtnSignalStrength ntnSignalStrength) throws android.os.RemoteException;

    public static class Default implements android.telephony.satellite.INtnSignalStrengthCallback {
        @Override // android.telephony.satellite.INtnSignalStrengthCallback
        public void onNtnSignalStrengthChanged(android.telephony.satellite.NtnSignalStrength ntnSignalStrength) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.satellite.INtnSignalStrengthCallback {
        static final int TRANSACTION_onNtnSignalStrengthChanged = 1;

        public Stub() {
            attachInterface(this, android.telephony.satellite.INtnSignalStrengthCallback.DESCRIPTOR);
        }

        public static android.telephony.satellite.INtnSignalStrengthCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.satellite.INtnSignalStrengthCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.satellite.INtnSignalStrengthCallback)) {
                return (android.telephony.satellite.INtnSignalStrengthCallback) queryLocalInterface;
            }
            return new android.telephony.satellite.INtnSignalStrengthCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onNtnSignalStrengthChanged";
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
                parcel.enforceInterface(android.telephony.satellite.INtnSignalStrengthCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.satellite.INtnSignalStrengthCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.satellite.NtnSignalStrength ntnSignalStrength = (android.telephony.satellite.NtnSignalStrength) parcel.readTypedObject(android.telephony.satellite.NtnSignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNtnSignalStrengthChanged(ntnSignalStrength);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.satellite.INtnSignalStrengthCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.satellite.INtnSignalStrengthCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.INtnSignalStrengthCallback
            public void onNtnSignalStrengthChanged(android.telephony.satellite.NtnSignalStrength ntnSignalStrength) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.INtnSignalStrengthCallback.DESCRIPTOR);
                    obtain.writeTypedObject(ntnSignalStrength, 0);
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
