package com.android.server.biometrics;

/* loaded from: classes.dex */
public class AuthService extends com.android.server.SystemService {
    private static final int DEFAULT_HIDL_DISABLED = 0;
    private static final java.lang.String SETTING_HIDL_DISABLED = "com.android.server.biometrics.AuthService.hidlDisabled";
    private static final java.lang.String SYSPROP_API_LEVEL = "ro.board.api_level";
    private static final java.lang.String SYSPROP_FIRST_API_LEVEL = "ro.board.first_api_level";
    private static final java.lang.String TAG = "AuthService";
    private android.hardware.biometrics.IBiometricService mBiometricService;

    @com.android.internal.annotations.VisibleForTesting
    final android.hardware.biometrics.IAuthService.Stub mImpl;
    private final com.android.server.biometrics.AuthService.Injector mInjector;

    @com.android.internal.annotations.VisibleForTesting
    public static class Injector {
        @com.android.internal.annotations.VisibleForTesting
        public android.hardware.biometrics.IBiometricService getBiometricService() {
            return android.hardware.biometrics.IBiometricService.Stub.asInterface(android.os.ServiceManager.getService("biometric"));
        }

        @com.android.internal.annotations.VisibleForTesting
        public void publishBinderService(com.android.server.biometrics.AuthService authService, android.hardware.biometrics.IAuthService.Stub stub) {
            authService.publishBinderService("auth", stub);
        }

        @com.android.internal.annotations.VisibleForTesting
        public java.lang.String[] getConfiguration(android.content.Context context) {
            return context.getResources().getStringArray(android.R.array.config_biometric_sensors);
        }

        @com.android.internal.annotations.VisibleForTesting
        public java.lang.String[] getFingerprintConfiguration(android.content.Context context) {
            return getConfiguration(context);
        }

        @com.android.internal.annotations.VisibleForTesting
        public java.lang.String[] getFaceConfiguration(android.content.Context context) {
            return getConfiguration(context);
        }

        @com.android.internal.annotations.VisibleForTesting
        public java.lang.String[] getIrisConfiguration(android.content.Context context) {
            return getConfiguration(context);
        }

        @com.android.internal.annotations.VisibleForTesting
        public android.hardware.fingerprint.IFingerprintService getFingerprintService() {
            return android.hardware.fingerprint.IFingerprintService.Stub.asInterface(android.os.ServiceManager.getService("fingerprint"));
        }

        @com.android.internal.annotations.VisibleForTesting
        public android.hardware.face.IFaceService getFaceService() {
            return android.hardware.face.IFaceService.Stub.asInterface(android.os.ServiceManager.getService("face"));
        }

        @com.android.internal.annotations.VisibleForTesting
        public android.hardware.iris.IIrisService getIrisService() {
            return android.hardware.iris.IIrisService.Stub.asInterface(android.os.ServiceManager.getService("iris"));
        }

        @com.android.internal.annotations.VisibleForTesting
        public android.app.AppOpsManager getAppOps(android.content.Context context) {
            return (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        }

        @com.android.internal.annotations.VisibleForTesting
        public boolean isHidlDisabled(android.content.Context context) {
            return (android.os.Build.IS_ENG || android.os.Build.IS_USERDEBUG) && android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), com.android.server.biometrics.AuthService.SETTING_HIDL_DISABLED, 0, -2) == 1;
        }

        @com.android.internal.annotations.VisibleForTesting
        public java.lang.String[] getFingerprintAidlInstances() {
            return android.os.ServiceManager.getDeclaredInstances(android.hardware.biometrics.fingerprint.IFingerprint.DESCRIPTOR);
        }

        @com.android.internal.annotations.VisibleForTesting
        public java.lang.String[] getFaceAidlInstances() {
            return android.os.ServiceManager.getDeclaredInstances(android.hardware.biometrics.face.IFace.DESCRIPTOR);
        }

        public com.android.server.biometrics.BiometricHandlerProvider getBiometricHandlerProvider() {
            return com.android.server.biometrics.BiometricHandlerProvider.getInstance();
        }
    }

    private final class AuthServiceImpl extends android.hardware.biometrics.IAuthService.Stub {
        private AuthServiceImpl() {
        }

        @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
        public android.hardware.biometrics.ITestSession createTestSession(int i, @android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
            super.createTestSession_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.biometrics.AuthService.this.mInjector.getBiometricService().createTestSession(i, iTestSessionCallback, str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
        public java.util.List<android.hardware.biometrics.SensorPropertiesInternal> getSensorProperties(java.lang.String str) throws android.os.RemoteException {
            super.getSensorProperties_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.biometrics.AuthService.this.mInjector.getBiometricService().getSensorProperties(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
        public java.lang.String getUiPackage() {
            super.getUiPackage_enforcePermission();
            return com.android.server.biometrics.AuthService.this.getContext().getResources().getString(android.R.string.config_bandwidthEstimateSource);
        }

        public long authenticate(android.os.IBinder iBinder, long j, int i, android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver, java.lang.String str, android.hardware.biometrics.PromptInfo promptInfo) throws android.os.RemoteException {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            if (i == callingUserId) {
                com.android.server.biometrics.AuthService.this.checkPermission();
            } else {
                android.util.Slog.w(com.android.server.biometrics.AuthService.TAG, "User " + callingUserId + " is requesting authentication of userid: " + i);
                com.android.server.biometrics.AuthService.this.checkInternalPermission();
            }
            if (!com.android.server.biometrics.AuthService.this.checkAppOps(callingUid, str, "authenticate()")) {
                authenticateFastFail("Denied by app ops: " + str, iBiometricServiceReceiver);
                return -1L;
            }
            if (iBinder == null || iBiometricServiceReceiver == null || str == null || promptInfo == null) {
                authenticateFastFail("Unable to authenticate, one or more null arguments", iBiometricServiceReceiver);
                return -1L;
            }
            if (!com.android.server.biometrics.Utils.isForeground(callingUid, callingPid)) {
                authenticateFastFail("Caller is not foreground: " + str, iBiometricServiceReceiver);
                return -1L;
            }
            if (promptInfo.containsTestConfigurations() && com.android.server.biometrics.AuthService.this.getContext().checkCallingOrSelfPermission("android.permission.TEST_BIOMETRIC") != 0) {
                com.android.server.biometrics.AuthService.this.checkInternalPermission();
            }
            if (promptInfo.containsPrivateApiConfigurations()) {
                com.android.server.biometrics.AuthService.this.checkInternalPermission();
            }
            if (promptInfo.containsSetLogoApiConfigurations()) {
                com.android.server.biometrics.AuthService.this.checkManageBiometricPermission();
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.companion.virtual.VirtualDeviceManagerInternal virtualDeviceManagerInternal = (com.android.server.companion.virtual.VirtualDeviceManagerInternal) com.android.server.biometrics.AuthService.this.getLocalService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class);
                if (virtualDeviceManagerInternal != null) {
                    virtualDeviceManagerInternal.onAuthenticationPrompt(callingUid);
                }
                long authenticate = com.android.server.biometrics.AuthService.this.mBiometricService.authenticate(iBinder, j, i, iBiometricServiceReceiver, str, promptInfo);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return authenticate;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        private void authenticateFastFail(java.lang.String str, android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver) {
            android.util.Slog.e(com.android.server.biometrics.AuthService.TAG, "authenticateFastFail: " + str);
            try {
                iBiometricServiceReceiver.onError(0, 5, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.biometrics.AuthService.TAG, "authenticateFastFail failed to notify caller", e);
            }
        }

        public void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
            com.android.server.biometrics.AuthService.this.checkPermission();
            if (iBinder == null || str == null) {
                android.util.Slog.e(com.android.server.biometrics.AuthService.TAG, "Unable to cancel authentication, one or more null arguments");
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.biometrics.AuthService.this.mBiometricService.cancelAuthentication(iBinder, str, j);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int canAuthenticate(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            if (i != callingUserId) {
                com.android.server.biometrics.AuthService.this.checkInternalPermission();
            } else {
                com.android.server.biometrics.AuthService.this.checkPermission();
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                int canAuthenticate = com.android.server.biometrics.AuthService.this.mBiometricService.canAuthenticate(str, i, callingUserId, i2);
                android.util.Slog.d(com.android.server.biometrics.AuthService.TAG, "canAuthenticate, userId: " + i + ", callingUserId: " + callingUserId + ", authenticators: " + i2 + ", result: " + canAuthenticate);
                return canAuthenticate;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public long getLastAuthenticationTime(int i, int i2) throws android.os.RemoteException {
            if (i != android.os.UserHandle.getCallingUserId()) {
                com.android.server.biometrics.AuthService.this.checkInternalPermission();
            } else {
                com.android.server.biometrics.AuthService.this.checkPermission();
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (!android.hardware.biometrics.Flags.lastAuthenticationTime()) {
                    throw new java.lang.UnsupportedOperationException();
                }
                return com.android.server.biometrics.AuthService.this.mBiometricService.getLastAuthenticationTime(i, i2);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean hasEnrolledBiometrics(int i, java.lang.String str) throws android.os.RemoteException {
            com.android.server.biometrics.AuthService.this.checkInternalPermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.biometrics.AuthService.this.mBiometricService.hasEnrolledBiometrics(i, str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerEnabledOnKeyguardCallback(android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws android.os.RemoteException {
            com.android.server.biometrics.AuthService.this.checkInternalPermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.biometrics.AuthService.this.mBiometricService.registerEnabledOnKeyguardCallback(iBiometricEnabledOnKeyguardCallback);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException {
            com.android.server.biometrics.AuthService.this.checkInternalPermission();
            android.hardware.fingerprint.IFingerprintService fingerprintService = com.android.server.biometrics.AuthService.this.mInjector.getFingerprintService();
            if (fingerprintService != null) {
                fingerprintService.registerAuthenticationStateListener(authenticationStateListener);
            }
            android.hardware.face.IFaceService faceService = com.android.server.biometrics.AuthService.this.mInjector.getFaceService();
            if (faceService != null) {
                faceService.registerAuthenticationStateListener(authenticationStateListener);
            }
        }

        public void unregisterAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) throws android.os.RemoteException {
            com.android.server.biometrics.AuthService.this.checkInternalPermission();
            android.hardware.fingerprint.IFingerprintService fingerprintService = com.android.server.biometrics.AuthService.this.mInjector.getFingerprintService();
            if (fingerprintService != null) {
                fingerprintService.unregisterAuthenticationStateListener(authenticationStateListener);
            }
            android.hardware.face.IFaceService faceService = com.android.server.biometrics.AuthService.this.mInjector.getFaceService();
            if (faceService != null) {
                faceService.unregisterAuthenticationStateListener(authenticationStateListener);
            }
        }

        public void invalidateAuthenticatorIds(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException {
            com.android.server.biometrics.AuthService.this.checkInternalPermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.biometrics.AuthService.this.mBiometricService.invalidateAuthenticatorIds(i, i2, iInvalidationCallback);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public long[] getAuthenticatorIds(int i) throws android.os.RemoteException {
            if (i != android.os.UserHandle.getCallingUserId()) {
                com.android.server.biometrics.AuthService.this.getContext().enforceCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL", "Must have android.permission.USE_BIOMETRIC_INTERNAL permission.");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.biometrics.AuthService.this.mBiometricService.getAuthenticatorIds(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void resetLockoutTimeBound(android.os.IBinder iBinder, java.lang.String str, int i, int i2, byte[] bArr) throws android.os.RemoteException {
            com.android.server.biometrics.AuthService.this.checkInternalPermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.biometrics.AuthService.this.mBiometricService.resetLockoutTimeBound(iBinder, str, i, i2, bArr);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void resetLockout(int i, byte[] bArr) throws android.os.RemoteException {
            com.android.server.biometrics.AuthService.this.checkInternalPermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.biometrics.AuthService.this.mBiometricService.resetLockout(i, bArr);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.lang.CharSequence getButtonLabel(int i, java.lang.String str, int i2) throws android.os.RemoteException {
            java.lang.String str2;
            int callingUserId = android.os.UserHandle.getCallingUserId();
            if (i != callingUserId) {
                com.android.server.biometrics.AuthService.this.checkInternalPermission();
            } else {
                com.android.server.biometrics.AuthService.this.checkPermission();
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                switch (com.android.server.biometrics.AuthService.getCredentialBackupModality(com.android.server.biometrics.AuthService.this.mBiometricService.getCurrentModality(str, i, callingUserId, i2))) {
                    case 0:
                        str2 = null;
                        break;
                    case 1:
                        str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.roamingText4);
                        break;
                    case 2:
                        str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.fast_scroll_numeric_alphabet);
                        break;
                    case 8:
                        str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.face_acquired_pan_too_extreme);
                        break;
                    default:
                        str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.battery_saver_charged_notification_summary);
                        break;
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return str2;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public java.lang.CharSequence getPromptMessage(int i, java.lang.String str, int i2) throws android.os.RemoteException {
            java.lang.String str2;
            int callingUserId = android.os.UserHandle.getCallingUserId();
            if (i != callingUserId) {
                com.android.server.biometrics.AuthService.this.checkInternalPermission();
            } else {
                com.android.server.biometrics.AuthService.this.checkPermission();
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                int currentModality = com.android.server.biometrics.AuthService.this.mBiometricService.getCurrentModality(str, i, callingUserId, i2);
                boolean isCredentialRequested = com.android.server.biometrics.Utils.isCredentialRequested(i2);
                switch (com.android.server.biometrics.AuthService.getCredentialBackupModality(currentModality)) {
                    case 0:
                        str2 = null;
                        break;
                    case 1:
                        str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.roamingText5);
                        break;
                    case 2:
                        if (isCredentialRequested) {
                            str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.fingerprint_authenticated);
                            break;
                        } else {
                            str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.fcError);
                            break;
                        }
                    case 8:
                        if (isCredentialRequested) {
                            str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.face_authenticated_no_confirmation_required);
                            break;
                        } else {
                            str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.face_acquired_recalibrate_alt);
                            break;
                        }
                    default:
                        if (isCredentialRequested) {
                            str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.biometric_error_canceled);
                            break;
                        } else {
                            str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.battery_saver_description);
                            break;
                        }
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return str2;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public java.lang.CharSequence getSettingName(int i, java.lang.String str, int i2) throws android.os.RemoteException {
            java.lang.String str2;
            if (i != android.os.UserHandle.getCallingUserId()) {
                com.android.server.biometrics.AuthService.this.checkInternalPermission();
            } else {
                com.android.server.biometrics.AuthService.this.checkPermission();
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                int supportedModalities = com.android.server.biometrics.AuthService.this.mBiometricService.getSupportedModalities(i2);
                switch (supportedModalities) {
                    case 0:
                        str2 = null;
                        break;
                    case 1:
                        str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.roamingText4);
                        break;
                    case 2:
                        str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.fast_scroll_numeric_alphabet);
                        break;
                    case 3:
                    case 5:
                    case 6:
                    case 7:
                    default:
                        if ((supportedModalities & 1) == 0) {
                            str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.battery_saver_charged_notification_summary);
                            break;
                        } else {
                            int i3 = supportedModalities & (-2);
                            if (i3 == 2) {
                                str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.fingerprint_app_setting_name);
                                break;
                            } else if (i3 == 8) {
                                str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.face_authenticated_confirmation_required);
                                break;
                            } else {
                                str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.biometric_dialog_default_title);
                                break;
                            }
                        }
                    case 4:
                        str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.battery_saver_charged_notification_summary);
                        break;
                    case 8:
                        str2 = com.android.server.biometrics.AuthService.this.getContext().getString(android.R.string.face_acquired_pan_too_extreme);
                        break;
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return str2;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public AuthService(android.content.Context context) {
        this(context, new com.android.server.biometrics.AuthService.Injector());
    }

    public AuthService(android.content.Context context, com.android.server.biometrics.AuthService.Injector injector) {
        super(context);
        this.mInjector = injector;
        this.mImpl = new com.android.server.biometrics.AuthService.AuthServiceImpl();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        com.android.server.biometrics.SensorConfig[] sensorConfigArr;
        this.mBiometricService = this.mInjector.getBiometricService();
        if (!this.mInjector.isHidlDisabled(getContext())) {
            int i = android.os.SystemProperties.getInt(SYSPROP_API_LEVEL, android.os.SystemProperties.getInt(SYSPROP_FIRST_API_LEVEL, 0));
            java.lang.String[] configuration = this.mInjector.getConfiguration(getContext());
            if (configuration.length == 0 && i == 30) {
                android.util.Slog.w(TAG, "Found R vendor partition without config_biometric_sensors");
                configuration = generateRSdkCompatibleConfiguration();
            }
            sensorConfigArr = new com.android.server.biometrics.SensorConfig[configuration.length];
            for (int i2 = 0; i2 < configuration.length; i2++) {
                sensorConfigArr[i2] = new com.android.server.biometrics.SensorConfig(configuration[i2]);
            }
        } else {
            sensorConfigArr = null;
        }
        com.android.server.biometrics.Flags.deHidl();
        registerAuthenticators(sensorConfigArr);
        this.mInjector.publishBinderService(this, this.mImpl);
    }

    private void registerAuthenticators() {
        com.android.server.biometrics.BiometricHandlerProvider biometricHandlerProvider = this.mInjector.getBiometricHandlerProvider();
        biometricHandlerProvider.getFingerprintHandler().post(new java.lang.Runnable() { // from class: com.android.server.biometrics.AuthService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.AuthService.this.lambda$registerAuthenticators$0();
            }
        });
        biometricHandlerProvider.getFaceHandler().post(new java.lang.Runnable() { // from class: com.android.server.biometrics.AuthService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.AuthService.this.lambda$registerAuthenticators$1();
            }
        });
        registerIrisSensors(this.mInjector.getIrisConfiguration(getContext()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerAuthenticators$0() {
        registerFingerprintSensors(this.mInjector.getFingerprintAidlInstances(), this.mInjector.getFingerprintConfiguration(getContext()), getContext(), this.mInjector.getFingerprintService());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerAuthenticators$1() {
        registerFaceSensors(this.mInjector.getFaceAidlInstances(), this.mInjector.getFaceConfiguration(getContext()), getContext(), this.mInjector.getFaceService());
    }

    private void registerIrisSensors(java.lang.String[] strArr) {
        com.android.server.biometrics.SensorConfig[] sensorConfigArr;
        if (!this.mInjector.isHidlDisabled(getContext())) {
            int i = android.os.SystemProperties.getInt(SYSPROP_API_LEVEL, android.os.SystemProperties.getInt(SYSPROP_FIRST_API_LEVEL, 0));
            if (strArr.length == 0 && i == 30) {
                android.util.Slog.w(TAG, "Found R vendor partition without config_biometric_sensors");
                strArr = generateRSdkCompatibleConfiguration();
            }
            sensorConfigArr = new com.android.server.biometrics.SensorConfig[strArr.length];
            for (int i2 = 0; i2 < strArr.length; i2++) {
                sensorConfigArr[i2] = new com.android.server.biometrics.SensorConfig(strArr[i2]);
            }
        } else {
            sensorConfigArr = null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (sensorConfigArr != null) {
            for (com.android.server.biometrics.SensorConfig sensorConfig : sensorConfigArr) {
                switch (sensorConfig.modality) {
                    case 4:
                        arrayList.add(getHidlIrisSensorProps(sensorConfig.id, sensorConfig.strength));
                        break;
                    default:
                        android.util.Slog.e(TAG, "Unknown modality: " + sensorConfig.modality);
                        break;
                }
            }
        }
        android.hardware.iris.IIrisService irisService = this.mInjector.getIrisService();
        if (irisService != null) {
            try {
                irisService.registerAuthenticators(arrayList);
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "RemoteException when registering iris authenticators", e);
                return;
            }
        }
        if (arrayList.size() > 0) {
            android.util.Slog.e(TAG, "HIDL iris configuration exists, but IrisService is null.");
        }
    }

    private static void registerFaceSensors(java.lang.String[] strArr, java.lang.String[] strArr2, android.content.Context context, android.hardware.face.IFaceService iFaceService) {
        android.hardware.face.FaceSensorConfigurations faceSensorConfigurations = new android.hardware.face.FaceSensorConfigurations(strArr2 != null && strArr2.length > 0);
        if (strArr2 != null && strArr2.length > 0) {
            faceSensorConfigurations.addHidlConfigs(strArr2, context);
        }
        if (strArr != null && strArr.length > 0) {
            faceSensorConfigurations.addAidlConfigs(strArr, new java.util.function.Function() { // from class: com.android.server.biometrics.AuthService$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    android.hardware.biometrics.face.IFace lambda$registerFaceSensors$2;
                    lambda$registerFaceSensors$2 = com.android.server.biometrics.AuthService.lambda$registerFaceSensors$2((java.lang.String) obj);
                    return lambda$registerFaceSensors$2;
                }
            });
        }
        if (iFaceService != null) {
            try {
                iFaceService.registerAuthenticatorsLegacy(faceSensorConfigurations);
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "RemoteException when registering face authenticators", e);
                return;
            }
        }
        if (faceSensorConfigurations.hasSensorConfigurations()) {
            android.util.Slog.e(TAG, "Face configuration exists, but FaceService is null.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.biometrics.face.IFace lambda$registerFaceSensors$2(java.lang.String str) {
        return android.hardware.biometrics.face.IFace.Stub.asInterface(android.os.Binder.allowBlocking(android.os.ServiceManager.waitForDeclaredService(str)));
    }

    private static void registerFingerprintSensors(java.lang.String[] strArr, java.lang.String[] strArr2, android.content.Context context, android.hardware.fingerprint.IFingerprintService iFingerprintService) {
        android.hardware.fingerprint.FingerprintSensorConfigurations fingerprintSensorConfigurations = new android.hardware.fingerprint.FingerprintSensorConfigurations(strArr2 == null || strArr2.length <= 0);
        if (strArr2 != null && strArr2.length > 0) {
            fingerprintSensorConfigurations.addHidlSensors(strArr2, context);
        }
        if (strArr != null && strArr.length > 0) {
            fingerprintSensorConfigurations.addAidlSensors(strArr, new java.util.function.Function() { // from class: com.android.server.biometrics.AuthService$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    android.hardware.biometrics.fingerprint.IFingerprint lambda$registerFingerprintSensors$3;
                    lambda$registerFingerprintSensors$3 = com.android.server.biometrics.AuthService.lambda$registerFingerprintSensors$3((java.lang.String) obj);
                    return lambda$registerFingerprintSensors$3;
                }
            });
        }
        if (iFingerprintService != null) {
            try {
                iFingerprintService.registerAuthenticatorsLegacy(fingerprintSensorConfigurations);
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "RemoteException when registering fingerprint authenticators", e);
                return;
            }
        }
        if (fingerprintSensorConfigurations.hasSensorConfigurations()) {
            android.util.Slog.e(TAG, "Fingerprint configuration exists, but FingerprintService is null.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.biometrics.fingerprint.IFingerprint lambda$registerFingerprintSensors$3(java.lang.String str) {
        return android.hardware.biometrics.fingerprint.IFingerprint.Stub.asInterface(android.os.Binder.allowBlocking(android.os.ServiceManager.waitForDeclaredService(str)));
    }

    @android.annotation.NonNull
    private java.lang.String[] generateRSdkCompatibleConfiguration() {
        android.content.pm.PackageManager packageManager = getContext().getPackageManager();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (packageManager.hasSystemFeature("android.hardware.fingerprint")) {
            arrayList.add(java.lang.String.valueOf(2));
        }
        if (packageManager.hasSystemFeature("android.hardware.biometrics.face")) {
            arrayList.add(java.lang.String.valueOf(8));
        }
        java.lang.String valueOf = java.lang.String.valueOf(4095);
        java.lang.String[] strArr = new java.lang.String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            strArr[i] = java.lang.String.join(":", java.lang.String.valueOf(i), (java.lang.String) arrayList.get(i), valueOf);
        }
        android.util.Slog.d(TAG, "Generated config_biometric_sensors: " + java.util.Arrays.toString(strArr));
        return strArr;
    }

    private void registerAuthenticators(@android.annotation.Nullable com.android.server.biometrics.SensorConfig[] sensorConfigArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        if (sensorConfigArr != null) {
            for (com.android.server.biometrics.SensorConfig sensorConfig : sensorConfigArr) {
                android.util.Slog.d(TAG, "Registering HIDL ID: " + sensorConfig.id + " Modality: " + sensorConfig.modality + " Strength: " + sensorConfig.strength);
                switch (sensorConfig.modality) {
                    case 2:
                        arrayList.add(getHidlFingerprintSensorProps(sensorConfig.id, sensorConfig.strength));
                        break;
                    case 4:
                        arrayList3.add(getHidlIrisSensorProps(sensorConfig.id, sensorConfig.strength));
                        break;
                    case 8:
                        arrayList2.add(getHidlFaceSensorProps(sensorConfig.id, sensorConfig.strength));
                        break;
                    default:
                        android.util.Slog.e(TAG, "Unknown modality: " + sensorConfig.modality);
                        break;
                }
            }
        }
        android.hardware.fingerprint.IFingerprintService fingerprintService = this.mInjector.getFingerprintService();
        if (fingerprintService != null) {
            try {
                fingerprintService.registerAuthenticators(arrayList);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "RemoteException when registering fingerprint authenticators", e);
            }
        } else if (arrayList.size() > 0) {
            android.util.Slog.e(TAG, "HIDL fingerprint configuration exists, but FingerprintService is null.");
        }
        android.hardware.face.IFaceService faceService = this.mInjector.getFaceService();
        if (faceService != null) {
            try {
                faceService.registerAuthenticators(arrayList2);
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(TAG, "RemoteException when registering face authenticators", e2);
            }
        } else if (arrayList2.size() > 0) {
            android.util.Slog.e(TAG, "HIDL face configuration exists, but FaceService is null.");
        }
        android.hardware.iris.IIrisService irisService = this.mInjector.getIrisService();
        if (irisService != null) {
            try {
                irisService.registerAuthenticators(arrayList3);
                return;
            } catch (android.os.RemoteException e3) {
                android.util.Slog.e(TAG, "RemoteException when registering iris authenticators", e3);
                return;
            }
        }
        if (arrayList3.size() > 0) {
            android.util.Slog.e(TAG, "HIDL iris configuration exists, but IrisService is null.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkInternalPermission() {
        getContext().enforceCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL", "Must have USE_BIOMETRIC_INTERNAL permission");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkManageBiometricPermission() {
        getContext().enforceCallingOrSelfPermission("android.permission.SET_BIOMETRIC_DIALOG_LOGO", "Must have SET_BIOMETRIC_DIALOG_LOGO permission");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkPermission() {
        if (getContext().checkCallingOrSelfPermission("android.permission.USE_FINGERPRINT") != 0) {
            getContext().enforceCallingOrSelfPermission("android.permission.USE_BIOMETRIC", "Must have USE_BIOMETRIC permission");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkAppOps(int i, java.lang.String str, java.lang.String str2) {
        return this.mInjector.getAppOps(getContext()).noteOp(78, i, str, (java.lang.String) null, str2) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getCredentialBackupModality(int i) {
        return i == 1 ? i : i & (-2);
    }

    private android.hardware.fingerprint.FingerprintSensorPropertiesInternal getHidlFingerprintSensorProps(int i, int i2) {
        int i3;
        int[] intArray = getContext().getResources().getIntArray(android.R.array.config_tvExternalInputLoggingDeviceOnScreenDisplayNames);
        java.util.List<android.hardware.biometrics.SensorLocationInternal> workaroundSensorProps = com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.getWorkaroundSensorProps(getContext());
        boolean z = !com.android.internal.util.ArrayUtils.isEmpty(intArray);
        boolean z2 = getContext().getResources().getBoolean(android.R.bool.config_isDisplayHingeAlwaysSeparating);
        if (z) {
            i3 = 3;
        } else if (z2) {
            i3 = 4;
        } else {
            i3 = 1;
        }
        int integer = getContext().getResources().getInteger(android.R.integer.config_externalDisplayPeakWidth);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (z && intArray.length == 3) {
            return new android.hardware.fingerprint.FingerprintSensorPropertiesInternal(i, com.android.server.biometrics.Utils.authenticatorStrengthToPropertyStrength(i2), integer, arrayList, i3, true, false, java.util.List.of(new android.hardware.biometrics.SensorLocationInternal("", intArray[0], intArray[1], intArray[2])));
        }
        if (!workaroundSensorProps.isEmpty()) {
            return new android.hardware.fingerprint.FingerprintSensorPropertiesInternal(i, com.android.server.biometrics.Utils.authenticatorStrengthToPropertyStrength(i2), integer, arrayList, i3, false, false, workaroundSensorProps);
        }
        return new android.hardware.fingerprint.FingerprintSensorPropertiesInternal(i, com.android.server.biometrics.Utils.authenticatorStrengthToPropertyStrength(i2), integer, arrayList, i3, false);
    }

    private android.hardware.face.FaceSensorPropertiesInternal getHidlFaceSensorProps(int i, int i2) {
        boolean z = getContext().getResources().getBoolean(android.R.bool.config_evenDimmerEnabled);
        return new android.hardware.face.FaceSensorPropertiesInternal(i, com.android.server.biometrics.Utils.authenticatorStrengthToPropertyStrength(i2), getContext().getResources().getInteger(android.R.integer.config_externalDisplayPeakRefreshRate), new java.util.ArrayList(), 0, false, z, true);
    }

    private android.hardware.biometrics.SensorPropertiesInternal getHidlIrisSensorProps(int i, int i2) {
        return new android.hardware.biometrics.SensorPropertiesInternal(i, com.android.server.biometrics.Utils.authenticatorStrengthToPropertyStrength(i2), 1, new java.util.ArrayList(), false, false);
    }
}
