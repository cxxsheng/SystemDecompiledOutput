package android.hardware.oemlock;

/* loaded from: classes.dex */
public interface IOemLock extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$oemlock$IOemLock".replace('$', '.');
    public static final java.lang.String HASH = "782d36d56fbdca1105672dd96b8e955b6a81dadf";
    public static final int VERSION = 1;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    java.lang.String getName() throws android.os.RemoteException;

    boolean isOemUnlockAllowedByCarrier() throws android.os.RemoteException;

    boolean isOemUnlockAllowedByDevice() throws android.os.RemoteException;

    int setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) throws android.os.RemoteException;

    void setOemUnlockAllowedByDevice(boolean z) throws android.os.RemoteException;

    public static class Default implements android.hardware.oemlock.IOemLock {
        @Override // android.hardware.oemlock.IOemLock
        public java.lang.String getName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.oemlock.IOemLock
        public boolean isOemUnlockAllowedByCarrier() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.oemlock.IOemLock
        public boolean isOemUnlockAllowedByDevice() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.oemlock.IOemLock
        public int setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.oemlock.IOemLock
        public void setOemUnlockAllowedByDevice(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.oemlock.IOemLock
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.oemlock.IOemLock
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.oemlock.IOemLock {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getName = 1;
        static final int TRANSACTION_isOemUnlockAllowedByCarrier = 2;
        static final int TRANSACTION_isOemUnlockAllowedByDevice = 3;
        static final int TRANSACTION_setOemUnlockAllowedByCarrier = 4;
        static final int TRANSACTION_setOemUnlockAllowedByDevice = 5;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.oemlock.IOemLock.DESCRIPTOR);
        }

        public static android.hardware.oemlock.IOemLock asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.oemlock.IOemLock.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.oemlock.IOemLock)) {
                return (android.hardware.oemlock.IOemLock) queryLocalInterface;
            }
            return new android.hardware.oemlock.IOemLock.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getName";
                case 2:
                    return "isOemUnlockAllowedByCarrier";
                case 3:
                    return "isOemUnlockAllowedByDevice";
                case 4:
                    return "setOemUnlockAllowedByCarrier";
                case 5:
                    return "setOemUnlockAllowedByDevice";
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
            java.lang.String str = android.hardware.oemlock.IOemLock.DESCRIPTOR;
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
                    java.lang.String name = getName();
                    parcel2.writeNoException();
                    parcel2.writeString(name);
                    return true;
                case 2:
                    boolean isOemUnlockAllowedByCarrier = isOemUnlockAllowedByCarrier();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOemUnlockAllowedByCarrier);
                    return true;
                case 3:
                    boolean isOemUnlockAllowedByDevice = isOemUnlockAllowedByDevice();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOemUnlockAllowedByDevice);
                    return true;
                case 4:
                    boolean readBoolean = parcel.readBoolean();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int oemUnlockAllowedByCarrier = setOemUnlockAllowedByCarrier(readBoolean, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(oemUnlockAllowedByCarrier);
                    return true;
                case 5:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOemUnlockAllowedByDevice(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.oemlock.IOemLock {
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
                return android.hardware.oemlock.IOemLock.DESCRIPTOR;
            }

            @Override // android.hardware.oemlock.IOemLock
            public java.lang.String getName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.oemlock.IOemLock.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getName is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.oemlock.IOemLock
            public boolean isOemUnlockAllowedByCarrier() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.oemlock.IOemLock.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isOemUnlockAllowedByCarrier is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.oemlock.IOemLock
            public boolean isOemUnlockAllowedByDevice() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.oemlock.IOemLock.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isOemUnlockAllowedByDevice is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.oemlock.IOemLock
            public int setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.oemlock.IOemLock.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setOemUnlockAllowedByCarrier is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.oemlock.IOemLock
            public void setOemUnlockAllowedByDevice(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.oemlock.IOemLock.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setOemUnlockAllowedByDevice is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.oemlock.IOemLock
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.oemlock.IOemLock.DESCRIPTOR);
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

            @Override // android.hardware.oemlock.IOemLock
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.oemlock.IOemLock.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.oemlock.IOemLock.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
