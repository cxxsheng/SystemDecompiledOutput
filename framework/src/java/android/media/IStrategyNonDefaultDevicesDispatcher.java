package android.media;

/* loaded from: classes2.dex */
public interface IStrategyNonDefaultDevicesDispatcher extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IStrategyNonDefaultDevicesDispatcher";

    void dispatchNonDefDevicesChanged(int i, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException;

    public static class Default implements android.media.IStrategyNonDefaultDevicesDispatcher {
        @Override // android.media.IStrategyNonDefaultDevicesDispatcher
        public void dispatchNonDefDevicesChanged(int i, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IStrategyNonDefaultDevicesDispatcher {
        static final int TRANSACTION_dispatchNonDefDevicesChanged = 1;

        public Stub() {
            attachInterface(this, android.media.IStrategyNonDefaultDevicesDispatcher.DESCRIPTOR);
        }

        public static android.media.IStrategyNonDefaultDevicesDispatcher asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IStrategyNonDefaultDevicesDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IStrategyNonDefaultDevicesDispatcher)) {
                return (android.media.IStrategyNonDefaultDevicesDispatcher) queryLocalInterface;
            }
            return new android.media.IStrategyNonDefaultDevicesDispatcher.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dispatchNonDefDevicesChanged";
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
                parcel.enforceInterface(android.media.IStrategyNonDefaultDevicesDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IStrategyNonDefaultDevicesDispatcher.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchNonDefDevicesChanged(readInt, createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IStrategyNonDefaultDevicesDispatcher {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IStrategyNonDefaultDevicesDispatcher.DESCRIPTOR;
            }

            @Override // android.media.IStrategyNonDefaultDevicesDispatcher
            public void dispatchNonDefDevicesChanged(int i, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IStrategyNonDefaultDevicesDispatcher.DESCRIPTOR);
                    obtain.writeInt(i);
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
