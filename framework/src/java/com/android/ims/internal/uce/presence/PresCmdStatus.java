package com.android.ims.internal.uce.presence;

/* loaded from: classes4.dex */
public class PresCmdStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresCmdStatus> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresCmdStatus>() { // from class: com.android.ims.internal.uce.presence.PresCmdStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresCmdStatus createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.presence.PresCmdStatus(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresCmdStatus[] newArray(int i) {
            return new com.android.ims.internal.uce.presence.PresCmdStatus[i];
        }
    };
    private com.android.ims.internal.uce.presence.PresCmdId mCmdId;
    private int mRequestId;
    private com.android.ims.internal.uce.common.StatusCode mStatus;
    private int mUserData;

    public com.android.ims.internal.uce.presence.PresCmdId getCmdId() {
        return this.mCmdId;
    }

    public void setCmdId(com.android.ims.internal.uce.presence.PresCmdId presCmdId) {
        this.mCmdId = presCmdId;
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

    public int getRequestId() {
        return this.mRequestId;
    }

    public void setRequestId(int i) {
        this.mRequestId = i;
    }

    public PresCmdStatus() {
        this.mCmdId = new com.android.ims.internal.uce.presence.PresCmdId();
        this.mStatus = new com.android.ims.internal.uce.common.StatusCode();
        this.mStatus = new com.android.ims.internal.uce.common.StatusCode();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mUserData);
        parcel.writeInt(this.mRequestId);
        parcel.writeParcelable(this.mCmdId, i);
        parcel.writeParcelable(this.mStatus, i);
    }

    private PresCmdStatus(android.os.Parcel parcel) {
        this.mCmdId = new com.android.ims.internal.uce.presence.PresCmdId();
        this.mStatus = new com.android.ims.internal.uce.common.StatusCode();
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mUserData = parcel.readInt();
        this.mRequestId = parcel.readInt();
        this.mCmdId = (com.android.ims.internal.uce.presence.PresCmdId) parcel.readParcelable(com.android.ims.internal.uce.presence.PresCmdId.class.getClassLoader(), com.android.ims.internal.uce.presence.PresCmdId.class);
        this.mStatus = (com.android.ims.internal.uce.common.StatusCode) parcel.readParcelable(com.android.ims.internal.uce.common.StatusCode.class.getClassLoader(), com.android.ims.internal.uce.common.StatusCode.class);
    }
}
