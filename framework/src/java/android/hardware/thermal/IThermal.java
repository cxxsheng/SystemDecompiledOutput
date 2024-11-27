package android.hardware.thermal;

/* loaded from: classes2.dex */
public interface IThermal extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$thermal$IThermal".replace('$', '.');
    public static final java.lang.String HASH = "2f49c78011338b42b43d5d0e250d9b520850cc1f";
    public static final int VERSION = 2;

    android.hardware.thermal.CoolingDevice[] getCoolingDevices() throws android.os.RemoteException;

    android.hardware.thermal.CoolingDevice[] getCoolingDevicesWithType(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.hardware.thermal.TemperatureThreshold[] getTemperatureThresholds() throws android.os.RemoteException;

    android.hardware.thermal.TemperatureThreshold[] getTemperatureThresholdsWithType(int i) throws android.os.RemoteException;

    android.hardware.thermal.Temperature[] getTemperatures() throws android.os.RemoteException;

    android.hardware.thermal.Temperature[] getTemperaturesWithType(int i) throws android.os.RemoteException;

    void registerCoolingDeviceChangedCallbackWithType(android.hardware.thermal.ICoolingDeviceChangedCallback iCoolingDeviceChangedCallback, int i) throws android.os.RemoteException;

    void registerThermalChangedCallback(android.hardware.thermal.IThermalChangedCallback iThermalChangedCallback) throws android.os.RemoteException;

    void registerThermalChangedCallbackWithType(android.hardware.thermal.IThermalChangedCallback iThermalChangedCallback, int i) throws android.os.RemoteException;

    void unregisterCoolingDeviceChangedCallback(android.hardware.thermal.ICoolingDeviceChangedCallback iCoolingDeviceChangedCallback) throws android.os.RemoteException;

    void unregisterThermalChangedCallback(android.hardware.thermal.IThermalChangedCallback iThermalChangedCallback) throws android.os.RemoteException;

    public static class Default implements android.hardware.thermal.IThermal {
        @Override // android.hardware.thermal.IThermal
        public android.hardware.thermal.CoolingDevice[] getCoolingDevices() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public android.hardware.thermal.CoolingDevice[] getCoolingDevicesWithType(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public android.hardware.thermal.Temperature[] getTemperatures() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public android.hardware.thermal.Temperature[] getTemperaturesWithType(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public android.hardware.thermal.TemperatureThreshold[] getTemperatureThresholds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public android.hardware.thermal.TemperatureThreshold[] getTemperatureThresholdsWithType(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.thermal.IThermal
        public void registerThermalChangedCallback(android.hardware.thermal.IThermalChangedCallback iThermalChangedCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.thermal.IThermal
        public void registerThermalChangedCallbackWithType(android.hardware.thermal.IThermalChangedCallback iThermalChangedCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.thermal.IThermal
        public void unregisterThermalChangedCallback(android.hardware.thermal.IThermalChangedCallback iThermalChangedCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.thermal.IThermal
        public void registerCoolingDeviceChangedCallbackWithType(android.hardware.thermal.ICoolingDeviceChangedCallback iCoolingDeviceChangedCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.thermal.IThermal
        public void unregisterCoolingDeviceChangedCallback(android.hardware.thermal.ICoolingDeviceChangedCallback iCoolingDeviceChangedCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.thermal.IThermal
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.thermal.IThermal
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.thermal.IThermal {
        static final int TRANSACTION_getCoolingDevices = 1;
        static final int TRANSACTION_getCoolingDevicesWithType = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getTemperatureThresholds = 5;
        static final int TRANSACTION_getTemperatureThresholdsWithType = 6;
        static final int TRANSACTION_getTemperatures = 3;
        static final int TRANSACTION_getTemperaturesWithType = 4;
        static final int TRANSACTION_registerCoolingDeviceChangedCallbackWithType = 10;
        static final int TRANSACTION_registerThermalChangedCallback = 7;
        static final int TRANSACTION_registerThermalChangedCallbackWithType = 8;
        static final int TRANSACTION_unregisterCoolingDeviceChangedCallback = 11;
        static final int TRANSACTION_unregisterThermalChangedCallback = 9;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.thermal.IThermal asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.thermal.IThermal)) {
                return (android.hardware.thermal.IThermal) queryLocalInterface;
            }
            return new android.hardware.thermal.IThermal.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getCoolingDevices";
                case 2:
                    return "getCoolingDevicesWithType";
                case 3:
                    return "getTemperatures";
                case 4:
                    return "getTemperaturesWithType";
                case 5:
                    return "getTemperatureThresholds";
                case 6:
                    return "getTemperatureThresholdsWithType";
                case 7:
                    return "registerThermalChangedCallback";
                case 8:
                    return "registerThermalChangedCallbackWithType";
                case 9:
                    return "unregisterThermalChangedCallback";
                case 10:
                    return "registerCoolingDeviceChangedCallbackWithType";
                case 11:
                    return "unregisterCoolingDeviceChangedCallback";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
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
            java.lang.String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.thermal.CoolingDevice[] coolingDevices = getCoolingDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(coolingDevices, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.thermal.CoolingDevice[] coolingDevicesWithType = getCoolingDevicesWithType(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(coolingDevicesWithType, 1);
                    return true;
                case 3:
                    android.hardware.thermal.Temperature[] temperatures = getTemperatures();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(temperatures, 1);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.thermal.Temperature[] temperaturesWithType = getTemperaturesWithType(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(temperaturesWithType, 1);
                    return true;
                case 5:
                    android.hardware.thermal.TemperatureThreshold[] temperatureThresholds = getTemperatureThresholds();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(temperatureThresholds, 1);
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.thermal.TemperatureThreshold[] temperatureThresholdsWithType = getTemperatureThresholdsWithType(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(temperatureThresholdsWithType, 1);
                    return true;
                case 7:
                    android.hardware.thermal.IThermalChangedCallback asInterface = android.hardware.thermal.IThermalChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerThermalChangedCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.hardware.thermal.IThermalChangedCallback asInterface2 = android.hardware.thermal.IThermalChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerThermalChangedCallbackWithType(asInterface2, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.hardware.thermal.IThermalChangedCallback asInterface3 = android.hardware.thermal.IThermalChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterThermalChangedCallback(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.hardware.thermal.ICoolingDeviceChangedCallback asInterface4 = android.hardware.thermal.ICoolingDeviceChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCoolingDeviceChangedCallbackWithType(asInterface4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.hardware.thermal.ICoolingDeviceChangedCallback asInterface5 = android.hardware.thermal.ICoolingDeviceChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCoolingDeviceChangedCallback(asInterface5);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.thermal.IThermal {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.thermal.IThermal
            public android.hardware.thermal.CoolingDevice[] getCoolingDevices() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getCoolingDevices is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.thermal.CoolingDevice[]) obtain2.createTypedArray(android.hardware.thermal.CoolingDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public android.hardware.thermal.CoolingDevice[] getCoolingDevicesWithType(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getCoolingDevicesWithType is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.thermal.CoolingDevice[]) obtain2.createTypedArray(android.hardware.thermal.CoolingDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public android.hardware.thermal.Temperature[] getTemperatures() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getTemperatures is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.thermal.Temperature[]) obtain2.createTypedArray(android.hardware.thermal.Temperature.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public android.hardware.thermal.Temperature[] getTemperaturesWithType(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getTemperaturesWithType is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.thermal.Temperature[]) obtain2.createTypedArray(android.hardware.thermal.Temperature.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public android.hardware.thermal.TemperatureThreshold[] getTemperatureThresholds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getTemperatureThresholds is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.thermal.TemperatureThreshold[]) obtain2.createTypedArray(android.hardware.thermal.TemperatureThreshold.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public android.hardware.thermal.TemperatureThreshold[] getTemperatureThresholdsWithType(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getTemperatureThresholdsWithType is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.thermal.TemperatureThreshold[]) obtain2.createTypedArray(android.hardware.thermal.TemperatureThreshold.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void registerThermalChangedCallback(android.hardware.thermal.IThermalChangedCallback iThermalChangedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iThermalChangedCallback);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method registerThermalChangedCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void registerThermalChangedCallbackWithType(android.hardware.thermal.IThermalChangedCallback iThermalChangedCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iThermalChangedCallback);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method registerThermalChangedCallbackWithType is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void unregisterThermalChangedCallback(android.hardware.thermal.IThermalChangedCallback iThermalChangedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iThermalChangedCallback);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method unregisterThermalChangedCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void registerCoolingDeviceChangedCallbackWithType(android.hardware.thermal.ICoolingDeviceChangedCallback iCoolingDeviceChangedCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iCoolingDeviceChangedCallback);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method registerCoolingDeviceChangedCallbackWithType is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public void unregisterCoolingDeviceChangedCallback(android.hardware.thermal.ICoolingDeviceChangedCallback iCoolingDeviceChangedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iCoolingDeviceChangedCallback);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method unregisterCoolingDeviceChangedCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermal
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.thermal.IThermal
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (java.lang.Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }
}
