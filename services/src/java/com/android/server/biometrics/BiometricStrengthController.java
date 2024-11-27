package com.android.server.biometrics;

/* loaded from: classes.dex */
public class BiometricStrengthController {
    private static final java.lang.String KEY_BIOMETRIC_STRENGTHS = "biometric_strengths";
    private static final java.lang.String TAG = "BiometricStrengthController";
    private android.provider.DeviceConfig.OnPropertiesChangedListener mDeviceConfigListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.biometrics.BiometricStrengthController$$ExternalSyntheticLambda0
        public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            com.android.server.biometrics.BiometricStrengthController.this.lambda$new$0(properties);
        }
    };
    private final com.android.server.biometrics.BiometricService mService;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.provider.DeviceConfig.Properties properties) {
        if (properties.getKeyset().contains(KEY_BIOMETRIC_STRENGTHS)) {
            updateStrengths();
        }
    }

    public BiometricStrengthController(@android.annotation.NonNull com.android.server.biometrics.BiometricService biometricService) {
        this.mService = biometricService;
    }

    public void startListening() {
        android.provider.DeviceConfig.addOnPropertiesChangedListener("biometrics", com.android.internal.os.BackgroundThread.getExecutor(), this.mDeviceConfigListener);
    }

    public void updateStrengths() {
        java.lang.String string = android.provider.DeviceConfig.getString("biometrics", KEY_BIOMETRIC_STRENGTHS, "null");
        if ("null".equals(string) || string.isEmpty()) {
            revertStrengths();
        } else {
            updateStrengths(string);
        }
    }

    private void updateStrengths(java.lang.String str) {
        java.util.Map<java.lang.Integer, java.lang.Integer> idToStrengthMap = getIdToStrengthMap(str);
        if (idToStrengthMap == null) {
            return;
        }
        java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = this.mService.mSensors.iterator();
        while (it.hasNext()) {
            com.android.server.biometrics.BiometricSensor next = it.next();
            int i = next.id;
            if (idToStrengthMap.containsKey(java.lang.Integer.valueOf(i))) {
                int intValue = idToStrengthMap.get(java.lang.Integer.valueOf(i)).intValue();
                android.util.Slog.d(TAG, "updateStrengths: update sensorId=" + i + " to newStrength=" + intValue);
                next.updateStrength(intValue);
            }
        }
    }

    private void revertStrengths() {
        java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = this.mService.mSensors.iterator();
        while (it.hasNext()) {
            com.android.server.biometrics.BiometricSensor next = it.next();
            android.util.Slog.d(TAG, "updateStrengths: revert sensorId=" + next.id + " to oemStrength=" + next.oemStrength);
            next.updateStrength(next.oemStrength);
        }
    }

    private static java.util.Map<java.lang.Integer, java.lang.Integer> getIdToStrengthMap(java.lang.String str) {
        if (str == null || str.isEmpty()) {
            android.util.Slog.d(TAG, "Flags are null or empty");
            return null;
        }
        java.util.HashMap hashMap = new java.util.HashMap();
        try {
            for (java.lang.String str2 : str.split(",")) {
                java.lang.String[] split = str2.split(":");
                hashMap.put(java.lang.Integer.valueOf(java.lang.Integer.parseInt(split[0])), java.lang.Integer.valueOf(java.lang.Integer.parseInt(split[1])));
            }
            return hashMap;
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Can't parse flag: " + str);
            return null;
        }
    }
}
