package android.security.metrics;

/* loaded from: classes3.dex */
public interface IKeystoreMetrics extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.metrics.IKeystoreMetrics";

    android.security.metrics.KeystoreAtom[] pullMetrics(int i) throws android.os.RemoteException;

    public static class Default implements android.security.metrics.IKeystoreMetrics {
        @Override // android.security.metrics.IKeystoreMetrics
        public android.security.metrics.KeystoreAtom[] pullMetrics(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.metrics.IKeystoreMetrics {
        static final int TRANSACTION_pullMetrics = 1;

        public Stub() {
            attachInterface(this, android.security.metrics.IKeystoreMetrics.DESCRIPTOR);
        }

        public static android.security.metrics.IKeystoreMetrics asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.metrics.IKeystoreMetrics.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.metrics.IKeystoreMetrics)) {
                return (android.security.metrics.IKeystoreMetrics) queryLocalInterface;
            }
            return new android.security.metrics.IKeystoreMetrics.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "pullMetrics";
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
                parcel.enforceInterface(android.security.metrics.IKeystoreMetrics.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.metrics.IKeystoreMetrics.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.security.metrics.KeystoreAtom[] pullMetrics = pullMetrics(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(pullMetrics, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.metrics.IKeystoreMetrics {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.metrics.IKeystoreMetrics.DESCRIPTOR;
            }

            @Override // android.security.metrics.IKeystoreMetrics
            public android.security.metrics.KeystoreAtom[] pullMetrics(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.metrics.IKeystoreMetrics.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.security.metrics.KeystoreAtom[]) obtain2.createTypedArray(android.security.metrics.KeystoreAtom.CREATOR);
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
