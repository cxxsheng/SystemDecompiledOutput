package android.hardware.security.keymint;

/* loaded from: classes2.dex */
public interface IKeyMintDevice extends android.os.IInterface {
    public static final int AUTH_TOKEN_MAC_LENGTH = 32;
    public static final java.lang.String DESCRIPTOR = "android$hardware$security$keymint$IKeyMintDevice".replace('$', '.');
    public static final java.lang.String HASH = "74a538630d5d90f732f361a2313cbb69b09eb047";
    public static final int VERSION = 3;

    void addRngEntropy(byte[] bArr) throws android.os.RemoteException;

    android.hardware.security.keymint.BeginResult begin(int i, byte[] bArr, android.hardware.security.keymint.KeyParameter[] keyParameterArr, android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException;

    byte[] convertStorageKeyToEphemeral(byte[] bArr) throws android.os.RemoteException;

    void deleteAllKeys() throws android.os.RemoteException;

    void deleteKey(byte[] bArr) throws android.os.RemoteException;

    void destroyAttestationIds() throws android.os.RemoteException;

    void deviceLocked(boolean z, android.hardware.security.secureclock.TimeStampToken timeStampToken) throws android.os.RemoteException;

    void earlyBootEnded() throws android.os.RemoteException;

    android.hardware.security.keymint.KeyCreationResult generateKey(android.hardware.security.keymint.KeyParameter[] keyParameterArr, android.hardware.security.keymint.AttestationKey attestationKey) throws android.os.RemoteException;

    android.hardware.security.keymint.KeyMintHardwareInfo getHardwareInfo() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.hardware.security.keymint.KeyCharacteristics[] getKeyCharacteristics(byte[] bArr, byte[] bArr2, byte[] bArr3) throws android.os.RemoteException;

    byte[] getRootOfTrust(byte[] bArr) throws android.os.RemoteException;

    byte[] getRootOfTrustChallenge() throws android.os.RemoteException;

    android.hardware.security.keymint.KeyCreationResult importKey(android.hardware.security.keymint.KeyParameter[] keyParameterArr, int i, byte[] bArr, android.hardware.security.keymint.AttestationKey attestationKey) throws android.os.RemoteException;

    android.hardware.security.keymint.KeyCreationResult importWrappedKey(byte[] bArr, byte[] bArr2, byte[] bArr3, android.hardware.security.keymint.KeyParameter[] keyParameterArr, long j, long j2) throws android.os.RemoteException;

    void sendRootOfTrust(byte[] bArr) throws android.os.RemoteException;

    byte[] upgradeKey(byte[] bArr, android.hardware.security.keymint.KeyParameter[] keyParameterArr) throws android.os.RemoteException;

    public static class Default implements android.hardware.security.keymint.IKeyMintDevice {
        @Override // android.hardware.security.keymint.IKeyMintDevice
        public android.hardware.security.keymint.KeyMintHardwareInfo getHardwareInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void addRngEntropy(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public android.hardware.security.keymint.KeyCreationResult generateKey(android.hardware.security.keymint.KeyParameter[] keyParameterArr, android.hardware.security.keymint.AttestationKey attestationKey) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public android.hardware.security.keymint.KeyCreationResult importKey(android.hardware.security.keymint.KeyParameter[] keyParameterArr, int i, byte[] bArr, android.hardware.security.keymint.AttestationKey attestationKey) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public android.hardware.security.keymint.KeyCreationResult importWrappedKey(byte[] bArr, byte[] bArr2, byte[] bArr3, android.hardware.security.keymint.KeyParameter[] keyParameterArr, long j, long j2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public byte[] upgradeKey(byte[] bArr, android.hardware.security.keymint.KeyParameter[] keyParameterArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void deleteKey(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void deleteAllKeys() throws android.os.RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void destroyAttestationIds() throws android.os.RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public android.hardware.security.keymint.BeginResult begin(int i, byte[] bArr, android.hardware.security.keymint.KeyParameter[] keyParameterArr, android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void deviceLocked(boolean z, android.hardware.security.secureclock.TimeStampToken timeStampToken) throws android.os.RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void earlyBootEnded() throws android.os.RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public byte[] convertStorageKeyToEphemeral(byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public android.hardware.security.keymint.KeyCharacteristics[] getKeyCharacteristics(byte[] bArr, byte[] bArr2, byte[] bArr3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public byte[] getRootOfTrustChallenge() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public byte[] getRootOfTrust(byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void sendRootOfTrust(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.security.keymint.IKeyMintDevice {
        static final int TRANSACTION_addRngEntropy = 2;
        static final int TRANSACTION_begin = 10;
        static final int TRANSACTION_convertStorageKeyToEphemeral = 13;
        static final int TRANSACTION_deleteAllKeys = 8;
        static final int TRANSACTION_deleteKey = 7;
        static final int TRANSACTION_destroyAttestationIds = 9;
        static final int TRANSACTION_deviceLocked = 11;
        static final int TRANSACTION_earlyBootEnded = 12;
        static final int TRANSACTION_generateKey = 3;
        static final int TRANSACTION_getHardwareInfo = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getKeyCharacteristics = 14;
        static final int TRANSACTION_getRootOfTrust = 16;
        static final int TRANSACTION_getRootOfTrustChallenge = 15;
        static final int TRANSACTION_importKey = 4;
        static final int TRANSACTION_importWrappedKey = 5;
        static final int TRANSACTION_sendRootOfTrust = 17;
        static final int TRANSACTION_upgradeKey = 6;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.security.keymint.IKeyMintDevice asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.security.keymint.IKeyMintDevice)) {
                return (android.hardware.security.keymint.IKeyMintDevice) queryLocalInterface;
            }
            return new android.hardware.security.keymint.IKeyMintDevice.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getHardwareInfo";
                case 2:
                    return "addRngEntropy";
                case 3:
                    return "generateKey";
                case 4:
                    return "importKey";
                case 5:
                    return "importWrappedKey";
                case 6:
                    return "upgradeKey";
                case 7:
                    return "deleteKey";
                case 8:
                    return "deleteAllKeys";
                case 9:
                    return "destroyAttestationIds";
                case 10:
                    return "begin";
                case 11:
                    return "deviceLocked";
                case 12:
                    return "earlyBootEnded";
                case 13:
                    return "convertStorageKeyToEphemeral";
                case 14:
                    return "getKeyCharacteristics";
                case 15:
                    return "getRootOfTrustChallenge";
                case 16:
                    return "getRootOfTrust";
                case 17:
                    return "sendRootOfTrust";
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
                    android.hardware.security.keymint.KeyMintHardwareInfo hardwareInfo = getHardwareInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(hardwareInfo, 1);
                    return true;
                case 2:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    addRngEntropy(createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.hardware.security.keymint.KeyParameter[] keyParameterArr = (android.hardware.security.keymint.KeyParameter[]) parcel.createTypedArray(android.hardware.security.keymint.KeyParameter.CREATOR);
                    android.hardware.security.keymint.AttestationKey attestationKey = (android.hardware.security.keymint.AttestationKey) parcel.readTypedObject(android.hardware.security.keymint.AttestationKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.security.keymint.KeyCreationResult generateKey = generateKey(keyParameterArr, attestationKey);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateKey, 1);
                    return true;
                case 4:
                    android.hardware.security.keymint.KeyParameter[] keyParameterArr2 = (android.hardware.security.keymint.KeyParameter[]) parcel.createTypedArray(android.hardware.security.keymint.KeyParameter.CREATOR);
                    int readInt = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    android.hardware.security.keymint.AttestationKey attestationKey2 = (android.hardware.security.keymint.AttestationKey) parcel.readTypedObject(android.hardware.security.keymint.AttestationKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.security.keymint.KeyCreationResult importKey = importKey(keyParameterArr2, readInt, createByteArray2, attestationKey2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(importKey, 1);
                    return true;
                case 5:
                    byte[] createByteArray3 = parcel.createByteArray();
                    byte[] createByteArray4 = parcel.createByteArray();
                    byte[] createByteArray5 = parcel.createByteArray();
                    android.hardware.security.keymint.KeyParameter[] keyParameterArr3 = (android.hardware.security.keymint.KeyParameter[]) parcel.createTypedArray(android.hardware.security.keymint.KeyParameter.CREATOR);
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    android.hardware.security.keymint.KeyCreationResult importWrappedKey = importWrappedKey(createByteArray3, createByteArray4, createByteArray5, keyParameterArr3, readLong, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(importWrappedKey, 1);
                    return true;
                case 6:
                    byte[] createByteArray6 = parcel.createByteArray();
                    android.hardware.security.keymint.KeyParameter[] keyParameterArr4 = (android.hardware.security.keymint.KeyParameter[]) parcel.createTypedArray(android.hardware.security.keymint.KeyParameter.CREATOR);
                    parcel.enforceNoDataAvail();
                    byte[] upgradeKey = upgradeKey(createByteArray6, keyParameterArr4);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(upgradeKey);
                    return true;
                case 7:
                    byte[] createByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    deleteKey(createByteArray7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    deleteAllKeys();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    destroyAttestationIds();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt2 = parcel.readInt();
                    byte[] createByteArray8 = parcel.createByteArray();
                    android.hardware.security.keymint.KeyParameter[] keyParameterArr5 = (android.hardware.security.keymint.KeyParameter[]) parcel.createTypedArray(android.hardware.security.keymint.KeyParameter.CREATOR);
                    android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken = (android.hardware.security.keymint.HardwareAuthToken) parcel.readTypedObject(android.hardware.security.keymint.HardwareAuthToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.security.keymint.BeginResult begin = begin(readInt2, createByteArray8, keyParameterArr5, hardwareAuthToken);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(begin, 1);
                    return true;
                case 11:
                    boolean readBoolean = parcel.readBoolean();
                    android.hardware.security.secureclock.TimeStampToken timeStampToken = (android.hardware.security.secureclock.TimeStampToken) parcel.readTypedObject(android.hardware.security.secureclock.TimeStampToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    deviceLocked(readBoolean, timeStampToken);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    earlyBootEnded();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    byte[] createByteArray9 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] convertStorageKeyToEphemeral = convertStorageKeyToEphemeral(createByteArray9);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(convertStorageKeyToEphemeral);
                    return true;
                case 14:
                    byte[] createByteArray10 = parcel.createByteArray();
                    byte[] createByteArray11 = parcel.createByteArray();
                    byte[] createByteArray12 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    android.hardware.security.keymint.KeyCharacteristics[] keyCharacteristics = getKeyCharacteristics(createByteArray10, createByteArray11, createByteArray12);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(keyCharacteristics, 1);
                    return true;
                case 15:
                    byte[] rootOfTrustChallenge = getRootOfTrustChallenge();
                    parcel2.writeNoException();
                    parcel2.writeFixedArray(rootOfTrustChallenge, 1, 16);
                    return true;
                case 16:
                    byte[] bArr = (byte[]) parcel.createFixedArray(byte[].class, 16);
                    parcel.enforceNoDataAvail();
                    byte[] rootOfTrust = getRootOfTrust(bArr);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(rootOfTrust);
                    return true;
                case 17:
                    byte[] createByteArray13 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendRootOfTrust(createByteArray13);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.security.keymint.IKeyMintDevice {
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

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public android.hardware.security.keymint.KeyMintHardwareInfo getHardwareInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method getHardwareInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.security.keymint.KeyMintHardwareInfo) obtain2.readTypedObject(android.hardware.security.keymint.KeyMintHardwareInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void addRngEntropy(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(2, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method addRngEntropy is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public android.hardware.security.keymint.KeyCreationResult generateKey(android.hardware.security.keymint.KeyParameter[] keyParameterArr, android.hardware.security.keymint.AttestationKey attestationKey) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(keyParameterArr, 0);
                    obtain.writeTypedObject(attestationKey, 0);
                    if (!this.mRemote.transact(3, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method generateKey is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.security.keymint.KeyCreationResult) obtain2.readTypedObject(android.hardware.security.keymint.KeyCreationResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public android.hardware.security.keymint.KeyCreationResult importKey(android.hardware.security.keymint.KeyParameter[] keyParameterArr, int i, byte[] bArr, android.hardware.security.keymint.AttestationKey attestationKey) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(keyParameterArr, 0);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(attestationKey, 0);
                    if (!this.mRemote.transact(4, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method importKey is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.security.keymint.KeyCreationResult) obtain2.readTypedObject(android.hardware.security.keymint.KeyCreationResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public android.hardware.security.keymint.KeyCreationResult importWrappedKey(byte[] bArr, byte[] bArr2, byte[] bArr3, android.hardware.security.keymint.KeyParameter[] keyParameterArr, long j, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    obtain.writeTypedArray(keyParameterArr, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    if (!this.mRemote.transact(5, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method importWrappedKey is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.security.keymint.KeyCreationResult) obtain2.readTypedObject(android.hardware.security.keymint.KeyCreationResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public byte[] upgradeKey(byte[] bArr, android.hardware.security.keymint.KeyParameter[] keyParameterArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedArray(keyParameterArr, 0);
                    if (!this.mRemote.transact(6, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method upgradeKey is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void deleteKey(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(7, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method deleteKey is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void deleteAllKeys() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method deleteAllKeys is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void destroyAttestationIds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method destroyAttestationIds is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public android.hardware.security.keymint.BeginResult begin(int i, byte[] bArr, android.hardware.security.keymint.KeyParameter[] keyParameterArr, android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedArray(keyParameterArr, 0);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    if (!this.mRemote.transact(10, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method begin is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.security.keymint.BeginResult) obtain2.readTypedObject(android.hardware.security.keymint.BeginResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void deviceLocked(boolean z, android.hardware.security.secureclock.TimeStampToken timeStampToken) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(timeStampToken, 0);
                    if (!this.mRemote.transact(11, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method deviceLocked is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void earlyBootEnded() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(12, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method earlyBootEnded is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public byte[] convertStorageKeyToEphemeral(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(13, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method convertStorageKeyToEphemeral is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public android.hardware.security.keymint.KeyCharacteristics[] getKeyCharacteristics(byte[] bArr, byte[] bArr2, byte[] bArr3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    if (!this.mRemote.transact(14, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method getKeyCharacteristics is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.security.keymint.KeyCharacteristics[]) obtain2.createTypedArray(android.hardware.security.keymint.KeyCharacteristics.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public byte[] getRootOfTrustChallenge() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(15, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method getRootOfTrustChallenge is unimplemented.");
                    }
                    obtain2.readException();
                    return (byte[]) obtain2.createFixedArray(byte[].class, 16);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public byte[] getRootOfTrust(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeFixedArray(bArr, 0, 16);
                    if (!this.mRemote.transact(16, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method getRootOfTrust is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void sendRootOfTrust(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(17, obtain, obtain2, 32)) {
                        throw new android.os.RemoteException("Method sendRootOfTrust is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
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

            @Override // android.hardware.security.keymint.IKeyMintDevice
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
