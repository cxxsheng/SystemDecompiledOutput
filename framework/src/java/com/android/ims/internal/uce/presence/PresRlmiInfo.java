package com.android.ims.internal.uce.presence;

/* loaded from: classes4.dex */
public class PresRlmiInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresRlmiInfo> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresRlmiInfo>() { // from class: com.android.ims.internal.uce.presence.PresRlmiInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresRlmiInfo createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.presence.PresRlmiInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresRlmiInfo[] newArray(int i) {
            return new com.android.ims.internal.uce.presence.PresRlmiInfo[i];
        }
    };
    private boolean mFullState;
    private java.lang.String mListName;
    private com.android.ims.internal.uce.presence.PresSubscriptionState mPresSubscriptionState;
    private int mRequestId;
    private int mSubscriptionExpireTime;
    private java.lang.String mSubscriptionTerminatedReason;
    private java.lang.String mUri;
    private int mVersion;

    public java.lang.String getUri() {
        return this.mUri;
    }

    public void setUri(java.lang.String str) {
        this.mUri = str;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public void setVersion(int i) {
        this.mVersion = i;
    }

    public boolean isFullState() {
        return this.mFullState;
    }

    public void setFullState(boolean z) {
        this.mFullState = z;
    }

    public java.lang.String getListName() {
        return this.mListName;
    }

    public void setListName(java.lang.String str) {
        this.mListName = str;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public void setRequestId(int i) {
        this.mRequestId = i;
    }

    public com.android.ims.internal.uce.presence.PresSubscriptionState getPresSubscriptionState() {
        return this.mPresSubscriptionState;
    }

    public void setPresSubscriptionState(com.android.ims.internal.uce.presence.PresSubscriptionState presSubscriptionState) {
        this.mPresSubscriptionState = presSubscriptionState;
    }

    public int getSubscriptionExpireTime() {
        return this.mSubscriptionExpireTime;
    }

    public void setSubscriptionExpireTime(int i) {
        this.mSubscriptionExpireTime = i;
    }

    public java.lang.String getSubscriptionTerminatedReason() {
        return this.mSubscriptionTerminatedReason;
    }

    public void setSubscriptionTerminatedReason(java.lang.String str) {
        this.mSubscriptionTerminatedReason = str;
    }

    public PresRlmiInfo() {
        this.mUri = "";
        this.mListName = "";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mUri);
        parcel.writeInt(this.mVersion);
        parcel.writeInt(this.mFullState ? 1 : 0);
        parcel.writeString(this.mListName);
        parcel.writeInt(this.mRequestId);
        parcel.writeParcelable(this.mPresSubscriptionState, i);
        parcel.writeInt(this.mSubscriptionExpireTime);
        parcel.writeString(this.mSubscriptionTerminatedReason);
    }

    private PresRlmiInfo(android.os.Parcel parcel) {
        this.mUri = "";
        this.mListName = "";
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mUri = parcel.readString();
        this.mVersion = parcel.readInt();
        this.mFullState = parcel.readInt() != 0;
        this.mListName = parcel.readString();
        this.mRequestId = parcel.readInt();
        this.mPresSubscriptionState = (com.android.ims.internal.uce.presence.PresSubscriptionState) parcel.readParcelable(com.android.ims.internal.uce.presence.PresSubscriptionState.class.getClassLoader(), com.android.ims.internal.uce.presence.PresSubscriptionState.class);
        this.mSubscriptionExpireTime = parcel.readInt();
        this.mSubscriptionTerminatedReason = parcel.readString();
    }
}
