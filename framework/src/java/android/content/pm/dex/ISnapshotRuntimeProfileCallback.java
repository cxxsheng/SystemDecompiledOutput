package android.content.pm.dex;

/* loaded from: classes.dex */
public interface ISnapshotRuntimeProfileCallback extends android.os.IInterface {
    void onError(int i) throws android.os.RemoteException;

    void onSuccess(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    public static class Default implements android.content.pm.dex.ISnapshotRuntimeProfileCallback {
        @Override // android.content.pm.dex.ISnapshotRuntimeProfileCallback
        public void onSuccess(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // android.content.pm.dex.ISnapshotRuntimeProfileCallback
        public void onError(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.dex.ISnapshotRuntimeProfileCallback {
        public static final java.lang.String DESCRIPTOR = "android.content.pm.dex.ISnapshotRuntimeProfileCallback";
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.content.pm.dex.ISnapshotRuntimeProfileCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.dex.ISnapshotRuntimeProfileCallback)) {
                return (android.content.pm.dex.ISnapshotRuntimeProfileCallback) queryLocalInterface;
            }
            return new android.content.pm.dex.ISnapshotRuntimeProfileCallback.Stub.Proxy(iBinder);
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSuccess(parcelFileDescriptor);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.dex.ISnapshotRuntimeProfileCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.dex.ISnapshotRuntimeProfileCallback.Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.dex.ISnapshotRuntimeProfileCallback
            public void onSuccess(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.dex.ISnapshotRuntimeProfileCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.dex.ISnapshotRuntimeProfileCallback
            public void onError(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.dex.ISnapshotRuntimeProfileCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
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
