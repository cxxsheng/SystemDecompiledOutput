package android.app;

/* loaded from: classes.dex */
public interface IUriGrantsManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IUriGrantsManager";

    int checkGrantUriPermission_ignoreNonSystem(int i, java.lang.String str, android.net.Uri uri, int i2, int i3) throws android.os.RemoteException;

    void clearGrantedUriPermissions(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getGrantedUriPermissions(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getUriPermissions(java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException;

    void grantUriPermissionFromOwner(android.os.IBinder iBinder, int i, java.lang.String str, android.net.Uri uri, int i2, int i3, int i4) throws android.os.RemoteException;

    void releasePersistableUriPermission(android.net.Uri uri, int i, java.lang.String str, int i2) throws android.os.RemoteException;

    void takePersistableUriPermission(android.net.Uri uri, int i, java.lang.String str, int i2) throws android.os.RemoteException;

    public static class Default implements android.app.IUriGrantsManager {
        @Override // android.app.IUriGrantsManager
        public void takePersistableUriPermission(android.net.Uri uri, int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IUriGrantsManager
        public void releasePersistableUriPermission(android.net.Uri uri, int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IUriGrantsManager
        public void grantUriPermissionFromOwner(android.os.IBinder iBinder, int i, java.lang.String str, android.net.Uri uri, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.app.IUriGrantsManager
        public android.content.pm.ParceledListSlice getGrantedUriPermissions(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IUriGrantsManager
        public void clearGrantedUriPermissions(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IUriGrantsManager
        public android.content.pm.ParceledListSlice getUriPermissions(java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IUriGrantsManager
        public int checkGrantUriPermission_ignoreNonSystem(int i, java.lang.String str, android.net.Uri uri, int i2, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IUriGrantsManager {
        static final int TRANSACTION_checkGrantUriPermission_ignoreNonSystem = 7;
        static final int TRANSACTION_clearGrantedUriPermissions = 5;
        static final int TRANSACTION_getGrantedUriPermissions = 4;
        static final int TRANSACTION_getUriPermissions = 6;
        static final int TRANSACTION_grantUriPermissionFromOwner = 3;
        static final int TRANSACTION_releasePersistableUriPermission = 2;
        static final int TRANSACTION_takePersistableUriPermission = 1;

        public Stub() {
            attachInterface(this, android.app.IUriGrantsManager.DESCRIPTOR);
        }

        public static android.app.IUriGrantsManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IUriGrantsManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IUriGrantsManager)) {
                return (android.app.IUriGrantsManager) queryLocalInterface;
            }
            return new android.app.IUriGrantsManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "takePersistableUriPermission";
                case 2:
                    return "releasePersistableUriPermission";
                case 3:
                    return "grantUriPermissionFromOwner";
                case 4:
                    return "getGrantedUriPermissions";
                case 5:
                    return "clearGrantedUriPermissions";
                case 6:
                    return "getUriPermissions";
                case 7:
                    return "checkGrantUriPermission_ignoreNonSystem";
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
                parcel.enforceInterface(android.app.IUriGrantsManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IUriGrantsManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    takePersistableUriPermission(uri, readInt, readString, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt3 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releasePersistableUriPermission(uri2, readInt3, readString2, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt5 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    android.net.Uri uri3 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantUriPermissionFromOwner(readStrongBinder, readInt5, readString3, uri3, readInt6, readInt7, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice grantedUriPermissions = getGrantedUriPermissions(readString4, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(grantedUriPermissions, 1);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearGrantedUriPermissions(readString5, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString6 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice uriPermissions = getUriPermissions(readString6, readBoolean, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(uriPermissions, 1);
                    return true;
                case 7:
                    int readInt11 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    android.net.Uri uri4 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkGrantUriPermission_ignoreNonSystem = checkGrantUriPermission_ignoreNonSystem(readInt11, readString7, uri4, readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkGrantUriPermission_ignoreNonSystem);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IUriGrantsManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IUriGrantsManager.DESCRIPTOR;
            }

            @Override // android.app.IUriGrantsManager
            public void takePersistableUriPermission(android.net.Uri uri, int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUriGrantsManager.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public void releasePersistableUriPermission(android.net.Uri uri, int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUriGrantsManager.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public void grantUriPermissionFromOwner(android.os.IBinder iBinder, int i, java.lang.String str, android.net.Uri uri, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUriGrantsManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public android.content.pm.ParceledListSlice getGrantedUriPermissions(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUriGrantsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public void clearGrantedUriPermissions(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUriGrantsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public android.content.pm.ParceledListSlice getUriPermissions(java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUriGrantsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public int checkGrantUriPermission_ignoreNonSystem(int i, java.lang.String str, android.net.Uri uri, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IUriGrantsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
