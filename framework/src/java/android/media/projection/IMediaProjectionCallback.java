package android.media.projection;

/* loaded from: classes2.dex */
public interface IMediaProjectionCallback extends android.os.IInterface {
    void onCapturedContentResize(int i, int i2) throws android.os.RemoteException;

    void onCapturedContentVisibilityChanged(boolean z) throws android.os.RemoteException;

    void onStop() throws android.os.RemoteException;

    public static class Default implements android.media.projection.IMediaProjectionCallback {
        @Override // android.media.projection.IMediaProjectionCallback
        public void onStop() throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onCapturedContentResize(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onCapturedContentVisibilityChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.projection.IMediaProjectionCallback {
        public static final java.lang.String DESCRIPTOR = "android.media.projection.IMediaProjectionCallback";
        static final int TRANSACTION_onCapturedContentResize = 2;
        static final int TRANSACTION_onCapturedContentVisibilityChanged = 3;
        static final int TRANSACTION_onStop = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.projection.IMediaProjectionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.projection.IMediaProjectionCallback)) {
                return (android.media.projection.IMediaProjectionCallback) queryLocalInterface;
            }
            return new android.media.projection.IMediaProjectionCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStop";
                case 2:
                    return "onCapturedContentResize";
                case 3:
                    return "onCapturedContentVisibilityChanged";
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
                    onStop();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCapturedContentResize(readInt, readInt2);
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCapturedContentVisibilityChanged(readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.projection.IMediaProjectionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.projection.IMediaProjectionCallback.Stub.DESCRIPTOR;
            }

            @Override // android.media.projection.IMediaProjectionCallback
            public void onStop() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionCallback
            public void onCapturedContentResize(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionCallback
            public void onCapturedContentVisibilityChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.projection.IMediaProjectionCallback.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
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
