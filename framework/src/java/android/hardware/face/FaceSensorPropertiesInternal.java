package android.hardware.face;

/* loaded from: classes2.dex */
public class FaceSensorPropertiesInternal extends android.hardware.biometrics.SensorPropertiesInternal {
    public static final android.os.Parcelable.Creator<android.hardware.face.FaceSensorPropertiesInternal> CREATOR = new android.os.Parcelable.Creator<android.hardware.face.FaceSensorPropertiesInternal>() { // from class: android.hardware.face.FaceSensorPropertiesInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceSensorPropertiesInternal createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.face.FaceSensorPropertiesInternal(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceSensorPropertiesInternal[] newArray(int i) {
            return new android.hardware.face.FaceSensorPropertiesInternal[i];
        }
    };
    public final int sensorType;
    public final boolean supportsFaceDetection;
    public final boolean supportsSelfIllumination;

    public FaceSensorPropertiesInternal(int i, int i2, int i3, java.util.List<android.hardware.biometrics.ComponentInfoInternal> list, int i4, boolean z, boolean z2, boolean z3) {
        super(i, i2, i3, list, true, z3);
        this.sensorType = i4;
        this.supportsFaceDetection = z;
        this.supportsSelfIllumination = z2;
    }

    protected FaceSensorPropertiesInternal(android.os.Parcel parcel) {
        super(parcel);
        this.sensorType = parcel.readInt();
        this.supportsFaceDetection = parcel.readBoolean();
        this.supportsSelfIllumination = parcel.readBoolean();
    }

    @Override // android.hardware.biometrics.SensorPropertiesInternal, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.hardware.biometrics.SensorPropertiesInternal, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.sensorType);
        parcel.writeBoolean(this.supportsFaceDetection);
        parcel.writeBoolean(this.supportsSelfIllumination);
    }

    @Override // android.hardware.biometrics.SensorPropertiesInternal
    public java.lang.String toString() {
        return "ID: " + this.sensorId + ", Strength: " + this.sensorStrength + ", Type: " + this.sensorType + ", SupportsFaceDetection: " + this.supportsFaceDetection;
    }
}
