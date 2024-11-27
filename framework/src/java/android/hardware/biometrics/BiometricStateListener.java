package android.hardware.biometrics;

/* loaded from: classes.dex */
public abstract class BiometricStateListener extends android.hardware.biometrics.IBiometricStateListener.Stub {
    public static final int ACTION_SENSOR_TOUCH = 0;
    public static final int STATE_AUTH_OTHER = 4;
    public static final int STATE_BP_AUTH = 3;
    public static final int STATE_ENROLLING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_KEYGUARD_AUTH = 2;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Action {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface State {
    }

    @Override // android.hardware.biometrics.IBiometricStateListener
    public void onStateChanged(int i) {
    }

    @Override // android.hardware.biometrics.IBiometricStateListener
    public void onBiometricAction(int i) {
    }

    @Override // android.hardware.biometrics.IBiometricStateListener
    public void onEnrollmentsChanged(int i, int i2, boolean z) {
    }
}
