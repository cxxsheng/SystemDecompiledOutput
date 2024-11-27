package android.os;

/* loaded from: classes3.dex */
public interface IPowerStatsService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IPowerStatsService";
    public static final java.lang.String KEY_ENERGY = "energy";
    public static final java.lang.String KEY_MONITORS = "monitors";
    public static final java.lang.String KEY_TIMESTAMPS = "timestamps";
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_UNSUPPORTED_POWER_MONITOR = 1;

    void getPowerMonitorReadings(int[] iArr, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void getSupportedPowerMonitors(android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    public static class Default implements android.os.IPowerStatsService {
        @Override // android.os.IPowerStatsService
        public void getSupportedPowerMonitors(android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerStatsService
        public void getPowerMonitorReadings(int[] iArr, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IPowerStatsService {
        static final int TRANSACTION_getPowerMonitorReadings = 2;
        static final int TRANSACTION_getSupportedPowerMonitors = 1;

        public Stub() {
            attachInterface(this, android.os.IPowerStatsService.DESCRIPTOR);
        }

        public static android.os.IPowerStatsService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IPowerStatsService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IPowerStatsService)) {
                return (android.os.IPowerStatsService) queryLocalInterface;
            }
            return new android.os.IPowerStatsService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSupportedPowerMonitors";
                case 2:
                    return "getPowerMonitorReadings";
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
                parcel.enforceInterface(android.os.IPowerStatsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IPowerStatsService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSupportedPowerMonitors(resultReceiver);
                    return true;
                case 2:
                    int[] createIntArray = parcel.createIntArray();
                    android.os.ResultReceiver resultReceiver2 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    getPowerMonitorReadings(createIntArray, resultReceiver2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IPowerStatsService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IPowerStatsService.DESCRIPTOR;
            }

            @Override // android.os.IPowerStatsService
            public void getSupportedPowerMonitors(android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IPowerStatsService.DESCRIPTOR);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerStatsService
            public void getPowerMonitorReadings(int[] iArr, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IPowerStatsService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(resultReceiver, 0);
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
