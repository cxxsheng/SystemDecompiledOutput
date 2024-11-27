package android.hardware.power.stats;

/* loaded from: classes.dex */
public interface IPowerStats extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$power$stats$IPowerStats".replace('$', '.');
    public static final java.lang.String HASH = "c3e113101b731c666717eb579492efa287a8f529";
    public static final int VERSION = 2;

    android.hardware.power.stats.EnergyConsumerResult[] getEnergyConsumed(int[] iArr) throws android.os.RemoteException;

    android.hardware.power.stats.EnergyConsumer[] getEnergyConsumerInfo() throws android.os.RemoteException;

    android.hardware.power.stats.Channel[] getEnergyMeterInfo() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.hardware.power.stats.PowerEntity[] getPowerEntityInfo() throws android.os.RemoteException;

    android.hardware.power.stats.StateResidencyResult[] getStateResidency(int[] iArr) throws android.os.RemoteException;

    android.hardware.power.stats.EnergyMeasurement[] readEnergyMeter(int[] iArr) throws android.os.RemoteException;

    public static class Default implements android.hardware.power.stats.IPowerStats {
        @Override // android.hardware.power.stats.IPowerStats
        public android.hardware.power.stats.PowerEntity[] getPowerEntityInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.power.stats.IPowerStats
        public android.hardware.power.stats.StateResidencyResult[] getStateResidency(int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.power.stats.IPowerStats
        public android.hardware.power.stats.EnergyConsumer[] getEnergyConsumerInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.power.stats.IPowerStats
        public android.hardware.power.stats.EnergyConsumerResult[] getEnergyConsumed(int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.power.stats.IPowerStats
        public android.hardware.power.stats.Channel[] getEnergyMeterInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.power.stats.IPowerStats
        public android.hardware.power.stats.EnergyMeasurement[] readEnergyMeter(int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.power.stats.IPowerStats
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.power.stats.IPowerStats
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.power.stats.IPowerStats {
        static final int TRANSACTION_getEnergyConsumed = 4;
        static final int TRANSACTION_getEnergyConsumerInfo = 3;
        static final int TRANSACTION_getEnergyMeterInfo = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getPowerEntityInfo = 1;
        static final int TRANSACTION_getStateResidency = 2;
        static final int TRANSACTION_readEnergyMeter = 6;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.power.stats.IPowerStats.DESCRIPTOR);
        }

        public static android.hardware.power.stats.IPowerStats asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.power.stats.IPowerStats.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.power.stats.IPowerStats)) {
                return (android.hardware.power.stats.IPowerStats) queryLocalInterface;
            }
            return new android.hardware.power.stats.IPowerStats.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getPowerEntityInfo";
                case 2:
                    return "getStateResidency";
                case 3:
                    return "getEnergyConsumerInfo";
                case 4:
                    return "getEnergyConsumed";
                case 5:
                    return "getEnergyMeterInfo";
                case 6:
                    return "readEnergyMeter";
                case TRANSACTION_getInterfaceHash /* 16777214 */:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.power.stats.IPowerStats.DESCRIPTOR;
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
                    android.hardware.power.stats.PowerEntity[] powerEntityInfo = getPowerEntityInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(powerEntityInfo, 1);
                    return true;
                case 2:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    android.hardware.power.stats.StateResidencyResult[] stateResidency = getStateResidency(createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(stateResidency, 1);
                    return true;
                case 3:
                    android.hardware.power.stats.EnergyConsumer[] energyConsumerInfo = getEnergyConsumerInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(energyConsumerInfo, 1);
                    return true;
                case 4:
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    android.hardware.power.stats.EnergyConsumerResult[] energyConsumed = getEnergyConsumed(createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(energyConsumed, 1);
                    return true;
                case 5:
                    android.hardware.power.stats.Channel[] energyMeterInfo = getEnergyMeterInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(energyMeterInfo, 1);
                    return true;
                case 6:
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    android.hardware.power.stats.EnergyMeasurement[] readEnergyMeter = readEnergyMeter(createIntArray3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(readEnergyMeter, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.power.stats.IPowerStats {
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
                return android.hardware.power.stats.IPowerStats.DESCRIPTOR;
            }

            @Override // android.hardware.power.stats.IPowerStats
            public android.hardware.power.stats.PowerEntity[] getPowerEntityInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.power.stats.IPowerStats.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getPowerEntityInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.power.stats.PowerEntity[]) obtain2.createTypedArray(android.hardware.power.stats.PowerEntity.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.stats.IPowerStats
            public android.hardware.power.stats.StateResidencyResult[] getStateResidency(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.power.stats.IPowerStats.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getStateResidency is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.power.stats.StateResidencyResult[]) obtain2.createTypedArray(android.hardware.power.stats.StateResidencyResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.stats.IPowerStats
            public android.hardware.power.stats.EnergyConsumer[] getEnergyConsumerInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.power.stats.IPowerStats.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getEnergyConsumerInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.power.stats.EnergyConsumer[]) obtain2.createTypedArray(android.hardware.power.stats.EnergyConsumer.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.stats.IPowerStats
            public android.hardware.power.stats.EnergyConsumerResult[] getEnergyConsumed(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.power.stats.IPowerStats.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getEnergyConsumed is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.power.stats.EnergyConsumerResult[]) obtain2.createTypedArray(android.hardware.power.stats.EnergyConsumerResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.stats.IPowerStats
            public android.hardware.power.stats.Channel[] getEnergyMeterInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.power.stats.IPowerStats.DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getEnergyMeterInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.power.stats.Channel[]) obtain2.createTypedArray(android.hardware.power.stats.Channel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.stats.IPowerStats
            public android.hardware.power.stats.EnergyMeasurement[] readEnergyMeter(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.power.stats.IPowerStats.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method readEnergyMeter is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.power.stats.EnergyMeasurement[]) obtain2.createTypedArray(android.hardware.power.stats.EnergyMeasurement.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.stats.IPowerStats
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.power.stats.IPowerStats.DESCRIPTOR);
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

            @Override // android.hardware.power.stats.IPowerStats
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.power.stats.IPowerStats.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.power.stats.IPowerStats.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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

        public int getMaxTransactionId() {
            return TRANSACTION_getInterfaceHash;
        }
    }
}
