package android.print;

/* loaded from: classes3.dex */
public interface ILayoutResultCallback extends android.os.IInterface {
    void onLayoutCanceled(int i) throws android.os.RemoteException;

    void onLayoutFailed(java.lang.CharSequence charSequence, int i) throws android.os.RemoteException;

    void onLayoutFinished(android.print.PrintDocumentInfo printDocumentInfo, boolean z, int i) throws android.os.RemoteException;

    void onLayoutStarted(android.os.ICancellationSignal iCancellationSignal, int i) throws android.os.RemoteException;

    public static class Default implements android.print.ILayoutResultCallback {
        @Override // android.print.ILayoutResultCallback
        public void onLayoutStarted(android.os.ICancellationSignal iCancellationSignal, int i) throws android.os.RemoteException {
        }

        @Override // android.print.ILayoutResultCallback
        public void onLayoutFinished(android.print.PrintDocumentInfo printDocumentInfo, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.print.ILayoutResultCallback
        public void onLayoutFailed(java.lang.CharSequence charSequence, int i) throws android.os.RemoteException {
        }

        @Override // android.print.ILayoutResultCallback
        public void onLayoutCanceled(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.print.ILayoutResultCallback {
        public static final java.lang.String DESCRIPTOR = "android.print.ILayoutResultCallback";
        static final int TRANSACTION_onLayoutCanceled = 4;
        static final int TRANSACTION_onLayoutFailed = 3;
        static final int TRANSACTION_onLayoutFinished = 2;
        static final int TRANSACTION_onLayoutStarted = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.print.ILayoutResultCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.print.ILayoutResultCallback)) {
                return (android.print.ILayoutResultCallback) queryLocalInterface;
            }
            return new android.print.ILayoutResultCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onLayoutStarted";
                case 2:
                    return "onLayoutFinished";
                case 3:
                    return "onLayoutFailed";
                case 4:
                    return "onLayoutCanceled";
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
                    onLayoutStarted(asInterface, readInt);
                    return true;
                case 2:
                    android.print.PrintDocumentInfo printDocumentInfo = (android.print.PrintDocumentInfo) parcel.readTypedObject(android.print.PrintDocumentInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutFinished(printDocumentInfo, readBoolean, readInt2);
                    return true;
                case 3:
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutFailed(charSequence, readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutCanceled(readInt4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.print.ILayoutResultCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.print.ILayoutResultCallback.Stub.DESCRIPTOR;
            }

            @Override // android.print.ILayoutResultCallback
            public void onLayoutStarted(android.os.ICancellationSignal iCancellationSignal, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.ILayoutResultCallback.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCancellationSignal);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.ILayoutResultCallback
            public void onLayoutFinished(android.print.PrintDocumentInfo printDocumentInfo, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.ILayoutResultCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printDocumentInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.ILayoutResultCallback
            public void onLayoutFailed(java.lang.CharSequence charSequence, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.ILayoutResultCallback.Stub.DESCRIPTOR);
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

            @Override // android.print.ILayoutResultCallback
            public void onLayoutCanceled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.ILayoutResultCallback.Stub.DESCRIPTOR);
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
