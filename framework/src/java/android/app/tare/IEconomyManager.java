package android.app.tare;

/* loaded from: classes.dex */
public interface IEconomyManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.tare.IEconomyManager";

    int getEnabledMode() throws android.os.RemoteException;

    public static class Default implements android.app.tare.IEconomyManager {
        @Override // android.app.tare.IEconomyManager
        public int getEnabledMode() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.tare.IEconomyManager {
        static final int TRANSACTION_getEnabledMode = 1;

        public Stub() {
            attachInterface(this, android.app.tare.IEconomyManager.DESCRIPTOR);
        }

        public static android.app.tare.IEconomyManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.tare.IEconomyManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.tare.IEconomyManager)) {
                return (android.app.tare.IEconomyManager) queryLocalInterface;
            }
            return new android.app.tare.IEconomyManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getEnabledMode";
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
                parcel.enforceInterface(android.app.tare.IEconomyManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.tare.IEconomyManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int enabledMode = getEnabledMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(enabledMode);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.tare.IEconomyManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.tare.IEconomyManager.DESCRIPTOR;
            }

            @Override // android.app.tare.IEconomyManager
            public int getEnabledMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.tare.IEconomyManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
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
