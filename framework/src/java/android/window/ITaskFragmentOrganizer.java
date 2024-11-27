package android.window;

/* loaded from: classes4.dex */
public interface ITaskFragmentOrganizer extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.ITaskFragmentOrganizer";

    void onTransactionReady(android.window.TaskFragmentTransaction taskFragmentTransaction) throws android.os.RemoteException;

    public static class Default implements android.window.ITaskFragmentOrganizer {
        @Override // android.window.ITaskFragmentOrganizer
        public void onTransactionReady(android.window.TaskFragmentTransaction taskFragmentTransaction) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.ITaskFragmentOrganizer {
        static final int TRANSACTION_onTransactionReady = 1;

        public Stub() {
            attachInterface(this, android.window.ITaskFragmentOrganizer.DESCRIPTOR);
        }

        public static android.window.ITaskFragmentOrganizer asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.ITaskFragmentOrganizer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.ITaskFragmentOrganizer)) {
                return (android.window.ITaskFragmentOrganizer) queryLocalInterface;
            }
            return new android.window.ITaskFragmentOrganizer.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTransactionReady";
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
                parcel.enforceInterface(android.window.ITaskFragmentOrganizer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.ITaskFragmentOrganizer.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.window.TaskFragmentTransaction taskFragmentTransaction = (android.window.TaskFragmentTransaction) parcel.readTypedObject(android.window.TaskFragmentTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTransactionReady(taskFragmentTransaction);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.ITaskFragmentOrganizer {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.ITaskFragmentOrganizer.DESCRIPTOR;
            }

            @Override // android.window.ITaskFragmentOrganizer
            public void onTransactionReady(android.window.TaskFragmentTransaction taskFragmentTransaction) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ITaskFragmentOrganizer.DESCRIPTOR);
                    obtain.writeTypedObject(taskFragmentTransaction, 0);
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
