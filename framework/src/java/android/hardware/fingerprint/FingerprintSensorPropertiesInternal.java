package android.hardware.fingerprint;

/* loaded from: classes2.dex */
public class FingerprintSensorPropertiesInternal extends android.hardware.biometrics.SensorPropertiesInternal {
    public static final android.os.Parcelable.Creator<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> CREATOR = new android.os.Parcelable.Creator<android.hardware.fingerprint.FingerprintSensorPropertiesInternal>() { // from class: android.hardware.fingerprint.FingerprintSensorPropertiesInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.fingerprint.FingerprintSensorPropertiesInternal createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.fingerprint.FingerprintSensorPropertiesInternal(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.fingerprint.FingerprintSensorPropertiesInternal[] newArray(int i) {
            return new android.hardware.fingerprint.FingerprintSensorPropertiesInternal[i];
        }
    };
    public final boolean halControlsIllumination;
    private final java.util.List<android.hardware.biometrics.SensorLocationInternal> mSensorLocations;
    public final int sensorType;

    public FingerprintSensorPropertiesInternal(int i, int i2, int i3, java.util.List<android.hardware.biometrics.ComponentInfoInternal> list, int i4, boolean z, boolean z2, java.util.List<android.hardware.biometrics.SensorLocationInternal> list2) {
        super(i, i2, i3, list, z2, false);
        this.sensorType = i4;
        this.halControlsIllumination = z;
        this.mSensorLocations = java.util.List.copyOf(list2);
    }

    public FingerprintSensorPropertiesInternal(int i, int i2, int i3, java.util.List<android.hardware.biometrics.ComponentInfoInternal> list, int i4, boolean z) {
        this(i, i2, i3, list, i4, false, z, java.util.List.of(new android.hardware.biometrics.SensorLocationInternal("", 540, com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_SECOND_ENTITY_TYPE, 130)));
    }

    protected FingerprintSensorPropertiesInternal(android.os.Parcel parcel) {
        super(parcel);
        this.sensorType = parcel.readInt();
        this.halControlsIllumination = parcel.readBoolean();
        this.mSensorLocations = parcel.createTypedArrayList(android.hardware.biometrics.SensorLocationInternal.CREATOR);
    }

    @Override // android.hardware.biometrics.SensorPropertiesInternal, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.hardware.biometrics.SensorPropertiesInternal, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.sensorType);
        parcel.writeBoolean(this.halControlsIllumination);
        parcel.writeTypedList(this.mSensorLocations);
    }

    public boolean isAnyUdfpsType() {
        switch (this.sensorType) {
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }

    public boolean isAnySidefpsType() {
        switch (this.sensorType) {
            case 4:
                return true;
            default:
                return false;
        }
    }

    public android.hardware.biometrics.SensorLocationInternal getLocation() {
        android.hardware.biometrics.SensorLocationInternal location = getLocation("");
        return location != null ? location : android.hardware.biometrics.SensorLocationInternal.DEFAULT;
    }

    public android.hardware.biometrics.SensorLocationInternal getLocation(java.lang.String str) {
        for (android.hardware.biometrics.SensorLocationInternal sensorLocationInternal : this.mSensorLocations) {
            if (sensorLocationInternal.displayId.equals(str)) {
                return sensorLocationInternal;
            }
        }
        return null;
    }

    public java.util.List<android.hardware.biometrics.SensorLocationInternal> getAllLocations() {
        return this.mSensorLocations;
    }

    @Override // android.hardware.biometrics.SensorPropertiesInternal
    public java.lang.String toString() {
        return "ID: " + this.sensorId + ", Strength: " + this.sensorStrength + ", Type: " + this.sensorType;
    }
}
