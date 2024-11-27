package android.hardware.health;

/* loaded from: classes.dex */
public interface IHealth extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$health$IHealth".replace('$', '.');
    public static final java.lang.String HASH = "3bab6273a5491102b29c9d7a1f0efa749533f46d";
    public static final int STATUS_CALLBACK_DIED = 4;
    public static final int STATUS_UNKNOWN = 2;
    public static final int VERSION = 3;

    android.hardware.health.BatteryHealthData getBatteryHealthData() throws android.os.RemoteException;

    int getCapacity() throws android.os.RemoteException;

    int getChargeCounterUah() throws android.os.RemoteException;

    int getChargeStatus() throws android.os.RemoteException;

    int getChargingPolicy() throws android.os.RemoteException;

    int getCurrentAverageMicroamps() throws android.os.RemoteException;

    int getCurrentNowMicroamps() throws android.os.RemoteException;

    android.hardware.health.DiskStats[] getDiskStats() throws android.os.RemoteException;

    long getEnergyCounterNwh() throws android.os.RemoteException;

    android.hardware.health.HealthInfo getHealthInfo() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.hardware.health.StorageInfo[] getStorageInfo() throws android.os.RemoteException;

    void registerCallback(android.hardware.health.IHealthInfoCallback iHealthInfoCallback) throws android.os.RemoteException;

    void setChargingPolicy(int i) throws android.os.RemoteException;

    void unregisterCallback(android.hardware.health.IHealthInfoCallback iHealthInfoCallback) throws android.os.RemoteException;

    void update() throws android.os.RemoteException;

    public static class Default implements android.hardware.health.IHealth {
        @Override // android.hardware.health.IHealth
        public void registerCallback(android.hardware.health.IHealthInfoCallback iHealthInfoCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.health.IHealth
        public void unregisterCallback(android.hardware.health.IHealthInfoCallback iHealthInfoCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.health.IHealth
        public void update() throws android.os.RemoteException {
        }

        @Override // android.hardware.health.IHealth
        public int getChargeCounterUah() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.health.IHealth
        public int getCurrentNowMicroamps() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.health.IHealth
        public int getCurrentAverageMicroamps() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.health.IHealth
        public int getCapacity() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.health.IHealth
        public long getEnergyCounterNwh() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.health.IHealth
        public int getChargeStatus() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.health.IHealth
        public android.hardware.health.StorageInfo[] getStorageInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.health.IHealth
        public android.hardware.health.DiskStats[] getDiskStats() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.health.IHealth
        public android.hardware.health.HealthInfo getHealthInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.health.IHealth
        public void setChargingPolicy(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.health.IHealth
        public int getChargingPolicy() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.health.IHealth
        public android.hardware.health.BatteryHealthData getBatteryHealthData() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.health.IHealth
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.health.IHealth
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.health.IHealth {
        static final int TRANSACTION_getBatteryHealthData = 15;
        static final int TRANSACTION_getCapacity = 7;
        static final int TRANSACTION_getChargeCounterUah = 4;
        static final int TRANSACTION_getChargeStatus = 9;
        static final int TRANSACTION_getChargingPolicy = 14;
        static final int TRANSACTION_getCurrentAverageMicroamps = 6;
        static final int TRANSACTION_getCurrentNowMicroamps = 5;
        static final int TRANSACTION_getDiskStats = 11;
        static final int TRANSACTION_getEnergyCounterNwh = 8;
        static final int TRANSACTION_getHealthInfo = 12;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getStorageInfo = 10;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_setChargingPolicy = 13;
        static final int TRANSACTION_unregisterCallback = 2;
        static final int TRANSACTION_update = 3;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.health.IHealth.DESCRIPTOR);
        }

        public static android.hardware.health.IHealth asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.health.IHealth.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.health.IHealth)) {
                return (android.hardware.health.IHealth) queryLocalInterface;
            }
            return new android.hardware.health.IHealth.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.health.IHealth.DESCRIPTOR;
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
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.health.IHealthInfoCallback asInterface = android.hardware.health.IHealthInfoCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.hardware.health.IHealthInfoCallback asInterface2 = android.hardware.health.IHealthInfoCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    update();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int chargeCounterUah = getChargeCounterUah();
                    parcel2.writeNoException();
                    parcel2.writeInt(chargeCounterUah);
                    return true;
                case 5:
                    int currentNowMicroamps = getCurrentNowMicroamps();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentNowMicroamps);
                    return true;
                case 6:
                    int currentAverageMicroamps = getCurrentAverageMicroamps();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentAverageMicroamps);
                    return true;
                case 7:
                    int capacity = getCapacity();
                    parcel2.writeNoException();
                    parcel2.writeInt(capacity);
                    return true;
                case 8:
                    long energyCounterNwh = getEnergyCounterNwh();
                    parcel2.writeNoException();
                    parcel2.writeLong(energyCounterNwh);
                    return true;
                case 9:
                    int chargeStatus = getChargeStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(chargeStatus);
                    return true;
                case 10:
                    android.hardware.health.StorageInfo[] storageInfo = getStorageInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(storageInfo, 1);
                    return true;
                case 11:
                    android.hardware.health.DiskStats[] diskStats = getDiskStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(diskStats, 1);
                    return true;
                case 12:
                    android.hardware.health.HealthInfo healthInfo = getHealthInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(healthInfo, 1);
                    return true;
                case 13:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setChargingPolicy(readInt);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int chargingPolicy = getChargingPolicy();
                    parcel2.writeNoException();
                    parcel2.writeInt(chargingPolicy);
                    return true;
                case 15:
                    android.hardware.health.BatteryHealthData batteryHealthData = getBatteryHealthData();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(batteryHealthData, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.health.IHealth {
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
                return android.hardware.health.IHealth.DESCRIPTOR;
            }

            @Override // android.hardware.health.IHealth
            public void registerCallback(android.hardware.health.IHealthInfoCallback iHealthInfoCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    obtain.writeStrongInterface(iHealthInfoCallback);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method registerCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public void unregisterCallback(android.hardware.health.IHealthInfoCallback iHealthInfoCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    obtain.writeStrongInterface(iHealthInfoCallback);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method unregisterCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public void update() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method update is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public int getChargeCounterUah() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getChargeCounterUah is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public int getCurrentNowMicroamps() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getCurrentNowMicroamps is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public int getCurrentAverageMicroamps() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getCurrentAverageMicroamps is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public int getCapacity() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getCapacity is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public long getEnergyCounterNwh() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getEnergyCounterNwh is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public int getChargeStatus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getChargeStatus is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public android.hardware.health.StorageInfo[] getStorageInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getStorageInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.health.StorageInfo[]) obtain2.createTypedArray(android.hardware.health.StorageInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public android.hardware.health.DiskStats[] getDiskStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getDiskStats is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.health.DiskStats[]) obtain2.createTypedArray(android.hardware.health.DiskStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public android.hardware.health.HealthInfo getHealthInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getHealthInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.health.HealthInfo) obtain2.readTypedObject(android.hardware.health.HealthInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public void setChargingPolicy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setChargingPolicy is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public int getChargingPolicy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getChargingPolicy is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public android.hardware.health.BatteryHealthData getBatteryHealthData() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getBatteryHealthData is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.health.BatteryHealthData) obtain2.readTypedObject(android.hardware.health.BatteryHealthData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealth
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
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

            @Override // android.hardware.health.IHealth
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.health.IHealth.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.health.IHealth.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }
    }
}
