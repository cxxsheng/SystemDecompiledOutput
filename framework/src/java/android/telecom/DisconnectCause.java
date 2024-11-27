package android.telecom;

/* loaded from: classes3.dex */
public final class DisconnectCause implements android.os.Parcelable {
    public static final int ANSWERED_ELSEWHERE = 11;
    public static final int BUSY = 7;
    public static final int CALL_PULLED = 12;
    public static final int CANCELED = 4;
    public static final int CONNECTION_MANAGER_NOT_SUPPORTED = 10;
    public static final android.os.Parcelable.Creator<android.telecom.DisconnectCause> CREATOR = new android.os.Parcelable.Creator<android.telecom.DisconnectCause>() { // from class: android.telecom.DisconnectCause.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.DisconnectCause createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.DisconnectCause(parcel.readInt(), android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel), android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), (android.telephony.ims.ImsReasonInfo) parcel.readParcelable(null, android.telephony.ims.ImsReasonInfo.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.DisconnectCause[] newArray(int i) {
            return new android.telecom.DisconnectCause[i];
        }
    };
    public static final int ERROR = 1;
    public static final int LOCAL = 2;
    public static final int MISSED = 5;
    public static final int OTHER = 9;
    public static final java.lang.String REASON_EMERGENCY_CALL_PLACED = "REASON_EMERGENCY_CALL_PLACED";
    public static final java.lang.String REASON_EMULATING_SINGLE_CALL = "EMULATING_SINGLE_CALL";
    public static final java.lang.String REASON_IMS_ACCESS_BLOCKED = "REASON_IMS_ACCESS_BLOCKED";
    public static final java.lang.String REASON_WIFI_ON_BUT_WFC_OFF = "REASON_WIFI_ON_BUT_WFC_OFF";
    public static final int REJECTED = 6;
    public static final int REMOTE = 3;
    public static final int RESTRICTED = 8;
    public static final int UNKNOWN = 0;
    private int mDisconnectCode;
    private java.lang.CharSequence mDisconnectDescription;
    private java.lang.CharSequence mDisconnectLabel;
    private java.lang.String mDisconnectReason;
    private android.telephony.ims.ImsReasonInfo mImsReasonInfo;
    private int mTelephonyDisconnectCause;
    private int mTelephonyPreciseDisconnectCause;
    private int mToneToPlay;

    public DisconnectCause(int i) {
        this(i, null, null, null, -1);
    }

    public DisconnectCause(int i, java.lang.String str) {
        this(i, null, null, str, -1);
    }

    public DisconnectCause(int i, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.String str) {
        this(i, charSequence, charSequence2, str, -1);
    }

    public DisconnectCause(int i, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.String str, int i2) {
        this(i, charSequence, charSequence2, str, i2, 36, 65535, null);
    }

    public DisconnectCause(int i, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.String str, int i2, int i3, int i4, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        this.mDisconnectCode = i;
        this.mDisconnectLabel = charSequence;
        this.mDisconnectDescription = charSequence2;
        this.mDisconnectReason = str;
        this.mToneToPlay = i2;
        this.mTelephonyDisconnectCause = i3;
        this.mTelephonyPreciseDisconnectCause = i4;
        this.mImsReasonInfo = imsReasonInfo;
    }

    public int getCode() {
        return this.mDisconnectCode;
    }

    public java.lang.CharSequence getLabel() {
        return this.mDisconnectLabel;
    }

    public java.lang.CharSequence getDescription() {
        return this.mDisconnectDescription;
    }

    public java.lang.String getReason() {
        return this.mDisconnectReason;
    }

    @android.annotation.SystemApi
    public int getTelephonyDisconnectCause() {
        return this.mTelephonyDisconnectCause;
    }

    @android.annotation.SystemApi
    public int getTelephonyPreciseDisconnectCause() {
        return this.mTelephonyPreciseDisconnectCause;
    }

    @android.annotation.SystemApi
    public android.telephony.ims.ImsReasonInfo getImsReasonInfo() {
        return this.mImsReasonInfo;
    }

    public int getTone() {
        return this.mToneToPlay;
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private int mDisconnectCode;
        private java.lang.CharSequence mDisconnectDescription;
        private java.lang.CharSequence mDisconnectLabel;
        private java.lang.String mDisconnectReason;
        private android.telephony.ims.ImsReasonInfo mImsReasonInfo;
        private int mTelephonyDisconnectCause;
        private int mTelephonyPreciseDisconnectCause;
        private int mToneToPlay;

        public android.telecom.DisconnectCause.Builder setCode(int i) {
            this.mDisconnectCode = i;
            return this;
        }

        public android.telecom.DisconnectCause.Builder setLabel(java.lang.CharSequence charSequence) {
            this.mDisconnectLabel = charSequence;
            return this;
        }

        public android.telecom.DisconnectCause.Builder setDescription(java.lang.CharSequence charSequence) {
            this.mDisconnectDescription = charSequence;
            return this;
        }

        public android.telecom.DisconnectCause.Builder setReason(java.lang.String str) {
            this.mDisconnectReason = str;
            return this;
        }

        public android.telecom.DisconnectCause.Builder setTone(int i) {
            this.mToneToPlay = i;
            return this;
        }

        public android.telecom.DisconnectCause.Builder setTelephonyDisconnectCause(int i) {
            this.mTelephonyDisconnectCause = i;
            return this;
        }

        public android.telecom.DisconnectCause.Builder setTelephonyPreciseDisconnectCause(int i) {
            this.mTelephonyPreciseDisconnectCause = i;
            return this;
        }

        public android.telecom.DisconnectCause.Builder setImsReasonInfo(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
            this.mImsReasonInfo = imsReasonInfo;
            return this;
        }

        public android.telecom.DisconnectCause build() {
            return new android.telecom.DisconnectCause(this.mDisconnectCode, this.mDisconnectLabel, this.mDisconnectDescription, this.mDisconnectReason, this.mToneToPlay, this.mTelephonyDisconnectCause, this.mTelephonyPreciseDisconnectCause, this.mImsReasonInfo);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mDisconnectCode);
        android.text.TextUtils.writeToParcel(this.mDisconnectLabel, parcel, i);
        android.text.TextUtils.writeToParcel(this.mDisconnectDescription, parcel, i);
        parcel.writeString(this.mDisconnectReason);
        parcel.writeInt(this.mToneToPlay);
        parcel.writeInt(this.mTelephonyDisconnectCause);
        parcel.writeInt(this.mTelephonyPreciseDisconnectCause);
        parcel.writeParcelable(this.mImsReasonInfo, 0);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return java.util.Objects.hashCode(java.lang.Integer.valueOf(this.mDisconnectCode)) + java.util.Objects.hashCode(this.mDisconnectLabel) + java.util.Objects.hashCode(this.mDisconnectDescription) + java.util.Objects.hashCode(this.mDisconnectReason) + java.util.Objects.hashCode(java.lang.Integer.valueOf(this.mToneToPlay)) + java.util.Objects.hashCode(java.lang.Integer.valueOf(this.mTelephonyDisconnectCause)) + java.util.Objects.hashCode(java.lang.Integer.valueOf(this.mTelephonyPreciseDisconnectCause)) + java.util.Objects.hashCode(this.mImsReasonInfo);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telecom.DisconnectCause)) {
            return false;
        }
        android.telecom.DisconnectCause disconnectCause = (android.telecom.DisconnectCause) obj;
        return java.util.Objects.equals(java.lang.Integer.valueOf(this.mDisconnectCode), java.lang.Integer.valueOf(disconnectCause.getCode())) && java.util.Objects.equals(this.mDisconnectLabel, disconnectCause.getLabel()) && java.util.Objects.equals(this.mDisconnectDescription, disconnectCause.getDescription()) && java.util.Objects.equals(this.mDisconnectReason, disconnectCause.getReason()) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mToneToPlay), java.lang.Integer.valueOf(disconnectCause.getTone())) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mTelephonyDisconnectCause), java.lang.Integer.valueOf(disconnectCause.getTelephonyDisconnectCause())) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mTelephonyPreciseDisconnectCause), java.lang.Integer.valueOf(disconnectCause.getTelephonyPreciseDisconnectCause())) && java.util.Objects.equals(this.mImsReasonInfo, disconnectCause.getImsReasonInfo());
    }

    public java.lang.String toString() {
        java.lang.String str;
        switch (this.mDisconnectCode) {
            case 0:
                str = "UNKNOWN";
                break;
            case 1:
                str = android.service.timezone.TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY;
                break;
            case 2:
                str = android.provider.CalendarContract.ACCOUNT_TYPE_LOCAL;
                break;
            case 3:
                str = "REMOTE";
                break;
            case 4:
                str = "CANCELED";
                break;
            case 5:
                str = "MISSED";
                break;
            case 6:
                str = "REJECTED";
                break;
            case 7:
                str = "BUSY";
                break;
            case 8:
                str = "RESTRICTED";
                break;
            case 9:
                str = "OTHER";
                break;
            case 10:
                str = "CONNECTION_MANAGER_NOT_SUPPORTED";
                break;
            case 11:
                str = "ANSWERED_ELSEWHERE";
                break;
            case 12:
                str = "CALL_PULLED";
                break;
            default:
                str = "invalid code: " + this.mDisconnectCode;
                break;
        }
        return "DisconnectCause [ Code: (" + str + ") Label: (" + (this.mDisconnectLabel == null ? "" : this.mDisconnectLabel.toString()) + ") Description: (" + (this.mDisconnectDescription == null ? "" : this.mDisconnectDescription.toString()) + ") Reason: (" + (this.mDisconnectReason != null ? this.mDisconnectReason : "") + ") Tone: (" + this.mToneToPlay + ")  TelephonyCause: " + this.mTelephonyDisconnectCause + "/" + this.mTelephonyPreciseDisconnectCause + " ImsReasonInfo: " + this.mImsReasonInfo + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
