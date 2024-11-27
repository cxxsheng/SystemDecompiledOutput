package android.telephony.satellite.stub;

/* loaded from: classes3.dex */
public interface INtnSignalStrengthConsumer extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.satellite.stub.INtnSignalStrengthConsumer";

    void accept(android.telephony.satellite.stub.NtnSignalStrength ntnSignalStrength) throws android.os.RemoteException;

    public static class Default implements android.telephony.satellite.stub.INtnSignalStrengthConsumer {
        @Override // android.telephony.satellite.stub.INtnSignalStrengthConsumer
        public void accept(android.telephony.satellite.stub.NtnSignalStrength ntnSignalStrength) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.satellite.stub.INtnSignalStrengthConsumer {
        static final int TRANSACTION_accept = 1;

        public Stub() {
            attachInterface(this, android.telephony.satellite.stub.INtnSignalStrengthConsumer.DESCRIPTOR);
        }

        public static android.telephony.satellite.stub.INtnSignalStrengthConsumer asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.satellite.stub.INtnSignalStrengthConsumer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.satellite.stub.INtnSignalStrengthConsumer)) {
                return (android.telephony.satellite.stub.INtnSignalStrengthConsumer) queryLocalInterface;
            }
            return new android.telephony.satellite.stub.INtnSignalStrengthConsumer.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "accept";
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
                parcel.enforceInterface(android.telephony.satellite.stub.INtnSignalStrengthConsumer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.satellite.stub.INtnSignalStrengthConsumer.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.satellite.stub.NtnSignalStrength ntnSignalStrength = (android.telephony.satellite.stub.NtnSignalStrength) parcel.readTypedObject(android.telephony.satellite.stub.NtnSignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    accept(ntnSignalStrength);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.satellite.stub.INtnSignalStrengthConsumer {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.satellite.stub.INtnSignalStrengthConsumer.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.stub.INtnSignalStrengthConsumer
            public void accept(android.telephony.satellite.stub.NtnSignalStrength ntnSignalStrength) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.INtnSignalStrengthConsumer.DESCRIPTOR);
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
