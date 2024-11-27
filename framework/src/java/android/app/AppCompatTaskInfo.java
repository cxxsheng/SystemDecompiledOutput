package android.app;

/* loaded from: classes.dex */
public class AppCompatTaskInfo implements android.os.Parcelable {
    public static final int CAMERA_COMPAT_CONTROL_DISMISSED = 3;
    public static final int CAMERA_COMPAT_CONTROL_HIDDEN = 0;
    public static final int CAMERA_COMPAT_CONTROL_TREATMENT_APPLIED = 2;
    public static final int CAMERA_COMPAT_CONTROL_TREATMENT_SUGGESTED = 1;
    public static final android.os.Parcelable.Creator<android.app.AppCompatTaskInfo> CREATOR = new android.os.Parcelable.Creator<android.app.AppCompatTaskInfo>() { // from class: android.app.AppCompatTaskInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.AppCompatTaskInfo createFromParcel(android.os.Parcel parcel) {
            return new android.app.AppCompatTaskInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.AppCompatTaskInfo[] newArray(int i) {
            return new android.app.AppCompatTaskInfo[i];
        }
    };
    public int cameraCompatControlState;
    public boolean isFromLetterboxDoubleTap;
    public boolean isLetterboxDoubleTapEnabled;
    public boolean isSystemFullscreenOverrideEnabled;
    public boolean isUserFullscreenOverrideEnabled;
    public boolean topActivityBoundsLetterboxed;
    public boolean topActivityEligibleForLetterboxEducation;
    public boolean topActivityEligibleForUserAspectRatioButton;
    public boolean topActivityInSizeCompat;
    public int topActivityLetterboxHeight;
    public int topActivityLetterboxHorizontalPosition;
    public int topActivityLetterboxVerticalPosition;
    public int topActivityLetterboxWidth;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CameraCompatControlState {
    }

    private AppCompatTaskInfo() {
        this.cameraCompatControlState = 0;
    }

    static android.app.AppCompatTaskInfo create() {
        return new android.app.AppCompatTaskInfo();
    }

    private AppCompatTaskInfo(android.os.Parcel parcel) {
        this.cameraCompatControlState = 0;
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean hasCameraCompatControl() {
        return (this.cameraCompatControlState == 0 || this.cameraCompatControlState == 3) ? false : true;
    }

    public boolean hasCompatUI() {
        return hasCameraCompatControl() || this.topActivityInSizeCompat || this.topActivityEligibleForLetterboxEducation || this.isLetterboxDoubleTapEnabled || this.topActivityEligibleForUserAspectRatioButton;
    }

    public boolean isTopActivityPillarboxed() {
        return this.topActivityLetterboxWidth < this.topActivityLetterboxHeight;
    }

    public boolean equalsForTaskOrganizer(android.app.AppCompatTaskInfo appCompatTaskInfo) {
        return appCompatTaskInfo != null && this.isFromLetterboxDoubleTap == appCompatTaskInfo.isFromLetterboxDoubleTap && this.topActivityEligibleForUserAspectRatioButton == appCompatTaskInfo.topActivityEligibleForUserAspectRatioButton && this.topActivityLetterboxVerticalPosition == appCompatTaskInfo.topActivityLetterboxVerticalPosition && this.topActivityLetterboxWidth == appCompatTaskInfo.topActivityLetterboxWidth && this.topActivityLetterboxHeight == appCompatTaskInfo.topActivityLetterboxHeight && this.topActivityLetterboxHorizontalPosition == appCompatTaskInfo.topActivityLetterboxHorizontalPosition && this.isUserFullscreenOverrideEnabled == appCompatTaskInfo.isUserFullscreenOverrideEnabled && this.isSystemFullscreenOverrideEnabled == appCompatTaskInfo.isSystemFullscreenOverrideEnabled;
    }

    public boolean equalsForCompatUi(android.app.AppCompatTaskInfo appCompatTaskInfo) {
        return appCompatTaskInfo != null && this.topActivityInSizeCompat == appCompatTaskInfo.topActivityInSizeCompat && this.isFromLetterboxDoubleTap == appCompatTaskInfo.isFromLetterboxDoubleTap && this.topActivityEligibleForUserAspectRatioButton == appCompatTaskInfo.topActivityEligibleForUserAspectRatioButton && this.topActivityEligibleForLetterboxEducation == appCompatTaskInfo.topActivityEligibleForLetterboxEducation && this.topActivityLetterboxVerticalPosition == appCompatTaskInfo.topActivityLetterboxVerticalPosition && this.topActivityLetterboxHorizontalPosition == appCompatTaskInfo.topActivityLetterboxHorizontalPosition && this.topActivityLetterboxWidth == appCompatTaskInfo.topActivityLetterboxWidth && this.topActivityLetterboxHeight == appCompatTaskInfo.topActivityLetterboxHeight && this.cameraCompatControlState == appCompatTaskInfo.cameraCompatControlState && this.isUserFullscreenOverrideEnabled == appCompatTaskInfo.isUserFullscreenOverrideEnabled && this.isSystemFullscreenOverrideEnabled == appCompatTaskInfo.isSystemFullscreenOverrideEnabled;
    }

    void readFromParcel(android.os.Parcel parcel) {
        this.topActivityInSizeCompat = parcel.readBoolean();
        this.topActivityEligibleForLetterboxEducation = parcel.readBoolean();
        this.cameraCompatControlState = parcel.readInt();
        this.isLetterboxDoubleTapEnabled = parcel.readBoolean();
        this.topActivityEligibleForUserAspectRatioButton = parcel.readBoolean();
        this.topActivityBoundsLetterboxed = parcel.readBoolean();
        this.isFromLetterboxDoubleTap = parcel.readBoolean();
        this.topActivityLetterboxVerticalPosition = parcel.readInt();
        this.topActivityLetterboxHorizontalPosition = parcel.readInt();
        this.topActivityLetterboxWidth = parcel.readInt();
        this.topActivityLetterboxHeight = parcel.readInt();
        this.isUserFullscreenOverrideEnabled = parcel.readBoolean();
        this.isSystemFullscreenOverrideEnabled = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.topActivityInSizeCompat);
        parcel.writeBoolean(this.topActivityEligibleForLetterboxEducation);
        parcel.writeInt(this.cameraCompatControlState);
        parcel.writeBoolean(this.isLetterboxDoubleTapEnabled);
        parcel.writeBoolean(this.topActivityEligibleForUserAspectRatioButton);
        parcel.writeBoolean(this.topActivityBoundsLetterboxed);
        parcel.writeBoolean(this.isFromLetterboxDoubleTap);
        parcel.writeInt(this.topActivityLetterboxVerticalPosition);
        parcel.writeInt(this.topActivityLetterboxHorizontalPosition);
        parcel.writeInt(this.topActivityLetterboxWidth);
        parcel.writeInt(this.topActivityLetterboxHeight);
        parcel.writeBoolean(this.isUserFullscreenOverrideEnabled);
        parcel.writeBoolean(this.isSystemFullscreenOverrideEnabled);
    }

    public java.lang.String toString() {
        return "AppCompatTaskInfo { topActivityInSizeCompat=" + this.topActivityInSizeCompat + " topActivityEligibleForLetterboxEducation= " + this.topActivityEligibleForLetterboxEducation + " isLetterboxDoubleTapEnabled= " + this.isLetterboxDoubleTapEnabled + " topActivityEligibleForUserAspectRatioButton= " + this.topActivityEligibleForUserAspectRatioButton + " topActivityBoundsLetterboxed= " + this.topActivityBoundsLetterboxed + " isFromLetterboxDoubleTap= " + this.isFromLetterboxDoubleTap + " topActivityLetterboxVerticalPosition= " + this.topActivityLetterboxVerticalPosition + " topActivityLetterboxHorizontalPosition= " + this.topActivityLetterboxHorizontalPosition + " topActivityLetterboxWidth=" + this.topActivityLetterboxWidth + " topActivityLetterboxHeight=" + this.topActivityLetterboxHeight + " isUserFullscreenOverrideEnabled=" + this.isUserFullscreenOverrideEnabled + " isSystemFullscreenOverrideEnabled=" + this.isSystemFullscreenOverrideEnabled + " cameraCompatControlState=" + cameraCompatControlStateToString(this.cameraCompatControlState) + "}";
    }

    public static java.lang.String cameraCompatControlStateToString(int i) {
        switch (i) {
            case 0:
                return "hidden";
            case 1:
                return "treatment-suggested";
            case 2:
                return "treatment-applied";
            case 3:
                return "dismissed";
            default:
                throw new java.lang.AssertionError("Unexpected camera compat control state: " + i);
        }
    }
}
