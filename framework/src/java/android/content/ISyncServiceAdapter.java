package android.content;

/* loaded from: classes.dex */
public interface ISyncServiceAdapter extends android.os.IInterface {
    void cancelSync(android.content.ISyncContext iSyncContext) throws android.os.RemoteException;

    void startSync(android.content.ISyncContext iSyncContext, android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.content.ISyncServiceAdapter {
        @Override // android.content.ISyncServiceAdapter
        public void startSync(android.content.ISyncContext iSyncContext, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.content.ISyncServiceAdapter
        public void cancelSync(android.content.ISyncContext iSyncContext) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.ISyncServiceAdapter {
        public static final java.lang.String DESCRIPTOR = "android.content.ISyncServiceAdapter";
        static final int TRANSACTION_cancelSync = 2;
        static final int TRANSACTION_startSync = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.content.ISyncServiceAdapter asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.ISyncServiceAdapter)) {
                return (android.content.ISyncServiceAdapter) queryLocalInterface;
            }
            return new android.content.ISyncServiceAdapter.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startSync";
                case 2:
                    return "cancelSync";
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
                    android.content.ISyncContext asInterface = android.content.ISyncContext.Stub.asInterface(parcel.readStrongBinder());
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startSync(asInterface, bundle);
                    return true;
                case 2:
                    android.content.ISyncContext asInterface2 = android.content.ISyncContext.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelSync(asInterface2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.ISyncServiceAdapter {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.ISyncServiceAdapter.Stub.DESCRIPTOR;
            }

            @Override // android.content.ISyncServiceAdapter
            public void startSync(android.content.ISyncContext iSyncContext, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.ISyncServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSyncContext);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.ISyncServiceAdapter
            public void cancelSync(android.content.ISyncContext iSyncContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.ISyncServiceAdapter.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSyncContext);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
