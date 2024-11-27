package android.hardware.weaver;

/* loaded from: classes.dex */
public interface IWeaver extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$weaver$IWeaver".replace('$', '.');
    public static final java.lang.String HASH = "0d60d74c2704ad281e219244514516db8482ef3d";
    public static final int STATUS_FAILED = 1;
    public static final int STATUS_INCORRECT_KEY = 2;
    public static final int STATUS_THROTTLE = 3;
    public static final int VERSION = 2;

    android.hardware.weaver.WeaverConfig getConfig() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.hardware.weaver.WeaverReadResponse read(int i, byte[] bArr) throws android.os.RemoteException;

    void write(int i, byte[] bArr, byte[] bArr2) throws android.os.RemoteException;

    public static class Default implements android.hardware.weaver.IWeaver {
        @Override // android.hardware.weaver.IWeaver
        public android.hardware.weaver.WeaverConfig getConfig() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.weaver.IWeaver
        public android.hardware.weaver.WeaverReadResponse read(int i, byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.weaver.IWeaver
        public void write(int i, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
        }

        @Override // android.hardware.weaver.IWeaver
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.weaver.IWeaver
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.weaver.IWeaver {
        static final int TRANSACTION_getConfig = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_read = 2;
        static final int TRANSACTION_write = 3;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.weaver.IWeaver.DESCRIPTOR);
        }

        public static android.hardware.weaver.IWeaver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.weaver.IWeaver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.weaver.IWeaver)) {
                return (android.hardware.weaver.IWeaver) queryLocalInterface;
            }
            return new android.hardware.weaver.IWeaver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getConfig";
                case 2:
                    return "read";
                case 3:
                    return "write";
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
            java.lang.String str = android.hardware.weaver.IWeaver.DESCRIPTOR;
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
                    android.hardware.weaver.WeaverConfig config = getConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(config, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    android.hardware.weaver.WeaverReadResponse read = read(readInt, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(read, 1);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    write(readInt2, createByteArray2, createByteArray3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.weaver.IWeaver {
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
                return android.hardware.weaver.IWeaver.DESCRIPTOR;
            }

            @Override // android.hardware.weaver.IWeaver
            public android.hardware.weaver.WeaverConfig getConfig() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.weaver.IWeaver.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getConfig is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.weaver.WeaverConfig) obtain2.readTypedObject(android.hardware.weaver.WeaverConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.weaver.IWeaver
            public android.hardware.weaver.WeaverReadResponse read(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.weaver.IWeaver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method read is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.weaver.WeaverReadResponse) obtain2.readTypedObject(android.hardware.weaver.WeaverReadResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.weaver.IWeaver
            public void write(int i, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.weaver.IWeaver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method write is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.weaver.IWeaver
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.weaver.IWeaver.DESCRIPTOR);
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

            @Override // android.hardware.weaver.IWeaver
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.weaver.IWeaver.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.weaver.IWeaver.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
