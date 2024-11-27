package android.hardware;

/* loaded from: classes.dex */
public class CameraInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.CameraInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.CameraInfo>() { // from class: android.hardware.CameraInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.CameraInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.CameraInfo cameraInfo = new android.hardware.CameraInfo();
            cameraInfo.readFromParcel(parcel);
            return cameraInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.CameraInfo[] newArray(int i) {
            return new android.hardware.CameraInfo[i];
        }
    };
    public android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.info.facing);
        parcel.writeInt(this.info.orientation);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.info.facing = parcel.readInt();
        this.info.orientation = parcel.readInt();
    }
}
