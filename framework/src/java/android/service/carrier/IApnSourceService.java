package android.service.carrier;

/* loaded from: classes3.dex */
public interface IApnSourceService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.carrier.IApnSourceService";

    android.content.ContentValues[] getApns(int i) throws android.os.RemoteException;

    public static class Default implements android.service.carrier.IApnSourceService {
        @Override // android.service.carrier.IApnSourceService
        public android.content.ContentValues[] getApns(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.carrier.IApnSourceService {
        static final int TRANSACTION_getApns = 1;

        public Stub() {
            attachInterface(this, android.service.carrier.IApnSourceService.DESCRIPTOR);
        }

        public static android.service.carrier.IApnSourceService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.carrier.IApnSourceService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.carrier.IApnSourceService)) {
                return (android.service.carrier.IApnSourceService) queryLocalInterface;
            }
            return new android.service.carrier.IApnSourceService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getApns";
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
                parcel.enforceInterface(android.service.carrier.IApnSourceService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.carrier.IApnSourceService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.ContentValues[] apns = getApns(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(apns, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.carrier.IApnSourceService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.carrier.IApnSourceService.DESCRIPTOR;
            }

            @Override // android.service.carrier.IApnSourceService
            public android.content.ContentValues[] getApns(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.carrier.IApnSourceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ContentValues[]) obtain2.createTypedArray(android.content.ContentValues.CREATOR);
                } finally {
                    obtain2.recycle();
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
