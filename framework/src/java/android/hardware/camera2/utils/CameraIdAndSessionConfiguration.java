package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class CameraIdAndSessionConfiguration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.camera2.utils.CameraIdAndSessionConfiguration> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.utils.CameraIdAndSessionConfiguration>() { // from class: android.hardware.camera2.utils.CameraIdAndSessionConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.utils.CameraIdAndSessionConfiguration createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.camera2.utils.CameraIdAndSessionConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.utils.CameraIdAndSessionConfiguration[] newArray(int i) {
            return new android.hardware.camera2.utils.CameraIdAndSessionConfiguration[i];
        }
    };
    private java.lang.String mCameraId;
    private android.hardware.camera2.params.SessionConfiguration mSessionConfiguration;

    public CameraIdAndSessionConfiguration(java.lang.String str, android.hardware.camera2.params.SessionConfiguration sessionConfiguration) {
        this.mCameraId = str;
        this.mSessionConfiguration = sessionConfiguration;
    }

    private CameraIdAndSessionConfiguration(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mCameraId);
        this.mSessionConfiguration.writeToParcel(parcel, i);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mCameraId = parcel.readString();
        this.mSessionConfiguration = android.hardware.camera2.params.SessionConfiguration.CREATOR.createFromParcel(parcel);
    }

    public java.lang.String getCameraId() {
        return this.mCameraId;
    }

    public android.hardware.camera2.params.SessionConfiguration getSessionConfiguration() {
        return this.mSessionConfiguration;
    }
}
