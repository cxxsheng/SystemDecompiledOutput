package android.hardware;

/* loaded from: classes.dex */
public class CameraStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.CameraStatus> CREATOR = new android.os.Parcelable.Creator<android.hardware.CameraStatus>() { // from class: android.hardware.CameraStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.CameraStatus createFromParcel(android.os.Parcel parcel) {
            android.hardware.CameraStatus cameraStatus = new android.hardware.CameraStatus();
            cameraStatus.readFromParcel(parcel);
            return cameraStatus;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.CameraStatus[] newArray(int i) {
            return new android.hardware.CameraStatus[i];
        }
    };
    public java.lang.String cameraId;
    public java.lang.String clientPackage;
    public int status;
    public java.lang.String[] unavailablePhysicalCameras;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.cameraId);
        parcel.writeInt(this.status);
        parcel.writeStringArray(this.unavailablePhysicalCameras);
        parcel.writeString(this.clientPackage);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.cameraId = parcel.readString();
        this.status = parcel.readInt();
        this.unavailablePhysicalCameras = parcel.readStringArray();
        this.clientPackage = parcel.readString();
    }
}
