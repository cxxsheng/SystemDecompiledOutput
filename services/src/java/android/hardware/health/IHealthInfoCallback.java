package android.hardware.health;

/* loaded from: classes.dex */
public interface IHealthInfoCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$health$IHealthInfoCallback".replace('$', '.');
    public static final java.lang.String HASH = "3bab6273a5491102b29c9d7a1f0efa749533f46d";
    public static final int VERSION = 3;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void healthInfoChanged(android.hardware.health.HealthInfo healthInfo) throws android.os.RemoteException;

    public static class Default implements android.hardware.health.IHealthInfoCallback {
        @Override // android.hardware.health.IHealthInfoCallback
        public void healthInfoChanged(android.hardware.health.HealthInfo healthInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.health.IHealthInfoCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.health.IHealthInfoCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.health.IHealthInfoCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_healthInfoChanged = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.health.IHealthInfoCallback.DESCRIPTOR);
        }

        public static android.hardware.health.IHealthInfoCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.health.IHealthInfoCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.health.IHealthInfoCallback)) {
                return (android.hardware.health.IHealthInfoCallback) queryLocalInterface;
            }
            return new android.hardware.health.IHealthInfoCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.health.IHealthInfoCallback.DESCRIPTOR;
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
                    android.hardware.health.HealthInfo healthInfo = (android.hardware.health.HealthInfo) parcel.readTypedObject(android.hardware.health.HealthInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    healthInfoChanged(healthInfo);
                    break;
            }
            return true;
        }

        private static class Proxy implements android.hardware.health.IHealthInfoCallback {
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
                return android.hardware.health.IHealthInfoCallback.DESCRIPTOR;
            }

            @Override // android.hardware.health.IHealthInfoCallback
            public void healthInfoChanged(android.hardware.health.HealthInfo healthInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.health.IHealthInfoCallback.DESCRIPTOR);
                    obtain.writeTypedObject(healthInfo, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method healthInfoChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.health.IHealthInfoCallback
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.health.IHealthInfoCallback.DESCRIPTOR);
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

            @Override // android.hardware.health.IHealthInfoCallback
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.health.IHealthInfoCallback.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.health.IHealthInfoCallback.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
