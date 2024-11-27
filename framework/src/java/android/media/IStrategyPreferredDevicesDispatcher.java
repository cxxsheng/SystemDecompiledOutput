package android.media;

/* loaded from: classes2.dex */
public interface IStrategyPreferredDevicesDispatcher extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IStrategyPreferredDevicesDispatcher";

    void dispatchPrefDevicesChanged(int i, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException;

    public static class Default implements android.media.IStrategyPreferredDevicesDispatcher {
        @Override // android.media.IStrategyPreferredDevicesDispatcher
        public void dispatchPrefDevicesChanged(int i, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IStrategyPreferredDevicesDispatcher {
        static final int TRANSACTION_dispatchPrefDevicesChanged = 1;

        public Stub() {
            attachInterface(this, android.media.IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
        }

        public static android.media.IStrategyPreferredDevicesDispatcher asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IStrategyPreferredDevicesDispatcher)) {
                return (android.media.IStrategyPreferredDevicesDispatcher) queryLocalInterface;
            }
            return new android.media.IStrategyPreferredDevicesDispatcher.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dispatchPrefDevicesChanged";
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
                parcel.enforceInterface(android.media.IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchPrefDevicesChanged(readInt, createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IStrategyPreferredDevicesDispatcher {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IStrategyPreferredDevicesDispatcher.DESCRIPTOR;
            }

            @Override // android.media.IStrategyPreferredDevicesDispatcher
            public void dispatchPrefDevicesChanged(int i, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
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
