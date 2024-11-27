package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class ImsSsInfo implements android.os.Parcelable {
    public static final int CLIR_OUTGOING_DEFAULT = 0;
    public static final int CLIR_OUTGOING_INVOCATION = 1;
    public static final int CLIR_OUTGOING_SUPPRESSION = 2;
    public static final int CLIR_STATUS_NOT_PROVISIONED = 0;
    public static final int CLIR_STATUS_PROVISIONED_PERMANENT = 1;
    public static final int CLIR_STATUS_TEMPORARILY_ALLOWED = 4;
    public static final int CLIR_STATUS_TEMPORARILY_RESTRICTED = 3;
    public static final int CLIR_STATUS_UNKNOWN = 2;
    public static final android.os.Parcelable.Creator<android.telephony.ims.ImsSsInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.ImsSsInfo>() { // from class: android.telephony.ims.ImsSsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.ImsSsInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.ImsSsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.ImsSsInfo[] newArray(int i) {
            return new android.telephony.ims.ImsSsInfo[i];
        }
    };
    public static final int DISABLED = 0;
    public static final int ENABLED = 1;
    public static final int NOT_REGISTERED = -1;
    public static final int SERVICE_NOT_PROVISIONED = 0;
    public static final int SERVICE_PROVISIONED = 1;
    public static final int SERVICE_PROVISIONING_UNKNOWN = -1;
    private int mClirInterrogationStatus;
    private int mClirOutgoingState;
    public java.lang.String mIcbNum;
    public int mProvisionStatus;
    public int mStatus;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ClirInterrogationStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ClirOutgoingState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ServiceProvisionStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ServiceStatus {
    }

    public ImsSsInfo() {
        this.mProvisionStatus = -1;
        this.mClirInterrogationStatus = 2;
        this.mClirOutgoingState = 0;
    }

    public static final class Builder {
        private final android.telephony.ims.ImsSsInfo mImsSsInfo = new android.telephony.ims.ImsSsInfo();

        public Builder(int i) {
            this.mImsSsInfo.mStatus = i;
        }

        public android.telephony.ims.ImsSsInfo.Builder setIncomingCommunicationBarringNumber(java.lang.String str) {
            this.mImsSsInfo.mIcbNum = str;
            return this;
        }

        public android.telephony.ims.ImsSsInfo.Builder setProvisionStatus(int i) {
            this.mImsSsInfo.mProvisionStatus = i;
            return this;
        }

        public android.telephony.ims.ImsSsInfo.Builder setClirInterrogationStatus(int i) {
            this.mImsSsInfo.mClirInterrogationStatus = i;
            return this;
        }

        public android.telephony.ims.ImsSsInfo.Builder setClirOutgoingState(int i) {
            this.mImsSsInfo.mClirOutgoingState = i;
            return this;
        }

        public android.telephony.ims.ImsSsInfo build() {
            return this.mImsSsInfo;
        }
    }

    @java.lang.Deprecated
    public ImsSsInfo(int i, java.lang.String str) {
        this.mProvisionStatus = -1;
        this.mClirInterrogationStatus = 2;
        this.mClirOutgoingState = 0;
        this.mStatus = i;
        this.mIcbNum = str;
    }

    private ImsSsInfo(android.os.Parcel parcel) {
        this.mProvisionStatus = -1;
        this.mClirInterrogationStatus = 2;
        this.mClirOutgoingState = 0;
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
        parcel.writeString(this.mIcbNum);
        parcel.writeInt(this.mProvisionStatus);
        parcel.writeInt(this.mClirInterrogationStatus);
        parcel.writeInt(this.mClirOutgoingState);
    }

    public java.lang.String toString() {
        return super.toString() + ", Status: " + (this.mStatus == 0 ? "disabled" : "enabled") + ", ProvisionStatus: " + provisionStatusToString(this.mProvisionStatus);
    }

    private static java.lang.String provisionStatusToString(int i) {
        switch (i) {
            case 0:
                return "Service not provisioned";
            case 1:
                return "Service provisioned";
            default:
                return "Service provisioning unknown";
        }
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.mStatus = parcel.readInt();
        this.mIcbNum = parcel.readString();
        this.mProvisionStatus = parcel.readInt();
        this.mClirInterrogationStatus = parcel.readInt();
        this.mClirOutgoingState = parcel.readInt();
    }

    public int getStatus() {
        return this.mStatus;
    }

    @java.lang.Deprecated
    public java.lang.String getIcbNum() {
        return this.mIcbNum;
    }

    public java.lang.String getIncomingCommunicationBarringNumber() {
        return this.mIcbNum;
    }

    public int getProvisionStatus() {
        return this.mProvisionStatus;
    }

    public int getClirOutgoingState() {
        return this.mClirOutgoingState;
    }

    public int getClirInterrogationStatus() {
        return this.mClirInterrogationStatus;
    }

    public int[] getCompatArray(int i) {
        int[] iArr = new int[2];
        if (i == 8) {
            iArr[0] = getClirOutgoingState();
            iArr[1] = getClirInterrogationStatus();
            return iArr;
        }
        if (i == 10) {
            iArr[0] = getProvisionStatus();
        }
        iArr[0] = getStatus();
        iArr[1] = getProvisionStatus();
        return iArr;
    }
}
