package android.content;

/* loaded from: classes.dex */
public interface ISyncAdapter extends android.os.IInterface {
    void cancelSync(android.content.ISyncContext iSyncContext) throws android.os.RemoteException;

    void onUnsyncableAccount(android.content.ISyncAdapterUnsyncableAccountCallback iSyncAdapterUnsyncableAccountCallback) throws android.os.RemoteException;

    void startSync(android.content.ISyncContext iSyncContext, java.lang.String str, android.accounts.Account account, android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.content.ISyncAdapter {
        @Override // android.content.ISyncAdapter
        public void onUnsyncableAccount(android.content.ISyncAdapterUnsyncableAccountCallback iSyncAdapterUnsyncableAccountCallback) throws android.os.RemoteException {
        }

        @Override // android.content.ISyncAdapter
        public void startSync(android.content.ISyncContext iSyncContext, java.lang.String str, android.accounts.Account account, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.content.ISyncAdapter
        public void cancelSync(android.content.ISyncContext iSyncContext) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.ISyncAdapter {
        public static final java.lang.String DESCRIPTOR = "android.content.ISyncAdapter";
        static final int TRANSACTION_cancelSync = 3;
        static final int TRANSACTION_onUnsyncableAccount = 1;
        static final int TRANSACTION_startSync = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.content.ISyncAdapter asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.ISyncAdapter)) {
                return (android.content.ISyncAdapter) queryLocalInterface;
            }
            return new android.content.ISyncAdapter.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUnsyncableAccount";
                case 2:
                    return "startSync";
                case 3:
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
                    android.content.ISyncAdapterUnsyncableAccountCallback asInterface = android.content.ISyncAdapterUnsyncableAccountCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onUnsyncableAccount(asInterface);
                    return true;
                case 2:
                    android.content.ISyncContext asInterface2 = android.content.ISyncContext.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    android.accounts.Account account = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startSync(asInterface2, readString, account, bundle);
                    return true;
                case 3:
                    android.content.ISyncContext asInterface3 = android.content.ISyncContext.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelSync(asInterface3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.ISyncAdapter {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.ISyncAdapter.Stub.DESCRIPTOR;
            }

            @Override // android.content.ISyncAdapter
            public void onUnsyncableAccount(android.content.ISyncAdapterUnsyncableAccountCallback iSyncAdapterUnsyncableAccountCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.ISyncAdapter.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSyncAdapterUnsyncableAccountCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.ISyncAdapter
            public void startSync(android.content.ISyncContext iSyncContext, java.lang.String str, android.accounts.Account account, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.ISyncAdapter.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSyncContext);
                    obtain.writeString(str);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.ISyncAdapter
            public void cancelSync(android.content.ISyncContext iSyncContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.ISyncAdapter.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSyncContext);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
