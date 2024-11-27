package com.android.ims.internal.uce.common;

/* loaded from: classes4.dex */
public class UceLong implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.common.UceLong> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.common.UceLong>() { // from class: com.android.ims.internal.uce.common.UceLong.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.common.UceLong createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.common.UceLong(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.common.UceLong[] newArray(int i) {
            return new com.android.ims.internal.uce.common.UceLong[i];
        }
    };
    private int mClientId;
    private long mUceLong;

    public UceLong() {
        this.mClientId = 1001;
    }

    public long getUceLong() {
        return this.mUceLong;
    }

    public void setUceLong(long j) {
        this.mUceLong = j;
    }

    public int getClientId() {
        return this.mClientId;
    }

    public void setClientId(int i) {
        this.mClientId = i;
    }

    public static com.android.ims.internal.uce.common.UceLong getUceLongInstance() {
        return new com.android.ims.internal.uce.common.UceLong();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        writeToParcel(parcel);
    }

    private void writeToParcel(android.os.Parcel parcel) {
        parcel.writeLong(this.mUceLong);
        parcel.writeInt(this.mClientId);
    }

    private UceLong(android.os.Parcel parcel) {
        this.mClientId = 1001;
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mUceLong = parcel.readLong();
        this.mClientId = parcel.readInt();
    }
}
