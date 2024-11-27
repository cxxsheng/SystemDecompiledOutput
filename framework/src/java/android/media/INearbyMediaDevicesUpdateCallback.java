package android.media;

/* loaded from: classes2.dex */
public interface INearbyMediaDevicesUpdateCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.INearbyMediaDevicesUpdateCallback";

    void onDevicesUpdated(java.util.List<android.media.NearbyDevice> list) throws android.os.RemoteException;

    public static class Default implements android.media.INearbyMediaDevicesUpdateCallback {
        @Override // android.media.INearbyMediaDevicesUpdateCallback
        public void onDevicesUpdated(java.util.List<android.media.NearbyDevice> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.INearbyMediaDevicesUpdateCallback {
        static final int TRANSACTION_onDevicesUpdated = 1;

        public Stub() {
            attachInterface(this, android.media.INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
        }

        public static android.media.INearbyMediaDevicesUpdateCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.INearbyMediaDevicesUpdateCallback)) {
                return (android.media.INearbyMediaDevicesUpdateCallback) queryLocalInterface;
            }
            return new android.media.INearbyMediaDevicesUpdateCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDevicesUpdated";
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
                parcel.enforceInterface(android.media.INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.NearbyDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDevicesUpdated(createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.INearbyMediaDevicesUpdateCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.INearbyMediaDevicesUpdateCallback.DESCRIPTOR;
            }

            @Override // android.media.INearbyMediaDevicesUpdateCallback
            public void onDevicesUpdated(java.util.List<android.media.NearbyDevice> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.INearbyMediaDevicesUpdateCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
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
