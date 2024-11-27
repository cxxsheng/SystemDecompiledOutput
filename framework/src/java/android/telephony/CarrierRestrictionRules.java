package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class CarrierRestrictionRules implements android.os.Parcelable {
    public static final int CARRIER_RESTRICTION_DEFAULT_ALLOWED = 1;
    public static final int CARRIER_RESTRICTION_DEFAULT_NOT_ALLOWED = 0;
    public static final android.os.Parcelable.Creator<android.telephony.CarrierRestrictionRules> CREATOR = new android.os.Parcelable.Creator<android.telephony.CarrierRestrictionRules>() { // from class: android.telephony.CarrierRestrictionRules.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CarrierRestrictionRules createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CarrierRestrictionRules(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CarrierRestrictionRules[] newArray(int i) {
            return new android.telephony.CarrierRestrictionRules[i];
        }
    };
    public static final int MULTISIM_POLICY_ACTIVE_SERVICE_ON_ANY_SLOT_TO_UNBLOCK_OTHER_SLOTS = 6;
    public static final int MULTISIM_POLICY_ACTIVE_SERVICE_ON_SLOT_1_TO_UNBLOCK_OTHER_SLOTS = 5;
    public static final int MULTISIM_POLICY_ALL_SIMS_MUST_BE_VALID = 7;
    public static final int MULTISIM_POLICY_APPLY_TO_ALL_SLOTS = 2;
    public static final int MULTISIM_POLICY_APPLY_TO_ONLY_SLOT_1 = 3;
    public static final int MULTISIM_POLICY_NONE = 0;
    public static final int MULTISIM_POLICY_ONE_VALID_SIM_MUST_BE_PRESENT = 1;
    public static final int MULTISIM_POLICY_SLOT_POLICY_OTHER = 8;
    public static final int MULTISIM_POLICY_VALID_SIM_MUST_PRESENT_ON_SLOT_1 = 4;
    private static final char WILD_CHARACTER = '?';
    private java.util.List<android.telephony.CarrierInfo> mAllowedCarrierInfo;
    private java.util.List<android.service.carrier.CarrierIdentifier> mAllowedCarriers;
    private int mCarrierRestrictionDefault;
    private int mCarrierRestrictionStatus;
    private java.util.List<android.telephony.CarrierInfo> mExcludedCarrierInfo;
    private java.util.List<android.service.carrier.CarrierIdentifier> mExcludedCarriers;
    private int mMultiSimPolicy;
    private boolean mUseCarrierLockInfo;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CarrierRestrictionDefault {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MultiSimPolicy {
    }

    private CarrierRestrictionRules() {
        this.mAllowedCarriers = new java.util.ArrayList();
        this.mExcludedCarriers = new java.util.ArrayList();
        this.mAllowedCarrierInfo = new java.util.ArrayList();
        this.mExcludedCarrierInfo = new java.util.ArrayList();
        this.mCarrierRestrictionDefault = 0;
        this.mMultiSimPolicy = 0;
        this.mCarrierRestrictionStatus = 0;
        this.mUseCarrierLockInfo = false;
    }

    private CarrierRestrictionRules(android.os.Parcel parcel) {
        this.mAllowedCarriers = new java.util.ArrayList();
        this.mExcludedCarriers = new java.util.ArrayList();
        this.mAllowedCarrierInfo = new java.util.ArrayList();
        this.mExcludedCarrierInfo = new java.util.ArrayList();
        parcel.readTypedList(this.mAllowedCarriers, android.service.carrier.CarrierIdentifier.CREATOR);
        parcel.readTypedList(this.mExcludedCarriers, android.service.carrier.CarrierIdentifier.CREATOR);
        this.mCarrierRestrictionDefault = parcel.readInt();
        this.mMultiSimPolicy = parcel.readInt();
        this.mCarrierRestrictionStatus = parcel.readInt();
        if (com.android.internal.telephony.flags.Flags.carrierRestrictionRulesEnhancement()) {
            parcel.readTypedList(this.mAllowedCarrierInfo, android.telephony.CarrierInfo.CREATOR);
            parcel.readTypedList(this.mExcludedCarrierInfo, android.telephony.CarrierInfo.CREATOR);
            this.mUseCarrierLockInfo = parcel.readBoolean();
        }
    }

    public static android.telephony.CarrierRestrictionRules.Builder newBuilder() {
        return new android.telephony.CarrierRestrictionRules.Builder();
    }

    public boolean isAllCarriersAllowed() {
        if (com.android.internal.telephony.flags.Flags.carrierRestrictionStatus() && this.mCarrierRestrictionStatus == 1) {
            return true;
        }
        return (com.android.internal.telephony.flags.Flags.carrierRestrictionRulesEnhancement() && this.mUseCarrierLockInfo) ? this.mAllowedCarrierInfo.isEmpty() && this.mExcludedCarrierInfo.isEmpty() && this.mCarrierRestrictionDefault == 1 : this.mAllowedCarriers.isEmpty() && this.mExcludedCarriers.isEmpty() && this.mCarrierRestrictionDefault == 1;
    }

    public java.util.List<android.service.carrier.CarrierIdentifier> getAllowedCarriers() {
        return this.mAllowedCarriers;
    }

    public java.util.List<android.service.carrier.CarrierIdentifier> getExcludedCarriers() {
        return this.mExcludedCarriers;
    }

    public java.util.List<android.telephony.CarrierInfo> getExcludedCarriersInfoList() {
        return this.mExcludedCarrierInfo;
    }

    public java.util.List<android.telephony.CarrierInfo> getAllowedCarriersInfoList() {
        return this.mAllowedCarrierInfo;
    }

    public int getDefaultCarrierRestriction() {
        return this.mCarrierRestrictionDefault;
    }

    public int getMultiSimPolicy() {
        return this.mMultiSimPolicy;
    }

    public java.util.List<java.lang.Boolean> areCarrierIdentifiersAllowed(java.util.List<android.service.carrier.CarrierIdentifier> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        int i = 0;
        while (true) {
            if (i >= list.size()) {
                break;
            }
            boolean isCarrierIdInList = isCarrierIdInList(list.get(i), this.mAllowedCarriers);
            boolean isCarrierIdInList2 = isCarrierIdInList(list.get(i), this.mExcludedCarriers);
            if (this.mCarrierRestrictionDefault == 0) {
                arrayList.add(java.lang.Boolean.valueOf(isCarrierIdInList && !isCarrierIdInList2));
            } else {
                if (isCarrierIdInList2 && !isCarrierIdInList) {
                    r4 = false;
                }
                arrayList.add(java.lang.Boolean.valueOf(r4));
            }
            i++;
        }
        if (this.mMultiSimPolicy == 1) {
            java.util.Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (((java.lang.Boolean) it.next()).booleanValue()) {
                    arrayList.replaceAll(new java.util.function.UnaryOperator() { // from class: android.telephony.CarrierRestrictionRules$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final java.lang.Object apply(java.lang.Object obj) {
                            return android.telephony.CarrierRestrictionRules.lambda$areCarrierIdentifiersAllowed$0((java.lang.Boolean) obj);
                        }
                    });
                    break;
                }
            }
        }
        return arrayList;
    }

    static /* synthetic */ java.lang.Boolean lambda$areCarrierIdentifiersAllowed$0(java.lang.Boolean bool) {
        return true;
    }

    private static boolean isCarrierIdInList(android.service.carrier.CarrierIdentifier carrierIdentifier, java.util.List<android.service.carrier.CarrierIdentifier> list) {
        for (android.service.carrier.CarrierIdentifier carrierIdentifier2 : list) {
            if (patternMatch(carrierIdentifier.getMcc(), carrierIdentifier2.getMcc()) && patternMatch(carrierIdentifier.getMnc(), carrierIdentifier2.getMnc())) {
                java.lang.String convertNullToEmpty = convertNullToEmpty(carrierIdentifier2.getSpn());
                java.lang.String convertNullToEmpty2 = convertNullToEmpty(carrierIdentifier.getSpn());
                if (convertNullToEmpty.isEmpty() || patternMatch(convertNullToEmpty2, convertNullToEmpty)) {
                    java.lang.String convertNullToEmpty3 = convertNullToEmpty(carrierIdentifier2.getImsi());
                    java.lang.String convertNullToEmpty4 = convertNullToEmpty(carrierIdentifier.getImsi());
                    if (patternMatch(convertNullToEmpty4.substring(0, java.lang.Math.min(convertNullToEmpty4.length(), convertNullToEmpty3.length())), convertNullToEmpty3)) {
                        java.lang.String convertNullToEmpty5 = convertNullToEmpty(carrierIdentifier2.getGid1());
                        java.lang.String convertNullToEmpty6 = convertNullToEmpty(carrierIdentifier.getGid1());
                        if (patternMatch(convertNullToEmpty6.substring(0, java.lang.Math.min(convertNullToEmpty6.length(), convertNullToEmpty5.length())), convertNullToEmpty5)) {
                            java.lang.String convertNullToEmpty7 = convertNullToEmpty(carrierIdentifier2.getGid2());
                            java.lang.String convertNullToEmpty8 = convertNullToEmpty(carrierIdentifier.getGid2());
                            if (patternMatch(convertNullToEmpty8.substring(0, java.lang.Math.min(convertNullToEmpty8.length(), convertNullToEmpty7.length())), convertNullToEmpty7)) {
                                return true;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return false;
    }

    private static java.lang.String convertNullToEmpty(java.lang.String str) {
        return java.util.Objects.toString(str, "");
    }

    private static boolean patternMatch(java.lang.String str, java.lang.String str2) {
        if (str.length() != str2.length()) {
            return false;
        }
        java.lang.String lowerCase = str.toLowerCase(java.util.Locale.ROOT);
        java.lang.String lowerCase2 = str2.toLowerCase(java.util.Locale.ROOT);
        for (int i = 0; i < lowerCase2.length(); i++) {
            if (lowerCase2.charAt(i) != lowerCase.charAt(i) && lowerCase2.charAt(i) != '?') {
                return false;
            }
        }
        return true;
    }

    public int getCarrierRestrictionStatus() {
        return this.mCarrierRestrictionStatus;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.mAllowedCarriers);
        parcel.writeTypedList(this.mExcludedCarriers);
        parcel.writeInt(this.mCarrierRestrictionDefault);
        parcel.writeInt(this.mMultiSimPolicy);
        parcel.writeInt(this.mCarrierRestrictionStatus);
        if (com.android.internal.telephony.flags.Flags.carrierRestrictionRulesEnhancement()) {
            parcel.writeTypedList(this.mAllowedCarrierInfo);
            parcel.writeTypedList(this.mExcludedCarrierInfo);
            parcel.writeBoolean(this.mUseCarrierLockInfo);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "CarrierRestrictionRules(allowed:" + this.mAllowedCarriers + ", excluded:" + this.mExcludedCarriers + ", default:" + this.mCarrierRestrictionDefault + ", MultiSim policy:" + this.mMultiSimPolicy + getCarrierInfoList() + "  mIsCarrierLockInfoSupported = " + this.mUseCarrierLockInfo + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    private java.lang.String getCarrierInfoList() {
        if (com.android.internal.telephony.flags.Flags.carrierRestrictionRulesEnhancement()) {
            return ",  allowedCarrierInfoList:" + this.mAllowedCarrierInfo + ", excludedCarrierInfoList:" + this.mExcludedCarrierInfo;
        }
        return "";
    }

    public static final class Builder {
        private final android.telephony.CarrierRestrictionRules mRules = new android.telephony.CarrierRestrictionRules();

        public android.telephony.CarrierRestrictionRules build() {
            return this.mRules;
        }

        public android.telephony.CarrierRestrictionRules.Builder setAllCarriersAllowed() {
            this.mRules.mAllowedCarriers.clear();
            this.mRules.mExcludedCarriers.clear();
            this.mRules.mCarrierRestrictionDefault = 1;
            if (com.android.internal.telephony.flags.Flags.carrierRestrictionRulesEnhancement()) {
                this.mRules.mCarrierRestrictionStatus = 1;
                this.mRules.mAllowedCarrierInfo.clear();
                this.mRules.mExcludedCarrierInfo.clear();
                this.mRules.mUseCarrierLockInfo = false;
            }
            return this;
        }

        public android.telephony.CarrierRestrictionRules.Builder setAllowedCarriers(java.util.List<android.service.carrier.CarrierIdentifier> list) {
            this.mRules.mAllowedCarriers = new java.util.ArrayList(list);
            return this;
        }

        public android.telephony.CarrierRestrictionRules.Builder setExcludedCarriers(java.util.List<android.service.carrier.CarrierIdentifier> list) {
            this.mRules.mExcludedCarriers = new java.util.ArrayList(list);
            return this;
        }

        public android.telephony.CarrierRestrictionRules.Builder setDefaultCarrierRestriction(int i) {
            this.mRules.mCarrierRestrictionDefault = i;
            return this;
        }

        public android.telephony.CarrierRestrictionRules.Builder setMultiSimPolicy(int i) {
            this.mRules.mMultiSimPolicy = i;
            return this;
        }

        public android.telephony.CarrierRestrictionRules.Builder setCarrierRestrictionStatus(int i) {
            this.mRules.mCarrierRestrictionStatus = i;
            return this;
        }

        public android.telephony.CarrierRestrictionRules.Builder setAllowedCarrierInfo(java.util.List<android.telephony.CarrierInfo> list) {
            this.mRules.mAllowedCarrierInfo = new java.util.ArrayList(list);
            return this;
        }

        public android.telephony.CarrierRestrictionRules.Builder setExcludedCarrierInfo(java.util.List<android.telephony.CarrierInfo> list) {
            this.mRules.mExcludedCarrierInfo = new java.util.ArrayList(list);
            return this;
        }

        public android.telephony.CarrierRestrictionRules.Builder setCarrierLockInfoFeature(boolean z) {
            this.mRules.mUseCarrierLockInfo = z;
            return this;
        }
    }
}
