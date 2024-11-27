package android.hardware.biometrics.face;

/* loaded from: classes.dex */
public interface ISession extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$biometrics$face$ISession".replace('$', '.');
    public static final java.lang.String HASH = "c43fbb9be4a662cc9ace640dba21cccdb84c6c21";
    public static final int VERSION = 4;

    android.hardware.biometrics.common.ICancellationSignal authenticate(long j) throws android.os.RemoteException;

    android.hardware.biometrics.common.ICancellationSignal authenticateWithContext(long j, android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException;

    void close() throws android.os.RemoteException;

    android.hardware.biometrics.common.ICancellationSignal detectInteraction() throws android.os.RemoteException;

    android.hardware.biometrics.common.ICancellationSignal detectInteractionWithContext(android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException;

    @java.lang.Deprecated
    android.hardware.biometrics.common.ICancellationSignal enroll(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, android.hardware.common.NativeHandle nativeHandle) throws android.os.RemoteException;

    @java.lang.Deprecated
    android.hardware.biometrics.common.ICancellationSignal enrollWithContext(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, android.hardware.common.NativeHandle nativeHandle, android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException;

    android.hardware.biometrics.common.ICancellationSignal enrollWithOptions(android.hardware.biometrics.face.FaceEnrollOptions faceEnrollOptions) throws android.os.RemoteException;

    void enumerateEnrollments() throws android.os.RemoteException;

    void generateChallenge() throws android.os.RemoteException;

    void getAuthenticatorId() throws android.os.RemoteException;

    android.hardware.biometrics.face.EnrollmentStageConfig[] getEnrollmentConfig(byte b) throws android.os.RemoteException;

    void getFeatures() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void invalidateAuthenticatorId() throws android.os.RemoteException;

    void onContextChanged(android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException;

    void removeEnrollments(int[] iArr) throws android.os.RemoteException;

    void resetLockout(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException;

    void revokeChallenge(long j) throws android.os.RemoteException;

    void setFeature(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, byte b, boolean z) throws android.os.RemoteException;

    public static class Default implements android.hardware.biometrics.face.ISession {
        @Override // android.hardware.biometrics.face.ISession
        public void generateChallenge() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void revokeChallenge(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public android.hardware.biometrics.face.EnrollmentStageConfig[] getEnrollmentConfig(byte b) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public android.hardware.biometrics.common.ICancellationSignal enroll(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, android.hardware.common.NativeHandle nativeHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public android.hardware.biometrics.common.ICancellationSignal authenticate(long j) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public android.hardware.biometrics.common.ICancellationSignal detectInteraction() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public void enumerateEnrollments() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void removeEnrollments(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void getFeatures() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void setFeature(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, byte b, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void getAuthenticatorId() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void invalidateAuthenticatorId() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void resetLockout(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void close() throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public android.hardware.biometrics.common.ICancellationSignal authenticateWithContext(long j, android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public android.hardware.biometrics.common.ICancellationSignal enrollWithContext(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, android.hardware.common.NativeHandle nativeHandle, android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public android.hardware.biometrics.common.ICancellationSignal detectInteractionWithContext(android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public void onContextChanged(android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public android.hardware.biometrics.common.ICancellationSignal enrollWithOptions(android.hardware.biometrics.face.FaceEnrollOptions faceEnrollOptions) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.biometrics.face.ISession
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.biometrics.face.ISession {
        static final int TRANSACTION_authenticate = 5;
        static final int TRANSACTION_authenticateWithContext = 15;
        static final int TRANSACTION_close = 14;
        static final int TRANSACTION_detectInteraction = 6;
        static final int TRANSACTION_detectInteractionWithContext = 17;
        static final int TRANSACTION_enroll = 4;
        static final int TRANSACTION_enrollWithContext = 16;
        static final int TRANSACTION_enrollWithOptions = 19;
        static final int TRANSACTION_enumerateEnrollments = 7;
        static final int TRANSACTION_generateChallenge = 1;
        static final int TRANSACTION_getAuthenticatorId = 11;
        static final int TRANSACTION_getEnrollmentConfig = 3;
        static final int TRANSACTION_getFeatures = 9;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_invalidateAuthenticatorId = 12;
        static final int TRANSACTION_onContextChanged = 18;
        static final int TRANSACTION_removeEnrollments = 8;
        static final int TRANSACTION_resetLockout = 13;
        static final int TRANSACTION_revokeChallenge = 2;
        static final int TRANSACTION_setFeature = 10;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.biometrics.face.ISession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.biometrics.face.ISession)) {
                return (android.hardware.biometrics.face.ISession) queryLocalInterface;
            }
            return new android.hardware.biometrics.face.ISession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "generateChallenge";
                case 2:
                    return "revokeChallenge";
                case 3:
                    return "getEnrollmentConfig";
                case 4:
                    return "enroll";
                case 5:
                    return "authenticate";
                case 6:
                    return "detectInteraction";
                case 7:
                    return "enumerateEnrollments";
                case 8:
                    return "removeEnrollments";
                case 9:
                    return "getFeatures";
                case 10:
                    return "setFeature";
                case 11:
                    return "getAuthenticatorId";
                case 12:
                    return "invalidateAuthenticatorId";
                case 13:
                    return "resetLockout";
                case 14:
                    return "close";
                case 15:
                    return "authenticateWithContext";
                case 16:
                    return "enrollWithContext";
                case 17:
                    return "detectInteractionWithContext";
                case 18:
                    return "onContextChanged";
                case 19:
                    return "enrollWithOptions";
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
                    generateChallenge();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    revokeChallenge(readLong);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.face.EnrollmentStageConfig[] enrollmentConfig = getEnrollmentConfig(readByte);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(enrollmentConfig, 1);
                    return true;
                case 4:
                    android.hardware.keymaster.HardwareAuthToken hardwareAuthToken = (android.hardware.keymaster.HardwareAuthToken) parcel.readTypedObject(android.hardware.keymaster.HardwareAuthToken.CREATOR);
                    byte readByte2 = parcel.readByte();
                    byte[] createByteArray = parcel.createByteArray();
                    android.hardware.common.NativeHandle nativeHandle = (android.hardware.common.NativeHandle) parcel.readTypedObject(android.hardware.common.NativeHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.common.ICancellationSignal enroll = enroll(hardwareAuthToken, readByte2, createByteArray, nativeHandle);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(enroll);
                    return true;
                case 5:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.common.ICancellationSignal authenticate = authenticate(readLong2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(authenticate);
                    return true;
                case 6:
                    android.hardware.biometrics.common.ICancellationSignal detectInteraction = detectInteraction();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(detectInteraction);
                    return true;
                case 7:
                    enumerateEnrollments();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    removeEnrollments(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    getFeatures();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.hardware.keymaster.HardwareAuthToken hardwareAuthToken2 = (android.hardware.keymaster.HardwareAuthToken) parcel.readTypedObject(android.hardware.keymaster.HardwareAuthToken.CREATOR);
                    byte readByte3 = parcel.readByte();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFeature(hardwareAuthToken2, readByte3, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    getAuthenticatorId();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    invalidateAuthenticatorId();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.hardware.keymaster.HardwareAuthToken hardwareAuthToken3 = (android.hardware.keymaster.HardwareAuthToken) parcel.readTypedObject(android.hardware.keymaster.HardwareAuthToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    resetLockout(hardwareAuthToken3);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    long readLong3 = parcel.readLong();
                    android.hardware.biometrics.common.OperationContext operationContext = (android.hardware.biometrics.common.OperationContext) parcel.readTypedObject(android.hardware.biometrics.common.OperationContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.common.ICancellationSignal authenticateWithContext = authenticateWithContext(readLong3, operationContext);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(authenticateWithContext);
                    return true;
                case 16:
                    android.hardware.keymaster.HardwareAuthToken hardwareAuthToken4 = (android.hardware.keymaster.HardwareAuthToken) parcel.readTypedObject(android.hardware.keymaster.HardwareAuthToken.CREATOR);
                    byte readByte4 = parcel.readByte();
                    byte[] createByteArray2 = parcel.createByteArray();
                    android.hardware.common.NativeHandle nativeHandle2 = (android.hardware.common.NativeHandle) parcel.readTypedObject(android.hardware.common.NativeHandle.CREATOR);
                    android.hardware.biometrics.common.OperationContext operationContext2 = (android.hardware.biometrics.common.OperationContext) parcel.readTypedObject(android.hardware.biometrics.common.OperationContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.common.ICancellationSignal enrollWithContext = enrollWithContext(hardwareAuthToken4, readByte4, createByteArray2, nativeHandle2, operationContext2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(enrollWithContext);
                    return true;
                case 17:
                    android.hardware.biometrics.common.OperationContext operationContext3 = (android.hardware.biometrics.common.OperationContext) parcel.readTypedObject(android.hardware.biometrics.common.OperationContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.common.ICancellationSignal detectInteractionWithContext = detectInteractionWithContext(operationContext3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(detectInteractionWithContext);
                    return true;
                case 18:
                    android.hardware.biometrics.common.OperationContext operationContext4 = (android.hardware.biometrics.common.OperationContext) parcel.readTypedObject(android.hardware.biometrics.common.OperationContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onContextChanged(operationContext4);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    android.hardware.biometrics.face.FaceEnrollOptions faceEnrollOptions = (android.hardware.biometrics.face.FaceEnrollOptions) parcel.readTypedObject(android.hardware.biometrics.face.FaceEnrollOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.common.ICancellationSignal enrollWithOptions = enrollWithOptions(faceEnrollOptions);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(enrollWithOptions);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.biometrics.face.ISession {
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

            @Override // android.hardware.biometrics.face.ISession
            public void generateChallenge() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method generateChallenge is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void revokeChallenge(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method revokeChallenge is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public android.hardware.biometrics.face.EnrollmentStageConfig[] getEnrollmentConfig(byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByte(b);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getEnrollmentConfig is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.biometrics.face.EnrollmentStageConfig[]) obtain2.createTypedArray(android.hardware.biometrics.face.EnrollmentStageConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public android.hardware.biometrics.common.ICancellationSignal enroll(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, android.hardware.common.NativeHandle nativeHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    obtain.writeByte(b);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(nativeHandle, 0);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method enroll is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.biometrics.common.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public android.hardware.biometrics.common.ICancellationSignal authenticate(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method authenticate is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.biometrics.common.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public android.hardware.biometrics.common.ICancellationSignal detectInteraction() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method detectInteraction is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.biometrics.common.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void enumerateEnrollments() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method enumerateEnrollments is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void removeEnrollments(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method removeEnrollments is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void getFeatures() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getFeatures is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void setFeature(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, byte b, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    obtain.writeByte(b);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setFeature is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void getAuthenticatorId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getAuthenticatorId is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void invalidateAuthenticatorId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method invalidateAuthenticatorId is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void resetLockout(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method resetLockout is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public android.hardware.biometrics.common.ICancellationSignal authenticateWithContext(long j, android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method authenticateWithContext is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.biometrics.common.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public android.hardware.biometrics.common.ICancellationSignal enrollWithContext(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, android.hardware.common.NativeHandle nativeHandle, android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    obtain.writeByte(b);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(nativeHandle, 0);
                    obtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method enrollWithContext is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.biometrics.common.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public android.hardware.biometrics.common.ICancellationSignal detectInteractionWithContext(android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method detectInteractionWithContext is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.biometrics.common.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void onContextChanged(android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(18, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method onContextChanged is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public android.hardware.biometrics.common.ICancellationSignal enrollWithOptions(android.hardware.biometrics.face.FaceEnrollOptions faceEnrollOptions) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(faceEnrollOptions, 0);
                    if (!this.mRemote.transact(19, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method enrollWithOptions is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.biometrics.common.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
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

            @Override // android.hardware.biometrics.face.ISession
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
