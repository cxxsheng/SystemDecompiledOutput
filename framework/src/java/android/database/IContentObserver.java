package android.database;

/* loaded from: classes.dex */
public interface IContentObserver extends android.os.IInterface {
    void onChange(boolean z, android.net.Uri uri, int i) throws android.os.RemoteException;

    void onChangeEtc(boolean z, android.net.Uri[] uriArr, int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.database.IContentObserver {
        @Override // android.database.IContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) throws android.os.RemoteException {
        }

        @Override // android.database.IContentObserver
        public void onChangeEtc(boolean z, android.net.Uri[] uriArr, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.database.IContentObserver {
        public static final java.lang.String DESCRIPTOR = "android.database.IContentObserver";
        static final int TRANSACTION_onChange = 1;
        static final int TRANSACTION_onChangeEtc = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.database.IContentObserver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.database.IContentObserver)) {
                return (android.database.IContentObserver) queryLocalInterface;
            }
            return new android.database.IContentObserver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onChange";
                case 2:
                    return "onChangeEtc";
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
                    boolean readBoolean = parcel.readBoolean();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onChange(readBoolean, uri, readInt);
                    return true;
                case 2:
                    boolean readBoolean2 = parcel.readBoolean();
                    android.net.Uri[] uriArr = (android.net.Uri[]) parcel.createTypedArray(android.net.Uri.CREATOR);
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onChangeEtc(readBoolean2, uriArr, readInt2, readInt3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.database.IContentObserver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.database.IContentObserver.Stub.DESCRIPTOR;
            }

            @Override // android.database.IContentObserver
            public void onChange(boolean z, android.net.Uri uri, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.database.IContentObserver.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.database.IContentObserver
            public void onChangeEtc(boolean z, android.net.Uri[] uriArr, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.database.IContentObserver.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeTypedArray(uriArr, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
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
