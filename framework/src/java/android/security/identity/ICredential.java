package android.security.identity;

/* loaded from: classes3.dex */
public interface ICredential extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.identity.ICredential";
    public static final int STATUS_NOT_IN_REQUEST_MESSAGE = 3;
    public static final int STATUS_NOT_REQUESTED = 2;
    public static final int STATUS_NO_ACCESS_CONTROL_PROFILES = 6;
    public static final int STATUS_NO_SUCH_ENTRY = 1;
    public static final int STATUS_OK = 0;
    public static final int STATUS_READER_AUTHENTICATION_FAILED = 5;
    public static final int STATUS_USER_AUTHENTICATION_FAILED = 4;

    byte[] createEphemeralKeyPair() throws android.os.RemoteException;

    byte[] deleteCredential() throws android.os.RemoteException;

    byte[] deleteWithChallenge(byte[] bArr) throws android.os.RemoteException;

    android.security.identity.AuthKeyParcel[] getAuthKeysNeedingCertification() throws android.os.RemoteException;

    long[] getAuthenticationDataExpirations() throws android.os.RemoteException;

    int[] getAuthenticationDataUsageCount() throws android.os.RemoteException;

    byte[] getCredentialKeyCertificateChain() throws android.os.RemoteException;

    android.security.identity.GetEntriesResultParcel getEntries(byte[] bArr, android.security.identity.RequestNamespaceParcel[] requestNamespaceParcelArr, byte[] bArr2, byte[] bArr3, boolean z, boolean z2, boolean z3) throws android.os.RemoteException;

    byte[] proveOwnership(byte[] bArr) throws android.os.RemoteException;

    long selectAuthKey(boolean z, boolean z2, boolean z3) throws android.os.RemoteException;

    void setAvailableAuthenticationKeys(int i, int i2, long j) throws android.os.RemoteException;

    void setReaderEphemeralPublicKey(byte[] bArr) throws android.os.RemoteException;

    void storeStaticAuthenticationData(android.security.identity.AuthKeyParcel authKeyParcel, byte[] bArr) throws android.os.RemoteException;

    void storeStaticAuthenticationDataWithExpiration(android.security.identity.AuthKeyParcel authKeyParcel, long j, byte[] bArr) throws android.os.RemoteException;

    android.security.identity.IWritableCredential update() throws android.os.RemoteException;

    public static class Default implements android.security.identity.ICredential {
        @Override // android.security.identity.ICredential
        public byte[] createEphemeralKeyPair() throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public void setReaderEphemeralPublicKey(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.security.identity.ICredential
        public byte[] deleteCredential() throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public byte[] deleteWithChallenge(byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public byte[] proveOwnership(byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public byte[] getCredentialKeyCertificateChain() throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public long selectAuthKey(boolean z, boolean z2, boolean z3) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.security.identity.ICredential
        public android.security.identity.GetEntriesResultParcel getEntries(byte[] bArr, android.security.identity.RequestNamespaceParcel[] requestNamespaceParcelArr, byte[] bArr2, byte[] bArr3, boolean z, boolean z2, boolean z3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public void setAvailableAuthenticationKeys(int i, int i2, long j) throws android.os.RemoteException {
        }

        @Override // android.security.identity.ICredential
        public android.security.identity.AuthKeyParcel[] getAuthKeysNeedingCertification() throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public void storeStaticAuthenticationData(android.security.identity.AuthKeyParcel authKeyParcel, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.security.identity.ICredential
        public void storeStaticAuthenticationDataWithExpiration(android.security.identity.AuthKeyParcel authKeyParcel, long j, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.security.identity.ICredential
        public int[] getAuthenticationDataUsageCount() throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public long[] getAuthenticationDataExpirations() throws android.os.RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public android.security.identity.IWritableCredential update() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.identity.ICredential {
        static final int TRANSACTION_createEphemeralKeyPair = 1;
        static final int TRANSACTION_deleteCredential = 3;
        static final int TRANSACTION_deleteWithChallenge = 4;
        static final int TRANSACTION_getAuthKeysNeedingCertification = 10;
        static final int TRANSACTION_getAuthenticationDataExpirations = 14;
        static final int TRANSACTION_getAuthenticationDataUsageCount = 13;
        static final int TRANSACTION_getCredentialKeyCertificateChain = 6;
        static final int TRANSACTION_getEntries = 8;
        static final int TRANSACTION_proveOwnership = 5;
        static final int TRANSACTION_selectAuthKey = 7;
        static final int TRANSACTION_setAvailableAuthenticationKeys = 9;
        static final int TRANSACTION_setReaderEphemeralPublicKey = 2;
        static final int TRANSACTION_storeStaticAuthenticationData = 11;
        static final int TRANSACTION_storeStaticAuthenticationDataWithExpiration = 12;
        static final int TRANSACTION_update = 15;

        public Stub() {
            attachInterface(this, android.security.identity.ICredential.DESCRIPTOR);
        }

        public static android.security.identity.ICredential asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.identity.ICredential.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.identity.ICredential)) {
                return (android.security.identity.ICredential) queryLocalInterface;
            }
            return new android.security.identity.ICredential.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createEphemeralKeyPair";
                case 2:
                    return "setReaderEphemeralPublicKey";
                case 3:
                    return "deleteCredential";
                case 4:
                    return "deleteWithChallenge";
                case 5:
                    return "proveOwnership";
                case 6:
                    return "getCredentialKeyCertificateChain";
                case 7:
                    return "selectAuthKey";
                case 8:
                    return "getEntries";
                case 9:
                    return "setAvailableAuthenticationKeys";
                case 10:
                    return "getAuthKeysNeedingCertification";
                case 11:
                    return "storeStaticAuthenticationData";
                case 12:
                    return "storeStaticAuthenticationDataWithExpiration";
                case 13:
                    return "getAuthenticationDataUsageCount";
                case 14:
                    return "getAuthenticationDataExpirations";
                case 15:
                    return "update";
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
                parcel.enforceInterface(android.security.identity.ICredential.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.identity.ICredential.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    byte[] createEphemeralKeyPair = createEphemeralKeyPair();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(createEphemeralKeyPair);
                    return true;
                case 2:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setReaderEphemeralPublicKey(createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    byte[] deleteCredential = deleteCredential();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(deleteCredential);
                    return true;
                case 4:
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] deleteWithChallenge = deleteWithChallenge(createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(deleteWithChallenge);
                    return true;
                case 5:
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] proveOwnership = proveOwnership(createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(proveOwnership);
                    return true;
                case 6:
                    byte[] credentialKeyCertificateChain = getCredentialKeyCertificateChain();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(credentialKeyCertificateChain);
                    return true;
                case 7:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    long selectAuthKey = selectAuthKey(readBoolean, readBoolean2, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeLong(selectAuthKey);
                    return true;
                case 8:
                    byte[] createByteArray4 = parcel.createByteArray();
                    android.security.identity.RequestNamespaceParcel[] requestNamespaceParcelArr = (android.security.identity.RequestNamespaceParcel[]) parcel.createTypedArray(android.security.identity.RequestNamespaceParcel.CREATOR);
                    byte[] createByteArray5 = parcel.createByteArray();
                    byte[] createByteArray6 = parcel.createByteArray();
                    boolean readBoolean4 = parcel.readBoolean();
                    boolean readBoolean5 = parcel.readBoolean();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.security.identity.GetEntriesResultParcel entries = getEntries(createByteArray4, requestNamespaceParcelArr, createByteArray5, createByteArray6, readBoolean4, readBoolean5, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(entries, 1);
                    return true;
                case 9:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setAvailableAuthenticationKeys(readInt, readInt2, readLong);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.security.identity.AuthKeyParcel[] authKeysNeedingCertification = getAuthKeysNeedingCertification();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(authKeysNeedingCertification, 1);
                    return true;
                case 11:
                    android.security.identity.AuthKeyParcel authKeyParcel = (android.security.identity.AuthKeyParcel) parcel.readTypedObject(android.security.identity.AuthKeyParcel.CREATOR);
                    byte[] createByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    storeStaticAuthenticationData(authKeyParcel, createByteArray7);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.security.identity.AuthKeyParcel authKeyParcel2 = (android.security.identity.AuthKeyParcel) parcel.readTypedObject(android.security.identity.AuthKeyParcel.CREATOR);
                    long readLong2 = parcel.readLong();
                    byte[] createByteArray8 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    storeStaticAuthenticationDataWithExpiration(authKeyParcel2, readLong2, createByteArray8);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int[] authenticationDataUsageCount = getAuthenticationDataUsageCount();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(authenticationDataUsageCount);
                    return true;
                case 14:
                    long[] authenticationDataExpirations = getAuthenticationDataExpirations();
                    parcel2.writeNoException();
                    parcel2.writeLongArray(authenticationDataExpirations);
                    return true;
                case 15:
                    android.security.identity.IWritableCredential update = update();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(update);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.identity.ICredential {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.identity.ICredential.DESCRIPTOR;
            }

            @Override // android.security.identity.ICredential
            public byte[] createEphemeralKeyPair() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public void setReaderEphemeralPublicKey(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public byte[] deleteCredential() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public byte[] deleteWithChallenge(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public byte[] proveOwnership(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public byte[] getCredentialKeyCertificateChain() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public long selectAuthKey(boolean z, boolean z2, boolean z3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public android.security.identity.GetEntriesResultParcel getEntries(byte[] bArr, android.security.identity.RequestNamespaceParcel[] requestNamespaceParcelArr, byte[] bArr2, byte[] bArr3, boolean z, boolean z2, boolean z3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedArray(requestNamespaceParcelArr, 0);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.security.identity.GetEntriesResultParcel) obtain2.readTypedObject(android.security.identity.GetEntriesResultParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public void setAvailableAuthenticationKeys(int i, int i2, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public android.security.identity.AuthKeyParcel[] getAuthKeysNeedingCertification() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.security.identity.AuthKeyParcel[]) obtain2.createTypedArray(android.security.identity.AuthKeyParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public void storeStaticAuthenticationData(android.security.identity.AuthKeyParcel authKeyParcel, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    obtain.writeTypedObject(authKeyParcel, 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public void storeStaticAuthenticationDataWithExpiration(android.security.identity.AuthKeyParcel authKeyParcel, long j, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    obtain.writeTypedObject(authKeyParcel, 0);
                    obtain.writeLong(j);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public int[] getAuthenticationDataUsageCount() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public long[] getAuthenticationDataExpirations() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public android.security.identity.IWritableCredential update() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredential.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.security.identity.IWritableCredential.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }
    }
}
