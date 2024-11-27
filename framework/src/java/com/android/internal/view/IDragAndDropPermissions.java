package com.android.internal.view;

/* loaded from: classes5.dex */
public interface IDragAndDropPermissions extends android.os.IInterface {
    void release() throws android.os.RemoteException;

    void take(android.os.IBinder iBinder) throws android.os.RemoteException;

    void takeTransient() throws android.os.RemoteException;

    public static class Default implements com.android.internal.view.IDragAndDropPermissions {
        @Override // com.android.internal.view.IDragAndDropPermissions
        public void take(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IDragAndDropPermissions
        public void takeTransient() throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IDragAndDropPermissions
        public void release() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.view.IDragAndDropPermissions {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.view.IDragAndDropPermissions";
        static final int TRANSACTION_release = 3;
        static final int TRANSACTION_take = 1;
        static final int TRANSACTION_takeTransient = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.view.IDragAndDropPermissions asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.view.IDragAndDropPermissions)) {
                return (com.android.internal.view.IDragAndDropPermissions) queryLocalInterface;
            }
            return new com.android.internal.view.IDragAndDropPermissions.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "take";
                case 2:
                    return "takeTransient";
                case 3:
                    return "release";
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
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    take(readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    takeTransient();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    release();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.view.IDragAndDropPermissions {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.view.IDragAndDropPermissions.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.view.IDragAndDropPermissions
            public void take(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IDragAndDropPermissions.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IDragAndDropPermissions
            public void takeTransient() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IDragAndDropPermissions.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IDragAndDropPermissions
            public void release() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IDragAndDropPermissions.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
