package android.hardware.biometrics;

/* loaded from: classes.dex */
public class BiometricPrompt implements android.hardware.biometrics.BiometricAuthenticator, android.hardware.biometrics.BiometricConstants {
    public static final int AUTHENTICATION_RESULT_TYPE_BIOMETRIC = 2;
    public static final int AUTHENTICATION_RESULT_TYPE_DEVICE_CREDENTIAL = 1;
    public static final int DISMISSED_REASON_BIOMETRIC_CONFIRMED = 1;
    public static final int DISMISSED_REASON_BIOMETRIC_CONFIRM_NOT_REQUIRED = 4;
    public static final int DISMISSED_REASON_CREDENTIAL_CONFIRMED = 7;
    public static final int DISMISSED_REASON_ERROR = 5;
    public static final int DISMISSED_REASON_NEGATIVE = 2;
    public static final int DISMISSED_REASON_SERVER_REQUESTED = 6;
    public static final int DISMISSED_REASON_USER_CANCEL = 3;
    public static final int HIDE_DIALOG_DELAY = 2000;
    private static final java.lang.String TAG = "BiometricPrompt";
    private android.hardware.biometrics.BiometricPrompt.AuthenticationCallback mAuthenticationCallback;
    private final android.hardware.biometrics.IBiometricServiceReceiver mBiometricServiceReceiver;
    private final android.content.Context mContext;
    private android.hardware.biometrics.BiometricPrompt.CryptoObject mCryptoObject;
    private java.util.concurrent.Executor mExecutor;
    private boolean mIsPromptShowing;
    private final android.hardware.biometrics.BiometricPrompt.ButtonInfo mNegativeButtonInfo;
    private final android.hardware.biometrics.PromptInfo mPromptInfo;
    private final android.hardware.biometrics.IAuthService mService;
    private final android.os.IBinder mToken;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AuthenticationResultType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DismissedReason {
    }

    private static class ButtonInfo {
        java.util.concurrent.Executor executor;
        android.content.DialogInterface.OnClickListener listener;

        ButtonInfo(java.util.concurrent.Executor executor, android.content.DialogInterface.OnClickListener onClickListener) {
            this.executor = executor;
            this.listener = onClickListener;
        }
    }

    public static class Builder {
        private android.content.Context mContext;
        private android.hardware.biometrics.BiometricPrompt.ButtonInfo mNegativeButtonInfo;
        private android.hardware.biometrics.PromptInfo mPromptInfo = new android.hardware.biometrics.PromptInfo();
        private android.hardware.biometrics.IAuthService mService;

        public Builder(android.content.Context context) {
            this.mContext = context;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setLogoRes(int i) {
            this.mPromptInfo.setLogoRes(i);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setLogoBitmap(android.graphics.Bitmap bitmap) {
            this.mPromptInfo.setLogoBitmap(bitmap);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setLogoDescription(java.lang.String str) {
            this.mPromptInfo.setLogoDescription(str);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setTitle(java.lang.CharSequence charSequence) {
            this.mPromptInfo.setTitle(charSequence);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setUseDefaultTitle() {
            this.mPromptInfo.setUseDefaultTitle(true);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setSubtitle(java.lang.CharSequence charSequence) {
            this.mPromptInfo.setSubtitle(charSequence);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setUseDefaultSubtitle() {
            this.mPromptInfo.setUseDefaultSubtitle(true);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setDescription(java.lang.CharSequence charSequence) {
            this.mPromptInfo.setDescription(charSequence);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setContentView(android.hardware.biometrics.PromptContentView promptContentView) {
            this.mPromptInfo.setContentView(promptContentView);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setService(android.hardware.biometrics.IAuthService iAuthService) {
            this.mService = iAuthService;
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setTextForDeviceCredential(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.CharSequence charSequence3) {
            if (charSequence != null) {
                this.mPromptInfo.setDeviceCredentialTitle(charSequence);
            }
            if (charSequence2 != null) {
                this.mPromptInfo.setDeviceCredentialSubtitle(charSequence2);
            }
            if (charSequence3 != null) {
                this.mPromptInfo.setDeviceCredentialDescription(charSequence3);
            }
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setNegativeButton(java.lang.CharSequence charSequence, java.util.concurrent.Executor executor, android.content.DialogInterface.OnClickListener onClickListener) {
            if (android.text.TextUtils.isEmpty(charSequence)) {
                throw new java.lang.IllegalArgumentException("Text must be set and non-empty");
            }
            if (executor == null) {
                throw new java.lang.IllegalArgumentException("Executor must not be null");
            }
            if (onClickListener == null) {
                throw new java.lang.IllegalArgumentException("Listener must not be null");
            }
            this.mPromptInfo.setNegativeButtonText(charSequence);
            this.mNegativeButtonInfo = new android.hardware.biometrics.BiometricPrompt.ButtonInfo(executor, onClickListener);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setConfirmationRequired(boolean z) {
            this.mPromptInfo.setConfirmationRequested(z);
            return this;
        }

        @java.lang.Deprecated
        public android.hardware.biometrics.BiometricPrompt.Builder setDeviceCredentialAllowed(boolean z) {
            this.mPromptInfo.setDeviceCredentialAllowed(z);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setAllowedAuthenticators(int i) {
            this.mPromptInfo.setAuthenticators(i);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setAllowedSensorIds(java.util.List<java.lang.Integer> list) {
            this.mPromptInfo.setAllowedSensorIds(list);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setAllowBackgroundAuthentication(boolean z) {
            this.mPromptInfo.setAllowBackgroundAuthentication(z);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setAllowBackgroundAuthentication(boolean z, boolean z2) {
            this.mPromptInfo.setAllowBackgroundAuthentication(z);
            this.mPromptInfo.setUseParentProfileForDeviceCredential(z2);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setDisallowBiometricsIfPolicyExists(boolean z) {
            this.mPromptInfo.setDisallowBiometricsIfPolicyExists(z);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setReceiveSystemEvents(boolean z) {
            this.mPromptInfo.setReceiveSystemEvents(z);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setIgnoreEnrollmentState(boolean z) {
            this.mPromptInfo.setIgnoreEnrollmentState(z);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setIsForLegacyFingerprintManager(int i) {
            this.mPromptInfo.setIsForLegacyFingerprintManager(i);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt.Builder setShowEmergencyCallButton(boolean z) {
            this.mPromptInfo.setShowEmergencyCallButton(z);
            return this;
        }

        public android.hardware.biometrics.BiometricPrompt build() {
            android.hardware.biometrics.IAuthService iAuthService;
            java.lang.CharSequence title = this.mPromptInfo.getTitle();
            java.lang.CharSequence negativeButtonText = this.mPromptInfo.getNegativeButtonText();
            boolean isUseDefaultTitle = this.mPromptInfo.isUseDefaultTitle();
            boolean z = this.mPromptInfo.isDeviceCredentialAllowed() || android.hardware.biometrics.BiometricPrompt.isCredentialAllowed(this.mPromptInfo.getAuthenticators());
            if (android.text.TextUtils.isEmpty(title) && !isUseDefaultTitle) {
                throw new java.lang.IllegalArgumentException("Title must be set and non-empty");
            }
            if (android.text.TextUtils.isEmpty(negativeButtonText) && !z) {
                throw new java.lang.IllegalArgumentException("Negative text must be set and non-empty");
            }
            if (!android.text.TextUtils.isEmpty(negativeButtonText) && z) {
                throw new java.lang.IllegalArgumentException("Can't have both negative button behavior and device credential enabled");
            }
            if (this.mService == null) {
                iAuthService = android.hardware.biometrics.IAuthService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.AUTH_SERVICE));
            } else {
                iAuthService = this.mService;
            }
            this.mService = iAuthService;
            return new android.hardware.biometrics.BiometricPrompt(this.mContext, this.mPromptInfo, this.mNegativeButtonInfo, this.mService);
        }
    }

    private class OnAuthenticationCancelListener implements android.os.CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnAuthenticationCancelListener(long j) {
            this.mAuthRequestId = j;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            if (!android.hardware.biometrics.BiometricPrompt.this.mIsPromptShowing) {
                android.util.Log.w(android.hardware.biometrics.BiometricPrompt.TAG, "BP is not showing");
            } else {
                android.util.Log.d(android.hardware.biometrics.BiometricPrompt.TAG, "Cancel BP authentication requested for: " + this.mAuthRequestId);
                android.hardware.biometrics.BiometricPrompt.this.cancelAuthentication(this.mAuthRequestId);
            }
        }
    }

    /* renamed from: android.hardware.biometrics.BiometricPrompt$1, reason: invalid class name */
    class AnonymousClass1 extends android.hardware.biometrics.IBiometricServiceReceiver.Stub {
        AnonymousClass1() {
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onAuthenticationSucceeded(final int i) {
            android.hardware.biometrics.BiometricPrompt.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.biometrics.BiometricPrompt.AnonymousClass1.this.lambda$onAuthenticationSucceeded$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationSucceeded$0(int i) {
            android.hardware.biometrics.BiometricPrompt.this.mAuthenticationCallback.onAuthenticationSucceeded(new android.hardware.biometrics.BiometricPrompt.AuthenticationResult(android.hardware.biometrics.BiometricPrompt.this.mCryptoObject, i));
            android.hardware.biometrics.BiometricPrompt.this.mIsPromptShowing = false;
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onAuthenticationFailed() {
            android.hardware.biometrics.BiometricPrompt.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.biometrics.BiometricPrompt.AnonymousClass1.this.lambda$onAuthenticationFailed$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationFailed$1() {
            android.hardware.biometrics.BiometricPrompt.this.mAuthenticationCallback.onAuthenticationFailed();
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onError(int i, final int i2, int i3) {
            final java.lang.String errorString;
            switch (i) {
                case 2:
                    errorString = android.hardware.fingerprint.FingerprintManager.getErrorString(android.hardware.biometrics.BiometricPrompt.this.mContext, i2, i3);
                    break;
                case 8:
                    errorString = android.hardware.face.FaceManager.getErrorString(android.hardware.biometrics.BiometricPrompt.this.mContext, i2, i3);
                    break;
                default:
                    errorString = null;
                    break;
            }
            if (errorString == null) {
                switch (i2) {
                    case 5:
                        errorString = android.hardware.biometrics.BiometricPrompt.this.mContext.getString(com.android.internal.R.string.biometric_error_canceled);
                        break;
                    case 10:
                        errorString = android.hardware.biometrics.BiometricPrompt.this.mContext.getString(com.android.internal.R.string.biometric_error_user_canceled);
                        break;
                    case 12:
                        errorString = android.hardware.biometrics.BiometricPrompt.this.mContext.getString(com.android.internal.R.string.biometric_error_hw_unavailable);
                        break;
                    case 14:
                        errorString = android.hardware.biometrics.BiometricPrompt.this.mContext.getString(com.android.internal.R.string.biometric_error_device_not_secured);
                        break;
                    default:
                        android.util.Log.e(android.hardware.biometrics.BiometricPrompt.TAG, "Unknown error, modality: " + i + " error: " + i2 + " vendorCode: " + i3);
                        errorString = android.hardware.biometrics.BiometricPrompt.this.mContext.getString(com.android.internal.R.string.biometric_error_generic);
                        break;
                }
            }
            android.hardware.biometrics.BiometricPrompt.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.biometrics.BiometricPrompt.AnonymousClass1.this.lambda$onError$2(i2, errorString);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(int i, java.lang.String str) {
            android.hardware.biometrics.BiometricPrompt.this.mAuthenticationCallback.onAuthenticationError(i, str);
            android.hardware.biometrics.BiometricPrompt.this.mIsPromptShowing = false;
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onAcquired(final int i, final java.lang.String str) {
            android.hardware.biometrics.BiometricPrompt.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.biometrics.BiometricPrompt.AnonymousClass1.this.lambda$onAcquired$3(i, str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAcquired$3(int i, java.lang.String str) {
            android.hardware.biometrics.BiometricPrompt.this.mAuthenticationCallback.onAuthenticationHelp(i, str);
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onDialogDismissed(int i) {
            if (i == 2) {
                android.hardware.biometrics.BiometricPrompt.this.mNegativeButtonInfo.executor.execute(new java.lang.Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.biometrics.BiometricPrompt.AnonymousClass1.this.lambda$onDialogDismissed$4();
                    }
                });
            } else {
                android.hardware.biometrics.BiometricPrompt.this.mIsPromptShowing = false;
                android.util.Log.e(android.hardware.biometrics.BiometricPrompt.TAG, "Unknown reason: " + i);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDialogDismissed$4() {
            android.hardware.biometrics.BiometricPrompt.this.mNegativeButtonInfo.listener.onClick(null, -2);
            android.hardware.biometrics.BiometricPrompt.this.mIsPromptShowing = false;
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onSystemEvent(final int i) {
            android.hardware.biometrics.BiometricPrompt.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.biometrics.BiometricPrompt.AnonymousClass1.this.lambda$onSystemEvent$5(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSystemEvent$5(int i) {
            android.hardware.biometrics.BiometricPrompt.this.mAuthenticationCallback.onSystemEvent(i);
        }
    }

    private BiometricPrompt(android.content.Context context, android.hardware.biometrics.PromptInfo promptInfo, android.hardware.biometrics.BiometricPrompt.ButtonInfo buttonInfo, android.hardware.biometrics.IAuthService iAuthService) {
        this.mToken = new android.os.Binder();
        this.mBiometricServiceReceiver = new android.hardware.biometrics.BiometricPrompt.AnonymousClass1();
        this.mContext = context;
        this.mPromptInfo = promptInfo;
        this.mNegativeButtonInfo = buttonInfo;
        this.mService = iAuthService;
        this.mIsPromptShowing = false;
    }

    public int getLogoRes() {
        return this.mPromptInfo.getLogoRes();
    }

    public android.graphics.Bitmap getLogoBitmap() {
        return this.mPromptInfo.getLogoBitmap();
    }

    public java.lang.String getLogoDescription() {
        return this.mPromptInfo.getLogoDescription();
    }

    public java.lang.CharSequence getTitle() {
        return this.mPromptInfo.getTitle();
    }

    public boolean shouldUseDefaultTitle() {
        return this.mPromptInfo.isUseDefaultTitle();
    }

    public java.lang.CharSequence getSubtitle() {
        return this.mPromptInfo.getSubtitle();
    }

    public boolean shouldUseDefaultSubtitle() {
        return this.mPromptInfo.isUseDefaultSubtitle();
    }

    public java.lang.CharSequence getDescription() {
        return this.mPromptInfo.getDescription();
    }

    public android.hardware.biometrics.PromptContentView getContentView() {
        return this.mPromptInfo.getContentView();
    }

    public java.lang.CharSequence getNegativeButtonText() {
        return this.mPromptInfo.getNegativeButtonText();
    }

    public boolean isConfirmationRequired() {
        return this.mPromptInfo.isConfirmationRequested();
    }

    public int getAllowedAuthenticators() {
        return this.mPromptInfo.getAuthenticators();
    }

    public java.util.List<java.lang.Integer> getAllowedSensorIds() {
        return this.mPromptInfo.getAllowedSensorIds();
    }

    public boolean isAllowBackgroundAuthentication() {
        return this.mPromptInfo.isAllowBackgroundAuthentication();
    }

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

        @java.lang.Deprecated
        public CryptoObject(android.security.identity.IdentityCredential identityCredential) {
            super(identityCredential);
        }

        public CryptoObject(android.security.identity.PresentationSession presentationSession) {
            super(presentationSession);
        }

        public CryptoObject(javax.crypto.KeyAgreement keyAgreement) {
            super(keyAgreement);
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

        public long getOperationHandle() {
            return super.getOpId();
        }
    }

    public static class AuthenticationResult extends android.hardware.biometrics.BiometricAuthenticator.AuthenticationResult {
        public AuthenticationResult(android.hardware.biometrics.BiometricPrompt.CryptoObject cryptoObject, int i) {
            super(cryptoObject, i, null, 0);
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationResult
        public android.hardware.biometrics.BiometricPrompt.CryptoObject getCryptoObject() {
            return (android.hardware.biometrics.BiometricPrompt.CryptoObject) super.getCryptoObject();
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationResult
        public int getAuthenticationType() {
            return super.getAuthenticationType();
        }
    }

    public static abstract class AuthenticationCallback extends android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback {
        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationError(int i, java.lang.CharSequence charSequence) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationHelp(int i, java.lang.CharSequence charSequence) {
        }

        public void onAuthenticationSucceeded(android.hardware.biometrics.BiometricPrompt.AuthenticationResult authenticationResult) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationFailed() {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationAcquired(int i) {
        }

        public void onSystemEvent(int i) {
        }
    }

    public void authenticateUser(android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.hardware.biometrics.BiometricPrompt.AuthenticationCallback authenticationCallback, int i) {
        if (cancellationSignal == null) {
            throw new java.lang.IllegalArgumentException("Must supply a cancellation signal");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must supply an executor");
        }
        if (authenticationCallback == null) {
            throw new java.lang.IllegalArgumentException("Must supply a callback");
        }
        authenticateInternal(0L, cancellationSignal, executor, authenticationCallback, i);
    }

    public long authenticateForOperation(android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.hardware.biometrics.BiometricPrompt.AuthenticationCallback authenticationCallback, long j) {
        if (cancellationSignal == null) {
            throw new java.lang.IllegalArgumentException("Must supply a cancellation signal");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must supply an executor");
        }
        if (authenticationCallback == null) {
            throw new java.lang.IllegalArgumentException("Must supply a callback");
        }
        return authenticateInternal(j, cancellationSignal, executor, authenticationCallback, this.mContext.getUserId());
    }

    public void authenticate(android.hardware.biometrics.BiometricPrompt.CryptoObject cryptoObject, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.hardware.biometrics.BiometricPrompt.AuthenticationCallback authenticationCallback) {
        com.android.internal.util.FrameworkStatsLog.write(353, true, this.mPromptInfo.isConfirmationRequested(), this.mPromptInfo.isDeviceCredentialAllowed(), this.mPromptInfo.getAuthenticators() != 0, this.mPromptInfo.getAuthenticators());
        if (cryptoObject == null) {
            throw new java.lang.IllegalArgumentException("Must supply a crypto object");
        }
        if (cancellationSignal == null) {
            throw new java.lang.IllegalArgumentException("Must supply a cancellation signal");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must supply an executor");
        }
        if (authenticationCallback == null) {
            throw new java.lang.IllegalArgumentException("Must supply a callback");
        }
        int authenticators = this.mPromptInfo.getAuthenticators();
        if (authenticators == 0) {
            authenticators = 15;
        }
        if ((authenticators & 255 & (-16)) != 0) {
            throw new java.lang.IllegalArgumentException("Only Strong biometrics supported with crypto");
        }
        authenticateInternal(cryptoObject, cancellationSignal, executor, authenticationCallback, this.mContext.getUserId());
    }

    public void authenticate(android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.hardware.biometrics.BiometricPrompt.AuthenticationCallback authenticationCallback) {
        com.android.internal.util.FrameworkStatsLog.write(353, false, this.mPromptInfo.isConfirmationRequested(), this.mPromptInfo.isDeviceCredentialAllowed(), this.mPromptInfo.getAuthenticators() != 0, this.mPromptInfo.getAuthenticators());
        if (cancellationSignal == null) {
            throw new java.lang.IllegalArgumentException("Must supply a cancellation signal");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Must supply an executor");
        }
        if (authenticationCallback == null) {
            throw new java.lang.IllegalArgumentException("Must supply a callback");
        }
        authenticateInternal((android.hardware.biometrics.BiometricPrompt.CryptoObject) null, cancellationSignal, executor, authenticationCallback, this.mContext.getUserId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAuthentication(long j) {
        if (this.mService != null) {
            try {
                this.mService.cancelAuthentication(this.mToken, this.mContext.getPackageName(), j);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Unable to cancel authentication", e);
            }
        }
    }

    private void authenticateInternal(android.hardware.biometrics.BiometricPrompt.CryptoObject cryptoObject, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.hardware.biometrics.BiometricPrompt.AuthenticationCallback authenticationCallback, int i) {
        this.mCryptoObject = cryptoObject;
        authenticateInternal(cryptoObject != null ? cryptoObject.getOpId() : 0L, cancellationSignal, executor, authenticationCallback, i);
    }

    private long authenticateInternal(long j, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, final android.hardware.biometrics.BiometricPrompt.AuthenticationCallback authenticationCallback, int i) {
        android.hardware.biometrics.PromptInfo promptInfo;
        if (this.mCryptoObject != null && this.mCryptoObject.getOpId() != j) {
            android.util.Log.w(TAG, "CryptoObject operation ID does not match argument; setting field to null");
            this.mCryptoObject = null;
        }
        try {
            if (!cancellationSignal.isCanceled()) {
                this.mExecutor = executor;
                this.mAuthenticationCallback = authenticationCallback;
                if (this.mIsPromptShowing) {
                    final java.lang.String string = this.mContext.getString(com.android.internal.R.string.biometric_error_canceled);
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.biometrics.BiometricPrompt.this.lambda$authenticateInternal$0(string);
                        }
                    });
                    return -1L;
                }
                if (j != 0) {
                    android.os.Parcel obtain = android.os.Parcel.obtain();
                    this.mPromptInfo.writeToParcel(obtain, 0);
                    obtain.setDataPosition(0);
                    android.hardware.biometrics.PromptInfo promptInfo2 = new android.hardware.biometrics.PromptInfo(obtain);
                    if (promptInfo2.getAuthenticators() == 0) {
                        promptInfo2.setAuthenticators(15);
                    }
                    promptInfo = promptInfo2;
                } else {
                    promptInfo = this.mPromptInfo;
                }
                long authenticate = this.mService.authenticate(this.mToken, j, i, this.mBiometricServiceReceiver, this.mContext.getPackageName(), promptInfo);
                cancellationSignal.setOnCancelListener(new android.hardware.biometrics.BiometricPrompt.OnAuthenticationCancelListener(authenticate));
                this.mIsPromptShowing = true;
                return authenticate;
            }
            android.util.Log.w(TAG, "Authentication already canceled");
            return -1L;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Remote exception while authenticating", e);
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.biometrics.BiometricPrompt$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.biometrics.BiometricPrompt.this.lambda$authenticateInternal$1(authenticationCallback);
                }
            });
            return -1L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$authenticateInternal$0(java.lang.String str) {
        this.mAuthenticationCallback.onAuthenticationError(5, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$authenticateInternal$1(android.hardware.biometrics.BiometricPrompt.AuthenticationCallback authenticationCallback) {
        authenticationCallback.onAuthenticationError(1, this.mContext.getString(com.android.internal.R.string.biometric_error_hw_unavailable));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isCredentialAllowed(int i) {
        return (i & 32768) != 0;
    }
}
