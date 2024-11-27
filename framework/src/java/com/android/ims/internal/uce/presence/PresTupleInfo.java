package com.android.ims.internal.uce.presence;

/* loaded from: classes4.dex */
public class PresTupleInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresTupleInfo> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresTupleInfo>() { // from class: com.android.ims.internal.uce.presence.PresTupleInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresTupleInfo createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.presence.PresTupleInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresTupleInfo[] newArray(int i) {
            return new com.android.ims.internal.uce.presence.PresTupleInfo[i];
        }
    };
    private java.lang.String mContactUri;
    private java.lang.String mFeatureTag;
    private java.lang.String mTimestamp;
    private java.lang.String mVersion;

    public java.lang.String getFeatureTag() {
        return this.mFeatureTag;
    }

    public void setFeatureTag(java.lang.String str) {
        this.mFeatureTag = str;
    }

    public java.lang.String getContactUri() {
        return this.mContactUri;
    }

    public void setContactUri(java.lang.String str) {
        this.mContactUri = str;
    }

    public java.lang.String getTimestamp() {
        return this.mTimestamp;
    }

    public void setTimestamp(java.lang.String str) {
        this.mTimestamp = str;
    }

    public java.lang.String getVersion() {
        return this.mVersion;
    }

    public void setVersion(java.lang.String str) {
        this.mVersion = str;
    }

    public PresTupleInfo() {
        this.mFeatureTag = "";
        this.mContactUri = "";
        this.mTimestamp = "";
        this.mVersion = "";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mFeatureTag);
        parcel.writeString(this.mContactUri);
        parcel.writeString(this.mTimestamp);
        parcel.writeString(this.mVersion);
    }

    private PresTupleInfo(android.os.Parcel parcel) {
        this.mFeatureTag = "";
        this.mContactUri = "";
        this.mTimestamp = "";
        this.mVersion = "";
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mFeatureTag = parcel.readString();
        this.mContactUri = parcel.readString();
        this.mTimestamp = parcel.readString();
        this.mVersion = parcel.readString();
    }
}
