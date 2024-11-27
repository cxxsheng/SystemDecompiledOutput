package android.security.authorization;

/* loaded from: classes3.dex */
public interface IKeystoreAuthorization extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.authorization.IKeystoreAuthorization";

    void addAuthToken(android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException;

    android.security.authorization.AuthorizationTokens getAuthTokensForCredStore(long j, long j2, long j3) throws android.os.RemoteException;

    long getLastAuthTime(long j, int[] iArr) throws android.os.RemoteException;

    void onDeviceLocked(int i, long[] jArr, boolean z) throws android.os.RemoteException;

    void onDeviceUnlocked(int i, byte[] bArr) throws android.os.RemoteException;

    void onNonLskfUnlockMethodsExpired(int i) throws android.os.RemoteException;

    void onWeakUnlockMethodsExpired(int i) throws android.os.RemoteException;

    public static class Default implements android.security.authorization.IKeystoreAuthorization {
        @Override // android.security.authorization.IKeystoreAuthorization
        public void addAuthToken(android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public void onDeviceUnlocked(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public void onDeviceLocked(int i, long[] jArr, boolean z) throws android.os.RemoteException {
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public void onWeakUnlockMethodsExpired(int i) throws android.os.RemoteException {
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public void onNonLskfUnlockMethodsExpired(int i) throws android.os.RemoteException {
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public android.security.authorization.AuthorizationTokens getAuthTokensForCredStore(long j, long j2, long j3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.authorization.IKeystoreAuthorization
        public long getLastAuthTime(long j, int[] iArr) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.authorization.IKeystoreAuthorization {
        static final int TRANSACTION_addAuthToken = 1;
        static final int TRANSACTION_getAuthTokensForCredStore = 6;
        static final int TRANSACTION_getLastAuthTime = 7;
        static final int TRANSACTION_onDeviceLocked = 3;
        static final int TRANSACTION_onDeviceUnlocked = 2;
        static final int TRANSACTION_onNonLskfUnlockMethodsExpired = 5;
        static final int TRANSACTION_onWeakUnlockMethodsExpired = 4;

        public Stub() {
            attachInterface(this, android.security.authorization.IKeystoreAuthorization.DESCRIPTOR);
        }

        public static android.security.authorization.IKeystoreAuthorization asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.authorization.IKeystoreAuthorization.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.authorization.IKeystoreAuthorization)) {
                return (android.security.authorization.IKeystoreAuthorization) queryLocalInterface;
            }
            return new android.security.authorization.IKeystoreAuthorization.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addAuthToken";
                case 2:
                    return "onDeviceUnlocked";
                case 3:
                    return "onDeviceLocked";
                case 4:
                    return "onWeakUnlockMethodsExpired";
                case 5:
                    return "onNonLskfUnlockMethodsExpired";
                case 6:
                    return "getAuthTokensForCredStore";
                case 7:
                    return "getLastAuthTime";
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
                parcel.enforceInterface(android.security.authorization.IKeystoreAuthorization.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.authorization.IKeystoreAuthorization.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken = (android.hardware.security.keymint.HardwareAuthToken) parcel.readTypedObject(android.hardware.security.keymint.HardwareAuthToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    addAuthToken(hardwareAuthToken);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onDeviceUnlocked(readInt, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    long[] createLongArray = parcel.createLongArray();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onDeviceLocked(readInt2, createLongArray, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onWeakUnlockMethodsExpired(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNonLskfUnlockMethodsExpired(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    android.security.authorization.AuthorizationTokens authTokensForCredStore = getAuthTokensForCredStore(readLong, readLong2, readLong3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(authTokensForCredStore, 1);
                    return true;
                case 7:
                    long readLong4 = parcel.readLong();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long lastAuthTime = getLastAuthTime(readLong4, createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastAuthTime);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.authorization.IKeystoreAuthorization {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.authorization.IKeystoreAuthorization.DESCRIPTOR;
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void addAuthToken(android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.authorization.IKeystoreAuthorization.DESCRIPTOR);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    this.mRemote.transact(1, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void onDeviceUnlocked(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.authorization.IKeystoreAuthorization.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(2, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void onDeviceLocked(int i, long[] jArr, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.authorization.IKeystoreAuthorization.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLongArray(jArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void onWeakUnlockMethodsExpired(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.authorization.IKeystoreAuthorization.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public void onNonLskfUnlockMethodsExpired(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.authorization.IKeystoreAuthorization.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public android.security.authorization.AuthorizationTokens getAuthTokensForCredStore(long j, long j2, long j3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.authorization.IKeystoreAuthorization.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    this.mRemote.transact(6, obtain, obtain2, 32);
                    obtain2.readException();
                    return (android.security.authorization.AuthorizationTokens) obtain2.readTypedObject(android.security.authorization.AuthorizationTokens.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.authorization.IKeystoreAuthorization
            public long getLastAuthTime(long j, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.authorization.IKeystoreAuthorization.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(7, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
