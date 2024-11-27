package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class PhysicalCaptureResultInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.camera2.impl.PhysicalCaptureResultInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.impl.PhysicalCaptureResultInfo>() { // from class: android.hardware.camera2.impl.PhysicalCaptureResultInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.impl.PhysicalCaptureResultInfo createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.camera2.impl.PhysicalCaptureResultInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.impl.PhysicalCaptureResultInfo[] newArray(int i) {
            return new android.hardware.camera2.impl.PhysicalCaptureResultInfo[i];
        }
    };
    private java.lang.String cameraId;
    private android.hardware.camera2.impl.CameraMetadataNative cameraMetadata;

    private PhysicalCaptureResultInfo(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public PhysicalCaptureResultInfo(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) {
        this.cameraId = str;
        this.cameraMetadata = cameraMetadataNative;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.cameraId);
        this.cameraMetadata.writeToParcel(parcel, i);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.cameraId = parcel.readString();
        this.cameraMetadata = new android.hardware.camera2.impl.CameraMetadataNative();
        this.cameraMetadata.readFromParcel(parcel);
    }

    public java.lang.String getCameraId() {
        return this.cameraId;
    }

    public android.hardware.camera2.impl.CameraMetadataNative getCameraMetadata() {
        return this.cameraMetadata;
    }
}
