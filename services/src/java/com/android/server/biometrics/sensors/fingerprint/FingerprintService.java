package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public class FingerprintService extends com.android.server.SystemService {
    protected static final java.lang.String TAG = "FingerprintService";

    @android.annotation.NonNull
    private final java.util.function.Supplier<java.lang.String[]> mAidlInstanceNameSupplier;
    private final android.app.AppOpsManager mAppOps;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthenticationStateListeners mAuthenticationStateListeners;

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.BiometricContext mBiometricContext;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.BiometricStateCallback<com.android.server.biometrics.sensors.fingerprint.ServiceProvider, android.hardware.fingerprint.FingerprintSensorPropertiesInternal> mBiometricStateCallback;

    @android.annotation.NonNull
    private final java.util.function.Function<java.lang.String, com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider> mFingerprintProvider;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.FingerprintService.FingerprintProviderFunction mFingerprintProviderFunction;
    private final com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher mGestureAvailabilityDispatcher;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;
    private final com.android.internal.widget.LockPatternUtils mLockPatternUtils;
    private final com.android.server.biometrics.sensors.LockoutResetDispatcher mLockoutResetDispatcher;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.FingerprintServiceRegistry mRegistry;

    @com.android.internal.annotations.VisibleForTesting
    final android.hardware.fingerprint.IFingerprintService.Stub mServiceWrapper;

    interface FingerprintProviderFunction {
        com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider getFingerprintProvider(android.util.Pair<java.lang.String, android.hardware.biometrics.fingerprint.SensorProps[]> pair, boolean z);
    }

    /* renamed from: com.android.server.biometrics.sensors.fingerprint.FingerprintService$1, reason: invalid class name */
    class AnonymousClass1 extends android.hardware.fingerprint.IFingerprintService.Stub {
        AnonymousClass1() {
        }

        @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
        public android.hardware.biometrics.ITestSession createTestSession(int i, @android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull java.lang.String str) {
            super.createTestSession_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for createTestSession, sensorId: " + i);
                return null;
            }
            return providerForSensor.createTestSession(i, iTestSessionCallback, str);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public byte[] dumpSensorServiceStateProto(int i, boolean z) {
            super.dumpSensorServiceStateProto_enforcePermission();
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor != null) {
                providerForSensor.dumpProtoState(i, protoOutputStream, z);
            }
            protoOutputStream.flush();
            return protoOutputStream.getBytes();
        }

        public java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> getSensorPropertiesInternal(@android.annotation.NonNull java.lang.String str) {
            if (com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getContext().checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL") != 0) {
                com.android.server.biometrics.Utils.checkPermission(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getContext(), "android.permission.TEST_BIOMETRIC");
            }
            return com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getAllProperties();
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public android.hardware.fingerprint.FingerprintSensorPropertiesInternal getSensorProperties(int i, @android.annotation.NonNull java.lang.String str) {
            super.getSensorProperties_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "No matching sensor for getSensorProperties, sensorId: " + i + ", caller: " + str);
                return null;
            }
            return providerForSensor.getSensorProperties(i);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_FINGERPRINT")
        public void generateChallenge(android.os.IBinder iBinder, int i, int i2, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str) {
            super.generateChallenge_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "No matching sensor for generateChallenge, sensorId: " + i);
                return;
            }
            providerForSensor.scheduleGenerateChallenge(i, i2, iBinder, iFingerprintServiceReceiver, str);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_FINGERPRINT")
        public void revokeChallenge(android.os.IBinder iBinder, int i, int i2, java.lang.String str, long j) {
            super.revokeChallenge_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "No matching sensor for revokeChallenge, sensorId: " + i);
                return;
            }
            providerForSensor.scheduleRevokeChallenge(i, i2, iBinder, str, j);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_FINGERPRINT")
        public long enroll(android.os.IBinder iBinder, @android.annotation.NonNull byte[] bArr, int i, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str, int i2, android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions) {
            super.enroll_enforcePermission();
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.fingerprint.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for enroll");
                return -1L;
            }
            return ((com.android.server.biometrics.sensors.fingerprint.ServiceProvider) singleProvider.second).scheduleEnroll(((java.lang.Integer) singleProvider.first).intValue(), iBinder, bArr, i, iFingerprintServiceReceiver, str, i2, fingerprintEnrollOptions);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_FINGERPRINT")
        public void cancelEnrollment(android.os.IBinder iBinder, long j) {
            super.cancelEnrollment_enforcePermission();
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.fingerprint.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for cancelEnrollment");
            } else {
                ((com.android.server.biometrics.sensors.fingerprint.ServiceProvider) singleProvider.second).cancelEnrollment(((java.lang.Integer) singleProvider.first).intValue(), iBinder, j);
            }
        }

        public long authenticate(android.os.IBinder iBinder, long j, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.fingerprint.ServiceProvider> pair;
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            int callingUserId = android.os.UserHandle.getCallingUserId();
            java.lang.String opPackageName = fingerprintAuthenticateOptions.getOpPackageName();
            java.lang.String attributionTag = fingerprintAuthenticateOptions.getAttributionTag();
            int userId = fingerprintAuthenticateOptions.getUserId();
            if (!com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.canUseFingerprint(opPackageName, attributionTag, true, callingUid, callingPid, callingUserId)) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Authenticate rejecting package: " + opPackageName);
                return -1L;
            }
            boolean isKeyguard = com.android.server.biometrics.Utils.isKeyguard(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getContext(), opPackageName);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            if (isKeyguard) {
                try {
                    if (com.android.server.biometrics.Utils.isUserEncryptedOrLockdown(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mLockPatternUtils, userId)) {
                        android.util.EventLog.writeEvent(1397638484, "79776455");
                        android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Authenticate invoked when user is encrypted or lockdown");
                        return -1L;
                    }
                } finally {
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            boolean z = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getContext().checkCallingPermission("android.permission.MANAGE_FINGERPRINT") != 0;
            int i = isKeyguard ? 1 : 3;
            if (fingerprintAuthenticateOptions.getSensorId() == -1) {
                pair = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getSingleProvider();
            } else {
                com.android.server.biometrics.Utils.checkPermission(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getContext(), "android.permission.USE_BIOMETRIC_INTERNAL");
                pair = new android.util.Pair<>(java.lang.Integer.valueOf(fingerprintAuthenticateOptions.getSensorId()), com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(fingerprintAuthenticateOptions.getSensorId()));
            }
            if (pair == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for authenticate");
                return -1L;
            }
            fingerprintAuthenticateOptions.setSensorId(((java.lang.Integer) pair.first).intValue());
            android.hardware.fingerprint.FingerprintSensorPropertiesInternal sensorProperties = ((com.android.server.biometrics.sensors.fingerprint.ServiceProvider) pair.second).getSensorProperties(fingerprintAuthenticateOptions.getSensorId());
            if (!isKeyguard && !com.android.server.biometrics.Utils.isSettings(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getContext(), opPackageName) && sensorProperties != null && (sensorProperties.isAnyUdfpsType() || sensorProperties.isAnySidefpsType())) {
                try {
                    return authenticateWithPrompt(j, sensorProperties, callingUid, callingUserId, iFingerprintServiceReceiver, opPackageName, fingerprintAuthenticateOptions.isIgnoreEnrollmentState());
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Invalid package", e);
                    return -1L;
                }
            }
            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.companion.virtual.VirtualDeviceManagerInternal virtualDeviceManagerInternal = (com.android.server.companion.virtual.VirtualDeviceManagerInternal) com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getLocalService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class);
                if (virtualDeviceManagerInternal != null) {
                    virtualDeviceManagerInternal.onAuthenticationPrompt(callingUid);
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return ((com.android.server.biometrics.sensors.fingerprint.ServiceProvider) pair.second).scheduleAuthenticate(iBinder, j, 0, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFingerprintServiceReceiver), fingerprintAuthenticateOptions, z, i, isKeyguard);
            } finally {
            }
        }

        private long authenticateWithPrompt(long j, @android.annotation.NonNull final android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, int i, final int i2, final android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str, boolean z) throws android.content.pm.PackageManager.NameNotFoundException {
            android.content.Context uiContext = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getUiContext();
            android.content.Context createPackageContextAsUser = uiContext.createPackageContextAsUser(str, 0, android.os.UserHandle.getUserHandleForUid(i));
            java.util.concurrent.Executor mainExecutor = uiContext.getMainExecutor();
            return new android.hardware.biometrics.BiometricPrompt.Builder(createPackageContextAsUser).setTitle(uiContext.getString(android.R.string.battery_saver_description_with_learn_more)).setSubtitle(uiContext.getString(android.R.string.fcError)).setNegativeButton(uiContext.getString(android.R.string.cancel), mainExecutor, new android.content.DialogInterface.OnClickListener() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService$1$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(android.content.DialogInterface dialogInterface, int i3) {
                    com.android.server.biometrics.sensors.fingerprint.FingerprintService.AnonymousClass1.lambda$authenticateWithPrompt$0(iFingerprintServiceReceiver, dialogInterface, i3);
                }
            }).setIsForLegacyFingerprintManager(fingerprintSensorPropertiesInternal.sensorId).setIgnoreEnrollmentState(z).build().authenticateForOperation(new android.os.CancellationSignal(), mainExecutor, new android.hardware.biometrics.BiometricPrompt.AuthenticationCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService.1.1
                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public void onAuthenticationError(int i3, java.lang.CharSequence charSequence) {
                    try {
                        if (com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.isKnownErrorCode(i3)) {
                            iFingerprintServiceReceiver.onError(i3, 0);
                        } else {
                            iFingerprintServiceReceiver.onError(8, i3);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Remote exception in onAuthenticationError()", e);
                    }
                }

                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public void onAuthenticationSucceeded(android.hardware.biometrics.BiometricPrompt.AuthenticationResult authenticationResult) {
                    try {
                        iFingerprintServiceReceiver.onAuthenticationSucceeded(new android.hardware.fingerprint.Fingerprint("", 0, 0L), i2, fingerprintSensorPropertiesInternal.sensorStrength == 2);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Remote exception in onAuthenticationSucceeded()", e);
                    }
                }

                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public void onAuthenticationFailed() {
                    try {
                        iFingerprintServiceReceiver.onAuthenticationFailed();
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Remote exception in onAuthenticationFailed()", e);
                    }
                }

                public void onAuthenticationAcquired(int i3) {
                    try {
                        if (com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.isKnownAcquiredCode(i3)) {
                            iFingerprintServiceReceiver.onAcquired(i3, 0);
                        } else {
                            iFingerprintServiceReceiver.onAcquired(6, i3);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Remote exception in onAuthenticationAcquired()", e);
                    }
                }

                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public void onAuthenticationHelp(int i3, java.lang.CharSequence charSequence) {
                    onAuthenticationAcquired(i3);
                }
            }, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$authenticateWithPrompt$0(android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, android.content.DialogInterface dialogInterface, int i) {
            try {
                iFingerprintServiceReceiver.onError(10, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Remote exception in negative button onClick()", e);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public long detectFingerprint(android.os.IBinder iBinder, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
            super.detectFingerprint_enforcePermission();
            java.lang.String opPackageName = fingerprintAuthenticateOptions.getOpPackageName();
            if (!com.android.server.biometrics.Utils.isKeyguard(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getContext(), opPackageName)) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "detectFingerprint called from non-sysui package: " + opPackageName);
                return -1L;
            }
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.fingerprint.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for detectFingerprint");
                return -1L;
            }
            fingerprintAuthenticateOptions.setSensorId(((java.lang.Integer) singleProvider.first).intValue());
            return ((com.android.server.biometrics.sensors.fingerprint.ServiceProvider) singleProvider.second).scheduleFingerDetect(iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFingerprintServiceReceiver), fingerprintAuthenticateOptions, 1);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_BIOMETRIC")
        public void prepareForAuthentication(android.os.IBinder iBinder, long j, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, @android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, long j2, int i, boolean z, boolean z2) {
            super.prepareForAuthentication_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(fingerprintAuthenticateOptions.getSensorId());
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for prepareForAuthentication");
            } else {
                providerForSensor.scheduleAuthenticate(iBinder, j, i, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iBiometricSensorReceiver), fingerprintAuthenticateOptions, j2, true, z2 ? 3 : 2, z);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_BIOMETRIC")
        public void startPreparedClient(int i, int i2) {
            super.startPreparedClient_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for startPreparedClient");
            } else {
                providerForSensor.startPreparedClient(i, i2);
            }
        }

        public void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, long j) {
            if (!com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.canUseFingerprint(str, str2, true, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), android.os.UserHandle.getCallingUserId())) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "cancelAuthentication rejecting package: " + str);
                return;
            }
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.fingerprint.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for cancelAuthentication");
            } else {
                ((com.android.server.biometrics.sensors.fingerprint.ServiceProvider) singleProvider.second).cancelAuthentication(((java.lang.Integer) singleProvider.first).intValue(), iBinder, j);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void cancelFingerprintDetect(android.os.IBinder iBinder, java.lang.String str, long j) {
            super.cancelFingerprintDetect_enforcePermission();
            if (!com.android.server.biometrics.Utils.isKeyguard(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getContext(), str)) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "cancelFingerprintDetect called from non-sysui package: " + str);
                return;
            }
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.fingerprint.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for cancelFingerprintDetect");
            } else {
                ((com.android.server.biometrics.sensors.fingerprint.ServiceProvider) singleProvider.second).cancelAuthentication(((java.lang.Integer) singleProvider.first).intValue(), iBinder, j);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_BIOMETRIC")
        public void cancelAuthenticationFromService(int i, android.os.IBinder iBinder, java.lang.String str, long j) {
            super.cancelAuthenticationFromService_enforcePermission();
            android.util.Slog.d(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "cancelAuthenticationFromService, sensorId: " + i);
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for cancelAuthenticationFromService");
            } else {
                providerForSensor.cancelAuthentication(i, iBinder, j);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_FINGERPRINT")
        public void remove(android.os.IBinder iBinder, int i, int i2, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str) {
            super.remove_enforcePermission();
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.fingerprint.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for remove");
            } else {
                ((com.android.server.biometrics.sensors.fingerprint.ServiceProvider) singleProvider.second).scheduleRemove(((java.lang.Integer) singleProvider.first).intValue(), iBinder, iFingerprintServiceReceiver, i, i2, str);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void removeAll(android.os.IBinder iBinder, int i, final android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str) {
            super.removeAll_enforcePermission();
            android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver2 = new android.hardware.fingerprint.FingerprintServiceReceiver() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService.1.2
                final int numSensors;
                int sensorsFinishedRemoving = 0;

                {
                    this.numSensors = com.android.server.biometrics.sensors.fingerprint.FingerprintService.AnonymousClass1.this.getSensorPropertiesInternal(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getContext().getOpPackageName()).size();
                }

                public void onRemoved(android.hardware.fingerprint.Fingerprint fingerprint, int i2) throws android.os.RemoteException {
                    if (i2 == 0) {
                        this.sensorsFinishedRemoving++;
                        android.util.Slog.d(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "sensorsFinishedRemoving: " + this.sensorsFinishedRemoving + ", numSensors: " + this.numSensors);
                        if (this.sensorsFinishedRemoving == this.numSensors) {
                            iFingerprintServiceReceiver.onRemoved((android.hardware.fingerprint.Fingerprint) null, 0);
                        }
                    }
                }
            };
            for (com.android.server.biometrics.sensors.fingerprint.ServiceProvider serviceProvider : com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviders()) {
                java.util.Iterator<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> it = serviceProvider.getSensorProperties().iterator();
                while (it.hasNext()) {
                    serviceProvider.scheduleRemoveAll(it.next().sensorId, iBinder, iFingerprintServiceReceiver2, i, str);
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void addLockoutResetCallback(android.hardware.biometrics.IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, java.lang.String str) {
            super.addLockoutResetCallback_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mLockoutResetDispatcher.addCallback(iBiometricServiceLockoutResetCallback, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
            new com.android.server.biometrics.sensors.fingerprint.FingerprintShellCommand(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getContext(), com.android.server.biometrics.sensors.fingerprint.FingerprintService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        protected void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (!com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getContext(), com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, printWriter)) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (strArr.length > 1 && "--proto".equals(strArr[0]) && "--state".equals(strArr[1])) {
                    android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
                    for (com.android.server.biometrics.sensors.fingerprint.ServiceProvider serviceProvider : com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviders()) {
                        java.util.Iterator<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> it = serviceProvider.getSensorProperties().iterator();
                        while (it.hasNext()) {
                            serviceProvider.dumpProtoState(it.next().sensorId, protoOutputStream, false);
                        }
                    }
                    protoOutputStream.flush();
                } else if (strArr.length > 0 && "--proto".equals(strArr[0])) {
                    for (com.android.server.biometrics.sensors.fingerprint.ServiceProvider serviceProvider2 : com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviders()) {
                        java.util.Iterator<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> it2 = serviceProvider2.getSensorProperties().iterator();
                        while (it2.hasNext()) {
                            serviceProvider2.dumpProtoMetrics(it2.next().sensorId, fileDescriptor);
                        }
                    }
                } else {
                    for (com.android.server.biometrics.sensors.fingerprint.ServiceProvider serviceProvider3 : com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviders()) {
                        for (android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal : serviceProvider3.getSensorProperties()) {
                            printWriter.println("Dumping for sensorId: " + fingerprintSensorPropertiesInternal.sensorId + ", provider: " + serviceProvider3.getClass().getSimpleName());
                            java.lang.StringBuilder sb = new java.lang.StringBuilder();
                            sb.append("Fps state: ");
                            sb.append(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mBiometricStateCallback.getBiometricState());
                            printWriter.println(sb.toString());
                            serviceProvider3.dumpInternal(fingerprintSensorPropertiesInternal.sensorId, printWriter);
                            printWriter.println();
                        }
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isHardwareDetectedDeprecated(java.lang.String str, java.lang.String str2) {
            if (!com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.canUseFingerprint(str, str2, false, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), android.os.UserHandle.getCallingUserId())) {
                return false;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.fingerprint.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getSingleProvider();
                if (singleProvider != null) {
                    return ((com.android.server.biometrics.sensors.fingerprint.ServiceProvider) singleProvider.second).isHardwareDetected(((java.lang.Integer) singleProvider.first).intValue());
                }
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for isHardwareDetectedDeprecated, caller: " + str);
                return false;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public boolean isHardwareDetected(int i, java.lang.String str) {
            super.isHardwareDetected_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for isHardwareDetected, caller: " + str);
                return false;
            }
            return providerForSensor.isHardwareDetected(i);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_FINGERPRINT")
        public void rename(int i, int i2, java.lang.String str) {
            super.rename_enforcePermission();
            if (!com.android.server.biometrics.Utils.isCurrentUserOrProfile(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getContext(), i2)) {
                return;
            }
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.fingerprint.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for rename");
            } else {
                ((com.android.server.biometrics.sensors.fingerprint.ServiceProvider) singleProvider.second).rename(((java.lang.Integer) singleProvider.first).intValue(), i, i2, str);
            }
        }

        public java.util.List<android.hardware.fingerprint.Fingerprint> getEnrolledFingerprints(int i, java.lang.String str, java.lang.String str2) {
            if (!com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.canUseFingerprint(str, str2, false, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), android.os.UserHandle.getCallingUserId())) {
                return java.util.Collections.emptyList();
            }
            if (i != android.os.UserHandle.getCallingUserId()) {
                com.android.server.biometrics.Utils.checkPermission(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getContext(), "android.permission.INTERACT_ACROSS_USERS");
            }
            return com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getEnrolledFingerprintsDeprecated(i, str);
        }

        public boolean hasEnrolledFingerprintsDeprecated(int i, java.lang.String str, java.lang.String str2) {
            if (!com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.canUseFingerprint(str, str2, false, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), android.os.UserHandle.getCallingUserId())) {
                return false;
            }
            if (i != android.os.UserHandle.getCallingUserId()) {
                com.android.server.biometrics.Utils.checkPermission(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getContext(), "android.permission.INTERACT_ACROSS_USERS");
            }
            return !com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getEnrolledFingerprintsDeprecated(i, str).isEmpty();
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public boolean hasEnrolledFingerprints(int i, int i2, java.lang.String str) {
            super.hasEnrolledFingerprints_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor != null) {
                return providerForSensor.getEnrolledFingerprints(i, i2).size() > 0;
            }
            android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for hasEnrolledFingerprints, caller: " + str);
            return false;
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public int getLockoutModeForUser(int i, int i2) {
            super.getLockoutModeForUser_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for getLockoutModeForUser");
                return 0;
            }
            return providerForSensor.getLockoutModeForUser(i, i2);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void invalidateAuthenticatorId(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
            super.invalidateAuthenticatorId_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for invalidateAuthenticatorId");
            } else {
                providerForSensor.scheduleInvalidateAuthenticatorId(i, i2, iInvalidationCallback);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public long getAuthenticatorId(int i, int i2) {
            super.getAuthenticatorId_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for getAuthenticatorId");
                return 0L;
            }
            return providerForSensor.getAuthenticatorId(i, i2);
        }

        @android.annotation.EnforcePermission("android.permission.RESET_FINGERPRINT_LOCKOUT")
        public void resetLockout(android.os.IBinder iBinder, int i, int i2, @android.annotation.Nullable byte[] bArr, java.lang.String str) {
            super.resetLockout_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for resetLockout, caller: " + str);
                return;
            }
            providerForSensor.scheduleResetLockout(i, i2, bArr);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_FINGERPRINT")
        public boolean isClientActive() {
            super.isClientActive_enforcePermission();
            return com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mGestureAvailabilityDispatcher.isAnySensorActive();
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_FINGERPRINT")
        public void addClientActiveCallback(android.hardware.fingerprint.IFingerprintClientActiveCallback iFingerprintClientActiveCallback) {
            super.addClientActiveCallback_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mGestureAvailabilityDispatcher.registerCallback(iFingerprintClientActiveCallback);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_FINGERPRINT")
        public void removeClientActiveCallback(android.hardware.fingerprint.IFingerprintClientActiveCallback iFingerprintClientActiveCallback) {
            super.removeClientActiveCallback_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mGestureAvailabilityDispatcher.removeCallback(iFingerprintClientActiveCallback);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void registerAuthenticatorsLegacy(@android.annotation.NonNull final android.hardware.fingerprint.FingerprintSensorConfigurations fingerprintSensorConfigurations) {
            super.registerAuthenticatorsLegacy_enforcePermission();
            if (!fingerprintSensorConfigurations.hasSensorConfigurations()) {
                android.util.Slog.d(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "No fingerprint sensors available.");
            } else {
                com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.registerAll(new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService$1$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        java.util.List lambda$registerAuthenticatorsLegacy$1;
                        lambda$registerAuthenticatorsLegacy$1 = com.android.server.biometrics.sensors.fingerprint.FingerprintService.AnonymousClass1.this.lambda$registerAuthenticatorsLegacy$1(fingerprintSensorConfigurations);
                        return lambda$registerAuthenticatorsLegacy$1;
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.util.List lambda$registerAuthenticatorsLegacy$1(android.hardware.fingerprint.FingerprintSensorConfigurations fingerprintSensorConfigurations) {
            return com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getProviders(fingerprintSensorConfigurations);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void registerAuthenticators(@android.annotation.NonNull final java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> list) {
            super.registerAuthenticators_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.registerAll(new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.util.List lambda$registerAuthenticators$2;
                    lambda$registerAuthenticators$2 = com.android.server.biometrics.sensors.fingerprint.FingerprintService.AnonymousClass1.this.lambda$registerAuthenticators$2(list);
                    return lambda$registerAuthenticators$2;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.util.List lambda$registerAuthenticators$2(java.util.List list) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.lang.String[] strArr = (java.lang.String[]) com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mAidlInstanceNameSupplier.get();
            if (strArr != null) {
                arrayList.addAll(com.google.android.collect.Lists.newArrayList(strArr));
            }
            android.util.Pair filterAvailableHalInstances = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.filterAvailableHalInstances(list, arrayList);
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            arrayList2.addAll(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getHidlProviders((java.util.List) filterAvailableHalInstances.first));
            arrayList2.addAll(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.getAidlProviders((java.util.List) filterAvailableHalInstances.second));
            return arrayList2;
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void addAuthenticatorsRegisteredCallback(android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback iFingerprintAuthenticatorsRegisteredCallback) {
            super.addAuthenticatorsRegisteredCallback_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.addAllRegisteredCallback(iFingerprintAuthenticatorsRegisteredCallback);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void registerAuthenticationStateListener(@android.annotation.NonNull android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) {
            super.registerAuthenticationStateListener_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mAuthenticationStateListeners.registerAuthenticationStateListener(authenticationStateListener);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void unregisterAuthenticationStateListener(@android.annotation.NonNull android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) {
            super.unregisterAuthenticationStateListener_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mAuthenticationStateListeners.unregisterAuthenticationStateListener(authenticationStateListener);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void registerBiometricStateListener(@android.annotation.NonNull android.hardware.biometrics.IBiometricStateListener iBiometricStateListener) {
            super.registerBiometricStateListener_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mBiometricStateCallback.registerBiometricStateListener(iBiometricStateListener);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void onPointerDown(long j, int i, android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
            super.onPointerDown_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "No matching provider for onFingerDown, sensorId: " + i);
                return;
            }
            providerForSensor.onPointerDown(j, i, pointerContext);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void onPointerUp(long j, int i, android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
            super.onPointerUp_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "No matching provider for onFingerUp, sensorId: " + i);
                return;
            }
            providerForSensor.onPointerUp(j, i, pointerContext);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void onUdfpsUiEvent(int i, long j, int i2) {
            super.onUdfpsUiEvent_enforcePermission();
            com.android.server.biometrics.sensors.fingerprint.ServiceProvider providerForSensor = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviderForSensor(i2);
            if (providerForSensor == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "No matching provider for onUdfpsUiEvent, sensorId: " + i2);
                return;
            }
            providerForSensor.onUdfpsUiEvent(i, j, i2);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void setUdfpsOverlayController(@android.annotation.NonNull android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController) {
            super.setUdfpsOverlayController_enforcePermission();
            java.util.Iterator<com.android.server.biometrics.sensors.fingerprint.ServiceProvider> it = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviders().iterator();
            while (it.hasNext()) {
                it.next().setUdfpsOverlayController(iUdfpsOverlayController);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void setSidefpsController(@android.annotation.NonNull android.hardware.fingerprint.ISidefpsController iSidefpsController) {
            super.setSidefpsController_enforcePermission();
            java.util.Iterator<com.android.server.biometrics.sensors.fingerprint.ServiceProvider> it = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviders().iterator();
            while (it.hasNext()) {
                it.next().setSidefpsController(iSidefpsController);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void onPowerPressed() {
            super.onPowerPressed_enforcePermission();
            java.util.Iterator<com.android.server.biometrics.sensors.fingerprint.ServiceProvider> it = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviders().iterator();
            while (it.hasNext()) {
                it.next().onPowerPressed();
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void scheduleWatchdog() {
            super.scheduleWatchdog_enforcePermission();
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.fingerprint.ServiceProvider> singleProvider = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getSingleProvider();
            if (singleProvider == null) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Null provider for scheduling watchdog");
            } else {
                ((com.android.server.biometrics.sensors.fingerprint.ServiceProvider) singleProvider.second).scheduleWatchdog(((java.lang.Integer) singleProvider.first).intValue());
            }
        }
    }

    public FingerprintService(android.content.Context context) {
        this(context, com.android.server.biometrics.log.BiometricContext.getInstance(context), new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.hardware.biometrics.IBiometricService lambda$new$0;
                lambda$new$0 = com.android.server.biometrics.sensors.fingerprint.FingerprintService.lambda$new$0();
                return lambda$new$0;
            }
        }, new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String[] lambda$new$1;
                lambda$new$1 = com.android.server.biometrics.sensors.fingerprint.FingerprintService.lambda$new$1();
                return lambda$new$1;
            }
        }, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.biometrics.IBiometricService lambda$new$0() {
        return android.hardware.biometrics.IBiometricService.Stub.asInterface(android.os.ServiceManager.getService("biometric"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String[] lambda$new$1() {
        return android.os.ServiceManager.getDeclaredInstances(android.hardware.biometrics.fingerprint.IFingerprint.DESCRIPTOR);
    }

    @com.android.internal.annotations.VisibleForTesting
    FingerprintService(android.content.Context context, com.android.server.biometrics.log.BiometricContext biometricContext, java.util.function.Supplier<android.hardware.biometrics.IBiometricService> supplier, java.util.function.Supplier<java.lang.String[]> supplier2, java.util.function.Function<java.lang.String, com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider> function, com.android.server.biometrics.sensors.fingerprint.FingerprintService.FingerprintProviderFunction fingerprintProviderFunction) {
        super(context);
        this.mServiceWrapper = new com.android.server.biometrics.sensors.fingerprint.FingerprintService.AnonymousClass1();
        this.mBiometricContext = biometricContext;
        this.mAidlInstanceNameSupplier = supplier2;
        this.mAppOps = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        this.mGestureAvailabilityDispatcher = new com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher();
        this.mLockoutResetDispatcher = new com.android.server.biometrics.sensors.LockoutResetDispatcher(context);
        this.mLockPatternUtils = new com.android.internal.widget.LockPatternUtils(context);
        this.mBiometricStateCallback = new com.android.server.biometrics.sensors.BiometricStateCallback<>(android.os.UserManager.get(context));
        this.mAuthenticationStateListeners = new com.android.server.biometrics.sensors.AuthenticationStateListeners();
        this.mFingerprintProvider = function == null ? new java.util.function.Function() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider lambda$new$2;
                lambda$new$2 = com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.lambda$new$2((java.lang.String) obj);
                return lambda$new$2;
            }
        } : function;
        com.android.server.biometrics.Flags.deHidl();
        this.mFingerprintProviderFunction = new com.android.server.biometrics.sensors.fingerprint.FingerprintService.FingerprintProviderFunction() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService$$ExternalSyntheticLambda1
            @Override // com.android.server.biometrics.sensors.fingerprint.FingerprintService.FingerprintProviderFunction
            public final com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider getFingerprintProvider(android.util.Pair pair, boolean z) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider lambda$new$4;
                lambda$new$4 = com.android.server.biometrics.sensors.fingerprint.FingerprintService.lambda$new$4(pair, z);
                return lambda$new$4;
            }
        };
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        this.mRegistry = new com.android.server.biometrics.sensors.fingerprint.FingerprintServiceRegistry(this.mServiceWrapper, supplier);
        this.mRegistry.addAllRegisteredCallback(new android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback.Stub() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService.2
            public void onAllAuthenticatorsRegistered(java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> list) {
                com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mBiometricStateCallback.start(com.android.server.biometrics.sensors.fingerprint.FingerprintService.this.mRegistry.getProviders());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider lambda$new$2(java.lang.String str) {
        java.lang.String str2 = android.hardware.biometrics.fingerprint.IFingerprint.DESCRIPTOR + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str;
        android.hardware.biometrics.fingerprint.IFingerprint asInterface = android.hardware.biometrics.fingerprint.IFingerprint.Stub.asInterface(android.os.Binder.allowBlocking(android.os.ServiceManager.waitForDeclaredService(str2)));
        if (asInterface != null) {
            try {
                return new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider(getContext(), this.mBiometricStateCallback, this.mAuthenticationStateListeners, asInterface.getSensorProps(), str, this.mLockoutResetDispatcher, this.mGestureAvailabilityDispatcher, this.mBiometricContext, true);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception in getSensorProps: " + str2);
                return null;
            }
        }
        android.util.Slog.e(TAG, "Unable to get declared service: " + str2);
        return null;
    }

    private /* synthetic */ com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider lambda$new$3(android.util.Pair pair, boolean z) {
        return new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider(getContext(), this.mBiometricStateCallback, this.mAuthenticationStateListeners, (android.hardware.biometrics.fingerprint.SensorProps[]) pair.second, (java.lang.String) pair.first, this.mLockoutResetDispatcher, this.mGestureAvailabilityDispatcher, this.mBiometricContext, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider lambda$new$4(android.util.Pair pair, boolean z) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public java.util.List<com.android.server.biometrics.sensors.fingerprint.ServiceProvider> getProviders(@android.annotation.NonNull android.hardware.fingerprint.FingerprintSensorConfigurations fingerprintSensorConfigurations) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(this.mFingerprintProviderFunction.getFingerprintProvider(filterAvailableHalInstances(fingerprintSensorConfigurations), fingerprintSensorConfigurations.getResetLockoutRequiresHardwareAuthToken()));
        return arrayList;
    }

    @android.annotation.NonNull
    private android.util.Pair<java.lang.String, android.hardware.biometrics.fingerprint.SensorProps[]> filterAvailableHalInstances(android.hardware.fingerprint.FingerprintSensorConfigurations fingerprintSensorConfigurations) {
        android.util.Pair<java.lang.String, android.hardware.biometrics.fingerprint.SensorProps[]> sensorPair = fingerprintSensorConfigurations.getSensorPair();
        if (fingerprintSensorConfigurations.isSingleSensorConfigurationPresent()) {
            return sensorPair;
        }
        android.util.Pair<java.lang.String, android.hardware.biometrics.fingerprint.SensorProps[]> sensorPairForInstance = fingerprintSensorConfigurations.getSensorPairForInstance("virtual");
        if (com.android.server.biometrics.Utils.isVirtualEnabled(getContext())) {
            if (sensorPairForInstance != null) {
                return sensorPairForInstance;
            }
            android.util.Slog.e(TAG, "Could not find virtual interface while it is enabled");
            return sensorPair;
        }
        if (sensorPairForInstance != null) {
            return fingerprintSensorConfigurations.getSensorPairNotForInstance("virtual");
        }
        return sensorPair;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.util.Pair<java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal>, java.util.List<java.lang.String>> filterAvailableHalInstances(@android.annotation.NonNull java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> list, @android.annotation.NonNull java.util.List<java.lang.String> list2) {
        if (list.size() + list2.size() <= 1) {
            return new android.util.Pair<>(list, list2);
        }
        int indexOf = list2.indexOf("virtual");
        if (!com.android.server.biometrics.Utils.isVirtualEnabled(getContext())) {
            java.util.ArrayList arrayList = new java.util.ArrayList(list2);
            if (indexOf != -1) {
                arrayList.remove(indexOf);
            }
            return new android.util.Pair<>(list, arrayList);
        }
        if (indexOf != -1) {
            android.util.Slog.i(TAG, "virtual hal is used");
            return new android.util.Pair<>(new java.util.ArrayList(), java.util.List.of(list2.get(indexOf)));
        }
        android.util.Slog.e(TAG, "Could not find virtual interface while it is enabled");
        return new android.util.Pair<>(list, list2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public java.util.List<com.android.server.biometrics.sensors.fingerprint.ServiceProvider> getHidlProviders(@android.annotation.NonNull java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> list) {
        com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21 newInstance;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal : list) {
            if ((android.os.Build.IS_USERDEBUG || android.os.Build.IS_ENG) && getContext().getResources().getBoolean(android.R.bool.allow_test_udfps) && android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.CONFIG_ENABLE_TEST_UDFPS, 0, -2) != 0) {
                newInstance = com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.newInstance(getContext(), this.mBiometricStateCallback, this.mAuthenticationStateListeners, fingerprintSensorPropertiesInternal, this.mLockoutResetDispatcher, this.mGestureAvailabilityDispatcher, com.android.server.biometrics.log.BiometricContext.getInstance(getContext()));
            } else {
                newInstance = com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.newInstance(getContext(), this.mBiometricStateCallback, this.mAuthenticationStateListeners, fingerprintSensorPropertiesInternal, this.mHandler, this.mLockoutResetDispatcher, this.mGestureAvailabilityDispatcher);
            }
            arrayList.add(newInstance);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public java.util.List<com.android.server.biometrics.sensors.fingerprint.ServiceProvider> getAidlProviders(@android.annotation.NonNull java.util.List<java.lang.String> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str : list) {
            com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider apply = this.mFingerprintProvider.apply(str);
            android.util.Slog.i(TAG, "Adding AIDL provider: " + str);
            arrayList.add(apply);
        }
        return arrayList;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("fingerprint", this.mServiceWrapper);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public java.util.List<android.hardware.fingerprint.Fingerprint> getEnrolledFingerprintsDeprecated(int i, java.lang.String str) {
        android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.fingerprint.ServiceProvider> singleProvider = this.mRegistry.getSingleProvider();
        if (singleProvider == null) {
            android.util.Slog.w(TAG, "Null provider for getEnrolledFingerprintsDeprecated, caller: " + str);
            return java.util.Collections.emptyList();
        }
        return ((com.android.server.biometrics.sensors.fingerprint.ServiceProvider) singleProvider.second).getEnrolledFingerprints(((java.lang.Integer) singleProvider.first).intValue(), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canUseFingerprint(java.lang.String str, java.lang.String str2, boolean z, int i, int i2, int i3) {
        if (getContext().checkCallingPermission("android.permission.USE_FINGERPRINT") != 0) {
            com.android.server.biometrics.Utils.checkPermission(getContext(), "android.permission.USE_BIOMETRIC");
        }
        if (android.os.Binder.getCallingUid() == 1000 || com.android.server.biometrics.Utils.isKeyguard(getContext(), str)) {
            return true;
        }
        if (!com.android.server.biometrics.Utils.isCurrentUserOrProfile(getContext(), i3)) {
            android.util.Slog.w(TAG, "Rejecting " + str + "; not a current user or profile");
            return false;
        }
        if (!checkAppOps(i, str, str2)) {
            android.util.Slog.w(TAG, "Rejecting " + str + "; permission denied");
            return false;
        }
        if (!z || com.android.server.biometrics.Utils.isForeground(i, i2)) {
            return true;
        }
        android.util.Slog.w(TAG, "Rejecting " + str + "; not in foreground");
        return false;
    }

    private boolean checkAppOps(int i, java.lang.String str, java.lang.String str2) {
        return this.mAppOps.noteOp(78, i, str, str2, (java.lang.String) null) == 0 || this.mAppOps.noteOp(55, i, str, str2, (java.lang.String) null) == 0;
    }

    void syncEnrollmentsNow() {
        com.android.server.biometrics.Utils.checkPermissionOrShell(getContext(), "android.permission.MANAGE_FINGERPRINT");
        if (com.android.server.biometrics.Utils.isVirtualEnabled(getContext())) {
            android.util.Slog.i(TAG, "Sync virtual enrollments");
            int currentUser = android.app.ActivityManager.getCurrentUser();
            final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(this.mRegistry.getProviders().size());
            for (com.android.server.biometrics.sensors.fingerprint.ServiceProvider serviceProvider : this.mRegistry.getProviders()) {
                java.util.Iterator<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> it = serviceProvider.getSensorProperties().iterator();
                while (it.hasNext()) {
                    serviceProvider.scheduleInternalCleanup(it.next().sensorId, currentUser, new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.FingerprintService.3
                        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                        public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                            countDownLatch.countDown();
                            if (!z) {
                                android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.FingerprintService.TAG, "Sync virtual enrollments failed");
                            }
                        }
                    }, true);
                }
            }
            try {
                countDownLatch.await(3L, java.util.concurrent.TimeUnit.SECONDS);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Failed to wait for sync finishing", e);
            }
        }
    }

    void simulateVhalFingerDown() {
        if (com.android.server.biometrics.Utils.isVirtualEnabled(getContext())) {
            android.util.Slog.i(TAG, "Simulate virtual HAL finger down event");
            android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.fingerprint.ServiceProvider> singleProvider = this.mRegistry.getSingleProvider();
            if (singleProvider != null) {
                ((com.android.server.biometrics.sensors.fingerprint.ServiceProvider) singleProvider.second).simulateVhalFingerDown(android.os.UserHandle.getCallingUserId(), ((java.lang.Integer) singleProvider.first).intValue());
            }
        }
    }

    void sendFingerprintReEnrollNotification() {
        com.android.server.biometrics.Utils.checkPermissionOrShell(getContext(), "android.permission.MANAGE_FINGERPRINT");
        if (android.os.Build.IS_DEBUGGABLE) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.util.Pair<java.lang.Integer, com.android.server.biometrics.sensors.fingerprint.ServiceProvider> singleProvider = this.mRegistry.getSingleProvider();
                if (singleProvider != null) {
                    ((com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider) singleProvider.second).sendFingerprintReEnrollNotification();
                } else {
                    android.util.Slog.w(TAG, "Null provider for notification");
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }
}
