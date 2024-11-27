package android.telephony;

/* loaded from: classes3.dex */
public final class NetworkRegistrationInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.NetworkRegistrationInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.NetworkRegistrationInfo>() { // from class: android.telephony.NetworkRegistrationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.NetworkRegistrationInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.NetworkRegistrationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.NetworkRegistrationInfo[] newArray(int i) {
            return new android.telephony.NetworkRegistrationInfo[i];
        }
    };
    public static final int DOMAIN_CS = 1;
    public static final int DOMAIN_CS_PS = 3;
    public static final int DOMAIN_PS = 2;
    public static final int DOMAIN_UNKNOWN = 0;
    public static final int FIRST_SERVICE_TYPE = 1;
    public static final int LAST_SERVICE_TYPE = 6;
    public static final int NR_STATE_CONNECTED = 3;
    public static final int NR_STATE_NONE = 0;
    public static final int NR_STATE_NOT_RESTRICTED = 2;
    public static final int NR_STATE_RESTRICTED = 1;

    @android.annotation.SystemApi
    public static final int REGISTRATION_STATE_DENIED = 3;

    @android.annotation.SystemApi
    public static final int REGISTRATION_STATE_EMERGENCY = 6;

    @android.annotation.SystemApi
    public static final int REGISTRATION_STATE_HOME = 1;

    @android.annotation.SystemApi
    public static final int REGISTRATION_STATE_NOT_REGISTERED_OR_SEARCHING = 0;

    @android.annotation.SystemApi
    public static final int REGISTRATION_STATE_NOT_REGISTERED_SEARCHING = 2;

    @android.annotation.SystemApi
    public static final int REGISTRATION_STATE_ROAMING = 5;

    @android.annotation.SystemApi
    public static final int REGISTRATION_STATE_UNKNOWN = 4;
    public static final long RETURN_REGISTRATION_STATE_EMERGENCY = 255938466;
    public static final int SERVICE_TYPE_DATA = 2;
    public static final int SERVICE_TYPE_EMERGENCY = 5;
    public static final int SERVICE_TYPE_MMS = 6;
    public static final int SERVICE_TYPE_SMS = 3;
    public static final int SERVICE_TYPE_UNKNOWN = 0;
    public static final int SERVICE_TYPE_VIDEO = 4;
    public static final int SERVICE_TYPE_VOICE = 1;
    private int mAccessNetworkTechnology;
    private java.util.ArrayList<java.lang.Integer> mAvailableServices;
    private android.telephony.CellIdentity mCellIdentity;
    private android.telephony.DataSpecificRegistrationInfo mDataSpecificInfo;
    private final int mDomain;
    private final boolean mEmergencyOnly;
    private boolean mIsNonTerrestrialNetwork;
    private boolean mIsUsingCarrierAggregation;
    private final int mNetworkRegistrationState;
    private int mNrState;
    private int mRegistrationState;
    private final int mRejectCause;
    private int mRoamingType;
    private java.lang.String mRplmn;
    private final int mTransportType;
    private android.telephony.VoiceSpecificRegistrationInfo mVoiceSpecificInfo;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Domain {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NRState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RegistrationState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ServiceType {
    }

    private NetworkRegistrationInfo(int i, int i2, int i3, int i4, int i5, boolean z, java.util.List<java.lang.Integer> list, android.telephony.CellIdentity cellIdentity, java.lang.String str, android.telephony.VoiceSpecificRegistrationInfo voiceSpecificRegistrationInfo, android.telephony.DataSpecificRegistrationInfo dataSpecificRegistrationInfo, boolean z2) {
        this.mDomain = i;
        this.mTransportType = i2;
        this.mRegistrationState = i3;
        this.mNetworkRegistrationState = i3;
        this.mRoamingType = i3 == 5 ? 1 : 0;
        setAccessNetworkTechnology(i4);
        this.mRejectCause = i5;
        this.mAvailableServices = list != null ? new java.util.ArrayList<>(list) : new java.util.ArrayList<>();
        this.mCellIdentity = cellIdentity;
        this.mEmergencyOnly = z;
        this.mNrState = 0;
        this.mRplmn = str;
        this.mVoiceSpecificInfo = voiceSpecificRegistrationInfo;
        this.mDataSpecificInfo = dataSpecificRegistrationInfo;
        this.mIsNonTerrestrialNetwork = z2;
        updateNrState();
    }

    public NetworkRegistrationInfo(int i, int i2, int i3, int i4, int i5, boolean z, java.util.List<java.lang.Integer> list, android.telephony.CellIdentity cellIdentity, java.lang.String str, boolean z2, int i6, int i7, int i8) {
        this(i, i2, i3, i4, i5, z, list, cellIdentity, str, new android.telephony.VoiceSpecificRegistrationInfo(z2, i6, i7, i8), null, false);
    }

    public NetworkRegistrationInfo(int i, int i2, int i3, int i4, int i5, boolean z, java.util.List<java.lang.Integer> list, android.telephony.CellIdentity cellIdentity, java.lang.String str, int i6, boolean z2, boolean z3, boolean z4, android.telephony.VopsSupportInfo vopsSupportInfo) {
        this(i, i2, i3, i4, i5, z, list, cellIdentity, str, null, new android.telephony.DataSpecificRegistrationInfo.Builder(i6).setDcNrRestricted(z2).setNrAvailable(z3).setEnDcAvailable(z4).setVopsSupportInfo(vopsSupportInfo).build(), false);
    }

    private NetworkRegistrationInfo(android.os.Parcel parcel) {
        this.mDomain = parcel.readInt();
        this.mTransportType = parcel.readInt();
        this.mRegistrationState = parcel.readInt();
        this.mNetworkRegistrationState = parcel.readInt();
        this.mRoamingType = parcel.readInt();
        this.mAccessNetworkTechnology = parcel.readInt();
        this.mRejectCause = parcel.readInt();
        this.mEmergencyOnly = parcel.readBoolean();
        this.mAvailableServices = new java.util.ArrayList<>();
        parcel.readList(this.mAvailableServices, java.lang.Integer.class.getClassLoader(), java.lang.Integer.class);
        this.mCellIdentity = (android.telephony.CellIdentity) parcel.readParcelable(android.telephony.CellIdentity.class.getClassLoader(), android.telephony.CellIdentity.class);
        this.mVoiceSpecificInfo = (android.telephony.VoiceSpecificRegistrationInfo) parcel.readParcelable(android.telephony.VoiceSpecificRegistrationInfo.class.getClassLoader(), android.telephony.VoiceSpecificRegistrationInfo.class);
        this.mDataSpecificInfo = (android.telephony.DataSpecificRegistrationInfo) parcel.readParcelable(android.telephony.DataSpecificRegistrationInfo.class.getClassLoader(), android.telephony.DataSpecificRegistrationInfo.class);
        this.mNrState = parcel.readInt();
        this.mRplmn = parcel.readString();
        this.mIsUsingCarrierAggregation = parcel.readBoolean();
        this.mIsNonTerrestrialNetwork = parcel.readBoolean();
    }

    public NetworkRegistrationInfo(android.telephony.NetworkRegistrationInfo networkRegistrationInfo) {
        this.mDomain = networkRegistrationInfo.mDomain;
        this.mTransportType = networkRegistrationInfo.mTransportType;
        this.mRegistrationState = networkRegistrationInfo.mRegistrationState;
        this.mNetworkRegistrationState = networkRegistrationInfo.mNetworkRegistrationState;
        this.mRoamingType = networkRegistrationInfo.mRoamingType;
        this.mAccessNetworkTechnology = networkRegistrationInfo.mAccessNetworkTechnology;
        this.mIsUsingCarrierAggregation = networkRegistrationInfo.mIsUsingCarrierAggregation;
        this.mIsNonTerrestrialNetwork = networkRegistrationInfo.mIsNonTerrestrialNetwork;
        this.mRejectCause = networkRegistrationInfo.mRejectCause;
        this.mEmergencyOnly = networkRegistrationInfo.mEmergencyOnly;
        this.mAvailableServices = new java.util.ArrayList<>(networkRegistrationInfo.mAvailableServices);
        if (networkRegistrationInfo.mCellIdentity != null) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            networkRegistrationInfo.mCellIdentity.writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            this.mCellIdentity = android.telephony.CellIdentity.CREATOR.createFromParcel(obtain);
            obtain.recycle();
        }
        if (networkRegistrationInfo.mVoiceSpecificInfo != null) {
            this.mVoiceSpecificInfo = new android.telephony.VoiceSpecificRegistrationInfo(networkRegistrationInfo.mVoiceSpecificInfo);
        }
        if (networkRegistrationInfo.mDataSpecificInfo != null) {
            this.mDataSpecificInfo = new android.telephony.DataSpecificRegistrationInfo(networkRegistrationInfo.mDataSpecificInfo);
        }
        this.mNrState = networkRegistrationInfo.mNrState;
        this.mRplmn = networkRegistrationInfo.mRplmn;
    }

    public int getTransportType() {
        return this.mTransportType;
    }

    public int getDomain() {
        return this.mDomain;
    }

    public int getNrState() {
        return this.mNrState;
    }

    public void setNrState(int i) {
        this.mNrState = i;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public int getRegistrationState() {
        if (this.mRegistrationState == 6 && !android.app.compat.CompatChanges.isChangeEnabled(RETURN_REGISTRATION_STATE_EMERGENCY)) {
            if (this.mAccessNetworkTechnology == 13) {
                return 3;
            }
            if (this.mAccessNetworkTechnology == 20) {
                return 0;
            }
        }
        return this.mRegistrationState;
    }

    @android.annotation.SystemApi
    public int getNetworkRegistrationState() {
        return this.mNetworkRegistrationState;
    }

    @java.lang.Deprecated
    public boolean isRegistered() {
        return this.mRegistrationState == 1 || this.mRegistrationState == 5;
    }

    public boolean isNetworkRegistered() {
        return this.mNetworkRegistrationState == 1 || this.mNetworkRegistrationState == 5;
    }

    @java.lang.Deprecated
    public boolean isSearching() {
        return this.mRegistrationState == 2;
    }

    public boolean isNetworkSearching() {
        return this.mNetworkRegistrationState == 2;
    }

    public java.lang.String getRegisteredPlmn() {
        return this.mRplmn;
    }

    @java.lang.Deprecated
    public boolean isRoaming() {
        return this.mRoamingType != 0;
    }

    public boolean isNetworkRoaming() {
        return this.mNetworkRegistrationState == 5;
    }

    public boolean isInService() {
        return this.mRegistrationState == 1 || this.mRegistrationState == 5;
    }

    public void setRoamingType(int i) {
        this.mRoamingType = i;
        if (isRoaming()) {
            if (this.mRegistrationState == 1) {
                this.mRegistrationState = 5;
            }
        } else if (this.mRegistrationState == 5) {
            this.mRegistrationState = 1;
        }
    }

    @android.annotation.SystemApi
    public int getRoamingType() {
        return this.mRoamingType;
    }

    @android.annotation.SystemApi
    public boolean isEmergencyEnabled() {
        return this.mEmergencyOnly;
    }

    public java.util.List<java.lang.Integer> getAvailableServices() {
        return java.util.Collections.unmodifiableList(this.mAvailableServices);
    }

    public void setAvailableServices(java.util.List<java.lang.Integer> list) {
        this.mAvailableServices = new java.util.ArrayList<>(list);
    }

    public int getAccessNetworkTechnology() {
        return this.mAccessNetworkTechnology;
    }

    public void setAccessNetworkTechnology(int i) {
        if (i == 19) {
            this.mIsUsingCarrierAggregation = true;
            i = 13;
        }
        this.mAccessNetworkTechnology = i;
    }

    public int getRejectCause() {
        return this.mRejectCause;
    }

    public android.telephony.CellIdentity getCellIdentity() {
        return this.mCellIdentity;
    }

    public void setIsUsingCarrierAggregation(boolean z) {
        this.mIsUsingCarrierAggregation = z;
    }

    public boolean isUsingCarrierAggregation() {
        return this.mIsUsingCarrierAggregation;
    }

    public void setIsNonTerrestrialNetwork(boolean z) {
        this.mIsNonTerrestrialNetwork = z;
    }

    public boolean isNonTerrestrialNetwork() {
        return this.mIsNonTerrestrialNetwork;
    }

    public android.telephony.VoiceSpecificRegistrationInfo getVoiceSpecificInfo() {
        return this.mVoiceSpecificInfo;
    }

    @android.annotation.SystemApi
    public android.telephony.DataSpecificRegistrationInfo getDataSpecificInfo() {
        return this.mDataSpecificInfo;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static java.lang.String serviceTypeToString(int i) {
        switch (i) {
            case 1:
                return "VOICE";
            case 2:
                return "DATA";
            case 3:
                return "SMS";
            case 4:
                return "VIDEO";
            case 5:
                return "EMERGENCY";
            case 6:
                return "MMS";
            default:
                return "Unknown service type " + i;
        }
    }

    public static java.lang.String registrationStateToString(int i) {
        switch (i) {
            case 0:
                return "NOT_REG_OR_SEARCHING";
            case 1:
                return "HOME";
            case 2:
                return "NOT_REG_SEARCHING";
            case 3:
                return "DENIED";
            case 4:
                return "UNKNOWN";
            case 5:
                return "ROAMING";
            case 6:
                return "EMERGENCY";
            default:
                return "Unknown reg state " + i;
        }
    }

    public static java.lang.String nrStateToString(int i) {
        switch (i) {
            case 1:
                return "RESTRICTED";
            case 2:
                return "NOT_RESTRICTED";
            case 3:
                return "CONNECTED";
            default:
                return android.security.keystore.KeyProperties.DIGEST_NONE;
        }
    }

    static java.lang.String domainToString(int i) {
        switch (i) {
            case 1:
                return "CS";
            case 2:
                return "PS";
            case 3:
                return "CS_PS";
            default:
                return "UNKNOWN";
        }
    }

    public static java.lang.String isNonTerrestrialNetworkToString(boolean z) {
        return z ? "NON-TERRESTRIAL" : "TERRESTRIAL";
    }

    public java.lang.String toString() {
        return "NetworkRegistrationInfo{ domain=" + domainToString(this.mDomain) + " transportType=" + android.telephony.AccessNetworkConstants.transportTypeToString(this.mTransportType) + " registrationState=" + registrationStateToString(this.mRegistrationState) + " networkRegistrationState=" + registrationStateToString(this.mNetworkRegistrationState) + " roamingType=" + android.telephony.ServiceState.roamingTypeToString(this.mRoamingType) + " accessNetworkTechnology=" + android.telephony.TelephonyManager.getNetworkTypeName(this.mAccessNetworkTechnology) + " rejectCause=" + this.mRejectCause + " emergencyEnabled=" + this.mEmergencyOnly + " availableServices=" + (android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + (this.mAvailableServices != null ? (java.lang.String) this.mAvailableServices.stream().map(new java.util.function.Function() { // from class: android.telephony.NetworkRegistrationInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String serviceTypeToString;
                serviceTypeToString = android.telephony.NetworkRegistrationInfo.serviceTypeToString(((java.lang.Integer) obj).intValue());
                return serviceTypeToString;
            }
        }).collect(java.util.stream.Collectors.joining(",")) : null) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END) + " cellIdentity=" + this.mCellIdentity + " voiceSpecificInfo=" + this.mVoiceSpecificInfo + " dataSpecificInfo=" + this.mDataSpecificInfo + " nrState=" + (android.os.Build.IS_DEBUGGABLE ? nrStateToString(this.mNrState) : "****") + " rRplmn=" + this.mRplmn + " isUsingCarrierAggregation=" + this.mIsUsingCarrierAggregation + " isNonTerrestrialNetwork=" + isNonTerrestrialNetworkToString(this.mIsNonTerrestrialNetwork) + "}";
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mDomain), java.lang.Integer.valueOf(this.mTransportType), java.lang.Integer.valueOf(this.mRegistrationState), java.lang.Integer.valueOf(this.mNetworkRegistrationState), java.lang.Integer.valueOf(this.mRoamingType), java.lang.Integer.valueOf(this.mAccessNetworkTechnology), java.lang.Integer.valueOf(this.mRejectCause), java.lang.Boolean.valueOf(this.mEmergencyOnly), this.mAvailableServices, this.mCellIdentity, this.mVoiceSpecificInfo, this.mDataSpecificInfo, java.lang.Integer.valueOf(this.mNrState), this.mRplmn, java.lang.Boolean.valueOf(this.mIsUsingCarrierAggregation), java.lang.Boolean.valueOf(this.mIsNonTerrestrialNetwork));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.NetworkRegistrationInfo)) {
            return false;
        }
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo = (android.telephony.NetworkRegistrationInfo) obj;
        return this.mDomain == networkRegistrationInfo.mDomain && this.mTransportType == networkRegistrationInfo.mTransportType && this.mRegistrationState == networkRegistrationInfo.mRegistrationState && this.mNetworkRegistrationState == networkRegistrationInfo.mNetworkRegistrationState && this.mRoamingType == networkRegistrationInfo.mRoamingType && this.mAccessNetworkTechnology == networkRegistrationInfo.mAccessNetworkTechnology && this.mRejectCause == networkRegistrationInfo.mRejectCause && this.mEmergencyOnly == networkRegistrationInfo.mEmergencyOnly && this.mAvailableServices.equals(networkRegistrationInfo.mAvailableServices) && this.mIsUsingCarrierAggregation == networkRegistrationInfo.mIsUsingCarrierAggregation && java.util.Objects.equals(this.mCellIdentity, networkRegistrationInfo.mCellIdentity) && java.util.Objects.equals(this.mVoiceSpecificInfo, networkRegistrationInfo.mVoiceSpecificInfo) && java.util.Objects.equals(this.mDataSpecificInfo, networkRegistrationInfo.mDataSpecificInfo) && android.text.TextUtils.equals(this.mRplmn, networkRegistrationInfo.mRplmn) && this.mNrState == networkRegistrationInfo.mNrState && this.mIsNonTerrestrialNetwork == networkRegistrationInfo.mIsNonTerrestrialNetwork;
    }

    @Override // android.os.Parcelable
    @android.annotation.SystemApi
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mDomain);
        parcel.writeInt(this.mTransportType);
        parcel.writeInt(this.mRegistrationState);
        parcel.writeInt(this.mNetworkRegistrationState);
        parcel.writeInt(this.mRoamingType);
        parcel.writeInt(this.mAccessNetworkTechnology);
        parcel.writeInt(this.mRejectCause);
        parcel.writeBoolean(this.mEmergencyOnly);
        parcel.writeList(this.mAvailableServices);
        parcel.writeParcelable(this.mCellIdentity, 0);
        parcel.writeParcelable(this.mVoiceSpecificInfo, 0);
        parcel.writeParcelable(this.mDataSpecificInfo, 0);
        parcel.writeInt(this.mNrState);
        parcel.writeString(this.mRplmn);
        parcel.writeBoolean(this.mIsUsingCarrierAggregation);
        parcel.writeBoolean(this.mIsNonTerrestrialNetwork);
    }

    public void updateNrState() {
        this.mNrState = 0;
        if (this.mDataSpecificInfo != null && this.mDataSpecificInfo.isEnDcAvailable) {
            if (!this.mDataSpecificInfo.isDcNrRestricted && this.mDataSpecificInfo.isNrAvailable) {
                this.mNrState = 2;
            } else {
                this.mNrState = 1;
            }
        }
    }

    public android.telephony.NetworkRegistrationInfo sanitizeLocationInfo() {
        android.telephony.NetworkRegistrationInfo copy = copy();
        copy.mCellIdentity = null;
        return copy;
    }

    private android.telephony.NetworkRegistrationInfo copy() {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo = new android.telephony.NetworkRegistrationInfo(obtain);
        obtain.recycle();
        return networkRegistrationInfo;
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private int mAccessNetworkTechnology;
        private java.util.List<java.lang.Integer> mAvailableServices;
        private android.telephony.CellIdentity mCellIdentity;
        private android.telephony.DataSpecificRegistrationInfo mDataSpecificRegistrationInfo;
        private int mDomain;
        private boolean mEmergencyOnly;
        private boolean mIsNonTerrestrialNetwork;
        private int mNetworkRegistrationState;
        private int mRejectCause;
        private java.lang.String mRplmn = "";
        private int mTransportType;
        private android.telephony.VoiceSpecificRegistrationInfo mVoiceSpecificRegistrationInfo;

        public Builder() {
        }

        public Builder(android.telephony.NetworkRegistrationInfo networkRegistrationInfo) {
            this.mDomain = networkRegistrationInfo.mDomain;
            this.mTransportType = networkRegistrationInfo.mTransportType;
            this.mNetworkRegistrationState = networkRegistrationInfo.mNetworkRegistrationState;
            this.mAccessNetworkTechnology = networkRegistrationInfo.mAccessNetworkTechnology;
            this.mRejectCause = networkRegistrationInfo.mRejectCause;
            this.mEmergencyOnly = networkRegistrationInfo.mEmergencyOnly;
            this.mAvailableServices = new java.util.ArrayList(networkRegistrationInfo.mAvailableServices);
            this.mCellIdentity = networkRegistrationInfo.mCellIdentity;
            if (networkRegistrationInfo.mDataSpecificInfo != null) {
                this.mDataSpecificRegistrationInfo = new android.telephony.DataSpecificRegistrationInfo(networkRegistrationInfo.mDataSpecificInfo);
            }
            if (networkRegistrationInfo.mVoiceSpecificInfo != null) {
                this.mVoiceSpecificRegistrationInfo = new android.telephony.VoiceSpecificRegistrationInfo(networkRegistrationInfo.mVoiceSpecificInfo);
            }
            this.mIsNonTerrestrialNetwork = networkRegistrationInfo.mIsNonTerrestrialNetwork;
        }

        public android.telephony.NetworkRegistrationInfo.Builder setDomain(int i) {
            this.mDomain = i;
            return this;
        }

        public android.telephony.NetworkRegistrationInfo.Builder setTransportType(int i) {
            this.mTransportType = i;
            return this;
        }

        public android.telephony.NetworkRegistrationInfo.Builder setRegistrationState(int i) {
            this.mNetworkRegistrationState = i;
            return this;
        }

        public android.telephony.NetworkRegistrationInfo.Builder setAccessNetworkTechnology(int i) {
            this.mAccessNetworkTechnology = i;
            return this;
        }

        public android.telephony.NetworkRegistrationInfo.Builder setRejectCause(int i) {
            this.mRejectCause = i;
            return this;
        }

        @android.annotation.SystemApi
        public android.telephony.NetworkRegistrationInfo.Builder setEmergencyOnly(boolean z) {
            this.mEmergencyOnly = z;
            return this;
        }

        @android.annotation.SystemApi
        public android.telephony.NetworkRegistrationInfo.Builder setAvailableServices(java.util.List<java.lang.Integer> list) {
            this.mAvailableServices = list;
            return this;
        }

        @android.annotation.SystemApi
        public android.telephony.NetworkRegistrationInfo.Builder setCellIdentity(android.telephony.CellIdentity cellIdentity) {
            this.mCellIdentity = cellIdentity;
            return this;
        }

        public android.telephony.NetworkRegistrationInfo.Builder setRegisteredPlmn(java.lang.String str) {
            this.mRplmn = str;
            return this;
        }

        public android.telephony.NetworkRegistrationInfo.Builder setVoiceSpecificInfo(android.telephony.VoiceSpecificRegistrationInfo voiceSpecificRegistrationInfo) {
            this.mVoiceSpecificRegistrationInfo = voiceSpecificRegistrationInfo;
            return this;
        }

        public android.telephony.NetworkRegistrationInfo.Builder setDataSpecificInfo(android.telephony.DataSpecificRegistrationInfo dataSpecificRegistrationInfo) {
            this.mDataSpecificRegistrationInfo = dataSpecificRegistrationInfo;
            return this;
        }

        public android.telephony.NetworkRegistrationInfo.Builder setIsNonTerrestrialNetwork(boolean z) {
            this.mIsNonTerrestrialNetwork = z;
            return this;
        }

        @android.annotation.SystemApi
        public android.telephony.NetworkRegistrationInfo build() {
            return new android.telephony.NetworkRegistrationInfo(this.mDomain, this.mTransportType, this.mNetworkRegistrationState, this.mAccessNetworkTechnology, this.mRejectCause, this.mEmergencyOnly, this.mAvailableServices, this.mCellIdentity, this.mRplmn, this.mVoiceSpecificRegistrationInfo, this.mDataSpecificRegistrationInfo, this.mIsNonTerrestrialNetwork);
        }
    }
}
