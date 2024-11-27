package android.hardware.fingerprint;

@java.lang.Deprecated
/* loaded from: classes2.dex */
public class FingerprintManager implements android.hardware.biometrics.BiometricAuthenticator, android.hardware.biometrics.BiometricFingerprintConstants {
    private static final boolean DEBUG = true;
    public static final int ENROLL_ENROLL = 2;
    public static final int ENROLL_FIND_SENSOR = 1;
    private static final int MSG_ACQUIRED = 101;
    private static final int MSG_AUTHENTICATION_FAILED = 103;
    private static final int MSG_AUTHENTICATION_SUCCEEDED = 102;
    private static final int MSG_CHALLENGE_GENERATED = 106;
    private static final int MSG_ENROLL_RESULT = 100;
    private static final int MSG_ERROR = 104;
    private static final int MSG_FINGERPRINT_DETECTED = 107;
    private static final int MSG_POWER_BUTTON_PRESSED = 110;
    private static final int MSG_REMOVED = 105;
    private static final int MSG_UDFPS_OVERLAY_SHOWN = 111;
    private static final int MSG_UDFPS_POINTER_DOWN = 108;
    private static final int MSG_UDFPS_POINTER_UP = 109;
    public static final int SENSOR_ID_ANY = -1;
    private static final java.lang.String TAG = "FingerprintManager";
    public static final int UDFPS_UI_OVERLAY_SHOWN = 1;
    public static final int UDFPS_UI_READY = 2;
    private android.hardware.fingerprint.FingerprintManager.AuthenticationCallback mAuthenticationCallback;
    private android.content.Context mContext;
    private android.hardware.fingerprint.FingerprintManager.CryptoObject mCryptoObject;
    private float[] mEnrollStageThresholds;
    private android.hardware.fingerprint.FingerprintManager.EnrollmentCallback mEnrollmentCallback;
    private android.hardware.fingerprint.FingerprintManager.FingerprintDetectionCallback mFingerprintDetectionCallback;
    private android.hardware.fingerprint.FingerprintManager.GenerateChallengeCallback mGenerateChallengeCallback;
    private android.os.Handler mHandler;
    private android.hardware.fingerprint.FingerprintManager.RemovalCallback mRemovalCallback;
    private android.hardware.fingerprint.FingerprintManager.RemoveTracker mRemoveTracker;
    private android.hardware.fingerprint.IFingerprintService mService;
    private android.os.IBinder mToken = new android.os.Binder();
    private java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> mProps = new java.util.ArrayList();
    private android.hardware.fingerprint.IFingerprintServiceReceiver mServiceReceiver = new android.hardware.fingerprint.IFingerprintServiceReceiver.Stub() { // from class: android.hardware.fingerprint.FingerprintManager.3
        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onEnrollResult(android.hardware.fingerprint.Fingerprint fingerprint, int i) {
            android.hardware.fingerprint.FingerprintManager.this.mHandler.obtainMessage(100, i, 0, fingerprint).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAcquired(int i, int i2) {
            android.hardware.fingerprint.FingerprintManager.this.mHandler.obtainMessage(101, i, i2).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationSucceeded(android.hardware.fingerprint.Fingerprint fingerprint, int i, boolean z) {
            android.hardware.fingerprint.FingerprintManager.this.mHandler.obtainMessage(102, i, z ? 1 : 0, fingerprint).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onFingerprintDetected(int i, int i2, boolean z) {
            android.hardware.fingerprint.FingerprintManager.this.mHandler.obtainMessage(107, i, i2, java.lang.Boolean.valueOf(z)).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationFailed() {
            android.hardware.fingerprint.FingerprintManager.this.mHandler.obtainMessage(103).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onError(int i, int i2) {
            android.hardware.fingerprint.FingerprintManager.this.mHandler.obtainMessage(104, i, i2).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onRemoved(android.hardware.fingerprint.Fingerprint fingerprint, int i) {
            android.hardware.fingerprint.FingerprintManager.this.mHandler.obtainMessage(105, i, 0, fingerprint).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onChallengeGenerated(int i, int i2, long j) {
            android.hardware.fingerprint.FingerprintManager.this.mHandler.obtainMessage(106, i, i2, java.lang.Long.valueOf(j)).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerDown(int i) {
            android.hardware.fingerprint.FingerprintManager.this.mHandler.obtainMessage(108, i, 0).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerUp(int i) {
            android.hardware.fingerprint.FingerprintManager.this.mHandler.obtainMessage(109, i, 0).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsOverlayShown() {
            android.hardware.fingerprint.FingerprintManager.this.mHandler.obtainMessage(111).sendToTarget();
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EnrollReason {
    }

    public interface GenerateChallengeCallback {
        void onChallengeGenerated(int i, int i2, long j);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UdfpsUiEvent {
    }

    private static class RemoveTracker {
        static final int REMOVE_ALL = 2;
        static final int REMOVE_SINGLE = 1;
        final int mRemoveRequest;
        final android.hardware.fingerprint.Fingerprint mSingleFingerprint;

        @interface RemoveRequest {
        }

        RemoveTracker(int i, android.hardware.fingerprint.Fingerprint fingerprint) {
            this.mRemoveRequest = i;
            this.mSingleFingerprint = fingerprint;
        }
    }

    public java.util.List<android.hardware.biometrics.SensorProperties> getSensorProperties() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> it = getSensorPropertiesInternal().iterator();
        while (it.hasNext()) {
            arrayList.add(android.hardware.fingerprint.FingerprintSensorProperties.from(it.next()));
        }
        return arrayList;
    }

    public android.hardware.biometrics.BiometricTestSession createTestSession(int i) {
        try {
            return new android.hardware.biometrics.BiometricTestSession(this.mContext, i, new android.hardware.biometrics.BiometricTestSession.TestSessionProvider() { // from class: android.hardware.fingerprint.FingerprintManager$$ExternalSyntheticLambda0
                @Override // android.hardware.biometrics.BiometricTestSession.TestSessionProvider
                public final android.hardware.biometrics.ITestSession createTestSession(android.content.Context context, int i2, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback) {
                    android.hardware.biometrics.ITestSession lambda$createTestSession$0;
                    lambda$createTestSession$0 = android.hardware.fingerprint.FingerprintManager.this.lambda$createTestSession$0(context, i2, iTestSessionCallback);
                    return lambda$createTestSession$0;
                }
            });
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.hardware.biometrics.ITestSession lambda$createTestSession$0(android.content.Context context, int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback) throws android.os.RemoteException {
        return this.mService.createTestSession(i, iTestSessionCallback, context.getOpPackageName());
    }

    private class OnEnrollCancelListener implements android.os.CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        private OnEnrollCancelListener(long j) {
            this.mAuthRequestId = j;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            android.util.Slog.d(android.hardware.fingerprint.FingerprintManager.TAG, "Cancel fingerprint enrollment requested for: " + this.mAuthRequestId);
            android.hardware.fingerprint.FingerprintManager.this.cancelEnrollment(this.mAuthRequestId);
        }
    }

    private class OnAuthenticationCancelListener implements android.os.CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnAuthenticationCancelListener(long j) {
            this.mAuthRequestId = j;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            android.util.Slog.d(android.hardware.fingerprint.FingerprintManager.TAG, "Cancel fingerprint authentication requested for: " + this.mAuthRequestId);
            android.hardware.fingerprint.FingerprintManager.this.cancelAuthentication(this.mAuthRequestId);
        }
    }

    private class OnFingerprintDetectionCancelListener implements android.os.CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnFingerprintDetectionCancelListener(long j) {
            this.mAuthRequestId = j;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            android.util.Slog.d(android.hardware.fingerprint.FingerprintManager.TAG, "Cancel fingerprint detect requested for: " + this.mAuthRequestId);
            android.hardware.fingerprint.FingerprintManager.this.cancelFingerprintDetect(this.mAuthRequestId);
        }
    }

    @java.lang.Deprecated
    public static final class CryptoObject extends android.hardware.biometrics.CryptoObject {
        public CryptoObject(java.security.Signature signature) {
            super(signature);
        }

        public CryptoObject(javax.crypto.Cipher cipher) {
            super(cipher);
        }

        public CryptoObject(javax.crypto.Mac mac) {
            super(mac);
        }

        @Override // android.hardware.biometrics.CryptoObject
        public java.security.Signature getSignature() {
            return super.getSignature();
        }

        @Override // android.hardware.biometrics.CryptoObject
        public javax.crypto.Cipher getCipher() {
            return super.getCipher();
        }

        @Override // android.hardware.biometrics.CryptoObject
        public javax.crypto.Mac getMac() {
            return super.getMac();
        }

        @Override // android.hardware.biometrics.CryptoObject
        @java.lang.Deprecated
        public android.security.identity.IdentityCredential getIdentityCredential() {
            return super.getIdentityCredential();
        }

        @Override // android.hardware.biometrics.CryptoObject
        public android.security.identity.PresentationSession getPresentationSession() {
            return super.getPresentationSession();
        }

        @Override // android.hardware.biometrics.CryptoObject
        public javax.crypto.KeyAgreement getKeyAgreement() {
            return super.getKeyAgreement();
        }
    }

    @java.lang.Deprecated
    public static class AuthenticationResult {
        private android.hardware.fingerprint.FingerprintManager.CryptoObject mCryptoObject;
        private android.hardware.fingerprint.Fingerprint mFingerprint;
        private boolean mIsStrongBiometric;
        private int mUserId;

        public AuthenticationResult(android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject, android.hardware.fingerprint.Fingerprint fingerprint, int i, boolean z) {
            this.mCryptoObject = cryptoObject;
            this.mFingerprint = fingerprint;
            this.mUserId = i;
            this.mIsStrongBiometric = z;
        }

        public android.hardware.fingerprint.FingerprintManager.CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        public android.hardware.fingerprint.Fingerprint getFingerprint() {
            return this.mFingerprint;
        }

        public int getUserId() {
            return this.mUserId;
        }

        public boolean isStrongBiometric() {
            return this.mIsStrongBiometric;
        }
    }

    @java.lang.Deprecated
    public static abstract class AuthenticationCallback extends android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback {
        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationError(int i, java.lang.CharSequence charSequence) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationHelp(int i, java.lang.CharSequence charSequence) {
        }

        public void onAuthenticationSucceeded(android.hardware.fingerprint.FingerprintManager.AuthenticationResult authenticationResult) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationFailed() {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationAcquired(int i) {
        }

        public void onUdfpsPointerDown(int i) {
        }

        public void onUdfpsPointerUp(int i) {
        }
    }

    public interface FingerprintDetectionCallback {
        void onFingerprintDetected(int i, int i2, boolean z);

        default void onDetectionError(int i) {
        }
    }

    public static abstract class EnrollmentCallback {
        public void onEnrollmentError(int i, java.lang.CharSequence charSequence) {
        }

        public void onEnrollmentHelp(int i, java.lang.CharSequence charSequence) {
        }

        public void onEnrollmentProgress(int i) {
        }

        public void onAcquired(boolean z) {
        }

        public void onUdfpsPointerDown(int i) {
        }

        public void onUdfpsPointerUp(int i) {
        }

        public void onUdfpsOverlayShown() {
        }
    }

    public static abstract class RemovalCallback {
        public void onRemovalError(android.hardware.fingerprint.Fingerprint fingerprint, int i, java.lang.CharSequence charSequence) {
        }

        public void onRemovalSucceeded(android.hardware.fingerprint.Fingerprint fingerprint, int i) {
        }
    }

    public static abstract class LockoutResetCallback {
        public void onLockoutReset(int i) {
        }
    }

    private void useHandler(android.os.Handler handler) {
        if (handler != null) {
            this.mHandler = new android.hardware.fingerprint.FingerprintManager.MyHandler(handler.getLooper());
        } else if (this.mHandler.getLooper() != this.mContext.getMainLooper()) {
            this.mHandler = new android.hardware.fingerprint.FingerprintManager.MyHandler(this.mContext.getMainLooper());
        }
    }

    @java.lang.Deprecated
    public void authenticate(android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject, android.os.CancellationSignal cancellationSignal, int i, android.hardware.fingerprint.FingerprintManager.AuthenticationCallback authenticationCallback, android.os.Handler handler) {
        authenticate(cryptoObject, cancellationSignal, authenticationCallback, handler, -1, this.mContext.getUserId(), i);
    }

    @java.lang.Deprecated
    public void authenticate(android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject, android.os.CancellationSignal cancellationSignal, android.hardware.fingerprint.FingerprintManager.AuthenticationCallback authenticationCallback, android.os.Handler handler, int i) {
        authenticate(cryptoObject, cancellationSignal, authenticationCallback, handler, -1, i, 0);
    }

    @java.lang.Deprecated
    public void authenticate(android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject, android.os.CancellationSignal cancellationSignal, android.hardware.fingerprint.FingerprintManager.AuthenticationCallback authenticationCallback, android.os.Handler handler, int i, int i2, int i3) {
        authenticate(cryptoObject, cancellationSignal, authenticationCallback, handler, new android.hardware.fingerprint.FingerprintAuthenticateOptions.Builder().setSensorId(i).setUserId(i2).setIgnoreEnrollmentState(i3 != 0).build());
    }

    public void authenticate(android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject, android.os.CancellationSignal cancellationSignal, android.hardware.fingerprint.FingerprintManager.AuthenticationCallback authenticationCallback, android.os.Handler handler, android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
        com.android.internal.util.FrameworkStatsLog.write(356, 1, this.mContext.getApplicationInfo().uid, this.mContext.getApplicationInfo().targetSdkVersion);
        if (authenticationCallback == null) {
            throw new java.lang.IllegalArgumentException("Must supply an authentication callback");
        }
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            android.util.Slog.w(TAG, "authentication already canceled");
            return;
        }
        fingerprintAuthenticateOptions.setOpPackageName(this.mContext.getOpPackageName());
        fingerprintAuthenticateOptions.setAttributionTag(this.mContext.getAttributionTag());
        if (this.mService != null) {
            try {
                useHandler(handler);
                this.mAuthenticationCallback = authenticationCallback;
                this.mCryptoObject = cryptoObject;
                long authenticate = this.mService.authenticate(this.mToken, cryptoObject != null ? cryptoObject.getOpId() : 0L, this.mServiceReceiver, fingerprintAuthenticateOptions);
                if (cancellationSignal != null) {
                    cancellationSignal.setOnCancelListener(new android.hardware.fingerprint.FingerprintManager.OnAuthenticationCancelListener(authenticate));
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Remote exception while authenticating: ", e);
                authenticationCallback.onAuthenticationError(1, getErrorString(this.mContext, 1, 0));
            }
        }
    }

    public void detectFingerprint(android.os.CancellationSignal cancellationSignal, android.hardware.fingerprint.FingerprintManager.FingerprintDetectionCallback fingerprintDetectionCallback, android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
        if (this.mService == null) {
            return;
        }
        if (cancellationSignal.isCanceled()) {
            android.util.Slog.w(TAG, "Detection already cancelled");
            return;
        }
        fingerprintAuthenticateOptions.setOpPackageName(this.mContext.getOpPackageName());
        fingerprintAuthenticateOptions.setAttributionTag(this.mContext.getAttributionTag());
        this.mFingerprintDetectionCallback = fingerprintDetectionCallback;
        try {
            cancellationSignal.setOnCancelListener(new android.hardware.fingerprint.FingerprintManager.OnFingerprintDetectionCancelListener(this.mService.detectFingerprint(this.mToken, this.mServiceReceiver, fingerprintAuthenticateOptions)));
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Remote exception when requesting finger detect", e);
        }
    }

    public void enroll(byte[] bArr, android.os.CancellationSignal cancellationSignal, int i, android.hardware.fingerprint.FingerprintManager.EnrollmentCallback enrollmentCallback, int i2, android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions) {
        int i3;
        if (i != -2) {
            i3 = i;
        } else {
            i3 = getCurrentUserId();
        }
        if (enrollmentCallback == null) {
            throw new java.lang.IllegalArgumentException("Must supply an enrollment callback");
        }
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            android.util.Slog.w(TAG, "enrollment already canceled");
            return;
        }
        if (bArr == null) {
            enrollmentCallback.onEnrollmentError(2, getErrorString(this.mContext, 2, 0));
            return;
        }
        if (this.mService != null) {
            try {
                this.mEnrollmentCallback = enrollmentCallback;
                long enroll = this.mService.enroll(this.mToken, bArr, i3, this.mServiceReceiver, this.mContext.getOpPackageName(), i2, fingerprintEnrollOptions);
                if (cancellationSignal != null) {
                    cancellationSignal.setOnCancelListener(new android.hardware.fingerprint.FingerprintManager.OnEnrollCancelListener(enroll));
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Remote exception in enroll: ", e);
                enrollmentCallback.onEnrollmentError(1, getErrorString(this.mContext, 1, 0));
            }
        }
    }

    public void generateChallenge(int i, int i2, android.hardware.fingerprint.FingerprintManager.GenerateChallengeCallback generateChallengeCallback) {
        if (this.mService != null) {
            try {
                this.mGenerateChallengeCallback = generateChallengeCallback;
                this.mService.generateChallenge(this.mToken, i, i2, this.mServiceReceiver, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void generateChallenge(int i, android.hardware.fingerprint.FingerprintManager.GenerateChallengeCallback generateChallengeCallback) {
        android.hardware.fingerprint.FingerprintSensorPropertiesInternal firstFingerprintSensor = getFirstFingerprintSensor();
        if (firstFingerprintSensor == null) {
            android.util.Slog.e(TAG, "No sensors");
        } else {
            generateChallenge(firstFingerprintSensor.sensorId, i, generateChallengeCallback);
        }
    }

    public void revokeChallenge(int i, long j) {
        if (this.mService != null) {
            try {
                android.hardware.fingerprint.FingerprintSensorPropertiesInternal firstFingerprintSensor = getFirstFingerprintSensor();
                if (firstFingerprintSensor == null) {
                    android.util.Slog.e(TAG, "No sensors");
                } else {
                    this.mService.revokeChallenge(this.mToken, firstFingerprintSensor.sensorId, i, this.mContext.getOpPackageName(), j);
                }
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void resetLockout(int i, int i2, byte[] bArr) {
        if (this.mService != null) {
            try {
                this.mService.resetLockout(this.mToken, i, i2, bArr, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void remove(android.hardware.fingerprint.Fingerprint fingerprint, int i, android.hardware.fingerprint.FingerprintManager.RemovalCallback removalCallback) {
        if (this.mService != null) {
            try {
                this.mRemovalCallback = removalCallback;
                this.mRemoveTracker = new android.hardware.fingerprint.FingerprintManager.RemoveTracker(1, fingerprint);
                this.mService.remove(this.mToken, fingerprint.getBiometricId(), i, this.mServiceReceiver, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeAll(int i, android.hardware.fingerprint.FingerprintManager.RemovalCallback removalCallback) {
        if (this.mService != null) {
            try {
                this.mRemovalCallback = removalCallback;
                this.mRemoveTracker = new android.hardware.fingerprint.FingerprintManager.RemoveTracker(2, null);
                this.mService.removeAll(this.mToken, i, this.mServiceReceiver, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void rename(int i, int i2, java.lang.String str) {
        if (this.mService != null) {
            try {
                this.mService.rename(i, i2, str);
                return;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.util.Slog.w(TAG, "rename(): Service not connected!");
    }

    public java.util.List<android.hardware.fingerprint.Fingerprint> getEnrolledFingerprints(int i) {
        if (this.mService != null) {
            try {
                return this.mService.getEnrolledFingerprints(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public java.util.List<android.hardware.fingerprint.Fingerprint> getEnrolledFingerprints() {
        return getEnrolledFingerprints(this.mContext.getUserId());
    }

    public boolean hasEnrolledTemplates() {
        return hasEnrolledFingerprints();
    }

    public boolean hasEnrolledTemplates(int i) {
        return hasEnrolledFingerprints(i);
    }

    public void setUdfpsOverlayController(android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController) {
        if (this.mService == null) {
            android.util.Slog.w(TAG, "setUdfpsOverlayController: no fingerprint service");
            return;
        }
        try {
            this.mService.setUdfpsOverlayController(iUdfpsOverlayController);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSidefpsController(android.hardware.fingerprint.ISidefpsController iSidefpsController) {
        if (this.mService == null) {
            android.util.Slog.w(TAG, "setSidefpsController: no fingerprint service");
            return;
        }
        try {
            this.mService.setSidefpsController(iSidefpsController);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerBiometricStateListener(android.hardware.biometrics.BiometricStateListener biometricStateListener) {
        try {
            this.mService.registerBiometricStateListener(biometricStateListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPointerDown(long j, int i, int i2, int i3, float f, float f2) {
        if (this.mService == null) {
            android.util.Slog.w(TAG, "onPointerDown: no fingerprint service");
            return;
        }
        android.hardware.biometrics.fingerprint.PointerContext pointerContext = new android.hardware.biometrics.fingerprint.PointerContext();
        pointerContext.x = i2;
        pointerContext.y = i3;
        pointerContext.minor = f;
        pointerContext.major = f2;
        try {
            this.mService.onPointerDown(j, i, pointerContext);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPointerUp(long j, int i) {
        if (this.mService == null) {
            android.util.Slog.w(TAG, "onPointerUp: no fingerprint service");
            return;
        }
        try {
            this.mService.onPointerUp(j, i, new android.hardware.biometrics.fingerprint.PointerContext());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPointerDown(long j, int i, int i2, float f, float f2, float f3, float f4, float f5, long j2, long j3, boolean z) {
        if (this.mService == null) {
            android.util.Slog.w(TAG, "onPointerDown: no fingerprint service");
            return;
        }
        android.hardware.biometrics.fingerprint.PointerContext pointerContext = new android.hardware.biometrics.fingerprint.PointerContext();
        pointerContext.pointerId = i2;
        pointerContext.x = f;
        pointerContext.y = f2;
        pointerContext.minor = f3;
        pointerContext.major = f4;
        pointerContext.orientation = f5;
        pointerContext.time = j2;
        pointerContext.gestureStart = j3;
        pointerContext.isAod = z;
        try {
            this.mService.onPointerDown(j, i, pointerContext);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPointerUp(long j, int i, int i2, float f, float f2, float f3, float f4, float f5, long j2, long j3, boolean z) {
        if (this.mService == null) {
            android.util.Slog.w(TAG, "onPointerUp: no fingerprint service");
            return;
        }
        android.hardware.biometrics.fingerprint.PointerContext pointerContext = new android.hardware.biometrics.fingerprint.PointerContext();
        pointerContext.pointerId = i2;
        pointerContext.x = f;
        pointerContext.y = f2;
        pointerContext.minor = f3;
        pointerContext.major = f4;
        pointerContext.orientation = f5;
        pointerContext.time = j2;
        pointerContext.gestureStart = j3;
        pointerContext.isAod = z;
        try {
            this.mService.onPointerUp(j, i, pointerContext);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onUdfpsUiEvent(int i, long j, int i2) {
        if (this.mService == null) {
            android.util.Slog.w(TAG, "onUdfpsUiEvent: no fingerprint service");
            return;
        }
        try {
            this.mService.onUdfpsUiEvent(i, j, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPowerPressed() {
        android.util.Slog.i(TAG, "onPowerPressed");
        this.mHandler.obtainMessage(110).sendToTarget();
    }

    @java.lang.Deprecated
    public boolean hasEnrolledFingerprints() {
        com.android.internal.util.FrameworkStatsLog.write(356, 2, this.mContext.getApplicationInfo().uid, this.mContext.getApplicationInfo().targetSdkVersion);
        return hasEnrolledFingerprints(android.os.UserHandle.myUserId());
    }

    public boolean hasEnrolledFingerprints(int i) {
        if (this.mService != null) {
            try {
                return this.mService.hasEnrolledFingerprintsDeprecated(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @java.lang.Deprecated
    public boolean isHardwareDetected() {
        com.android.internal.util.FrameworkStatsLog.write(356, 3, this.mContext.getApplicationInfo().uid, this.mContext.getApplicationInfo().targetSdkVersion);
        if (this.mService != null) {
            try {
                return this.mService.isHardwareDetectedDeprecated(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.util.Slog.w(TAG, "isFingerprintHardwareDetected(): Service not connected!");
        return false;
    }

    public java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> getSensorPropertiesInternal() {
        try {
            if (this.mProps.isEmpty() && this.mService != null) {
                return this.mService.getSensorPropertiesInternal(this.mContext.getOpPackageName());
            }
            return this.mProps;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPowerbuttonFps() {
        android.hardware.fingerprint.FingerprintSensorPropertiesInternal firstFingerprintSensor = getFirstFingerprintSensor();
        return firstFingerprintSensor != null && firstFingerprintSensor.sensorType == 4;
    }

    public void addAuthenticatorsRegisteredCallback(android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback iFingerprintAuthenticatorsRegisteredCallback) {
        if (this.mService != null) {
            try {
                this.mService.addAuthenticatorsRegisteredCallback(iFingerprintAuthenticatorsRegisteredCallback);
                return;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.util.Slog.w(TAG, "addProvidersAvailableCallback(): Service not connected!");
    }

    public int getLockoutModeForUser(int i, int i2) {
        if (this.mService != null) {
            try {
                return this.mService.getLockoutModeForUser(i, i2);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                return 0;
            }
        }
        return 0;
    }

    public void scheduleWatchdog() {
        try {
            this.mService.scheduleWatchdog();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addLockoutResetCallback(android.hardware.fingerprint.FingerprintManager.LockoutResetCallback lockoutResetCallback) {
        if (this.mService != null) {
            try {
                this.mService.addLockoutResetCallback(new android.hardware.fingerprint.FingerprintManager.AnonymousClass1((android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class), lockoutResetCallback), this.mContext.getOpPackageName());
                return;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.util.Slog.w(TAG, "addLockoutResetCallback(): Service not connected!");
    }

    /* renamed from: android.hardware.fingerprint.FingerprintManager$1, reason: invalid class name */
    class AnonymousClass1 extends android.hardware.biometrics.IBiometricServiceLockoutResetCallback.Stub {
        final /* synthetic */ android.hardware.fingerprint.FingerprintManager.LockoutResetCallback val$callback;
        final /* synthetic */ android.os.PowerManager val$powerManager;

        AnonymousClass1(android.os.PowerManager powerManager, android.hardware.fingerprint.FingerprintManager.LockoutResetCallback lockoutResetCallback) {
            this.val$powerManager = powerManager;
            this.val$callback = lockoutResetCallback;
        }

        @Override // android.hardware.biometrics.IBiometricServiceLockoutResetCallback
        public void onLockoutReset(final int i, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
            try {
                final android.os.PowerManager.WakeLock newWakeLock = this.val$powerManager.newWakeLock(1, "lockoutResetCallback");
                newWakeLock.acquire();
                android.os.Handler handler = android.hardware.fingerprint.FingerprintManager.this.mHandler;
                final android.hardware.fingerprint.FingerprintManager.LockoutResetCallback lockoutResetCallback = this.val$callback;
                handler.post(new java.lang.Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.fingerprint.FingerprintManager.AnonymousClass1.lambda$onLockoutReset$0(android.hardware.fingerprint.FingerprintManager.LockoutResetCallback.this, i, newWakeLock);
                    }
                });
            } finally {
                iRemoteCallback.sendResult(null);
            }
        }

        static /* synthetic */ void lambda$onLockoutReset$0(android.hardware.fingerprint.FingerprintManager.LockoutResetCallback lockoutResetCallback, int i, android.os.PowerManager.WakeLock wakeLock) {
            try {
                lockoutResetCallback.onLockoutReset(i);
            } finally {
                wakeLock.release();
            }
        }
    }

    private class MyHandler extends android.os.Handler {
        private MyHandler(android.content.Context context) {
            super(context.getMainLooper());
        }

        private MyHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 100:
                    android.hardware.fingerprint.FingerprintManager.this.sendEnrollResult((android.hardware.fingerprint.Fingerprint) message.obj, message.arg1);
                    return;
                case 101:
                    android.hardware.fingerprint.FingerprintManager.this.sendAcquiredResult(message.arg1, message.arg2);
                    return;
                case 102:
                    android.hardware.fingerprint.FingerprintManager.this.sendAuthenticatedSucceeded((android.hardware.fingerprint.Fingerprint) message.obj, message.arg1, message.arg2 == 1);
                    return;
                case 103:
                    android.hardware.fingerprint.FingerprintManager.this.sendAuthenticatedFailed();
                    return;
                case 104:
                    android.hardware.fingerprint.FingerprintManager.this.sendErrorResult(message.arg1, message.arg2);
                    return;
                case 105:
                    android.hardware.fingerprint.FingerprintManager.this.sendRemovedResult((android.hardware.fingerprint.Fingerprint) message.obj, message.arg1);
                    return;
                case 106:
                    android.hardware.fingerprint.FingerprintManager.this.sendChallengeGenerated(message.arg1, message.arg2, ((java.lang.Long) message.obj).longValue());
                    return;
                case 107:
                    android.hardware.fingerprint.FingerprintManager.this.sendFingerprintDetected(message.arg1, message.arg2, ((java.lang.Boolean) message.obj).booleanValue());
                    return;
                case 108:
                    android.hardware.fingerprint.FingerprintManager.this.sendUdfpsPointerDown(message.arg1);
                    return;
                case 109:
                    android.hardware.fingerprint.FingerprintManager.this.sendUdfpsPointerUp(message.arg1);
                    return;
                case 110:
                    android.hardware.fingerprint.FingerprintManager.this.sendPowerPressed();
                    return;
                case 111:
                    android.hardware.fingerprint.FingerprintManager.this.sendUdfpsOverlayShown();
                    break;
            }
            android.util.Slog.w(android.hardware.fingerprint.FingerprintManager.TAG, "Unknown message: " + message.what);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRemovedResult(android.hardware.fingerprint.Fingerprint fingerprint, int i) {
        if (this.mRemovalCallback == null) {
            return;
        }
        if (this.mRemoveTracker == null) {
            android.util.Slog.w(TAG, "Removal tracker is null");
            return;
        }
        if (this.mRemoveTracker.mRemoveRequest == 1) {
            if (fingerprint == null) {
                android.util.Slog.e(TAG, "Received MSG_REMOVED, but fingerprint is null");
                return;
            }
            if (this.mRemoveTracker.mSingleFingerprint == null) {
                android.util.Slog.e(TAG, "Missing fingerprint");
                return;
            }
            int biometricId = fingerprint.getBiometricId();
            int biometricId2 = this.mRemoveTracker.mSingleFingerprint.getBiometricId();
            if (biometricId2 != 0 && biometricId != 0 && biometricId != biometricId2) {
                android.util.Slog.w(TAG, "Finger id didn't match: " + biometricId + " != " + biometricId2);
                return;
            }
        }
        this.mRemovalCallback.onRemovalSucceeded(fingerprint, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEnrollResult(android.hardware.fingerprint.Fingerprint fingerprint, int i) {
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onEnrollmentProgress(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedSucceeded(android.hardware.fingerprint.Fingerprint fingerprint, int i, boolean z) {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationSucceeded(new android.hardware.fingerprint.FingerprintManager.AuthenticationResult(this.mCryptoObject, fingerprint, i, z));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedFailed() {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationFailed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAcquiredResult(int i, int i2) {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationAcquired(i);
        }
        if (this.mEnrollmentCallback != null && i != 7) {
            this.mEnrollmentCallback.onAcquired(i == 0);
        }
        java.lang.String acquiredString = getAcquiredString(this.mContext, i, i2);
        if (acquiredString == null) {
            return;
        }
        int i3 = i == 6 ? i2 + 1000 : i;
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onEnrollmentHelp(i3, acquiredString);
        } else if (this.mAuthenticationCallback != null && i != 7) {
            this.mAuthenticationCallback.onAuthenticationHelp(i3, acquiredString);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorResult(int i, int i2) {
        int i3 = i == 8 ? i2 + 1000 : i;
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onEnrollmentError(i3, getErrorString(this.mContext, i, i2));
            return;
        }
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationError(i3, getErrorString(this.mContext, i, i2));
            return;
        }
        if (this.mRemovalCallback != null) {
            this.mRemovalCallback.onRemovalError(this.mRemoveTracker != null ? this.mRemoveTracker.mSingleFingerprint : null, i3, getErrorString(this.mContext, i, i2));
        } else if (this.mFingerprintDetectionCallback != null) {
            this.mFingerprintDetectionCallback.onDetectionError(i);
            this.mFingerprintDetectionCallback = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendChallengeGenerated(int i, int i2, long j) {
        if (this.mGenerateChallengeCallback == null) {
            android.util.Slog.e(TAG, "sendChallengeGenerated, callback null");
        } else {
            this.mGenerateChallengeCallback.onChallengeGenerated(i, i2, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendFingerprintDetected(int i, int i2, boolean z) {
        if (this.mFingerprintDetectionCallback == null) {
            android.util.Slog.e(TAG, "sendFingerprintDetected, callback null");
        } else {
            this.mFingerprintDetectionCallback.onFingerprintDetected(i, i2, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUdfpsPointerDown(int i) {
        if (this.mAuthenticationCallback == null) {
            android.util.Slog.e(TAG, "sendUdfpsPointerDown, callback null");
        } else {
            this.mAuthenticationCallback.onUdfpsPointerDown(i);
        }
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onUdfpsPointerDown(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUdfpsPointerUp(int i) {
        if (this.mAuthenticationCallback == null) {
            android.util.Slog.e(TAG, "sendUdfpsPointerUp, callback null");
        } else {
            this.mAuthenticationCallback.onUdfpsPointerUp(i);
        }
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onUdfpsPointerUp(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPowerPressed() {
        try {
            this.mService.onPowerPressed();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error sending power press", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUdfpsOverlayShown() {
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onUdfpsOverlayShown();
        }
    }

    public FingerprintManager(android.content.Context context, android.hardware.fingerprint.IFingerprintService iFingerprintService) {
        this.mContext = context;
        this.mService = iFingerprintService;
        if (this.mService == null) {
            android.util.Slog.v(TAG, "FingerprintService was null");
        }
        this.mHandler = new android.hardware.fingerprint.FingerprintManager.MyHandler(context);
        if (context.checkCallingOrSelfPermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL) == 0) {
            addAuthenticatorsRegisteredCallback(new android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback.Stub() { // from class: android.hardware.fingerprint.FingerprintManager.2
                @Override // android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback
                public void onAllAuthenticatorsRegistered(java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> list) {
                    android.hardware.fingerprint.FingerprintManager.this.mProps = list;
                }
            });
        }
    }

    private int getCurrentUserId() {
        try {
            return android.app.ActivityManager.getService().getCurrentUser().id;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private android.hardware.fingerprint.FingerprintSensorPropertiesInternal getFirstFingerprintSensor() {
        java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal();
        if (sensorPropertiesInternal.isEmpty()) {
            return null;
        }
        return sensorPropertiesInternal.get(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelEnrollment(long j) {
        if (this.mService != null) {
            try {
                this.mService.cancelEnrollment(this.mToken, j);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAuthentication(long j) {
        if (this.mService != null) {
            try {
                this.mService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), j);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelFingerprintDetect(long j) {
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.cancelFingerprintDetect(this.mToken, this.mContext.getOpPackageName(), j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getEnrollStageCount() {
        if (this.mEnrollStageThresholds == null) {
            this.mEnrollStageThresholds = createEnrollStageThresholds(this.mContext);
        }
        return this.mEnrollStageThresholds.length + 1;
    }

    public float getEnrollStageThreshold(int i) {
        if (this.mEnrollStageThresholds == null) {
            this.mEnrollStageThresholds = createEnrollStageThresholds(this.mContext);
        }
        if (i < 0 || i > this.mEnrollStageThresholds.length) {
            android.util.Slog.w(TAG, "Unsupported enroll stage index: " + i);
            return i < 0 ? 0.0f : 1.0f;
        }
        if (i == this.mEnrollStageThresholds.length) {
            return 1.0f;
        }
        return this.mEnrollStageThresholds[i];
    }

    private float[] createEnrollStageThresholds(android.content.Context context) {
        java.lang.String[] stringArray;
        if (isPowerbuttonFps()) {
            stringArray = context.getResources().getStringArray(com.android.internal.R.array.config_sfps_enroll_stage_thresholds);
        } else {
            stringArray = context.getResources().getStringArray(com.android.internal.R.array.config_udfps_enroll_stage_thresholds);
        }
        int length = stringArray.length;
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = java.lang.Float.parseFloat(stringArray[i]);
        }
        return fArr;
    }

    public static java.lang.String getErrorString(android.content.Context context, int i, int i2) {
        switch (i) {
            case 1:
                return context.getString(com.android.internal.R.string.fingerprint_error_hw_not_available);
            case 2:
                return context.getString(com.android.internal.R.string.fingerprint_error_unable_to_process);
            case 3:
                return context.getString(com.android.internal.R.string.fingerprint_error_timeout);
            case 4:
                return context.getString(com.android.internal.R.string.fingerprint_error_no_space);
            case 5:
                return context.getString(com.android.internal.R.string.fingerprint_error_canceled);
            case 7:
                return context.getString(com.android.internal.R.string.fingerprint_error_lockout);
            case 8:
                java.lang.String[] stringArray = context.getResources().getStringArray(com.android.internal.R.array.fingerprint_error_vendor);
                if (i2 < stringArray.length) {
                    return stringArray[i2];
                }
                break;
            case 9:
                return context.getString(com.android.internal.R.string.fingerprint_error_lockout_permanent);
            case 10:
                return context.getString(com.android.internal.R.string.fingerprint_error_user_canceled);
            case 11:
                return context.getString(com.android.internal.R.string.fingerprint_error_no_fingerprints);
            case 12:
                return context.getString(com.android.internal.R.string.fingerprint_error_hw_not_present);
            case 15:
                return context.getString(com.android.internal.R.string.fingerprint_error_security_update_required);
            case 18:
                return context.getString(com.android.internal.R.string.fingerprint_error_bad_calibration);
            case 19:
                return context.getString(com.android.internal.R.string.fingerprint_error_power_pressed);
        }
        android.util.Slog.w(TAG, "Invalid error message: " + i + ", " + i2);
        return context.getString(com.android.internal.R.string.fingerprint_error_vendor_unknown);
    }

    public static java.lang.String getAcquiredString(android.content.Context context, int i, int i2) {
        switch (i) {
            case 0:
                return null;
            case 1:
                return context.getString(com.android.internal.R.string.fingerprint_acquired_partial);
            case 2:
                return context.getString(com.android.internal.R.string.fingerprint_acquired_insufficient);
            case 3:
                return context.getString(com.android.internal.R.string.fingerprint_acquired_imager_dirty);
            case 4:
                return context.getString(com.android.internal.R.string.fingerprint_acquired_too_slow);
            case 5:
                return context.getString(com.android.internal.R.string.fingerprint_acquired_too_fast);
            case 6:
                java.lang.String[] stringArray = context.getResources().getStringArray(com.android.internal.R.array.fingerprint_acquired_vendor);
                if (i2 < stringArray.length) {
                    return stringArray[i2];
                }
                break;
            case 7:
                return null;
            case 9:
                return context.getString(com.android.internal.R.string.fingerprint_acquired_immobile);
            case 10:
                return context.getString(com.android.internal.R.string.fingerprint_acquired_too_bright);
            case 11:
                return context.getString(com.android.internal.R.string.fingerprint_acquired_power_press);
        }
        android.util.Slog.w(TAG, "Invalid acquired message: " + i + ", " + i2);
        return null;
    }
}
