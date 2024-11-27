package android.permission;

/* loaded from: classes3.dex */
public interface IOnPermissionsChangeListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.permission.IOnPermissionsChangeListener";

    void onPermissionsChanged(int i, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.permission.IOnPermissionsChangeListener {
        @Override // android.permission.IOnPermissionsChangeListener
        public void onPermissionsChanged(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.permission.IOnPermissionsChangeListener {
        static final int TRANSACTION_onPermissionsChanged = 1;

        public Stub() {
            attachInterface(this, android.permission.IOnPermissionsChangeListener.DESCRIPTOR);
        }

        public static android.permission.IOnPermissionsChangeListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.permission.IOnPermissionsChangeListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.permission.IOnPermissionsChangeListener)) {
                return (android.permission.IOnPermissionsChangeListener) queryLocalInterface;
            }
            return new android.permission.IOnPermissionsChangeListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onPermissionsChanged";
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
                parcel.enforceInterface(android.permission.IOnPermissionsChangeListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.permission.IOnPermissionsChangeListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onPermissionsChanged(readInt, readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.permission.IOnPermissionsChangeListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.permission.IOnPermissionsChangeListener.DESCRIPTOR;
            }

            @Override // android.permission.IOnPermissionsChangeListener
            public void onPermissionsChanged(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IOnPermissionsChangeListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
