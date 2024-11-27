package com.android.server.biometrics;

/* loaded from: classes.dex */
public class BiometricHandlerProvider {
    private static final com.android.server.biometrics.BiometricHandlerProvider sBiometricHandlerProvider = new com.android.server.biometrics.BiometricHandlerProvider();
    private final android.os.Handler mBiometricsCallbackHandler = getNewHandler("BiometricsCallbackHandler");
    private final android.os.Handler mFingerprintHandler = getNewHandler("FingerprintHandler");
    private final android.os.Handler mFaceHandler = getNewHandler("FaceHandler");

    public static com.android.server.biometrics.BiometricHandlerProvider getInstance() {
        return sBiometricHandlerProvider;
    }

    private BiometricHandlerProvider() {
    }

    public synchronized android.os.Handler getBiometricCallbackHandler() {
        return this.mBiometricsCallbackHandler;
    }

    public synchronized android.os.Handler getFaceHandler() {
        return this.mFaceHandler;
    }

    public synchronized android.os.Handler getFingerprintHandler() {
        return this.mFingerprintHandler;
    }

    private android.os.Handler getNewHandler(java.lang.String str) {
        com.android.server.biometrics.Flags.deHidl();
        return new android.os.Handler(android.os.Looper.getMainLooper());
    }
}
