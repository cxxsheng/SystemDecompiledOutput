package android.media.tv;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class TvInputHardwareInfo implements android.os.Parcelable {
    public static final int CABLE_CONNECTION_STATUS_CONNECTED = 1;
    public static final int CABLE_CONNECTION_STATUS_DISCONNECTED = 2;
    public static final int CABLE_CONNECTION_STATUS_UNKNOWN = 0;
    public static final android.os.Parcelable.Creator<android.media.tv.TvInputHardwareInfo> CREATOR = new android.os.Parcelable.Creator<android.media.tv.TvInputHardwareInfo>() { // from class: android.media.tv.TvInputHardwareInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TvInputHardwareInfo createFromParcel(android.os.Parcel parcel) {
            try {
                android.media.tv.TvInputHardwareInfo tvInputHardwareInfo = new android.media.tv.TvInputHardwareInfo();
                tvInputHardwareInfo.readFromParcel(parcel);
                return tvInputHardwareInfo;
            } catch (java.lang.Exception e) {
                android.util.Log.e(android.media.tv.TvInputHardwareInfo.TAG, "Exception creating TvInputHardwareInfo from parcel", e);
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TvInputHardwareInfo[] newArray(int i) {
            return new android.media.tv.TvInputHardwareInfo[i];
        }
    };
    static final java.lang.String TAG = "TvInputHardwareInfo";
    public static final int TV_INPUT_TYPE_COMPONENT = 6;
    public static final int TV_INPUT_TYPE_COMPOSITE = 3;
    public static final int TV_INPUT_TYPE_DISPLAY_PORT = 10;
    public static final int TV_INPUT_TYPE_DVI = 8;
    public static final int TV_INPUT_TYPE_HDMI = 9;
    public static final int TV_INPUT_TYPE_OTHER_HARDWARE = 1;
    public static final int TV_INPUT_TYPE_SCART = 5;
    public static final int TV_INPUT_TYPE_SVIDEO = 4;
    public static final int TV_INPUT_TYPE_TUNER = 2;
    public static final int TV_INPUT_TYPE_VGA = 7;
    private java.lang.String mAudioAddress;
    private int mAudioType;
    private int mCableConnectionStatus;
    private int mDeviceId;
    private int mHdmiPortId;
    private int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CableConnectionStatus {
    }

    private TvInputHardwareInfo() {
    }

    public int getDeviceId() {
        return this.mDeviceId;
    }

    public int getType() {
        return this.mType;
    }

    public int getAudioType() {
        return this.mAudioType;
    }

    public java.lang.String getAudioAddress() {
        return this.mAudioAddress;
    }

    public int getHdmiPortId() {
        if (this.mType != 9) {
            throw new java.lang.IllegalStateException();
        }
        return this.mHdmiPortId;
    }

    public int getCableConnectionStatus() {
        return this.mCableConnectionStatus;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("TvInputHardwareInfo {id=").append(this.mDeviceId);
        sb.append(", type=").append(this.mType);
        sb.append(", audio_type=").append(this.mAudioType);
        sb.append(", audio_addr=").append(this.mAudioAddress);
        if (this.mType == 9) {
            sb.append(", hdmi_port=").append(this.mHdmiPortId);
        }
        sb.append(", cable_connection_status=").append(this.mCableConnectionStatus);
        sb.append("}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mDeviceId);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mAudioType);
        parcel.writeString(this.mAudioAddress);
        if (this.mType == 9) {
            parcel.writeInt(this.mHdmiPortId);
        }
        parcel.writeInt(this.mCableConnectionStatus);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mDeviceId = parcel.readInt();
        this.mType = parcel.readInt();
        this.mAudioType = parcel.readInt();
        this.mAudioAddress = parcel.readString();
        if (this.mType == 9) {
            this.mHdmiPortId = parcel.readInt();
        }
        this.mCableConnectionStatus = parcel.readInt();
    }

    public android.media.tv.TvInputHardwareInfo.Builder toBuilder() {
        android.media.tv.TvInputHardwareInfo.Builder cableConnectionStatus = new android.media.tv.TvInputHardwareInfo.Builder().deviceId(this.mDeviceId).type(this.mType).audioType(this.mAudioType).audioAddress(this.mAudioAddress).cableConnectionStatus(this.mCableConnectionStatus);
        if (this.mType == 9) {
            cableConnectionStatus.hdmiPortId(this.mHdmiPortId);
        }
        return cableConnectionStatus;
    }

    public static final class Builder {
        private java.lang.Integer mDeviceId = null;
        private java.lang.Integer mType = null;
        private int mAudioType = 0;
        private java.lang.String mAudioAddress = "";
        private java.lang.Integer mHdmiPortId = null;
        private java.lang.Integer mCableConnectionStatus = 0;

        public android.media.tv.TvInputHardwareInfo.Builder deviceId(int i) {
            this.mDeviceId = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.media.tv.TvInputHardwareInfo.Builder type(int i) {
            this.mType = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.media.tv.TvInputHardwareInfo.Builder audioType(int i) {
            this.mAudioType = i;
            return this;
        }

        public android.media.tv.TvInputHardwareInfo.Builder audioAddress(java.lang.String str) {
            this.mAudioAddress = str;
            return this;
        }

        public android.media.tv.TvInputHardwareInfo.Builder hdmiPortId(int i) {
            this.mHdmiPortId = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.media.tv.TvInputHardwareInfo.Builder cableConnectionStatus(int i) {
            this.mCableConnectionStatus = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.media.tv.TvInputHardwareInfo build() {
            if (this.mDeviceId == null || this.mType == null) {
                throw new java.lang.UnsupportedOperationException();
            }
            if ((this.mType.intValue() == 9 && this.mHdmiPortId == null) || (this.mType.intValue() != 9 && this.mHdmiPortId != null)) {
                throw new java.lang.UnsupportedOperationException();
            }
            android.media.tv.TvInputHardwareInfo tvInputHardwareInfo = new android.media.tv.TvInputHardwareInfo();
            tvInputHardwareInfo.mDeviceId = this.mDeviceId.intValue();
            tvInputHardwareInfo.mType = this.mType.intValue();
            tvInputHardwareInfo.mAudioType = this.mAudioType;
            if (tvInputHardwareInfo.mAudioType != 0) {
                tvInputHardwareInfo.mAudioAddress = this.mAudioAddress;
            }
            if (this.mHdmiPortId != null) {
                tvInputHardwareInfo.mHdmiPortId = this.mHdmiPortId.intValue();
            }
            tvInputHardwareInfo.mCableConnectionStatus = this.mCableConnectionStatus.intValue();
            return tvInputHardwareInfo;
        }
    }
}
