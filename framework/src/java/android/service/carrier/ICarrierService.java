package android.service.carrier;

/* loaded from: classes3.dex */
public interface ICarrierService extends android.os.IInterface {
    void getCarrierConfig(int i, android.service.carrier.CarrierIdentifier carrierIdentifier, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    public static class Default implements android.service.carrier.ICarrierService {
        @Override // android.service.carrier.ICarrierService
        public void getCarrierConfig(int i, android.service.carrier.CarrierIdentifier carrierIdentifier, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.carrier.ICarrierService {
        public static final java.lang.String DESCRIPTOR = "android.service.carrier.ICarrierService";
        static final int TRANSACTION_getCarrierConfig = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.carrier.ICarrierService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.carrier.ICarrierService)) {
                return (android.service.carrier.ICarrierService) queryLocalInterface;
            }
            return new android.service.carrier.ICarrierService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getCarrierConfig";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.service.carrier.CarrierIdentifier carrierIdentifier = (android.service.carrier.CarrierIdentifier) parcel.readTypedObject(android.service.carrier.CarrierIdentifier.CREATOR);
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCarrierConfig(readInt, carrierIdentifier, resultReceiver);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.carrier.ICarrierService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.carrier.ICarrierService.Stub.DESCRIPTOR;
            }

            @Override // android.service.carrier.ICarrierService
            public void getCarrierConfig(int i, android.service.carrier.CarrierIdentifier carrierIdentifier, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.carrier.ICarrierService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(carrierIdentifier, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
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
