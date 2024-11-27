package android.hardware.fingerprint;

/* loaded from: classes2.dex */
public final class HidlFingerprintSensorConfig extends android.hardware.biometrics.fingerprint.SensorProps {
    private int mModality;
    private int mSensorId;
    private int mStrength;

    public void parse(java.lang.String str, android.content.Context context) throws java.lang.IllegalArgumentException {
        java.lang.String[] split = str.split(":");
        if (split.length < 3) {
            throw new java.lang.IllegalArgumentException();
        }
        this.mSensorId = java.lang.Integer.parseInt(split[0]);
        this.mModality = java.lang.Integer.parseInt(split[1]);
        this.mStrength = java.lang.Integer.parseInt(split[2]);
        mapHidlToAidlSensorConfiguration(context);
    }

    public int getModality() {
        return this.mModality;
    }

    private void mapHidlToAidlSensorConfiguration(android.content.Context context) {
        this.commonProps = new android.hardware.biometrics.common.CommonProps();
        this.commonProps.componentInfo = null;
        this.commonProps.sensorId = this.mSensorId;
        this.commonProps.sensorStrength = authenticatorStrengthToPropertyStrength(this.mStrength);
        this.commonProps.maxEnrollmentsPerUser = context.getResources().getInteger(com.android.internal.R.integer.config_fingerprintMaxTemplatesPerUser);
        this.halControlsIllumination = false;
        this.sensorLocations = new android.hardware.biometrics.fingerprint.SensorLocation[1];
        int[] intArray = context.getResources().getIntArray(com.android.internal.R.array.config_udfps_sensor_props);
        boolean z = !com.android.internal.util.ArrayUtils.isEmpty(intArray);
        boolean z2 = context.getResources().getBoolean(com.android.internal.R.bool.config_is_powerbutton_fps);
        if (z) {
            this.sensorType = (byte) 0;
        } else if (z2) {
            this.sensorType = (byte) 4;
        } else {
            this.sensorType = (byte) 1;
        }
        if (z && intArray.length == 3) {
            setSensorLocation(intArray[0], intArray[1], intArray[2]);
        } else {
            setSensorLocation(540, com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_SECOND_ENTITY_TYPE, 130);
        }
    }

    private void setSensorLocation(int i, int i2, int i3) {
        this.sensorLocations[0] = new android.hardware.biometrics.fingerprint.SensorLocation();
        this.sensorLocations[0].display = "";
        this.sensorLocations[0].sensorLocationX = i;
        this.sensorLocations[0].sensorLocationY = i2;
        this.sensorLocations[0].sensorRadius = i3;
    }

    private byte authenticatorStrengthToPropertyStrength(int i) {
        switch (i) {
            case 15:
                return (byte) 2;
            case 255:
                return (byte) 1;
            case 4095:
                return (byte) 0;
            default:
                throw new java.lang.IllegalArgumentException("Unknown strength: " + i);
        }
    }
}
