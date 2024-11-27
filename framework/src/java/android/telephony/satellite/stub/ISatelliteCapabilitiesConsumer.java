package android.telephony.satellite.stub;

/* loaded from: classes3.dex */
public interface ISatelliteCapabilitiesConsumer extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer";

    void accept(android.telephony.satellite.stub.SatelliteCapabilities satelliteCapabilities) throws android.os.RemoteException;

    public static class Default implements android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer {
        @Override // android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer
        public void accept(android.telephony.satellite.stub.SatelliteCapabilities satelliteCapabilities) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer {
        static final int TRANSACTION_accept = 1;

        public Stub() {
            attachInterface(this, android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer.DESCRIPTOR);
        }

        public static android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer)) {
                return (android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer) queryLocalInterface;
            }
            return new android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer.Stub.Proxy(iBinder);
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
                parcel.enforceInterface(android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.satellite.stub.SatelliteCapabilities satelliteCapabilities = (android.telephony.satellite.stub.SatelliteCapabilities) parcel.readTypedObject(android.telephony.satellite.stub.SatelliteCapabilities.CREATOR);
                    parcel.enforceNoDataAvail();
                    accept(satelliteCapabilities);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer
            public void accept(android.telephony.satellite.stub.SatelliteCapabilities satelliteCapabilities) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer.DESCRIPTOR);
                    obtain.writeTypedObject(satelliteCapabilities, 0);
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
