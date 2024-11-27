package android.security.rkp;

/* loaded from: classes3.dex */
public interface IGetKeyCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.rkp.IGetKeyCallback";

    public @interface ErrorCode {
        public static final byte ERROR_PENDING_INTERNET_CONNECTIVITY = 3;
        public static final byte ERROR_PERMANENT = 5;
        public static final byte ERROR_REQUIRES_SECURITY_PATCH = 2;
        public static final byte ERROR_UNKNOWN = 1;
    }

    void onCancel() throws android.os.RemoteException;

    void onError(byte b, java.lang.String str) throws android.os.RemoteException;

    void onSuccess(android.security.rkp.RemotelyProvisionedKey remotelyProvisionedKey) throws android.os.RemoteException;

    public static class Default implements android.security.rkp.IGetKeyCallback {
        @Override // android.security.rkp.IGetKeyCallback
        public void onSuccess(android.security.rkp.RemotelyProvisionedKey remotelyProvisionedKey) throws android.os.RemoteException {
        }

        @Override // android.security.rkp.IGetKeyCallback
        public void onCancel() throws android.os.RemoteException {
        }

        @Override // android.security.rkp.IGetKeyCallback
        public void onError(byte b, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.rkp.IGetKeyCallback {
        static final int TRANSACTION_onCancel = 2;
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, android.security.rkp.IGetKeyCallback.DESCRIPTOR);
        }

        public static android.security.rkp.IGetKeyCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.rkp.IGetKeyCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.rkp.IGetKeyCallback)) {
                return (android.security.rkp.IGetKeyCallback) queryLocalInterface;
            }
            return new android.security.rkp.IGetKeyCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSuccess";
                case 2:
                    return "onCancel";
                case 3:
                    return "onError";
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
                parcel.enforceInterface(android.security.rkp.IGetKeyCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.rkp.IGetKeyCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.security.rkp.RemotelyProvisionedKey remotelyProvisionedKey = (android.security.rkp.RemotelyProvisionedKey) parcel.readTypedObject(android.security.rkp.RemotelyProvisionedKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSuccess(remotelyProvisionedKey);
                    return true;
                case 2:
                    onCancel();
                    return true;
                case 3:
                    byte readByte = parcel.readByte();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onError(readByte, readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.rkp.IGetKeyCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.rkp.IGetKeyCallback.DESCRIPTOR;
            }

            @Override // android.security.rkp.IGetKeyCallback
            public void onSuccess(android.security.rkp.RemotelyProvisionedKey remotelyProvisionedKey) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.security.rkp.IGetKeyCallback.DESCRIPTOR);
                    obtain.writeTypedObject(remotelyProvisionedKey, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.security.rkp.IGetKeyCallback
            public void onCancel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.security.rkp.IGetKeyCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.security.rkp.IGetKeyCallback
            public void onError(byte b, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.security.rkp.IGetKeyCallback.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
