package android.os;

/* loaded from: classes3.dex */
public interface IThermalService extends android.os.IInterface {
    android.os.CoolingDevice[] getCurrentCoolingDevices() throws android.os.RemoteException;

    android.os.CoolingDevice[] getCurrentCoolingDevicesWithType(int i) throws android.os.RemoteException;

    android.os.Temperature[] getCurrentTemperatures() throws android.os.RemoteException;

    android.os.Temperature[] getCurrentTemperaturesWithType(int i) throws android.os.RemoteException;

    int getCurrentThermalStatus() throws android.os.RemoteException;

    float getThermalHeadroom(int i) throws android.os.RemoteException;

    float[] getThermalHeadroomThresholds() throws android.os.RemoteException;

    boolean registerThermalEventListener(android.os.IThermalEventListener iThermalEventListener) throws android.os.RemoteException;

    boolean registerThermalEventListenerWithType(android.os.IThermalEventListener iThermalEventListener, int i) throws android.os.RemoteException;

    boolean registerThermalStatusListener(android.os.IThermalStatusListener iThermalStatusListener) throws android.os.RemoteException;

    boolean unregisterThermalEventListener(android.os.IThermalEventListener iThermalEventListener) throws android.os.RemoteException;

    boolean unregisterThermalStatusListener(android.os.IThermalStatusListener iThermalStatusListener) throws android.os.RemoteException;

    public static class Default implements android.os.IThermalService {
        @Override // android.os.IThermalService
        public boolean registerThermalEventListener(android.os.IThermalEventListener iThermalEventListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IThermalService
        public boolean registerThermalEventListenerWithType(android.os.IThermalEventListener iThermalEventListener, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IThermalService
        public boolean unregisterThermalEventListener(android.os.IThermalEventListener iThermalEventListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IThermalService
        public android.os.Temperature[] getCurrentTemperatures() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IThermalService
        public android.os.Temperature[] getCurrentTemperaturesWithType(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IThermalService
        public boolean registerThermalStatusListener(android.os.IThermalStatusListener iThermalStatusListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IThermalService
        public boolean unregisterThermalStatusListener(android.os.IThermalStatusListener iThermalStatusListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IThermalService
        public int getCurrentThermalStatus() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IThermalService
        public android.os.CoolingDevice[] getCurrentCoolingDevices() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IThermalService
        public android.os.CoolingDevice[] getCurrentCoolingDevicesWithType(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IThermalService
        public float getThermalHeadroom(int i) throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.os.IThermalService
        public float[] getThermalHeadroomThresholds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IThermalService {
        public static final java.lang.String DESCRIPTOR = "android.os.IThermalService";
        static final int TRANSACTION_getCurrentCoolingDevices = 9;
        static final int TRANSACTION_getCurrentCoolingDevicesWithType = 10;
        static final int TRANSACTION_getCurrentTemperatures = 4;
        static final int TRANSACTION_getCurrentTemperaturesWithType = 5;
        static final int TRANSACTION_getCurrentThermalStatus = 8;
        static final int TRANSACTION_getThermalHeadroom = 11;
        static final int TRANSACTION_getThermalHeadroomThresholds = 12;
        static final int TRANSACTION_registerThermalEventListener = 1;
        static final int TRANSACTION_registerThermalEventListenerWithType = 2;
        static final int TRANSACTION_registerThermalStatusListener = 6;
        static final int TRANSACTION_unregisterThermalEventListener = 3;
        static final int TRANSACTION_unregisterThermalStatusListener = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.os.IThermalService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IThermalService)) {
                return (android.os.IThermalService) queryLocalInterface;
            }
            return new android.os.IThermalService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerThermalEventListener";
                case 2:
                    return "registerThermalEventListenerWithType";
                case 3:
                    return "unregisterThermalEventListener";
                case 4:
                    return "getCurrentTemperatures";
                case 5:
                    return "getCurrentTemperaturesWithType";
                case 6:
                    return "registerThermalStatusListener";
                case 7:
                    return "unregisterThermalStatusListener";
                case 8:
                    return "getCurrentThermalStatus";
                case 9:
                    return "getCurrentCoolingDevices";
                case 10:
                    return "getCurrentCoolingDevicesWithType";
                case 11:
                    return "getThermalHeadroom";
                case 12:
                    return "getThermalHeadroomThresholds";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IThermalEventListener asInterface = android.os.IThermalEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerThermalEventListener = registerThermalEventListener(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerThermalEventListener);
                    return true;
                case 2:
                    android.os.IThermalEventListener asInterface2 = android.os.IThermalEventListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean registerThermalEventListenerWithType = registerThermalEventListenerWithType(asInterface2, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerThermalEventListenerWithType);
                    return true;
                case 3:
                    android.os.IThermalEventListener asInterface3 = android.os.IThermalEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterThermalEventListener = unregisterThermalEventListener(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterThermalEventListener);
                    return true;
                case 4:
                    android.os.Temperature[] currentTemperatures = getCurrentTemperatures();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(currentTemperatures, 1);
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.Temperature[] currentTemperaturesWithType = getCurrentTemperaturesWithType(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(currentTemperaturesWithType, 1);
                    return true;
                case 6:
                    android.os.IThermalStatusListener asInterface4 = android.os.IThermalStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerThermalStatusListener = registerThermalStatusListener(asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerThermalStatusListener);
                    return true;
                case 7:
                    android.os.IThermalStatusListener asInterface5 = android.os.IThermalStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterThermalStatusListener = unregisterThermalStatusListener(asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterThermalStatusListener);
                    return true;
                case 8:
                    int currentThermalStatus = getCurrentThermalStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentThermalStatus);
                    return true;
                case 9:
                    android.os.CoolingDevice[] currentCoolingDevices = getCurrentCoolingDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(currentCoolingDevices, 1);
                    return true;
                case 10:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.CoolingDevice[] currentCoolingDevicesWithType = getCurrentCoolingDevicesWithType(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(currentCoolingDevicesWithType, 1);
                    return true;
                case 11:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float thermalHeadroom = getThermalHeadroom(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeFloat(thermalHeadroom);
                    return true;
                case 12:
                    float[] thermalHeadroomThresholds = getThermalHeadroomThresholds();
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(thermalHeadroomThresholds);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IThermalService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IThermalService.Stub.DESCRIPTOR;
            }

            @Override // android.os.IThermalService
            public boolean registerThermalEventListener(android.os.IThermalEventListener iThermalEventListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IThermalService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iThermalEventListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public boolean registerThermalEventListenerWithType(android.os.IThermalEventListener iThermalEventListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IThermalService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iThermalEventListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public boolean unregisterThermalEventListener(android.os.IThermalEventListener iThermalEventListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IThermalService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iThermalEventListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public android.os.Temperature[] getCurrentTemperatures() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IThermalService.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Temperature[]) obtain2.createTypedArray(android.os.Temperature.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public android.os.Temperature[] getCurrentTemperaturesWithType(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IThermalService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Temperature[]) obtain2.createTypedArray(android.os.Temperature.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public boolean registerThermalStatusListener(android.os.IThermalStatusListener iThermalStatusListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IThermalService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iThermalStatusListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public boolean unregisterThermalStatusListener(android.os.IThermalStatusListener iThermalStatusListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IThermalService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iThermalStatusListener);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public int getCurrentThermalStatus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IThermalService.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public android.os.CoolingDevice[] getCurrentCoolingDevices() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IThermalService.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.CoolingDevice[]) obtain2.createTypedArray(android.os.CoolingDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public android.os.CoolingDevice[] getCurrentCoolingDevicesWithType(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IThermalService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.CoolingDevice[]) obtain2.createTypedArray(android.os.CoolingDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public float getThermalHeadroom(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IThermalService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IThermalService
            public float[] getThermalHeadroomThresholds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IThermalService.Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createFloatArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
