package android.hardware.thermal;

/* loaded from: classes2.dex */
public interface IThermalChangedCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$thermal$IThermalChangedCallback".replace('$', '.');
    public static final java.lang.String HASH = "2f49c78011338b42b43d5d0e250d9b520850cc1f";
    public static final int VERSION = 2;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void notifyThrottling(android.hardware.thermal.Temperature temperature) throws android.os.RemoteException;

    public static class Default implements android.hardware.thermal.IThermalChangedCallback {
        @Override // android.hardware.thermal.IThermalChangedCallback
        public void notifyThrottling(android.hardware.thermal.Temperature temperature) throws android.os.RemoteException {
        }

        @Override // android.hardware.thermal.IThermalChangedCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.thermal.IThermalChangedCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.thermal.IThermalChangedCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_notifyThrottling = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.thermal.IThermalChangedCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.thermal.IThermalChangedCallback)) {
                return (android.hardware.thermal.IThermalChangedCallback) queryLocalInterface;
            }
            return new android.hardware.thermal.IThermalChangedCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "notifyThrottling";
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
                    android.hardware.thermal.Temperature temperature = (android.hardware.thermal.Temperature) parcel.readTypedObject(android.hardware.thermal.Temperature.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyThrottling(temperature);
                    break;
            }
            return true;
        }

        private static class Proxy implements android.hardware.thermal.IThermalChangedCallback {
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

            @Override // android.hardware.thermal.IThermalChangedCallback
            public void notifyThrottling(android.hardware.thermal.Temperature temperature) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(temperature, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyThrottling is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.thermal.IThermalChangedCallback
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

            @Override // android.hardware.thermal.IThermalChangedCallback
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
