package android.print;

/* loaded from: classes3.dex */
public interface IPrintDocumentAdapter extends android.os.IInterface {
    void finish() throws android.os.RemoteException;

    void kill(java.lang.String str) throws android.os.RemoteException;

    void layout(android.print.PrintAttributes printAttributes, android.print.PrintAttributes printAttributes2, android.print.ILayoutResultCallback iLayoutResultCallback, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void setObserver(android.print.IPrintDocumentAdapterObserver iPrintDocumentAdapterObserver) throws android.os.RemoteException;

    void start() throws android.os.RemoteException;

    void write(android.print.PageRange[] pageRangeArr, android.os.ParcelFileDescriptor parcelFileDescriptor, android.print.IWriteResultCallback iWriteResultCallback, int i) throws android.os.RemoteException;

    public static class Default implements android.print.IPrintDocumentAdapter {
        @Override // android.print.IPrintDocumentAdapter
        public void setObserver(android.print.IPrintDocumentAdapterObserver iPrintDocumentAdapterObserver) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintDocumentAdapter
        public void start() throws android.os.RemoteException {
        }

        @Override // android.print.IPrintDocumentAdapter
        public void layout(android.print.PrintAttributes printAttributes, android.print.PrintAttributes printAttributes2, android.print.ILayoutResultCallback iLayoutResultCallback, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintDocumentAdapter
        public void write(android.print.PageRange[] pageRangeArr, android.os.ParcelFileDescriptor parcelFileDescriptor, android.print.IWriteResultCallback iWriteResultCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintDocumentAdapter
        public void finish() throws android.os.RemoteException {
        }

        @Override // android.print.IPrintDocumentAdapter
        public void kill(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.print.IPrintDocumentAdapter {
        public static final java.lang.String DESCRIPTOR = "android.print.IPrintDocumentAdapter";
        static final int TRANSACTION_finish = 5;
        static final int TRANSACTION_kill = 6;
        static final int TRANSACTION_layout = 3;
        static final int TRANSACTION_setObserver = 1;
        static final int TRANSACTION_start = 2;
        static final int TRANSACTION_write = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.print.IPrintDocumentAdapter asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.print.IPrintDocumentAdapter)) {
                return (android.print.IPrintDocumentAdapter) queryLocalInterface;
            }
            return new android.print.IPrintDocumentAdapter.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setObserver";
                case 2:
                    return "start";
                case 3:
                    return android.media.TtmlUtils.TAG_LAYOUT;
                case 4:
                    return "write";
                case 5:
                    return "finish";
                case 6:
                    return "kill";
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
                    android.print.IPrintDocumentAdapterObserver asInterface = android.print.IPrintDocumentAdapterObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setObserver(asInterface);
                    return true;
                case 2:
                    start();
                    return true;
                case 3:
                    android.print.PrintAttributes printAttributes = (android.print.PrintAttributes) parcel.readTypedObject(android.print.PrintAttributes.CREATOR);
                    android.print.PrintAttributes printAttributes2 = (android.print.PrintAttributes) parcel.readTypedObject(android.print.PrintAttributes.CREATOR);
                    android.print.ILayoutResultCallback asInterface2 = android.print.ILayoutResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    layout(printAttributes, printAttributes2, asInterface2, bundle, readInt);
                    return true;
                case 4:
                    android.print.PageRange[] pageRangeArr = (android.print.PageRange[]) parcel.createTypedArray(android.print.PageRange.CREATOR);
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.print.IWriteResultCallback asInterface3 = android.print.IWriteResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    write(pageRangeArr, parcelFileDescriptor, asInterface3, readInt2);
                    return true;
                case 5:
                    finish();
                    return true;
                case 6:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    kill(readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.print.IPrintDocumentAdapter {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.print.IPrintDocumentAdapter.Stub.DESCRIPTOR;
            }

            @Override // android.print.IPrintDocumentAdapter
            public void setObserver(android.print.IPrintDocumentAdapterObserver iPrintDocumentAdapterObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintDocumentAdapter.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintDocumentAdapterObserver);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintDocumentAdapter
            public void start() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintDocumentAdapter.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintDocumentAdapter
            public void layout(android.print.PrintAttributes printAttributes, android.print.PrintAttributes printAttributes2, android.print.ILayoutResultCallback iLayoutResultCallback, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintDocumentAdapter.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printAttributes, 0);
                    obtain.writeTypedObject(printAttributes2, 0);
                    obtain.writeStrongInterface(iLayoutResultCallback);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintDocumentAdapter
            public void write(android.print.PageRange[] pageRangeArr, android.os.ParcelFileDescriptor parcelFileDescriptor, android.print.IWriteResultCallback iWriteResultCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintDocumentAdapter.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(pageRangeArr, 0);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongInterface(iWriteResultCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintDocumentAdapter
            public void finish() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintDocumentAdapter.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintDocumentAdapter
            public void kill(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintDocumentAdapter.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
