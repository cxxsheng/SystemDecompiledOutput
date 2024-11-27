package com.android.ims.internal.uce.options;

/* loaded from: classes4.dex */
public class OptionsCmdStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.options.OptionsCmdStatus> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.options.OptionsCmdStatus>() { // from class: com.android.ims.internal.uce.options.OptionsCmdStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.options.OptionsCmdStatus createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.options.OptionsCmdStatus(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.options.OptionsCmdStatus[] newArray(int i) {
            return new com.android.ims.internal.uce.options.OptionsCmdStatus[i];
        }
    };
    private com.android.ims.internal.uce.common.CapInfo mCapInfo;
    private com.android.ims.internal.uce.options.OptionsCmdId mCmdId;
    private com.android.ims.internal.uce.common.StatusCode mStatus;
    private int mUserData;

    public com.android.ims.internal.uce.options.OptionsCmdId getCmdId() {
        return this.mCmdId;
    }

    public void setCmdId(com.android.ims.internal.uce.options.OptionsCmdId optionsCmdId) {
        this.mCmdId = optionsCmdId;
    }

    public int getUserData() {
        return this.mUserData;
    }

    public void setUserData(int i) {
        this.mUserData = i;
    }

    public com.android.ims.internal.uce.common.StatusCode getStatus() {
        return this.mStatus;
    }

    public void setStatus(com.android.ims.internal.uce.common.StatusCode statusCode) {
        this.mStatus = statusCode;
    }

    public OptionsCmdStatus() {
        this.mStatus = new com.android.ims.internal.uce.common.StatusCode();
        this.mCapInfo = new com.android.ims.internal.uce.common.CapInfo();
        this.mCmdId = new com.android.ims.internal.uce.options.OptionsCmdId();
        this.mUserData = 0;
    }

    public com.android.ims.internal.uce.common.CapInfo getCapInfo() {
        return this.mCapInfo;
    }

    public void setCapInfo(com.android.ims.internal.uce.common.CapInfo capInfo) {
        this.mCapInfo = capInfo;
    }

    public static com.android.ims.internal.uce.options.OptionsCmdStatus getOptionsCmdStatusInstance() {
        return new com.android.ims.internal.uce.options.OptionsCmdStatus();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mUserData);
        parcel.writeParcelable(this.mCmdId, i);
        parcel.writeParcelable(this.mStatus, i);
        parcel.writeParcelable(this.mCapInfo, i);
    }

    private OptionsCmdStatus(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mUserData = parcel.readInt();
        this.mCmdId = (com.android.ims.internal.uce.options.OptionsCmdId) parcel.readParcelable(com.android.ims.internal.uce.options.OptionsCmdId.class.getClassLoader(), com.android.ims.internal.uce.options.OptionsCmdId.class);
        this.mStatus = (com.android.ims.internal.uce.common.StatusCode) parcel.readParcelable(com.android.ims.internal.uce.common.StatusCode.class.getClassLoader(), com.android.ims.internal.uce.common.StatusCode.class);
        this.mCapInfo = (com.android.ims.internal.uce.common.CapInfo) parcel.readParcelable(com.android.ims.internal.uce.common.CapInfo.class.getClassLoader(), com.android.ims.internal.uce.common.CapInfo.class);
    }
}
