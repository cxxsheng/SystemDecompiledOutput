package com.android.ims.internal.uce.options;

/* loaded from: classes4.dex */
public class OptionsCapInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.options.OptionsCapInfo> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.options.OptionsCapInfo>() { // from class: com.android.ims.internal.uce.options.OptionsCapInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.options.OptionsCapInfo createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.options.OptionsCapInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.options.OptionsCapInfo[] newArray(int i) {
            return new com.android.ims.internal.uce.options.OptionsCapInfo[i];
        }
    };
    private com.android.ims.internal.uce.common.CapInfo mCapInfo;
    private java.lang.String mSdp;

    public static com.android.ims.internal.uce.options.OptionsCapInfo getOptionsCapInfoInstance() {
        return new com.android.ims.internal.uce.options.OptionsCapInfo();
    }

    public java.lang.String getSdp() {
        return this.mSdp;
    }

    public void setSdp(java.lang.String str) {
        this.mSdp = str;
    }

    public OptionsCapInfo() {
        this.mSdp = "";
        this.mCapInfo = new com.android.ims.internal.uce.common.CapInfo();
    }

    public com.android.ims.internal.uce.common.CapInfo getCapInfo() {
        return this.mCapInfo;
    }

    public void setCapInfo(com.android.ims.internal.uce.common.CapInfo capInfo) {
        this.mCapInfo = capInfo;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mSdp);
        parcel.writeParcelable(this.mCapInfo, i);
    }

    private OptionsCapInfo(android.os.Parcel parcel) {
        this.mSdp = "";
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mSdp = parcel.readString();
        this.mCapInfo = (com.android.ims.internal.uce.common.CapInfo) parcel.readParcelable(com.android.ims.internal.uce.common.CapInfo.class.getClassLoader(), com.android.ims.internal.uce.common.CapInfo.class);
    }
}
