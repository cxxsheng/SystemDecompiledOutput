package android.telephony.data;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class DataCallResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.data.DataCallResponse> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.DataCallResponse>() { // from class: android.telephony.data.DataCallResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.DataCallResponse createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.data.DataCallResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.DataCallResponse[] newArray(int i) {
            return new android.telephony.data.DataCallResponse[i];
        }
    };
    public static final int HANDOVER_FAILURE_MODE_DO_FALLBACK = 1;
    public static final int HANDOVER_FAILURE_MODE_LEGACY = 0;
    public static final int HANDOVER_FAILURE_MODE_NO_FALLBACK_RETRY_HANDOVER = 2;
    public static final int HANDOVER_FAILURE_MODE_NO_FALLBACK_RETRY_SETUP_NORMAL = 3;
    public static final int HANDOVER_FAILURE_MODE_UNKNOWN = -1;
    public static final int LINK_STATUS_ACTIVE = 2;
    public static final int LINK_STATUS_DORMANT = 1;
    public static final int LINK_STATUS_INACTIVE = 0;
    public static final int LINK_STATUS_UNKNOWN = -1;
    public static final int PDU_SESSION_ID_NOT_SET = 0;
    public static final int RETRY_DURATION_UNDEFINED = -1;
    private final java.util.List<android.net.LinkAddress> mAddresses;
    private final int mCause;
    private final android.telephony.data.Qos mDefaultQos;
    private final java.util.List<java.net.InetAddress> mDnsAddresses;
    private final java.util.List<java.net.InetAddress> mGatewayAddresses;
    private final int mHandoverFailureMode;
    private final int mId;
    private final java.lang.String mInterfaceName;
    private final int mLinkStatus;
    private final int mMtu;
    private final int mMtuV4;
    private final int mMtuV6;
    private final int mNetworkValidationStatus;
    private final java.util.List<java.net.InetAddress> mPcscfAddresses;
    private final int mPduSessionId;
    private final int mProtocolType;
    private final java.util.List<android.telephony.data.QosBearerSession> mQosBearerSessions;
    private final android.telephony.data.NetworkSliceInfo mSliceInfo;
    private final long mSuggestedRetryTime;
    private final java.util.List<android.telephony.data.TrafficDescriptor> mTrafficDescriptors;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HandoverFailureMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LinkStatus {
    }

    public DataCallResponse(int i, int i2, int i3, int i4, int i5, java.lang.String str, java.util.List<android.net.LinkAddress> list, java.util.List<java.net.InetAddress> list2, java.util.List<java.net.InetAddress> list3, java.util.List<java.net.InetAddress> list4, int i6) {
        this(i, i2, i3, i4, i5, str == null ? "" : str, list == null ? java.util.Collections.emptyList() : list, list2 == null ? java.util.Collections.emptyList() : list2, list3 == null ? java.util.Collections.emptyList() : list3, list4 == null ? java.util.Collections.emptyList() : list4, i6, i6, i6, 0, 0, null, java.util.Collections.emptyList(), null, java.util.Collections.emptyList(), 0);
    }

    private DataCallResponse(int i, long j, int i2, int i3, int i4, java.lang.String str, java.util.List<android.net.LinkAddress> list, java.util.List<java.net.InetAddress> list2, java.util.List<java.net.InetAddress> list3, java.util.List<java.net.InetAddress> list4, int i5, int i6, int i7, int i8, int i9, android.telephony.data.Qos qos, java.util.List<android.telephony.data.QosBearerSession> list5, android.telephony.data.NetworkSliceInfo networkSliceInfo, java.util.List<android.telephony.data.TrafficDescriptor> list6, int i10) {
        this.mCause = i;
        this.mSuggestedRetryTime = j;
        this.mId = i2;
        this.mLinkStatus = i3;
        this.mProtocolType = i4;
        this.mInterfaceName = str;
        this.mAddresses = new java.util.ArrayList(list);
        this.mDnsAddresses = new java.util.ArrayList(list2);
        this.mGatewayAddresses = new java.util.ArrayList(list3);
        this.mPcscfAddresses = new java.util.ArrayList(list4);
        this.mMtu = i5;
        this.mMtuV4 = i6;
        this.mMtuV6 = i7;
        this.mHandoverFailureMode = i8;
        this.mPduSessionId = i9;
        this.mDefaultQos = qos;
        this.mQosBearerSessions = new java.util.ArrayList(list5);
        this.mSliceInfo = networkSliceInfo;
        this.mTrafficDescriptors = new java.util.ArrayList(list6);
        this.mNetworkValidationStatus = i10;
        if (this.mLinkStatus == 2 || this.mLinkStatus == 1) {
            java.util.Objects.requireNonNull(this.mInterfaceName, "Active data calls must be on a valid interface!");
            if (this.mCause != 0) {
                throw new java.lang.IllegalStateException("Active data call must not have a failure!");
            }
        }
    }

    public DataCallResponse(android.os.Parcel parcel) {
        this.mCause = parcel.readInt();
        this.mSuggestedRetryTime = parcel.readLong();
        this.mId = parcel.readInt();
        this.mLinkStatus = parcel.readInt();
        this.mProtocolType = parcel.readInt();
        this.mInterfaceName = parcel.readString();
        this.mAddresses = new java.util.ArrayList();
        parcel.readList(this.mAddresses, android.net.LinkAddress.class.getClassLoader(), android.net.LinkAddress.class);
        this.mDnsAddresses = new java.util.ArrayList();
        parcel.readList(this.mDnsAddresses, java.net.InetAddress.class.getClassLoader(), java.net.InetAddress.class);
        this.mGatewayAddresses = new java.util.ArrayList();
        parcel.readList(this.mGatewayAddresses, java.net.InetAddress.class.getClassLoader(), java.net.InetAddress.class);
        this.mPcscfAddresses = new java.util.ArrayList();
        parcel.readList(this.mPcscfAddresses, java.net.InetAddress.class.getClassLoader(), java.net.InetAddress.class);
        this.mMtu = parcel.readInt();
        this.mMtuV4 = parcel.readInt();
        this.mMtuV6 = parcel.readInt();
        this.mHandoverFailureMode = parcel.readInt();
        this.mPduSessionId = parcel.readInt();
        this.mDefaultQos = (android.telephony.data.Qos) parcel.readParcelable(android.telephony.data.Qos.class.getClassLoader(), android.telephony.data.Qos.class);
        this.mQosBearerSessions = new java.util.ArrayList();
        parcel.readList(this.mQosBearerSessions, android.telephony.data.QosBearerSession.class.getClassLoader(), android.telephony.data.QosBearerSession.class);
        this.mSliceInfo = (android.telephony.data.NetworkSliceInfo) parcel.readParcelable(android.telephony.data.NetworkSliceInfo.class.getClassLoader(), android.telephony.data.NetworkSliceInfo.class);
        this.mTrafficDescriptors = new java.util.ArrayList();
        parcel.readList(this.mTrafficDescriptors, android.telephony.data.TrafficDescriptor.class.getClassLoader(), android.telephony.data.TrafficDescriptor.class);
        this.mNetworkValidationStatus = parcel.readInt();
    }

    public int getCause() {
        return this.mCause;
    }

    @java.lang.Deprecated
    public int getSuggestedRetryTime() {
        if (this.mSuggestedRetryTime == -1) {
            return 0;
        }
        if (this.mSuggestedRetryTime > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) this.mSuggestedRetryTime;
    }

    public long getRetryDurationMillis() {
        return this.mSuggestedRetryTime;
    }

    public int getId() {
        return this.mId;
    }

    public int getLinkStatus() {
        return this.mLinkStatus;
    }

    public int getProtocolType() {
        return this.mProtocolType;
    }

    public java.lang.String getInterfaceName() {
        return this.mInterfaceName;
    }

    public java.util.List<android.net.LinkAddress> getAddresses() {
        return java.util.Collections.unmodifiableList(this.mAddresses);
    }

    public java.util.List<java.net.InetAddress> getDnsAddresses() {
        return java.util.Collections.unmodifiableList(this.mDnsAddresses);
    }

    public java.util.List<java.net.InetAddress> getGatewayAddresses() {
        return java.util.Collections.unmodifiableList(this.mGatewayAddresses);
    }

    public java.util.List<java.net.InetAddress> getPcscfAddresses() {
        return java.util.Collections.unmodifiableList(this.mPcscfAddresses);
    }

    @java.lang.Deprecated
    public int getMtu() {
        return this.mMtu;
    }

    public int getMtuV4() {
        return this.mMtuV4;
    }

    public int getMtuV6() {
        return this.mMtuV6;
    }

    public int getHandoverFailureMode() {
        return this.mHandoverFailureMode;
    }

    public int getPduSessionId() {
        return this.mPduSessionId;
    }

    public android.telephony.data.Qos getDefaultQos() {
        return this.mDefaultQos;
    }

    public java.util.List<android.telephony.data.QosBearerSession> getQosBearerSessions() {
        return java.util.Collections.unmodifiableList(this.mQosBearerSessions);
    }

    public android.telephony.data.NetworkSliceInfo getSliceInfo() {
        return this.mSliceInfo;
    }

    public java.util.List<android.telephony.data.TrafficDescriptor> getTrafficDescriptors() {
        return java.util.Collections.unmodifiableList(this.mTrafficDescriptors);
    }

    public int getNetworkValidationStatus() {
        return this.mNetworkValidationStatus;
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("DataCallResponse: {").append(" cause=").append(android.telephony.DataFailCause.toString(this.mCause)).append(" retry=").append(this.mSuggestedRetryTime).append(" cid=").append(this.mId).append(" linkStatus=").append(this.mLinkStatus).append(" protocolType=").append(this.mProtocolType).append(" ifname=").append(this.mInterfaceName).append(" addresses=").append(this.mAddresses).append(" dnses=").append(this.mDnsAddresses).append(" gateways=").append(this.mGatewayAddresses).append(" pcscf=").append(this.mPcscfAddresses).append(" mtu=").append(getMtu()).append(" mtuV4=").append(getMtuV4()).append(" mtuV6=").append(getMtuV6()).append(" handoverFailureMode=").append(failureModeToString(this.mHandoverFailureMode)).append(" pduSessionId=").append(getPduSessionId()).append(" defaultQos=").append(this.mDefaultQos).append(" qosBearerSessions=").append(this.mQosBearerSessions).append(" sliceInfo=").append(this.mSliceInfo).append(" trafficDescriptors=").append(this.mTrafficDescriptors).append(" networkValidationStatus=").append(android.telephony.PreciseDataConnectionState.networkValidationStatusToString(this.mNetworkValidationStatus)).append("}");
        return stringBuffer.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.data.DataCallResponse)) {
            return false;
        }
        android.telephony.data.DataCallResponse dataCallResponse = (android.telephony.data.DataCallResponse) obj;
        return this.mCause == dataCallResponse.mCause && this.mSuggestedRetryTime == dataCallResponse.mSuggestedRetryTime && this.mId == dataCallResponse.mId && this.mLinkStatus == dataCallResponse.mLinkStatus && this.mProtocolType == dataCallResponse.mProtocolType && this.mInterfaceName.equals(dataCallResponse.mInterfaceName) && this.mAddresses.size() == dataCallResponse.mAddresses.size() && this.mAddresses.containsAll(dataCallResponse.mAddresses) && this.mDnsAddresses.size() == dataCallResponse.mDnsAddresses.size() && this.mDnsAddresses.containsAll(dataCallResponse.mDnsAddresses) && this.mGatewayAddresses.size() == dataCallResponse.mGatewayAddresses.size() && this.mGatewayAddresses.containsAll(dataCallResponse.mGatewayAddresses) && this.mPcscfAddresses.size() == dataCallResponse.mPcscfAddresses.size() && this.mPcscfAddresses.containsAll(dataCallResponse.mPcscfAddresses) && this.mMtu == dataCallResponse.mMtu && this.mMtuV4 == dataCallResponse.mMtuV4 && this.mMtuV6 == dataCallResponse.mMtuV6 && this.mHandoverFailureMode == dataCallResponse.mHandoverFailureMode && this.mPduSessionId == dataCallResponse.mPduSessionId && java.util.Objects.equals(this.mDefaultQos, dataCallResponse.mDefaultQos) && this.mQosBearerSessions.size() == dataCallResponse.mQosBearerSessions.size() && this.mQosBearerSessions.containsAll(dataCallResponse.mQosBearerSessions) && java.util.Objects.equals(this.mSliceInfo, dataCallResponse.mSliceInfo) && this.mTrafficDescriptors.size() == dataCallResponse.mTrafficDescriptors.size() && this.mTrafficDescriptors.containsAll(dataCallResponse.mTrafficDescriptors) && this.mNetworkValidationStatus == dataCallResponse.mNetworkValidationStatus;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mCause), java.lang.Long.valueOf(this.mSuggestedRetryTime), java.lang.Integer.valueOf(this.mId), java.lang.Integer.valueOf(this.mLinkStatus), java.lang.Integer.valueOf(this.mProtocolType), this.mInterfaceName, java.util.Set.copyOf(this.mAddresses), java.util.Set.copyOf(this.mDnsAddresses), java.util.Set.copyOf(this.mGatewayAddresses), java.util.Set.copyOf(this.mPcscfAddresses), java.lang.Integer.valueOf(this.mMtu), java.lang.Integer.valueOf(this.mMtuV4), java.lang.Integer.valueOf(this.mMtuV6), java.lang.Integer.valueOf(this.mHandoverFailureMode), java.lang.Integer.valueOf(this.mPduSessionId), this.mDefaultQos, java.util.Set.copyOf(this.mQosBearerSessions), this.mSliceInfo, java.util.Set.copyOf(this.mTrafficDescriptors), java.lang.Integer.valueOf(this.mNetworkValidationStatus));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mCause);
        parcel.writeLong(this.mSuggestedRetryTime);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mLinkStatus);
        parcel.writeInt(this.mProtocolType);
        parcel.writeString(this.mInterfaceName);
        parcel.writeList(this.mAddresses);
        parcel.writeList(this.mDnsAddresses);
        parcel.writeList(this.mGatewayAddresses);
        parcel.writeList(this.mPcscfAddresses);
        parcel.writeInt(this.mMtu);
        parcel.writeInt(this.mMtuV4);
        parcel.writeInt(this.mMtuV6);
        parcel.writeInt(this.mHandoverFailureMode);
        parcel.writeInt(this.mPduSessionId);
        parcel.writeParcelable(this.mDefaultQos, i);
        parcel.writeList(this.mQosBearerSessions);
        parcel.writeParcelable(this.mSliceInfo, i);
        parcel.writeList(this.mTrafficDescriptors);
        parcel.writeInt(this.mNetworkValidationStatus);
    }

    public static java.lang.String failureModeToString(int i) {
        switch (i) {
            case -1:
                return "unknown";
            case 0:
                return "legacy";
            case 1:
                return com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_FALLBACK;
            case 2:
                return "retry handover";
            case 3:
                return "retry setup new one";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static final class Builder {
        private int mCause;
        private android.telephony.data.Qos mDefaultQos;
        private int mId;
        private int mLinkStatus;
        private int mMtu;
        private int mMtuV4;
        private int mMtuV6;
        private int mProtocolType;
        private android.telephony.data.NetworkSliceInfo mSliceInfo;
        private long mSuggestedRetryTime = -1;
        private java.lang.String mInterfaceName = "";
        private java.util.List<android.net.LinkAddress> mAddresses = java.util.Collections.emptyList();
        private java.util.List<java.net.InetAddress> mDnsAddresses = java.util.Collections.emptyList();
        private java.util.List<java.net.InetAddress> mGatewayAddresses = java.util.Collections.emptyList();
        private java.util.List<java.net.InetAddress> mPcscfAddresses = java.util.Collections.emptyList();
        private int mHandoverFailureMode = 0;
        private int mPduSessionId = 0;
        private java.util.List<android.telephony.data.QosBearerSession> mQosBearerSessions = new java.util.ArrayList();
        private java.util.List<android.telephony.data.TrafficDescriptor> mTrafficDescriptors = new java.util.ArrayList();
        private int mNetworkValidationStatus = 0;

        public android.telephony.data.DataCallResponse.Builder setCause(int i) {
            this.mCause = i;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.DataCallResponse.Builder setSuggestedRetryTime(int i) {
            this.mSuggestedRetryTime = i;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setRetryDurationMillis(long j) {
            this.mSuggestedRetryTime = j;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setId(int i) {
            this.mId = i;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setLinkStatus(int i) {
            this.mLinkStatus = i;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setProtocolType(int i) {
            this.mProtocolType = i;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setInterfaceName(java.lang.String str) {
            if (str == null) {
                str = "";
            }
            this.mInterfaceName = str;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setAddresses(java.util.List<android.net.LinkAddress> list) {
            java.util.Objects.requireNonNull(list);
            this.mAddresses = list;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setDnsAddresses(java.util.List<java.net.InetAddress> list) {
            java.util.Objects.requireNonNull(list);
            this.mDnsAddresses = list;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setGatewayAddresses(java.util.List<java.net.InetAddress> list) {
            java.util.Objects.requireNonNull(list);
            this.mGatewayAddresses = list;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setPcscfAddresses(java.util.List<java.net.InetAddress> list) {
            java.util.Objects.requireNonNull(list);
            this.mPcscfAddresses = list;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setMtu(int i) {
            this.mMtu = i;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setMtuV4(int i) {
            this.mMtuV4 = i;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setMtuV6(int i) {
            this.mMtuV6 = i;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setHandoverFailureMode(int i) {
            this.mHandoverFailureMode = i;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setPduSessionId(int i) {
            com.android.internal.util.Preconditions.checkArgument(i >= 0, "pduSessionId must be greater than or equal to0");
            com.android.internal.util.Preconditions.checkArgument(i <= 15, "pduSessionId must be less than or equal to 15.");
            this.mPduSessionId = i;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setDefaultQos(android.telephony.data.Qos qos) {
            this.mDefaultQos = qos;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setQosBearerSessions(java.util.List<android.telephony.data.QosBearerSession> list) {
            java.util.Objects.requireNonNull(list);
            this.mQosBearerSessions = list;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setSliceInfo(android.telephony.data.NetworkSliceInfo networkSliceInfo) {
            this.mSliceInfo = networkSliceInfo;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setTrafficDescriptors(java.util.List<android.telephony.data.TrafficDescriptor> list) {
            java.util.Objects.requireNonNull(list);
            this.mTrafficDescriptors = list;
            return this;
        }

        public android.telephony.data.DataCallResponse.Builder setNetworkValidationStatus(int i) {
            this.mNetworkValidationStatus = i;
            return this;
        }

        public android.telephony.data.DataCallResponse build() {
            return new android.telephony.data.DataCallResponse(this.mCause, this.mSuggestedRetryTime, this.mId, this.mLinkStatus, this.mProtocolType, this.mInterfaceName, this.mAddresses, this.mDnsAddresses, this.mGatewayAddresses, this.mPcscfAddresses, this.mMtu, this.mMtuV4, this.mMtuV6, this.mHandoverFailureMode, this.mPduSessionId, this.mDefaultQos, this.mQosBearerSessions, this.mSliceInfo, this.mTrafficDescriptors, this.mNetworkValidationStatus);
        }
    }
}
