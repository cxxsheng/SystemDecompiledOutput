package android.print;

/* loaded from: classes3.dex */
public interface IWriteResultCallback extends android.os.IInterface {
    void onWriteCanceled(int i) throws android.os.RemoteException;

    void onWriteFailed(java.lang.CharSequence charSequence, int i) throws android.os.RemoteException;

    void onWriteFinished(android.print.PageRange[] pageRangeArr, int i) throws android.os.RemoteException;

    void onWriteStarted(android.os.ICancellationSignal iCancellationSignal, int i) throws android.os.RemoteException;

    public static class Default implements android.print.IWriteResultCallback {
        @Override // android.print.IWriteResultCallback
        public void onWriteStarted(android.os.ICancellationSignal iCancellationSignal, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IWriteResultCallback
        public void onWriteFinished(android.print.PageRange[] pageRangeArr, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IWriteResultCallback
        public void onWriteFailed(java.lang.CharSequence charSequence, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IWriteResultCallback
        public void onWriteCanceled(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.print.IWriteResultCallback {
        public static final java.lang.String DESCRIPTOR = "android.print.IWriteResultCallback";
        static final int TRANSACTION_onWriteCanceled = 4;
        static final int TRANSACTION_onWriteFailed = 3;
        static final int TRANSACTION_onWriteFinished = 2;
        static final int TRANSACTION_onWriteStarted = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.print.IWriteResultCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.print.IWriteResultCallback)) {
                return (android.print.IWriteResultCallback) queryLocalInterface;
            }
            return new android.print.IWriteResultCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onWriteStarted";
                case 2:
                    return "onWriteFinished";
                case 3:
                    return "onWriteFailed";
                case 4:
                    return "onWriteCanceled";
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
                    android.os.ICancellationSignal asInterface = android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onWriteStarted(asInterface, readInt);
                    return true;
                case 2:
                    android.print.PageRange[] pageRangeArr = (android.print.PageRange[]) parcel.createTypedArray(android.print.PageRange.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onWriteFinished(pageRangeArr, readInt2);
                    return true;
                case 3:
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onWriteFailed(charSequence, readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onWriteCanceled(readInt4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.print.IWriteResultCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.print.IWriteResultCallback.Stub.DESCRIPTOR;
            }

            @Override // android.print.IWriteResultCallback
            public void onWriteStarted(android.os.ICancellationSignal iCancellationSignal, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IWriteResultCallback.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCancellationSignal);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IWriteResultCallback
            public void onWriteFinished(android.print.PageRange[] pageRangeArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IWriteResultCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(pageRangeArr, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IWriteResultCallback
            public void onWriteFailed(java.lang.CharSequence charSequence, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IWriteResultCallback.Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IWriteResultCallback
            public void onWriteCanceled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IWriteResultCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
