package com.android.ims.internal.uce.presence;

/* loaded from: classes4.dex */
public class PresResInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresResInfo> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresResInfo>() { // from class: com.android.ims.internal.uce.presence.PresResInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresResInfo createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.presence.PresResInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresResInfo[] newArray(int i) {
            return new com.android.ims.internal.uce.presence.PresResInfo[i];
        }
    };
    private java.lang.String mDisplayName;
    private com.android.ims.internal.uce.presence.PresResInstanceInfo mInstanceInfo;
    private java.lang.String mResUri;

    public com.android.ims.internal.uce.presence.PresResInstanceInfo getInstanceInfo() {
        return this.mInstanceInfo;
    }

    public void setInstanceInfo(com.android.ims.internal.uce.presence.PresResInstanceInfo presResInstanceInfo) {
        this.mInstanceInfo = presResInstanceInfo;
    }

    public java.lang.String getResUri() {
        return this.mResUri;
    }

    public void setResUri(java.lang.String str) {
        this.mResUri = str;
    }

    public java.lang.String getDisplayName() {
        return this.mDisplayName;
    }

    public void setDisplayName(java.lang.String str) {
        this.mDisplayName = str;
    }

    public PresResInfo() {
        this.mResUri = "";
        this.mDisplayName = "";
        this.mInstanceInfo = new com.android.ims.internal.uce.presence.PresResInstanceInfo();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mResUri);
        parcel.writeString(this.mDisplayName);
        parcel.writeParcelable(this.mInstanceInfo, i);
    }

    private PresResInfo(android.os.Parcel parcel) {
        this.mResUri = "";
        this.mDisplayName = "";
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mResUri = parcel.readString();
        this.mDisplayName = parcel.readString();
        this.mInstanceInfo = (com.android.ims.internal.uce.presence.PresResInstanceInfo) parcel.readParcelable(com.android.ims.internal.uce.presence.PresResInstanceInfo.class.getClassLoader(), com.android.ims.internal.uce.presence.PresResInstanceInfo.class);
    }
}
