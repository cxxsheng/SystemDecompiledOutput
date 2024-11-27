package com.android.ims.internal.uce.presence;

/* loaded from: classes4.dex */
public class PresCapInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresCapInfo> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresCapInfo>() { // from class: com.android.ims.internal.uce.presence.PresCapInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresCapInfo createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.presence.PresCapInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresCapInfo[] newArray(int i) {
            return new com.android.ims.internal.uce.presence.PresCapInfo[i];
        }
    };
    private com.android.ims.internal.uce.common.CapInfo mCapInfo;
    private java.lang.String mContactUri;

    public com.android.ims.internal.uce.common.CapInfo getCapInfo() {
        return this.mCapInfo;
    }

    public void setCapInfo(com.android.ims.internal.uce.common.CapInfo capInfo) {
        this.mCapInfo = capInfo;
    }

    public java.lang.String getContactUri() {
        return this.mContactUri;
    }

    public void setContactUri(java.lang.String str) {
        this.mContactUri = str;
    }

    public PresCapInfo() {
        this.mContactUri = "";
        this.mCapInfo = new com.android.ims.internal.uce.common.CapInfo();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mContactUri);
        parcel.writeParcelable(this.mCapInfo, i);
    }

    private PresCapInfo(android.os.Parcel parcel) {
        this.mContactUri = "";
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mContactUri = parcel.readString();
        this.mCapInfo = (com.android.ims.internal.uce.common.CapInfo) parcel.readParcelable(com.android.ims.internal.uce.common.CapInfo.class.getClassLoader(), com.android.ims.internal.uce.common.CapInfo.class);
    }
}
