package android.telephony.euicc;

/* loaded from: classes3.dex */
public final class DownloadableSubscription implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.euicc.DownloadableSubscription> CREATOR = new android.os.Parcelable.Creator<android.telephony.euicc.DownloadableSubscription>() { // from class: android.telephony.euicc.DownloadableSubscription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.euicc.DownloadableSubscription createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.euicc.DownloadableSubscription(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.euicc.DownloadableSubscription[] newArray(int i) {
            return new android.telephony.euicc.DownloadableSubscription[i];
        }
    };
    private java.util.List<android.telephony.UiccAccessRule> accessRules;
    private java.lang.String carrierName;
    private java.lang.String confirmationCode;

    @java.lang.Deprecated
    public final java.lang.String encodedActivationCode;

    public java.lang.String getEncodedActivationCode() {
        return this.encodedActivationCode;
    }

    private DownloadableSubscription(java.lang.String str) {
        this.encodedActivationCode = str;
    }

    private DownloadableSubscription(android.os.Parcel parcel) {
        this.encodedActivationCode = parcel.readString();
        this.confirmationCode = parcel.readString();
        this.carrierName = parcel.readString();
        this.accessRules = new java.util.ArrayList();
        parcel.readTypedList(this.accessRules, android.telephony.UiccAccessRule.CREATOR);
    }

    private DownloadableSubscription(java.lang.String str, java.lang.String str2, java.lang.String str3, java.util.List<android.telephony.UiccAccessRule> list) {
        this.encodedActivationCode = str;
        this.confirmationCode = str2;
        this.carrierName = str3;
        this.accessRules = list;
    }

    public static final class Builder {
        java.util.List<android.telephony.UiccAccessRule> accessRules;
        private java.lang.String carrierName;
        private java.lang.String confirmationCode;
        private java.lang.String encodedActivationCode;

        @android.annotation.SystemApi
        public Builder() {
        }

        public Builder(android.telephony.euicc.DownloadableSubscription downloadableSubscription) {
            this.encodedActivationCode = downloadableSubscription.getEncodedActivationCode();
            this.confirmationCode = downloadableSubscription.getConfirmationCode();
            this.carrierName = downloadableSubscription.getCarrierName();
            this.accessRules = downloadableSubscription.getAccessRules();
        }

        public Builder(java.lang.String str) {
            this.encodedActivationCode = str;
        }

        public android.telephony.euicc.DownloadableSubscription build() {
            return new android.telephony.euicc.DownloadableSubscription(this.encodedActivationCode, this.confirmationCode, this.carrierName, this.accessRules);
        }

        public android.telephony.euicc.DownloadableSubscription.Builder setEncodedActivationCode(java.lang.String str) {
            this.encodedActivationCode = str;
            return this;
        }

        public android.telephony.euicc.DownloadableSubscription.Builder setConfirmationCode(java.lang.String str) {
            this.confirmationCode = str;
            return this;
        }

        @android.annotation.SystemApi
        public android.telephony.euicc.DownloadableSubscription.Builder setCarrierName(java.lang.String str) {
            this.carrierName = str;
            return this;
        }

        @android.annotation.SystemApi
        public android.telephony.euicc.DownloadableSubscription.Builder setAccessRules(java.util.List<android.telephony.UiccAccessRule> list) {
            this.accessRules = list;
            return this;
        }
    }

    public static android.telephony.euicc.DownloadableSubscription forActivationCode(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str, "Activation code may not be null");
        return new android.telephony.euicc.DownloadableSubscription(str);
    }

    @java.lang.Deprecated
    public void setConfirmationCode(java.lang.String str) {
        this.confirmationCode = str;
    }

    public java.lang.String getConfirmationCode() {
        return this.confirmationCode;
    }

    @java.lang.Deprecated
    public void setCarrierName(java.lang.String str) {
        this.carrierName = str;
    }

    @android.annotation.SystemApi
    public java.lang.String getCarrierName() {
        return this.carrierName;
    }

    @android.annotation.SystemApi
    public java.util.List<android.telephony.UiccAccessRule> getAccessRules() {
        return this.accessRules;
    }

    @java.lang.Deprecated
    public void setAccessRules(java.util.List<android.telephony.UiccAccessRule> list) {
        this.accessRules = list;
    }

    @java.lang.Deprecated
    public void setAccessRules(android.telephony.UiccAccessRule[] uiccAccessRuleArr) {
        this.accessRules = java.util.Arrays.asList(uiccAccessRuleArr);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.encodedActivationCode);
        parcel.writeString(this.confirmationCode);
        parcel.writeString(this.carrierName);
        parcel.writeTypedList(this.accessRules);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
