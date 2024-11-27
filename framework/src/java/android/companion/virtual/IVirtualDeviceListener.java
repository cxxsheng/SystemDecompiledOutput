package android.companion.virtual;

/* loaded from: classes.dex */
public interface IVirtualDeviceListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.virtual.IVirtualDeviceListener";

    void onVirtualDeviceClosed(int i) throws android.os.RemoteException;

    void onVirtualDeviceCreated(int i) throws android.os.RemoteException;

    public static class Default implements android.companion.virtual.IVirtualDeviceListener {
        @Override // android.companion.virtual.IVirtualDeviceListener
        public void onVirtualDeviceCreated(int i) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDeviceListener
        public void onVirtualDeviceClosed(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.virtual.IVirtualDeviceListener {
        static final int TRANSACTION_onVirtualDeviceClosed = 2;
        static final int TRANSACTION_onVirtualDeviceCreated = 1;

        public Stub() {
            attachInterface(this, android.companion.virtual.IVirtualDeviceListener.DESCRIPTOR);
        }

        public static android.companion.virtual.IVirtualDeviceListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.virtual.IVirtualDeviceListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.virtual.IVirtualDeviceListener)) {
                return (android.companion.virtual.IVirtualDeviceListener) queryLocalInterface;
            }
            return new android.companion.virtual.IVirtualDeviceListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onVirtualDeviceCreated";
                case 2:
                    return "onVirtualDeviceClosed";
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
                parcel.enforceInterface(android.companion.virtual.IVirtualDeviceListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.virtual.IVirtualDeviceListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVirtualDeviceCreated(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVirtualDeviceClosed(readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.virtual.IVirtualDeviceListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.virtual.IVirtualDeviceListener.DESCRIPTOR;
            }

            @Override // android.companion.virtual.IVirtualDeviceListener
            public void onVirtualDeviceCreated(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceListener
            public void onVirtualDeviceClosed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceListener.DESCRIPTOR);
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
