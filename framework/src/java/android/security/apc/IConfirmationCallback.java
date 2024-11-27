package android.security.apc;

/* loaded from: classes3.dex */
public interface IConfirmationCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.apc.IConfirmationCallback";

    @java.lang.Deprecated
    void onCompleted(int i, byte[] bArr) throws android.os.RemoteException;

    public static class Default implements android.security.apc.IConfirmationCallback {
        @Override // android.security.apc.IConfirmationCallback
        public void onCompleted(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.apc.IConfirmationCallback {
        static final int TRANSACTION_onCompleted = 1;

        public Stub() {
            attachInterface(this, android.security.apc.IConfirmationCallback.DESCRIPTOR);
        }

        public static android.security.apc.IConfirmationCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.apc.IConfirmationCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.apc.IConfirmationCallback)) {
                return (android.security.apc.IConfirmationCallback) queryLocalInterface;
            }
            return new android.security.apc.IConfirmationCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.security.apc.IConfirmationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.apc.IConfirmationCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onCompleted(readInt, createByteArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.apc.IConfirmationCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.apc.IConfirmationCallback.DESCRIPTOR;
            }

            @Override // android.security.apc.IConfirmationCallback
            public void onCompleted(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.security.apc.IConfirmationCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
