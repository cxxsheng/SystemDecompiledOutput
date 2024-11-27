package android.hardware.face;

/* loaded from: classes2.dex */
public interface IFaceService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.face.IFaceService";

    void addAuthenticatorsRegisteredCallback(android.hardware.face.IFaceAuthenticatorsRegisteredCallback iFaceAuthenticatorsRegisteredCallback) throws android.os.RemoteException;

    void addLockoutResetCallback(android.hardware.biometrics.IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, java.lang.String str) throws android.os.RemoteException;

    long authenticate(android.os.IBinder iBinder, long j, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions) throws android.os.RemoteException;

    void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException;

    void cancelAuthenticationFromService(int i, android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException;

    void cancelEnrollment(android.os.IBinder iBinder, long j) throws android.os.RemoteException;

    void cancelFaceDetect(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException;

    android.hardware.biometrics.ITestSession createTestSession(int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException;

    long detectFace(android.os.IBinder iBinder, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions) throws android.os.RemoteException;

    byte[] dumpSensorServiceStateProto(int i, boolean z) throws android.os.RemoteException;

    long enroll(int i, android.os.IBinder iBinder, byte[] bArr, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str, int[] iArr, android.view.Surface surface, boolean z, android.hardware.face.FaceEnrollOptions faceEnrollOptions) throws android.os.RemoteException;

    long enrollRemotely(int i, android.os.IBinder iBinder, byte[] bArr, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str, int[] iArr) throws android.os.RemoteException;

    void generateChallenge(android.os.IBinder iBinder, int i, int i2, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException;

    long getAuthenticatorId(int i, int i2) throws android.os.RemoteException;

    java.util.List<android.hardware.face.Face> getEnrolledFaces(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    void getFeature(android.os.IBinder iBinder, int i, int i2, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException;

    int getLockoutModeForUser(int i, int i2) throws android.os.RemoteException;

    android.hardware.face.FaceSensorPropertiesInternal getSensorProperties(int i, java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.hardware.face.FaceSensorPropertiesInternal> getSensorPropertiesInternal(java.lang.String str) throws android.os.RemoteException;

    boolean hasEnrolledFaces(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    void invalidateAuthenticatorId(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException;

    boolean isHardwareDetected(int i, java.lang.String str) throws android.os.RemoteException;

    void prepareForAuthentication(boolean z, android.os.IBinder iBinder, long j, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, long j2, int i, boolean z2) throws android.os.RemoteException;

    void registerAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException;

    void registerAuthenticators(java.util.List<android.hardware.face.FaceSensorPropertiesInternal> list) throws android.os.RemoteException;

    void registerAuthenticatorsLegacy(android.hardware.face.FaceSensorConfigurations faceSensorConfigurations) throws android.os.RemoteException;

    void registerBiometricStateListener(android.hardware.biometrics.IBiometricStateListener iBiometricStateListener) throws android.os.RemoteException;

    void remove(android.os.IBinder iBinder, int i, int i2, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException;

    void removeAll(android.os.IBinder iBinder, int i, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException;

    void resetLockout(android.os.IBinder iBinder, int i, int i2, byte[] bArr, java.lang.String str) throws android.os.RemoteException;

    void revokeChallenge(android.os.IBinder iBinder, int i, int i2, java.lang.String str, long j) throws android.os.RemoteException;

    void scheduleWatchdog() throws android.os.RemoteException;

    void setFeature(android.os.IBinder iBinder, int i, int i2, boolean z, byte[] bArr, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException;

    void startPreparedClient(int i, int i2) throws android.os.RemoteException;

    void unregisterAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException;

    public static class Default implements android.hardware.face.IFaceService {
        @Override // android.hardware.face.IFaceService
        public android.hardware.biometrics.ITestSession createTestSession(int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.face.IFaceService
        public byte[] dumpSensorServiceStateProto(int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.face.IFaceService
        public java.util.List<android.hardware.face.FaceSensorPropertiesInternal> getSensorPropertiesInternal(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.face.IFaceService
        public android.hardware.face.FaceSensorPropertiesInternal getSensorProperties(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.face.IFaceService
        public long authenticate(android.os.IBinder iBinder, long j, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.face.IFaceService
        public long detectFace(android.os.IBinder iBinder, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.face.IFaceService
        public void prepareForAuthentication(boolean z, android.os.IBinder iBinder, long j, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, long j2, int i, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void startPreparedClient(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void cancelFaceDetect(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void cancelAuthenticationFromService(int i, android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public long enroll(int i, android.os.IBinder iBinder, byte[] bArr, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str, int[] iArr, android.view.Surface surface, boolean z, android.hardware.face.FaceEnrollOptions faceEnrollOptions) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.face.IFaceService
        public long enrollRemotely(int i, android.os.IBinder iBinder, byte[] bArr, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str, int[] iArr) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.face.IFaceService
        public void cancelEnrollment(android.os.IBinder iBinder, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void remove(android.os.IBinder iBinder, int i, int i2, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void removeAll(android.os.IBinder iBinder, int i, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public java.util.List<android.hardware.face.Face> getEnrolledFaces(int i, int i2, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.face.IFaceService
        public boolean isHardwareDetected(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.face.IFaceService
        public void generateChallenge(android.os.IBinder iBinder, int i, int i2, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void revokeChallenge(android.os.IBinder iBinder, int i, int i2, java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public boolean hasEnrolledFaces(int i, int i2, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.face.IFaceService
        public int getLockoutModeForUser(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.face.IFaceService
        public void invalidateAuthenticatorId(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public long getAuthenticatorId(int i, int i2) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.face.IFaceService
        public void resetLockout(android.os.IBinder iBinder, int i, int i2, byte[] bArr, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void addLockoutResetCallback(android.hardware.biometrics.IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void setFeature(android.os.IBinder iBinder, int i, int i2, boolean z, byte[] bArr, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void getFeature(android.os.IBinder iBinder, int i, int i2, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void registerAuthenticators(java.util.List<android.hardware.face.FaceSensorPropertiesInternal> list) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void registerAuthenticatorsLegacy(android.hardware.face.FaceSensorConfigurations faceSensorConfigurations) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void addAuthenticatorsRegisteredCallback(android.hardware.face.IFaceAuthenticatorsRegisteredCallback iFaceAuthenticatorsRegisteredCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void registerAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void unregisterAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void registerBiometricStateListener(android.hardware.biometrics.IBiometricStateListener iBiometricStateListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.face.IFaceService
        public void scheduleWatchdog() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.face.IFaceService {
        static final int TRANSACTION_addAuthenticatorsRegisteredCallback = 31;
        static final int TRANSACTION_addLockoutResetCallback = 26;
        static final int TRANSACTION_authenticate = 5;
        static final int TRANSACTION_cancelAuthentication = 9;
        static final int TRANSACTION_cancelAuthenticationFromService = 11;
        static final int TRANSACTION_cancelEnrollment = 14;
        static final int TRANSACTION_cancelFaceDetect = 10;
        static final int TRANSACTION_createTestSession = 1;
        static final int TRANSACTION_detectFace = 6;
        static final int TRANSACTION_dumpSensorServiceStateProto = 2;
        static final int TRANSACTION_enroll = 12;
        static final int TRANSACTION_enrollRemotely = 13;
        static final int TRANSACTION_generateChallenge = 19;
        static final int TRANSACTION_getAuthenticatorId = 24;
        static final int TRANSACTION_getEnrolledFaces = 17;
        static final int TRANSACTION_getFeature = 28;
        static final int TRANSACTION_getLockoutModeForUser = 22;
        static final int TRANSACTION_getSensorProperties = 4;
        static final int TRANSACTION_getSensorPropertiesInternal = 3;
        static final int TRANSACTION_hasEnrolledFaces = 21;
        static final int TRANSACTION_invalidateAuthenticatorId = 23;
        static final int TRANSACTION_isHardwareDetected = 18;
        static final int TRANSACTION_prepareForAuthentication = 7;
        static final int TRANSACTION_registerAuthenticationStateListener = 32;
        static final int TRANSACTION_registerAuthenticators = 29;
        static final int TRANSACTION_registerAuthenticatorsLegacy = 30;
        static final int TRANSACTION_registerBiometricStateListener = 34;
        static final int TRANSACTION_remove = 15;
        static final int TRANSACTION_removeAll = 16;
        static final int TRANSACTION_resetLockout = 25;
        static final int TRANSACTION_revokeChallenge = 20;
        static final int TRANSACTION_scheduleWatchdog = 35;
        static final int TRANSACTION_setFeature = 27;
        static final int TRANSACTION_startPreparedClient = 8;
        static final int TRANSACTION_unregisterAuthenticationStateListener = 33;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, android.hardware.face.IFaceService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.hardware.face.IFaceService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.face.IFaceService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.face.IFaceService)) {
                return (android.hardware.face.IFaceService) queryLocalInterface;
            }
            return new android.hardware.face.IFaceService.Stub.Proxy(iBinder);
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
                    return "detectFace";
                case 7:
                    return "prepareForAuthentication";
                case 8:
                    return "startPreparedClient";
                case 9:
                    return "cancelAuthentication";
                case 10:
                    return "cancelFaceDetect";
                case 11:
                    return "cancelAuthenticationFromService";
                case 12:
                    return "enroll";
                case 13:
                    return "enrollRemotely";
                case 14:
                    return "cancelEnrollment";
                case 15:
                    return "remove";
                case 16:
                    return "removeAll";
                case 17:
                    return "getEnrolledFaces";
                case 18:
                    return "isHardwareDetected";
                case 19:
                    return "generateChallenge";
                case 20:
                    return "revokeChallenge";
                case 21:
                    return "hasEnrolledFaces";
                case 22:
                    return "getLockoutModeForUser";
                case 23:
                    return "invalidateAuthenticatorId";
                case 24:
                    return "getAuthenticatorId";
                case 25:
                    return "resetLockout";
                case 26:
                    return "addLockoutResetCallback";
                case 27:
                    return "setFeature";
                case 28:
                    return "getFeature";
                case 29:
                    return "registerAuthenticators";
                case 30:
                    return "registerAuthenticatorsLegacy";
                case 31:
                    return "addAuthenticatorsRegisteredCallback";
                case 32:
                    return "registerAuthenticationStateListener";
                case 33:
                    return "unregisterAuthenticationStateListener";
                case 34:
                    return "registerBiometricStateListener";
                case 35:
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
                parcel.enforceInterface(android.hardware.face.IFaceService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.face.IFaceService.DESCRIPTOR);
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
                    java.util.List<android.hardware.face.FaceSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal(readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(sensorPropertiesInternal, 1);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.hardware.face.FaceSensorPropertiesInternal sensorProperties = getSensorProperties(readInt3, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sensorProperties, 1);
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    long readLong = parcel.readLong();
                    android.hardware.face.IFaceServiceReceiver asInterface2 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions = (android.hardware.face.FaceAuthenticateOptions) parcel.readTypedObject(android.hardware.face.FaceAuthenticateOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long authenticate = authenticate(readStrongBinder, readLong, asInterface2, faceAuthenticateOptions);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticate);
                    return true;
                case 6:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.hardware.face.IFaceServiceReceiver asInterface3 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions2 = (android.hardware.face.FaceAuthenticateOptions) parcel.readTypedObject(android.hardware.face.FaceAuthenticateOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long detectFace = detectFace(readStrongBinder2, asInterface3, faceAuthenticateOptions2);
                    parcel2.writeNoException();
                    parcel2.writeLong(detectFace);
                    return true;
                case 7:
                    boolean readBoolean2 = parcel.readBoolean();
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    long readLong2 = parcel.readLong();
                    android.hardware.biometrics.IBiometricSensorReceiver asInterface4 = android.hardware.biometrics.IBiometricSensorReceiver.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions3 = (android.hardware.face.FaceAuthenticateOptions) parcel.readTypedObject(android.hardware.face.FaceAuthenticateOptions.CREATOR);
                    long readLong3 = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    prepareForAuthentication(readBoolean2, readStrongBinder3, readLong2, asInterface4, faceAuthenticateOptions3, readLong3, readInt4, readBoolean3);
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
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthentication(readStrongBinder4, readString4, readLong4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    java.lang.String readString5 = parcel.readString();
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelFaceDetect(readStrongBinder5, readString5, readLong5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt7 = parcel.readInt();
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    java.lang.String readString6 = parcel.readString();
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthenticationFromService(readInt7, readStrongBinder6, readString6, readLong6);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt8 = parcel.readInt();
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    byte[] createByteArray = parcel.createByteArray();
                    android.hardware.face.IFaceServiceReceiver asInterface5 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString7 = parcel.readString();
                    int[] createIntArray = parcel.createIntArray();
                    android.view.Surface surface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    android.hardware.face.FaceEnrollOptions faceEnrollOptions = (android.hardware.face.FaceEnrollOptions) parcel.readTypedObject(android.hardware.face.FaceEnrollOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long enroll = enroll(readInt8, readStrongBinder7, createByteArray, asInterface5, readString7, createIntArray, surface, readBoolean4, faceEnrollOptions);
                    parcel2.writeNoException();
                    parcel2.writeLong(enroll);
                    return true;
                case 13:
                    int readInt9 = parcel.readInt();
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    byte[] createByteArray2 = parcel.createByteArray();
                    android.hardware.face.IFaceServiceReceiver asInterface6 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString8 = parcel.readString();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long enrollRemotely = enrollRemotely(readInt9, readStrongBinder8, createByteArray2, asInterface6, readString8, createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeLong(enrollRemotely);
                    return true;
                case 14:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    long readLong7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelEnrollment(readStrongBinder9, readLong7);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    android.hardware.face.IFaceServiceReceiver asInterface7 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    remove(readStrongBinder10, readInt10, readInt11, asInterface7, readString9);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    int readInt12 = parcel.readInt();
                    android.hardware.face.IFaceServiceReceiver asInterface8 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeAll(readStrongBinder11, readInt12, asInterface8, readString10);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.hardware.face.Face> enrolledFaces = getEnrolledFaces(readInt13, readInt14, readString11);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enrolledFaces, 1);
                    return true;
                case 18:
                    int readInt15 = parcel.readInt();
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isHardwareDetected = isHardwareDetected(readInt15, readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHardwareDetected);
                    return true;
                case 19:
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    android.hardware.face.IFaceServiceReceiver asInterface9 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    generateChallenge(readStrongBinder12, readInt16, readInt17, asInterface9, readString13);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    java.lang.String readString14 = parcel.readString();
                    long readLong8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    revokeChallenge(readStrongBinder13, readInt18, readInt19, readString14, readLong8);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasEnrolledFaces = hasEnrolledFaces(readInt20, readInt21, readString15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnrolledFaces);
                    return true;
                case 22:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lockoutModeForUser = getLockoutModeForUser(readInt22, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockoutModeForUser);
                    return true;
                case 23:
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    android.hardware.biometrics.IInvalidationCallback asInterface10 = android.hardware.biometrics.IInvalidationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    invalidateAuthenticatorId(readInt24, readInt25, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long authenticatorId = getAuthenticatorId(readInt26, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticatorId);
                    return true;
                case 25:
                    android.os.IBinder readStrongBinder14 = parcel.readStrongBinder();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetLockout(readStrongBinder14, readInt28, readInt29, createByteArray3, readString16);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    android.hardware.biometrics.IBiometricServiceLockoutResetCallback asInterface11 = android.hardware.biometrics.IBiometricServiceLockoutResetCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addLockoutResetCallback(asInterface11, readString17);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    android.os.IBinder readStrongBinder15 = parcel.readStrongBinder();
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    byte[] createByteArray4 = parcel.createByteArray();
                    android.hardware.face.IFaceServiceReceiver asInterface12 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setFeature(readStrongBinder15, readInt30, readInt31, readBoolean5, createByteArray4, asInterface12, readString18);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    android.os.IBinder readStrongBinder16 = parcel.readStrongBinder();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    android.hardware.face.IFaceServiceReceiver asInterface13 = android.hardware.face.IFaceServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getFeature(readStrongBinder16, readInt32, readInt33, asInterface13, readString19);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.hardware.face.FaceSensorPropertiesInternal.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerAuthenticators(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    android.hardware.face.FaceSensorConfigurations faceSensorConfigurations = (android.hardware.face.FaceSensorConfigurations) parcel.readTypedObject(android.hardware.face.FaceSensorConfigurations.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerAuthenticatorsLegacy(faceSensorConfigurations);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    android.hardware.face.IFaceAuthenticatorsRegisteredCallback asInterface14 = android.hardware.face.IFaceAuthenticatorsRegisteredCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addAuthenticatorsRegisteredCallback(asInterface14);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    android.hardware.biometrics.AuthenticationStateListener asInterface15 = android.hardware.biometrics.AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAuthenticationStateListener(asInterface15);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    android.hardware.biometrics.AuthenticationStateListener asInterface16 = android.hardware.biometrics.AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAuthenticationStateListener(asInterface16);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    android.hardware.biometrics.IBiometricStateListener asInterface17 = android.hardware.biometrics.IBiometricStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerBiometricStateListener(asInterface17);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    scheduleWatchdog();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.face.IFaceService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.face.IFaceService.DESCRIPTOR;
            }

            @Override // android.hardware.face.IFaceService
            public android.hardware.biometrics.ITestSession createTestSession(int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
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

            @Override // android.hardware.face.IFaceService
            public byte[] dumpSensorServiceStateProto(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
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

            @Override // android.hardware.face.IFaceService
            public java.util.List<android.hardware.face.FaceSensorPropertiesInternal> getSensorPropertiesInternal(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.face.FaceSensorPropertiesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public android.hardware.face.FaceSensorPropertiesInternal getSensorProperties(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.face.FaceSensorPropertiesInternal) obtain2.readTypedObject(android.hardware.face.FaceSensorPropertiesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long authenticate(android.os.IBinder iBinder, long j, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeTypedObject(faceAuthenticateOptions, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long detectFace(android.os.IBinder iBinder, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeTypedObject(faceAuthenticateOptions, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void prepareForAuthentication(boolean z, android.os.IBinder iBinder, long j, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, long j2, int i, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iBiometricSensorReceiver);
                    obtain.writeTypedObject(faceAuthenticateOptions, 0);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void startPreparedClient(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void cancelFaceDetect(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
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

            @Override // android.hardware.face.IFaceService
            public void cancelAuthenticationFromService(int i, android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
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

            @Override // android.hardware.face.IFaceService
            public long enroll(int i, android.os.IBinder iBinder, byte[] bArr, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str, int[] iArr, android.view.Surface surface, boolean z, android.hardware.face.FaceEnrollOptions faceEnrollOptions) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(faceEnrollOptions, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long enrollRemotely(int i, android.os.IBinder iBinder, byte[] bArr, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void cancelEnrollment(android.os.IBinder iBinder, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void remove(android.os.IBinder iBinder, int i, int i2, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void removeAll(android.os.IBinder iBinder, int i, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public java.util.List<android.hardware.face.Face> getEnrolledFaces(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.face.Face.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean isHardwareDetected(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void generateChallenge(android.os.IBinder iBinder, int i, int i2, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void revokeChallenge(android.os.IBinder iBinder, int i, int i2, java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public boolean hasEnrolledFaces(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public int getLockoutModeForUser(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void invalidateAuthenticatorId(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public long getAuthenticatorId(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void resetLockout(android.os.IBinder iBinder, int i, int i2, byte[] bArr, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void addLockoutResetCallback(android.hardware.biometrics.IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricServiceLockoutResetCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void setFeature(android.os.IBinder iBinder, int i, int i2, boolean z, byte[] bArr, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void getFeature(android.os.IBinder iBinder, int i, int i2, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iFaceServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void registerAuthenticators(java.util.List<android.hardware.face.FaceSensorPropertiesInternal> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void registerAuthenticatorsLegacy(android.hardware.face.FaceSensorConfigurations faceSensorConfigurations) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeTypedObject(faceSensorConfigurations, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void addAuthenticatorsRegisteredCallback(android.hardware.face.IFaceAuthenticatorsRegisteredCallback iFaceAuthenticatorsRegisteredCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongInterface(iFaceAuthenticatorsRegisteredCallback);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void registerAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void unregisterAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void registerBiometricStateListener(android.hardware.biometrics.IBiometricStateListener iBiometricStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricStateListener);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceService
            public void scheduleWatchdog() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.face.IFaceService.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        protected void createTestSession_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void dumpSensorServiceStateProto_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getSensorPropertiesInternal_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getSensorProperties_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void authenticate_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void detectFace_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void prepareForAuthentication_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void startPreparedClient_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void cancelAuthentication_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void cancelFaceDetect_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void cancelAuthenticationFromService_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void enroll_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void enrollRemotely_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void cancelEnrollment_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void remove_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void removeAll_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getEnrolledFaces_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void isHardwareDetected_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void generateChallenge_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void revokeChallenge_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void hasEnrolledFaces_enforcePermission() throws java.lang.SecurityException {
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
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void addLockoutResetCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void setFeature_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getFeature_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void registerAuthenticators_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void registerAuthenticatorsLegacy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void registerAuthenticationStateListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void unregisterAuthenticationStateListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void scheduleWatchdog_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 34;
        }
    }
}
