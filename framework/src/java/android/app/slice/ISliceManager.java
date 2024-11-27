package android.app.slice;

/* loaded from: classes.dex */
public interface ISliceManager extends android.os.IInterface {
    void applyRestore(byte[] bArr, int i) throws android.os.RemoteException;

    int checkSlicePermission(android.net.Uri uri, java.lang.String str, int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException;

    byte[] getBackupPayload(int i) throws android.os.RemoteException;

    android.net.Uri[] getPinnedSlices(java.lang.String str) throws android.os.RemoteException;

    android.app.slice.SliceSpec[] getPinnedSpecs(android.net.Uri uri, java.lang.String str) throws android.os.RemoteException;

    void grantPermissionFromUser(android.net.Uri uri, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException;

    void grantSlicePermission(java.lang.String str, java.lang.String str2, android.net.Uri uri) throws android.os.RemoteException;

    boolean hasSliceAccess(java.lang.String str) throws android.os.RemoteException;

    void pinSlice(java.lang.String str, android.net.Uri uri, android.app.slice.SliceSpec[] sliceSpecArr, android.os.IBinder iBinder) throws android.os.RemoteException;

    void revokeSlicePermission(java.lang.String str, java.lang.String str2, android.net.Uri uri) throws android.os.RemoteException;

    void unpinSlice(java.lang.String str, android.net.Uri uri, android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.app.slice.ISliceManager {
        @Override // android.app.slice.ISliceManager
        public void pinSlice(java.lang.String str, android.net.Uri uri, android.app.slice.SliceSpec[] sliceSpecArr, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.slice.ISliceManager
        public void unpinSlice(java.lang.String str, android.net.Uri uri, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.slice.ISliceManager
        public boolean hasSliceAccess(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.slice.ISliceManager
        public android.app.slice.SliceSpec[] getPinnedSpecs(android.net.Uri uri, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.slice.ISliceManager
        public android.net.Uri[] getPinnedSlices(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.slice.ISliceManager
        public byte[] getBackupPayload(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.slice.ISliceManager
        public void applyRestore(byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.app.slice.ISliceManager
        public void grantSlicePermission(java.lang.String str, java.lang.String str2, android.net.Uri uri) throws android.os.RemoteException {
        }

        @Override // android.app.slice.ISliceManager
        public void revokeSlicePermission(java.lang.String str, java.lang.String str2, android.net.Uri uri) throws android.os.RemoteException {
        }

        @Override // android.app.slice.ISliceManager
        public int checkSlicePermission(android.net.Uri uri, java.lang.String str, int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.slice.ISliceManager
        public void grantPermissionFromUser(android.net.Uri uri, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.slice.ISliceManager {
        public static final java.lang.String DESCRIPTOR = "android.app.slice.ISliceManager";
        static final int TRANSACTION_applyRestore = 7;
        static final int TRANSACTION_checkSlicePermission = 10;
        static final int TRANSACTION_getBackupPayload = 6;
        static final int TRANSACTION_getPinnedSlices = 5;
        static final int TRANSACTION_getPinnedSpecs = 4;
        static final int TRANSACTION_grantPermissionFromUser = 11;
        static final int TRANSACTION_grantSlicePermission = 8;
        static final int TRANSACTION_hasSliceAccess = 3;
        static final int TRANSACTION_pinSlice = 1;
        static final int TRANSACTION_revokeSlicePermission = 9;
        static final int TRANSACTION_unpinSlice = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.slice.ISliceManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.slice.ISliceManager)) {
                return (android.app.slice.ISliceManager) queryLocalInterface;
            }
            return new android.app.slice.ISliceManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "pinSlice";
                case 2:
                    return "unpinSlice";
                case 3:
                    return "hasSliceAccess";
                case 4:
                    return "getPinnedSpecs";
                case 5:
                    return "getPinnedSlices";
                case 6:
                    return "getBackupPayload";
                case 7:
                    return "applyRestore";
                case 8:
                    return "grantSlicePermission";
                case 9:
                    return "revokeSlicePermission";
                case 10:
                    return "checkSlicePermission";
                case 11:
                    return "grantPermissionFromUser";
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
                    java.lang.String readString = parcel.readString();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.app.slice.SliceSpec[] sliceSpecArr = (android.app.slice.SliceSpec[]) parcel.createTypedArray(android.app.slice.SliceSpec.CREATOR);
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    pinSlice(readString, uri, sliceSpecArr, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unpinSlice(readString2, uri2, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasSliceAccess = hasSliceAccess(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSliceAccess);
                    return true;
                case 4:
                    android.net.Uri uri3 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.slice.SliceSpec[] pinnedSpecs = getPinnedSpecs(uri3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(pinnedSpecs, 1);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.net.Uri[] pinnedSlices = getPinnedSlices(readString5);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(pinnedSlices, 1);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] backupPayload = getBackupPayload(readInt);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(backupPayload);
                    return true;
                case 7:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applyRestore(createByteArray, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    android.net.Uri uri4 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    grantSlicePermission(readString6, readString7, uri4);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    android.net.Uri uri5 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    revokeSlicePermission(readString8, readString9, uri5);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.net.Uri uri6 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    java.lang.String readString10 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    int checkSlicePermission = checkSlicePermission(uri6, readString10, readInt3, readInt4, createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkSlicePermission);
                    return true;
                case 11:
                    android.net.Uri uri7 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    java.lang.String readString11 = parcel.readString();
                    java.lang.String readString12 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    grantPermissionFromUser(uri7, readString11, readString12, readBoolean);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.slice.ISliceManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.slice.ISliceManager.Stub.DESCRIPTOR;
            }

            @Override // android.app.slice.ISliceManager
            public void pinSlice(java.lang.String str, android.net.Uri uri, android.app.slice.SliceSpec[] sliceSpecArr, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.slice.ISliceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedArray(sliceSpecArr, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public void unpinSlice(java.lang.String str, android.net.Uri uri, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.slice.ISliceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public boolean hasSliceAccess(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.slice.ISliceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public android.app.slice.SliceSpec[] getPinnedSpecs(android.net.Uri uri, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.slice.ISliceManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.slice.SliceSpec[]) obtain2.createTypedArray(android.app.slice.SliceSpec.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public android.net.Uri[] getPinnedSlices(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.slice.ISliceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.Uri[]) obtain2.createTypedArray(android.net.Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public byte[] getBackupPayload(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.slice.ISliceManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public void applyRestore(byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.slice.ISliceManager.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public void grantSlicePermission(java.lang.String str, java.lang.String str2, android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.slice.ISliceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public void revokeSlicePermission(java.lang.String str, java.lang.String str2, android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.slice.ISliceManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public int checkSlicePermission(android.net.Uri uri, java.lang.String str, int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.slice.ISliceManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public void grantPermissionFromUser(android.net.Uri uri, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.slice.ISliceManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
