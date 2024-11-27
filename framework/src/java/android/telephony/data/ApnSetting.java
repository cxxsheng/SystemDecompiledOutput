package android.telephony.data;

/* loaded from: classes3.dex */
public class ApnSetting implements android.os.Parcelable {
    private static final java.util.Map<java.lang.Integer, java.lang.String> APN_TYPE_INT_MAP;
    private static final java.util.Map<java.lang.String, java.lang.Integer> APN_TYPE_STRING_MAP = new android.util.ArrayMap();
    public static final int AUTH_TYPE_CHAP = 2;
    public static final int AUTH_TYPE_NONE = 0;
    public static final int AUTH_TYPE_PAP = 1;
    public static final int AUTH_TYPE_PAP_OR_CHAP = 3;
    public static final int AUTH_TYPE_UNKNOWN = -1;
    public static final android.os.Parcelable.Creator<android.telephony.data.ApnSetting> CREATOR;
    public static final int INFRASTRUCTURE_CELLULAR = 1;
    public static final int INFRASTRUCTURE_SATELLITE = 2;
    private static final java.lang.String LOG_TAG = "ApnSetting";
    public static final int MVNO_TYPE_GID = 2;
    public static final int MVNO_TYPE_ICCID = 3;
    public static final int MVNO_TYPE_IMSI = 1;
    private static final java.util.Map<java.lang.Integer, java.lang.String> MVNO_TYPE_INT_MAP;
    public static final int MVNO_TYPE_SPN = 0;
    private static final java.util.Map<java.lang.String, java.lang.Integer> MVNO_TYPE_STRING_MAP;
    public static final int MVNO_TYPE_UNKNOWN = -1;
    private static final java.util.Map<java.lang.Integer, java.lang.String> PROTOCOL_INT_MAP;
    public static final int PROTOCOL_IP = 0;
    public static final int PROTOCOL_IPV4V6 = 2;
    public static final int PROTOCOL_IPV6 = 1;
    public static final int PROTOCOL_NON_IP = 4;
    public static final int PROTOCOL_PPP = 3;
    private static final java.util.Map<java.lang.String, java.lang.Integer> PROTOCOL_STRING_MAP;
    public static final int PROTOCOL_UNKNOWN = -1;
    public static final int PROTOCOL_UNSTRUCTURED = 5;
    public static final int TYPE_ALL = 255;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_ALL_STRING = "*";
    public static final int TYPE_BIP = 8192;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_BIP_STRING = "bip";
    public static final int TYPE_CBS = 128;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_CBS_STRING = "cbs";
    public static final int TYPE_DEFAULT = 17;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_DEFAULT_STRING = "default";
    public static final int TYPE_DUN = 8;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_DUN_STRING = "dun";
    public static final int TYPE_EMERGENCY = 512;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_EMERGENCY_STRING = "emergency";
    public static final int TYPE_ENTERPRISE = 16384;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_ENTERPRISE_STRING = "enterprise";
    public static final int TYPE_FOTA = 32;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_FOTA_STRING = "fota";
    public static final int TYPE_HIPRI = 16;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_HIPRI_STRING = "hipri";
    public static final int TYPE_IA = 256;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_IA_STRING = "ia";
    public static final int TYPE_IMS = 64;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_IMS_STRING = "ims";
    public static final int TYPE_MCX = 1024;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_MCX_STRING = "mcx";
    public static final int TYPE_MMS = 2;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_MMS_STRING = "mms";
    public static final int TYPE_NONE = 0;
    public static final int TYPE_RCS = 32768;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_RCS_STRING = "rcs";
    public static final int TYPE_SUPL = 4;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_SUPL_STRING = "supl";
    public static final int TYPE_VSIM = 4096;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_VSIM_STRING = "vsim";
    public static final int TYPE_XCAP = 2048;

    @android.annotation.SystemApi
    public static final java.lang.String TYPE_XCAP_STRING = "xcap";
    public static final int UNSET_MTU = 0;
    private static final int UNSPECIFIED_INT = -1;
    private static final java.lang.String UNSPECIFIED_STRING = "";
    private static final java.lang.String V2_FORMAT_REGEX = "^\\[ApnSettingV2\\]\\s*";
    private static final java.lang.String V3_FORMAT_REGEX = "^\\[ApnSettingV3\\]\\s*";
    private static final java.lang.String V4_FORMAT_REGEX = "^\\[ApnSettingV4\\]\\s*";
    private static final java.lang.String V5_FORMAT_REGEX = "^\\[ApnSettingV5\\]\\s*";
    private static final java.lang.String V6_FORMAT_REGEX = "^\\[ApnSettingV6\\]\\s*";
    private static final java.lang.String V7_FORMAT_REGEX = "^\\[ApnSettingV7\\]\\s*";
    private static final boolean VDBG = false;
    private final boolean mAlwaysOn;
    private final java.lang.String mApnName;
    private final int mApnSetId;
    private final int mApnTypeBitmask;
    private final int mAuthType;
    private final boolean mCarrierEnabled;
    private final int mCarrierId;
    private final int mEditedStatus;
    private final java.lang.String mEntryName;
    private final boolean mEsimBootstrapProvisioning;
    private final int mId;
    private final int mInfrastructureBitmask;
    private final long mLingeringNetworkTypeBitmask;
    private final int mMaxConns;
    private final int mMaxConnsTime;
    private final java.lang.String mMmsProxyAddress;
    private final int mMmsProxyPort;
    private final android.net.Uri mMmsc;
    private final int mMtuV4;
    private final int mMtuV6;
    private final java.lang.String mMvnoMatchData;
    private final int mMvnoType;
    private final int mNetworkTypeBitmask;
    private final java.lang.String mOperatorNumeric;
    private final java.lang.String mPassword;
    private boolean mPermanentFailed;
    private final boolean mPersistent;
    private final int mProfileId;
    private final int mProtocol;
    private final java.lang.String mProxyAddress;
    private final int mProxyPort;
    private final int mRoamingProtocol;
    private final int mSkip464Xlat;
    private final java.lang.String mUser;
    private final int mWaitTime;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ApnType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ApnTypeString {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AuthType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InfrastructureBitmask {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MvnoType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProtocolType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Skip464XlatStatus {
    }

    static {
        APN_TYPE_STRING_MAP.put("*", 255);
        APN_TYPE_STRING_MAP.put("default", 17);
        APN_TYPE_STRING_MAP.put("mms", 2);
        APN_TYPE_STRING_MAP.put("supl", 4);
        APN_TYPE_STRING_MAP.put("dun", 8);
        APN_TYPE_STRING_MAP.put("hipri", 16);
        APN_TYPE_STRING_MAP.put("fota", 32);
        APN_TYPE_STRING_MAP.put("ims", 64);
        APN_TYPE_STRING_MAP.put("cbs", 128);
        APN_TYPE_STRING_MAP.put("ia", 256);
        APN_TYPE_STRING_MAP.put("emergency", 512);
        APN_TYPE_STRING_MAP.put("mcx", 1024);
        APN_TYPE_STRING_MAP.put("xcap", 2048);
        APN_TYPE_STRING_MAP.put(TYPE_ENTERPRISE_STRING, 16384);
        APN_TYPE_STRING_MAP.put(TYPE_VSIM_STRING, 4096);
        APN_TYPE_STRING_MAP.put(TYPE_BIP_STRING, 8192);
        APN_TYPE_STRING_MAP.put(TYPE_RCS_STRING, 32768);
        APN_TYPE_INT_MAP = new android.util.ArrayMap();
        APN_TYPE_INT_MAP.put(17, "default");
        APN_TYPE_INT_MAP.put(2, "mms");
        APN_TYPE_INT_MAP.put(4, "supl");
        APN_TYPE_INT_MAP.put(8, "dun");
        APN_TYPE_INT_MAP.put(16, "hipri");
        APN_TYPE_INT_MAP.put(32, "fota");
        APN_TYPE_INT_MAP.put(64, "ims");
        APN_TYPE_INT_MAP.put(128, "cbs");
        APN_TYPE_INT_MAP.put(256, "ia");
        APN_TYPE_INT_MAP.put(512, "emergency");
        APN_TYPE_INT_MAP.put(1024, "mcx");
        APN_TYPE_INT_MAP.put(2048, "xcap");
        APN_TYPE_INT_MAP.put(16384, TYPE_ENTERPRISE_STRING);
        APN_TYPE_INT_MAP.put(4096, TYPE_VSIM_STRING);
        APN_TYPE_INT_MAP.put(8192, TYPE_BIP_STRING);
        APN_TYPE_INT_MAP.put(32768, TYPE_RCS_STRING);
        PROTOCOL_STRING_MAP = new android.util.ArrayMap();
        PROTOCOL_STRING_MAP.put(android.telephony.CarrierConfigManager.Apn.PROTOCOL_IPV4, 0);
        PROTOCOL_STRING_MAP.put("IPV6", 1);
        PROTOCOL_STRING_MAP.put(android.telephony.CarrierConfigManager.Apn.PROTOCOL_IPV4V6, 2);
        PROTOCOL_STRING_MAP.put("PPP", 3);
        PROTOCOL_STRING_MAP.put("NON-IP", 4);
        PROTOCOL_STRING_MAP.put("UNSTRUCTURED", 5);
        PROTOCOL_INT_MAP = new android.util.ArrayMap();
        PROTOCOL_INT_MAP.put(0, android.telephony.CarrierConfigManager.Apn.PROTOCOL_IPV4);
        PROTOCOL_INT_MAP.put(1, "IPV6");
        PROTOCOL_INT_MAP.put(2, android.telephony.CarrierConfigManager.Apn.PROTOCOL_IPV4V6);
        PROTOCOL_INT_MAP.put(3, "PPP");
        PROTOCOL_INT_MAP.put(4, "NON-IP");
        PROTOCOL_INT_MAP.put(5, "UNSTRUCTURED");
        MVNO_TYPE_STRING_MAP = new android.util.ArrayMap();
        MVNO_TYPE_STRING_MAP.put(android.provider.Telephony.CarrierId.All.SPN, 0);
        MVNO_TYPE_STRING_MAP.put("imsi", 1);
        MVNO_TYPE_STRING_MAP.put("gid", 2);
        MVNO_TYPE_STRING_MAP.put("iccid", 3);
        MVNO_TYPE_INT_MAP = new android.util.ArrayMap();
        MVNO_TYPE_INT_MAP.put(0, android.provider.Telephony.CarrierId.All.SPN);
        MVNO_TYPE_INT_MAP.put(1, "imsi");
        MVNO_TYPE_INT_MAP.put(2, "gid");
        MVNO_TYPE_INT_MAP.put(3, "iccid");
        CREATOR = new android.os.Parcelable.Creator<android.telephony.data.ApnSetting>() { // from class: android.telephony.data.ApnSetting.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.data.ApnSetting createFromParcel(android.os.Parcel parcel) {
                return android.telephony.data.ApnSetting.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.data.ApnSetting[] newArray(int i) {
                return new android.telephony.data.ApnSetting[i];
            }
        };
    }

    public int getMtuV4() {
        return this.mMtuV4;
    }

    public int getMtuV6() {
        return this.mMtuV6;
    }

    public int getProfileId() {
        return this.mProfileId;
    }

    public boolean isPersistent() {
        return this.mPersistent;
    }

    public int getMaxConns() {
        return this.mMaxConns;
    }

    public int getWaitTime() {
        return this.mWaitTime;
    }

    public int getMaxConnsTime() {
        return this.mMaxConnsTime;
    }

    public java.lang.String getMvnoMatchData() {
        return this.mMvnoMatchData;
    }

    public int getApnSetId() {
        return this.mApnSetId;
    }

    public boolean getPermanentFailed() {
        return this.mPermanentFailed;
    }

    public void setPermanentFailed(boolean z) {
        this.mPermanentFailed = z;
    }

    public java.lang.String getEntryName() {
        return this.mEntryName;
    }

    public java.lang.String getApnName() {
        return this.mApnName;
    }

    @java.lang.Deprecated
    public java.net.InetAddress getProxyAddress() {
        return inetAddressFromString(this.mProxyAddress);
    }

    public java.lang.String getProxyAddressAsString() {
        return this.mProxyAddress;
    }

    public int getProxyPort() {
        return this.mProxyPort;
    }

    public android.net.Uri getMmsc() {
        return this.mMmsc;
    }

    @java.lang.Deprecated
    public java.net.InetAddress getMmsProxyAddress() {
        return inetAddressFromString(this.mMmsProxyAddress);
    }

    public java.lang.String getMmsProxyAddressAsString() {
        return this.mMmsProxyAddress;
    }

    public int getMmsProxyPort() {
        return this.mMmsProxyPort;
    }

    public java.lang.String getUser() {
        return this.mUser;
    }

    public java.lang.String getPassword() {
        return this.mPassword;
    }

    public int getAuthType() {
        return this.mAuthType;
    }

    public int getApnTypeBitmask() {
        return this.mApnTypeBitmask;
    }

    public int getId() {
        return this.mId;
    }

    public java.lang.String getOperatorNumeric() {
        return this.mOperatorNumeric;
    }

    public int getProtocol() {
        return this.mProtocol;
    }

    public int getRoamingProtocol() {
        return this.mRoamingProtocol;
    }

    public boolean isEnabled() {
        return this.mCarrierEnabled;
    }

    public int getNetworkTypeBitmask() {
        return this.mNetworkTypeBitmask;
    }

    public long getLingeringNetworkTypeBitmask() {
        return this.mLingeringNetworkTypeBitmask;
    }

    public int getMvnoType() {
        return this.mMvnoType;
    }

    public int getCarrierId() {
        return this.mCarrierId;
    }

    public int getSkip464Xlat() {
        return this.mSkip464Xlat;
    }

    public boolean isAlwaysOn() {
        return this.mAlwaysOn;
    }

    public boolean isForInfrastructure(int i) {
        return (i & this.mInfrastructureBitmask) != 0;
    }

    public int getInfrastructureBitmask() {
        return this.mInfrastructureBitmask;
    }

    public boolean isEsimBootstrapProvisioning() {
        return this.mEsimBootstrapProvisioning;
    }

    public int getEditedStatus() {
        return this.mEditedStatus;
    }

    private ApnSetting(android.telephony.data.ApnSetting.Builder builder) {
        int i = 0;
        this.mPermanentFailed = false;
        this.mEntryName = builder.mEntryName;
        this.mApnName = builder.mApnName;
        this.mProxyAddress = builder.mProxyAddress;
        this.mProxyPort = builder.mProxyPort;
        this.mMmsc = builder.mMmsc;
        this.mMmsProxyAddress = builder.mMmsProxyAddress;
        this.mMmsProxyPort = builder.mMmsProxyPort;
        this.mUser = builder.mUser;
        this.mPassword = builder.mPassword;
        if (builder.mAuthType != -1) {
            i = builder.mAuthType;
        } else if (!android.text.TextUtils.isEmpty(builder.mUser)) {
            i = 3;
        }
        this.mAuthType = i;
        this.mApnTypeBitmask = builder.mApnTypeBitmask;
        this.mId = builder.mId;
        this.mOperatorNumeric = builder.mOperatorNumeric;
        this.mProtocol = builder.mProtocol;
        this.mRoamingProtocol = builder.mRoamingProtocol;
        this.mMtuV4 = builder.mMtuV4;
        this.mMtuV6 = builder.mMtuV6;
        this.mCarrierEnabled = builder.mCarrierEnabled;
        this.mNetworkTypeBitmask = builder.mNetworkTypeBitmask;
        this.mLingeringNetworkTypeBitmask = builder.mLingeringNetworkTypeBitmask;
        this.mProfileId = builder.mProfileId;
        this.mPersistent = builder.mModemCognitive;
        this.mMaxConns = builder.mMaxConns;
        this.mWaitTime = builder.mWaitTime;
        this.mMaxConnsTime = builder.mMaxConnsTime;
        this.mMvnoType = builder.mMvnoType;
        this.mMvnoMatchData = builder.mMvnoMatchData;
        this.mApnSetId = builder.mApnSetId;
        this.mCarrierId = builder.mCarrierId;
        this.mSkip464Xlat = builder.mSkip464Xlat;
        this.mAlwaysOn = builder.mAlwaysOn;
        this.mInfrastructureBitmask = builder.mInfrastructureBitmask;
        this.mEsimBootstrapProvisioning = builder.mEsimBootstrapProvisioning;
        this.mEditedStatus = builder.mEditedStatus;
    }

    public static android.telephony.data.ApnSetting makeApnSetting(android.database.Cursor cursor) {
        int apnTypesBitmaskFromString = getApnTypesBitmaskFromString(cursor.getString(cursor.getColumnIndexOrThrow("type")));
        int i = cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.NETWORK_TYPE_BITMASK));
        if (i == 0) {
            i = android.telephony.ServiceState.convertBearerBitmaskToNetworkTypeBitmask(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.BEARER_BITMASK)));
        }
        int i2 = cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.MTU_V4));
        if (i2 == 0) {
            i2 = cursor.getInt(cursor.getColumnIndexOrThrow("mtu"));
        }
        return new android.telephony.data.ApnSetting.Builder().setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id"))).setOperatorNumeric(cursor.getString(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.NUMERIC))).setEntryName(cursor.getString(cursor.getColumnIndexOrThrow("name"))).setApnName(cursor.getString(cursor.getColumnIndexOrThrow("apn"))).setProxyAddress(cursor.getString(cursor.getColumnIndexOrThrow("proxy"))).setProxyPort(portFromString(cursor.getString(cursor.getColumnIndexOrThrow("port")))).setMmsc(UriFromString(cursor.getString(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.MMSC)))).setMmsProxyAddress(cursor.getString(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.MMSPROXY))).setMmsProxyPort(portFromString(cursor.getString(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.MMSPORT)))).setUser(cursor.getString(cursor.getColumnIndexOrThrow("user"))).setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password"))).setAuthType(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.AUTH_TYPE))).setApnTypeBitmask(apnTypesBitmaskFromString).setProtocol(getProtocolIntFromString(cursor.getString(cursor.getColumnIndexOrThrow("protocol")))).setRoamingProtocol(getProtocolIntFromString(cursor.getString(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.ROAMING_PROTOCOL)))).setCarrierEnabled(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.CARRIER_ENABLED)) == 1).setNetworkTypeBitmask(i).setLingeringNetworkTypeBitmask(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.LINGERING_NETWORK_TYPE_BITMASK))).setProfileId(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.PROFILE_ID))).setModemCognitive(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.MODEM_PERSIST)) == 1).setMaxConns(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.MAX_CONNECTIONS))).setWaitTime(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.WAIT_TIME_RETRY))).setMaxConnsTime(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.TIME_LIMIT_FOR_MAX_CONNECTIONS))).setMtuV4(i2).setMtuV6(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.MTU_V6))).setMvnoType(getMvnoTypeIntFromString(cursor.getString(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.MVNO_TYPE)))).setMvnoMatchData(cursor.getString(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.MVNO_MATCH_DATA))).setApnSetId(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.APN_SET_ID))).setCarrierId(cursor.getInt(cursor.getColumnIndexOrThrow("carrier_id"))).setSkip464Xlat(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.SKIP_464XLAT))).setAlwaysOn(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.ALWAYS_ON)) == 1).setInfrastructureBitmask(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.INFRASTRUCTURE_BITMASK))).setEsimBootstrapProvisioning(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.ESIM_BOOTSTRAP_PROVISIONING)) == 1).setEditedStatus(cursor.getInt(cursor.getColumnIndexOrThrow(android.provider.Telephony.Carriers.EDITED_STATUS))).buildWithoutCheck();
    }

    public static android.telephony.data.ApnSetting makeApnSetting(android.telephony.data.ApnSetting apnSetting) {
        return new android.telephony.data.ApnSetting.Builder().setId(apnSetting.mId).setOperatorNumeric(apnSetting.mOperatorNumeric).setEntryName(apnSetting.mEntryName).setApnName(apnSetting.mApnName).setProxyAddress(apnSetting.mProxyAddress).setProxyPort(apnSetting.mProxyPort).setMmsc(apnSetting.mMmsc).setMmsProxyAddress(apnSetting.mMmsProxyAddress).setMmsProxyPort(apnSetting.mMmsProxyPort).setUser(apnSetting.mUser).setPassword(apnSetting.mPassword).setAuthType(apnSetting.mAuthType).setApnTypeBitmask(apnSetting.mApnTypeBitmask).setProtocol(apnSetting.mProtocol).setRoamingProtocol(apnSetting.mRoamingProtocol).setCarrierEnabled(apnSetting.mCarrierEnabled).setNetworkTypeBitmask(apnSetting.mNetworkTypeBitmask).setLingeringNetworkTypeBitmask(apnSetting.mLingeringNetworkTypeBitmask).setProfileId(apnSetting.mProfileId).setModemCognitive(apnSetting.mPersistent).setMaxConns(apnSetting.mMaxConns).setWaitTime(apnSetting.mWaitTime).setMaxConnsTime(apnSetting.mMaxConnsTime).setMtuV4(apnSetting.mMtuV4).setMtuV6(apnSetting.mMtuV6).setMvnoType(apnSetting.mMvnoType).setMvnoMatchData(apnSetting.mMvnoMatchData).setApnSetId(apnSetting.mApnSetId).setCarrierId(apnSetting.mCarrierId).setSkip464Xlat(apnSetting.mSkip464Xlat).setAlwaysOn(apnSetting.mAlwaysOn).setInfrastructureBitmask(apnSetting.mInfrastructureBitmask).setEsimBootstrapProvisioning(apnSetting.mEsimBootstrapProvisioning).setEditedStatus(apnSetting.mEditedStatus).buildWithoutCheck();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("[ApnSetting] ").append(this.mEntryName).append(", ").append(this.mId).append(", ").append(this.mOperatorNumeric).append(", ").append(this.mApnName).append(", ").append(this.mProxyAddress).append(", ").append(UriToString(this.mMmsc)).append(", ").append(this.mMmsProxyAddress).append(", ").append(portToString(this.mMmsProxyPort)).append(", ").append(portToString(this.mProxyPort)).append(", ").append(this.mAuthType).append(", ");
        sb.append(android.text.TextUtils.join(" | ", getApnTypesStringFromBitmask(this.mApnTypeBitmask).split(",")));
        sb.append(", ").append(PROTOCOL_INT_MAP.get(java.lang.Integer.valueOf(this.mProtocol)));
        sb.append(", ").append(PROTOCOL_INT_MAP.get(java.lang.Integer.valueOf(this.mRoamingProtocol)));
        sb.append(", ").append(this.mCarrierEnabled);
        sb.append(", ").append(this.mProfileId);
        sb.append(", ").append(this.mPersistent);
        sb.append(", ").append(this.mMaxConns);
        sb.append(", ").append(this.mWaitTime);
        sb.append(", ").append(this.mMaxConnsTime);
        sb.append(", ").append(this.mMtuV4);
        sb.append(", ").append(this.mMtuV6);
        sb.append(", ").append(MVNO_TYPE_INT_MAP.get(java.lang.Integer.valueOf(this.mMvnoType)));
        sb.append(", ").append(this.mMvnoMatchData);
        sb.append(", ").append(this.mPermanentFailed);
        sb.append(", ").append(android.telephony.TelephonyManager.convertNetworkTypeBitmaskToString(this.mNetworkTypeBitmask));
        sb.append(", ").append(android.telephony.TelephonyManager.convertNetworkTypeBitmaskToString(this.mLingeringNetworkTypeBitmask));
        sb.append(", ").append(this.mApnSetId);
        sb.append(", ").append(this.mCarrierId);
        sb.append(", ").append(this.mSkip464Xlat);
        sb.append(", ").append(this.mAlwaysOn);
        sb.append(", ").append(this.mInfrastructureBitmask);
        sb.append(", ").append(java.util.Objects.hash(this.mUser, this.mPassword));
        sb.append(", ").append(this.mEsimBootstrapProvisioning);
        sb.append(", ").append(com.android.internal.telephony.util.TelephonyUtils.apnEditedStatusToString(this.mEditedStatus));
        return sb.toString();
    }

    public boolean hasMvnoParams() {
        return (android.text.TextUtils.isEmpty(getMvnoTypeStringFromInt(this.mMvnoType)) || android.text.TextUtils.isEmpty(this.mMvnoMatchData)) ? false : true;
    }

    private boolean hasApnType(int i) {
        return (this.mApnTypeBitmask & i) == i;
    }

    public boolean isEmergencyApn() {
        return hasApnType(512);
    }

    public boolean canHandleType(int i) {
        if (!this.mCarrierEnabled) {
            return false;
        }
        return hasApnType(i);
    }

    private boolean typeSameAny(android.telephony.data.ApnSetting apnSetting, android.telephony.data.ApnSetting apnSetting2) {
        if ((apnSetting.mApnTypeBitmask & apnSetting2.mApnTypeBitmask) != 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mApnName, this.mProxyAddress, java.lang.Integer.valueOf(this.mProxyPort), this.mMmsc, this.mMmsProxyAddress, java.lang.Integer.valueOf(this.mMmsProxyPort), this.mUser, this.mPassword, java.lang.Integer.valueOf(this.mAuthType), java.lang.Integer.valueOf(this.mApnTypeBitmask), java.lang.Integer.valueOf(this.mId), this.mOperatorNumeric, java.lang.Integer.valueOf(this.mProtocol), java.lang.Integer.valueOf(this.mRoamingProtocol), java.lang.Integer.valueOf(this.mMtuV4), java.lang.Integer.valueOf(this.mMtuV6), java.lang.Boolean.valueOf(this.mCarrierEnabled), java.lang.Integer.valueOf(this.mNetworkTypeBitmask), java.lang.Long.valueOf(this.mLingeringNetworkTypeBitmask), java.lang.Integer.valueOf(this.mProfileId), java.lang.Boolean.valueOf(this.mPersistent), java.lang.Integer.valueOf(this.mMaxConns), java.lang.Integer.valueOf(this.mWaitTime), java.lang.Integer.valueOf(this.mMaxConnsTime), java.lang.Integer.valueOf(this.mMvnoType), this.mMvnoMatchData, java.lang.Integer.valueOf(this.mApnSetId), java.lang.Integer.valueOf(this.mCarrierId), java.lang.Integer.valueOf(this.mSkip464Xlat), java.lang.Boolean.valueOf(this.mAlwaysOn), java.lang.Integer.valueOf(this.mInfrastructureBitmask), java.lang.Boolean.valueOf(this.mEsimBootstrapProvisioning));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.data.ApnSetting)) {
            return false;
        }
        android.telephony.data.ApnSetting apnSetting = (android.telephony.data.ApnSetting) obj;
        return this.mEntryName.equals(apnSetting.mEntryName) && this.mId == apnSetting.mId && java.util.Objects.equals(this.mOperatorNumeric, apnSetting.mOperatorNumeric) && java.util.Objects.equals(this.mApnName, apnSetting.mApnName) && java.util.Objects.equals(this.mProxyAddress, apnSetting.mProxyAddress) && java.util.Objects.equals(this.mMmsc, apnSetting.mMmsc) && java.util.Objects.equals(this.mMmsProxyAddress, apnSetting.mMmsProxyAddress) && this.mMmsProxyPort == apnSetting.mMmsProxyPort && this.mProxyPort == apnSetting.mProxyPort && java.util.Objects.equals(this.mUser, apnSetting.mUser) && java.util.Objects.equals(this.mPassword, apnSetting.mPassword) && this.mAuthType == apnSetting.mAuthType && this.mApnTypeBitmask == apnSetting.mApnTypeBitmask && this.mProtocol == apnSetting.mProtocol && this.mRoamingProtocol == apnSetting.mRoamingProtocol && this.mCarrierEnabled == apnSetting.mCarrierEnabled && this.mProfileId == apnSetting.mProfileId && this.mPersistent == apnSetting.mPersistent && this.mMaxConns == apnSetting.mMaxConns && this.mWaitTime == apnSetting.mWaitTime && this.mMaxConnsTime == apnSetting.mMaxConnsTime && this.mMtuV4 == apnSetting.mMtuV4 && this.mMtuV6 == apnSetting.mMtuV6 && this.mMvnoType == apnSetting.mMvnoType && java.util.Objects.equals(this.mMvnoMatchData, apnSetting.mMvnoMatchData) && this.mNetworkTypeBitmask == apnSetting.mNetworkTypeBitmask && this.mLingeringNetworkTypeBitmask == apnSetting.mLingeringNetworkTypeBitmask && this.mApnSetId == apnSetting.mApnSetId && this.mCarrierId == apnSetting.mCarrierId && this.mSkip464Xlat == apnSetting.mSkip464Xlat && this.mAlwaysOn == apnSetting.mAlwaysOn && this.mInfrastructureBitmask == apnSetting.mInfrastructureBitmask && this.mEsimBootstrapProvisioning == apnSetting.mEsimBootstrapProvisioning;
    }

    public boolean equals(java.lang.Object obj, boolean z) {
        if (!(obj instanceof android.telephony.data.ApnSetting)) {
            return false;
        }
        android.telephony.data.ApnSetting apnSetting = (android.telephony.data.ApnSetting) obj;
        if (!this.mEntryName.equals(apnSetting.mEntryName) || !java.util.Objects.equals(this.mOperatorNumeric, apnSetting.mOperatorNumeric) || !java.util.Objects.equals(this.mApnName, apnSetting.mApnName) || !java.util.Objects.equals(this.mProxyAddress, apnSetting.mProxyAddress) || !java.util.Objects.equals(this.mMmsc, apnSetting.mMmsc) || !java.util.Objects.equals(this.mMmsProxyAddress, apnSetting.mMmsProxyAddress) || !java.util.Objects.equals(java.lang.Integer.valueOf(this.mMmsProxyPort), java.lang.Integer.valueOf(apnSetting.mMmsProxyPort)) || !java.util.Objects.equals(java.lang.Integer.valueOf(this.mProxyPort), java.lang.Integer.valueOf(apnSetting.mProxyPort)) || !java.util.Objects.equals(this.mUser, apnSetting.mUser) || !java.util.Objects.equals(this.mPassword, apnSetting.mPassword) || !java.util.Objects.equals(java.lang.Integer.valueOf(this.mAuthType), java.lang.Integer.valueOf(apnSetting.mAuthType)) || !java.util.Objects.equals(java.lang.Integer.valueOf(this.mApnTypeBitmask), java.lang.Integer.valueOf(apnSetting.mApnTypeBitmask)) || !java.util.Objects.equals(java.lang.Long.valueOf(this.mLingeringNetworkTypeBitmask), java.lang.Long.valueOf(apnSetting.mLingeringNetworkTypeBitmask))) {
            return false;
        }
        if (z || java.util.Objects.equals(java.lang.Integer.valueOf(this.mProtocol), java.lang.Integer.valueOf(apnSetting.mProtocol))) {
            return (!z || java.util.Objects.equals(java.lang.Integer.valueOf(this.mRoamingProtocol), java.lang.Integer.valueOf(apnSetting.mRoamingProtocol))) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mCarrierEnabled), java.lang.Boolean.valueOf(apnSetting.mCarrierEnabled)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mProfileId), java.lang.Integer.valueOf(apnSetting.mProfileId)) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mPersistent), java.lang.Boolean.valueOf(apnSetting.mPersistent)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mMaxConns), java.lang.Integer.valueOf(apnSetting.mMaxConns)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mWaitTime), java.lang.Integer.valueOf(apnSetting.mWaitTime)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mMaxConnsTime), java.lang.Integer.valueOf(apnSetting.mMaxConnsTime)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mMtuV4), java.lang.Integer.valueOf(apnSetting.mMtuV4)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mMtuV6), java.lang.Integer.valueOf(apnSetting.mMtuV6)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mMvnoType), java.lang.Integer.valueOf(apnSetting.mMvnoType)) && java.util.Objects.equals(this.mMvnoMatchData, apnSetting.mMvnoMatchData) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mApnSetId), java.lang.Integer.valueOf(apnSetting.mApnSetId)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mCarrierId), java.lang.Integer.valueOf(apnSetting.mCarrierId)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mSkip464Xlat), java.lang.Integer.valueOf(apnSetting.mSkip464Xlat)) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mAlwaysOn), java.lang.Boolean.valueOf(apnSetting.mAlwaysOn)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mInfrastructureBitmask), java.lang.Integer.valueOf(apnSetting.mInfrastructureBitmask)) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mEsimBootstrapProvisioning), java.lang.Boolean.valueOf(apnSetting.mEsimBootstrapProvisioning));
        }
        return false;
    }

    public boolean similar(android.telephony.data.ApnSetting apnSetting) {
        return !canHandleType(8) && !apnSetting.canHandleType(8) && java.util.Objects.equals(this.mApnName, apnSetting.mApnName) && xorEqualsString(this.mProxyAddress, apnSetting.mProxyAddress) && xorEqualsInt(this.mProxyPort, apnSetting.mProxyPort) && xorEquals(this.mMmsc, apnSetting.mMmsc) && xorEqualsString(this.mMmsProxyAddress, apnSetting.mMmsProxyAddress) && xorEqualsInt(this.mMmsProxyPort, apnSetting.mMmsProxyPort) && xorEqualsString(this.mUser, apnSetting.mUser) && xorEqualsString(this.mPassword, apnSetting.mPassword) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mAuthType), java.lang.Integer.valueOf(apnSetting.mAuthType)) && !typeSameAny(this, apnSetting) && java.util.Objects.equals(this.mOperatorNumeric, apnSetting.mOperatorNumeric) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mProtocol), java.lang.Integer.valueOf(apnSetting.mProtocol)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mRoamingProtocol), java.lang.Integer.valueOf(apnSetting.mRoamingProtocol)) && mtuUnsetOrEquals(this.mMtuV4, apnSetting.mMtuV4) && mtuUnsetOrEquals(this.mMtuV6, apnSetting.mMtuV6) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mCarrierEnabled), java.lang.Boolean.valueOf(apnSetting.mCarrierEnabled)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mNetworkTypeBitmask), java.lang.Integer.valueOf(apnSetting.mNetworkTypeBitmask)) && java.util.Objects.equals(java.lang.Long.valueOf(this.mLingeringNetworkTypeBitmask), java.lang.Long.valueOf(apnSetting.mLingeringNetworkTypeBitmask)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mProfileId), java.lang.Integer.valueOf(apnSetting.mProfileId)) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mPersistent), java.lang.Boolean.valueOf(apnSetting.mPersistent)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mApnSetId), java.lang.Integer.valueOf(apnSetting.mApnSetId)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mCarrierId), java.lang.Integer.valueOf(apnSetting.mCarrierId)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mSkip464Xlat), java.lang.Integer.valueOf(apnSetting.mSkip464Xlat)) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mAlwaysOn), java.lang.Boolean.valueOf(apnSetting.mAlwaysOn)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mInfrastructureBitmask), java.lang.Integer.valueOf(apnSetting.mInfrastructureBitmask)) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mEsimBootstrapProvisioning), java.lang.Boolean.valueOf(apnSetting.mEsimBootstrapProvisioning));
    }

    private boolean xorEquals(java.lang.Object obj, java.lang.Object obj2) {
        return obj == null || obj2 == null || obj.equals(obj2);
    }

    private boolean xorEqualsString(java.lang.String str, java.lang.String str2) {
        return android.text.TextUtils.isEmpty(str) || android.text.TextUtils.isEmpty(str2) || str.equals(str2);
    }

    private boolean xorEqualsInt(int i, int i2) {
        return i == -1 || i2 == -1 || i == i2;
    }

    private boolean mtuUnsetOrEquals(int i, int i2) {
        return i <= 0 || i2 <= 0 || i == i2;
    }

    private java.lang.String nullToEmpty(java.lang.String str) {
        return str == null ? "" : str;
    }

    public android.content.ContentValues toContentValues() {
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put(android.provider.Telephony.Carriers.NUMERIC, nullToEmpty(this.mOperatorNumeric));
        if (!android.text.TextUtils.isEmpty(this.mOperatorNumeric) && (this.mOperatorNumeric.length() == 5 || this.mOperatorNumeric.length() == 6)) {
            contentValues.put("mcc", this.mOperatorNumeric.substring(0, 3));
            contentValues.put("mnc", this.mOperatorNumeric.substring(3));
        }
        contentValues.put("name", nullToEmpty(this.mEntryName));
        contentValues.put("apn", nullToEmpty(this.mApnName));
        contentValues.put("proxy", nullToEmpty(this.mProxyAddress));
        contentValues.put("port", nullToEmpty(portToString(this.mProxyPort)));
        contentValues.put(android.provider.Telephony.Carriers.MMSC, nullToEmpty(UriToString(this.mMmsc)));
        contentValues.put(android.provider.Telephony.Carriers.MMSPORT, nullToEmpty(portToString(this.mMmsProxyPort)));
        contentValues.put(android.provider.Telephony.Carriers.MMSPROXY, nullToEmpty(this.mMmsProxyAddress));
        contentValues.put("user", nullToEmpty(this.mUser));
        contentValues.put("password", nullToEmpty(this.mPassword));
        contentValues.put(android.provider.Telephony.Carriers.AUTH_TYPE, java.lang.Integer.valueOf(this.mAuthType));
        contentValues.put("type", nullToEmpty(getApnTypesStringFromBitmask(this.mApnTypeBitmask)));
        contentValues.put("protocol", getProtocolStringFromInt(this.mProtocol));
        contentValues.put(android.provider.Telephony.Carriers.ROAMING_PROTOCOL, getProtocolStringFromInt(this.mRoamingProtocol));
        contentValues.put(android.provider.Telephony.Carriers.CARRIER_ENABLED, java.lang.Boolean.valueOf(this.mCarrierEnabled));
        contentValues.put(android.provider.Telephony.Carriers.MVNO_TYPE, getMvnoTypeStringFromInt(this.mMvnoType));
        contentValues.put(android.provider.Telephony.Carriers.MVNO_MATCH_DATA, nullToEmpty(this.mMvnoMatchData));
        contentValues.put(android.provider.Telephony.Carriers.NETWORK_TYPE_BITMASK, java.lang.Integer.valueOf(this.mNetworkTypeBitmask));
        contentValues.put(android.provider.Telephony.Carriers.LINGERING_NETWORK_TYPE_BITMASK, java.lang.Long.valueOf(this.mLingeringNetworkTypeBitmask));
        contentValues.put(android.provider.Telephony.Carriers.MTU_V4, java.lang.Integer.valueOf(this.mMtuV4));
        contentValues.put(android.provider.Telephony.Carriers.MTU_V6, java.lang.Integer.valueOf(this.mMtuV6));
        contentValues.put("carrier_id", java.lang.Integer.valueOf(this.mCarrierId));
        contentValues.put(android.provider.Telephony.Carriers.SKIP_464XLAT, java.lang.Integer.valueOf(this.mSkip464Xlat));
        contentValues.put(android.provider.Telephony.Carriers.ALWAYS_ON, java.lang.Boolean.valueOf(this.mAlwaysOn));
        contentValues.put(android.provider.Telephony.Carriers.INFRASTRUCTURE_BITMASK, java.lang.Integer.valueOf(this.mInfrastructureBitmask));
        contentValues.put(android.provider.Telephony.Carriers.ESIM_BOOTSTRAP_PROVISIONING, java.lang.Boolean.valueOf(this.mEsimBootstrapProvisioning));
        return contentValues;
    }

    public java.util.List<java.lang.Integer> getApnTypes() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.Integer num : APN_TYPE_INT_MAP.keySet()) {
            if ((this.mApnTypeBitmask & num.intValue()) == num.intValue()) {
                arrayList.add(num);
            }
        }
        return arrayList;
    }

    public static java.lang.String getApnTypesStringFromBitmask(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.Integer num : APN_TYPE_INT_MAP.keySet()) {
            if ((num.intValue() & i) == num.intValue()) {
                arrayList.add(APN_TYPE_INT_MAP.get(num));
            }
        }
        return android.text.TextUtils.join(",", arrayList);
    }

    public static int[] getApnTypesFromBitmask(final int i) {
        return APN_TYPE_INT_MAP.keySet().stream().filter(new java.util.function.Predicate() { // from class: android.telephony.data.ApnSetting$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.telephony.data.ApnSetting.lambda$getApnTypesFromBitmask$0(i, (java.lang.Integer) obj);
            }
        }).mapToInt(new android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
    }

    static /* synthetic */ boolean lambda$getApnTypesFromBitmask$0(int i, java.lang.Integer num) {
        return (i & num.intValue()) == num.intValue();
    }

    @android.annotation.SystemApi
    public static java.lang.String getApnTypeString(int i) {
        if (i == 255) {
            return "*";
        }
        java.lang.String str = APN_TYPE_INT_MAP.get(java.lang.Integer.valueOf(i));
        return str == null ? "" : str;
    }

    @android.annotation.SystemApi
    public static int getApnTypeInt(java.lang.String str) {
        return APN_TYPE_STRING_MAP.getOrDefault(str.toLowerCase(java.util.Locale.ROOT), 0).intValue();
    }

    public static int getApnTypesBitmaskFromString(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return 255;
        }
        int i = 0;
        for (java.lang.String str2 : str.split(",")) {
            java.lang.Integer num = APN_TYPE_STRING_MAP.get(str2.toLowerCase(java.util.Locale.ROOT));
            if (num != null) {
                i |= num.intValue();
            }
        }
        return i;
    }

    public static int getMvnoTypeIntFromString(java.lang.String str) {
        if (!android.text.TextUtils.isEmpty(str)) {
            str = str.toLowerCase(java.util.Locale.ROOT);
        }
        java.lang.Integer num = MVNO_TYPE_STRING_MAP.get(str);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public static java.lang.String getMvnoTypeStringFromInt(int i) {
        java.lang.String str = MVNO_TYPE_INT_MAP.get(java.lang.Integer.valueOf(i));
        return str == null ? "" : str;
    }

    public static int getProtocolIntFromString(java.lang.String str) {
        java.lang.Integer num = PROTOCOL_STRING_MAP.get(str);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public static java.lang.String getProtocolStringFromInt(int i) {
        java.lang.String str = PROTOCOL_INT_MAP.get(java.lang.Integer.valueOf(i));
        return str == null ? "" : str;
    }

    private static android.net.Uri UriFromString(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        return android.net.Uri.parse(str);
    }

    private static java.lang.String UriToString(android.net.Uri uri) {
        if (uri == null) {
            return null;
        }
        return uri.toString();
    }

    public static java.net.InetAddress inetAddressFromString(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return java.net.InetAddress.getByName(str);
        } catch (java.net.UnknownHostException e) {
            android.util.Log.e(LOG_TAG, "Can't parse InetAddress from string: unknown host.");
            return null;
        }
    }

    public static java.lang.String inetAddressToString(java.net.InetAddress inetAddress) {
        if (inetAddress == null) {
            return null;
        }
        java.lang.String obj = inetAddress.toString();
        if (android.text.TextUtils.isEmpty(obj)) {
            return null;
        }
        java.lang.String substring = obj.substring(0, obj.indexOf("/"));
        java.lang.String substring2 = obj.substring(obj.indexOf("/") + 1);
        if (android.text.TextUtils.isEmpty(substring) && android.text.TextUtils.isEmpty(substring2)) {
            return null;
        }
        return android.text.TextUtils.isEmpty(substring) ? substring2 : substring;
    }

    private static int portFromString(java.lang.String str) {
        if (!android.text.TextUtils.isEmpty(str)) {
            try {
                return java.lang.Integer.parseInt(str);
            } catch (java.lang.NumberFormatException e) {
                android.util.Log.e(LOG_TAG, "Can't parse port from String");
            }
        }
        return -1;
    }

    private static java.lang.String portToString(int i) {
        if (i == -1) {
            return null;
        }
        return java.lang.Integer.toString(i);
    }

    public boolean canSupportNetworkType(int i) {
        if (i == 16 && (this.mNetworkTypeBitmask & 3) != 0) {
            return true;
        }
        return android.telephony.ServiceState.bitmaskHasTech(this.mNetworkTypeBitmask, i);
    }

    public boolean canSupportLingeringNetworkType(int i) {
        if (this.mLingeringNetworkTypeBitmask == 0) {
            return canSupportNetworkType(i);
        }
        if (i == 16 && (this.mLingeringNetworkTypeBitmask & 3) != 0) {
            return true;
        }
        return android.telephony.ServiceState.bitmaskHasTech((int) this.mLingeringNetworkTypeBitmask, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mOperatorNumeric);
        parcel.writeString(this.mEntryName);
        parcel.writeString(this.mApnName);
        parcel.writeString(this.mProxyAddress);
        parcel.writeInt(this.mProxyPort);
        parcel.writeParcelable(this.mMmsc, i);
        parcel.writeString(this.mMmsProxyAddress);
        parcel.writeInt(this.mMmsProxyPort);
        parcel.writeString(this.mUser);
        parcel.writeString(this.mPassword);
        parcel.writeInt(this.mAuthType);
        parcel.writeInt(this.mApnTypeBitmask);
        parcel.writeInt(this.mProtocol);
        parcel.writeInt(this.mRoamingProtocol);
        parcel.writeBoolean(this.mCarrierEnabled);
        parcel.writeInt(this.mNetworkTypeBitmask);
        parcel.writeLong(this.mLingeringNetworkTypeBitmask);
        parcel.writeInt(this.mProfileId);
        parcel.writeBoolean(this.mPersistent);
        parcel.writeInt(this.mMaxConns);
        parcel.writeInt(this.mWaitTime);
        parcel.writeInt(this.mMaxConnsTime);
        parcel.writeInt(this.mMtuV4);
        parcel.writeInt(this.mMtuV6);
        parcel.writeInt(this.mMvnoType);
        parcel.writeString(this.mMvnoMatchData);
        parcel.writeInt(this.mApnSetId);
        parcel.writeInt(this.mCarrierId);
        parcel.writeInt(this.mSkip464Xlat);
        parcel.writeBoolean(this.mAlwaysOn);
        parcel.writeInt(this.mInfrastructureBitmask);
        parcel.writeBoolean(this.mEsimBootstrapProvisioning);
        parcel.writeInt(this.mEditedStatus);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.telephony.data.ApnSetting readFromParcel(android.os.Parcel parcel) {
        return new android.telephony.data.ApnSetting.Builder().setId(parcel.readInt()).setOperatorNumeric(parcel.readString()).setEntryName(parcel.readString()).setApnName(parcel.readString()).setProxyAddress(parcel.readString()).setProxyPort(parcel.readInt()).setMmsc((android.net.Uri) parcel.readParcelable(android.net.Uri.class.getClassLoader(), android.net.Uri.class)).setMmsProxyAddress(parcel.readString()).setMmsProxyPort(parcel.readInt()).setUser(parcel.readString()).setPassword(parcel.readString()).setAuthType(parcel.readInt()).setApnTypeBitmask(parcel.readInt()).setProtocol(parcel.readInt()).setRoamingProtocol(parcel.readInt()).setCarrierEnabled(parcel.readBoolean()).setNetworkTypeBitmask(parcel.readInt()).setLingeringNetworkTypeBitmask(parcel.readLong()).setProfileId(parcel.readInt()).setModemCognitive(parcel.readBoolean()).setMaxConns(parcel.readInt()).setWaitTime(parcel.readInt()).setMaxConnsTime(parcel.readInt()).setMtuV4(parcel.readInt()).setMtuV6(parcel.readInt()).setMvnoType(parcel.readInt()).setMvnoMatchData(parcel.readString()).setApnSetId(parcel.readInt()).setCarrierId(parcel.readInt()).setSkip464Xlat(parcel.readInt()).setAlwaysOn(parcel.readBoolean()).setInfrastructureBitmask(parcel.readInt()).setEsimBootstrapProvisioning(parcel.readBoolean()).setEditedStatus(parcel.readInt()).buildWithoutCheck();
    }

    public static class Builder {
        private boolean mAlwaysOn;
        private java.lang.String mApnName;
        private int mApnSetId;
        private int mApnTypeBitmask;
        private boolean mCarrierEnabled;
        private java.lang.String mEntryName;
        private boolean mEsimBootstrapProvisioning;
        private int mId;
        private long mLingeringNetworkTypeBitmask;
        private int mMaxConns;
        private int mMaxConnsTime;
        private java.lang.String mMmsProxyAddress;
        private android.net.Uri mMmsc;
        private boolean mModemCognitive;
        private int mMtuV4;
        private int mMtuV6;
        private java.lang.String mMvnoMatchData;
        private int mNetworkTypeBitmask;
        private java.lang.String mOperatorNumeric;
        private java.lang.String mPassword;
        private int mProfileId;
        private java.lang.String mProxyAddress;
        private java.lang.String mUser;
        private int mWaitTime;
        private int mProxyPort = -1;
        private int mMmsProxyPort = -1;
        private int mAuthType = -1;
        private int mProtocol = -1;
        private int mRoamingProtocol = -1;
        private int mMvnoType = -1;
        private int mCarrierId = -1;
        private int mSkip464Xlat = -1;
        private int mInfrastructureBitmask = 3;
        private int mEditedStatus = 0;

        public android.telephony.data.ApnSetting.Builder setId(int i) {
            this.mId = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setMtuV4(int i) {
            this.mMtuV4 = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setMtuV6(int i) {
            this.mMtuV6 = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setProfileId(int i) {
            this.mProfileId = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setPersistent(boolean z) {
            return setModemCognitive(z);
        }

        public android.telephony.data.ApnSetting.Builder setModemCognitive(boolean z) {
            this.mModemCognitive = z;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setMaxConns(int i) {
            this.mMaxConns = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setWaitTime(int i) {
            this.mWaitTime = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setMaxConnsTime(int i) {
            this.mMaxConnsTime = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setMvnoMatchData(java.lang.String str) {
            this.mMvnoMatchData = str;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setApnSetId(int i) {
            this.mApnSetId = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setEntryName(java.lang.String str) {
            this.mEntryName = str;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setApnName(java.lang.String str) {
            this.mApnName = str;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.ApnSetting.Builder setProxyAddress(java.net.InetAddress inetAddress) {
            this.mProxyAddress = android.telephony.data.ApnSetting.inetAddressToString(inetAddress);
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setProxyAddress(java.lang.String str) {
            this.mProxyAddress = str;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setProxyPort(int i) {
            this.mProxyPort = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setMmsc(android.net.Uri uri) {
            this.mMmsc = uri;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.ApnSetting.Builder setMmsProxyAddress(java.net.InetAddress inetAddress) {
            this.mMmsProxyAddress = android.telephony.data.ApnSetting.inetAddressToString(inetAddress);
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setMmsProxyAddress(java.lang.String str) {
            this.mMmsProxyAddress = str;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setMmsProxyPort(int i) {
            this.mMmsProxyPort = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setUser(java.lang.String str) {
            this.mUser = str;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setPassword(java.lang.String str) {
            this.mPassword = str;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setAuthType(int i) {
            this.mAuthType = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setApnTypeBitmask(int i) {
            this.mApnTypeBitmask = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setOperatorNumeric(java.lang.String str) {
            this.mOperatorNumeric = str;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setProtocol(int i) {
            this.mProtocol = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setRoamingProtocol(int i) {
            this.mRoamingProtocol = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setCarrierEnabled(boolean z) {
            this.mCarrierEnabled = z;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setNetworkTypeBitmask(int i) {
            this.mNetworkTypeBitmask = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setLingeringNetworkTypeBitmask(long j) {
            this.mLingeringNetworkTypeBitmask = j;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setMvnoType(int i) {
            this.mMvnoType = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setCarrierId(int i) {
            this.mCarrierId = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setSkip464Xlat(int i) {
            this.mSkip464Xlat = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setAlwaysOn(boolean z) {
            this.mAlwaysOn = z;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setInfrastructureBitmask(int i) {
            this.mInfrastructureBitmask = i;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setEsimBootstrapProvisioning(boolean z) {
            this.mEsimBootstrapProvisioning = z;
            return this;
        }

        public android.telephony.data.ApnSetting.Builder setEditedStatus(int i) {
            this.mEditedStatus = i;
            return this;
        }

        public android.telephony.data.ApnSetting build() {
            if ((this.mApnTypeBitmask & 65535) == 0 || android.text.TextUtils.isEmpty(this.mApnName) || android.text.TextUtils.isEmpty(this.mEntryName)) {
                return null;
            }
            if ((this.mApnTypeBitmask & 2) != 0 && !android.text.TextUtils.isEmpty(this.mMmsProxyAddress) && this.mMmsProxyAddress.startsWith(android.content.IntentFilter.SCHEME_HTTP)) {
                android.util.Log.wtf(android.telephony.data.ApnSetting.LOG_TAG, "mms proxy(" + this.mMmsProxyAddress + ") should be a hostname, not a url");
                this.mMmsProxyAddress = android.net.Uri.parse(this.mMmsProxyAddress).getHost();
            }
            return new android.telephony.data.ApnSetting(this);
        }

        public android.telephony.data.ApnSetting buildWithoutCheck() {
            return new android.telephony.data.ApnSetting(this);
        }
    }
}
