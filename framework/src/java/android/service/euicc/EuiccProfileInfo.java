package android.service.euicc;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class EuiccProfileInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.euicc.EuiccProfileInfo> CREATOR = new android.os.Parcelable.Creator<android.service.euicc.EuiccProfileInfo>() { // from class: android.service.euicc.EuiccProfileInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.euicc.EuiccProfileInfo createFromParcel(android.os.Parcel parcel) {
            return new android.service.euicc.EuiccProfileInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.euicc.EuiccProfileInfo[] newArray(int i) {
            return new android.service.euicc.EuiccProfileInfo[i];
        }
    };
    public static final int POLICY_RULE_DELETE_AFTER_DISABLING = 4;
    public static final int POLICY_RULE_DO_NOT_DELETE = 2;
    public static final int POLICY_RULE_DO_NOT_DISABLE = 1;
    public static final int PROFILE_CLASS_OPERATIONAL = 2;
    public static final int PROFILE_CLASS_PROVISIONING = 1;
    public static final int PROFILE_CLASS_TESTING = 0;
    public static final int PROFILE_CLASS_UNSET = -1;
    public static final int PROFILE_STATE_DISABLED = 0;
    public static final int PROFILE_STATE_ENABLED = 1;
    public static final int PROFILE_STATE_UNSET = -1;
    private final android.telephony.UiccAccessRule[] mAccessRules;
    private final android.service.carrier.CarrierIdentifier mCarrierIdentifier;
    private final java.lang.String mIccid;
    private final java.lang.String mNickname;
    private final int mPolicyRules;
    private final int mProfileClass;
    private final java.lang.String mProfileName;
    private final java.lang.String mServiceProviderName;
    private final int mState;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PolicyRule {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProfileClass {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProfileState {
    }

    @java.lang.Deprecated
    public EuiccProfileInfo(java.lang.String str, android.telephony.UiccAccessRule[] uiccAccessRuleArr, java.lang.String str2) {
        if (!android.text.TextUtils.isDigitsOnly(str)) {
            throw new java.lang.IllegalArgumentException("iccid contains invalid characters: " + str);
        }
        this.mIccid = str;
        this.mAccessRules = uiccAccessRuleArr;
        this.mNickname = str2;
        this.mServiceProviderName = null;
        this.mProfileName = null;
        this.mProfileClass = -1;
        this.mState = -1;
        this.mCarrierIdentifier = null;
        this.mPolicyRules = 0;
    }

    private EuiccProfileInfo(android.os.Parcel parcel) {
        this.mIccid = parcel.readString();
        this.mNickname = parcel.readString();
        this.mServiceProviderName = parcel.readString();
        this.mProfileName = parcel.readString();
        this.mProfileClass = parcel.readInt();
        this.mState = parcel.readInt();
        if (parcel.readByte() == 1) {
            this.mCarrierIdentifier = android.service.carrier.CarrierIdentifier.CREATOR.createFromParcel(parcel);
        } else {
            this.mCarrierIdentifier = null;
        }
        this.mPolicyRules = parcel.readInt();
        this.mAccessRules = (android.telephony.UiccAccessRule[]) parcel.createTypedArray(android.telephony.UiccAccessRule.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mIccid);
        parcel.writeString(this.mNickname);
        parcel.writeString(this.mServiceProviderName);
        parcel.writeString(this.mProfileName);
        parcel.writeInt(this.mProfileClass);
        parcel.writeInt(this.mState);
        if (this.mCarrierIdentifier != null) {
            parcel.writeByte((byte) 1);
            this.mCarrierIdentifier.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeInt(this.mPolicyRules);
        parcel.writeTypedArray(this.mAccessRules, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private java.util.List<android.telephony.UiccAccessRule> mAccessRules;
        private android.service.carrier.CarrierIdentifier mCarrierIdentifier;
        private java.lang.String mIccid;
        private java.lang.String mNickname;
        private int mPolicyRules;
        private int mProfileClass;
        private java.lang.String mProfileName;
        private java.lang.String mServiceProviderName;
        private int mState;

        public Builder(java.lang.String str) {
            if (!android.text.TextUtils.isDigitsOnly(str)) {
                throw new java.lang.IllegalArgumentException("iccid contains invalid characters: " + str);
            }
            this.mIccid = str;
        }

        public Builder(android.service.euicc.EuiccProfileInfo euiccProfileInfo) {
            java.util.List<android.telephony.UiccAccessRule> asList;
            this.mIccid = euiccProfileInfo.mIccid;
            this.mNickname = euiccProfileInfo.mNickname;
            this.mServiceProviderName = euiccProfileInfo.mServiceProviderName;
            this.mProfileName = euiccProfileInfo.mProfileName;
            this.mProfileClass = euiccProfileInfo.mProfileClass;
            this.mState = euiccProfileInfo.mState;
            this.mCarrierIdentifier = euiccProfileInfo.mCarrierIdentifier;
            this.mPolicyRules = euiccProfileInfo.mPolicyRules;
            if (euiccProfileInfo.mAccessRules == null) {
                asList = java.util.Collections.emptyList();
            } else {
                asList = java.util.Arrays.asList(euiccProfileInfo.mAccessRules);
            }
            this.mAccessRules = asList;
        }

        public android.service.euicc.EuiccProfileInfo build() {
            if (this.mIccid == null) {
                throw new java.lang.IllegalStateException("ICCID must be set for a profile.");
            }
            return new android.service.euicc.EuiccProfileInfo(this.mIccid, this.mNickname, this.mServiceProviderName, this.mProfileName, this.mProfileClass, this.mState, this.mCarrierIdentifier, this.mPolicyRules, this.mAccessRules);
        }

        public android.service.euicc.EuiccProfileInfo.Builder setIccid(java.lang.String str) {
            if (!android.text.TextUtils.isDigitsOnly(str)) {
                throw new java.lang.IllegalArgumentException("iccid contains invalid characters: " + str);
            }
            this.mIccid = str;
            return this;
        }

        public android.service.euicc.EuiccProfileInfo.Builder setNickname(java.lang.String str) {
            this.mNickname = str;
            return this;
        }

        public android.service.euicc.EuiccProfileInfo.Builder setServiceProviderName(java.lang.String str) {
            this.mServiceProviderName = str;
            return this;
        }

        public android.service.euicc.EuiccProfileInfo.Builder setProfileName(java.lang.String str) {
            this.mProfileName = str;
            return this;
        }

        public android.service.euicc.EuiccProfileInfo.Builder setProfileClass(int i) {
            this.mProfileClass = i;
            return this;
        }

        public android.service.euicc.EuiccProfileInfo.Builder setState(int i) {
            this.mState = i;
            return this;
        }

        public android.service.euicc.EuiccProfileInfo.Builder setCarrierIdentifier(android.service.carrier.CarrierIdentifier carrierIdentifier) {
            this.mCarrierIdentifier = carrierIdentifier;
            return this;
        }

        public android.service.euicc.EuiccProfileInfo.Builder setPolicyRules(int i) {
            this.mPolicyRules = i;
            return this;
        }

        public android.service.euicc.EuiccProfileInfo.Builder setUiccAccessRule(java.util.List<android.telephony.UiccAccessRule> list) {
            this.mAccessRules = list;
            return this;
        }
    }

    private EuiccProfileInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i, int i2, android.service.carrier.CarrierIdentifier carrierIdentifier, int i3, java.util.List<android.telephony.UiccAccessRule> list) {
        this.mIccid = str;
        this.mNickname = str2;
        this.mServiceProviderName = str3;
        this.mProfileName = str4;
        this.mProfileClass = i;
        this.mState = i2;
        this.mCarrierIdentifier = carrierIdentifier;
        this.mPolicyRules = i3;
        if (list != null && list.size() > 0) {
            this.mAccessRules = (android.telephony.UiccAccessRule[]) list.toArray(new android.telephony.UiccAccessRule[list.size()]);
        } else {
            this.mAccessRules = null;
        }
    }

    public java.lang.String getIccid() {
        return this.mIccid;
    }

    public java.util.List<android.telephony.UiccAccessRule> getUiccAccessRules() {
        if (this.mAccessRules == null) {
            return null;
        }
        return java.util.Arrays.asList(this.mAccessRules);
    }

    public java.lang.String getNickname() {
        return this.mNickname;
    }

    public java.lang.String getServiceProviderName() {
        return this.mServiceProviderName;
    }

    public java.lang.String getProfileName() {
        return this.mProfileName;
    }

    public int getProfileClass() {
        return this.mProfileClass;
    }

    public int getState() {
        return this.mState;
    }

    public android.service.carrier.CarrierIdentifier getCarrierIdentifier() {
        return this.mCarrierIdentifier;
    }

    public int getPolicyRules() {
        return this.mPolicyRules;
    }

    public boolean hasPolicyRules() {
        return this.mPolicyRules != 0;
    }

    public boolean hasPolicyRule(int i) {
        return (i & this.mPolicyRules) != 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.euicc.EuiccProfileInfo euiccProfileInfo = (android.service.euicc.EuiccProfileInfo) obj;
        if (java.util.Objects.equals(this.mIccid, euiccProfileInfo.mIccid) && java.util.Objects.equals(this.mNickname, euiccProfileInfo.mNickname) && java.util.Objects.equals(this.mServiceProviderName, euiccProfileInfo.mServiceProviderName) && java.util.Objects.equals(this.mProfileName, euiccProfileInfo.mProfileName) && this.mProfileClass == euiccProfileInfo.mProfileClass && this.mState == euiccProfileInfo.mState && java.util.Objects.equals(this.mCarrierIdentifier, euiccProfileInfo.mCarrierIdentifier) && this.mPolicyRules == euiccProfileInfo.mPolicyRules && java.util.Arrays.equals(this.mAccessRules, euiccProfileInfo.mAccessRules)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((java.util.Objects.hashCode(this.mIccid) + 31) * 31) + java.util.Objects.hashCode(this.mNickname)) * 31) + java.util.Objects.hashCode(this.mServiceProviderName)) * 31) + java.util.Objects.hashCode(this.mProfileName)) * 31) + this.mProfileClass) * 31) + this.mState) * 31) + java.util.Objects.hashCode(this.mCarrierIdentifier)) * 31) + this.mPolicyRules) * 31) + java.util.Arrays.hashCode(this.mAccessRules);
    }

    public java.lang.String toString() {
        return "EuiccProfileInfo (nickname=" + this.mNickname + ", serviceProviderName=" + this.mServiceProviderName + ", profileName=" + this.mProfileName + ", profileClass=" + this.mProfileClass + ", state=" + this.mState + ", CarrierIdentifier=" + this.mCarrierIdentifier + ", policyRules=" + this.mPolicyRules + ", accessRules=" + java.util.Arrays.toString(this.mAccessRules) + ", iccid=" + android.telephony.SubscriptionInfo.getPrintableId(this.mIccid) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
