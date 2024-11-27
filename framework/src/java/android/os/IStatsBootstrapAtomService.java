package android.os;

/* loaded from: classes3.dex */
public interface IStatsBootstrapAtomService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IStatsBootstrapAtomService";

    void reportBootstrapAtom(android.os.StatsBootstrapAtom statsBootstrapAtom) throws android.os.RemoteException;

    public static class Default implements android.os.IStatsBootstrapAtomService {
        @Override // android.os.IStatsBootstrapAtomService
        public void reportBootstrapAtom(android.os.StatsBootstrapAtom statsBootstrapAtom) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IStatsBootstrapAtomService {
        static final int TRANSACTION_reportBootstrapAtom = 1;

        public Stub() {
            attachInterface(this, android.os.IStatsBootstrapAtomService.DESCRIPTOR);
        }

        public static android.os.IStatsBootstrapAtomService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IStatsBootstrapAtomService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IStatsBootstrapAtomService)) {
                return (android.os.IStatsBootstrapAtomService) queryLocalInterface;
            }
            return new android.os.IStatsBootstrapAtomService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "reportBootstrapAtom";
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
                parcel.enforceInterface(android.os.IStatsBootstrapAtomService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IStatsBootstrapAtomService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.StatsBootstrapAtom statsBootstrapAtom = (android.os.StatsBootstrapAtom) parcel.readTypedObject(android.os.StatsBootstrapAtom.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportBootstrapAtom(statsBootstrapAtom);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IStatsBootstrapAtomService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IStatsBootstrapAtomService.DESCRIPTOR;
            }

            @Override // android.os.IStatsBootstrapAtomService
            public void reportBootstrapAtom(android.os.StatsBootstrapAtom statsBootstrapAtom) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IStatsBootstrapAtomService.DESCRIPTOR);
                    obtain.writeTypedObject(statsBootstrapAtom, 0);
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
