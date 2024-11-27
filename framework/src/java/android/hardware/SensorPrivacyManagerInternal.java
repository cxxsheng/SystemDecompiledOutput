package android.hardware;

/* loaded from: classes.dex */
public abstract class SensorPrivacyManagerInternal {

    public interface OnSensorPrivacyChangedListener {
        void onSensorPrivacyChanged(boolean z);
    }

    public interface OnUserSensorPrivacyChangedListener {
        void onSensorPrivacyChanged(int i, boolean z);
    }

    public abstract void addSensorPrivacyListener(int i, int i2, android.hardware.SensorPrivacyManagerInternal.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener);

    public abstract void addSensorPrivacyListenerForAllUsers(int i, android.hardware.SensorPrivacyManagerInternal.OnUserSensorPrivacyChangedListener onUserSensorPrivacyChangedListener);

    public abstract boolean isSensorPrivacyEnabled(int i, int i2);

    public abstract void setPhysicalToggleSensorPrivacy(int i, int i2, boolean z);
}
