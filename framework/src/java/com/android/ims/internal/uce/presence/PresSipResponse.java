package com.android.ims.internal.uce.presence;

/* loaded from: classes4.dex */
public class PresSipResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresSipResponse> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresSipResponse>() { // from class: com.android.ims.internal.uce.presence.PresSipResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresSipResponse createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.presence.PresSipResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresSipResponse[] newArray(int i) {
            return new com.android.ims.internal.uce.presence.PresSipResponse[i];
        }
    };
    private com.android.ims.internal.uce.presence.PresCmdId mCmdId;
    private java.lang.String mReasonHeader;
    private java.lang.String mReasonPhrase;
    private int mRequestId;
    private int mRetryAfter;
    private int mSipResponseCode;

    public com.android.ims.internal.uce.presence.PresCmdId getCmdId() {
        return this.mCmdId;
    }

    public void setCmdId(com.android.ims.internal.uce.presence.PresCmdId presCmdId) {
        this.mCmdId = presCmdId;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public void setRequestId(int i) {
        this.mRequestId = i;
    }

    public int getSipResponseCode() {
        return this.mSipResponseCode;
    }

    public void setSipResponseCode(int i) {
        this.mSipResponseCode = i;
    }

    public java.lang.String getReasonPhrase() {
        return this.mReasonPhrase;
    }

    public void setReasonPhrase(java.lang.String str) {
        this.mReasonPhrase = str;
    }

    public int getRetryAfter() {
        return this.mRetryAfter;
    }

    public void setRetryAfter(int i) {
        this.mRetryAfter = i;
    }

    public java.lang.String getReasonHeader() {
        return this.mReasonHeader;
    }

    public void setReasonHeader(java.lang.String str) {
        this.mReasonHeader = str;
    }

    public PresSipResponse() {
        this.mCmdId = new com.android.ims.internal.uce.presence.PresCmdId();
        this.mRequestId = 0;
        this.mSipResponseCode = 0;
        this.mRetryAfter = 0;
        this.mReasonPhrase = "";
        this.mReasonHeader = "";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRequestId);
        parcel.writeInt(this.mSipResponseCode);
        parcel.writeString(this.mReasonPhrase);
        parcel.writeParcelable(this.mCmdId, i);
        parcel.writeInt(this.mRetryAfter);
        parcel.writeString(this.mReasonHeader);
    }

    private PresSipResponse(android.os.Parcel parcel) {
        this.mCmdId = new com.android.ims.internal.uce.presence.PresCmdId();
        this.mRequestId = 0;
        this.mSipResponseCode = 0;
        this.mRetryAfter = 0;
        this.mReasonPhrase = "";
        this.mReasonHeader = "";
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mRequestId = parcel.readInt();
        this.mSipResponseCode = parcel.readInt();
        this.mReasonPhrase = parcel.readString();
        this.mCmdId = (com.android.ims.internal.uce.presence.PresCmdId) parcel.readParcelable(com.android.ims.internal.uce.presence.PresCmdId.class.getClassLoader(), com.android.ims.internal.uce.presence.PresCmdId.class);
        this.mRetryAfter = parcel.readInt();
        this.mReasonHeader = parcel.readString();
    }
}
