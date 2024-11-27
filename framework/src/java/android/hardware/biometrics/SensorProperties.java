package android.hardware.biometrics;

/* loaded from: classes.dex */
public class SensorProperties {
    public static final int STRENGTH_CONVENIENCE = 0;
    public static final int STRENGTH_STRONG = 2;
    public static final int STRENGTH_WEAK = 1;
    private final java.util.List<android.hardware.biometrics.SensorProperties.ComponentInfo> mComponentInfo;
    private final int mSensorId;
    private final int mSensorStrength;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Strength {
    }

    public static final class ComponentInfo {
        private final java.lang.String mComponentId;
        private final java.lang.String mFirmwareVersion;
        private final java.lang.String mHardwareVersion;
        private final java.lang.String mSerialNumber;
        private final java.lang.String mSoftwareVersion;

        public ComponentInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
            this.mComponentId = str;
            this.mHardwareVersion = str2;
            this.mFirmwareVersion = str3;
            this.mSerialNumber = str4;
            this.mSoftwareVersion = str5;
        }

        public java.lang.String getComponentId() {
            return this.mComponentId;
        }

        public java.lang.String getHardwareVersion() {
            return this.mHardwareVersion;
        }

        public java.lang.String getFirmwareVersion() {
            return this.mFirmwareVersion;
        }

        public java.lang.String getSerialNumber() {
            return this.mSerialNumber;
        }

        public java.lang.String getSoftwareVersion() {
            return this.mSoftwareVersion;
        }

        public static android.hardware.biometrics.SensorProperties.ComponentInfo from(android.hardware.biometrics.ComponentInfoInternal componentInfoInternal) {
            return new android.hardware.biometrics.SensorProperties.ComponentInfo(componentInfoInternal.componentId, componentInfoInternal.hardwareVersion, componentInfoInternal.firmwareVersion, componentInfoInternal.serialNumber, componentInfoInternal.softwareVersion);
        }
    }

    public SensorProperties(int i, int i2, java.util.List<android.hardware.biometrics.SensorProperties.ComponentInfo> list) {
        this.mSensorId = i;
        this.mSensorStrength = i2;
        this.mComponentInfo = list;
    }

    public int getSensorId() {
        return this.mSensorId;
    }

    public int getSensorStrength() {
        return this.mSensorStrength;
    }

    public java.util.List<android.hardware.biometrics.SensorProperties.ComponentInfo> getComponentInfo() {
        return this.mComponentInfo;
    }

    public static android.hardware.biometrics.SensorProperties from(android.hardware.biometrics.SensorPropertiesInternal sensorPropertiesInternal) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.hardware.biometrics.ComponentInfoInternal> it = sensorPropertiesInternal.componentInfo.iterator();
        while (it.hasNext()) {
            arrayList.add(android.hardware.biometrics.SensorProperties.ComponentInfo.from(it.next()));
        }
        return new android.hardware.biometrics.SensorProperties(sensorPropertiesInternal.sensorId, sensorPropertiesInternal.sensorStrength, arrayList);
    }
}
