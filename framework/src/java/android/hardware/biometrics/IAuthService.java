package android.hardware.biometrics;

/* loaded from: classes.dex */
public interface IAuthService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.biometrics.IAuthService";

    long authenticate(android.os.IBinder iBinder, long j, int i, android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver, java.lang.String str, android.hardware.biometrics.PromptInfo promptInfo) throws android.os.RemoteException;

    int canAuthenticate(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException;

    android.hardware.biometrics.ITestSession createTestSession(int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException;

    long[] getAuthenticatorIds(int i) throws android.os.RemoteException;

    java.lang.CharSequence getButtonLabel(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    long getLastAuthenticationTime(int i, int i2) throws android.os.RemoteException;

    java.lang.CharSequence getPromptMessage(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    java.util.List<android.hardware.biometrics.SensorPropertiesInternal> getSensorProperties(java.lang.String str) throws android.os.RemoteException;

    java.lang.CharSequence getSettingName(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    java.lang.String getUiPackage() throws android.os.RemoteException;

    boolean hasEnrolledBiometrics(int i, java.lang.String str) throws android.os.RemoteException;

    void invalidateAuthenticatorIds(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException;

    void registerAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException;

    void registerEnabledOnKeyguardCallback(android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws android.os.RemoteException;

    void resetLockout(int i, byte[] bArr) throws android.os.RemoteException;

    void resetLockoutTimeBound(android.os.IBinder iBinder, java.lang.String str, int i, int i2, byte[] bArr) throws android.os.RemoteException;

    void unregisterAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException;

    public static class Default implements android.hardware.biometrics.IAuthService {
        @Override // android.hardware.biometrics.IAuthService
        public android.hardware.biometrics.ITestSession createTestSession(int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public java.util.List<android.hardware.biometrics.SensorPropertiesInternal> getSensorProperties(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public java.lang.String getUiPackage() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public long authenticate(android.os.IBinder iBinder, long j, int i, android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver, java.lang.String str, android.hardware.biometrics.PromptInfo promptInfo) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.biometrics.IAuthService
        public void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IAuthService
        public int canAuthenticate(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IAuthService
        public long getLastAuthenticationTime(int i, int i2) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.biometrics.IAuthService
        public boolean hasEnrolledBiometrics(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.biometrics.IAuthService
        public void registerEnabledOnKeyguardCallback(android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IAuthService
        public void registerAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IAuthService
        public void unregisterAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IAuthService
        public void invalidateAuthenticatorIds(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IAuthService
        public long[] getAuthenticatorIds(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public void resetLockoutTimeBound(android.os.IBinder iBinder, java.lang.String str, int i, int i2, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IAuthService
        public void resetLockout(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.biometrics.IAuthService
        public java.lang.CharSequence getButtonLabel(int i, java.lang.String str, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public java.lang.CharSequence getPromptMessage(int i, java.lang.String str, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public java.lang.CharSequence getSettingName(int i, java.lang.String str, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.biometrics.IAuthService {
        static final int TRANSACTION_authenticate = 4;
        static final int TRANSACTION_canAuthenticate = 6;
        static final int TRANSACTION_cancelAuthentication = 5;
        static final int TRANSACTION_createTestSession = 1;
        static final int TRANSACTION_getAuthenticatorIds = 13;
        static final int TRANSACTION_getButtonLabel = 16;
        static final int TRANSACTION_getLastAuthenticationTime = 7;
        static final int TRANSACTION_getPromptMessage = 17;
        static final int TRANSACTION_getSensorProperties = 2;
        static final int TRANSACTION_getSettingName = 18;
        static final int TRANSACTION_getUiPackage = 3;
        static final int TRANSACTION_hasEnrolledBiometrics = 8;
        static final int TRANSACTION_invalidateAuthenticatorIds = 12;
        static final int TRANSACTION_registerAuthenticationStateListener = 10;
        static final int TRANSACTION_registerEnabledOnKeyguardCallback = 9;
        static final int TRANSACTION_resetLockout = 15;
        static final int TRANSACTION_resetLockoutTimeBound = 14;
        static final int TRANSACTION_unregisterAuthenticationStateListener = 11;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, android.hardware.biometrics.IAuthService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.hardware.biometrics.IAuthService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.biometrics.IAuthService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.biometrics.IAuthService)) {
                return (android.hardware.biometrics.IAuthService) queryLocalInterface;
            }
            return new android.hardware.biometrics.IAuthService.Stub.Proxy(iBinder);
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
                    return "getUiPackage";
                case 4:
                    return "authenticate";
                case 5:
                    return "cancelAuthentication";
                case 6:
                    return "canAuthenticate";
                case 7:
                    return "getLastAuthenticationTime";
                case 8:
                    return "hasEnrolledBiometrics";
                case 9:
                    return "registerEnabledOnKeyguardCallback";
                case 10:
                    return "registerAuthenticationStateListener";
                case 11:
                    return "unregisterAuthenticationStateListener";
                case 12:
                    return "invalidateAuthenticatorIds";
                case 13:
                    return "getAuthenticatorIds";
                case 14:
                    return "resetLockoutTimeBound";
                case 15:
                    return "resetLockout";
                case 16:
                    return "getButtonLabel";
                case 17:
                    return "getPromptMessage";
                case 18:
                    return "getSettingName";
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
                parcel.enforceInterface(android.hardware.biometrics.IAuthService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.biometrics.IAuthService.DESCRIPTOR);
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
                    java.lang.String uiPackage = getUiPackage();
                    parcel2.writeNoException();
                    parcel2.writeString(uiPackage);
                    return true;
                case 4:
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
                case 5:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    java.lang.String readString4 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthentication(readStrongBinder2, readString4, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString5 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int canAuthenticate = canAuthenticate(readString5, readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(canAuthenticate);
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long lastAuthenticationTime = getLastAuthenticationTime(readInt5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastAuthenticationTime);
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasEnrolledBiometrics = hasEnrolledBiometrics(readInt7, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnrolledBiometrics);
                    return true;
                case 9:
                    android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback asInterface3 = android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerEnabledOnKeyguardCallback(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.hardware.biometrics.AuthenticationStateListener asInterface4 = android.hardware.biometrics.AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAuthenticationStateListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.hardware.biometrics.AuthenticationStateListener asInterface5 = android.hardware.biometrics.AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAuthenticationStateListener(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    android.hardware.biometrics.IInvalidationCallback asInterface6 = android.hardware.biometrics.IInvalidationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    invalidateAuthenticatorIds(readInt8, readInt9, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long[] authenticatorIds = getAuthenticatorIds(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(authenticatorIds);
                    return true;
                case 14:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    java.lang.String readString7 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetLockoutTimeBound(readStrongBinder3, readString7, readInt11, readInt12, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt13 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetLockout(readInt13, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt14 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence buttonLabel = getButtonLabel(readInt14, readString8, readInt15);
                    parcel2.writeNoException();
                    if (buttonLabel != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(buttonLabel, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 17:
                    int readInt16 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence promptMessage = getPromptMessage(readInt16, readString9, readInt17);
                    parcel2.writeNoException();
                    if (promptMessage != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(promptMessage, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 18:
                    int readInt18 = parcel.readInt();
                    java.lang.String readString10 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence settingName = getSettingName(readInt18, readString10, readInt19);
                    parcel2.writeNoException();
                    if (settingName != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(settingName, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.biometrics.IAuthService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.biometrics.IAuthService.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IAuthService
            public android.hardware.biometrics.ITestSession createTestSession(int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
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

            @Override // android.hardware.biometrics.IAuthService
            public java.util.List<android.hardware.biometrics.SensorPropertiesInternal> getSensorProperties(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.biometrics.SensorPropertiesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public java.lang.String getUiPackage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public long authenticate(android.os.IBinder iBinder, long j, int i, android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver, java.lang.String str, android.hardware.biometrics.PromptInfo promptInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBiometricServiceReceiver);
                    obtain.writeString(str);
                    obtain.writeTypedObject(promptInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public int canAuthenticate(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public long getLastAuthenticationTime(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public boolean hasEnrolledBiometrics(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void registerEnabledOnKeyguardCallback(android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricEnabledOnKeyguardCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void registerAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void unregisterAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void invalidateAuthenticatorIds(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public long[] getAuthenticatorIds(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void resetLockoutTimeBound(android.os.IBinder iBinder, java.lang.String str, int i, int i2, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void resetLockout(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public java.lang.CharSequence getButtonLabel(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public java.lang.CharSequence getPromptMessage(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public java.lang.CharSequence getSettingName(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void createTestSession_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void getSensorProperties_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void getUiPackage_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }
    }
}
