package android.os;

/* loaded from: classes3.dex */
public interface IHardwarePropertiesManager extends android.os.IInterface {
    android.os.CpuUsageInfo[] getCpuUsages(java.lang.String str) throws android.os.RemoteException;

    float[] getDeviceTemperatures(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    float[] getFanSpeeds(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.os.IHardwarePropertiesManager {
        @Override // android.os.IHardwarePropertiesManager
        public float[] getDeviceTemperatures(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IHardwarePropertiesManager
        public android.os.CpuUsageInfo[] getCpuUsages(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IHardwarePropertiesManager
        public float[] getFanSpeeds(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IHardwarePropertiesManager {
        public static final java.lang.String DESCRIPTOR = "android.os.IHardwarePropertiesManager";
        static final int TRANSACTION_getCpuUsages = 2;
        static final int TRANSACTION_getDeviceTemperatures = 1;
        static final int TRANSACTION_getFanSpeeds = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.os.IHardwarePropertiesManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IHardwarePropertiesManager)) {
                return (android.os.IHardwarePropertiesManager) queryLocalInterface;
            }
            return new android.os.IHardwarePropertiesManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDeviceTemperatures";
                case 2:
                    return "getCpuUsages";
                case 3:
                    return "getFanSpeeds";
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
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float[] deviceTemperatures = getDeviceTemperatures(readString, readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(deviceTemperatures);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.CpuUsageInfo[] cpuUsages = getCpuUsages(readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(cpuUsages, 1);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    float[] fanSpeeds = getFanSpeeds(readString3);
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(fanSpeeds);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IHardwarePropertiesManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IHardwarePropertiesManager.Stub.DESCRIPTOR;
            }

            @Override // android.os.IHardwarePropertiesManager
            public float[] getDeviceTemperatures(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IHardwarePropertiesManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createFloatArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IHardwarePropertiesManager
            public android.os.CpuUsageInfo[] getCpuUsages(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IHardwarePropertiesManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.CpuUsageInfo[]) obtain2.createTypedArray(android.os.CpuUsageInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IHardwarePropertiesManager
            public float[] getFanSpeeds(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IHardwarePropertiesManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
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
            return 2;
        }
    }
}
