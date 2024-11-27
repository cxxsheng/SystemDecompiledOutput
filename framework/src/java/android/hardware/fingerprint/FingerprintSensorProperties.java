package android.hardware.fingerprint;

/* loaded from: classes2.dex */
public class FingerprintSensorProperties extends android.hardware.biometrics.SensorProperties {
    public static final int TYPE_HOME_BUTTON = 5;
    public static final int TYPE_POWER_BUTTON = 4;
    public static final int TYPE_REAR = 1;
    public static final int TYPE_UDFPS_OPTICAL = 3;
    public static final int TYPE_UDFPS_ULTRASONIC = 2;
    public static final int TYPE_UNKNOWN = 0;
    final int mSensorType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SensorType {
    }

    public static android.hardware.fingerprint.FingerprintSensorProperties from(android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.hardware.biometrics.ComponentInfoInternal> it = fingerprintSensorPropertiesInternal.componentInfo.iterator();
        while (it.hasNext()) {
            arrayList.add(android.hardware.biometrics.SensorProperties.ComponentInfo.from(it.next()));
        }
        return new android.hardware.fingerprint.FingerprintSensorProperties(fingerprintSensorPropertiesInternal.sensorId, fingerprintSensorPropertiesInternal.sensorStrength, arrayList, fingerprintSensorPropertiesInternal.sensorType);
    }

    public FingerprintSensorProperties(int i, int i2, java.util.List<android.hardware.biometrics.SensorProperties.ComponentInfo> list, int i3) {
        super(i, i2, list);
        this.mSensorType = i3;
    }

    public int getSensorType() {
        return this.mSensorType;
    }
}
