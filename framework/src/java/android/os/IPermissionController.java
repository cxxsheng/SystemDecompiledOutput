package android.os;

/* loaded from: classes3.dex */
public interface IPermissionController extends android.os.IInterface {
    boolean checkPermission(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    int getPackageUid(java.lang.String str, int i) throws android.os.RemoteException;

    java.lang.String[] getPackagesForUid(int i) throws android.os.RemoteException;

    boolean isRuntimePermission(java.lang.String str) throws android.os.RemoteException;

    int noteOp(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    public static class Default implements android.os.IPermissionController {
        @Override // android.os.IPermissionController
        public boolean checkPermission(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPermissionController
        public int noteOp(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IPermissionController
        public java.lang.String[] getPackagesForUid(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IPermissionController
        public boolean isRuntimePermission(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPermissionController
        public int getPackageUid(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IPermissionController {
        public static final java.lang.String DESCRIPTOR = "android.os.IPermissionController";
        static final int TRANSACTION_checkPermission = 1;
        static final int TRANSACTION_getPackageUid = 5;
        static final int TRANSACTION_getPackagesForUid = 3;
        static final int TRANSACTION_isRuntimePermission = 4;
        static final int TRANSACTION_noteOp = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.os.IPermissionController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IPermissionController)) {
                return (android.os.IPermissionController) queryLocalInterface;
            }
            return new android.os.IPermissionController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "checkPermission";
                case 2:
                    return "noteOp";
                case 3:
                    return "getPackagesForUid";
                case 4:
                    return "isRuntimePermission";
                case 5:
                    return "getPackageUid";
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkPermission = checkPermission(readString, readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkPermission);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int noteOp = noteOp(readString2, readInt3, readString3);
                    parcel2.writeNoException();
                    parcel2.writeInt(noteOp);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] packagesForUid = getPackagesForUid(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(packagesForUid);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isRuntimePermission = isRuntimePermission(readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRuntimePermission);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int packageUid = getPackageUid(readString5, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageUid);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IPermissionController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IPermissionController.Stub.DESCRIPTOR;
            }

            @Override // android.os.IPermissionController
            public boolean checkPermission(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPermissionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPermissionController
            public int noteOp(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPermissionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPermissionController
            public java.lang.String[] getPackagesForUid(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPermissionController.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPermissionController
            public boolean isRuntimePermission(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPermissionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPermissionController
            public int getPackageUid(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPermissionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
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
            return 4;
        }
    }
}
