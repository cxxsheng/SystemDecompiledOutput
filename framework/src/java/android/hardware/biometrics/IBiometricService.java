package android.hardware.biometrics;

/* loaded from: classes.dex */
public interface IBiometricService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.biometrics.IBiometricService";

    long authenticate(android.os.IBinder iBinder, long j, int i, android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver, java.lang.String str, android.hardware.biometrics.PromptInfo promptInfo) throws android.os.RemoteException;

    int canAuthenticate(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException;

    android.hardware.biometrics.ITestSession createTestSession(int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException;

    long[] getAuthenticatorIds(int i) throws android.os.RemoteException;

    int getCurrentModality(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    int getCurrentStrength(int i) throws android.os.RemoteException;

    long getLastAuthenticationTime(int i, int i2) throws android.os.RemoteException;

    java.util.List<android.hardware.biometrics.SensorPropertiesInternal> getSensorProperties(java.lang.String str) throws android.os.RemoteException;

    int getSupportedModalities(int i) throws android.os.RemoteException;

    boolean hasEnrolledBiometrics(int i, java.lang.String str) throws android.os.RemoteException;

    void invalidateAuthenticatorIds(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException;

    void onReadyForAuthentication(long j, int i) throws android.os.RemoteException;

    void registerAuthenticator(int i, int i2, int i3, android.hardware.biometrics.IBiometricAuthenticator iBiometricAuthenticator) throws android.os.RemoteException;

    void registerEnabledOnKeyguardCallback(android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws android.os.RemoteException;

    void resetLockout(int i, byte[] bArr) throws android.os.RemoteException;

    void resetLockoutTimeBound(android.os.IBinder iBinder, java.lang.String str, int i, int i2, byte[] bArr) throws android.os.RemoteException;

    public static class Default implements android.hardware.biometrics.IBiometricService {
        @Override // android.hardware.biometrics.IBiometricService
        public android.hardware.biometrics.ITestSession createTestSession(int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public java.util.List<android.hardware.biometrics.SensorPropertiesInternal> getSensorProperties(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public long authenticate(android.os.IBinder iBinder, long j, int i, android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver, java.lang.String str, android.hardware.biometrics.PromptInfo promptInfo) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public int canAuthenticate(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public long getLastAuthenticationTime(int i, int i2) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public boolean hasEnrolledBiometrics(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void registerAuthenticator(int i, int i2, int i3, android.hardware.biometrics.IBiometricAuthenticator iBiometricAuthenticator) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void registerEnabledOnKeyguardCallback(android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void onReadyForAuthentication(long j, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void invalidateAuthenticatorIds(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public long[] getAuthenticatorIds(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void resetLockoutTimeBound(android.os.IBinder iBinder, java.lang.String str, int i, int i2, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void resetLockout(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public int getCurrentStrength(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public int getCurrentModality(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public int getSupportedModalities(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.biometrics.IBiometricService {
        static final int TRANSACTION_authenticate = 3;
        static final int TRANSACTION_canAuthenticate = 5;
        static final int TRANSACTION_cancelAuthentication = 4;
        static final int TRANSACTION_createTestSession = 1;
        static final int TRANSACTION_getAuthenticatorIds = 12;
        static final int TRANSACTION_getCurrentModality = 16;
        static final int TRANSACTION_getCurrentStrength = 15;
        static final int TRANSACTION_getLastAuthenticationTime = 6;
        static final int TRANSACTION_getSensorProperties = 2;
        static final int TRANSACTION_getSupportedModalities = 17;
        static final int TRANSACTION_hasEnrolledBiometrics = 7;
        static final int TRANSACTION_invalidateAuthenticatorIds = 11;
        static final int TRANSACTION_onReadyForAuthentication = 10;
        static final int TRANSACTION_registerAuthenticator = 8;
        static final int TRANSACTION_registerEnabledOnKeyguardCallback = 9;
        static final int TRANSACTION_resetLockout = 14;
        static final int TRANSACTION_resetLockoutTimeBound = 13;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, android.hardware.biometrics.IBiometricService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.hardware.biometrics.IBiometricService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.biometrics.IBiometricService)) {
                return (android.hardware.biometrics.IBiometricService) queryLocalInterface;
            }
            return new android.hardware.biometrics.IBiometricService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createTestSession";
                case 2:
                    return "getSensorProperties";
                case 3:
                    return "authenticate";
                case 4:
                    return "cancelAuthentication";
                case 5:
                    return "canAuthenticate";
                case 6:
                    return "getLastAuthenticationTime";
                case 7:
                    return "hasEnrolledBiometrics";
                case 8:
                    return "registerAuthenticator";
                case 9:
                    return "registerEnabledOnKeyguardCallback";
                case 10:
                    return "onReadyForAuthentication";
                case 11:
                    return "invalidateAuthenticatorIds";
                case 12:
                    return "getAuthenticatorIds";
                case 13:
                    return "resetLockoutTimeBound";
                case 14:
                    return "resetLockout";
                case 15:
                    return "getCurrentStrength";
                case 16:
                    return "getCurrentModality";
                case 17:
                    return "getSupportedModalities";
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
                parcel.enforceInterface(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.hardware.biometrics.ITestSessionCallback asInterface = android.hardware.biometrics.ITestSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.hardware.biometrics.ITestSession createTestSession = createTestSession(readInt, asInterface, readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createTestSession);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.hardware.biometrics.SensorPropertiesInternal> sensorProperties = getSensorProperties(readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(sensorProperties, 1);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    long readLong = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    android.hardware.biometrics.IBiometricServiceReceiver asInterface2 = android.hardware.biometrics.IBiometricServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString3 = parcel.readString();
                    android.hardware.biometrics.PromptInfo promptInfo = (android.hardware.biometrics.PromptInfo) parcel.readTypedObject(android.hardware.biometrics.PromptInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long authenticate = authenticate(readStrongBinder, readLong, readInt2, asInterface2, readString3, promptInfo);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticate);
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    java.lang.String readString4 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthentication(readStrongBinder2, readString4, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int canAuthenticate = canAuthenticate(readString5, readInt3, readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(canAuthenticate);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long lastAuthenticationTime = getLastAuthenticationTime(readInt6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastAuthenticationTime);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasEnrolledBiometrics = hasEnrolledBiometrics(readInt8, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnrolledBiometrics);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    android.hardware.biometrics.IBiometricAuthenticator asInterface3 = android.hardware.biometrics.IBiometricAuthenticator.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAuthenticator(readInt9, readInt10, readInt11, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback asInterface4 = android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerEnabledOnKeyguardCallback(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    long readLong3 = parcel.readLong();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onReadyForAuthentication(readLong3, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    android.hardware.biometrics.IInvalidationCallback asInterface5 = android.hardware.biometrics.IInvalidationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    invalidateAuthenticatorIds(readInt13, readInt14, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long[] authenticatorIds = getAuthenticatorIds(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(authenticatorIds);
                    return true;
                case 13:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    java.lang.String readString7 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetLockoutTimeBound(readStrongBinder3, readString7, readInt16, readInt17, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetLockout(readInt18, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int currentStrength = getCurrentStrength(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentStrength);
                    return true;
                case 16:
                    java.lang.String readString8 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int currentModality = getCurrentModality(readString8, readInt20, readInt21, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentModality);
                    return true;
                case 17:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int supportedModalities = getSupportedModalities(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeInt(supportedModalities);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.biometrics.IBiometricService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.biometrics.IBiometricService.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricService
            public android.hardware.biometrics.ITestSession createTestSession(int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTestSessionCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.biometrics.ITestSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public java.util.List<android.hardware.biometrics.SensorPropertiesInternal> getSensorProperties(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.biometrics.SensorPropertiesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public long authenticate(android.os.IBinder iBinder, long j, int i, android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver, java.lang.String str, android.hardware.biometrics.PromptInfo promptInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBiometricServiceReceiver);
                    obtain.writeString(str);
                    obtain.writeTypedObject(promptInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int canAuthenticate(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public long getLastAuthenticationTime(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public boolean hasEnrolledBiometrics(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void registerAuthenticator(int i, int i2, int i3, android.hardware.biometrics.IBiometricAuthenticator iBiometricAuthenticator) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iBiometricAuthenticator);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void registerEnabledOnKeyguardCallback(android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricEnabledOnKeyguardCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void onReadyForAuthentication(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void invalidateAuthenticatorIds(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public long[] getAuthenticatorIds(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void resetLockoutTimeBound(android.os.IBinder iBinder, java.lang.String str, int i, int i2, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void resetLockout(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int getCurrentStrength(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int getCurrentModality(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int getSupportedModalities(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void createTestSession_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getSensorProperties_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void authenticate_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void cancelAuthentication_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void canAuthenticate_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getLastAuthenticationTime_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void hasEnrolledBiometrics_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void registerAuthenticator_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void registerEnabledOnKeyguardCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void onReadyForAuthentication_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void invalidateAuthenticatorIds_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getAuthenticatorIds_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void resetLockoutTimeBound_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void resetLockout_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getCurrentStrength_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getCurrentModality_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getSupportedModalities_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16;
        }
    }
}
