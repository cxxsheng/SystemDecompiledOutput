package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class ImsSsData implements android.os.Parcelable {
    public static final int RESULT_SUCCESS = 0;
    public static final int SERVICE_CLASS_DATA = 2;
    public static final int SERVICE_CLASS_DATA_CIRCUIT_ASYNC = 32;
    public static final int SERVICE_CLASS_DATA_CIRCUIT_SYNC = 16;
    public static final int SERVICE_CLASS_DATA_PACKET_ACCESS = 64;
    public static final int SERVICE_CLASS_DATA_PAD = 128;
    public static final int SERVICE_CLASS_FAX = 4;
    public static final int SERVICE_CLASS_NONE = 0;
    public static final int SERVICE_CLASS_SMS = 8;
    public static final int SERVICE_CLASS_VOICE = 1;
    public static final int SS_ACTIVATION = 0;
    public static final int SS_ALL_BARRING = 18;
    public static final int SS_ALL_DATA_TELESERVICES = 3;
    public static final int SS_ALL_TELESERVICES_EXCEPT_SMS = 5;
    public static final int SS_ALL_TELESEVICES = 1;
    public static final int SS_ALL_TELE_AND_BEARER_SERVICES = 0;
    public static final int SS_BAIC = 16;
    public static final int SS_BAIC_ROAMING = 17;
    public static final int SS_BAOC = 13;
    public static final int SS_BAOIC = 14;
    public static final int SS_BAOIC_EXC_HOME = 15;
    public static final int SS_CFU = 0;
    public static final int SS_CFUT = 6;
    public static final int SS_CF_ALL = 4;
    public static final int SS_CF_ALL_CONDITIONAL = 5;
    public static final int SS_CF_BUSY = 1;
    public static final int SS_CF_NOT_REACHABLE = 3;
    public static final int SS_CF_NO_REPLY = 2;
    public static final int SS_CLIP = 7;
    public static final int SS_CLIR = 8;
    public static final int SS_CNAP = 11;
    public static final int SS_COLP = 9;
    public static final int SS_COLR = 10;
    public static final int SS_DEACTIVATION = 1;
    public static final int SS_ERASURE = 4;
    public static final int SS_INCOMING_BARRING = 20;
    public static final int SS_INCOMING_BARRING_ANONYMOUS = 22;
    public static final int SS_INCOMING_BARRING_DN = 21;
    public static final int SS_INTERROGATION = 2;
    public static final int SS_OUTGOING_BARRING = 19;
    public static final int SS_REGISTRATION = 3;
    public static final int SS_SMS_SERVICES = 4;
    public static final int SS_TELEPHONY = 2;
    public static final int SS_WAIT = 12;
    private java.util.List<android.telephony.ims.ImsCallForwardInfo> mCfInfo;
    private java.util.List<android.telephony.ims.ImsSsInfo> mImsSsInfo;
    private int[] mSsInfo;
    public final int requestType;
    public final int result;
    public final int serviceClass;
    public final int serviceType;
    public final int teleserviceType;
    private static final java.lang.String TAG = android.telephony.ims.ImsSsData.class.getCanonicalName();
    public static final android.os.Parcelable.Creator<android.telephony.ims.ImsSsData> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.ImsSsData>() { // from class: android.telephony.ims.ImsSsData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.ImsSsData createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.ImsSsData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.ImsSsData[] newArray(int i) {
            return new android.telephony.ims.ImsSsData[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RequestType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ServiceClassFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ServiceType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TeleserviceType {
    }

    public static final class Builder {
        private android.telephony.ims.ImsSsData mImsSsData;

        public Builder(int i, int i2, int i3, int i4, int i5) {
            this.mImsSsData = new android.telephony.ims.ImsSsData(i, i2, i3, i4, i5);
        }

        public android.telephony.ims.ImsSsData.Builder setSuppServiceInfo(java.util.List<android.telephony.ims.ImsSsInfo> list) {
            this.mImsSsData.mImsSsInfo = list;
            return this;
        }

        public android.telephony.ims.ImsSsData.Builder setCallForwardingInfo(java.util.List<android.telephony.ims.ImsCallForwardInfo> list) {
            this.mImsSsData.mCfInfo = list;
            return this;
        }

        public android.telephony.ims.ImsSsData build() {
            return this.mImsSsData;
        }
    }

    public ImsSsData(int i, int i2, int i3, int i4, int i5) {
        this.serviceType = i;
        this.requestType = i2;
        this.teleserviceType = i3;
        this.serviceClass = i4;
        this.result = i5;
    }

    private ImsSsData(android.os.Parcel parcel) {
        this.serviceType = parcel.readInt();
        this.requestType = parcel.readInt();
        this.teleserviceType = parcel.readInt();
        this.serviceClass = parcel.readInt();
        this.result = parcel.readInt();
        this.mSsInfo = parcel.createIntArray();
        this.mCfInfo = parcel.readParcelableList(new java.util.ArrayList(), getClass().getClassLoader(), android.telephony.ims.ImsCallForwardInfo.class);
        this.mImsSsInfo = parcel.readParcelableList(new java.util.ArrayList(), getClass().getClassLoader(), android.telephony.ims.ImsSsInfo.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(getServiceType());
        parcel.writeInt(getRequestType());
        parcel.writeInt(getTeleserviceType());
        parcel.writeInt(getServiceClass());
        parcel.writeInt(getResult());
        parcel.writeIntArray(this.mSsInfo);
        parcel.writeParcelableList(this.mCfInfo, 0);
        parcel.writeParcelableList(this.mImsSsInfo, 0);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isTypeCF() {
        return getServiceType() == 0 || getServiceType() == 1 || getServiceType() == 2 || getServiceType() == 3 || getServiceType() == 4 || getServiceType() == 5;
    }

    public boolean isTypeCf() {
        return isTypeCF();
    }

    public boolean isTypeUnConditional() {
        return getServiceType() == 0 || getServiceType() == 4;
    }

    public boolean isTypeCW() {
        return getServiceType() == 12;
    }

    public boolean isTypeCw() {
        return isTypeCW();
    }

    public boolean isTypeClip() {
        return getServiceType() == 7;
    }

    public boolean isTypeColr() {
        return getServiceType() == 10;
    }

    public boolean isTypeColp() {
        return getServiceType() == 9;
    }

    public boolean isTypeClir() {
        return getServiceType() == 8;
    }

    public boolean isTypeIcb() {
        return getServiceType() == 21 || getServiceType() == 22;
    }

    public boolean isTypeBarring() {
        return getServiceType() == 13 || getServiceType() == 14 || getServiceType() == 15 || getServiceType() == 16 || getServiceType() == 17 || getServiceType() == 18 || getServiceType() == 19 || getServiceType() == 20;
    }

    public boolean isTypeInterrogation() {
        return getRequestType() == 2;
    }

    public int getRequestType() {
        return this.requestType;
    }

    public int getServiceType() {
        return this.serviceType;
    }

    public int getTeleserviceType() {
        return this.teleserviceType;
    }

    public int getServiceClass() {
        return this.serviceClass;
    }

    public int getResult() {
        return this.result;
    }

    public void setSuppServiceInfo(int[] iArr) {
        this.mSsInfo = iArr;
    }

    public void setImsSpecificSuppServiceInfo(android.telephony.ims.ImsSsInfo[] imsSsInfoArr) {
        this.mImsSsInfo = java.util.Arrays.asList(imsSsInfoArr);
    }

    public void setCallForwardingInfo(android.telephony.ims.ImsCallForwardInfo[] imsCallForwardInfoArr) {
        this.mCfInfo = java.util.Arrays.asList(imsCallForwardInfoArr);
    }

    public int[] getSuppServiceInfoCompat() {
        if (this.mSsInfo != null) {
            return this.mSsInfo;
        }
        int[] iArr = new int[2];
        if (this.mImsSsInfo == null || this.mImsSsInfo.size() == 0) {
            com.android.telephony.Rlog.e(TAG, "getSuppServiceInfoCompat: Could not parse mImsSsInfo, returning empty int[]");
            return iArr;
        }
        if (isTypeClir()) {
            iArr[0] = this.mImsSsInfo.get(0).getClirOutgoingState();
            iArr[1] = this.mImsSsInfo.get(0).getClirInterrogationStatus();
            return iArr;
        }
        if (isTypeColr()) {
            iArr[0] = this.mImsSsInfo.get(0).getProvisionStatus();
        }
        iArr[0] = this.mImsSsInfo.get(0).getStatus();
        iArr[1] = this.mImsSsInfo.get(0).getProvisionStatus();
        return iArr;
    }

    public java.util.List<android.telephony.ims.ImsSsInfo> getSuppServiceInfo() {
        return this.mImsSsInfo;
    }

    public java.util.List<android.telephony.ims.ImsCallForwardInfo> getCallForwardInfo() {
        return this.mCfInfo;
    }

    public java.lang.String toString() {
        return "[ImsSsData] ServiceType: " + getServiceType() + " RequestType: " + getRequestType() + " TeleserviceType: " + getTeleserviceType() + " ServiceClass: " + getServiceClass() + " Result: " + getResult();
    }
}
