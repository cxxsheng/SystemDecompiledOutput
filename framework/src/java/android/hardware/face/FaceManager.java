package android.hardware.face;

/* loaded from: classes2.dex */
public class FaceManager implements android.hardware.biometrics.BiometricAuthenticator, android.hardware.biometrics.BiometricFaceConstants {
    private static final int MSG_ACQUIRED = 101;
    private static final int MSG_AUTHENTICATION_FAILED = 103;
    private static final int MSG_AUTHENTICATION_FRAME = 112;
    private static final int MSG_AUTHENTICATION_SUCCEEDED = 102;
    private static final int MSG_CHALLENGE_GENERATED = 108;
    private static final int MSG_ENROLLMENT_FRAME = 113;
    private static final int MSG_ENROLL_RESULT = 100;
    private static final int MSG_ERROR = 104;
    private static final int MSG_FACE_DETECTED = 109;
    private static final int MSG_GET_FEATURE_COMPLETED = 106;
    private static final int MSG_REMOVED = 105;
    private static final int MSG_SET_FEATURE_COMPLETED = 107;
    private static final java.lang.String TAG = "FaceManager";
    private android.hardware.face.FaceManager.AuthenticationCallback mAuthenticationCallback;
    private final android.content.Context mContext;
    private android.hardware.biometrics.CryptoObject mCryptoObject;
    private android.hardware.face.FaceManager.EnrollmentCallback mEnrollmentCallback;
    private android.hardware.face.FaceManager.FaceDetectionCallback mFaceDetectionCallback;
    private android.hardware.face.FaceManager.GenerateChallengeCallback mGenerateChallengeCallback;
    private android.hardware.face.FaceManager.GetFeatureCallback mGetFeatureCallback;
    private android.os.Handler mHandler;
    private android.hardware.face.FaceManager.RemovalCallback mRemovalCallback;
    private android.hardware.face.Face mRemovalFace;
    private final android.hardware.face.IFaceService mService;
    private android.hardware.face.FaceManager.SetFeatureCallback mSetFeatureCallback;
    private final android.os.IBinder mToken = new android.os.Binder();
    private java.util.List<android.hardware.face.FaceSensorPropertiesInternal> mProps = new java.util.ArrayList();
    private final android.hardware.face.IFaceServiceReceiver mServiceReceiver = new android.hardware.face.IFaceServiceReceiver.Stub() { // from class: android.hardware.face.FaceManager.1
        @Override // android.hardware.face.IFaceServiceReceiver
        public void onEnrollResult(android.hardware.face.Face face, int i) {
            android.hardware.face.FaceManager.this.mHandler.obtainMessage(100, i, 0, face).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAcquired(int i, int i2) {
            android.hardware.face.FaceManager.this.mHandler.obtainMessage(101, i, i2).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationSucceeded(android.hardware.face.Face face, int i, boolean z) {
            android.hardware.face.FaceManager.this.mHandler.obtainMessage(102, i, z ? 1 : 0, face).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFaceDetected(int i, int i2, boolean z) {
            android.hardware.face.FaceManager.this.mHandler.obtainMessage(109, i, i2, java.lang.Boolean.valueOf(z)).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationFailed() {
            android.hardware.face.FaceManager.this.mHandler.obtainMessage(103).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onError(int i, int i2) {
            android.hardware.face.FaceManager.this.mHandler.obtainMessage(104, i, i2).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onRemoved(android.hardware.face.Face face, int i) {
            android.hardware.face.FaceManager.this.mHandler.obtainMessage(105, i, 0, face).sendToTarget();
            if (i == 0) {
                android.provider.Settings.Secure.putIntForUser(android.hardware.face.FaceManager.this.mContext.getContentResolver(), android.provider.Settings.Secure.FACE_UNLOCK_RE_ENROLL, 0, -2);
            }
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFeatureSet(boolean z, int i) {
            android.hardware.face.FaceManager.this.mHandler.obtainMessage(107, i, 0, java.lang.Boolean.valueOf(z)).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = java.lang.Boolean.valueOf(z);
            obtain.arg2 = iArr;
            obtain.arg3 = zArr;
            android.hardware.face.FaceManager.this.mHandler.obtainMessage(106, obtain).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onChallengeGenerated(int i, int i2, long j) {
            android.hardware.face.FaceManager.this.mHandler.obtainMessage(108, i, i2, java.lang.Long.valueOf(j)).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationFrame(android.hardware.face.FaceAuthenticationFrame faceAuthenticationFrame) {
            android.hardware.face.FaceManager.this.mHandler.obtainMessage(112, faceAuthenticationFrame).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onEnrollmentFrame(android.hardware.face.FaceEnrollFrame faceEnrollFrame) {
            android.hardware.face.FaceManager.this.mHandler.obtainMessage(113, faceEnrollFrame).sendToTarget();
        }
    };

    public interface GenerateChallengeCallback {
        void onGenerateChallengeResult(int i, int i2, long j);
    }

    public static abstract class GetFeatureCallback {
        public abstract void onCompleted(boolean z, int[] iArr, boolean[] zArr);
    }

    public static abstract class SetFeatureCallback {
        public abstract void onCompleted(boolean z, int i);
    }

    public FaceManager(android.content.Context context, android.hardware.face.IFaceService iFaceService) {
        this.mContext = context;
        this.mService = iFaceService;
        if (this.mService == null) {
            android.util.Slog.v(TAG, "FaceAuthenticationManagerService was null");
        }
        this.mHandler = new android.hardware.face.FaceManager.MyHandler(context);
        if (context.checkCallingOrSelfPermission(android.Manifest.permission.USE_BIOMETRIC_INTERNAL) == 0) {
            addAuthenticatorsRegisteredCallback(new android.hardware.face.IFaceAuthenticatorsRegisteredCallback.Stub() { // from class: android.hardware.face.FaceManager.2
                @Override // android.hardware.face.IFaceAuthenticatorsRegisteredCallback
                public void onAllAuthenticatorsRegistered(java.util.List<android.hardware.face.FaceSensorPropertiesInternal> list) {
                    android.hardware.face.FaceManager.this.mProps = list;
                }
            });
        }
    }

    private void useHandler(android.os.Handler handler) {
        if (handler != null) {
            this.mHandler = new android.hardware.face.FaceManager.MyHandler(handler.getLooper());
        } else if (this.mHandler.getLooper() != this.mContext.getMainLooper()) {
            this.mHandler = new android.hardware.face.FaceManager.MyHandler(this.mContext.getMainLooper());
        }
    }

    @java.lang.Deprecated
    public void authenticate(android.hardware.biometrics.CryptoObject cryptoObject, android.os.CancellationSignal cancellationSignal, android.hardware.face.FaceManager.AuthenticationCallback authenticationCallback, android.os.Handler handler, int i) {
        authenticate(cryptoObject, cancellationSignal, authenticationCallback, handler, new android.hardware.face.FaceAuthenticateOptions.Builder().setUserId(i).build());
    }

    public void authenticate(android.hardware.biometrics.CryptoObject cryptoObject, android.os.CancellationSignal cancellationSignal, android.hardware.face.FaceManager.AuthenticationCallback authenticationCallback, android.os.Handler handler, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions) {
        if (authenticationCallback == null) {
            throw new java.lang.IllegalArgumentException("Must supply an authentication callback");
        }
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            android.util.Slog.w(TAG, "authentication already canceled");
            return;
        }
        faceAuthenticateOptions.setOpPackageName(this.mContext.getOpPackageName());
        faceAuthenticateOptions.setAttributionTag(this.mContext.getAttributionTag());
        try {
            if (this.mService != null) {
                try {
                    useHandler(handler);
                    this.mAuthenticationCallback = authenticationCallback;
                    this.mCryptoObject = cryptoObject;
                    long opId = cryptoObject != null ? cryptoObject.getOpId() : 0L;
                    android.os.Trace.beginSection("FaceManager#authenticate");
                    long authenticate = this.mService.authenticate(this.mToken, opId, this.mServiceReceiver, faceAuthenticateOptions);
                    if (cancellationSignal != null) {
                        cancellationSignal.setOnCancelListener(new android.hardware.face.FaceManager.OnAuthenticationCancelListener(authenticate));
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Remote exception while authenticating: ", e);
                    authenticationCallback.onAuthenticationError(1, getErrorString(this.mContext, 1, 0));
                }
            }
        } finally {
            android.os.Trace.endSection();
        }
    }

    public void detectFace(android.os.CancellationSignal cancellationSignal, android.hardware.face.FaceManager.FaceDetectionCallback faceDetectionCallback, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions) {
        if (this.mService == null) {
            return;
        }
        if (cancellationSignal.isCanceled()) {
            android.util.Slog.w(TAG, "Detection already cancelled");
            return;
        }
        faceAuthenticateOptions.setOpPackageName(this.mContext.getOpPackageName());
        faceAuthenticateOptions.setAttributionTag(this.mContext.getAttributionTag());
        this.mFaceDetectionCallback = faceDetectionCallback;
        try {
            cancellationSignal.setOnCancelListener(new android.hardware.face.FaceManager.OnFaceDetectionCancelListener(this.mService.detectFace(this.mToken, this.mServiceReceiver, faceAuthenticateOptions)));
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Remote exception when requesting finger detect", e);
        }
    }

    public void enroll(int i, byte[] bArr, android.os.CancellationSignal cancellationSignal, android.hardware.face.FaceManager.EnrollmentCallback enrollmentCallback, int[] iArr) {
        enroll(i, bArr, cancellationSignal, enrollmentCallback, iArr, null, false, new android.hardware.face.FaceEnrollOptions.Builder().build());
    }

    public void enroll(int i, byte[] bArr, android.os.CancellationSignal cancellationSignal, android.hardware.face.FaceManager.EnrollmentCallback enrollmentCallback, int[] iArr, android.view.Surface surface, boolean z, android.hardware.face.FaceEnrollOptions faceEnrollOptions) {
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
        if (getEnrolledFaces(i).size() >= this.mContext.getResources().getInteger(com.android.internal.R.integer.config_faceMaxTemplatesPerUser)) {
            enrollmentCallback.onEnrollmentError(1, getErrorString(this.mContext, 1, 0));
            return;
        }
        if (this.mService != null) {
            try {
                try {
                    this.mEnrollmentCallback = enrollmentCallback;
                    android.os.Trace.beginSection("FaceManager#enroll");
                    long enroll = this.mService.enroll(i, this.mToken, bArr, this.mServiceReceiver, this.mContext.getOpPackageName(), iArr, surface, z, faceEnrollOptions);
                    if (cancellationSignal != null) {
                        cancellationSignal.setOnCancelListener(new android.hardware.face.FaceManager.OnEnrollCancelListener(enroll));
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Remote exception in enroll: ", e);
                    enrollmentCallback.onEnrollmentError(1, getErrorString(this.mContext, 1, 0));
                }
            } finally {
                android.os.Trace.endSection();
            }
        }
    }

    public void enrollRemotely(int i, byte[] bArr, android.os.CancellationSignal cancellationSignal, android.hardware.face.FaceManager.EnrollmentCallback enrollmentCallback, int[] iArr) {
        if (enrollmentCallback == null) {
            throw new java.lang.IllegalArgumentException("Must supply an enrollment callback");
        }
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            android.util.Slog.w(TAG, "enrollRemotely is already canceled.");
            return;
        }
        if (this.mService != null) {
            try {
                try {
                    this.mEnrollmentCallback = enrollmentCallback;
                    android.os.Trace.beginSection("FaceManager#enrollRemotely");
                    long enrollRemotely = this.mService.enrollRemotely(i, this.mToken, bArr, this.mServiceReceiver, this.mContext.getOpPackageName(), iArr);
                    if (cancellationSignal != null) {
                        cancellationSignal.setOnCancelListener(new android.hardware.face.FaceManager.OnEnrollCancelListener(enrollRemotely));
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Remote exception in enrollRemotely: ", e);
                    enrollmentCallback.onEnrollmentError(1, getErrorString(this.mContext, 1, 0));
                }
            } finally {
                android.os.Trace.endSection();
            }
        }
    }

    public void generateChallenge(int i, int i2, android.hardware.face.FaceManager.GenerateChallengeCallback generateChallengeCallback) {
        if (this.mService != null) {
            try {
                this.mGenerateChallengeCallback = generateChallengeCallback;
                this.mService.generateChallenge(this.mToken, i, i2, this.mServiceReceiver, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void generateChallenge(int i, android.hardware.face.FaceManager.GenerateChallengeCallback generateChallengeCallback) {
        java.util.List<android.hardware.face.FaceSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal();
        if (sensorPropertiesInternal.isEmpty()) {
            android.util.Slog.e(TAG, "No sensors");
        } else {
            generateChallenge(sensorPropertiesInternal.get(0).sensorId, i, generateChallengeCallback);
        }
    }

    public void revokeChallenge(int i, int i2, long j) {
        if (this.mService != null) {
            try {
                this.mService.revokeChallenge(this.mToken, i, i2, this.mContext.getOpPackageName(), j);
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

    public void setFeature(int i, int i2, boolean z, byte[] bArr, android.hardware.face.FaceManager.SetFeatureCallback setFeatureCallback) {
        if (this.mService != null) {
            try {
                this.mSetFeatureCallback = setFeatureCallback;
                this.mService.setFeature(this.mToken, i, i2, z, bArr, this.mServiceReceiver, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void getFeature(int i, int i2, android.hardware.face.FaceManager.GetFeatureCallback getFeatureCallback) {
        if (this.mService != null) {
            try {
                this.mGetFeatureCallback = getFeatureCallback;
                this.mService.getFeature(this.mToken, i, i2, this.mServiceReceiver, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void remove(android.hardware.face.Face face, int i, android.hardware.face.FaceManager.RemovalCallback removalCallback) {
        if (this.mService != null) {
            try {
                this.mRemovalCallback = removalCallback;
                this.mRemovalFace = face;
                this.mService.remove(this.mToken, face.getBiometricId(), i, this.mServiceReceiver, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeAll(int i, android.hardware.face.FaceManager.RemovalCallback removalCallback) {
        if (this.mService != null) {
            try {
                this.mRemovalCallback = removalCallback;
                this.mService.removeAll(this.mToken, i, this.mServiceReceiver, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.util.List<android.hardware.face.Face> getEnrolledFaces(int i) {
        java.util.List<android.hardware.face.FaceSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal();
        if (sensorPropertiesInternal.isEmpty()) {
            android.util.Slog.e(TAG, "No sensors");
            return new java.util.ArrayList();
        }
        if (this.mService != null) {
            try {
                return this.mService.getEnrolledFaces(sensorPropertiesInternal.get(0).sensorId, i, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public java.util.List<android.hardware.face.Face> getEnrolledFaces() {
        return getEnrolledFaces(android.os.UserHandle.myUserId());
    }

    public boolean hasEnrolledTemplates() {
        return hasEnrolledTemplates(android.os.UserHandle.myUserId());
    }

    public boolean hasEnrolledTemplates(int i) {
        java.util.List<android.hardware.face.FaceSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal();
        if (sensorPropertiesInternal.isEmpty()) {
            android.util.Slog.e(TAG, "No sensors");
            return false;
        }
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.hasEnrolledFaces(sensorPropertiesInternal.get(0).sensorId, i, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isHardwareDetected() {
        java.util.List<android.hardware.face.FaceSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal();
        if (sensorPropertiesInternal.isEmpty()) {
            android.util.Slog.e(TAG, "No sensors");
            return false;
        }
        if (this.mService != null) {
            try {
                return this.mService.isHardwareDetected(sensorPropertiesInternal.get(0).sensorId, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.util.Slog.w(TAG, "isFaceHardwareDetected(): Service not connected!");
        return false;
    }

    public java.util.List<android.hardware.face.FaceSensorProperties> getSensorProperties() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.hardware.face.FaceSensorPropertiesInternal> it = getSensorPropertiesInternal().iterator();
        while (it.hasNext()) {
            arrayList.add(android.hardware.face.FaceSensorProperties.from(it.next()));
        }
        return arrayList;
    }

    public java.util.List<android.hardware.face.FaceSensorPropertiesInternal> getSensorPropertiesInternal() {
        try {
            if (this.mProps.isEmpty() && this.mService != null) {
                return this.mService.getSensorPropertiesInternal(this.mContext.getOpPackageName());
            }
            return this.mProps;
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return this.mProps;
        }
    }

    public void registerBiometricStateListener(android.hardware.biometrics.BiometricStateListener biometricStateListener) {
        try {
            this.mService.registerBiometricStateListener(biometricStateListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addAuthenticatorsRegisteredCallback(android.hardware.face.IFaceAuthenticatorsRegisteredCallback iFaceAuthenticatorsRegisteredCallback) {
        if (this.mService != null) {
            try {
                this.mService.addAuthenticatorsRegisteredCallback(iFaceAuthenticatorsRegisteredCallback);
                return;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.util.Slog.w(TAG, "addAuthenticatorsRegisteredCallback(): Service not connected!");
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

    public void addLockoutResetCallback(android.hardware.face.FaceManager.LockoutResetCallback lockoutResetCallback) {
        if (this.mService != null) {
            try {
                this.mService.addLockoutResetCallback(new android.hardware.face.FaceManager.AnonymousClass3((android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class), lockoutResetCallback), this.mContext.getOpPackageName());
                return;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.util.Slog.w(TAG, "addLockoutResetCallback(): Service not connected!");
    }

    /* renamed from: android.hardware.face.FaceManager$3, reason: invalid class name */
    class AnonymousClass3 extends android.hardware.biometrics.IBiometricServiceLockoutResetCallback.Stub {
        final /* synthetic */ android.hardware.face.FaceManager.LockoutResetCallback val$callback;
        final /* synthetic */ android.os.PowerManager val$powerManager;

        AnonymousClass3(android.os.PowerManager powerManager, android.hardware.face.FaceManager.LockoutResetCallback lockoutResetCallback) {
            this.val$powerManager = powerManager;
            this.val$callback = lockoutResetCallback;
        }

        @Override // android.hardware.biometrics.IBiometricServiceLockoutResetCallback
        public void onLockoutReset(final int i, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
            try {
                final android.os.PowerManager.WakeLock newWakeLock = this.val$powerManager.newWakeLock(1, "faceLockoutResetCallback");
                newWakeLock.acquire();
                android.os.Handler handler = android.hardware.face.FaceManager.this.mHandler;
                final android.hardware.face.FaceManager.LockoutResetCallback lockoutResetCallback = this.val$callback;
                handler.post(new java.lang.Runnable() { // from class: android.hardware.face.FaceManager$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.face.FaceManager.AnonymousClass3.lambda$onLockoutReset$0(android.hardware.face.FaceManager.LockoutResetCallback.this, i, newWakeLock);
                    }
                });
            } finally {
                iRemoteCallback.sendResult(null);
            }
        }

        static /* synthetic */ void lambda$onLockoutReset$0(android.hardware.face.FaceManager.LockoutResetCallback lockoutResetCallback, int i, android.os.PowerManager.WakeLock wakeLock) {
            try {
                lockoutResetCallback.onLockoutReset(i);
            } finally {
                wakeLock.release();
            }
        }
    }

    public void scheduleWatchdog() {
        try {
            this.mService.scheduleWatchdog();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
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
                this.mService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName(), j);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelFaceDetect(long j) {
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.cancelFaceDetect(this.mToken, this.mContext.getOpPackageName(), j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static java.lang.String getErrorString(android.content.Context context, int i, int i2) {
        switch (i) {
            case 1:
                return context.getString(com.android.internal.R.string.face_error_hw_not_available);
            case 2:
                return context.getString(com.android.internal.R.string.face_error_unable_to_process);
            case 3:
                return context.getString(com.android.internal.R.string.face_error_timeout);
            case 4:
                return context.getString(com.android.internal.R.string.face_error_no_space);
            case 5:
                return context.getString(com.android.internal.R.string.face_error_canceled);
            case 7:
                return context.getString(com.android.internal.R.string.face_error_lockout);
            case 8:
                java.lang.String[] stringArray = context.getResources().getStringArray(com.android.internal.R.array.face_error_vendor);
                if (i2 < stringArray.length) {
                    return stringArray[i2];
                }
                break;
            case 9:
                return context.getString(com.android.internal.R.string.face_error_lockout_permanent);
            case 10:
                return context.getString(com.android.internal.R.string.face_error_user_canceled);
            case 11:
                return context.getString(com.android.internal.R.string.face_error_not_enrolled);
            case 12:
                return context.getString(com.android.internal.R.string.face_error_hw_not_present);
            case 15:
                return context.getString(com.android.internal.R.string.face_error_security_update_required);
            case 16:
                return context.getString(com.android.internal.R.string.face_recalibrate_notification_content);
        }
        android.util.Slog.w(TAG, "Invalid error message: " + i + ", " + i2);
        return context.getString(com.android.internal.R.string.face_error_vendor_unknown);
    }

    public static int getMappedAcquiredInfo(int i, int i2) {
        switch (i) {
            case 0:
                return 0;
            case 1:
            case 2:
            case 3:
                return 2;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return 1;
            case 10:
            case 11:
            case 12:
            case 13:
                return 2;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            default:
                return 0;
            case 22:
                return i2 + 1000;
        }
    }

    public static class AuthenticationResult {
        private final android.hardware.biometrics.CryptoObject mCryptoObject;
        private final android.hardware.face.Face mFace;
        private final boolean mIsStrongBiometric;
        private final int mUserId;

        public AuthenticationResult(android.hardware.biometrics.CryptoObject cryptoObject, android.hardware.face.Face face, int i, boolean z) {
            this.mCryptoObject = cryptoObject;
            this.mFace = face;
            this.mUserId = i;
            this.mIsStrongBiometric = z;
        }

        public android.hardware.biometrics.CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        public android.hardware.face.Face getFace() {
            return this.mFace;
        }

        public int getUserId() {
            return this.mUserId;
        }

        public boolean isStrongBiometric() {
            return this.mIsStrongBiometric;
        }
    }

    public static abstract class AuthenticationCallback extends android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback {
        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationError(int i, java.lang.CharSequence charSequence) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationHelp(int i, java.lang.CharSequence charSequence) {
        }

        public void onAuthenticationSucceeded(android.hardware.face.FaceManager.AuthenticationResult authenticationResult) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationFailed() {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationAcquired(int i) {
        }
    }

    public interface FaceDetectionCallback {
        void onFaceDetected(int i, int i2, boolean z);

        default void onDetectionError(int i) {
        }
    }

    public static abstract class EnrollmentCallback {
        public void onEnrollmentError(int i, java.lang.CharSequence charSequence) {
        }

        public void onEnrollmentHelp(int i, java.lang.CharSequence charSequence) {
        }

        public void onEnrollmentFrame(int i, java.lang.CharSequence charSequence, android.hardware.face.FaceEnrollCell faceEnrollCell, int i2, float f, float f2, float f3) {
            onEnrollmentHelp(i, charSequence);
        }

        public void onEnrollmentProgress(int i) {
        }
    }

    public static abstract class RemovalCallback {
        public void onRemovalError(android.hardware.face.Face face, int i, java.lang.CharSequence charSequence) {
        }

        public void onRemovalSucceeded(android.hardware.face.Face face, int i) {
        }
    }

    public static abstract class LockoutResetCallback {
        public void onLockoutReset(int i) {
        }
    }

    private class OnEnrollCancelListener implements android.os.CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        private OnEnrollCancelListener(long j) {
            this.mAuthRequestId = j;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            android.util.Slog.d(android.hardware.face.FaceManager.TAG, "Cancel face enrollment requested for: " + this.mAuthRequestId);
            android.hardware.face.FaceManager.this.cancelEnrollment(this.mAuthRequestId);
        }
    }

    private class OnAuthenticationCancelListener implements android.os.CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnAuthenticationCancelListener(long j) {
            this.mAuthRequestId = j;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            android.util.Slog.d(android.hardware.face.FaceManager.TAG, "Cancel face authentication requested for: " + this.mAuthRequestId);
            android.hardware.face.FaceManager.this.cancelAuthentication(this.mAuthRequestId);
        }
    }

    private class OnFaceDetectionCancelListener implements android.os.CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnFaceDetectionCancelListener(long j) {
            this.mAuthRequestId = j;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            android.util.Slog.d(android.hardware.face.FaceManager.TAG, "Cancel face detect requested for: " + this.mAuthRequestId);
            android.hardware.face.FaceManager.this.cancelFaceDetect(this.mAuthRequestId);
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
            android.os.Trace.beginSection("FaceManager#handleMessage: " + java.lang.Integer.toString(message.what));
            switch (message.what) {
                case 100:
                    android.hardware.face.FaceManager.this.sendEnrollResult((android.hardware.face.Face) message.obj, message.arg1);
                    break;
                case 101:
                    android.hardware.face.FaceManager.this.sendAcquiredResult(message.arg1, message.arg2);
                    break;
                case 102:
                    android.hardware.face.FaceManager.this.sendAuthenticatedSucceeded((android.hardware.face.Face) message.obj, message.arg1, message.arg2 == 1);
                    break;
                case 103:
                    android.hardware.face.FaceManager.this.sendAuthenticatedFailed();
                    break;
                case 104:
                    android.hardware.face.FaceManager.this.sendErrorResult(message.arg1, message.arg2);
                    break;
                case 105:
                    android.hardware.face.FaceManager.this.sendRemovedResult((android.hardware.face.Face) message.obj, message.arg1);
                    break;
                case 106:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.hardware.face.FaceManager.this.sendGetFeatureCompleted(((java.lang.Boolean) someArgs.arg1).booleanValue(), (int[]) someArgs.arg2, (boolean[]) someArgs.arg3);
                    someArgs.recycle();
                    break;
                case 107:
                    android.hardware.face.FaceManager.this.sendSetFeatureCompleted(((java.lang.Boolean) message.obj).booleanValue(), message.arg1);
                    break;
                case 108:
                    android.hardware.face.FaceManager.this.sendChallengeGenerated(message.arg1, message.arg2, ((java.lang.Long) message.obj).longValue());
                    break;
                case 109:
                    android.hardware.face.FaceManager.this.sendFaceDetected(message.arg1, message.arg2, ((java.lang.Boolean) message.obj).booleanValue());
                    break;
                case 110:
                case 111:
                default:
                    android.util.Slog.w(android.hardware.face.FaceManager.TAG, "Unknown message: " + message.what);
                    break;
                case 112:
                    android.hardware.face.FaceManager.this.sendAuthenticationFrame((android.hardware.face.FaceAuthenticationFrame) message.obj);
                    break;
                case 113:
                    android.hardware.face.FaceManager.this.sendEnrollmentFrame((android.hardware.face.FaceEnrollFrame) message.obj);
                    break;
            }
            android.os.Trace.endSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSetFeatureCompleted(boolean z, int i) {
        if (this.mSetFeatureCallback == null) {
            return;
        }
        this.mSetFeatureCallback.onCompleted(z, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendGetFeatureCompleted(boolean z, int[] iArr, boolean[] zArr) {
        if (this.mGetFeatureCallback == null) {
            return;
        }
        this.mGetFeatureCallback.onCompleted(z, iArr, zArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendChallengeGenerated(int i, int i2, long j) {
        if (this.mGenerateChallengeCallback == null) {
            return;
        }
        this.mGenerateChallengeCallback.onGenerateChallengeResult(i, i2, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendFaceDetected(int i, int i2, boolean z) {
        if (this.mFaceDetectionCallback == null) {
            android.util.Slog.e(TAG, "sendFaceDetected, callback null");
        } else {
            this.mFaceDetectionCallback.onFaceDetected(i, i2, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRemovedResult(android.hardware.face.Face face, int i) {
        if (this.mRemovalCallback == null) {
            return;
        }
        this.mRemovalCallback.onRemovalSucceeded(face, i);
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
            this.mRemovalCallback.onRemovalError(this.mRemovalFace, i3, getErrorString(this.mContext, i, i2));
        } else if (this.mFaceDetectionCallback != null) {
            this.mFaceDetectionCallback.onDetectionError(i);
            this.mFaceDetectionCallback = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEnrollResult(android.hardware.face.Face face, int i) {
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onEnrollmentProgress(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedSucceeded(android.hardware.face.Face face, int i, boolean z) {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationSucceeded(new android.hardware.face.FaceManager.AuthenticationResult(this.mCryptoObject, face, i, z));
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
            sendAuthenticationFrame(new android.hardware.face.FaceAuthenticationFrame(new android.hardware.face.FaceDataFrame(i, i2)));
        } else if (this.mEnrollmentCallback != null) {
            sendEnrollmentFrame(new android.hardware.face.FaceEnrollFrame(null, 0, new android.hardware.face.FaceDataFrame(i, i2)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticationFrame(android.hardware.face.FaceAuthenticationFrame faceAuthenticationFrame) {
        if (faceAuthenticationFrame == null) {
            android.util.Slog.w(TAG, "Received null authentication frame");
            return;
        }
        if (this.mAuthenticationCallback != null) {
            int acquiredInfo = faceAuthenticationFrame.getData().getAcquiredInfo();
            int vendorCode = faceAuthenticationFrame.getData().getVendorCode();
            int helpCode = getHelpCode(acquiredInfo, vendorCode);
            java.lang.String authHelpMessage = getAuthHelpMessage(this.mContext, acquiredInfo, vendorCode);
            this.mAuthenticationCallback.onAuthenticationAcquired(acquiredInfo);
            if (authHelpMessage != null) {
                this.mAuthenticationCallback.onAuthenticationHelp(helpCode, authHelpMessage);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEnrollmentFrame(android.hardware.face.FaceEnrollFrame faceEnrollFrame) {
        if (faceEnrollFrame == null) {
            android.util.Slog.w(TAG, "Received null enrollment frame");
            return;
        }
        if (this.mEnrollmentCallback != null) {
            android.hardware.face.FaceDataFrame data = faceEnrollFrame.getData();
            int acquiredInfo = data.getAcquiredInfo();
            int vendorCode = data.getVendorCode();
            this.mEnrollmentCallback.onEnrollmentFrame(getHelpCode(acquiredInfo, vendorCode), getEnrollHelpMessage(this.mContext, acquiredInfo, vendorCode), faceEnrollFrame.getCell(), faceEnrollFrame.getStage(), data.getPan(), data.getTilt(), data.getDistance());
        }
    }

    private static int getHelpCode(int i, int i2) {
        if (i != 22) {
            return i;
        }
        return i2 + 1000;
    }

    public static java.lang.String getAuthHelpMessage(android.content.Context context, int i, int i2) {
        switch (i) {
            case 0:
            case 20:
                return null;
            case 1:
                return context.getString(com.android.internal.R.string.face_acquired_insufficient);
            case 2:
                return context.getString(com.android.internal.R.string.face_acquired_too_bright);
            case 3:
                return context.getString(com.android.internal.R.string.face_acquired_too_dark);
            case 4:
                return context.getString(com.android.internal.R.string.face_acquired_too_close);
            case 5:
                return context.getString(com.android.internal.R.string.face_acquired_too_far);
            case 6:
                return context.getString(com.android.internal.R.string.face_acquired_too_low);
            case 7:
                return context.getString(com.android.internal.R.string.face_acquired_too_high);
            case 8:
                return context.getString(com.android.internal.R.string.face_acquired_too_left);
            case 9:
                return context.getString(com.android.internal.R.string.face_acquired_too_right);
            case 10:
                return context.getString(com.android.internal.R.string.face_acquired_poor_gaze);
            case 11:
                return context.getString(com.android.internal.R.string.face_acquired_not_detected);
            case 12:
                return context.getString(com.android.internal.R.string.face_acquired_too_much_motion);
            case 13:
                return context.getString(com.android.internal.R.string.face_acquired_recalibrate);
            case 14:
                return context.getString(com.android.internal.R.string.face_acquired_too_different);
            case 15:
                return context.getString(com.android.internal.R.string.face_acquired_too_similar);
            case 16:
                return context.getString(com.android.internal.R.string.face_acquired_pan_too_extreme);
            case 17:
                return context.getString(com.android.internal.R.string.face_acquired_tilt_too_extreme);
            case 18:
                return context.getString(com.android.internal.R.string.face_acquired_roll_too_extreme);
            case 19:
                return context.getString(com.android.internal.R.string.face_acquired_obscured);
            case 21:
                return context.getString(com.android.internal.R.string.face_acquired_sensor_dirty);
            case 22:
                java.lang.String[] stringArray = context.getResources().getStringArray(com.android.internal.R.array.face_acquired_vendor);
                if (i2 < stringArray.length) {
                    return stringArray[i2];
                }
                break;
            case 25:
                return context.getString(com.android.internal.R.string.face_acquired_dark_glasses_detected);
            case 26:
                return context.getString(com.android.internal.R.string.face_acquired_mouth_covering_detected);
        }
        android.util.Slog.w(TAG, "Unknown authentication acquired message: " + i + ", " + i2);
        return null;
    }

    public static java.lang.String getEnrollHelpMessage(android.content.Context context, int i, int i2) {
        switch (i) {
            case 0:
            case 20:
                return null;
            case 1:
                return context.getString(com.android.internal.R.string.face_acquired_insufficient);
            case 2:
                return context.getString(com.android.internal.R.string.face_acquired_too_bright);
            case 3:
                return context.getString(com.android.internal.R.string.face_acquired_too_dark);
            case 4:
                return context.getString(com.android.internal.R.string.face_acquired_too_close);
            case 5:
                return context.getString(com.android.internal.R.string.face_acquired_too_far);
            case 6:
                return context.getString(com.android.internal.R.string.face_acquired_too_low);
            case 7:
                return context.getString(com.android.internal.R.string.face_acquired_too_high);
            case 8:
                return context.getString(com.android.internal.R.string.face_acquired_too_left);
            case 9:
                return context.getString(com.android.internal.R.string.face_acquired_too_right);
            case 10:
                return context.getString(com.android.internal.R.string.face_acquired_poor_gaze);
            case 11:
                return context.getString(com.android.internal.R.string.face_acquired_not_detected);
            case 12:
                return context.getString(com.android.internal.R.string.face_acquired_too_much_motion);
            case 13:
                return context.getString(com.android.internal.R.string.face_acquired_recalibrate);
            case 14:
                return context.getString(com.android.internal.R.string.face_acquired_too_different);
            case 15:
                return context.getString(com.android.internal.R.string.face_acquired_too_similar);
            case 16:
                return context.getString(com.android.internal.R.string.face_acquired_pan_too_extreme);
            case 17:
                return context.getString(com.android.internal.R.string.face_acquired_tilt_too_extreme);
            case 18:
                return context.getString(com.android.internal.R.string.face_acquired_roll_too_extreme);
            case 19:
                return context.getString(com.android.internal.R.string.face_acquired_obscured);
            case 21:
                return context.getString(com.android.internal.R.string.face_acquired_sensor_dirty);
            case 22:
                java.lang.String[] stringArray = context.getResources().getStringArray(com.android.internal.R.array.face_acquired_vendor);
                if (i2 < stringArray.length) {
                    return stringArray[i2];
                }
                break;
            case 25:
                return context.getString(com.android.internal.R.string.face_acquired_dark_glasses_detected);
            case 26:
                return context.getString(com.android.internal.R.string.face_acquired_mouth_covering_detected);
        }
        android.util.Slog.w(TAG, "Unknown enrollment acquired message: " + i + ", " + i2);
        return null;
    }
}
