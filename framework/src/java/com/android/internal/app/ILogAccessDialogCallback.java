package com.android.internal.app;

/* loaded from: classes4.dex */
public interface ILogAccessDialogCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.app.ILogAccessDialogCallback";

    void approveAccessForClient(int i, java.lang.String str) throws android.os.RemoteException;

    void declineAccessForClient(int i, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.ILogAccessDialogCallback {
        @Override // com.android.internal.app.ILogAccessDialogCallback
        public void approveAccessForClient(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.ILogAccessDialogCallback
        public void declineAccessForClient(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.ILogAccessDialogCallback {
        static final int TRANSACTION_approveAccessForClient = 1;
        static final int TRANSACTION_declineAccessForClient = 2;

        public Stub() {
            attachInterface(this, com.android.internal.app.ILogAccessDialogCallback.DESCRIPTOR);
        }

        public static com.android.internal.app.ILogAccessDialogCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.app.ILogAccessDialogCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.ILogAccessDialogCallback)) {
                return (com.android.internal.app.ILogAccessDialogCallback) queryLocalInterface;
            }
            return new com.android.internal.app.ILogAccessDialogCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "approveAccessForClient";
                case 2:
                    return "declineAccessForClient";
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
                parcel.enforceInterface(com.android.internal.app.ILogAccessDialogCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.app.ILogAccessDialogCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    approveAccessForClient(readInt, readString);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    declineAccessForClient(readInt2, readString2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.ILogAccessDialogCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.ILogAccessDialogCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.app.ILogAccessDialogCallback
            public void approveAccessForClient(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ILogAccessDialogCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ILogAccessDialogCallback
            public void declineAccessForClient(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.ILogAccessDialogCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
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
