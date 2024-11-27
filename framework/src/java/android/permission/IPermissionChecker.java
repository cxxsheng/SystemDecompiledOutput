package android.permission;

/* loaded from: classes3.dex */
public interface IPermissionChecker extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.permission.IPermissionChecker";
    public static final int PERMISSION_GRANTED = 0;
    public static final int PERMISSION_HARD_DENIED = 2;
    public static final int PERMISSION_SOFT_DENIED = 1;

    int checkOp(int i, android.content.AttributionSourceState attributionSourceState, java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException;

    int checkPermission(java.lang.String str, android.content.AttributionSourceState attributionSourceState, java.lang.String str2, boolean z, boolean z2, boolean z3, int i) throws android.os.RemoteException;

    void finishDataDelivery(int i, android.content.AttributionSourceState attributionSourceState, boolean z) throws android.os.RemoteException;

    public static class Default implements android.permission.IPermissionChecker {
        @Override // android.permission.IPermissionChecker
        public int checkPermission(java.lang.String str, android.content.AttributionSourceState attributionSourceState, java.lang.String str2, boolean z, boolean z2, boolean z3, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionChecker
        public void finishDataDelivery(int i, android.content.AttributionSourceState attributionSourceState, boolean z) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionChecker
        public int checkOp(int i, android.content.AttributionSourceState attributionSourceState, java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.permission.IPermissionChecker {
        static final int TRANSACTION_checkOp = 3;
        static final int TRANSACTION_checkPermission = 1;
        static final int TRANSACTION_finishDataDelivery = 2;

        public Stub() {
            attachInterface(this, android.permission.IPermissionChecker.DESCRIPTOR);
        }

        public static android.permission.IPermissionChecker asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.permission.IPermissionChecker.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.permission.IPermissionChecker)) {
                return (android.permission.IPermissionChecker) queryLocalInterface;
            }
            return new android.permission.IPermissionChecker.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.permission.IPermissionChecker.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.permission.IPermissionChecker.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    android.content.AttributionSourceState attributionSourceState = (android.content.AttributionSourceState) parcel.readTypedObject(android.content.AttributionSourceState.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkPermission = checkPermission(readString, attributionSourceState, readString2, readBoolean, readBoolean2, readBoolean3, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkPermission);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.content.AttributionSourceState attributionSourceState2 = (android.content.AttributionSourceState) parcel.readTypedObject(android.content.AttributionSourceState.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishDataDelivery(readInt2, attributionSourceState2, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    android.content.AttributionSourceState attributionSourceState3 = (android.content.AttributionSourceState) parcel.readTypedObject(android.content.AttributionSourceState.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int checkOp = checkOp(readInt3, attributionSourceState3, readString3, readBoolean5, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkOp);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.permission.IPermissionChecker {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.permission.IPermissionChecker.DESCRIPTOR;
            }

            @Override // android.permission.IPermissionChecker
            public int checkPermission(java.lang.String str, android.content.AttributionSourceState attributionSourceState, java.lang.String str2, boolean z, boolean z2, boolean z3, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionChecker.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionChecker
            public void finishDataDelivery(int i, android.content.AttributionSourceState attributionSourceState, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionChecker.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionChecker
            public int checkOp(int i, android.content.AttributionSourceState attributionSourceState, java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionChecker.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
