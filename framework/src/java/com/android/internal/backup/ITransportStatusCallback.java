package com.android.internal.backup;

/* loaded from: classes4.dex */
public interface ITransportStatusCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.backup.ITransportStatusCallback";

    void onOperationComplete() throws android.os.RemoteException;

    void onOperationCompleteWithStatus(int i) throws android.os.RemoteException;

    public static class Default implements com.android.internal.backup.ITransportStatusCallback {
        @Override // com.android.internal.backup.ITransportStatusCallback
        public void onOperationCompleteWithStatus(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.ITransportStatusCallback
        public void onOperationComplete() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.backup.ITransportStatusCallback {
        static final int TRANSACTION_onOperationComplete = 2;
        static final int TRANSACTION_onOperationCompleteWithStatus = 1;

        public Stub() {
            attachInterface(this, com.android.internal.backup.ITransportStatusCallback.DESCRIPTOR);
        }

        public static com.android.internal.backup.ITransportStatusCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.backup.ITransportStatusCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.backup.ITransportStatusCallback)) {
                return (com.android.internal.backup.ITransportStatusCallback) queryLocalInterface;
            }
            return new com.android.internal.backup.ITransportStatusCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onOperationCompleteWithStatus";
                case 2:
                    return "onOperationComplete";
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
                parcel.enforceInterface(com.android.internal.backup.ITransportStatusCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.backup.ITransportStatusCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onOperationCompleteWithStatus(readInt);
                    return true;
                case 2:
                    onOperationComplete();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.backup.ITransportStatusCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.backup.ITransportStatusCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.backup.ITransportStatusCallback
            public void onOperationCompleteWithStatus(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.ITransportStatusCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.ITransportStatusCallback
            public void onOperationComplete() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.ITransportStatusCallback.DESCRIPTOR);
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
