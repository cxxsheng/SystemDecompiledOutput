package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class SipDelegateConfiguration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.SipDelegateConfiguration> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.SipDelegateConfiguration>() { // from class: android.telephony.ims.SipDelegateConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.SipDelegateConfiguration createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.SipDelegateConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.SipDelegateConfiguration[] newArray(int i) {
            return new android.telephony.ims.SipDelegateConfiguration[i];
        }
    };
    public static final int SIP_TRANSPORT_TCP = 1;
    public static final int SIP_TRANSPORT_UDP = 0;
    public static final int UDP_PAYLOAD_SIZE_UNDEFINED = -1;
    private java.lang.String mAssociatedUriHeader;
    private java.lang.String mCniHeader;
    private java.lang.String mContactUserParam;
    private android.net.Uri mGruu;
    private java.lang.String mHomeDomain;
    private java.lang.String mImei;
    private android.telephony.ims.SipDelegateConfiguration.IpSecConfiguration mIpSecConfiguration;
    private boolean mIsSipCompactFormEnabled;
    private boolean mIsSipKeepaliveEnabled;
    private final java.net.InetSocketAddress mLocalAddress;
    private int mMaxUdpPayloadSize;
    private java.net.InetSocketAddress mNatAddress;
    private java.lang.String mPaniHeader;
    private java.lang.String mPathHeader;
    private java.lang.String mPlaniHeader;
    private java.lang.String mPrivateUserIdentifier;
    private java.lang.String mPublicUserIdentifier;
    private java.lang.String mServiceRouteHeader;
    private java.lang.String mSipAuthHeader;
    private java.lang.String mSipAuthNonce;
    private final java.net.InetSocketAddress mSipServerAddress;
    private final int mTransportType;
    private java.lang.String mUserAgentHeader;
    private final long mVersion;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TransportType {
    }

    public static final class IpSecConfiguration {
        private final int mLastLocalTxPort;
        private final int mLastRemoteTxPort;
        private final int mLocalRxPort;
        private final int mLocalTxPort;
        private final int mRemoteRxPort;
        private final int mRemoteTxPort;
        private final java.lang.String mSecurityHeader;

        public IpSecConfiguration(int i, int i2, int i3, int i4, int i5, int i6, java.lang.String str) {
            this.mLocalTxPort = i;
            this.mLocalRxPort = i2;
            this.mLastLocalTxPort = i3;
            this.mRemoteTxPort = i4;
            this.mRemoteRxPort = i5;
            this.mLastRemoteTxPort = i6;
            this.mSecurityHeader = str;
        }

        public int getLocalTxPort() {
            return this.mLocalTxPort;
        }

        public int getLocalRxPort() {
            return this.mLocalRxPort;
        }

        public int getLastLocalTxPort() {
            return this.mLastLocalTxPort;
        }

        public int getRemoteTxPort() {
            return this.mRemoteTxPort;
        }

        public int getRemoteRxPort() {
            return this.mRemoteRxPort;
        }

        public int getLastRemoteTxPort() {
            return this.mLastRemoteTxPort;
        }

        public java.lang.String getSipSecurityVerifyHeader() {
            return this.mSecurityHeader;
        }

        public void addToParcel(android.os.Parcel parcel) {
            parcel.writeInt(this.mLocalTxPort);
            parcel.writeInt(this.mLocalRxPort);
            parcel.writeInt(this.mLastLocalTxPort);
            parcel.writeInt(this.mRemoteTxPort);
            parcel.writeInt(this.mRemoteRxPort);
            parcel.writeInt(this.mLastRemoteTxPort);
            parcel.writeString(this.mSecurityHeader);
        }

        public static android.telephony.ims.SipDelegateConfiguration.IpSecConfiguration fromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.SipDelegateConfiguration.IpSecConfiguration(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString());
        }

        public java.lang.String toString() {
            return "IpSecConfiguration{localTx=" + this.mLocalTxPort + ", localRx=" + this.mLocalRxPort + ", lastLocalTx=" + this.mLastLocalTxPort + ", remoteTx=" + this.mRemoteTxPort + ", remoteRx=" + this.mRemoteRxPort + ", lastRemoteTx=" + this.mLastRemoteTxPort + ", securityHeader=" + this.mSecurityHeader + '}';
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.telephony.ims.SipDelegateConfiguration.IpSecConfiguration ipSecConfiguration = (android.telephony.ims.SipDelegateConfiguration.IpSecConfiguration) obj;
            if (this.mLocalTxPort == ipSecConfiguration.mLocalTxPort && this.mLocalRxPort == ipSecConfiguration.mLocalRxPort && this.mLastLocalTxPort == ipSecConfiguration.mLastLocalTxPort && this.mRemoteTxPort == ipSecConfiguration.mRemoteTxPort && this.mRemoteRxPort == ipSecConfiguration.mRemoteRxPort && this.mLastRemoteTxPort == ipSecConfiguration.mLastRemoteTxPort && java.util.Objects.equals(this.mSecurityHeader, ipSecConfiguration.mSecurityHeader)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mLocalTxPort), java.lang.Integer.valueOf(this.mLocalRxPort), java.lang.Integer.valueOf(this.mLastLocalTxPort), java.lang.Integer.valueOf(this.mRemoteTxPort), java.lang.Integer.valueOf(this.mRemoteRxPort), java.lang.Integer.valueOf(this.mLastRemoteTxPort), this.mSecurityHeader);
        }
    }

    public static final class Builder {
        private final android.telephony.ims.SipDelegateConfiguration mConfig;

        public Builder(long j, int i, java.net.InetSocketAddress inetSocketAddress, java.net.InetSocketAddress inetSocketAddress2) {
            this.mConfig = new android.telephony.ims.SipDelegateConfiguration(j, i, inetSocketAddress, inetSocketAddress2);
        }

        public Builder(android.telephony.ims.SipDelegateConfiguration sipDelegateConfiguration) {
            this.mConfig = sipDelegateConfiguration.copyAndIncrementVersion();
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setSipCompactFormEnabled(boolean z) {
            this.mConfig.mIsSipCompactFormEnabled = z;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setSipKeepaliveEnabled(boolean z) {
            this.mConfig.mIsSipKeepaliveEnabled = z;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setMaxUdpPayloadSizeBytes(int i) {
            this.mConfig.mMaxUdpPayloadSize = i;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setPublicUserIdentifier(java.lang.String str) {
            this.mConfig.mPublicUserIdentifier = str;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setPrivateUserIdentifier(java.lang.String str) {
            this.mConfig.mPrivateUserIdentifier = str;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setHomeDomain(java.lang.String str) {
            this.mConfig.mHomeDomain = str;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setImei(java.lang.String str) {
            this.mConfig.mImei = str;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setIpSecConfiguration(android.telephony.ims.SipDelegateConfiguration.IpSecConfiguration ipSecConfiguration) {
            this.mConfig.mIpSecConfiguration = ipSecConfiguration;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setNatSocketAddress(java.net.InetSocketAddress inetSocketAddress) {
            this.mConfig.mNatAddress = inetSocketAddress;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setPublicGruuUri(android.net.Uri uri) {
            this.mConfig.mGruu = uri;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setSipAuthenticationHeader(java.lang.String str) {
            this.mConfig.mSipAuthHeader = str;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setSipAuthenticationNonce(java.lang.String str) {
            this.mConfig.mSipAuthNonce = str;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setSipServiceRouteHeader(java.lang.String str) {
            this.mConfig.mServiceRouteHeader = str;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setSipPathHeader(java.lang.String str) {
            this.mConfig.mPathHeader = str;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setSipUserAgentHeader(java.lang.String str) {
            this.mConfig.mUserAgentHeader = str;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setSipContactUserParameter(java.lang.String str) {
            this.mConfig.mContactUserParam = str;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setSipPaniHeader(java.lang.String str) {
            this.mConfig.mPaniHeader = str;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setSipPlaniHeader(java.lang.String str) {
            this.mConfig.mPlaniHeader = str;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setSipCniHeader(java.lang.String str) {
            this.mConfig.mCniHeader = str;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration.Builder setSipAssociatedUriHeader(java.lang.String str) {
            this.mConfig.mAssociatedUriHeader = str;
            return this;
        }

        public android.telephony.ims.SipDelegateConfiguration build() {
            return this.mConfig;
        }
    }

    private SipDelegateConfiguration(long j, int i, java.net.InetSocketAddress inetSocketAddress, java.net.InetSocketAddress inetSocketAddress2) {
        this.mIsSipCompactFormEnabled = false;
        this.mIsSipKeepaliveEnabled = false;
        this.mMaxUdpPayloadSize = -1;
        this.mPublicUserIdentifier = null;
        this.mPrivateUserIdentifier = null;
        this.mHomeDomain = null;
        this.mImei = null;
        this.mGruu = null;
        this.mSipAuthHeader = null;
        this.mSipAuthNonce = null;
        this.mServiceRouteHeader = null;
        this.mPathHeader = null;
        this.mUserAgentHeader = null;
        this.mContactUserParam = null;
        this.mPaniHeader = null;
        this.mPlaniHeader = null;
        this.mCniHeader = null;
        this.mAssociatedUriHeader = null;
        this.mIpSecConfiguration = null;
        this.mNatAddress = null;
        this.mVersion = j;
        this.mTransportType = i;
        this.mLocalAddress = inetSocketAddress;
        this.mSipServerAddress = inetSocketAddress2;
    }

    private SipDelegateConfiguration(android.os.Parcel parcel) {
        this.mIsSipCompactFormEnabled = false;
        this.mIsSipKeepaliveEnabled = false;
        this.mMaxUdpPayloadSize = -1;
        this.mPublicUserIdentifier = null;
        this.mPrivateUserIdentifier = null;
        this.mHomeDomain = null;
        this.mImei = null;
        this.mGruu = null;
        this.mSipAuthHeader = null;
        this.mSipAuthNonce = null;
        this.mServiceRouteHeader = null;
        this.mPathHeader = null;
        this.mUserAgentHeader = null;
        this.mContactUserParam = null;
        this.mPaniHeader = null;
        this.mPlaniHeader = null;
        this.mCniHeader = null;
        this.mAssociatedUriHeader = null;
        this.mIpSecConfiguration = null;
        this.mNatAddress = null;
        this.mVersion = parcel.readLong();
        this.mTransportType = parcel.readInt();
        this.mLocalAddress = readAddressFromParcel(parcel);
        this.mSipServerAddress = readAddressFromParcel(parcel);
        this.mIsSipCompactFormEnabled = parcel.readBoolean();
        this.mIsSipKeepaliveEnabled = parcel.readBoolean();
        this.mMaxUdpPayloadSize = parcel.readInt();
        this.mPublicUserIdentifier = parcel.readString();
        this.mPrivateUserIdentifier = parcel.readString();
        this.mHomeDomain = parcel.readString();
        this.mImei = parcel.readString();
        this.mGruu = (android.net.Uri) parcel.readParcelable(null, android.net.Uri.class);
        this.mSipAuthHeader = parcel.readString();
        this.mSipAuthNonce = parcel.readString();
        this.mServiceRouteHeader = parcel.readString();
        this.mPathHeader = parcel.readString();
        this.mUserAgentHeader = parcel.readString();
        this.mContactUserParam = parcel.readString();
        this.mPaniHeader = parcel.readString();
        this.mPlaniHeader = parcel.readString();
        this.mCniHeader = parcel.readString();
        this.mAssociatedUriHeader = parcel.readString();
        if (parcel.readBoolean()) {
            this.mIpSecConfiguration = android.telephony.ims.SipDelegateConfiguration.IpSecConfiguration.fromParcel(parcel);
        }
        if (parcel.readBoolean()) {
            this.mNatAddress = readAddressFromParcel(parcel);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mVersion);
        parcel.writeInt(this.mTransportType);
        writeAddressToParcel(this.mLocalAddress, parcel);
        writeAddressToParcel(this.mSipServerAddress, parcel);
        parcel.writeBoolean(this.mIsSipCompactFormEnabled);
        parcel.writeBoolean(this.mIsSipKeepaliveEnabled);
        parcel.writeInt(this.mMaxUdpPayloadSize);
        parcel.writeString(this.mPublicUserIdentifier);
        parcel.writeString(this.mPrivateUserIdentifier);
        parcel.writeString(this.mHomeDomain);
        parcel.writeString(this.mImei);
        parcel.writeParcelable(this.mGruu, i);
        parcel.writeString(this.mSipAuthHeader);
        parcel.writeString(this.mSipAuthNonce);
        parcel.writeString(this.mServiceRouteHeader);
        parcel.writeString(this.mPathHeader);
        parcel.writeString(this.mUserAgentHeader);
        parcel.writeString(this.mContactUserParam);
        parcel.writeString(this.mPaniHeader);
        parcel.writeString(this.mPlaniHeader);
        parcel.writeString(this.mCniHeader);
        parcel.writeString(this.mAssociatedUriHeader);
        parcel.writeBoolean(this.mIpSecConfiguration != null);
        if (this.mIpSecConfiguration != null) {
            this.mIpSecConfiguration.addToParcel(parcel);
        }
        parcel.writeBoolean(this.mNatAddress != null);
        if (this.mNatAddress != null) {
            writeAddressToParcel(this.mNatAddress, parcel);
        }
    }

    public android.telephony.ims.SipDelegateConfiguration copyAndIncrementVersion() {
        android.telephony.ims.SipDelegateConfiguration sipDelegateConfiguration = new android.telephony.ims.SipDelegateConfiguration(getVersion() + 1, this.mTransportType, this.mLocalAddress, this.mSipServerAddress);
        sipDelegateConfiguration.mIsSipCompactFormEnabled = this.mIsSipCompactFormEnabled;
        sipDelegateConfiguration.mIsSipKeepaliveEnabled = this.mIsSipKeepaliveEnabled;
        sipDelegateConfiguration.mMaxUdpPayloadSize = this.mMaxUdpPayloadSize;
        sipDelegateConfiguration.mIpSecConfiguration = this.mIpSecConfiguration;
        sipDelegateConfiguration.mNatAddress = this.mNatAddress;
        sipDelegateConfiguration.mPublicUserIdentifier = this.mPublicUserIdentifier;
        sipDelegateConfiguration.mPrivateUserIdentifier = this.mPrivateUserIdentifier;
        sipDelegateConfiguration.mHomeDomain = this.mHomeDomain;
        sipDelegateConfiguration.mImei = this.mImei;
        sipDelegateConfiguration.mGruu = this.mGruu;
        sipDelegateConfiguration.mSipAuthHeader = this.mSipAuthHeader;
        sipDelegateConfiguration.mSipAuthNonce = this.mSipAuthNonce;
        sipDelegateConfiguration.mServiceRouteHeader = this.mServiceRouteHeader;
        sipDelegateConfiguration.mPathHeader = this.mPathHeader;
        sipDelegateConfiguration.mUserAgentHeader = this.mUserAgentHeader;
        sipDelegateConfiguration.mContactUserParam = this.mContactUserParam;
        sipDelegateConfiguration.mPaniHeader = this.mPaniHeader;
        sipDelegateConfiguration.mPlaniHeader = this.mPlaniHeader;
        sipDelegateConfiguration.mCniHeader = this.mCniHeader;
        sipDelegateConfiguration.mAssociatedUriHeader = this.mAssociatedUriHeader;
        return sipDelegateConfiguration;
    }

    public long getVersion() {
        return this.mVersion;
    }

    public int getTransportType() {
        return this.mTransportType;
    }

    public java.net.InetSocketAddress getLocalAddress() {
        return this.mLocalAddress;
    }

    public java.net.InetSocketAddress getSipServerAddress() {
        return this.mSipServerAddress;
    }

    public boolean isSipCompactFormEnabled() {
        return this.mIsSipCompactFormEnabled;
    }

    public boolean isSipKeepaliveEnabled() {
        return this.mIsSipKeepaliveEnabled;
    }

    public int getMaxUdpPayloadSizeBytes() {
        return this.mMaxUdpPayloadSize;
    }

    public java.lang.String getPublicUserIdentifier() {
        return this.mPublicUserIdentifier;
    }

    public java.lang.String getPrivateUserIdentifier() {
        return this.mPrivateUserIdentifier;
    }

    public java.lang.String getHomeDomain() {
        return this.mHomeDomain;
    }

    public java.lang.String getImei() {
        return this.mImei;
    }

    public android.telephony.ims.SipDelegateConfiguration.IpSecConfiguration getIpSecConfiguration() {
        return this.mIpSecConfiguration;
    }

    public java.net.InetSocketAddress getNatSocketAddress() {
        return this.mNatAddress;
    }

    public android.net.Uri getPublicGruuUri() {
        return this.mGruu;
    }

    public java.lang.String getSipAuthenticationHeader() {
        return this.mSipAuthHeader;
    }

    public java.lang.String getSipAuthenticationNonce() {
        return this.mSipAuthNonce;
    }

    public java.lang.String getSipServiceRouteHeader() {
        return this.mServiceRouteHeader;
    }

    public java.lang.String getSipPathHeader() {
        return this.mPathHeader;
    }

    public java.lang.String getSipUserAgentHeader() {
        return this.mUserAgentHeader;
    }

    public java.lang.String getSipContactUserParameter() {
        return this.mContactUserParam;
    }

    public java.lang.String getSipPaniHeader() {
        return this.mPaniHeader;
    }

    public java.lang.String getSipPlaniHeader() {
        return this.mPlaniHeader;
    }

    public java.lang.String getSipCniHeader() {
        return this.mCniHeader;
    }

    public java.lang.String getSipAssociatedUriHeader() {
        return this.mAssociatedUriHeader;
    }

    private void writeAddressToParcel(java.net.InetSocketAddress inetSocketAddress, android.os.Parcel parcel) {
        parcel.writeByteArray(inetSocketAddress.getAddress().getAddress());
        parcel.writeInt(inetSocketAddress.getPort());
    }

    private java.net.InetSocketAddress readAddressFromParcel(android.os.Parcel parcel) {
        byte[] createByteArray = parcel.createByteArray();
        try {
            return new java.net.InetSocketAddress(java.net.InetAddress.getByAddress(createByteArray), parcel.readInt());
        } catch (java.net.UnknownHostException e) {
            android.util.Log.e("SipDelegateConfiguration", "exception reading address, returning null");
            return null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.ims.SipDelegateConfiguration sipDelegateConfiguration = (android.telephony.ims.SipDelegateConfiguration) obj;
        if (this.mVersion == sipDelegateConfiguration.mVersion && this.mTransportType == sipDelegateConfiguration.mTransportType && this.mIsSipCompactFormEnabled == sipDelegateConfiguration.mIsSipCompactFormEnabled && this.mIsSipKeepaliveEnabled == sipDelegateConfiguration.mIsSipKeepaliveEnabled && this.mMaxUdpPayloadSize == sipDelegateConfiguration.mMaxUdpPayloadSize && java.util.Objects.equals(this.mLocalAddress, sipDelegateConfiguration.mLocalAddress) && java.util.Objects.equals(this.mSipServerAddress, sipDelegateConfiguration.mSipServerAddress) && java.util.Objects.equals(this.mPublicUserIdentifier, sipDelegateConfiguration.mPublicUserIdentifier) && java.util.Objects.equals(this.mPrivateUserIdentifier, sipDelegateConfiguration.mPrivateUserIdentifier) && java.util.Objects.equals(this.mHomeDomain, sipDelegateConfiguration.mHomeDomain) && java.util.Objects.equals(this.mImei, sipDelegateConfiguration.mImei) && java.util.Objects.equals(this.mGruu, sipDelegateConfiguration.mGruu) && java.util.Objects.equals(this.mSipAuthHeader, sipDelegateConfiguration.mSipAuthHeader) && java.util.Objects.equals(this.mSipAuthNonce, sipDelegateConfiguration.mSipAuthNonce) && java.util.Objects.equals(this.mServiceRouteHeader, sipDelegateConfiguration.mServiceRouteHeader) && java.util.Objects.equals(this.mPathHeader, sipDelegateConfiguration.mPathHeader) && java.util.Objects.equals(this.mUserAgentHeader, sipDelegateConfiguration.mUserAgentHeader) && java.util.Objects.equals(this.mContactUserParam, sipDelegateConfiguration.mContactUserParam) && java.util.Objects.equals(this.mPaniHeader, sipDelegateConfiguration.mPaniHeader) && java.util.Objects.equals(this.mPlaniHeader, sipDelegateConfiguration.mPlaniHeader) && java.util.Objects.equals(this.mCniHeader, sipDelegateConfiguration.mCniHeader) && java.util.Objects.equals(this.mAssociatedUriHeader, sipDelegateConfiguration.mAssociatedUriHeader) && java.util.Objects.equals(this.mIpSecConfiguration, sipDelegateConfiguration.mIpSecConfiguration) && java.util.Objects.equals(this.mNatAddress, sipDelegateConfiguration.mNatAddress)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mVersion), java.lang.Integer.valueOf(this.mTransportType), this.mLocalAddress, this.mSipServerAddress, java.lang.Boolean.valueOf(this.mIsSipCompactFormEnabled), java.lang.Boolean.valueOf(this.mIsSipKeepaliveEnabled), java.lang.Integer.valueOf(this.mMaxUdpPayloadSize), this.mPublicUserIdentifier, this.mPrivateUserIdentifier, this.mHomeDomain, this.mImei, this.mGruu, this.mSipAuthHeader, this.mSipAuthNonce, this.mServiceRouteHeader, this.mPathHeader, this.mUserAgentHeader, this.mContactUserParam, this.mPaniHeader, this.mPlaniHeader, this.mCniHeader, this.mAssociatedUriHeader, this.mIpSecConfiguration, this.mNatAddress);
    }

    public java.lang.String toString() {
        return "SipDelegateConfiguration{ mVersion=" + this.mVersion + ", mTransportType=" + this.mTransportType + '}';
    }
}
