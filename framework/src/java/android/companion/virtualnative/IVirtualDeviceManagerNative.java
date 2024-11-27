package android.companion.virtualnative;

/* loaded from: classes.dex */
public interface IVirtualDeviceManagerNative extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.virtualnative.IVirtualDeviceManagerNative";
    public static final int DEVICE_POLICY_CUSTOM = 1;
    public static final int DEVICE_POLICY_DEFAULT = 0;
    public static final int POLICY_TYPE_ACTIVITY = 3;
    public static final int POLICY_TYPE_AUDIO = 1;
    public static final int POLICY_TYPE_RECENTS = 2;
    public static final int POLICY_TYPE_SENSORS = 0;

    int[] getDeviceIdsForUid(int i) throws android.os.RemoteException;

    int getDevicePolicy(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.companion.virtualnative.IVirtualDeviceManagerNative {
        @Override // android.companion.virtualnative.IVirtualDeviceManagerNative
        public int[] getDeviceIdsForUid(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.companion.virtualnative.IVirtualDeviceManagerNative
        public int getDevicePolicy(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.virtualnative.IVirtualDeviceManagerNative {
        static final int TRANSACTION_getDeviceIdsForUid = 1;
        static final int TRANSACTION_getDevicePolicy = 2;

        public Stub() {
            attachInterface(this, android.companion.virtualnative.IVirtualDeviceManagerNative.DESCRIPTOR);
        }

        public static android.companion.virtualnative.IVirtualDeviceManagerNative asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.virtualnative.IVirtualDeviceManagerNative.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.virtualnative.IVirtualDeviceManagerNative)) {
                return (android.companion.virtualnative.IVirtualDeviceManagerNative) queryLocalInterface;
            }
            return new android.companion.virtualnative.IVirtualDeviceManagerNative.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDeviceIdsForUid";
                case 2:
                    return "getDevicePolicy";
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
                parcel.enforceInterface(android.companion.virtualnative.IVirtualDeviceManagerNative.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.virtualnative.IVirtualDeviceManagerNative.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] deviceIdsForUid = getDeviceIdsForUid(readInt);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(deviceIdsForUid);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int devicePolicy = getDevicePolicy(readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(devicePolicy);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.virtualnative.IVirtualDeviceManagerNative {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.virtualnative.IVirtualDeviceManagerNative.DESCRIPTOR;
            }

            @Override // android.companion.virtualnative.IVirtualDeviceManagerNative
            public int[] getDeviceIdsForUid(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtualnative.IVirtualDeviceManagerNative.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtualnative.IVirtualDeviceManagerNative
            public int getDevicePolicy(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtualnative.IVirtualDeviceManagerNative.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
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
