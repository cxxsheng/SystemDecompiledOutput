package android.hardware.security.keymint;

/* loaded from: classes.dex */
public interface IRemotelyProvisionedComponent extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$security$keymint$IRemotelyProvisionedComponent".replace('$', '.');
    public static final java.lang.String HASH = "7d14edbfab5c490efa407ba55fa80614bb48ae8e";
    public static final int STATUS_FAILED = 1;
    public static final int STATUS_INVALID_EEK = 5;
    public static final int STATUS_INVALID_MAC = 2;
    public static final int STATUS_PRODUCTION_KEY_IN_TEST_REQUEST = 3;
    public static final int STATUS_REMOVED = 6;
    public static final int STATUS_TEST_KEY_IN_PRODUCTION_REQUEST = 4;
    public static final int VERSION = 3;

    byte[] generateCertificateRequest(boolean z, android.hardware.security.keymint.MacedPublicKey[] macedPublicKeyArr, byte[] bArr, byte[] bArr2, android.hardware.security.keymint.DeviceInfo deviceInfo, android.hardware.security.keymint.ProtectedData protectedData) throws android.os.RemoteException;

    byte[] generateCertificateRequestV2(android.hardware.security.keymint.MacedPublicKey[] macedPublicKeyArr, byte[] bArr) throws android.os.RemoteException;

    byte[] generateEcdsaP256KeyPair(boolean z, android.hardware.security.keymint.MacedPublicKey macedPublicKey) throws android.os.RemoteException;

    android.hardware.security.keymint.RpcHardwareInfo getHardwareInfo() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    public static class Default implements android.hardware.security.keymint.IRemotelyProvisionedComponent {
        @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
        public android.hardware.security.keymint.RpcHardwareInfo getHardwareInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
        public byte[] generateEcdsaP256KeyPair(boolean z, android.hardware.security.keymint.MacedPublicKey macedPublicKey) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
        public byte[] generateCertificateRequest(boolean z, android.hardware.security.keymint.MacedPublicKey[] macedPublicKeyArr, byte[] bArr, byte[] bArr2, android.hardware.security.keymint.DeviceInfo deviceInfo, android.hardware.security.keymint.ProtectedData protectedData) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
        public byte[] generateCertificateRequestV2(android.hardware.security.keymint.MacedPublicKey[] macedPublicKeyArr, byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.security.keymint.IRemotelyProvisionedComponent {
        static final int TRANSACTION_generateCertificateRequest = 3;
        static final int TRANSACTION_generateCertificateRequestV2 = 4;
        static final int TRANSACTION_generateEcdsaP256KeyPair = 2;
        static final int TRANSACTION_getHardwareInfo = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.security.keymint.IRemotelyProvisionedComponent.DESCRIPTOR);
        }

        public static android.hardware.security.keymint.IRemotelyProvisionedComponent asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.security.keymint.IRemotelyProvisionedComponent.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.security.keymint.IRemotelyProvisionedComponent)) {
                return (android.hardware.security.keymint.IRemotelyProvisionedComponent) queryLocalInterface;
            }
            return new android.hardware.security.keymint.IRemotelyProvisionedComponent.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.security.keymint.IRemotelyProvisionedComponent.DESCRIPTOR;
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
                    android.hardware.security.keymint.RpcHardwareInfo hardwareInfo = getHardwareInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(hardwareInfo, 1);
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    android.hardware.security.keymint.MacedPublicKey macedPublicKey = new android.hardware.security.keymint.MacedPublicKey();
                    parcel.enforceNoDataAvail();
                    byte[] generateEcdsaP256KeyPair = generateEcdsaP256KeyPair(readBoolean, macedPublicKey);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(generateEcdsaP256KeyPair);
                    parcel2.writeTypedObject(macedPublicKey, 1);
                    return true;
                case 3:
                    boolean readBoolean2 = parcel.readBoolean();
                    android.hardware.security.keymint.MacedPublicKey[] macedPublicKeyArr = (android.hardware.security.keymint.MacedPublicKey[]) parcel.createTypedArray(android.hardware.security.keymint.MacedPublicKey.CREATOR);
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    android.hardware.security.keymint.DeviceInfo deviceInfo = new android.hardware.security.keymint.DeviceInfo();
                    android.hardware.security.keymint.ProtectedData protectedData = new android.hardware.security.keymint.ProtectedData();
                    parcel.enforceNoDataAvail();
                    byte[] generateCertificateRequest = generateCertificateRequest(readBoolean2, macedPublicKeyArr, createByteArray, createByteArray2, deviceInfo, protectedData);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(generateCertificateRequest);
                    parcel2.writeTypedObject(deviceInfo, 1);
                    parcel2.writeTypedObject(protectedData, 1);
                    return true;
                case 4:
                    android.hardware.security.keymint.MacedPublicKey[] macedPublicKeyArr2 = (android.hardware.security.keymint.MacedPublicKey[]) parcel.createTypedArray(android.hardware.security.keymint.MacedPublicKey.CREATOR);
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] generateCertificateRequestV2 = generateCertificateRequestV2(macedPublicKeyArr2, createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(generateCertificateRequestV2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.security.keymint.IRemotelyProvisionedComponent {
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
                return android.hardware.security.keymint.IRemotelyProvisionedComponent.DESCRIPTOR;
            }

            @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
            public android.hardware.security.keymint.RpcHardwareInfo getHardwareInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.security.keymint.IRemotelyProvisionedComponent.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getHardwareInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.security.keymint.RpcHardwareInfo) obtain2.readTypedObject(android.hardware.security.keymint.RpcHardwareInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
            public byte[] generateEcdsaP256KeyPair(boolean z, android.hardware.security.keymint.MacedPublicKey macedPublicKey) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.security.keymint.IRemotelyProvisionedComponent.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method generateEcdsaP256KeyPair is unimplemented.");
                    }
                    obtain2.readException();
                    byte[] createByteArray = obtain2.createByteArray();
                    if (obtain2.readInt() != 0) {
                        macedPublicKey.readFromParcel(obtain2);
                    }
                    return createByteArray;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
            public byte[] generateCertificateRequest(boolean z, android.hardware.security.keymint.MacedPublicKey[] macedPublicKeyArr, byte[] bArr, byte[] bArr2, android.hardware.security.keymint.DeviceInfo deviceInfo, android.hardware.security.keymint.ProtectedData protectedData) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.security.keymint.IRemotelyProvisionedComponent.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeTypedArray(macedPublicKeyArr, 0);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method generateCertificateRequest is unimplemented.");
                    }
                    obtain2.readException();
                    byte[] createByteArray = obtain2.createByteArray();
                    if (obtain2.readInt() != 0) {
                        deviceInfo.readFromParcel(obtain2);
                    }
                    if (obtain2.readInt() != 0) {
                        protectedData.readFromParcel(obtain2);
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return createByteArray;
                } catch (java.lang.Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }

            @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
            public byte[] generateCertificateRequestV2(android.hardware.security.keymint.MacedPublicKey[] macedPublicKeyArr, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.security.keymint.IRemotelyProvisionedComponent.DESCRIPTOR);
                    obtain.writeTypedArray(macedPublicKeyArr, 0);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method generateCertificateRequestV2 is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.security.keymint.IRemotelyProvisionedComponent.DESCRIPTOR);
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

            @Override // android.hardware.security.keymint.IRemotelyProvisionedComponent
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.security.keymint.IRemotelyProvisionedComponent.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.security.keymint.IRemotelyProvisionedComponent.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
