package com.android.server.biometrics.sensors.face;

/* loaded from: classes.dex */
public class FaceUtils implements com.android.server.biometrics.sensors.BiometricUtils<android.hardware.face.Face> {
    private static final java.lang.String LEGACY_FACE_FILE = "settings_face.xml";
    private static final java.lang.Object sInstanceLock = new java.lang.Object();
    private static android.util.SparseArray<com.android.server.biometrics.sensors.face.FaceUtils> sInstances;
    private final java.lang.String mFileName;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.SparseArray<com.android.server.biometrics.sensors.face.FaceUserState> mUserStates = new android.util.SparseArray<>();

    public static com.android.server.biometrics.sensors.face.FaceUtils getInstance(int i) {
        return getInstance(i, null);
    }

    private static com.android.server.biometrics.sensors.face.FaceUtils getInstance(int i, @android.annotation.Nullable java.lang.String str) {
        com.android.server.biometrics.sensors.face.FaceUtils faceUtils;
        synchronized (sInstanceLock) {
            try {
                if (sInstances == null) {
                    sInstances = new android.util.SparseArray<>();
                }
                if (sInstances.get(i) == null) {
                    if (str == null) {
                        str = "settings_face_" + i + ".xml";
                    }
                    sInstances.put(i, new com.android.server.biometrics.sensors.face.FaceUtils(str));
                }
                faceUtils = sInstances.get(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return faceUtils;
    }

    public static com.android.server.biometrics.sensors.face.FaceUtils getLegacyInstance(int i) {
        return getInstance(i, LEGACY_FACE_FILE);
    }

    private FaceUtils(java.lang.String str) {
        this.mFileName = str;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public java.util.List<android.hardware.face.Face> getBiometricsForUser(android.content.Context context, int i) {
        return getStateForUser(context, i).getBiometrics();
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public void addBiometricForUser(android.content.Context context, int i, android.hardware.face.Face face) {
        getStateForUser(context, i).addBiometric(face);
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

    private com.android.server.biometrics.sensors.face.FaceUserState getStateForUser(android.content.Context context, int i) {
        com.android.server.biometrics.sensors.face.FaceUserState faceUserState;
        synchronized (this) {
            try {
                faceUserState = this.mUserStates.get(i);
                if (faceUserState == null) {
                    faceUserState = new com.android.server.biometrics.sensors.face.FaceUserState(context, i, this.mFileName);
                    this.mUserStates.put(i, faceUserState);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return faceUserState;
    }
}
