package android.telephony.euicc;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class EuiccRulesAuthTable implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.euicc.EuiccRulesAuthTable> CREATOR = new android.os.Parcelable.Creator<android.telephony.euicc.EuiccRulesAuthTable>() { // from class: android.telephony.euicc.EuiccRulesAuthTable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.euicc.EuiccRulesAuthTable createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.euicc.EuiccRulesAuthTable(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.euicc.EuiccRulesAuthTable[] newArray(int i) {
            return new android.telephony.euicc.EuiccRulesAuthTable[i];
        }
    };
    public static final int POLICY_RULE_FLAG_CONSENT_REQUIRED = 1;
    private final android.service.carrier.CarrierIdentifier[][] mCarrierIds;
    private final int[] mPolicyRuleFlags;
    private final int[] mPolicyRules;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PolicyRuleFlag {
    }

    public static final class Builder {
        private android.service.carrier.CarrierIdentifier[][] mCarrierIds;
        private int[] mPolicyRuleFlags;
        private int[] mPolicyRules;
        private int mPosition;

        public Builder(int i) {
            this.mPolicyRules = new int[i];
            this.mCarrierIds = new android.service.carrier.CarrierIdentifier[i][];
            this.mPolicyRuleFlags = new int[i];
        }

        public android.telephony.euicc.EuiccRulesAuthTable build() {
            if (this.mPosition != this.mPolicyRules.length) {
                throw new java.lang.IllegalStateException("Not enough rules are added, expected: " + this.mPolicyRules.length + ", added: " + this.mPosition);
            }
            return new android.telephony.euicc.EuiccRulesAuthTable(this.mPolicyRules, this.mCarrierIds, this.mPolicyRuleFlags);
        }

        public android.telephony.euicc.EuiccRulesAuthTable.Builder add(int i, java.util.List<android.service.carrier.CarrierIdentifier> list, int i2) {
            if (this.mPosition >= this.mPolicyRules.length) {
                throw new java.lang.ArrayIndexOutOfBoundsException(this.mPosition);
            }
            this.mPolicyRules[this.mPosition] = i;
            if (list != null && list.size() > 0) {
                this.mCarrierIds[this.mPosition] = (android.service.carrier.CarrierIdentifier[]) list.toArray(new android.service.carrier.CarrierIdentifier[list.size()]);
            }
            this.mPolicyRuleFlags[this.mPosition] = i2;
            this.mPosition++;
            return this;
        }
    }

    public static boolean match(java.lang.String str, java.lang.String str2) {
        if (str.length() < str2.length()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != 'E' && (i >= str2.length() || str.charAt(i) != str2.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private EuiccRulesAuthTable(int[] iArr, android.service.carrier.CarrierIdentifier[][] carrierIdentifierArr, int[] iArr2) {
        this.mPolicyRules = iArr;
        this.mCarrierIds = carrierIdentifierArr;
        this.mPolicyRuleFlags = iArr2;
    }

    public int findIndex(int i, android.service.carrier.CarrierIdentifier carrierIdentifier) {
        android.service.carrier.CarrierIdentifier[] carrierIdentifierArr;
        for (int i2 = 0; i2 < this.mPolicyRules.length; i2++) {
            if ((this.mPolicyRules[i2] & i) != 0 && (carrierIdentifierArr = this.mCarrierIds[i2]) != null && carrierIdentifierArr.length != 0) {
                for (android.service.carrier.CarrierIdentifier carrierIdentifier2 : carrierIdentifierArr) {
                    if (match(carrierIdentifier2.getMcc(), carrierIdentifier.getMcc()) && match(carrierIdentifier2.getMnc(), carrierIdentifier.getMnc())) {
                        java.lang.String gid1 = carrierIdentifier2.getGid1();
                        if (android.text.TextUtils.isEmpty(gid1) || gid1.equals(carrierIdentifier.getGid1())) {
                            java.lang.String gid2 = carrierIdentifier2.getGid2();
                            if (android.text.TextUtils.isEmpty(gid2) || gid2.equals(carrierIdentifier.getGid2())) {
                                return i2;
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }

    public boolean hasPolicyRuleFlag(int i, int i2) {
        if (i < 0 || i >= this.mPolicyRules.length) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        return (this.mPolicyRuleFlags[i] & i2) != 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeIntArray(this.mPolicyRules);
        for (android.service.carrier.CarrierIdentifier[] carrierIdentifierArr : this.mCarrierIds) {
            parcel.writeTypedArray(carrierIdentifierArr, i);
        }
        parcel.writeIntArray(this.mPolicyRuleFlags);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.euicc.EuiccRulesAuthTable euiccRulesAuthTable = (android.telephony.euicc.EuiccRulesAuthTable) obj;
        if (this.mCarrierIds.length != euiccRulesAuthTable.mCarrierIds.length) {
            return false;
        }
        for (int i = 0; i < this.mCarrierIds.length; i++) {
            android.service.carrier.CarrierIdentifier[] carrierIdentifierArr = this.mCarrierIds[i];
            android.service.carrier.CarrierIdentifier[] carrierIdentifierArr2 = euiccRulesAuthTable.mCarrierIds[i];
            if (carrierIdentifierArr != null && carrierIdentifierArr2 != null) {
                if (carrierIdentifierArr.length != carrierIdentifierArr2.length) {
                    return false;
                }
                for (int i2 = 0; i2 < carrierIdentifierArr.length; i2++) {
                    if (!carrierIdentifierArr[i2].equals(carrierIdentifierArr2[i2])) {
                        return false;
                    }
                }
            } else if (carrierIdentifierArr != null || carrierIdentifierArr2 != null) {
                return false;
            }
        }
        if (java.util.Arrays.equals(this.mPolicyRules, euiccRulesAuthTable.mPolicyRules) && java.util.Arrays.equals(this.mPolicyRuleFlags, euiccRulesAuthTable.mPolicyRuleFlags)) {
            return true;
        }
        return false;
    }

    private EuiccRulesAuthTable(android.os.Parcel parcel) {
        this.mPolicyRules = parcel.createIntArray();
        int length = this.mPolicyRules.length;
        this.mCarrierIds = new android.service.carrier.CarrierIdentifier[length][];
        for (int i = 0; i < length; i++) {
            this.mCarrierIds[i] = (android.service.carrier.CarrierIdentifier[]) parcel.createTypedArray(android.service.carrier.CarrierIdentifier.CREATOR);
        }
        this.mPolicyRuleFlags = parcel.createIntArray();
    }
}
