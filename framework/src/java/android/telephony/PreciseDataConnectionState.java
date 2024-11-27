package android.telephony;

/* loaded from: classes3.dex */
public final class PreciseDataConnectionState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.PreciseDataConnectionState> CREATOR = new android.os.Parcelable.Creator<android.telephony.PreciseDataConnectionState>() { // from class: android.telephony.PreciseDataConnectionState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.PreciseDataConnectionState createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.PreciseDataConnectionState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.PreciseDataConnectionState[] newArray(int i) {
            return new android.telephony.PreciseDataConnectionState[i];
        }
    };
    private static final long GET_DATA_CONNECTION_STATE_R_VERSION = 148535736;
    public static final int NETWORK_VALIDATION_FAILURE = 4;
    public static final int NETWORK_VALIDATION_IN_PROGRESS = 2;
    public static final int NETWORK_VALIDATION_NOT_REQUESTED = 1;
    public static final int NETWORK_VALIDATION_SUCCESS = 3;
    public static final int NETWORK_VALIDATION_UNSUPPORTED = 0;
    private final android.telephony.data.ApnSetting mApnSetting;
    private final android.telephony.data.Qos mDefaultQos;
    private final int mFailCause;
    private final int mId;
    private final android.net.LinkProperties mLinkProperties;
    private final int mNetworkType;
    private final int mNetworkValidationStatus;
    private final int mState;
    private final int mTransportType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NetworkValidationStatus {
    }

    @java.lang.Deprecated
    public PreciseDataConnectionState(int i, int i2, int i3, java.lang.String str, android.net.LinkProperties linkProperties, int i4) {
        this(-1, -1, i, i2, linkProperties, i4, new android.telephony.data.ApnSetting.Builder().setApnTypeBitmask(i3).setApnName(str).setEntryName(str).build(), null, 0);
    }

    private PreciseDataConnectionState(int i, int i2, int i3, int i4, android.net.LinkProperties linkProperties, int i5, android.telephony.data.ApnSetting apnSetting, android.telephony.data.Qos qos, int i6) {
        this.mTransportType = i;
        this.mId = i2;
        this.mState = i3;
        this.mNetworkType = i4;
        this.mLinkProperties = linkProperties;
        this.mFailCause = i5;
        this.mApnSetting = apnSetting;
        this.mDefaultQos = qos;
        this.mNetworkValidationStatus = i6;
    }

    private PreciseDataConnectionState(android.os.Parcel parcel) {
        this.mTransportType = parcel.readInt();
        this.mId = parcel.readInt();
        this.mState = parcel.readInt();
        this.mNetworkType = parcel.readInt();
        this.mLinkProperties = (android.net.LinkProperties) parcel.readParcelable(android.net.LinkProperties.class.getClassLoader(), android.net.LinkProperties.class);
        this.mFailCause = parcel.readInt();
        this.mApnSetting = (android.telephony.data.ApnSetting) parcel.readParcelable(android.telephony.data.ApnSetting.class.getClassLoader(), android.telephony.data.ApnSetting.class);
        this.mDefaultQos = (android.telephony.data.Qos) parcel.readParcelable(android.telephony.data.Qos.class.getClassLoader(), android.telephony.data.Qos.class);
        this.mNetworkValidationStatus = parcel.readInt();
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public int getDataConnectionState() {
        if (this.mState == 4 && !android.compat.Compatibility.isChangeEnabled(GET_DATA_CONNECTION_STATE_R_VERSION)) {
            return 2;
        }
        return this.mState;
    }

    public int getTransportType() {
        return this.mTransportType;
    }

    public int getId() {
        return this.mId;
    }

    public int getState() {
        return this.mState;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public int getDataConnectionApnTypeBitMask() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getApnTypeBitmask();
        }
        return 0;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public java.lang.String getDataConnectionApn() {
        return this.mApnSetting != null ? this.mApnSetting.getApnName() : "";
    }

    public android.net.LinkProperties getLinkProperties() {
        return this.mLinkProperties;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public int getDataConnectionFailCause() {
        return this.mFailCause;
    }

    public int getLastCauseCode() {
        return this.mFailCause;
    }

    public android.telephony.data.ApnSetting getApnSetting() {
        return this.mApnSetting;
    }

    public android.telephony.data.Qos getDefaultQos() {
        return this.mDefaultQos;
    }

    public int getNetworkValidationStatus() {
        return this.mNetworkValidationStatus;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mTransportType);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mNetworkType);
        parcel.writeParcelable(this.mLinkProperties, i);
        parcel.writeInt(this.mFailCause);
        parcel.writeParcelable(this.mApnSetting, i);
        parcel.writeParcelable(this.mDefaultQos, i);
        parcel.writeInt(this.mNetworkValidationStatus);
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mTransportType), java.lang.Integer.valueOf(this.mId), java.lang.Integer.valueOf(this.mState), java.lang.Integer.valueOf(this.mNetworkType), java.lang.Integer.valueOf(this.mFailCause), this.mLinkProperties, this.mApnSetting, this.mDefaultQos, java.lang.Integer.valueOf(this.mNetworkValidationStatus));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.PreciseDataConnectionState preciseDataConnectionState = (android.telephony.PreciseDataConnectionState) obj;
        if (this.mTransportType == preciseDataConnectionState.mTransportType && this.mId == preciseDataConnectionState.mId && this.mState == preciseDataConnectionState.mState && this.mNetworkType == preciseDataConnectionState.mNetworkType && this.mFailCause == preciseDataConnectionState.mFailCause && java.util.Objects.equals(this.mLinkProperties, preciseDataConnectionState.mLinkProperties) && java.util.Objects.equals(this.mApnSetting, preciseDataConnectionState.mApnSetting) && java.util.Objects.equals(this.mDefaultQos, preciseDataConnectionState.mDefaultQos) && this.mNetworkValidationStatus == preciseDataConnectionState.mNetworkValidationStatus) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(" state: " + com.android.internal.telephony.util.TelephonyUtils.dataStateToString(this.mState));
        sb.append(", transport: " + android.telephony.AccessNetworkConstants.transportTypeToString(this.mTransportType));
        sb.append(", id: " + this.mId);
        sb.append(", network type: " + android.telephony.TelephonyManager.getNetworkTypeName(this.mNetworkType));
        sb.append(", APN Setting: " + this.mApnSetting);
        sb.append(", link properties: " + this.mLinkProperties);
        sb.append(", default QoS: " + this.mDefaultQos);
        sb.append(", fail cause: " + android.telephony.DataFailCause.toString(this.mFailCause));
        sb.append(", network validation status: " + networkValidationStatusToString(this.mNetworkValidationStatus));
        return sb.toString();
    }

    public static java.lang.String networkValidationStatusToString(int i) {
        switch (i) {
            case 0:
                return "unsupported";
            case 1:
                return "not requested";
            case 2:
                return "in progress";
            case 3:
                return "success";
            case 4:
                return "failure";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static final class Builder {
        private android.telephony.data.ApnSetting mApnSetting;
        private android.telephony.data.Qos mDefaultQos;
        private android.net.LinkProperties mLinkProperties;
        private int mTransportType = -1;
        private int mId = -1;
        private int mState = -1;
        private int mNetworkType = 0;
        private int mFailCause = 0;
        private int mNetworkValidationStatus = 0;

        public android.telephony.PreciseDataConnectionState.Builder setTransportType(int i) {
            this.mTransportType = i;
            return this;
        }

        public android.telephony.PreciseDataConnectionState.Builder setId(int i) {
            this.mId = i;
            return this;
        }

        public android.telephony.PreciseDataConnectionState.Builder setState(int i) {
            this.mState = i;
            return this;
        }

        public android.telephony.PreciseDataConnectionState.Builder setNetworkType(int i) {
            this.mNetworkType = i;
            return this;
        }

        public android.telephony.PreciseDataConnectionState.Builder setLinkProperties(android.net.LinkProperties linkProperties) {
            this.mLinkProperties = linkProperties;
            return this;
        }

        public android.telephony.PreciseDataConnectionState.Builder setFailCause(int i) {
            this.mFailCause = i;
            return this;
        }

        public android.telephony.PreciseDataConnectionState.Builder setApnSetting(android.telephony.data.ApnSetting apnSetting) {
            this.mApnSetting = apnSetting;
            return this;
        }

        public android.telephony.PreciseDataConnectionState.Builder setDefaultQos(android.telephony.data.Qos qos) {
            this.mDefaultQos = qos;
            return this;
        }

        public android.telephony.PreciseDataConnectionState.Builder setNetworkValidationStatus(int i) {
            this.mNetworkValidationStatus = i;
            return this;
        }

        public android.telephony.PreciseDataConnectionState build() {
            return new android.telephony.PreciseDataConnectionState(this.mTransportType, this.mId, this.mState, this.mNetworkType, this.mLinkProperties, this.mFailCause, this.mApnSetting, this.mDefaultQos, this.mNetworkValidationStatus);
        }
    }
}
