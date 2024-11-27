package com.android.server.biometrics;

/* loaded from: classes.dex */
class PreAuthInfo {
    static final int AUTHENTICATOR_OK = 1;
    static final int BIOMETRIC_DISABLED_BY_DEVICE_POLICY = 3;
    static final int BIOMETRIC_HARDWARE_NOT_DETECTED = 6;
    static final int BIOMETRIC_INSUFFICIENT_STRENGTH = 4;
    static final int BIOMETRIC_INSUFFICIENT_STRENGTH_AFTER_DOWNGRADE = 5;
    static final int BIOMETRIC_LOCKOUT_PERMANENT = 11;
    static final int BIOMETRIC_LOCKOUT_TIMED = 10;
    static final int BIOMETRIC_NOT_ENABLED_FOR_APPS = 8;
    static final int BIOMETRIC_NOT_ENROLLED = 7;
    static final int BIOMETRIC_NO_HARDWARE = 2;
    static final int BIOMETRIC_SENSOR_PRIVACY_ENABLED = 12;
    static final int CREDENTIAL_NOT_ENROLLED = 9;
    private static final java.lang.String TAG = "BiometricService/PreAuthInfo";
    final boolean confirmationRequested;
    final android.content.Context context;
    final boolean credentialAvailable;
    final boolean credentialRequested;
    final java.util.List<com.android.server.biometrics.BiometricSensor> eligibleSensors;
    final boolean ignoreEnrollmentState;
    final java.util.List<android.util.Pair<com.android.server.biometrics.BiometricSensor, java.lang.Integer>> ineligibleSensors;
    private final com.android.server.biometrics.BiometricCameraManager mBiometricCameraManager;
    private final boolean mBiometricRequested;
    private final int mBiometricStrengthRequested;
    final int userId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface AuthenticatorStatus {
    }

    private PreAuthInfo(boolean z, int i, boolean z2, java.util.List<com.android.server.biometrics.BiometricSensor> list, java.util.List<android.util.Pair<com.android.server.biometrics.BiometricSensor, java.lang.Integer>> list2, boolean z3, boolean z4, boolean z5, int i2, android.content.Context context, com.android.server.biometrics.BiometricCameraManager biometricCameraManager) {
        this.mBiometricRequested = z;
        this.mBiometricStrengthRequested = i;
        this.mBiometricCameraManager = biometricCameraManager;
        this.credentialRequested = z2;
        this.eligibleSensors = list;
        this.ineligibleSensors = list2;
        this.credentialAvailable = z3;
        this.confirmationRequested = z4;
        this.ignoreEnrollmentState = z5;
        this.userId = i2;
        this.context = context;
    }

    static com.android.server.biometrics.PreAuthInfo create(android.app.trust.ITrustManager iTrustManager, android.app.admin.DevicePolicyManager devicePolicyManager, com.android.server.biometrics.BiometricService.SettingObserver settingObserver, java.util.List<com.android.server.biometrics.BiometricSensor> list, int i, android.hardware.biometrics.PromptInfo promptInfo, java.lang.String str, boolean z, android.content.Context context, com.android.server.biometrics.BiometricCameraManager biometricCameraManager) throws android.os.RemoteException {
        boolean isConfirmationRequested = promptInfo.isConfirmationRequested();
        boolean isBiometricRequested = com.android.server.biometrics.Utils.isBiometricRequested(promptInfo);
        int publicBiometricStrength = com.android.server.biometrics.Utils.getPublicBiometricStrength(promptInfo);
        boolean isCredentialRequested = com.android.server.biometrics.Utils.isCredentialRequested(promptInfo);
        boolean isDeviceSecure = iTrustManager.isDeviceSecure(i, context.getDeviceId());
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        if (isBiometricRequested) {
            java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = list.iterator();
            while (it.hasNext()) {
                com.android.server.biometrics.BiometricSensor next = it.next();
                java.util.Iterator<com.android.server.biometrics.BiometricSensor> it2 = it;
                int statusForBiometricAuthenticator = getStatusForBiometricAuthenticator(devicePolicyManager, settingObserver, next, i, str, z, publicBiometricStrength, promptInfo.getAllowedSensorIds(), promptInfo.isIgnoreEnrollmentState(), biometricCameraManager);
                android.util.Slog.d(TAG, "Package: " + str + " Sensor ID: " + next.id + " Modality: " + next.modality + " Status: " + statusForBiometricAuthenticator);
                if (statusForBiometricAuthenticator == 1) {
                    arrayList.add(next);
                } else {
                    arrayList2.add(new android.util.Pair(next, java.lang.Integer.valueOf(statusForBiometricAuthenticator)));
                }
                it = it2;
            }
        }
        return new com.android.server.biometrics.PreAuthInfo(isBiometricRequested, publicBiometricStrength, isCredentialRequested, arrayList, arrayList2, isDeviceSecure, isConfirmationRequested, promptInfo.isIgnoreEnrollmentState(), i, context, biometricCameraManager);
    }

    private static int getStatusForBiometricAuthenticator(android.app.admin.DevicePolicyManager devicePolicyManager, com.android.server.biometrics.BiometricService.SettingObserver settingObserver, com.android.server.biometrics.BiometricSensor biometricSensor, int i, java.lang.String str, boolean z, int i2, @android.annotation.NonNull java.util.List<java.lang.Integer> list, boolean z2, com.android.server.biometrics.BiometricCameraManager biometricCameraManager) {
        if (!list.isEmpty() && !list.contains(java.lang.Integer.valueOf(biometricSensor.id))) {
            return 2;
        }
        if (biometricSensor.modality == 8 && biometricCameraManager.isAnyCameraUnavailable()) {
            return 6;
        }
        boolean isAtLeastStrength = com.android.server.biometrics.Utils.isAtLeastStrength(biometricSensor.oemStrength, i2);
        boolean isAtLeastStrength2 = com.android.server.biometrics.Utils.isAtLeastStrength(biometricSensor.getCurrentStrength(), i2);
        if (isAtLeastStrength && !isAtLeastStrength2) {
            return 5;
        }
        if (!isAtLeastStrength) {
            return 4;
        }
        try {
            if (!biometricSensor.impl.isHardwareDetected(str)) {
                return 6;
            }
            if (!biometricSensor.impl.hasEnrolledTemplates(i, str) && !z2) {
                return 7;
            }
            if (biometricCameraManager != null && biometricSensor.modality == 8 && biometricCameraManager.isCameraPrivacyEnabled()) {
                return 12;
            }
            int lockoutModeForUser = biometricSensor.impl.getLockoutModeForUser(i);
            if (lockoutModeForUser == 1) {
                return 10;
            }
            if (lockoutModeForUser == 2) {
                return 11;
            }
            if (!isEnabledForApp(settingObserver, biometricSensor.modality, i)) {
                return 8;
            }
            if (!z || !isBiometricDisabledByDevicePolicy(devicePolicyManager, biometricSensor.modality, i)) {
                return 1;
            }
            return 3;
        } catch (android.os.RemoteException e) {
            return 6;
        }
    }

    private static boolean isEnabledForApp(com.android.server.biometrics.BiometricService.SettingObserver settingObserver, int i, int i2) {
        return settingObserver.getEnabledForApps(i2);
    }

    private static boolean isBiometricDisabledByDevicePolicy(android.app.admin.DevicePolicyManager devicePolicyManager, int i, int i2) {
        int mapModalityToDevicePolicyType = mapModalityToDevicePolicyType(i);
        if (mapModalityToDevicePolicyType == 0) {
            throw new java.lang.IllegalStateException("Modality unknown to devicePolicyManager: " + i);
        }
        boolean z = (devicePolicyManager.getKeyguardDisabledFeatures(null, i2) & mapModalityToDevicePolicyType) != 0;
        android.util.Slog.w(TAG, "isBiometricDisabledByDevicePolicy(" + i + "," + i2 + ")=" + z);
        return z;
    }

    private static int mapModalityToDevicePolicyType(int i) {
        switch (i) {
            case 2:
                return 32;
            case 4:
                return 256;
            case 8:
                return 128;
            default:
                android.util.Slog.e(TAG, "Error modality=" + i);
                return 0;
        }
    }

    private android.util.Pair<com.android.server.biometrics.BiometricSensor, java.lang.Integer> calculateErrorByPriority() {
        android.util.Pair<com.android.server.biometrics.BiometricSensor, java.lang.Integer> pair = null;
        android.util.Pair<com.android.server.biometrics.BiometricSensor, java.lang.Integer> pair2 = null;
        android.util.Pair<com.android.server.biometrics.BiometricSensor, java.lang.Integer> pair3 = null;
        for (android.util.Pair<com.android.server.biometrics.BiometricSensor, java.lang.Integer> pair4 : this.ineligibleSensors) {
            int intValue = ((java.lang.Integer) pair4.second).intValue();
            if (intValue == 10 || intValue == 11) {
                pair = pair4;
            }
            if (intValue == 7) {
                pair3 = pair4;
            }
            if (intValue == 6) {
                pair2 = pair4;
            }
        }
        if (pair != null) {
            return pair;
        }
        if (pair2 != null) {
            return pair2;
        }
        if (pair3 != null) {
            return pair3;
        }
        return this.ineligibleSensors.get(0);
    }

    private android.util.Pair<java.lang.Integer, java.lang.Integer> getInternalStatus() {
        boolean z;
        int i = 0;
        if (this.mBiometricCameraManager == null) {
            z = false;
        } else {
            z = this.mBiometricCameraManager.isCameraPrivacyEnabled();
        }
        int i2 = 12;
        if (this.mBiometricRequested && this.credentialRequested) {
            if (this.credentialAvailable || !this.eligibleSensors.isEmpty()) {
                java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = this.eligibleSensors.iterator();
                while (it.hasNext()) {
                    i |= it.next().modality;
                }
                if (this.credentialAvailable) {
                    i |= 1;
                    i2 = 1;
                } else if (i != 8 || !z) {
                    i2 = 1;
                }
            } else if (!this.ineligibleSensors.isEmpty()) {
                android.util.Pair<com.android.server.biometrics.BiometricSensor, java.lang.Integer> calculateErrorByPriority = calculateErrorByPriority();
                i = 0 | ((com.android.server.biometrics.BiometricSensor) calculateErrorByPriority.first).modality;
                i2 = ((java.lang.Integer) calculateErrorByPriority.second).intValue();
            } else {
                i2 = 9;
                i = 1;
            }
        } else if (this.mBiometricRequested) {
            if (!this.eligibleSensors.isEmpty()) {
                java.util.Iterator<com.android.server.biometrics.BiometricSensor> it2 = this.eligibleSensors.iterator();
                while (it2.hasNext()) {
                    i |= it2.next().modality;
                }
                if (i != 8 || !z) {
                    i2 = 1;
                }
            } else if (!this.ineligibleSensors.isEmpty()) {
                android.util.Pair<com.android.server.biometrics.BiometricSensor, java.lang.Integer> calculateErrorByPriority2 = calculateErrorByPriority();
                i = 0 | ((com.android.server.biometrics.BiometricSensor) calculateErrorByPriority2.first).modality;
                i2 = ((java.lang.Integer) calculateErrorByPriority2.second).intValue();
            } else {
                i2 = 2;
            }
        } else if (this.credentialRequested) {
            i2 = this.credentialAvailable ? 1 : 9;
            i = 1;
        } else {
            android.util.Slog.e(TAG, "No authenticators requested");
            i2 = 2;
        }
        android.util.Slog.d(TAG, "getCanAuthenticateInternal Modality: " + i + " AuthenticatorStatus: " + i2);
        return new android.util.Pair<>(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }

    int getCanAuthenticateResult() {
        return com.android.server.biometrics.Utils.biometricConstantsToBiometricManager(com.android.server.biometrics.Utils.authenticatorStatusToBiometricConstant(((java.lang.Integer) getInternalStatus().second).intValue()));
    }

    android.util.Pair<java.lang.Integer, java.lang.Integer> getPreAuthenticateStatus() {
        android.util.Pair<java.lang.Integer, java.lang.Integer> internalStatus = getInternalStatus();
        int authenticatorStatusToBiometricConstant = com.android.server.biometrics.Utils.authenticatorStatusToBiometricConstant(((java.lang.Integer) internalStatus.second).intValue());
        int intValue = ((java.lang.Integer) internalStatus.first).intValue();
        switch (((java.lang.Integer) internalStatus.second).intValue()) {
            case 1:
            case 2:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
                break;
            case 3:
            case 4:
            case 8:
            default:
                intValue = 0;
                break;
        }
        return new android.util.Pair<>(java.lang.Integer.valueOf(intValue), java.lang.Integer.valueOf(authenticatorStatusToBiometricConstant));
    }

    boolean shouldShowCredential() {
        return this.credentialRequested && this.credentialAvailable;
    }

    int getEligibleModalities() {
        java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = this.eligibleSensors.iterator();
        int i = 0;
        while (it.hasNext()) {
            i |= it.next().modality;
        }
        if (this.credentialRequested && this.credentialAvailable) {
            return i | 1;
        }
        return i;
    }

    int numSensorsWaitingForCookie() {
        int i = 0;
        for (com.android.server.biometrics.BiometricSensor biometricSensor : this.eligibleSensors) {
            if (biometricSensor.getSensorState() == 1) {
                android.util.Slog.d(TAG, "Sensor ID: " + biometricSensor.id + " Waiting for cookie: " + biometricSensor.getCookie());
                i++;
            }
        }
        return i;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("BiometricRequested: " + this.mBiometricRequested + ", StrengthRequested: " + this.mBiometricStrengthRequested + ", CredentialRequested: " + this.credentialRequested);
        sb.append(", Eligible:{");
        java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = this.eligibleSensors.iterator();
        while (it.hasNext()) {
            sb.append(it.next().id);
            sb.append(" ");
        }
        sb.append("}");
        sb.append(", Ineligible:{");
        for (android.util.Pair<com.android.server.biometrics.BiometricSensor, java.lang.Integer> pair : this.ineligibleSensors) {
            sb.append(pair.first);
            sb.append(":");
            sb.append(pair.second);
            sb.append(" ");
        }
        sb.append("}");
        sb.append(", CredentialAvailable: ");
        sb.append(this.credentialAvailable);
        sb.append(", ");
        return sb.toString();
    }
}
