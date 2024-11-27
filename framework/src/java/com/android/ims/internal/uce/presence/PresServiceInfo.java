package com.android.ims.internal.uce.presence;

/* loaded from: classes4.dex */
public class PresServiceInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresServiceInfo> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresServiceInfo>() { // from class: com.android.ims.internal.uce.presence.PresServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresServiceInfo createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.presence.PresServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresServiceInfo[] newArray(int i) {
            return new com.android.ims.internal.uce.presence.PresServiceInfo[i];
        }
    };
    public static final int UCE_PRES_MEDIA_CAP_FULL_AUDIO_AND_VIDEO = 2;
    public static final int UCE_PRES_MEDIA_CAP_FULL_AUDIO_ONLY = 1;
    public static final int UCE_PRES_MEDIA_CAP_NONE = 0;
    public static final int UCE_PRES_MEDIA_CAP_UNKNOWN = 3;
    private int mMediaCap;
    private java.lang.String mServiceDesc;
    private java.lang.String mServiceID;
    private java.lang.String mServiceVer;

    public int getMediaType() {
        return this.mMediaCap;
    }

    public void setMediaType(int i) {
        this.mMediaCap = i;
    }

    public java.lang.String getServiceId() {
        return this.mServiceID;
    }

    public void setServiceId(java.lang.String str) {
        this.mServiceID = str;
    }

    public java.lang.String getServiceDesc() {
        return this.mServiceDesc;
    }

    public void setServiceDesc(java.lang.String str) {
        this.mServiceDesc = str;
    }

    public java.lang.String getServiceVer() {
        return this.mServiceVer;
    }

    public void setServiceVer(java.lang.String str) {
        this.mServiceVer = str;
    }

    public PresServiceInfo() {
        this.mMediaCap = 0;
        this.mServiceID = "";
        this.mServiceDesc = "";
        this.mServiceVer = "";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mServiceID);
        parcel.writeString(this.mServiceDesc);
        parcel.writeString(this.mServiceVer);
        parcel.writeInt(this.mMediaCap);
    }

    private PresServiceInfo(android.os.Parcel parcel) {
        this.mMediaCap = 0;
        this.mServiceID = "";
        this.mServiceDesc = "";
        this.mServiceVer = "";
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mServiceID = parcel.readString();
        this.mServiceDesc = parcel.readString();
        this.mServiceVer = parcel.readString();
        this.mMediaCap = parcel.readInt();
    }
}
