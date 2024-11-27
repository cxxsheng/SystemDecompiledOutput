package android.media;

/* loaded from: classes2.dex */
public interface ISpatializerHeadTrackingModeCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.ISpatializerHeadTrackingModeCallback";

    void dispatchSpatializerActualHeadTrackingModeChanged(int i) throws android.os.RemoteException;

    void dispatchSpatializerDesiredHeadTrackingModeChanged(int i) throws android.os.RemoteException;

    public static class Default implements android.media.ISpatializerHeadTrackingModeCallback {
        @Override // android.media.ISpatializerHeadTrackingModeCallback
        public void dispatchSpatializerActualHeadTrackingModeChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializerHeadTrackingModeCallback
        public void dispatchSpatializerDesiredHeadTrackingModeChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.ISpatializerHeadTrackingModeCallback {
        static final int TRANSACTION_dispatchSpatializerActualHeadTrackingModeChanged = 1;
        static final int TRANSACTION_dispatchSpatializerDesiredHeadTrackingModeChanged = 2;

        public Stub() {
            attachInterface(this, android.media.ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
        }

        public static android.media.ISpatializerHeadTrackingModeCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.ISpatializerHeadTrackingModeCallback)) {
                return (android.media.ISpatializerHeadTrackingModeCallback) queryLocalInterface;
            }
            return new android.media.ISpatializerHeadTrackingModeCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dispatchSpatializerActualHeadTrackingModeChanged";
                case 2:
                    return "dispatchSpatializerDesiredHeadTrackingModeChanged";
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
                parcel.enforceInterface(android.media.ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSpatializerActualHeadTrackingModeChanged(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSpatializerDesiredHeadTrackingModeChanged(readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.ISpatializerHeadTrackingModeCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.ISpatializerHeadTrackingModeCallback.DESCRIPTOR;
            }

            @Override // android.media.ISpatializerHeadTrackingModeCallback
            public void dispatchSpatializerActualHeadTrackingModeChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializerHeadTrackingModeCallback
            public void dispatchSpatializerDesiredHeadTrackingModeChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializerHeadTrackingModeCallback.DESCRIPTOR);
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
