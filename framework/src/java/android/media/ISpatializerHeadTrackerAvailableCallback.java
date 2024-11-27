package android.media;

/* loaded from: classes2.dex */
public interface ISpatializerHeadTrackerAvailableCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.ISpatializerHeadTrackerAvailableCallback";

    void dispatchSpatializerHeadTrackerAvailable(boolean z) throws android.os.RemoteException;

    public static class Default implements android.media.ISpatializerHeadTrackerAvailableCallback {
        @Override // android.media.ISpatializerHeadTrackerAvailableCallback
        public void dispatchSpatializerHeadTrackerAvailable(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.ISpatializerHeadTrackerAvailableCallback {
        static final int TRANSACTION_dispatchSpatializerHeadTrackerAvailable = 1;

        public Stub() {
            attachInterface(this, android.media.ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
        }

        public static android.media.ISpatializerHeadTrackerAvailableCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.ISpatializerHeadTrackerAvailableCallback)) {
                return (android.media.ISpatializerHeadTrackerAvailableCallback) queryLocalInterface;
            }
            return new android.media.ISpatializerHeadTrackerAvailableCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dispatchSpatializerHeadTrackerAvailable";
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
                parcel.enforceInterface(android.media.ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dispatchSpatializerHeadTrackerAvailable(readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.ISpatializerHeadTrackerAvailableCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR;
            }

            @Override // android.media.ISpatializerHeadTrackerAvailableCallback
            public void dispatchSpatializerHeadTrackerAvailable(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializerHeadTrackerAvailableCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
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