package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class DataSpecificRegistrationInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.DataSpecificRegistrationInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.DataSpecificRegistrationInfo>() { // from class: android.telephony.DataSpecificRegistrationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.DataSpecificRegistrationInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.DataSpecificRegistrationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.DataSpecificRegistrationInfo[] newArray(int i) {
            return new android.telephony.DataSpecificRegistrationInfo[i];
        }
    };
    public static final int LTE_ATTACH_EXTRA_INFO_CSFB_NOT_PREFERRED = 1;
    public static final int LTE_ATTACH_EXTRA_INFO_NONE = 0;
    public static final int LTE_ATTACH_EXTRA_INFO_SMS_ONLY = 2;
    public static final int LTE_ATTACH_TYPE_COMBINED = 2;
    public static final int LTE_ATTACH_TYPE_EPS_ONLY = 1;
    public static final int LTE_ATTACH_TYPE_UNKNOWN = 0;
    public final boolean isDcNrRestricted;
    public final boolean isEnDcAvailable;
    public final boolean isNrAvailable;
    private final int mLteAttachExtraInfo;
    private final int mLteAttachResultType;
    private final android.telephony.VopsSupportInfo mVopsSupportInfo;
    public final int maxDataCalls;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LteAttachExtraInfo {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LteAttachResultType {
    }

    private DataSpecificRegistrationInfo(android.telephony.DataSpecificRegistrationInfo.Builder builder) {
        this.maxDataCalls = builder.mMaxDataCalls;
        this.isDcNrRestricted = builder.mIsDcNrRestricted;
        this.isNrAvailable = builder.mIsNrAvailable;
        this.isEnDcAvailable = builder.mIsEnDcAvailable;
        this.mVopsSupportInfo = builder.mVopsSupportInfo;
        this.mLteAttachResultType = builder.mLteAttachResultType;
        this.mLteAttachExtraInfo = builder.mLteAttachExtraInfo;
    }

    public DataSpecificRegistrationInfo(int i, boolean z, boolean z2, boolean z3, android.telephony.VopsSupportInfo vopsSupportInfo) {
        this.maxDataCalls = i;
        this.isDcNrRestricted = z;
        this.isNrAvailable = z2;
        this.isEnDcAvailable = z3;
        this.mVopsSupportInfo = vopsSupportInfo;
        this.mLteAttachResultType = 0;
        this.mLteAttachExtraInfo = 0;
    }

    DataSpecificRegistrationInfo(android.telephony.DataSpecificRegistrationInfo dataSpecificRegistrationInfo) {
        this.maxDataCalls = dataSpecificRegistrationInfo.maxDataCalls;
        this.isDcNrRestricted = dataSpecificRegistrationInfo.isDcNrRestricted;
        this.isNrAvailable = dataSpecificRegistrationInfo.isNrAvailable;
        this.isEnDcAvailable = dataSpecificRegistrationInfo.isEnDcAvailable;
        this.mVopsSupportInfo = dataSpecificRegistrationInfo.mVopsSupportInfo;
        this.mLteAttachResultType = dataSpecificRegistrationInfo.mLteAttachResultType;
        this.mLteAttachExtraInfo = dataSpecificRegistrationInfo.mLteAttachExtraInfo;
    }

    private DataSpecificRegistrationInfo(android.os.Parcel parcel) {
        this.maxDataCalls = parcel.readInt();
        this.isDcNrRestricted = parcel.readBoolean();
        this.isNrAvailable = parcel.readBoolean();
        this.isEnDcAvailable = parcel.readBoolean();
        this.mVopsSupportInfo = (android.telephony.VopsSupportInfo) parcel.readParcelable(android.telephony.VopsSupportInfo.class.getClassLoader(), android.telephony.VopsSupportInfo.class);
        this.mLteAttachResultType = parcel.readInt();
        this.mLteAttachExtraInfo = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.maxDataCalls);
        parcel.writeBoolean(this.isDcNrRestricted);
        parcel.writeBoolean(this.isNrAvailable);
        parcel.writeBoolean(this.isEnDcAvailable);
        parcel.writeParcelable(this.mVopsSupportInfo, i);
        parcel.writeInt(this.mLteAttachResultType);
        parcel.writeInt(this.mLteAttachExtraInfo);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return getClass().getName() + " :{" + (" maxDataCalls = " + this.maxDataCalls) + (" isDcNrRestricted = " + this.isDcNrRestricted) + (" isNrAvailable = " + this.isNrAvailable) + (" isEnDcAvailable = " + this.isEnDcAvailable) + (" mLteAttachResultType = " + this.mLteAttachResultType) + (" mLteAttachExtraInfo = " + this.mLteAttachExtraInfo) + (" " + this.mVopsSupportInfo) + " }";
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.maxDataCalls), java.lang.Boolean.valueOf(this.isDcNrRestricted), java.lang.Boolean.valueOf(this.isNrAvailable), java.lang.Boolean.valueOf(this.isEnDcAvailable), this.mVopsSupportInfo, java.lang.Integer.valueOf(this.mLteAttachResultType), java.lang.Integer.valueOf(this.mLteAttachExtraInfo));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.DataSpecificRegistrationInfo)) {
            return false;
        }
        android.telephony.DataSpecificRegistrationInfo dataSpecificRegistrationInfo = (android.telephony.DataSpecificRegistrationInfo) obj;
        return this.maxDataCalls == dataSpecificRegistrationInfo.maxDataCalls && this.isDcNrRestricted == dataSpecificRegistrationInfo.isDcNrRestricted && this.isNrAvailable == dataSpecificRegistrationInfo.isNrAvailable && this.isEnDcAvailable == dataSpecificRegistrationInfo.isEnDcAvailable && java.util.Objects.equals(this.mVopsSupportInfo, dataSpecificRegistrationInfo.mVopsSupportInfo) && this.mLteAttachResultType == dataSpecificRegistrationInfo.mLteAttachResultType && this.mLteAttachExtraInfo == dataSpecificRegistrationInfo.mLteAttachExtraInfo;
    }

    @java.lang.Deprecated
    public android.telephony.LteVopsSupportInfo getLteVopsSupportInfo() {
        if (this.mVopsSupportInfo instanceof android.telephony.LteVopsSupportInfo) {
            return (android.telephony.LteVopsSupportInfo) this.mVopsSupportInfo;
        }
        return new android.telephony.LteVopsSupportInfo(1, 1);
    }

    public android.telephony.VopsSupportInfo getVopsSupportInfo() {
        return this.mVopsSupportInfo;
    }

    public int getLteAttachResultType() {
        return this.mLteAttachResultType;
    }

    public int getLteAttachExtraInfo() {
        return this.mLteAttachExtraInfo;
    }

    public static final class Builder {
        private boolean mIsDcNrRestricted;
        private boolean mIsEnDcAvailable;
        private boolean mIsNrAvailable;
        private final int mMaxDataCalls;
        private android.telephony.VopsSupportInfo mVopsSupportInfo;
        private int mLteAttachResultType = 0;
        private int mLteAttachExtraInfo = 0;

        public Builder(int i) {
            this.mMaxDataCalls = i;
        }

        public android.telephony.DataSpecificRegistrationInfo.Builder setDcNrRestricted(boolean z) {
            this.mIsDcNrRestricted = z;
            return this;
        }

        public android.telephony.DataSpecificRegistrationInfo.Builder setNrAvailable(boolean z) {
            this.mIsNrAvailable = z;
            return this;
        }

        public android.telephony.DataSpecificRegistrationInfo.Builder setEnDcAvailable(boolean z) {
            this.mIsEnDcAvailable = z;
            return this;
        }

        public android.telephony.DataSpecificRegistrationInfo.Builder setVopsSupportInfo(android.telephony.VopsSupportInfo vopsSupportInfo) {
            this.mVopsSupportInfo = vopsSupportInfo;
            return this;
        }

        public android.telephony.DataSpecificRegistrationInfo.Builder setLteAttachResultType(int i) {
            this.mLteAttachResultType = i;
            return this;
        }

        public android.telephony.DataSpecificRegistrationInfo.Builder setLteAttachExtraInfo(int i) {
            this.mLteAttachExtraInfo = i;
            return this;
        }

        public android.telephony.DataSpecificRegistrationInfo build() {
            return new android.telephony.DataSpecificRegistrationInfo(this);
        }
    }
}
