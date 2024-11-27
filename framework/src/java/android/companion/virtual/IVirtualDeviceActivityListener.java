package android.companion.virtual;

/* loaded from: classes.dex */
public interface IVirtualDeviceActivityListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.virtual.IVirtualDeviceActivityListener";

    void onDisplayEmpty(int i) throws android.os.RemoteException;

    void onTopActivityChanged(int i, android.content.ComponentName componentName, int i2) throws android.os.RemoteException;

    public static class Default implements android.companion.virtual.IVirtualDeviceActivityListener {
        @Override // android.companion.virtual.IVirtualDeviceActivityListener
        public void onTopActivityChanged(int i, android.content.ComponentName componentName, int i2) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDeviceActivityListener
        public void onDisplayEmpty(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.virtual.IVirtualDeviceActivityListener {
        static final int TRANSACTION_onDisplayEmpty = 2;
        static final int TRANSACTION_onTopActivityChanged = 1;

        public Stub() {
            attachInterface(this, android.companion.virtual.IVirtualDeviceActivityListener.DESCRIPTOR);
        }

        public static android.companion.virtual.IVirtualDeviceActivityListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.virtual.IVirtualDeviceActivityListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.virtual.IVirtualDeviceActivityListener)) {
                return (android.companion.virtual.IVirtualDeviceActivityListener) queryLocalInterface;
            }
            return new android.companion.virtual.IVirtualDeviceActivityListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTopActivityChanged";
                case 2:
                    return "onDisplayEmpty";
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
                parcel.enforceInterface(android.companion.virtual.IVirtualDeviceActivityListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.virtual.IVirtualDeviceActivityListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTopActivityChanged(readInt, componentName, readInt2);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayEmpty(readInt3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.virtual.IVirtualDeviceActivityListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.virtual.IVirtualDeviceActivityListener.DESCRIPTOR;
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onTopActivityChanged(int i, android.content.ComponentName componentName, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceActivityListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onDisplayEmpty(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.IVirtualDeviceActivityListener.DESCRIPTOR);
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
