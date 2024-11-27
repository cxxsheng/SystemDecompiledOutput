package android.companion.virtual.sensor;

/* loaded from: classes.dex */
public interface IVirtualSensorCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.virtual.sensor.IVirtualSensorCallback";

    void onConfigurationChanged(android.companion.virtual.sensor.VirtualSensor virtualSensor, boolean z, int i, int i2) throws android.os.RemoteException;

    void onDirectChannelConfigured(int i, android.companion.virtual.sensor.VirtualSensor virtualSensor, int i2, int i3) throws android.os.RemoteException;

    void onDirectChannelCreated(int i, android.os.SharedMemory sharedMemory) throws android.os.RemoteException;

    void onDirectChannelDestroyed(int i) throws android.os.RemoteException;

    public static class Default implements android.companion.virtual.sensor.IVirtualSensorCallback {
        @Override // android.companion.virtual.sensor.IVirtualSensorCallback
        public void onConfigurationChanged(android.companion.virtual.sensor.VirtualSensor virtualSensor, boolean z, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.sensor.IVirtualSensorCallback
        public void onDirectChannelCreated(int i, android.os.SharedMemory sharedMemory) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.sensor.IVirtualSensorCallback
        public void onDirectChannelDestroyed(int i) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.sensor.IVirtualSensorCallback
        public void onDirectChannelConfigured(int i, android.companion.virtual.sensor.VirtualSensor virtualSensor, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.virtual.sensor.IVirtualSensorCallback {
        static final int TRANSACTION_onConfigurationChanged = 1;
        static final int TRANSACTION_onDirectChannelConfigured = 4;
        static final int TRANSACTION_onDirectChannelCreated = 2;
        static final int TRANSACTION_onDirectChannelDestroyed = 3;

        public Stub() {
            attachInterface(this, android.companion.virtual.sensor.IVirtualSensorCallback.DESCRIPTOR);
        }

        public static android.companion.virtual.sensor.IVirtualSensorCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.virtual.sensor.IVirtualSensorCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.virtual.sensor.IVirtualSensorCallback)) {
                return (android.companion.virtual.sensor.IVirtualSensorCallback) queryLocalInterface;
            }
            return new android.companion.virtual.sensor.IVirtualSensorCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onConfigurationChanged";
                case 2:
                    return "onDirectChannelCreated";
                case 3:
                    return "onDirectChannelDestroyed";
                case 4:
                    return "onDirectChannelConfigured";
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
                parcel.enforceInterface(android.companion.virtual.sensor.IVirtualSensorCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.virtual.sensor.IVirtualSensorCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.companion.virtual.sensor.VirtualSensor virtualSensor = (android.companion.virtual.sensor.VirtualSensor) parcel.readTypedObject(android.companion.virtual.sensor.VirtualSensor.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onConfigurationChanged(virtualSensor, readBoolean, readInt, readInt2);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    android.os.SharedMemory sharedMemory = (android.os.SharedMemory) parcel.readTypedObject(android.os.SharedMemory.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDirectChannelCreated(readInt3, sharedMemory);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDirectChannelDestroyed(readInt4);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    android.companion.virtual.sensor.VirtualSensor virtualSensor2 = (android.companion.virtual.sensor.VirtualSensor) parcel.readTypedObject(android.companion.virtual.sensor.VirtualSensor.CREATOR);
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDirectChannelConfigured(readInt5, virtualSensor2, readInt6, readInt7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.virtual.sensor.IVirtualSensorCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.virtual.sensor.IVirtualSensorCallback.DESCRIPTOR;
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onConfigurationChanged(android.companion.virtual.sensor.VirtualSensor virtualSensor, boolean z, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.sensor.IVirtualSensorCallback.DESCRIPTOR);
                    obtain.writeTypedObject(virtualSensor, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelCreated(int i, android.os.SharedMemory sharedMemory) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.sensor.IVirtualSensorCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sharedMemory, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelDestroyed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.sensor.IVirtualSensorCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelConfigured(int i, android.companion.virtual.sensor.VirtualSensor virtualSensor, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.sensor.IVirtualSensorCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(virtualSensor, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
