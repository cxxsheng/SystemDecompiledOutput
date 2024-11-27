package android.system.keystore2;

/* loaded from: classes3.dex */
public interface IKeystoreSecurityLevel extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$system$keystore2$IKeystoreSecurityLevel".replace('$', '.');
    public static final java.lang.String HASH = "4f1c704008e5687ed0d6f1590464aed39fc7f64e";
    public static final int KEY_FLAG_AUTH_BOUND_WITHOUT_CRYPTOGRAPHIC_LSKF_BINDING = 1;
    public static final int VERSION = 3;

    android.system.keystore2.EphemeralStorageKeyResponse convertStorageKeyToEphemeral(android.system.keystore2.KeyDescriptor keyDescriptor) throws android.os.RemoteException;

    android.system.keystore2.CreateOperationResponse createOperation(android.system.keystore2.KeyDescriptor keyDescriptor, android.hardware.security.keymint.KeyParameter[] keyParameterArr, boolean z) throws android.os.RemoteException;

    void deleteKey(android.system.keystore2.KeyDescriptor keyDescriptor) throws android.os.RemoteException;

    android.system.keystore2.KeyMetadata generateKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2, android.hardware.security.keymint.KeyParameter[] keyParameterArr, int i, byte[] bArr) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.system.keystore2.KeyMetadata importKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2, android.hardware.security.keymint.KeyParameter[] keyParameterArr, int i, byte[] bArr) throws android.os.RemoteException;

    android.system.keystore2.KeyMetadata importWrappedKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2, byte[] bArr, android.hardware.security.keymint.KeyParameter[] keyParameterArr, android.system.keystore2.AuthenticatorSpec[] authenticatorSpecArr) throws android.os.RemoteException;

    public static class Default implements android.system.keystore2.IKeystoreSecurityLevel {
        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public android.system.keystore2.CreateOperationResponse createOperation(android.system.keystore2.KeyDescriptor keyDescriptor, android.hardware.security.keymint.KeyParameter[] keyParameterArr, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public android.system.keystore2.KeyMetadata generateKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2, android.hardware.security.keymint.KeyParameter[] keyParameterArr, int i, byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public android.system.keystore2.KeyMetadata importKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2, android.hardware.security.keymint.KeyParameter[] keyParameterArr, int i, byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public android.system.keystore2.KeyMetadata importWrappedKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2, byte[] bArr, android.hardware.security.keymint.KeyParameter[] keyParameterArr, android.system.keystore2.AuthenticatorSpec[] authenticatorSpecArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public android.system.keystore2.EphemeralStorageKeyResponse convertStorageKeyToEphemeral(android.system.keystore2.KeyDescriptor keyDescriptor) throws android.os.RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public void deleteKey(android.system.keystore2.KeyDescriptor keyDescriptor) throws android.os.RemoteException {
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.system.keystore2.IKeystoreSecurityLevel {
        static final int TRANSACTION_convertStorageKeyToEphemeral = 5;
        static final int TRANSACTION_createOperation = 1;
        static final int TRANSACTION_deleteKey = 6;
        static final int TRANSACTION_generateKey = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_importKey = 3;
        static final int TRANSACTION_importWrappedKey = 4;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.system.keystore2.IKeystoreSecurityLevel asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.system.keystore2.IKeystoreSecurityLevel)) {
                return (android.system.keystore2.IKeystoreSecurityLevel) queryLocalInterface;
            }
            return new android.system.keystore2.IKeystoreSecurityLevel.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createOperation";
                case 2:
                    return "generateKey";
                case 3:
                    return "importKey";
                case 4:
                    return "importWrappedKey";
                case 5:
                    return "convertStorageKeyToEphemeral";
                case 6:
                    return "deleteKey";
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
                    android.system.keystore2.KeyDescriptor keyDescriptor = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    android.hardware.security.keymint.KeyParameter[] keyParameterArr = (android.hardware.security.keymint.KeyParameter[]) parcel.createTypedArray(android.hardware.security.keymint.KeyParameter.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.system.keystore2.CreateOperationResponse createOperation = createOperation(keyDescriptor, keyParameterArr, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createOperation, 1);
                    return true;
                case 2:
                    android.system.keystore2.KeyDescriptor keyDescriptor2 = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    android.system.keystore2.KeyDescriptor keyDescriptor3 = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    android.hardware.security.keymint.KeyParameter[] keyParameterArr2 = (android.hardware.security.keymint.KeyParameter[]) parcel.createTypedArray(android.hardware.security.keymint.KeyParameter.CREATOR);
                    int readInt = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    android.system.keystore2.KeyMetadata generateKey = generateKey(keyDescriptor2, keyDescriptor3, keyParameterArr2, readInt, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateKey, 1);
                    return true;
                case 3:
                    android.system.keystore2.KeyDescriptor keyDescriptor4 = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    android.system.keystore2.KeyDescriptor keyDescriptor5 = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    android.hardware.security.keymint.KeyParameter[] keyParameterArr3 = (android.hardware.security.keymint.KeyParameter[]) parcel.createTypedArray(android.hardware.security.keymint.KeyParameter.CREATOR);
                    int readInt2 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    android.system.keystore2.KeyMetadata importKey = importKey(keyDescriptor4, keyDescriptor5, keyParameterArr3, readInt2, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(importKey, 1);
                    return true;
                case 4:
                    android.system.keystore2.KeyDescriptor keyDescriptor6 = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    android.system.keystore2.KeyDescriptor keyDescriptor7 = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    byte[] createByteArray3 = parcel.createByteArray();
                    android.hardware.security.keymint.KeyParameter[] keyParameterArr4 = (android.hardware.security.keymint.KeyParameter[]) parcel.createTypedArray(android.hardware.security.keymint.KeyParameter.CREATOR);
                    android.system.keystore2.AuthenticatorSpec[] authenticatorSpecArr = (android.system.keystore2.AuthenticatorSpec[]) parcel.createTypedArray(android.system.keystore2.AuthenticatorSpec.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.system.keystore2.KeyMetadata importWrappedKey = importWrappedKey(keyDescriptor6, keyDescriptor7, createByteArray3, keyParameterArr4, authenticatorSpecArr);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(importWrappedKey, 1);
                    return true;
                case 5:
                    android.system.keystore2.KeyDescriptor keyDescriptor8 = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.system.keystore2.EphemeralStorageKeyResponse convertStorageKeyToEphemeral = convertStorageKeyToEphemeral(keyDescriptor8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(convertStorageKeyToEphemeral, 1);
                    return true;
                case 6:
                    android.system.keystore2.KeyDescriptor keyDescriptor9 = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteKey(keyDescriptor9);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.system.keystore2.IKeystoreSecurityLevel {
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

            @Override // android.system.keystore2.IKeystoreSecurityLevel
            public android.system.keystore2.CreateOperationResponse createOperation(android.system.keystore2.KeyDescriptor keyDescriptor, android.hardware.security.keymint.KeyParameter[] keyParameterArr, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    obtain.writeTypedArray(keyParameterArr, 0);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(1, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method createOperation is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.system.keystore2.CreateOperationResponse) obtain2.readTypedObject(android.system.keystore2.CreateOperationResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreSecurityLevel
            public android.system.keystore2.KeyMetadata generateKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2, android.hardware.security.keymint.KeyParameter[] keyParameterArr, int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    obtain.writeTypedObject(keyDescriptor2, 0);
                    obtain.writeTypedArray(keyParameterArr, 0);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(2, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method generateKey is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.system.keystore2.KeyMetadata) obtain2.readTypedObject(android.system.keystore2.KeyMetadata.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreSecurityLevel
            public android.system.keystore2.KeyMetadata importKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2, android.hardware.security.keymint.KeyParameter[] keyParameterArr, int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    obtain.writeTypedObject(keyDescriptor2, 0);
                    obtain.writeTypedArray(keyParameterArr, 0);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(3, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method importKey is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.system.keystore2.KeyMetadata) obtain2.readTypedObject(android.system.keystore2.KeyMetadata.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreSecurityLevel
            public android.system.keystore2.KeyMetadata importWrappedKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2, byte[] bArr, android.hardware.security.keymint.KeyParameter[] keyParameterArr, android.system.keystore2.AuthenticatorSpec[] authenticatorSpecArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    obtain.writeTypedObject(keyDescriptor2, 0);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedArray(keyParameterArr, 0);
                    obtain.writeTypedArray(authenticatorSpecArr, 0);
                    if (!this.mRemote.transact(4, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method importWrappedKey is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.system.keystore2.KeyMetadata) obtain2.readTypedObject(android.system.keystore2.KeyMetadata.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreSecurityLevel
            public android.system.keystore2.EphemeralStorageKeyResponse convertStorageKeyToEphemeral(android.system.keystore2.KeyDescriptor keyDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    if (!this.mRemote.transact(5, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method convertStorageKeyToEphemeral is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.system.keystore2.EphemeralStorageKeyResponse) obtain2.readTypedObject(android.system.keystore2.EphemeralStorageKeyResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreSecurityLevel
            public void deleteKey(android.system.keystore2.KeyDescriptor keyDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    if (!this.mRemote.transact(6, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method deleteKey is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreSecurityLevel
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

            @Override // android.system.keystore2.IKeystoreSecurityLevel
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
