package android.telephony;

/* loaded from: classes3.dex */
public class ServiceState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ServiceState> CREATOR = new android.os.Parcelable.Creator<android.telephony.ServiceState>() { // from class: android.telephony.ServiceState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ServiceState createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ServiceState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ServiceState[] newArray(int i) {
            return new android.telephony.ServiceState[i];
        }
    };
    static final boolean DBG = false;
    public static final int DUPLEX_MODE_FDD = 1;
    public static final int DUPLEX_MODE_TDD = 2;
    public static final int DUPLEX_MODE_UNKNOWN = 0;
    private static final java.lang.String EXTRA_SERVICE_STATE = "android.intent.extra.SERVICE_STATE";
    public static final int FREQUENCY_RANGE_COUNT = 5;
    public static final int FREQUENCY_RANGE_HIGH = 3;
    public static final int FREQUENCY_RANGE_LOW = 1;
    public static final int FREQUENCY_RANGE_MID = 2;
    public static final int FREQUENCY_RANGE_MMWAVE = 4;
    public static final int FREQUENCY_RANGE_UNKNOWN = 0;
    static final java.lang.String LOG_TAG = "PHONE";
    private static final int NEXT_RIL_RADIO_TECHNOLOGY = 21;
    public static final int RIL_RADIO_CDMA_TECHNOLOGY_BITMASK = 6392;
    public static final int RIL_RADIO_TECHNOLOGY_1xRTT = 6;
    public static final int RIL_RADIO_TECHNOLOGY_EDGE = 2;
    public static final int RIL_RADIO_TECHNOLOGY_EHRPD = 13;
    public static final int RIL_RADIO_TECHNOLOGY_EVDO_0 = 7;
    public static final int RIL_RADIO_TECHNOLOGY_EVDO_A = 8;
    public static final int RIL_RADIO_TECHNOLOGY_EVDO_B = 12;
    public static final int RIL_RADIO_TECHNOLOGY_GPRS = 1;
    public static final int RIL_RADIO_TECHNOLOGY_GSM = 16;
    public static final int RIL_RADIO_TECHNOLOGY_HSDPA = 9;
    public static final int RIL_RADIO_TECHNOLOGY_HSPA = 11;
    public static final int RIL_RADIO_TECHNOLOGY_HSPAP = 15;
    public static final int RIL_RADIO_TECHNOLOGY_HSUPA = 10;
    public static final int RIL_RADIO_TECHNOLOGY_IS95A = 4;
    public static final int RIL_RADIO_TECHNOLOGY_IS95B = 5;
    public static final int RIL_RADIO_TECHNOLOGY_IWLAN = 18;
    public static final int RIL_RADIO_TECHNOLOGY_LTE = 14;
    public static final int RIL_RADIO_TECHNOLOGY_LTE_CA = 19;
    public static final int RIL_RADIO_TECHNOLOGY_NR = 20;
    public static final int RIL_RADIO_TECHNOLOGY_TD_SCDMA = 17;
    public static final int RIL_RADIO_TECHNOLOGY_UMTS = 3;
    public static final int RIL_RADIO_TECHNOLOGY_UNKNOWN = 0;

    @android.annotation.SystemApi
    public static final int ROAMING_TYPE_DOMESTIC = 2;

    @android.annotation.SystemApi
    public static final int ROAMING_TYPE_INTERNATIONAL = 3;

    @android.annotation.SystemApi
    public static final int ROAMING_TYPE_NOT_ROAMING = 0;

    @android.annotation.SystemApi
    public static final int ROAMING_TYPE_UNKNOWN = 1;
    public static final int STATE_EMERGENCY_ONLY = 2;
    public static final int STATE_IN_SERVICE = 0;
    public static final int STATE_OUT_OF_SERVICE = 1;
    public static final int STATE_POWER_OFF = 3;
    public static final int UNKNOWN_ID = -1;
    static final boolean VDBG = false;
    private int mArfcnRsrpBoost;
    private int mCdmaDefaultRoamingIndicator;
    private int mCdmaEriIconIndex;
    private int mCdmaEriIconMode;
    private int mCdmaRoamingIndicator;
    private int[] mCellBandwidths;
    private int mChannelNumber;
    private boolean mCssIndicator;
    private int mDataRegState;
    private boolean mIsDataRoamingFromRegistration;
    private boolean mIsEmergencyOnly;
    private boolean mIsIwlanPreferred;
    private boolean mIsManualNetworkSelection;
    private int mNetworkId;
    private final java.util.List<android.telephony.NetworkRegistrationInfo> mNetworkRegistrationInfos;
    private int mNrFrequencyRange;
    private java.lang.String mOperatorAlphaLong;
    private java.lang.String mOperatorAlphaLongRaw;
    private java.lang.String mOperatorAlphaShort;
    private java.lang.String mOperatorAlphaShortRaw;
    private java.lang.String mOperatorNumeric;
    private int mSystemId;
    private int mVoiceRegState;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DuplexMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FrequencyRange {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RegState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RilRadioTechnology {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RoamingType {
    }

    public static final java.lang.String getRoamingLogString(int i) {
        switch (i) {
            case 0:
                return "home";
            case 1:
                return "roaming";
            case 2:
                return "Domestic Roaming";
            case 3:
                return "International Roaming";
            default:
                return "UNKNOWN";
        }
    }

    public static android.telephony.ServiceState newFromBundle(android.os.Bundle bundle) {
        android.telephony.ServiceState serviceState = new android.telephony.ServiceState();
        serviceState.setFromNotifierBundle(bundle);
        return serviceState;
    }

    public ServiceState() {
        this.mVoiceRegState = 1;
        this.mDataRegState = 1;
        this.mCellBandwidths = new int[0];
        this.mArfcnRsrpBoost = 0;
        this.mNetworkRegistrationInfos = new java.util.ArrayList();
    }

    public ServiceState(android.telephony.ServiceState serviceState) {
        this.mVoiceRegState = 1;
        this.mDataRegState = 1;
        this.mCellBandwidths = new int[0];
        this.mArfcnRsrpBoost = 0;
        this.mNetworkRegistrationInfos = new java.util.ArrayList();
        copyFrom(serviceState);
    }

    protected void copyFrom(android.telephony.ServiceState serviceState) {
        this.mVoiceRegState = serviceState.mVoiceRegState;
        this.mDataRegState = serviceState.mDataRegState;
        this.mOperatorAlphaLong = serviceState.mOperatorAlphaLong;
        this.mOperatorAlphaShort = serviceState.mOperatorAlphaShort;
        this.mOperatorNumeric = serviceState.mOperatorNumeric;
        this.mIsManualNetworkSelection = serviceState.mIsManualNetworkSelection;
        this.mCssIndicator = serviceState.mCssIndicator;
        this.mNetworkId = serviceState.mNetworkId;
        this.mSystemId = serviceState.mSystemId;
        this.mCdmaRoamingIndicator = serviceState.mCdmaRoamingIndicator;
        this.mCdmaDefaultRoamingIndicator = serviceState.mCdmaDefaultRoamingIndicator;
        this.mCdmaEriIconIndex = serviceState.mCdmaEriIconIndex;
        this.mCdmaEriIconMode = serviceState.mCdmaEriIconMode;
        this.mIsEmergencyOnly = serviceState.mIsEmergencyOnly;
        this.mChannelNumber = serviceState.mChannelNumber;
        this.mCellBandwidths = serviceState.mCellBandwidths == null ? null : java.util.Arrays.copyOf(serviceState.mCellBandwidths, serviceState.mCellBandwidths.length);
        this.mArfcnRsrpBoost = serviceState.mArfcnRsrpBoost;
        synchronized (this.mNetworkRegistrationInfos) {
            this.mNetworkRegistrationInfos.clear();
            java.util.Iterator<android.telephony.NetworkRegistrationInfo> it = serviceState.getNetworkRegistrationInfoList().iterator();
            while (it.hasNext()) {
                this.mNetworkRegistrationInfos.add(new android.telephony.NetworkRegistrationInfo(it.next()));
            }
        }
        this.mNrFrequencyRange = serviceState.mNrFrequencyRange;
        this.mOperatorAlphaLongRaw = serviceState.mOperatorAlphaLongRaw;
        this.mOperatorAlphaShortRaw = serviceState.mOperatorAlphaShortRaw;
        this.mIsDataRoamingFromRegistration = serviceState.mIsDataRoamingFromRegistration;
        this.mIsIwlanPreferred = serviceState.mIsIwlanPreferred;
    }

    @java.lang.Deprecated
    public ServiceState(android.os.Parcel parcel) {
        boolean z;
        boolean z2;
        this.mVoiceRegState = 1;
        this.mDataRegState = 1;
        this.mCellBandwidths = new int[0];
        this.mArfcnRsrpBoost = 0;
        this.mNetworkRegistrationInfos = new java.util.ArrayList();
        this.mVoiceRegState = parcel.readInt();
        this.mDataRegState = parcel.readInt();
        this.mOperatorAlphaLong = parcel.readString();
        this.mOperatorAlphaShort = parcel.readString();
        this.mOperatorNumeric = parcel.readString();
        if (parcel.readInt() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mIsManualNetworkSelection = z;
        if (parcel.readInt() != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.mCssIndicator = z2;
        this.mNetworkId = parcel.readInt();
        this.mSystemId = parcel.readInt();
        this.mCdmaRoamingIndicator = parcel.readInt();
        this.mCdmaDefaultRoamingIndicator = parcel.readInt();
        this.mCdmaEriIconIndex = parcel.readInt();
        this.mCdmaEriIconMode = parcel.readInt();
        this.mIsEmergencyOnly = parcel.readInt() != 0;
        this.mArfcnRsrpBoost = parcel.readInt();
        synchronized (this.mNetworkRegistrationInfos) {
            parcel.readList(this.mNetworkRegistrationInfos, android.telephony.NetworkRegistrationInfo.class.getClassLoader(), android.telephony.NetworkRegistrationInfo.class);
        }
        this.mChannelNumber = parcel.readInt();
        this.mCellBandwidths = parcel.createIntArray();
        this.mNrFrequencyRange = parcel.readInt();
        this.mOperatorAlphaLongRaw = parcel.readString();
        this.mOperatorAlphaShortRaw = parcel.readString();
        this.mIsDataRoamingFromRegistration = parcel.readBoolean();
        this.mIsIwlanPreferred = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mVoiceRegState);
        parcel.writeInt(this.mDataRegState);
        parcel.writeString(this.mOperatorAlphaLong);
        parcel.writeString(this.mOperatorAlphaShort);
        parcel.writeString(this.mOperatorNumeric);
        parcel.writeInt(this.mIsManualNetworkSelection ? 1 : 0);
        parcel.writeInt(this.mCssIndicator ? 1 : 0);
        parcel.writeInt(this.mNetworkId);
        parcel.writeInt(this.mSystemId);
        parcel.writeInt(this.mCdmaRoamingIndicator);
        parcel.writeInt(this.mCdmaDefaultRoamingIndicator);
        parcel.writeInt(this.mCdmaEriIconIndex);
        parcel.writeInt(this.mCdmaEriIconMode);
        parcel.writeInt(this.mIsEmergencyOnly ? 1 : 0);
        parcel.writeInt(this.mArfcnRsrpBoost);
        synchronized (this.mNetworkRegistrationInfos) {
            parcel.writeList(this.mNetworkRegistrationInfos);
        }
        parcel.writeInt(this.mChannelNumber);
        parcel.writeIntArray(this.mCellBandwidths);
        parcel.writeInt(this.mNrFrequencyRange);
        parcel.writeString(this.mOperatorAlphaLongRaw);
        parcel.writeString(this.mOperatorAlphaShortRaw);
        parcel.writeBoolean(this.mIsDataRoamingFromRegistration);
        parcel.writeBoolean(this.mIsIwlanPreferred);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getState() {
        return getVoiceRegState();
    }

    public int getVoiceRegState() {
        return this.mVoiceRegState;
    }

    public int getDataRegState() {
        return this.mDataRegState;
    }

    public int getDataRegistrationState() {
        return getDataRegState();
    }

    public int getDuplexMode() {
        if (!isPsOnlyTech(getRilDataRadioTechnology())) {
            return 0;
        }
        return android.telephony.AccessNetworkUtils.getDuplexModeForEutranBand(android.telephony.AccessNetworkUtils.getOperatingBandForEarfcn(this.mChannelNumber));
    }

    public int getChannelNumber() {
        return this.mChannelNumber;
    }

    public int[] getCellBandwidths() {
        return this.mCellBandwidths == null ? new int[0] : this.mCellBandwidths;
    }

    public boolean getRoaming() {
        return getVoiceRoaming() || getDataRoaming();
    }

    public boolean getVoiceRoaming() {
        return getVoiceRoamingType() != 0;
    }

    public int getVoiceRoamingType() {
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo = getNetworkRegistrationInfo(1, 1);
        if (networkRegistrationInfo != null) {
            return networkRegistrationInfo.getRoamingType();
        }
        return 0;
    }

    public boolean getDataRoaming() {
        return getDataRoamingType() != 0;
    }

    public void setDataRoamingFromRegistration(boolean z) {
        this.mIsDataRoamingFromRegistration = z;
    }

    public boolean getDataRoamingFromRegistration() {
        return this.mIsDataRoamingFromRegistration;
    }

    public int getDataRoamingType() {
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo = getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo != null) {
            return networkRegistrationInfo.getRoamingType();
        }
        return 0;
    }

    public boolean isEmergencyOnly() {
        return this.mIsEmergencyOnly;
    }

    public int getCdmaRoamingIndicator() {
        return this.mCdmaRoamingIndicator;
    }

    public int getCdmaDefaultRoamingIndicator() {
        return this.mCdmaDefaultRoamingIndicator;
    }

    public int getCdmaEriIconIndex() {
        return this.mCdmaEriIconIndex;
    }

    public int getCdmaEriIconMode() {
        return this.mCdmaEriIconMode;
    }

    public java.lang.String getOperatorAlphaLong() {
        return this.mOperatorAlphaLong;
    }

    public java.lang.String getVoiceOperatorAlphaLong() {
        return this.mOperatorAlphaLong;
    }

    public java.lang.String getOperatorAlphaShort() {
        return this.mOperatorAlphaShort;
    }

    public java.lang.String getVoiceOperatorAlphaShort() {
        return this.mOperatorAlphaShort;
    }

    public java.lang.String getDataOperatorAlphaShort() {
        return this.mOperatorAlphaShort;
    }

    public java.lang.String getOperatorAlpha() {
        if (android.text.TextUtils.isEmpty(this.mOperatorAlphaLong)) {
            return this.mOperatorAlphaShort;
        }
        return this.mOperatorAlphaLong;
    }

    public java.lang.String getOperatorNumeric() {
        return this.mOperatorNumeric;
    }

    public java.lang.String getVoiceOperatorNumeric() {
        return this.mOperatorNumeric;
    }

    public java.lang.String getDataOperatorNumeric() {
        return this.mOperatorNumeric;
    }

    public boolean getIsManualSelection() {
        return this.mIsManualNetworkSelection;
    }

    public int hashCode() {
        int hash;
        synchronized (this.mNetworkRegistrationInfos) {
            hash = java.util.Objects.hash(java.lang.Integer.valueOf(this.mVoiceRegState), java.lang.Integer.valueOf(this.mDataRegState), java.lang.Integer.valueOf(this.mChannelNumber), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mCellBandwidths)), this.mOperatorAlphaLong, this.mOperatorAlphaShort, this.mOperatorNumeric, java.lang.Boolean.valueOf(this.mIsManualNetworkSelection), java.lang.Boolean.valueOf(this.mCssIndicator), java.lang.Integer.valueOf(this.mNetworkId), java.lang.Integer.valueOf(this.mSystemId), java.lang.Integer.valueOf(this.mCdmaRoamingIndicator), java.lang.Integer.valueOf(this.mCdmaDefaultRoamingIndicator), java.lang.Integer.valueOf(this.mCdmaEriIconIndex), java.lang.Integer.valueOf(this.mCdmaEriIconMode), java.lang.Boolean.valueOf(this.mIsEmergencyOnly), java.lang.Integer.valueOf(this.mArfcnRsrpBoost), this.mNetworkRegistrationInfos, java.lang.Integer.valueOf(this.mNrFrequencyRange), this.mOperatorAlphaLongRaw, this.mOperatorAlphaShortRaw, java.lang.Boolean.valueOf(this.mIsDataRoamingFromRegistration), java.lang.Boolean.valueOf(this.mIsIwlanPreferred));
        }
        return hash;
    }

    public boolean equals(java.lang.Object obj) {
        boolean z = false;
        if (!(obj instanceof android.telephony.ServiceState)) {
            return false;
        }
        android.telephony.ServiceState serviceState = (android.telephony.ServiceState) obj;
        synchronized (this.mNetworkRegistrationInfos) {
            if (this.mVoiceRegState == serviceState.mVoiceRegState && this.mDataRegState == serviceState.mDataRegState && this.mIsManualNetworkSelection == serviceState.mIsManualNetworkSelection && this.mChannelNumber == serviceState.mChannelNumber && java.util.Arrays.equals(this.mCellBandwidths, serviceState.mCellBandwidths) && equalsHandlesNulls(this.mOperatorAlphaLong, serviceState.mOperatorAlphaLong) && equalsHandlesNulls(this.mOperatorAlphaShort, serviceState.mOperatorAlphaShort) && equalsHandlesNulls(this.mOperatorNumeric, serviceState.mOperatorNumeric) && equalsHandlesNulls(java.lang.Boolean.valueOf(this.mCssIndicator), java.lang.Boolean.valueOf(serviceState.mCssIndicator)) && equalsHandlesNulls(java.lang.Integer.valueOf(this.mNetworkId), java.lang.Integer.valueOf(serviceState.mNetworkId)) && equalsHandlesNulls(java.lang.Integer.valueOf(this.mSystemId), java.lang.Integer.valueOf(serviceState.mSystemId)) && equalsHandlesNulls(java.lang.Integer.valueOf(this.mCdmaRoamingIndicator), java.lang.Integer.valueOf(serviceState.mCdmaRoamingIndicator)) && equalsHandlesNulls(java.lang.Integer.valueOf(this.mCdmaDefaultRoamingIndicator), java.lang.Integer.valueOf(serviceState.mCdmaDefaultRoamingIndicator)) && this.mIsEmergencyOnly == serviceState.mIsEmergencyOnly && equalsHandlesNulls(this.mOperatorAlphaLongRaw, serviceState.mOperatorAlphaLongRaw) && equalsHandlesNulls(this.mOperatorAlphaShortRaw, serviceState.mOperatorAlphaShortRaw) && this.mNetworkRegistrationInfos.size() == serviceState.mNetworkRegistrationInfos.size() && this.mNetworkRegistrationInfos.containsAll(serviceState.mNetworkRegistrationInfos) && this.mNrFrequencyRange == serviceState.mNrFrequencyRange && this.mIsDataRoamingFromRegistration == serviceState.mIsDataRoamingFromRegistration && this.mIsIwlanPreferred == serviceState.mIsIwlanPreferred) {
                z = true;
            }
        }
        return z;
    }

    public static java.lang.String roamingTypeToString(int i) {
        switch (i) {
            case 0:
                return "NOT_ROAMING";
            case 1:
                return "UNKNOWN";
            case 2:
                return "DOMESTIC";
            case 3:
                return "INTERNATIONAL";
            default:
                return "Unknown roaming type " + i;
        }
    }

    public static java.lang.String rilRadioTechnologyToString(int i) {
        switch (i) {
            case 0:
                return "Unknown";
            case 1:
                return "GPRS";
            case 2:
                return "EDGE";
            case 3:
                return "UMTS";
            case 4:
                return "CDMA-IS95A";
            case 5:
                return "CDMA-IS95B";
            case 6:
                return "1xRTT";
            case 7:
                return "EvDo-rev.0";
            case 8:
                return "EvDo-rev.A";
            case 9:
                return "HSDPA";
            case 10:
                return "HSUPA";
            case 11:
                return "HSPA";
            case 12:
                return "EvDo-rev.B";
            case 13:
                return "eHRPD";
            case 14:
                return com.android.internal.telephony.DctConstants.RAT_NAME_LTE;
            case 15:
                return "HSPAP";
            case 16:
                return "GSM";
            case 17:
                return "TD-SCDMA";
            case 18:
                return "IWLAN";
            case 19:
                return "LTE_CA";
            case 20:
                return "NR_SA";
            default:
                com.android.telephony.Rlog.w(LOG_TAG, "Unexpected radioTechnology=" + i);
                return "Unexpected";
        }
    }

    public static java.lang.String frequencyRangeToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "LOW";
            case 2:
                return "MID";
            case 3:
                return "HIGH";
            case 4:
                return "MMWAVE";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static java.lang.String rilServiceStateToString(int i) {
        switch (i) {
            case 0:
                return "IN_SERVICE";
            case 1:
                return "OUT_OF_SERVICE";
            case 2:
                return "EMERGENCY_ONLY";
            case 3:
                return "POWER_OFF";
            default:
                return "UNKNOWN";
        }
    }

    public java.lang.String toString() {
        java.lang.String str;
        synchronized (this.mNetworkRegistrationInfos) {
            str = "{mVoiceRegState=" + this.mVoiceRegState + (android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + rilServiceStateToString(this.mVoiceRegState) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END) + ", mDataRegState=" + this.mDataRegState + (android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + rilServiceStateToString(this.mDataRegState) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END) + ", mChannelNumber=" + this.mChannelNumber + ", duplexMode()=" + getDuplexMode() + ", mCellBandwidths=" + java.util.Arrays.toString(this.mCellBandwidths) + ", mOperatorAlphaLong=" + this.mOperatorAlphaLong + ", mOperatorAlphaShort=" + this.mOperatorAlphaShort + ", isManualNetworkSelection=" + this.mIsManualNetworkSelection + (this.mIsManualNetworkSelection ? "(manual)" : "(automatic)") + ", getRilVoiceRadioTechnology=" + getRilVoiceRadioTechnology() + (android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + rilRadioTechnologyToString(getRilVoiceRadioTechnology()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END) + ", getRilDataRadioTechnology=" + getRilDataRadioTechnology() + (android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + rilRadioTechnologyToString(getRilDataRadioTechnology()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END) + ", mCssIndicator=" + (this.mCssIndicator ? "supported" : "unsupported") + ", mNetworkId=" + this.mNetworkId + ", mSystemId=" + this.mSystemId + ", mCdmaRoamingIndicator=" + this.mCdmaRoamingIndicator + ", mCdmaDefaultRoamingIndicator=" + this.mCdmaDefaultRoamingIndicator + ", mIsEmergencyOnly=" + this.mIsEmergencyOnly + ", isUsingCarrierAggregation=" + isUsingCarrierAggregation() + ", mArfcnRsrpBoost=" + this.mArfcnRsrpBoost + ", mNetworkRegistrationInfos=" + this.mNetworkRegistrationInfos + ", mNrFrequencyRange=" + (android.os.Build.IS_DEBUGGABLE ? this.mNrFrequencyRange : 0) + ", mOperatorAlphaLongRaw=" + this.mOperatorAlphaLongRaw + ", mOperatorAlphaShortRaw=" + this.mOperatorAlphaShortRaw + ", mIsDataRoamingFromRegistration=" + this.mIsDataRoamingFromRegistration + ", mIsIwlanPreferred=" + this.mIsIwlanPreferred + "}";
        }
        return str;
    }

    private void init() {
        this.mVoiceRegState = 1;
        this.mDataRegState = 1;
        this.mChannelNumber = -1;
        this.mCellBandwidths = new int[0];
        this.mOperatorAlphaLong = null;
        this.mOperatorAlphaShort = null;
        this.mOperatorNumeric = null;
        this.mIsManualNetworkSelection = false;
        this.mCssIndicator = false;
        this.mNetworkId = -1;
        this.mSystemId = -1;
        this.mCdmaRoamingIndicator = -1;
        this.mCdmaDefaultRoamingIndicator = -1;
        this.mCdmaEriIconIndex = -1;
        this.mCdmaEriIconMode = -1;
        this.mIsEmergencyOnly = false;
        this.mArfcnRsrpBoost = 0;
        this.mNrFrequencyRange = 0;
        synchronized (this.mNetworkRegistrationInfos) {
            this.mNetworkRegistrationInfos.clear();
            addNetworkRegistrationInfo(new android.telephony.NetworkRegistrationInfo.Builder().setDomain(1).setTransportType(1).setRegistrationState(4).build());
            addNetworkRegistrationInfo(new android.telephony.NetworkRegistrationInfo.Builder().setDomain(2).setTransportType(1).setRegistrationState(4).build());
            addNetworkRegistrationInfo(new android.telephony.NetworkRegistrationInfo.Builder().setDomain(2).setTransportType(2).setRegistrationState(4).build());
        }
        this.mOperatorAlphaLongRaw = null;
        this.mOperatorAlphaShortRaw = null;
        this.mIsDataRoamingFromRegistration = false;
        this.mIsIwlanPreferred = false;
    }

    public void setStateOutOfService() {
        init();
    }

    public void setStateOff() {
        init();
        this.mVoiceRegState = 3;
        this.mDataRegState = 3;
    }

    public void setOutOfService(boolean z) {
        init();
        if (z) {
            this.mVoiceRegState = 3;
            this.mDataRegState = 3;
        }
    }

    public void setState(int i) {
        setVoiceRegState(i);
    }

    public void setVoiceRegState(int i) {
        this.mVoiceRegState = i;
    }

    public void setDataRegState(int i) {
        this.mDataRegState = i;
    }

    public void setCellBandwidths(int[] iArr) {
        this.mCellBandwidths = iArr;
    }

    public void setChannelNumber(int i) {
        this.mChannelNumber = i;
    }

    public void setRoaming(boolean z) {
        setVoiceRoaming(z);
        setDataRoaming(z);
    }

    public void setVoiceRoaming(boolean z) {
        setVoiceRoamingType(z ? 1 : 0);
    }

    public void setVoiceRoamingType(int i) {
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo = getNetworkRegistrationInfo(1, 1);
        if (networkRegistrationInfo == null) {
            networkRegistrationInfo = new android.telephony.NetworkRegistrationInfo.Builder().setDomain(1).setTransportType(1).build();
        }
        networkRegistrationInfo.setRoamingType(i);
        addNetworkRegistrationInfo(networkRegistrationInfo);
    }

    public void setDataRoaming(boolean z) {
        setDataRoamingType(z ? 1 : 0);
    }

    public void setDataRoamingType(int i) {
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo = getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo == null) {
            networkRegistrationInfo = new android.telephony.NetworkRegistrationInfo.Builder().setDomain(2).setTransportType(1).build();
        }
        networkRegistrationInfo.setRoamingType(i);
        addNetworkRegistrationInfo(networkRegistrationInfo);
    }

    public void setEmergencyOnly(boolean z) {
        this.mIsEmergencyOnly = z;
    }

    public void setCdmaRoamingIndicator(int i) {
        this.mCdmaRoamingIndicator = i;
    }

    public void setCdmaDefaultRoamingIndicator(int i) {
        this.mCdmaDefaultRoamingIndicator = i;
    }

    public void setCdmaEriIconIndex(int i) {
        this.mCdmaEriIconIndex = i;
    }

    public void setCdmaEriIconMode(int i) {
        this.mCdmaEriIconMode = i;
    }

    public void setOperatorName(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mOperatorAlphaLong = str;
        this.mOperatorAlphaShort = str2;
        this.mOperatorNumeric = str3;
    }

    public void setOperatorAlphaLong(java.lang.String str) {
        this.mOperatorAlphaLong = str;
    }

    public void setIsManualSelection(boolean z) {
        this.mIsManualNetworkSelection = z;
    }

    private static boolean equalsHandlesNulls(java.lang.Object obj, java.lang.Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    private void setFromNotifierBundle(android.os.Bundle bundle) {
        android.telephony.ServiceState serviceState = (android.telephony.ServiceState) bundle.getParcelable(EXTRA_SERVICE_STATE, android.telephony.ServiceState.class);
        if (serviceState != null) {
            copyFrom(serviceState);
        }
    }

    public void fillInNotifierBundle(android.os.Bundle bundle) {
        bundle.putParcelable(EXTRA_SERVICE_STATE, this);
        bundle.putInt(android.content.Intent.EXTRA_VOICE_REG_STATE, this.mVoiceRegState);
        bundle.putInt(android.content.Intent.EXTRA_DATA_REG_STATE, this.mDataRegState);
        bundle.putInt(android.content.Intent.EXTRA_DATA_ROAMING_TYPE, getDataRoamingType());
        bundle.putInt(android.content.Intent.EXTRA_VOICE_ROAMING_TYPE, getVoiceRoamingType());
        bundle.putString(android.content.Intent.EXTRA_OPERATOR_ALPHA_LONG, this.mOperatorAlphaLong);
        bundle.putString(android.content.Intent.EXTRA_OPERATOR_ALPHA_SHORT, this.mOperatorAlphaShort);
        bundle.putString(android.content.Intent.EXTRA_OPERATOR_NUMERIC, this.mOperatorNumeric);
        bundle.putString(android.content.Intent.EXTRA_DATA_OPERATOR_ALPHA_LONG, this.mOperatorAlphaLong);
        bundle.putString(android.content.Intent.EXTRA_DATA_OPERATOR_ALPHA_SHORT, this.mOperatorAlphaShort);
        bundle.putString(android.content.Intent.EXTRA_DATA_OPERATOR_NUMERIC, this.mOperatorNumeric);
        bundle.putBoolean(android.content.Intent.EXTRA_MANUAL, this.mIsManualNetworkSelection);
        bundle.putInt(android.content.Intent.EXTRA_VOICE_RADIO_TECH, getRilVoiceRadioTechnology());
        bundle.putInt(android.content.Intent.EXTRA_DATA_RADIO_TECH, getRilDataRadioTechnology());
        bundle.putBoolean(android.content.Intent.EXTRA_CSS_INDICATOR, this.mCssIndicator);
        bundle.putInt(android.content.Intent.EXTRA_NETWORK_ID, this.mNetworkId);
        bundle.putInt(android.content.Intent.EXTRA_SYSTEM_ID, this.mSystemId);
        bundle.putInt(android.content.Intent.EXTRA_CDMA_ROAMING_INDICATOR, this.mCdmaRoamingIndicator);
        bundle.putInt(android.content.Intent.EXTRA_CDMA_DEFAULT_ROAMING_INDICATOR, this.mCdmaDefaultRoamingIndicator);
        bundle.putBoolean(android.content.Intent.EXTRA_EMERGENCY_ONLY, this.mIsEmergencyOnly);
        bundle.putBoolean(android.content.Intent.EXTRA_IS_DATA_ROAMING_FROM_REGISTRATION, getDataRoamingFromRegistration());
        bundle.putBoolean(android.content.Intent.EXTRA_IS_USING_CARRIER_AGGREGATION, isUsingCarrierAggregation());
        bundle.putInt("ArfcnRsrpBoost", this.mArfcnRsrpBoost);
        bundle.putInt("ChannelNumber", this.mChannelNumber);
        bundle.putIntArray("CellBandwidths", this.mCellBandwidths);
        bundle.putInt("mNrFrequencyRange", this.mNrFrequencyRange);
        bundle.putString("operator-alpha-long-raw", this.mOperatorAlphaLongRaw);
        bundle.putString("operator-alpha-short-raw", this.mOperatorAlphaShortRaw);
    }

    public void setRilVoiceRadioTechnology(int i) {
        com.android.telephony.Rlog.e(LOG_TAG, "ServiceState.setRilVoiceRadioTechnology() called. It's encouraged to use addNetworkRegistrationInfo() instead *******");
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo = getNetworkRegistrationInfo(1, 1);
        if (networkRegistrationInfo == null) {
            networkRegistrationInfo = new android.telephony.NetworkRegistrationInfo.Builder().setDomain(1).setTransportType(1).build();
        }
        networkRegistrationInfo.setAccessNetworkTechnology(rilRadioTechnologyToNetworkType(i));
        addNetworkRegistrationInfo(networkRegistrationInfo);
    }

    public void setRilDataRadioTechnology(int i) {
        com.android.telephony.Rlog.e(LOG_TAG, "ServiceState.setRilDataRadioTechnology() called. It's encouraged to use addNetworkRegistrationInfo() instead *******");
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo = getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo == null) {
            networkRegistrationInfo = new android.telephony.NetworkRegistrationInfo.Builder().setDomain(2).setTransportType(1).build();
        }
        networkRegistrationInfo.setAccessNetworkTechnology(rilRadioTechnologyToNetworkType(i));
        addNetworkRegistrationInfo(networkRegistrationInfo);
    }

    public boolean isUsingCarrierAggregation() {
        if (getCellBandwidths().length > 1) {
            return true;
        }
        synchronized (this.mNetworkRegistrationInfos) {
            java.util.Iterator<android.telephony.NetworkRegistrationInfo> it = this.mNetworkRegistrationInfos.iterator();
            while (it.hasNext()) {
                if (it.next().isUsingCarrierAggregation()) {
                    return true;
                }
            }
            return false;
        }
    }

    public int getNrFrequencyRange() {
        return this.mNrFrequencyRange;
    }

    public int getNrState() {
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo = getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo == null) {
            return 0;
        }
        return networkRegistrationInfo.getNrState();
    }

    public void setNrFrequencyRange(int i) {
        this.mNrFrequencyRange = i;
    }

    public int getArfcnRsrpBoost() {
        return this.mArfcnRsrpBoost;
    }

    public void setArfcnRsrpBoost(int i) {
        this.mArfcnRsrpBoost = i;
    }

    public void setCssIndicator(int i) {
        this.mCssIndicator = i != 0;
    }

    public void setCdmaSystemAndNetworkId(int i, int i2) {
        this.mSystemId = i;
        this.mNetworkId = i2;
    }

    public int getRilVoiceRadioTechnology() {
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo = getNetworkRegistrationInfo(1, 1);
        if (networkRegistrationInfo != null) {
            return networkTypeToRilRadioTechnology(networkRegistrationInfo.getAccessNetworkTechnology());
        }
        return 0;
    }

    public int getRilDataRadioTechnology() {
        return networkTypeToRilRadioTechnology(getDataNetworkType());
    }

    public static int rilRadioTechnologyToNetworkType(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
            case 5:
                return 4;
            case 6:
                return 7;
            case 7:
                return 5;
            case 8:
                return 6;
            case 9:
                return 8;
            case 10:
                return 9;
            case 11:
                return 10;
            case 12:
                return 12;
            case 13:
                return 14;
            case 14:
                return 13;
            case 15:
                return 15;
            case 16:
                return 16;
            case 17:
                return 17;
            case 18:
                return 18;
            case 19:
                return 19;
            case 20:
                return 20;
            default:
                return 0;
        }
    }

    public static int rilRadioTechnologyToAccessNetworkType(int i) {
        switch (i) {
            case 1:
            case 2:
            case 16:
                return 1;
            case 3:
            case 9:
            case 10:
            case 11:
            case 15:
            case 17:
                return 2;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 12:
            case 13:
                return 4;
            case 14:
            case 19:
                return 3;
            case 18:
                return 5;
            case 20:
                return 6;
            default:
                return 0;
        }
    }

    public static int networkTypeToRilRadioTechnology(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 7;
            case 6:
                return 8;
            case 7:
                return 6;
            case 8:
                return 9;
            case 9:
                return 10;
            case 10:
                return 11;
            case 11:
            default:
                return 0;
            case 12:
                return 12;
            case 13:
                return 14;
            case 14:
                return 13;
            case 15:
                return 15;
            case 16:
                return 16;
            case 17:
                return 17;
            case 18:
                return 18;
            case 19:
                return 19;
            case 20:
                return 20;
        }
    }

    public int getDataNetworkType() {
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo = getNetworkRegistrationInfo(2, 2);
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo2 = getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo == null || !networkRegistrationInfo.isInService()) {
            if (networkRegistrationInfo2 != null) {
                return networkRegistrationInfo2.getAccessNetworkTechnology();
            }
            return 0;
        }
        if (!networkRegistrationInfo2.isInService() || this.mIsIwlanPreferred) {
            return networkRegistrationInfo.getAccessNetworkTechnology();
        }
        return networkRegistrationInfo2.getAccessNetworkTechnology();
    }

    public int getVoiceNetworkType() {
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo = getNetworkRegistrationInfo(1, 1);
        if (networkRegistrationInfo != null) {
            return networkRegistrationInfo.getAccessNetworkTechnology();
        }
        return 0;
    }

    public int getCssIndicator() {
        return this.mCssIndicator ? 1 : 0;
    }

    public int getCdmaNetworkId() {
        return this.mNetworkId;
    }

    public int getCdmaSystemId() {
        return this.mSystemId;
    }

    public static boolean isGsm(int i) {
        return i == 1 || i == 2 || i == 3 || i == 9 || i == 10 || i == 11 || i == 14 || i == 15 || i == 16 || i == 17 || i == 18 || i == 19 || i == 20;
    }

    public static boolean isCdma(int i) {
        return i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 12 || i == 13;
    }

    public static boolean isPsOnlyTech(int i) {
        return i == 14 || i == 19 || i == 20;
    }

    public static boolean bearerBitmapHasCdma(int i) {
        return (convertNetworkTypeBitmaskToBearerBitmask(i) & RIL_RADIO_CDMA_TECHNOLOGY_BITMASK) != 0;
    }

    public static boolean bitmaskHasTech(int i, int i2) {
        if (i == 0) {
            return true;
        }
        if (i2 >= 1 && (i & (1 << (i2 - 1))) != 0) {
            return true;
        }
        return false;
    }

    public static int getBitmaskForTech(int i) {
        if (i >= 1) {
            return 1 << (i - 1);
        }
        return 0;
    }

    public static int getBitmaskFromString(java.lang.String str) {
        int i = 0;
        for (java.lang.String str2 : str.split("\\|")) {
            try {
                int parseInt = java.lang.Integer.parseInt(str2.trim());
                if (parseInt == 0) {
                    return 0;
                }
                i |= getBitmaskForTech(parseInt);
            } catch (java.lang.NumberFormatException e) {
                return 0;
            }
        }
        return i;
    }

    public static int convertNetworkTypeBitmaskToBearerBitmask(int i) {
        if (i == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < 21; i3++) {
            if (bitmaskHasTech(i, rilRadioTechnologyToNetworkType(i3))) {
                i2 |= getBitmaskForTech(i3);
            }
        }
        return i2;
    }

    public static int convertBearerBitmaskToNetworkTypeBitmask(int i) {
        if (i == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < 21; i3++) {
            if (bitmaskHasTech(i, i3)) {
                i2 |= getBitmaskForTech(rilRadioTechnologyToNetworkType(i3));
            }
        }
        return i2;
    }

    public static android.telephony.ServiceState mergeServiceStates(android.telephony.ServiceState serviceState, android.telephony.ServiceState serviceState2) {
        if (serviceState2.mVoiceRegState != 0) {
            return serviceState;
        }
        android.telephony.ServiceState serviceState3 = new android.telephony.ServiceState(serviceState);
        serviceState3.mVoiceRegState = serviceState2.mVoiceRegState;
        serviceState3.mIsEmergencyOnly = false;
        return serviceState3;
    }

    public java.util.List<android.telephony.NetworkRegistrationInfo> getNetworkRegistrationInfoList() {
        java.util.ArrayList arrayList;
        synchronized (this.mNetworkRegistrationInfos) {
            arrayList = new java.util.ArrayList();
            java.util.Iterator<android.telephony.NetworkRegistrationInfo> it = this.mNetworkRegistrationInfos.iterator();
            while (it.hasNext()) {
                arrayList.add(new android.telephony.NetworkRegistrationInfo(it.next()));
            }
        }
        return arrayList;
    }

    @android.annotation.SystemApi
    public java.util.List<android.telephony.NetworkRegistrationInfo> getNetworkRegistrationInfoListForTransportType(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mNetworkRegistrationInfos) {
            for (android.telephony.NetworkRegistrationInfo networkRegistrationInfo : this.mNetworkRegistrationInfos) {
                if (networkRegistrationInfo.getTransportType() == i) {
                    arrayList.add(new android.telephony.NetworkRegistrationInfo(networkRegistrationInfo));
                }
            }
        }
        return arrayList;
    }

    @android.annotation.SystemApi
    public java.util.List<android.telephony.NetworkRegistrationInfo> getNetworkRegistrationInfoListForDomain(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mNetworkRegistrationInfos) {
            for (android.telephony.NetworkRegistrationInfo networkRegistrationInfo : this.mNetworkRegistrationInfos) {
                if ((networkRegistrationInfo.getDomain() & i) != 0) {
                    arrayList.add(new android.telephony.NetworkRegistrationInfo(networkRegistrationInfo));
                }
            }
        }
        return arrayList;
    }

    @android.annotation.SystemApi
    public android.telephony.NetworkRegistrationInfo getNetworkRegistrationInfo(int i, int i2) {
        synchronized (this.mNetworkRegistrationInfos) {
            for (android.telephony.NetworkRegistrationInfo networkRegistrationInfo : this.mNetworkRegistrationInfos) {
                if (networkRegistrationInfo.getTransportType() == i2 && (networkRegistrationInfo.getDomain() & i) != 0) {
                    return new android.telephony.NetworkRegistrationInfo(networkRegistrationInfo);
                }
            }
            return null;
        }
    }

    public void addNetworkRegistrationInfo(android.telephony.NetworkRegistrationInfo networkRegistrationInfo) {
        if (networkRegistrationInfo == null) {
            return;
        }
        synchronized (this.mNetworkRegistrationInfos) {
            int i = 0;
            while (true) {
                if (i >= this.mNetworkRegistrationInfos.size()) {
                    break;
                }
                android.telephony.NetworkRegistrationInfo networkRegistrationInfo2 = this.mNetworkRegistrationInfos.get(i);
                if (networkRegistrationInfo2.getTransportType() != networkRegistrationInfo.getTransportType() || networkRegistrationInfo2.getDomain() != networkRegistrationInfo.getDomain()) {
                    i++;
                } else {
                    this.mNetworkRegistrationInfos.remove(i);
                    break;
                }
            }
            this.mNetworkRegistrationInfos.add(new android.telephony.NetworkRegistrationInfo(networkRegistrationInfo));
        }
    }

    public android.telephony.ServiceState createLocationInfoSanitizedCopy(boolean z) {
        android.telephony.ServiceState serviceState = new android.telephony.ServiceState(this);
        synchronized (serviceState.mNetworkRegistrationInfos) {
            java.util.List list = (java.util.List) serviceState.mNetworkRegistrationInfos.stream().map(new java.util.function.Function() { // from class: android.telephony.ServiceState$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return ((android.telephony.NetworkRegistrationInfo) obj).sanitizeLocationInfo();
                }
            }).collect(java.util.stream.Collectors.toList());
            serviceState.mNetworkRegistrationInfos.clear();
            serviceState.mNetworkRegistrationInfos.addAll(list);
        }
        if (!z) {
            return serviceState;
        }
        serviceState.mOperatorAlphaLong = null;
        serviceState.mOperatorAlphaShort = null;
        serviceState.mOperatorNumeric = null;
        serviceState.mSystemId = -1;
        serviceState.mNetworkId = -1;
        return serviceState;
    }

    public void setOperatorAlphaLongRaw(java.lang.String str) {
        this.mOperatorAlphaLongRaw = str;
    }

    public java.lang.String getOperatorAlphaLongRaw() {
        return this.mOperatorAlphaLongRaw;
    }

    public void setOperatorAlphaShortRaw(java.lang.String str) {
        this.mOperatorAlphaShortRaw = str;
    }

    public java.lang.String getOperatorAlphaShortRaw() {
        return this.mOperatorAlphaShortRaw;
    }

    public void setIwlanPreferred(boolean z) {
        this.mIsIwlanPreferred = z;
    }

    public boolean isIwlanPreferred() {
        return this.mIsIwlanPreferred;
    }

    public boolean isSearching() {
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo = getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo != null && networkRegistrationInfo.getRegistrationState() == 2) {
            return true;
        }
        android.telephony.NetworkRegistrationInfo networkRegistrationInfo2 = getNetworkRegistrationInfo(1, 1);
        return networkRegistrationInfo2 != null && networkRegistrationInfo2.getRegistrationState() == 2;
    }

    public static boolean isFrequencyRangeValid(int i) {
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            return true;
        }
        return false;
    }

    public boolean isUsingNonTerrestrialNetwork() {
        synchronized (this.mNetworkRegistrationInfos) {
            java.util.Iterator<android.telephony.NetworkRegistrationInfo> it = this.mNetworkRegistrationInfos.iterator();
            while (it.hasNext()) {
                if (it.next().isNonTerrestrialNetwork()) {
                    return true;
                }
            }
            return false;
        }
    }
}
