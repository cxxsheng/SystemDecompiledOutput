package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public class FingerprintUtils implements com.android.server.biometrics.sensors.BiometricUtils<android.hardware.fingerprint.Fingerprint> {
    private static final java.lang.String LEGACY_FINGERPRINT_FILE = "settings_fingerprint.xml";
    private static final java.lang.Object sInstanceLock = new java.lang.Object();
    private static android.util.SparseArray<com.android.server.biometrics.sensors.fingerprint.FingerprintUtils> sInstances;
    private final java.lang.String mFileName;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.SparseArray<com.android.server.biometrics.sensors.fingerprint.FingerprintUserState> mUserStates = new android.util.SparseArray<>();

    public static com.android.server.biometrics.sensors.fingerprint.FingerprintUtils getInstance(int i) {
        return getInstance(i, null);
    }

    private static com.android.server.biometrics.sensors.fingerprint.FingerprintUtils getInstance(int i, @android.annotation.Nullable java.lang.String str) {
        com.android.server.biometrics.sensors.fingerprint.FingerprintUtils fingerprintUtils;
        synchronized (sInstanceLock) {
            try {
                if (sInstances == null) {
                    sInstances = new android.util.SparseArray<>();
                }
                if (sInstances.get(i) == null) {
                    if (str == null) {
                        str = "settings_fingerprint_" + i + ".xml";
                    }
                    sInstances.put(i, new com.android.server.biometrics.sensors.fingerprint.FingerprintUtils(str));
                }
                fingerprintUtils = sInstances.get(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return fingerprintUtils;
    }

    public static com.android.server.biometrics.sensors.fingerprint.FingerprintUtils getLegacyInstance(int i) {
        return getInstance(i, LEGACY_FINGERPRINT_FILE);
    }

    private FingerprintUtils(java.lang.String str) {
        this.mFileName = str;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public java.util.List<android.hardware.fingerprint.Fingerprint> getBiometricsForUser(android.content.Context context, int i) {
        return getStateForUser(context, i).getBiometrics();
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public void addBiometricForUser(android.content.Context context, int i, android.hardware.fingerprint.Fingerprint fingerprint) {
        getStateForUser(context, i).addBiometric(fingerprint);
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public void removeBiometricForUser(android.content.Context context, int i, int i2) {
        getStateForUser(context, i).removeBiometric(i2);
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public void renameBiometricForUser(android.content.Context context, int i, int i2, java.lang.CharSequence charSequence) {
        if (android.text.TextUtils.isEmpty(charSequence)) {
            return;
        }
        getStateForUser(context, i).renameBiometric(i2, charSequence);
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public java.lang.CharSequence getUniqueName(android.content.Context context, int i) {
        return getStateForUser(context, i).getUniqueName();
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public void setInvalidationInProgress(android.content.Context context, int i, boolean z) {
        getStateForUser(context, i).setInvalidationInProgress(z);
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public boolean isInvalidationInProgress(android.content.Context context, int i) {
        return getStateForUser(context, i).isInvalidationInProgress();
    }

    private com.android.server.biometrics.sensors.fingerprint.FingerprintUserState getStateForUser(android.content.Context context, int i) {
        com.android.server.biometrics.sensors.fingerprint.FingerprintUserState fingerprintUserState;
        synchronized (this) {
            try {
                fingerprintUserState = this.mUserStates.get(i);
                if (fingerprintUserState == null) {
                    fingerprintUserState = new com.android.server.biometrics.sensors.fingerprint.FingerprintUserState(context, i, this.mFileName);
                    this.mUserStates.put(i, fingerprintUserState);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return fingerprintUserState;
    }

    public static boolean isKnownErrorCode(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return true;
            default:
                return false;
        }
    }

    public static boolean isKnownAcquiredCode(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
                return true;
            case 8:
            default:
                return false;
        }
    }
}
