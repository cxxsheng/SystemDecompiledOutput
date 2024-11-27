package android.hardware.fingerprint;

/* loaded from: classes2.dex */
public interface IFingerprintService extends android.os.IInterface {
    void addAuthenticatorsRegisteredCallback(android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback iFingerprintAuthenticatorsRegisteredCallback) throws android.os.RemoteException;

    void addClientActiveCallback(android.hardware.fingerprint.IFingerprintClientActiveCallback iFingerprintClientActiveCallback) throws android.os.RemoteException;

    void addLockoutResetCallback(android.hardware.biometrics.IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, java.lang.String str) throws android.os.RemoteException;

    long authenticate(android.os.IBinder iBinder, long j, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions) throws android.os.RemoteException;

    void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, long j) throws android.os.RemoteException;

    void cancelAuthenticationFromService(int i, android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException;

    void cancelEnrollment(android.os.IBinder iBinder, long j) throws android.os.RemoteException;

    void cancelFingerprintDetect(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException;

    android.hardware.biometrics.ITestSession createTestSession(int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException;

    long detectFingerprint(android.os.IBinder iBinder, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions) throws android.os.RemoteException;

    byte[] dumpSensorServiceStateProto(int i, boolean z) throws android.os.RemoteException;

    long enroll(android.os.IBinder iBinder, byte[] bArr, int i, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str, int i2, android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions) throws android.os.RemoteException;

    void generateChallenge(android.os.IBinder iBinder, int i, int i2, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str) throws android.os.RemoteException;

    long getAuthenticatorId(int i, int i2) throws android.os.RemoteException;

    java.util.List<android.hardware.fingerprint.Fingerprint> getEnrolledFingerprints(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getLockoutModeForUser(int i, int i2) throws android.os.RemoteException;

    android.hardware.fingerprint.FingerprintSensorPropertiesInternal getSensorProperties(int i, java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> getSensorPropertiesInternal(java.lang.String str) throws android.os.RemoteException;

    boolean hasEnrolledFingerprints(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    boolean hasEnrolledFingerprintsDeprecated(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void invalidateAuthenticatorId(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException;

    boolean isClientActive() throws android.os.RemoteException;

    boolean isHardwareDetected(int i, java.lang.String str) throws android.os.RemoteException;

    boolean isHardwareDetectedDeprecated(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void onPointerDown(long j, int i, android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException;

    void onPointerUp(long j, int i, android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException;

    void onPowerPressed() throws android.os.RemoteException;

    void onUdfpsUiEvent(int i, long j, int i2) throws android.os.RemoteException;

    void prepareForAuthentication(android.os.IBinder iBinder, long j, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, long j2, int i, boolean z, boolean z2) throws android.os.RemoteException;

    void registerAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException;

    void registerAuthenticators(java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> list) throws android.os.RemoteException;

    void registerAuthenticatorsLegacy(android.hardware.fingerprint.FingerprintSensorConfigurations fingerprintSensorConfigurations) throws android.os.RemoteException;

    void registerBiometricStateListener(android.hardware.biometrics.IBiometricStateListener iBiometricStateListener) throws android.os.RemoteException;

    void remove(android.os.IBinder iBinder, int i, int i2, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str) throws android.os.RemoteException;

    void removeAll(android.os.IBinder iBinder, int i, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str) throws android.os.RemoteException;

    void removeClientActiveCallback(android.hardware.fingerprint.IFingerprintClientActiveCallback iFingerprintClientActiveCallback) throws android.os.RemoteException;

    void rename(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    void resetLockout(android.os.IBinder iBinder, int i, int i2, byte[] bArr, java.lang.String str) throws android.os.RemoteException;

    void revokeChallenge(android.os.IBinder iBinder, int i, int i2, java.lang.String str, long j) throws android.os.RemoteException;

    void scheduleWatchdog() throws android.os.RemoteException;

    void setSidefpsController(android.hardware.fingerprint.ISidefpsController iSidefpsController) throws android.os.RemoteException;

    void setUdfpsOverlayController(android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController) throws android.os.RemoteException;

    void startPreparedClient(int i, int i2) throws android.os.RemoteException;

    void unregisterAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException;

    public static class Default implements android.hardware.fingerprint.IFingerprintService {
        @Override // android.hardware.fingerprint.IFingerprintService
        public android.hardware.biometrics.ITestSession createTestSession(int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public byte[] dumpSensorServiceStateProto(int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> getSensorPropertiesInternal(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public android.hardware.fingerprint.FingerprintSensorPropertiesInternal getSensorProperties(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public long authenticate(android.os.IBinder iBinder, long j, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public long detectFingerprint(android.os.IBinder iBinder, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void prepareForAuthentication(android.os.IBinder iBinder, long j, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, long j2, int i, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void startPreparedClient(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void cancelFingerprintDetect(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void cancelAuthenticationFromService(int i, android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public long enroll(android.os.IBinder iBinder, byte[] bArr, int i, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str, int i2, android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void cancelEnrollment(android.os.IBinder iBinder, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void remove(android.os.IBinder iBinder, int i, int i2, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void removeAll(android.os.IBinder iBinder, int i, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void rename(int i, int i2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public java.util.List<android.hardware.fingerprint.Fingerprint> getEnrolledFingerprints(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean isHardwareDetectedDeprecated(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean isHardwareDetected(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void generateChallenge(android.os.IBinder iBinder, int i, int i2, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void revokeChallenge(android.os.IBinder iBinder, int i, int i2, java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean hasEnrolledFingerprintsDeprecated(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean hasEnrolledFingerprints(int i, int i2, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int getLockoutModeForUser(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void invalidateAuthenticatorId(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public long getAuthenticatorId(int i, int i2) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void resetLockout(android.os.IBinder iBinder, int i, int i2, byte[] bArr, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void addLockoutResetCallback(android.hardware.biometrics.IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean isClientActive() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void addClientActiveCallback(android.hardware.fingerprint.IFingerprintClientActiveCallback iFingerprintClientActiveCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void removeClientActiveCallback(android.hardware.fingerprint.IFingerprintClientActiveCallback iFingerprintClientActiveCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void registerAuthenticatorsLegacy(android.hardware.fingerprint.FingerprintSensorConfigurations fingerprintSensorConfigurations) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void registerAuthenticators(java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> list) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void addAuthenticatorsRegisteredCallback(android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback iFingerprintAuthenticatorsRegisteredCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void onPointerDown(long j, int i, android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void onPointerUp(long j, int i, android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void onUdfpsUiEvent(int i, long j, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void setUdfpsOverlayController(android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void setSidefpsController(android.hardware.fingerprint.ISidefpsController iSidefpsController) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void registerAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void unregisterAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void registerBiometricStateListener(android.hardware.biometrics.IBiometricStateListener iBiometricStateListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void onPowerPressed() throws android.os.RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void scheduleWatchdog() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.fingerprint.IFingerprintService {
        public static final java.lang.String DESCRIPTOR = "android.hardware.fingerprint.IFingerprintService";
        static final int TRANSACTION_addAuthenticatorsRegisteredCallback = 34;
        static final int TRANSACTION_addClientActiveCallback = 30;
        static final int TRANSACTION_addLockoutResetCallback = 28;
        static final int TRANSACTION_authenticate = 5;
        static final int TRANSACTION_cancelAuthentication = 9;
        static final int TRANSACTION_cancelAuthenticationFromService = 11;
        static final int TRANSACTION_cancelEnrollment = 13;
        static final int TRANSACTION_cancelFingerprintDetect = 10;
        static final int TRANSACTION_createTestSession = 1;
        static final int TRANSACTION_detectFingerprint = 6;
        static final int TRANSACTION_dumpSensorServiceStateProto = 2;
        static final int TRANSACTION_enroll = 12;
        static final int TRANSACTION_generateChallenge = 20;
        static final int TRANSACTION_getAuthenticatorId = 26;
        static final int TRANSACTION_getEnrolledFingerprints = 17;
        static final int TRANSACTION_getLockoutModeForUser = 24;
        static final int TRANSACTION_getSensorProperties = 4;
        static final int TRANSACTION_getSensorPropertiesInternal = 3;
        static final int TRANSACTION_hasEnrolledFingerprints = 23;
        static final int TRANSACTION_hasEnrolledFingerprintsDeprecated = 22;
        static final int TRANSACTION_invalidateAuthenticatorId = 25;
        static final int TRANSACTION_isClientActive = 29;
        static final int TRANSACTION_isHardwareDetected = 19;
        static final int TRANSACTION_isHardwareDetectedDeprecated = 18;
        static final int TRANSACTION_onPointerDown = 35;
        static final int TRANSACTION_onPointerUp = 36;
        static final int TRANSACTION_onPowerPressed = 43;
        static final int TRANSACTION_onUdfpsUiEvent = 37;
        static final int TRANSACTION_prepareForAuthentication = 7;
        static final int TRANSACTION_registerAuthenticationStateListener = 40;
        static final int TRANSACTION_registerAuthenticators = 33;
        static final int TRANSACTION_registerAuthenticatorsLegacy = 32;
        static final int TRANSACTION_registerBiometricStateListener = 42;
        static final int TRANSACTION_remove = 14;
        static final int TRANSACTION_removeAll = 15;
        static final int TRANSACTION_removeClientActiveCallback = 31;
        static final int TRANSACTION_rename = 16;
        static final int TRANSACTION_resetLockout = 27;
        static final int TRANSACTION_revokeChallenge = 21;
        static final int TRANSACTION_scheduleWatchdog = 44;
        static final int TRANSACTION_setSidefpsController = 39;
        static final int TRANSACTION_setUdfpsOverlayController = 38;
        static final int TRANSACTION_startPreparedClient = 8;
        static final int TRANSACTION_unregisterAuthenticationStateListener = 41;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.hardware.fingerprint.IFingerprintService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.fingerprint.IFingerprintService)) {
                return (android.hardware.fingerprint.IFingerprintService) queryLocalInterface;
            }
            return new android.hardware.fingerprint.IFingerprintService.Stub.Proxy(iBinder);
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
                    return "dumpSensorServiceStateProto";
                case 3:
                    return "getSensorPropertiesInternal";
                case 4:
                    return "getSensorProperties";
                case 5:
                    return "authenticate";
                case 6:
                    return "detectFingerprint";
                case 7:
                    return "prepareForAuthentication";
                case 8:
                    return "startPreparedClient";
                case 9:
                    return "cancelAuthentication";
                case 10:
                    return "cancelFingerprintDetect";
                case 11:
                    return "cancelAuthenticationFromService";
                case 12:
                    return "enroll";
                case 13:
                    return "cancelEnrollment";
                case 14:
                    return "remove";
                case 15:
                    return "removeAll";
                case 16:
                    return "rename";
                case 17:
                    return "getEnrolledFingerprints";
                case 18:
                    return "isHardwareDetectedDeprecated";
                case 19:
                    return "isHardwareDetected";
                case 20:
                    return "generateChallenge";
                case 21:
                    return "revokeChallenge";
                case 22:
                    return "hasEnrolledFingerprintsDeprecated";
                case 23:
                    return "hasEnrolledFingerprints";
                case 24:
                    return "getLockoutModeForUser";
                case 25:
                    return "invalidateAuthenticatorId";
                case 26:
                    return "getAuthenticatorId";
                case 27:
                    return "resetLockout";
                case 28:
                    return "addLockoutResetCallback";
                case 29:
                    return "isClientActive";
                case 30:
                    return "addClientActiveCallback";
                case 31:
                    return "removeClientActiveCallback";
                case 32:
                    return "registerAuthenticatorsLegacy";
                case 33:
                    return "registerAuthenticators";
                case 34:
                    return "addAuthenticatorsRegisteredCallback";
                case 35:
                    return "onPointerDown";
                case 36:
                    return "onPointerUp";
                case 37:
                    return "onUdfpsUiEvent";
                case 38:
                    return "setUdfpsOverlayController";
                case 39:
                    return "setSidefpsController";
                case 40:
                    return "registerAuthenticationStateListener";
                case 41:
                    return "unregisterAuthenticationStateListener";
                case 42:
                    return "registerBiometricStateListener";
                case 43:
                    return "onPowerPressed";
                case 44:
                    return "scheduleWatchdog";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
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
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] dumpSensorServiceStateProto = dumpSensorServiceStateProto(readInt2, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(dumpSensorServiceStateProto);
                    return true;
                case 3:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal(readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(sensorPropertiesInternal, 1);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.hardware.fingerprint.FingerprintSensorPropertiesInternal sensorProperties = getSensorProperties(readInt3, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sensorProperties, 1);
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    long readLong = parcel.readLong();
                    android.hardware.fingerprint.IFingerprintServiceReceiver asInterface2 = android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions = (android.hardware.fingerprint.FingerprintAuthenticateOptions) parcel.readTypedObject(android.hardware.fingerprint.FingerprintAuthenticateOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long authenticate = authenticate(readStrongBinder, readLong, asInterface2, fingerprintAuthenticateOptions);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticate);
                    return true;
                case 6:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.hardware.fingerprint.IFingerprintServiceReceiver asInterface3 = android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions2 = (android.hardware.fingerprint.FingerprintAuthenticateOptions) parcel.readTypedObject(android.hardware.fingerprint.FingerprintAuthenticateOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long detectFingerprint = detectFingerprint(readStrongBinder2, asInterface3, fingerprintAuthenticateOptions2);
                    parcel2.writeNoException();
                    parcel2.writeLong(detectFingerprint);
                    return true;
                case 7:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    long readLong2 = parcel.readLong();
                    android.hardware.biometrics.IBiometricSensorReceiver asInterface4 = android.hardware.biometrics.IBiometricSensorReceiver.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions3 = (android.hardware.fingerprint.FingerprintAuthenticateOptions) parcel.readTypedObject(android.hardware.fingerprint.FingerprintAuthenticateOptions.CREATOR);
                    long readLong3 = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    prepareForAuthentication(readStrongBinder3, readLong2, asInterface4, fingerprintAuthenticateOptions3, readLong3, readInt4, readBoolean2, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startPreparedClient(readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthentication(readStrongBinder4, readString4, readString5, readLong4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    java.lang.String readString6 = parcel.readString();
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelFingerprintDetect(readStrongBinder5, readString6, readLong5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt7 = parcel.readInt();
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    java.lang.String readString7 = parcel.readString();
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthenticationFromService(readInt7, readStrongBinder6, readString7, readLong6);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt8 = parcel.readInt();
                    android.hardware.fingerprint.IFingerprintServiceReceiver asInterface5 = android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString8 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions = (android.hardware.fingerprint.FingerprintEnrollOptions) parcel.readTypedObject(android.hardware.fingerprint.FingerprintEnrollOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long enroll = enroll(readStrongBinder7, createByteArray, readInt8, asInterface5, readString8, readInt9, fingerprintEnrollOptions);
                    parcel2.writeNoException();
                    parcel2.writeLong(enroll);
                    return true;
                case 13:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    long readLong7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelEnrollment(readStrongBinder8, readLong7);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    android.hardware.fingerprint.IFingerprintServiceReceiver asInterface6 = android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    remove(readStrongBinder9, readInt10, readInt11, asInterface6, readString9);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    int readInt12 = parcel.readInt();
                    android.hardware.fingerprint.IFingerprintServiceReceiver asInterface7 = android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeAll(readStrongBinder10, readInt12, asInterface7, readString10);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rename(readInt13, readInt14, readString11);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt15 = parcel.readInt();
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.hardware.fingerprint.Fingerprint> enrolledFingerprints = getEnrolledFingerprints(readInt15, readString12, readString13);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enrolledFingerprints, 1);
                    return true;
                case 18:
                    java.lang.String readString14 = parcel.readString();
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isHardwareDetectedDeprecated = isHardwareDetectedDeprecated(readString14, readString15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHardwareDetectedDeprecated);
                    return true;
                case 19:
                    int readInt16 = parcel.readInt();
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isHardwareDetected = isHardwareDetected(readInt16, readString16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHardwareDetected);
                    return true;
                case 20:
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    android.hardware.fingerprint.IFingerprintServiceReceiver asInterface8 = android.hardware.fingerprint.IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    generateChallenge(readStrongBinder11, readInt17, readInt18, asInterface8, readString17);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    java.lang.String readString18 = parcel.readString();
                    long readLong8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    revokeChallenge(readStrongBinder12, readInt19, readInt20, readString18, readLong8);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt21 = parcel.readInt();
                    java.lang.String readString19 = parcel.readString();
                    java.lang.String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasEnrolledFingerprintsDeprecated = hasEnrolledFingerprintsDeprecated(readInt21, readString19, readString20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnrolledFingerprintsDeprecated);
                    return true;
                case 23:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    java.lang.String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasEnrolledFingerprints = hasEnrolledFingerprints(readInt22, readInt23, readString21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnrolledFingerprints);
                    return true;
                case 24:
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lockoutModeForUser = getLockoutModeForUser(readInt24, readInt25);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockoutModeForUser);
                    return true;
                case 25:
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    android.hardware.biometrics.IInvalidationCallback asInterface9 = android.hardware.biometrics.IInvalidationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    invalidateAuthenticatorId(readInt26, readInt27, asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long authenticatorId = getAuthenticatorId(readInt28, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticatorId);
                    return true;
                case 27:
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    java.lang.String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetLockout(readStrongBinder13, readInt30, readInt31, createByteArray2, readString22);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    android.hardware.biometrics.IBiometricServiceLockoutResetCallback asInterface10 = android.hardware.biometrics.IBiometricServiceLockoutResetCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addLockoutResetCallback(asInterface10, readString23);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    boolean isClientActive = isClientActive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isClientActive);
                    return true;
                case 30:
                    android.hardware.fingerprint.IFingerprintClientActiveCallback asInterface11 = android.hardware.fingerprint.IFingerprintClientActiveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addClientActiveCallback(asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    android.hardware.fingerprint.IFingerprintClientActiveCallback asInterface12 = android.hardware.fingerprint.IFingerprintClientActiveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeClientActiveCallback(asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    android.hardware.fingerprint.FingerprintSensorConfigurations fingerprintSensorConfigurations = (android.hardware.fingerprint.FingerprintSensorConfigurations) parcel.readTypedObject(android.hardware.fingerprint.FingerprintSensorConfigurations.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerAuthenticatorsLegacy(fingerprintSensorConfigurations);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.hardware.fingerprint.FingerprintSensorPropertiesInternal.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerAuthenticators(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback asInterface13 = android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addAuthenticatorsRegisteredCallback(asInterface13);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    long readLong9 = parcel.readLong();
                    int readInt32 = parcel.readInt();
                    android.hardware.biometrics.fingerprint.PointerContext pointerContext = (android.hardware.biometrics.fingerprint.PointerContext) parcel.readTypedObject(android.hardware.biometrics.fingerprint.PointerContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPointerDown(readLong9, readInt32, pointerContext);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    long readLong10 = parcel.readLong();
                    int readInt33 = parcel.readInt();
                    android.hardware.biometrics.fingerprint.PointerContext pointerContext2 = (android.hardware.biometrics.fingerprint.PointerContext) parcel.readTypedObject(android.hardware.biometrics.fingerprint.PointerContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPointerUp(readLong10, readInt33, pointerContext2);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt34 = parcel.readInt();
                    long readLong11 = parcel.readLong();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUdfpsUiEvent(readInt34, readLong11, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    android.hardware.fingerprint.IUdfpsOverlayController asInterface14 = android.hardware.fingerprint.IUdfpsOverlayController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setUdfpsOverlayController(asInterface14);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    android.hardware.fingerprint.ISidefpsController asInterface15 = android.hardware.fingerprint.ISidefpsController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSidefpsController(asInterface15);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    android.hardware.biometrics.AuthenticationStateListener asInterface16 = android.hardware.biometrics.AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAuthenticationStateListener(asInterface16);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    android.hardware.biometrics.AuthenticationStateListener asInterface17 = android.hardware.biometrics.AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAuthenticationStateListener(asInterface17);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    android.hardware.biometrics.IBiometricStateListener asInterface18 = android.hardware.biometrics.IBiometricStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerBiometricStateListener(asInterface18);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    onPowerPressed();
                    return true;
                case 44:
                    scheduleWatchdog();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.fingerprint.IFingerprintService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public android.hardware.biometrics.ITestSession createTestSession(int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
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

            @Override // android.hardware.fingerprint.IFingerprintService
            public byte[] dumpSensorServiceStateProto(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> getSensorPropertiesInternal(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.fingerprint.FingerprintSensorPropertiesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public android.hardware.fingerprint.FingerprintSensorPropertiesInternal getSensorProperties(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.fingerprint.FingerprintSensorPropertiesInternal) obtain2.readTypedObject(android.hardware.fingerprint.FingerprintSensorPropertiesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long authenticate(android.os.IBinder iBinder, long j, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iFingerprintServiceReceiver);
                    obtain.writeTypedObject(fingerprintAuthenticateOptions, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long detectFingerprint(android.os.IBinder iBinder, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iFingerprintServiceReceiver);
                    obtain.writeTypedObject(fingerprintAuthenticateOptions, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void prepareForAuthentication(android.os.IBinder iBinder, long j, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, long j2, int i, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iBiometricSensorReceiver);
                    obtain.writeTypedObject(fingerprintAuthenticateOptions, 0);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void startPreparedClient(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelFingerprintDetect(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelAuthenticationFromService(int i, android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long enroll(android.os.IBinder iBinder, byte[] bArr, int i, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str, int i2, android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iFingerprintServiceReceiver);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(fingerprintEnrollOptions, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelEnrollment(android.os.IBinder iBinder, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void remove(android.os.IBinder iBinder, int i, int i2, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iFingerprintServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void removeAll(android.os.IBinder iBinder, int i, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iFingerprintServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void rename(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public java.util.List<android.hardware.fingerprint.Fingerprint> getEnrolledFingerprints(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.fingerprint.Fingerprint.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean isHardwareDetectedDeprecated(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean isHardwareDetected(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void generateChallenge(android.os.IBinder iBinder, int i, int i2, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iFingerprintServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void revokeChallenge(android.os.IBinder iBinder, int i, int i2, java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean hasEnrolledFingerprintsDeprecated(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean hasEnrolledFingerprints(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int getLockoutModeForUser(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void invalidateAuthenticatorId(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long getAuthenticatorId(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void resetLockout(android.os.IBinder iBinder, int i, int i2, byte[] bArr, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void addLockoutResetCallback(android.hardware.biometrics.IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricServiceLockoutResetCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean isClientActive() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void addClientActiveCallback(android.hardware.fingerprint.IFingerprintClientActiveCallback iFingerprintClientActiveCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iFingerprintClientActiveCallback);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void removeClientActiveCallback(android.hardware.fingerprint.IFingerprintClientActiveCallback iFingerprintClientActiveCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iFingerprintClientActiveCallback);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void registerAuthenticatorsLegacy(android.hardware.fingerprint.FingerprintSensorConfigurations fingerprintSensorConfigurations) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fingerprintSensorConfigurations, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void registerAuthenticators(java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void addAuthenticatorsRegisteredCallback(android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback iFingerprintAuthenticatorsRegisteredCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iFingerprintAuthenticatorsRegisteredCallback);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onPointerDown(long j, int i, android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pointerContext, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onPointerUp(long j, int i, android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pointerContext, 0);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onUdfpsUiEvent(int i, long j, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void setUdfpsOverlayController(android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUdfpsOverlayController);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void setSidefpsController(android.hardware.fingerprint.ISidefpsController iSidefpsController) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSidefpsController);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void registerAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void unregisterAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void registerBiometricStateListener(android.hardware.biometrics.IBiometricStateListener iBiometricStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricStateListener);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onPowerPressed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void scheduleWatchdog() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IFingerprintService.Stub.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        protected void createTestSession_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void dumpSensorServiceStateProto_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getSensorProperties_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void detectFingerprint_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void prepareForAuthentication_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void startPreparedClient_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void cancelFingerprintDetect_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void cancelAuthenticationFromService_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void enroll_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void cancelEnrollment_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void remove_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void removeAll_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void rename_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void isHardwareDetected_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void generateChallenge_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void revokeChallenge_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void hasEnrolledFingerprints_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getLockoutModeForUser_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void invalidateAuthenticatorId_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getAuthenticatorId_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void resetLockout_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.RESET_FINGERPRINT_LOCKOUT, getCallingPid(), getCallingUid());
        }

        protected void addLockoutResetCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void isClientActive_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void addClientActiveCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void removeClientActiveCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void registerAuthenticatorsLegacy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void registerAuthenticators_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void addAuthenticatorsRegisteredCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void onPointerDown_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void onPointerUp_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void onUdfpsUiEvent_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void setUdfpsOverlayController_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void setSidefpsController_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void registerAuthenticationStateListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void unregisterAuthenticationStateListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void registerBiometricStateListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void onPowerPressed_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void scheduleWatchdog_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 43;
        }
    }
}
