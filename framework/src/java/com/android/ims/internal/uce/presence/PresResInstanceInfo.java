package com.android.ims.internal.uce.presence;

/* loaded from: classes4.dex */
public class PresResInstanceInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresResInstanceInfo> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresResInstanceInfo>() { // from class: com.android.ims.internal.uce.presence.PresResInstanceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresResInstanceInfo createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.presence.PresResInstanceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresResInstanceInfo[] newArray(int i) {
            return new com.android.ims.internal.uce.presence.PresResInstanceInfo[i];
        }
    };
    public static final int UCE_PRES_RES_INSTANCE_STATE_ACTIVE = 0;
    public static final int UCE_PRES_RES_INSTANCE_STATE_PENDING = 1;
    public static final int UCE_PRES_RES_INSTANCE_STATE_TERMINATED = 2;
    public static final int UCE_PRES_RES_INSTANCE_STATE_UNKNOWN = 3;
    public static final int UCE_PRES_RES_INSTANCE_UNKNOWN = 4;
    private java.lang.String mId;
    private java.lang.String mPresentityUri;
    private java.lang.String mReason;
    private int mResInstanceState;
    private com.android.ims.internal.uce.presence.PresTupleInfo[] mTupleInfoArray;

    public int getResInstanceState() {
        return this.mResInstanceState;
    }

    public void setResInstanceState(int i) {
        this.mResInstanceState = i;
    }

    public java.lang.String getResId() {
        return this.mId;
    }

    public void setResId(java.lang.String str) {
        this.mId = str;
    }

    public java.lang.String getReason() {
        return this.mReason;
    }

    public void setReason(java.lang.String str) {
        this.mReason = str;
    }

    public java.lang.String getPresentityUri() {
        return this.mPresentityUri;
    }

    public void setPresentityUri(java.lang.String str) {
        this.mPresentityUri = str;
    }

    public com.android.ims.internal.uce.presence.PresTupleInfo[] getTupleInfo() {
        return this.mTupleInfoArray;
    }

    public void setTupleInfo(com.android.ims.internal.uce.presence.PresTupleInfo[] presTupleInfoArr) {
        this.mTupleInfoArray = new com.android.ims.internal.uce.presence.PresTupleInfo[presTupleInfoArr.length];
        this.mTupleInfoArray = presTupleInfoArr;
    }

    public PresResInstanceInfo() {
        this.mId = "";
        this.mReason = "";
        this.mPresentityUri = "";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeString(this.mReason);
        parcel.writeInt(this.mResInstanceState);
        parcel.writeString(this.mPresentityUri);
        parcel.writeParcelableArray(this.mTupleInfoArray, i);
    }

    private PresResInstanceInfo(android.os.Parcel parcel) {
        this.mId = "";
        this.mReason = "";
        this.mPresentityUri = "";
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mId = parcel.readString();
        this.mReason = parcel.readString();
        this.mResInstanceState = parcel.readInt();
        this.mPresentityUri = parcel.readString();
        android.os.Parcelable[] parcelableArr = (android.os.Parcelable[]) parcel.readParcelableArray(com.android.ims.internal.uce.presence.PresTupleInfo.class.getClassLoader(), com.android.ims.internal.uce.presence.PresTupleInfo.class);
        this.mTupleInfoArray = new com.android.ims.internal.uce.presence.PresTupleInfo[0];
        if (parcelableArr != null) {
            this.mTupleInfoArray = (com.android.ims.internal.uce.presence.PresTupleInfo[]) java.util.Arrays.copyOf(parcelableArr, parcelableArr.length, com.android.ims.internal.uce.presence.PresTupleInfo[].class);
        }
    }
}
