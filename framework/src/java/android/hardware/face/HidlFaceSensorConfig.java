package android.hardware.face;

/* loaded from: classes2.dex */
public final class HidlFaceSensorConfig extends android.hardware.biometrics.face.SensorProps {
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
        mapHidlToAidlFaceSensorConfigurations(context);
    }

    public int getModality() {
        return this.mModality;
    }

    private void mapHidlToAidlFaceSensorConfigurations(android.content.Context context) {
        this.commonProps = new android.hardware.biometrics.common.CommonProps();
        this.commonProps.sensorId = this.mSensorId;
        this.commonProps.sensorStrength = authenticatorStrengthToPropertyStrength(this.mStrength);
        this.halControlsPreview = context.getResources().getBoolean(com.android.internal.R.bool.config_faceAuthSupportsSelfIllumination);
        this.commonProps.maxEnrollmentsPerUser = context.getResources().getInteger(com.android.internal.R.integer.config_faceMaxTemplatesPerUser);
        this.commonProps.componentInfo = null;
        this.supportsDetectInteraction = false;
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
