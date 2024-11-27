package android.hardware.face;

/* loaded from: classes2.dex */
public class FaceSensorProperties extends android.hardware.biometrics.SensorProperties {
    public static final int TYPE_IR = 2;
    public static final int TYPE_RGB = 1;
    public static final int TYPE_UNKNOWN = 0;
    final int mSensorType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SensorType {
    }

    public static android.hardware.face.FaceSensorProperties from(android.hardware.face.FaceSensorPropertiesInternal faceSensorPropertiesInternal) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.hardware.biometrics.ComponentInfoInternal> it = faceSensorPropertiesInternal.componentInfo.iterator();
        while (it.hasNext()) {
            arrayList.add(android.hardware.biometrics.SensorProperties.ComponentInfo.from(it.next()));
        }
        return new android.hardware.face.FaceSensorProperties(faceSensorPropertiesInternal.sensorId, faceSensorPropertiesInternal.sensorStrength, arrayList, faceSensorPropertiesInternal.sensorType);
    }

    public FaceSensorProperties(int i, int i2, java.util.List<android.hardware.biometrics.SensorProperties.ComponentInfo> list, int i3) {
        super(i, i2, list);
        this.mSensorType = i3;
    }

    public int getSensorType() {
        return this.mSensorType;
    }
}
