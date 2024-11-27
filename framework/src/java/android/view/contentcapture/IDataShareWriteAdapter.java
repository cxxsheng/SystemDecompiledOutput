package android.view.contentcapture;

/* loaded from: classes4.dex */
public interface IDataShareWriteAdapter extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.contentcapture.IDataShareWriteAdapter";

    void error(int i) throws android.os.RemoteException;

    void finish() throws android.os.RemoteException;

    void rejected() throws android.os.RemoteException;

    void write(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    public static class Default implements android.view.contentcapture.IDataShareWriteAdapter {
        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void write(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void error(int i) throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void rejected() throws android.os.RemoteException {
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void finish() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.contentcapture.IDataShareWriteAdapter {
        static final int TRANSACTION_error = 2;
        static final int TRANSACTION_finish = 4;
        static final int TRANSACTION_rejected = 3;
        static final int TRANSACTION_write = 1;

        public Stub() {
            attachInterface(this, android.view.contentcapture.IDataShareWriteAdapter.DESCRIPTOR);
        }

        public static android.view.contentcapture.IDataShareWriteAdapter asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.contentcapture.IDataShareWriteAdapter.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.contentcapture.IDataShareWriteAdapter)) {
                return (android.view.contentcapture.IDataShareWriteAdapter) queryLocalInterface;
            }
            return new android.view.contentcapture.IDataShareWriteAdapter.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "write";
                case 2:
                    return "error";
                case 3:
                    return "rejected";
                case 4:
                    return "finish";
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
                parcel.enforceInterface(android.view.contentcapture.IDataShareWriteAdapter.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.contentcapture.IDataShareWriteAdapter.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    write(parcelFileDescriptor);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    error(readInt);
                    return true;
                case 3:
                    rejected();
                    return true;
                case 4:
                    finish();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.contentcapture.IDataShareWriteAdapter {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.contentcapture.IDataShareWriteAdapter.DESCRIPTOR;
            }

            @Override // android.view.contentcapture.IDataShareWriteAdapter
            public void write(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IDataShareWriteAdapter.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IDataShareWriteAdapter
            public void error(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IDataShareWriteAdapter.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IDataShareWriteAdapter
            public void rejected() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IDataShareWriteAdapter.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IDataShareWriteAdapter
            public void finish() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.contentcapture.IDataShareWriteAdapter.DESCRIPTOR);
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
