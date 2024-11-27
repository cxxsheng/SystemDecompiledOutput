package android.hardware.biometrics;

/* loaded from: classes.dex */
public class ComponentInfoInternal implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.biometrics.ComponentInfoInternal> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.ComponentInfoInternal>() { // from class: android.hardware.biometrics.ComponentInfoInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.ComponentInfoInternal createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.biometrics.ComponentInfoInternal(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.ComponentInfoInternal[] newArray(int i) {
            return new android.hardware.biometrics.ComponentInfoInternal[i];
        }
    };
    public final java.lang.String componentId;
    public final java.lang.String firmwareVersion;
    public final java.lang.String hardwareVersion;
    public final java.lang.String serialNumber;
    public final java.lang.String softwareVersion;

    public static android.hardware.biometrics.ComponentInfoInternal from(android.hardware.biometrics.ComponentInfoInternal componentInfoInternal) {
        return new android.hardware.biometrics.ComponentInfoInternal(componentInfoInternal.componentId, componentInfoInternal.hardwareVersion, componentInfoInternal.firmwareVersion, componentInfoInternal.serialNumber, componentInfoInternal.softwareVersion);
    }

    public ComponentInfoInternal(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
        this.componentId = str;
        this.hardwareVersion = str2;
        this.firmwareVersion = str3;
        this.serialNumber = str4;
        this.softwareVersion = str5;
    }

    protected ComponentInfoInternal(android.os.Parcel parcel) {
        this.componentId = parcel.readString();
        this.hardwareVersion = parcel.readString();
        this.firmwareVersion = parcel.readString();
        this.serialNumber = parcel.readString();
        this.softwareVersion = parcel.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.componentId);
        parcel.writeString(this.hardwareVersion);
        parcel.writeString(this.firmwareVersion);
        parcel.writeString(this.serialNumber);
        parcel.writeString(this.softwareVersion);
    }

    public java.lang.String toString() {
        return "ComponentId: " + this.componentId + ", HardwareVersion: " + this.hardwareVersion + ", FirmwareVersion: " + this.firmwareVersion + ", SerialNumber " + this.serialNumber + ", SoftwareVersion: " + this.softwareVersion;
    }
}
