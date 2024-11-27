package android.security.legacykeystore;

/* loaded from: classes3.dex */
public interface ILegacyKeystore extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.legacykeystore.ILegacyKeystore";
    public static final int ERROR_ENTRY_NOT_FOUND = 7;
    public static final int ERROR_PERMISSION_DENIED = 6;
    public static final int ERROR_SYSTEM_ERROR = 4;
    public static final int UID_SELF = -1;

    byte[] get(java.lang.String str, int i) throws android.os.RemoteException;

    java.lang.String[] list(java.lang.String str, int i) throws android.os.RemoteException;

    void put(java.lang.String str, int i, byte[] bArr) throws android.os.RemoteException;

    void remove(java.lang.String str, int i) throws android.os.RemoteException;

    public static class Default implements android.security.legacykeystore.ILegacyKeystore {
        @Override // android.security.legacykeystore.ILegacyKeystore
        public byte[] get(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.legacykeystore.ILegacyKeystore
        public void put(java.lang.String str, int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.security.legacykeystore.ILegacyKeystore
        public void remove(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.security.legacykeystore.ILegacyKeystore
        public java.lang.String[] list(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.legacykeystore.ILegacyKeystore {
        static final int TRANSACTION_get = 1;
        static final int TRANSACTION_list = 4;
        static final int TRANSACTION_put = 2;
        static final int TRANSACTION_remove = 3;

        public Stub() {
            attachInterface(this, android.security.legacykeystore.ILegacyKeystore.DESCRIPTOR);
        }

        public static android.security.legacykeystore.ILegacyKeystore asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.legacykeystore.ILegacyKeystore.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.legacykeystore.ILegacyKeystore)) {
                return (android.security.legacykeystore.ILegacyKeystore) queryLocalInterface;
            }
            return new android.security.legacykeystore.ILegacyKeystore.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "get";
                case 2:
                    return "put";
                case 3:
                    return "remove";
                case 4:
                    return android.app.slice.Slice.HINT_LIST;
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
                parcel.enforceInterface(android.security.legacykeystore.ILegacyKeystore.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.legacykeystore.ILegacyKeystore.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] bArr = get(readString, readInt);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArr);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    put(readString2, readInt2, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    remove(readString3, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] list = list(readString4, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(list);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.legacykeystore.ILegacyKeystore {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.legacykeystore.ILegacyKeystore.DESCRIPTOR;
            }

            @Override // android.security.legacykeystore.ILegacyKeystore
            public byte[] get(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.legacykeystore.ILegacyKeystore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.legacykeystore.ILegacyKeystore
            public void put(java.lang.String str, int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.legacykeystore.ILegacyKeystore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.legacykeystore.ILegacyKeystore
            public void remove(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.legacykeystore.ILegacyKeystore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.legacykeystore.ILegacyKeystore
            public java.lang.String[] list(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.legacykeystore.ILegacyKeystore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
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
