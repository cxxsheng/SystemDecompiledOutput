package android.hardware.usb;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class DisplayPortAltModeInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.usb.DisplayPortAltModeInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.usb.DisplayPortAltModeInfo>() { // from class: android.hardware.usb.DisplayPortAltModeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.DisplayPortAltModeInfo createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.usb.DisplayPortAltModeInfo(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readBoolean(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.DisplayPortAltModeInfo[] newArray(int i) {
            return new android.hardware.usb.DisplayPortAltModeInfo[i];
        }
    };
    public static final int DISPLAYPORT_ALT_MODE_STATUS_CAPABLE_DISABLED = 2;
    public static final int DISPLAYPORT_ALT_MODE_STATUS_ENABLED = 3;
    public static final int DISPLAYPORT_ALT_MODE_STATUS_NOT_CAPABLE = 1;
    public static final int DISPLAYPORT_ALT_MODE_STATUS_UNKNOWN = 0;
    public static final int LINK_TRAINING_STATUS_FAILURE = 2;
    public static final int LINK_TRAINING_STATUS_SUCCESS = 1;
    public static final int LINK_TRAINING_STATUS_UNKNOWN = 0;
    private final int mCableStatus;
    private final boolean mHotPlugDetect;
    private final int mLinkTrainingStatus;
    private final int mNumLanes;
    private final int mPartnerSinkStatus;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DisplayPortAltModeStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LinkTrainingStatus {
    }

    public DisplayPortAltModeInfo() {
        this.mPartnerSinkStatus = 0;
        this.mCableStatus = 0;
        this.mNumLanes = 0;
        this.mHotPlugDetect = false;
        this.mLinkTrainingStatus = 0;
    }

    public DisplayPortAltModeInfo(int i, int i2, int i3, boolean z, int i4) {
        this.mPartnerSinkStatus = i;
        this.mCableStatus = i2;
        this.mNumLanes = i3;
        this.mHotPlugDetect = z;
        this.mLinkTrainingStatus = i4;
    }

    public int getPartnerSinkStatus() {
        return this.mPartnerSinkStatus;
    }

    public int getCableStatus() {
        return this.mCableStatus;
    }

    public int getNumberOfLanes() {
        return this.mNumLanes;
    }

    public boolean isHotPlugDetectActive() {
        return this.mHotPlugDetect;
    }

    public int getLinkTrainingStatus() {
        return this.mLinkTrainingStatus;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPartnerSinkStatus);
        parcel.writeInt(this.mCableStatus);
        parcel.writeInt(this.mNumLanes);
        parcel.writeBoolean(this.mHotPlugDetect);
        parcel.writeInt(this.mLinkTrainingStatus);
    }

    private java.lang.String displayPortAltModeStatusToString(int i) {
        switch (i) {
            case 1:
                return "not capable";
            case 2:
                return "capable disabled";
            case 3:
                return "enabled";
            default:
                return "unknown";
        }
    }

    private java.lang.String linkTrainingStatusToString(int i) {
        switch (i) {
            case 1:
                return "success";
            case 2:
                return "failure";
            default:
                return "unknown";
        }
    }

    public java.lang.String toString() {
        return "DisplayPortAltModeInfo{partnerSink=" + displayPortAltModeStatusToString(this.mPartnerSinkStatus) + ", cable=" + displayPortAltModeStatusToString(this.mCableStatus) + ", numLanes=" + this.mNumLanes + ", hotPlugDetect=" + this.mHotPlugDetect + ", linkTrainingStatus=" + linkTrainingStatusToString(this.mLinkTrainingStatus) + "}";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.usb.DisplayPortAltModeInfo)) {
            return false;
        }
        android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo = (android.hardware.usb.DisplayPortAltModeInfo) obj;
        return this.mPartnerSinkStatus == displayPortAltModeInfo.mPartnerSinkStatus && this.mCableStatus == displayPortAltModeInfo.mCableStatus && this.mNumLanes == displayPortAltModeInfo.mNumLanes && this.mHotPlugDetect == displayPortAltModeInfo.mHotPlugDetect && this.mLinkTrainingStatus == displayPortAltModeInfo.mLinkTrainingStatus;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mPartnerSinkStatus), java.lang.Integer.valueOf(this.mCableStatus), java.lang.Integer.valueOf(this.mNumLanes), java.lang.Boolean.valueOf(this.mHotPlugDetect), java.lang.Integer.valueOf(this.mLinkTrainingStatus));
    }
}
