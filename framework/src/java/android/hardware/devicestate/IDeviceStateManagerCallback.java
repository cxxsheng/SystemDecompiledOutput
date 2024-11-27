package android.hardware.devicestate;

/* loaded from: classes2.dex */
public interface IDeviceStateManagerCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.devicestate.IDeviceStateManagerCallback";

    void onDeviceStateInfoChanged(android.hardware.devicestate.DeviceStateInfo deviceStateInfo) throws android.os.RemoteException;

    void onRequestActive(android.os.IBinder iBinder) throws android.os.RemoteException;

    void onRequestCanceled(android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.hardware.devicestate.IDeviceStateManagerCallback {
        @Override // android.hardware.devicestate.IDeviceStateManagerCallback
        public void onDeviceStateInfoChanged(android.hardware.devicestate.DeviceStateInfo deviceStateInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.devicestate.IDeviceStateManagerCallback
        public void onRequestActive(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.devicestate.IDeviceStateManagerCallback
        public void onRequestCanceled(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.devicestate.IDeviceStateManagerCallback {
        static final int TRANSACTION_onDeviceStateInfoChanged = 1;
        static final int TRANSACTION_onRequestActive = 2;
        static final int TRANSACTION_onRequestCanceled = 3;

        public Stub() {
            attachInterface(this, android.hardware.devicestate.IDeviceStateManagerCallback.DESCRIPTOR);
        }

        public static android.hardware.devicestate.IDeviceStateManagerCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.devicestate.IDeviceStateManagerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.devicestate.IDeviceStateManagerCallback)) {
                return (android.hardware.devicestate.IDeviceStateManagerCallback) queryLocalInterface;
            }
            return new android.hardware.devicestate.IDeviceStateManagerCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDeviceStateInfoChanged";
                case 2:
                    return "onRequestActive";
                case 3:
                    return "onRequestCanceled";
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
                parcel.enforceInterface(android.hardware.devicestate.IDeviceStateManagerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.devicestate.IDeviceStateManagerCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.devicestate.DeviceStateInfo deviceStateInfo = (android.hardware.devicestate.DeviceStateInfo) parcel.readTypedObject(android.hardware.devicestate.DeviceStateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDeviceStateInfoChanged(deviceStateInfo);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onRequestActive(readStrongBinder);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onRequestCanceled(readStrongBinder2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.devicestate.IDeviceStateManagerCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.devicestate.IDeviceStateManagerCallback.DESCRIPTOR;
            }

            @Override // android.hardware.devicestate.IDeviceStateManagerCallback
            public void onDeviceStateInfoChanged(android.hardware.devicestate.DeviceStateInfo deviceStateInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.devicestate.IDeviceStateManagerCallback.DESCRIPTOR);
                    obtain.writeTypedObject(deviceStateInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManagerCallback
            public void onRequestActive(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.devicestate.IDeviceStateManagerCallback.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManagerCallback
            public void onRequestCanceled(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.devicestate.IDeviceStateManagerCallback.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
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
