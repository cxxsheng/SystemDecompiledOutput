package android.hardware.gnss.measurement_corrections;

/* loaded from: classes2.dex */
public interface IMeasurementCorrectionsCallback extends android.os.IInterface {
    public static final int CAPABILITY_EXCESS_PATH_LENGTH = 2;
    public static final int CAPABILITY_LOS_SATS = 1;
    public static final int CAPABILITY_REFLECTING_PLANE = 4;
    public static final java.lang.String DESCRIPTOR = "android$hardware$gnss$measurement_corrections$IMeasurementCorrectionsCallback".replace('$', '.');
    public static final java.lang.String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void setCapabilitiesCb(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsCallback {
        @Override // android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsCallback
        public void setCapabilitiesCb(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_setCapabilitiesCb = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsCallback)) {
                return (android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsCallback) queryLocalInterface;
            }
            return new android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setCapabilitiesCb";
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCapabilitiesCb(readInt);
                    parcel2.writeNoException();
                    break;
            }
            return true;
        }

        private static class Proxy implements android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsCallback {
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

            @Override // android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsCallback
            public void setCapabilitiesCb(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setCapabilitiesCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsCallback
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

            @Override // android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsCallback
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