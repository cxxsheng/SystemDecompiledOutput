package android.hardware.security.keymint;

/* loaded from: classes2.dex */
public interface IKeyMintOperation extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$security$keymint$IKeyMintOperation".replace('$', '.');
    public static final java.lang.String HASH = "74a538630d5d90f732f361a2313cbb69b09eb047";
    public static final int VERSION = 3;

    void abort() throws android.os.RemoteException;

    byte[] finish(byte[] bArr, byte[] bArr2, android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken, android.hardware.security.secureclock.TimeStampToken timeStampToken, byte[] bArr3) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    byte[] update(byte[] bArr, android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken, android.hardware.security.secureclock.TimeStampToken timeStampToken) throws android.os.RemoteException;

    void updateAad(byte[] bArr, android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken, android.hardware.security.secureclock.TimeStampToken timeStampToken) throws android.os.RemoteException;

    public static class Default implements android.hardware.security.keymint.IKeyMintOperation {
        @Override // android.hardware.security.keymint.IKeyMintOperation
        public void updateAad(byte[] bArr, android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken, android.hardware.security.secureclock.TimeStampToken timeStampToken) throws android.os.RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintOperation
        public byte[] update(byte[] bArr, android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken, android.hardware.security.secureclock.TimeStampToken timeStampToken) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintOperation
        public byte[] finish(byte[] bArr, byte[] bArr2, android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken, android.hardware.security.secureclock.TimeStampToken timeStampToken, byte[] bArr3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintOperation
        public void abort() throws android.os.RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintOperation
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.security.keymint.IKeyMintOperation
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.security.keymint.IKeyMintOperation {
        static final int TRANSACTION_abort = 4;
        static final int TRANSACTION_finish = 3;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_update = 2;
        static final int TRANSACTION_updateAad = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.security.keymint.IKeyMintOperation asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.security.keymint.IKeyMintOperation)) {
                return (android.hardware.security.keymint.IKeyMintOperation) queryLocalInterface;
            }
            return new android.hardware.security.keymint.IKeyMintOperation.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateAad";
                case 2:
                    return "update";
                case 3:
                    return "finish";
                case 4:
                    return "abort";
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
                    byte[] createByteArray = parcel.createByteArray();
                    android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken = (android.hardware.security.keymint.HardwareAuthToken) parcel.readTypedObject(android.hardware.security.keymint.HardwareAuthToken.CREATOR);
                    android.hardware.security.secureclock.TimeStampToken timeStampToken = (android.hardware.security.secureclock.TimeStampToken) parcel.readTypedObject(android.hardware.security.secureclock.TimeStampToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateAad(createByteArray, hardwareAuthToken, timeStampToken);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    byte[] createByteArray2 = parcel.createByteArray();
                    android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken2 = (android.hardware.security.keymint.HardwareAuthToken) parcel.readTypedObject(android.hardware.security.keymint.HardwareAuthToken.CREATOR);
                    android.hardware.security.secureclock.TimeStampToken timeStampToken2 = (android.hardware.security.secureclock.TimeStampToken) parcel.readTypedObject(android.hardware.security.secureclock.TimeStampToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    byte[] update = update(createByteArray2, hardwareAuthToken2, timeStampToken2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(update);
                    return true;
                case 3:
                    byte[] createByteArray3 = parcel.createByteArray();
                    byte[] createByteArray4 = parcel.createByteArray();
                    android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken3 = (android.hardware.security.keymint.HardwareAuthToken) parcel.readTypedObject(android.hardware.security.keymint.HardwareAuthToken.CREATOR);
                    android.hardware.security.secureclock.TimeStampToken timeStampToken3 = (android.hardware.security.secureclock.TimeStampToken) parcel.readTypedObject(android.hardware.security.secureclock.TimeStampToken.CREATOR);
                    byte[] createByteArray5 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] finish = finish(createByteArray3, createByteArray4, hardwareAuthToken3, timeStampToken3, createByteArray5);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(finish);
                    return true;
                case 4:
                    abort();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.security.keymint.IKeyMintOperation {
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

            @Override // android.hardware.security.keymint.IKeyMintOperation
            public void updateAad(byte[] bArr, android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken, android.hardware.security.secureclock.TimeStampToken timeStampToken) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    obtain.writeTypedObject(timeStampToken, 0);
                    if (!this.mRemote.transact(1, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method updateAad is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintOperation
            public byte[] update(byte[] bArr, android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken, android.hardware.security.secureclock.TimeStampToken timeStampToken) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    obtain.writeTypedObject(timeStampToken, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method update is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintOperation
            public byte[] finish(byte[] bArr, byte[] bArr2, android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken, android.hardware.security.secureclock.TimeStampToken timeStampToken, byte[] bArr3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    obtain.writeTypedObject(timeStampToken, 0);
                    obtain.writeByteArray(bArr3);
                    if (!this.mRemote.transact(3, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method finish is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintOperation
            public void abort() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method abort is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintOperation
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

            @Override // android.hardware.security.keymint.IKeyMintOperation
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
